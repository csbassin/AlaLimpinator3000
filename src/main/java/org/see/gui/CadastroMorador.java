package org.see.gui;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;

import org.see.exception.EmailJaCadastradoException;
import org.see.modelo.Morador;
import org.see.repository.writer.MoradorWriter;
import org.see.service.MoradorService;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class CadastroMorador {

	private JFrame frame;
	private JButton btnSalvar;
	private JLabel lblNome;
	private JTextField txtNome;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JCheckBox chckAtivo;
	private MoradorService ms;
	
	public CadastroMorador() {
		try {
			ms = new MoradorService();
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
		frame.setTitle("Cadastro de Morador");
		frame.getContentPane().setFont(new Font("Noto Sans", Font.PLAIN, 14));
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JLabel lblNewLabel = new JLabel("Cadastrar Morador");
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
				String email = txtEmail.getText();
				boolean ativo = chckAtivo.isSelected();
				System.out.println(ativo);
				if(!nome.trim().isEmpty() && !email.trim().isEmpty()) {
					try {
						ms.cadastrar(new Morador(nome, email, ativo));
						txtNome.setText("");
						txtEmail.setText("");
						chckAtivo.setSelected(true);
						JOptionPane.showMessageDialog(null, "Morador cadastrado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					}catch(EmailJaCadastradoException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage(), "E-mail em uso", JOptionPane.ERROR_MESSAGE);
					}catch(Exception e3){
						JOptionPane.showMessageDialog(null, e3.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Há campos obrigatórios vazios. Preencha-os antes de continuar.", "Campos vazios", JOptionPane.WARNING_MESSAGE);
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
		
		lblEmail = new JLabel("E-mail:");
		springLayout.putConstraint(SpringLayout.NORTH, lblEmail, 6, SpringLayout.SOUTH, txtNome);
		springLayout.putConstraint(SpringLayout.WEST, lblEmail, 0, SpringLayout.WEST, lblNewLabel);
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		frame.getContentPane().add(lblEmail);
		
		txtEmail = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtEmail, 0, SpringLayout.SOUTH, lblEmail);
		springLayout.putConstraint(SpringLayout.WEST, txtEmail, 0, SpringLayout.WEST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.EAST, txtEmail, 0, SpringLayout.EAST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.EAST, txtNome, 0, SpringLayout.EAST, separator);

		txtEmail.setColumns(10);
		frame.getContentPane().add(txtEmail);
		
		chckAtivo = new JCheckBox("Ativo");
		chckAtivo.setSelected(true);
		springLayout.putConstraint(SpringLayout.NORTH, chckAtivo, 6, SpringLayout.SOUTH, txtEmail);
		springLayout.putConstraint(SpringLayout.WEST, chckAtivo, 0, SpringLayout.WEST, lblNewLabel);
		chckAtivo.setForeground(Color.WHITE);
		chckAtivo.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		chckAtivo.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(chckAtivo);
		frame.setBounds(100, 100, 640, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		visible();
	}
	
	private void visible() {
		frame.setVisible(true);
	}
}
