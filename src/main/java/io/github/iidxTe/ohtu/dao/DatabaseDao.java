package io.github.iidxTe.ohtu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.github.iidxTe.ohtu.model.Book;
import io.github.iidxTe.ohtu.model.Bookmark;
import io.github.iidxTe.ohtu.model.User;

@Repository
public class DatabaseDao implements BookmarkDao {

    @Autowired
    private DataSource db;
    
    private void init() {
        try (Connection conn = db.getConnection()) {
            PreparedStatement query = conn.prepareStatement("CREATE TABLE IF NOT EXISTS books (" + 
                    "    id INTEGER PRIMARY KEY AUTO_INCREMENT," + 
                    "    userId INTEGER," + 
                    "    title VARCHAR," + 
                    "    author VARCHAR," + 
                    "    isbn VARCHAR," + 
                    "    isRead BOOLEAN" + 
                    ")");
            query.executeUpdate();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    
    @Override
    public List<Bookmark> getAll(User user) {
        init();
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
        init();
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
        init();
        // TODO Auto-generated method stub
        
    }

    @Override
    public Bookmark getById(int id) {
        init();
        // TODO Auto-generated method stub
        return null;
    }

}
