package org.see.gui;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JSeparator;
import javax.swing.table.DefaultTableModel;

import org.see.Miscelaneous;
import org.see.modelo.Sorteio;
import org.see.modelo.Tarefa;
import org.see.service.SorteioService;
import org.see.service.TarefaService;

import java.io.IOException;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ListagemSorteio {

    private JFrame frame;
    private SorteioService ss;
    private JTable table;
    private JScrollPane scrollPane;
    private String[] title = new String[]{"Número", "Data do sorteio", "Data Limite"};
    private DefaultTableModel dtm = new DefaultTableModel(title, 0);
    private List<Sorteio> sorteios;

    public ListagemSorteio() {
        try {
            ss = new SorteioService();
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
                    sorteios = ss.readAll();
                    for(Sorteio sorteio:sorteios) {
                        addToTable(sorteio);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.out.println("Elapsed time: "+(System.currentTimeMillis()-start)+" ms");
            }
        });
        frame.setTitle("Sorteios realizados");
        frame.getContentPane().setFont(new Font("Noto Sans", Font.PLAIN, 14));
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        SpringLayout springLayout = new SpringLayout();
        frame.getContentPane().setLayout(springLayout);

        JLabel lblNewLabel = new JLabel("Sorteios realizados - clique para detalhes");
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
        table.setRowHeight(40);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting() && table.getSelectedRowCount()>0){
					System.out.println(e.getFirstIndex());
					new EditarSorteio(sorteios.get(e.getFirstIndex()));
					table.clearSelection();
				}
			}
        	
        });
        scrollPane.setViewportView(table);
        frame.setBounds(100, 100, 640, 480);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        visible();
    }

    private void addToTable(Sorteio sorteio) {
        dtm.addRow(new String[]{sorteio.getNumero().toString(), Miscelaneous.formatDateTime(sorteio.getDataHoraSorteio()), Miscelaneous.formatDate(sorteio.getDataLimite())});
    }

    private void visible() {
        frame.setVisible(true);
    }
}
