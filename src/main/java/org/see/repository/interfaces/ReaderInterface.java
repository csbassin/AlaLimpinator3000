package org.see.repository.interfaces;

import org.see.exception.NullIndexException;
import org.see.exception.PosicaoDeCursorInvalidaException;
import org.see.exception.StringMuitoLongaException;

import java.io.IOException;

public interface ReaderInterface<T> {
    T read(int index) throws Exception;
    T readNext() throws IOException, PosicaoDeCursorInvalidaException, StringMuitoLongaException, NullIndexException, IllegalAccessException;
    void close() throws IOException;
}
