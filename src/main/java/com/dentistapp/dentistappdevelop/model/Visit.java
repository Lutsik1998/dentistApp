package com.dentistapp.dentistappdevelop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class Visit extends BaseEntity {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm")
    @Schema(pattern = "yyyy-MM-dd-HH-mm")
    private LocalDateTime dateTimeStart;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm")
    @Schema(pattern = "yyyy-MM-dd-HH-mm")
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
}
