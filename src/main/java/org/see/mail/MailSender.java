package org.see.mail;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.see.Miscelaneous;
import org.see.config.StaticConfigObjects;
import org.see.modelo.MoradorTarefaCombinacao;
import org.see.modelo.Sorteio;

public class MailSender {
		
	
	public static void alertarMoradores(Sorteio sorteio) {
		String subject = "Tarefas da semana sorteadas - "+Miscelaneous.formatDate(sorteio.getDataHoraSorteio().toLocalDate()); 
		String body = "Olá, ";
		List<MoradorTarefaCombinacao> mtces = sorteio.getAssociacoes();
		List<Integer> jaNotificados = new ArrayList<>();
		for(int i = 0; i<mtces.size(); i++) {
			if(!jaNotificados.contains(mtces.get(i).getMorador().getIndex())) {
				body = "Olá, ";
				body += mtces.get(i).getMorador().getNome()+".\nSuas tarefas para a semana são:\n";
				for(int j = i; j<mtces.size(); j++) { 
					if(mtces.get(i).getMorador().getIndex().equals(mtces.get(j).getMorador().getIndex())) { // se são o mesmo morador
						body+=mtces.get(j).getTarefa().getNome()+";\n";
					}
				}
				body+="Data limite para entrega: "+Miscelaneous.formatDate(sorteio.getDataLimite());
				envioEmail(subject, body, mtces.get(i).getMorador().getEmail());
				jaNotificados.add(mtces.get(i).getMorador().getIndex());
			}
		}
	}
	public static void dedurarMorador() {
		
	}
	public static void sendTeste() {
		envioEmail("Teste", "Email de teste", "cauabassin@outlook.com");
	}
	
	public static void envioEmail(String assunto, String corpo, String destinatario) {
		SmtpAuthenticator pass =  new SmtpAuthenticator();
		Properties props = new Properties();
		
		props.put("mail.smtp.host", StaticConfigObjects.SMTP_MAIL_SERVER);
		props.put("mail.smtp.port", StaticConfigObjects.PORTA_SERVIDOR_EMAIL);
		props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
		//props.put("mail.debug", "true");
        
		Session session = Session.getInstance(props);
		
		try {
			System.out.println(StaticConfigObjects.password);
			Message mensagem = new MimeMessage(Session.getDefaultInstance(props, pass));
			mensagem.setFrom(new InternetAddress(StaticConfigObjects.EMAIL));
			mensagem.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			mensagem.setSubject(assunto);
			mensagem.setSentDate(Date.from(Instant.now()));
			mensagem.setText(corpo);
			Transport transport = session.getTransport("smtp");
            transport.connect(StaticConfigObjects.SMTP_MAIL_SERVER , StaticConfigObjects.PORTA_SERVIDOR_EMAIL , StaticConfigObjects.EMAIL, StaticConfigObjects.password);
			Transport.send(mensagem);
			System.out.println("Email enviado para "+destinatario);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
