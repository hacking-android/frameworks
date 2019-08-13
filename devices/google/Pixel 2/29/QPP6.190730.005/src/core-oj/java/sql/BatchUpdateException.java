/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLException;
import java.util.Arrays;

public class BatchUpdateException
extends SQLException {
    private static final long serialVersionUID = 5977529877145521757L;
    private final int[] updateCounts;

    public BatchUpdateException() {
        this(null, null, 0, null);
    }

    public BatchUpdateException(String object, String string, int n, int[] arrn) {
        super((String)object, string, n);
        object = arrn == null ? null : Arrays.copyOf(arrn, arrn.length);
        this.updateCounts = object;
    }

    public BatchUpdateException(String object, String string, int n, int[] arrn, Throwable throwable) {
        super((String)object, string, n, throwable);
        object = arrn == null ? null : Arrays.copyOf(arrn, arrn.length);
        this.updateCounts = object;
    }

    public BatchUpdateException(String string, String string2, int[] arrn) {
        this(string, string2, 0, arrn);
    }

    public BatchUpdateException(String string, String string2, int[] arrn, Throwable throwable) {
        this(string, string2, 0, arrn, throwable);
    }

    public BatchUpdateException(String string, int[] arrn) {
        this(string, null, 0, arrn);
    }

    public BatchUpdateException(String string, int[] arrn, Throwable throwable) {
        this(string, null, 0, arrn, throwable);
    }

    public BatchUpdateException(Throwable throwable) {
        String string = throwable == null ? null : throwable.toString();
        this(string, null, 0, null, throwable);
    }

    public BatchUpdateException(int[] arrn) {
        this(null, null, 0, arrn);
    }

    public BatchUpdateException(int[] arrn, Throwable throwable) {
        String string = throwable == null ? null : throwable.toString();
        this(string, null, 0, arrn, throwable);
    }

    public int[] getUpdateCounts() {
        Object object = this.updateCounts;
        object = object == null ? null : Arrays.copyOf(object, ((int[])object).length);
        return object;
    }
}

