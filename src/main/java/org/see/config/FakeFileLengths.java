package org.see.config;

import org.see.annotation.FakeSize;
import org.see.modelo.Morador;
import org.see.modelo.Tarefa;

public class FakeFileLengths {
    @FakeSize(target = Morador.class)
    public static long morador = 0;
    @FakeSize(target = Tarefa.class)
    public static long tarefa = 0;
}
