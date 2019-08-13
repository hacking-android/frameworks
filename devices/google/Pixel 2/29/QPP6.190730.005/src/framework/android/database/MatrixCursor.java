/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.annotation.UnsupportedAppUsage;
import android.database.AbstractCursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.DatabaseUtils;
import java.io.Serializable;
import java.util.ArrayList;

public class MatrixCursor
extends AbstractCursor {
    private final int columnCount;
    private final String[] columnNames;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private Object[] data;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int rowCount = 0;

    public MatrixCursor(String[] arrstring) {
        this(arrstring, 16);
    }

    public MatrixCursor(String[] arrstring, int n) {
        this.columnNames = arrstring;
        this.columnCount = arrstring.length;
        int n2 = n;
        if (n < 1) {
            n2 = 1;
        }
        this.data = new Object[this.columnCount * n2];
    }

    private void addRow(ArrayList<?> serializable, int n) {
        int n2 = ((ArrayList)serializable).size();
        if (n2 == this.columnCount) {
            ++this.rowCount;
            Object[] arrobject = this.data;
            for (int i = 0; i < n2; ++i) {
                arrobject[n + i] = ((ArrayList)serializable).get(i);
            }
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("columnNames.length = ");
        ((StringBuilder)serializable).append(this.columnCount);
        ((StringBuilder)serializable).append(", columnValues.size() = ");
        ((StringBuilder)serializable).append(n2);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    private void ensureCapacity(int n) {
        Object[] arrobject = this.data;
        if (n > arrobject.length) {
            int n2;
            Object[] arrobject2 = this.data;
            int n3 = n2 = arrobject.length * 2;
            if (n2 < n) {
                n3 = n;
            }
            this.data = new Object[n3];
            System.arraycopy(arrobject2, 0, this.data, 0, arrobject2.length);
        }
    }

    @UnsupportedAppUsage
    private Object get(int n) {
        if (n >= 0 && n < this.columnCount) {
            if (this.mPos >= 0) {
                if (this.mPos < this.rowCount) {
                    return this.data[this.mPos * this.columnCount + n];
                }
                throw new CursorIndexOutOfBoundsException("After last row.");
            }
            throw new CursorIndexOutOfBoundsException("Before first row.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requested column: ");
        stringBuilder.append(n);
        stringBuilder.append(", # of columns: ");
        stringBuilder.append(this.columnCount);
        throw new CursorIndexOutOfBoundsException(stringBuilder.toString());
    }

    public void addRow(Iterable<?> iterable2) {
        int n = this.rowCount;
        int n2 = this.columnCount;
        n *= n2;
        this.ensureCapacity(n2 += n);
        if (iterable2 instanceof ArrayList) {
            this.addRow((ArrayList)iterable2, n);
            return;
        }
        Object[] arrobject = this.data;
        for (Iterable<?> iterable2 : iterable2) {
            if (n != n2) {
                arrobject[n] = iterable2;
                ++n;
                continue;
            }
            throw new IllegalArgumentException("columnValues.size() > columnNames.length");
        }
        if (n == n2) {
            ++this.rowCount;
            return;
        }
        throw new IllegalArgumentException("columnValues.size() < columnNames.length");
    }

    public void addRow(Object[] arrobject) {
        int n = arrobject.length;
        int n2 = this.columnCount;
        if (n == n2) {
            n = this.rowCount;
            this.rowCount = n + 1;
            this.ensureCapacity(n2 + (n *= n2));
            System.arraycopy(arrobject, 0, this.data, n, this.columnCount);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("columnNames.length = ");
        stringBuilder.append(this.columnCount);
        stringBuilder.append(", columnValues.length = ");
        stringBuilder.append(arrobject.length);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public byte[] getBlob(int n) {
        return (byte[])this.get(n);
    }

    @Override
    public String[] getColumnNames() {
        return this.columnNames;
    }

    @Override
    public int getCount() {
        return this.rowCount;
    }

    @Override
    public double getDouble(int n) {
        Object object = this.get(n);
        if (object == null) {
            return 0.0;
        }
        if (object instanceof Number) {
            return ((Number)object).doubleValue();
        }
        return Double.parseDouble(object.toString());
    }

    @Override
    public float getFloat(int n) {
        Object object = this.get(n);
        if (object == null) {
            return 0.0f;
        }
        if (object instanceof Number) {
            return ((Number)object).floatValue();
        }
        return Float.parseFloat(object.toString());
    }

    @Override
    public int getInt(int n) {
        Object object = this.get(n);
        if (object == null) {
            return 0;
        }
        if (object instanceof Number) {
            return ((Number)object).intValue();
        }
        return Integer.parseInt(object.toString());
    }

    @Override
    public long getLong(int n) {
        Object object = this.get(n);
        if (object == null) {
            return 0L;
        }
        if (object instanceof Number) {
            return ((Number)object).longValue();
        }
        return Long.parseLong(object.toString());
    }

    @Override
    public short getShort(int n) {
        Object object = this.get(n);
        if (object == null) {
            return 0;
        }
        if (object instanceof Number) {
            return ((Number)object).shortValue();
        }
        return Short.parseShort(object.toString());
    }

    @Override
    public String getString(int n) {
        Object object = this.get(n);
        if (object == null) {
            return null;
        }
        return object.toString();
    }

    @Override
    public int getType(int n) {
        return DatabaseUtils.getTypeOfObject(this.get(n));
    }

    @Override
    public boolean isNull(int n) {
        boolean bl = this.get(n) == null;
        return bl;
    }

    public RowBuilder newRow() {
        int n = this.rowCount;
        this.rowCount = n + 1;
        this.ensureCapacity(this.rowCount * this.columnCount);
        return new RowBuilder(n);
    }

    public class RowBuilder {
        private final int endIndex;
        private int index;
        private final int row;

        RowBuilder(int n) {
            this.row = n;
            this.index = MatrixCursor.this.columnCount * n;
            this.endIndex = this.index + MatrixCursor.this.columnCount;
        }

        public final RowBuilder add(int n, Object object) {
            MatrixCursor.access$100((MatrixCursor)MatrixCursor.this)[this.row * MatrixCursor.access$000((MatrixCursor)MatrixCursor.this) + n] = object;
            return this;
        }

        public RowBuilder add(Object object) {
            if (this.index != this.endIndex) {
                Object[] arrobject = MatrixCursor.this.data;
                int n = this.index;
                this.index = n + 1;
                arrobject[n] = object;
                return this;
            }
            throw new CursorIndexOutOfBoundsException("No more columns left.");
        }

        public RowBuilder add(String string2, Object object) {
            for (int i = 0; i < MatrixCursor.this.columnNames.length; ++i) {
                if (!string2.equals(MatrixCursor.this.columnNames[i])) continue;
                MatrixCursor.access$100((MatrixCursor)MatrixCursor.this)[this.row * MatrixCursor.access$000((MatrixCursor)MatrixCursor.this) + i] = object;
            }
            return this;
        }
    }

}

