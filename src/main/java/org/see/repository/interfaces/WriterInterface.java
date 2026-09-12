package org.see.repository.interfaces;

import org.see.exception.NullIndexException;
import org.see.exception.StringMuitoLongaException;

import java.io.IOException;

public interface WriterInterface<T> {
    void write(T obj) throws Exception;
    void update(T obj) throws Exception;
}
