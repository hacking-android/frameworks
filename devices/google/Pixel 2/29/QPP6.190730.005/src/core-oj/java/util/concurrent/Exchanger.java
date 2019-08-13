/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import sun.misc.Unsafe;

public class Exchanger<V> {
    private static final int ABASE;
    private static final int ASHIFT = 7;
    private static final long BLOCKER;
    private static final long BOUND;
    static final int FULL;
    private static final long MATCH;
    private static final int MMASK = 255;
    private static final int NCPU;
    private static final Object NULL_ITEM;
    private static final int SEQ = 256;
    private static final long SLOT;
    private static final int SPINS = 1024;
    private static final Object TIMED_OUT;
    private static final Unsafe U;
    private volatile Node[] arena;
    private volatile int bound;
    private final Participant participant = new Participant();
    private volatile Node slot;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        NCPU = Runtime.getRuntime().availableProcessors();
        int n = NCPU;
        n = n >= 510 ? 255 : (n >>>= 1);
        FULL = n;
        NULL_ITEM = new Object();
        TIMED_OUT = new Object();
        U = Unsafe.getUnsafe();
        try {
            BOUND = U.objectFieldOffset(Exchanger.class.getDeclaredField("bound"));
            SLOT = U.objectFieldOffset(Exchanger.class.getDeclaredField("slot"));
            MATCH = U.objectFieldOffset(Node.class.getDeclaredField("match"));
            BLOCKER = U.objectFieldOffset(Thread.class.getDeclaredField("parkBlocker"));
            n = U.arrayIndexScale(Node[].class);
            if ((n - 1 & n) == 0 && n <= 128) {
                ABASE = U.arrayBaseOffset(Node[].class) + 128;
                return;
            }
            Error error = new Error("Unsupported array scale");
            throw error;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    private final Object arenaExchange(Object object, boolean bl, long l) {
        Object object2 = this;
        Node[] arrnode = ((Exchanger)object2).arena;
        Node node = (Node)((Exchanger)object2).participant.get();
        int n = node.index;
        block0 : do {
            int n2;
            int n3;
            int n4;
            block23 : {
                block24 : {
                    Object object3 = object;
                    Object object4 = U;
                    long l2 = (n << 7) + ABASE;
                    if ((object4 = (Node)((Unsafe)object4).getObjectVolatile(arrnode, l2)) != null && U.compareAndSwapObject(arrnode, l2, object4, null)) {
                        object = ((Node)object4).item;
                        ((Node)object4).match = object3;
                        object2 = ((Node)object4).parked;
                        if (object2 != null) {
                            U.unpark(object2);
                        }
                        return object;
                    }
                    n4 = ((Exchanger)object2).bound;
                    n3 = n4 & 255;
                    if (n > n3 || object4 != null) break block23;
                    node.item = object3;
                    boolean bl2 = U.compareAndSwapObject(arrnode, l2, null, node);
                    object4 = null;
                    if (!bl2) break block24;
                    long l3 = bl && n3 == 0 ? System.nanoTime() + l : 0L;
                    object3 = Thread.currentThread();
                    n2 = node.hash;
                    n = 1024;
                    object2 = object4;
                    do {
                        block25 : {
                            long l4;
                            block26 : {
                                if ((object4 = node.match) != null) {
                                    U.putOrderedObject(node, MATCH, object2);
                                    node.item = object2;
                                    node.hash = n2;
                                    return object4;
                                }
                                if (n > 0) {
                                    n2 = n2 << 1 ^ n2;
                                    n2 ^= n2 >>> 3;
                                    if ((n2 ^= n2 << 10) == 0) {
                                        n2 = (int)((Thread)object3).getId();
                                        object2 = null;
                                        n2 |= 1024;
                                        continue;
                                    }
                                    if (n2 < 0) {
                                        if ((--n & 511) == 0) {
                                            Thread.yield();
                                        }
                                        object2 = null;
                                        continue;
                                    }
                                    object2 = null;
                                    continue;
                                }
                                if (U.getObjectVolatile(arrnode, l2) != node) {
                                    object2 = null;
                                    n = 1024;
                                    continue;
                                }
                                if (((Thread)object3).isInterrupted() || n3 != 0) break block25;
                                if (!bl) break block26;
                                l = l4 = l3 - System.nanoTime();
                                if (l4 <= 0L) break block25;
                            }
                            object2 = U;
                            l4 = BLOCKER;
                            ((Unsafe)object2).putObject(object3, l4, this);
                            node.parked = object3;
                            if (U.getObjectVolatile(arrnode, l2) == node) {
                                U.park(false, l);
                            }
                            node.parked = null;
                            U.putObject(object3, BLOCKER, null);
                            object2 = null;
                            continue;
                        }
                        object2 = this;
                        if (U.getObjectVolatile(arrnode, l2) == node) {
                            object4 = U;
                            Object var21_17 = null;
                            if (((Unsafe)object4).compareAndSwapObject(arrnode, l2, node, null)) {
                                if (n3 != 0) {
                                    U.compareAndSwapInt(this, BOUND, n4, n4 + 256 - 1);
                                }
                                node.item = var21_17;
                                node.hash = n2;
                                node.index = n = node.index >>> 1;
                                if (Thread.interrupted()) {
                                    return var21_17;
                                }
                                if (!bl || n3 != 0 || l > 0L) continue block0;
                                return TIMED_OUT;
                            }
                        }
                        object2 = null;
                    } while (true);
                }
                node.item = null;
                continue;
            }
            if (node.bound != n4) {
                node.bound = n4;
                node.collides = 0;
                n = n == n3 && n3 != 0 ? n3 - 1 : n3;
            } else {
                n2 = node.collides;
                if (n2 >= n3 && n3 != FULL && U.compareAndSwapInt(this, BOUND, n4, n4 + 256 + 1)) {
                    n = n3 + 1;
                } else {
                    node.collides = n2 + 1;
                    if (n == 0) {
                        n = n3;
                    }
                }
            }
            node.index = --n;
        } while (true);
    }

    private final Object slotExchange(Object object, boolean bl, long l) {
        Object object2 = (Node)this.participant.get();
        Thread thread = Thread.currentThread();
        if (thread.isInterrupted()) {
            return null;
        }
        do {
            block16 : {
                int n;
                block15 : {
                    Object object3 = this.slot;
                    int n2 = 1;
                    if (object3 != null) {
                        if (U.compareAndSwapObject(this, SLOT, object3, null)) {
                            object2 = ((Node)object3).item;
                            ((Node)object3).match = object;
                            object = ((Node)object3).parked;
                            if (object != null) {
                                U.unpark(object);
                            }
                            return object2;
                        }
                        if (NCPU <= 1 || this.bound != 0 || !U.compareAndSwapInt(this, BOUND, 0, 256)) continue;
                        this.arena = new Node[FULL + 2 << 7];
                        continue;
                    }
                    if (this.arena != null) {
                        return null;
                    }
                    ((Node)object2).item = object;
                    if (!U.compareAndSwapObject(this, SLOT, null, object2)) break block16;
                    n = ((Node)object2).hash;
                    long l2 = bl ? System.nanoTime() + l : 0L;
                    if (NCPU > 1) {
                        n2 = 1024;
                    }
                    do {
                        block17 : {
                            block18 : {
                                long l3;
                                object = object3 = ((Node)object2).match;
                                if (object3 != null) break block15;
                                if (n2 > 0) {
                                    int n3;
                                    n = n << 1 ^ n;
                                    if ((n3 = (n ^= n >>> 3) ^ n << 10) == 0) {
                                        n = (int)thread.getId() | 1024;
                                        continue;
                                    }
                                    n = n3;
                                    if (n3 >= 0) continue;
                                    int n4 = n2 - 1;
                                    n = n3;
                                    n2 = n4;
                                    if ((n4 & 511) != 0) continue;
                                    Thread.yield();
                                    n = n3;
                                    n2 = n4;
                                    continue;
                                }
                                if (this.slot != object2) {
                                    n2 = 1024;
                                    continue;
                                }
                                if (thread.isInterrupted() || this.arena != null) break block17;
                                if (!bl) break block18;
                                l = l3 = l2 - System.nanoTime();
                                if (l3 <= 0L) break block17;
                            }
                            U.putObject(thread, BLOCKER, this);
                            ((Node)object2).parked = thread;
                            if (this.slot == object2) {
                                U.park(false, l);
                            }
                            ((Node)object2).parked = null;
                            U.putObject(thread, BLOCKER, null);
                            continue;
                        }
                        if (U.compareAndSwapObject(this, SLOT, object2, null)) break;
                    } while (true);
                    object = bl && l <= 0L && !thread.isInterrupted() ? TIMED_OUT : null;
                }
                U.putOrderedObject(object2, MATCH, null);
                ((Node)object2).item = null;
                ((Node)object2).hash = n;
                return object;
            }
            ((Node)object2).item = null;
        } while (true);
    }

    public V exchange(V object) throws InterruptedException {
        block6 : {
            block5 : {
                Object object2;
                block4 : {
                    object2 = object == null ? NULL_ITEM : object;
                    if (this.arena != null) break block4;
                    Object object3 = this.slotExchange(object2, false, 0L);
                    object = object3;
                    if (object3 != null) break block5;
                }
                if (Thread.interrupted()) break block6;
                object2 = this.arenaExchange(object2, false, 0L);
                object = object2;
                if (object2 == null) break block6;
            }
            if (object == NULL_ITEM) {
                object = null;
            }
            return object;
        }
        throw new InterruptedException();
    }

    public V exchange(V object, long l, TimeUnit object2) throws InterruptedException, TimeoutException {
        block7 : {
            block6 : {
                Object object3;
                block5 : {
                    object3 = object == null ? NULL_ITEM : object;
                    l = object2.toNanos(l);
                    if (this.arena != null) break block5;
                    object2 = this.slotExchange(object3, true, l);
                    object = object2;
                    if (object2 != null) break block6;
                }
                if (Thread.interrupted()) break block7;
                object2 = this.arenaExchange(object3, true, l);
                object = object2;
                if (object2 == null) break block7;
            }
            if (object != TIMED_OUT) {
                if (object == NULL_ITEM) {
                    object = null;
                }
                return object;
            }
            throw new TimeoutException();
        }
        throw new InterruptedException();
    }

    static final class Node {
        int bound;
        int collides;
        int hash;
        int index;
        Object item;
        volatile Object match;
        volatile Thread parked;

        Node() {
        }
    }

    static final class Participant
    extends ThreadLocal<Node> {
        Participant() {
        }

        @Override
        public Node initialValue() {
            return new Node();
        }
    }

}

