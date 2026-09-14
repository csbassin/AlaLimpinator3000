package org.see.modelo;

import org.see.Miscelaneous;
import org.see.annotation.GetSize;
import org.see.annotation.Index;
import org.see.config.StaticConfigObjects;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MoradorTarefaCombinacao {
    @Index
    private Integer numero;
    private Morador morador;
    private Tarefa tarefa;
    private boolean feita;
    private Integer sorteio;

    /**
     * Use este construtor no momento que for sortear a tarefa.
     * "Feita" é definida como false, a data e hora do sorteio é agora e a data limite é calculada somando o prazo à ela.
     * @param morador: O morador sorteado
     * @param tarefa: A tarefa sorteada para ele.
     */
    public MoradorTarefaCombinacao(Morador morador, Tarefa tarefa){
        this.morador = morador;
        this.tarefa = tarefa;
        feita = false;
    }

    /**
     * Use este construtor para quando for ler do DB.
     * @param numero: O índice autogerado
     * @param morador
     * @param tarefa
     * @param feita
     */
    public MoradorTarefaCombinacao(int numero, Morador morador, Tarefa tarefa, boolean feita, Integer sorteio){
        this.morador = morador;
        this.feita = feita;
        this.tarefa = tarefa;
        this.numero = numero;
        this.sorteio = sorteio;
    }
    @GetSize
    public static int getCombinacaoSizeInBytes(){
        return 13; // 4 do id do morador, 4 do id da tarefa, 1 do boolean, 4 do numero do sorteio
    }

    @Override
    public String toString(){
        return "Morador: "+morador.getNome()+". Tarefa: "+tarefa.getNome()+". Feita: "+(feita?"Sim":"Não")+". Data e hora do sorteio: ";
    }

    public boolean isFeita() {
        return feita;
    }

    public void setFeita(boolean feita) {
        this.feita = feita;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public Integer getSorteio() {
        return sorteio;
    }

    public void setSorteio(Integer sorteio) {
        this.sorteio = sorteio;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
