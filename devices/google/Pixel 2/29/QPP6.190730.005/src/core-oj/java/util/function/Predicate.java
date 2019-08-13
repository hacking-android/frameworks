/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.function.-$
 *  java.util.function.-$$Lambda
 *  java.util.function.-$$Lambda$wLIh0GiBW9398cTP8uaTH8KoGwo
 */
package java.util.function;

import java.util.Objects;
import java.util.function.-$;
import java.util.function._$$Lambda$Predicate$17UUIF1CH_K9duk0ChtjSwOycuM;
import java.util.function._$$Lambda$Predicate$GyIVQ08CWbeMZxHDkkrN_5apRkc;
import java.util.function._$$Lambda$Predicate$L51YwfosqnYQ8QKStSMYaDgSslA;
import java.util.function._$$Lambda$Predicate$SDsDck317M7uJ9htNLy7zOBr1L8;
import java.util.function._$$Lambda$wLIh0GiBW9398cTP8uaTH8KoGwo;

@FunctionalInterface
public interface Predicate<T> {
    public static <T> Predicate<T> isEqual(Object object) {
        object = object == null ? _$$Lambda$wLIh0GiBW9398cTP8uaTH8KoGwo.INSTANCE : new _$$Lambda$Predicate$SDsDck317M7uJ9htNLy7zOBr1L8(object);
        return object;
    }

    public static /* synthetic */ boolean lambda$and$0(Predicate predicate, Predicate predicate2, Object object) {
        boolean bl = predicate.test(object) && predicate2.test(object);
        return bl;
    }

    public static /* synthetic */ boolean lambda$isEqual$3(Object object, Object object2) {
        return object.equals(object2);
    }

    public static /* synthetic */ boolean lambda$negate$1(Predicate predicate, Object object) {
        return predicate.test(object) ^ true;
    }

    public static /* synthetic */ boolean lambda$or$2(Predicate predicate, Predicate predicate2, Object object) {
        boolean bl = predicate.test(object) || predicate2.test(object);
        return bl;
    }

    default public Predicate<T> and(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return new _$$Lambda$Predicate$GyIVQ08CWbeMZxHDkkrN_5apRkc(this, predicate);
    }

    default public Predicate<T> negate() {
        return new _$$Lambda$Predicate$L51YwfosqnYQ8QKStSMYaDgSslA(this);
    }

    default public Predicate<T> or(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return new _$$Lambda$Predicate$17UUIF1CH_K9duk0ChtjSwOycuM(this, predicate);
    }

    public boolean test(T var1);
}

