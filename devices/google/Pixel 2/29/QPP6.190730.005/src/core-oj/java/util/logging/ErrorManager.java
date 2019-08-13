/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.io.PrintStream;

public class ErrorManager {
    public static final int CLOSE_FAILURE = 3;
    public static final int FLUSH_FAILURE = 2;
    public static final int FORMAT_FAILURE = 5;
    public static final int GENERIC_FAILURE = 0;
    public static final int OPEN_FAILURE = 4;
    public static final int WRITE_FAILURE = 1;
    private boolean reported = false;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void error(String string, Exception exception, int n) {
        synchronized (this) {
            void var2_2;
            void var3_3;
            boolean bl = this.reported;
            if (bl) {
                return;
            }
            this.reported = true;
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("java.util.logging.ErrorManager: ");
            ((StringBuilder)charSequence).append((int)var3_3);
            String string2 = ((StringBuilder)charSequence).toString();
            charSequence = string2;
            if (string != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(": ");
                ((StringBuilder)charSequence).append(string);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            System.err.println((String)charSequence);
            if (var2_2 != null) {
                var2_2.printStackTrace();
            }
            return;
        }
    }
}

