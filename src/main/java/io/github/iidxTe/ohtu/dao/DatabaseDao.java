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
            try (PreparedStatement query = conn.prepareStatement("CREATE TABLE IF NOT EXISTS books ("
                    + "    id SERIAL PRIMARY KEY,"
                    + "    title VARCHAR,"
                    + "    author VARCHAR,"
                    + "    isbn VARCHAR,"
                    + "    creator VARCHAR,"
                    + "    onList BOOLEAN"
                    + ")")) {
            	
                query.executeUpdate();
            }
            
            // Users table
            try (PreparedStatement query = conn.prepareStatement("CREATE TABLE IF NOT EXISTS users ("
                    + "    id SERIAL PRIMARY KEY,"
                    + "    name VARCHAR NOT NULL,"
                    + "    password VARCHAR NOT NULL,"
                    + "    displayName VARCHAR NOT NULL,"
                    + "    readerGroup VARCHAR"
                    + ")")) {
            	
                query.executeUpdate();
            }
            
            //UserBook table
            
            try (PreparedStatement query = conn.prepareStatement("CREATE TABLE IF NOT EXISTS userbook ("
                    + "    id SERIAL PRIMARY KEY,"
                    + "    user_id INTEGER NOT NULL,"
                    + "    book_id INTEGER NOT NULL,"
                    + "    hasRead BOOLEAN,"
                    + "    FOREIGN KEY (user_id) REFERENCES users(id),"
                    + "    FOREIGN KEY (book_id) REFERENCES books(id)"
                    + ")")) {
            	
                query.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    
    @Override
    public List<Bookmark> getOwnedBookmarks(User user) {
        try (Connection conn = db.getConnection(); PreparedStatement query = conn.prepareStatement("SELECT b.id, title, author, isbn, creator, hasRead FROM userbook u"
                + " LEFT JOIN books b ON b.id = u.id"
                + " WHERE u.user_id = ?")) {
        	
            query.setInt(1, user.getId());
            
            try (ResultSet results = query.executeQuery()) {
	            List<Bookmark> bookmarks = new ArrayList<>();
	            
	            while (results.next()) {
	                bookmarks.add(new Book(results));
	            }
	
	            return bookmarks;
            }
            
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
            
            return bookmarks;
        }
    }

    @Override
    public void addBookmark(User user, Bookmark bookmark) {
        try (Connection conn = db.getConnection()) {
        	
            Book book = (Book) bookmark;
            try (PreparedStatement query = conn.prepareStatement("INSERT INTO books (title, author, isbn, creator, onList) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            	
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
	        }
            
            try (PreparedStatement query = conn.prepareStatement("INSERT INTO userbook (user_id, book_id, hasRead) VALUES (?, ?, ?)")) {
            	
            	query.setInt(1, user.getId());
	            query.setInt(2, book.getId());
	            query.setBoolean(3, bookmark.isRead());
	            
	            query.executeUpdate();    
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void updateBookmark(Bookmark bookmark) {
        try (Connection conn = db.getConnection()) {
            Book book = (Book) bookmark;
            try (PreparedStatement query = conn.prepareStatement("UPDATE books SET title = ?, author = ?, isbn = ?"
                    + " WHERE books.id = ?")) {
            	
	            query.setString(1, book.getTitle());
	            query.setString(2, book.getAuthor());
	            query.setString(3, book.getIsbn());
	            query.setInt(4, book.getId());
	            
                query.executeUpdate();
            }
            
            try (PreparedStatement query = conn.prepareStatement("UPDATE userbook SET hasRead = ?"
                    + " WHERE book_id = ?")) {
            	
	            //Not updating owner, bookmarks can't change owner
            	query.setBoolean(1, book.isRead());
            	query.setInt(2, book.getId());
            	
            	query.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public Bookmark getBookmarkById(int id) {
        try (Connection conn = db.getConnection()) {
            try (PreparedStatement query = conn.prepareStatement("SELECT u.id, title, author, isbn, creator, hasRead FROM userbook u"
                    + " LEFT JOIN books b ON b.id = u.id"
                    + " WHERE u.id = ?")) {
            	
	            query.setInt(1, id);
	            
	            try (ResultSet results = query.executeQuery()) {
		            if (results.next()) {
		                return new Book(results);
		            }
	            }
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
        
        return null;
    }

    @Override
    public User getUser(String name) {
        try (Connection conn = db.getConnection(); PreparedStatement query = conn.prepareStatement("SELECT id, name, displayName, readerGroup FROM users WHERE name = ?")) {
        	
        	query.setString(1, name);
            
            try (ResultSet results = query.executeQuery()) {
	            if (results.next()) {
	                return new User(results);
	            }
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
        
        return null;
    }
    
    @Override
    public List<User> getUsersByGroup(String group) {
        List<User> users = new ArrayList<>();
        try (Connection conn = db.getConnection(); PreparedStatement query = conn.prepareStatement("SELECT id, name, displayName, readerGroup FROM users WHERE readerGroup = ?")) {
            
        	query.setString(1, group);
            
            try (ResultSet results = query.executeQuery()) {
	            while (results.next()) {
	                users.add(new User(results));
	            }
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
        
        try (Connection conn = db.getConnection();
        		PreparedStatement query = conn.prepareStatement("INSERT INTO users (name, password, displayName) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
        	
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
        try (Connection conn = db.getConnection();
        		PreparedStatement query = conn.prepareStatement("UPDATE users SET displayName = ?, readerGroup = ? WHERE id = ?")) {
        	
            query.setString(1, user.getDisplayName());
            query.setString(2, user.getGroup());
            query.setInt(3, user.getId());
            
            query.executeUpdate();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void deleteBookmark(int bookId, boolean permanent) {
        try (Connection conn = db.getConnection()) {
            if (permanent) {
                try (PreparedStatement query = conn.prepareStatement("DELETE FROM userbook WHERE book_id = ?")) {
                	
                	query.setInt(1, bookId);
                	
            		query.executeUpdate();
                }
                
                try (PreparedStatement query = conn.prepareStatement("DELETE FROM books WHERE books.id = ?")) {
                	
                    query.setInt(1, bookId);
                    
                    query.executeUpdate();      
                }
            } else {
                try (PreparedStatement query = conn.prepareStatement("UPDATE books SET onList = ? WHERE books.id = ?")) {
                	
	                query.setBoolean(1, false);
	                query.setInt(2, bookId);
	                
	                query.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    
    @Override
    public boolean isOwner(int userId, int bookmarkId) {
        try (Connection conn = db.getConnection();
        		PreparedStatement query = conn.prepareStatement("SELECT NULL FROM userbook"
                        + " WHERE book_id = ? AND user_id = ? LIMIT 1")) {
        	
            query.setInt(1, bookmarkId);
            query.setInt(2, userId);
            
            try (ResultSet results = query.executeQuery()) {
                if (results.next()) {
                	return true;
                }
                
                return false;
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
}
