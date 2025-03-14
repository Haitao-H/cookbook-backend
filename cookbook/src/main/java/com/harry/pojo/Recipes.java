package com.harry.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipes implements Serializable {
    private Integer id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String ingredients;
    @NotEmpty
    private String instructions;
    @NotEmpty
    @URL
    private String coverImage;
    private int userId;
    @NotNull
    private int categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
