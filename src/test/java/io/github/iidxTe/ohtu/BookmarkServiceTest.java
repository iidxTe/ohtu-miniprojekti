package io.github.iidxTe.ohtu;

import io.github.iidxTe.ohtu.dao.ListDao;
import io.github.iidxTe.ohtu.domain.BookmarkService;
import io.github.iidxTe.ohtu.model.Book;
import io.github.iidxTe.ohtu.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookmarkServiceTest {

    private BookmarkService service;
    private User user;

    public BookmarkServiceTest() {
        service = new BookmarkService();
        service.setDao(new ListDao());
        user = new User("test");
        user.setId(0);
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void listAllWithEmptyListReturnsEmpty() {
        assertEquals(0, service.listAllByUser(user).size());
        assertEquals("[]", service.listAllByUser(user).toString());
    }

    @Test
    public void creatingItemIncreasesListAllSize() {
        service.createBook(user, "abc", "matti", "123");
        assertEquals(1, service.listAllByUser(user).size());
    }

    @Test
    public void listAllWithOneItemAddedReturnsThatItem() {
        Book book = new Book("abc", "matti", "123");
        service.createBook(user, "abc", "matti", "123");
        assertEquals(book.toString(), service.listAllByUser(user).get(0).toString());
    }

    @Test
    public void listAllWithMultipleItemsAddedReturnsThoseItems() {
        Book book = new Book("abc", "matti", "123");
        Book book2 = new Book("ihmemaa", "liisa", "456");
        service.createBook(user, "abc", "matti", "123");
        service.createBook(user, "ihmemaa", "liisa", "456");
        assertEquals(book.toString(), service.listAllByUser(user).get(0).toString());
        assertEquals(book2.toString(), service.listAllByUser(user).get(1).toString());
    }

}
