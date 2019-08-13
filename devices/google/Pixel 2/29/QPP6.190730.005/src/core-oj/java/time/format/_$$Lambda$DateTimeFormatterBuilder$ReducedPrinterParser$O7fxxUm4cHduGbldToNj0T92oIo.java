/*
 * Decompiled with CFR 0.145.
 */
package java.time.format;

import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseContext;
import java.util.function.Consumer;

public final class _$$Lambda$DateTimeFormatterBuilder$ReducedPrinterParser$O7fxxUm4cHduGbldToNj0T92oIo
implements Consumer {
    private final /* synthetic */ DateTimeFormatterBuilder.ReducedPrinterParser f$0;
    private final /* synthetic */ DateTimeParseContext f$1;
    private final /* synthetic */ long f$2;
    private final /* synthetic */ int f$3;
    private final /* synthetic */ int f$4;

    public /* synthetic */ _$$Lambda$DateTimeFormatterBuilder$ReducedPrinterParser$O7fxxUm4cHduGbldToNj0T92oIo(DateTimeFormatterBuilder.ReducedPrinterParser reducedPrinterParser, DateTimeParseContext dateTimeParseContext, long l, int n, int n2) {
        this.f$0 = reducedPrinterParser;
        this.f$1 = dateTimeParseContext;
        this.f$2 = l;
        this.f$3 = n;
        this.f$4 = n2;
    }

    public final void accept(Object object) {
        this.f$0.lambda$setValue$0$DateTimeFormatterBuilder$ReducedPrinterParser(this.f$1, this.f$2, this.f$3, this.f$4, (Chronology)object);
    }
}

