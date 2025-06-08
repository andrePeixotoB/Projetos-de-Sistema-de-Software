package service;

import model.Pedido;
import model.CupomDescontoEntrega;

public class MetodoDescontoValorPedido implements IMetodoDescontoTaxaEntrega {
    private final static double VALOR_FIXO_DESCONTO = 5.0;
    private static final double LIMITE_VALOR_PEDIDO_PARA_DESCONTO = 200.0;
    
    @Override
    public void calcularDesconto(Pedido pedido) {
        pedido.aplicarDesconto(new CupomDescontoEntrega("Desconto por valor total do pedido superior a ", VALOR_FIXO_DESCONTO));
    }

    @Override
    public boolean seAplica(Pedido pedido) {
        if(pedido.getValorPedido() > LIMITE_VALOR_PEDIDO_PARA_DESCONTO){
            return true;
        }
        return false;
    }
}

