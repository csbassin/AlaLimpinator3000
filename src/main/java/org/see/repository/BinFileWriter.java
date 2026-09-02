package org.see.repository;

import org.see.Miscelaneous;
import org.see.exception.StringMuitoLongaException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BinFileWriter implements AutoCloseable {
    private ByteArrayOutputStream buffer;
    private FileOutputStream fos;

    public BinFileWriter(File file) throws FileNotFoundException {
        fos = new FileOutputStream(file, true);
        this.buffer = new ByteArrayOutputStream();
    }


    public void writeData(LocalDate data){
        int[] formatted = Miscelaneous.dateToArray(data);
        buffer.write((byte)formatted[0]);
        buffer.write((byte)formatted[1]);
        writeInt(formatted[2]);
    }

    public void writeDateTime(LocalDateTime dateTime){
        int[] formatted = Miscelaneous.dateTimeToArray(dateTime);
        buffer.write((byte)formatted[0]);
        buffer.write((byte)formatted[1]);
        writeInt(formatted[2]);
        writeByte((byte)formatted[3]);
        writeByte((byte)formatted[4]);
        writeByte((byte)formatted[5]);
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
        fos.write(buffer.toByteArray());
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
