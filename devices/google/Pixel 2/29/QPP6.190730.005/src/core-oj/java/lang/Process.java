/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public abstract class Process {
    public abstract void destroy();

    public Process destroyForcibly() {
        this.destroy();
        return this;
    }

    public abstract int exitValue();

    public abstract InputStream getErrorStream();

    public abstract InputStream getInputStream();

    public abstract OutputStream getOutputStream();

    public boolean isAlive() {
        try {
            this.exitValue();
            return false;
        }
        catch (IllegalThreadStateException illegalThreadStateException) {
            return true;
        }
    }

    public abstract int waitFor() throws InterruptedException;

    public boolean waitFor(long l, TimeUnit timeUnit) throws InterruptedException {
        long l2 = System.nanoTime();
        long l3 = timeUnit.toNanos(l);
        do {
            try {
                this.exitValue();
                return true;
            }
            catch (IllegalThreadStateException illegalThreadStateException) {
                if (l3 <= 0L) continue;
                Thread.sleep(Math.min(TimeUnit.NANOSECONDS.toMillis(l3) + 1L, 100L));
                if ((l3 = timeUnit.toNanos(l) - (System.nanoTime() - l2)) > 0L) continue;
                return false;
            }
            break;
        } while (true);
    }
}

