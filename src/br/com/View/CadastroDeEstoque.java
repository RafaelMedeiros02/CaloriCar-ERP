/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.View;

import br.com.DAO.EstoqueDAO;
import br.com.DAO.UtilDAO;
import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelEstoque;
import br.com.Model.ModelUtil;
import br.com.Util.FormatarVirgula;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael Medeiros
 */
public class CadastroDeEstoque extends javax.swing.JFrame {

    UtilDAO udao = new UtilDAO();
    EstoqueDAO edao = new EstoqueDAO();
    ModelEstoque mE = new ModelEstoque();
    ModelUtil mU = new ModelUtil();
    FormatarVirgula fv = new FormatarVirgula();
    private Connection connection;
    PesquisarEstoque e = new PesquisarEstoque();

    public void carrega_categoria() {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM  tbl_categoria";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                jComboBoxCategoria.addItem(rs.getString("descricao"));
            }
            stmt.close();
            rs.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    public void carrega_fornecedor() {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM  tbl_fornecedor";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                jComboBoxFornecedor.addItem(rs.getString("nome"));
            }
            stmt.close();
            rs.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    public CadastroDeEstoque() {
        initComponents();
        setIconImage(new ImageIcon("Img/caloriv2.png").getImage());
        setTitle("ERP - CaloriCar - Autopecas ");
        carrega_categoria();
        carrega_fornecedor();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldDescricaoProduto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldCodBarras = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxCategoria = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxFornecedor = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldGetFornecedor = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldPrecoCusto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldPrecoVenda = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldLucroUnidade = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldAlertaEstoque = new javax.swing.JTextField();
        jButtonCadastrar = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Cadastro de Estoque");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Descrição Produto:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText(" Código de Barras:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Categoria:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Fornecedor:");

        jComboBoxFornecedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..", "OUTRO", " " }));
        jComboBoxFornecedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxFornecedorItemStateChanged(evt);
            }
        });
        jComboBoxFornecedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxFornecedorMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Outro Fornecedor:");

        jTextFieldGetFornecedor.setEditable(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Preço de Custo:");

        jTextFieldPrecoCusto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPrecoCustoKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Preço de Venda:");

        jTextFieldPrecoVenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPrecoVendaKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Lucro por Unidade:");

        jTextFieldLucroUnidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldLucroUnidadeKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("    Alerta Estoque:");

        jTextFieldAlertaEstoque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldAlertaEstoqueKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldGetFornecedor))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldCodBarras)
                            .addComponent(jTextFieldDescricaoProduto)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldPrecoCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldLucroUnidade)))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldAlertaEstoque)
                            .addComponent(jTextFieldPrecoVenda, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldDescricaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldCodBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldGetFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldPrecoCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jTextFieldAlertaEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jTextFieldLucroUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonCadastrar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonCadastrar.setText("Cadastrar");
        jButtonCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastrarActionPerformed(evt);
            }
        });

        jButtonAlterar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonAlterar.setText("Atualizar");
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

        jButtonExcluir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonExcluir.setText("Excluir");
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/Img/pecaGrande.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonCadastrar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonExcluir)))
                        .addGap(0, 310, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addGap(14, 14, 14)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCadastrar)
                    .addComponent(jButtonAlterar)
                    .addComponent(jButtonExcluir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(605, 420));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastrarActionPerformed
        if (jTextFieldDescricaoProduto.getText().isEmpty()
                || jTextFieldCodBarras.getText().isEmpty()
                || jTextFieldPrecoCusto.getText().isEmpty()
                || jTextFieldPrecoVenda.getText().isEmpty()
                || jTextFieldGetFornecedor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Desculpe, mas você não pode cadastrar um produto sem preencher todos os campos!", "Alerta", JOptionPane.WARNING_MESSAGE);
        } else {
            int opcao = JOptionPane.showConfirmDialog(null, "Realizando o cadastro de: " + jTextFieldDescricaoProduto.getText() + " ao estoque, Confirmar?");
            if (opcao == JOptionPane.YES_OPTION) {
                mE.setDescricao(jTextFieldDescricaoProduto.getText());
                mE.setCod_barras(jTextFieldCodBarras.getText());
                mE.setCategoria(String.valueOf(jComboBoxCategoria.getSelectedItem()));
                mE.setFornecedor(jTextFieldGetFornecedor.getText());
                mE.setPreco_custo(fv.converterVirgulaParaPonto(jTextFieldPrecoCusto.getText()));
                mE.setPreco_venda(fv.converterVirgulaParaPonto(jTextFieldPrecoVenda.getText()));
                mE.setLucro_unidade(fv.converterVirgulaParaPonto(jTextFieldLucroUnidade.getText()));
                mE.setAlerta_estoque(Integer.parseInt(jTextFieldAlertaEstoque.getText()));
                try {
                    edao.cadastrarPeca(mE);
                    JOptionPane.showMessageDialog(null, jTextFieldDescricaoProduto.getText() + ", foi cadastrado(a) com sucesso!");
                    this.dispose();
                    e.revalidate();
                    e.repaint();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Falha ao cadastrar!", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
            } else {

            }

        }
    }//GEN-LAST:event_jButtonCadastrarActionPerformed

    private void jTextFieldPrecoVendaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPrecoVendaKeyReleased
        double precocusto;
        double precovenda;
        double lucropunidade;

        precocusto = fv.converterVirgulaParaPonto(jTextFieldPrecoCusto.getText());
        precovenda = fv.converterVirgulaParaPonto(jTextFieldPrecoVenda.getText());

        lucropunidade = precovenda - precocusto;

        jTextFieldLucroUnidade.setText(String.valueOf(lucropunidade));
    }//GEN-LAST:event_jTextFieldPrecoVendaKeyReleased

    private void jComboBoxFornecedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxFornecedorMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxFornecedorMouseClicked

    private void jComboBoxFornecedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxFornecedorItemStateChanged
        if (jComboBoxFornecedor.getSelectedItem().equals("OUTRO")) {
            jTextFieldGetFornecedor.setEditable(true);
            jTextFieldGetFornecedor.setText("");

        } else {
            jTextFieldGetFornecedor.setEditable(false);
            jTextFieldGetFornecedor.setText(String.valueOf(jComboBoxFornecedor.getSelectedItem()));
        }


    }//GEN-LAST:event_jComboBoxFornecedorItemStateChanged

    private void jTextFieldLucroUnidadeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLucroUnidadeKeyReleased
        double precocusto;
        double precovenda;
        double lucropunidade;

        precocusto = fv.converterVirgulaParaPonto(jTextFieldPrecoCusto.getText());
        lucropunidade = fv.converterVirgulaParaPonto(jTextFieldLucroUnidade.getText());

        precovenda = precocusto + lucropunidade;

        jTextFieldPrecoVenda.setText(String.valueOf(precovenda));

    }//GEN-LAST:event_jTextFieldLucroUnidadeKeyReleased

    private void jTextFieldPrecoCustoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPrecoCustoKeyReleased
        jTextFieldLucroUnidade.setText("");
        jTextFieldPrecoVenda.setText("");
    }//GEN-LAST:event_jTextFieldPrecoCustoKeyReleased

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed
        if (jTextFieldDescricaoProduto.getText().isEmpty()
                || jTextFieldCodBarras.getText().isEmpty()
                || jTextFieldPrecoCusto.getText().isEmpty()
                || jTextFieldPrecoVenda.getText().isEmpty()
                || jTextFieldGetFornecedor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Desculpe, mas você não pode alterar um produto sem preencher todos os campos!", "Alerta", JOptionPane.WARNING_MESSAGE);
        } else {
            int opcao = JOptionPane.showConfirmDialog(null, "Alterando dados referente a: " + jTextFieldDescricaoProduto.getText());
            if (opcao == JOptionPane.YES_OPTION) {

                mE.setDescricao(jTextFieldDescricaoProduto.getText());
                mE.setCod_barras(jTextFieldCodBarras.getText());
                mE.setCategoria(String.valueOf(jComboBoxCategoria.getSelectedItem()));
                mE.setFornecedor(jTextFieldGetFornecedor.getText());
                mE.setPreco_custo(fv.converterVirgulaParaPonto(jTextFieldPrecoCusto.getText()));
                mE.setPreco_venda(fv.converterVirgulaParaPonto(jTextFieldPrecoVenda.getText()));
                mE.setLucro_unidade(fv.converterVirgulaParaPonto(jTextFieldLucroUnidade.getText()));
                mE.setAlerta_estoque(Integer.parseInt(jTextFieldAlertaEstoque.getText()));
                try {
                    edao.alteraDados(mE);
                    JOptionPane.showMessageDialog(null, jTextFieldDescricaoProduto.getText() + ", foi alterado(a) com sucesso!");
                    edao.atualizaStatus_Alerta();
                    edao.atualizaStatus_ESGOTADO();
                    edao.atualizaStatus_Estoque();
                    this.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Falha ao alterar!", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
            } else {

            }
        }
    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        if (jTextFieldCodBarras.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Desculpe, mas você não pode excluir um produto sem informar seu código de barras!", "Alerta", JOptionPane.WARNING_MESSAGE);
        } else {
            int opcao = JOptionPane.showConfirmDialog(null, "Excluindo dados referente a: " + jTextFieldDescricaoProduto.getText());
            if (opcao == JOptionPane.YES_OPTION) {
                mE.setCod_barras(jTextFieldCodBarras.getText());
                try {
                    edao.excluirProduto(mE);
                    JOptionPane.showMessageDialog(null, jTextFieldDescricaoProduto.getText() + ", foi excluido(a) com sucesso!");
                    this.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Falha ao excluir!", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
            } else {

            }
        }
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jTextFieldAlertaEstoqueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAlertaEstoqueKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAlertaEstoqueKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CadastroDeEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroDeEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroDeEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroDeEstoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroDeEstoque().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonCadastrar;
    private javax.swing.JButton jButtonExcluir;
    public javax.swing.JComboBox<String> jComboBoxCategoria;
    public javax.swing.JComboBox<String> jComboBoxFornecedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    public javax.swing.JTextField jTextFieldAlertaEstoque;
    public javax.swing.JTextField jTextFieldCodBarras;
    public javax.swing.JTextField jTextFieldDescricaoProduto;
    public javax.swing.JTextField jTextFieldGetFornecedor;
    public javax.swing.JTextField jTextFieldLucroUnidade;
    public javax.swing.JTextField jTextFieldPrecoCusto;
    public javax.swing.JTextField jTextFieldPrecoVenda;
    // End of variables declaration//GEN-END:variables
}
