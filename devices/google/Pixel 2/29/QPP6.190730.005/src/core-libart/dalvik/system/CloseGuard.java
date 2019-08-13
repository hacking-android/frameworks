/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.Serializable;

public final class CloseGuard {
    private static volatile Tracker currentTracker;
    private static volatile Reporter reporter;
    private static volatile boolean stackAndTrackingEnabled;
    private Object closerNameOrAllocationInfo;

    static {
        stackAndTrackingEnabled = true;
        reporter = new DefaultReporter();
        currentTracker = null;
    }

    @UnsupportedAppUsage
    private CloseGuard() {
    }

    @UnsupportedAppUsage
    public static CloseGuard get() {
        return new CloseGuard();
    }

    public static Reporter getReporter() {
        return reporter;
    }

    public static Tracker getTracker() {
        return currentTracker;
    }

    public static boolean isEnabled() {
        return stackAndTrackingEnabled;
    }

    @UnsupportedAppUsage
    public static void setEnabled(boolean bl) {
        stackAndTrackingEnabled = bl;
    }

    @UnsupportedAppUsage
    public static void setReporter(Reporter reporter) {
        if (reporter != null) {
            CloseGuard.reporter = reporter;
            return;
        }
        throw new NullPointerException("reporter == null");
    }

    public static void setTracker(Tracker tracker) {
        currentTracker = tracker;
    }

    @UnsupportedAppUsage
    public void close() {
        Object object;
        Tracker tracker = currentTracker;
        if (tracker != null && (object = this.closerNameOrAllocationInfo) instanceof Throwable) {
            tracker.close((Throwable)object);
        }
        this.closerNameOrAllocationInfo = null;
    }

    @UnsupportedAppUsage
    public void open(String object) {
        if (object != null) {
            if (!stackAndTrackingEnabled) {
                this.closerNameOrAllocationInfo = object;
                return;
            }
            Serializable serializable = new StringBuilder();
            serializable.append("Explicit termination method '");
            serializable.append((String)object);
            serializable.append("' not called");
            serializable = new Throwable(serializable.toString());
            this.closerNameOrAllocationInfo = serializable;
            object = currentTracker;
            if (object != null) {
                object.open((Throwable)serializable);
            }
            return;
        }
        throw new NullPointerException("closer == null");
    }

    @UnsupportedAppUsage
    public void warnIfOpen() {
        Object object = this.closerNameOrAllocationInfo;
        if (object != null) {
            if (object instanceof String) {
                object = new StringBuilder();
                ((StringBuilder)object).append("A resource failed to call ");
                ((StringBuilder)object).append((String)this.closerNameOrAllocationInfo);
                ((StringBuilder)object).append(". ");
                System.logW((String)((StringBuilder)object).toString());
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("A resource was acquired at attached stack trace but never released. ");
                ((StringBuilder)object).append("See java.io.Closeable for information on avoiding resource leaks.");
                String string = ((StringBuilder)object).toString();
                object = (Throwable)this.closerNameOrAllocationInfo;
                reporter.report(string, (Throwable)object);
            }
        }
    }

    private static final class DefaultReporter
    implements Reporter {
        @UnsupportedAppUsage
        private DefaultReporter() {
        }

        @Override
        public void report(String string, Throwable throwable) {
            System.logW((String)string, (Throwable)throwable);
        }
    }

    public static interface Reporter {
        @UnsupportedAppUsage
        public void report(String var1, Throwable var2);
    }

    public static interface Tracker {
        public void close(Throwable var1);

        public void open(Throwable var1);
    }

}

