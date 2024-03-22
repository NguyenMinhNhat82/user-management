package com.nmn.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository {
    public static final String HASH_KEY = "JWT_TOKEN";

    @Autowired
    RedisTemplate template;
    public void saveUserToken(int idUser, String token){
        template.opsForHash().put(HASH_KEY,idUser,token);
    }

    public void deleteToken(int idUser){
        template.opsForHash().delete(HASH_KEY,idUser);
    }

    public String getTokenByIdUser(int userID){
       return template.opsForHash().get(HASH_KEY,userID).toString();
    }
}
