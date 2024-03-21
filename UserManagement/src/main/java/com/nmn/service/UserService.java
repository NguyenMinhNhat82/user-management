package com.nmn.service;


import com.nmn.dto.UserDTO;
import com.nmn.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    public List<User> getListUser(Map<String, String> params);

    public UserDTO addOrUpdateUser(UserDTO user);
    public Boolean deleteUser(Integer id);

    public User getUserByID(Integer id);

    public Boolean changePassWord(Integer idUser, String newPassword);

    public User findUserByEmail(String email);
}
