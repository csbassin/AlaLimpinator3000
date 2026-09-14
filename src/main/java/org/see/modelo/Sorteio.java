package org.see.modelo;

import org.see.annotation.GetSize;
import org.see.annotation.Index;
import org.see.config.StaticConfigObjects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Sorteio { // vai guardar as datas do sorteio e a data limite para as tarefas, em vez de guardar direto na associação
    @Index
    private Integer numero;
    private LocalDateTime dataHoraSorteio;
    private LocalDate dataLimite;
    //salvo em arquivo separado
    private List<MoradorTarefaCombinacao> associacoes;

    /**
     * Usar esse construtor na hora de sortear
     */
    public Sorteio(){
        this.dataHoraSorteio = LocalDateTime.now();
        this.dataLimite = dataHoraSorteio.toLocalDate().plusDays(StaticConfigObjects.PRAZO_REALIZACAO_TAREFA_DIAS);
    }
    public Sorteio(int numero, LocalDateTime dataHoraSorteio, LocalDate dataLimite, List<MoradorTarefaCombinacao> associacoes){
        this.dataHoraSorteio = dataHoraSorteio;
        this.dataLimite = dataLimite;
        this.associacoes = associacoes;
        this.numero = numero;
    }

    @GetSize
    public static int getSorteioSizeInBytes(){
        return 16; //8+8 das duas datas
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        return ((Sorteio)obj).getNumero().equals(this.numero);
    }


    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDateTime getDataHoraSorteio() {
        return dataHoraSorteio;
    }

    public void setDataHoraSorteio(LocalDateTime dataHoraSorteio) {
        this.dataHoraSorteio = dataHoraSorteio;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public List<MoradorTarefaCombinacao> getAssociacoes() {
        return associacoes;
    }

    public void setAssociacoes(List<MoradorTarefaCombinacao> associacoes) {
        this.associacoes = associacoes;
    }
}
