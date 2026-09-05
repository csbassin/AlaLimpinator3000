package org.see.repository.cache.flusher;

import org.see.config.StaticConfigObjects;
import org.see.repository.cache.Cache;
import org.see.repository.cache.CacheLine;
import org.see.repository.interfaces.WriterInterface;

public class CacheFlusher<T> extends Thread{

    protected Cache<T> cache;
    protected WriterInterface<T> writer;
    protected boolean stop = false;
    

    protected CacheFlusher(Cache<T> cache, WriterInterface<T> writer){
        this.cache = cache;
        this.writer = writer;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    protected void flush() {
    	for(CacheLine line:cache){
            if(line.isModified()){
            	System.out.println("Encontrei um dado que precisa de flushing");
                try{
                    writer.writeBypassingCache((T)line.getElement());
                    line.setModified(false);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    
    @Override
    public void run(){
        while(!stop){
            try {
                synchronized (this){
                    wait();
                }
                flush();
                
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    
}
