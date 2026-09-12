package org.see.gui;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import org.see.modelo.Morador;
import org.see.modelo.Tarefa;
import org.see.service.MoradorService;
import org.see.service.TarefaService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class CadastroTarefa {

    private JFrame frame;
    private JButton btnSalvar;
    private JLabel lblNome;
    private JTextField txtNome;
    private JLabel lblDescricao;
    private JTextField txtDescricao;
    private JCheckBox chckFixa;
    private JComboBox cmbMorador;
    
    private TarefaService ts;
    private MoradorService ms;

    public CadastroTarefa() {
    	try {
    		ts = new TarefaService();
    		ms = new MoradorService();
    	}catch(Exception e) { // TODO tratar direito // seria legal tratar direito... mas precisa?
    		System.out.println(e.getMessage());
    		return;
    	}
    	
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowOpened(WindowEvent e) {
        		try {
        			for(Morador m:ms.readAll()) {
        				if(m.isAtivo()) {
        					cmbMorador.addItem(m);
        				}
        			}
        			
        		}catch(Exception eq) {
        			eq.printStackTrace();
        			frame.dispose();
        		}
        		
        	}
        });
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
                Morador morador = null;

                if(chckFixa.isSelected()) {
                    morador = (Morador) cmbMorador.getSelectedItem();
                }

                if(!nome.trim().isEmpty() && !descicao.trim().isEmpty()) {
                    try {
                        ts.cadastrar(new Tarefa(nome, descicao, morador));
                    }catch(Exception e2) {
                        e2.printStackTrace();
                    }
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
        
        chckFixa = new JCheckBox("Fixa");
        chckFixa.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent arg0) {
        		if(arg0.getStateChange() == ItemEvent.SELECTED) {
        			cmbMorador.setEnabled(true);
        		}else {
        			cmbMorador.setEnabled(false);
        		}
        	}
        });
        springLayout.putConstraint(SpringLayout.NORTH, chckFixa, 6, SpringLayout.SOUTH, txtDescricao);
        springLayout.putConstraint(SpringLayout.WEST, chckFixa, 0, SpringLayout.WEST, lblNewLabel);
        chckFixa.setForeground(Color.WHITE);
        chckFixa.setFont(new Font("Noto Sans", Font.PLAIN, 14));
        chckFixa.setBackground(Color.DARK_GRAY);
        frame.getContentPane().add(chckFixa);
        
        cmbMorador = new JComboBox();
        cmbMorador.setEnabled(false);
        springLayout.putConstraint(SpringLayout.WEST, cmbMorador, 0, SpringLayout.WEST, lblNewLabel);
        springLayout.putConstraint(SpringLayout.EAST, cmbMorador, 0, SpringLayout.EAST, txtDescricao);
        frame.getContentPane().add(cmbMorador);
        
        JLabel lblMorador = new JLabel("Morador Responsável:");
        springLayout.putConstraint(SpringLayout.NORTH, lblMorador, 4, SpringLayout.SOUTH, chckFixa);
        springLayout.putConstraint(SpringLayout.NORTH, cmbMorador, 6, SpringLayout.SOUTH, lblMorador);
        springLayout.putConstraint(SpringLayout.WEST, lblMorador, 0, SpringLayout.WEST, lblNewLabel);
        lblMorador.setForeground(Color.WHITE);
        lblMorador.setFont(new Font("Noto Sans", Font.PLAIN, 14));
        frame.getContentPane().add(lblMorador);

        visible();
    }

    private void visible() {
        frame.setVisible(true);
    }
}