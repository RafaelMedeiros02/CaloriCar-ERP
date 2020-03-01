
package br.com.Model;

import java.util.Date;


public class ModelVenda {
    
        //Informações dos Produtos
    String cod_barras;
    String descricao;
    double valorUnidade;
    int alerta;
    int qtde;
    
    //tabela de vendas
    int tItem;
    String tDescricao;
    String tCodBarras;
    double tValor;
    int tQtde;
    double tValorTotal;
    
    //informações caixa
    String usuario;
    String dataHora;
    String pdv;
    String data;
    String clienteRef;
    
    //valores
    double subTotal;
    double descontos;
    double totalapagar;
    double areceber;
    double troco;
    int codVenda;
    String cliente;
    String formaPagamento;
    
    //parcelas
    String clienteParcela;
    int refVenda;
    double valorParcela;
    Date dataParcela;
    String dataParcelaS;
    String status;
    double totalCompra;
    String cpf;
    String desc;
    
    boolean finaliza_venda;

    public boolean isFinaliza_venda() {
        return finaliza_venda;
    }

    public void setFinaliza_venda(boolean finaliza_venda) {
        this.finaliza_venda = finaliza_venda;
    }
    
    

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getClienteRef() {
        return clienteRef;
    }

    public void setClienteRef(String clienteRef) {
        this.clienteRef = clienteRef;
    }
    
    
    
    

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }
    
    

    public String getDataParcelaS() {
        return dataParcelaS;
    }

    public void setDataParcelaS(String dataParcelaS) {
        this.dataParcelaS = dataParcelaS;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    

    public String getClienteParcela() {
        return clienteParcela;
    }

    public void setClienteParcela(String clienteParcela) {
        this.clienteParcela = clienteParcela;
    }

    public int getRefVenda() {
        return refVenda;
    }

    public void setRefVenda(int refVenda) {
        this.refVenda = refVenda;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public Date getDataParcela() {
        return dataParcela;
    }

    public void setDataParcela(Date dataParcela) {
        this.dataParcela = dataParcela;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
 

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
    

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    

    public int getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(int codVenda) {
        this.codVenda = codVenda;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDescontos() {
        return descontos;
    }

    public void setDescontos(double descontos) {
        this.descontos = descontos;
    }

    public double getTotalapagar() {
        return totalapagar;
    }

    public void setTotalapagar(double totalapagar) {
        this.totalapagar = totalapagar;
    }

    public double getAreceber() {
        return areceber;
    }

    public void setAreceber(double areceber) {
        this.areceber = areceber;
    }

    public double getTroco() {
        return troco;
    }

    public void setTroco(double troco) {
        this.troco = troco;
    }
    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getPdv() {
        return pdv;
    }

    public void setPdv(String pdv) {
        this.pdv = pdv;
    }
    

    public int gettItem() {
        return tItem;
    }

    public void settItem(int tItem) {
        this.tItem = tItem;
    }

    public String gettDescricao() {
        return tDescricao;
    }

    public void settDescricao(String tDescricao) {
        this.tDescricao = tDescricao;
    }

    public String gettCodBarras() {
        return tCodBarras;
    }

    public void settCodBarras(String tCodBarras) {
        this.tCodBarras = tCodBarras;
    }

    public double gettValor() {
        return tValor;
    }

    public void settValor(double tValor) {
        this.tValor = tValor;
    }

    public int gettQtde() {
        return tQtde;
    }

    public void settQtde(int tQtde) {
        this.tQtde = tQtde;
    }

    public double gettValorTotal() {
        return tValorTotal;
    }

    public void settValorTotal(double tValorTotal) {
        this.tValorTotal = tValorTotal;
    }
    
    

    public int getAlerta() {
        return alerta;
    }

    public void setAlerta(int alerta) {
        this.alerta = alerta;
    }
    
    

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
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

    public double getValorUnidade() {
        return valorUnidade;
    }

    public void setValorUnidade(double valorUnidade) {
        this.valorUnidade = valorUnidade;
    }
    
    
    
}
