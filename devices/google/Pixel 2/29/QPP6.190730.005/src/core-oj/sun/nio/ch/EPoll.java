/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import sun.misc.Unsafe;

class EPoll {
    static final int EPOLLONESHOT = 1073741824;
    static final int EPOLL_CTL_ADD = 1;
    static final int EPOLL_CTL_DEL = 2;
    static final int EPOLL_CTL_MOD = 3;
    private static final int OFFSETOF_EVENTS;
    private static final int OFFSETOF_FD;
    private static final int SIZEOF_EPOLLEVENT;
    private static final Unsafe unsafe;

    static {
        unsafe = Unsafe.getUnsafe();
        SIZEOF_EPOLLEVENT = EPoll.eventSize();
        OFFSETOF_EVENTS = EPoll.eventsOffset();
        OFFSETOF_FD = EPoll.dataOffset();
    }

    private EPoll() {
    }

    static long allocatePollArray(int n) {
        return unsafe.allocateMemory(SIZEOF_EPOLLEVENT * n);
    }

    private static native int dataOffset();

    static native int epollCreate() throws IOException;

    static native int epollCtl(int var0, int var1, int var2, int var3);

    static native int epollWait(int var0, long var1, int var3) throws IOException;

    private static native int eventSize();

    private static native int eventsOffset();

    static void freePollArray(long l) {
        unsafe.freeMemory(l);
    }

    static int getDescriptor(long l) {
        return unsafe.getInt((long)OFFSETOF_FD + l);
    }

    static long getEvent(long l, int n) {
        return (long)(SIZEOF_EPOLLEVENT * n) + l;
    }

    static int getEvents(long l) {
        return unsafe.getInt((long)OFFSETOF_EVENTS + l);
    }
}

