package org.crishof.usersv.service;

import org.crishof.usersv.controller.models.AuthResponse;
import org.crishof.usersv.controller.models.AuthenticationRequest;
import org.crishof.usersv.controller.models.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse authenticate(AuthenticationRequest request);


}
