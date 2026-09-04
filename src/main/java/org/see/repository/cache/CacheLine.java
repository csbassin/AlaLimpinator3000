package org.see.repository.cache;

import org.see.config.StaticConfigObjects;

import java.util.Arrays;

public class CacheLine<T>{
    private boolean modified;
    private long blockNumber;
    private T[] block;

    public CacheLine(){
      modified = false;
      blockNumber = 0;
      block = (T[]) new Object[StaticConfigObjects.cacheBlockSize];
      Arrays.fill(block, null);
    }

    public T[] getBlock() {
        return block;
    }

    public void setBlock(T[] block) {
        this.block = block;
    }

    public long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}
