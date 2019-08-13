/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import sun.misc.Cleaner;
import sun.misc.Unsafe;
import sun.misc.VM;
import sun.nio.ch.DirectBuffer;
import sun.nio.ch.IOUtil;
import sun.security.action.GetPropertyAction;

public class Util {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long MAX_CACHED_BUFFER_SIZE;
    private static final int TEMP_BUF_POOL_SIZE;
    private static ThreadLocal<BufferCache> bufferCache;
    private static volatile String bugLevel;
    private static Unsafe unsafe;

    static {
        TEMP_BUF_POOL_SIZE = IOUtil.IOV_MAX;
        MAX_CACHED_BUFFER_SIZE = Util.getMaxCachedBufferSize();
        bufferCache = new ThreadLocal<BufferCache>(){

            @Override
            protected BufferCache initialValue() {
                return new BufferCache();
            }
        };
        unsafe = Unsafe.getUnsafe();
        bugLevel = null;
    }

    private static byte _get(long l) {
        return unsafe.getByte(l);
    }

    private static void _put(long l, byte by) {
        unsafe.putByte(l, by);
    }

    static /* synthetic */ boolean access$100(int n) {
        return Util.isBufferTooLarge(n);
    }

    static /* synthetic */ boolean access$200(ByteBuffer byteBuffer) {
        return Util.isBufferTooLarge(byteBuffer);
    }

    static boolean atBugLevel(String string) {
        if (bugLevel == null) {
            if (!VM.isBooted()) {
                return false;
            }
            String string2 = AccessController.doPrivileged(new GetPropertyAction("sun.nio.ch.bugLevel"));
            if (string2 == null) {
                string2 = "";
            }
            bugLevel = string2;
        }
        return bugLevel.equals(string);
    }

    static void erase(ByteBuffer byteBuffer) {
        unsafe.setMemory(((DirectBuffer)((Object)byteBuffer)).address(), byteBuffer.capacity(), (byte)0);
    }

    private static void free(ByteBuffer object) {
        if ((object = ((DirectBuffer)object).cleaner()) != null) {
            ((Cleaner)object).clean();
        }
    }

