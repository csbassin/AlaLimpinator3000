package org.see.gui;


import org.see.exception.NaoHaMaisTarefasException;
import org.see.modelo.Morador;
import org.see.modelo.Tarefa;
import org.see.service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class SorteioTarefas { //eu sei que o nome ficou ruim.
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private Map<Morador, List<Tarefa>> escala = new HashMap<Morador, List<Tarefa>>();
    private TarefaService ts;
    private MoradorService ms;
    private String[] title = {"Morador", "Tarefa", "Descrição"};
    private DefaultTableModel dtm = new DefaultTableModel(title, 0);
//    private Random rand = new Random();

    public SorteioTarefas() {
        try {
            ts = new TarefaService();
            ms = new MoradorService();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        tarefaAleatorinator();
        tabelaPreenchinator();
        initialize();
    }

    public Map<Morador, List<Tarefa>> tarefaAleatorinator() {
        try {
            List<Morador> moradores = ms.readAll();

            for (Morador morador : moradores) {
                if (morador.isAtivo()) {
                    escala.put(morador, new ArrayList<>());
                }
            }

            if (escala.isEmpty()) {
                return escala;
            }

            boolean acabou = false;

            while (!acabou) {
                for (Morador morador : escala.keySet()) {
                    try {
                        Tarefa tarefa = ts.readRandom();
                        escala.get(morador).add(tarefa);
                    } catch (NaoHaMaisTarefasException e) {
                        acabou = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return escala;
    }

    private void visible() {
        frame.setVisible(true);
    }

    private void tabelaPreenchinator() {
        for (Map.Entry<Morador, List<Tarefa>> entrada : escala.entrySet()) {

            Morador infeliz = entrada.getKey();
            List<Tarefa> tribulacoes = entrada.getValue();

            for (Tarefa tarefa : tribulacoes) {
                dtm.addRow(new String[]{
                        infeliz.getNome(),
                        tarefa.getNome(),
                        tarefa.getDescricao()
                });
            }
        }
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Sorteio de Tarefas");
        frame.setBounds(100, 100, 640, 480);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setFont(new Font("Noto Sans", Font.PLAIN, 14));
        frame.getContentPane().setBackground(Color.DARK_GRAY);

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
        scrollPane.setViewportView(table);
        visible();
    }
}
