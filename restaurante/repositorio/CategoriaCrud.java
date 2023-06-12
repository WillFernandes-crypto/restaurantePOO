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
            JOptionPane.showMessageDialog(null, "Categoria cadastrada com sucesso!");
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
        Categoria categoria = buscarCategoria(codCategoria);
        if (categoria != null) {
            String novoCodCategoria = JOptionPane.showInputDialog("Digite o novo codigo da categoria:");
            String novoTipoCategoria = JOptionPane.showInputDialog("Digite o novo tipo da categoria:");
            int codCateg = Integer.parseInt(novoCodCategoria);
            if (novoTipoCategoria != null && !novoTipoCategoria.isEmpty() && novoCodCategoria != null
                    && !novoCodCategoria.isEmpty()) {
                if (verificarExistencia(codCateg, novoTipoCategoria)) {
                    JOptionPane.showMessageDialog(null, "Já existe uma categoria com o mesmo código ou nome!");
                } else {
                    categoria.setCodCategoria(codCateg);
                    categoria.setTipoCategoria(novoTipoCategoria);
                    atualizarListaCategorias();
                    JOptionPane.showMessageDialog(null, "Categoria atualizada com sucesso!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Tipo de categoria inválido!");
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

    private boolean verificarExistencia(int codCategoria, String tipoCategoria) {
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
