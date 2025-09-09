package com.project.chitfundmanager.dto.authDTO;

import com.project.chitfundmanager.model.enums.Role;
import com.project.chitfundmanager.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private String userName;
    private String mobileNumber;
    private Role role;
    private UserStatus userStatus;
}
