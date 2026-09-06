package org.see.repository.cache;

import org.see.annotation.FakeSize;
import org.see.annotation.Index;
import org.see.config.FakeFileLengths;
import org.see.config.StaticConfigObjects;
import org.see.exception.NullIndexException;
import org.see.exception.SemAnotacaoIndexException;
import org.see.exception.StringMuitoLongaException;
import org.see.modelo.Morador;
import org.see.repository.cache.flusher.CacheFlusher;
import org.see.repository.interfaces.ReaderInterface;
import org.see.repository.interfaces.WriterInterface;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;


public /*abstract*/ class Cache<T> extends ArrayList<CacheLine<T>> {

    private static int cacheSize = 100;

    private ReaderInterface<T> rd;
    private WriterInterface<T> wr;
    protected CacheFlusher<T> flusher;

    protected Cache(ReaderInterface<T> rd, WriterInterface<T> wr){
        super(cacheSize); // caches podem armazenar até 100 itens por default
        this.rd = rd;
        this.wr = wr;
        for(int i = 0; i<cacheSize; i++){ // cria linhas de cache vazias
            this.add(new CacheLine<>());
        }
    }

    //deve retornar quem foi substituído. modified vai depender de quê tipo de acesso fez o cara ser escrito
    public void write(T objeto) throws IllegalAccessException, IOException, StringMuitoLongaException, NullIndexException { // vou fazer como um cache de mapeamento direto. A posição onde ele vai entrar é @Index%100
        // passo 1: olhar se o cara já está na cache. Se já estiver, substitui e seta "modificado" como 1
        // preciso pegar o valor do campo anotado com Index

        Field fieldIndex = null;
        for(Field f:objeto.getClass().getDeclaredFields()){
            if(f.isAnnotationPresent(Index.class)) {
                fieldIndex = f;
                fieldIndex.setAccessible(true);
                break;
            }
        }

        if(fieldIndex==null){
            throw new SemAnotacaoIndexException("Não se pode criar um cache de uma classe sem um campo anotado com @Index.");
        }

        Integer valueIndex = (Integer) fieldIndex.get(objeto);
        // Objeto ainda não está presente
        // preciso pegar o fakeFile correspondente
        FakeFileLengths fake = new FakeFileLengths();
        Field fieldFakeSize = null;
        for(Field f:fake.getClass().getDeclaredFields()){ // para cada campo
            if(f.isAnnotationPresent(FakeSize.class)){ // verifico se tem a anotação fakeSize
                FakeSize a = f.getDeclaredAnnotation(FakeSize.class);
                if(a.target() == objeto.getClass()){ // verifico se o alvo é igual ao desejado
                    System.out.println("Achei o fakeSize correspondente");
                    fieldFakeSize = f;
                    break;
                }
            }
        }

        long valueFakeSize = fieldFakeSize.getLong(fake);
        if(valueIndex == null){
            valueIndex = (int) valueFakeSize /Morador.getMoradorSizeInBytes();
            valueFakeSize += Morador.getMoradorSizeInBytes();
            fieldIndex.set(objeto, valueIndex);
            wr.write(objeto);
            fieldFakeSize.set(fake, valueFakeSize);
        }

        int i = calculaIndex(valueIndex);
        CacheLine<T> line = this.get(calculaIndex(i));

        // três casos: é null, é o mesmo número, é número diferente. O último representa uma substituição
        if(line.getElement() == null){
            System.out.println("Cache miss");
            // não posso concluir nada sobre a modificação aqui, pode ser escrito na cache por um read ou um write
            line.setElementNumber(valueIndex);
            line.setElement(objeto);
        }else if(line.getElementNumber()==valueIndex){ // espero que faça unboxing automático
            System.out.println("Cache hit");
            //Quando eu escrevo por cima de um cara igual, é porque foi modificado.
            line.setElement(objeto);
            line.setModified(true);
        }else{ // último caso: Substituição. Retorno somente se ele precisar ser escrito em MS, ou seja, se m=1
            System.out.println("Cache miss");
            if(line.isModified()){
                wr.update(objeto);
            }
            line.setElementNumber(valueIndex);
            line.setElement(objeto);
        }
        synchronized(flusher) {
            flusher.notify();
        }
    }

    public T read(int index) throws Exception { // leitura não faz modificar
        int i = calculaIndex(index);
        CacheLine<T> line = this.get(i);
        T retorno = null;

        if(line.getElementNumber()==index){
            System.out.println("Cache hit");
            retorno = line.getElement();
        }else{ // pode retornar o elemento errado. Verificar quando for usar
            System.out.println("Cache miss");
            if(!line.isModified()){
                retorno = rd.read(index);
            }
            else {
                retorno = line.getElement();
            }
        }

        return retorno;
    }

//    public void upadate(T objeto) throws IllegalAccessException {
//        Field index = null;
//        for(Field field:objeto.getClass().getDeclaredFields()){
//            index = field;
//            index.setAccessible(true);
//            break;
//        }
//
//        if(index==null){
//            throw new SemAnotacaoIndexException("Objeto não encontrado");
//        }
//
//        Integer value = (Integer) index.get(objeto);
//        int i = calculaIndex(value);
//
//        CacheLine<T> line = this.get(i);
//
//        // Se n está na cache, traszemos da memória e atualizamos a cache
//        if(line.getElement() != null){
//            line.setElement(objeto);
//
//        }
//    }

    private int calculaIndex(int index) {
        return index%cacheSize;
    }
}
