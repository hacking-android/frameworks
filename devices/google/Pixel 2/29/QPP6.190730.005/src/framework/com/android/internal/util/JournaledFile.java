/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import java.io.File;
import java.io.IOException;

@Deprecated
public class JournaledFile {
    File mReal;
    File mTemp;
    boolean mWriting;

    @UnsupportedAppUsage
    public JournaledFile(File file, File file2) {
        this.mReal = file;
        this.mTemp = file2;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public File chooseForRead() {
        block7 : {
            File file;
            block6 : {
                block5 : {
                    File file2;
                    if (!this.mReal.exists()) break block5;
                    file = file2 = this.mReal;
                    if (this.mTemp.exists()) {
                        this.mTemp.delete();
                        file = file2;
                    }
                    break block6;
                }
                if (!this.mTemp.exists()) break block7;
                file = this.mTemp;
                this.mTemp.renameTo(this.mReal);
            }
            return file;
        }
        return this.mReal;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public File chooseForWrite() {
        if (!this.mWriting) {
            if (!this.mReal.exists()) {
                try {
                    this.mReal.createNewFile();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            if (this.mTemp.exists()) {
                this.mTemp.delete();
            }
            this.mWriting = true;
            return this.mTemp;
        }
        throw new IllegalStateException("uncommitted write already in progress");
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void commit() {
        if (this.mWriting) {
            this.mWriting = false;
            this.mTemp.renameTo(this.mReal);
            return;
        }
        throw new IllegalStateException("no file to commit");
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void rollback() {
        if (this.mWriting) {
            this.mWriting = false;
            this.mTemp.delete();
            return;
        }
        throw new IllegalStateException("no file to roll back");
    }
}

