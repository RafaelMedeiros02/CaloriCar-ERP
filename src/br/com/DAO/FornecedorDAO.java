
package br.com.DAO;

import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelFornecedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class FornecedorDAO {

private Connection connection;
    
    public FornecedorDAO () {
        
     this.connection = new ConnectionFactory().getConnection();

    
    }
    
      public void cadastrarFornecedor(ModelFornecedor mF) {

        String sql = "INSERT INTO tbl_fornecedor (nome, endereco, telefone_fixo, celular, CNPJ,CPF,email,descricao) values (?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mF.getNome());
            stmt.setString(2, mF.getEndereco());
            stmt.setString(3, mF.getTelefone_fixo());
            stmt.setString(4, mF.getCelular());
            stmt.setString(5, mF.getCnpj());
            stmt.setString(6, mF.getCpf());
            stmt.setString(7, mF.getEmail());
            stmt.setString(8, mF.getDescricao());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar fornecedor, Verifique os dados e tente novamente!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }
    
         public void alteraDados(ModelFornecedor mF) {

        String sql = "UPDATE tbl_fornecedor SET nome=?, endereco=?, telefone_fixo=?, celular=?, CNPJ=?, CPF=?, email=?, descricao=? WHERE cod_fornecedor=?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, mF.getNome());
            stmt.setString(2, mF.getEndereco());
            stmt.setString(3, mF.getTelefone_fixo());
            stmt.setString(4, mF.getCelular());
            stmt.setString(5, mF.getCnpj());
            stmt.setString(6, mF.getCpf());
            stmt.setString(7, mF.getEmail());
            stmt.setString(8, mF.getDescricao());
            stmt.setInt(9, mF.getCod_fornecedor());
            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }
         
          public void excluirCliente(ModelFornecedor mF) {

        String sql = "DELETE FROM tbl_fornecedor where cod_fornecedor=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mF.getCod_fornecedor());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }
          
             public ArrayList<ModelFornecedor> pesquisarFornecedores(String nome) throws Exception {
        ArrayList<ModelFornecedor> listarFornecedores = new ArrayList();
        try {
            String sql = "SELECT cod_fornecedor, nome, telefone_fixo,celular, email FROM tbl_fornecedor WHERE nome LIKE '%" + nome + "%'ORDER BY nome ASC";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelFornecedor mF = new ModelFornecedor();
                mF.setCod_fornecedor(rs.getInt("cod_fornecedor"));
                mF.setNome(rs.getString("nome"));
                mF.setTelefone_fixo(rs.getString("telefone_fixo"));
                mF.setCelular(rs.getString("celular"));
                mF.setEmail(rs.getString("email"));
                listarFornecedores.add(mF);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar fornecedores", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listarFornecedores;

    }
    
      public void pesquisaInfoFornecedor(ModelFornecedor mF) {

        int cod = mF.getCod_fornecedor();
        String sql = "SELECT * FROM  tbl_fornecedor where cod_fornecedor=" + cod;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                mF.setCod_fornecedor(rs.getInt("cod_fornecedor"));
                mF.setNome(rs.getString("nome"));
                mF.setEndereco(rs.getString("endereco"));
                mF.setCelular(rs.getString("celular"));
                mF.setTelefone_fixo(rs.getString("telefone_fixo"));
                mF.setCpf(rs.getString("CPF"));
                mF.setCnpj(rs.getString("CNPJ"));
                mF.setEmail(rs.getString("email"));
                mF.setDescricao(rs.getString("descricao"));

            } else {
                JOptionPane.showMessageDialog(null, "Não foi possivel retornar sua busca!");

            }
            stmt.close();
            rs.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

    
}
