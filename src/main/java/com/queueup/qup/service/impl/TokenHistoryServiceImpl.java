package com.queueup.qup.service.impl;

import com.queueup.qup.controller.LogInController;
import com.queueup.qup.dto.TokenHistoryDto;
import com.queueup.qup.entity.Token;
import com.queueup.qup.entity.TokenHistory;
import com.queueup.qup.repository.TokenHistoryRepo;
import com.queueup.qup.repository.TokenRepo;
import com.queueup.qup.repository.UserRepo;
import com.queueup.qup.service.TokenHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TokenHistoryServiceImpl implements TokenHistoryService {

    @Autowired
    LogInController logInController;

    @Autowired
    UserRepo userRepo;

    @Autowired
    TokenRepo tokenRepo;
    private final TokenHistoryRepo TokenHistoryRepo;

    LocalDate localDate = LocalDate.now();

    public TokenHistoryServiceImpl(TokenHistoryRepo TokenHistoryRepo) {
        this.TokenHistoryRepo = TokenHistoryRepo;
    }

    @Override
    public TokenHistoryDto save(TokenHistoryDto TokenHistoryDto) {
        TokenHistory entity = TokenHistory.builder()
                .token_history_id(TokenHistoryDto.getToken_history_id())
                .token_key(TokenHistoryDto.getToken_key())
                .build();
        entity.setToken_number(tokenRepo.findAll().size());
        entity.setFk_user_id(logInController.loggedInUserid);
        entity.setName(userRepo.findNameById(logInController.loggedInUserid));
        entity.setUsername(userRepo.findUsernameById(logInController.loggedInUserid));
        entity.setDate(localDate);
        entity=TokenHistoryRepo.save(entity);
        return TokenHistoryDto.builder()
                .token_history_id(entity.getToken_history_id())
                .token_key(TokenHistoryDto.getToken_key())
                .build();
    }

    @Override
    public List<TokenHistoryDto> findAll() {
        List<TokenHistory> TokenHistoryList = TokenHistoryRepo.findAll();
        return TokenHistoryList.stream().map(
                TokenHistory -> TokenHistoryDto.builder()
                        .token_history_id(TokenHistory.getToken_history_id())
                        .token_key(TokenHistory.getToken_key())
                        .fk_user_id(TokenHistory.getFk_user_id())
                        .name(TokenHistory.getName())
                        .username(TokenHistory.getUsername())
                        .token_number(TokenHistory.getToken_number())
                        .date(TokenHistory.getDate())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public TokenHistoryDto findById(Integer integer) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

}
