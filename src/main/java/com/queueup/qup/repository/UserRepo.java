package com.queueup.qup.repository;

import com.queueup.qup.controller.IndexController;
import com.queueup.qup.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer>{


    @Query(value = "select count(*) from tbl_user", nativeQuery = true)
    public Integer totalUsers();

    @Query(value = "select email from tbl_user where email = ?1", nativeQuery = true)
    public String getUserByEmail(String email);

    @Query(value = "select password from tbl_user where email = ?1", nativeQuery = true)
    public String getUserByPassword(String email);

    @Query(value = "select id from tbl_user where email = ?1", nativeQuery = true)
    public Integer getUserId(String email);

    @Query(value = "select name from tbl_user where id = ?1", nativeQuery = true)
    public String findNameById(Integer id);

    @Query(value = "select user_name from tbl_user where id = ?1", nativeQuery = true)
    public String findUsernameById(Integer id);

    @Query(value = "select role from tbl_user where email = ?1", nativeQuery = true)
    public String getRole(String email);

    @Query(value = "select role from tbl_user where id= ?1",nativeQuery = true)
    public String getRoleByID(Integer id);

    @Query(value = "select * from tbl_user where email = ?1", nativeQuery = true)
    public String findByEmail(String email);

    @Query(value = "select  * from tbl_user where id = ?1",nativeQuery = true)
    public List<User> getUserDetailsById(Integer id);

}
