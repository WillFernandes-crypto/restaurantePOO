package repositorio;

import dados.*;
import testes.Main;

import java.awt.GridLayout;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class UsuarioCrud {

    private List<Usuario> user;

    public UsuarioCrud() {
        user = new ArrayList<>();
    }

    public boolean acesso() {
        boolean isAdmin = false;
        boolean isGerente = false;

        String adminLogin = "admin";
        String adminSenha = "admin";

        // Tela de acesso
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Login:"));
        JTextField loginField = new JTextField(10);
        panel.add(loginField);
        panel.add(new JLabel("Senha:"));
        JPasswordField senhaField = new JPasswordField(10);
        panel.add(senhaField);

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(null, "Acesso");

        // Adicionar listener para tratar o fechamento do diálogo
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Impede que o botão "X" feche o diálogo
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Retornar ao menu principal
                Main.MenuPrincipal();
                dialog.dispose();
            }
        });

        dialog.setVisible(true);

        // Verificar o resultado do diálogo
        int result = (Integer) optionPane.getValue();

        if (result == JOptionPane.OK_OPTION) {
            // Obter o login e a senha digitados
            String login = loginField.getText();
            String senha = new String(senhaField.getPassword());

            if (adminLogin.equals(login) && adminSenha.equals(senha)) {
                isAdmin = true;
            } else {
                for (Usuario usuario : user) {
                    if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                        if (usuario instanceof Gerente) {
                            isGerente = true;
                            break;
                        }
                    }
                }
            }

            if (isAdmin || isGerente) {
                JOptionPane.showMessageDialog(null, "Credenciais corretas. Acesso permitido!");
                dialog.dispose(); // Fechar a tela de acesso
                return true; // Acesso concedido
            } else {
                JOptionPane.showMessageDialog(null, "Credenciais incorretas. Acesso negado!");
                return false; // Acesso negado
            }
        } else {
            Main.MenuPrincipal();
            dialog.dispose(); // Fechar a tela de acesso
            return false; // Acesso negado
        }
    }

    public void cadUsuario(String nome, String login, String senha, boolean userGerente, boolean userGarcom) {
        if (userGerente) {
            Gerente novoGerente = new Gerente(nome, login, senha);
            user.add(novoGerente);
        } else if (userGarcom) {
            Garcom novoGarcom = new Garcom(nome, login, senha);
            user.add(novoGarcom);
        }
    }

    public boolean verificaCred(String login, String senha) {
        for (Usuario usuario : user) {
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    public void listaUser() {
        StringBuilder lista = new StringBuilder("Lista de Usuários:\n");
        for (Usuario usuario : user) {
            lista.append(usuario.getNome()).append(" - ").append(usuario.getLogin()).append(" - ")
                    .append(usuario.getTipo()).append("\n");
        }
        JOptionPane.showMessageDialog(null, lista.toString());
    }

    public void delUser(String login) {
        Usuario delUser = null;
        for (Usuario usuario : user) {
            if (usuario.getLogin().equals(login)) {
                delUser = usuario;
                break;
            }
        }
        if (delUser != null) {
            user.remove(delUser);
            JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Usuário não encontrado!");
        }
    }

    public void alterarCredenciais(String login) {
        Iterator<Usuario> iterator = user.iterator();
        Usuario usuarioEncontrado = null;
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getLogin().equals(login)) {
                usuarioEncontrado = usuario;
                break;
            }
        }
        if (usuarioEncontrado != null) {
            JPasswordField senhaField = new JPasswordField(10);
            JButton verSenhaButton = new JButton("Ver senha");

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Novo nome:"));
            JTextField nomeField = new JTextField(usuarioEncontrado.getNome());
            panel.add(nomeField);
            panel.add(new JLabel("Novo login:"));
            JTextField loginField = new JTextField(usuarioEncontrado.getLogin());
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

            // Definir seleção inicial com base no tipo de usuário atual
            if (usuarioEncontrado instanceof Gerente) {
                gerenteButton.setSelected(true);
            } else if (usuarioEncontrado instanceof Garcom) {
                garcomButton.setSelected(true);
            }

            ButtonGroup tipoUsuarioGroup = new ButtonGroup();
            tipoUsuarioGroup.add(gerenteButton);
            tipoUsuarioGroup.add(garcomButton);

            panel.add(new JLabel("Tipo de usuário:"));
            panel.add(gerenteButton);
            panel.add(new JLabel(""));
            panel.add(garcomButton);

            int result = JOptionPane.showConfirmDialog(null, panel, "Alterar credenciais",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String novoNome = nomeField.getText();
                String novoLogin = loginField.getText();
                String novaSenha = new String(senhaField.getPassword());

                if (!novoNome.isEmpty()) {
                    usuarioEncontrado.setNome(novoNome);
                } else {
                    JOptionPane.showMessageDialog(null, "Nome inválido! O nome não pode ser vazio.");
                    return;
                }

                if (!novoLogin.isEmpty()) {
                    usuarioEncontrado.setLogin(novoLogin);
                } else {
                    JOptionPane.showMessageDialog(null, "Login inválido! O login não pode ser vazio.");
                    return;
                }

                if (!novaSenha.isEmpty()) {
                    usuarioEncontrado.setSenha(novaSenha);
                } else {
                    JOptionPane.showMessageDialog(null, "Senha inválida! A senha não pode ser vazia.");
                    return;
                }

                if (gerenteButton.isSelected()) {
                    if (usuarioEncontrado instanceof Gerente) {
                        JOptionPane.showMessageDialog(null, "O usuário já é um Gerente.");
                    } else {
                        user.remove(usuarioEncontrado);
                        Gerente novoGerente = new Gerente(usuarioEncontrado.getNome(), usuarioEncontrado.getLogin(),
                                usuarioEncontrado.getSenha());
                        user.add(novoGerente);
                        JOptionPane.showMessageDialog(null, "Tipo de usuário alterado para Gerente.");
                    }
                } else if (garcomButton.isSelected()) {
                    if (usuarioEncontrado instanceof Garcom) {
                        JOptionPane.showMessageDialog(null, "O usuário já é um Garçom.");
                    } else {
                        user.remove(usuarioEncontrado);
                        Garcom novoGarcom = new Garcom(usuarioEncontrado.getNome(), usuarioEncontrado.getLogin(),
                                usuarioEncontrado.getSenha());
                        user.add(novoGarcom);
                        JOptionPane.showMessageDialog(null, "Tipo de usuário alterado para Garçom.");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Usuário não encontrado!");
        }
    }

    public List<Usuario> getUser() {
        return user;
    }
}
