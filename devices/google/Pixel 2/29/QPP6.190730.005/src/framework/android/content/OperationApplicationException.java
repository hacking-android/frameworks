/*
 * Decompiled with CFR 0.145.
 */
package android.content;

public class OperationApplicationException
extends Exception {
    private final int mNumSuccessfulYieldPoints;

    public OperationApplicationException() {
        this.mNumSuccessfulYieldPoints = 0;
    }

    public OperationApplicationException(int n) {
        this.mNumSuccessfulYieldPoints = n;
    }

    public OperationApplicationException(String string2) {
        super(string2);
        this.mNumSuccessfulYieldPoints = 0;
    }

    public OperationApplicationException(String string2, int n) {
        super(string2);
        this.mNumSuccessfulYieldPoints = n;
    }

    public OperationApplicationException(String string2, Throwable throwable) {
        super(string2, throwable);
        this.mNumSuccessfulYieldPoints = 0;
    }

    public OperationApplicationException(Throwable throwable) {
        super(throwable);
        this.mNumSuccessfulYieldPoints = 0;
    }

    public int getNumSuccessfulYieldPoints() {
        return this.mNumSuccessfulYieldPoints;
    }
}

