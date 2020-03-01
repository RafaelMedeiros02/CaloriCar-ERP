
package br.com.Model;


public class ModelEstoque {
    
    String cod_barras;
    String descricao;
    String categoria;
    String fornecedor;
    double preco_custo;
    double preco_venda;
    double lucro_unidade;
    int quantidade;
    int alerta_estoque;
    String status_estoque;

    public String getStatus_estoque() {
        return status_estoque;
    }

    public void setStatus_estoque(String status_estoque) {
        this.status_estoque = status_estoque;
    }
    

    public int getAlerta_estoque() {
        return alerta_estoque;
    }

    public void setAlerta_estoque(int alerta_estoque) {
        this.alerta_estoque = alerta_estoque;
    }
    

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    

    public String getCod_barras() {
        return cod_barras;
    }

    public void setCod_barras(String cod_barras) {
        this.cod_barras = cod_barras;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public double getPreco_custo() {
        return preco_custo;
    }

    public void setPreco_custo(double preco_custo) {
        this.preco_custo = preco_custo;
    }

    public double getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(double preco_venda) {
        this.preco_venda = preco_venda;
    }

    public double getLucro_unidade() {
        return lucro_unidade;
    }

    public void setLucro_unidade(double lucro_unidade) {
        this.lucro_unidade = lucro_unidade;
    }
    
    
}
