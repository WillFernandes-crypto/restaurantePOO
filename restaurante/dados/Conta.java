package dados;

import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Conta {

    public void emitirNotaFiscal(Pedido pedido) {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido: ").append(pedido.getCodPedido()).append("\n\n");

        List<Produto> itens = pedido.getItens();
        for (Produto item : itens) {
            sb.append(item.getNomeProduto()).append(" - ").append(item.getQtdProduto()).append(" unidade(s)\n");
        }

        sb.append("\nTotal: R$ ").append(pedido.getTotalPedido()).append("\n");
        sb.append("Valor Pago: R$ ").append(pedido.getValorPago()).append("\n");
        sb.append("Troco: R$ ").append(pedido.getValorPago() - pedido.getTotalPedido()).append("\n");

        String nomeArquivo = "NotaFiscal_Mesa" + pedido.getCodPedido() + ".txt";
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write(sb.toString());
            JOptionPane.showMessageDialog(null, "Nota fiscal emitida!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao emitir nota fiscal: " + e.getMessage());
        }
    }
}
