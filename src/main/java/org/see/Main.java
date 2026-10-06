package org.see;

import org.see.config.StaticConfigObjects;
import org.see.exception.NaoHaMaisTarefasException;
import org.see.mail.MailSender;
import org.see.modelo.Morador;

import org.see.modelo.MoradorTarefaCombinacao;
import org.see.modelo.Tarefa;
import org.see.service.MoradorService;
import org.see.service.MoradorTarefaCombinacaoService;
import org.see.service.SenhaService;
import org.see.service.SorteioService;
import org.see.service.TarefaService;

import java.util.List;

import javax.swing.JOptionPane;

public class Main {
    static void main() {
        teste();
    }
    private static void teste(){
        try{
        	SenhaService senhaService = new SenhaService();

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
            MailSender.sendTeste();

        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("teste falhou");
        }
    }
}
