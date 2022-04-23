Feature: User
  As an admin
  I want to create new user
  So that I can access the detail data user

  Scenario Outline: POST - As an admin I have be able to create new user
    Given I set an endpoint for POST new user
    When I request POST detail user are "<username>" username and "<password>" password
    Then I validate the status code is <statusCode>
    And validate the "<message>" after create user
    And get userId if "<message>" for other request
    Examples:
      | username  | password    | statusCode | message  |
      | new       | String123*  | 201        | success  |
      |           | String123*  | 400        | required |
      | string123 |             | 400        | required |
      |           |             | 400        | required |
      | same      | String123*  | 406        | existed  |

  Scenario: POST - As a user I have be able to generate token
    Given I set an endpoint for POST generate token
    When I request POST generate token
    Then I validate the status code is 200
    And validate the data detail after generate token
    And get token for other request

  Scenario: POST - As a user I have be able to authorize
    Given I set an endpoint for POST authorize
    When I request POST authorize
    Then I validate the status code is 200
    And validate the data detail after authorize

#  Scenario: GET - As a user I have be able to get detail user
#    Given I set an endpoint for GET detail user
#    When I request GET detail user
#    Then I validate the status code is 200
#    And validate the data detail