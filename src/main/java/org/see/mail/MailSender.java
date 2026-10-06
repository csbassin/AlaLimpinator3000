package org.see.mail;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.see.config.StaticConfigObjects;
import org.see.modelo.MoradorTarefaCombinacao;

public class MailSender {
		
	
	public void alertar(MoradorTarefaCombinacao mtc) {
	
		//envioEmail();
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
		props.put("mail.debug", "true");
        
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

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
