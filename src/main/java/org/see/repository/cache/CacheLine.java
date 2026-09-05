package org.see.repository.cache;

import org.see.config.StaticConfigObjects;

import java.util.Arrays;

public class CacheLine<T>{
    private boolean modified;
    private long blockNumber;
    private T element; // decidi abstrair a questão dos blocos por simplicidade

    public CacheLine(){
      modified = false;
      blockNumber = 0;
      element = null;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T block) {
        this.element = block;
    }

    public long getElementNumber() {
        return blockNumber;
    }

    public void setElementNumber(long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}
