package com.example.forcavendasapp.controller;

import android.content.Context;

import com.example.forcavendasapp.dao.ItemDao;
import com.example.forcavendasapp.dao.PedidoDao;
import com.example.forcavendasapp.dao.PedidoItemDao;
import com.example.forcavendasapp.model.Item;
import com.example.forcavendasapp.model.Pedido;
import com.example.forcavendasapp.model.PedidoItem;

import java.util.ArrayList;
import java.util.List;

public class PedidoController {

    private Context context;

    public PedidoController(Context context) {
        this.context = context;
    }

    public long salvarPedido(Pedido pedido) {

        if (pedido.getItens().isEmpty()) {

        } else {
            long codPedido = PedidoDao.getInstancia(context).insert(pedido);

            for (int i = 0; i < pedido.getItens().size(); i++) {
                PedidoItem pedidoItem = new PedidoItem();
                pedidoItem.setCodigoPedido((int) codPedido);
                pedidoItem.setCodigoItem(pedido.getItens().get(i).getCodigo());
                PedidoItemDao.getInstancia(context).insert(pedidoItem);
            }

            return codPedido;

        }
        return -1;

    }

    public long atualizarPedido(Pedido pedido) {
        return PedidoDao.getInstancia(context).update(pedido);
    }

    public long apagarPedido(Pedido pedido) {
        return PedidoDao.getInstancia(context).delete(pedido);
    }

    public ArrayList<Pedido> findAllPedido() {
        return PedidoDao.getInstancia(context).getAll();
    }

    public Pedido findByIdPedido(int id) throws Exception {

        if (id <= 0) {
            throw new Exception("Informe um valor maior que zero.");
        } else {
            Pedido pedido = PedidoDao.getInstancia(context).getById(id);

            System.out.println(pedido);

            if (pedido == null) {
                throw new Exception("Pedido n° " + id + "não encontrado.");
            } else {
                return pedido;
            }
        }
    }

    public String validaPedido(int codCliente, int codEndereco, double valor) {
        String mensagem = "";

        if (codCliente == 0) {
            mensagem += "Cliente deve ser informado!\n";
        }
        if (String.valueOf(valor) == null || String.valueOf(valor).isEmpty()) {
            mensagem += "Valor deve ser informado!\n";
        }
        if (codEndereco == 0) {
            mensagem += "Endereco deve ser informado!\n";
        }
        return mensagem;
    }

}
