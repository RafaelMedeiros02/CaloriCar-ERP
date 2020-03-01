package br.com.DAO;

import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelOrcamento;
import br.com.Nuvem.ConnectionFactoryNuvem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class OrcamentoDAO {

    private Connection connection;

    public OrcamentoDAO() {

        this.connection = new ConnectionFactoryNuvem().getConnection();

    }

    public void buscaCod(ModelOrcamento mO) {

        String sql = "SELECT * FROM tbl_orcamentos ORDER BY cod_orcamento DESC LIMIT 1";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                mO.setCod_orcamento(rs.getInt("cod_orcamento"));
            } else {


            }
            stmt.close();
            rs.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

    public void salvaOrcamento(ModelOrcamento mO) {

        String sql = "INSERT INTO tbl_orcamentos (cod_orcamento, cliente, telefone, celular, total_final, cod_peca,descricao,valorUnidade,qtde,totalPPeca,data_orcamento,usuario) values "
                + "(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mO.getCod_orcamento());
            stmt.setString(2, mO.getCliente());
            stmt.setString(3, mO.getTelefone());
            stmt.setString(4, mO.getCelular());
            stmt.setDouble(5, mO.getValor_total());
            stmt.setString(6, mO.getCod_barras());
            stmt.setString(7, mO.getDescricao());
            stmt.setDouble(8, mO.getValor_unidade());
            stmt.setInt(9, mO.getQtde());
            stmt.setDouble(10, mO.getValorPPeca());
            stmt.setString(11, mO.getDataOrcamento());
            stmt.setString(12, mO.getUsuario());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar orçamento!!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }
    
      public ArrayList<ModelOrcamento> pesquisarOrcamentos(String nome) throws Exception {
        ArrayList<ModelOrcamento> listarOrcamentos = new ArrayList();
        try {
            String sql = "SELECT cod_orcamento, cliente, data_orcamento FROM tbl_orcamentos WHERE cliente LIKE '%" + nome + "%'ORDER BY cod_orcamento ASC";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelOrcamento mO = new ModelOrcamento();
                mO.setCod_orcamento(rs.getInt("cod_orcamento"));
                mO.setCliente(rs.getString("cliente"));
                mO.setDataOrcamento(rs.getString("data_orcamento"));
                listarOrcamentos.add(mO);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar orcamento", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listarOrcamentos;

    }
      
        public void pesquisaInfo(ModelOrcamento mO) {

        int cod = mO.getCod_orcamento();
        String sql = "SELECT * FROM  tbl_orcamentos where cod_orcamento=" + cod;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                mO.setCod_orcamento(rs.getInt("cod_orcamento"));
                mO.setCliente(rs.getString("cliente"));
                mO.setCelular(rs.getString("celular"));
                mO.setTelefone(rs.getString("telefone"));
                mO.setValor_total(rs.getDouble("total_final"));
                mO.setDataOrcamento(rs.getString("data_orcamento"));
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possivel retornar sua busca!");

            }
            stmt.close();
            rs.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }
        
          public ArrayList<ModelOrcamento> pesquisarOrcamentos(int cod) throws Exception {
        ArrayList<ModelOrcamento> listarOrcamentos = new ArrayList();
        try {
            String sql = "SELECT cod_orcamento, total_final, cod_peca,descricao,valorUnidade,qtde,totalPPeca FROM tbl_orcamentos WHERE cod_orcamento = '" + cod + "';";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelOrcamento mO = new ModelOrcamento();
                mO.setCod_orcamento(rs.getInt("cod_orcamento"));
                mO.setValor_total(rs.getDouble("total_final"));
                mO.setCod_barras(rs.getString("cod_peca"));
                mO.setDescricao(rs.getString("descricao"));
                mO.setValor_unidade(rs.getDouble("valorUnidade"));
                mO.setQtde(rs.getInt("qtde"));
                mO.setValorPPeca(rs.getDouble("totalPPeca"));
                listarOrcamentos.add(mO);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar orcamento", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listarOrcamentos;

    }
          
            public void apagaOrcameto(ModelOrcamento mO) {
        int cod = mO.getCod_orcamento();
        String sql = "DELETE FROM  tbl_orcamentos WHERE cod_orcamento =" + cod;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao APAGAR orçamento!!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }
        

}
