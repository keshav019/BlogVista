package com.blog.app.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.app.dto.LoginDto;
import com.blog.app.dto.LoginResponseDto;
import com.blog.app.dto.MailDto;
import com.blog.app.dto.RegisterDto;
import com.blog.app.dto.RegisterResponseDto;
import com.blog.app.exception.TokenExpiredException;
import com.blog.app.exception.UserAlreadyExistException;
import com.blog.app.exception.UserNotExistException;
import com.blog.app.model.User;
import com.blog.app.model.UserDetailsImpl;
import com.blog.app.repository.UserRepository;
import com.blog.app.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;
    
    @Mock
    private EmailService emailService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    private User createTestUser(RegisterDto registerDto, boolean verified) {
        User user = createTestUser(registerDto);
        user.setEnable(verified);
        return user;
    }
    
    private User createTestUser(RegisterDto registerDto) {
        User user = new User(registerDto);
        user.setEnable(true);
        user.setCreatedTimeStamp(LocalDateTime.now());
        user.setUpdateTimeStamp(LocalDateTime.now());
        user.setToken("123456");
        return user;
    }

    @Test
    public void testRegisterNewUser() throws UserAlreadyExistException {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("newuser@example.com");
        registerDto.setPassword("keshav@123");
        when(userRepository.findById("newuser@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any(String.class))).thenReturn("ejwjjwqkdnjhbjhwdjhwwj");
        when(userRepository.save(any(User.class))).thenReturn(createTestUser(registerDto));
        doNothing().when(emailService).sendEmail(any(MailDto.class));
        RegisterResponseDto responseDto = userService.register(registerDto);

        assertEquals("newuser@example.com", responseDto.getEmail());
    }

    @Test
    public void testRegisterExistingUnverifiedUser() throws UserAlreadyExistException {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("existinguser@example.com");
        registerDto.setPassword("keshav@123");
        when(userRepository.findById("existinguser@example.com")).thenReturn(Optional.of(createTestUser(registerDto, false)));
        when(passwordEncoder.encode(any(String.class))).thenReturn("ejwjjwqkdnjhbjhwdjhwwj");
        when(userRepository.save(any(User.class))).thenReturn(createTestUser(registerDto));
        doNothing().when(emailService).sendEmail(any(MailDto.class));
        RegisterResponseDto responseDto = userService.register(registerDto);

        assertEquals("existinguser@example.com", responseDto.getEmail());
    }

    @Test
    public void testRegisterExistingVerifiedUser() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("existinguser@example.com");
        when(userRepository.findById("existinguser@example.com")).thenReturn(Optional.of(createTestUser(registerDto, true)));
         
        assertThrows(UserAlreadyExistException.class, () -> userService.register(registerDto));
    }

    @Test
    public void testLogin() throws UserNotExistException {
    	LoginDto loginDto = new LoginDto("test@example.com", "password");
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername("test@example.com");
        userDetails.setPassword("password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("mockedJWTToken");
        LoginResponseDto response = userService.login(loginDto);
        assertEquals("test@example.com", response.getEmail());
        assertEquals("mockedJWTToken", response.getToken());
    }
    
    @Test
    void testLoginUserNotExist() throws Exception {
        LoginDto loginDto = new LoginDto("nonexistent@example.com", "password");
        when(authenticationManager.authenticate(any())).thenThrow(new UsernameNotFoundException("User not found"));
        assertThrows(UsernameNotFoundException.class, () -> userService.login(loginDto));
    }

    @Test
    public void testVerifyEmail() throws UserNotExistException, TokenExpiredException {
        String email = "existinguser@example.com";
        String token = "123456";
        User user = createTestUser(new RegisterDto(), false);
        user.setToken(token);
        when(userRepository.findById(email)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        String result = userService.verifyEmail(email, token);

        assertEquals("Email verified !", result);
        assertEquals(true, user.isEnable());
        assertEquals(null, user.getToken());
    }

    @Test
    public void testVerifyEmailWithExpiredToken() throws UserNotExistException {
        String email = "existinguser@example.com";
        String token = "123456";

        User user = new User();
        user.setEnable(false);
        user.setEmail(email);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyMinutesBefore = now.minus(20, ChronoUnit.MINUTES);
        user.setCreatedTimeStamp(twentyMinutesBefore);
        user.setUpdateTimeStamp(twentyMinutesBefore);
        user.setToken(token);
        when(userRepository.findById(email)).thenReturn(Optional.of(user));
        assertThrows(TokenExpiredException.class, () -> userService.verifyEmail(email, token));
    }

    @Test
    public void testVerifyEmailWithInvalidToken() throws UserNotExistException {
        String email = "existinguser@example.com";
        String token = "invalidToken";

        User user = createTestUser(new RegisterDto(), false);
        user.setToken("validToken");
        when(userRepository.findById(email)).thenReturn(Optional.of(user));

        assertThrows(TokenExpiredException.class, () -> userService.verifyEmail(email, token));
    }
    
    @Test
    public void testUpdateUser() throws UserNotExistException {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("existinguser@example.com");
        registerDto.setFirstname("newuser");

        User existingUser = createTestUser(registerDto);
        when(userRepository.findById("existinguser@example.com")).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        RegisterResponseDto responseDto = userService.update(registerDto);
        assertEquals("existinguser@example.com", responseDto.getEmail());
        assertEquals("newuser", responseDto.getFirstname());
    }

    @Test
    public void testUpdateNonExistingUser() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("nonexistinguser@example.com");
        when(userRepository.findById("nonexistinguser@example.com")).thenReturn(Optional.empty());
        assertThrows(UserNotExistException.class, () -> userService.update(registerDto));
    }

    @Test
    public void testForgotPassword() throws UserNotExistException {
        String email = "existinguser@example.com";

        User existingUser = createTestUser(new RegisterDto(), true);
        when(userRepository.findById(email)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        doNothing().when(emailService).sendEmail(any(MailDto.class));

        String result = userService.forgotPassword(email);

        assertEquals("Otp Email Sent !", result);
    }

    @Test
    public void testForgotPasswordForNonExistingUser() {
        String email = "nonexistinguser@example.com";

        when(userRepository.findById(email)).thenReturn(Optional.empty());

        assertThrows(UserNotExistException.class, () -> userService.forgotPassword(email));
    }

    @Test
    public void testResetPassword() throws UserNotExistException, TokenExpiredException {
        String email = "existinguser@example.com";
        String newPassword = "newPassword";
        String token = "123456";

        User existingUser = createTestUser(new RegisterDto(), true);
        existingUser.setToken(token);
        when(userRepository.findById(email)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        String result = userService.resetPassword(newPassword, email, token);

        assertEquals("Password Updated", result);
    }

    @Test
    public void testResetPasswordForNonExistingUser() {
        String email = "nonexistinguser@example.com";
        String newPassword = "newPassword";
        String token = "123456";

        when(userRepository.findById(email)).thenReturn(Optional.empty());

        assertThrows(UserNotExistException.class, () -> userService.resetPassword(newPassword, email, token));
    }

    @Test
    public void testResetPasswordWithExpiredToken() {
        String email = "existinguser@example.com";
        String newPassword = "newPassword";
        String token = "expiredToken";

        User existingUser = new User();
        existingUser.setEnable(true);
        existingUser.setEmail(email);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyMinutesBefore = now.minus(20, ChronoUnit.MINUTES);
        existingUser.setCreatedTimeStamp(twentyMinutesBefore);
        existingUser.setUpdateTimeStamp(twentyMinutesBefore);
        existingUser.setToken(token);
        when(userRepository.findById(email)).thenReturn(Optional.of(existingUser));

        assertThrows(TokenExpiredException.class, () -> userService.resetPassword(newPassword, email, token));
    }

    @Test
    public void testResetPasswordWithInvalidToken() {
        String email = "existinguser@example.com";
        String newPassword = "123456";
        String token = "123456";

        User existingUser = createTestUser(new RegisterDto(), true);
        existingUser.setToken("234567");
        when(userRepository.findById(email)).thenReturn(Optional.of(existingUser));

        assertThrows(TokenExpiredException.class, () -> userService.resetPassword(newPassword, email, token));
    }

}
