package com.dentistapp.dentistappdevelop.model;

import org.springframework.data.annotation.Id;

public class BaseEntity {
    @Id
    public String id;

    public BaseEntity(String id) {
        this.id = id;
    }

    public BaseEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
