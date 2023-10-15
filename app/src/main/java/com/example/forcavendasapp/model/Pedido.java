package com.example.forcavendasapp.model;

import java.util.List;

public class Pedido {

    private int codigo;
    private int codPessoa;
    private List<Item> itens;
    private double vlrTotal;

    private int codEndereco;

    public Pedido() {
    }

    public Pedido(int codigo, int codPessoa, List<Item> itens, double vlrTotal, int codEndereco) {
        this.codigo = codigo;
        this.codPessoa = codPessoa;
        this.itens = itens;
        this.vlrTotal = vlrTotal;
        this.codEndereco = codEndereco;
    }

    public int getCodEndereco() {
        return codEndereco;
    }

    public void setCodEndereco(int codEndereco) {
        this.codEndereco = codEndereco;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodPessoa() {
        return codPessoa;
    }

    public void setCodPessoa(int codPessoa) {
        this.codPessoa = codPessoa;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public double getVlrTotal() {
        return vlrTotal;
    }

    public void setVlrTotal(double vlrTotal) {
        this.vlrTotal = vlrTotal;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "codigo=" + codigo +
                ", codPessoa=" + codPessoa +
                ", itens=" + itens +
                ", vlrTotal=" + vlrTotal +
                ", codEndereco=" + codEndereco +
                '}';
    }
}
