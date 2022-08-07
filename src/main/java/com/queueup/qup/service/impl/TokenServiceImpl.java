package com.queueup.qup.service.impl;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.controller.user.UserController;
import com.queueup.qup.dto.TokenDto;
import com.queueup.qup.entity.Token;
import com.queueup.qup.repository.TokenRepo;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    LogInController logInController;

    @Autowired
    UserController userController;

    @Autowired
    UserRepo userRepo;
    private final TokenRepo tokenRepo;

    LocalDate localdate = LocalDate.now();

    public TokenServiceImpl(TokenRepo tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    @Override
    public TokenDto save(TokenDto tokenDto) {
        Token entity = Token.builder()
                .token_id(tokenDto.getToken_id())
                .token_key(tokenDto.getToken_key())
                .build();
        entity.setToken_number(tokenRepo.findAll().size()+1);
        entity.setFk_user_id(userRepo.getIdByUserName(logInController.loggedInUserDetail.get(userController.UserName)));
        entity.setName(userRepo.findNameByUserName(logInController.loggedInUserDetail.get(userController.UserName)));
        entity.setUsername(logInController.loggedInUserDetail.get(userController.UserName));
        entity.setEmail(userRepo.getEmailByUserName(logInController.loggedInUserDetail.get(userController.UserName)));
        entity.setStatus(0);
        entity.setDate(localdate);
        entity=tokenRepo.save(entity);
        return tokenDto.builder()
                .token_id(entity.getToken_id())
                .token_key(tokenDto.getToken_key())
                .build();
    }

    @Override
    public List<TokenDto> findAll() {
        List<Token> tokenList = tokenRepo.findAll();
        return tokenList.stream().map(
                token -> TokenDto.builder()
                        .token_id(token.getToken_id())
                        .token_key(token.getToken_key())
                        .fk_user_id(token.getFk_user_id())
                        .name(token.getName())
                        .username(token.getUsername())
                        .token_number(token.getToken_number())
                        .status(token.getStatus())
                        .statusChangedBy(token.getStatusChangedBy())
                        .email(token.getEmail())
                        .date(token.getDate())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public TokenDto findById(Integer integer) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

}
