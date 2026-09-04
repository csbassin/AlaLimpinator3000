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

public class CadastroMorador {

	private JFrame frame;
	private JButton btnSalvar;

	
	public CadastroMorador() {
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
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		visible();
	}
	
	private void visible() {
		frame.setVisible(true);
	}
	
}
