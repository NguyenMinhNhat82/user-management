package com.nmn.controller;

import com.nmn.dto.UserDTO;
import com.nmn.model.User;
import com.nmn.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService  userService;


    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/get-user/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable("id") Integer id){
        return new ResponseEntity<>(userService.getUserByID(id), HttpStatus.OK);
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> request){
        Integer id = Integer.parseInt(request.get("id"));
        String password = request.get("password");
        if(userService.changePassWord(id,password))
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        return new ResponseEntity<>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/update-profile")
    public  ResponseEntity<UserDTO> updateProfile(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(userService.addOrUpdateUser(userDTO), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/current-user",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> currentUser(Principal user){
        User u = userService.findUserByEmail(user.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

}
