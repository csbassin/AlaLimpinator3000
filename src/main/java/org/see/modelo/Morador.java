package org.see.modelo;

import org.see.config.StaticConfigObjects;
import org.see.exception.StringMuitoLongaException;

public class Morador {
    private Integer index = null;
    private String nome;
    private String email;
    private boolean ativo;

    public Morador(){}
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

    public static int getMoradorSizeInBytes(){
        return StaticConfigObjects.maxMoradorNameLen+StaticConfigObjects.maxEmailLen+1; //1 é um byte que representa o boolean "ativo"
    }
}
