package repositorio;

public interface IProduto {
    void cadProduto(int codProduto, String nomeProduto, double precoProduto, int qtdProduto);
    boolean buscaProd(int codProduto, int codCategoria);
    void listaProd();
    void delProd(int codProduto);
    void alteraProd(int codProduto);
}
