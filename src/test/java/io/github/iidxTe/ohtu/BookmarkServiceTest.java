package io.github.iidxTe.ohtu;

import io.github.iidxTe.ohtu.domain.BookmarkService;
import io.github.iidxTe.ohtu.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookmarkServiceTest {

    private BookmarkService service;

    public BookmarkServiceTest() {
        service = new BookmarkService();
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void listAllWithEmptyListReturnsEmpty() {
        assertEquals(0, service.listAll().size());
        assertEquals("[]", service.listAll().toString());
    }

    @Test
    public void creatingItemIncreasesListAllSize() {
        service.createBook("abc", "matti", "123");
        assertEquals(1, service.listAll().size());
    }

    @Test
    public void listAllWithOneItemAddedReturnsThatItem() {
        Book book = new Book("abc", "matti", "123");
        service.createBook("abc", "matti", "123");
        assertEquals(book.toString(), service.listAll().get(0).toString());
    }

    @Test
    public void listAllWithMultipleItemsAddedReturnsThoseItems() {
        Book book = new Book("abc", "matti", "123");
        Book book2 = new Book("ihmemaa", "liisa", "456");
        service.createBook("abc", "matti", "123");
        service.createBook("ihmemaa", "liisa", "456");
        assertEquals(book.toString(), service.listAll().get(0).toString());
        assertEquals(book2.toString(), service.listAll().get(1).toString());
    }

}
