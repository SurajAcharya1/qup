package com.queueup.qup.repository;

import com.queueup.qup.PasswordEncryption;
import com.queueup.qup.controller.IndexController;
import com.queueup.qup.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
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

    @Query(value="select user_name from tbl_user where email = ?1",nativeQuery = true)
    public String getUserName(String email);

    @Query(value = "select name from tbl_user where email = ?1", nativeQuery = true)
    public String findNameByEmail(String email);

    @Query(value = "select name from tbl_user where id = ?1", nativeQuery = true)
    public String findNameById(Integer fk_user_id);

    @Query(value = "select user_name from tbl_user where id = ?1", nativeQuery = true)
    public String findUsernameById(Integer id);

    @Query(value = "select role from tbl_user where email = ?1", nativeQuery = true)
    public String getRole(String email);

    @Query(value = "select role from tbl_user where id= ?1",nativeQuery = true)
    public String getRoleByID(Integer id);

    @Query(value = "select role from tbl_user where user_name= ?1",nativeQuery = true)
    public String getRoleByUserName(String user_name);

    @Query(value = "select email from tbl_user where id= ?1",nativeQuery = true)
    public String getEmailByID(Integer id);

    @Query(value = "select * from tbl_user where email = ?1", nativeQuery = true)
    public String findByEmail(String email);

    @Query(value = "select  * from tbl_user where id = ?1",nativeQuery = true)
    public List<User> getUserDetailsById(Integer id);

    @Query(value = "select  id from tbl_user where user_name = ?1",nativeQuery = true)
    public Integer getIdByUserName(String userName);

    @Query(value = "select name from tbl_user where user_name = ?1", nativeQuery = true)
    public String findNameByUserName(String userName);

    @Query(value = "select email from tbl_user where user_name= ?1",nativeQuery = true)
    public String getEmailByUserName(String userName);

    @Query(value = "select  * from tbl_user where user_name = ?1",nativeQuery = true)
    public List<User> getUserDetailsByUserName(String userName);

    @Modifying
    @Transactional
    @Query(value = "update tbl_user set role = ?1 where id = ?2", nativeQuery = true)
    public void updateUserRole(String role, Integer id);

    @Modifying
    @Transactional
    @Query(value = "insert into tbl_user(email, gender, name, password, phone_number, role, user_name) values ('admin@admin.com','Admin','Admin',?1,1111111111,'ADMIN','Admin');", nativeQuery = true)
    public void createAdminIfNull(String password);

    @Query(value = "select count(role)from tbl_user where role = 'ADMIN'", nativeQuery = true)
    public Integer countAdmin();

    @Query(value = "select user_name from tbl_user where user_name = ?1", nativeQuery = true)
    public String findUserNameByUserName(String userName);

    @Query(value = "select email from tbl_user where email = ?1", nativeQuery = true)
    public String findEmailByEmail(String email);

    @Query(value = "select phone_number from tbl_user where phone_number = ?1", nativeQuery = true)
    public String findPhone_numberByphone_number(String phone_number);

    @Modifying
    @Transactional
    @Query(value = "create view incompleteStatus as select token_number, email from tbl_token", nativeQuery = true)
    public void createTokenViewAtFirst();
}
