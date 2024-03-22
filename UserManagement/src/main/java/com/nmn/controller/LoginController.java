package com.nmn.controller;


import com.nmn.dto.AuthenticationDTO;
import com.nmn.dto.response.AuthenticationResponse;
import com.nmn.model.User;
import com.nmn.service.TokenService;
import com.nmn.service.UserService;
import com.nmn.service.jwt.UserDetailsServiceImpl;
import com.nmn.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

@RestController
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/test")
    String test(){
        return "Test";
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDTO.getEmail());
        User user = userService.findUserByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        if(!jwt.isEmpty()  || user != null){
            tokenService.saveToken(user.getId(),jwt);
        }
        return new AuthenticationResponse(jwt);

    }
    @GetMapping("/refresh-token")
    public AuthenticationResponse reFreshToken(Principal user){
        User userAuthentication = userService.findUserByEmail(user.getName());
        if(user!= null){
            final String jwt = jwtUtil.generateToken(userAuthentication.getEmail());
            tokenService.saveToken(userAuthentication.getId(),jwt);
            return new AuthenticationResponse(jwt);
        }
        return new AuthenticationResponse("");
    }

    @GetMapping("/logout-token")
    public AuthenticationResponse logout(Principal user){
        User userAuthentication = userService.findUserByEmail(user.getName());
        if(user!= null){
            tokenService.deleteToken(userAuthentication.getId());
            return new AuthenticationResponse("Logged out");
        }
        return new AuthenticationResponse("Log out fail");
    }
}
