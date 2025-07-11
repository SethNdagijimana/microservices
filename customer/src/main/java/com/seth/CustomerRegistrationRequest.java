package com.seth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is required")
        String email) {

}
