/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.ShellCallback;
import android.os.ShellCommand;
import java.io.FileDescriptor;
import java.io.PrintStream;

public abstract class BaseCommand {
    public static final String FATAL_ERROR_CODE = "Error type 1";
    public static final String NO_CLASS_ERROR_CODE = "Error type 3";
    public static final String NO_SYSTEM_ERROR_CODE = "Error type 2";
    @UnsupportedAppUsage
    protected final ShellCommand mArgs = new ShellCommand(){

        @Override
        public int onCommand(String string2) {
            return 0;
        }

        @Override
        public void onHelp() {
        }
    };
    private String[] mRawArgs;

    public String[] getRawArgs() {
        return this.mRawArgs;
    }

    public String nextArg() {
        return this.mArgs.getNextArg();
    }

    public String nextArgRequired() {
        return this.mArgs.getNextArgRequired();
    }

    public String nextOption() {
        return this.mArgs.getNextOption();
    }

    public abstract void onRun() throws Exception;

    public abstract void onShowUsage(PrintStream var1);

    public String peekNextArg() {
        return this.mArgs.peekNextArg();
    }

    public void run(String[] arrstring) {
        if (arrstring.length < 1) {
            this.onShowUsage(System.out);
            return;
        }
        this.mRawArgs = arrstring;
        this.mArgs.init(null, null, null, null, arrstring, null, 0);
        try {
            this.onRun();
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
            System.exit(1);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.onShowUsage(System.err);
            System.err.println();
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error: ");
            stringBuilder.append(illegalArgumentException.getMessage());
            printStream.println(stringBuilder.toString());
        }
    }

    public void showError(String string2) {
        this.onShowUsage(System.err);
        System.err.println();
        System.err.println(string2);
    }

    public void showUsage() {
        this.onShowUsage(System.err);
    }

}

