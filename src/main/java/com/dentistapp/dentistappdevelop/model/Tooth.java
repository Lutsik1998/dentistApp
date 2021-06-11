package com.dentistapp.dentistappdevelop.model;

import lombok.Data;

@Data
public class Tooth {

    private String information;
    private ToothState state;

    public Tooth() {
        this.state = ToothState.HEALTHY;
    }
}

enum ToothState{
    HEALTHY,
    IN_PROGRESS,
    REMOVED,
    NOT_HEALTHY

}
