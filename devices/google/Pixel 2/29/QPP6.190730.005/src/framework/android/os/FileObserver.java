/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.util.Log;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class FileObserver {
    public static final int ACCESS = 1;
    public static final int ALL_EVENTS = 4095;
    public static final int ATTRIB = 4;
    public static final int CLOSE_NOWRITE = 16;
    public static final int CLOSE_WRITE = 8;
    public static final int CREATE = 256;
    public static final int DELETE = 512;
    public static final int DELETE_SELF = 1024;
    private static final String LOG_TAG = "FileObserver";
    public static final int MODIFY = 2;
    public static final int MOVED_FROM = 64;
    public static final int MOVED_TO = 128;
    public static final int MOVE_SELF = 2048;
    public static final int OPEN = 32;
    @UnsupportedAppUsage
    private static ObserverThread s_observerThread = new ObserverThread();
    private int[] mDescriptors;
    private final List<File> mFiles;
    private final int mMask;

    static {
        s_observerThread.start();
    }

    public FileObserver(File file) {
        this(Arrays.asList(file));
    }

    public FileObserver(File file, int n) {
        this(Arrays.asList(file), n);
    }

    @Deprecated
    public FileObserver(String string2) {
        this(new File(string2));
    }

    @Deprecated
    public FileObserver(String string2, int n) {
        this(new File(string2), n);
    }

    public FileObserver(List<File> list) {
        this(list, 4095);
    }

    public FileObserver(List<File> list, int n) {
        this.mFiles = list;
        this.mMask = n;
    }

    protected void finalize() {
        this.stopWatching();
    }

    public abstract void onEvent(int var1, String var2);

    public void startWatching() {
        if (this.mDescriptors == null) {
            this.mDescriptors = s_observerThread.startWatching(this.mFiles, this.mMask, this);
        }
    }

    public void stopWatching() {
        int[] arrn = this.mDescriptors;
        if (arrn != null) {
            s_observerThread.stopWatching(arrn);
            this.mDescriptors = null;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NotifyEventType {
    }

    private static class ObserverThread
    extends Thread {
        private int m_fd = this.init();
        private HashMap<Integer, WeakReference> m_observers = new HashMap();

        public ObserverThread() {
            super(FileObserver.LOG_TAG);
        }

        private native int init();

        private native void observe(int var1);

        private native void startWatching(int var1, String[] var2, int var3, int[] var4);

        private native void stopWatching(int var1, int[] var2);

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @UnsupportedAppUsage
        public void onEvent(int n, int n2, String charSequence) {
            Object object = null;
            HashMap<Integer, WeakReference> hashMap = this.m_observers;
            synchronized (hashMap) {
                Object object2 = this.m_observers.get(n);
                if (object2 != null) {
                    object = object2 = (FileObserver)((Reference)object2).get();
                    if (object2 == null) {
                        this.m_observers.remove(n);
                        object = object2;
                    }
                }
            }
            if (object == null) return;
            try {
                ((FileObserver)object).onEvent(n2, (String)charSequence);
                return;
            }
            catch (Throwable throwable) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unhandled exception in FileObserver ");
                ((StringBuilder)charSequence).append(object);
                Log.wtf("FileObserver", ((StringBuilder)charSequence).toString(), throwable);
            }
        }

        @Override
        public void run() {
            this.observe(this.m_fd);
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public int[] startWatching(List<File> arrn, int n, FileObserver object) {
            int n2;
            int n3 = arrn.size();
            Object object2 = new String[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                object2[n2] = ((File)arrn.get(n2)).getAbsolutePath();
            }
            arrn = new int[n3];
            Arrays.fill(arrn, -1);
            this.startWatching(this.m_fd, (String[])object2, n, arrn);
            object2 = new WeakReference<FileObserver>((FileObserver)object);
            object = this.m_observers;
            synchronized (object) {
                n2 = arrn.length;
                n = 0;
                while (n < n2) {
                    n3 = arrn[n];
                    if (n3 >= 0) {
                        this.m_observers.put(n3, (WeakReference)object2);
                    }
                    ++n;
                }
                return arrn;
            }
        }

        public void stopWatching(int[] arrn) {
            this.stopWatching(this.m_fd, arrn);
        }
    }

}

