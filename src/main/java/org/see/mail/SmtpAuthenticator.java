package org.see.mail;


import org.see.config.StaticConfigObjects;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class SmtpAuthenticator extends Authenticator {
	
	public SmtpAuthenticator() {
	    super();
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
	    if ((StaticConfigObjects.EMAIL != null) && (!StaticConfigObjects.EMAIL.isEmpty()) && (StaticConfigObjects.password != null)&& (!StaticConfigObjects.password.isEmpty())) {
	        return new PasswordAuthentication(StaticConfigObjects.EMAIL, StaticConfigObjects.password.toCharArray());
	    }

	    return null;
	}

}
