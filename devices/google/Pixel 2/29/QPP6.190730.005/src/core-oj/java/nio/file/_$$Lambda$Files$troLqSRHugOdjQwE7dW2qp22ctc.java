/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.nio.file.-$
 *  java.nio.file.-$$Lambda
 *  java.nio.file.-$$Lambda$Files
 *  java.nio.file.-$$Lambda$Files$troLqSRHugOdjQwE7dW2qp22ctc
 */
package java.nio.file;

import java.nio.file.-$;
import java.nio.file.FileTreeWalker;
import java.nio.file.Files;
import java.util.function.Function;

public final class _$$Lambda$Files$troLqSRHugOdjQwE7dW2qp22ctc
implements Function {
    public static final /* synthetic */ -$.Lambda.Files.troLqSRHugOdjQwE7dW2qp22ctc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Files$troLqSRHugOdjQwE7dW2qp22ctc();
    }

    private /* synthetic */ _$$Lambda$Files$troLqSRHugOdjQwE7dW2qp22ctc() {
    }

    public final Object apply(Object object) {
        return Files.lambda$walk$1((FileTreeWalker.Event)object);
    }
}

