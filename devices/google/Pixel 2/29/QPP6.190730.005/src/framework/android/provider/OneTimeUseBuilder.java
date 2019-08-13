/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

public abstract class OneTimeUseBuilder<T> {
    private boolean used = false;

    public abstract T build();

    protected void checkNotUsed() {
        if (!this.used) {
            return;
        }
        throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
    }

    protected void markUsed() {
        this.checkNotUsed();
        this.used = true;
    }
}

