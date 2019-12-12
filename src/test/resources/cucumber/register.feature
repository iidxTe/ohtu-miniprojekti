Feature: As a user I can register an account

    Scenario: registration is successful with valid username and password
        Given user is not registered
        When username "Sofia" with password "salasana" registers
        Then user "Sofia" is registered

