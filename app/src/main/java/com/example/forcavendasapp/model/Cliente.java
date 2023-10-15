package com.example.forcavendasapp.model;

import java.util.Date;

public class Cliente {

    private int codigo;
    private String nome;
    private String cpf;
    private String dtNasc;
    private int codEndereco;

    public Cliente(String nome, String cpf, String dtNasc, int codEndereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.dtNasc = dtNasc;
        this.codEndereco = codEndereco;
    }

    public Cliente(int codigo, String nome, String cpf, String dtNasc, int codEndereco) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.dtNasc = dtNasc;
        this.codEndereco = codEndereco;
    }

    public Cliente() {
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(String dtNasc) {
        this.dtNasc = dtNasc;
    }

    public long getCodEndereco() {
        return codEndereco;
    }

    public void setCodEndereco(int codEndereco) {
        this.codEndereco = codEndereco;
    }

    @Override
    public String toString() {
        if (cpf == null) {
            return nome;
        } else {
            return nome + " - CPF: " + cpf;
        }

    }
}
