package repositorio;

import dados.*;
import javax.swing.*;
import java.awt.GridLayout;
import java.util.*;

public class CategoriaCrud {

    private List<Categoria> categorias;
    private JPanel categoriaPanel;

    public CategoriaCrud() {
        categorias = new ArrayList<>();
        categoriaPanel = new JPanel(new GridLayout(0, 1));
    }

    public void cadastrarCategoria(int codCategoria, String tipoCategoria) {
        if (verificarExistencia(codCategoria, tipoCategoria)) {
            JOptionPane.showMessageDialog(null, "Já existe uma categoria com o mesmo código ou nome!");
        } else {
            Categoria novaCategoria = new Categoria(codCategoria, tipoCategoria);
            categorias.add(novaCategoria);
            atualizarListaCategorias();
        }
    }

    public void listarCategorias() {
        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma categoria cadastrada!");
        } else {
            JOptionPane.showMessageDialog(null, categoriaPanel);
        }
    }

    public void alteraCateg(int codCategoria) {

        Iterator<Categoria> iterator = categorias.iterator();
        Categoria categEncontrada = null;
        while (iterator.hasNext()) {
            Categoria categoria = iterator.next();
            if (Integer.valueOf(categoria.getCodCategoria()).equals(codCategoria)) {
                categEncontrada = categoria;
                break;
            }
        }

        if (categEncontrada != null) {

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Novo codigo da categoria:"));
            JTextField codCategField = new JTextField(categEncontrada.getCodCategoria());
            panel.add(codCategField);
            panel.add(new JLabel("Novo tipo da categoria:"));
            JTextField tipoCategField = new JTextField(categEncontrada.getTipoCategoria());
            panel.add(tipoCategField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Alterar credenciais",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String novoCodCateg = codCategField.getText();
                String novoTipoCateg = tipoCategField.getText();
                int newCodCateg = Integer.parseInt(novoCodCateg);

                if (novoCodCateg != null && !novoCodCateg.isEmpty()) {
                    categEncontrada.setCodCategoria(newCodCateg);
                } else {
                    JOptionPane.showMessageDialog(null, "O codigo da categoria não pode ser vazio.");
                    return;
                }

                if (novoTipoCateg != null && !novoTipoCateg.isEmpty()) {
                    categEncontrada.setTipoCategoria(novoTipoCateg);
                } else {
                    JOptionPane.showMessageDialog(null, "O tipo de categoria não pode ser vazio.");
                    return;
                }

                if (verificarExistencia(newCodCateg, novoTipoCateg)) {
                    JOptionPane.showMessageDialog(null, "Já existe uma categoria com o mesmo código ou nome!");
                } else {
                    categEncontrada.setCodCategoria(newCodCateg);
                    categEncontrada.setTipoCategoria(novoTipoCateg);
                    atualizarListaCategorias();
                    JOptionPane.showMessageDialog(null, "Categoria atualizada com sucesso!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Categoria não encontrada!");
        }
    }

    public void excluiCategoria(int codCategoria) {
        Categoria categoria = buscarCategoria(codCategoria);
        if (categoria != null) {
            categorias.remove(categoria);
            atualizarListaCategorias();
            JOptionPane.showMessageDialog(null, "Categoria excluída com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Categoria não encontrada!");
        }
    }

    private void atualizarListaCategorias() {
        categoriaPanel.removeAll();
        for (Categoria categoria : categorias) {
            JLabel labelCategoria = new JLabel(
                    categoria.getTipoCategoria() + " | Código: " + categoria.getCodCategoria());
            categoriaPanel.add(labelCategoria);
        }
        categoriaPanel.revalidate();
    }

    public boolean verificarExistencia(int codCategoria, String tipoCategoria) {
        for (Categoria categoria : categorias) {
            if (categoria.getCodCategoria() == codCategoria
                    || categoria.getTipoCategoria().equalsIgnoreCase(tipoCategoria)) {
                return true;
            }
        }
        return false;
    }

    private Categoria buscarCategoria(int codCategoria) {
        for (Categoria categoria : categorias) {
            if (categoria.getCodCategoria() == codCategoria) {
                return categoria;
            }
        }
        return null;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }
}
