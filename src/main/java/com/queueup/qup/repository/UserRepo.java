package com.queueup.qup.repository;

import com.queueup.qup.controller.IndexController;
import com.queueup.qup.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User,Integer>{


    @Query(value = "select count(*) from tbl_user", nativeQuery = true)
    public Integer totalUsers();

    @Query(value = "select email from tbl_user where email = ?1", nativeQuery = true)
    public String getUserByEmail(String email);

    @Query(value = "select password from tbl_user where password = ?1", nativeQuery = true)
    public String getUserByPassword(String password);
}
