package com.blog.blog.controller;

import com.blog.blog.dto.AuthenticationResponse;
import com.blog.blog.dto.LoginRequest;
import com.blog.blog.dto.RefreshTokenRequest;
import com.blog.blog.dto.RegisterRequest;
import com.blog.blog.repository.UserRepository;
import com.blog.blog.service.AuthService;
import com.blog.blog.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserRepository userRepository;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterRequest registerRequest) throws URISyntaxException
    {
//       if(userRepository.findOneByEmailIgnoreCase(registerRequest.getEmail()).isPresent()){
//           throw new EmailAlreadyUsedException();
//       }
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Success", HttpStatus.OK);
    }
    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token)
    {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully",HttpStatus.OK);

    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest)
    {
        return authService.login(loginRequest);
    }
    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        return authService.refreshToken(refreshTokenRequest);
    }
    @PostMapping("/logout")
     public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh token Deleted Successfully!!");
    }
}
