package izhar.tutorlah.server.controllers;

import izhar.tutorlah.server.auth.AuthenticationRequest;
import izhar.tutorlah.server.auth.AuthenticationResponse;
import izhar.tutorlah.server.auth.RegisterRequest;
import izhar.tutorlah.server.exceptions.ErrorResponse;
import izhar.tutorlah.server.exceptions.UserAlreadyExistsException;
import izhar.tutorlah.server.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        try{
            return ResponseEntity.ok(authenticationService.register(request));
        } catch (UserAlreadyExistsException e){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(),"User already exists");
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthenticationResponse("User already exists"));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
