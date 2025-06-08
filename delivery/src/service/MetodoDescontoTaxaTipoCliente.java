package service;

import java.util.HashMap;
import java.util.Map;
import model.Pedido;
import model.CupomDescontoEntrega;

public class MetodoDescontoTaxaTipoCliente implements IMetodoDescontoTaxaEntrega {
    private final Map<String, Double> tabelaDescontosPorCategoriaCliente;

    public MetodoDescontoTaxaTipoCliente(){
        this.tabelaDescontosPorCategoriaCliente = Map.of(
            "Bronze", 1.00,
            "Prata", 2.00,
            "Ouro", 3.00
        );
    }

    @Override
    public void calcularDesconto(Pedido pedidoEmProcesso) {
        String categoriaCliente = pedidoEmProcesso.getCliente().getTipo();
        Double descontoAplicavel = this.tabelaDescontosPorCategoriaCliente.get(categoriaCliente);

        // Verifica se a categoria existe e o desconto é válido
        if (descontoAplicavel != null && descontoAplicavel > 0) {
            String descricaoCupom = "Benefício por categoria de cliente: " + categoriaCliente;
            CupomDescontoEntrega cupomGerado = new CupomDescontoEntrega(descricaoCupom, descontoAplicavel);
            pedidoEmProcesso.aplicarDesconto(cupomGerado);
        }
    }

    @Override
    public boolean seAplica(Pedido pedidoEmProcesso) {
        String tipoCliente = pedidoEmProcesso.getCliente().getTipo();
        boolean elegivel = this.tabelaDescontosPorCategoriaCliente.containsKey(tipoCliente);
        return elegivel;
    }
}


