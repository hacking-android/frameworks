/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.harmony.dalvik.ddmc.Chunk
 *  org.apache.harmony.dalvik.ddmc.ChunkHandler
 *  org.apache.harmony.dalvik.ddmc.DdmServer
 */
package android.ddm;

import android.os.Debug;
import android.util.Log;
import java.nio.ByteBuffer;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

public class DdmHandleProfiling
extends ChunkHandler {
    public static final int CHUNK_MPRE;
    public static final int CHUNK_MPRQ;
    public static final int CHUNK_MPRS;
    public static final int CHUNK_MPSE;
    public static final int CHUNK_MPSS;
    public static final int CHUNK_SPSE;
    public static final int CHUNK_SPSS;
    private static final boolean DEBUG = false;
    private static DdmHandleProfiling mInstance;

    static {
        CHUNK_MPRS = DdmHandleProfiling.type((String)"MPRS");
        CHUNK_MPRE = DdmHandleProfiling.type((String)"MPRE");
        CHUNK_MPSS = DdmHandleProfiling.type((String)"MPSS");
        CHUNK_MPSE = DdmHandleProfiling.type((String)"MPSE");
        CHUNK_MPRQ = DdmHandleProfiling.type((String)"MPRQ");
        CHUNK_SPSS = DdmHandleProfiling.type((String)"SPSS");
        CHUNK_SPSE = DdmHandleProfiling.type((String)"SPSE");
        mInstance = new DdmHandleProfiling();
    }

    private DdmHandleProfiling() {
    }

    private Chunk handleMPRE(Chunk arrby) {
        boolean bl;
        try {
            Debug.stopMethodTracing();
            bl = false;
        }
        catch (RuntimeException runtimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Method profiling end failed: ");
            stringBuilder.append(runtimeException.getMessage());
            Log.w("ddm-heap", stringBuilder.toString());
            bl = true;
        }
        arrby = new byte[]{(byte)(bl ? 1 : 0)};
        return new Chunk(CHUNK_MPRE, arrby, 0, arrby.length);
    }

    private Chunk handleMPRQ(Chunk arrby) {
        int n = Debug.getMethodTracingMode();
        arrby = new byte[]{(byte)n};
        return new Chunk(CHUNK_MPRQ, arrby, 0, arrby.length);
    }

    private Chunk handleMPRS(Chunk object) {
        object = DdmHandleProfiling.wrapChunk((Chunk)object);
        int n = ((ByteBuffer)object).getInt();
        int n2 = ((ByteBuffer)object).getInt();
        object = DdmHandleProfiling.getString((ByteBuffer)object, (int)((ByteBuffer)object).getInt());
        try {
            Debug.startMethodTracing((String)object, n, n2);
            return null;
        }
        catch (RuntimeException runtimeException) {
            return DdmHandleProfiling.createFailChunk((int)1, (String)runtimeException.getMessage());
        }
    }

    private Chunk handleMPSEOrSPSE(Chunk chunk, String string2) {
        try {
            Debug.stopMethodTracing();
            return null;
        }
        catch (RuntimeException runtimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" prof stream end failed: ");
            stringBuilder.append(runtimeException.getMessage());
            Log.w("ddm-heap", stringBuilder.toString());
            return DdmHandleProfiling.createFailChunk((int)1, (String)runtimeException.getMessage());
        }
    }

    private Chunk handleMPSS(Chunk object) {
        object = DdmHandleProfiling.wrapChunk((Chunk)object);
        int n = ((ByteBuffer)object).getInt();
        int n2 = ((ByteBuffer)object).getInt();
        try {
            Debug.startMethodTracingDdms(n, n2, false, 0);
            return null;
        }
        catch (RuntimeException runtimeException) {
            return DdmHandleProfiling.createFailChunk((int)1, (String)runtimeException.getMessage());
        }
    }

    private Chunk handleSPSS(Chunk object) {
        object = DdmHandleProfiling.wrapChunk((Chunk)object);
        int n = ((ByteBuffer)object).getInt();
        int n2 = ((ByteBuffer)object).getInt();
        int n3 = ((ByteBuffer)object).getInt();
        try {
            Debug.startMethodTracingDdms(n, n2, true, n3);
            return null;
        }
        catch (RuntimeException runtimeException) {
            return DdmHandleProfiling.createFailChunk((int)1, (String)runtimeException.getMessage());
        }
    }

    public static void register() {
        DdmServer.registerHandler((int)CHUNK_MPRS, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_MPRE, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_MPSS, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_MPSE, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_MPRQ, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_SPSS, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_SPSE, (ChunkHandler)mInstance);
    }

    public void connected() {
    }

    public void disconnected() {
    }

    public Chunk handleChunk(Chunk object) {
        int n = ((Chunk)object).type;
        if (n == CHUNK_MPRS) {
            return this.handleMPRS((Chunk)object);
        }
        if (n == CHUNK_MPRE) {
            return this.handleMPRE((Chunk)object);
        }
        if (n == CHUNK_MPSS) {
            return this.handleMPSS((Chunk)object);
        }
        if (n == CHUNK_MPSE) {
            return this.handleMPSEOrSPSE((Chunk)object, "Method");
        }
        if (n == CHUNK_MPRQ) {
            return this.handleMPRQ((Chunk)object);
        }
        if (n == CHUNK_SPSS) {
            return this.handleSPSS((Chunk)object);
        }
        if (n == CHUNK_SPSE) {
            return this.handleMPSEOrSPSE((Chunk)object, "Sample");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown packet ");
        ((StringBuilder)object).append(ChunkHandler.name((int)n));
        throw new RuntimeException(((StringBuilder)object).toString());
    }
}

