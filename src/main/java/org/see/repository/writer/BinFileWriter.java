package org.see.repository.writer;

import org.see.Miscelaneous;
import org.see.exception.StringMuitoLongaException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class BinFileWriter implements AutoCloseable {
    private ByteArrayOutputStream buffer;
    private RandomAccessFile raf;
    private File file;

    public BinFileWriter(File file) throws FileNotFoundException {
        this.file = file;
        raf = new RandomAccessFile(file, "rw");
        this.buffer = new ByteArrayOutputStream();
    }

    public long getLength(){
        return file.length();
    }

    public void setCursor(long cursor) throws IOException {
        raf.seek(cursor);
    }

    public void setCursorEnd() throws IOException {
        raf.seek(file.length());
    }

    public void writeBoolean(boolean value){
        if(value){
            buffer.writeBytes(new byte[]{1});
        }else{
            buffer.writeBytes(new byte[]{0});
        }
    }

    public void writeData(LocalDate data){
        /*int[] formatted = Miscelaneous.dateToArray(data);
        buffer.write((byte)formatted[0]);
        buffer.write((byte)formatted[1]);
        writeInt(formatted[2]);*/
        writeLong(data.toEpochDay());
    }

    public void writeDateTime(LocalDateTime dateTime){
        writeLong(dateTime.atZone(ZoneId.systemDefault()).toEpochSecond());
    }

    public void writeLong(long value) {
        buffer.writeBytes(ByteBuffer.allocate(8).putLong(value).array());
    }

    public void writeInt(int value) {
        buffer.writeBytes(ByteBuffer.allocate(4).putInt(value).array());
    }

    public void writeFloat(float value) {
        buffer.writeBytes(ByteBuffer.allocate(4).putFloat(value).array());
    }

    public void writeByte(byte value) {
        buffer.write(value);
    }

    public void writeBytes(byte[] values) {
        buffer.writeBytes(values);
    }

    public void writeString(String value, int length) throws StringMuitoLongaException {
        if(value.length() > length){
            throw new StringMuitoLongaException("O texto deve ter no máximo "+length+" caracteres.");
        }
        buffer.writeBytes((value + "\0".repeat((length - value.length()))).getBytes(StandardCharsets.ISO_8859_1));
    }

    public void flush() throws IOException {
        raf.write(buffer.toByteArray());
        clear();
    }

    public byte[] toByteArray() {
        return buffer.toByteArray();
    }

    public void clear() {
        buffer.reset();
    }

    @Override
    public void close() throws IOException {
        try{
            flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            buffer.close();
        }
    }
}
