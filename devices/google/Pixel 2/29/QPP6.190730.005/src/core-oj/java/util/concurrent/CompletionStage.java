/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface CompletionStage<T> {
    public CompletionStage<Void> acceptEither(CompletionStage<? extends T> var1, Consumer<? super T> var2);

    public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> var1, Consumer<? super T> var2);

    public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> var1, Consumer<? super T> var2, Executor var3);

    public <U> CompletionStage<U> applyToEither(CompletionStage<? extends T> var1, Function<? super T, U> var2);

    public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> var1, Function<? super T, U> var2);

    public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> var1, Function<? super T, U> var2, Executor var3);

    public CompletionStage<T> exceptionally(Function<Throwable, ? extends T> var1);

    public <U> CompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> var1);

    public <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> var1);

    public <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> var1, Executor var2);

    public CompletionStage<Void> runAfterBoth(CompletionStage<?> var1, Runnable var2);

    public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> var1, Runnable var2);

    public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> var1, Runnable var2, Executor var3);

    public CompletionStage<Void> runAfterEither(CompletionStage<?> var1, Runnable var2);

    public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> var1, Runnable var2);

    public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> var1, Runnable var2, Executor var3);

    public CompletionStage<Void> thenAccept(Consumer<? super T> var1);

    public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> var1);

    public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> var1, Executor var2);

    public <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> var1, BiConsumer<? super T, ? super U> var2);

    public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> var1, BiConsumer<? super T, ? super U> var2);

    public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> var1, BiConsumer<? super T, ? super U> var2, Executor var3);

    public <U> CompletionStage<U> thenApply(Function<? super T, ? extends U> var1);

    public <U> CompletionStage<U> thenApplyAsync(Function<? super T, ? extends U> var1);

    public <U> CompletionStage<U> thenApplyAsync(Function<? super T, ? extends U> var1, Executor var2);

    public <U, V> CompletionStage<V> thenCombine(CompletionStage<? extends U> var1, BiFunction<? super T, ? super U, ? extends V> var2);

    public <U, V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> var1, BiFunction<? super T, ? super U, ? extends V> var2);

    public <U, V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> var1, BiFunction<? super T, ? super U, ? extends V> var2, Executor var3);

    public <U> CompletionStage<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> var1);

    public <U> CompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> var1);

    public <U> CompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> var1, Executor var2);

    public CompletionStage<Void> thenRun(Runnable var1);

    public CompletionStage<Void> thenRunAsync(Runnable var1);

    public CompletionStage<Void> thenRunAsync(Runnable var1, Executor var2);

    public CompletableFuture<T> toCompletableFuture();

    public CompletionStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> var1);

    public CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> var1);

    public CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> var1, Executor var2);
}

