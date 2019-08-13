/*
 * Decompiled with CFR 0.145.
 */
package android.metrics;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;

@SystemApi
public class LogMaker {
    @VisibleForTesting
    public static final int MAX_SERIALIZED_SIZE = 4000;
    private static final String TAG = "LogBuilder";
    private SparseArray<Object> entries = new SparseArray();

    public LogMaker(int n) {
        this.setCategory(n);
    }

    public LogMaker(Object[] arrobject) {
        if (arrobject != null) {
            this.deserialize(arrobject);
        } else {
            this.setCategory(0);
        }
    }

    public LogMaker addTaggedData(int n, Object object) {
        if (object == null) {
            return this.clearTaggedData(n);
        }
        if (this.isValidValue(object)) {
            if (object.toString().getBytes().length > 4000) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Log value too long, omitted: ");
                stringBuilder.append(object.toString());
                Log.i(TAG, stringBuilder.toString());
            } else {
                this.entries.put(n, object);
            }
            return this;
        }
        throw new IllegalArgumentException("Value must be loggable type - int, long, float, String");
    }

    public LogMaker clearCategory() {
        this.entries.remove(757);
        return this;
    }

    public LogMaker clearPackageName() {
        this.entries.remove(806);
        return this;
    }

    public LogMaker clearProcessId() {
        this.entries.remove(865);
        return this;
    }

    public LogMaker clearSubtype() {
        this.entries.remove(759);
        return this;
    }

    public LogMaker clearTaggedData(int n) {
        this.entries.delete(n);
        return this;
    }

    public LogMaker clearTimestamp() {
        this.entries.remove(805);
        return this;
    }

    public LogMaker clearType() {
        this.entries.remove(758);
        return this;
    }

    public LogMaker clearUid() {
        this.entries.remove(943);
        return this;
    }

    public void deserialize(Object[] arrobject) {
        int n = 0;
        while (arrobject != null && n < arrobject.length) {
            Object object;
            int n2 = n + 1;
            Object object2 = arrobject[n];
            if (n2 < arrobject.length) {
                n = n2 + 1;
                object = arrobject[n2];
            } else {
                n = n2;
                object = null;
            }
            if (object2 instanceof Integer) {
                this.entries.put((Integer)object2, object);
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid key ");
            object = object2 == null ? "null" : object2.toString();
            stringBuilder.append((String)object);
            Log.i(TAG, stringBuilder.toString());
        }
    }

    public int getCategory() {
        Object object = this.entries.get(757);
        if (object instanceof Integer) {
            return (Integer)object;
        }
        return 0;
    }

    public long getCounterBucket() {
        Object object = this.entries.get(801);
        if (object instanceof Number) {
            return ((Number)object).longValue();
        }
        return 0L;
    }

    public String getCounterName() {
        Object object = this.entries.get(799);
        if (object instanceof String) {
            return (String)object;
        }
        return null;
    }

    public int getCounterValue() {
        Object object = this.entries.get(802);
        if (object instanceof Integer) {
            return (Integer)object;
        }
        return 0;
    }

    public SparseArray<Object> getEntries() {
        return this.entries;
    }

    public String getPackageName() {
        Object object = this.entries.get(806);
        if (object instanceof String) {
            return (String)object;
        }
        return null;
    }

    public int getProcessId() {
        Object object = this.entries.get(865);
        if (object instanceof Integer) {
            return (Integer)object;
        }
        return -1;
    }

    public int getSubtype() {
        Object object = this.entries.get(759);
        if (object instanceof Integer) {
            return (Integer)object;
        }
        return 0;
    }

    public Object getTaggedData(int n) {
        return this.entries.get(n);
    }

    public long getTimestamp() {
        Object object = this.entries.get(805);
        if (object instanceof Long) {
            return (Long)object;
        }
        return 0L;
    }

    public int getType() {
        Object object = this.entries.get(758);
        if (object instanceof Integer) {
            return (Integer)object;
        }
        return 0;
    }

    public int getUid() {
        Object object = this.entries.get(943);
        if (object instanceof Integer) {
            return (Integer)object;
        }
        return -1;
    }

    public boolean isLongCounterBucket() {
        return this.entries.get(801) instanceof Long;
    }

    public boolean isSubsetOf(LogMaker logMaker) {
        if (logMaker == null) {
            return false;
        }
        for (int i = 0; i < this.entries.size(); ++i) {
            int n = this.entries.keyAt(i);
            Object object = this.entries.valueAt(i);
            Object object2 = logMaker.entries.get(n);
            if ((object != null || object2 == null) && object.equals(object2)) continue;
            return false;
        }
        return true;
    }

    public boolean isValidValue(Object object) {
        boolean bl = object instanceof Integer || object instanceof String || object instanceof Long || object instanceof Float;
        return bl;
    }

    public Object[] serialize() {
        int n;
        Object object = new Object[this.entries.size() * 2];
        for (n = 0; n < this.entries.size(); ++n) {
            object[n * 2] = this.entries.keyAt(n);
            object[n * 2 + 1] = this.entries.valueAt(n);
        }
        n = object.toString().getBytes().length;
        if (n <= 4000) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Log line too long, did not emit: ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" bytes.");
        Log.i(TAG, ((StringBuilder)object).toString());
        throw new RuntimeException();
    }

    public LogMaker setCategory(int n) {
        this.entries.put(757, n);
        return this;
    }

    public LogMaker setComponentName(ComponentName componentName) {
        this.entries.put(806, componentName.getPackageName());
        this.entries.put(871, componentName.getClassName());
        return this;
    }

    public LogMaker setCounterBucket(int n) {
        this.entries.put(801, n);
        return this;
    }

    public LogMaker setCounterBucket(long l) {
        this.entries.put(801, l);
        return this;
    }

    public LogMaker setCounterName(String string2) {
        this.entries.put(799, string2);
        return this;
    }

    public LogMaker setCounterValue(int n) {
        this.entries.put(802, n);
        return this;
    }

    public LogMaker setLatency(long l) {
        this.entries.put(1359, l);
        return this;
    }

    public LogMaker setPackageName(String string2) {
        this.entries.put(806, string2);
        return this;
    }

    public LogMaker setProcessId(int n) {
        this.entries.put(865, n);
        return this;
    }

    public LogMaker setSubtype(int n) {
        this.entries.put(759, n);
        return this;
    }

    public LogMaker setTimestamp(long l) {
        this.entries.put(805, l);
        return this;
    }

    public LogMaker setType(int n) {
        this.entries.put(758, n);
        return this;
    }

    public LogMaker setUid(int n) {
        this.entries.put(943, n);
        return this;
    }
}

