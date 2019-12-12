Feature: As user I want to mark the books I have already read

    Scenario: Mark a book read
        Given user has an unread book
        When a book is marked read
        Then the book should be marked read