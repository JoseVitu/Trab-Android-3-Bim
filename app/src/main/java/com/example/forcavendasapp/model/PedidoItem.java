package com.example.forcavendasapp.model;

public class PedidoItem {

    private int codigo;

    private int codigoItem;
    private int codigoPedido;

    public PedidoItem() {
    }

    public PedidoItem(int codigo, int codigoItem, int codigoPedido) {
        this.codigo = codigo;
        this.codigoItem = codigoItem;
        this.codigoPedido = codigoPedido;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(int codigoItem) {
        this.codigoItem = codigoItem;
    }

    public int getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(int codigoPedido) {
        this.codigoPedido = codigoPedido;
    }
}
