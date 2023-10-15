package com.example.forcavendasapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.forcavendasapp.R;
import com.example.forcavendasapp.controller.EnderecoController;
import com.example.forcavendasapp.model.Endereco;


public class EnderecoActivity extends AppCompatActivity {

    Button btnSalvar, btnVoltar;
    EditText editLogradouro, editNumero, editBairro, editCidade, editUf;

    EnderecoController enderecoController = new EnderecoController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);



        editLogradouro = findViewById(R.id.editLogradouro);
        editNumero = findViewById(R.id.editNumero);
        editBairro = findViewById(R.id.editBairro);
        editCidade = findViewById(R.id.editCidade);
        editUf = findViewById(R.id.editUF);
        btnSalvar = findViewById(R.id.btnSalvarEndereco);
        btnVoltar = findViewById(R.id.btnVoltarMenu);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpaCampos();
                Intent intent = new Intent(EnderecoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarEndereco();
            }
        });
    }

    private void salvarEndereco() {
        String logradouro = editLogradouro.getText().toString();
        String numero = editNumero.getText().toString();
        String bairro = editBairro.getText().toString();
        String cidade = editCidade.getText().toString();
        String uf = editUf.getText().toString();

        if (logradouro.isEmpty()) {
            editLogradouro.setError("Logradouro é obrigatório!");
        }

        if (numero.isEmpty()) {
            editNumero.setError("Número é obrigatório!");
        }

        if (!numero.matches("\\d+")) {
            editNumero.setError("Número inválido!");
        }

        if (bairro.isEmpty()) {
            editBairro.setError("Bairro é obrigatório!");
        }

        if (cidade.isEmpty()) {
            editCidade.setError("Cidade é obrigatório!");
        }

        if (uf.isEmpty() || uf.length() != 2) {
            editUf.setError("UF deve ter 2 caracteres!");
        }else{

        Endereco novoEndereco = new Endereco(logradouro, Integer.parseInt(numero), bairro, cidade, uf);

        long resultado = enderecoController.salvarEndereco(novoEndereco);

        if (resultado != -1) {
            Toast.makeText(this, "Endereço cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            limpaCampos();
        } else {
            Toast.makeText(this, "Erro ao cadastrar endereço!", Toast.LENGTH_SHORT).show();
        }
    }
    }

    private void limpaCampos() {
        editLogradouro.setText("");
        editNumero.setText("");
        editBairro.setText("");
        editCidade.setText("");
        editUf.setText("");
    }
}