package com.harry.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @NotNull
    private Integer id;
    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    private String username;
    @JsonIgnore
    private String password;
    @NotEmpty
    @Email
    private String email;

    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
