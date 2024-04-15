package dev.nampd.hr_management.auth;

import dev.nampd.hr_management.model.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<GenericResponse<AuthenticationResponse>> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        return ResponseEntity.ok(new GenericResponse<>(authenticationService.authenticate(authenticationRequest)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<GenericResponse<AuthenticationResponse>> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(new GenericResponse<>(authenticationService.refreshToken(request)));
    }
}
