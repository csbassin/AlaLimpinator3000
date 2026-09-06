package org.see.config;

import org.see.annotation.FakeSize;
import org.see.modelo.Morador;

public class FakeFileLengths {
    @FakeSize(target = Morador.class)
    public static long morador = 0;
}
