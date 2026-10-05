package org.see.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.see.exception.EmailJaCadastradoException;
import org.see.modelo.Morador;
import org.see.modelo.MoradorTarefaCombinacao;
import org.see.modelo.Sorteio;
import org.see.repository.writer.MoradorWriter;
import org.see.service.MoradorService;
import org.see.service.MoradorTarefaCombinacaoService;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditarSorteio {

	private JFrame frame;
	private JButton btnSalvar;
	private JCheckBox chckFeita;
	private MoradorService ms;
	
	private Sorteio sorteio;
	private List<MoradorTarefaCombinacao> alterados = new ArrayList<>();
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel dtm = new DefaultTableModel(new String[]{"Morador", "Tarefa", "Feita"}, 0);
	
	MoradorTarefaCombinacao selecionado = null;
	private JComboBox<MoradorTarefaCombinacao> cmbEditar;

	public EditarSorteio(Sorteio sorteio) {
		this.sorteio = sorteio;
		for(MoradorTarefaCombinacao mtc:sorteio.getAssociacoes()){
			alterados.add(new MoradorTarefaCombinacao(mtc.getNumero(), mtc.getMorador(), mtc.getTarefa(), mtc.isFeita(), mtc.getSorteio()));
		}
		System.out.println(sorteio.toString());
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Edição de status");
		frame.getContentPane().setFont(new Font("Noto Sans", Font.PLAIN, 14));
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		JLabel lblNewLabel = new JLabel("Editar Status das Tarefas");

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
		btnSalvar.setEnabled(false);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					MoradorTarefaCombinacaoService mtcs = new MoradorTarefaCombinacaoService();
					for(MoradorTarefaCombinacao alt:alterados){
						mtcs.update(alt);
					}
				}catch (Exception e1){
					JOptionPane.showMessageDialog(null, "Erro", e1.getMessage(), JOptionPane.ERROR_MESSAGE);
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
		
		chckFeita = new JCheckBox("Feita");
		springLayout.putConstraint(SpringLayout.WEST, chckFeita, 0, SpringLayout.WEST, btnSalvar);
		springLayout.putConstraint(SpringLayout.SOUTH, chckFeita, -6, SpringLayout.NORTH, btnCancelar);
		chckFeita.setSelected(false);
		chckFeita.setForeground(Color.WHITE);
		chckFeita.setFont(new Font("Noto Sans", Font.PLAIN, 14));
		chckFeita.setBackground(Color.DARK_GRAY);
		chckFeita.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent arg0) {
        		System.out.println("Evento disparado");
        		if(arg0.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("Modificada");// crio a modificada
					alterados.get(cmbEditar.getSelectedIndex()).setFeita(true);
					dtm.setValueAt("Sim", cmbEditar.getSelectedIndex(), 2);
        			
        		}else if(arg0.getStateChange() == ItemEvent.DESELECTED){ // quando eu deselecionar, preciso procurar na lista alterados se ele está lá
        			int index = -1;
        			for(int i = 0; i<alterados.size(); i++) {
        				if(alterados.get(i).getNumero().equals(selecionado.getNumero())) { // se for o mesmo
        					index = i;
        					break;
        				}
        			}
        			if(index!=-1) {
						alterados.get(cmbEditar.getSelectedIndex()).setFeita(false);
        				dtm.setValueAt("Não", cmbEditar.getSelectedIndex(), 2);
        			}
        		}
                btnSalvar.setEnabled(!alterados.isEmpty());
				System.out.println("Alterados==================");
				for(MoradorTarefaCombinacao m: alterados){
					System.out.println(m.toString()+"\n==================================\n");
				}
			}
        });
		frame.getContentPane().add(chckFeita);
		
		scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, separator);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, separator);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -40, SpringLayout.NORTH, chckFeita);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, lblNewLabel);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable(dtm);
		table.setRowSelectionAllowed(false);
		scrollPane.setViewportView(table);
		
		cmbEditar = new JComboBox<>();
		cmbEditar.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("Selecionado");
					chckFeita.setSelected(((MoradorTarefaCombinacao) cmbEditar.getSelectedItem()).isFeita());
					selecionado = (MoradorTarefaCombinacao) cmbEditar.getSelectedItem();
				}
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, cmbEditar, 0, SpringLayout.WEST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, cmbEditar, -6, SpringLayout.NORTH, chckFeita);
		springLayout.putConstraint(SpringLayout.EAST, cmbEditar, 0, SpringLayout.EAST, scrollPane);
		frame.getContentPane().add(cmbEditar);
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		for(MoradorTarefaCombinacao mtc:sorteio.getAssociacoes()) {
			addToTable(mtc);
			addToComboBox(mtc);
		}
		visible();
	}
	
	private void addToTable(MoradorTarefaCombinacao mtc) {
		dtm.addRow(new String[]{mtc.getMorador().getNome(), mtc.getTarefa().getNome(), mtc.isFeita()?"Sim":"Não"});
	}
	private void addToComboBox(MoradorTarefaCombinacao mtc) {
		cmbEditar.addItem(mtc);
	}
	private void visible() {
		frame.setVisible(true);
	}
}
