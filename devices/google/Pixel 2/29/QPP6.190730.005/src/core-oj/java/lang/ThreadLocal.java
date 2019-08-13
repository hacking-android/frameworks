/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class ThreadLocal<T> {
    private static final int HASH_INCREMENT = 1640531527;
    private static AtomicInteger nextHashCode = new AtomicInteger();
    private final int threadLocalHashCode = ThreadLocal.nextHashCode();

    static ThreadLocalMap createInheritedMap(ThreadLocalMap threadLocalMap) {
        return new ThreadLocalMap(threadLocalMap);
    }

    private static int nextHashCode() {
        return nextHashCode.getAndAdd(1640531527);
    }

    private T setInitialValue() {
        T t = this.initialValue();
        Thread thread = Thread.currentThread();
        ThreadLocalMap threadLocalMap = this.getMap(thread);
        if (threadLocalMap != null) {
            threadLocalMap.set(this, t);
        } else {
            this.createMap(thread, t);
        }
        return t;
    }

    public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> supplier) {
        return new SuppliedThreadLocal<S>(supplier);
    }

    T childValue(T t) {
        throw new UnsupportedOperationException();
    }

    void createMap(Thread thread, T t) {
        thread.threadLocals = new ThreadLocalMap(this, t);
    }

    public T get() {
        Object object = this.getMap(Thread.currentThread());
        if (object != null && (object = ((ThreadLocalMap)object).getEntry(this)) != null) {
            return (T)((ThreadLocalMap.Entry)object).value;
        }
        return this.setInitialValue();
    }

    ThreadLocalMap getMap(Thread thread) {
        return thread.threadLocals;
    }

    protected T initialValue() {
        return null;
    }

    public void remove() {
        ThreadLocalMap threadLocalMap = this.getMap(Thread.currentThread());
        if (threadLocalMap != null) {
            threadLocalMap.remove(this);
        }
    }

    public void set(T t) {
        Thread thread = Thread.currentThread();
        ThreadLocalMap threadLocalMap = this.getMap(thread);
        if (threadLocalMap != null) {
            threadLocalMap.set(this, t);
        } else {
            this.createMap(thread, t);
        }
    }

    static final class SuppliedThreadLocal<T>
    extends ThreadLocal<T> {
        private final Supplier<? extends T> supplier;

        SuppliedThreadLocal(Supplier<? extends T> supplier) {
            this.supplier = Objects.requireNonNull(supplier);
        }

        @Override
        protected T initialValue() {
            return this.supplier.get();
        }
    }

    static class ThreadLocalMap {
        private static final int INITIAL_CAPACITY = 16;
        private int size = 0;
        private Entry[] table;
        private int threshold;

        private ThreadLocalMap(ThreadLocalMap arrentry) {
            arrentry = arrentry.table;
            int n = arrentry.length;
            this.setThreshold(n);
            this.table = new Entry[n];
            for (int i = 0; i < n; ++i) {
                Entry[] arrentry2;
                Entry entry = arrentry[i];
                if (entry == null || (arrentry2 = (Entry[])entry.get()) == null) continue;
                entry = new Entry((ThreadLocal<?>)arrentry2, arrentry2.childValue(entry.value));
                int n2 = ((ThreadLocal)arrentry2).threadLocalHashCode & n - 1;
                while ((arrentry2 = this.table)[n2] != null) {
                    n2 = ThreadLocalMap.nextIndex(n2, n);
                }
                arrentry2[n2] = entry;
                ++this.size;
            }
        }

        ThreadLocalMap(ThreadLocal<?> threadLocal, Object object) {
            this.table = new Entry[16];
            int n = threadLocal.threadLocalHashCode;
            this.table[n & 15] = new Entry(threadLocal, object);
            this.size = 1;
            this.setThreshold(16);
        }

        private boolean cleanSomeSlots(int n, int n2) {
            boolean bl = false;
            Entry[] arrentry = this.table;
            int n3 = arrentry.length;
            do {
                int n4 = ThreadLocalMap.nextIndex(n, n3);
                Entry entry = arrentry[n4];
                boolean bl2 = bl;
                n = n4;
                int n5 = n2;
                if (entry != null) {
                    bl2 = bl;
                    n = n4;
                    n5 = n2;
                    if (entry.get() == null) {
                        n5 = n3;
                        bl2 = true;
                        n = this.expungeStaleEntry(n4);
                    }
                }
                n2 = n5 >>>= 1;
                if (n5 == 0) {
                    return bl2;
                }
                bl = bl2;
            } while (true);
        }

        private void expungeStaleEntries() {
            Entry[] arrentry = this.table;
            int n = arrentry.length;
            for (int i = 0; i < n; ++i) {
                Entry entry = arrentry[i];
                if (entry == null || entry.get() != null) continue;
                this.expungeStaleEntry(i);
            }
        }

        private int expungeStaleEntry(int n) {
            Entry entry;
            Entry[] arrentry = this.table;
            int n2 = arrentry.length;
            arrentry[n].value = null;
            arrentry[n] = null;
            --this.size;
            n = ThreadLocalMap.nextIndex(n, n2);
            while ((entry = arrentry[n]) != null) {
                ThreadLocal threadLocal = (ThreadLocal)entry.get();
                if (threadLocal == null) {
                    entry.value = null;
                    arrentry[n] = null;
                    --this.size;
                } else {
                    int n3 = threadLocal.threadLocalHashCode & n2 - 1;
                    if (n3 != n) {
                        arrentry[n] = null;
                        while (arrentry[n3] != null) {
                            n3 = ThreadLocalMap.nextIndex(n3, n2);
                        }
                        arrentry[n3] = entry;
                    }
                }
                n = ThreadLocalMap.nextIndex(n, n2);
            }
            return n;
        }

        private Entry getEntry(ThreadLocal<?> threadLocal) {
            int n = threadLocal.threadLocalHashCode;
            Object object = this.table;
            n &= ((Entry[])object).length - 1;
            if ((object = object[n]) != null && ((Reference)object).get() == threadLocal) {
                return object;
            }
            return this.getEntryAfterMiss(threadLocal, n, (Entry)object);
        }

        private Entry getEntryAfterMiss(ThreadLocal<?> threadLocal, int n, Entry entry) {
            Entry[] arrentry = this.table;
            int n2 = arrentry.length;
            while (entry != null) {
                ThreadLocal threadLocal2 = (ThreadLocal)entry.get();
                if (threadLocal2 == threadLocal) {
                    return entry;
                }
                if (threadLocal2 == null) {
                    this.expungeStaleEntry(n);
                } else {
                    n = ThreadLocalMap.nextIndex(n, n2);
                }
                entry = arrentry[n];
            }
            return null;
        }

        private static int nextIndex(int n, int n2) {
            n = n + 1 < n2 ? ++n : 0;
            return n;
        }

        private static int prevIndex(int n, int n2) {
            n = n - 1 >= 0 ? --n : n2 - 1;
            return n;
        }

        private void rehash() {
            this.expungeStaleEntries();
            int n = this.size;
            int n2 = this.threshold;
            if (n >= n2 - n2 / 4) {
                this.resize();
            }
        }

        private void remove(ThreadLocal<?> threadLocal) {
            Entry[] arrentry = this.table;
            int n = arrentry.length;
            int n2 = threadLocal.threadLocalHashCode & n - 1;
            Entry entry = arrentry[n2];
            while (entry != null) {
                int n3;
                if (entry.get() == threadLocal) {
                    entry.clear();
                    this.expungeStaleEntry(n2);
                    return;
                }
                n2 = n3 = ThreadLocalMap.nextIndex(n2, n);
                entry = arrentry[n3];
            }
        }

        private void replaceStaleEntry(ThreadLocal<?> threadLocal, Object object, int n) {
            Entry entry;
            Object object2;
            Entry[] arrentry = this.table;
            int n2 = arrentry.length;
            int n3 = n;
            int n4 = ThreadLocalMap.prevIndex(n, n2);
            while ((object2 = arrentry[n4]) != null) {
                if (((Reference)object2).get() == null) {
                    n3 = n4;
                }
                n4 = ThreadLocalMap.prevIndex(n4, n2);
            }
            n4 = ThreadLocalMap.nextIndex(n, n2);
            while ((entry = arrentry[n4]) != null) {
                int n5;
                object2 = (ThreadLocal)entry.get();
                if (object2 == threadLocal) {
                    entry.value = object;
                    arrentry[n4] = arrentry[n];
                    arrentry[n] = entry;
                    n5 = n3;
                    if (n3 == n) {
                        n5 = n4;
                    }
                    this.cleanSomeSlots(this.expungeStaleEntry(n5), n2);
                    return;
                }
                n5 = n3;
                if (object2 == null) {
                    n5 = n3;
                    if (n3 == n) {
                        n5 = n4;
                    }
                }
                n4 = ThreadLocalMap.nextIndex(n4, n2);
                n3 = n5;
            }
            arrentry[n].value = null;
            arrentry[n] = new Entry(threadLocal, object);
            if (n3 != n) {
                this.cleanSomeSlots(this.expungeStaleEntry(n3), n2);
            }
        }

        private void resize() {
            Entry[] arrentry = this.table;
            int n = arrentry.length;
            int n2 = n * 2;
            Entry[] arrentry2 = new Entry[n2];
            int n3 = 0;
            for (int i = 0; i < n; ++i) {
                Entry entry = arrentry[i];
                int n4 = n3;
                if (entry != null) {
                    ThreadLocal threadLocal = (ThreadLocal)entry.get();
                    if (threadLocal == null) {
                        entry.value = null;
                        n4 = n3;
                    } else {
                        n4 = threadLocal.threadLocalHashCode & n2 - 1;
                        while (arrentry2[n4] != null) {
                            n4 = ThreadLocalMap.nextIndex(n4, n2);
                        }
                        arrentry2[n4] = entry;
                        n4 = n3 + 1;
                    }
                }
                n3 = n4;
            }
            this.setThreshold(n2);
            this.size = n3;
            this.table = arrentry2;
        }

        private void set(ThreadLocal<?> threadLocal, Object object) {
            int n;
            Entry[] arrentry = this.table;
            int n2 = arrentry.length;
            int n3 = threadLocal.threadLocalHashCode & n2 - 1;
            Entry entry = arrentry[n3];
            while (entry != null) {
                ThreadLocal threadLocal2 = (ThreadLocal)entry.get();
                if (threadLocal2 == threadLocal) {
                    entry.value = object;
                    return;
                }
                if (threadLocal2 == null) {
                    this.replaceStaleEntry(threadLocal, object, n3);
                    return;
                }
                n3 = n = ThreadLocalMap.nextIndex(n3, n2);
                entry = arrentry[n];
            }
            arrentry[n3] = new Entry(threadLocal, object);
            this.size = n = this.size + 1;
            if (!this.cleanSomeSlots(n3, n) && n >= this.threshold) {
                this.rehash();
            }
        }

        private void setThreshold(int n) {
            this.threshold = n * 2 / 3;
        }

        static class Entry
        extends WeakReference<ThreadLocal<?>> {
            Object value;

            Entry(ThreadLocal<?> threadLocal, Object object) {
                super(threadLocal);
                this.value = object;
            }
        }

    }

}

