//package org.see.repository.cache;
//
//import org.see.modelo.tarefas.Tarefa;
//import org.see.repository.reader.MoradorReader;
//import org.see.repository.writer.MoradorWriter;
//
//public class CacheTarefa extends Cache<Tarefa>{
//    private static CacheTarefa cache = null;
//
//    private CacheTarefa(){
//        super();
//    }
//
//    public static Cache getCacheTarefa(){
//        if(cache == null){
//            cache = new CacheTarefa();
//        }
//        return cache;
//    }
//}
