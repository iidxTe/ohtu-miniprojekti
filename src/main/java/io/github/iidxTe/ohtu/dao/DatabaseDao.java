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
            PreparedStatement query = conn.prepareStatement("CREATE TABLE IF NOT EXISTS books (" + 
                    "    id SERIAL PRIMARY KEY," + 
                    "    userId INTEGER," + 
                    "    title VARCHAR," + 
                    "    author VARCHAR," + 
                    "    isbn VARCHAR," + 
                    "    isRead BOOLEAN" + 
                    ")");
            query.executeUpdate();
            
            // Users table
            PreparedStatement query2 = conn.prepareStatement("CREATE TABLE IF NOT EXISTS users ("
                    + "    id SERIAL PRIMARY KEY,"
                    + "    name VARCHAR NOT NULL,"
                    + "    password VARCHAR NOT NULL,"
                    + "    displayName VARCHAR NOT NULL"
                    + ")");
            query2.executeUpdate();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    
    @Override
    public List<Bookmark> getAll(User user) {
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM books WHERE userId=?");
            query.setInt(1, user.getId());
            ResultSet results = query.executeQuery();
            
            List<Bookmark> bookmarks = new ArrayList<>();
            while (results.next()) {
                Bookmark book = new Book(results.getString("title"),
                        results.getString("author"), results.getString("isbn"));
                book.setId(results.getInt("id"));
                book.setIsRead(results.getBoolean("isRead"));
                bookmarks.add(book);
            }
            return bookmarks;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void add(User user, Bookmark bookmark) {
        try (Connection conn = db.getConnection()) {
            Book book = (Book) bookmark;
            PreparedStatement query = conn.prepareStatement("INSERT INTO books (title,userId,author,isbn,isRead) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            query.setString(1, book.getTitle());
            query.setInt(2, user.getId());
            query.setString(3, book.getAuthor());
            query.setString(4, book.getIsbn());
            query.setBoolean(5, book.isRead());
            query.executeUpdate();
            
            try (ResultSet keys = query.getGeneratedKeys()) {
                keys.next();
                book.setId((int) keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void update(Bookmark bookmark) {
        try (Connection conn = db.getConnection()) {
            Book book = (Book) bookmark;
            PreparedStatement query = conn.prepareStatement("UPDATE books SET title=?, author=?, isbn=?, isRead=? WHERE id=?");
            query.setString(1, book.getTitle());
            // Not updating userId, bookmarks cannot change owner
            query.setString(2, book.getAuthor());
            query.setString(3, book.getIsbn());
            query.setBoolean(4, book.isRead());
            query.setInt(5, book.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public Bookmark getById(int id) {
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM books WHERE id=?");
            query.setInt(1, id);
            ResultSet results = query.executeQuery();
            
            if (results.next()) { // Expecting 0-1 results
                Bookmark book = new Book(results.getString("title"),
                        results.getString("author"), results.getString("isbn"));
                book.setId(results.getInt("id"));
                book.setIsRead(results.getBoolean("isRead"));
                return book;
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
        return null;
    }

    @Override
    public User getUser(String name) {
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM users WHERE name=?");
            query.setString(1, name);
            ResultSet results = query.executeQuery();
            
            if (results.next()) {
                User user = new User(name);
                user.setId(results.getInt("id"));
                user.setDisplayName(results.getString("displayName"));
                return user;
            }
        } catch (SQLException e) {
            throw new Error(e);
        }
        return null;
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
            PreparedStatement query = conn.prepareStatement("UPDATE users SET displayName=? WHERE id=?");
            query.setString(1, user.getDisplayName());
            query.setInt(2, user.getId());
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

}
