package com.queueup.qup.service;

import com.queueup.qup.controller.LogInController;

import java.util.List;

public interface GenericCrudService <D, ID>{
    D save(D d);

    List<D> findAll();

    D findById(ID id);

    void deleteById(ID id);

}
