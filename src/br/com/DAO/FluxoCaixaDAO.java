
package br.com.DAO;

import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelMovCaixa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class FluxoCaixaDAO {
    
    private Connection connection;
    
    public FluxoCaixaDAO() {
    
    
    this.connection =  new ConnectionFactory().getConnection();

    }
    
      public void registraCaixaVenda(ModelMovCaixa mMC) {

        String sql = "INSERT INTO tbl_movcax (data_hora, razao, valor_entrada, data) values (?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mMC.getDataHora());
            stmt.setString(2, "VENDA PDV");
            stmt.setDouble(3, mMC.getValor_entrada());
            stmt.setString(4, mMC.getData());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }
    
         
            
              public void registraCaixaOficina(ModelMovCaixa mMC) {

        String sql = "INSERT INTO tbl_movcax (data_hora, razao, valor_entrada,data) values (?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mMC.getDataHora());
            stmt.setString(2, "SERVICOS OFICINA");
            stmt.setDouble(3, mMC.getValor_entrada());
            stmt.setString(4, mMC.getData());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }
              
               
                 
                    public void registraCaixaParcelaPaga(ModelMovCaixa mMC) {

        String sql = "INSERT INTO tbl_movcax (data_hora, razao, valor_entrada, data) values (?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mMC.getDataHora());
            stmt.setString(2, "PARCELA PAGA");
            stmt.setDouble(3, mMC.getValor_entrada());
            stmt.setString(4, mMC.getData());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }
                    
        public ArrayList<ModelMovCaixa> exibirFluxoCaixa(String data_inicio, String data_fim) throws Exception {
        ArrayList<ModelMovCaixa> listar = new ArrayList();
        try {
            String sql = "SELECT * FROM tbl_movcax WHERE STR_TO_DATE(data, '%d/%m/%Y') BETWEEN STR_TO_DATE('"+data_inicio+"','%d/%m/%Y') AND STR_TO_DATE('"+data_fim+"','%d/%m/%Y')";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelMovCaixa mMC = new ModelMovCaixa();
                mMC.setDataHora(rs.getString("data_hora"));
                mMC.setRazao(rs.getString("razao"));
                mMC.setValor_entrada(rs.getDouble("valor_entrada"));
                listar.add(mMC);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao exibir caixa", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listar;

    }
}
