package repositorio;

import dados.*;
import java.util.*;
import javax.swing.*;

public class ProdutoCrud {

    private List<Produto> prod;
    private CategoriaCrud categoriaCrud;

    public ProdutoCrud(CategoriaCrud categoriaCrud) {
        prod = new ArrayList<>();
        this.categoriaCrud = categoriaCrud;
    }

    public List<Produto> getProdutos() {
        return prod;
    }

    public ProdutoCrud() {
        this.prod = new ArrayList<>();
    }

    public int size() {
        return prod.size();
    }

    public Produto[] getProdutosArray() {
        return prod.toArray(new Produto[prod.size()]);
    }

    public void cadProduto(int codProduto, String nomeProduto, double precoProduto, int qtdProduto) {
        List<Categoria> categorias = categoriaCrud.getCategorias();
        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma categoria cadastrada!");
            return;
        }

        // Cria um array unidimensional de strings para as opções de categorias
        String[] opcoesCategorias = new String[categorias.size()];
        for (int i = 0; i < categorias.size(); i++) {
            opcoesCategorias[i] = categorias.get(i).getTipoCategoria();
        }

        // Exibe o JOptionPane com as opções de categorias
        String selectedCategoria = (String) JOptionPane.showInputDialog(
                null,
                "Selecione a categoria:",
                "Cadastro de Produto",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoesCategorias,
                opcoesCategorias[0]);

        if (selectedCategoria != null) {
            // Encontra a categoria selecionada
            Categoria categoriaSelecionada = null;
            for (Categoria categoria : categorias) {
                if (categoria.getTipoCategoria().equals(selectedCategoria)) {
                    categoriaSelecionada = categoria;
                    break;
                }
            }

            int codCategoria = categoriaSelecionada.getCodCategoria();

            // Verifica se já existe um produto com o mesmo código e mesma categoria
            if (buscaProd(codProduto, codCategoria)) {
                JOptionPane.showMessageDialog(null, "Já existe um produto com o mesmo código e mesma categoria!");
            } else {
                Produto novoProduto = new Produto(codProduto, codCategoria, nomeProduto, precoProduto, qtdProduto);
                prod.add(novoProduto);
            }
        }
    }

    public boolean buscaProd(int codProduto, int codCategoria) {
        for (Produto produto : prod) {
            if (produto.getCodProduto() == codProduto && produto.getCodCategoria() == codCategoria) {
                return true;
            }
        }
        return false;
    }

    public void listaProd() {
        StringBuilder lista = new StringBuilder("Lista de Produtos:\n");
        for (Produto produto : prod) {
            int codCategoria = produto.getCodCategoria();
            String tipoCategoria = obterTipoCategoria(codCategoria);
            lista.append("Codigo do Produto: ").append(produto.getCodProduto())
                    .append(" | Tipo de Categoria: ").append(tipoCategoria)
                    .append(" | Nome: ").append(produto.getNomeProduto())
                    .append(" | Preço: ").append(produto.getPrecoProduto())
                    .append(" | Quantidade: ").append(produto.getQtdProduto()).append("\n");
        }
        JOptionPane.showMessageDialog(null, lista.toString());
    }

    private String obterTipoCategoria(int codCategoria) {
        List<Categoria> categorias = categoriaCrud.getCategorias();
        for (Categoria categoria : categorias) {
            if (categoria.getCodCategoria() == codCategoria) {
                return categoria.getTipoCategoria();
            }
        }
        return "Categoria não encontrada";
    }

    public void delProd(int codProduto) {
        Produto delProd = null;
        for (Produto produto : prod) {
            if (produto.getCodProduto() == codProduto) {
                delProd = produto;
                break;
            }
        }
        if (delProd != null) {
            prod.remove(delProd);
            JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!");
        }
    }

    public void alteraProd(int codProduto) {
        Produto prodFound = null;
        for (Produto produto : prod) {
            if (produto.getCodProduto() == codProduto) {
                prodFound = produto;
                break;
            }
        }
        if (prodFound != null) {
            String codP = JOptionPane.showInputDialog("Novo codigo do produto: ");
            int newCodP = Integer.parseInt(codP);
            String newNome = JOptionPane.showInputDialog("Novo nome do produto: ");
            String priceP = JOptionPane.showInputDialog("Novo valor do produto: ");
            double newPriceP = Double.parseDouble(priceP.replace(",", "."));
            String nqp = JOptionPane.showInputDialog("Nova quantidade do produto: ");
            int newQtdProd = Integer.parseInt(nqp);

            // Obter a nova categoria selecionada
            List<Categoria> categorias = categoriaCrud.getCategorias();
            String[] opcoesCategorias = new String[categorias.size()];
            for (int i = 0; i < categorias.size(); i++) {
                opcoesCategorias[i] = categorias.get(i).getTipoCategoria();
            }
            String selectedCategoria = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecione a nova categoria:",
                    "Alteração de Produto",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opcoesCategorias,
                    opcoesCategorias[0]);

            if (newNome != null && !newNome.isEmpty() &&
                    priceP != null && !priceP.isEmpty() &&
                    nqp != null && !nqp.isEmpty() &&
                    selectedCategoria != null) {

                int newCodCategoria = -1;
                // Encontrar o código da nova categoria selecionada
                for (Categoria categoria : categorias) {
                    if (categoria.getTipoCategoria().equals(selectedCategoria)) {
                        newCodCategoria = categoria.getCodCategoria();
                        break;
                    }
                }

                // Verificar se já existe um produto com o novo código e nova categoria
                if (buscaProd(newCodP, newCodCategoria)) {
                    JOptionPane.showMessageDialog(null, "Já existe um produto com o mesmo código e mesma categoria!");
                } else {
                    prodFound.setCodProduto(newCodP);
                    prodFound.setNomeProduto(newNome);
                    prodFound.setPrecoProduto(newPriceP);
                    prodFound.setQtdProduto(newQtdProd);
                    prodFound.setCodCategoria(newCodCategoria);
                    JOptionPane.showMessageDialog(null, "Dados do produto foram alterados com sucesso!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Algum dado foi inserido incorretamente...");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!");
        }
    }
}
