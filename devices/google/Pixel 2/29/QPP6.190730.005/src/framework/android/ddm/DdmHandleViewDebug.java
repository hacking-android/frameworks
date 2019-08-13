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
import android.view.View;
import android.view.ViewDebug;
import android.view.WindowManagerGlobal;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

public class DdmHandleViewDebug
extends ChunkHandler {
    private static final int CHUNK_VULW = DdmHandleViewDebug.type((String)"VULW");
    private static final int CHUNK_VUOP;
    private static final int CHUNK_VURT;
    private static final int ERR_EXCEPTION = -3;
    private static final int ERR_INVALID_OP = -1;
    private static final int ERR_INVALID_PARAM = -2;
    private static final String TAG = "DdmViewDebug";
    private static final int VUOP_CAPTURE_VIEW = 1;
    private static final int VUOP_DUMP_DISPLAYLIST = 2;
    private static final int VUOP_INVOKE_VIEW_METHOD = 4;
    private static final int VUOP_PROFILE_VIEW = 3;
    private static final int VUOP_SET_LAYOUT_PARAMETER = 5;
    private static final int VURT_CAPTURE_LAYERS = 2;
    private static final int VURT_DUMP_HIERARCHY = 1;
    private static final int VURT_DUMP_THEME = 3;
    private static final DdmHandleViewDebug sInstance;

    static {
        CHUNK_VURT = DdmHandleViewDebug.type((String)"VURT");
        CHUNK_VUOP = DdmHandleViewDebug.type((String)"VUOP");
        sInstance = new DdmHandleViewDebug();
    }

    private DdmHandleViewDebug() {
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Chunk captureLayers(View chunk) {
        Throwable throwable2222;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        ViewDebug.captureLayers((View)chunk, dataOutputStream);
        try {
            dataOutputStream.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        chunk = byteArrayOutputStream.toByteArray();
        return new Chunk(CHUNK_VURT, (byte[])chunk, 0, ((byte[])chunk).length);
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                chunk = new StringBuilder();
                chunk.append("Unexpected error while obtaining view hierarchy: ");
                chunk.append(iOException.getMessage());
                chunk = DdmHandleViewDebug.createFailChunk((int)1, (String)chunk.toString());
            }
            try {
                dataOutputStream.close();
                return chunk;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return chunk;
        }
        try {
            dataOutputStream.close();
            throw throwable2222;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        throw throwable2222;
    }

    private Chunk captureView(View arrby, View object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        try {
            ViewDebug.capture((View)arrby, (OutputStream)byteArrayOutputStream, (View)object);
            arrby = byteArrayOutputStream.toByteArray();
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected error while capturing view: ");
            ((StringBuilder)object).append(iOException.getMessage());
            return DdmHandleViewDebug.createFailChunk((int)1, (String)((StringBuilder)object).toString());
        }
        return new Chunk(CHUNK_VUOP, arrby, 0, arrby.length);
    }

    private Chunk dumpDisplayLists(final View view, final View view2) {
        view.post(new Runnable(){

            @Override
            public void run() {
                ViewDebug.outputDisplayList(view, view2);
            }
        });
        return null;
    }

    /*
     * Exception decompiling
     */
    private Chunk dumpHierarchy(View var1_1, ByteBuffer var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[SIMPLE_IF_TAKEN]], but top level block is 0[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private Chunk dumpTheme(View arrby) {
        Object object = new ByteArrayOutputStream(1024);
        try {
            ViewDebug.dumpTheme((View)arrby, (OutputStream)object);
            arrby = ((ByteArrayOutputStream)object).toByteArray();
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected error while dumping the theme: ");
            ((StringBuilder)object).append(iOException.getMessage());
            return DdmHandleViewDebug.createFailChunk((int)1, (String)((StringBuilder)object).toString());
        }
        return new Chunk(CHUNK_VURT, arrby, 0, arrby.length);
    }

    private View getRootView(ByteBuffer object) {
        try {
            object = DdmHandleViewDebug.getString((ByteBuffer)object, (int)((ByteBuffer)object).getInt());
            object = WindowManagerGlobal.getInstance().getRootView((String)object);
            return object;
        }
        catch (BufferUnderflowException bufferUnderflowException) {
            return null;
        }
    }

    private View getTargetView(View view, ByteBuffer object) {
        try {
            object = DdmHandleViewDebug.getString((ByteBuffer)object, (int)((ByteBuffer)object).getInt());
        }
        catch (BufferUnderflowException bufferUnderflowException) {
            return null;
        }
        return ViewDebug.findView(view, (String)object);
    }

    private Chunk invokeViewMethod(View object, View object2, ByteBuffer object3) {
        String string2 = DdmHandleViewDebug.getString((ByteBuffer)object3, (int)((ByteBuffer)object3).getInt());
        if (!((Buffer)object3).hasRemaining()) {
            object3 = new Class[]{};
            object = new Object[]{};
        } else {
            int n = ((ByteBuffer)object3).getInt();
            Class[] arrclass = new Class[n];
            object = new Object[n];
            for (int i = 0; i < n; ++i) {
                char c = ((ByteBuffer)object3).getChar();
                if (c != 'F') {
                    if (c != 'S') {
                        if (c != 'Z') {
                            if (c != 'I') {
                                if (c != 'J') {
                                    switch (c) {
                                        default: {
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("arg ");
                                            ((StringBuilder)object).append(i);
                                            ((StringBuilder)object).append(", unrecognized type: ");
                                            ((StringBuilder)object).append(c);
                                            Log.e(TAG, ((StringBuilder)object).toString());
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append("Unsupported parameter type (");
                                            ((StringBuilder)object).append(c);
                                            ((StringBuilder)object).append(") to invoke view method.");
                                            return DdmHandleViewDebug.createFailChunk((int)-2, (String)((StringBuilder)object).toString());
                                        }
                                        case 'D': {
                                            arrclass[i] = Double.TYPE;
                                            object[i] = ((ByteBuffer)object3).getDouble();
                                            break;
                                        }
                                        case 'C': {
                                            arrclass[i] = Character.TYPE;
                                            object[i] = Character.valueOf(((ByteBuffer)object3).getChar());
                                            break;
                                        }
                                        case 'B': {
                                            arrclass[i] = Byte.TYPE;
                                            object[i] = ((ByteBuffer)object3).get();
                                            break;
                                        }
                                    }
                                    continue;
                                }
                                arrclass[i] = Long.TYPE;
                                object[i] = ((ByteBuffer)object3).getLong();
                                continue;
                            }
                            arrclass[i] = Integer.TYPE;
                            object[i] = ((ByteBuffer)object3).getInt();
                            continue;
                        }
                        arrclass[i] = Boolean.TYPE;
                        boolean bl = ((ByteBuffer)object3).get() != 0;
                        object[i] = bl;
                        continue;
                    }
                    arrclass[i] = Short.TYPE;
                    object[i] = ((ByteBuffer)object3).getShort();
                    continue;
                }
                arrclass[i] = Float.TYPE;
                object[i] = Float.valueOf(((ByteBuffer)object3).getFloat());
            }
            object3 = arrclass;
        }
        try {
            object3 = object2.getClass().getMethod(string2, (Class<?>)object3);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("No such method: ");
            ((StringBuilder)object2).append(noSuchMethodException.getMessage());
            Log.e(TAG, ((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("No such method: ");
            ((StringBuilder)object2).append(noSuchMethodException.getMessage());
            return DdmHandleViewDebug.createFailChunk((int)-2, (String)((StringBuilder)object2).toString());
        }
        try {
            ViewDebug.invokeViewMethod((View)object2, (Method)object3, (Object[])object);
            return null;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Exception while invoking method: ");
            ((StringBuilder)object).append(exception.getCause().getMessage());
            Log.e(TAG, ((StringBuilder)object).toString());
            object = object2 = exception.getCause().getMessage();
            if (object2 == null) {
                object = exception.getCause().toString();
            }
            return DdmHandleViewDebug.createFailChunk((int)-3, (String)object);
        }
    }

    private Chunk listWindows() {
        int n;
        String[] arrstring = WindowManagerGlobal.getInstance().getViewRootNames();
        int n2 = arrstring.length;
        int n3 = 0;
        int n4 = 4;
        for (n = 0; n < n2; ++n) {
            n4 = n4 + 4 + arrstring[n].length() * 2;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n4);
        byteBuffer.order(ChunkHandler.CHUNK_ORDER);
        byteBuffer.putInt(arrstring.length);
        n = arrstring.length;
        for (n4 = n3; n4 < n; ++n4) {
            String string2 = arrstring[n4];
            byteBuffer.putInt(string2.length());
            DdmHandleViewDebug.putString((ByteBuffer)byteBuffer, (String)string2);
        }
        return new Chunk(CHUNK_VULW, byteBuffer);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Chunk profileView(View arrby, View object) {
        Throwable throwable2222;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(32768);
        arrby = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream), 32768);
        ViewDebug.profileViewAndChildren((View)object, (BufferedWriter)arrby);
        try {
            ((BufferedWriter)arrby).close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        arrby = byteArrayOutputStream.toByteArray();
        return new Chunk(CHUNK_VUOP, (byte[])arrby, 0, ((Object)arrby).length);
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected error while profiling view: ");
                ((StringBuilder)object).append(iOException.getMessage());
                object = DdmHandleViewDebug.createFailChunk((int)1, (String)((StringBuilder)object).toString());
            }
            try {
                ((BufferedWriter)arrby).close();
                return object;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return object;
        }
        try {
            ((BufferedWriter)arrby).close();
            throw throwable2222;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        throw throwable2222;
    }

    public static void register() {
        DdmServer.registerHandler((int)CHUNK_VULW, (ChunkHandler)sInstance);
        DdmServer.registerHandler((int)CHUNK_VURT, (ChunkHandler)sInstance);
        DdmServer.registerHandler((int)CHUNK_VUOP, (ChunkHandler)sInstance);
    }

    private Chunk setLayoutParameter(View object, View view, ByteBuffer object2) {
        object = DdmHandleViewDebug.getString((ByteBuffer)object2, (int)((ByteBuffer)object2).getInt());
        int n = ((ByteBuffer)object2).getInt();
        try {
            ViewDebug.setLayoutParameter(view, (String)object, n);
            return null;
        }
        catch (Exception exception) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Exception setting layout parameter: ");
            ((StringBuilder)object2).append(exception);
            Log.e(TAG, ((StringBuilder)object2).toString());
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Error accessing field ");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(":");
            ((StringBuilder)object2).append(exception.getMessage());
            return DdmHandleViewDebug.createFailChunk((int)-3, (String)((StringBuilder)object2).toString());
        }
    }

    public void connected() {
    }

    public void disconnected() {
    }

    public Chunk handleChunk(Chunk object) {
        int n = ((Chunk)object).type;
        if (n == CHUNK_VULW) {
            return this.listWindows();
        }
        ByteBuffer byteBuffer = DdmHandleViewDebug.wrapChunk((Chunk)object);
        int n2 = byteBuffer.getInt();
        View view = this.getRootView(byteBuffer);
        if (view == null) {
            return DdmHandleViewDebug.createFailChunk((int)-2, (String)"Invalid View Root");
        }
        if (n == CHUNK_VURT) {
            if (n2 == 1) {
                return this.dumpHierarchy(view, byteBuffer);
            }
            if (n2 == 2) {
                return this.captureLayers(view);
            }
            if (n2 == 3) {
                return this.dumpTheme(view);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown view root operation: ");
            ((StringBuilder)object).append(n2);
            return DdmHandleViewDebug.createFailChunk((int)-1, (String)((StringBuilder)object).toString());
        }
        object = this.getTargetView(view, byteBuffer);
        if (object == null) {
            return DdmHandleViewDebug.createFailChunk((int)-2, (String)"Invalid target view");
        }
        if (n == CHUNK_VUOP) {
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 != 4) {
                            if (n2 != 5) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Unknown view operation: ");
                                ((StringBuilder)object).append(n2);
                                return DdmHandleViewDebug.createFailChunk((int)-1, (String)((StringBuilder)object).toString());
                            }
                            return this.setLayoutParameter(view, (View)object, byteBuffer);
                        }
                        return this.invokeViewMethod(view, (View)object, byteBuffer);
                    }
                    return this.profileView(view, (View)object);
                }
                return this.dumpDisplayLists(view, (View)object);
            }
            return this.captureView(view, (View)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown packet ");
        ((StringBuilder)object).append(ChunkHandler.name((int)n));
        throw new RuntimeException(((StringBuilder)object).toString());
    }

}

