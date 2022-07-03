package com.queueup.qup.repository;


import com.queueup.qup.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDate;

public interface TokenRepo extends JpaRepository<Token, Integer> {

    @Query(value = "select token_number from tbl_token where fk_user_id = ?1 and status=0", nativeQuery = true)
    public Integer getTokenNumber(Integer fk_user_id);

    @Modifying
    @Transactional
    @Query(value = "delete from tbl_token",nativeQuery = true)
    public void deleteAllTokens();

    @Query(value = "select count(*) from tbl_token where status=0", nativeQuery = true)
    public Integer getRemainingTokenCount();

    @Query(value = "select token_number from tbl_token where status=0 fetch first 1 row only ", nativeQuery = true)
    public Integer getCurrentUserTokenNumber();

    @Modifying
    @Transactional
    @Query(value = "update tbl_token set status = 1 where token_number = ?1", nativeQuery = true)
    public void setUserStatustoComplete(Integer token_number);

    @Modifying
    @Transactional
    @Query(value = "update tbl_token set status = 2 where token_number = ?1", nativeQuery = true)
    public void setUserStatusToAbsent(Integer token_number);

    @Modifying
    @Transactional
    @Query(value = "update tbl_token set status = 3 where token_number = ?1", nativeQuery = true)
    public void setUserStatusToCancelled(Integer token_number);

    @Modifying
    @Transactional
    @Query(value = "update tbl_token set status_changed_by = 'Admin' where token_number = ?1", nativeQuery = true)
    public void setStatusChangedByAdmin(Integer token_number);

    @Modifying
    @Transactional
    @Query(value = "update tbl_token set status_changed_by = 'User' where token_number = ?1", nativeQuery = true)
    public void setStatusChangedByUser(Integer token_number);

    @Query(value = "select email from tbl_token where token_number = ?1", nativeQuery = true)
    public String getEmailFromTokenNumber(Integer token_Number);

    @Modifying
    @Transactional
    @Query(value = "delete from tbl_token where date != ?1", nativeQuery = true)
    public void deleteByDate(LocalDate date);
}