package br.com.DAO;


import br.com.Model.ModelControleVeiculos;
import br.com.Nuvem.ConnectionFactoryNuvem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ControleVeiculoDAO {

    private Connection connection;
    
    

    public ControleVeiculoDAO() {

        this.connection = new ConnectionFactoryNuvem().getConnection();
        
       

    }

    public void abreOrdemServico(ModelControleVeiculos mCV) {

        String sql = "INSERT INTO tbl_servicos (ordem_servico, cliente, fixo,"
                + " celular, modelo, placa, data_entrada) values (?,?,?,?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mCV.getOrdem());
            stmt.setString(2, mCV.getCliente());
            stmt.setString(3, mCV.getFixo());
            stmt.setString(4, mCV.getCelular());
            stmt.setString(5, mCV.getModelo());
            stmt.setString(6, mCV.getPlaca());
            stmt.setString(7, mCV.getData_entrada());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir ordem de serviço, Verifique os dados e tente novamente!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }

    public void pesquisa_cod(ModelControleVeiculos mCV) {

        String sql = "SELECT * FROM  tbl_servicos ORDER BY ordem_servico DESC LIMIT 1";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                mCV.setOrdem(rs.getInt("ordem_servico"));

            } else {

            }
            stmt.close();
            rs.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

       
    
    public ArrayList<ModelControleVeiculos> pesquisarOrdem_PorCliente(String cliente) throws Exception {
        ArrayList<ModelControleVeiculos> listar = new ArrayList();
        try {
            String sql = "SELECT ordem_servico, cliente, modelo, placa FROM tbl_servicos WHERE cliente LIKE '%" + cliente + "%'ORDER BY ordem_servico ASC";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelControleVeiculos mCV = new ModelControleVeiculos();
                mCV.setOrdem(rs.getInt("ordem_servico"));
                mCV.setCliente(rs.getString("cliente"));
                mCV.setModelo(rs.getString("modelo"));
                mCV.setPlaca(rs.getString("placa"));

                listar.add(mCV);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar ordem", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listar;

    }

    public ArrayList<ModelControleVeiculos> pesquisarOrdem_PorModelo(String modelo) throws Exception {
        ArrayList<ModelControleVeiculos> listar = new ArrayList();
        try {
            String sql = "SELECT ordem_servico, cliente, modelo, placa FROM tbl_servicos WHERE modelo LIKE '%" + modelo + "%'ORDER BY ordem_servico ASC";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelControleVeiculos mCV = new ModelControleVeiculos();
                mCV.setOrdem(rs.getInt("ordem_servico"));
                mCV.setCliente(rs.getString("cliente"));
                mCV.setModelo(rs.getString("modelo"));
                mCV.setPlaca(rs.getString("placa"));

                listar.add(mCV);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar ordem", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listar;

    }

    public void pesquisa_info(ModelControleVeiculos mCV) {

        int cod = mCV.getOrdem();
        String sql = "SELECT * FROM  tbl_servicos WHERE ordem_servico=" + cod;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                mCV.setOrdem(rs.getInt("ordem_servico"));
                mCV.setCelular(rs.getString("celular"));
                mCV.setFixo(rs.getString("fixo"));
                mCV.setData_entrada(rs.getString("data_entrada"));

            } else {

            }
            stmt.close();
            rs.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

    public void salvaTabela(ModelControleVeiculos mCV) {

        String sql = "INSERT INTO tbl_servicos_rel (cod_ordem, descricao, valor, cod_barras, qtde, total)"
                + " values (?,?,?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mCV.getOrdem());
            stmt.setString(2, mCV.getDescricao());
            stmt.setDouble(3, mCV.getValor());
            stmt.setString(4, mCV.getCodBarras());
            stmt.setInt(5, mCV.getQtde());
            stmt.setDouble(6, mCV.getTotal());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar tabela, Verifique os dados e tente novamente!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }

    public ArrayList<ModelControleVeiculos> pesquisaServicosOrdem(int cod_ordem) throws Exception {
        ArrayList<ModelControleVeiculos> listar = new ArrayList();
        try {
            String sql = "SELECT descricao, valor, cod_barras, qtde, total,cont  FROM tbl_servicos_rel WHERE cod_ordem =" + cod_ordem;
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelControleVeiculos mCV = new ModelControleVeiculos();
                mCV.setDescricao(rs.getString("descricao"));
                mCV.setValor(rs.getDouble("valor"));
                mCV.setCodBarras(rs.getString("cod_barras"));
                mCV.setQtde(rs.getInt("qtde"));
                mCV.setTotal(rs.getDouble("total"));
                mCV.setPosi(rs.getInt("cont"));
                listar.add(mCV);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listar;

    }

    public void salvaTabela_Final(ModelControleVeiculos mCV) {

        String sql = "INSERT INTO tbl_servicos_reg (cod_servico, cliente, modelo, placa, data_entrada, data_saida,"
                + "sub_total,descontos,forma_pagamento,total_pagar,a_receber,troco,descricao,qtde,valor_unit,valor_total,encerramento,usuario)"
                + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mCV.getOrdem());
            stmt.setString(2, mCV.getCliente());
            stmt.setString(3, mCV.getModelo());
            stmt.setString(4, mCV.getPlaca());
            stmt.setString(5, mCV.getData_entrada());
            stmt.setString(6, mCV.getData_saida());
            stmt.setDouble(7, mCV.getSubtotal());
            stmt.setDouble(8, mCV.getDescontos());
            stmt.setString(9, mCV.getPagamento());
            stmt.setDouble(10, mCV.getTotalapagar());
            stmt.setDouble(11, mCV.getAreceber());
            stmt.setDouble(12, mCV.getTroco());
            stmt.setString(13, mCV.getDescricao());
            stmt.setInt(14, mCV.getQtde());
            stmt.setDouble(15, mCV.getValor());
            stmt.setDouble(16, mCV.getTotal());
            stmt.setString(17, mCV.getData_encerramento());
            stmt.setString(18, mCV.getUsuario());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar tabela, Verifique os dados e tente novamente!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }

    public void encerraOrdem(ModelControleVeiculos mCV) {

        String sql = "DELETE FROM tbl_servicos where ordem_servico=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mCV.getOrdem());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    public void encerraServicos(ModelControleVeiculos mCV) {

        String sql = "DELETE FROM tbl_servicos_rel where cod_ordem=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mCV.getOrdem());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }
    
       public void retiraLinha(ModelControleVeiculos mCV) {

        String sql = "DELETE FROM tbl_servicos_rel where cont=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mCV.getPosi());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }
       
        public ArrayList<ModelControleVeiculos> pesquisar(String inicio, String fim) throws Exception {
        ArrayList<ModelControleVeiculos> listar = new ArrayList();
   
        try {
            //String sql = "SELECT * FROM tbl_servicos_reg WHERE data_entrada BETWEEN '"+ inicio + "'AND'" + fim + "'";
            String sql = "SELECT * FROM tbl_servicos_reg WHERE STR_TO_DATE(data_entrada, '%d/%m/%Y') BETWEEN STR_TO_DATE('"+inicio+"','%d/%m/%Y') AND STR_TO_DATE('"+fim+"','%d/%m/%Y')";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelControleVeiculos mCV = new ModelControleVeiculos();
                mCV.setCliente(rs.getString("cliente"));
                mCV.setModelo(rs.getString("modelo"));
                mCV.setPlaca(rs.getString("placa"));
                mCV.setData_entrada(rs.getString("data_entrada"));
                mCV.setData_saida(rs.getString("data_saida"));
                mCV.setValor(rs.getDouble("valor_unit"));
                mCV.setDescricao(rs.getString("descricao"));
                mCV.setData_encerramento(rs.getString("encerramento"));
                mCV.setCodunic(rs.getInt("cod_servico"));
                listar.add(mCV);
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
