package service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import model.Pedido;
import model.CupomDescontoEntrega;

public class MetodoDescontoDiaDaSemana implements IMetodoDescontoTaxaEntrega {

    public MetodoDescontoDiaDaSemana() {
    }

    @Override
    public void calcularDesconto(Pedido pedidoCorrente) {
        LocalDate dataDoPedido = pedidoCorrente.getData();
        DayOfWeek diaDaSemana = dataDoPedido.getDayOfWeek();
        double taxaEntregaAtual = pedidoCorrente.getTaxaEntrega();

        double valorDesconto = 0.0;
        String motivoDesconto = null;

        switch (diaDaSemana) {
            case TUESDAY:
                // Desconto total da taxa na terça-feira
                valorDesconto = taxaEntregaAtual;
                motivoDesconto = "Promoção Terça Total";
                break;
            case THURSDAY:
                // Desconto de 60% na quinta-feira
                valorDesconto = taxaEntregaAtual * 0.60;
                motivoDesconto = "Promoção Quinta 60%";
                break;
            default:
                // Nenhum desconto nos outros dias
                break;
        }

        if (valorDesconto > 0 && motivoDesconto != null) {
            CupomDescontoEntrega cupomDiaSemana = new CupomDescontoEntrega(motivoDesconto, valorDesconto);
            pedidoCorrente.aplicarDesconto(cupomDiaSemana);
        }
    }

    @Override
    public boolean seAplica(Pedido pedidoCorrente) {
        DayOfWeek diaDaCompra = pedidoCorrente.getData().getDayOfWeek();

        boolean aplicavel = (diaDaCompra == DayOfWeek.TUESDAY || diaDaCompra == DayOfWeek.THURSDAY);
        return aplicavel;
    }
}


