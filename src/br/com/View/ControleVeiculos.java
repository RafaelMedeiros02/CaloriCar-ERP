/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.View;

import br.com.DAO.ControleVeiculoDAO;
import br.com.DAO.EstoqueDAO;
import br.com.DAO.UtilDAO;
import br.com.DAO.VendasDAO;
import br.com.Model.ModelControleVeiculos;
import br.com.Model.ModelEstoque;
import br.com.Model.ModelMovCaixa;
import br.com.Model.ModelVenda;
import br.com.Util.FormatarVirgula;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rafael Medeiros
 */
public class ControleVeiculos extends javax.swing.JFrame {

    ModelVenda mV = new ModelVenda();
    EstoqueDAO edao = new EstoqueDAO();
    JAbreOrdem aO = new JAbreOrdem();
    VendasDAO vdao = new VendasDAO();
    ModelEstoque mE = new ModelEstoque();
    ModelControleVeiculos mCV = new ModelControleVeiculos();
    boolean perm;
    public int ordem;
    ModelMovCaixa mMC = new ModelMovCaixa();
    int qtde = 1;
    UtilDAO udao = new UtilDAO();
    private ConsultaEstoqueOrcamento cEo;
    private EncerraOrdemServico eOs;
    FormatarVirgula fv = new FormatarVirgula();
    float soma;
    
    float valorTotal;
    String cliente;
    String modelo;
    String placa;
    public int cod_unic;
    float valor;
    ControleVeiculoDAO cVD = new ControleVeiculoDAO();

