package org.see.modelo.tarefas;

import org.see.modelo.Morador;

public class TarefaFixa extends Tarefa{ // tarefa que é exclusiva de um morador
    private Morador moradorAssociado;

    public Morador getMoradorAssociado() {
        return moradorAssociado;
    }

    public void setMoradorAssociado(Morador moradorAssociado) {
        this.moradorAssociado = moradorAssociado;
    }
}
