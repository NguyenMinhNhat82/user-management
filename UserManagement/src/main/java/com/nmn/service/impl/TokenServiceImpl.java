package com.nmn.service.impl;

import com.nmn.repository.TokenRepository;
import com.nmn.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenRepository tokenRepository;
    @Override
    public void saveToken(int idUser, String token) {
        tokenRepository.saveUserToken(idUser,token);
    }

    @Override
    public void deleteToken(int idUser) {
        tokenRepository.deleteToken(idUser);
    }

    @Override
    public String findTokenByUser(int user) {
        return tokenRepository.getTokenByIdUser(user);
    }
}
