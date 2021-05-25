package com.dentistapp.dentistappdevelop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Recipe extends BaseEntity {
    private String fileName;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Schema(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;
}
