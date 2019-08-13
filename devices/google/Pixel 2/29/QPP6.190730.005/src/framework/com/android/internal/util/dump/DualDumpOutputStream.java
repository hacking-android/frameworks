/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util.dump;

import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.IndentingPrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DualDumpOutputStream {
    private static final String LOG_TAG = DualDumpOutputStream.class.getSimpleName();
    private final LinkedList<DumpObject> mDumpObjects = new LinkedList();
    private final IndentingPrintWriter mIpw;
    private final ProtoOutputStream mProtoStream;

    public DualDumpOutputStream(ProtoOutputStream protoOutputStream) {
        this.mProtoStream = protoOutputStream;
        this.mIpw = null;
    }

    public DualDumpOutputStream(IndentingPrintWriter indentingPrintWriter) {
        this.mProtoStream = null;
        this.mIpw = indentingPrintWriter;
        this.mDumpObjects.add(new DumpObject(null));
    }

    public void end(long l) {
        Object object = this.mProtoStream;
        if (object != null) {
            ((ProtoOutputStream)object).end(l);
        } else {
            if ((long)System.identityHashCode(this.mDumpObjects.getLast()) != l) {
                String string2 = LOG_TAG;
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected token for ending ");
                ((StringBuilder)object).append(this.mDumpObjects.getLast().name);
                ((StringBuilder)object).append(" at ");
                ((StringBuilder)object).append(Arrays.toString(Thread.currentThread().getStackTrace()));
                Log.w(string2, ((StringBuilder)object).toString());
            }
            this.mDumpObjects.removeLast();
        }
    }

    public void flush() {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.flush();
        } else {
            if (this.mDumpObjects.size() == 1) {
                this.mDumpObjects.getFirst().print(this.mIpw, false);
                this.mDumpObjects.clear();
                this.mDumpObjects.add(new DumpObject(null));
            }
            this.mIpw.flush();
        }
    }

    public boolean isProto() {
        boolean bl = this.mProtoStream != null;
        return bl;
    }

    public long start(String string2, long l) {
        Object object = this.mProtoStream;
        if (object != null) {
            return ((ProtoOutputStream)object).start(l);
        }
        object = new DumpObject(string2);
        this.mDumpObjects.getLast().add(string2, (Dumpable)object);
        this.mDumpObjects.addLast((DumpObject)object);
        return System.identityHashCode(object);
    }

    public void write(String string2, long l, double d) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(l, d);
        } else {
            this.mDumpObjects.getLast().add(string2, new DumpField(string2, String.valueOf(d)));
        }
    }

    public void write(String string2, long l, float f) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(l, f);
        } else {
            this.mDumpObjects.getLast().add(string2, new DumpField(string2, String.valueOf(f)));
        }
    }

    public void write(String string2, long l, int n) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(l, n);
        } else {
            this.mDumpObjects.getLast().add(string2, new DumpField(string2, String.valueOf(n)));
        }
    }

    public void write(String string2, long l, long l2) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(l, l2);
        } else {
            this.mDumpObjects.getLast().add(string2, new DumpField(string2, String.valueOf(l2)));
        }
    }

    public void write(String string2, long l, String string3) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(l, string3);
        } else {
            this.mDumpObjects.getLast().add(string2, new DumpField(string2, String.valueOf(string3)));
        }
    }

    public void write(String string2, long l, boolean bl) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(l, bl);
        } else {
            this.mDumpObjects.getLast().add(string2, new DumpField(string2, String.valueOf(bl)));
        }
    }

    public void write(String string2, long l, byte[] arrby) {
        ProtoOutputStream protoOutputStream = this.mProtoStream;
        if (protoOutputStream != null) {
            protoOutputStream.write(l, arrby);
        } else {
            this.mDumpObjects.getLast().add(string2, new DumpField(string2, Arrays.toString(arrby)));
        }
    }

    public void writeNested(String string2, byte[] arrby) {
        if (this.mIpw == null) {
            Log.w(LOG_TAG, "writeNested does not work for proto logging");
            return;
        }
        this.mDumpObjects.getLast().add(string2, new DumpField(string2, new String(arrby, StandardCharsets.UTF_8).trim()));
    }

    private static class DumpField
    extends Dumpable {
        private final String mValue;

        private DumpField(String string2, String string3) {
            super(string2);
            this.mValue = string3;
        }

        @Override
        void print(IndentingPrintWriter indentingPrintWriter, boolean bl) {
            if (bl) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.name);
                stringBuilder.append("=");
                stringBuilder.append(this.mValue);
                indentingPrintWriter.println(stringBuilder.toString());
            } else {
                indentingPrintWriter.println(this.mValue);
            }
        }
    }

    private static class DumpObject
    extends Dumpable {
        private final LinkedHashMap<String, ArrayList<Dumpable>> mSubObjects = new LinkedHashMap();

        private DumpObject(String string2) {
            super(string2);
        }

        public void add(String string2, Dumpable dumpable) {
            ArrayList<Dumpable> arrayList;
            ArrayList<Dumpable> arrayList2 = arrayList = this.mSubObjects.get(string2);
            if (arrayList == null) {
                arrayList2 = new ArrayList(1);
                this.mSubObjects.put(string2, arrayList2);
            }
            arrayList2.add(dumpable);
        }

        @Override
        void print(IndentingPrintWriter indentingPrintWriter, boolean bl) {
            if (bl) {
                StringBuilder serializable = new StringBuilder();
                serializable.append(this.name);
                serializable.append("={");
                indentingPrintWriter.println(serializable.toString());
            } else {
                indentingPrintWriter.println("{");
            }
            indentingPrintWriter.increaseIndent();
            for (ArrayList<Dumpable> arrayList : this.mSubObjects.values()) {
                int n = arrayList.size();
                if (n == 1) {
                    arrayList.get(0).print(indentingPrintWriter, true);
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(arrayList.get((int)0).name);
                stringBuilder.append("=[");
                indentingPrintWriter.println(stringBuilder.toString());
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < n; ++i) {
                    arrayList.get(i).print(indentingPrintWriter, false);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("]");
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("}");
        }
    }

    private static abstract class Dumpable {
        final String name;

        private Dumpable(String string2) {
            this.name = string2;
        }

        abstract void print(IndentingPrintWriter var1, boolean var2);
    }

}

