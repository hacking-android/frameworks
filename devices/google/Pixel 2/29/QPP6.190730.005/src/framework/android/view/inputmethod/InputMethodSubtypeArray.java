/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Slog;
import android.view.inputmethod.InputMethodSubtype;
import java.util.List;

public class InputMethodSubtypeArray {
    private static final String TAG = "InputMethodSubtypeArray";
    private volatile byte[] mCompressedData;
    private final int mCount;
    private volatile int mDecompressedSize;
    private volatile InputMethodSubtype[] mInstance;
    private final Object mLockObject = new Object();

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    public InputMethodSubtypeArray(Parcel parcel) {
        this.mCount = parcel.readInt();
        if (this.mCount > 0) {
            this.mDecompressedSize = parcel.readInt();
            this.mCompressedData = parcel.createByteArray();
        }
    }

    @UnsupportedAppUsage
    public InputMethodSubtypeArray(List<InputMethodSubtype> list) {
        if (list == null) {
            this.mCount = 0;
            return;
        }
        this.mCount = list.size();
        this.mInstance = list.toArray(new InputMethodSubtype[this.mCount]);
    }

    /*
     * Exception decompiling
     */
    private static byte[] compress(byte[] var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 4 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    /*
     * Exception decompiling
     */
    private static byte[] decompress(byte[] var0, int var1_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    private static byte[] marshall(InputMethodSubtype[] arrobject) {
        Parcel parcel;
        Parcel parcel2 = null;
        try {
            parcel2 = parcel = Parcel.obtain();
        }
        catch (Throwable throwable) {
            if (parcel2 != null) {
                parcel2.recycle();
            }
            throw throwable;
        }
        parcel.writeTypedArray((Parcelable[])arrobject, 0);
        parcel2 = parcel;
        arrobject = parcel.marshall();
        parcel.recycle();
        return arrobject;
    }

    private static InputMethodSubtype[] unmarshall(byte[] arrobject) {
        Parcel parcel;
        Parcel parcel2 = null;
        try {
            parcel2 = parcel = Parcel.obtain();
        }
        catch (Throwable throwable) {
            if (parcel2 != null) {
                parcel2.recycle();
            }
            throw throwable;
        }
        parcel.unmarshall((byte[])arrobject, 0, arrobject.length);
        parcel2 = parcel;
        parcel.setDataPosition(0);
        parcel2 = parcel;
        arrobject = parcel.createTypedArray(InputMethodSubtype.CREATOR);
        parcel.recycle();
        return arrobject;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InputMethodSubtype get(int n) {
        if (n >= 0 && this.mCount > n) {
            InputMethodSubtype[] arrinputMethodSubtype;
            Object[] arrobject = arrinputMethodSubtype = this.mInstance;
            if (arrinputMethodSubtype == null) {
                Object object = this.mLockObject;
                synchronized (object) {
                    arrobject = arrinputMethodSubtype = this.mInstance;
                    if (arrinputMethodSubtype == null) {
                        arrobject = InputMethodSubtypeArray.decompress(this.mCompressedData, this.mDecompressedSize);
                        this.mCompressedData = null;
                        this.mDecompressedSize = 0;
                        if (arrobject != null) {
                            arrobject = InputMethodSubtypeArray.unmarshall((byte[])arrobject);
                        } else {
                            Slog.e(TAG, "Failed to decompress data. Returns null as fallback.");
                            arrobject = new InputMethodSubtype[this.mCount];
                        }
                        this.mInstance = arrobject;
                    }
                }
            }
            return arrobject[n];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getCount() {
        return this.mCount;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void writeToParcel(Parcel parcel) {
        int n = this.mCount;
        if (n == 0) {
            parcel.writeInt(n);
            return;
        }
        byte[] arrby = this.mCompressedData;
        int n2 = this.mDecompressedSize;
        byte[] arrby2 = arrby;
        n = n2;
        if (arrby == null) {
            arrby2 = arrby;
            n = n2;
            if (n2 == 0) {
                Object object = this.mLockObject;
                synchronized (object) {
                    arrby = this.mCompressedData;
                    n2 = this.mDecompressedSize;
                    arrby2 = arrby;
                    n = n2;
                    if (arrby == null) {
                        arrby2 = arrby;
                        n = n2;
                        if (n2 == 0) {
                            arrby = InputMethodSubtypeArray.marshall(this.mInstance);
                            arrby2 = InputMethodSubtypeArray.compress(arrby);
                            if (arrby2 == null) {
                                n = -1;
                                Slog.i(TAG, "Failed to compress data.");
                            } else {
                                n = arrby.length;
                            }
                            this.mDecompressedSize = n;
                            this.mCompressedData = arrby2;
                        }
                    }
                }
            }
        }
        if (arrby2 != null && n > 0) {
            parcel.writeInt(this.mCount);
            parcel.writeInt(n);
            parcel.writeByteArray(arrby2);
            return;
        }
        Slog.i(TAG, "Unexpected state. Behaving as an empty array.");
        parcel.writeInt(0);
    }
}

