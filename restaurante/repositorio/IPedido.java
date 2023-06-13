package repositorio;

public interface IPedido {
    void anotarPedido(int codPedido);
    void finalizarPedido(int codPedido, double valorPago);
    void listarPedido(int codPedido);
    void listarTodosPedidos();
}
