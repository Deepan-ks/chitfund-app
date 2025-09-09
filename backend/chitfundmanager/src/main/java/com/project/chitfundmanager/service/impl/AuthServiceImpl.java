package com.project.chitfundmanager.service.impl;

import com.project.chitfundmanager.dto.authDTO.LoginRequest;
import com.project.chitfundmanager.dto.authDTO.LoginResponse;
import com.project.chitfundmanager.dto.authDTO.RegistrationResponse;
import com.project.chitfundmanager.dto.authDTO.RegistrationRequest;
import com.project.chitfundmanager.model.AuthUser;
import com.project.chitfundmanager.model.Manager;
import com.project.chitfundmanager.model.Member;
import com.project.chitfundmanager.model.enums.Role;
import com.project.chitfundmanager.model.enums.UserStatus;
import com.project.chitfundmanager.repository.AuthUserRepository;
import com.project.chitfundmanager.repository.ManagerRepository;
import com.project.chitfundmanager.repository.MemberRepository;
import com.project.chitfundmanager.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthUserRepository authUserRepository;
    private final ManagerRepository managerRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Override
    @Transactional
    public RegistrationResponse register(RegistrationRequest request) {
        if (authUserRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new RuntimeException("mobile number already exists");
        }

        AuthUser user = AuthUser.builder()
                .mobileNumber(request.getMobileNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole().toUpperCase()))
                .status(UserStatus.ACTIVE)
                .build();

        user = authUserRepository.save(user);

        if (user.getRole() == Role.MANAGER) {
            Manager manager = Manager.builder()
                    .authUser(user)
                    .name(request.getName())
                    .email(request.getEmail())
                    .build();

            managerRepository.save(manager);

            return new RegistrationResponse(manager.getName(), user.getMobileNumber(), user.getRole(), user.getStatus());
        } else {
            Member mem = Member.builder()
                    .authUser(user)
                    .name(request.getName())
                    .age(request.getAge())
                    .gender(request.getGender())
                    .occupation(request.getOccupation())
                    .address(request.getAddress())
                    .mobileNumber(request.getMobileNumber())
                    .build();

            memberRepository.save(mem);

            return new RegistrationResponse(mem.getName(), user.getMobileNumber(), user.getRole(), user.getStatus());
        }
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        AuthUser authUser = authUserRepository.findByMobileNumber(request.getMobileNumber())
                .orElseThrow(() -> new RuntimeException("Mobile number not found"));

        if (!passwordEncoder.matches(request.getPassword(), authUser.getPassword())) {
            return new LoginResponse(null, "Invalid credentials", null);
        }

        if (authUser.getStatus() != UserStatus.ACTIVE) {
            return new LoginResponse(null, "User not active", null);
        }

        String token = jwtUtil.generateToken(authUser);

        String name = authUser.getRole() == Role.MANAGER
                ? managerRepository.findByAuthUser(authUser).map(Manager::getName).orElse("")
                : memberRepository.findByAuthUser(authUser).map(Member::getName).orElse("");

        return new LoginResponse(token, authUser.getRole().name(), name);
    }
}
