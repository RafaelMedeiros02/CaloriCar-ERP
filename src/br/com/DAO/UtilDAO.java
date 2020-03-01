package br.com.DAO;

import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelEstoque;
import br.com.Model.ModelUtil;
import br.com.Model.ModelVenda;
import br.com.View.CadastroDeEstoque;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class UtilDAO {

    private Connection connection;

    public UtilDAO() {

        this.connection = new ConnectionFactory().getConnection();

    }

    public ArrayList<ModelUtil> pesquisarCategoria(String descricao) throws Exception {
        ArrayList<ModelUtil> listarCategoria = new ArrayList();
        try {
            String sql = "SELECT cod_categoria, descricao FROM tbl_categoria WHERE descricao LIKE '%" + descricao + "%'ORDER BY cod_categoria ASC";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelUtil mU = new ModelUtil();
                mU.setCod_categoria(rs.getInt("cod_categoria"));
                mU.setDescricao(rs.getString("descricao"));
                listarCategoria.add(mU);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar!", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listarCategoria;

    }

    public ArrayList<ModelUtil> pesquisarPagamentosTaxas(String descricao) throws Exception {
        ArrayList<ModelUtil> listarPagamentosTaxas = new ArrayList();
        try {
            String sql = "SELECT cod_pagamento, metodo,taxa FROM tbl_pagamento WHERE metodo LIKE '%" + descricao + "%'ORDER BY cod_pagamento ASC";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelUtil mU = new ModelUtil();
                mU.setCod_pagamento(rs.getInt("cod_pagamento"));
                mU.setMetodo(rs.getString("metodo"));
                mU.setTaxa(rs.getDouble("taxa"));
                listarPagamentosTaxas.add(mU);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar!", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listarPagamentosTaxas;

    }

    public void excluirCategoria(ModelUtil mU) {

        String sql = "DELETE FROM tbl_categoria where descricao=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mU.getDescricao());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    public void excluirTaxaPag(ModelUtil mU) {

        String sql = "DELETE FROM tbl_pagamento where metodo=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mU.getMetodo());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    public void cadastrarCategoria(ModelUtil mU) {

        String sql = "INSERT INTO tbl_categoria (descricao) values (?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mU.getDescricao());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }

    public void cadastrarTaxaPagamento(ModelUtil mU) {

        String sql = "INSERT INTO tbl_pagamento (metodo) values (?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mU.getMetodo());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }

    public void confereParcelas() {

        String sql = "UPDATE  tbl_parcelamento SET status='VENCIDA' WHERE datediff(curdate() , STR_TO_DATE(data_parcela, '%d/%m/%Y')) >=1";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }

    public ArrayList<ModelEstoque> getTableLocal() throws Exception {
        ArrayList<ModelEstoque> pegaTabelaLocal = new ArrayList();

        try {
            String sql = "SELECT * FROM tbl_estoque;";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelEstoque mE = new ModelEstoque();
                mE.setCod_barras(rs.getString("cod_barras"));
                mE.setDescricao(rs.getString("descricao"));
                mE.setCategoria(rs.getString("categoria"));
                mE.setFornecedor(rs.getString("fornecedor"));
                mE.setPreco_custo(rs.getDouble("preco_custo"));
                mE.setPreco_venda(rs.getDouble("preco_venda"));
                mE.setLucro_unidade(rs.getDouble("lucro_unidade"));
                mE.setQuantidade(rs.getInt("quantidade"));
                mE.setStatus_estoque(rs.getString("status_peca"));
                mE.setAlerta_estoque(rs.getInt("alerta_qtde"));
                pegaTabelaLocal.add(mE);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pegaTabelaLocal;

    }

    public void busca_produto_PDV(ModelVenda mV) {

        String codigoBarras = mV.getCod_barras();
        String sql = "SELECT * FROM  tbl_estoque where cod_barras= '" + codigoBarras + "'";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next() && rs != null) {
                mV.setCod_barras(rs.getString("cod_barras"));
                mV.setDescricao(rs.getString("descricao"));
                mV.setValorUnidade(rs.getDouble("preco_venda"));

            }

        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Código inválido ou erro externo.", "ERRO", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);

        }
    }

}
