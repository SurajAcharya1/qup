package com.queueup.qup.repository;


import com.queueup.qup.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TokenRepo extends JpaRepository<Token, Integer> {

    @Query(value = "select token_number from tbl_token where fk_user_id = ?1", nativeQuery = true)
    public Integer getTokenNumber(Integer fk_user_id);

    @Modifying
    @Transactional
    @Query(value = "delete from tbl_token",nativeQuery = true)
    public void deleteAllTokens();

}