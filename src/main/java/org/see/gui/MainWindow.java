package org.see.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow {

	private JFrame frame;
	private JButton btnCadastrarMorador;
	private JButton btnCadastrarTarefa;

	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
			}
		});
		frame.setTitle("Combate à porcaria: Por Cauã Bassin e Eric Maia Dias");
		frame.getContentPane().setFont(new Font("Noto Sans", Font.PLAIN, 14));
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JLabel lblNewLabel = new JLabel("AlaLimpinator3000");
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

		btnCadastrarMorador = new JButton("Cadastrar Morador");
		btnCadastrarMorador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CadastroMorador();
			}
		});

		btnCadastrarMorador.setBorder(new LineBorder(Color.WHITE, 2));
		btnCadastrarMorador.setForeground(Color.WHITE);
		btnCadastrarMorador.setContentAreaFilled(false);
		btnCadastrarMorador.setFont(new Font("Noto Sans", Font.PLAIN, 16));
		springLayout.putConstraint(SpringLayout.NORTH, btnCadastrarMorador, 6, SpringLayout.SOUTH, separator);
		springLayout.putConstraint(SpringLayout.WEST, btnCadastrarMorador, 10, SpringLayout.WEST, separator);
		springLayout.putConstraint(SpringLayout.SOUTH, btnCadastrarMorador, 40, SpringLayout.SOUTH, separator);
		springLayout.putConstraint(SpringLayout.EAST, btnCadastrarMorador, -10, SpringLayout.EAST, separator);
		frame.getContentPane().add(btnCadastrarMorador);

		btnCadastrarTarefa = new JButton("Cadastrar Tarefa");
		btnCadastrarTarefa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CadastroTarefa();
			}
		});
		btnCadastrarTarefa.setBorder(new LineBorder(Color.WHITE, 2));
		btnCadastrarTarefa.setForeground(Color.WHITE);
		btnCadastrarTarefa.setContentAreaFilled(false);
		btnCadastrarTarefa.setFont(new Font("Noto Sans", Font.PLAIN, 16));
		springLayout.putConstraint(SpringLayout.NORTH, btnCadastrarTarefa, 10, SpringLayout.SOUTH, btnCadastrarMorador);
		springLayout.putConstraint(SpringLayout.WEST, btnCadastrarTarefa, 10, SpringLayout.WEST, separator);
		springLayout.putConstraint(SpringLayout.SOUTH, btnCadastrarTarefa, 40, SpringLayout.SOUTH, btnCadastrarMorador);
		springLayout.putConstraint(SpringLayout.EAST, btnCadastrarTarefa, -10, SpringLayout.EAST, separator);
		frame.getContentPane().add(btnCadastrarTarefa);
		
		JButton btnListarMoradores = new JButton("Visualizar Moradores Cadastrados");
		btnListarMoradores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ListagemMorador();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnListarMoradores, 6, SpringLayout.SOUTH, btnCadastrarTarefa);
		springLayout.putConstraint(SpringLayout.WEST, btnListarMoradores, 0, SpringLayout.WEST, btnCadastrarTarefa);
		springLayout.putConstraint(SpringLayout.EAST, btnListarMoradores, 0, SpringLayout.EAST, btnCadastrarTarefa);
		btnListarMoradores.setForeground(Color.WHITE);
		btnListarMoradores.setFont(new Font("Noto Sans", Font.PLAIN, 16));
		btnListarMoradores.setContentAreaFilled(false);
		btnListarMoradores.setBorder(new LineBorder(Color.WHITE, 2));
		frame.getContentPane().add(btnListarMoradores);

		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}