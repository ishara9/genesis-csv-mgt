Scenario: Testing valid GET endpoint
Given url 'genesis/api/v1/files/records'
When method GET
Then status 200