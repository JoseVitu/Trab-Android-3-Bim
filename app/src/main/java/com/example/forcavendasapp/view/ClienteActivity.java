package com.example.forcavendasapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.forcavendasapp.R;
import com.example.forcavendasapp.controller.ClienteController;
import com.example.forcavendasapp.controller.EnderecoController;
import com.example.forcavendasapp.model.Cliente;
import com.example.forcavendasapp.model.Endereco;

import java.util.ArrayList;


public class ClienteActivity extends AppCompatActivity {

    private EditText edNomeCliente, edCpfCliente, edDataNascimentoCliente;

    Button btnSalvar, btnVoltar;

    private Spinner spEnderecoCliente;
    private ArrayList<Endereco> listaEnderecos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);
        spEnderecoCliente = findViewById(R.id.spCodEnderecoCliente);
        edNomeCliente = findViewById(R.id.editNomeCliente);
        edCpfCliente = findViewById(R.id.editCpfCliente);
        edDataNascimentoCliente = findViewById(R.id.editDtNascCliente);
        btnSalvar = findViewById(R.id.btnSalvarCliente);
        btnVoltar = findViewById(R.id.btnVoltarMenu);

        EnderecoController enderecoController = new EnderecoController(this);

        listaEnderecos = enderecoController.findAllEndereco();
        ArrayAdapter<Endereco> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaEnderecos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spEnderecoCliente.setAdapter(adapter);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarCliente();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpaCampos();
                Intent intent = new Intent(ClienteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void salvarCliente() {

        Endereco enderecoSelecionado = (Endereco) spEnderecoCliente.getSelectedItem();
        String nome = edNomeCliente.getText().toString();
        String cpf = edCpfCliente.getText().toString();
        String dataNasc = edDataNascimentoCliente.toString();
        int codEndereco = enderecoSelecionado.getCodigo();

        if (nome.isEmpty() || edNomeCliente.equals("")) {
            edNomeCliente.setError("Informe o nome");
        }
        if (cpf.isEmpty() || edCpfCliente.equals("")) {
            edCpfCliente.setError("Informe o CPF");
        }
        if (dataNasc.isEmpty() || edDataNascimentoCliente.equals("")) {
            edDataNascimentoCliente.setError("Informe a data de nascimento");
        }
        else {
            ClienteController clienteController = new ClienteController(this);

            String mensagem = clienteController.validaCliente("0", nome, cpf, dataNasc, codEndereco);

            if (!mensagem.equals("")) {
                Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
            }

            Cliente novoCliente = new Cliente(nome, cpf, dataNasc, codEndereco);

            long resultado = clienteController.salvarCliente(novoCliente);

            if (resultado != -1) {
                Toast.makeText(this, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                limpaCampos();
            } else {
                Toast.makeText(this, "Erro ao cadastrar cliente!", Toast.LENGTH_SHORT).show();
            }
        }


    }
    private void limpaCampos() {
        edNomeCliente.setText("");
        edCpfCliente.setText("");
        edDataNascimentoCliente.setText("");
    }
}