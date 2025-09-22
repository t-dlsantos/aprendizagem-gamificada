Feature: Finalize course and release tickets

  Scenario: Student finishes a course with average grade 7
    Given a student "Thiago" with 0 tickets
    And a course "DevOps" with a module graded 7
    When the student finalizes the course
    Then the student should have 3 more tickets

  Scenario: Student finishes a course with average grade above 7
    Given a student "Thiago" with 0 tickets
    And a course "DevOps" with a module graded 8
    When the student finalizes the course
    Then the student should have 3 more tickets

  Scenario: Student finishes a course with average grade below 7
    Given a student "Thiago" with 0 tickets
    And a course "DevOps" with a module graded 5
    When the student finalizes the course
    Then the student should have the same number of tickets