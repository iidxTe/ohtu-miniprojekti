package io.github.iidxTe.ohtu;

import io.github.iidxTe.ohtu.model.Book;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {
    
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void constructorCreatesNewBook() {
        Book book = new Book("abc", "matti", "123", "matti");
        
        assertEquals("abc", book.getTitle());
        assertEquals("matti", book.getAuthor());
        assertEquals("123", book.getIsbn());
        assertEquals("book", book.getType());
    }
}
