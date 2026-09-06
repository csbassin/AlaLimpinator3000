package org.see.repository.cache;

import org.see.exception.SemAnotacaoIndexException;
import org.see.repository.cache.flusher.CacheFlusher;

import java.lang.reflect.Field;
import java.util.ArrayList;


public /*abstract*/ class Cache<T> extends ArrayList<CacheLine<T>> {

    private static int cacheSize = 100;
    protected CacheFlusher<T> flusher;

    protected Cache(){
        super(cacheSize); // caches podem armazenar até 100 itens por default
        for(int i = 0; i<cacheSize; i++){ // cria linhas de cache vazias
            this.add(new CacheLine<>());
        }
    }

    //deve retornar quem foi substituído. modified vai depender de quê tipo de acesso fez o cara ser escrito
    public T write(T objeto, boolean modified) throws IllegalAccessException { // vou fazer como um cache de mapeamento direto. A posição onde ele vai entrar é @Index%100
        // passo 1: olhar se o cara já está na cache. Se já estiver, substitui e seta "modificado" como 1
        // preciso pegar o valor do campo anotado com Index
        Field index = null;
        for(Field field:objeto.getClass().getDeclaredFields()){
            index = field;
            index.setAccessible(true);
            break;
        }
        if(index!=null){
            Integer value = (Integer) index.get(objeto);
            if(value == null){
                throw new RuntimeException("Índice deve ser diferente de null.");
            }
            CacheLine<T> line = this.get(value%cacheSize);
            // três casos: é null, é o mesmo número, é número diferente. O último representa uma substituição
            line.setModified(modified);
            if(line.getElement() == null){
                System.out.println("Cache miss");
                // não posso concluir nada sobre a modificação aqui, pode ser escrito na cache por um read ou um write
                line.setElementNumber(value);
                line.setElement(objeto);
                synchronized(flusher) {
                	flusher.notify();
                }
                
                return null;
            }else if(line.getElementNumber()==value){ // espero que faça unboxing automático
                System.out.println("Cache hit");
                //Quando eu escrevo por cima de um cara igual, é porque foi modificado.
                line.setElement(objeto);
                line.setModified(true);
                synchronized(flusher) {
                	flusher.notify();
                }                
                return null;
            }else{ // último caso: Substituição. Retorno somente se ele precisar ser escrito em MS, ou seja, se m=1
                System.out.println("Cache miss");
                T retorno = null;
                if(line.isModified()){
                    retorno = line.getElement();
                }
                line.setElementNumber(value);
                line.setElement(objeto);
                synchronized(flusher) {
                	flusher.notify();
                }
                return retorno;
            }
        }else{
            throw new SemAnotacaoIndexException("Não se pode criar um cache de uma classe sem um campo anotado com @Index.");
        }
    }
    public T read(int index){ // leitura não faz modificar
        CacheLine<T> line = this.get(index%cacheSize);
        if(line.getElementNumber()==index){
            System.out.println("Cache hit");
            return line.getElement();
        }else{ // pode retornar o elemento errado. Verificar quando for usar
            System.out.println("Cache miss");
            if(!line.isModified()){
                return null;
            }
            return line.getElement();
        }
    }
}
