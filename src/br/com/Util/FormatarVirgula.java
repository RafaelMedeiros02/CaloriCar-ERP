
package br.com.Util;


public class FormatarVirgula {
    
    
    /**
     * Converte virgula para ponto
     * @param pString
     * @return double
     */
    
    public double converterVirgulaParaPonto(String pString) {
    
        String retorno = new String();
        int tamanhoString = pString.length();
        for(int i = 0; i < tamanhoString; i++) {
        if(pString.charAt(i) == ',') {
        retorno += '.';
        } else {
        retorno += pString.charAt(i);
        }
        }
     return Double.parseDouble(retorno);
    }
}
