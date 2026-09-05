package org.see.gui;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class CadastroTarefa {

    private JFrame frame;
    private JButton btnSalvar;
    private JLabel lblNome;
    private JTextField txtNome;
    private JLabel lblDescricao;
    private JTextField txtDescricao;
//    private TarefaWriter tw;

    public CadastroTarefa() {
//        try {
//            tw = new TarefaWriter();
//        }catch(FileNotFoundException e) {
//            e.printStackTrace();
//            return;
//        }
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Cadastro de Tarefa");
        frame.getContentPane().setFont(new Font("Noto Sans", Font.PLAIN, 14));
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        frame.setBounds(100, 100, 640, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        SpringLayout springLayout = new SpringLayout();
        frame.getContentPane().setLayout(springLayout);

        JLabel lblNewLabel = new JLabel("Cadastrar Tarefa");
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

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String descicao = txtDescricao.getText();

                if(!nome.trim().isEmpty() && !descicao.trim().isEmpty()) {
//                    try {
//                        tw.write(new Tarefa(nome, descicao, numero));
//                    }catch(Exception e2) {
//                        e2.printStackTrace();
//                    }
                    System.out.println("Tentou salvar!"); // Apenas para teste
                }else{
                    System.out.println("Criar janelas de erro - Campos vazios");
                }
            }
        });
        springLayout.putConstraint(SpringLayout.NORTH, btnSalvar, -45, SpringLayout.SOUTH, frame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, btnSalvar, -10, SpringLayout.SOUTH, frame.getContentPane());
        btnSalvar.setBorder(new LineBorder(Color.WHITE, 2));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setContentAreaFilled(false);
        btnSalvar.setFont(new Font("Noto Sans", Font.PLAIN, 16));
        springLayout.putConstraint(SpringLayout.WEST, btnSalvar, 10, SpringLayout.WEST, separator);
        springLayout.putConstraint(SpringLayout.EAST, btnSalvar, -10, SpringLayout.EAST, separator);
        frame.getContentPane().add(btnSalvar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
            }
        });
        springLayout.putConstraint(SpringLayout.NORTH, btnCancelar, -40, SpringLayout.NORTH, btnSalvar);
        springLayout.putConstraint(SpringLayout.WEST, btnCancelar, 0, SpringLayout.WEST, btnSalvar);
        springLayout.putConstraint(SpringLayout.EAST, btnCancelar, 0, SpringLayout.EAST, btnSalvar);
        springLayout.putConstraint(SpringLayout.SOUTH, btnCancelar, -7, SpringLayout.NORTH, btnSalvar);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Noto Sans", Font.PLAIN, 16));
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setBorder(new LineBorder(Color.WHITE, 2));
        frame.getContentPane().add(btnCancelar);

        lblNome = new JLabel("Nome:");
        springLayout.putConstraint(SpringLayout.NORTH, lblNome, 6, SpringLayout.SOUTH, separator);
        springLayout.putConstraint(SpringLayout.WEST, lblNome, 0, SpringLayout.WEST, lblNewLabel);
        lblNome.setForeground(Color.WHITE);
        lblNome.setFont(new Font("Noto Sans", Font.PLAIN, 14));
        frame.getContentPane().add(lblNome);

        txtNome = new JTextField();
        springLayout.putConstraint(SpringLayout.NORTH, txtNome, 4, SpringLayout.SOUTH, lblNome);
        springLayout.putConstraint(SpringLayout.WEST, txtNome, 0, SpringLayout.WEST, lblNewLabel);
        springLayout.putConstraint(SpringLayout.EAST, txtNome, 0, SpringLayout.EAST, lblNewLabel);
        frame.getContentPane().add(txtNome);
        txtNome.setColumns(10);

        lblDescricao = new JLabel("Descrição:");
        springLayout.putConstraint(SpringLayout.NORTH, lblDescricao, 6, SpringLayout.SOUTH, txtNome);
        springLayout.putConstraint(SpringLayout.WEST, lblDescricao, 0, SpringLayout.WEST, lblNewLabel);
        lblDescricao.setForeground(Color.WHITE);
        lblDescricao.setFont(new Font("Noto Sans", Font.PLAIN, 14));
        frame.getContentPane().add(lblDescricao);

        txtDescricao = new JTextField();
        springLayout.putConstraint(SpringLayout.NORTH, txtDescricao, 0, SpringLayout.SOUTH, lblDescricao);
        springLayout.putConstraint(SpringLayout.WEST, txtDescricao, 0, SpringLayout.WEST, lblNewLabel);
        springLayout.putConstraint(SpringLayout.EAST, txtDescricao, 0, SpringLayout.EAST, lblNewLabel);
        txtDescricao.setColumns(10);
        frame.getContentPane().add(txtDescricao);

        visible();
    }

    private void visible() {
        frame.setVisible(true);
    }
}