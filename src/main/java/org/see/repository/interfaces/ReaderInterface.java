package org.see.repository.interfaces;

import org.see.exception.NullIndexException;
import org.see.exception.PosicaoDeCursorInvalidaException;
import org.see.exception.StringMuitoLongaException;

import java.io.IOException;

public interface ReaderInterface<T> {
    T read(int index) throws Exception;
    int getQuantidadeElementos() throws IOException;
    void close() throws IOException;
}
