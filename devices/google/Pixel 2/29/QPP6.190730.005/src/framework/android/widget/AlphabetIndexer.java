/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.util.SparseIntArray;
import android.widget.SectionIndexer;
import java.text.Collator;

public class AlphabetIndexer
extends DataSetObserver
implements SectionIndexer {
    private SparseIntArray mAlphaMap;
    protected CharSequence mAlphabet;
    private String[] mAlphabetArray;
    private int mAlphabetLength;
    private Collator mCollator;
    protected int mColumnIndex;
    protected Cursor mDataCursor;

    public AlphabetIndexer(Cursor cursor, int n, CharSequence charSequence) {
        int n2;
        this.mDataCursor = cursor;
        this.mColumnIndex = n;
        this.mAlphabet = charSequence;
        this.mAlphabetLength = charSequence.length();
        this.mAlphabetArray = new String[this.mAlphabetLength];
        for (n = 0; n < (n2 = this.mAlphabetLength); ++n) {
            this.mAlphabetArray[n] = Character.toString(this.mAlphabet.charAt(n));
        }
        this.mAlphaMap = new SparseIntArray(n2);
        if (cursor != null) {
            cursor.registerDataSetObserver(this);
        }
        this.mCollator = Collator.getInstance();
        this.mCollator.setStrength(0);
    }

    protected int compare(String string2, String string3) {
        string2 = string2.length() == 0 ? " " : string2.substring(0, 1);
        return this.mCollator.compare(string2, string3);
    }

    @Override
    public int getPositionForSection(int n) {
        SparseIntArray sparseIntArray = this.mAlphaMap;
        Cursor cursor = this.mDataCursor;
        if (cursor != null && this.mAlphabet != null) {
            if (n <= 0) {
                return 0;
            }
            int n2 = this.mAlphabetLength;
            int n3 = n;
            if (n >= n2) {
                n3 = n2 - 1;
            }
            int n4 = cursor.getPosition();
            int n5 = cursor.getCount();
            n = 0;
            n2 = n5;
            char c = this.mAlphabet.charAt(n3);
            String string2 = Character.toString(c);
            int n6 = sparseIntArray.get(c, Integer.MIN_VALUE);
            if (Integer.MIN_VALUE != n6) {
                if (n6 < 0) {
                    n2 = -n6;
                } else {
                    return n6;
                }
            }
            n6 = n;
            if (n3 > 0) {
                n3 = sparseIntArray.get(this.mAlphabet.charAt(n3 - 1), Integer.MIN_VALUE);
                n6 = n;
                if (n3 != Integer.MIN_VALUE) {
                    n6 = Math.abs(n3);
                }
            }
            n = (n2 + n6) / 2;
            do {
                n3 = --n;
                if (n >= n2) break;
                cursor.moveToPosition(n);
                String string3 = cursor.getString(this.mColumnIndex);
                if (string3 == null) {
                    if (n != 0) continue;
                    n3 = n;
                    break;
                }
                n3 = this.compare(string3, string2);
                if (n3 != 0) {
                    if (n3 < 0) {
                        n6 = n3 = n + 1;
                        n = n2;
                        if (n3 >= n5) {
                            n3 = n5;
                            break;
                        }
                    }
                } else if (n6 == n) {
                    n3 = n;
                    break;
                }
                n3 = (n6 + n) / 2;
                n2 = n;
                n = n3;
            } while (true);
            sparseIntArray.put(c, n3);
            cursor.moveToPosition(n4);
            return n3;
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int n) {
        int n2 = this.mDataCursor.getPosition();
        this.mDataCursor.moveToPosition(n);
        String string2 = this.mDataCursor.getString(this.mColumnIndex);
        this.mDataCursor.moveToPosition(n2);
        for (n = 0; n < this.mAlphabetLength; ++n) {
            if (this.compare(string2, Character.toString(this.mAlphabet.charAt(n))) != 0) continue;
            return n;
        }
        return 0;
    }

    @Override
    public Object[] getSections() {
        return this.mAlphabetArray;
    }

    @Override
    public void onChanged() {
        super.onChanged();
        this.mAlphaMap.clear();
    }

    @Override
    public void onInvalidated() {
        super.onInvalidated();
        this.mAlphaMap.clear();
    }

    public void setCursor(Cursor cursor) {
        Cursor cursor2 = this.mDataCursor;
        if (cursor2 != null) {
            cursor2.unregisterDataSetObserver(this);
        }
        this.mDataCursor = cursor;
        if (cursor != null) {
            this.mDataCursor.registerDataSetObserver(this);
        }
        this.mAlphaMap.clear();
    }
}

