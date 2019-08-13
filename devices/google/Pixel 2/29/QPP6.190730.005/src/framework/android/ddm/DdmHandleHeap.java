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

import android.os.Debug;
import android.util.Log;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;
import org.apache.harmony.dalvik.ddmc.DdmVmInternal;

public class DdmHandleHeap
extends ChunkHandler {
    public static final int CHUNK_HPDS;
    public static final int CHUNK_HPDU;
    public static final int CHUNK_HPGC;
    public static final int CHUNK_HPIF;
    public static final int CHUNK_HPSG;
    public static final int CHUNK_NHSG;
    public static final int CHUNK_REAE;
    public static final int CHUNK_REAL;
    public static final int CHUNK_REAQ;
    private static DdmHandleHeap mInstance;

    static {
        CHUNK_HPIF = DdmHandleHeap.type((String)"HPIF");
        CHUNK_HPSG = DdmHandleHeap.type((String)"HPSG");
        CHUNK_HPDU = DdmHandleHeap.type((String)"HPDU");
        CHUNK_HPDS = DdmHandleHeap.type((String)"HPDS");
        CHUNK_NHSG = DdmHandleHeap.type((String)"NHSG");
        CHUNK_HPGC = DdmHandleHeap.type((String)"HPGC");
        CHUNK_REAE = DdmHandleHeap.type((String)"REAE");
        CHUNK_REAQ = DdmHandleHeap.type((String)"REAQ");
        CHUNK_REAL = DdmHandleHeap.type((String)"REAL");
        mInstance = new DdmHandleHeap();
    }

    private DdmHandleHeap() {
    }

    private Chunk handleHPDS(Chunk object) {
        DdmHandleHeap.wrapChunk((Chunk)object);
        object = null;
        try {
            Debug.dumpHprofDataDdms();
        }
        catch (RuntimeException runtimeException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Exception: ");
            ((StringBuilder)object).append(runtimeException.getMessage());
            object = ((StringBuilder)object).toString();
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            object = "hprof dumps not supported in this VM";
        }
        if (object != null) {
            Log.w("ddm-heap", (String)object);
            return DdmHandleHeap.createFailChunk((int)1, (String)object);
        }
        return null;
    }

    private Chunk handleHPDU(Chunk object) {
        int n;
        object = DdmHandleHeap.wrapChunk((Chunk)object);
        object = DdmHandleHeap.getString((ByteBuffer)object, (int)((ByteBuffer)object).getInt());
        try {
            Debug.dumpHprofData((String)object);
            n = 0;
        }
        catch (RuntimeException runtimeException) {
            n = -1;
        }
        catch (IOException iOException) {
            n = -1;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            Log.w("ddm-heap", "hprof dumps not supported in this VM");
            n = -1;
        }
        object = new byte[]{(byte)n};
        return new Chunk(CHUNK_HPDU, (byte[])object, 0, ((Object)object).length);
    }

    private Chunk handleHPGC(Chunk chunk) {
        Runtime.getRuntime().gc();
        return null;
    }

    private Chunk handleHPIF(Chunk chunk) {
        if (!DdmVmInternal.heapInfoNotify((int)DdmHandleHeap.wrapChunk((Chunk)chunk).get())) {
            return DdmHandleHeap.createFailChunk((int)1, (String)"Unsupported HPIF what");
        }
        return null;
    }

    private Chunk handleHPSGNHSG(Chunk object, boolean bl) {
        if (!DdmVmInternal.heapSegmentNotify((int)((ByteBuffer)(object = DdmHandleHeap.wrapChunk((Chunk)object))).get(), (int)((ByteBuffer)object).get(), (boolean)bl)) {
            return DdmHandleHeap.createFailChunk((int)1, (String)"Unsupported HPSG what/when");
        }
        return null;
    }

    private Chunk handleREAE(Chunk chunk) {
        boolean bl = DdmHandleHeap.wrapChunk((Chunk)chunk).get() != 0;
        DdmVmInternal.enableRecentAllocations((boolean)bl);
        return null;
    }

    private Chunk handleREAL(Chunk arrby) {
        arrby = DdmVmInternal.getRecentAllocations();
        return new Chunk(CHUNK_REAL, arrby, 0, arrby.length);
    }

    private Chunk handleREAQ(Chunk arrby) {
        arrby = new byte[]{(byte)(DdmVmInternal.getRecentAllocationStatus() ? 1 : 0)};
        return new Chunk(CHUNK_REAQ, arrby, 0, arrby.length);
    }

    public static void register() {
        DdmServer.registerHandler((int)CHUNK_HPIF, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_HPSG, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_HPDU, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_HPDS, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_NHSG, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_HPGC, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_REAE, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_REAQ, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_REAL, (ChunkHandler)mInstance);
    }

    public void connected() {
    }

    public void disconnected() {
    }

    public Chunk handleChunk(Chunk object) {
        int n = ((Chunk)object).type;
        if (n == CHUNK_HPIF) {
            return this.handleHPIF((Chunk)object);
        }
        if (n == CHUNK_HPSG) {
            return this.handleHPSGNHSG((Chunk)object, false);
        }
        if (n == CHUNK_HPDU) {
            return this.handleHPDU((Chunk)object);
        }
        if (n == CHUNK_HPDS) {
            return this.handleHPDS((Chunk)object);
        }
        if (n == CHUNK_NHSG) {
            return this.handleHPSGNHSG((Chunk)object, true);
        }
        if (n == CHUNK_HPGC) {
            return this.handleHPGC((Chunk)object);
        }
        if (n == CHUNK_REAE) {
            return this.handleREAE((Chunk)object);
        }
        if (n == CHUNK_REAQ) {
            return this.handleREAQ((Chunk)object);
        }
        if (n == CHUNK_REAL) {
            return this.handleREAL((Chunk)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown packet ");
        ((StringBuilder)object).append(ChunkHandler.name((int)n));
        throw new RuntimeException(((StringBuilder)object).toString());
    }
}

