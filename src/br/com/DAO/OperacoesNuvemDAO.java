
package br.com.DAO;

import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelControleVeiculos;
import br.com.Model.ModelEstoque;
import br.com.Nuvem.ConnectionFactoryNuvem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class OperacoesNuvemDAO {
    
    
    private Connection connection;
    
    
    public OperacoesNuvemDAO () {
    
            this.connection = new ConnectionFactoryNuvem().getConnection();
           
    
    }
    
 
      
        public void limpaTabela() {

        String sql = "TRUNCATE TABLE tbl_estoque;";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }
        
          public void sobeTabela(ModelEstoque mE) {

        String sql = "INSERT INTO tbl_estoque (cod_barras, descricao, categoria, fornecedor, preco_custo, preco_venda, lucro_unidade, quantidade,status_peca, alerta_qtde) values (?,?,?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mE.getCod_barras());
            stmt.setString(2, mE.getDescricao());
            stmt.setString(3, mE.getCategoria());
            stmt.setString(4, mE.getFornecedor());
            stmt.setDouble(5, mE.getPreco_custo());
            stmt.setDouble(6, mE.getPreco_venda());
            stmt.setDouble(7, mE.getLucro_unidade());
            stmt.setInt(8, mE.getQuantidade());
            stmt.setString(9, mE.getStatus_estoque());
            stmt.setInt(10, mE.getAlerta_estoque());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto, verifique se já não existe um cadastro referente a esse código de barras!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }

          public ArrayList<ModelEstoque> pesquisarEstoque_Alterar(String descricao) throws Exception {
        ArrayList<ModelEstoque> listarEstoque = new ArrayList();
        try {
            String sql = "SELECT cod_barras, descricao,quantidade,preco_venda FROM tbl_estoque WHERE descricao LIKE '%" + descricao + "%'ORDER BY descricao ASC";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelEstoque mE = new ModelEstoque();
                mE.setCod_barras(rs.getString("cod_barras"));
                mE.setDescricao(rs.getString("descricao"));
                mE.setQuantidade(rs.getInt("quantidade"));
                mE.setPreco_venda(rs.getDouble("preco_venda"));
                listarEstoque.add(mE);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar estoque!", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listarEstoque;

    }
          

    
}
