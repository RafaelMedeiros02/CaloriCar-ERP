package br.com.DAO;

import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelEntrada;
import br.com.Model.ModelEstoque;
import br.com.Model.ModelVenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EstoqueDAO {

    private Connection connection;

    public EstoqueDAO() {

        this.connection = new ConnectionFactory().getConnection();

    }

    public void cadastrarPeca(ModelEstoque mE) {

        String sql = "INSERT INTO tbl_estoque (cod_barras, descricao, categoria, fornecedor, preco_custo, preco_venda, lucro_unidade, quantidade, alerta_qtde) values (?,?,?,?,?,?,?,0,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mE.getCod_barras());
            stmt.setString(2, mE.getDescricao());
            stmt.setString(3, mE.getCategoria());
            stmt.setString(4, mE.getFornecedor());
            stmt.setDouble(5, mE.getPreco_custo());
            stmt.setDouble(6, mE.getPreco_venda());
            stmt.setDouble(7, mE.getLucro_unidade());
            stmt.setInt(8, mE.getAlerta_estoque());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto, verifique se já não existe um cadastro referente a esse código de barras!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }

    public void alteraDados(ModelEstoque mE) {

        String sql = "UPDATE tbl_estoque SET descricao =?, categoria=?, fornecedor=?, preco_custo=?, preco_venda=?, lucro_unidade=?, alerta_qtde=? WHERE cod_barras=?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, mE.getDescricao());
            stmt.setString(2, mE.getCategoria());
            stmt.setString(3, mE.getFornecedor());
            stmt.setDouble(4, mE.getPreco_custo());
            stmt.setDouble(5, mE.getPreco_venda());
            stmt.setDouble(6, mE.getLucro_unidade());
            stmt.setInt(7, mE.getAlerta_estoque());
            stmt.setString(8, mE.getCod_barras());
            stmt.execute();
            stmt.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

    public void excluirProduto(ModelEstoque mE) {

        String sql = "DELETE FROM tbl_estoque where cod_barras=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mE.getCod_barras());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    public ArrayList<ModelEstoque> pesquisarEstoque(String descricao) throws Exception {
        ArrayList<ModelEstoque> listarEstoque = new ArrayList();
        try {
            String sql = "SELECT cod_barras, descricao, categoria,fornecedor, preco_custo, preco_venda, lucro_unidade,quantidade,status_peca,alerta_qtde FROM tbl_estoque WHERE descricao LIKE '%" + descricao + "%'ORDER BY descricao ASC";
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

    public ArrayList<ModelEstoque> pesquisarEstoque_CodBarras(String cod_barras) throws Exception {
        ArrayList<ModelEstoque> listarEstoque = new ArrayList();
        try {
            String sql = "SELECT cod_barras, descricao, categoria,fornecedor, preco_custo, preco_venda, lucro_unidade,quantidade,status_peca,alerta_qtde FROM tbl_estoque WHERE cod_barras LIKE '%" + cod_barras + "%'ORDER BY cod_barras ASC";
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

    public void pesquisaInfoEstoque(ModelEstoque mE) {

        String cod = mE.getCod_barras();
        String sql = "SELECT * FROM  tbl_estoque where cod_barras= '"+cod+"'"; 
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                mE.setCod_barras(rs.getString("cod_barras"));
                mE.setDescricao(rs.getString("descricao"));
                mE.setCategoria(rs.getString("categoria"));
                mE.setFornecedor(rs.getString("fornecedor"));
                mE.setPreco_custo(rs.getDouble("preco_custo"));
                mE.setPreco_venda(rs.getDouble("preco_venda"));
                mE.setLucro_unidade(rs.getDouble("lucro_unidade"));
                mE.setQuantidade(rs.getInt("quantidade"));
                mE.setAlerta_estoque(rs.getInt("alerta_qtde"));

            } else {
                JOptionPane.showMessageDialog(null, "Não foi possivel retornar sua busca!");

            }
            stmt.close();
            rs.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

    public void pesquisaEntradas(ModelEntrada mEE) {

        String cod = mEE.getCod_barras();
        String sql = "SELECT * FROM  tbl_entrada where cod_barras='" + cod + "'ORDER BY data_entrada DESC LIMIT 1";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {

                mEE.setUltima_entrada(rs.getString("data_entrada"));

            } else {
                mEE.setUltima_entrada("Nenhum registro..");

            }
            stmt.close();
            rs.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

    public void gravarEntrada(ModelEntrada mEE) {

        String sql = "INSERT INTO tbl_entrada ( descricao, cod_barras, fornecedor, data_entrada, qtde_entrada, valor_total) values (?,?,?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mEE.getDescricao());
            stmt.setString(2, mEE.getCod_barras());
            stmt.setString(3, mEE.getFornecedor());
            stmt.setString(4, mEE.getData_entrada());
            stmt.setInt(5, mEE.getQtde_entrada());
            stmt.setDouble(6, mEE.getValor_entrada());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar entrada, verifique se preencheu todos os campos de acordo!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }

    public void alteraEstoque(ModelEntrada mEE) {

        String sql = "UPDATE tbl_estoque SET quantidade=(quantidade+?) WHERE cod_barras=?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, mEE.getQtde_entrada());
            stmt.setString(2, mEE.getCod_barras());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }

    public void atualizaStatus_ESGOTADO() {

        String sql = "UPDATE tbl_estoque SET status_peca='ESGOTADO' WHERE quantidade <=0";
        try {

            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    public void atualizaStatus_Alerta() {

        String sql = "UPDATE tbl_estoque SET status_peca='AlertaLimite' WHERE quantidade <=alerta_qtde AND quantidade >0";
        try {

            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    public void atualizaStatus_Estoque() {

        String sql = "UPDATE tbl_estoque SET status_peca='EmEstoque' WHERE quantidade >alerta_qtde";
        try {

            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    public ArrayList<ModelEstoque> pesquisarEstoque_Status(String status) throws Exception {
        ArrayList<ModelEstoque> listarEstoque = new ArrayList();
        try {
            String sql = "SELECT cod_barras, descricao, categoria,fornecedor, preco_custo, preco_venda, lucro_unidade,quantidade,status_peca,alerta_qtde FROM tbl_estoque WHERE status_peca = '" + status + "'";
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
                listarEstoque.add(mE);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lista vazia para essa condição!");
            e.printStackTrace();
        }
        return listarEstoque;

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

    public void busca_produto(ModelEstoque mE) {

        String codigoBarras = mE.getCod_barras();
        String sql = "SELECT * FROM  tbl_estoque where cod_barras= '" + codigoBarras + "'";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next() && rs != null) {
                mE.setCod_barras(rs.getString("cod_barras"));
                mE.setDescricao(rs.getString("descricao"));
                mE.setPreco_venda(rs.getDouble("preco_venda"));
                mE.setStatus_estoque(rs.getString("status_peca"));

            }

        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Código inválido ou erro externo.", "ERRO", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);

        }
    }

    public void baixaPDV_Estoque(ModelVenda mV) {

        String sql = "UPDATE tbl_estoque SET quantidade=(quantidade-?) WHERE cod_barras=?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, mV.getQtde());
            stmt.setString(2, mV.getCod_barras());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }
    
    

    public void confere_estoque(ModelVenda mV) {

        String codigoBarras = mV.getCod_barras();
        String sql = "SELECT * FROM  tbl_estoque where cod_barras= '" + codigoBarras + "'";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next() && rs != null) {
                mV.setQtde(rs.getInt("quantidade"));
                mV.setAlerta(rs.getInt("alerta_qtde"));

            }

        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Código inválido ou erro externo.", "ERRO", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);

        }
    }

    public void devolucaoEstoquePDV(ModelVenda mV) {

        String sql = "UPDATE tbl_estoque SET quantidade=(quantidade+?) WHERE cod_barras=?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, mV.getQtde());
            stmt.setString(2, mV.getCod_barras());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    }
    
    
     public ArrayList<ModelEstoque> pesquisarEstoque_Alerta() throws Exception {
        ArrayList<ModelEstoque> listarEstoque = new ArrayList();
        try {
            String sql = "SELECT cod_barras, descricao,quantidade,status_peca FROM tbl_estoque WHERE status_peca IN ('ESGOTADO','AlertaLimite')";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelEstoque mE = new ModelEstoque();
                mE.setCod_barras(rs.getString("cod_barras"));
                mE.setDescricao(rs.getString("descricao"));
                mE.setQuantidade(rs.getInt("quantidade"));
                mE.setStatus_estoque(rs.getString("status_peca"));
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
     
       public ArrayList<ModelEntrada> pesquisar(String inicio, String fim) throws Exception {
        ArrayList<ModelEntrada> listar = new ArrayList();
   
        try {
            //String sql = "SELECT * FROM tbl_servicos_reg WHERE data_entrada BETWEEN '"+ inicio + "'AND'" + fim + "'";
            String sql = "SELECT * FROM tbl_entrada WHERE STR_TO_DATE(data_entrada, '%d/%m/%Y') BETWEEN STR_TO_DATE('"+inicio+"','%d/%m/%Y') AND STR_TO_DATE('"+fim+"','%d/%m/%Y')";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelEntrada mE = new ModelEntrada();
                mE.setDescricao(rs.getString("descricao"));
                mE.setCod_barras(rs.getString("cod_barras"));
                mE.setFornecedor(rs.getString("fornecedor"));
                mE.setData_entrada(rs.getString("data_entrada"));
                mE.setQtde_entrada(rs.getInt("qtde_entrada"));
                mE.setValor_entrada(rs.getDouble("valor_total"));
                        
                listar.add(mE);
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
