/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.nio.file.-$
 *  java.nio.file.-$$Lambda
 *  java.nio.file.-$$Lambda$Files
 *  java.nio.file.-$$Lambda$Files$cNMxoBpYNc_xj_crDjR6l6JHUZ0
 */
package java.nio.file;

import java.nio.file.-$;
import java.nio.file.FileTreeWalker;
import java.nio.file.Files;
import java.util.function.Function;

public final class _$$Lambda$Files$cNMxoBpYNc_xj_crDjR6l6JHUZ0
implements Function {
    public static final /* synthetic */ -$.Lambda.Files.cNMxoBpYNc_xj_crDjR6l6JHUZ0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Files$cNMxoBpYNc_xj_crDjR6l6JHUZ0();
    }

    private /* synthetic */ _$$Lambda$Files$cNMxoBpYNc_xj_crDjR6l6JHUZ0() {
    }

    public final Object apply(Object object) {
        return Files.lambda$find$3((FileTreeWalker.Event)object);
    }
}

