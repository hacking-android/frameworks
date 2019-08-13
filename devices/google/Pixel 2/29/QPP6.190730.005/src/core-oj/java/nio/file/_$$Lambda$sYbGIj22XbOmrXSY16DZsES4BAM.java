/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.FileTreeIterator;

public final class _$$Lambda$sYbGIj22XbOmrXSY16DZsES4BAM
implements Runnable {
    private final /* synthetic */ FileTreeIterator f$0;

    public /* synthetic */ _$$Lambda$sYbGIj22XbOmrXSY16DZsES4BAM(FileTreeIterator fileTreeIterator) {
        this.f$0 = fileTreeIterator;
    }

    @Override
    public final void run() {
        this.f$0.close();
    }
}

