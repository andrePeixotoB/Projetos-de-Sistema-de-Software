package model;


public class CupomDesconto {
    private String codigo;
    private double percentualDesconto;

    public CupomDesconto(String codigo, double percentualDesconto) {
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    @Override
    public String toString() {
        return "CupomDesconto{" +
               "codigo='" + codigo + '\'' +
               ", percentualDesconto=" + percentualDesconto +
               '}';
    }
}

