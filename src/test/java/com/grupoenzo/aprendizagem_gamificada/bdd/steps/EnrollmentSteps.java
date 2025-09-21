package com.grupoenzo.aprendizagem_gamificada.bdd.steps;

import com.grupoenzo.aprendizagem_gamificada.bdd.World;
import com.grupoenzo.aprendizagem_gamificada.modules.course.entities.CourseEntity;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

@Component
public class EnrollmentSteps {

    @Autowired
    private World world;

    // GIVENs
    @Given("a BASIC student named {string} and complete a course")
    public void aBasicStudent(String name) {
        steps_support.basicStudent(world, name);
    }

    // WHENs
    @When("the student's avarage grade was greater than 7")
    public void avarageGreaterThanSeven(String courseId) {
        try {

        }
        catch (Exception e) { world.error = e; }
    }

    @When("the student's avarage grade was equals 7")
    public void avarageEqualsSeven(String courseId) {
        try {

        }
        catch (Exception e) { world.error = e; }
    }

    @When("the student's avarage grade was less than 7")
    public void avarageLessThanSeven(String courseId) {
        try {

        }
        catch (Exception e) { world.error = e; }
    }

    // THENs
    @Then("the student should receive a new course")
    public void shouldReceiveNewCourse(String courseId) {
//        assertTrue(world.student.receiveNewCourse(courseId));
    }

    @Then("the student should not receive a new course")
    public void shouldNotReceiveNewCourse(String courseId) {
//        assertTrue(world.student.isEnrolledIn(courseId));
    }
}
