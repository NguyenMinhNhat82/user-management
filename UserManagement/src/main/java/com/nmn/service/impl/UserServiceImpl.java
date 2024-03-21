package com.nmn.service.impl;

import com.nmn.dto.UserDTO;
import com.nmn.dto.mapper.UserMapper;
import com.nmn.model.User;
import com.nmn.repository.UserRepository;
import com.nmn.repository.UserRepositoryCus;
import com.nmn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositoryCus  userRepositoryCus;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getListUser(Map<String,String> params) {
        return userRepositoryCus.getListUser(params);
    }

    @Override
    public UserDTO addOrUpdateUser(UserDTO user) {
        return userMapper.toTDO(userRepository.save(userMapper.toEntity(user))) ;
    }

    @Override
    public Boolean deleteUser(Integer id) {
        try {
            userRepository.delete(userRepository.findUserById(id));
            return  true;
        }
        catch (Exception ex){
            return false;
        }
    }

    @Override
    public User getUserByID(Integer id) {
        return userRepository.findUserById(id);
    }

    @Override
    public Boolean changePassWord(Integer idUser, String newPassword) {
        try {
            User user = userRepository.findUserById(idUser);
            if(user == null)
                 throw new Exception("Not found user");
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }
}
