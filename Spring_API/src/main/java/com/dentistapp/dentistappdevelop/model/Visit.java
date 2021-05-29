package com.dentistapp.dentistappdevelop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class Visit extends BaseEntity {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Schema(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTimeStart;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Schema(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTimeEnd;
    @NotNull
    private String information;
    @NotNull
    private String doctorId;
    @NotNull
    private String officeId;
    @NotNull
    private String patientId;
    private Review review;
    private boolean finished = false;
    private List<Recipe> recipes;
}
