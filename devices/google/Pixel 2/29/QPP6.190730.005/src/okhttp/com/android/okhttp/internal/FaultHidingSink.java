/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal;

import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.ForwardingSink;
import com.android.okhttp.okio.Sink;
import java.io.IOException;

class FaultHidingSink
extends ForwardingSink {
    private boolean hasErrors;

    public FaultHidingSink(Sink sink) {
        super(sink);
    }

    @Override
    public void close() throws IOException {
        if (this.hasErrors) {
            return;
        }
        try {
            super.close();
        }
        catch (IOException iOException) {
            this.hasErrors = true;
            this.onException(iOException);
        }
    }

    @Override
    public void flush() throws IOException {
        if (this.hasErrors) {
            return;
        }
        try {
            super.flush();
        }
        catch (IOException iOException) {
            this.hasErrors = true;
            this.onException(iOException);
        }
    }

    protected void onException(IOException iOException) {
    }

    @Override
    public void write(Buffer buffer, long l) throws IOException {
        if (this.hasErrors) {
            buffer.skip(l);
            return;
        }
        try {
            super.write(buffer, l);
        }
        catch (IOException iOException) {
            this.hasErrors = true;
            this.onException(iOException);
        }
    }
}

