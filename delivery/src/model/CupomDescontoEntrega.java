package model;


public class CupomDescontoEntrega {
    private String metodo;
    private double valor;

    public CupomDescontoEntrega(String nomeMetodo, double valorDesconto) {
        this.metodo = nomeMetodo;
        this.valor = valorDesconto;
    }

    public String getNomeMetodo() {
        return metodo;
    }

    public double getValorDesconto() {
        return valor;
    }

    @Override
    public String toString() {
        return "CupomDescontoEntrega{" + "nomeMetodo=" + metodo + ", valorDesconto=" + valor + '}';
    }

}