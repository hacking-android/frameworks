/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.SQLWarning;

public class DataTruncation
extends SQLWarning {
    private static final long serialVersionUID = 6464298989504059473L;
    private int dataSize;
    private int index;
    private boolean parameter;
    private boolean read;
    private int transferSize;

    public DataTruncation(int n, boolean bl, boolean bl2, int n2, int n3) {
        String string = bl2 ? "01004" : "22001";
        super("Data truncation", string);
        this.index = n;
        this.parameter = bl;
        this.read = bl2;
        this.dataSize = n2;
        this.transferSize = n3;
    }

    public DataTruncation(int n, boolean bl, boolean bl2, int n2, int n3, Throwable throwable) {
        String string = bl2 ? "01004" : "22001";
        super("Data truncation", string, throwable);
        this.index = n;
        this.parameter = bl;
        this.read = bl2;
        this.dataSize = n2;
        this.transferSize = n3;
    }

    public int getDataSize() {
        return this.dataSize;
    }

    public int getIndex() {
        return this.index;
    }

    public boolean getParameter() {
        return this.parameter;
    }

    public boolean getRead() {
        return this.read;
    }

    public int getTransferSize() {
        return this.transferSize;
    }
}

