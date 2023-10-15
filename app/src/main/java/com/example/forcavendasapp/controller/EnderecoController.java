package com.example.forcavendasapp.controller;

import android.content.Context;

import com.example.forcavendasapp.dao.ClienteDao;
import com.example.forcavendasapp.dao.EnderecoDao;
import com.example.forcavendasapp.model.Cliente;
import com.example.forcavendasapp.model.Endereco;

import java.util.ArrayList;

public class EnderecoController {

    private Context context;

    public EnderecoController(Context context) {
        this.context = context;
    }

    public long salvarEndereco(Endereco endereco){
        return EnderecoDao.getInstancia(context).insert(endereco);
    }

    public long atualizarEndereco(Endereco endereco){
        return EnderecoDao.getInstancia(context).update(endereco);
    }

    public long apagarEndereco(Endereco endereco){
        return EnderecoDao.getInstancia(context).delete(endereco);
    }

    public ArrayList<Endereco> findAllEndereco(){
        return EnderecoDao.getInstancia(context).getAll();
    }

    public Endereco findByIdEndereco(int id){
        return EnderecoDao.getInstancia(context).getById(id);}

    public String validaEndereco(String codigo, String logradouro, String numero, String bairro, String cidade, String uf){
        String mensagem = "";
        if(codigo == null || codigo.isEmpty()){
            mensagem += "Codigo do endereço deve ser informado!\n";
        }else{
            try{
                if(Integer.parseInt(codigo) <= 0){
                    mensagem += "Codigo do endereço deve ser maior que zero!\n";
                }
            }catch(NumberFormatException ex){
                mensagem += "Codigo do endereço deve ser número válido!\n";
            }

        }
        if(logradouro == null || logradouro.isEmpty()){
            mensagem += "Logradouro do endereço deve ser informado!\n";
        }
        if(numero == null || numero.isEmpty()){
            mensagem += "Numero do endereço deve ser informado!\n";
        }
        if(bairro == null || bairro.isEmpty()){
            mensagem += "Bairro do endereço deve ser informado!\n";
        }
        if(cidade == null || cidade.isEmpty()){
            mensagem += "Cidade do endereço deve ser informado!\n";
        }
        if(uf == null || uf.isEmpty()){
            mensagem += "UF do endereço deve ser informado!\n";
        }
        return mensagem;
    }
}
