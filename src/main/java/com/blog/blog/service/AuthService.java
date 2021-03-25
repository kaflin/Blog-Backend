package com.blog.blog.service;


import com.blog.blog.Model.NotificationEmail;
import com.blog.blog.Model.User;
import com.blog.blog.Model.VerificationToken;
import com.blog.blog.dto.AuthenticationResponse;
import com.blog.blog.dto.LoginRequest;
import com.blog.blog.dto.RefreshTokenRequest;
import com.blog.blog.dto.RegisterRequest;
import com.blog.blog.exceptions.SpringRedditException;
import com.blog.blog.repository.UserRepository;
import com.blog.blog.repository.VerificationTokenRepository;
import com.blog.blog.security.JwtProvider;
import lombok.AllArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.SecondaryTable;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;



    public void signup(RegisterRequest registerRequest) {
        User user=new User();
        user.setUsername((registerRequest.getUsername()));
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for Signing up to Blog,"+
                "please click on the below url to activate your account:"+
                "http://localhost:8081/api/auth/accountVerification/"+token));

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken=verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(()->new SpringRedditException("Invalid Token")));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username=verificationToken.getUser().getUsername();
        User user=userRepository.findByUsername(username).orElseThrow(()->new SpringRedditException("User not found with username+ "+username));
        user.setEnabled(true);
        userRepository.save(user);
    }



    public AuthenticationResponse login(LoginRequest loginRequest) {
        //authenticationManager will take Username and password for generating token
        Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
               loginRequest.getPassword()));
        //Before Generating Token We have to store authenticate object to SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        //Now these our Authentication Manager Verifies our credentials at Background,if they are matching they return object called authenticate
        String token=jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();

    }

}
