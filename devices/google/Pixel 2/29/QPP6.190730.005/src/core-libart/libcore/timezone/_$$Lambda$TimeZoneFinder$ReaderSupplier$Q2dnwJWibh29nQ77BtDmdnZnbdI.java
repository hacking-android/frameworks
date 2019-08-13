/*
 * Decompiled with CFR 0.145.
 */
package libcore.timezone;

import java.io.Reader;
import libcore.timezone.TimeZoneFinder;

public final class _$$Lambda$TimeZoneFinder$ReaderSupplier$Q2dnwJWibh29nQ77BtDmdnZnbdI
implements TimeZoneFinder.ReaderSupplier {
    private final /* synthetic */ String f$0;

    public /* synthetic */ _$$Lambda$TimeZoneFinder$ReaderSupplier$Q2dnwJWibh29nQ77BtDmdnZnbdI(String string) {
        this.f$0 = string;
    }

    @Override
    public final Reader get() {
        return TimeZoneFinder.ReaderSupplier.lambda$forString$1(this.f$0);
    }
}

