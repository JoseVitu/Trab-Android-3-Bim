package com.example.forcavendasapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forcavendasapp.R;
import com.example.forcavendasapp.adapter.ItemListAdapter;
import com.example.forcavendasapp.controller.ClienteController;
import com.example.forcavendasapp.controller.EnderecoController;
import com.example.forcavendasapp.controller.ItemController;
import com.example.forcavendasapp.controller.PedidoController;
import com.example.forcavendasapp.model.Cliente;
import com.example.forcavendasapp.model.Endereco;
import com.example.forcavendasapp.model.Item;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PedidoVendaActivity extends AppCompatActivity {

    private LinearLayout llNvPedido, llBuscaPedido;
    private Spinner spEnderecoEntrega, spCliente, spItem;
    private ArrayList<Endereco> listaEnderecos;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Item> listaItems;
    private ArrayList<Item> listaItemsSelecionados = new ArrayList<>();
    private LinearLayout llAPrazo;
    private TextView tvQntItem, tvTotalItensLista, tvTotal, tvTitleParcelas, tvParcelas, tvTotalFrete, clienteTv, enderecoTv, valorTotalTv, codigo;
    private RecyclerView rvListaItems;
    private Button btGerarParcelas, btSalvar, btVoltarMenu, btBuscaPedido, btVoltar;
    private EditText edQuantParcelas, edCodigo;
    private RadioGroup rgFormaPgt;
    private RadioButton rbAVista, rbAPrazo;
    private double valorTotal = 0;
    private double valorTotalCondicao = 0;
    private double valorFrete = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        spEnderecoEntrega = findViewById(R.id.spEnderecoEntrega);
        spCliente = findViewById(R.id.spCliente);
        spItem = findViewById(R.id.spItem);
        rvListaItems = findViewById(R.id.rvItems);
        tvQntItem = findViewById(R.id.tvQntItem);
        tvTotalItensLista = findViewById(R.id.tvTotalLista);
        rbAPrazo = findViewById(R.id.rbAPrazo);
        rbAVista = findViewById(R.id.rbAVista);
        tvTotal = findViewById(R.id.tvTotal);
        llAPrazo = findViewById(R.id.llAPrazo);
        btGerarParcelas = findViewById(R.id.btnGerarParcelas);
        edQuantParcelas = findViewById(R.id.edQuantParcelas);
        tvTitleParcelas = findViewById(R.id.tvTitleParcelas);
        tvParcelas = findViewById(R.id.tvParcelas);
        rgFormaPgt = findViewById(R.id.rgFormaPgt);
        tvTotalFrete = findViewById(R.id.tvTotalFrete);
        btSalvar = findViewById(R.id.btnSalvar);
        btVoltarMenu = findViewById(R.id.btnVoltarMenu);
        edCodigo = findViewById(R.id.edCodigo);
        llNvPedido = findViewById(R.id.llNvPedido);
        llBuscaPedido = findViewById(R.id.busca_pedido);
        btBuscaPedido = findViewById(R.id.btBuscaPedido);
        btVoltar = findViewById(R.id.btnVoltar);
        clienteTv = findViewById(R.id.cliente);
        enderecoTv = findViewById(R.id.endereco);
        valorTotalTv = findViewById(R.id.valorTotal);
        codigo = findViewById(R.id.codigo);

        rgFormaPgt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                edQuantParcelas.setText(null);

                if (rbAVista.isChecked()) {
                    llAPrazo.setVisibility(View.GONE);
                    tvTitleParcelas.setVisibility(View.GONE);
                    tvParcelas.setVisibility(View.GONE);
                    calculaTotalPedidoCondicoes();
                    tvParcelas.setText("");
                } else if (rbAPrazo.isChecked()) {
                    llAPrazo.setVisibility(View.VISIBLE);
                    tvTitleParcelas.setVisibility(View.VISIBLE);
                    tvParcelas.setVisibility(View.VISIBLE);
                    calculaTotalPedidoCondicoes();
                }
            }
        });

        btGerarParcelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qntParcela = edQuantParcelas.getText().toString();


                try{
                    if (qntParcela.equals(null) || qntParcela.equals("") || qntParcela.equals("0")) {
                        edQuantParcelas.setError("Informe um valor maior que zero.");
                    } else {
                        int qntParcelas = Integer.parseInt(qntParcela);
                        Double valorParcela = valorTotalCondicao / qntParcelas;

                        String parcelas = "";

                        for (int i = 0; i < qntParcelas; i++) {
                            parcelas += "Parcela " + (i + 1) + " - R$ - " + valorParcela + "\n";
                        }

                        tvParcelas.setText(parcelas);
                        parcelas = "";

                    }
                }catch (Exception ex){
                    Log.e("ERRO!", ex.getMessage());
                }

            }
        });

        Item selecioneItem = new Item();
        selecioneItem.setDescricao("Selecione um Item!");

        Endereco selecioneEndereco = new Endereco();
        selecioneEndereco.setCidade("Selecione um Endereço!");

        Cliente selecioneCliente = new Cliente();
        selecioneCliente.setNome("Selecione um Cliente!");

        EnderecoController enderecoController = new EnderecoController(this);
        ClienteController clienteController = new ClienteController(this);
        ItemController itemController = new ItemController(this);

        listaEnderecos = enderecoController.findAllEndereco();
        listaClientes = clienteController.findAllCliente();
        listaItems = itemController.findAllItem();

        listaItems.add(0, selecioneItem);
        listaEnderecos.add(0, selecioneEndereco);
        listaClientes.add(0, selecioneCliente);


        ArrayAdapter<Endereco> adapterEndereco = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaEnderecos);
        adapterEndereco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<Cliente> adapterCliente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaClientes);
        adapterCliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<Item> adapterItem = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaItems);
        adapterItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spEnderecoEntrega.setAdapter(adapterEndereco);
        spCliente.setAdapter(adapterCliente);
        spItem.setAdapter(adapterItem);

        spEnderecoEntrega.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Endereco enderecoSelecionado = (Endereco) spEnderecoEntrega.getItemAtPosition(position);
                System.out.println("Endereço: " + enderecoSelecionado.getCidade());
                if (position > 1) {
                    if ("toledo".equalsIgnoreCase(enderecoSelecionado.getCidade().trim())) {
                        valorFrete = 0;
                        tvTotalFrete.setText("Frete Grátis!");
                    } else {
                        valorFrete = 20;
                        tvTotalFrete.setText("Frete: R$: 20,00!");
                    }
                    calculaTotalPedido();
                    calculaTotalPedidoCondicoes();
                } else {
                    valorFrete = 0;
                    calculaTotalPedido();
                    calculaTotalPedidoCondicoes();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    Item itemSelecionado = (Item) spItem.getItemAtPosition(position);
                    listaItemsSelecionados.add(itemSelecionado);
                    atualizarListaItems();
                    calculaTotalPedido();
                    calculaTotalPedidoCondicoes();
                    spItem.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarPedido();
                limpaCampos();
            }
        });

        btVoltarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpaCampos();
                Intent intent = new Intent(PedidoVendaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btBuscaPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarPedido();
                limpaCampos();
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llNvPedido.setVisibility(View.VISIBLE);
                llBuscaPedido.setVisibility(View.GONE);
            }
        });

    }
    private void limpaCampos() {

        edCodigo.setText("");
        spCliente.setSelection(0);
        spItem.setSelection(0);
        listaItemsSelecionados.clear();
        calculaTotalPedido();
        calculaTotalPedidoCondicoes();
        spEnderecoEntrega.setSelection(0);
        edQuantParcelas.setText("");
        tvParcelas.setText("");
        atualizarListaItems();

    }
    private void atualizarListaItems() {
        ItemListAdapter adapter = new ItemListAdapter(listaItemsSelecionados, this);
        rvListaItems.setLayoutManager(new LinearLayoutManager(this));

        rvListaItems.setAdapter(adapter);
        if (adapter.getItemCount() > 1) {
            tvQntItem.setText(adapter.getItemCount() + " itens selecionados");
        } else {
            tvQntItem.setText(adapter.getItemCount() + " item selecionado");
        }
    }

    private void calculaTotalPedido() {

        valorTotal = 0;

        for (Item listaItemsSelecionado : listaItemsSelecionados) {
            valorTotal += listaItemsSelecionado.getVlrUnit();
        }

        tvTotalItensLista.setText("Total dos Itens R$ " + valorTotal);

    }

    private void calculaTotalPedidoCondicoes() {

        valorTotalCondicao = 0;

        if (rbAVista.isChecked()) {
            valorTotalCondicao = valorTotal - (valorTotal * 0.05) + valorFrete;
        } else if (rbAPrazo.isChecked()) {
            valorTotalCondicao = valorTotal + (valorTotal * 0.05) + valorFrete;
        }

        if (valorTotalCondicao == 0) {
            tvTotal.setText("Selecione uma condição.");
        } else {
            tvTotal.setText("R$ " + valorTotalCondicao);
        }

    }

    private void salvarPedido() {

        if (listaItemsSelecionados.isEmpty()) {
            Toast.makeText(this, "Informe os itens", Toast.LENGTH_SHORT).show();
        }
        if (spCliente.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Informe o cliente", Toast.LENGTH_SHORT).show();
        }
        if (spEnderecoEntrega.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Informe o Endereço de Entrega", Toast.LENGTH_SHORT).show();
        }
        if (valorTotalCondicao == 0) {
            Toast.makeText(this, "Selecione a Condição de Pagamento", Toast.LENGTH_SHORT).show();
        } else {
            com.example.forcavendasapp.model.Pedido nvPedido = new com.example.forcavendasapp.model.Pedido();

            Cliente cliente = (Cliente) spCliente.getSelectedItem();
            Endereco endereco = (Endereco) spEnderecoEntrega.getSelectedItem();

            nvPedido.setCodPessoa(Integer.valueOf(String.valueOf(cliente.getCodigo())));
            nvPedido.setCodEndereco(Integer.valueOf(String.valueOf(endereco.getCodigo())));
            nvPedido.setItens(listaItemsSelecionados);
            nvPedido.setVlrTotal(valorTotalCondicao);

            try {
                PedidoController pedidoController = new PedidoController(this);
                long codGerado = pedidoController.salvarPedido(nvPedido);
                Toast.makeText(this, "GERADO PEDIDO N° " + codGerado, Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }

    }

    private void buscarPedido() {

        try {
            String codigoBusca = edCodigo.getText().toString();

            if (codigoBusca.equals("") || codigoBusca.equals(null)) {
                edCodigo.setError("Informe um código para pesquisar.");
            } else {

                PedidoController pedidoController = new PedidoController(this);
                ClienteController clienteController = new ClienteController(this);
                EnderecoController enderecoController = new EnderecoController(this);

                com.example.forcavendasapp.model.Pedido buscaPedido = pedidoController.findByIdPedido(Integer.valueOf(edCodigo.getText().toString()));

                Cliente cliente = clienteController.findByIdCliente(buscaPedido.getCodPessoa());
                Endereco endereco = enderecoController.findByIdEndereco(buscaPedido.getCodEndereco());

                llNvPedido.setVisibility(View.GONE);
                llBuscaPedido.setVisibility(View.VISIBLE);

                codigo.setText("PEDIDO N° " + String.valueOf(buscaPedido.getCodigo()));
                clienteTv.setText(cliente.toString());
                enderecoTv.setText("Endereço: " + endereco.toString());

                DecimalFormat formato = new DecimalFormat("0.00");
                double numero = buscaPedido.getVlrTotal();
                String numeroFormatado = formato.format(numero);

                valorTotalTv.setText("Valor Total: R$ " + String.valueOf(numeroFormatado));
            }

        } catch (Exception ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("ERRO BUSCAR PEDIDO!", ex.getMessage());

        }

    }

}