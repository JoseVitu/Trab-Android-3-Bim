package com.example.forcavendasapp.model;

public class Item {

    private int codigo;
    private String descricao;
    private double vlrUnit;
    private String unMedida;

    public Item(int codigo, String descricao, double vlrUnit, String unMedida) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.vlrUnit = vlrUnit;
        this.unMedida = unMedida;
    }

    public Item() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getVlrUnit() {
        return vlrUnit;
    }

    public void setVlrUnit(double vlrUnit) {
        this.vlrUnit = vlrUnit;
    }

    public String getUnMedida() {
        return unMedida;
    }

    public void setUnMedida(String unMedida) {
        this.unMedida = unMedida;
    }

    @Override
    public String toString() {

        if(unMedida == null || vlrUnit == 0){
            return descricao;
        } else {
            return descricao + " - " + unMedida + " - R$: " + vlrUnit;
        }

    }
}
