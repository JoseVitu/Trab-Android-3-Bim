package com.example.forcavendasapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.forcavendasapp.R;
import com.example.forcavendasapp.controller.ItemController;
import com.example.forcavendasapp.model.Item;

public class ItemActivity extends AppCompatActivity {

    Button btnSalvar, btnCancelar;
    EditText editDescricao, editUnidadeMedida, editValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_item);

        editDescricao = findViewById(R.id.editDescricaoItem);
        editUnidadeMedida = findViewById(R.id.editUnMedidaItem);
        editValor = findViewById(R.id.editVlrUnitItem);
        btnSalvar = findViewById(R.id.btnSalvarItem);
        btnCancelar = findViewById(R.id.btnVoltarMenu);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpaCampos();
                Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    salvarItem();
                } catch (Exception ex){
                    Log.e("Erro SAlvar", ex.getMessage());
                }
            }
        });

    }

    public void salvarItem(){
        ItemController itemController = new ItemController(this);

        String descricao = editDescricao.getText().toString();
        String unMedida = editUnidadeMedida.getText().toString();
        String valorTotal = editValor.getText().toString();



        if (descricao.isEmpty() || descricao.equals("")) {
            editDescricao.setError("Preencha a Descrição");
        }
        if (unMedida.isEmpty() || unMedida.equals("")) {
            editUnidadeMedida.setError("Preencha a Unidade de Medida");
        }
        if (valorTotal.isEmpty() || valorTotal.equals("")) {
            editValor.setError("Preencha o Valor");
        }
        else{
            try {
                double vlrUnit = Double.parseDouble(valorTotal);

                Item novoItem = new Item();
                novoItem.setDescricao(descricao);
                novoItem.setUnMedida(unMedida);
                novoItem.setVlrUnit(vlrUnit);

                itemController.validaItem(descricao, vlrUnit, unMedida);
                itemController.salvarItem(novoItem);

                Toast.makeText(this, "Item Salvo", Toast.LENGTH_SHORT).show();
                limpaCampos();
            } catch (NumberFormatException e) {
                Log.e("Erro Salvar", "O valor não é um número válido");
            }
        }
    }

    public void limpaCampos(){
        editValor.setText("");
        editDescricao.setText("");
        editUnidadeMedida.setText("");
    }

}