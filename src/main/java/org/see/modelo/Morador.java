package org.see.modelo;

import org.see.annotation.GetSize;
import org.see.annotation.Index;
import org.see.config.StaticConfigObjects;
import org.see.exception.StringMuitoLongaException;

public class Morador {
    @Index
    private Integer index = null;
    private String nome;
    private String email;
    private boolean ativo;
    public static long fakeSize;

    public Morador(String nome, String email, boolean ativo){
        this.nome = nome;
        this.email = email;
        this.ativo = ativo;
    }
    public Morador(int index, String nome, String email, boolean ativo){
        this.nome = nome;
        this.email = email;
        this.index = index;
        this.ativo = ativo;
    }

    public Integer getIndex() {
        return index;
    }
    @Override
    public boolean equals(Object obj){
        return index.equals(((Morador)obj).getIndex());
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // só quem vai mexer no index é o writer
    public void setIndex(int index) {
        this.index = index;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws StringMuitoLongaException {
        if(email.length()> StaticConfigObjects.MAX_EMAIL_LEN){
            throw new StringMuitoLongaException("E-mail deve ter no máximo "+StaticConfigObjects.MAX_EMAIL_LEN +" caracteres.");
        }
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws StringMuitoLongaException {
        if(nome.length()>StaticConfigObjects.MAX_MORADOR_NAME_LEN){
            throw new StringMuitoLongaException("Nome deve ter no máximo"+StaticConfigObjects.MAX_MORADOR_NAME_LEN +"caracteres.");
        }
        this.nome = nome;
    }

    @GetSize
    public static int getMoradorSizeInBytes(){
        return StaticConfigObjects.MAX_MORADOR_NAME_LEN +StaticConfigObjects.MAX_EMAIL_LEN +1; //1 é um byte que representa o boolean "ativo"
    }

    @Override
    public String toString() {
        return "Morador " + index + ": " + nome + ", "  + email + ", ativo: " + ativo;
    }
}
