
package br.com.Model;


public class ModelEntrada {
    
    
    int cod_entrada;
    String descricao;
    String cod_barras;
    String fornecedor;
    String data_entrada;
    String ultima_entrada;
    int qtde_atual;
    int qtde_entrada;
    double valor_entrada;
    String statusEstoque;

    public int getCod_entrada() {
        return cod_entrada;
    }

    public void setCod_entrada(int cod_entrada) {
        this.cod_entrada = cod_entrada;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCod_barras() {
        return cod_barras;
    }

    public void setCod_barras(String cod_barras) {
        this.cod_barras = cod_barras;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getData_entrada() {
        return data_entrada;
    }

    public void setData_entrada(String data_entrada) {
        this.data_entrada = data_entrada;
    }

    public String getUltima_entrada() {
        return ultima_entrada;
    }

    public void setUltima_entrada(String ultima_entrada) {
        this.ultima_entrada = ultima_entrada;
    }

    public int getQtde_atual() {
        return qtde_atual;
    }

    public void setQtde_atual(int qtde_atual) {
        this.qtde_atual = qtde_atual;
    }

    public int getQtde_entrada() {
        return qtde_entrada;
    }

    public void setQtde_entrada(int qtde_entrada) {
        this.qtde_entrada = qtde_entrada;
    }

    public double getValor_entrada() {
        return valor_entrada;
    }

    public void setValor_entrada(double valor_entrada) {
        this.valor_entrada = valor_entrada;
    }
   
    
}
