package com.queueup.qup.repository;

import com.queueup.qup.controller.admin.AdminKeyController;
import com.queueup.qup.dto.KeyDto;
import com.queueup.qup.entity.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface KeyRepo extends JpaRepository<Key,Integer> {

    @Query(value = "select key from tbl_key where key = ?1", nativeQuery = true)
    public String getKeyFromLogin(String key);

    @Query(value = "select key from tbl_key", nativeQuery = true)
    public String getKey();

    @Modifying
    @Transactional
    @Query(value = "update tbl_key set key_name = ?1, key = ?2 where key_id = ?3", nativeQuery = true)
    public void updateKey(String name, String Key, Integer Key_id);


    @Query(value = "select key_name from tbl_key where key_id=?1", nativeQuery = true)
    public String getKeyNamebyId(Integer key_id);

    @Query(value = "select key from tbl_key where key_id=?1", nativeQuery = true)
    public String getKeybyId(Integer key_id);

}
