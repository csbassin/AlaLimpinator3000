package org.see.modelo;

import org.see.annotation.GetSize;
import org.see.annotation.Index;
import org.see.config.StaticConfigObjects;

public class Tarefa {
    private String nome;
    private String descricao;
    @Index
    private Integer numero;
    private Morador moradorAssociado;

    public Tarefa(String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;
    }
    public Tarefa(String nome, String descricao, Morador morador){
        this.nome = nome;
        this.descricao = descricao;
        this.moradorAssociado = morador;
    }

    @Override
    public boolean equals(Object obj){
        return numero.equals(((Tarefa)obj).getNumero());
    }

    public boolean isFixa(){
        return moradorAssociado!=null;
    }

    public String getNome() {
        return nome;
    }
    @GetSize
    public static int getTarefaSizeInBytes(){
        return StaticConfigObjects.MAX_TAREFA_DESCRICAO_LEN+StaticConfigObjects.MAX_TAREFA_NAME_LEN+5; //1 byte vai dizer se é Fixa ou não, 4 bytes representam o index do morador
    }

    public Integer getNumero() {
        return numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Morador getMoradorAssociado() {
        return moradorAssociado;
    }

    public void setMoradorAssociado(Morador moradorAssociado) {
        this.moradorAssociado = moradorAssociado;
    }

    public void setDescricao(String descricao) {this.descricao = descricao;}
}
