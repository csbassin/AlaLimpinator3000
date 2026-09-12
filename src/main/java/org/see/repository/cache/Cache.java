package org.see.repository.cache;

import org.see.annotation.FakeSize;
import org.see.annotation.GetSize;
import org.see.annotation.Index;
import org.see.config.FakeFileLengths;
import org.see.exception.IndiceInexistenteException;
import org.see.exception.SemAnotacaoIndexException;
import org.see.repository.cache.flusher.CacheFlusher;
import org.see.repository.interfaces.ReaderInterface;
import org.see.repository.interfaces.WriterInterface;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class Cache<T> extends ArrayList<CacheLine<T>> {

    private static int cacheSize = 100;

    private ReaderInterface<T> rd;
    protected CacheFlusher<T> flusher;

    protected Cache(ReaderInterface<T> rd, WriterInterface<T> wr){
        super(cacheSize); // caches podem armazenar até 100 itens por default
        this.rd = rd;
        for(int i = 0; i<cacheSize; i++){ // cria linhas de cache vazias
            this.add(new CacheLine<>());
        }
    }

    // toda vez que eu escrevo na cache, ele notifica o flusher, então, provavelmente não preciso do writer aqui
    public void write(T objeto) throws IllegalAccessException { // vou fazer como um cache de mapeamento direto. A posição onde ele vai entrar é @Index%100
        // preciso pegar o valor do campo anotado com Index
        Field fieldIndex = null;
        for(Field f:objeto.getClass().getDeclaredFields()){
            if(f.isAnnotationPresent(Index.class)) {
                fieldIndex = f;
                fieldIndex.setAccessible(true);
                break;
            }
        }

        // preciso pegar o fakeFile correspondente
        Field fieldFakeSize = null;
        for(Field f:FakeFileLengths.class.getDeclaredFields()){ // para cada campo
            if(f.isAnnotationPresent(FakeSize.class)){ // verifico se tem a anotação fakeSize
                FakeSize a = f.getDeclaredAnnotation(FakeSize.class);
                if(a.target() == objeto.getClass()){ // verifico se o alvo é igual ao desejado
                    System.out.println("Achei o fakeSize correspondente");
                    fieldFakeSize = f;
                    break;
                }
            }
        }
        // localizar getSize
        Method getSize = null;
        for(Method m:objeto.getClass().getDeclaredMethods()){
           if(m.isAnnotationPresent(GetSize.class)){
                getSize = m;
                break;
            }
        }
        if(fieldIndex==null || fieldFakeSize == null || getSize == null){
            throw new SemAnotacaoIndexException("Falta um @Index, @FakeSize ou @GetSize.");
        }

        Integer valueIndex = (Integer) fieldIndex.get(objeto);
        long valueFakeSize = fieldFakeSize.getLong(FakeFileLengths.class);


        try{
            if(valueIndex == null){ // Objeto ainda não está presente em ms
                int size = (int) getSize.invoke(objeto);
                valueIndex = (int) valueFakeSize/size;
                valueFakeSize += size;
                fieldIndex.set(objeto, valueIndex);
                fieldFakeSize.set(FakeFileLengths.class, valueFakeSize);
            }
        }catch(InvocationTargetException e){
            throw new RuntimeException(e);
        }

        int i = calculaPosicao(valueIndex);
        CacheLine<T> line = this.get(calculaPosicao(i));

        // três casos: é null, é o mesmo número, é número diferente. O último representa uma substituição
        line.setModified(true);
        if(line.getElement() == null){
            //System.out.println("Cache miss");
            line.setElementNumber(valueIndex);
            line.setElement(objeto);
        }else if(line.getElementNumber()==valueIndex){ // espero que faça unboxing automático
            //System.out.println("Cache hit");
            line.setElement(objeto);
        }else{
            //System.out.println("Cache miss");
            /*if(line.isModified()){ // ele precisa escrever o cara que vai sair
                wr.update(line.getElement());
            }*/
            line.setElementNumber(valueIndex);
            line.setElement(objeto);
        }
        synchronized (flusher){
            flusher.notify();
        }
    }

    public T read(int index) throws Exception { // leitura não faz modificar
        int posicao = calculaPosicao(index);
        CacheLine<T> line = this.get(posicao);
        T retorno = null;

        if(line.getElement() != null && line.getElementNumber()==index){
            //System.out.println("Cache hit");
            retorno = line.getElement();
        }else{
            //System.out.println("Cache miss");
            try{
                retorno = rd.read(index);
                this.get(posicao).setElementNumber(index);
                this.get(posicao).setElement(retorno);
                this.get(posicao).setModified(false);
            }catch(IndiceInexistenteException e){
                System.out.println(e.getMessage());
                return null;
            }

        }

        return retorno;
    }

    public int getQuantidadeElementos() throws IOException {
        return rd.getQuantidadeElementos();
    }

    private int calculaPosicao(int index) {
        return index%cacheSize;
    }
}
