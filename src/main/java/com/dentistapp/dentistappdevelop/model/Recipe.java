package com.dentistapp.dentistappdevelop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Recipe extends BaseEntity {
    @NotNull
    private String recipeNumber;
    @NotNull
    @Pattern(regexp = "^((0[1-9])|(1[0-6]))$")
    private String codeMFI;
    private String drugs;
    private String fileName;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Schema(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;
    @NotNull
    @Schema(pattern = "yyyy-MM-dd")
    private LocalDate lastDate;
    private String additionalProperties;
    @Pattern(regexp = "^(B|R)|((100|([0-9]{1,2}(,[0-9]*)?))%)$")
    private String payment;
}
