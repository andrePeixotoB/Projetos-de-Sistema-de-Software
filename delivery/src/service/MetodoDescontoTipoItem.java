package service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import model.Pedido;
import model.Item;
import model.CupomDescontoEntrega;

public class MetodoDescontoTipoItem implements IMetodoDescontoTaxaEntrega {
    private final Map<String, Double> regrasDescontoPorCategoria;

    public MetodoDescontoTipoItem(){
        this.regrasDescontoPorCategoria = Map.of(
            "Alimentação", 5.00,
            "Educação", 2.00,
            "Lazer", 1.50
        );
    }

    @Override
    public void calcularDesconto(Pedido pedidoAtual) {
        // Obtém a lista de itens
        List<Item> itensDoPedido = pedidoAtual.getItens();

        // Itera sobre os itens usando um for
        for (Item itemEspecifico : itensDoPedido) {
            String categoriaDoItem = itemEspecifico.getTipo();
            // Verifica se existe uma regra de desconto para a categoria do item
            if (this.regrasDescontoPorCategoria.containsKey(categoriaDoItem)) {
                Double valorDescontoItem = this.regrasDescontoPorCategoria.get(categoriaDoItem);
                // Garante que o valor do desconto é válido
                if (valorDescontoItem != null && valorDescontoItem > 0) {
                    String descricaoCupom = String.format("Desconto por item (%s): %s",
                                                        categoriaDoItem, itemEspecifico.getNome());
                    CupomDescontoEntrega cupomGerado = new CupomDescontoEntrega(descricaoCupom, valorDescontoItem);
                    pedidoAtual.aplicarDesconto(cupomGerado);
                }
            }
        }
    }

    @Override
    public boolean seAplica(Pedido pedidoAtual) {
        List<Item> itensVerificar = pedidoAtual.getItens();

        for (Item item : itensVerificar) {
            String tipoItemAtual = item.getTipo();
            if (this.regrasDescontoPorCategoria.containsKey(tipoItemAtual)) {
                return true;
            }
        }
        return false;
    }
}


