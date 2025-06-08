package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Pedido;
import service.IMetodoDescontoTaxaEntrega;
import service.MetodoDescontoDiaDaSemana;
import service.MetodoDescontoIntervaloDeData;
import service.MetodoDescontoTaxaPorBairro;
import service.MetodoDescontoTaxaTipoCliente;
import service.MetodoDescontoTipoItem;
import service.MetodoDescontoValorPedido;

public class CalculadoraDeDescontoService {
    private List<IMetodoDescontoTaxaEntrega> metodosDeDesconto;
    
    public CalculadoraDeDescontoService(){
        metodosDeDesconto = new ArrayList<>();
        metodosDeDesconto.add(new MetodoDescontoTaxaPorBairro());
	    metodosDeDesconto.add(new MetodoDescontoTaxaTipoCliente());
	    metodosDeDesconto.add(new MetodoDescontoTipoItem());
	    metodosDeDesconto.add(new MetodoDescontoValorPedido()); 
        metodosDeDesconto.add(new MetodoDescontoDiaDaSemana());
        metodosDeDesconto.add(new MetodoDescontoIntervaloDeData(LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 15), 15.0));
    }
        
    public void calcularDesconto(Pedido pedido){
        for(IMetodoDescontoTaxaEntrega metodosDescontoTaxaEntrega: metodosDeDesconto){
            if(pedido.getDescontoConcedidoTotal() < 10.0){
                if(metodosDescontoTaxaEntrega.seAplica(pedido)){
                    metodosDescontoTaxaEntrega.calcularDesconto(pedido);
                }
            }
        }
    }
}