package io.github.iidxTe.ohtu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import io.github.iidxTe.ohtu.model.Book;
import io.github.iidxTe.ohtu.model.Bookmark;
import io.github.iidxTe.ohtu.model.User;

@Repository
@Primary
public class DatabaseDao implements BookmarkDao, UserDao {

    @Autowired
    private DataSource db;

    /**
     * Our passwords are encoded with this. The encoding used is stored as
     * prefix of the password. At time of writing, Spring Security used Bcrypt
     * for encoding.
     */
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @PostConstruct
    private void init() {
        try (Connection conn = db.getConnection()) {
            // Books table
            PreparedStatement query = conn.prepareStatement("CREATE TABLE IF NOT EXISTS books ("
                    + "    id SERIAL PRIMARY KEY,"
                    + "    title VARCHAR,"
                    + "    author VARCHAR,"
                    + "    isbn VARCHAR,"
                    + "    creator VARCHAR,"
                    + "    visible BOOLEAN"
                    + ")");
            query.executeUpdate();

            // Users table
            PreparedStatement query2 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS users ("
                    + "    id SERIAL PRIMARY KEY,"
                    + "    name VARCHAR NOT NULL,"
                    + "    password VARCHAR NOT NULL,"
                    + "    displayName VARCHAR NOT NULL,"
                    + "    readerGroup VARCHAR"
                    + ")");
            query2.executeUpdate();

            //UserBook table
            PreparedStatement query3 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS userbook ("
                    + "    id SERIAL PRIMARY KEY,"
                    + "    user_id INTEGER NOT NULL,"
                    + "    book_id INTEGER NOT NULL,"
                    + "    owner BOOLEAN,"
                    + "    hasRead BOOLEAN"
                    + ")");
            query3.executeUpdate();

        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public List<Bookmark> getOwnedBookmarks(User user) {
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("SELECT books.id, books.title, books.author, books.isbn, users.displayName, userbook.hasRead"
                    + "    FROM books, users, userbook"
                    + "    WHERE books.id = userbook.book_id"
                    + "    AND users.id = userbook.user_id"
                    + "    AND userbook.user_id = ?"
                    + "    AND userbook.owner = ?"
                    + "    ORDER BY books.title");
            query.setInt(1, user.getId());
            query.setBoolean(2, true);

            ResultSet results = query.executeQuery();

            List<Bookmark> bookmarks = new ArrayList<>();

            while (results.next()) {
                Bookmark book = new Book(results.getString("books.title"),
                        results.getString("author"), results.getString("books.isbn"), results.getString("users.displayName"));
                book.setId(results.getInt("books.id"));
                book.setIsRead(results.getBoolean("userbook.hasRead"));
                bookmarks.add(book);
            }

            return bookmarks;

        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public List<Bookmark> getVisibleBookmarks(User user) {
        if (user.getGroup() == null) { // User is not in group, so we'll take only their own bookmarks
            return getOwnedBookmarks(user);
        } else {
            // Get group members, including the user themself
            List<User> groupMembers = getUsersByGroup(user.getGroup());

            // Get ALL books from ALL members - in a not terribly efficient way
            // Each user comes only once, so no need to worry about duplicate bookmarks
            List<Bookmark> bookmarks = new ArrayList<>();
            for (User member : groupMembers) {

                bookmarks.addAll(getOwnedBookmarks(member));
            }

            for (Bookmark bookmark : bookmarks) {
                for (User member : groupMembers) {
                    checkuserbook(member.getId(), bookmark);
                }
            }
            List<Bookmark> allBookmarks = new ArrayList<>();
            for (Bookmark bookmark : bookmarks) {
                allBookmarks.add(getBookmarkById(bookmark.getId(), user.getId()));
            }
            return allBookmarks;
        }
    }

    @Override
    public void addBookmark(User user, Bookmark bookmark) {
        try (Connection conn = db.getConnection()) {
            Book book = (Book) bookmark;
            PreparedStatement query = conn.prepareStatement("INSERT INTO books (title, author, isbn, creator, visible) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            query.setString(1, book.getTitle());
            query.setString(2, book.getAuthor());
            query.setString(3, book.getIsbn());
            query.setString(4, user.getDisplayName());
            query.setBoolean(5, true);
            query.executeUpdate();

            try (ResultSet keys = query.getGeneratedKeys()) {
                keys.next();
                book.setId((int) keys.getLong(1));
            }

            PreparedStatement query2 = conn.prepareStatement("INSERT INTO userbook (user_id, book_id, owner, hasRead) VALUES (?, ?, ?, ?)");
            query2.setInt(1, user.getId());
            query2.setInt(2, book.getId());
            query2.setBoolean(3, true);
            query2.setBoolean(4, false);
            query2.executeUpdate();

        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void updateBookmark(int userId, Bookmark bookmark) {
        try (Connection conn = db.getConnection()) {
            Book book = (Book) bookmark;
            PreparedStatement query = conn.prepareStatement("UPDATE books SET title=?, author=?, isbn=?"
                    + " WHERE books.id=?");
            query.setString(1, book.getTitle());
            query.setString(2, book.getAuthor());
            query.setString(3, book.getIsbn());
            query.setInt(4, book.getId());
            query.executeUpdate();

            PreparedStatement query2 = conn.prepareStatement("UPDATE userbook SET hasRead=?"
                    + " WHERE book_id=?"
                    + " AND user_id=?");
            //Not updating owner, bookmarks can't change owner
            query2.setBoolean(1, book.isRead());
            query2.setInt(2, book.getId());
            query2.setInt(3, userId);
            query2.executeUpdate();

        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public Bookmark getBookmarkById(int id, int userId) {
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM books, userbook"
                    + " WHERE books.id = userbook.book_id"
                    + " AND books.id=?"
                    + " AND user_id=?");
            query.setInt(1, id);
            query.setInt(2, userId);
            ResultSet results = query.executeQuery();

            if (results.next()) { // Expecting 0-1 results
                Bookmark book = new Book(results.getString("title"),
                        results.getString("author"), results.getString("isbn"), results.getString("creator"));
                book.setId(results.getInt("books.id"));
                book.setIsRead(results.getBoolean("hasRead"));
                return book;
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
        return null;
    }

    /**
     * Assembles an user from data returned from database.
     *
     * @param results Database result set.
     * @return An user.
     * @throws SQLException Data returned was not what we expected.
     */
    private User userFromDb(ResultSet results) throws SQLException {
        User user = new User(results.getString("name"));
        user.setId(results.getInt("id"));
        user.setDisplayName(results.getString("displayName"));
        user.setGroup(results.getString("readerGroup"));
        return user;
    }

    @Override
    public User getUser(String name) {
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM users WHERE name=?");
            query.setString(1, name);
            ResultSet results = query.executeQuery();

            if (results.next()) {
                return userFromDb(results);
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
        return null;
    }

    @Override
    public List<User> getUsersByGroup(String group) {
        List<User> users = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM users WHERE readerGroup=?");
            query.setString(1, group);
            ResultSet results = query.executeQuery();

            // Read all users with matching group
            while (results.next()) {
                users.add(userFromDb(results));
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
        return users; // Return users, if any
    }

    @Override
    public User createUser(String name, String password) {
        if (getUser(name) != null) {
            throw new IllegalStateException("user already exists");
        }

        User user = new User(name);
        user.setDisplayName(name); // Initial display name is name

        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("INSERT INTO users (name,password,displayName) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            query.setString(1, user.getName());
            query.setString(2, passwordEncoder.encode(password));
            query.setString(3, user.getDisplayName());
            query.executeUpdate();

            try (ResultSet keys = query.getGeneratedKeys()) {
                keys.next();
                user.setId((int) keys.getLong(1));
            }
            return user;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void updateUser(User user) {
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("UPDATE users SET displayName=?, readerGroup=? WHERE id=?");
            query.setString(1, user.getDisplayName());
            query.setString(2, user.getGroup());
            query.setInt(3, user.getId());

            query.executeUpdate();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void deleteBookmark(int bookId) {
        try (Connection conn = db.getConnection()) {

            PreparedStatement query2 = conn.prepareStatement("DELETE FROM userbook"
                    + " WHERE book_id = ?");
            query2.setInt(1, bookId);
            query2.executeUpdate();

            PreparedStatement query = conn.prepareStatement("DELETE FROM books"
                    + " WHERE books.id = ?");
            query.setInt(1, bookId);
            query.executeUpdate();

        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public boolean isOwner(int userId, int bookmarkId) {
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM userbook"
                    + " WHERE book_id = ?"
                    + " AND user_id=?");
            query.setInt(1, bookmarkId);
            query.setInt(2, userId);
            ResultSet results = query.executeQuery();

            boolean isOwner = false;
            if (results.next()) { // Expecting 0-1 results
                isOwner = results.getBoolean("owner");
            }

            return isOwner;

        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void updateHasread(int userId, Bookmark bookmark) {
        try (Connection conn = db.getConnection()) {
            Book book = (Book) bookmark;
            PreparedStatement query3 = conn.prepareStatement("UPDATE userbook SET hasRead=?"
                    + " WHERE book_id=?"
                    + " AND user_id=?");
            query3.setBoolean(1, book.isRead());
            query3.setInt(2, book.getId());
            query3.setInt(3, userId);
            query3.executeUpdate();

        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    private void checkuserbook(int userId, Bookmark bookmark) {
        try (Connection conn = db.getConnection()) {
            Book book = (Book) bookmark;
            PreparedStatement query = conn.prepareStatement("SELECT * FROM userbook"
                    + " WHERE book_id = ?"
                    + " AND user_id=?");
            query.setInt(1, book.getId());
            query.setInt(2, userId);
            ResultSet results = query.executeQuery();

            int counter = 0;
            if (results.next()) { // Expecting 0-1 results
                counter++;
            }

            if (counter == 0) {
                PreparedStatement query2 = conn.prepareStatement("INSERT INTO userbook (user_id, book_id, owner, hasRead) VALUES (?, ?, ?, ?)");
                query2.setInt(1, userId);
                query2.setInt(2, book.getId());
                query2.setBoolean(3, false);
                query2.setBoolean(4, false);
                query2.executeUpdate();
            }

        } catch (SQLException e) {
            throw new Error(e);
        }
    }

}
