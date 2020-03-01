
package br.com.Model;

public class ModelCompras {
    
    int cod_compra;
    String descricao;
    double valorAproximado;
    String data_compra;
    String usuario;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    

    public int getCod_compra() {
        return cod_compra;
    }

    public void setCod_compra(int cod_compra) {
        this.cod_compra = cod_compra;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorAproximado() {
        return valorAproximado;
    }

    public void setValorAproximado(double valorAproximado) {
        this.valorAproximado = valorAproximado;
    }

    public String getData_compra() {
        return data_compra;
    }

    public void setData_compra(String data_compra) {
        this.data_compra = data_compra;
    }
    
    
    
    
}
