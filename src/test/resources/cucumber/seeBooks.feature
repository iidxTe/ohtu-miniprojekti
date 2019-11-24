Feature: As a user I can see all the books I have added

    Scenario:
        Given the list is empty
        When a book is added
        Then the book should be on the list

    Scenario:
        Given the list is not empty
        When a book is added
        Then the book should be on the list