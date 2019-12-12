Feature: As a user I can set a separate display name

    Scenario: Change display name
        Given that user "dragonslayer" is registered
        When display name of "dragonslayer" is changed to "Sonja"
        Then display name of "dragonslayer" is "Sonja"

