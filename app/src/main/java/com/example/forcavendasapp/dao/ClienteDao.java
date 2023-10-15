package com.example.forcavendasapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.forcavendasapp.helper.SQLiteDataHelper;
import com.example.forcavendasapp.model.Cliente;

import java.util.ArrayList;

public class ClienteDao implements GenericDao<Cliente> {

    //Abrir a conexão com a BD
    private SQLiteOpenHelper openHelper;

    //Base de Dados
    private SQLiteDatabase bd;

    //nome das colunas da tabela
    private String[] colunas = {"CODIGO", "NOME", "CPF", "DTNASCIMENTO", "CODENDERECO"};

    //nome da tabela
    private String tableName = "CLIENTE";

    private Context context;

    private static ClienteDao instancia;

    private ClienteDao(Context context) {
        this.context = context;
        //Carregando base de dados
        openHelper = new SQLiteDataHelper(this.context, "UNIPAR2", null, 1);

        //Atribuindo a base de dados a variavel e dando permissão para escrever nas tabelas
        bd = openHelper.getWritableDatabase();
    }

    public static ClienteDao getInstancia(Context context) {
        if (instancia == null)
            return instancia = new ClienteDao(context);
        else
            return instancia;
    }

    @Override
    public long insert(Cliente obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put("NOME", obj.getNome());
            valores.put("CPF", obj.getCpf());
            valores.put("DTNASCIMENTO", obj.getDtNasc());
            valores.put("CODENDERECO", obj.getCodEndereco());

            return bd.insert(tableName, null, valores);
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDAO.insert(): " + ex.getMessage());
        }
        return -1;
    }

    @Override
    public long update(Cliente obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put("NOME", obj.getNome());
            valores.put("CPF", obj.getCpf());
            valores.put("DTNASCIMENTO", obj.getDtNasc());
            valores.put("CODENDERECO", obj.getCodEndereco());

            String[] identificador = {String.valueOf(obj.getCodigo())};
            return bd.update(tableName, valores, "CODIGO = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDAO.update(): " + ex.getMessage());
        }
        return -1;
    }

    @Override
    public long delete(Cliente obj) {
        try {
            String[] identification = {String.valueOf(obj.getCodigo())};
            return bd.delete(tableName, "CODIGO = ?", identification);
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDAO.delete(): " + ex.getMessage());
        }
        return -1;
    }

    @Override
    public ArrayList<Cliente> getAll() {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            Cursor cursor = bd.query(tableName, colunas, null, null, null, null, "CODIGO asc");
            if (cursor.moveToFirst()) {
                do {
                    Cliente cliente = new Cliente();
                    cliente.setCodigo(cursor.getInt(0));
                    cliente.setNome(cursor.getString(1));
                    cliente.setCpf(cursor.getString(2));
                    cliente.setDtNasc(cursor.getString(3));
                    cliente.setCodEndereco(cursor.getInt(4));

                    lista.add(cliente);
                } while (cursor.moveToNext());
            }
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDAO.getAll(): " + ex.getMessage());
        }
        return lista;
    }

    @Override
    public Cliente getById(int id) {
        try {
            Cursor cursor = bd.query(tableName, colunas, "CODIGO = ?", new String[] { String.valueOf(id) }, null, null, "CODIGO asc");
            if (cursor.moveToFirst()) {
                Cliente cliente = new Cliente();
                cliente.setCodigo(cursor.getInt(0));
                cliente.setNome(cursor.getString(1));
                cliente.setCpf(cursor.getString(2));
                cliente.setDtNasc(cursor.getString(3));
                cliente.setCodEndereco(cursor.getInt(4));

                return cliente;
            }
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDAO.getById(): " + ex.getMessage());
        }
        return null;
    }
}
