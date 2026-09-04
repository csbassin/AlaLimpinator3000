package org.see.repository.cache;

import org.see.modelo.Morador;

public class CacheMorador extends Cache<Morador>{
    private static CacheMorador cache = null;

    private CacheMorador(){
        super();
    }

    public static Cache getCacheMorador(){
        if(cache == null){
            cache = new CacheMorador();
        }
        return cache;
    }
}