    private static long getMaxCachedBufferSize() {
        String string = AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                return System.getProperty("jdk.nio.maxCachedBufferSize");
            }
        });
        if (string != null) {
            try {
                long l = Long.parseLong(string);
                if (l >= 0L) {
                    return l;
                }
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return Long.MAX_VALUE;
    }

    public static ByteBuffer getTemporaryDirectBuffer(int n) {
        if (Util.isBufferTooLarge(n)) {
            return ByteBuffer.allocateDirect(n);
        }
        BufferCache bufferCache = Util.bufferCache.get();
        ByteBuffer byteBuffer = bufferCache.get(n);
        if (byteBuffer != null) {
            return byteBuffer;
        }
        if (!bufferCache.isEmpty()) {
            Util.free(bufferCache.removeFirst());
        }
        return ByteBuffer.allocateDirect(n);
    }

    private static boolean isBufferTooLarge(int n) {
        boolean bl = (long)n > MAX_CACHED_BUFFER_SIZE;
        return bl;
    }

    private static boolean isBufferTooLarge(ByteBuffer byteBuffer) {
        return Util.isBufferTooLarge(byteBuffer.capacity());
    }

    static void offerFirstTemporaryDirectBuffer(ByteBuffer byteBuffer) {
        if (Util.isBufferTooLarge(byteBuffer)) {
            Util.free(byteBuffer);
            return;
        }
        if (!bufferCache.get().offerFirst(byteBuffer)) {
            Util.free(byteBuffer);
        }
    }

    static void offerLastTemporaryDirectBuffer(ByteBuffer byteBuffer) {
        if (Util.isBufferTooLarge(byteBuffer)) {
            Util.free(byteBuffer);
            return;
        }
        if (!bufferCache.get().offerLast(byteBuffer)) {
            Util.free(byteBuffer);
        }
    }

    public static void releaseTemporaryDirectBuffer(ByteBuffer byteBuffer) {
        Util.offerFirstTemporaryDirectBuffer(byteBuffer);
    }

    static ByteBuffer[] subsequence(ByteBuffer[] arrbyteBuffer, int n, int n2) {
        if (n == 0 && n2 == arrbyteBuffer.length) {
            return arrbyteBuffer;
        }
        ByteBuffer[] arrbyteBuffer2 = new ByteBuffer[n2];
        for (int i = 0; i < n2; ++i) {
            arrbyteBuffer2[i] = arrbyteBuffer[n + i];
        }
        return arrbyteBuffer2;
    }

    static <E> Set<E> ungrowableSet(final Set<E> set) {
        return new Set<E>(){

            @Override
            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void clear() {
                set.clear();
            }

            @Override
            public boolean contains(Object object) {
                return set.contains(object);
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return set.containsAll(collection);
            }

            @Override
            public boolean equals(Object object) {
                return set.equals(object);
            }

            @Override
            public int hashCode() {
                return set.hashCode();
            }

            @Override
            public boolean isEmpty() {
                return set.isEmpty();
            }

            @Override
            public Iterator<E> iterator() {
                return set.iterator();
            }

            @Override
            public boolean remove(Object object) {
                return set.remove(object);
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return set.removeAll(collection);
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return set.retainAll(collection);
            }

            @Override
            public int size() {
                return set.size();
            }

            @Override
            public Object[] toArray() {
                return set.toArray();
            }

            @Override
            public <T> T[] toArray(T[] arrT) {
                return set.toArray(arrT);
            }

            public String toString() {
                return set.toString();
            }
        };
    }

    static Unsafe unsafe() {
        return unsafe;
    }

    private static class BufferCache {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private ByteBuffer[] buffers = new ByteBuffer[Util.access$000()];
        private int count;
        private int start;

        BufferCache() {
        }

        private int next(int n) {
            return (n + 1) % TEMP_BUF_POOL_SIZE;
        }

        ByteBuffer get(int n) {
            int n2;
            ByteBuffer byteBuffer;
            if (this.count == 0) {
                return null;
            }
            ByteBuffer[] arrbyteBuffer = this.buffers;
            ByteBuffer byteBuffer2 = byteBuffer = arrbyteBuffer[this.start];
            if (byteBuffer.capacity() < n) {
                byteBuffer = null;
                n2 = this.start;
                do {
                    int n3;
                    n2 = n3 = this.next(n2);
                    byteBuffer2 = byteBuffer;
                    if (n3 == this.start) break;
                    byteBuffer2 = arrbyteBuffer[n2];
                    if (byteBuffer2 != null) continue;
                    byteBuffer2 = byteBuffer;
                    break;
                } while (byteBuffer2.capacity() < n);
                if (byteBuffer2 == null) {
                    return null;
                }
                arrbyteBuffer[n2] = arrbyteBuffer[this.start];
            }
            n2 = this.start;
            arrbyteBuffer[n2] = null;
            this.start = this.next(n2);
            --this.count;
            byteBuffer2.rewind();
            byteBuffer2.limit(n);
            return byteBuffer2;
        }

        boolean isEmpty() {
            boolean bl = this.count == 0;
            return bl;
        }

        boolean offerFirst(ByteBuffer byteBuffer) {
            if (this.count >= TEMP_BUF_POOL_SIZE) {
                return false;
            }
            this.start = (this.start + TEMP_BUF_POOL_SIZE - 1) % TEMP_BUF_POOL_SIZE;
            this.buffers[this.start] = byteBuffer;
            ++this.count;
            return true;
        }

        boolean offerLast(ByteBuffer byteBuffer) {
            if (this.count >= TEMP_BUF_POOL_SIZE) {
                return false;
            }
            int n = this.start;
            int n2 = this.count++;
            int n3 = TEMP_BUF_POOL_SIZE;
            this.buffers[(n + n2) % n3] = byteBuffer;
            return true;
        }

        ByteBuffer removeFirst() {
            ByteBuffer[] arrbyteBuffer = this.buffers;
            int n = this.start;
            ByteBuffer byteBuffer = arrbyteBuffer[n];
            arrbyteBuffer[n] = null;
            this.start = this.next(n);
            --this.count;
            return byteBuffer;
        }
    }

}

