/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.locks;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReadWriteLock;
import sun.misc.Unsafe;

public class StampedLock
implements Serializable {
    private static final long ABITS = 255L;
    private static final int CANCELLED = 1;
    private static final int HEAD_SPINS;
    private static final long INTERRUPTED = 1L;
    private static final int LG_READERS = 7;
    private static final int MAX_HEAD_SPINS;
    private static final int NCPU;
    private static final long ORIGIN = 256L;
    private static final int OVERFLOW_YIELD_RATE = 7;
    private static final long PARKBLOCKER;
    private static final long RBITS = 127L;
    private static final long RFULL = 126L;
    private static final int RMODE = 0;
    private static final long RUNIT = 1L;
    private static final long SBITS = -128L;
    private static final int SPINS;
    private static final long STATE;
    private static final Unsafe U;
    private static final int WAITING = -1;
    private static final long WBIT = 128L;
    private static final long WCOWAIT;
    private static final long WHEAD;
    private static final int WMODE = 1;
    private static final long WNEXT;
    private static final long WSTATUS;
    private static final long WTAIL;
    private static final long serialVersionUID = -6001602636862214147L;
    transient ReadLockView readLockView;
    transient ReadWriteLockView readWriteLockView;
    private transient int readerOverflow;
    private volatile transient long state = 256L;
    private volatile transient WNode whead;
    transient WriteLockView writeLockView;
    private volatile transient WNode wtail;

    static {
        int n = NCPU = Runtime.getRuntime().availableProcessors();
        int n2 = 0;
        n = n > 1 ? 64 : 0;
        SPINS = n;
        n = NCPU > 1 ? 1024 : 0;
        HEAD_SPINS = n;
        n = n2;
        if (NCPU > 1) {
            n = 65536;
        }
        MAX_HEAD_SPINS = n;
        U = Unsafe.getUnsafe();
        try {
            STATE = U.objectFieldOffset(StampedLock.class.getDeclaredField("state"));
            WHEAD = U.objectFieldOffset(StampedLock.class.getDeclaredField("whead"));
            WTAIL = U.objectFieldOffset(StampedLock.class.getDeclaredField("wtail"));
            WSTATUS = U.objectFieldOffset(WNode.class.getDeclaredField("status"));
            WNEXT = U.objectFieldOffset(WNode.class.getDeclaredField("next"));
            WCOWAIT = U.objectFieldOffset(WNode.class.getDeclaredField("cowait"));
            PARKBLOCKER = U.objectFieldOffset(Thread.class.getDeclaredField("parkBlocker"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private long acquireRead(boolean var1_1, long var2_2) {
        var4_3 = -1;
        var5_4 = 0;
        var6_5 = null;
        do {
            block71 : {
                block74 : {
                    block73 : {
                        block72 : {
                            block70 : {
                                block66 : {
                                    if ((var7_6 = this.whead) != (var8_7 = this.wtail)) break block66;
                                    var9_8 = var7_6;
                                    var7_6 = var8_7;
                                    var8_7 = var9_8;
                                    do {
                                        block68 : {
                                            block69 : {
                                                block67 : {
                                                    if ((var12_10 = (var10_9 = this.state) & 255L) >= 126L) break block67;
                                                    var9_8 = StampedLock.U;
                                                    var14_11 = StampedLock.STATE;
                                                    var18_13 = var16_12 = var10_9 + 1L;
                                                    if (!var9_8.compareAndSwapLong(this, var14_11, var10_9, var16_12)) break block68;
                                                    break block69;
                                                }
                                                if (var12_10 >= 128L) break block68;
                                                var18_13 = var16_12 = this.tryIncReaderOverflow(var10_9);
                                                if (var16_12 == 0L) break block68;
                                            }
                                            if (var5_4 == 0) return var18_13;
                                            Thread.currentThread().interrupt();
                                            return var18_13;
                                        }
                                        if (var12_10 < 128L) continue;
                                        if (var4_3 > 0) {
                                            if (LockSupport.nextSecondarySeed() < 0) continue;
                                            --var4_3;
                                            continue;
                                        }
                                        var20_14 = var7_6;
                                        var9_8 = var8_7;
                                        if (var4_3 == 0) {
                                            var20_14 = this.whead;
                                            var9_8 = this.wtail;
                                            if (var20_14 == var8_7 && var9_8 == var7_6) {
                                                var9_8 = var8_7;
                                                var8_7 = var7_6;
                                                var7_6 = var9_8;
                                                break;
                                            }
                                            if (var20_14 != var9_8) {
                                                var7_6 = var20_14;
                                                var8_7 = var9_8;
                                                break;
                                            }
                                            var8_7 = var20_14;
                                            var20_14 = var9_8;
                                            var9_8 = var8_7;
                                        }
                                        var4_3 = StampedLock.SPINS;
                                        var7_6 = var20_14;
                                        var8_7 = var9_8;
                                    } while (true);
                                }
                                var21_15 = true;
                                if (var8_7 != null) break block70;
                                var8_7 = new WNode(1, null);
                                if (StampedLock.U.compareAndSwapObject(this, StampedLock.WHEAD, null, var8_7)) {
                                    this.wtail = var8_7;
                                }
                                var8_7 = var6_5;
                                var22_16 = var5_4;
                                break block71;
                            }
                            if (var6_5 != null) break block72;
                            var8_7 = new WNode(0, (WNode)var8_7);
                            var22_16 = var5_4;
                            break block71;
                        }
                        if (var7_6 == var8_7 || var8_7.mode != 0) break block73;
                        var7_6 = StampedLock.U;
                        var18_13 = StampedLock.WCOWAIT;
                        var6_5.cowait = var9_8 = var8_7.cowait;
                        if (var7_6.compareAndSwapObject(var8_7, var18_13, var9_8, var6_5)) break block74;
                        var6_5.cowait = null;
                        var8_7 = var6_5;
                        var22_16 = var5_4;
                        break block71;
                    }
                    var9_8 = var8_7;
                    var7_6 = null;
                    if (var6_5.prev != var9_8) {
                        var6_5.prev = var9_8;
                        var8_7 = var6_5;
                        var22_16 = var5_4;
                    } else {
                        var8_7 = var6_5;
                        var22_16 = var5_4;
                        if (StampedLock.U.compareAndSwapObject(this, StampedLock.WTAIL, var9_8, var6_5)) {
                            break;
                        }
                    }
                    break block71;
                }
                block2 : do {
                    block75 : {
                        if ((var9_8 = this.whead) != null && (var7_6 = var9_8.cowait) != null && StampedLock.U.compareAndSwapObject(var9_8, StampedLock.WCOWAIT, var7_6, var7_6.cowait) && (var7_6 = var7_6.thread) != null) {
                            StampedLock.U.unpark(var7_6);
                        }
                        var20_14 = var8_7.prev;
                        var23_17 = var21_15;
                        var7_6 = var8_7;
                        if (var9_8 == var20_14) break block75;
                        var23_17 = var21_15;
                        var7_6 = var8_7;
                        if (var9_8 == var8_7) break block75;
                        if (var20_14 != null) ** GOTO lbl121
                        var23_17 = var21_15;
                        var7_6 = var8_7;
                    }
                    do {
                        block79 : {
                            block77 : {
                                block78 : {
                                    block76 : {
                                        if ((var12_10 = (var14_11 = this.state) & 255L) >= 126L) break block76;
                                        var8_7 = StampedLock.U;
                                        var10_9 = StampedLock.STATE;
                                        var18_13 = var16_12 = var14_11 + 1L;
                                        if (!var8_7.compareAndSwapLong(this, var10_9, var14_11, var16_12)) break block77;
                                        break block78;
                                    }
                                    if (var12_10 >= 128L) break block77;
                                    var18_13 = var16_12 = this.tryIncReaderOverflow(var14_11);
                                    if (var16_12 == 0L) break block77;
                                }
                                if (var5_4 == 0) return var18_13;
                                Thread.currentThread().interrupt();
                                return var18_13;
                            }
                            var8_7 = var7_6;
                            if (var12_10 < 128L) break block79;
lbl121: // 2 sources:
                            if (this.whead == var9_8 && var8_7.prev == var20_14) {
                                if (var20_14 != null && var9_8 != var8_7 && var8_7.status <= 0) {
                                    if (var2_2 == 0L) {
                                        var18_13 = 0L;
                                    } else {
                                        var18_13 = var2_2 - System.nanoTime();
                                        if (var18_13 <= 0L) {
                                            if (var5_4 == 0) return this.cancelWaiter(var6_5, (WNode)var8_7, false);
                                            Thread.currentThread().interrupt();
                                            return this.cancelWaiter(var6_5, (WNode)var8_7, false);
                                        }
                                    }
                                    var7_6 = Thread.currentThread();
                                    StampedLock.U.putObject(var7_6, StampedLock.PARKBLOCKER, this);
                                    var6_5.thread = var7_6;
                                    if ((var9_8 != var20_14 || (this.state & 255L) == 128L) && this.whead == var9_8 && var8_7.prev == var20_14) {
                                        StampedLock.U.park(false, var18_13);
                                    }
                                    var6_5.thread = null;
                                    StampedLock.U.putObject(var7_6, StampedLock.PARKBLOCKER, null);
                                    if (Thread.interrupted()) {
                                        if (var1_1) {
                                            return this.cancelWaiter(var6_5, (WNode)var8_7, true);
                                        }
                                        var5_4 = 1;
                                    }
                                } else {
                                    var8_7 = null;
                                    var22_16 = var5_4;
                                    break block2;
                                }
                            }
                            var21_15 = true;
                            continue block2;
                        }
                        var23_17 = true;
                        var7_6 = var8_7;
                    } while (true);
                    break;
                } while (true);
            }
            var6_5 = var8_7;
            var5_4 = var22_16;
        } while (true);
        var9_8.next = var6_5;
        var4_3 = -1;
        do {
            block86 : {
                block88 : {
                    block87 : {
                        block85 : {
                            block81 : {
                                block80 : {
                                    var8_7 = var20_14 = this.whead;
                                    if (var20_14 == var9_8) break block80;
                                    var25_19 = var7_6;
                                    var26_20 = var8_7;
                                    var22_16 = var4_3;
                                    var20_14 = var26_20;
                                    var8_7 = var9_8;
                                    var7_6 = var25_19;
                                    if (var26_20 != null) {
                                        do {
                                            var27_21 = var26_20.cowait;
                                            var22_16 = var4_3;
                                            var20_14 = var26_20;
                                            var8_7 = var9_8;
                                            var7_6 = var25_19;
                                            if (var27_21 != null) {
                                                if (!StampedLock.U.compareAndSwapObject(var26_20, StampedLock.WCOWAIT, var27_21, var27_21.cowait) || (var8_7 = var27_21.thread) == null) continue;
                                                StampedLock.U.unpark(var8_7);
                                                continue;
                                            }
                                            ** GOTO lbl226
                                            break;
                                        } while (true);
                                    }
                                    break block81;
                                }
                                if (var4_3 < 0) {
                                    var4_3 = StampedLock.HEAD_SPINS;
                                } else if (var4_3 < StampedLock.MAX_HEAD_SPINS) {
                                    var4_3 <<= 1;
                                }
                                var24_18 = var4_3;
                                do {
                                    block83 : {
                                        block84 : {
                                            block82 : {
                                                var18_13 = var16_12 = this.state;
                                                var10_9 = var16_12 & 255L;
                                                if (var10_9 >= 126L) break block82;
                                                var20_14 = StampedLock.U;
                                                var14_11 = StampedLock.STATE;
                                                var16_12 = var12_10 = var18_13 + 1L;
                                                if (!var20_14.compareAndSwapLong(this, var14_11, var18_13, var12_10)) break block83;
                                                break block84;
                                            }
                                            if (var10_9 >= 128L) break block83;
                                            var16_12 = var12_10 = this.tryIncReaderOverflow(var18_13);
                                            if (var12_10 == 0L) break block83;
                                        }
                                        this.whead = var6_5;
                                        var6_5.prev = var7_6;
                                        var7_6 = var9_8;
                                        var2_2 = var18_13;
                                        do {
                                            if ((var9_8 = var6_5.cowait) == null) {
                                                if (var5_4 == 0) return var16_12;
                                                Thread.currentThread().interrupt();
                                                return var16_12;
                                            }
                                            if (!StampedLock.U.compareAndSwapObject(var6_5, StampedLock.WCOWAIT, var9_8, var9_8.cowait) || (var9_8 = var9_8.thread) == null) continue;
                                            StampedLock.U.unpark(var9_8);
                                        } while (true);
                                    }
                                    var22_16 = var24_18;
                                    if (var10_9 >= 128L) {
                                        var22_16 = var24_18--;
                                        if (LockSupport.nextSecondarySeed() >= 0) {
                                            var22_16 = var24_18;
                                            if (var24_18 <= 0) {
                                                var22_16 = var4_3;
                                                var20_14 = var8_7;
                                                var8_7 = var9_8;
                                                break;
                                            }
                                        }
                                    }
                                    var24_18 = var22_16;
                                } while (true);
                            }
                            if (this.whead != var20_14) ** GOTO lbl-1000
                            var9_8 = var6_5.prev;
                            if (var9_8 == var8_7) break block85;
                            if (var9_8 == null) ** GOTO lbl-1000
                            var9_8.next = var6_5;
                            break block86;
                        }
                        var4_3 = var8_7.status;
                        if (var4_3 != 0) break block87;
                        StampedLock.U.compareAndSwapInt(var8_7, StampedLock.WSTATUS, 0, -1);
                        ** GOTO lbl-1000
                    }
                    if (var4_3 != 1) break block88;
                    var9_8 = var8_7.prev;
                    if (var9_8 != null) {
                        var6_5.prev = var9_8;
                        var9_8.next = var6_5;
                    }
                    ** GOTO lbl-1000
                }
                if (var2_2 == 0L) {
                    var18_13 = 0L;
                } else {
                    var18_13 = var2_2 - System.nanoTime();
                    if (var18_13 <= 0L) {
                        return this.cancelWaiter(var6_5, var6_5, false);
                    }
                }
                var9_8 = Thread.currentThread();
                StampedLock.U.putObject(var9_8, StampedLock.PARKBLOCKER, this);
                var6_5.thread = var9_8;
                if (var8_7.status < 0 && (var8_7 != var20_14 || (this.state & 255L) == 128L) && this.whead == var20_14 && var6_5.prev == var8_7) {
                    StampedLock.U.park(false, var18_13);
                }
                var6_5.thread = var7_6;
                StampedLock.U.putObject(var9_8, StampedLock.PARKBLOCKER, var7_6);
                if (Thread.interrupted()) {
                    if (var1_1) {
                        return this.cancelWaiter(var6_5, var6_5, true);
                    }
                    var5_4 = 1;
                    var9_8 = var8_7;
                } else lbl-1000: // 5 sources:
                {
                    var9_8 = var8_7;
                }
            }
            var4_3 = var22_16;
        } while (true);
    }

    private long acquireWrite(boolean bl, long l) {
        WNode wNode = null;
        int n = -1;
        do {
            int n2;
            Object object;
            Object object2;
            long l2 = this.state;
            long l3 = 255L;
            long l4 = l2 & 255L;
            if (l4 == 0L) {
                object2 = U;
                l4 = STATE;
                l3 = l2 + 128L;
                n2 = n;
                object = wNode;
                if (((Unsafe)object2).compareAndSwapLong(this, l4, l2, l3)) {
                    return l3;
                }
            } else {
                int n3 = 0;
                if (n < 0) {
                    n2 = n3;
                    if (l4 == 128L) {
                        n2 = n3;
                        if (this.wtail == this.whead) {
                            n2 = SPINS;
                        }
                    }
                    object = wNode;
                } else if (n > 0) {
                    n2 = n;
                    object = wNode;
                    if (LockSupport.nextSecondarySeed() >= 0) {
                        n2 = n - 1;
                        object = wNode;
                    }
                } else {
                    object2 = this.wtail;
                    if (object2 == null) {
                        object = new WNode(1, null);
                        if (U.compareAndSwapObject(this, WHEAD, null, object)) {
                            this.wtail = object;
                        }
                        n2 = n;
                        object = wNode;
                    } else if (wNode == null) {
                        object = new WNode(1, (WNode)object2);
                        n2 = n;
                    } else if (wNode.prev != object2) {
                        wNode.prev = object2;
                        n2 = n;
                        object = wNode;
                    } else {
                        n2 = n;
                        object = wNode;
                        if (U.compareAndSwapObject(this, WTAIL, object2, wNode)) {
                            ((WNode)object2).next = wNode;
                            n2 = -1;
                            n = 0;
                            object = object2;
                            do {
                                boolean bl2;
                                WNode wNode2;
                                block48 : {
                                    boolean bl3 = false;
                                    wNode2 = this.whead;
                                    if (wNode2 == object) {
                                        if (n2 < 0) {
                                            n2 = HEAD_SPINS;
                                        } else if (n2 < MAX_HEAD_SPINS) {
                                            n2 <<= 1;
                                        }
                                        int n4 = n2;
                                        do {
                                            if (((l2 = this.state) & l3) == 0L) {
                                                object2 = U;
                                                l4 = STATE;
                                                l3 = l2 + 128L;
                                                if (((Unsafe)object2).compareAndSwapLong(this, l4, l2, l3)) {
                                                    this.whead = wNode;
                                                    wNode.prev = null;
                                                    if (n != 0) {
                                                        Thread.currentThread().interrupt();
                                                    }
                                                    return l3;
                                                }
                                                n3 = n4;
                                            } else {
                                                bl2 = bl3;
                                                n3 = n4--;
                                                if (LockSupport.nextSecondarySeed() >= 0) {
                                                    n3 = n4;
                                                    if (n4 <= 0) {
                                                        n3 = n2;
                                                        break block48;
                                                    }
                                                }
                                            }
                                            l3 = 255L;
                                            n4 = n3;
                                        } while (true);
                                    }
                                    bl3 = false;
                                    n3 = n2;
                                    bl2 = bl3;
                                    if (wNode2 != null) {
                                        do {
                                            object2 = wNode2.cowait;
                                            n3 = n2;
                                            bl2 = bl3;
                                            if (object2 == null) break;
                                            if (!U.compareAndSwapObject(wNode2, WCOWAIT, object2, ((WNode)object2).cowait) || (object2 = ((WNode)object2).thread) == null) continue;
                                            U.unpark(object2);
                                        } while (true);
                                    }
                                }
                                if (this.whead == wNode2) {
                                    object2 = wNode.prev;
                                    if (object2 != object) {
                                        if (object2 != null) {
                                            ((WNode)object2).next = wNode;
                                            object = object2;
                                        }
                                    } else {
                                        n2 = ((WNode)object).status;
                                        if (n2 == 0) {
                                            U.compareAndSwapInt(object, WSTATUS, 0, -1);
                                        } else if (n2 == 1) {
                                            object2 = ((WNode)object).prev;
                                            if (object2 != null) {
                                                wNode.prev = object2;
                                                ((WNode)object2).next = wNode;
                                            }
                                        } else {
                                            if (l == 0L) {
                                                l3 = 0L;
                                            } else {
                                                l3 = l2 = l - System.nanoTime();
                                                if (l2 <= 0L) {
                                                    return this.cancelWaiter(wNode, wNode, bl2);
                                                }
                                            }
                                            object2 = Thread.currentThread();
                                            U.putObject(object2, PARKBLOCKER, this);
                                            wNode.thread = object2;
                                            if (((WNode)object).status < 0 && (object != wNode2 || (this.state & 255L) != 0L) && this.whead == wNode2 && wNode.prev == object) {
                                                U.park(bl2, l3);
                                            }
                                            wNode.thread = null;
                                            U.putObject(object2, PARKBLOCKER, null);
                                            if (Thread.interrupted()) {
                                                if (bl) {
                                                    return this.cancelWaiter(wNode, wNode, true);
                                                }
                                                n = 1;
                                            }
                                        }
                                    }
                                }
                                l3 = 255L;
                                n2 = n3;
                            } while (true);
                        }
                    }
                }
            }
            n = n2;
            wNode = object;
        } while (true);
    }

    private long cancelWaiter(WNode wNode, WNode object, boolean bl) {
        long l;
        block21 : {
            Object object2;
            Object object3;
            long l2;
            block22 : {
                if (wNode == null || object == null) break block22;
                wNode.status = 1;
                object2 = object;
                while ((object3 = ((WNode)object2).cowait) != null) {
                    if (((WNode)object3).status == 1) {
                        U.compareAndSwapObject(object2, WCOWAIT, object3, ((WNode)object3).cowait);
                        object2 = object;
                        continue;
                    }
                    object2 = object3;
                }
                if (object != wNode) break block22;
                object = ((WNode)object).cowait;
                while (object != null) {
                    object2 = ((WNode)object).thread;
                    if (object2 != null) {
                        U.unpark(object2);
                    }
                    object = ((WNode)object).cowait;
                }
                object3 = wNode.prev;
                while (object3 != null) {
                    block19 : {
                        block23 : {
                            WNode wNode2;
                            block20 : {
                                do {
                                    if ((wNode2 = wNode.next) != null && wNode2.status != 1) {
                                        object2 = wNode2;
                                        break block19;
                                    }
                                    object = this.wtail;
                                    object2 = null;
                                    while (object != null && object != wNode) {
                                        if (((WNode)object).status != 1) {
                                            object2 = object;
                                        }
                                        object = ((WNode)object).prev;
                                    }
                                    if (wNode2 == object2) break block20;
                                } while (!U.compareAndSwapObject(wNode, WNEXT, wNode2, object2));
                                object = object2;
                                break block23;
                            }
                            object = wNode2;
                        }
                        object2 = object;
                        if (object == null) {
                            object2 = object;
                            if (wNode == this.wtail) {
                                U.compareAndSwapObject(this, WTAIL, wNode, object3);
                                object2 = object;
                            }
                        }
                    }
                    if (((WNode)object3).next == wNode) {
                        U.compareAndSwapObject(object3, WNEXT, wNode, object2);
                    }
                    if (object2 != null && (object = ((WNode)object2).thread) != null) {
                        ((WNode)object2).thread = null;
                        U.unpark(object);
                    }
                    if (((WNode)object3).status != 1 || (object = ((WNode)object3).prev) == null) break;
                    wNode.prev = object;
                    U.compareAndSwapObject(object, WNEXT, object3, object2);
                    object3 = object;
                }
            }
            block5 : do {
                object3 = this.whead;
                l = 0L;
                if (object3 == null) break block21;
                object = ((WNode)object3).next;
                wNode = object;
                if (object != null) {
                    object2 = wNode;
                    if (wNode.status != 1) continue;
                }
                object2 = this.wtail;
                object = wNode;
                wNode = object2;
                do {
                    object2 = object;
                    if (wNode == null) continue block5;
                    object2 = object;
                    if (wNode == object3) continue block5;
                    if (wNode.status <= 0) {
                        object = wNode;
                    }
                    wNode = wNode.prev;
                } while (true);
            } while (object3 != this.whead);
            if (object2 != null && ((WNode)object3).status == 0 && ((l2 = this.state) & 255L) != 128L && (l2 == 0L || ((WNode)object2).mode == 0)) {
                this.release((WNode)object3);
            }
        }
        if (bl || Thread.interrupted()) {
            l = 1L;
        }
        return l;
    }

    private int getReadLockCount(long l) {
        long l2;
        l = l2 = 127L & l;
        if (l2 >= 126L) {
            l = (long)this.readerOverflow + 126L;
        }
        return (int)l;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        U.putLongVolatile(this, STATE, 256L);
    }

    private void release(WNode object) {
        block6 : {
            WNode wNode;
            block8 : {
                WNode wNode2;
                WNode wNode3;
                block7 : {
                    if (object == null) break block6;
                    U.compareAndSwapInt(object, WSTATUS, -1, 0);
                    wNode2 = wNode3 = ((WNode)object).next;
                    if (wNode3 == null) break block7;
                    wNode = wNode2;
                    if (wNode2.status != 1) break block8;
                }
                wNode3 = this.wtail;
                do {
                    wNode = wNode2;
                    if (wNode3 == null) break;
                    wNode = wNode2;
                    if (wNode3 == object) break;
                    if (wNode3.status <= 0) {
                        wNode2 = wNode3;
                    }
                    wNode3 = wNode3.prev;
                } while (true);
            }
            if (wNode != null && (object = wNode.thread) != null) {
                U.unpark(object);
            }
        }
    }

    private long tryDecReaderOverflow(long l) {
        if ((255L & l) == 126L) {
            if (U.compareAndSwapLong(this, STATE, l, l | 127L)) {
                int n = this.readerOverflow;
                if (n > 0) {
                    this.readerOverflow = n - 1;
                } else {
                    --l;
                }
                U.putLongVolatile(this, STATE, l);
                return l;
            }
        } else if ((LockSupport.nextSecondarySeed() & 7) == 0) {
            Thread.yield();
        }
        return 0L;
    }

    private long tryIncReaderOverflow(long l) {
        if ((255L & l) == 126L) {
            if (U.compareAndSwapLong(this, STATE, l, l | 127L)) {
                ++this.readerOverflow;
                U.putLongVolatile(this, STATE, l);
                return l;
            }
        } else if ((LockSupport.nextSecondarySeed() & 7) == 0) {
            Thread.yield();
        }
        return 0L;
    }

    public Lock asReadLock() {
        ReadLockView readLockView = this.readLockView;
        if (readLockView == null) {
            this.readLockView = readLockView = new ReadLockView();
        }
        return readLockView;
    }

    public ReadWriteLock asReadWriteLock() {
        ReadWriteLockView readWriteLockView = this.readWriteLockView;
        if (readWriteLockView == null) {
            this.readWriteLockView = readWriteLockView = new ReadWriteLockView();
        }
        return readWriteLockView;
    }

    public Lock asWriteLock() {
        WriteLockView writeLockView = this.writeLockView;
        if (writeLockView == null) {
            this.writeLockView = writeLockView = new WriteLockView();
        }
        return writeLockView;
    }

    public int getReadLockCount() {
        return this.getReadLockCount(this.state);
    }

    public boolean isReadLocked() {
        boolean bl = (this.state & 127L) != 0L;
        return bl;
    }

    public boolean isWriteLocked() {
        boolean bl = (this.state & 128L) != 0L;
        return bl;
    }

    public long readLock() {
        long l;
        block3 : {
            block2 : {
                long l2;
                long l3 = this.state;
                if (this.whead != this.wtail || (255L & l3) >= 126L) break block2;
                Unsafe unsafe = U;
                long l4 = STATE;
                l = l2 = l3 + 1L;
                if (unsafe.compareAndSwapLong(this, l4, l3, l2)) break block3;
            }
            l = this.acquireRead(false, 0L);
        }
        return l;
    }

    public long readLockInterruptibly() throws InterruptedException {
        long l;
        if (!Thread.interrupted() && (l = this.acquireRead(true, 0L)) != 1L) {
            return l;
        }
        throw new InterruptedException();
    }

    public String toString() {
        CharSequence charSequence;
        long l = this.state;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        if ((255L & l) == 0L) {
            charSequence = "[Unlocked]";
        } else if ((128L & l) != 0L) {
            charSequence = "[Write-locked]";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[Read-locks:");
            ((StringBuilder)charSequence).append(this.getReadLockCount(l));
            ((StringBuilder)charSequence).append("]");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        return stringBuilder.toString();
    }

    public long tryConvertToOptimisticRead(long l) {
        long l2;
        long l3 = l & 255L;
        U.loadFence();
        while (((l2 = this.state) & -128L) == (l & -128L)) {
            Object object;
            long l4 = l2 & 255L;
            if (l4 == 0L) {
                if (l3 != 0L) break;
                return l2;
            }
            if (l4 == 128L) {
                if (l3 != l4) break;
                object = U;
                l3 = STATE;
                l = 128L + l2;
                if (l == 0L) {
                    l = 256L;
                }
                ((Unsafe)object).putLongVolatile(this, l3, l);
                object = this.whead;
                if (object != null && ((WNode)object).status != 0) {
                    this.release((WNode)object);
                }
                return l;
            }
            if (l3 == 0L || l3 >= 128L) break;
            if (l4 < 126L) {
                object = U;
                long l5 = STATE;
                long l6 = l2 - 1L;
                if (!((Unsafe)object).compareAndSwapLong(this, l5, l2, l6)) continue;
                if (l4 == 1L && (object = this.whead) != null && ((WNode)object).status != 0) {
                    this.release((WNode)object);
                }
                return l6 & -128L;
            }
            if ((l2 = this.tryDecReaderOverflow(l2)) == 0L) continue;
            return l2 & -128L;
        }
        return 0L;
    }

    public long tryConvertToReadLock(long l) {
        long l2;
        long l3 = l & 255L;
        while (((l2 = this.state) & -128L) == (l & -128L)) {
            Object object;
            long l4 = l2 & 255L;
            if (l4 == 0L) {
                if (l3 != 0L) break;
                if (l4 < 126L) {
                    object = U;
                    l4 = STATE;
                    long l5 = l2 + 1L;
                    if (!((Unsafe)object).compareAndSwapLong(this, l4, l2, l5)) continue;
                    return l5;
                }
                if ((l2 = this.tryIncReaderOverflow(l2)) == 0L) continue;
                return l2;
            }
            if (l4 == 128L) {
                if (l3 != l4) break;
                object = U;
                l = STATE;
                l3 = 129L + l2;
                ((Unsafe)object).putLongVolatile(this, l, l3);
                object = this.whead;
                if (object != null && ((WNode)object).status != 0) {
                    this.release((WNode)object);
                }
                return l3;
            }
            if (l3 == 0L || l3 >= 128L) break;
            return l;
        }
        return 0L;
    }

    public long tryConvertToWriteLock(long l) {
        long l2;
        long l3 = l & 255L;
        while (((l2 = this.state) & -128L) == (l & -128L)) {
            Unsafe unsafe;
            long l4;
            long l5 = l2 & 255L;
            if (l5 == 0L) {
                if (l3 != 0L) break;
                unsafe = U;
                l5 = STATE;
                l4 = l2 + 128L;
                if (!unsafe.compareAndSwapLong(this, l5, l2, l4)) continue;
                return l4;
            }
            if (l5 == 128L) {
                if (l3 != l5) break;
                return l;
            }
            if (l5 != 1L || l3 == 0L) break;
            unsafe = U;
            l5 = STATE;
            l4 = l2 - 1L + 128L;
            if (!unsafe.compareAndSwapLong(this, l5, l2, l4)) continue;
            return l4;
        }
        return 0L;
    }

    public long tryOptimisticRead() {
        long l = this.state;
        long l2 = 0L;
        if ((l & 128L) == 0L) {
            l2 = l & -128L;
        }
        return l2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public long tryReadLock() {
        do lbl-1000: // 3 sources:
        {
            if ((var3_2 = (var1_1 = this.state) & 255L) == 128L) {
                return 0L;
            }
            if (var3_2 >= 126L) continue;
            var5_3 = StampedLock.U;
            var3_2 = StampedLock.STATE;
            var6_4 = var1_1 + 1L;
            if (!var5_3.compareAndSwapLong(this, var3_2, var1_1, var6_4)) ** GOTO lbl-1000
            return var6_4;
        } while ((var1_1 = this.tryIncReaderOverflow(var1_1)) == 0L);
        return var1_1;
    }

    public long tryReadLock(long l, TimeUnit object) throws InterruptedException {
        l = ((TimeUnit)((Object)object)).toNanos(l);
        if (!Thread.interrupted()) {
            long l2 = this.state;
            long l3 = l2 & 255L;
            if (l3 != 128L) {
                if (l3 < 126L) {
                    object = U;
                    long l4 = STATE;
                    l3 = l2 + 1L;
                    if (((Unsafe)object).compareAndSwapLong(this, l4, l2, l3)) {
                        return l3;
                    }
                } else if ((l2 = this.tryIncReaderOverflow(l2)) != 0L) {
                    return l2;
                }
            }
            if (l <= 0L) {
                return 0L;
            }
            l = l2 = System.nanoTime() + l;
            if (l2 == 0L) {
                l = 1L;
            }
            if ((l = this.acquireRead(true, l)) != 1L) {
                return l;
            }
        }
        throw new InterruptedException();
    }

    public boolean tryUnlockRead() {
        long l;
        long l2;
        while ((l = (l2 = this.state) & 255L) != 0L && l < 128L) {
            if (l < 126L) {
                WNode wNode;
                if (!U.compareAndSwapLong(this, STATE, l2, l2 - 1L)) continue;
                if (l == 1L && (wNode = this.whead) != null && wNode.status != 0) {
                    this.release(wNode);
                }
                return true;
            }
            if (this.tryDecReaderOverflow(l2) == 0L) continue;
            return true;
        }
        return false;
    }

    public boolean tryUnlockWrite() {
        long l = this.state;
        if ((l & 128L) != 0L) {
            Object object = U;
            long l2 = STATE;
            if ((l = 128L + l) == 0L) {
                l = 256L;
            }
            ((Unsafe)object).putLongVolatile(this, l2, l);
            object = this.whead;
            if (object != null && ((WNode)object).status != 0) {
                this.release((WNode)object);
            }
            return true;
        }
        return false;
    }

    public long tryWriteLock() {
        long l;
        block3 : {
            block2 : {
                long l2;
                long l3 = this.state;
                if ((l3 & 255L) != 0L) break block2;
                Unsafe unsafe = U;
                long l4 = STATE;
                l = l2 = l3 + 128L;
                if (unsafe.compareAndSwapLong(this, l4, l3, l2)) break block3;
            }
            l = 0L;
        }
        return l;
    }

    public long tryWriteLock(long l, TimeUnit timeUnit) throws InterruptedException {
        l = timeUnit.toNanos(l);
        if (!Thread.interrupted()) {
            long l2 = this.tryWriteLock();
            if (l2 != 0L) {
                return l2;
            }
            if (l <= 0L) {
                return 0L;
            }
            l = l2 = System.nanoTime() + l;
            if (l2 == 0L) {
                l = 1L;
            }
            if ((l = this.acquireWrite(true, l)) != 1L) {
                return l;
            }
        }
        throw new InterruptedException();
    }

    public void unlock(long l) {
        long l2;
        long l3;
        long l4 = l & 255L;
        while (((l2 = this.state) & -128L) == (l & -128L) && (l3 = l2 & 255L) != 0L) {
            if (l3 == 128L) {
                if (l4 != l3) break;
                Object object = U;
                l3 = STATE;
                l = 128L + l2;
                if (l == 0L) {
                    l = 256L;
                }
                ((Unsafe)object).putLongVolatile(this, l3, l);
                object = this.whead;
                if (object != null && ((WNode)object).status != 0) {
                    this.release((WNode)object);
                }
                return;
            }
            if (l4 == 0L || l4 >= 128L) break;
            if (l3 < 126L) {
                WNode wNode;
                if (!U.compareAndSwapLong(this, STATE, l2, l2 - 1L)) continue;
                if (l3 == 1L && (wNode = this.whead) != null && wNode.status != 0) {
                    this.release(wNode);
                }
                return;
            }
            if (this.tryDecReaderOverflow(l2) == 0L) continue;
            return;
        }
        throw new IllegalMonitorStateException();
    }

    public void unlockRead(long l) {
        long l2;
        long l3;
        while (((l2 = this.state) & -128L) == (l & -128L) && (l & 255L) != 0L && (l3 = 255L & l2) != 0L && l3 != 128L) {
            if (l3 < 126L) {
                WNode wNode;
                if (!U.compareAndSwapLong(this, STATE, l2, l2 - 1L)) continue;
                if (l3 == 1L && (wNode = this.whead) != null && wNode.status != 0) {
                    this.release(wNode);
                }
            } else if (this.tryDecReaderOverflow(l2) == 0L) continue;
            return;
        }
        throw new IllegalMonitorStateException();
    }

    public void unlockWrite(long l) {
        if (this.state == l && (l & 128L) != 0L) {
            Object object = U;
            long l2 = STATE;
            if ((l = 128L + l) == 0L) {
                l = 256L;
            }
            ((Unsafe)object).putLongVolatile(this, l2, l);
            object = this.whead;
            if (object != null && ((WNode)object).status != 0) {
                this.release((WNode)object);
            }
            return;
        }
        throw new IllegalMonitorStateException();
    }

    final void unstampedUnlockRead() {
        long l;
        long l2;
        while ((l = (l2 = this.state) & 255L) != 0L && l < 128L) {
            if (l < 126L) {
                WNode wNode;
                if (!U.compareAndSwapLong(this, STATE, l2, l2 - 1L)) continue;
                if (l == 1L && (wNode = this.whead) != null && wNode.status != 0) {
                    this.release(wNode);
                }
            } else if (this.tryDecReaderOverflow(l2) == 0L) continue;
            return;
        }
        throw new IllegalMonitorStateException();
    }

    final void unstampedUnlockWrite() {
        long l = this.state;
        if ((l & 128L) != 0L) {
            Object object = U;
            long l2 = STATE;
            if ((l = 128L + l) == 0L) {
                l = 256L;
            }
            ((Unsafe)object).putLongVolatile(this, l2, l);
            object = this.whead;
            if (object != null && ((WNode)object).status != 0) {
                this.release((WNode)object);
            }
            return;
        }
        throw new IllegalMonitorStateException();
    }

    public boolean validate(long l) {
        U.loadFence();
        boolean bl = (l & -128L) == (-128L & this.state);
        return bl;
    }

    public long writeLock() {
        long l;
        block3 : {
            block2 : {
                long l2;
                long l3 = this.state;
                if ((l3 & 255L) != 0L) break block2;
                Unsafe unsafe = U;
                long l4 = STATE;
                l = l2 = l3 + 128L;
                if (unsafe.compareAndSwapLong(this, l4, l3, l2)) break block3;
            }
            l = this.acquireWrite(false, 0L);
        }
        return l;
    }

    public long writeLockInterruptibly() throws InterruptedException {
        long l;
        if (!Thread.interrupted() && (l = this.acquireWrite(true, 0L)) != 1L) {
            return l;
        }
        throw new InterruptedException();
    }

    final class ReadLockView
    implements Lock {
        ReadLockView() {
        }

        @Override
        public void lock() {
            StampedLock.this.readLock();
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            StampedLock.this.readLockInterruptibly();
        }

        @Override
        public Condition newCondition() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean tryLock() {
            boolean bl = StampedLock.this.tryReadLock() != 0L;
            return bl;
        }

        @Override
        public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
            boolean bl = StampedLock.this.tryReadLock(l, timeUnit) != 0L;
            return bl;
        }

        @Override
        public void unlock() {
            StampedLock.this.unstampedUnlockRead();
        }
    }

    final class ReadWriteLockView
    implements ReadWriteLock {
        ReadWriteLockView() {
        }

        @Override
        public Lock readLock() {
            return StampedLock.this.asReadLock();
        }

        @Override
        public Lock writeLock() {
            return StampedLock.this.asWriteLock();
        }
    }

    static final class WNode {
        volatile WNode cowait;
        final int mode;
        volatile WNode next;
        volatile WNode prev;
        volatile int status;
        volatile Thread thread;

        WNode(int n, WNode wNode) {
            this.mode = n;
            this.prev = wNode;
        }
    }

    final class WriteLockView
    implements Lock {
        WriteLockView() {
        }

        @Override
        public void lock() {
            StampedLock.this.writeLock();
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            StampedLock.this.writeLockInterruptibly();
        }

        @Override
        public Condition newCondition() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean tryLock() {
            boolean bl = StampedLock.this.tryWriteLock() != 0L;
            return bl;
        }

        @Override
        public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
            boolean bl = StampedLock.this.tryWriteLock(l, timeUnit) != 0L;
            return bl;
        }

        @Override
        public void unlock() {
            StampedLock.this.unstampedUnlockWrite();
        }
    }

}

