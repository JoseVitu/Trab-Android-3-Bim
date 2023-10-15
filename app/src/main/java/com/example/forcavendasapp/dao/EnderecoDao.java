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
import com.example.forcavendasapp.model.Endereco;

import java.util.ArrayList;

public class EnderecoDao implements GenericDao<Endereco> {

    //Abrir a conexão com a BD
    private SQLiteOpenHelper openHelper;

    //Base de Dados
    private SQLiteDatabase bd;

    //nome das colunas da tabela
    private String[] colunas = {"CODIGO", "LOGRADOURO", "NUMERO", "BAIRRO", "CIDADE", "UF"};

    //nome da tabela
    private String tableName = "ENDERECO";

    private Context context;

    private static EnderecoDao instancia;

    private EnderecoDao(Context context) {
        this.context = context;
        //Carregando base de dados
        openHelper = new SQLiteDataHelper(this.context, "UNIPAR2", null, 1);

        //Atribuindo a base de dados a variavel e dando permissão para escrever nas tabelas
        bd = openHelper.getWritableDatabase();
    }

    public static EnderecoDao getInstancia(Context context) {
        if (instancia == null)
            return instancia = new EnderecoDao(context);
        else
            return instancia;
    }

    @Override
    public long insert(Endereco obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put("LOGRADOURO", obj.getLogradouro());
            valores.put("NUMERO", obj.getNumero());
            valores.put("BAIRRO", obj.getBairro());
            valores.put("CIDADE", obj.getCidade());
            valores.put("UF", obj.getUf());

            return bd.insert(tableName, null, valores);
        } catch (SQLException ex) {
            Log.e("ERRO", "EnderecoDao.insert(): " + ex.getMessage());
        }
        return -1;
    }

    @Override
    public long update(Endereco obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put("LOGRADOURO", obj.getLogradouro());
            valores.put("NUMERO", obj.getNumero());
            valores.put("BAIRRO", obj.getBairro());
            valores.put("CIDADE", obj.getCidade());
            valores.put("UF", obj.getUf());

            String[] identificador = {String.valueOf(obj.getCodigo())};
            return bd.update(tableName, valores, "CODIGO = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO", "EnderecoDao.update(): " + ex.getMessage());
        }
        return -1;
    }

    @Override
    public long delete(Endereco obj) {
        try {
            String[] identification = {String.valueOf(obj.getCodigo())};
            return bd.delete(tableName, "CODIGO = ?", identification);
        } catch (SQLException ex) {
            Log.e("ERRO", "EnderecoDao.delete(): " + ex.getMessage());
        }
        return -1;
    }

    @Override
    public ArrayList<Endereco> getAll() {
        ArrayList<Endereco> lista = new ArrayList<>();
        try {
            Cursor cursor = bd.query(tableName, colunas, null, null, null, null, "CODIGO asc");
            if (cursor.moveToFirst()) {
                do {
                    Endereco endereco = new Endereco();
                    endereco.setCodigo(cursor.getInt(0));
                    endereco.setLogradouro(cursor.getString(1));
                    endereco.setNumero(cursor.getInt(2));
                    endereco.setBairro(cursor.getString(3));
                    endereco.setCidade(cursor.getString(4));
                    endereco.setUf(cursor.getString(5));

                    lista.add(endereco);
                } while (cursor.moveToNext());
            }
        } catch (SQLException ex) {
            Log.e("ERRO", "EnderecoDao.getAll(): " + ex.getMessage());
        }
        return lista;
    }

    @Override
    public Endereco getById(int id) {
        try {
            Cursor cursor = bd.query(tableName, colunas, "CODIGO = ?", new String[] { String.valueOf(id) }, null, null, "CODIGO asc");
            if (cursor.moveToFirst()) {
                Endereco endereco = new Endereco();
                endereco.setCodigo(cursor.getInt(0));
                endereco.setLogradouro(cursor.getString(1));
                endereco.setNumero(cursor.getInt(2));
                endereco.setBairro(cursor.getString(3));
                endereco.setCidade(cursor.getString(4));
                endereco.setUf(cursor.getString(5));

                return endereco;
            }
        } catch (SQLException ex) {
            Log.e("ERRO", "EnderecoDao.getById(): " + ex.getMessage());
        }
        return null;
    }
}
