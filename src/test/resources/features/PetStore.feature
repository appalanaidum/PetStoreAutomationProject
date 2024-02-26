Feature: Pet Store
 
@Sanity  
  Scenario: Create a new pet
    Given I have a new pet object with name "Cat" and ID 11
    When I add the new pet
    Then the response status code should be 200
    And the response body should contain the details of the new pet
    
@Sanity  
 Scenario: Get pet by valid ID
    Given I have a valid pet ID
    When I request the pet by ID
    Then the response status code should be 200
 
@Sanity   
    Scenario: Delete an existing pet
    Given I have an existing pet with ID 11
    When I delete the pet with ID 11
    Then the response status code should be 200
    And the pet with ID 11 should not exist anymore