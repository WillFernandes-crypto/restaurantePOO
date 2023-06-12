package dados;

import java.util.*;

public class Pedido {
    private int codPedido;
    private List<Produto> itens;
    private boolean ativo;
    private double valorPago;

    public Pedido(int codPedido) {
        this.codPedido = codPedido;
        this.itens = new ArrayList<>();
        this.ativo = true;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
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