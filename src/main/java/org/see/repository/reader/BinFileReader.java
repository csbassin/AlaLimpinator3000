package org.see.repository.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BinFileReader {
    // todo adicionar cache aqui
    private RandomAccessFile raf;

    public BinFileReader(File file) throws FileNotFoundException {
        raf = new RandomAccessFile(file, "r");
    }

    public void setCursor(long cursor) throws IOException {
        raf.seek(cursor);
    }
    public String readString(boolean resetCursor, long start, long length) throws IOException { // todo testar se funciona
        StringBuilder sb = new StringBuilder();
        if(resetCursor){
            setCursor(start);
        }
        for(int i = 0; i<length; i++){
            char read = (char)raf.readUnsignedByte();
            if(read != '\0'){
                sb.append(read);
            }
        }
        return sb.toString();
    }
    public long getLength() throws IOException {
        return raf.length();
    }
}
