package org.crishof.usersv.service;

import lombok.RequiredArgsConstructor;
import org.crishof.usersv.config.JwtService;
import org.crishof.usersv.controller.models.AuthResponse;
import org.crishof.usersv.controller.models.AuthenticationRequest;
import org.crishof.usersv.controller.models.RegisterRequest;
import org.crishof.usersv.enums.UserRole;
import org.crishof.usersv.model.User;
import org.crishof.usersv.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {

        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())).role(UserRole.USER).build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}
