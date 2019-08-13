/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.harmony.dalvik.ddmc.Chunk
 *  org.apache.harmony.dalvik.ddmc.ChunkHandler
 *  org.apache.harmony.dalvik.ddmc.DdmServer
 *  org.apache.harmony.dalvik.ddmc.DdmVmInternal
 */
package android.ddm;

import java.nio.ByteBuffer;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;
import org.apache.harmony.dalvik.ddmc.DdmVmInternal;

public class DdmHandleThread
extends ChunkHandler {
    public static final int CHUNK_STKL;
    public static final int CHUNK_THCR;
    public static final int CHUNK_THDE;
    public static final int CHUNK_THEN;
    public static final int CHUNK_THST;
    private static DdmHandleThread mInstance;

    static {
        CHUNK_THEN = DdmHandleThread.type((String)"THEN");
        CHUNK_THCR = DdmHandleThread.type((String)"THCR");
        CHUNK_THDE = DdmHandleThread.type((String)"THDE");
        CHUNK_THST = DdmHandleThread.type((String)"THST");
        CHUNK_STKL = DdmHandleThread.type((String)"STKL");
        mInstance = new DdmHandleThread();
    }

    private DdmHandleThread() {
    }

    private Chunk createStackChunk(StackTraceElement[] arrstackTraceElement, int n) {
        int n2 = arrstackTraceElement.length;
        int n3 = 0 + 4 + 4 + 4;
        for (int i = 0; i < n2; ++i) {
            int n4;
            StackTraceElement stackTraceElement = arrstackTraceElement[i];
            n3 = n4 = n3 + (stackTraceElement.getClassName().length() * 2 + 4) + (stackTraceElement.getMethodName().length() * 2 + 4) + 4;
            if (stackTraceElement.getFileName() != null) {
                n3 = n4 + stackTraceElement.getFileName().length() * 2;
            }
            n3 += 4;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
        byteBuffer.putInt(0);
        byteBuffer.putInt(n);
        byteBuffer.putInt(arrstackTraceElement.length);
        for (StackTraceElement stackTraceElement : arrstackTraceElement) {
            byteBuffer.putInt(stackTraceElement.getClassName().length());
            DdmHandleThread.putString((ByteBuffer)byteBuffer, (String)stackTraceElement.getClassName());
            byteBuffer.putInt(stackTraceElement.getMethodName().length());
            DdmHandleThread.putString((ByteBuffer)byteBuffer, (String)stackTraceElement.getMethodName());
            if (stackTraceElement.getFileName() != null) {
                byteBuffer.putInt(stackTraceElement.getFileName().length());
                DdmHandleThread.putString((ByteBuffer)byteBuffer, (String)stackTraceElement.getFileName());
            } else {
                byteBuffer.putInt(0);
            }
            byteBuffer.putInt(stackTraceElement.getLineNumber());
        }
        return new Chunk(CHUNK_STKL, byteBuffer);
    }

    private Chunk handleSTKL(Chunk arrstackTraceElement) {
        int n = DdmHandleThread.wrapChunk((Chunk)arrstackTraceElement).getInt();
        arrstackTraceElement = DdmVmInternal.getStackTraceById((int)n);
        if (arrstackTraceElement == null) {
            return DdmHandleThread.createFailChunk((int)1, (String)"Stack trace unavailable");
        }
        return this.createStackChunk(arrstackTraceElement, n);
    }

    private Chunk handleTHEN(Chunk chunk) {
        boolean bl = DdmHandleThread.wrapChunk((Chunk)chunk).get() != 0;
        DdmVmInternal.threadNotify((boolean)bl);
        return null;
    }

    private Chunk handleTHST(Chunk arrby) {
        DdmHandleThread.wrapChunk((Chunk)arrby);
        arrby = DdmVmInternal.getThreadStats();
        if (arrby != null) {
            return new Chunk(CHUNK_THST, arrby, 0, arrby.length);
        }
        return DdmHandleThread.createFailChunk((int)1, (String)"Can't build THST chunk");
    }

    public static void register() {
        DdmServer.registerHandler((int)CHUNK_THEN, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_THST, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_STKL, (ChunkHandler)mInstance);
    }

    public void connected() {
    }

    public void disconnected() {
    }

    public Chunk handleChunk(Chunk object) {
        int n = ((Chunk)object).type;
        if (n == CHUNK_THEN) {
            return this.handleTHEN((Chunk)object);
        }
        if (n == CHUNK_THST) {
            return this.handleTHST((Chunk)object);
        }
        if (n == CHUNK_STKL) {
            return this.handleSTKL((Chunk)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown packet ");
        ((StringBuilder)object).append(ChunkHandler.name((int)n));
        throw new RuntimeException(((StringBuilder)object).toString());
    }
}

