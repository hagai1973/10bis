Feature: Test login using Facebook
Scenario: Test login using Facebook
    Given open Google Chrome and go to 10bis
    When I click on login and login with Facebook account
    Then user should be able to login successfully
