/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  libcore.util.EmptyArray
 */
package java.lang;

import dalvik.annotation.optimization.FastNative;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import libcore.util.EmptyArray;

public class Throwable
implements Serializable {
    private static final String CAUSE_CAPTION = "Caused by: ";
    private static Throwable[] EMPTY_THROWABLE_ARRAY;
    private static final String NULL_CAUSE_MESSAGE = "Cannot suppress a null exception.";
    private static final String SELF_SUPPRESSION_MESSAGE = "Self-suppression not permitted";
    private static final String SUPPRESSED_CAPTION = "Suppressed: ";
    private static final long serialVersionUID = -3042686055658047285L;
    private transient Object backtrace;
    private Throwable cause = this;
    private String detailMessage;
    private StackTraceElement[] stackTrace = EmptyArray.STACK_TRACE_ELEMENT;
    private List<Throwable> suppressedExceptions = Collections.emptyList();

    public Throwable() {
        this.fillInStackTrace();
    }

    public Throwable(String string) {
        this.fillInStackTrace();
        this.detailMessage = string;
    }

    public Throwable(String string, Throwable throwable) {
        this.fillInStackTrace();
        this.detailMessage = string;
        this.cause = throwable;
    }

    protected Throwable(String string, Throwable throwable, boolean bl, boolean bl2) {
        if (bl2) {
            this.fillInStackTrace();
        } else {
            this.stackTrace = null;
        }
        this.detailMessage = string;
        this.cause = throwable;
        if (!bl) {
            this.suppressedExceptions = null;
        }
    }

    public Throwable(Throwable throwable) {
        this.fillInStackTrace();
        String string = throwable == null ? null : throwable.toString();
        this.detailMessage = string;
        this.cause = throwable;
    }

    private StackTraceElement[] getOurStackTrace() {
        synchronized (this) {
            block7 : {
                block6 : {
                    if (this.stackTrace == EmptyArray.STACK_TRACE_ELEMENT || this.stackTrace == null && this.backtrace != null) break block6;
                    if (this.stackTrace == null) {
                        StackTraceElement[] arrstackTraceElement = EmptyArray.STACK_TRACE_ELEMENT;
                        return arrstackTraceElement;
                    }
                    break block7;
                }
                this.stackTrace = Throwable.nativeGetStackTrace(this.backtrace);
                this.backtrace = null;
                if (this.stackTrace != null) break block7;
                StackTraceElement[] arrstackTraceElement = EmptyArray.STACK_TRACE_ELEMENT;
                return arrstackTraceElement;
            }
            StackTraceElement[] arrstackTraceElement = this.stackTrace;
            return arrstackTraceElement;
        }
    }

    @FastNative
    private static native Object nativeFillInStackTrace();

    @FastNative
    private static native StackTraceElement[] nativeGetStackTrace(Object var0);

    /*
     * WARNING - void declaration
     */
    private void printEnclosedStackTrace(PrintStreamOrWriter printStreamOrWriter, StackTraceElement[] object, String object22, String string, Set<Throwable> set) {
        void var5_7;
        if (var5_7.contains(this)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("\t[CIRCULAR REFERENCE:");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append("]");
            printStreamOrWriter.println(((StringBuilder)object).toString());
        } else {
            void var4_6;
            var5_7.add(this);
            StackTraceElement[] arrstackTraceElement = this.getOurStackTrace();
            int n = arrstackTraceElement.length;
            int n2 = ((Object)object).length;
            --n;
            --n2;
            while (n >= 0 && n2 >= 0 && arrstackTraceElement[n].equals(object[n2])) {
                --n;
                --n2;
            }
            int n3 = arrstackTraceElement.length - 1 - n;
            object = new StringBuilder();
            ((StringBuilder)object).append((String)var4_6);
            ((StringBuilder)object).append((String)object22);
            ((StringBuilder)object).append(this);
            printStreamOrWriter.println(((StringBuilder)object).toString());
            for (n2 = 0; n2 <= n; ++n2) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)var4_6);
                ((StringBuilder)object).append("\tat ");
                ((StringBuilder)object).append(arrstackTraceElement[n2]);
                printStreamOrWriter.println(((StringBuilder)object).toString());
            }
            if (n3 != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)var4_6);
                ((StringBuilder)object).append("\t... ");
                ((StringBuilder)object).append(n3);
                ((StringBuilder)object).append(" more");
                printStreamOrWriter.println(((StringBuilder)object).toString());
            }
            for (Throwable throwable : this.getSuppressed()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)var4_6);
                stringBuilder.append("\t");
                Throwable.super.printEnclosedStackTrace(printStreamOrWriter, arrstackTraceElement, "Suppressed: ", stringBuilder.toString(), (Set<Throwable>)var5_7);
            }
            object = this.getCause();
            if (object != null) {
                Throwable.super.printEnclosedStackTrace(printStreamOrWriter, arrstackTraceElement, "Caused by: ", (String)var4_6, (Set<Throwable>)var5_7);
            }
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void printStackTrace(PrintStreamOrWriter printStreamOrWriter) {
        Set<Throwable> set = Collections.newSetFromMap(new IdentityHashMap<K, V>());
        set.add(this);
        Object object = printStreamOrWriter.lock();
        synchronized (object) {
            StackTraceElement[] arrstackTraceElement;
            Object object2;
            printStreamOrWriter.println(this);
            for (StackTraceElement stackTraceElement : this.getOurStackTrace()) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("\tat ");
                ((StringBuilder)object2).append(stackTraceElement);
                printStreamOrWriter.println(((StringBuilder)object2).toString());
            }
            object2 = this.getSuppressed();
            int n = ((Throwable[])object2).length;
            for (int i = 0; i < n; ++i) {
                Throwable.super.printEnclosedStackTrace(printStreamOrWriter, arrstackTraceElement, "Suppressed: ", "\t", set);
            }
            object2 = this.getCause();
            if (object2 != null) {
                Throwable.super.printEnclosedStackTrace(printStreamOrWriter, arrstackTraceElement, "Caused by: ", "", set);
            }
            return;
        }
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        object.defaultReadObject();
        object = this.suppressedExceptions;
        if (object != null) {
            block11 : {
                if (object.isEmpty()) {
                    object = Collections.emptyList();
                } else {
                    block12 : {
                        ArrayList<StackTraceElement[]> arrayList = new ArrayList<StackTraceElement[]>(1);
                        Iterator<Throwable> iterator = this.suppressedExceptions.iterator();
                        do {
                            object = arrayList;
                            if (!iterator.hasNext()) break block11;
                            object = iterator.next();
                            if (object == null) break block12;
                            if (object == this) break;
                            arrayList.add((StackTraceElement[])object);
                        } while (true);
                        throw new IllegalArgumentException("Self-suppression not permitted");
                    }
                    throw new NullPointerException("Cannot suppress a null exception.");
                }
            }
            this.suppressedExceptions = object;
        }
        object = this.stackTrace;
        if (object != null) {
            if (((StackTraceElement[])object).length != 0) {
                if (((StackTraceElement[])object).length == 1 && SentinelHolder.STACK_TRACE_ELEMENT_SENTINEL.equals(this.stackTrace[0])) {
                    this.stackTrace = null;
                } else {
                    object = this.stackTrace;
                    int n = ((StackTraceElement[])object).length;
                    for (int i = 0; i < n; ++i) {
                        if (object[i] != null) {
                            continue;
                        }
                        throw new NullPointerException("null StackTraceElement in serial stream. ");
                    }
                }
            }
        } else {
            this.stackTrace = new StackTraceElement[0];
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        synchronized (this) {
            void var1_4;
            StackTraceElement[] arrstackTraceElement;
            block9 : {
                block8 : {
                    this.getOurStackTrace();
                    arrstackTraceElement = this.stackTrace;
                    StackTraceElement[] arrstackTraceElement2 = this.stackTrace;
                    if (arrstackTraceElement2 != null) break block8;
                    try {
                        this.stackTrace = SentinelHolder.STACK_TRACE_SENTINEL;
                    }
                    catch (Throwable throwable) {
                        break block9;
                    }
                }
                try {
                    objectOutputStream.defaultWriteObject();
                    this.stackTrace = arrstackTraceElement;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                return;
            }
            this.stackTrace = arrstackTraceElement;
            throw var1_4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void addSuppressed(Throwable throwable) {
        synchronized (this) {
            if (throwable == this) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Self-suppression not permitted", throwable);
                throw illegalArgumentException;
            }
            if (throwable == null) {
                throwable = new NullPointerException("Cannot suppress a null exception.");
                throw throwable;
            }
            ArrayList<Throwable> arrayList = this.suppressedExceptions;
            if (arrayList == null) {
                return;
            }
            if (this.suppressedExceptions.isEmpty()) {
                arrayList = new ArrayList<Throwable>(1);
                this.suppressedExceptions = arrayList;
            }
            this.suppressedExceptions.add(throwable);
            return;
        }
    }

    public Throwable fillInStackTrace() {
        synchronized (this) {
            if (this.stackTrace != null || this.backtrace != null) {
                this.backtrace = Throwable.nativeFillInStackTrace();
                this.stackTrace = EmptyArray.STACK_TRACE_ELEMENT;
            }
            return this;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Throwable getCause() {
        synchronized (this) {
            block4 : {
                if (this.cause != this) break block4;
                return null;
            }
            return this.cause;
        }
    }

    public String getLocalizedMessage() {
        return this.getMessage();
    }

    public String getMessage() {
        return this.detailMessage;
    }

    public StackTraceElement[] getStackTrace() {
        return (StackTraceElement[])this.getOurStackTrace().clone();
    }

    public final Throwable[] getSuppressed() {
        synchronized (this) {
            block5 : {
                if (EMPTY_THROWABLE_ARRAY == null) {
                    EMPTY_THROWABLE_ARRAY = new Throwable[0];
                }
                if (this.suppressedExceptions == null || this.suppressedExceptions.isEmpty()) break block5;
                Throwable[] arrthrowable = this.suppressedExceptions.toArray(EMPTY_THROWABLE_ARRAY);
                return arrthrowable;
            }
            Throwable[] arrthrowable = EMPTY_THROWABLE_ARRAY;
            return arrthrowable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Throwable initCause(Throwable throwable) {
        synchronized (this) {
            if (this.cause != this) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Can't overwrite cause with ");
                stringBuilder.append(Objects.toString(throwable, "a null"));
                IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString(), this);
                throw illegalStateException;
            }
            if (throwable != this) {
                this.cause = throwable;
                return this;
            }
            throwable = new IllegalArgumentException("Self-causation not permitted", this);
            throw throwable;
        }
    }

    public void printStackTrace() {
        this.printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream printStream) {
        this.printStackTrace(new WrappedPrintStream(printStream));
    }

    public void printStackTrace(PrintWriter printWriter) {
        this.printStackTrace(new WrappedPrintWriter(printWriter));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setStackTrace(StackTraceElement[] object) {
        object = (StackTraceElement[])object.clone();
        for (int i = 0; i < ((StackTraceElement[])object).length; ++i) {
            if (object[i] != null) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("stackTrace[");
            ((StringBuilder)object).append(i);
            ((StringBuilder)object).append("]");
            throw new NullPointerException(((StringBuilder)object).toString());
        }
        synchronized (this) {
            if (this.stackTrace == null && this.backtrace == null) {
                return;
            }
            this.stackTrace = object;
            return;
        }
    }

    public String toString() {
        String string;
        block0 : {
            string = this.getClass().getName();
            String string2 = this.getLocalizedMessage();
            if (string2 == null) break block0;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(": ");
            stringBuilder.append(string2);
            string = stringBuilder.toString();
        }
        return string;
    }

    private static abstract class PrintStreamOrWriter {
        private PrintStreamOrWriter() {
        }

        abstract Object lock();

        abstract void println(Object var1);
    }

    private static class SentinelHolder {
        public static final StackTraceElement STACK_TRACE_ELEMENT_SENTINEL = new StackTraceElement("", "", null, Integer.MIN_VALUE);
        public static final StackTraceElement[] STACK_TRACE_SENTINEL = new StackTraceElement[]{STACK_TRACE_ELEMENT_SENTINEL};

        private SentinelHolder() {
        }
    }

    private static class WrappedPrintStream
    extends PrintStreamOrWriter {
        private final PrintStream printStream;

        WrappedPrintStream(PrintStream printStream) {
            this.printStream = printStream;
        }

        @Override
        Object lock() {
            return this.printStream;
        }

        @Override
        void println(Object object) {
            this.printStream.println(object);
        }
    }

    private static class WrappedPrintWriter
    extends PrintStreamOrWriter {
        private final PrintWriter printWriter;

        WrappedPrintWriter(PrintWriter printWriter) {
            this.printWriter = printWriter;
        }

        @Override
        Object lock() {
            return this.printWriter;
        }

        @Override
        void println(Object object) {
            this.printWriter.println(object);
        }
    }

}

