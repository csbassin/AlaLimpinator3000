package org.see.repository.interfaces;

import org.see.exception.NullIndexException;
import org.see.exception.StringMuitoLongaException;

import java.io.IOException;

public interface WriterInterface<T> {
    //void genericWrite(T obj) throws StringMuitoLongaException, IOException;
    int write(T obj) throws IOException, StringMuitoLongaException;
    void update(T obj) throws NullIndexException, IOException, StringMuitoLongaException;
}
