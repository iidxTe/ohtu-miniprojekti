Feature: As a user I can add new books

    Scenario: Add a book
        Given the list is empty
        When a book is added
        Then the book should be on the list

    Scenario: Add many books
        Given the list is empty
        When a book is added
        And another book is added
        Then the books should be on the list