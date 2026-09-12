package org.see.gui;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.JSeparator;
import javax.swing.table.DefaultTableModel;

import org.see.modelo.Tarefa;
import org.see.service.TarefaService;

import java.io.IOException;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ListagemTarefa {

    private JFrame frame;
    private TarefaService ts;
    private JTable table;
    private JScrollPane scrollPane;
    private String[] title = new String[]{"Nome", "Descrição", "Morador Associado"};
    private DefaultTableModel dtm = new DefaultTableModel(title, 0);


    public ListagemTarefa() {
        try {
            ts = new TarefaService();
        }catch(IOException e) {
            e.printStackTrace();
            return;
        }
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                long start = System.currentTimeMillis();
                try {
                    List<Tarefa> tarefas = ts.readAll();
                    for(Tarefa tarefa:tarefas) {
                        System.out.println(tarefa.getNome());
                        addToTable(tarefa);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.out.println("Elapsed time: "+(System.currentTimeMillis()-start)+" ms");
            }
        });
        frame.setTitle("Cadastro de Tarefa");
        frame.getContentPane().setFont(new Font("Noto Sans", Font.PLAIN, 14));
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        SpringLayout springLayout = new SpringLayout();
        frame.getContentPane().setLayout(springLayout);

        JLabel lblNewLabel = new JLabel("Tarefas Cadastradas");
        springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 10, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, 30, SpringLayout.NORTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, -10, SpringLayout.EAST, frame.getContentPane());
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Noto Sans", Font.PLAIN, 18));
        frame.getContentPane().add(lblNewLabel);

        JSeparator separator = new JSeparator();
        springLayout.putConstraint(SpringLayout.NORTH, separator, 6, SpringLayout.SOUTH, lblNewLabel);
        springLayout.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, lblNewLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, separator, 8, SpringLayout.SOUTH, lblNewLabel);
        springLayout.putConstraint(SpringLayout.EAST, separator, 0, SpringLayout.EAST, lblNewLabel);
        frame.getContentPane().add(separator);

        scrollPane = new JScrollPane();
        springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 8, SpringLayout.SOUTH, separator);
        springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, frame.getContentPane());
        frame.getContentPane().add(scrollPane);

        table = new JTable(dtm);
        scrollPane.setViewportView(table);
        frame.setBounds(100, 100, 640, 480);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        visible();
    }

    private void addToTable(Tarefa tarefa) {
        dtm.addRow(new String[]{tarefa.getNome(), tarefa.getDescricao(),
                tarefa.getMoradorAssociado() != null ? tarefa.getMoradorAssociado().getNome() : "ninguém"});
    }

    private void visible() {
        frame.setVisible(true);
    }
}
