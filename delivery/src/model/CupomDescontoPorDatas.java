package model;

import java.time.LocalDate;

public class CupomDescontoPorDatas {
	private LocalDate dataInicio;
	private LocalDate dataFinal;
	private double desconto;

	public CupomDescontoPorDatas(LocalDate dataInicio, LocalDate dataFinal, double desconto) {
		this.dataInicio = dataInicio;
		this.dataFinal = dataFinal;
		this.desconto = desconto;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	@Override
	public String toString() {
		return "CupomDescontoDatas{" +
				"dataFinal=" + dataFinal +
				", dataInicio=" + dataInicio +
				", desconto=" + desconto +
				'}';
	}
}