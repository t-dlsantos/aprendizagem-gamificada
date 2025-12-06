package com.grupoenzo.recommender.core.domain;

import java.util.UUID;

public class Course {
    private UUID id;
    private String title;
    private String name;
    private String description;

    public Course(UUID id, String title, String description) {
        this.id = id;
        this.title = title;
        this.name = title;
        this.description = description;
    }

    public Course(UUID id, String title) {
        this(id, title, "");
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getShortDescription() { return description; }
}
