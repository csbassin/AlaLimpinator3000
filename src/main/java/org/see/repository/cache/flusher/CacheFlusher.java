package org.see.repository.cache.flusher;

import org.see.config.StaticConfigObjects;
import org.see.repository.cache.Cache;
import org.see.repository.cache.CacheLine;
import org.see.repository.interfaces.WriterInterface;

public class CacheFlusher<T> extends Thread{

    private Cache<T> cache;
    private WriterInterface<T> writer;
    private boolean stop = false;

    public CacheFlusher(Cache<T> cache, WriterInterface<T> writer){
        this.cache = cache;
        this.writer = writer;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    @Override
    public void run(){
        while(!stop){
            try {
                synchronized (this){
                    wait();
                }
                for(CacheLine<T> line:cache){
                    if(line.isModified()){
                        System.out.println("Encontrei dado que precisa de flushing.");
                        try{
                            writer.update((T)line.getElement());
                            line.setModified(false);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
