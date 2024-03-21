package com.nmn.repository;

import com.nmn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserById(Integer id);

    User findFirstByEmail(String email);

}
