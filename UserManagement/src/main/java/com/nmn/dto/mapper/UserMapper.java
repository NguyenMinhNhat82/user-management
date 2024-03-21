package com.nmn.dto.mapper;

import com.nmn.dto.UserDTO;
import com.nmn.model.Role;
import com.nmn.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toTDO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPhone(user.getPhone());
        userDTO.setDob(user.getDob());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());

        userDTO.setRole(user.getRole().toString());
        return userDTO;
    }

    public User toEntity(UserDTO userDTO){
        User user  = new User();
        user.setId(userDTO.getId());
        if(userDTO.getId() == null){
            user.setId(0);
        }
        else
            user.setId(userDTO.getId());
        user.setDob(userDTO.getDob());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setRole(Role.valueOf(userDTO.getRole()));
        return user;
    }
}
