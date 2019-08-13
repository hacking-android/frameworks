/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import sun.misc.Unsafe;

public class CompletableFuture<T>
implements Future<T>,
CompletionStage<T> {
    static final int ASYNC = 1;
    private static final Executor ASYNC_POOL;
    static final int NESTED = -1;
    private static final long NEXT;
    static final AltResult NIL;
    private static final long RESULT;
    static final int SPINS;
    private static final long STACK;
    static final int SYNC = 0;
    private static final Unsafe U;
    private static final boolean USE_COMMON_POOL;
    volatile Object result;
    volatile Completion stack;

    static {
        NIL = new AltResult(null);
        int n = ForkJoinPool.getCommonPoolParallelism();
        int n2 = 0;
        boolean bl = n > 1;
        USE_COMMON_POOL = bl;
        Executor executor = USE_COMMON_POOL ? ForkJoinPool.commonPool() : new ThreadPerTaskExecutor();
        ASYNC_POOL = executor;
        if (Runtime.getRuntime().availableProcessors() > 1) {
            n2 = 256;
        }
        SPINS = n2;
        U = Unsafe.getUnsafe();
        try {
            RESULT = U.objectFieldOffset(CompletableFuture.class.getDeclaredField("result"));
            STACK = U.objectFieldOffset(CompletableFuture.class.getDeclaredField("stack"));
            NEXT = U.objectFieldOffset(Completion.class.getDeclaredField("next"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public CompletableFuture() {
    }

    CompletableFuture(Object object) {
        this.result = object;
    }

    public static CompletableFuture<Void> allOf(CompletableFuture<?> ... arrcompletableFuture) {
        return CompletableFuture.andTree(arrcompletableFuture, 0, arrcompletableFuture.length - 1);
    }

    static CompletableFuture<Void> andTree(CompletableFuture<?>[] object, int n, int n2) {
        block6 : {
            CompletableFuture<Void> completableFuture;
            block5 : {
                block4 : {
                    completableFuture = new CompletableFuture<Void>();
                    if (n <= n2) break block4;
                    completableFuture.result = NIL;
                    break block5;
                }
                int n3 = n + n2 >>> 1;
                CompletableFuture<Object> completableFuture2 = n == n3 ? object[n] : CompletableFuture.andTree(object, n, n3);
                if (completableFuture2 == null || (object = n == n2 ? completableFuture2 : (n2 == n3 + 1 ? object[n2] : CompletableFuture.andTree(object, n3 + 1, n2))) == null) break block6;
                if (!completableFuture.biRelay(completableFuture2, (CompletableFuture<?>)object)) {
                    BiRelay biRelay = new BiRelay(completableFuture, completableFuture2, object);
                    completableFuture2.bipush((CompletableFuture<?>)object, biRelay);
                    biRelay.tryFire(0);
                }
            }
            return completableFuture;
        }
        throw new NullPointerException();
    }

    public static CompletableFuture<Object> anyOf(CompletableFuture<?> ... arrcompletableFuture) {
        return CompletableFuture.orTree(arrcompletableFuture, 0, arrcompletableFuture.length - 1);
    }

    static CompletableFuture<Void> asyncRunStage(Executor executor, Runnable runnable) {
        if (runnable != null) {
            CompletableFuture<Void> completableFuture = new CompletableFuture<Void>();
            executor.execute(new AsyncRun(completableFuture, runnable));
            return completableFuture;
        }
        throw new NullPointerException();
    }

    static <U> CompletableFuture<U> asyncSupplyStage(Executor executor, Supplier<U> supplier) {
        if (supplier != null) {
            CompletableFuture<T> completableFuture = new CompletableFuture<T>();
            executor.execute(new AsyncSupply<T>(completableFuture, supplier));
            return completableFuture;
        }
        throw new NullPointerException();
    }

    private <U> CompletableFuture<Void> biAcceptStage(Executor object, CompletionStage<U> completionStage, BiConsumer<? super T, ? super U> biConsumer) {
        CompletableFuture<U> completableFuture;
        if (biConsumer != null && (completableFuture = completionStage.toCompletableFuture()) != null) {
            completionStage = this.newIncompleteFuture();
            if (object != null || !((CompletableFuture)completionStage).biAccept(this, completableFuture, biConsumer, null)) {
                object = new BiAccept<T, U>((Executor)object, (CompletableFuture<Void>)completionStage, this, (CompletableFuture<? super U>)completableFuture, biConsumer);
                this.bipush(completableFuture, (BiCompletion<?, ?, ?>)object);
                ((BiAccept)object).tryFire(0);
            }
            return completionStage;
        }
        throw new NullPointerException();
    }

    private <U, V> CompletableFuture<V> biApplyStage(Executor object, CompletionStage<U> completionStage, BiFunction<? super T, ? super U, ? extends V> biFunction) {
        if (biFunction != null && (completionStage = completionStage.toCompletableFuture()) != null) {
            CompletableFuture<U> completableFuture = this.newIncompleteFuture();
            if (object != null || !completableFuture.biApply(this, (CompletableFuture<S>)completionStage, (BiFunction<R, S, U>)biFunction, (BiApply<R, S, U>)null)) {
                object = new BiApply<T, U, V>((Executor)object, (CompletableFuture<? extends V>)completableFuture, this, (CompletableFuture<? super U>)completionStage, biFunction);
                this.bipush((CompletableFuture<?>)completionStage, (BiCompletion<?, ?, ?>)object);
                ((BiApply)object).tryFire(0);
            }
            return completableFuture;
        }
        throw new NullPointerException();
    }

    private CompletableFuture<Void> biRunStage(Executor object, CompletionStage<?> completionStage, Runnable runnable) {
        CompletableFuture<?> completableFuture;
        if (runnable != null && (completableFuture = completionStage.toCompletableFuture()) != null) {
            completionStage = this.newIncompleteFuture();
            if (object != null || !((CompletableFuture)completionStage).biRun(this, completableFuture, runnable, null)) {
                object = new BiRun((Executor)object, (CompletableFuture<Void>)completionStage, this, completableFuture, runnable);
                this.bipush(completableFuture, (BiCompletion<?, ?, ?>)object);
                ((BiRun)object).tryFire(0);
            }
            return completionStage;
        }
        throw new NullPointerException();
    }

    public static <U> CompletableFuture<U> completedFuture(U object) {
        if (object == null) {
            object = NIL;
        }
        return new CompletableFuture<T>(object);
    }

    public static <U> CompletionStage<U> completedStage(U object) {
        if (object == null) {
            object = NIL;
        }
        return new MinimalStage(object);
    }

    public static Executor delayedExecutor(long l, TimeUnit timeUnit) {
        if (timeUnit != null) {
            return new DelayedExecutor(l, timeUnit, ASYNC_POOL);
        }
        throw new NullPointerException();
    }

    public static Executor delayedExecutor(long l, TimeUnit timeUnit, Executor executor) {
        if (timeUnit != null && executor != null) {
            return new DelayedExecutor(l, timeUnit, executor);
        }
        throw new NullPointerException();
    }

    static Object encodeRelay(Object object) {
        block0 : {
            Throwable throwable;
            if (!(object instanceof AltResult) || (throwable = ((AltResult)object).ex) == null || throwable instanceof CompletionException) break block0;
            object = new AltResult(new CompletionException(throwable));
        }
        return object;
    }

    static Object encodeThrowable(Throwable throwable, Object object) {
        Throwable throwable2;
        if (!(throwable instanceof CompletionException)) {
            throwable2 = new CompletionException(throwable);
        } else {
            throwable2 = throwable;
            if (object instanceof AltResult) {
                throwable2 = throwable;
                if (throwable == ((AltResult)object).ex) {
                    return object;
                }
            }
        }
        return new AltResult(throwable2);
    }

    static AltResult encodeThrowable(Throwable throwable) {
        if (!(throwable instanceof CompletionException)) {
            throwable = new CompletionException(throwable);
        }
        return new AltResult(throwable);
    }

    public static <U> CompletableFuture<U> failedFuture(Throwable throwable) {
        if (throwable != null) {
            return new CompletableFuture<T>(new AltResult(throwable));
        }
        throw new NullPointerException();
    }

    public static <U> CompletionStage<U> failedStage(Throwable throwable) {
        if (throwable != null) {
            return new MinimalStage(new AltResult(throwable));
        }
        throw new NullPointerException();
    }

    static void lazySetNext(Completion completion, Completion completion2) {
        U.putOrderedObject(completion, NEXT, completion2);
    }

    private <U extends T> CompletableFuture<Void> orAcceptStage(Executor object, CompletionStage<U> completionStage, Consumer<? super T> consumer) {
        CompletableFuture<U> completableFuture;
        if (consumer != null && (completableFuture = completionStage.toCompletableFuture()) != null) {
            completionStage = this.newIncompleteFuture();
            if (object != null || !((CompletableFuture)completionStage).orAccept(this, completableFuture, consumer, null)) {
                object = new OrAccept<T, U>((Executor)object, (CompletableFuture<Void>)completionStage, this, completableFuture, consumer);
                this.orpush(completableFuture, (BiCompletion<?, ?, ?>)object);
                ((OrAccept)object).tryFire(0);
            }
            return completionStage;
        }
        throw new NullPointerException();
    }

    private <U extends T, V> CompletableFuture<V> orApplyStage(Executor object, CompletionStage<U> completionStage, Function<? super T, ? extends V> function) {
        if (function != null && (completionStage = completionStage.toCompletableFuture()) != null) {
            CompletableFuture<U> completableFuture = this.newIncompleteFuture();
            if (object != null || !completableFuture.orApply(this, (CompletableFuture<S>)completionStage, (Function<R, U>)function, (OrApply<R, S, U>)null)) {
                object = new OrApply<T, U, V>((Executor)object, (CompletableFuture<? extends V>)completableFuture, this, (CompletableFuture<U>)completionStage, function);
                this.orpush((CompletableFuture<?>)completionStage, (BiCompletion<?, ?, ?>)object);
                ((OrApply)object).tryFire(0);
            }
            return completableFuture;
        }
        throw new NullPointerException();
    }

    private CompletableFuture<Void> orRunStage(Executor object, CompletionStage<?> completionStage, Runnable runnable) {
        if (runnable != null && (completionStage = completionStage.toCompletableFuture()) != null) {
            CompletableFuture<Void> completableFuture = this.newIncompleteFuture();
            if (object != null || !completableFuture.orRun(this, (CompletableFuture<?>)completionStage, runnable, null)) {
                object = new OrRun((Executor)object, completableFuture, this, (CompletableFuture<?>)completionStage, runnable);
                this.orpush((CompletableFuture<?>)completionStage, (BiCompletion<?, ?, ?>)object);
                ((OrRun)object).tryFire(0);
            }
            return completableFuture;
        }
        throw new NullPointerException();
    }

    static CompletableFuture<Object> orTree(CompletableFuture<?>[] object, int n, int n2) {
        CompletableFuture<Object> completableFuture = new CompletableFuture<Object>();
        if (n <= n2) {
            int n3 = n + n2 >>> 1;
            CompletableFuture<Object> completableFuture2 = n == n3 ? object[n] : CompletableFuture.orTree(object, n, n3);
            if (completableFuture2 != null && (object = n == n2 ? completableFuture2 : (n2 == n3 + 1 ? object[n2] : CompletableFuture.orTree(object, n3 + 1, n2))) != null) {
                if (!completableFuture.orRelay(completableFuture2, (CompletableFuture<?>)object)) {
                    OrRelay orRelay = new OrRelay(completableFuture, completableFuture2, object);
                    completableFuture2.orpush((CompletableFuture<?>)object, orRelay);
                    orRelay.tryFire(0);
                }
            } else {
                throw new NullPointerException();
            }
        }
        return completableFuture;
    }

    private static <T> T reportGet(Object object) throws InterruptedException, ExecutionException {
        if (object != null) {
            if (object instanceof AltResult) {
                Object object2;
                object = object2 = ((AltResult)object).ex;
                if (object2 == null) {
                    return null;
                }
                if (!(object instanceof CancellationException)) {
                    object2 = object;
                    if (object instanceof CompletionException) {
                        Throwable throwable = ((Throwable)object).getCause();
                        object2 = object;
                        if (throwable != null) {
                            object2 = throwable;
                        }
                    }
                    throw new ExecutionException((Throwable)object2);
                }
                throw (CancellationException)object;
            }
            return (T)object;
        }
        throw new InterruptedException();
    }

    private static <T> T reportJoin(Object object) {
        if (object instanceof AltResult) {
            object = ((AltResult)object).ex;
            if (object == null) {
                return null;
            }
            if (!(object instanceof CancellationException)) {
                if (object instanceof CompletionException) {
                    throw (CompletionException)object;
                }
                throw new CompletionException((Throwable)object);
            }
            throw (CancellationException)object;
        }
        return (T)object;
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.asyncRunStage(ASYNC_POOL, runnable);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor) {
        return CompletableFuture.asyncRunStage(CompletableFuture.screenExecutor(executor), runnable);
    }

    static Executor screenExecutor(Executor executor) {
        if (!USE_COMMON_POOL && executor == ForkJoinPool.commonPool()) {
            return ASYNC_POOL;
        }
        if (executor != null) {
            return executor;
        }
        throw new NullPointerException();
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return CompletableFuture.asyncSupplyStage(ASYNC_POOL, supplier);
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor) {
        return CompletableFuture.asyncSupplyStage(CompletableFuture.screenExecutor(executor), supplier);
    }

    private Object timedGet(long l) throws TimeoutException {
        if (Thread.interrupted()) {
            return null;
        }
        if (l > 0L) {
            Object object;
            long l2 = System.nanoTime() + l;
            if (l2 == 0L) {
                l2 = 1L;
            }
            Signaller signaller = null;
            boolean bl = false;
            while ((object = this.result) == null) {
                if (signaller == null) {
                    signaller = new Signaller(true, l, l2);
                    continue;
                }
                if (!bl) {
                    bl = this.tryPushStack(signaller);
                    continue;
                }
                if (signaller.nanos <= 0L) break;
                try {
                    ForkJoinPool.managedBlock(signaller);
                }
                catch (InterruptedException interruptedException) {
                    signaller.interrupted = true;
                }
                if (!signaller.interrupted) continue;
            }
            if (signaller != null) {
                signaller.thread = null;
            }
            if (object != null) {
                this.postComplete();
            } else {
                this.cleanStack();
            }
            if (object != null || signaller != null && signaller.interrupted) {
                return object;
            }
        }
        throw new TimeoutException();
    }

    private CompletableFuture<Void> uniAcceptStage(Executor object, Consumer<? super T> consumer) {
        if (consumer != null) {
            CompletableFuture<Void> completableFuture = this.newIncompleteFuture();
            if (object != null || !completableFuture.uniAccept(this, consumer, null)) {
                object = new UniAccept<T>((Executor)object, completableFuture, this, consumer);
                this.push((UniCompletion<?, ?>)object);
                ((UniAccept)object).tryFire(0);
            }
            return completableFuture;
        }
        throw new NullPointerException();
    }

    private <V> CompletableFuture<V> uniApplyStage(Executor object, Function<? super T, ? extends V> function) {
        if (function != null) {
            CompletableFuture<U> completableFuture = this.newIncompleteFuture();
            if (object != null || !completableFuture.uniApply(this, function, null)) {
                object = new UniApply<T, V>((Executor)object, completableFuture, this, function);
                this.push((UniCompletion<?, ?>)object);
                ((UniApply)object).tryFire(0);
            }
            return completableFuture;
        }
        throw new NullPointerException();
    }

    private MinimalStage<T> uniAsMinimalStage() {
        MinimalStage minimalStage = this.result;
        if (minimalStage != null) {
            return new MinimalStage(CompletableFuture.encodeRelay(minimalStage));
        }
        minimalStage = new MinimalStage();
        UniRelay uniRelay = new UniRelay(minimalStage, this);
        this.push(uniRelay);
        uniRelay.tryFire(0);
        return minimalStage;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private <V> CompletableFuture<V> uniComposeStage(Executor object, Function<? super T, ? extends CompletionStage<V>> uniRelay) {
        if (uniRelay == null) throw new NullPointerException();
        CompletableFuture<U> completableFuture = this.newIncompleteFuture();
        if (object == null) {
            Object object2;
            Object object3 = object2 = this.result;
            if (object2 != null) {
                object = object3;
                if (object3 instanceof AltResult) {
                    object = ((AltResult)object3).ex;
                    if (object != null) {
                        completableFuture.result = CompletableFuture.encodeThrowable(object, object3);
                        return completableFuture;
                    }
                    object = null;
                }
                try {
                    object = ((CompletionStage)uniRelay.apply(object)).toCompletableFuture();
                    uniRelay = ((CompletableFuture)object).result;
                    if (uniRelay != null) {
                        completableFuture.completeRelay(uniRelay);
                        return completableFuture;
                    }
                    uniRelay = new UniRelay<U>(completableFuture, (CompletableFuture<U>)object);
                    ((CompletableFuture)object).push(uniRelay);
                    uniRelay.tryFire(0);
                    return completableFuture;
                }
                catch (Throwable throwable) {
                    completableFuture.result = CompletableFuture.encodeThrowable(throwable);
                    return completableFuture;
                }
            }
        }
        object = new UniCompose((Executor)object, completableFuture, this, uniRelay);
        this.push((UniCompletion<?, ?>)object);
        ((UniCompose)object).tryFire(0);
        return completableFuture;
    }

    private CompletableFuture<T> uniCopyStage() {
        CompletableFuture<U> completableFuture = this.newIncompleteFuture();
        UniRelay<U> uniRelay = this.result;
        if (uniRelay != null) {
            completableFuture.completeRelay(uniRelay);
        } else {
            uniRelay = new UniRelay<U>(completableFuture, this);
            this.push(uniRelay);
            uniRelay.tryFire(0);
        }
        return completableFuture;
    }

    private CompletableFuture<T> uniExceptionallyStage(Function<Throwable, ? extends T> object) {
        if (object != null) {
            CompletableFuture<U> completableFuture = this.newIncompleteFuture();
            if (!completableFuture.uniExceptionally(this, (Function<Throwable, U>)object, (UniExceptionally<U>)null)) {
                object = new UniExceptionally<T>((CompletableFuture<? extends T>)completableFuture, this, (Function<Throwable, ? extends T>)object);
                this.push((UniCompletion<?, ?>)object);
                ((UniExceptionally)object).tryFire(0);
            }
            return completableFuture;
        }
        throw new NullPointerException();
    }

    private <V> CompletableFuture<V> uniHandleStage(Executor object, BiFunction<? super T, Throwable, ? extends V> biFunction) {
        if (biFunction != null) {
            CompletableFuture<U> completableFuture = this.newIncompleteFuture();
            if (object != null || !completableFuture.uniHandle(this, biFunction, null)) {
                object = new UniHandle<T, V>((Executor)object, completableFuture, this, biFunction);
                this.push((UniCompletion<?, ?>)object);
                ((UniHandle)object).tryFire(0);
            }
            return completableFuture;
        }
        throw new NullPointerException();
    }

    private CompletableFuture<Void> uniRunStage(Executor object, Runnable runnable) {
        if (runnable != null) {
            CompletableFuture<Void> completableFuture = this.newIncompleteFuture();
            if (object != null || !completableFuture.uniRun(this, runnable, null)) {
                object = new UniRun((Executor)object, completableFuture, this, runnable);
                this.push((UniCompletion<?, ?>)object);
                ((UniRun)object).tryFire(0);
            }
            return completableFuture;
        }
        throw new NullPointerException();
    }

    private CompletableFuture<T> uniWhenCompleteStage(Executor object, BiConsumer<? super T, ? super Throwable> biConsumer) {
        if (biConsumer != null) {
            CompletableFuture<U> completableFuture = this.newIncompleteFuture();
            if (object != null || !completableFuture.uniWhenComplete(this, biConsumer, null)) {
                object = new UniWhenComplete<T>((Executor)object, completableFuture, this, biConsumer);
                this.push((UniCompletion<?, ?>)object);
                ((UniWhenComplete)object).tryFire(0);
            }
            return completableFuture;
        }
        throw new NullPointerException();
    }

    private Object waitingGet(boolean bl) {
        Object object;
        Signaller signaller = null;
        boolean bl2 = false;
        int n = SPINS;
        while ((object = this.result) == null) {
            if (n > 0) {
                if (ThreadLocalRandom.nextSecondarySeed() < 0) continue;
                --n;
                continue;
            }
            if (signaller == null) {
                signaller = new Signaller(bl, 0L, 0L);
                continue;
            }
            if (!bl2) {
                bl2 = this.tryPushStack(signaller);
                continue;
            }
            try {
                ForkJoinPool.managedBlock(signaller);
            }
            catch (InterruptedException interruptedException) {
                signaller.interrupted = true;
            }
            if (!signaller.interrupted || !bl) continue;
        }
        if (signaller != null) {
            signaller.thread = null;
            if (signaller.interrupted) {
                if (bl) {
                    this.cleanStack();
                } else {
                    Thread.currentThread().interrupt();
                }
            }
        }
        if (object != null) {
            this.postComplete();
        }
        return object;
    }

    public CompletableFuture<Void> acceptEither(CompletionStage<? extends T> completionStage, Consumer<? super T> consumer) {
        return this.orAcceptStage(null, completionStage, consumer);
    }

    public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> completionStage, Consumer<? super T> consumer) {
        return this.orAcceptStage(this.defaultExecutor(), completionStage, consumer);
    }

    public CompletableFuture<Void> acceptEitherAsync(CompletionStage<? extends T> completionStage, Consumer<? super T> consumer, Executor executor) {
        return this.orAcceptStage(CompletableFuture.screenExecutor(executor), completionStage, consumer);
    }

    @Override
    public <U> CompletableFuture<U> applyToEither(CompletionStage<? extends T> completionStage, Function<? super T, U> function) {
        return this.orApplyStage(null, completionStage, function);
    }

    @Override
    public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> completionStage, Function<? super T, U> function) {
        return this.orApplyStage(this.defaultExecutor(), completionStage, function);
    }

    @Override
    public <U> CompletableFuture<U> applyToEitherAsync(CompletionStage<? extends T> completionStage, Function<? super T, U> function, Executor executor) {
        return this.orApplyStage(CompletableFuture.screenExecutor(executor), completionStage, function);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final <R, S> boolean biAccept(CompletableFuture<R> object, CompletableFuture<S> object2, BiConsumer<? super R, ? super S> biConsumer, BiAccept<R, S> biAccept) {
        Object object3;
        block8 : {
            if (object == null) return false;
            object3 = object = ((CompletableFuture)object).result;
            if (object == null || object2 == null) return false;
            object = object2 = ((CompletableFuture)object2).result;
            if (object2 == null || biConsumer == null) return false;
            if (this.result != null) return true;
            object2 = object3;
            if (object3 instanceof AltResult) {
                object2 = ((AltResult)object3).ex;
                if (object2 != null) {
                    this.completeThrowable((Throwable)object2, object3);
                    return true;
                }
                object2 = null;
            }
            object3 = object;
            if (object instanceof AltResult) {
                object3 = ((AltResult)object).ex;
                if (object3 != null) {
                    this.completeThrowable((Throwable)object3, object);
                    return true;
                }
                object3 = null;
            }
            if (biAccept != null) {
                if (biAccept.claim()) break block8;
                return false;
            }
        }
        try {
            biConsumer.accept(object2, object3);
            this.completeNull();
            return true;
        }
        catch (Throwable throwable) {
            this.completeThrowable(throwable);
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final <R, S> boolean biApply(CompletableFuture<R> object, CompletableFuture<S> object2, BiFunction<? super R, ? super S, ? extends T> biFunction, BiApply<R, S, T> biApply) {
        Object object3;
        block8 : {
            if (object == null) return false;
            object3 = object = ((CompletableFuture)object).result;
            if (object == null || object2 == null) return false;
            object = object2 = ((CompletableFuture)object2).result;
            if (object2 == null || biFunction == null) return false;
            if (this.result != null) return true;
            object2 = object3;
            if (object3 instanceof AltResult) {
                object2 = ((AltResult)object3).ex;
                if (object2 != null) {
                    this.completeThrowable((Throwable)object2, object3);
                    return true;
                }
                object2 = null;
            }
            object3 = object;
            if (object instanceof AltResult) {
                object3 = ((AltResult)object).ex;
                if (object3 != null) {
                    this.completeThrowable((Throwable)object3, object);
                    return true;
                }
                object3 = null;
            }
            if (biApply != null) {
                if (biApply.claim()) break block8;
                return false;
            }
        }
        try {
            this.completeValue(biFunction.apply(object2, object3));
            return true;
        }
        catch (Throwable throwable) {
            this.completeThrowable(throwable);
        }
        return true;
    }

    boolean biRelay(CompletableFuture<?> object, CompletableFuture<?> object2) {
        if (object != null && (object = ((CompletableFuture)object).result) != null && object2 != null && (object2 = ((CompletableFuture)object2).result) != null) {
            if (this.result == null) {
                Throwable throwable;
                if (object instanceof AltResult && (throwable = ((AltResult)object).ex) != null) {
                    this.completeThrowable(throwable, object);
                } else if (object2 instanceof AltResult && (object = ((AltResult)object2).ex) != null) {
                    this.completeThrowable((Throwable)object, object2);
                } else {
                    this.completeNull();
                }
            }
            return true;
        }
        return false;
    }

    final boolean biRun(CompletableFuture<?> object, CompletableFuture<?> object2, Runnable runnable, BiRun<?, ?> biRun) {
        Object object3;
        if (object != null && (object3 = ((CompletableFuture)object).result) != null && object2 != null && (object = ((CompletableFuture)object2).result) != null && runnable != null) {
            if (this.result == null) {
                if (object3 instanceof AltResult && (object2 = ((AltResult)object3).ex) != null) {
                    this.completeThrowable((Throwable)object2, object3);
                } else if (object instanceof AltResult && (object2 = ((AltResult)object).ex) != null) {
                    this.completeThrowable((Throwable)object2, object);
                } else {
                    block10 : {
                        if (biRun != null) {
                            if (biRun.claim()) break block10;
                            return false;
                        }
                    }
                    try {
                        runnable.run();
                        this.completeNull();
                    }
                    catch (Throwable throwable) {
                        this.completeThrowable(throwable);
                    }
                }
            }
            return true;
        }
        return false;
    }

    final void bipush(CompletableFuture<?> completableFuture, BiCompletion<?, ?, ?> completion) {
        if (completion != null) {
            Object object;
            while ((object = this.result) == null && !this.tryPushStack(completion)) {
                CompletableFuture.lazySetNext(completion, null);
            }
            if (completableFuture != null && completableFuture != this && completableFuture.result == null) {
                if (object == null) {
                    completion = new CoCompletion((BiCompletion<?, ?, ?>)completion);
                }
                while (completableFuture.result == null && !completableFuture.tryPushStack(completion)) {
                    CompletableFuture.lazySetNext(completion, null);
                }
            }
        }
    }

    @Override
    public boolean cancel(boolean bl) {
        Object object = this.result;
        boolean bl2 = true;
        boolean bl3 = object == null && this.internalComplete(new AltResult(new CancellationException()));
        this.postComplete();
        bl = bl2;
        if (!bl3) {
            bl = this.isCancelled() ? bl2 : false;
        }
        return bl;
    }

    final boolean casStack(Completion completion, Completion completion2) {
        return U.compareAndSwapObject(this, STACK, completion, completion2);
    }

    final void cleanStack() {
        Completion completion = null;
        Completion completion2 = this.stack;
        while (completion2 != null) {
            Completion completion3 = completion2.next;
            if (completion2.isLive()) {
                completion = completion2;
                completion2 = completion3;
                continue;
            }
            if (completion == null) {
                this.casStack(completion2, completion3);
                completion2 = this.stack;
                continue;
            }
            completion.next = completion3;
            if (completion.isLive()) {
                completion2 = completion3;
                continue;
            }
            completion = null;
            completion2 = this.stack;
        }
    }

    public boolean complete(T t) {
        boolean bl = this.completeValue(t);
        this.postComplete();
        return bl;
    }

    public CompletableFuture<T> completeAsync(Supplier<? extends T> supplier) {
        return this.completeAsync(supplier, this.defaultExecutor());
    }

    public CompletableFuture<T> completeAsync(Supplier<? extends T> supplier, Executor executor) {
        if (supplier != null && executor != null) {
            executor.execute(new AsyncSupply<T>(this, supplier));
            return this;
        }
        throw new NullPointerException();
    }

    public boolean completeExceptionally(Throwable throwable) {
        if (throwable != null) {
            boolean bl = this.internalComplete(new AltResult(throwable));
            this.postComplete();
            return bl;
        }
        throw new NullPointerException();
    }

    final boolean completeNull() {
        return U.compareAndSwapObject(this, RESULT, null, NIL);
    }

    public CompletableFuture<T> completeOnTimeout(T t, long l, TimeUnit timeUnit) {
        if (timeUnit != null) {
            if (this.result == null) {
                this.whenComplete((BiConsumer)new Canceller(Delayer.delay(new DelayedCompleter<T>(this, t), l, timeUnit)));
            }
            return this;
        }
        throw new NullPointerException();
    }

    final boolean completeRelay(Object object) {
        return U.compareAndSwapObject(this, RESULT, null, CompletableFuture.encodeRelay(object));
    }

    final boolean completeThrowable(Throwable throwable) {
        return U.compareAndSwapObject(this, RESULT, null, CompletableFuture.encodeThrowable(throwable));
    }

    final boolean completeThrowable(Throwable throwable, Object object) {
        return U.compareAndSwapObject(this, RESULT, null, CompletableFuture.encodeThrowable(throwable, object));
    }

    final boolean completeValue(T object) {
        long l;
        Unsafe unsafe;
        block0 : {
            unsafe = U;
            l = RESULT;
            if (object != null) break block0;
            object = NIL;
        }
        return unsafe.compareAndSwapObject(this, l, null, object);
    }

    public CompletableFuture<T> copy() {
        return this.uniCopyStage();
    }

    public Executor defaultExecutor() {
        return ASYNC_POOL;
    }

    Object encodeOutcome(T object, Throwable throwable) {
        if (throwable == null) {
            if (object == null) {
                object = NIL;
            }
        } else {
            object = CompletableFuture.encodeThrowable(throwable);
        }
        return object;
    }

    final Object encodeValue(T object) {
        block0 : {
            if (object != null) break block0;
            object = NIL;
        }
        return object;
    }

    @Override
    public CompletableFuture<T> exceptionally(Function<Throwable, ? extends T> function) {
        return this.uniExceptionallyStage(function);
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        Object object;
        block0 : {
            object = this.result;
            if (object != null) break block0;
            object = this.waitingGet(true);
        }
        return CompletableFuture.reportGet(object);
    }

    @Override
    public T get(long l, TimeUnit object) throws InterruptedException, ExecutionException, TimeoutException {
        block0 : {
            l = object.toNanos(l);
            object = this.result;
            if (object != null) break block0;
            object = this.timedGet(l);
        }
        return CompletableFuture.reportGet(object);
    }

    public T getNow(T t) {
        Object object = this.result;
        if (object != null) {
            t = CompletableFuture.reportJoin(object);
        }
        return t;
    }

    public int getNumberOfDependents() {
        int n = 0;
        Completion completion = this.stack;
        while (completion != null) {
            ++n;
            completion = completion.next;
        }
        return n;
    }

    @Override
    public <U> CompletableFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> biFunction) {
        return this.uniHandleStage(null, biFunction);
    }

    @Override
    public <U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> biFunction) {
        return this.uniHandleStage(this.defaultExecutor(), biFunction);
    }

    @Override
    public <U> CompletableFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> biFunction, Executor executor) {
        return this.uniHandleStage(CompletableFuture.screenExecutor(executor), biFunction);
    }

    final boolean internalComplete(Object object) {
        return U.compareAndSwapObject(this, RESULT, null, object);
    }

    @Override
    public boolean isCancelled() {
        Object object = this.result;
        boolean bl = object instanceof AltResult && ((AltResult)object).ex instanceof CancellationException;
        return bl;
    }

    public boolean isCompletedExceptionally() {
        Object object = this.result;
        boolean bl = object instanceof AltResult && object != NIL;
        return bl;
    }

    @Override
    public boolean isDone() {
        boolean bl = this.result != null;
        return bl;
    }

    public T join() {
        Object object;
        block0 : {
            object = this.result;
            if (object != null) break block0;
            object = this.waitingGet(false);
        }
        return CompletableFuture.reportJoin(object);
    }

    public CompletionStage<T> minimalCompletionStage() {
        return this.uniAsMinimalStage();
    }

    public <U> CompletableFuture<U> newIncompleteFuture() {
        return new CompletableFuture<T>();
    }

    public void obtrudeException(Throwable throwable) {
        if (throwable != null) {
            this.result = new AltResult(throwable);
            this.postComplete();
            return;
        }
        throw new NullPointerException();
    }

    public void obtrudeValue(T object) {
        if (object == null) {
            object = NIL;
        }
        this.result = object;
        this.postComplete();
    }

    final <R, S extends R> boolean orAccept(CompletableFuture<R> object, CompletableFuture<S> object2, Consumer<? super R> consumer, OrAccept<R, S> orAccept) {
        block13 : {
            block14 : {
                Object object3;
                if (object == null || object2 == null) break block13;
                object = object3 = ((CompletableFuture)object).result;
                if (object3 != null) break block14;
                object = object2 = ((CompletableFuture)object2).result;
                if (object2 == null) break block13;
            }
            if (consumer != null) {
                if (this.result == null) {
                    block11 : {
                        block12 : {
                            block10 : {
                                if (orAccept != null) {
                                    if (orAccept.claim()) break block10;
                                    return false;
                                }
                            }
                            object2 = object;
                            if (!(object instanceof AltResult)) break block11;
                            object2 = ((AltResult)object).ex;
                            if (object2 == null) break block12;
                            this.completeThrowable((Throwable)object2, object);
                        }
                        object2 = null;
                    }
                    try {
                        consumer.accept(object2);
                        this.completeNull();
                    }
                    catch (Throwable throwable) {
                        this.completeThrowable(throwable);
                    }
                }
                return true;
            }
        }
        return false;
    }

    final <R, S extends R> boolean orApply(CompletableFuture<R> object, CompletableFuture<S> object2, Function<? super R, ? extends T> function, OrApply<R, S, T> orApply) {
        block13 : {
            block14 : {
                Object object3;
                if (object == null || object2 == null) break block13;
                object = object3 = ((CompletableFuture)object).result;
                if (object3 != null) break block14;
                object = object2 = ((CompletableFuture)object2).result;
                if (object2 == null) break block13;
            }
            if (function != null) {
                if (this.result == null) {
                    block11 : {
                        block12 : {
                            block10 : {
                                if (orApply != null) {
                                    if (orApply.claim()) break block10;
                                    return false;
                                }
                            }
                            object2 = object;
                            if (!(object instanceof AltResult)) break block11;
                            object2 = ((AltResult)object).ex;
                            if (object2 == null) break block12;
                            this.completeThrowable((Throwable)object2, object);
                        }
                        object2 = null;
                    }
                    try {
                        this.completeValue(function.apply(object2));
                    }
                    catch (Throwable throwable) {
                        this.completeThrowable(throwable);
                    }
                }
                return true;
            }
        }
        return false;
    }

    final boolean orRelay(CompletableFuture<?> object, CompletableFuture<?> object2) {
        block4 : {
            block5 : {
                Object object3;
                if (object == null || object2 == null) break block4;
                object = object3 = ((CompletableFuture)object).result;
                if (object3 != null) break block5;
                object = object2 = ((CompletableFuture)object2).result;
                if (object2 == null) break block4;
            }
            if (this.result == null) {
                this.completeRelay(object);
            }
            return true;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    final boolean orRun(CompletableFuture<?> var1_1, CompletableFuture<?> var2_3, Runnable var3_4, OrRun<?, ?> var4_5) {
        if (var1_1 == null) return false;
        if (var2_3 == null) return false;
        var1_1 = var5_6 = var1_1.result;
        if (var5_6 == null) {
            var1_1 = var2_3 = var2_3.result;
            if (var2_3 == null) return false;
        }
        if (var3_4 == null) {
            return false;
        }
        if (this.result != null) return true;
        if (var4_5 == null) ** GOTO lbl14
        try {
            if (!var4_5.claim()) {
                return false;
            }
lbl14: // 3 sources:
            if (var1_1 instanceof AltResult && (var2_3 = ((AltResult)var1_1).ex) != null) {
                this.completeThrowable((Throwable)var2_3, var1_1);
                return true;
            }
            var3_4.run();
            this.completeNull();
            return true;
        }
        catch (Throwable var1_2) {
            this.completeThrowable(var1_2);
        }
        return true;
    }

    public CompletableFuture<T> orTimeout(long l, TimeUnit timeUnit) {
        if (timeUnit != null) {
            if (this.result == null) {
                this.whenComplete((BiConsumer)new Canceller(Delayer.delay(new Timeout(this), l, timeUnit)));
            }
            return this;
        }
        throw new NullPointerException();
    }

    final void orpush(CompletableFuture<?> completableFuture, BiCompletion<?, ?, ?> completion) {
        if (completion != null) {
            while ((completableFuture == null || completableFuture.result == null) && this.result == null) {
                if (this.tryPushStack(completion)) {
                    if (completableFuture == null || completableFuture == this || completableFuture.result != null) break;
                    completion = new CoCompletion((BiCompletion<?, ?, ?>)completion);
                    while (this.result == null && completableFuture.result == null && !completableFuture.tryPushStack(completion)) {
                        CompletableFuture.lazySetNext(completion, null);
                    }
                    break;
                }
                CompletableFuture.lazySetNext(completion, null);
            }
        }
    }

    final void postComplete() {
        Future<Object> future = this;
        do {
            Future<Void> future2;
            Completion completion;
            CompletableFuture<?> completableFuture;
            block7 : {
                block8 : {
                    completion = future.stack;
                    future2 = completion;
                    completableFuture = future;
                    if (completion != null) break block7;
                    if (future == this) break block8;
                    completableFuture = this;
                    future = this.stack;
                    future2 = future;
                    if (future != null) break block7;
                }
                return;
            }
            completion = ((Completion)future2).next;
            future = completableFuture;
            if (!completableFuture.casStack((Completion)future2, completion)) continue;
            if (completion != null) {
                if (completableFuture != this) {
                    this.pushStack((Completion)future2);
                    future = completableFuture;
                    continue;
                }
                ((Completion)future2).next = null;
            }
            if ((completableFuture = ((Completion)future2).tryFire(-1)) == null) {
                completableFuture = this;
            }
            future = completableFuture;
        } while (true);
    }

    final CompletableFuture<T> postFire(CompletableFuture<?> completableFuture, int n) {
        if (completableFuture != null && completableFuture.stack != null) {
            if (n >= 0 && completableFuture.result != null) {
                completableFuture.postComplete();
            } else {
                completableFuture.cleanStack();
            }
        }
        if (this.result != null && this.stack != null) {
            if (n < 0) {
                return this;
            }
            this.postComplete();
        }
        return null;
    }

    final CompletableFuture<T> postFire(CompletableFuture<?> completableFuture, CompletableFuture<?> completableFuture2, int n) {
        if (completableFuture2 != null && completableFuture2.stack != null) {
            if (n >= 0 && completableFuture2.result != null) {
                completableFuture2.postComplete();
            } else {
                completableFuture2.cleanStack();
            }
        }
        return this.postFire(completableFuture, n);
    }

    final void push(UniCompletion<?, ?> uniCompletion) {
        if (uniCompletion != null) {
            while (this.result == null && !this.tryPushStack(uniCompletion)) {
                CompletableFuture.lazySetNext(uniCompletion, null);
            }
        }
    }

    final void pushStack(Completion completion) {
        while (!this.tryPushStack(completion)) {
        }
    }

    public CompletableFuture<Void> runAfterBoth(CompletionStage<?> completionStage, Runnable runnable) {
        return this.biRunStage(null, completionStage, runnable);
    }

    public CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> completionStage, Runnable runnable) {
        return this.biRunStage(this.defaultExecutor(), completionStage, runnable);
    }

    public CompletableFuture<Void> runAfterBothAsync(CompletionStage<?> completionStage, Runnable runnable, Executor executor) {
        return this.biRunStage(CompletableFuture.screenExecutor(executor), completionStage, runnable);
    }

    public CompletableFuture<Void> runAfterEither(CompletionStage<?> completionStage, Runnable runnable) {
        return this.orRunStage(null, completionStage, runnable);
    }

    public CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> completionStage, Runnable runnable) {
        return this.orRunStage(this.defaultExecutor(), completionStage, runnable);
    }

    public CompletableFuture<Void> runAfterEitherAsync(CompletionStage<?> completionStage, Runnable runnable, Executor executor) {
        return this.orRunStage(CompletableFuture.screenExecutor(executor), completionStage, runnable);
    }

    public CompletableFuture<Void> thenAccept(Consumer<? super T> consumer) {
        return this.uniAcceptStage(null, consumer);
    }

    public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> consumer) {
        return this.uniAcceptStage(this.defaultExecutor(), consumer);
    }

    public CompletableFuture<Void> thenAcceptAsync(Consumer<? super T> consumer, Executor executor) {
        return this.uniAcceptStage(CompletableFuture.screenExecutor(executor), consumer);
    }

    public <U> CompletableFuture<Void> thenAcceptBoth(CompletionStage<? extends U> completionStage, BiConsumer<? super T, ? super U> biConsumer) {
        return this.biAcceptStage(null, completionStage, biConsumer);
    }

    public <U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> completionStage, BiConsumer<? super T, ? super U> biConsumer) {
        return this.biAcceptStage(this.defaultExecutor(), completionStage, biConsumer);
    }

    public <U> CompletableFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> completionStage, BiConsumer<? super T, ? super U> biConsumer, Executor executor) {
        return this.biAcceptStage(CompletableFuture.screenExecutor(executor), completionStage, biConsumer);
    }

    @Override
    public <U> CompletableFuture<U> thenApply(Function<? super T, ? extends U> function) {
        return this.uniApplyStage(null, function);
    }

    @Override
    public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> function) {
        return this.uniApplyStage(this.defaultExecutor(), function);
    }

    @Override
    public <U> CompletableFuture<U> thenApplyAsync(Function<? super T, ? extends U> function, Executor executor) {
        return this.uniApplyStage(CompletableFuture.screenExecutor(executor), function);
    }

    @Override
    public <U, V> CompletableFuture<V> thenCombine(CompletionStage<? extends U> completionStage, BiFunction<? super T, ? super U, ? extends V> biFunction) {
        return this.biApplyStage(null, completionStage, biFunction);
    }

    @Override
    public <U, V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> completionStage, BiFunction<? super T, ? super U, ? extends V> biFunction) {
        return this.biApplyStage(this.defaultExecutor(), completionStage, biFunction);
    }

    @Override
    public <U, V> CompletableFuture<V> thenCombineAsync(CompletionStage<? extends U> completionStage, BiFunction<? super T, ? super U, ? extends V> biFunction, Executor executor) {
        return this.biApplyStage(CompletableFuture.screenExecutor(executor), completionStage, biFunction);
    }

    @Override
    public <U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> function) {
        return this.uniComposeStage(null, function);
    }

    @Override
    public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> function) {
        return this.uniComposeStage(this.defaultExecutor(), function);
    }

    @Override
    public <U> CompletableFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> function, Executor executor) {
        return this.uniComposeStage(CompletableFuture.screenExecutor(executor), function);
    }

    public CompletableFuture<Void> thenRun(Runnable runnable) {
        return this.uniRunStage(null, runnable);
    }

    public CompletableFuture<Void> thenRunAsync(Runnable runnable) {
        return this.uniRunStage(this.defaultExecutor(), runnable);
    }

    public CompletableFuture<Void> thenRunAsync(Runnable runnable, Executor executor) {
        return this.uniRunStage(CompletableFuture.screenExecutor(executor), runnable);
    }

    @Override
    public CompletableFuture<T> toCompletableFuture() {
        return this;
    }

    public String toString() {
        Object object = this.result;
        int n = 0;
        Object object2 = this.stack;
        while (object2 != null) {
            ++n;
            object2 = ((Completion)object2).next;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        if (object == null) {
            if (n == 0) {
                object2 = "[Not completed]";
            } else {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("[Not completed, ");
                ((StringBuilder)object2).append(n);
                ((StringBuilder)object2).append(" dependents]");
                object2 = ((StringBuilder)object2).toString();
            }
        } else {
            object2 = object instanceof AltResult && ((AltResult)object).ex != null ? "[Completed exceptionally]" : "[Completed normally]";
        }
        stringBuilder.append((String)object2);
        return stringBuilder.toString();
    }

    final boolean tryPushStack(Completion completion) {
        Completion completion2 = this.stack;
        CompletableFuture.lazySetNext(completion, completion2);
        return U.compareAndSwapObject(this, STACK, completion2, completion);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final <S> boolean uniAccept(CompletableFuture<S> object, Consumer<? super S> consumer, UniAccept<S> uniAccept) {
        Object object2;
        block6 : {
            if (object == null) return false;
            object = object2 = ((CompletableFuture)object).result;
            if (object2 == null || consumer == null) return false;
            if (this.result != null) return true;
            object2 = object;
            if (object instanceof AltResult) {
                object2 = ((AltResult)object).ex;
                if (object2 != null) {
                    this.completeThrowable((Throwable)object2, object);
                    return true;
                }
                object2 = null;
            }
            if (uniAccept != null) {
                if (uniAccept.claim()) break block6;
                return false;
            }
        }
        try {
            consumer.accept(object2);
            this.completeNull();
            return true;
        }
        catch (Throwable throwable) {
            this.completeThrowable(throwable);
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final <S> boolean uniApply(CompletableFuture<S> object, Function<? super S, ? extends T> function, UniApply<S, T> uniApply) {
        Object object2;
        block6 : {
            if (object == null) return false;
            object = object2 = ((CompletableFuture)object).result;
            if (object2 == null || function == null) return false;
            if (this.result != null) return true;
            object2 = object;
            if (object instanceof AltResult) {
                object2 = ((AltResult)object).ex;
                if (object2 != null) {
                    this.completeThrowable((Throwable)object2, object);
                    return true;
                }
                object2 = null;
            }
            if (uniApply != null) {
                if (uniApply.claim()) break block6;
                return false;
            }
        }
        try {
            this.completeValue(function.apply(object2));
            return true;
        }
        catch (Throwable throwable) {
            this.completeThrowable(throwable);
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    final <S> boolean uniCompose(CompletableFuture<S> object, Function<? super S, ? extends CompletionStage<T>> uniRelay, UniCompose<S, T> uniCompose) {
        Object object2;
        block6 : {
            if (object == null) return false;
            object = object2 = ((CompletableFuture)object).result;
            if (object2 == null || uniRelay == null) return false;
            if (this.result != null) return true;
            object2 = object;
            if (object instanceof AltResult) {
                object2 = ((AltResult)object).ex;
                if (object2 != null) {
                    this.completeThrowable((Throwable)object2, object);
                    return true;
                }
                object2 = null;
            }
            if (uniCompose != null) {
                if (uniCompose.claim()) break block6;
                return false;
            }
        }
        try {
            object = ((CompletionStage)uniRelay.apply(object2)).toCompletableFuture();
            if (((CompletableFuture)object).result != null && this.uniRelay((CompletableFuture<T>)object)) return true;
            uniRelay = new UniRelay<Object>(this, (CompletableFuture<Object>)object);
            ((CompletableFuture)object).push(uniRelay);
            uniRelay.tryFire(0);
            object = this.result;
            if (object != null) return true;
            return false;
        }
        catch (Throwable throwable) {
            this.completeThrowable(throwable);
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final boolean uniExceptionally(CompletableFuture<T> object, Function<? super Throwable, ? extends T> function, UniExceptionally<T> uniExceptionally) {
        if (object == null) return false;
        Object object2 = ((CompletableFuture)object).result;
        if (object2 == null) return false;
        if (function == null) {
            return false;
        }
        if (this.result != null) return true;
        try {
            if (object2 instanceof AltResult && (object = ((AltResult)object2).ex) != null) {
                if (uniExceptionally != null && !uniExceptionally.claim()) {
                    return false;
                }
                this.completeValue(function.apply((Throwable)object));
                return true;
            }
            this.internalComplete(object2);
            return true;
        }
        catch (Throwable throwable) {
            this.completeThrowable(throwable);
        }
        return true;
    }

    final <S> boolean uniHandle(CompletableFuture<S> object, BiFunction<? super S, Throwable, ? extends T> biFunction, UniHandle<S, T> serializable) {
        if (object != null && (object = ((CompletableFuture)object).result) != null && biFunction != null) {
            if (this.result == null) {
                block9 : {
                    block8 : {
                        if (serializable != null) {
                            if (serializable.claim()) break block8;
                            return false;
                        }
                    }
                    if (object instanceof AltResult) {
                        serializable = ((AltResult)object).ex;
                        object = null;
                        break block9;
                    }
                    serializable = null;
                }
                try {
                    this.completeValue(biFunction.apply((S)object, (Throwable)serializable));
                }
                catch (Throwable throwable) {
                    this.completeThrowable(throwable);
                }
            }
            return true;
        }
        return false;
    }

    final boolean uniRelay(CompletableFuture<T> object) {
        if (object != null && (object = ((CompletableFuture)object).result) != null) {
            if (this.result == null) {
                this.completeRelay(object);
            }
            return true;
        }
        return false;
    }

    final boolean uniRun(CompletableFuture<?> object, Runnable runnable, UniRun<?> uniRun) {
        Object object2;
        if (object != null && (object2 = ((CompletableFuture)object).result) != null && runnable != null) {
            if (this.result == null) {
                if (object2 instanceof AltResult && (object = ((AltResult)object2).ex) != null) {
                    this.completeThrowable((Throwable)object, object2);
                } else {
                    block8 : {
                        if (uniRun != null) {
                            if (uniRun.claim()) break block8;
                            return false;
                        }
                    }
                    try {
                        runnable.run();
                        this.completeNull();
                    }
                    catch (Throwable throwable) {
                        this.completeThrowable(throwable);
                    }
                }
            }
            return true;
        }
        return false;
    }

    final boolean uniWhenComplete(CompletableFuture<T> object, BiConsumer<? super T, ? super Throwable> biConsumer, UniWhenComplete<T> object2) {
        Object object3;
        Object object4 = null;
        Object var5_6 = null;
        if (object != null && (object3 = ((CompletableFuture)object).result) != null && biConsumer != null) {
            if (this.result == null) {
                block13 : {
                    Object object5;
                    block12 : {
                        block11 : {
                            block10 : {
                                if (object2 != null) {
                                    object5 = object4;
                                    if (((UniCompletion)object2).claim()) break block10;
                                    return false;
                                }
                            }
                            object5 = object4;
                            if (!(object3 instanceof AltResult)) break block11;
                            object5 = object4;
                            object = ((AltResult)object3).ex;
                            object2 = null;
                            break block12;
                        }
                        object2 = object3;
                        object = var5_6;
                    }
                    object5 = object;
                    biConsumer.accept(object2, (Throwable)object);
                    if (object != null) break block13;
                    object5 = object;
                    try {
                        this.internalComplete(object3);
                        return true;
                    }
                    catch (Throwable throwable) {
                        if (object5 == null) {
                            object = throwable;
                            break block13;
                        }
                        object = object5;
                        if (object5 == throwable) break block13;
                        ((Throwable)object5).addSuppressed(throwable);
                        object = object5;
                    }
                }
                this.completeThrowable((Throwable)object, object3);
            }
            return true;
        }
        return false;
    }

    @Override
    public CompletableFuture<T> whenComplete(BiConsumer<? super T, ? super Throwable> biConsumer) {
        return this.uniWhenCompleteStage(null, biConsumer);
    }

    @Override
    public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> biConsumer) {
        return this.uniWhenCompleteStage(this.defaultExecutor(), biConsumer);
    }

    @Override
    public CompletableFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> biConsumer, Executor executor) {
        return this.uniWhenCompleteStage(CompletableFuture.screenExecutor(executor), biConsumer);
    }

    static final class AltResult {
        final Throwable ex;

        AltResult(Throwable throwable) {
            this.ex = throwable;
        }
    }

    static final class AsyncRun
    extends ForkJoinTask<Void>
    implements Runnable,
    AsynchronousCompletionTask {
        CompletableFuture<Void> dep;
        Runnable fn;

        AsyncRun(CompletableFuture<Void> completableFuture, Runnable runnable) {
            this.dep = completableFuture;
            this.fn = runnable;
        }

        @Override
        public final boolean exec() {
            this.run();
            return true;
        }

        @Override
        public final Void getRawResult() {
            return null;
        }

        @Override
        public void run() {
            Runnable runnable;
            CompletableFuture<Void> completableFuture = this.dep;
            if (completableFuture != null && (runnable = this.fn) != null) {
                this.dep = null;
                this.fn = null;
                if (completableFuture.result == null) {
                    try {
                        runnable.run();
                        completableFuture.completeNull();
                    }
                    catch (Throwable throwable) {
                        completableFuture.completeThrowable(throwable);
                    }
                }
                completableFuture.postComplete();
            }
        }

        @Override
        public final void setRawResult(Void void_) {
        }
    }

    static final class AsyncSupply<T>
    extends ForkJoinTask<Void>
    implements Runnable,
    AsynchronousCompletionTask {
        CompletableFuture<T> dep;
        Supplier<? extends T> fn;

        AsyncSupply(CompletableFuture<T> completableFuture, Supplier<? extends T> supplier) {
            this.dep = completableFuture;
            this.fn = supplier;
        }

        @Override
        public final boolean exec() {
            this.run();
            return true;
        }

        @Override
        public final Void getRawResult() {
            return null;
        }

        @Override
        public void run() {
            Supplier<T> supplier;
            CompletableFuture<T> completableFuture = this.dep;
            if (completableFuture != null && (supplier = this.fn) != null) {
                this.dep = null;
                this.fn = null;
                if (completableFuture.result == null) {
                    try {
                        completableFuture.completeValue(supplier.get());
                    }
                    catch (Throwable throwable) {
                        completableFuture.completeThrowable(throwable);
                    }
                }
                completableFuture.postComplete();
            }
        }

        @Override
        public final void setRawResult(Void void_) {
        }
    }

    public static interface AsynchronousCompletionTask {
    }

    static final class BiAccept<T, U>
    extends BiCompletion<T, U, Void> {
        BiConsumer<? super T, ? super U> fn;

        BiAccept(Executor executor, CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3, BiConsumer<? super T, ? super U> biConsumer) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = biConsumer;
        }

        final CompletableFuture<Void> tryFire(int n) {
            BiAccept biAccept;
            CompletableFuture completableFuture;
            BiConsumer<? super T, ? super U> biConsumer;
            CompletableFuture completableFuture2;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 != null && completableFuture3.biAccept(completableFuture2 = this.src, completableFuture = this.snd, biConsumer = this.fn, biAccept = n > 0 ? null : this)) {
                this.dep = null;
                this.src = null;
                this.snd = null;
                this.fn = null;
                return completableFuture3.postFire(completableFuture2, completableFuture, n);
            }
            return null;
        }
    }

    static final class BiApply<T, U, V>
    extends BiCompletion<T, U, V> {
        BiFunction<? super T, ? super U, ? extends V> fn;

        BiApply(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3, BiFunction<? super T, ? super U, ? extends V> biFunction) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = biFunction;
        }

        final CompletableFuture<V> tryFire(int n) {
            BiApply biApply;
            BiFunction<? super T, ? super U, ? extends V> biFunction;
            CompletableFuture completableFuture;
            CompletableFuture completableFuture2;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 != null && completableFuture3.biApply(completableFuture2 = this.src, completableFuture = this.snd, biFunction = this.fn, biApply = n > 0 ? null : this)) {
                this.dep = null;
                this.src = null;
                this.snd = null;
                this.fn = null;
                return completableFuture3.postFire(completableFuture2, completableFuture, n);
            }
            return null;
        }
    }

    static abstract class BiCompletion<T, U, V>
    extends UniCompletion<T, V> {
        CompletableFuture<U> snd;

        BiCompletion(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3) {
            super(executor, completableFuture, completableFuture2);
            this.snd = completableFuture3;
        }
    }

    static final class BiRelay<T, U>
    extends BiCompletion<T, U, Void> {
        BiRelay(CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3) {
            super(null, completableFuture, completableFuture2, completableFuture3);
        }

        final CompletableFuture<Void> tryFire(int n) {
            CompletableFuture completableFuture;
            CompletableFuture completableFuture2;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 != null && completableFuture3.biRelay(completableFuture2 = this.src, completableFuture = this.snd)) {
                this.src = null;
                this.snd = null;
                this.dep = null;
                return completableFuture3.postFire(completableFuture2, completableFuture, n);
            }
            return null;
        }
    }

    static final class BiRun<T, U>
    extends BiCompletion<T, U, Void> {
        Runnable fn;

        BiRun(Executor executor, CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3, Runnable runnable) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = runnable;
        }

        final CompletableFuture<Void> tryFire(int n) {
            BiRun biRun;
            CompletableFuture completableFuture;
            Runnable runnable;
            CompletableFuture completableFuture2;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 != null && completableFuture3.biRun(completableFuture2 = this.src, completableFuture = this.snd, runnable = this.fn, biRun = n > 0 ? null : this)) {
                this.dep = null;
                this.src = null;
                this.snd = null;
                this.fn = null;
                return completableFuture3.postFire(completableFuture2, completableFuture, n);
            }
            return null;
        }
    }

    static final class Canceller
    implements BiConsumer<Object, Throwable> {
        final Future<?> f;

        Canceller(Future<?> future) {
            this.f = future;
        }

        @Override
        public void accept(Object future, Throwable throwable) {
            if (throwable == null && (future = this.f) != null && !future.isDone()) {
                this.f.cancel(false);
            }
        }
    }

    static final class CoCompletion
    extends Completion {
        BiCompletion<?, ?, ?> base;

        CoCompletion(BiCompletion<?, ?, ?> biCompletion) {
            this.base = biCompletion;
        }

        @Override
        final boolean isLive() {
            BiCompletion<?, ?, ?> biCompletion = this.base;
            boolean bl = biCompletion != null && biCompletion.dep != null;
            return bl;
        }

        @Override
        final CompletableFuture<?> tryFire(int n) {
            Future<Void> future = this.base;
            if (future != null && (future = future.tryFire(n)) != null) {
                this.base = null;
                return future;
            }
            return null;
        }
    }

    static abstract class Completion
    extends ForkJoinTask<Void>
    implements Runnable,
    AsynchronousCompletionTask {
        volatile Completion next;

        Completion() {
        }

        @Override
        public final boolean exec() {
            this.tryFire(1);
            return false;
        }

        @Override
        public final Void getRawResult() {
            return null;
        }

        abstract boolean isLive();

        @Override
        public final void run() {
            this.tryFire(1);
        }

        @Override
        public final void setRawResult(Void void_) {
        }

        abstract CompletableFuture<?> tryFire(int var1);
    }

    static final class DelayedCompleter<U>
    implements Runnable {
        final CompletableFuture<U> f;
        final U u;

        DelayedCompleter(CompletableFuture<U> completableFuture, U u) {
            this.f = completableFuture;
            this.u = u;
        }

        @Override
        public void run() {
            CompletableFuture<U> completableFuture = this.f;
            if (completableFuture != null) {
                completableFuture.complete(this.u);
            }
        }
    }

    static final class DelayedExecutor
    implements Executor {
        final long delay;
        final Executor executor;
        final TimeUnit unit;

        DelayedExecutor(long l, TimeUnit timeUnit, Executor executor) {
            this.delay = l;
            this.unit = timeUnit;
            this.executor = executor;
        }

        @Override
        public void execute(Runnable runnable) {
            Delayer.delay(new TaskSubmitter(this.executor, runnable), this.delay, this.unit);
        }
    }

    static final class Delayer {
        static final ScheduledThreadPoolExecutor delayer;

        static {
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
            delayer = scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new DaemonThreadFactory());
            scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        }

        Delayer() {
        }

        static ScheduledFuture<?> delay(Runnable runnable, long l, TimeUnit timeUnit) {
            return delayer.schedule(runnable, l, timeUnit);
        }

        static final class DaemonThreadFactory
        implements ThreadFactory {
            DaemonThreadFactory() {
            }

            @Override
            public Thread newThread(Runnable runnable) {
                runnable = new Thread(runnable);
                ((Thread)runnable).setDaemon(true);
                ((Thread)runnable).setName("CompletableFutureDelayScheduler");
                return runnable;
            }
        }

    }

    static final class MinimalStage<T>
    extends CompletableFuture<T> {
        MinimalStage() {
        }

        MinimalStage(Object object) {
            super(object);
        }

        @Override
        public boolean cancel(boolean bl) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean complete(T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CompletableFuture<T> completeAsync(Supplier<? extends T> supplier) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CompletableFuture<T> completeAsync(Supplier<? extends T> supplier, Executor executor) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean completeExceptionally(Throwable throwable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CompletableFuture<T> completeOnTimeout(T t, long l, TimeUnit timeUnit) {
            throw new UnsupportedOperationException();
        }

        @Override
        public T get() {
            throw new UnsupportedOperationException();
        }

        @Override
        public T get(long l, TimeUnit timeUnit) {
            throw new UnsupportedOperationException();
        }

        @Override
        public T getNow(T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getNumberOfDependents() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isCancelled() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isCompletedExceptionally() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isDone() {
            throw new UnsupportedOperationException();
        }

        @Override
        public T join() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <U> CompletableFuture<U> newIncompleteFuture() {
            return new MinimalStage<T>();
        }

        @Override
        public void obtrudeException(Throwable throwable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void obtrudeValue(T t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CompletableFuture<T> orTimeout(long l, TimeUnit timeUnit) {
            throw new UnsupportedOperationException();
        }
    }

    static final class OrAccept<T, U extends T>
    extends BiCompletion<T, U, Void> {
        Consumer<? super T> fn;

        OrAccept(Executor executor, CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3, Consumer<? super T> consumer) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = consumer;
        }

        final CompletableFuture<Void> tryFire(int n) {
            OrAccept orAccept;
            CompletableFuture completableFuture;
            Consumer<? super T> consumer;
            CompletableFuture completableFuture2;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 != null && completableFuture3.orAccept(completableFuture2 = this.src, completableFuture = this.snd, consumer = this.fn, orAccept = n > 0 ? null : this)) {
                this.dep = null;
                this.src = null;
                this.snd = null;
                this.fn = null;
                return completableFuture3.postFire(completableFuture2, completableFuture, n);
            }
            return null;
        }
    }

    static final class OrApply<T, U extends T, V>
    extends BiCompletion<T, U, V> {
        Function<? super T, ? extends V> fn;

        OrApply(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3, Function<? super T, ? extends V> function) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = function;
        }

        final CompletableFuture<V> tryFire(int n) {
            CompletableFuture completableFuture;
            Function<? super T, ? extends V> function;
            CompletableFuture completableFuture2;
            OrApply orApply;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 != null && completableFuture3.orApply(completableFuture2 = this.src, completableFuture = this.snd, function = this.fn, orApply = n > 0 ? null : this)) {
                this.dep = null;
                this.src = null;
                this.snd = null;
                this.fn = null;
                return completableFuture3.postFire(completableFuture2, completableFuture, n);
            }
            return null;
        }
    }

    static final class OrRelay<T, U>
    extends BiCompletion<T, U, Object> {
        OrRelay(CompletableFuture<Object> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3) {
            super(null, completableFuture, completableFuture2, completableFuture3);
        }

        final CompletableFuture<Object> tryFire(int n) {
            CompletableFuture completableFuture;
            CompletableFuture completableFuture2;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 != null && completableFuture3.orRelay(completableFuture2 = this.src, completableFuture = this.snd)) {
                this.src = null;
                this.snd = null;
                this.dep = null;
                return completableFuture3.postFire(completableFuture2, completableFuture, n);
            }
            return null;
        }
    }

    static final class OrRun<T, U>
    extends BiCompletion<T, U, Void> {
        Runnable fn;

        OrRun(Executor executor, CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, CompletableFuture<U> completableFuture3, Runnable runnable) {
            super(executor, completableFuture, completableFuture2, completableFuture3);
            this.fn = runnable;
        }

        final CompletableFuture<Void> tryFire(int n) {
            OrRun orRun;
            CompletableFuture completableFuture;
            Runnable runnable;
            CompletableFuture completableFuture2;
            CompletableFuture completableFuture3 = this.dep;
            if (completableFuture3 != null && completableFuture3.orRun(completableFuture2 = this.src, completableFuture = this.snd, runnable = this.fn, orRun = n > 0 ? null : this)) {
                this.dep = null;
                this.src = null;
                this.snd = null;
                this.fn = null;
                return completableFuture3.postFire(completableFuture2, completableFuture, n);
            }
            return null;
        }
    }

    static final class Signaller
    extends Completion
    implements ForkJoinPool.ManagedBlocker {
        final long deadline;
        boolean interrupted;
        final boolean interruptible;
        long nanos;
        volatile Thread thread = Thread.currentThread();

        Signaller(boolean bl, long l, long l2) {
            this.interruptible = bl;
            this.nanos = l;
            this.deadline = l2;
        }

        @Override
        public boolean block() {
            while (!this.isReleasable()) {
                if (this.deadline == 0L) {
                    LockSupport.park(this);
                    continue;
                }
                LockSupport.parkNanos(this, this.nanos);
            }
            return true;
        }

        @Override
        final boolean isLive() {
            boolean bl = this.thread != null;
            return bl;
        }

        @Override
        public boolean isReleasable() {
            boolean bl;
            block5 : {
                block6 : {
                    boolean bl2 = Thread.interrupted();
                    bl = true;
                    if (bl2) {
                        this.interrupted = true;
                    }
                    if (this.interrupted && this.interruptible) break block5;
                    long l = this.deadline;
                    if (l == 0L) break block6;
                    if (this.nanos <= 0L) break block5;
                    this.nanos = l -= System.nanoTime();
                    if (l <= 0L) break block5;
                }
                if (this.thread != null) {
                    bl = false;
                }
            }
            return bl;
        }

        @Override
        final CompletableFuture<?> tryFire(int n) {
            Thread thread = this.thread;
            if (thread != null) {
                this.thread = null;
                LockSupport.unpark(thread);
            }
            return null;
        }
    }

    static final class TaskSubmitter
    implements Runnable {
        final Runnable action;
        final Executor executor;

        TaskSubmitter(Executor executor, Runnable runnable) {
            this.executor = executor;
            this.action = runnable;
        }

        @Override
        public void run() {
            this.executor.execute(this.action);
        }
    }

    static final class ThreadPerTaskExecutor
    implements Executor {
        ThreadPerTaskExecutor() {
        }

        @Override
        public void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
    }

    static final class Timeout
    implements Runnable {
        final CompletableFuture<?> f;

        Timeout(CompletableFuture<?> completableFuture) {
            this.f = completableFuture;
        }

        @Override
        public void run() {
            CompletableFuture<?> completableFuture = this.f;
            if (completableFuture != null && !completableFuture.isDone()) {
                this.f.completeExceptionally(new TimeoutException());
            }
        }
    }

    static final class UniAccept<T>
    extends UniCompletion<T, Void> {
        Consumer<? super T> fn;

        UniAccept(Executor executor, CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, Consumer<? super T> consumer) {
            super(executor, completableFuture, completableFuture2);
            this.fn = consumer;
        }

        final CompletableFuture<Void> tryFire(int n) {
            UniAccept uniAccept;
            Consumer<? super T> consumer;
            CompletableFuture completableFuture;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 != null && completableFuture2.uniAccept(completableFuture = this.src, consumer = this.fn, uniAccept = n > 0 ? null : this)) {
                this.dep = null;
                this.src = null;
                this.fn = null;
                return completableFuture2.postFire(completableFuture, n);
            }
            return null;
        }
    }

    static final class UniApply<T, V>
    extends UniCompletion<T, V> {
        Function<? super T, ? extends V> fn;

        UniApply(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2, Function<? super T, ? extends V> function) {
            super(executor, completableFuture, completableFuture2);
            this.fn = function;
        }

        final CompletableFuture<V> tryFire(int n) {
            Function<? super T, ? extends V> function;
            UniApply uniApply;
            CompletableFuture completableFuture;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 != null && completableFuture2.uniApply(completableFuture = this.src, function = this.fn, uniApply = n > 0 ? null : this)) {
                this.dep = null;
                this.src = null;
                this.fn = null;
                return completableFuture2.postFire(completableFuture, n);
            }
            return null;
        }
    }

    static abstract class UniCompletion<T, V>
    extends Completion {
        CompletableFuture<V> dep;
        Executor executor;
        CompletableFuture<T> src;

        UniCompletion(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2) {
            this.executor = executor;
            this.dep = completableFuture;
            this.src = completableFuture2;
        }

        final boolean claim() {
            Executor executor = this.executor;
            if (this.compareAndSetForkJoinTaskTag((short)0, (short)1)) {
                if (executor == null) {
                    return true;
                }
                this.executor = null;
                executor.execute(this);
            }
            return false;
        }

        @Override
        final boolean isLive() {
            boolean bl = this.dep != null;
            return bl;
        }
    }

    static final class UniCompose<T, V>
    extends UniCompletion<T, V> {
        Function<? super T, ? extends CompletionStage<V>> fn;

        UniCompose(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2, Function<? super T, ? extends CompletionStage<V>> function) {
            super(executor, completableFuture, completableFuture2);
            this.fn = function;
        }

        final CompletableFuture<V> tryFire(int n) {
            Function<? super T, ? extends CompletionStage<V>> function;
            UniCompose uniCompose;
            CompletableFuture completableFuture;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 != null && completableFuture2.uniCompose(completableFuture = this.src, function = this.fn, uniCompose = n > 0 ? null : this)) {
                this.dep = null;
                this.src = null;
                this.fn = null;
                return completableFuture2.postFire(completableFuture, n);
            }
            return null;
        }
    }

    static final class UniExceptionally<T>
    extends UniCompletion<T, T> {
        Function<? super Throwable, ? extends T> fn;

        UniExceptionally(CompletableFuture<T> completableFuture, CompletableFuture<T> completableFuture2, Function<? super Throwable, ? extends T> function) {
            super(null, completableFuture, completableFuture2);
            this.fn = function;
        }

        final CompletableFuture<T> tryFire(int n) {
            CompletableFuture completableFuture;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 != null && completableFuture2.uniExceptionally(completableFuture = this.src, this.fn, this)) {
                this.dep = null;
                this.src = null;
                this.fn = null;
                return completableFuture2.postFire(completableFuture, n);
            }
            return null;
        }
    }

    static final class UniHandle<T, V>
    extends UniCompletion<T, V> {
        BiFunction<? super T, Throwable, ? extends V> fn;

        UniHandle(Executor executor, CompletableFuture<V> completableFuture, CompletableFuture<T> completableFuture2, BiFunction<? super T, Throwable, ? extends V> biFunction) {
            super(executor, completableFuture, completableFuture2);
            this.fn = biFunction;
        }

        final CompletableFuture<V> tryFire(int n) {
            UniHandle uniHandle;
            BiFunction<? super T, Throwable, ? extends V> biFunction;
            CompletableFuture completableFuture;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 != null && completableFuture2.uniHandle(completableFuture = this.src, biFunction = this.fn, uniHandle = n > 0 ? null : this)) {
                this.dep = null;
                this.src = null;
                this.fn = null;
                return completableFuture2.postFire(completableFuture, n);
            }
            return null;
        }
    }

    static final class UniRelay<T>
    extends UniCompletion<T, T> {
        UniRelay(CompletableFuture<T> completableFuture, CompletableFuture<T> completableFuture2) {
            super(null, completableFuture, completableFuture2);
        }

        final CompletableFuture<T> tryFire(int n) {
            CompletableFuture completableFuture;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 != null && completableFuture2.uniRelay(completableFuture = this.src)) {
                this.src = null;
                this.dep = null;
                return completableFuture2.postFire(completableFuture, n);
            }
            return null;
        }
    }

    static final class UniRun<T>
    extends UniCompletion<T, Void> {
        Runnable fn;

        UniRun(Executor executor, CompletableFuture<Void> completableFuture, CompletableFuture<T> completableFuture2, Runnable runnable) {
            super(executor, completableFuture, completableFuture2);
            this.fn = runnable;
        }

        final CompletableFuture<Void> tryFire(int n) {
            Runnable runnable;
            UniRun uniRun;
            CompletableFuture completableFuture;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 != null && completableFuture2.uniRun(completableFuture = this.src, runnable = this.fn, uniRun = n > 0 ? null : this)) {
                this.dep = null;
                this.src = null;
                this.fn = null;
                return completableFuture2.postFire(completableFuture, n);
            }
            return null;
        }
    }

    static final class UniWhenComplete<T>
    extends UniCompletion<T, T> {
        BiConsumer<? super T, ? super Throwable> fn;

        UniWhenComplete(Executor executor, CompletableFuture<T> completableFuture, CompletableFuture<T> completableFuture2, BiConsumer<? super T, ? super Throwable> biConsumer) {
            super(executor, completableFuture, completableFuture2);
            this.fn = biConsumer;
        }

        final CompletableFuture<T> tryFire(int n) {
            BiConsumer<? super T, ? super Throwable> biConsumer;
            UniWhenComplete uniWhenComplete;
            CompletableFuture completableFuture;
            CompletableFuture completableFuture2 = this.dep;
            if (completableFuture2 != null && completableFuture2.uniWhenComplete(completableFuture = this.src, biConsumer = this.fn, uniWhenComplete = n > 0 ? null : this)) {
                this.dep = null;
                this.src = null;
                this.fn = null;
                return completableFuture2.postFire(completableFuture, n);
            }
            return null;
        }
    }

}

