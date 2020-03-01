/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.DAO;

import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelDevolucao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael Medeiros
 */
public class DevolucaoDAO {
    
    
    private Connection connection;
    
    public DevolucaoDAO() {
    
           this.connection = new ConnectionFactory().getConnection();

    
    }
    
    
    
    public void gravaDevolucao(ModelDevolucao mD) {

        String sql = "INSERT INTO tbl_devolucao (cod_barras, descricao, qtde,"
                + " valor, motivo, data_venda, data_devolucao,usuario,nmr_cupom) values (?,?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mD.getCodBarras());
            stmt.setString(2, mD.getDescricao());
            stmt.setInt(3, mD.getQtde());
            stmt.setDouble(4, mD.getValor());
            stmt.setString(5, mD.getMotivo());
            stmt.setString(6, mD.getDataVenda());
            stmt.setString(7, mD.getDataDevolucao());
            stmt.setString(8, mD.getUsuario());
            stmt.setInt(9, mD.getCodCupom());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar devolução!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }
    
     public ArrayList<ModelDevolucao> listar(String inicio, String fim) throws Exception {
        ArrayList<ModelDevolucao> listar = new ArrayList();
   
        try {
            //String sql = "SELECT * FROM tbl_servicos_reg WHERE data_entrada BETWEEN '"+ inicio + "'AND'" + fim + "'";
            String sql = "SELECT * FROM tbl_devolucao WHERE STR_TO_DATE(data_devolucao, '%d/%m/%Y') BETWEEN STR_TO_DATE('"+inicio+"','%d/%m/%Y') AND STR_TO_DATE('"+fim+"','%d/%m/%Y')";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelDevolucao mD = new ModelDevolucao();
                mD.setDescricao(rs.getString("descricao"));
                mD.setMotivo(rs.getString("motivo"));
                mD.setQtde(rs.getInt("qtde"));
                mD.setValor(rs.getDouble("valor"));
                mD.setCodCupom(rs.getInt("nmr_cupom"));
                mD.setDataDevolucao(rs.getString("data_devolucao"));
                listar.add(mD);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listar;

    }
    
}
