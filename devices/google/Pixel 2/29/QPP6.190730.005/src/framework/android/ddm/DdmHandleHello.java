/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 *  org.apache.harmony.dalvik.ddmc.Chunk
 *  org.apache.harmony.dalvik.ddmc.ChunkHandler
 *  org.apache.harmony.dalvik.ddmc.DdmServer
 */
package android.ddm;

import android.ddm.DdmHandleAppName;
import android.os.Debug;
import android.os.Process;
import android.os.UserHandle;
import dalvik.system.VMRuntime;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

public class DdmHandleHello
extends ChunkHandler {
    public static final int CHUNK_FEAT;
    public static final int CHUNK_HELO;
    public static final int CHUNK_WAIT;
    private static final int CLIENT_PROTOCOL_VERSION = 1;
    private static final String[] FRAMEWORK_FEATURES;
    private static DdmHandleHello mInstance;

    static {
        CHUNK_HELO = DdmHandleHello.type((String)"HELO");
        CHUNK_WAIT = DdmHandleHello.type((String)"WAIT");
        CHUNK_FEAT = DdmHandleHello.type((String)"FEAT");
        mInstance = new DdmHandleHello();
        FRAMEWORK_FEATURES = new String[]{"opengl-tracing", "view-hierarchy"};
    }

    private DdmHandleHello() {
    }

    private Chunk handleFEAT(Chunk arrstring) {
        int n;
        arrstring = Debug.getVmFeatureList();
        int n2 = (arrstring.length + FRAMEWORK_FEATURES.length) * 4 + 4;
        for (n = arrstring.length - 1; n >= 0; --n) {
            n2 += arrstring[n].length() * 2;
        }
        for (n = DdmHandleHello.FRAMEWORK_FEATURES.length - 1; n >= 0; --n) {
            n2 += FRAMEWORK_FEATURES[n].length() * 2;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.order(ChunkHandler.CHUNK_ORDER);
        byteBuffer.putInt(arrstring.length + FRAMEWORK_FEATURES.length);
        for (n2 = arrstring.length - 1; n2 >= 0; --n2) {
            byteBuffer.putInt(arrstring[n2].length());
            DdmHandleHello.putString((ByteBuffer)byteBuffer, (String)arrstring[n2]);
        }
        for (n2 = DdmHandleHello.FRAMEWORK_FEATURES.length - 1; n2 >= 0; --n2) {
            byteBuffer.putInt(FRAMEWORK_FEATURES[n2].length());
            DdmHandleHello.putString((ByteBuffer)byteBuffer, (String)FRAMEWORK_FEATURES[n2]);
        }
        return new Chunk(CHUNK_FEAT, byteBuffer);
    }

    private Chunk handleHELO(Chunk object) {
        DdmHandleHello.wrapChunk((Chunk)object).getInt();
        String string2 = System.getProperty("java.vm.name", "?");
        Object object2 = System.getProperty("java.vm.version", "?");
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" v");
        ((StringBuilder)object).append((String)object2);
        string2 = ((StringBuilder)object).toString();
        String string3 = DdmHandleAppName.getAppName();
        Object object3 = VMRuntime.getRuntime();
        object = object3.is64Bit() ? "64-bit" : "32-bit";
        CharSequence charSequence = object3.vmInstructionSet();
        object2 = object;
        if (charSequence != null) {
            object2 = object;
            if (((String)charSequence).length() > 0) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(" (");
                ((StringBuilder)object2).append((String)charSequence);
                ((StringBuilder)object2).append(")");
                object2 = ((StringBuilder)object2).toString();
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("CheckJNI=");
        object = object3.isCheckJniEnabled() ? "true" : "false";
        ((StringBuilder)charSequence).append((String)object);
        object = ((StringBuilder)charSequence).toString();
        boolean bl = object3.isNativeDebuggable();
        object3 = ByteBuffer.allocate(string2.length() * 2 + 28 + string3.length() * 2 + ((String)object2).length() * 2 + ((String)object).length() * 2 + 1);
        ((ByteBuffer)object3).order(ChunkHandler.CHUNK_ORDER);
        ((ByteBuffer)object3).putInt(1);
        ((ByteBuffer)object3).putInt(Process.myPid());
        ((ByteBuffer)object3).putInt(string2.length());
        ((ByteBuffer)object3).putInt(string3.length());
        DdmHandleHello.putString((ByteBuffer)object3, (String)string2);
        DdmHandleHello.putString((ByteBuffer)object3, (String)string3);
        ((ByteBuffer)object3).putInt(UserHandle.myUserId());
        ((ByteBuffer)object3).putInt(((String)object2).length());
        DdmHandleHello.putString((ByteBuffer)object3, (String)object2);
        ((ByteBuffer)object3).putInt(((String)object).length());
        DdmHandleHello.putString((ByteBuffer)object3, (String)object);
        ((ByteBuffer)object3).put((byte)(bl ? 1 : 0));
        object = new Chunk(CHUNK_HELO, (ByteBuffer)object3);
        if (Debug.waitingForDebugger()) {
            DdmHandleHello.sendWAIT(0);
        }
        return object;
    }

    public static void register() {
        DdmServer.registerHandler((int)CHUNK_HELO, (ChunkHandler)mInstance);
        DdmServer.registerHandler((int)CHUNK_FEAT, (ChunkHandler)mInstance);
    }

    public static void sendWAIT(int n) {
        byte by = (byte)n;
        DdmServer.sendChunk((Chunk)new Chunk(CHUNK_WAIT, new byte[]{by}, 0, 1));
    }

    public void connected() {
    }

    public void disconnected() {
    }

    public Chunk handleChunk(Chunk object) {
        int n = ((Chunk)object).type;
        if (n == CHUNK_HELO) {
            return this.handleHELO((Chunk)object);
        }
        if (n == CHUNK_FEAT) {
            return this.handleFEAT((Chunk)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown packet ");
        ((StringBuilder)object).append(ChunkHandler.name((int)n));
        throw new RuntimeException(((StringBuilder)object).toString());
    }
}

