package org.see.modelo;

import org.see.annotation.Index;

import java.time.LocalDateTime;

public class MoradorTarefaCombinacao {
    @Index
    private Integer numero;
    private Morador morador;
    private Tarefa tarefa;
    private LocalDateTime dataHoraAssociacao;
    private boolean feita;
    private LocalDateTime dataHoraLimite;
}
