/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.nio.channels.AsynchronousChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.ShutdownChannelGroupException;
import java.security.AccessController;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import sun.nio.ch.AsynchronousChannelGroupImpl;
import sun.nio.ch.Groupable;
import sun.nio.ch.PendingFuture;
import sun.security.action.GetIntegerAction;

class Invoker {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int maxHandlerInvokeCount = AccessController.doPrivileged(new GetIntegerAction("sun.nio.ch.maxCompletionHandlersOnStack", 16));
    private static final ThreadLocal<GroupAndInvokeCount> myGroupAndInvokeCount = new ThreadLocal<GroupAndInvokeCount>(){

        @Override
        protected GroupAndInvokeCount initialValue() {
            return null;
        }
    };

    private Invoker() {
    }

    static void bindToGroup(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl) {
        myGroupAndInvokeCount.set(new GroupAndInvokeCount(asynchronousChannelGroupImpl));
    }

    static GroupAndInvokeCount getGroupAndInvokeCount() {
        return myGroupAndInvokeCount.get();
    }

    static <V, A> void invoke(AsynchronousChannel asynchronousChannel, CompletionHandler<V, ? super A> completionHandler, A a, V v, Throwable throwable) {
        block8 : {
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            GroupAndInvokeCount groupAndInvokeCount = myGroupAndInvokeCount.get();
            boolean bl4 = bl;
            if (groupAndInvokeCount != null) {
                if (groupAndInvokeCount.group() == ((Groupable)((Object)asynchronousChannel)).group()) {
                    bl3 = true;
                }
                bl4 = bl;
                bl2 = bl3;
                if (bl3) {
                    bl4 = bl;
                    bl2 = bl3;
                    if (groupAndInvokeCount.invokeCount() < maxHandlerInvokeCount) {
                        bl4 = true;
                        bl2 = bl3;
                    }
                }
            }
            if (bl4) {
                Invoker.invokeDirect(groupAndInvokeCount, completionHandler, a, v, throwable);
            } else {
                try {
                    Invoker.invokeIndirectly(asynchronousChannel, completionHandler, a, v, throwable);
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    if (!bl2) break block8;
                    Invoker.invokeDirect(groupAndInvokeCount, completionHandler, a, v, throwable);
                }
            }
            return;
        }
        throw new ShutdownChannelGroupException();
    }

    static <V, A> void invoke(PendingFuture<V, A> pendingFuture) {
        CompletionHandler<V, A> completionHandler = pendingFuture.handler();
        if (completionHandler != null) {
            Invoker.invoke(pendingFuture.channel(), completionHandler, pendingFuture.attachment(), pendingFuture.value(), pendingFuture.exception());
        }
    }

    static <V, A> void invokeDirect(GroupAndInvokeCount groupAndInvokeCount, CompletionHandler<V, ? super A> completionHandler, A a, V v, Throwable throwable) {
        groupAndInvokeCount.incrementInvokeCount();
        Invoker.invokeUnchecked(completionHandler, a, v, throwable);
    }

    static <V, A> void invokeIndirectly(AsynchronousChannel object, final CompletionHandler<V, ? super A> completionHandler, final A a, final V v, final Throwable throwable) {
        try {
            object = ((Groupable)object).group();
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    GroupAndInvokeCount groupAndInvokeCount = (GroupAndInvokeCount)myGroupAndInvokeCount.get();
                    if (groupAndInvokeCount != null) {
                        groupAndInvokeCount.setInvokeCount(1);
                    }
                    Invoker.invokeUnchecked(completionHandler, a, v, throwable);
                }
            };
            ((AsynchronousChannelGroupImpl)object).executeOnPooledThread(runnable);
            return;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw new ShutdownChannelGroupException();
        }
    }

    static <V, A> void invokeIndirectly(final CompletionHandler<V, ? super A> completionHandler, final A a, final V v, final Throwable throwable, Executor executor) {
        try {
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    Invoker.invokeUnchecked(completionHandler, a, v, throwable);
                }
            };
            executor.execute(runnable);
            return;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            throw new ShutdownChannelGroupException();
        }
    }

    static <V, A> void invokeIndirectly(PendingFuture<V, A> pendingFuture) {
        CompletionHandler<V, A> completionHandler = pendingFuture.handler();
        if (completionHandler != null) {
            Invoker.invokeIndirectly(pendingFuture.channel(), completionHandler, pendingFuture.attachment(), pendingFuture.value(), pendingFuture.exception());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static void invokeOnThreadInThreadPool(Groupable var0, Runnable var1_2) {
        var2_3 = Invoker.myGroupAndInvokeCount.get();
        var0 = var0.group();
        var3_4 = var2_3 == null ? false : GroupAndInvokeCount.access$100(var2_3) == var0;
        if (!var3_4) ** GOTO lbl8
        try {
            var1_2.run();
            return;
lbl8: // 1 sources:
            var0.executeOnPooledThread(var1_2);
            return;
        }
        catch (RejectedExecutionException var0_1) {
            throw new ShutdownChannelGroupException();
        }
    }

    static <V, A> void invokeUnchecked(CompletionHandler<V, ? super A> completionHandler, A a, V v, Throwable throwable) {
        if (throwable == null) {
            completionHandler.completed(v, a);
        } else {
            completionHandler.failed(throwable, a);
        }
        Thread.interrupted();
    }

    static <V, A> void invokeUnchecked(PendingFuture<V, A> pendingFuture) {
        CompletionHandler<V, A> completionHandler = pendingFuture.handler();
        if (completionHandler != null) {
            Invoker.invokeUnchecked(completionHandler, pendingFuture.attachment(), pendingFuture.value(), pendingFuture.exception());
        }
    }

    static boolean isBoundToAnyGroup() {
        boolean bl = myGroupAndInvokeCount.get() != null;
        return bl;
    }

    static boolean mayInvokeDirect(GroupAndInvokeCount groupAndInvokeCount, AsynchronousChannelGroupImpl asynchronousChannelGroupImpl) {
        return groupAndInvokeCount != null && groupAndInvokeCount.group() == asynchronousChannelGroupImpl && groupAndInvokeCount.invokeCount() < maxHandlerInvokeCount;
    }

    static class GroupAndInvokeCount {
        private final AsynchronousChannelGroupImpl group;
        private int handlerInvokeCount;

        GroupAndInvokeCount(AsynchronousChannelGroupImpl asynchronousChannelGroupImpl) {
            this.group = asynchronousChannelGroupImpl;
        }

        static /* synthetic */ AsynchronousChannelGroupImpl access$100(GroupAndInvokeCount groupAndInvokeCount) {
            return groupAndInvokeCount.group;
        }

        AsynchronousChannelGroupImpl group() {
            return this.group;
        }

        void incrementInvokeCount() {
            ++this.handlerInvokeCount;
        }

        int invokeCount() {
            return this.handlerInvokeCount;
        }

        void resetInvokeCount() {
            this.handlerInvokeCount = 0;
        }

        void setInvokeCount(int n) {
            this.handlerInvokeCount = n;
        }
    }

}

