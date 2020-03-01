
package br.com.Model;

/**
 *
 * @author Rafael Medeiros
 */
public class ModelOrcamento {
    
    
    int cod_orcamento;
    String cliente;
    String telefone;
    String celular;
    double valor_pecas;
    double valor_total;
    String cod_barras;
    String descricao;
    double valor_unidade;
    int qtde;
    double valorPPeca;
    String dataOrcamento;
    String usuario;

    public String getDataOrcamento() {
        return dataOrcamento;
    }

    public void setDataOrcamento(String dataOrcamento) {
        this.dataOrcamento = dataOrcamento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    

    public int getCod_orcamento() {
        return cod_orcamento;
    }

    public void setCod_orcamento(int cod_orcamento) {
        this.cod_orcamento = cod_orcamento;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

   
    

    public double getValor_pecas() {
        return valor_pecas;
    }

    public void setValor_pecas(double valor_pecas) {
        this.valor_pecas = valor_pecas;
    }



    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
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

    public double getValor_unidade() {
        return valor_unidade;
    }

    public void setValor_unidade(double valor_unidade) {
        this.valor_unidade = valor_unidade;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public double getValorPPeca() {
        return valorPPeca;
    }

    public void setValorPPeca(double valorPPeca) {
        this.valorPPeca = valorPPeca;
    }
    
    
}
