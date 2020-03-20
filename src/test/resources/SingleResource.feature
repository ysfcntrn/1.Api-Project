Feature: Validation of single user
  Scenario: Checking user information
    Given User navigates the "https""reqres.in""api/unknown/2"
    When  The user sends the get request
    Then The user must see 200 status code
    Then match response "name=fuchsia rose"
    Then match response "year=2001"
   Then match response "color=#C74375"






