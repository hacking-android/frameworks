/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.Cursor;
import java.util.Iterator;

public final class CursorJoiner
implements Iterator<Result>,
Iterable<Result> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int[] mColumnsLeft;
    private int[] mColumnsRight;
    private Result mCompareResult;
    private boolean mCompareResultIsValid;
    private Cursor mCursorLeft;
    private Cursor mCursorRight;
    private String[] mValues;

    public CursorJoiner(Cursor object, String[] arrstring, Cursor cursor, String[] arrstring2) {
        if (arrstring.length == arrstring2.length) {
            this.mCursorLeft = object;
            this.mCursorRight = cursor;
            this.mCursorLeft.moveToFirst();
            this.mCursorRight.moveToFirst();
            this.mCompareResultIsValid = false;
            this.mColumnsLeft = this.buildColumnIndiciesArray((Cursor)object, arrstring);
            this.mColumnsRight = this.buildColumnIndiciesArray(cursor, arrstring2);
            this.mValues = new String[this.mColumnsLeft.length * 2];
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("you must have the same number of columns on the left and right, ");
        ((StringBuilder)object).append(arrstring.length);
        ((StringBuilder)object).append(" != ");
        ((StringBuilder)object).append(arrstring2.length);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private int[] buildColumnIndiciesArray(Cursor cursor, String[] arrstring) {
        int[] arrn = new int[arrstring.length];
        for (int i = 0; i < arrstring.length; ++i) {
            arrn[i] = cursor.getColumnIndexOrThrow(arrstring[i]);
        }
        return arrn;
    }

    private static int compareStrings(String ... arrstring) {
        if (arrstring.length % 2 == 0) {
            for (int i = 0; i < arrstring.length; i += 2) {
                String string2 = arrstring[i];
                int n = -1;
                if (string2 == null) {
                    if (arrstring[i + 1] == null) continue;
                    return -1;
                }
                if (arrstring[i + 1] == null) {
                    return 1;
                }
                int n2 = arrstring[i].compareTo(arrstring[i + 1]);
                if (n2 == 0) continue;
                i = n2 < 0 ? n : 1;
                return i;
            }
            return 0;
        }
        throw new IllegalArgumentException("you must specify an even number of values");
    }

    private void incrementCursors() {
        if (this.mCompareResultIsValid) {
            int n = 1.$SwitchMap$android$database$CursorJoiner$Result[this.mCompareResult.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.mCursorRight.moveToNext();
                    }
                } else {
                    this.mCursorLeft.moveToNext();
                }
            } else {
                this.mCursorLeft.moveToNext();
                this.mCursorRight.moveToNext();
            }
            this.mCompareResultIsValid = false;
        }
    }

    private static void populateValues(String[] arrstring, Cursor cursor, int[] arrn, int n) {
        for (int i = 0; i < arrn.length; ++i) {
            arrstring[i * 2 + n] = cursor.getString(arrn[i]);
        }
    }

    @Override
    public boolean hasNext() {
        boolean bl;
        block19 : {
            block18 : {
                boolean bl2;
                block12 : {
                    block17 : {
                        block16 : {
                            boolean bl3;
                            block13 : {
                                block15 : {
                                    block14 : {
                                        boolean bl4 = this.mCompareResultIsValid;
                                        boolean bl5 = false;
                                        bl3 = false;
                                        bl2 = false;
                                        bl = false;
                                        if (!bl4) break block12;
                                        int n = 1.$SwitchMap$android$database$CursorJoiner$Result[this.mCompareResult.ordinal()];
                                        if (n == 1) break block13;
                                        if (n != 2) {
                                            if (n == 3) {
                                                if (!this.mCursorLeft.isAfterLast() || !this.mCursorRight.isLast()) {
                                                    bl = true;
                                                }
                                                return bl;
                                            }
                                            StringBuilder stringBuilder = new StringBuilder();
                                            stringBuilder.append("bad value for mCompareResult, ");
                                            stringBuilder.append((Object)this.mCompareResult);
                                            throw new IllegalStateException(stringBuilder.toString());
                                        }
                                        if (!this.mCursorLeft.isLast()) break block14;
                                        bl = bl5;
                                        if (this.mCursorRight.isAfterLast()) break block15;
                                    }
                                    bl = true;
                                }
                                return bl;
                            }
                            if (!this.mCursorLeft.isLast()) break block16;
                            bl = bl3;
                            if (this.mCursorRight.isLast()) break block17;
                        }
                        bl = true;
                    }
                    return bl;
                }
                if (!this.mCursorLeft.isAfterLast()) break block18;
                bl = bl2;
                if (this.mCursorRight.isAfterLast()) break block19;
            }
            bl = true;
        }
        return bl;
    }

    @Override
    public Iterator<Result> iterator() {
        return this;
    }

    @Override
    public Result next() {
        if (this.hasNext()) {
            this.incrementCursors();
            int n = this.mCursorLeft.isAfterLast() ^ true;
            boolean bl = this.mCursorRight.isAfterLast();
            if (n != 0 && bl ^ true) {
                CursorJoiner.populateValues(this.mValues, this.mCursorLeft, this.mColumnsLeft, 0);
                CursorJoiner.populateValues(this.mValues, this.mCursorRight, this.mColumnsRight, 1);
                n = CursorJoiner.compareStrings(this.mValues);
                if (n != -1) {
                    if (n != 0) {
                        if (n == 1) {
                            this.mCompareResult = Result.RIGHT;
                        }
                    } else {
                        this.mCompareResult = Result.BOTH;
                    }
                } else {
                    this.mCompareResult = Result.LEFT;
                }
            } else {
                this.mCompareResult = n != 0 ? Result.LEFT : Result.RIGHT;
            }
            this.mCompareResultIsValid = true;
            return this.mCompareResult;
        }
        throw new IllegalStateException("you must only call next() when hasNext() is true");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("not implemented");
    }

    public static enum Result {
        RIGHT,
        LEFT,
        BOTH;
        
    }

}

