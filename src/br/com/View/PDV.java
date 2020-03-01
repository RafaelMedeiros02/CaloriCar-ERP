package br.com.View;

import br.com.DAO.EstoqueDAO;
import br.com.DAO.FluxoCaixaDAO;
import br.com.DAO.VendasDAO;
import br.com.Factory.ConnectionFactory;
import br.com.Model.ModelMovCaixa;
import br.com.Model.ModelUsuario;
import br.com.Model.ModelVenda;
import br.com.Util.AtualizadorHorario;
import br.com.Util.FormatarVirgula;
import br.com.Util.LimitaString;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PDV extends javax.swing.JFrame {

    private Connection connection;
    String usuario = ModelUsuario.nomeSession;
    String loja = "LOJA 1";
    LimitaString limit = new LimitaString();
    private ConfirmaVendaPDV cVp;
    ModelMovCaixa mMC = new ModelMovCaixa();
    FluxoCaixaDAO fcdao = new FluxoCaixaDAO();
    ModelVenda mV = new ModelVenda();
    VendasDAO vdao = new VendasDAO();
    EstoqueDAO edao = new EstoqueDAO();
    boolean perm = true;
    float valorTotal;
    int codVenda;
    public String ClienteRef = "O MESMO";
    boolean finaliza_venda = false;
    float soma;
    float valor;
    int qtde = 1;
    private ConsultaEstoqueOrcamento cEo;

    public void geraCodCupom() {

    }

    public float somaValorTotal() {

        soma = 0;
        valor = 0;
        int cont = jTablePDV.getRowCount();
        for (int i = 0; i < cont; i++) {
            valor = Float.parseFloat(String.valueOf(jTablePDV.getValueAt(i, 5)));
            soma += valor;
        }
        return soma;
    }
//
    public int cod_venda() {
        this.connection = new ConnectionFactory().getConnection();
        String sql = "SELECT * FROM  tbl_vendas ORDER BY cod_venda DESC LIMIT 1";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                codVenda = (rs.getInt("cod_venda")) + 1;
                //System.out.print(codVenda);

            }

            stmt.close();
            rs.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return codVenda;

    }
    


    public void imprimiCupom(ArrayList<ModelVenda> listaPDV, ModelVenda mV) {
        
        
        String dataCupom = "dd/MM/yyyy";
        String horarioCupom = "H:mm - a";
        String data, hora;
        //pega data
        java.util.Date tempoAtual = new java.util.Date();
        SimpleDateFormat formata = new SimpleDateFormat();
        data = formata.format(tempoAtual);
        formata = new SimpleDateFormat(horarioCupom);
        hora = formata.format(tempoAtual);
   

        String conteudoCupom = "";

        int i = 0;
        while (i < jTablePDV.getRowCount()) {
            conteudoCupom += (jTablePDV.getModel().getValueAt(i, 0).toString()) + "  ";
            conteudoCupom += limit.limitaString(jTablePDV.getModel().getValueAt(i, 1).toString()) + "    ";
            //System.out.println(limit.limitaString(jTablePDV.getModel().getValueAt(i, 1).toString()) + "    ");
            conteudoCupom += (jTablePDV.getModel().getValueAt(i, 3).toString()) + "  ";
            conteudoCupom += (jTablePDV.getModel().getValueAt(i, 4).toString()) + "  ";
            conteudoCupom += (jTablePDV.getModel().getValueAt(i, 5).toString()) + "\n\r";
            i++;
        }

        this.imprimir("-------------------------------------------- \n\r"
                + "      CaloriCar - Auto pecas/Centro Automotivo  \n\r"
                + "      Rua Toto Pedrosa, 110, Tapiratiba - SP   \n\r"
                + "                  (19 3657-8053)               \n\r"
                + "------------------------------------------------ \n\r"
                + "| Item |  Descricao |  Valor |  Qtde | Total |   \n\r"
                + conteudoCupom + ""
                + "------------------------------------------------ \n\r"
                + "SUBTOTAL(R$): " + mV.getSubTotal() + "\n\r"
                + "DESCONTOS(R$): " + mV.getDescontos() + "\n\r"
                + "TOTAL A PAGAR(R$):" + mV.getTotalapagar() + "\n\r"
                + "PAGAMENTO: " + mV.getFormaPagamento() + "\n\r"
                + "------------------------------------------------\n\r"
                + data + "\n\r"
                + "OPERADOR: " + ModelUsuario.nomeSession + "\n\r"
                + "CLIENTE: " + ClienteRef + "\n\r"
                + "CODIGO DA VENDA: " + codVenda + "\n\r"
                + "------------------------------------------------ \n\r"
                + "          * CUPOM SEM VALOR FISCAL * \n\r"
                + "------------------------------------------------ \n\r"
                + "        CaloriCar Agradece a Preferencia!! \n\r"
                + "\n\r"
                + "\n\r"
                + "\f"
                );

    }

    public void imprimir(String pTexto) {

        try {

            InputStream prin = new ByteArrayInputStream(pTexto.getBytes());
            DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            SimpleDoc documentoTexto = new SimpleDoc(prin, docFlavor, null);
            PrintService impressora = PrintServiceLookup.lookupDefaultPrintService();

            PrintRequestAttributeSet printerAttributes = new HashPrintRequestAttributeSet();
            printerAttributes.add(new JobName("Impressao", null));
            printerAttributes.add(OrientationRequested.PORTRAIT);
            printerAttributes.add(MediaSizeName.ISO_A4);

            DocPrintJob printJob = impressora.createPrintJob();

            try {
                printJob.print(documentoTexto, (PrintRequestAttributeSet) printerAttributes);

            } catch (PrintException e) {
                JOptionPane.showMessageDialog(null, "Não foi possivel realizar a impressão!", "Erro", JOptionPane.ERROR_MESSAGE);

            }

            prin.close();

        } catch (Exception e) {

        }

    }

    public void mostrarHora() {

        AtualizadorHorario ah = new AtualizadorHorario(jLabelRelogio);
        ah.mostrarData(true);
        Thread thHora = ah;
        thHora.start();
    }

    public void percorreTabelaSalvandoVenda() {
        ArrayList<ModelVenda> listaPDV = new ArrayList();

        FormatarVirgula fv = new FormatarVirgula();
        ModelVenda mV = new ModelVenda();
        int i = 0;
        while (i < jTablePDV.getRowCount()) {

            mV.settItem(Integer.parseInt(jTablePDV.getModel().getValueAt(i, 0).toString()));
            mV.settDescricao(jTablePDV.getModel().getValueAt(i, 1).toString());
            mV.settCodBarras((jTablePDV.getModel().getValueAt(i, 2).toString()));
            mV.settValor(Double.parseDouble(jTablePDV.getModel().getValueAt(i, 3).toString()));
            mV.settQtde(Integer.parseInt(jTablePDV.getModel().getValueAt(i, 4).toString()));
            mV.settValorTotal(Double.parseDouble(jTablePDV.getModel().getValueAt(i, 5).toString()));
            mV.setCod_barras((jTablePDV.getModel().getValueAt(i, 2).toString()));
            mV.setQtde(Integer.parseInt(jTablePDV.getModel().getValueAt(i, 4).toString()));
            i++;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String date = new SimpleDateFormat("dd/MM/yyyy - H:mm:ss").format(timestamp.getTime());
            String date2 = new SimpleDateFormat("dd/MM/yyyy").format(timestamp.getTime());
            mV.setDataHora(date);
            mV.setData(date2);
            mV.setUsuario(ModelUsuario.nomeSession);
            mV.setPdv(loja);
            mV.setSubTotal(cVp.subTotal);
            mV.setFormaPagamento(cVp.pagamento);
            mV.setDescontos(cVp.descontos);
            mV.setAreceber(cVp.aReceber);
            mV.setTotalapagar(cVp.totalAPagar);
            mV.setCodVenda(codVenda);
            mV.setCliente(ClienteRef);
    
            edao.baixaPDV_Estoque(mV);
            vdao.salvaVenda(mV);
        }

        //========================= testes ====================================
        //=====================================================================
        //caixa
        mMC.setDataHora(mV.getDataHora());
        mMC.setData(mV.getData());
        mMC.setValor_entrada(mV.getTotalapagar());
        fcdao.registraCaixaVenda(mMC);
        imprimiCupom(listaPDV, mV);
        JOptionPane.showMessageDialog(null, "Venda Realizada com Sucesso!");
        this.dispose();
        new PDV().setVisible(true);
       

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

    public void pegarProduto(java.awt.event.KeyEvent evt) {
        DefaultTableModel tabelaPdv = (DefaultTableModel) jTablePDV.getModel();

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
                        tabelaPdv.getRowCount() + 1,
                        mV.getDescricao(),
                        mV.getCod_barras(),
                        mV.getValorUnidade(),
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

    public PDV() {
        initComponents();
        cod_venda();
        Usuario.setText(usuario);
        jLabelLoja.setText(loja);
        mostrarHora();
        jTextFieldCodBarras.grabFocus();
        this.cEo = new ConsultaEstoqueOrcamento(this, true);
         setIconImage(new ImageIcon("Img/caloriv2.png").getImage());
        setTitle("ERP - CaloriCar - Autopecas ");
        this.cVp = new ConfirmaVendaPDV(this, true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePDV = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Usuario = new javax.swing.JLabel();
        jLabelLoja = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabelVTotal = new javax.swing.JLabel();
        jLabelRelogio = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldCodBarras = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTablePDV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTablePDV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Descrição", "CodBarras", "ValorUnidade", "Qtde", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
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
        jTablePDV.getTableHeader().setReorderingAllowed(false);
        jTablePDV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePDVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePDV);
        if (jTablePDV.getColumnModel().getColumnCount() > 0) {
            jTablePDV.getColumnModel().getColumn(0).setResizable(false);
            jTablePDV.getColumnModel().getColumn(0).setPreferredWidth(5);
            jTablePDV.getColumnModel().getColumn(1).setResizable(false);
            jTablePDV.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTablePDV.getColumnModel().getColumn(2).setResizable(false);
            jTablePDV.getColumnModel().getColumn(2).setPreferredWidth(50);
            jTablePDV.getColumnModel().getColumn(3).setResizable(false);
            jTablePDV.getColumnModel().getColumn(3).setPreferredWidth(10);
            jTablePDV.getColumnModel().getColumn(4).setResizable(false);
            jTablePDV.getColumnModel().getColumn(4).setPreferredWidth(5);
            jTablePDV.getColumnModel().getColumn(5).setResizable(false);
            jTablePDV.getColumnModel().getColumn(5).setPreferredWidth(10);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/Img/fundoPDV.jpg"))); // NOI18N

        Usuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Usuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Usuario.setText("jLabel2");

        jLabelLoja.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelLoja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelLoja.setText("jLabel2");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Valor Total:");

        jLabelVTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelVTotal.setText("........");

        jLabelRelogio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelRelogio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelRelogio.setText("jLabel4");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel4.setText("F1: Pesquisar Estoque");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel5.setText("F2: Alterar Quantidade");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel6.setText("F3: Finalizar Venda");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("F4: Retirar Item");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setText("F5: Definir Cliente");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Usuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jSeparator1)
                                    .addComponent(jSeparator2))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelLoja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelRelogio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addGap(106, 106, 106))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelVTotal)
                                .addGap(112, 112, 112))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Usuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelLoja)
                .addGap(12, 12, 12)
                .addComponent(jLabelRelogio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVTotal)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/Img/icons8-scanner-de-código-de-barras-2-20.png"))); // NOI18N

        jTextFieldCodBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCodBarrasActionPerformed(evt);
            }
        });
        jTextFieldCodBarras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldCodBarrasKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldCodBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldCodBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Atalhos");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem1.setText("Pesquisar Estoque");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem2.setText("Alterar Quantidade");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem4.setText("Finalizar Venda");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItem3.setText("Retirar Item");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem5.setText("Definir Cliente");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator4))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );

        setSize(new java.awt.Dimension(939, 611));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCodBarrasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCodBarrasKeyPressed
        try {
            pegarProduto(evt);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar código de barras");
            jTextFieldCodBarras.setText("");
        }
    }//GEN-LAST:event_jTextFieldCodBarrasKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        cEo.setVisible(true);
        jTextFieldCodBarras.setText(cEo.codBarras);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        qtde = Integer.parseInt(JOptionPane.showInputDialog("Informe a quantidade a ser inserida.."));
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        DefaultTableModel modeloTabela = (DefaultTableModel) jTablePDV.getModel();
        if (modeloTabela.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(null, "Não é possivel finalizar uma venda sem antes adicionar algum ítem!", "ERRO", JOptionPane.ERROR_MESSAGE);

        } else {
            cVp.subTotal = somaValorTotal();
            cVp.jLabelSubTotal.setText(new DecimalFormat("R$ #,##0.00").format(somaValorTotal()));
            cVp.codVenda = codVenda;
            cVp.setVisible(true);
            if (cVp.finaliza_venda == true) {
            percorreTabelaSalvandoVenda();
            }
            else {
            JOptionPane.showMessageDialog(null, "Venda Cancelada!");
            }

        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        ClienteRef = (JOptionPane.showInputDialog("Informe o Nome do Cliente.."));
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jTablePDVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePDVMouseClicked
        int qlinha = jTablePDV.getRowCount();
        if (qlinha < 1) {
            JOptionPane.showMessageDialog(null, "Não possui nada pra retirar.");
        } else {
            int escolha = JOptionPane.showConfirmDialog(null, "Deseja remover o item selecionado?");
            if (escolha == JOptionPane.YES_OPTION) {
                int setar = jTablePDV.getSelectedRow();
                String codBarras = (jTablePDV.getModel().getValueAt(setar, 2).toString());
                int linha = Integer.parseInt(jTablePDV.getModel().getValueAt(setar, 0).toString());
                int qtde = Integer.parseInt(jTablePDV.getModel().getValueAt(setar, 4).toString());
                DefaultTableModel modeloTabela = (DefaultTableModel) jTablePDV.getModel();
                mV.setCod_barras(codBarras);
                mV.setQtde(qtde);
                try {
                    edao.devolucaoEstoquePDV(mV);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao dar baixa no estoque!!");
                }
                modeloTabela.removeRow(linha - 1);
                jLabelVTotal.setText(new DecimalFormat("R$ #,##0.00").format(somaValorTotal()));
                valorTotal = (somaValorTotal());
                for (int i = 0; i < qlinha; i++) {

                    modeloTabela.setValueAt(i + 1, i, 0);

                }
            }
        }
    }//GEN-LAST:event_jTablePDVMouseClicked

    private void jTextFieldCodBarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCodBarrasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCodBarrasActionPerformed

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
            java.util.logging.Logger.getLogger(PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PDV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Usuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelLoja;
    private javax.swing.JLabel jLabelRelogio;
    private javax.swing.JLabel jLabelVTotal;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTablePDV;
    public javax.swing.JTextField jTextFieldCodBarras;
    // End of variables declaration//GEN-END:variables
}
