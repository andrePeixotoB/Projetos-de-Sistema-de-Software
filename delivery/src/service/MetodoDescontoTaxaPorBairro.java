package service;

import java.util.HashMap;
import java.util.Map;
import model.Pedido;
import model.CupomDescontoEntrega;

public class MetodoDescontoTaxaPorBairro implements IMetodoDescontoTaxaEntrega {
    private final Map<String, Double> mapaDescontosBairro;

    public MetodoDescontoTaxaPorBairro(){
        this.mapaDescontosBairro = Map.of(
            "Centro", 2.00,
            "Bela Vista", 3.00,
            "Cidade Maravilhosa", 1.50
        );
    }

    @Override
    public void calcularDesconto(Pedido pedidoAtual) {
        String bairroCliente = pedidoAtual.getCliente().getBairro();
        Double valorDesconto = this.mapaDescontosBairro.get(bairroCliente);

        if (valorDesconto != null && valorDesconto > 0) {
            String nomeCupom = "Desconto por bairro: " + bairroCliente;
            CupomDescontoEntrega cupom = new CupomDescontoEntrega(nomeCupom, valorDesconto);
            pedidoAtual.aplicarDesconto(cupom);
        }
    }

    @Override
    public boolean seAplica(Pedido pedidoAtual){
        String bairroDoCliente = pedidoAtual.getCliente().getBairro();
        boolean aplicavel = this.mapaDescontosBairro.containsKey(bairroDoCliente);
        return aplicavel;
    }
}


