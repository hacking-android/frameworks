/*
 * Decompiled with CFR 0.145.
 */
package java.nio.channels;

public interface CompletionHandler<V, A> {
    public void completed(V var1, A var2);

    public void failed(Throwable var1, A var2);
}

