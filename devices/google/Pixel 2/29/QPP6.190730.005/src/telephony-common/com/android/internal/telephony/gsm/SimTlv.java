/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.gsm;

public class SimTlv {
    int mCurDataLength;
    int mCurDataOffset;
    int mCurOffset;
    boolean mHasValidTlvObject;
    byte[] mRecord;
    int mTlvLength;
    int mTlvOffset;

    public SimTlv(byte[] arrby, int n, int n2) {
        this.mRecord = arrby;
        this.mTlvOffset = n;
        this.mTlvLength = n2;
        this.mCurOffset = n;
        this.mHasValidTlvObject = this.parseCurrentTlvObject();
    }

    private boolean parseCurrentTlvObject() {
        try {
            block4 : {
                block7 : {
                    block6 : {
                        block5 : {
                            if (this.mRecord[this.mCurOffset] == 0 || (this.mRecord[this.mCurOffset] & 255) == 255) break block4;
                            if ((this.mRecord[this.mCurOffset + 1] & 255) >= 128) break block5;
                            this.mCurDataLength = this.mRecord[this.mCurOffset + 1] & 255;
                            this.mCurDataOffset = this.mCurOffset + 2;
                            break block6;
                        }
                        if ((this.mRecord[this.mCurOffset + 1] & 255) != 129) break block7;
                        this.mCurDataLength = this.mRecord[this.mCurOffset + 2] & 255;
                        this.mCurDataOffset = this.mCurOffset + 3;
                    }
                    return this.mCurDataLength + this.mCurDataOffset <= this.mTlvOffset + this.mTlvLength;
                }
                return false;
            }
            return false;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return false;
        }
    }

    public byte[] getData() {
        if (!this.mHasValidTlvObject) {
            return null;
        }
        int n = this.mCurDataLength;
        byte[] arrby = new byte[n];
        System.arraycopy(this.mRecord, this.mCurDataOffset, arrby, 0, n);
        return arrby;
    }

    public int getTag() {
        if (!this.mHasValidTlvObject) {
            return 0;
        }
        return this.mRecord[this.mCurOffset] & 255;
    }

    public boolean isValidObject() {
        return this.mHasValidTlvObject;
    }

    public boolean nextObject() {
        if (!this.mHasValidTlvObject) {
            return false;
        }
        this.mCurOffset = this.mCurDataOffset + this.mCurDataLength;
        this.mHasValidTlvObject = this.parseCurrentTlvObject();
        return this.mHasValidTlvObject;
    }
}

