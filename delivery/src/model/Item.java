package model;


public class Item {
    private String nomeProduto;
    private int quantidade;
    private double valorUnitario;
    private String tipo;

    public Item(String nomeProduto, int quantidade, double valorUnitario, String tipo) {
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.tipo = tipo;
    }

    public String getNome() {
        return nomeProduto;
    }
    
    public double getValorTotal() {
        double total = this.quantidade * this.valorUnitario;
        return total;
    }
    
    public String getTipo() {
        return tipo;
    }

     @Override
    public String toString() {
        return "Item{" +
               "nome=\'" + nomeProduto + "\'" +
               ", quantidade=" + quantidade +
               ", valorUnitario=" + valorUnitario +
               ", tipo=\'" + tipo + "\'" +
               '}';
    }
}
