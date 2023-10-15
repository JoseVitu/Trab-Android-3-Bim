package com.example.forcavendasapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.forcavendasapp.R;
import com.example.forcavendasapp.controller.ClienteController;
import com.example.forcavendasapp.controller.EnderecoController;
import com.example.forcavendasapp.controller.ItemController;

public class MainActivity extends AppCompatActivity {

    private Button btnCadItem, btnCadCliente, btnCadEndereco, btnPedidoVenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnCadItem = findViewById(R.id.btnMenuItem);
        btnCadCliente = findViewById(R.id.btnMenuCliente);
        btnCadEndereco = findViewById(R.id.btnMenuEndereco);
        btnPedidoVenda = findViewById(R.id.btnMenuPedidoVenda);

        EnderecoController enderecoController = new EnderecoController(this);
        ClienteController clienteController = new ClienteController(this);
        ItemController itemController = new ItemController(this);

        btnCadEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnderecoActivity.class);
                startActivity(intent);
            }
        });

        btnCadCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (enderecoController.findAllEndereco().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Cadastre ao menos 1 endereço para continuar", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, ClienteActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnCadItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                startActivity(intent);
            }
        });

        btnPedidoVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (enderecoController.findAllEndereco().isEmpty() || clienteController.findAllCliente().isEmpty() || itemController.findAllItem().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Cadastre ao menos 1 item, 1 endereço e 1 cliente para continuar", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, PedidoVendaActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}