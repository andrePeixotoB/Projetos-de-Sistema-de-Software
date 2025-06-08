package service;

import java.time.LocalDate;
import model.Pedido;
import model.CupomDescontoEntrega;

public class MetodoDescontoIntervaloDeData implements IMetodoDescontoTaxaEntrega{
	private LocalDate dataI;
	private LocalDate dataF;
	private double desconto;

	public MetodoDescontoIntervaloDeData(LocalDate dataI, LocalDate dataF, double desconto) {
		this.dataI = dataI;
		this.dataF = dataF;
		this.desconto = desconto;
	}

	public LocalDate getdataF() {
		return dataF;
	}

	public void setdataF(LocalDate dataF) {
		this.dataF = dataF;
	}

	public LocalDate getdataI() {
		return dataI;
	}

	public void setdataI(LocalDate dataI) {
		this.dataI = dataI;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	@Override
    public boolean seAplica(Pedido pedidoProcessado) {
        LocalDate dataPedidoAtual = pedidoProcessado.getData();
        boolean dentroDoIntervalo = !dataPedidoAtual.isBefore(this.dataI) && !dataPedidoAtual.isAfter(this.dataF);
        return dentroDoIntervalo;
    }

	  @Override
    public void calcularDesconto(Pedido pedidoProcessado) {
        if (seAplica(pedidoProcessado)) {
            double taxaOriginal = 10.0;
            // Calcula o valor do desconto baseado no percentual sobre a taxa ORIGINAL
            double valorDescontoCalculado = taxaOriginal * (this.desconto / 100.0);

            // Garante que o desconto não seja maior que a taxa atual restante
            valorDescontoCalculado = Math.min(valorDescontoCalculado, pedidoProcessado.getTaxaEntrega());

            if (valorDescontoCalculado > 0) {
                String nomeCupom = String.format("Desconto Promocional (%.1f%%) - Válido de %s a %s",
                                               this.desconto, this.dataI, this.dataF);
                CupomDescontoEntrega cupomPeriodo = new CupomDescontoEntrega(nomeCupom, valorDescontoCalculado);
                pedidoProcessado.aplicarDesconto(cupomPeriodo);
            }
        }
    }

	@Override
	public String toString() {
		return "MetodoDescontoIntervaloDeData{" +
				"dataF=" + dataF +
				", dataI=" + dataI +
				", desconto=" + desconto +
				'}' ;
	}
}

