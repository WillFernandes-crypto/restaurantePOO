package dados;

import java.util.*;

public class Pedido {
    private int codPedido;
    private List<Produto> itens;
    private double valorPago;

    public Pedido(int codPedido) {
        this.codPedido = codPedido;
        this.itens = new ArrayList<>();
    }

    public int getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(int codPedido) {
        this.codPedido = codPedido;
    }

    public List<Produto> getItens() {
        return itens;
    }

    public void setItens(List<Produto> itens) {
        this.itens = itens;
    }

    public double getTotalPedido() {
        double totalPedido = 0.0;
        for (Produto item : itens) {
            totalPedido += item.getPrecoProduto() * item.getQtdProduto();
        }
        return totalPedido;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }
}