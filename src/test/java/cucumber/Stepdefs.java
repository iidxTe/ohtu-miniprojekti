package cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.github.iidxTe.ohtu.dao.ListDao;
import io.github.iidxTe.ohtu.model.Book;
import io.github.iidxTe.ohtu.model.Bookmark;

import static org.junit.Assert.*;

public class Stepdefs {

    ListDao listDao;
    Book book;

    @Given ("the list is empty")
    public void theListIsEmpty() {
        listDao = new ListDao();
    }

    @Given ("the list is not empty")
    public void theListIsNotEmpty() {
        listDao = new ListDao();
        book = new Book("Eeva Virtanen", "Tosi hyvä kirja", "13579");
        listDao.add(book);
    }

    @When("a book is added")
    public void aBookIsAdded() {
        book = new Book("Samuli Turska", "Näin kalastat kalaa", "314666");
    }

    @And("another book is added")
    public void anotherBookIsAdded() {
        book = new Book("Katariina Suuri", "Näin hallitset maailmaa", "9999999");
    }

    @Then("the book should be on the list")
    public void theBookShouldBeOnTheList() {
        boolean found = false;
        for (Bookmark bookmark : listDao.getAll()) {
            Book current = ((Book) bookmark);
            if (current.getIsbn() == "314666") {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Then("the books should be on the list")
    public void theBooksShouldBeOnTheList() {
        boolean found1 = false;
        boolean found2 = false;
        for (Bookmark bookmark : listDao.getAll()) {
            Book current = ((Book) bookmark);
            if (current.getIsbn() == "314666") {
                found1 = true;
            }
            if (current.getIsbn() == "9999999") {
                found2 = true;
            }
        }
        assertTrue(found1 && found2);
    }

}
