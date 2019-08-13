/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.dalvik.ddmc;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.apache.harmony.dalvik.ddmc.Chunk;

public abstract class ChunkHandler {
    public static final int CHUNK_FAIL;
    @UnsupportedAppUsage
    public static final ByteOrder CHUNK_ORDER;

    static {
        CHUNK_ORDER = ByteOrder.BIG_ENDIAN;
        CHUNK_FAIL = ChunkHandler.type("FAIL");
    }

    public static Chunk createFailChunk(int n, String object) {
        String string = object;
        if (object == null) {
            string = "";
        }
        object = ByteBuffer.allocate(string.length() * 2 + 8);
        ((ByteBuffer)object).order(CHUNK_ORDER);
        ((ByteBuffer)object).putInt(n);
        ((ByteBuffer)object).putInt(string.length());
        ChunkHandler.putString((ByteBuffer)object, string);
        return new Chunk(CHUNK_FAIL, (ByteBuffer)object);
    }

    public static String getString(ByteBuffer byteBuffer, int n) {
        char[] arrc = new char[n];
        for (int i = 0; i < n; ++i) {
            arrc[i] = byteBuffer.getChar();
        }
        return new String(arrc);
    }

    public static String name(int n) {
        return new String(new char[]{(char)(n >> 24 & 255), (char)(n >> 16 & 255), (char)(n >> 8 & 255), (char)(n & 255)});
    }

    public static void putString(ByteBuffer byteBuffer, String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            byteBuffer.putChar(string.charAt(i));
        }
    }

    public static int type(String string) {
        if (string.length() == 4) {
            int n = 0;
            for (int i = 0; i < 4; ++i) {
                n = n << 8 | string.charAt(i) & 255;
            }
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad type name: ");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static ByteBuffer wrapChunk(Chunk object) {
        object = ByteBuffer.wrap(((Chunk)object).data, ((Chunk)object).offset, ((Chunk)object).length);
        ((ByteBuffer)object).order(CHUNK_ORDER);
        return object;
    }

    public abstract void connected();

    public abstract void disconnected();

    public abstract Chunk handleChunk(Chunk var1);
}

