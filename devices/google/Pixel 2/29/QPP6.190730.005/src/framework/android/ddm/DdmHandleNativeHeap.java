/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.harmony.dalvik.ddmc.Chunk
 *  org.apache.harmony.dalvik.ddmc.ChunkHandler
 *  org.apache.harmony.dalvik.ddmc.DdmServer
 */
package android.ddm;

import android.util.Log;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

public class DdmHandleNativeHeap
extends ChunkHandler {
    public static final int CHUNK_NHGT = DdmHandleNativeHeap.type((String)"NHGT");
    private static DdmHandleNativeHeap mInstance = new DdmHandleNativeHeap();

    private DdmHandleNativeHeap() {
    }

    private native byte[] getLeakInfo();

    private Chunk handleNHGT(Chunk object) {
        byte[] arrby = this.getLeakInfo();
        if (arrby != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Sending ");
            ((StringBuilder)object).append(arrby.length);
            ((StringBuilder)object).append(" bytes");
            Log.i("ddm-nativeheap", ((StringBuilder)object).toString());
            return new Chunk(ChunkHandler.type((String)"NHGT"), arrby, 0, arrby.length);
        }
        return DdmHandleNativeHeap.createFailChunk((int)1, (String)"Something went wrong");
    }

    public static void register() {
        DdmServer.registerHandler((int)CHUNK_NHGT, (ChunkHandler)mInstance);
    }

    public void connected() {
    }

    public void disconnected() {
    }

    public Chunk handleChunk(Chunk object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Handling ");
        stringBuilder.append(DdmHandleNativeHeap.name((int)((Chunk)object).type));
        stringBuilder.append(" chunk");
        Log.i("ddm-nativeheap", stringBuilder.toString());
        int n = ((Chunk)object).type;
        if (n == CHUNK_NHGT) {
            return this.handleNHGT((Chunk)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown packet ");
        ((StringBuilder)object).append(ChunkHandler.name((int)n));
        throw new RuntimeException(((StringBuilder)object).toString());
    }
}

