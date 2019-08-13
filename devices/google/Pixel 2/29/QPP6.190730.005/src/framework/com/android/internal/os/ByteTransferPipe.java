/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import com.android.internal.os.TransferPipe;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;

public class ByteTransferPipe
extends TransferPipe {
    static final String TAG = "ByteTransferPipe";
    private ByteArrayOutputStream mOutputStream;

    public ByteTransferPipe() throws IOException {
    }

    public ByteTransferPipe(String string2) throws IOException {
        super(string2, TAG);
    }

    public byte[] get() throws IOException {
        this.go(null);
        return this.mOutputStream.toByteArray();
    }

    @Override
    protected OutputStream getNewOutputStream() {
        this.mOutputStream = new ByteArrayOutputStream();
        return this.mOutputStream;
    }
}

