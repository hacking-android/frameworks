/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class _$$Lambda$Collectors$BwzHl6O1mjAgxLE58ctIeFoVBAM
implements Supplier {
    private final /* synthetic */ BinaryOperator f$0;

    public /* synthetic */ _$$Lambda$Collectors$BwzHl6O1mjAgxLE58ctIeFoVBAM(BinaryOperator binaryOperator) {
        this.f$0 = binaryOperator;
    }

    public final Object get() {
        return Collectors.lambda$reducing$38(this.f$0);
    }
}

