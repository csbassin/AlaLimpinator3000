package org.see.modelo;

import org.see.config.StaticConfigObjects;
import org.see.exception.StringMuitoLongaException;

public class Morador {
    private String nome;
    private String email;

    public Morador(){}
    public Morador(String nome, String email){
        this.nome = nome;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws StringMuitoLongaException {
        if(email.length()> StaticConfigObjects.maxEmailLen){
            throw new StringMuitoLongaException("E-mail deve ter no máximo "+StaticConfigObjects.maxEmailLen+" caracteres.");
        }
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws StringMuitoLongaException {
        if(nome.length()>StaticConfigObjects.maxMoradorNameLen){
            throw new StringMuitoLongaException("Nome deve ter no máximo"+StaticConfigObjects.maxMoradorNameLen+"caracteres.");
        }
        this.nome = nome;
    }
}
