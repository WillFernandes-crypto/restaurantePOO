package repositorio;

import dados.Categoria;

public interface ICategoria {
    void cadastrarCategoria(int codCategoria, String tipoCategoria);
    void listarCategorias();
    void alteraCateg(int codCategoria);
    void excluiCategoria(int codCategoria);
    void atualizarListaCategorias();
    boolean verificarExistencia(int codCategoria, String tipoCategoria);
    Categoria buscarCategoria(int codCategoria);
}