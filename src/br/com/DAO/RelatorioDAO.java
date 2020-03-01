
package br.com.DAO;

import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelControleVeiculos;
import br.com.Model.ModelRelatorios;
import br.com.Model.ModelVenda;
import br.com.View.ServicosRealizados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class RelatorioDAO {
    
    private Connection connection;
    
    public RelatorioDAO() {
    
            this.connection = new ConnectionFactory().getConnection();
           
    
    }
    
    
     public ArrayList<ModelRelatorios> maisVendidos() throws Exception {
        ArrayList<ModelRelatorios> listar = new ArrayList();
        try {
            String sql = "SELECT descricao, COUNT(descricao) AS Qtd FROM  tbl_vendas GROUP BY descricao HAVING COUNT(descricao) > 0 ORDER BY COUNT(descricao) DESC";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelRelatorios mR = new ModelRelatorios();
                mR.setDescricao(rs.getString("descricao"));
                mR.setQtde(rs.getInt("Qtd"));
                listar.add(mR);
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
