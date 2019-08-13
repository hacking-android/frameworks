/*
 * Decompiled with CFR 0.145.
 */
package libcore.timezone;

import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import libcore.timezone.TimeZoneFinder;

public final class _$$Lambda$TimeZoneFinder$ReaderSupplier$gDAfECMWS_ohJ_Rfk1HN7JyDSJA
implements TimeZoneFinder.ReaderSupplier {
    private final /* synthetic */ Path f$0;
    private final /* synthetic */ Charset f$1;

    public /* synthetic */ _$$Lambda$TimeZoneFinder$ReaderSupplier$gDAfECMWS_ohJ_Rfk1HN7JyDSJA(Path path, Charset charset) {
        this.f$0 = path;
        this.f$1 = charset;
    }

    @Override
    public final Reader get() {
        return TimeZoneFinder.ReaderSupplier.lambda$forFile$0(this.f$0, this.f$1);
    }
}

