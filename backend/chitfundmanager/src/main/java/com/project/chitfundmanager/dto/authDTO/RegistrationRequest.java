package com.project.chitfundmanager.dto.authDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotEmpty
    private String mobileNumber;

    @NotEmpty
    private String password;

    @NotEmpty
    private String role;   // MANAGER or MEMBER

    private String name;

    private String email;  // optional for manager

    private Integer age;   // for member

    private String gender;

    private String occupation;

    private String address;
}
