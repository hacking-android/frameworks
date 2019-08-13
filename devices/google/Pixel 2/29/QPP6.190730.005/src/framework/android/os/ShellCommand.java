/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import com.android.internal.util.FastPrintWriter;
import java.io.BufferedInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public abstract class ShellCommand {
    static final boolean DEBUG = false;
    static final String TAG = "ShellCommand";
    private int mArgPos;
    private String[] mArgs;
    private String mCmd;
    private String mCurArgData;
    private FileDescriptor mErr;
    private FastPrintWriter mErrPrintWriter;
    private FileOutputStream mFileErr;
    private FileInputStream mFileIn;
    private FileOutputStream mFileOut;
    private FileDescriptor mIn;
    private InputStream mInputStream;
    private FileDescriptor mOut;
    private FastPrintWriter mOutPrintWriter;
    private ResultReceiver mResultReceiver;
    private ShellCallback mShellCallback;
    private Binder mTarget;

    public ResultReceiver adoptResultReceiver() {
        ResultReceiver resultReceiver = this.mResultReceiver;
        this.mResultReceiver = null;
        return resultReceiver;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int exec(Binder var1_1, FileDescriptor var2_4, FileDescriptor var3_5, FileDescriptor var4_7, String[] var5_8, ShellCallback var6_9, ResultReceiver var7_10) {
        block15 : {
            if (var5_8 != null && var5_8.length > 0) {
                var8_11 = var5_8[0];
                var9_12 = 1;
            } else {
                var8_11 = null;
                var9_12 = 0;
            }
            this.init((Binder)var1_1, (FileDescriptor)var2_4, var3_5, var4_7, var5_8, var6_9, var9_12);
            this.mCmd = var8_11;
            this.mResultReceiver = var7_10;
            var10_13 = -1;
            try {
                var10_13 = var9_12 = this.onCommand(this.mCmd);
                var1_1 = this.mOutPrintWriter;
                if (var1_1 == null) break block15;
            }
            catch (Throwable var1_2) {
                var2_4 = this.getErrPrintWriter();
                var2_4.println();
                var2_4.println("Exception occurred while executing:");
                var1_2.printStackTrace((PrintWriter)var2_4);
                var1_1 = this.mOutPrintWriter;
                if (var1_1 != null) {
                    var1_1.flush();
                }
                if ((var1_1 = this.mErrPrintWriter) != null) {
                    var1_1.flush();
                }
                var1_1 = this.mResultReceiver;
                var9_12 = var10_13;
                if (var1_1 == null) return var9_12;
                var9_12 = var10_13;
                ** GOTO lbl69
                {
                    catch (Throwable var1_3) {
                        var2_4 = this.mOutPrintWriter;
                        if (var2_4 != null) {
                            var2_4.flush();
                        }
                        if ((var2_4 = this.mErrPrintWriter) != null) {
                            var2_4.flush();
                        }
                        if ((var2_4 = this.mResultReceiver) == null) throw var1_3;
                        var2_4.send(-1, null);
                        throw var1_3;
                    }
                }
                catch (SecurityException var3_6) {
                    var1_1 = this.getErrPrintWriter();
                    var2_4 = new StringBuilder();
                    var2_4.append("Security exception: ");
                    var2_4.append(var3_6.getMessage());
                    var1_1.println(var2_4.toString());
                    var1_1.println();
                    var3_6.printStackTrace((PrintWriter)var1_1);
                    var1_1 = this.mOutPrintWriter;
                    if (var1_1 != null) {
                        var1_1.flush();
                    }
                    if ((var1_1 = this.mErrPrintWriter) != null) {
                        var1_1.flush();
                    }
                    var1_1 = this.mResultReceiver;
                    var9_12 = var10_13;
                    if (var1_1 == null) return var9_12;
                    var9_12 = var10_13;
lbl69: // 3 sources:
                    var1_1.send(var9_12, null);
                    return var9_12;
                }
            }
            var1_1.flush();
        }
        if ((var1_1 = this.mErrPrintWriter) != null) {
            var1_1.flush();
        }
        var1_1 = this.mResultReceiver;
        var9_12 = var10_13;
        if (var1_1 == null) return var9_12;
        var9_12 = var10_13;
        ** GOTO lbl69
    }

    public InputStream getBufferedInputStream() {
        if (this.mInputStream == null) {
            this.mInputStream = new BufferedInputStream(this.getRawInputStream());
        }
        return this.mInputStream;
    }

    public FileDescriptor getErrFileDescriptor() {
        return this.mErr;
    }

    public PrintWriter getErrPrintWriter() {
        if (this.mErr == null) {
            return this.getOutPrintWriter();
        }
        if (this.mErrPrintWriter == null) {
            this.mErrPrintWriter = new FastPrintWriter(this.getRawErrorStream());
        }
        return this.mErrPrintWriter;
    }

    public FileDescriptor getInFileDescriptor() {
        return this.mIn;
    }

    public String getNextArg() {
        if (this.mCurArgData != null) {
            String string2 = this.mCurArgData;
            this.mCurArgData = null;
            return string2;
        }
        int n = this.mArgPos;
        String[] arrstring = this.mArgs;
        if (n < arrstring.length) {
            this.mArgPos = n + 1;
            return arrstring[n];
        }
        return null;
    }

    public String getNextArgRequired() {
        CharSequence charSequence = this.getNextArg();
        if (charSequence != null) {
            return charSequence;
        }
        String string2 = this.mArgs[this.mArgPos - 1];
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Argument expected after \"");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("\"");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public String getNextOption() {
        if (this.mCurArgData == null) {
            int n;
            Object object = this.mArgs;
            if ((n = this.mArgPos++) >= ((String[])object).length) {
                return null;
            }
            if (!((String)(object = object[n])).startsWith("-")) {
                return null;
            }
            if (((String)object).equals("--")) {
                return null;
            }
            if (((String)object).length() > 1 && ((String)object).charAt(1) != '-') {
                if (((String)object).length() > 2) {
                    this.mCurArgData = ((String)object).substring(2);
                    return ((String)object).substring(0, 2);
                }
                this.mCurArgData = null;
                return object;
            }
            this.mCurArgData = null;
            return object;
        }
        String string2 = this.mArgs[this.mArgPos - 1];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No argument expected after \"");
        stringBuilder.append(string2);
        stringBuilder.append("\"");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public FileDescriptor getOutFileDescriptor() {
        return this.mOut;
    }

    public PrintWriter getOutPrintWriter() {
        if (this.mOutPrintWriter == null) {
            this.mOutPrintWriter = new FastPrintWriter(this.getRawOutputStream());
        }
        return this.mOutPrintWriter;
    }

    public OutputStream getRawErrorStream() {
        if (this.mFileErr == null) {
            this.mFileErr = new FileOutputStream(this.mErr);
        }
        return this.mFileErr;
    }

    public InputStream getRawInputStream() {
        if (this.mFileIn == null) {
            this.mFileIn = new FileInputStream(this.mIn);
        }
        return this.mFileIn;
    }

    public OutputStream getRawOutputStream() {
        if (this.mFileOut == null) {
            this.mFileOut = new FileOutputStream(this.mOut);
        }
        return this.mFileOut;
    }

    public ShellCallback getShellCallback() {
        return this.mShellCallback;
    }

    public int handleDefaultCommands(String arrstring) {
        if ("dump".equals(arrstring)) {
            arrstring = this.mArgs;
            String[] arrstring2 = new String[arrstring.length - 1];
            System.arraycopy(arrstring, 1, arrstring2, 0, arrstring.length - 1);
            this.mTarget.doDump(this.mOut, this.getOutPrintWriter(), arrstring2);
            return 0;
        }
        if (arrstring != null && !"help".equals(arrstring) && !"-h".equals(arrstring)) {
            PrintWriter printWriter = this.getOutPrintWriter();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown command: ");
            stringBuilder.append((String)arrstring);
            printWriter.println(stringBuilder.toString());
        } else {
            this.onHelp();
        }
        return -1;
    }

    public void init(Binder binder, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] arrstring, ShellCallback shellCallback, int n) {
        this.mTarget = binder;
        this.mIn = fileDescriptor;
        this.mOut = fileDescriptor2;
        this.mErr = fileDescriptor3;
        this.mArgs = arrstring;
        this.mShellCallback = shellCallback;
        this.mResultReceiver = null;
        this.mCmd = null;
        this.mArgPos = n;
        this.mCurArgData = null;
        this.mFileIn = null;
        this.mFileOut = null;
        this.mFileErr = null;
        this.mOutPrintWriter = null;
        this.mErrPrintWriter = null;
        this.mInputStream = null;
    }

    public abstract int onCommand(String var1);

    public abstract void onHelp();

    public ParcelFileDescriptor openFileForSystem(String object, String object2) {
        Appendable appendable;
        try {
            object2 = this.getShellCallback().openFile((String)object, "u:r:system_server:s0", (String)object2);
            if (object2 != null) {
                return object2;
            }
        }
        catch (RuntimeException runtimeException) {
            appendable = this.getErrPrintWriter();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failure opening file: ");
            stringBuilder.append(runtimeException.getMessage());
            ((PrintWriter)appendable).println(stringBuilder.toString());
        }
        object2 = this.getErrPrintWriter();
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("Error: Unable to open file: ");
        ((StringBuilder)appendable).append((String)object);
        ((PrintWriter)object2).println(((StringBuilder)appendable).toString());
        if (object == null || !((String)object).startsWith("/data/local/tmp/")) {
            object = this.getErrPrintWriter();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Consider using a file under ");
            ((StringBuilder)object2).append("/data/local/tmp/");
            ((PrintWriter)object).println(((StringBuilder)object2).toString());
        }
        return null;
    }

    @UnsupportedAppUsage
    public String peekNextArg() {
        String[] arrstring = this.mCurArgData;
        if (arrstring != null) {
            return arrstring;
        }
        int n = this.mArgPos;
        arrstring = this.mArgs;
        if (n < arrstring.length) {
            return arrstring[n];
        }
        return null;
    }
}

