Feature: Search by query with limit


  Background:
    Given I have endpoint

  Scenario: Search a query with a book name and a limit
    Given I have valid test data with
    When I send GET request
    Then I should have the relevant results

    Scenario: Search without a query
      Given I have invalid test data without a query
      When I send GET request
      Then I should have an error message

  Scenario Outline: Search invalid query
    Given I have invalid test data an invalid query <invalidQuery>
    When I send GET request
    Then I should have an error message

    Examples:
    | invalidQuery |
    | " "          |
    | ""            |
    | ":,."         |


  Scenario Outline: Search valid query with invalid limitation
    Given I have invalid test data an invalid query "<invalidLimit>"
    When I send GET request
    Then I should have an error message

    Examples:
      | invalidLimit |
      | -1           |
    |   1.5       |


  Scenario Outline: Search valid query with invalid limitation
    Given I have invalid test data an invalid query "<invalidLimit>"
    When I send GET request
    Then I should have an error message

    Examples:
      | invalidLimit |
      |               |
      | "abc"           |
      | "1"        |
      |   ""      |