
package br.com.DAO;

import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelVenda;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class VendasDAO {
    private Connection connection;
    
    public VendasDAO() {
    
          this.connection = new ConnectionFactory().getConnection();
       
    
    }
    
         public void busca_produto_PDV(ModelVenda mV) {

        String codigoBarras = mV.getCod_barras();
        String sql = "SELECT * FROM  tbl_estoque where cod_barras= '"+codigoBarras+"'"; 
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next() && rs !=null) {
               mV.setCod_barras(rs.getString("cod_barras"));
               mV.setDescricao(rs.getString("descricao"));
               mV.setValorUnidade(rs.getDouble("preco_venda"));

            }
            
            
        } catch (SQLException u) {
              JOptionPane.showMessageDialog(null, "Código inválido ou erro externo." , "ERRO", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
      
        }
    }
         
         
          public void salvaVenda(ModelVenda mV) {

        String sql = "INSERT INTO tbl_vendas (cod_venda,dataHora, usuario, pdv, cliente, item, descricao, cod_barras, valor_unidade,"
                + "qtde, total_item, subtotal, descontos, totalapagar, areceber,troco,formaPagamento,data) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mV.getCodVenda());
            stmt.setString(2, mV.getDataHora());
            stmt.setString(3, mV.getUsuario());
            stmt.setString(4, mV.getPdv());
            stmt.setString(5, mV.getCliente());
            stmt.setInt(6, mV.gettItem());
            stmt.setString(7, mV.gettDescricao());
            stmt.setString(8, mV.gettCodBarras());
            stmt.setDouble(9, mV.gettValor());
            stmt.setInt(10, mV.gettQtde());
            stmt.setDouble(11, mV.gettValorTotal());
            stmt.setDouble(12, mV.getSubTotal());
            stmt.setDouble(13, mV.getDescontos());
            stmt.setDouble(14, mV.getTotalapagar());
            stmt.setDouble(15, mV.getAreceber());
            stmt.setDouble(16, mV.getTroco());
            stmt.setString(17, mV.getFormaPagamento());
            stmt.setString(18, mV.getData());

            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar venda, Verifique os dados e tente novamente!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }
          
             public void salvaParcelas(ModelVenda mV) {

        String sql = "INSERT INTO tbl_parcelamento (ref,cliente, data_parcela, valor_parcela, status,cpf, valor_total,descr) values (?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, mV.getRefVenda());
            stmt.setString(2, mV.getCliente());
            stmt.setString(3,  mV.getDataParcelaS());
            stmt.setDouble(4, mV.getValorParcela());
            stmt.setString(5, "Aberto");
            stmt.setString(6, mV.getCpf());
            stmt.setDouble(7, mV.getTotalCompra());
            stmt.setString(8, mV.getDesc());
            stmt.execute();
            stmt.close();
            
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar parcela, Verifique os dados e tente novamente!", "Alerta", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(u);
        }

    }
             
               public ArrayList<ModelVenda> pesquisa_parcelas(String cliente) throws Exception {
        ArrayList<ModelVenda> listarParcela = new ArrayList();
        try {
            String sql = "SELECT ref, cliente, data_parcela,valor_parcela,status,cpf,valor_total,descr FROM tbl_parcelamento WHERE cliente LIKE '%" + cliente + "%'ORDER BY cliente ASC";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelVenda mV = new ModelVenda();
                mV.setRefVenda(rs.getInt("ref"));
                mV.setClienteParcela(rs.getString("cliente"));
                mV.setDataParcelaS(rs.getString("data_parcela"));
                mV.setValorParcela(rs.getDouble("valor_parcela"));
                mV.setStatus(rs.getString("status"));
                mV.setCpf(rs.getString("cpf"));
                mV.setTotalCompra(rs.getDouble("valor_total"));
                mV.setDesc(rs.getString("descr"));
                listarParcela.add(mV);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listarParcela;

    }
               
                 public ArrayList<ModelVenda> parcelas_data(String inicio, String fim) throws Exception {
        ArrayList<ModelVenda> listar = new ArrayList();
   
        try {
            String sql = "SELECT * FROM tbl_parcelamento WHERE STR_TO_DATE(data_parcela, '%d/%m/%Y') BETWEEN STR_TO_DATE('"+inicio+"','%d/%m/%Y') AND STR_TO_DATE('"+fim+"','%d/%m/%Y')";
      
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelVenda mV = new ModelVenda();
                mV.setRefVenda(rs.getInt("ref"));
                mV.setClienteParcela(rs.getString("cliente"));
                mV.setDataParcelaS(rs.getString("data_parcela"));
                mV.setValorParcela(rs.getDouble("valor_parcela"));
                mV.setStatus(rs.getString("status"));
                mV.setCpf(rs.getString("CPF"));
                mV.setTotalCompra(rs.getDouble("valor_total"));
                mV.setDesc(rs.getString("descr"));
                listar.add(mV);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listar;

    }

       public ArrayList<ModelVenda> pesquisa_parcelas_status(String status) throws Exception {
        ArrayList<ModelVenda> listarParcela = new ArrayList();
        try {
            String sql = "SELECT ref, cliente, data_parcela,valor_parcela,status,cpf,valor_total,descr FROM tbl_parcelamento WHERE status = '" + status + "'";
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelVenda mV = new ModelVenda();
                mV.setRefVenda(rs.getInt("ref"));
                mV.setClienteParcela(rs.getString("cliente"));
                mV.setDataParcelaS(rs.getString("data_parcela"));
                mV.setValorParcela(rs.getDouble("valor_parcela"));
                mV.setStatus(rs.getString("status"));
                mV.setCpf(rs.getString("cpf"));
                mV.setTotalCompra(rs.getDouble("valor_total"));
                mV.setDesc(rs.getString("descr"));
                listarParcela.add(mV);
            }
            stmt.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return listarParcela;

    }
       
        public void pagaParcela(ModelVenda mV) {

        String sql = "DELETE FROM tbl_parcelamento where data_parcela=? AND valor_parcela=? AND valor_total=? AND cliente=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mV.getDataParcelaS());
            stmt.setDouble(2, mV.getValorParcela());
            stmt.setDouble(3, mV.getTotalCompra());
            stmt.setString(4, mV.getClienteParcela());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }
        
         public ArrayList<ModelVenda> vendasData(String inicio, String fim) throws Exception {
        ArrayList<ModelVenda> listar = new ArrayList();
   
        try {
            String sql = "SELECT * FROM tbl_vendas WHERE STR_TO_DATE(data, '%d/%m/%Y') BETWEEN STR_TO_DATE('"+inicio+"','%d/%m/%Y') AND STR_TO_DATE('"+fim+"','%d/%m/%Y')";
      
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModelVenda mV = new ModelVenda();
                mV.settDescricao(rs.getString("descricao"));
                mV.settQtde(rs.getInt("qtde"));
                mV.settValor(rs.getDouble("valor_unidade"));
                mV.settValorTotal(rs.getDouble("total_item"));
                mV.setFormaPagamento(rs.getString("formaPagamento"));
                mV.setData(rs.getString("data"));
                mV.setUsuario(rs.getString("usuario"));
                listar.add(mV);
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
