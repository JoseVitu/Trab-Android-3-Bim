package com.example.forcavendasapp.dao;

import com.example.forcavendasapp.model.Cliente;

import java.util.ArrayList;

public interface GenericDao <Object> {

    long insert(Object obj);
    long update(Object obj);
    long delete(Object obj);
    ArrayList<Object> getAll();
    Object getById(int id);

}
