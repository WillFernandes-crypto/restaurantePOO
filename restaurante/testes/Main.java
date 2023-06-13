package testes;

import java.awt.GridLayout;

import javax.swing.*;
import repositorio.*;

public class Main {
    public static void main(String[] args) {
        MenuPrincipal();
    }

    public static void MenuPrincipal() {
        UsuarioCrud user = new UsuarioCrud();
        CategoriaCrud categoria = new CategoriaCrud();
        ProdutoCrud prod = new ProdutoCrud(categoria);
        PedidoCrud restaurante = new PedidoCrud(prod, null);

        String[] opcoes = { "Usuário", "Produto", "Categorias", "Pedido", "Sair" };

        try { // Tratamento de excessão para o acesso a elementos de arrays fora dos limites
            int escolha = JOptionPane.showOptionDialog(null, "Selecione uma opção:", "Menu", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

            while (escolha != 4) {
                switch (escolha) {

                    // Inicio da seção de CRUD de usuários

                    case 0:
                        // Função para acesso ao sistema com login e senha padrão ou cadastrados, mas
                        // somente gerentes permitidos
                        boolean acessoPermitido = user.acesso();
                        if (acessoPermitido) {
                            while (true) {
                                String[] opcoesUsuario = { "Cadastrar novo usuário", "Verificar credenciais de usuário",
                                        "Listar usuários", "Excluir usuário", "Alterar credenciais", "Sair" };
                                int opcao = JOptionPane.showOptionDialog(null, "Usuários", "Opções",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoesUsuario,
                                        opcoesUsuario[0]);

                                switch (opcao) {
                                    case 0:
                                        JPasswordField senhaField = new JPasswordField(10);
                                        JButton verSenhaButton = new JButton("Ver senha");

                                        JPanel panel = new JPanel(new GridLayout(0, 2));
                                        panel.add(new JLabel("Nome:"));
                                        JTextField nomeField = new JTextField();
                                        panel.add(nomeField);
                                        panel.add(new JLabel("Login:"));
                                        JTextField loginField = new JTextField();
                                        panel.add(loginField);
                                        panel.add(new JLabel("Nova senha:"));
                                        panel.add(senhaField);
                                        panel.add(new JLabel("")); // Espaço vazio para a próxima linha
                                        panel.add(verSenhaButton);

                                        verSenhaButton.addActionListener(e -> {
                                            if (verSenhaButton.getText().equals("Ver senha")) {
                                                senhaField.setEchoChar((char) 0);
                                                verSenhaButton.setText("Ocultar senha");
                                            } else {
                                                senhaField.setEchoChar('*');
                                                verSenhaButton.setText("Ver senha");
                                            }
                                        });

                                        // Adicionar botões de opção para selecionar o tipo de usuário
                                        JRadioButton gerenteButton = new JRadioButton("Gerente");
                                        JRadioButton garcomButton = new JRadioButton("Garçom");

                                        ButtonGroup tipoUsuarioGroup = new ButtonGroup();
                                        tipoUsuarioGroup.add(gerenteButton);
                                        tipoUsuarioGroup.add(garcomButton);

                                        panel.add(new JLabel("Tipo de usuário:"));
                                        panel.add(gerenteButton);
                                        panel.add(new JLabel(""));
                                        panel.add(garcomButton);

                                        int result = JOptionPane.showConfirmDialog(null, panel,
                                                "Cadastrar novo usuário",
                                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                                        if (result == JOptionPane.OK_OPTION) {
                                            String nomeUsuario = nomeField.getText();
                                            String loginUsuario = loginField.getText();
                                            String senhaUsuario = new String(senhaField.getPassword());

                                            // Verificar se o tipo de usuário foi selecionado
                                            if (!gerenteButton.isSelected() && !garcomButton.isSelected()) {
                                                JOptionPane.showMessageDialog(null, "Selecione o tipo de usuário!");
                                                break;
                                            }

                                            // Tratamento de exceções para Strings vazias e nulas
                                            if (!nomeUsuario.isEmpty() && !loginUsuario.isEmpty()
                                                    && !senhaUsuario.isEmpty()) {
                                                boolean userGerente = gerenteButton.isSelected();
                                                boolean userGarcom = garcomButton.isSelected();
                                                user.cadUsuario(nomeUsuario, loginUsuario, senhaUsuario, userGerente,
                                                        userGarcom);
                                                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                                            } else {
                                                JOptionPane.showMessageDialog(null,
                                                        "Dados inválidos! Preencha todos os campos.");
                                            }
                                        }
                                        break;

                                    case 1:
                                        String loginUsuario = JOptionPane.showInputDialog("Login:");
                                        String senhaUsuario = JOptionPane.showInputDialog("Senha:");

                                        if (loginUsuario != null && !loginUsuario.isEmpty() &&
                                                senhaUsuario != null && !senhaUsuario.isEmpty()) {

                                            boolean credenciaisCorretas = user.verificaCred(loginUsuario, senhaUsuario);

                                            if (credenciaisCorretas) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Credenciais corretas. Acesso permitido!");
                                            } else {
                                                JOptionPane.showMessageDialog(null,
                                                        "Credenciais incorretas. Acesso negado!");
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Dados inválidos! Preencha todos os campos.");
                                        }
                                        break;

                                    case 2:
                                        user.listaUser();
                                        break;

                                    case 3:
                                        loginUsuario = JOptionPane.showInputDialog("Login do usuário a ser removido:");
                                        if (loginUsuario != null && !loginUsuario.isEmpty()) {
                                            user.delUser(loginUsuario);
                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Login inválido! O login não pode ser vazio.");
                                        }
                                        break;

                                    case 4:
                                        loginUsuario = JOptionPane
                                                .showInputDialog("Login do usuário a ter as credenciais alteradas:");
                                        if (loginUsuario != null && !loginUsuario.isEmpty()) {
                                            user.alterarCredenciais(loginUsuario);
                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Login inválido! O login não pode ser vazio.");
                                        }

                                        break;

                                    case 5:
                                        escolha = JOptionPane.showOptionDialog(null, "Selecione uma opção:", "Menu",
                                                JOptionPane.DEFAULT_OPTION,
                                                JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
                                        break;

                                    default:
                                        JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                                        break;
                                }
                                if (opcao == 5) {
                                    break;
                                }
                            }
                        }
                        break;
                    // Fim da seção de CRUD de usuários

                    // Inicio da seção de CRUD dos produtos

                    case 1:
                        acessoPermitido = user.acesso();
                        if (acessoPermitido) {
                            while (true) {
                                String[] opcoesProduto = { "Cadastrar novo produto", "Verificar se produto já existe",
                                        "Listar produtos", "Excluir produto", "Alterar dados do produto", "Sair" };

                                int opProduto = JOptionPane.showOptionDialog(null, "Sistema de Cadastro de Usuários",
                                        "Opções",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoesProduto,
                                        opcoesProduto[0]);

                                switch (opProduto) {
                                    case 0:
                                        JPanel panel = new JPanel(new GridLayout(0, 2));
                                        panel.add(new JLabel("Código do Produto:"));
                                        JTextField codPField = new JTextField();
                                        panel.add(codPField);
                                        panel.add(new JLabel("Nome do Produto:"));
                                        JTextField nomeProdutoField = new JTextField();
                                        panel.add(nomeProdutoField);
                                        panel.add(new JLabel("Preço do Produto:"));
                                        JTextField pricePField = new JTextField();
                                        panel.add(pricePField);
                                        panel.add(new JLabel("Quantidade do Produto:"));
                                        JTextField qtdProdField = new JTextField();
                                        panel.add(qtdProdField);

                                        int result = JOptionPane.showConfirmDialog(null, panel,
                                                "Cadastrar novo produto",
                                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                                        if (result == JOptionPane.OK_OPTION) {
                                            String codP = codPField.getText();
                                            String nomeProduto = nomeProdutoField.getText();
                                            String priceP = pricePField.getText();
                                            String qtdProd = qtdProdField.getText();

                                            try { // Tratamento para conversão de caracteres inválidos
                                                int codProduto = Integer.parseInt(codP);
                                                double precoProduto = Double.parseDouble(priceP.replace(",", "."));
                                                int qtdProduto = Integer.parseInt(qtdProd);

                                                if (codP != null && !codP.isEmpty() &&
                                                        nomeProduto != null && !nomeProduto.isEmpty() &&
                                                        priceP != null && !priceP.isEmpty() &&
                                                        qtdProd != null && !qtdProd.isEmpty()) {

                                                    prod.cadProduto(codProduto, nomeProduto, precoProduto,
                                                            qtdProduto);
                                                    JOptionPane.showMessageDialog(null,
                                                            "Produto cadastrado com sucesso!");
                                                } else {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Dados inválidos! Preencha todos os campos.");
                                                }
                                            } catch (NumberFormatException e) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Erro na conversão de tipos. Certifique-se de inserir valores numéricos válidos!");
                                            }

                                        }

                                        break;

                                    case 1:
                                        String codProd = JOptionPane.showInputDialog("Digite o código do produto:");
                                        int codProduto = Integer.parseInt(codProd);
                                        String nomeProd = JOptionPane.showInputDialog("Digite o nome do produto:");
                                        String precoProd = JOptionPane.showInputDialog("Digite o preço do produto:");
                                        double precoProduto = Double.parseDouble(precoProd);
                                        String qtdProdutoStr = JOptionPane
                                                .showInputDialog("Digite a quantidade do produto:");
                                        int qtdProduto = Integer.parseInt(qtdProdutoStr);
                                        prod.cadProduto(codProduto, nomeProd, precoProduto, qtdProduto);
                                        break;

                                    case 2:
                                        prod.listaProd();
                                        break;

                                    case 3:
                                        String codP = JOptionPane.showInputDialog("Código do produto a ser removido: ");
                                        codProduto = Integer.parseInt(codP);
                                        if (codP != null && !codP.isEmpty()) {
                                            prod.delProd(codProduto);
                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Login inválido! O login não pode ser vazio.");
                                        }
                                        break;

                                    case 4:

                                        codP = JOptionPane.showInputDialog("Código do produto a ser alterado: ");
                                        codProduto = Integer.parseInt(codP);
                                        if (codP != null && !codP.isEmpty()) {
                                            prod.alteraProd(codProduto);
                                        } else {
                                            JOptionPane.showMessageDialog(null,
                                                    "Código inválido! O código não pode ser vazio.");
                                        }
                                        break;

                                    case 5:
                                        escolha = JOptionPane.showOptionDialog(null, "Selecione uma opção:", "Menu",
                                                JOptionPane.DEFAULT_OPTION,
                                                JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
                                        break;

                                    default:
                                        JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                                        break;
                                }
                                if (opProduto == 5) {
                                    break;
                                }
                            }
                        }
                        break;
                    // Fim da seção de CRUD dos produtos

                    // Inicio da seção de CRUD das categorias
                    case 2:
                        acessoPermitido = user.acesso();
                        if (acessoPermitido) {
                            while (true) {
                                String[] opcoesCategoria = { "Cadastrar nova categoria", "Listar categorias",
                                        "Alterar dados de uma categoria",
                                        "Excluir categoria", "Sair" };

                                int opCategoria = JOptionPane.showOptionDialog(null,
                                        "Sistema de Cadastro de Categorias",
                                        "Opções",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoesCategoria,
                                        opcoesCategoria[0]);
                                switch (opCategoria) {
                                    case 0:
                                        JPanel panel = new JPanel(new GridLayout(0, 2));
                                        panel.add(new JLabel("Código da Categoria:"));
                                        JTextField codPField = new JTextField();
                                        panel.add(codPField);
                                        panel.add(new JLabel("Tipo da Categoria:"));
                                        JTextField nomeProdutoField = new JTextField();
                                        panel.add(nomeProdutoField);

                                        int result = JOptionPane.showConfirmDialog(null, panel,
                                                "Cadastrar nova categoria",
                                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                                        if (result == JOptionPane.OK_OPTION) {
                                            String codCategoria = codPField.getText();
                                            String tipoCategoria = nomeProdutoField.getText();

                                            try { // Tratamento para conversão de caracteres inválidos
                                                int codCateg = Integer.parseInt(codCategoria);

                                                if (categoria.verificarExistencia(codCateg, tipoCategoria)) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Já existe uma categoria com o mesmo código ou nome!");
                                                } else if (tipoCategoria == null || tipoCategoria.isEmpty()
                                                        && codCateg <= 0) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Dados inválidos! Preencha todos os campos.");

                                                } else {
                                                    categoria.cadastrarCategoria(codCateg, tipoCategoria);
                                                    JOptionPane.showMessageDialog(null,
                                                            "Categoria cadastrada com sucesso!");

                                                }

                                            } catch (NumberFormatException e) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Erro na conversão de tipos. Certifique-se de inserir valores numéricos válidos!");
                                            }
                                        }
                                        break;

                                    case 1:
                                        categoria.listarCategorias();
                                        break;

                                    case 2:
                                        String codCategoria2 = JOptionPane
                                                .showInputDialog("Código da categoria a ser alterada:");

                                        try {
                                            int codCateg = Integer.parseInt(codCategoria2);
                                            categoria.alteraCateg(codCateg);
                                        } catch (NumberFormatException e) {
                                            JOptionPane.showMessageDialog(null, "Código da categoria inválido!");
                                        }
                                        break;

                                    case 3:
                                        String codCategoria3 = JOptionPane
                                                .showInputDialog("Código da categoria a ser excluída:");

                                        try {
                                            int codCateg = Integer.parseInt(codCategoria3);
                                            categoria.excluiCategoria(codCateg);
                                        } catch (NumberFormatException e) {
                                            JOptionPane.showMessageDialog(null, "Código da categoria inválido!");
                                        }
                                        break;

                                    case 4:
                                        escolha = JOptionPane.showOptionDialog(null, "Selecione uma opção:", "Menu",
                                                JOptionPane.DEFAULT_OPTION,
                                                JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
                                        break;

                                    default:
                                        JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                                        break;
                                }
                                if (opCategoria == 4) {
                                    break;
                                }
                            }
                        }
                        break;
                    // Fim da seção de CRUD das categorias

                    // Inicio da seção de CRUD dos pedidos
                    case 3:
                        while (true) {
                            String[] opcoesPedido = { "Anotar Pedido", "Finalizar Pedido", "Buscar Pedido",
                                    "Listar Todos os Pedidos", "Sair" };

                            int opPedido = JOptionPane.showOptionDialog(null, "Sistema de Pedidos de Restaurante",
                                    "Opções", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoesPedido,
                                    opcoesPedido[0]);

                            switch (opPedido) {
                                case 0:
                                    int codPedido = Integer.parseInt(JOptionPane.showInputDialog("Código do Pedido:"));

                                    restaurante.anotarPedido(codPedido);
                                    break;

                                case 1:
                                    codPedido = Integer.parseInt(JOptionPane.showInputDialog("Código do Pedido:"));
                                    double valorPago = Double.parseDouble(JOptionPane.showInputDialog("Valor Pago:"));

                                    restaurante.finalizarPedido(codPedido, valorPago);
                                    break;

                                case 2:
                                    codPedido = Integer.parseInt(JOptionPane.showInputDialog("Código do Pedido:"));
                                    restaurante.listarPedido(codPedido);

                                    // Mensagem de pedido finalizado com sucesso
                                    JOptionPane.showMessageDialog(null, "Pedido finalizado com sucesso!");

                                    break;

                                case 3:
                                    restaurante.listarTodosPedidos();
                                    break;

                                case 4:
                                    escolha = JOptionPane.showOptionDialog(null, "Selecione uma opção:", "Menu",
                                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes,
                                            opcoes[0]);
                                    break;

                                default:
                                    JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
                                    break;
                            }
                            if (opPedido == 4) {
                                break;
                            }
                        }
                        break;
                    // Fim da seção de CRUD dos pedidos
                    case 4:
                        escolha = JOptionPane.showOptionDialog(null, "Selecione uma opção:", "Menu",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
                        break;

                    default:
                        System.exit(0);
                        break;
                }
                if (escolha == 4) {
                    break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
        }
    }
}
