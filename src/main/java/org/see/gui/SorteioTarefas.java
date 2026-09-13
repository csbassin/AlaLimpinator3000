package org.see.gui;


import org.see.Miscelaneous;
import org.see.modelo.Morador;
import org.see.modelo.MoradorTarefaCombinacao;
import org.see.modelo.Tarefa;
import org.see.service.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class SorteioTarefas { //eu sei que o nome ficou ruim.
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private TarefaService ts;
    private MoradorService ms;
    private MoradorTarefaCombinacaoService mtcs;
    private String[] title = {"Morador", "Tarefa", "Descrição", "Fixa", "Data e Hora do sorteio", "Data limite"};
    private DefaultTableModel dtm = new DefaultTableModel(title, 0);

    public SorteioTarefas() {
        try {
            ts = new TarefaService();
            ms = new MoradorService();
            mtcs = new MoradorTarefaCombinacaoService();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        long start = System.currentTimeMillis();
        tabelaPreenchinator(tarefaAleatorinator());
        initialize();
        System.out.println("Elapsed Time: "+(System.currentTimeMillis()-start)+"ms");
    }

    public List<MoradorTarefaCombinacao> tarefaAleatorinator() {
        try{
            return mtcs.sortearTarefas();
        }catch(Exception e){
            //todo janela de erro
            e.printStackTrace();
        }
        return null;
    }

    private void visible() {
        frame.setVisible(true);
    }

    private void tabelaPreenchinator(List<MoradorTarefaCombinacao> combinacoes) {

        for (MoradorTarefaCombinacao mtc:combinacoes) {
            Morador infeliz = mtc.getMorador();
            Tarefa tarefa = mtc.getTarefa();
            dtm.addRow(new String[]{infeliz.getNome(), tarefa.getNome(), tarefa.getDescricao(), (tarefa.isFixa()?"Sim":"Não"), Miscelaneous.formatDateTime(mtc.getDataHoraAssociacao()), Miscelaneous.formatDate(mtc.getDataHoraLimite())});
        }

    }

    private void initialize() {
        frame = new JFrame();
        frame.setMinimumSize(new Dimension(800, 600));
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) { // recalculando o size das colunas
                int larguraScroll = scrollPane.getViewport().getWidth();
                int alturaScroll = scrollPane.getViewport().getHeight();
                int alturaLinha = alturaScroll/table.getRowCount();
                if(alturaLinha<40){ // tamanho mínimo é 40
                    table.setRowHeight(40);
                }else{
                    table.setRowHeight(alturaLinha);
                }
                // nome do morador
                table.getColumnModel().getColumn(0).setPreferredWidth((int)(larguraScroll*0.15)); //15% da largura do scroll
                // nome da tarefa
                table.getColumnModel().getColumn(1).setPreferredWidth((int)(larguraScroll*0.17)); //17% da largura do scroll
                // descrição da tarefa
                table.getColumnModel().getColumn(2).setPreferredWidth((int)(larguraScroll*0.4)); //40% da largura do scroll
                // fixa
                table.getColumnModel().getColumn(3).setPreferredWidth((int)(larguraScroll*0.05)); //5% da largura do scroll
                // data e hora do sorteio
                table.getColumnModel().getColumn(4).setPreferredWidth((int)(larguraScroll*0.15)); //15% da largura do scroll
                // data limite
                table.getColumnModel().getColumn(5).setPreferredWidth((int)(larguraScroll*0.08)); //8% da largura do scroll
            }
        });
        frame.setTitle("Sorteio de Tarefas");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setFont(new Font("Noto Sans", Font.PLAIN, 14));
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        SpringLayout springLayout = new SpringLayout();
        frame.getContentPane().setLayout(springLayout);

        JLabel lblTitulo = new JLabel("Escala de Tarefas");
        springLayout.putConstraint(SpringLayout.NORTH, lblTitulo, 0, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, lblTitulo, 10, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, lblTitulo, 30, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, lblTitulo, -10, SpringLayout.EAST, frame.getContentPane());
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Noto Sans", Font.PLAIN, 18));
        frame.getContentPane().add(lblTitulo);

        JSeparator separator = new JSeparator();
        springLayout.putConstraint(SpringLayout.NORTH, separator, 6, SpringLayout.SOUTH, lblTitulo);
        springLayout.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, lblTitulo);
        springLayout.putConstraint(SpringLayout.SOUTH, separator, 8, SpringLayout.SOUTH, lblTitulo);
        springLayout.putConstraint(SpringLayout.EAST, separator, 0, SpringLayout.EAST, lblTitulo);
        frame.getContentPane().add(separator);

        scrollPane = new JScrollPane();
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 8, SpringLayout.SOUTH, separator);
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, frame.getContentPane());
        frame.getContentPane().add(scrollPane);

        table = new JTable(dtm);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for(int i = 0; i<table.getColumnModel().getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setCellRenderer(new MultiLineCellRenderer());
        }
        scrollPane.setViewportView(table);
        visible();
    }
}
