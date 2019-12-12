package io.github.iidxTe.ohtu.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.Before;
import io.github.iidxTe.ohtu.testDao.ListBookmarkDao;
import io.github.iidxTe.ohtu.testDao.MapUserDao;
import io.github.iidxTe.ohtu.domain.BookmarkService;
import io.github.iidxTe.ohtu.model.Book;
import io.github.iidxTe.ohtu.model.Bookmark;
import io.github.iidxTe.ohtu.model.User;
import io.github.iidxTe.ohtu.controllers.UserController;
import io.github.iidxTe.ohtu.dao.UserDao;

import static org.junit.Assert.*;

import java.security.Principal;
import java.util.List;

import org.springframework.ui.ConcurrentModel;

public class Stepdefs {

    BookmarkService service;
    Book book;
    User user;
    UserController usercont;

    @Before public void setup() {
        usercont = new UserController();
        service = new BookmarkService();
        user = new User("test");
        user.setId(0);
    }
    
    @Given ("the list is empty")
    public void theListIsEmpty() {
        service.setDao(new ListBookmarkDao());
    }

    @Given ("the list is not empty")
    public void theListIsNotEmpty() {
        service.setDao(new ListBookmarkDao());
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
        for (Bookmark bookmark : service.listAllByUser(user)) {
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
        for (Bookmark bookmark : service.listAllByUser(user)) {
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

    @Given("user is not registered")
    public void userIsNotRegistered() {
        usercont.setDao(new MapUserDao());
    } 

    @When("username {string} with password {string} registers")
    public void userRegisters(String username, String password) {
        usercont.register(new ConcurrentModel(), username, password);
    }

    @Then ("user {string} is registered")
    public void isUserRegistered(String username) {
        assertTrue(usercont.getDao().getUser(username) != null);
    }

    @Then ("user {string} is not registered")
    public void isUserNotRegistered(String username) {
        assertTrue(usercont.getDao().getUser(username) == null);
    }

    @Given ("that user {string} is registered")
    public void userIsRegistered(String username) {
        UserDao userdao = new MapUserDao();
        userdao.createUser(username, "password");
        usercont.setDao(userdao);
    }

    @When ("display name of {string} is changed to {string}")
    public void displayNameIsChanged(String username, String displayName) {
        usercont.settings(createPrincipal(username), new ConcurrentModel(), displayName, "");
    }

    @Then ("display name of {string} is {string}")
    public void displayNameIs(String username, String displayName) {
        assertEquals(displayName, usercont.getDao().getUser(username).getDisplayName());
    }

    @Given ("user has an unread book")
    public void userHasAnUnreadBook() {
        service.setDao(new ListBookmarkDao());
        service.createBook(user, "Samuli Turska", "Näin kalastat kalaa", "314666");
    }

    @When ("a book is marked read")
    public void aBookIsMarkedRead() {
        List<Bookmark> bookmarks = service.listAllByUser(user);
        Bookmark book = bookmarks.get(0);
        service.updateBook(user.getId(), book.getId(), true);
    }

    @Then ("the book should be marked read")
    public void theBookShouldBeMarkedRead() {
        assertTrue(service.listAllByUser(user).get(0).isRead());
    }

    @When ("a book is deleted")
    public void aBookIsDeleted() {
        service.deleteBookmark(service.listAllByUser(user).get(0).getId(), user);
    }

    @Then ("the list should be empty")
    public void theListShouldBeEmpty() {
        assertTrue(service.listAllByUser(user).isEmpty());
    }

    


    private Principal createPrincipal(String name) {
        return new Principal(){
            @Override
            public String getName() {
                return name;
            }
        };
    }

}
