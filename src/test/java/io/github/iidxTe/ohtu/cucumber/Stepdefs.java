package io.github.iidxTe.ohtu.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.github.iidxTe.ohtu.dao.ListDao;
import io.github.iidxTe.ohtu.domain.BookmarkService;
import io.github.iidxTe.ohtu.model.Book;
import io.github.iidxTe.ohtu.model.Bookmark;
import io.github.iidxTe.ohtu.model.User;

import static org.junit.Assert.*;

public class Stepdefs {

    BookmarkService service;
    Book book;
    User user;

    public Stepdefs() {
        service = new BookmarkService();
        service.setDao(new ListDao());
        user = new User("test");
        user.setId(0);
    }
    
    @Given ("the list is empty")
    public void theListIsEmpty() {
        service.setDao(new ListDao());
    }

    @Given ("the list is not empty")
    public void theListIsNotEmpty() {
        service.setDao(new ListDao());
        service.createBook(user, "Eeva Virtanen", "Tosi hyvä kirja", "13579");
    }

    @When("a book is added")
    public void aBookIsAdded() {
        service.createBook(user, "Samuli Turska", "Näin kalastat kalaa", "314666");
    }

    @And("another book is added")
    public void anotherBookIsAdded() {
        service.createBook(user, "Katariina Suuri", "Näin hallitset maailmaa", "9999999");
    }

    @Then("the book should be on the list")
    public void theBookShouldBeOnTheList() {
        boolean found = false;
        for (Bookmark bookmark : service.listAll(user)) {
            Book current = ((Book) bookmark);
            if (current.getIsbn().equals("314666")) {
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
        for (Bookmark bookmark : service.listAll(user)) {
            Book current = ((Book) bookmark);
            if (current.getIsbn().equals("314666")) {
                found1 = true;
            }
            if (current.getIsbn().equals("9999999")) {
                found2 = true;
            }
        }
        assertTrue(found1 && found2);
    }

}
