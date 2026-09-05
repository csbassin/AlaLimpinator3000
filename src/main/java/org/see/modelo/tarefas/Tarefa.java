package org.see.modelo.tarefas;

public class Tarefa {
    private String nome;
    private String descricao;
    private int numero;

    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }

    public String getDescricao() {return descricao;}

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {this.descricao = descricao;}
}
