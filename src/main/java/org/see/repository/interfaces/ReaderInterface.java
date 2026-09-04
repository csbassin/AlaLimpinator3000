package org.see.repository.interfaces;

import org.see.exception.PosicaoDeCursorInvalidaException;

import java.io.IOException;

public interface ReaderInterface<T> {
    T read(int index) throws Exception;
    T readNext() throws IOException, PosicaoDeCursorInvalidaException;
    void close() throws IOException;
}
