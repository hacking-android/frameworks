/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.FileTreeWalker;
import java.nio.file.Files;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class _$$Lambda$Files$4idNQbLxq4bhe2g1MNnC6cjfF5E
implements Predicate {
    private final /* synthetic */ BiPredicate f$0;

    public /* synthetic */ _$$Lambda$Files$4idNQbLxq4bhe2g1MNnC6cjfF5E(BiPredicate biPredicate) {
        this.f$0 = biPredicate;
    }

    public final boolean test(Object object) {
        return Files.lambda$find$2(this.f$0, (FileTreeWalker.Event)object);
    }
}

