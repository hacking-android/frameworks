/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.apache.harmony.dalvik.ddmc.Chunk
 *  org.apache.harmony.dalvik.ddmc.ChunkHandler
 *  org.apache.harmony.dalvik.ddmc.DdmServer
 */
package android.ddm;

import android.annotation.UnsupportedAppUsage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

public class DdmHandleAppName
extends ChunkHandler {
    public static final int CHUNK_APNM = DdmHandleAppName.type((String)"APNM");
    private static volatile String mAppName = "";
    private static DdmHandleAppName mInstance = new DdmHandleAppName();

    private DdmHandleAppName() {
    }

    @UnsupportedAppUsage
    public static String getAppName() {
        return mAppName;
    }

    public static void register() {
    }

    private static void sendAPNM(String string2, int n) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(string2.length() * 2 + 4 + 4);
        byteBuffer.order(ChunkHandler.CHUNK_ORDER);
        byteBuffer.putInt(string2.length());
        DdmHandleAppName.putString((ByteBuffer)byteBuffer, (String)string2);
        byteBuffer.putInt(n);
        DdmServer.sendChunk((Chunk)new Chunk(CHUNK_APNM, byteBuffer));
    }

    @UnsupportedAppUsage
    public static void setAppName(String string2, int n) {
        if (string2 != null && string2.length() != 0) {
            mAppName = string2;
            DdmHandleAppName.sendAPNM(string2, n);
            return;
        }
    }

    public void connected() {
    }

    public void disconnected() {
    }

    public Chunk handleChunk(Chunk chunk) {
        return null;
    }
}

