package com.recipepile.validationConstraints;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public interface HumanUserValidation {

    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Please supply a valid email")
    String getEmail();
}
