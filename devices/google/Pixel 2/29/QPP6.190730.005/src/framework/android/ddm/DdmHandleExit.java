/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.harmony.dalvik.ddmc.Chunk
 *  org.apache.harmony.dalvik.ddmc.ChunkHandler
 *  org.apache.harmony.dalvik.ddmc.DdmServer
 */
package android.ddm;

import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

public class DdmHandleExit
extends ChunkHandler {
    public static final int CHUNK_EXIT = DdmHandleExit.type((String)"EXIT");
    private static DdmHandleExit mInstance = new DdmHandleExit();

    private DdmHandleExit() {
    }

    public static void register() {
        DdmServer.registerHandler((int)CHUNK_EXIT, (ChunkHandler)mInstance);
    }

    public void connected() {
    }

    public void disconnected() {
    }

    public Chunk handleChunk(Chunk chunk) {
        int n = DdmHandleExit.wrapChunk((Chunk)chunk).getInt();
        Runtime.getRuntime().halt(n);
        return null;
    }
}

