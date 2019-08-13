/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.content;

import android.os.ParcelFileDescriptor;
import com.android.internal.content.FileSystemProvider;
import java.io.File;
import java.io.IOException;

public final class _$$Lambda$FileSystemProvider$y9rjeYFpkvVjwD2Whw_ujCM_C7Y
implements ParcelFileDescriptor.OnCloseListener {
    private final /* synthetic */ FileSystemProvider f$0;
    private final /* synthetic */ String f$1;
    private final /* synthetic */ File f$2;

    public /* synthetic */ _$$Lambda$FileSystemProvider$y9rjeYFpkvVjwD2Whw_ujCM_C7Y(FileSystemProvider fileSystemProvider, String string2, File file) {
        this.f$0 = fileSystemProvider;
        this.f$1 = string2;
        this.f$2 = file;
    }

    @Override
    public final void onClose(IOException iOException) {
        this.f$0.lambda$openDocument$0$FileSystemProvider(this.f$1, this.f$2, iOException);
    }
}

