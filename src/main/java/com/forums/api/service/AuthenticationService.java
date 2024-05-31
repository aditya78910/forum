package com.forums.api.service;

import com.forums.api.dto.response.authentication.UserDTO;
import com.forums.api.entity.User;
import com.forums.api.exception.AuthenticationFailedException;
import com.forums.api.exception.ErrorResponse;
import com.forums.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class AuthenticationService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Value("${cookie.secret:secret-key}")
    private String secretKey;

    public UserDTO authenticateUser(String username, String password){
        return userRepository.findById(username)
                .filter(user -> doesPasswordMatch(password, user))
                .map(user -> UserDTO.builder()
                                .username(user.getUsername())
                                .email(user.getEmailId())
                                .build())
                .orElseThrow(this::generateAuthenticationFailureException);
    }

    private AuthenticationFailedException generateAuthenticationFailureException(){
        ErrorResponse<String> errorResponse
                = ErrorResponse.<String>builder()
                     .message("Authentication Falied")
                     .build();
        return new AuthenticationFailedException(errorResponse);
    }

    private boolean doesPasswordMatch(String password, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public String createToken(UserDTO user) {
        return user.getUsername() + "&" + user.getEmail() + "&" + calculateHmac(user);
    }

    public UserDTO findByToken(String token) {
        String[] parts = token.split("&");

        String username = parts[0];
        String email = parts[1];
        String hmac = parts[2];

        UserDTO userDto = UserDTO.builder()
                .username(username)
                .email(email)
                .build();

        System.out.println(userDto);
        System.out.println(email);
        System.out.println(hmac);

        if (!hmac.equals(calculateHmac(userDto))) {
            throw new RuntimeException("Invalid Cookie value");
        }

        return userDto;
    }

    private String calculateHmac(UserDTO user) {
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = (user.getUsername() + "&" + user.getEmail()).getBytes(StandardCharsets.UTF_8);

        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA512");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(valueBytes);
            return Base64.getEncoder().encodeToString(hmacBytes);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
