package com.queueup.qup.repository;

import com.queueup.qup.entity.LoginDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface LoginDetailRepo extends JpaRepository<LoginDetail, Integer> {

    @Modifying
    @Transactional
    @Query(value = "insert into tbl_login_detail (user_id,email,name)values (?1, ?2, ?3)", nativeQuery = true)
    public void setLoginDetails(Integer id,String email,String name);
}
