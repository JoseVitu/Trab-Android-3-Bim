package com.example.forcavendasapp.controller;

import android.content.Context;

import com.example.forcavendasapp.dao.ItemDao;
import com.example.forcavendasapp.model.Item;

import java.util.ArrayList;

public class ItemController {

    private Context context;

    public ItemController(Context context) {
        this.context = context;
    }

    public long salvarItem(Item item){
        return ItemDao.getInstancia(context).insert(item);
    }

    public long atualizarItem(Item item){
        return ItemDao.getInstancia(context).update(item);
    }

    public long apagarItem(Item item){
        return ItemDao.getInstancia(context).delete(item);
    }

    public ArrayList<Item> findAllItem(){
        return ItemDao.getInstancia(context).getAll();
    }

    public Item findByIdItem(int id){
        return ItemDao.getInstancia(context).getById(id);}

    public String validaItem(String descricao, double vlrUnitario, String unMedida){
        String mensagem = "";

        if(descricao == null || descricao.isEmpty()){
            mensagem += "Descrição do item deve ser informado!\n";
        }
        if(String.valueOf(vlrUnitario) == null || String.valueOf(vlrUnitario).isEmpty()){
            mensagem += "Valor Unitário do item deve ser informado!\n";
        }
        if(unMedida == null || unMedida.isEmpty()){
            mensagem += "Unidade de Medida do item deve ser informada!\n";
        }
        return mensagem;
    }

}
