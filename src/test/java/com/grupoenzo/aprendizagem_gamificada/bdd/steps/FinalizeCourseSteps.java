package com.grupoenzo.aprendizagem_gamificada.bdd.steps;

import com.grupoenzo.aprendizagem_gamificada.bdd.World;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinalizeCourseSteps {

    private final World world;

    public FinalizeCourseSteps(World world) {
        this.world = world;
    }

    @Given("a student {string} with {int} tickets")
    public void studentWithTickets(String name, int tickets) {
        steps_support.student(world, name, tickets);
    }

    @And("a course {string} with a module graded {int}")
    public void courseWithAModuleGraded(String courseName, int grade) {
        steps_support.courseWithGrade(world, courseName, grade);
    }

    @When("the student finalizes the course")
    public void studentFinalizesTheCourse() {
        world.ticketsBefore = world.student.getTicket().getValue();
        world.finalizeCourseUseCase.execute(world.student.getId(), world.course.getId());
    }

    @Then("the student should have {int} more tickets")
    public void studentShouldHaveMoreTickets(int extraTickets) {
        assertEquals(world.ticketsBefore + extraTickets, world.student.getTicket().getValue());
    }

    @Then("the student should have the same number of tickets")
    public void studentShouldHaveSameNumberOfTickets() {
        assertEquals(world.ticketsBefore, world.student.getTicket().getValue());
    }
}