package br.com.DAO;

import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelUsuario;
import br.com.View.Login;
import br.com.View.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO() {

        this.connection = new ConnectionFactory().getConnection();

    }

    public void logar(ModelUsuario mU) throws SQLException {

        String SQL = "SELECT * FROM tbl_usuarios WHERE nome_usuario=? AND senha=?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, mU.getNomeUsuario());
            stmt.setString(2, mU.getSenha());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Principal p = new Principal();
                    p.show();
                    //validando sessao
                    ModelUsuario.nomeSession = rs.getString("nome_completo");

                    if (rs.getString("permissao").equals("COMUM")) {
                        JOptionPane.showMessageDialog(null, "Bem Vindo(a) ao ERP CaloriCar: " + rs.getString("nome_completo"));
                        p.jMenuItem4Usuario.setVisible(false);
                       
                        

                    }
                    if (rs.getString("permissao").equals("TOTAL")) {
                        JOptionPane.showMessageDialog(null, "Bem Vindo(a) ao ERP CaloriCar: " + rs.getString("nome_completo"));

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Usuario ou senha Incorretos!!");
                    Login l = new Login();
                    l.dispose();
                    l.show();
                }

            }
        } catch (SQLException ex) {

        }
    }

    public void cadastrarUsuario(ModelUsuario mU) {

        String sql = "INSERT INTO tbl_usuarios (nome_completo, nome_usuario, permissao, senha) values (?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mU.getNomeCompleto());
            stmt.setString(2, mU.getNomeUsuario());
            stmt.setString(3, mU.getPermissao());
            stmt.setString(4, mU.getSenha());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar Usuário, Verifique os dados e tente novamente!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }

    public void alteraDados(ModelUsuario mU) {

        String sql = "UPDATE tbl_usuarios SET nome_completo=?, nome_usuario=?, permissao=?, senha=? WHERE cod_usuario=?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, mU.getNomeCompleto());
            stmt.setString(2, mU.getNomeUsuario());
            stmt.setString(3, mU.getPermissao());
            stmt.setString(4, mU.getSenha());
            stmt.setInt(5, mU.getCod_usuario());
            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

    public void excluirUsuario(ModelUsuario mU) {

        String sql = "DELETE FROM tbl_usuarios where cod_usuario=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mU.getCod_usuario());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }
    
     public ArrayList<ModelUsuario> pesquisarUsuarios(String nome) throws Exception {
        ArrayList<ModelUsuario> listarUsuarios = new ArrayList();
        try {
            String sql = "SELECT cod_usuario, nome_completo, nome_usuario,permissao FROM tbl_usuarios WHERE nome_completo LIKE '%" + nome + "%'ORDER BY nome_completo ASC";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelUsuario mU = new ModelUsuario();
                mU.setCod_usuario(rs.getInt("cod_usuario"));
                mU.setNomeCompleto(rs.getString("nome_completo"));
                mU.setNomeUsuario(rs.getString("nome_usuario"));
                mU.setPermissao(rs.getString("permissao"));
                listarUsuarios.add(mU);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar usuarios", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listarUsuarios;

    }
    
      public void pesquisaInfoUsuarios(ModelUsuario mU) {

        int cod = mU.getCod_usuario();
        String sql = "SELECT * FROM  tbl_usuarios where cod_usuario=" + cod;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                mU.setCod_usuario(rs.getInt("cod_usuario"));
                mU.setNomeCompleto(rs.getString("nome_completo"));
                mU.setNomeUsuario(rs.getString("nome_usuario"));
                mU.setPermissao(rs.getString("permissao"));
                mU.setSenha(rs.getString("senha"));
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
