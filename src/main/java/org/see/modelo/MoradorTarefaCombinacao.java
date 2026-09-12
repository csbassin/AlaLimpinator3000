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
    private LocalDateTime dataHoraAssociacao;
    private boolean feita;
    private LocalDate dataHoraLimite;

    /**
     * Use este construtor no momento que for sortear a tarefa.
     * "Feita" é definida como false, a data e hora do sorteio é agora e a data limite é calculada somando o prazo à ela.
     * @param morador: O morador sorteado
     * @param tarefa: A tarefa sorteada para ele.
     */
    public MoradorTarefaCombinacao(Morador morador, Tarefa tarefa){
        this.morador = morador;
        this.tarefa = tarefa;
        dataHoraAssociacao = LocalDateTime.now();
        feita = false;
        dataHoraLimite = (dataHoraAssociacao.toLocalDate()).plusDays(StaticConfigObjects.PRAZO_REALIZACAO_TAREFA_DIAS);
    }

    /**
     * Use este construtor para quando for ler do DB.
     * @param numero: O índice autogerado
     * @param morador
     * @param tarefa
     * @param dataHoraAssociacao
     * @param feita
     * @param dataHoraLimite
     */
    public MoradorTarefaCombinacao(int numero, Morador morador, Tarefa tarefa, LocalDateTime dataHoraAssociacao, boolean feita, LocalDate dataHoraLimite){
        this.dataHoraAssociacao = dataHoraAssociacao;
        this.dataHoraLimite = dataHoraLimite;
        this.morador = morador;
        this.feita = feita;
        this.tarefa = tarefa;
        this.numero = numero;
    }
    @GetSize
    public static int getCombinacaoSizeInBytes(){
        return 25; // 4 do id do morador, 4 do id da tarefa, 8 do long de epoch da data, 1 do boolean, 8 da outra epoch da outra data
    }

    @Override
    public String toString(){
        return "Morador: "+morador.getNome()+". Tarefa: "+tarefa.getNome()+". Feita: "+(feita?"Sim":"Não")+". Data e hora do sorteio: "+ Miscelaneous.formatDateTime(dataHoraAssociacao)+". Data Limite para realização: "+Miscelaneous.formatDate(dataHoraLimite);
    }

    public LocalDate getDataHoraLimite() {
        return dataHoraLimite;
    }

    public void setDataHoraLimite(LocalDate dataHoraLimite) {
        this.dataHoraLimite = dataHoraLimite;
    }

    public boolean isFeita() {
        return feita;
    }

    public void setFeita(boolean feita) {
        this.feita = feita;
    }

    public LocalDateTime getDataHoraAssociacao() {
        return dataHoraAssociacao;
    }

    public void setDataHoraAssociacao(LocalDateTime dataHoraAssociacao) {
        this.dataHoraAssociacao = dataHoraAssociacao;
    }

    public Tarefa getTarefa() {
        return tarefa;
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
