/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Util;

/**
 *
 * @author Rafael Medeiros
 */
public class LimitaString {
    
    
        public String limitaString(String texto) {
        if (texto.length() <= 40) {
        return texto;
        }
        else {
        return texto.substring(0,40);
        }
    
    }
    
}
