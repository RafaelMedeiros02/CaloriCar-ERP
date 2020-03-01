package br.com.DAO;

import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelCliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ClientesDAO {

    private Connection connection;

    public ClientesDAO() {
        this.connection = new ConnectionFactory().getConnection();

    }

    public void cadastrarCliente(ModelCliente mC) {

        String sql = "INSERT INTO tbl_clientes (nome, rua, numero, bairro, cidade, telefone_fixo, celular, cpf) values (?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mC.getNome());
            stmt.setString(2, mC.getRua());
            stmt.setString(3, mC.getNumero());
            stmt.setString(4, mC.getBairro());
            stmt.setString(5, mC.getCidade());
            stmt.setString(6, mC.getTelefone_fixo());
            stmt.setString(7, mC.getCelular());
            stmt.setString(8, mC.getCpf());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente, Verifique os dados e tente novamente!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }

    public void alteraDados(ModelCliente mC) {

        String sql = "UPDATE tbl_clientes SET nome=?, rua=?, numero=?, bairro=?, cidade=?, telefone_fixo=?, celular=?, cpf=? WHERE cod_cliente=?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, mC.getNome());
            stmt.setString(2, mC.getRua());
            stmt.setString(3, mC.getNumero());
            stmt.setString(4, mC.getBairro());
            stmt.setString(5, mC.getCelular());
            stmt.setString(6, mC.getTelefone_fixo());
            stmt.setString(7, mC.getCelular());
            stmt.setString(8, mC.getCpf());
            stmt.setInt(9, mC.getCod_cliente());
            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

    public void excluirCliente(ModelCliente mC) {

        String sql = "DELETE FROM tbl_clientes where cod_cliente=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mC.getCod_cliente());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    public ArrayList<ModelCliente> pesquisarClientes(String nome) throws Exception {
        ArrayList<ModelCliente> listarClientes = new ArrayList();
        try {
            String sql = "SELECT cod_cliente, nome, telefone_fixo,celular, cpf FROM tbl_clientes WHERE nome LIKE '%" + nome + "%'ORDER BY nome ASC";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelCliente mC = new ModelCliente();
                mC.setCod_cliente(rs.getInt("cod_cliente"));
                mC.setNome(rs.getString("nome"));
                mC.setTelefone_fixo(rs.getString("telefone_fixo"));
                mC.setCelular(rs.getString("celular"));
                mC.setCpf(rs.getString("cpf"));
                listarClientes.add(mC);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar clientes", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listarClientes;

    }
    
      public void pesquisaInfoCliente(ModelCliente mC) {

        int cod = mC.getCod_cliente();
        String sql = "SELECT * FROM  tbl_clientes where cod_cliente=" + cod;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                mC.setCod_cliente(rs.getInt("cod_cliente"));
                mC.setNome(rs.getString("nome"));
                mC.setRua(rs.getString("rua"));
                mC.setNumero(rs.getString("numero"));
                mC.setBairro(rs.getString("bairro"));
                mC.setCidade(rs.getString("cidade"));
                mC.setTelefone_fixo(rs.getString("telefone_fixo"));
                mC.setCelular(rs.getString("celular"));
                mC.setCpf(rs.getString("cpf"));
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
