/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructCapUserData
 *  android.system.StructCapUserHeader
 *  dalvik.system.VMRuntime
 *  libcore.io.IoUtils
 */
package com.android.internal.os;

import android.os.Process;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructCapUserData;
import android.system.StructCapUserHeader;
import android.util.Slog;
import android.util.TimingsTraceLog;
import com.android.internal.os.RuntimeInit;
import com.android.internal.os.Zygote;
import com.android.internal.os.ZygoteInit;
import dalvik.system.VMRuntime;
import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import libcore.io.IoUtils;

public class WrapperInit {
    private static final String TAG = "AndroidRuntime";

    private WrapperInit() {
    }

    public static void execApplication(String string2, String string3, int n, String string4, FileDescriptor fileDescriptor, String[] arrstring) {
        StringBuilder stringBuilder = new StringBuilder(string2);
        string2 = VMRuntime.is64BitInstructionSet((String)string4) ? "/system/bin/app_process64" : "/system/bin/app_process32";
        stringBuilder.append(' ');
        stringBuilder.append(string2);
        stringBuilder.append(" -Xcompiler-option --generate-mini-debug-info");
        stringBuilder.append(" /system/bin --application");
        if (string3 != null) {
            stringBuilder.append(" '--nice-name=");
            stringBuilder.append(string3);
            stringBuilder.append("'");
        }
        stringBuilder.append(" com.android.internal.os.WrapperInit ");
        int n2 = fileDescriptor != null ? fileDescriptor.getInt$() : 0;
        stringBuilder.append(n2);
        stringBuilder.append(' ');
        stringBuilder.append(n);
        Zygote.appendQuotedShellArgs(stringBuilder, arrstring);
        WrapperInit.preserveCapabilities();
        Zygote.execShell(stringBuilder.toString());
    }

    public static void main(String[] arrstring) {
        String[] arrstring2;
        int n = Integer.parseInt(arrstring[0], 10);
        int n2 = Integer.parseInt(arrstring[1], 10);
        if (n != 0) {
            try {
                FileDescriptor fileDescriptor = new FileDescriptor();
                fileDescriptor.setInt$(n);
                FileOutputStream fileOutputStream = new FileOutputStream(fileDescriptor);
                arrstring2 = new DataOutputStream(fileOutputStream);
                arrstring2.writeInt(Process.myPid());
                arrstring2.close();
                IoUtils.closeQuietly((FileDescriptor)fileDescriptor);
            }
            catch (IOException iOException) {
                Slog.d(TAG, "Could not write pid of wrapped process to Zygote pipe.", iOException);
            }
        }
        ZygoteInit.preload(new TimingsTraceLog("WrapperInitTiming", 16384L));
        arrstring2 = new String[arrstring.length - 2];
        System.arraycopy(arrstring, 2, arrstring2, 0, arrstring2.length);
        WrapperInit.wrapperInit(n2, arrstring2).run();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void preserveCapabilities() {
        Object object;
        StructCapUserData[] arrstructCapUserData;
        block9 : {
            object = new StructCapUserHeader(OsConstants._LINUX_CAPABILITY_VERSION_3, 0);
            try {
                arrstructCapUserData = Os.capget((StructCapUserHeader)object);
                if (arrstructCapUserData[0].permitted == arrstructCapUserData[0].inheritable && arrstructCapUserData[1].permitted == arrstructCapUserData[1].inheritable) break block9;
            }
            catch (ErrnoException errnoException) {
                Slog.e(TAG, "RuntimeInit: Failed capget", errnoException);
                return;
            }
            arrstructCapUserData[0] = new StructCapUserData(arrstructCapUserData[0].effective, arrstructCapUserData[0].permitted, arrstructCapUserData[0].permitted);
            arrstructCapUserData[1] = new StructCapUserData(arrstructCapUserData[1].effective, arrstructCapUserData[1].permitted, arrstructCapUserData[1].permitted);
            try {
                Os.capset((StructCapUserHeader)object, (StructCapUserData[])arrstructCapUserData);
            }
            catch (ErrnoException errnoException) {
                Slog.e(TAG, "RuntimeInit: Failed capset", errnoException);
                return;
            }
        }
        int i = 0;
        while (i < 64) {
            int n = OsConstants.CAP_TO_INDEX((int)i);
            int n2 = OsConstants.CAP_TO_MASK((int)i);
            if ((arrstructCapUserData[n].inheritable & n2) != 0) {
                try {
                    Os.prctl((int)OsConstants.PR_CAP_AMBIENT, (long)OsConstants.PR_CAP_AMBIENT_RAISE, (long)i, (long)0L, (long)0L);
                }
                catch (ErrnoException errnoException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("RuntimeInit: Failed to raise ambient capability ");
                    ((StringBuilder)object).append(i);
                    Slog.e(TAG, ((StringBuilder)object).toString(), errnoException);
                }
            }
            ++i;
        }
        return;
    }

    private static Runnable wrapperInit(int n, String[] arrstring) {
        ClassLoader classLoader;
        ClassLoader classLoader2 = classLoader = null;
        String[] arrstring2 = arrstring;
        if (arrstring != null) {
            classLoader2 = classLoader;
            arrstring2 = arrstring;
            if (arrstring.length > 2) {
                classLoader2 = classLoader;
                arrstring2 = arrstring;
                if (arrstring[0].equals("-cp")) {
                    classLoader2 = ZygoteInit.createPathClassLoader(arrstring[1], n);
                    Thread.currentThread().setContextClassLoader(classLoader2);
                    arrstring2 = new String[arrstring.length - 2];
                    System.arraycopy(arrstring, 2, arrstring2, 0, arrstring.length - 2);
                }
            }
        }
        Zygote.nativePreApplicationInit();
        return RuntimeInit.applicationInit(n, arrstring2, classLoader2);
    }
}

