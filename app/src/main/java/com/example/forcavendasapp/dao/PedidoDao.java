package com.example.forcavendasapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.forcavendasapp.helper.SQLiteDataHelper;
import com.example.forcavendasapp.model.Pedido;

import java.util.ArrayList;

public class PedidoDao implements GenericDao<Pedido> {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase bd;
    private String[] colunas = {"CODIGO", "CODPESSOA", "CODENDERECO", "VLRTOTAL"};
    private String tableName = "PEDIDO";
    private Context context;
    private static PedidoDao instancia;

    private PedidoDao(Context context) {
        this.context = context;
        openHelper = new SQLiteDataHelper(this.context, "UNIPAR2", null, 1);
        bd = openHelper.getWritableDatabase();
    }

    public static PedidoDao getInstancia(Context context) {
        if (instancia == null)
            return instancia = new PedidoDao(context);
        else
            return instancia;
    }

    @Override
    public long insert(Pedido obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put("CODPESSOA", obj.getCodPessoa());
            valores.put("CODENDERECO", obj.getCodEndereco());
            valores.put("VLRTOTAL", obj.getVlrTotal());

            return bd.insert(tableName, null, valores);
        } catch (SQLException ex) {
            Log.e("ERRO", "PedidoDao.insert(): " + ex.getMessage());
        }
        return -1;
    }

    @Override
    public long update(Pedido obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put("CODIGO", obj.getCodigo());
            valores.put("CODPESSOA", obj.getCodPessoa());
            valores.put("CODENDERECO", obj.getCodEndereco());
            valores.put("VLRTOTAL", obj.getVlrTotal());

            String[] identificador = {String.valueOf(obj.getCodigo())};
            return bd.update(tableName, valores, "CODIGO = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO", "PedidoDao.update(): " + ex.getMessage());
        }
        return -1;
    }

    @Override
    public long delete(Pedido obj) {
        try {
            String[] identification = {String.valueOf(obj.getCodigo())};
            return bd.delete(tableName, "CODIGO = ?", identification);
        } catch (SQLException ex) {
            Log.e("ERRO", "PedidoDao.delete(): " + ex.getMessage());
        }
        return -1;
    }

    @Override
    public ArrayList<Pedido> getAll() {
        ArrayList<Pedido> lista = new ArrayList<>();
        try {
            Cursor cursor = bd.query(tableName, colunas, null, null, null, null, "CODIGO asc");
            if (cursor.moveToFirst()) {
                do {
                    Pedido pedido = new Pedido();
                    pedido.setCodigo(cursor.getInt(0));
                    pedido.setCodPessoa(cursor.getInt(1));
                    pedido.setCodEndereco(cursor.getInt(2));
                    pedido.setVlrTotal(cursor.getDouble(3));

                    lista.add(pedido);
                } while (cursor.moveToNext());
            }
        } catch (SQLException ex) {
            Log.e("ERRO", "PedidoDao.getAll(): " + ex.getMessage());
        }
        return lista;
    }

    @Override
    public Pedido getById(int id) {
        try {
            Cursor cursor = bd.query(tableName, colunas, "CODIGO = ?", new String[] { String.valueOf(id) }, null, null, "CODIGO asc");
            if (cursor.moveToFirst()) {
                Pedido pedido = new Pedido();
                pedido.setCodigo(cursor.getInt(0));
                pedido.setCodPessoa(cursor.getInt(1));
                pedido.setCodEndereco(cursor.getInt(2));
                pedido.setVlrTotal(cursor.getDouble(3));

                return pedido;
            }
        } catch (SQLException ex) {
            Log.e("ERRO", "PedidoDao.getById(): " + ex.getMessage());
        }
        return null;
    }
}
