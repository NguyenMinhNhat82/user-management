package com.nmn.controller;

import com.nmn.dto.UserDTO;
import com.nmn.model.User;
import com.nmn.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/get-all")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<User>> getListUser(@RequestParam Map<String, String> params){
        return new ResponseEntity<>(userService.getListUser(params), HttpStatus.OK);
    }


    @Operation(summary = "Add or Update User", description = "Add or Update User")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/add-or-update")
    public ResponseEntity<UserDTO> addOrUpdateUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(userService.addOrUpdateUser(userDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete user", description = "Delete user")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id){
        if(userService.deleteUser(id))
            return new ResponseEntity<>("SUCCESS" , HttpStatus.OK);
        return new ResponseEntity<>("FAIL" , HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