    public void pesquisaOrdem_Cliente() {
        DefaultTableModel tabela = (DefaultTableModel) jTableOrdem.getModel();
        try {
            tabela.setNumRows(0);
            for (ModelControleVeiculos mCV : cVD.pesquisarOrdem_PorCliente(jTextFieldPesquisa.getText())) {
                tabela.addRow(new Object[]{
                    mCV.getOrdem(),
                    mCV.getModelo(),
                    mCV.getCliente(),
                    mCV.getPlaca()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void pesquisaOrdem_Modelo() {
        DefaultTableModel tabela = (DefaultTableModel) jTableOrdem.getModel();
        try {
            tabela.setNumRows(0);
            for (ModelControleVeiculos mCV : cVD.pesquisarOrdem_PorModelo(jTextFieldPesquisa.getText())) {

                tabela.addRow(new Object[]{
                    mCV.getOrdem(),
                    mCV.getModelo(),
                    mCV.getCliente(),
                    mCV.getPlaca()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void pesquisaServicosOrdem() {
        DefaultTableModel tabela = (DefaultTableModel) jTableRelacaoCusto.getModel();
        try {
            tabela.setNumRows(0);
            for (ModelControleVeiculos mCV : cVD.pesquisaServicosOrdem(ordem)) {
                tabela.addRow(new Object[]{
                    mCV.getDescricao(),
                    mCV.getValor(),
                    mCV.getCodBarras(),
                    mCV.getQtde(),
                    mCV.getTotal(),
                    mCV.getPosi()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void verificaEstoque() {
        mV.setCod_barras(jTextFieldCodBarras.getText());
        edao.confere_estoque(mV);
        if (qtde > mV.getQtde()) {
            JOptionPane.showMessageDialog(null, "A quantidade deste item é superior a quantidade no seu estoque!");
            perm = false;
        } else {
            perm = true;
           
        }

    }

    public float somaValorTotal() {

        soma = 0;
        valor = 0;
        int cont = jTableRelacaoCusto.getRowCount();
        for (int i = 0; i < cont; i++) {
            valor = Float.parseFloat(String.valueOf(jTableRelacaoCusto.getValueAt(i, 4)));
            soma += valor;
        }
        return soma;
    }

    public void calcula_total_final() {

        jLabelVTotal.setText(new DecimalFormat("R$ #,##0.00").format(somaValorTotal()));

    }

    public void pegarProduto(java.awt.event.KeyEvent evt) {
        DefaultTableModel tabelaPdv = (DefaultTableModel) jTableRelacaoCusto.getModel();

        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            mV.setCod_barras(jTextFieldCodBarras.getText());
            vdao.busca_produto_PDV(mV);
            if (mV.getDescricao().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Código de Barras Inválido", "ERRO", JOptionPane.ERROR_MESSAGE);
                jTextFieldCodBarras.setText("");
            } else {
                verificaEstoque();
                if (perm == true) {
                    tabelaPdv.addRow(new Object[]{
                        mV.getDescricao(),
                        mV.getValorUnidade(),
                        mV.getCod_barras(),
                        qtde,
                        mV.getValorUnidade() * qtde
                    });
                    valorTotal = (somaValorTotal());
                    jLabelVTotal.setText(new DecimalFormat("R$ #,##0.00").format(somaValorTotal()));

                    qtde = 1;
                    jTextFieldCodBarras.setText("");
                    mV.setCod_barras(null);
                    mV.setDescricao(null);

                } else {
                    jTextFieldCodBarras.setText("");
                }
            }

        }
    }

    public void salvaTabela() {
        //importando classes para o uso
        int i = 0;
        while (i < jTableRelacaoCusto.getRowCount()) {
            mCV.setDescricao(jTableRelacaoCusto.getModel().getValueAt(i, 0).toString());
            mCV.setValor(Double.parseDouble(jTableRelacaoCusto.getModel().getValueAt(i, 1).toString()));
            mCV.setCodBarras(jTableRelacaoCusto.getModel().getValueAt(i, 2).toString());
            mCV.setQtde(Integer.parseInt(jTableRelacaoCusto.getModel().getValueAt(i, 3).toString()));
            mCV.setTotal(Double.parseDouble(jTableRelacaoCusto.getModel().getValueAt(i, 4).toString()));
            i++;
            mCV.setOrdem(ordem);
            cVD.salvaTabela(mCV);
        }

        JOptionPane.showMessageDialog(null, "Inserido(a)s com sucesso!");
        this.dispose();
        new ControleVeiculos().setVisible(true);

    }

    public ControleVeiculos() {
        initComponents();
        this.eOs = new EncerraOrdemServico(this, true);
        this.cEo = new ConsultaEstoqueOrcamento(this, true);
        pesquisaOrdem_Cliente();
        jRadioButtonCliente.setSelected(true);
        setIconImage(new ImageIcon("Img/caloriv2.png").getImage());
        setTitle("ERP - CaloriCar - Autopecas ");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jRadioButtonCliente = new javax.swing.JRadioButton();
        jRadioButtonModelo = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableOrdem = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldPesquisa = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableRelacaoCusto = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabelCliente = new javax.swing.JLabel();
        jLabelPlaca = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabelModelo = new javax.swing.JLabel();
        jLabelDataEntrada = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelCelular = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabelFixo = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldDescriServ = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextFieldValorServ = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldCodBarras = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabelVTotal = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Veiculos no pátio:");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Pesquisar por:");

        buttonGroup1.add(jRadioButtonCliente);
        jRadioButtonCliente.setText("Cliente");

        buttonGroup1.add(jRadioButtonModelo);
        jRadioButtonModelo.setText("Modelo");

        jTableOrdem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ordem", "Modelo", "Cliente", "Placa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableOrdem.getTableHeader().setReorderingAllowed(false);
        jTableOrdem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableOrdemMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableOrdem);
        if (jTableOrdem.getColumnModel().getColumnCount() > 0) {
            jTableOrdem.getColumnModel().getColumn(0).setResizable(false);
            jTableOrdem.getColumnModel().getColumn(0).setPreferredWidth(5);
            jTableOrdem.getColumnModel().getColumn(1).setResizable(false);
            jTableOrdem.getColumnModel().getColumn(1).setPreferredWidth(70);
            jTableOrdem.getColumnModel().getColumn(2).setResizable(false);
            jTableOrdem.getColumnModel().getColumn(2).setPreferredWidth(70);
            jTableOrdem.getColumnModel().getColumn(3).setResizable(false);
            jTableOrdem.getColumnModel().getColumn(3).setPreferredWidth(10);
        }

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/Img/icons8-carro-filled-20.png"))); // NOI18N

        jTextFieldPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButtonModelo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                        .addComponent(jLabel3))
                    .addComponent(jSeparator2)
                    .addComponent(jTextFieldPesquisa)
                    .addComponent(jSeparator3))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jRadioButtonCliente)
                        .addComponent(jRadioButtonModelo))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Inserir:");

        jTableRelacaoCusto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descrição", "Valor", "CodBarras", "Qtde", "Total", "\""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableRelacaoCusto.getTableHeader().setReorderingAllowed(false);
        jTableRelacaoCusto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableRelacaoCustoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableRelacaoCusto);
        if (jTableRelacaoCusto.getColumnModel().getColumnCount() > 0) {
            jTableRelacaoCusto.getColumnModel().getColumn(0).setResizable(false);
            jTableRelacaoCusto.getColumnModel().getColumn(0).setPreferredWidth(80);
            jTableRelacaoCusto.getColumnModel().getColumn(1).setResizable(false);
            jTableRelacaoCusto.getColumnModel().getColumn(1).setPreferredWidth(5);
            jTableRelacaoCusto.getColumnModel().getColumn(2).setResizable(false);
            jTableRelacaoCusto.getColumnModel().getColumn(2).setPreferredWidth(30);
            jTableRelacaoCusto.getColumnModel().getColumn(3).setResizable(false);
            jTableRelacaoCusto.getColumnModel().getColumn(3).setPreferredWidth(5);
            jTableRelacaoCusto.getColumnModel().getColumn(4).setResizable(false);
            jTableRelacaoCusto.getColumnModel().getColumn(4).setPreferredWidth(5);
            jTableRelacaoCusto.getColumnModel().getColumn(5).setResizable(false);
            jTableRelacaoCusto.getColumnModel().getColumn(5).setPreferredWidth(1);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Cliente:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Placa:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Data Entrada:");

        jLabelCliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabelCliente.setText("..............");

        jLabelPlaca.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelPlaca.setText(".........");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Modelo:");
        jLabel10.setToolTipText("");

        jLabelModelo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelModelo.setText(".........");

        jLabelDataEntrada.setText(".........");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Celular:");

        jLabelCelular.setText(".........");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Fixo:");

        jLabelFixo.setText(".........");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelCliente))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelPlaca))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelModelo))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelDataEntrada))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelCelular)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelFixo)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabelCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabelPlaca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabelModelo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDataEntrada))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabelCelular)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelFixo))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Nova Ordem");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Encerrar Ordem");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setText("Serviços:");

        jLabel20.setText("    Valor:");

        jTextFieldValorServ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldValorServKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDescriServ))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldValorServ, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldDescriServ, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextFieldValorServ, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel21.setText("Peças e Produtos:");

        jTextFieldCodBarras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCodBarrasKeyPressed(evt);
            }
        });

        jLabel8.setText("F3: Pesquisar");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCodBarras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextFieldCodBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel22.setText("Relação Serviços e Custos");

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/Img/icons8-taxi-car-cab-transport-transport-vehicle-services-aplicação-28-32.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Custo Atual:");

        jLabelVTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelVTotal.setText(".........");

        jLabel7.setText("Aperte Enter ao inserir o valor do serviço ou código de barras.");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jMenu1.setText("Atalhos");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem1.setText("Alterar Qtde");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem2.setText("Pesquisar Estoque");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem3.setText("Atualiza");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelVTotal)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel23)))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelVTotal)
                                .addComponent(jLabel7)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel23)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(792, 665));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        aO.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextFieldPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisaKeyReleased
        if (jRadioButtonCliente.isSelected()) {
            pesquisaOrdem_Cliente();
        } else {
            if (jRadioButtonModelo.isSelected()) {
                pesquisaOrdem_Modelo();

            } else {

            }
        }
    }//GEN-LAST:event_jTextFieldPesquisaKeyReleased

    private void jTableOrdemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableOrdemMouseClicked
        int setar = jTableOrdem.getSelectedRow();
        mCV.setOrdem(Integer.parseInt(jTableOrdem.getModel().getValueAt(setar, 0).toString()));
        ordem = Integer.parseInt(jTableOrdem.getModel().getValueAt(setar, 0).toString());
        cVD.pesquisa_info(mCV);
        pesquisaServicosOrdem();
        jLabelCliente.setText(jTableOrdem.getModel().getValueAt(setar, 2).toString());
        cliente = (jTableOrdem.getModel().getValueAt(setar, 2).toString());
        modelo = (jTableOrdem.getModel().getValueAt(setar, 1).toString());
        placa = (jTableOrdem.getModel().getValueAt(setar, 3).toString());
        jLabelModelo.setText(jTableOrdem.getModel().getValueAt(setar, 1).toString());
        jLabelPlaca.setText(jTableOrdem.getModel().getValueAt(setar, 3).toString());
        jLabelDataEntrada.setText(mCV.getData_entrada());
        jLabelFixo.setText(mCV.getFixo());
        jLabelCelular.setText(mCV.getCelular());
        mCV.setOrdem(Integer.parseInt(jTableOrdem.getModel().getValueAt(setar, 0).toString()));
        calcula_total_final();


    }//GEN-LAST:event_jTableOrdemMouseClicked

    private void jTextFieldCodBarrasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCodBarrasKeyPressed
        try {
            if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                mV.setCod_barras(jTextFieldCodBarras.getText());
                udao.busca_produto_PDV(mV);
                if (mV.getDescricao().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Código de Barras Inválido", "ERRO", JOptionPane.ERROR_MESSAGE);
                    jTextFieldCodBarras.setText("");
                } else {
                    verificaEstoque();
                    if (perm == true) {
                        mCV.setDescricao(mV.getDescricao());
                        mCV.setQtde(qtde);
                        mCV.setValor(mV.getValorUnidade());
                        mCV.setCodBarras(mV.getCod_barras());
                        mCV.setOrdem(ordem);
                        mCV.setTotal(mV.getValorUnidade() * qtde);
                       

                        cVD.salvaTabela(mCV);
                        
                        
                        mV.setCod_barras(jTextFieldCodBarras.getText());
                        mV.setQtde(qtde);
                        edao.baixaPDV_Estoque(mV);
                        jLabelVTotal.setText(new DecimalFormat("R$ #,##0.00").format(somaValorTotal()));
                        qtde = 1;
                        jTextFieldCodBarras.setText("");
                        mV.setCod_barras(null);
                        mV.setDescricao(null);
                        this.dispose();
                        new ControleVeiculos().setVisible(true);
                    } else {
                        jTextFieldCodBarras.setText("");
                    }
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar código de barras");
            jTextFieldCodBarras.setText("");
        }
    }//GEN-LAST:event_jTextFieldCodBarrasKeyPressed

    private void jTextFieldValorServKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldValorServKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            mCV.setDescricao(jTextFieldDescriServ.getText());
            mCV.setQtde(qtde);
            mCV.setValor(fv.converterVirgulaParaPonto(jTextFieldValorServ.getText()));
            mCV.setCodBarras("SERVIÇO");
            mCV.setTotal(fv.converterVirgulaParaPonto(jTextFieldValorServ.getText()) * qtde);
            mCV.setOrdem(ordem);
            cVD.salvaTabela(mCV);
            jTextFieldDescriServ.setText("");
            jTextFieldValorServ.setText("");
            calcula_total_final();
            this.dispose();
            new ControleVeiculos().setVisible(true);

        }
    }//GEN-LAST:event_jTextFieldValorServKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        qtde = Integer.parseInt(JOptionPane.showInputDialog("Informe a quantidade a ser adicionada"));
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        cEo.setVisible(true);
        jTextFieldCodBarras.setText(cEo.codBarras);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.dispose();
        new ControleVeiculos().setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        eOs.jTextFieldCliente.setText(cliente);
        eOs.jTextFieldModelo.setText(modelo);
        eOs.jTextFieldPlaca.setText(placa);
        eOs.jTextFieldDataEntrada.setText(mCV.getData_entrada());
        eOs.jLabelSubTotal.setText(new DecimalFormat("R$ #,##0.00").format(somaValorTotal()));
        eOs.subtotal = somaValorTotal();
        calcula_total_final();
        eOs.ordem = mCV.getOrdem();
        eOs.pesquisaServicosOrdem();
        eOs.setVisible(true);
        this.dispose();
        new ControleVeiculos().setVisible(true);


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

    }//GEN-LAST:event_jLabel7MouseClicked

    private void jTableRelacaoCustoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRelacaoCustoMouseClicked
        int setar = jTableRelacaoCusto.getSelectedRow();
        mCV.setPosi(Integer.parseInt(jTableRelacaoCusto.getModel().getValueAt(setar, 5).toString()));
        int q = JOptionPane.showConfirmDialog(null, "Remover o item/serviço selecionado?");
        if (q == JOptionPane.YES_OPTION) {
            mV.setQtde(Integer.parseInt(jTableRelacaoCusto.getModel().getValueAt(setar, 3).toString()));
            mV.setCod_barras(jTableRelacaoCusto.getModel().getValueAt(setar, 2).toString());
            edao.devolucaoEstoquePDV(mV);
            cVD.retiraLinha(mCV);
            this.dispose();
            new ControleVeiculos().setVisible(true);
        } else {

        }


    }//GEN-LAST:event_jTableRelacaoCustoMouseClicked

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
            java.util.logging.Logger.getLogger(ControleVeiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControleVeiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControleVeiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControleVeiculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ControleVeiculos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCelular;
    private javax.swing.JLabel jLabelCliente;
    private javax.swing.JLabel jLabelDataEntrada;
    private javax.swing.JLabel jLabelFixo;
    private javax.swing.JLabel jLabelModelo;
    private javax.swing.JLabel jLabelPlaca;
    private javax.swing.JLabel jLabelVTotal;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButtonCliente;
    private javax.swing.JRadioButton jRadioButtonModelo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTable jTableOrdem;
    private javax.swing.JTable jTableRelacaoCusto;
    private javax.swing.JTextField jTextFieldCodBarras;
    private javax.swing.JTextField jTextFieldDescriServ;
    private javax.swing.JTextField jTextFieldPesquisa;
    private javax.swing.JTextField jTextFieldValorServ;
    // End of variables declaration//GEN-END:variables
}
