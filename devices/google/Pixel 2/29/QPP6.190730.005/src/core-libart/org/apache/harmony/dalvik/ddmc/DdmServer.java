/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.dalvik.ddmc;

import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.annotation.optimization.FastNative;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;

public class DdmServer {
    private static final int CONNECTED = 1;
    private static final int DISCONNECTED = 2;
    private static HashMap<Integer, ChunkHandler> mHandlerMap = new HashMap();
    private static volatile boolean mRegistrationComplete = false;
    private static boolean mRegistrationTimedOut = false;

    private DdmServer() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private static void broadcast(int n) {
        HashMap<Integer, ChunkHandler> hashMap = mHandlerMap;
        synchronized (hashMap) {
            Object object = mHandlerMap.values().iterator();
            while (object.hasNext()) {
                ChunkHandler chunkHandler = object.next();
                if (n != 1) {
                    if (n != 2) {
                        object = new UnsupportedOperationException();
                        throw object;
                    }
                    chunkHandler.disconnected();
                    continue;
                }
                chunkHandler.connected();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static Chunk dispatch(int n, byte[] arrby, int n2, int n3) {
        boolean bl;
        HashMap<Integer, ChunkHandler> hashMap = mHandlerMap;
        // MONITORENTER : hashMap
        while (!mRegistrationComplete && !(bl = mRegistrationTimedOut)) {
            try {
                mHandlerMap.wait(1000L);
                if (mRegistrationComplete) continue;
                mRegistrationTimedOut = true;
            }
            catch (InterruptedException interruptedException) {}
        }
        ChunkHandler chunkHandler = mHandlerMap.get(n);
        // MONITOREXIT : hashMap
        if (chunkHandler != null) return chunkHandler.handleChunk(new Chunk(n, arrby, n2, n3));
        return null;
    }

    @FastNative
    private static native void nativeSendChunk(int var0, byte[] var1, int var2, int var3);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void registerHandler(int n, ChunkHandler object) {
        if (object == null) {
            throw new NullPointerException("handler == null");
        }
        HashMap<Integer, ChunkHandler> hashMap = mHandlerMap;
        synchronized (hashMap) {
            if (mHandlerMap.get(n) == null) {
                mHandlerMap.put(n, (ChunkHandler)object);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("type ");
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append(" already registered");
            object = new RuntimeException(stringBuilder.toString());
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void registrationComplete() {
        HashMap<Integer, ChunkHandler> hashMap = mHandlerMap;
        synchronized (hashMap) {
            mRegistrationComplete = true;
            mHandlerMap.notifyAll();
            return;
        }
    }

    @UnsupportedAppUsage
    public static void sendChunk(Chunk chunk) {
        DdmServer.nativeSendChunk(chunk.type, chunk.data, chunk.offset, chunk.length);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ChunkHandler unregisterHandler(int n) {
        HashMap<Integer, ChunkHandler> hashMap = mHandlerMap;
        synchronized (hashMap) {
            return mHandlerMap.remove(n);
        }
    }
}

