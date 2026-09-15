package org.see.gui;

import org.see.config.StaticConfigObjects;
import org.see.service.SenhaService;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow {

	private JFrame frame;
	private JButton btnCadastrarMorador;
	private JButton btnCadastrarTarefa;
	private JButton btnEditarMorador;
	private SenhaService senhaService;

	public static void main(String[] args) {
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
			return;
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
		try{
			senhaService = new SenhaService();
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		String senha = senhaService.read();
		if(senha == null){
			senha = JOptionPane.showInputDialog("Qual a senha do e-mail?");
			try{
				senhaService.write(senha);
			}catch (Exception e){
				JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		StaticConfigObjects.password = senha;

		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
			}
		});
		frame.setTitle("AlaLimpinator3000™");
		frame.getContentPane().setFont(new Font("Noto Sans", Font.PLAIN, 14));
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JLabel lblNewLabel = new JLabel("Combate à porcaria");
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

		btnEditarMorador = new JButton("Editar Morador");
		btnEditarMorador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CadastroMorador(CadastroMorador.EDITAR);
			}
		});

		btnEditarMorador.setBorder(new LineBorder(Color.WHITE, 2));
		btnEditarMorador.setForeground(Color.WHITE);
		btnEditarMorador.setContentAreaFilled(false);
		btnEditarMorador.setFont(new Font("Noto Sans", Font.PLAIN, 16));
		springLayout.putConstraint(SpringLayout.NORTH, btnEditarMorador, 6, SpringLayout.SOUTH, btnCadastrarMorador);
		springLayout.putConstraint(SpringLayout.WEST, btnEditarMorador, 10, SpringLayout.WEST, separator);
		springLayout.putConstraint(SpringLayout.SOUTH, btnEditarMorador, 40, SpringLayout.SOUTH, btnCadastrarMorador);
		springLayout.putConstraint(SpringLayout.EAST, btnEditarMorador, -10, SpringLayout.EAST, separator);
		frame.getContentPane().add(btnEditarMorador);

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
		springLayout.putConstraint(SpringLayout.NORTH, btnCadastrarTarefa, 10, SpringLayout.SOUTH, btnEditarMorador);
		springLayout.putConstraint(SpringLayout.WEST, btnCadastrarTarefa, 10, SpringLayout.WEST, separator);
		springLayout.putConstraint(SpringLayout.SOUTH, btnCadastrarTarefa, 40, SpringLayout.SOUTH, btnEditarMorador);
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

		JButton btnListarTarefas = new JButton("Visualizar Tarefas Cadastradas");
		btnListarTarefas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ListagemTarefa();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnListarTarefas, 6, SpringLayout.SOUTH, btnListarMoradores);
		springLayout.putConstraint(SpringLayout.WEST, btnListarTarefas, 0, SpringLayout.WEST, btnListarMoradores);
		springLayout.putConstraint(SpringLayout.EAST, btnListarTarefas, 0, SpringLayout.EAST, btnListarMoradores);
		btnListarTarefas.setForeground(Color.WHITE);
		btnListarTarefas.setFont(new Font("Noto Sans", Font.PLAIN, 16));
		btnListarTarefas.setContentAreaFilled(false);
		btnListarTarefas.setBorder(new LineBorder(Color.WHITE, 2));
		frame.getContentPane().add(btnListarTarefas);

		JButton btnSortearTarefas = new JButton("Gerar escala de limpeza");
		btnSortearTarefas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SorteioTarefas();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnSortearTarefas, 6, SpringLayout.SOUTH, btnListarTarefas);
		springLayout.putConstraint(SpringLayout.WEST, btnSortearTarefas, 0, SpringLayout.WEST, btnListarTarefas);
		springLayout.putConstraint(SpringLayout.EAST, btnSortearTarefas, 0, SpringLayout.EAST, btnListarTarefas);
		btnSortearTarefas.setForeground(Color.WHITE);
		btnSortearTarefas.setFont(new Font("Noto Sans", Font.PLAIN, 16));
		btnSortearTarefas.setContentAreaFilled(false);
		btnSortearTarefas.setBorder(new LineBorder(Color.WHITE, 2));
		frame.getContentPane().add(btnSortearTarefas);

		JLabel lblCreditos = new JLabel("<html>Por Cauã Bassin e Eric Maia<br>feat: Luiz Eduardo<html>");
		lblCreditos.setForeground(Color.WHITE);
		lblCreditos.setFont(new Font("Noto Sans", Font.PLAIN, 15));
		springLayout.putConstraint(SpringLayout.SOUTH, lblCreditos, -5, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblCreditos, -10, SpringLayout.EAST, frame.getContentPane());

		frame.getContentPane().add(lblCreditos);

		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}