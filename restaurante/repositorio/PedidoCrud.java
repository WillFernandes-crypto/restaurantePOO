package repositorio;

import dados.*;

import javax.swing.*;
import java.awt.GridLayout;
import java.util.*;

public class PedidoCrud implements IPedido {
    private Map<Integer, List<Pedido>> pedidos; // Map to store the orders
    private List<Produto> produtos;

    public PedidoCrud(ProdutoCrud produtoCrud, Conta conta) {
        pedidos = new HashMap<>();
        produtos = produtoCrud.getProdutos();
    }

    @Override
    public void anotarPedido(int codPedido) {
        // Check if there are registered products before taking the order
        if (produtos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há produtos cadastrados!");
            return;
        }

        // Obtain the product code and desired quantity
        List<Produto> itens = new ArrayList<>();
        while (true) {
            String inputCodProduto = JOptionPane
                    .showInputDialog("Digite o código do produto / Selecione sair para realizar o pedido:");
            if (inputCodProduto == null || inputCodProduto.equalsIgnoreCase("Sair")) {
                break;
            }

            if (inputCodProduto.isEmpty()) {
                JOptionPane.showMessageDialog(null, "O código do produto não pode ser vazio!");
                continue;
            }

            int codProdutoEscolhido;
            try {
                codProdutoEscolhido = Integer.parseInt(inputCodProduto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Codigo de produto inválido!");
                continue;
            }

            Produto produtoEscolhido = null;
            for (Produto produto : produtos) {
                if (produto.getCodProduto() == codProdutoEscolhido) {
                    produtoEscolhido = produto;
                    break;
                }
            }

            if (produtoEscolhido == null) {
                JOptionPane.showMessageDialog(null, "Produto não encontrado!");
                continue;
            }

            String inputQuantidade = JOptionPane.showInputDialog("Digite a quantidade:");
            if (inputQuantidade == null || inputQuantidade.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Quantidade não pode ser vazia!");
                continue;
            }

            int quantidade;
            try {
                quantidade = Integer.parseInt(inputQuantidade);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantidade inválida!");
                continue;
            }

            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(null, "Quantidade deve ser maior que zero!");
                continue;
            }

            int qtdProdutoDisponivel = produtoEscolhido.getQtdProduto();
            if (quantidade > qtdProdutoDisponivel) {
                JOptionPane.showMessageDialog(null,
                        "Quantidade não disponivel para este produto. Quantidade em estoque é:: "
                                + qtdProdutoDisponivel);
                continue;
            }

            produtoEscolhido.setQtdProduto(qtdProdutoDisponivel - quantidade);

            boolean produtoJaExiste = false;
            for (Produto item : itens) {
                if (item.getCodProduto() == produtoEscolhido.getCodProduto()) {
                    item.setQtdProduto(item.getQtdProduto() + quantidade);
                    produtoJaExiste = true;
                    break;
                }
            }

            if (!produtoJaExiste) {
                Produto item = new Produto(produtoEscolhido.getCodProduto(), produtoEscolhido.getCodCategoria(),
                        produtoEscolhido.getNomeProduto(), produtoEscolhido.getPrecoProduto(), quantidade);
                itens.add(item);
            }
        }

        // Check if any products were added to the order
        if (itens.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pedido vazio!");
            return;
        }

        // Check if the order code already exists
        if (pedidos.containsKey(codPedido)) {
            int resposta = JOptionPane.showConfirmDialog(null,
                    "Codigo da Mesa já existente! Deseja adicionar os produtos mesmo assim?");
            if (resposta == JOptionPane.YES_OPTION) {
                List<Pedido> pedidosList = pedidos.get(codPedido);
                Pedido pedidoExistente = pedidosList.get(0);
                for (Produto item : itens) {
                    boolean produtoJaExiste = false;
                    for (Produto produtoExistente : pedidoExistente.getItens()) {
                        if (produtoExistente.getCodProduto() == item.getCodProduto()) {
                            produtoExistente.setQtdProduto(produtoExistente.getQtdProduto() + item.getQtdProduto());
                            produtoJaExiste = true;
                            break;
                        }
                    }
                    if (!produtoJaExiste) {
                        pedidoExistente.getItens().add(item);
                    }
                }
                JOptionPane.showMessageDialog(null, "Itens adicionados ao pedido existente!");
            } else {
                JOptionPane.showMessageDialog(null, "Pedido não alterado!");
            }
            return;
        }

        List<Pedido> pedidosList = new ArrayList<>();
        Pedido pedido = new Pedido(codPedido);
        pedido.setCodPedido(codPedido);
        pedido.setItens(itens);
        pedidosList.add(pedido);
        pedidos.put(codPedido, pedidosList);
        JOptionPane.showMessageDialog(null, "Pedido realizado!");
    }

    @Override
    public void finalizarPedido(int codPedido, double valorPago) {
        List<Pedido> pedidosList = pedidos.get(codPedido);
        if (pedidosList == null) {
            JOptionPane.showMessageDialog(null, "Pedido não encontrado!");
            return;
        }

        Pedido pedido = pedidosList.get(0);
        pedido.setValorPago(valorPago);

        if (valorPago >= pedido.getTotalPedido()) {
            if (valorPago > pedido.getTotalPedido()) {
                Conta conta = new Conta();
                conta.emitirNotaFiscal(pedido);
            } else {
                if (acesso()) {
                    Conta conta = new Conta();
                    conta.emitirNotaFiscal(pedido);
                    pedidos.remove(codPedido);
                    JOptionPane.showMessageDialog(null, "Pedido finalizado!");
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Acesso negado. Pedido não foi finalizado!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "O valor pago é inferior ao total. Consulte o gerente.");
        }
    }

    @Override
    public void listarPedido(int codPedido) {
        List<Pedido> pedidosList = pedidos.get(codPedido);
        if (pedidosList == null) {
            JOptionPane.showMessageDialog(null, "Pedido não encontrado!");
            return;
        }

        Pedido pedido = pedidosList.get(0);
        List<Produto> itens = pedido.getItens();

        StringBuilder sb = new StringBuilder();
        sb.append("Mesa: ").append(codPedido).append("\n");

        double totalPedido = 0.0;
        for (Produto item : itens) {
            sb.append(item.getNomeProduto()).append(" - ").append(item.getQtdProduto()).append(" unidade(s)\n");
            totalPedido += item.getPrecoProduto() * item.getQtdProduto();
        }

        sb.append("Total: R$ ");

        sb.append(totalPedido);

        sb.append("\n");

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    @Override
    public void listarTodosPedidos() {
        if (pedidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum pedido registrado!");
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(pedidos.size() + 1, 1));

        for (Map.Entry<Integer, List<Pedido>> entry : pedidos.entrySet()) {
            int codPedido = entry.getKey();
            JButton button = new JButton("Mesa: " + codPedido);
            button.addActionListener(e -> listarPedido(codPedido));
            panel.add(button);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        JOptionPane.showMessageDialog(null, scrollPane);
    }

    private boolean acesso() {
        UsuarioCrud user = new UsuarioCrud();
        user.acesso();
        return true;
    }
}
