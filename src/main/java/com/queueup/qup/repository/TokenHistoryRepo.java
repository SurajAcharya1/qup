package com.queueup.qup.repository;

import com.queueup.qup.entity.TokenHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TokenHistoryRepo extends JpaRepository<TokenHistory,Integer> {

    @Query(value = "select * from tbl_token_history where fk_user_id =?1", nativeQuery = true)
    public List<TokenHistory> getUserHistory(Integer fk_user_id);
}