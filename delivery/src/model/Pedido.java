package model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private double custoEntregaBase = 10;
    private final LocalDate dataPedido;
    private List<Item> listaItens;
    private Cliente dadosCliente;
    private List<CupomDescontoEntrega> cupons;
    private CupomDesconto cupomDescontoAplicado;
    
    public Pedido (LocalDate data, Cliente dadosCliente){
        this.dadosCliente = dadosCliente;
        this.dataPedido = data;
        this.listaItens = new ArrayList<>();
        this.cupons = new ArrayList<>();
    }
    public void adicionarItem(Item item){
        listaItens.add(item);
    }
    
    public double getValorPedido() {
        double valorTotalItens = 0.0;
        for (Item item : this.listaItens) {
            valorTotalItens += item.getValorTotal();
        }
        
        double valorTotalComEntrega = valorTotalItens + this.custoEntregaBase;

        if (this.cupomDescontoAplicado != null) {
            return valorTotalComEntrega * (1 - this.cupomDescontoAplicado.getPercentualDesconto() / 100);
        }
        return valorTotalComEntrega;
    }
    
    public Cliente getCliente(){
        return this.dadosCliente;
    }
    
    public List<Item> getItens(){
        return this.listaItens;
    }
    
    public double getTaxaEntrega(){
        return this.custoEntregaBase;
        
    }

    public LocalDate getData() {
        return dataPedido;
    }
    
    public void aplicarDesconto(CupomDescontoEntrega cupomInfo) {
        if (cupomInfo == null || cupomInfo.getValorDesconto() <= 0) {
            return;
        }

        double descontoMaximoPossivel = this.custoEntregaBase; 
        double descontoJaConcedido = getDescontoConcedidoTotal(); 
        double descontoAindaDisponivel = 10.0 - descontoJaConcedido; 

        if (descontoAindaDisponivel <= 0) {
            return; 
        }

        double valorDescontoCupom = cupomInfo.getValorDesconto();
        double valorRealAplicado = Math.min(valorDescontoCupom, descontoAindaDisponivel);
        valorRealAplicado = Math.min(valorRealAplicado, this.custoEntregaBase); 

        if (valorRealAplicado > 0) {
            this.custoEntregaBase -= valorRealAplicado;
            CupomDescontoEntrega cupomEfetivo = new CupomDescontoEntrega(cupomInfo.getNomeMetodo(), valorRealAplicado);
            if (valorRealAplicado < valorDescontoCupom) {
                 cupomEfetivo = new CupomDescontoEntrega(cupomInfo.getNomeMetodo() + " (aplicado parcialmente)", valorRealAplicado);
            }
            this.cupons.add(cupomEfetivo);
        }
    }

    public void aplicarCupomDesconto(CupomDesconto novoCupom) {
        if (novoCupom == null) {
            return;
        }

        if (this.cupomDescontoAplicado == null || novoCupom.getPercentualDesconto() > this.cupomDescontoAplicado.getPercentualDesconto()) {
            this.cupomDescontoAplicado = novoCupom;
        }
    }
    
    public double getDescontoConcedidoTotal() {
        double descontoTotal = 0;
        for(CupomDescontoEntrega cupom : this.cupons){
            descontoTotal += cupom.getValorDesconto();
        }
        // Garante que o desconto concedido não exceda o limite original de 10.0
        descontoTotal = Math.min(descontoTotal, 10.0);

        if (this.cupomDescontoAplicado != null) {
            double valorTotalItens = 0.0;
            for (Item item : this.listaItens) {
                valorTotalItens += item.getValorTotal();
            }
            double valorTotalComEntrega = valorTotalItens + this.custoEntregaBase;
            descontoTotal += valorTotalComEntrega * (this.cupomDescontoAplicado.getPercentualDesconto() / 100);
        }
        return descontoTotal;
    }
    
    public List<CupomDescontoEntrega> getCuponsDescontoEntrega(){
        return this.cupons;
    }

    public CupomDesconto getCupomDescontoAplicado() {
        return this.cupomDescontoAplicado;
    }

    @Override
    public String toString() {
        return String.format("Pedido [Data: %s, Cliente: %s, Itens: %d, Valor Itens: %.2f, Taxa Entrega Atual: %.2f, Desconto Concedido: %.2f, Cupom Desconto: %s]",
                dataPedido.toString(),
                dadosCliente.getNome(),
                listaItens.size(),
                getValorPedido(),
                custoEntregaBase,
                getDescontoConcedidoTotal(),
                (cupomDescontoAplicado != null ? cupomDescontoAplicado.getCodigo() : "Nenhum"));
    }

}

