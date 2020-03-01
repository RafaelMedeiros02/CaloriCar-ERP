
package br.com.DAO;

import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelCompras;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class ComprasDAO {
    
        private Connection connection;
        
        public ComprasDAO () {
        
                    this.connection = new ConnectionFactory().getConnection();
        
        }
        
          public void cadastrarCompra(ModelCompras mCO) {

        String sql = "INSERT INTO tbl_compras (descricao, usuario, data_compra) values (?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mCO.getDescricao());
            stmt.setString(2, mCO.getUsuario());
            stmt.setString(3, mCO.getData_compra());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar , Verifique os dados e tente novamente!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }
          
            public void excluirCompra(ModelCompras mCO) {

        String sql = "DELETE FROM tbl_compras where cod_compra=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mCO.getCod_compra());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }
            
             public ArrayList<ModelCompras> pesquisaListaCompras(String descricao) throws Exception {
        ArrayList<ModelCompras> listarCompras = new ArrayList();
        try {
            String sql = "SELECT cod_compra, descricao, usuario,data_compra FROM tbl_compras WHERE descricao LIKE '%" + descricao + "%'ORDER BY cod_compra ASC";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelCompras mCO = new ModelCompras();
                mCO.setCod_compra(rs.getInt("cod_compra"));
                mCO.setDescricao(rs.getString("descricao"));
                mCO.setUsuario(rs.getString("usuario"));
                mCO.setData_compra(rs.getString("data_compra"));
                listarCompras.add(mCO);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listarCompras;

    }
    
}
