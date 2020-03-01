
package br.com.Model;

/**
 *
 * @author Rafael Medeiros
 */
public class ModelMovCaixa {
    
    String dataHora;
    String razao;
    double valor_entrada;
    double valor_saida;
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    
    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public double getValor_entrada() {
        return valor_entrada;
    }

    public void setValor_entrada(double valor_entrada) {
        this.valor_entrada = valor_entrada;
    }

    public double getValor_saida() {
        return valor_saida;
    }

    public void setValor_saida(double valor_saida) {
        this.valor_saida = valor_saida;
    }
    
    
    
}
