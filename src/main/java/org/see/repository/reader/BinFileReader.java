package org.see.repository.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.time.*;

public class BinFileReader {
    private RandomAccessFile raf;

    public BinFileReader(File file) throws FileNotFoundException {
        raf = new RandomAccessFile(file, "r");
    }

    public void setCursor(long cursor) throws IOException {
        raf.seek(cursor);
    }

    public boolean readBoolean() throws IOException {
        byte b =  raf.readByte();
        if(b == 0){
            return false;
        }
        return true;
    }
    public LocalDate readLocalDate() throws IOException {
        return LocalDate.ofEpochDay(ByteBuffer.allocate(8).put(readBytes(8)).position(0).getLong());
    }
    public LocalDateTime readLocalDateTime() throws IOException {
        return LocalDateTime.ofEpochSecond(ByteBuffer.allocate(8).put(readBytes(8)).position(0).getLong(), 0, ZoneId.of("America/Sao_Paulo").getRules().getOffset(Instant.now()));
    }
    public int readInt() throws IOException {
        return ByteBuffer.allocate(4).put(readBytes(4)).position(0).getInt();
    }

    public byte[] readBytes(int n) throws IOException {
        byte[] bytes = new byte[n];
        for(int i = 0; i<n; i++){
            bytes[i] = raf.readByte();
        }
        return bytes;
    }

    public long getCursor() throws IOException {
        return raf.getFilePointer();
    }

    public String readString(boolean resetCursor, long start, long length) throws IOException { // todo testar se funciona
        StringBuilder sb = new StringBuilder();
        if(resetCursor){
            setCursor(start);
        }
        for(int i = 0; i<length; i++){
            char read = (char)raf.readUnsignedByte(); //porque salvo tudo como iso 8859-1
            if(read != '\0'){
                sb.append(read);
            }
        }
        return sb.toString();
    }
    public long getLength() throws IOException {
        return raf.length();
    }
    public void close() throws IOException {
        raf.close();
    }
}
