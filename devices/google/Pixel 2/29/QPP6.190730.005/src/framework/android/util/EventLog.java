/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventLog {
    private static final String COMMENT_PATTERN = "^\\s*(#.*)?$";
    private static final String TAG = "EventLog";
    private static final String TAGS_FILE = "/system/etc/event-log-tags";
    private static final String TAG_PATTERN = "^\\s*(\\d+)\\s+(\\w+)\\s*(\\(.*\\))?\\s*$";
    private static HashMap<String, Integer> sTagCodes = null;
    private static HashMap<Integer, String> sTagNames = null;

    public static int getTagCode(String object) {
        EventLog.readTagsFile();
        object = sTagCodes.get(object);
        int n = object != null ? (Integer)object : -1;
        return n;
    }

    public static String getTagName(int n) {
        EventLog.readTagsFile();
        return sTagNames.get(n);
    }

    public static native void readEvents(int[] var0, Collection<Event> var1) throws IOException;

    @SystemApi
    public static native void readEventsOnWrapping(int[] var0, long var1, Collection<Event> var3) throws IOException;

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static void readTagsFile() {
        Throwable throwable2222;
        Object object;
        block19 : {
            block20 : {
                // MONITORENTER : android.util.EventLog.class
                if (sTagCodes != null && (object = sTagNames) != null) {
                    // MONITOREXIT : android.util.EventLog.class
                    return;
                }
                object = new HashMap();
                sTagCodes = object;
                object = new HashMap();
                sTagNames = object;
                Pattern pattern = Pattern.compile("^\\s*(#.*)?$");
                Pattern pattern2 = Pattern.compile("^\\s*(\\d+)\\s+(\\w+)\\s*(\\(.*\\))?\\s*$");
                Object object2 = null;
                String string2 = null;
                object = string2;
                BufferedReader bufferedReader = object2;
                object = string2;
                bufferedReader = object2;
                object = string2;
                bufferedReader = object2;
                Object object3 = new FileReader("/system/etc/event-log-tags");
                object = string2;
                bufferedReader = object2;
                BufferedReader bufferedReader2 = new BufferedReader((Reader)object3, 256);
                do {
                    object = bufferedReader2;
                    bufferedReader = bufferedReader2;
                    string2 = bufferedReader2.readLine();
                    if (string2 == null) break;
                    object = bufferedReader2;
                    bufferedReader = bufferedReader2;
                    if (pattern.matcher(string2).matches()) continue;
                    object = bufferedReader2;
                    bufferedReader = bufferedReader2;
                    object2 = pattern2.matcher(string2);
                    object = bufferedReader2;
                    bufferedReader = bufferedReader2;
                    if (!((Matcher)object2).matches()) {
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        object2 = new StringBuilder();
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        ((StringBuilder)object2).append("Bad entry in /system/etc/event-log-tags: ");
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        ((StringBuilder)object2).append(string2);
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        Log.wtf("EventLog", ((StringBuilder)object2).toString());
                        continue;
                    }
                    object = bufferedReader2;
                    bufferedReader = bufferedReader2;
                    try {
                        int n = Integer.parseInt(((Matcher)object2).group(1));
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        object2 = ((Matcher)object2).group(2);
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        sTagCodes.put((String)object2, n);
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        sTagNames.put(n, (String)object2);
                    }
                    catch (NumberFormatException numberFormatException) {
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        object3 = new StringBuilder();
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        ((StringBuilder)object3).append("Error in /system/etc/event-log-tags: ");
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        ((StringBuilder)object3).append(string2);
                        object = bufferedReader2;
                        bufferedReader = bufferedReader2;
                        Log.wtf("EventLog", ((StringBuilder)object3).toString(), numberFormatException);
                    }
                } while (true);
                try {
                    bufferedReader2.close();
                    return;
                }
                catch (IOException iOException) {
                    return;
                }
                {
                    catch (Throwable throwable2222) {
                        break block19;
                    }
                    catch (IOException iOException) {}
                    object = bufferedReader;
                    {
                        Log.wtf("EventLog", "Error reading /system/etc/event-log-tags", iOException);
                        if (bufferedReader == null) break block20;
                    }
                    try {
                        bufferedReader.close();
                        return;
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
            }
            // MONITOREXIT : android.util.EventLog.class
            return;
        }
        if (object == null) throw throwable2222;
        try {
            ((BufferedReader)object).close();
            throw throwable2222;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        throw throwable2222;
    }

    public static native int writeEvent(int var0, float var1);

    public static native int writeEvent(int var0, int var1);

    public static native int writeEvent(int var0, long var1);

    public static native int writeEvent(int var0, String var1);

    public static native int writeEvent(int var0, Object ... var1);

    public static final class Event {
        private static final int DATA_OFFSET = 4;
        private static final byte FLOAT_TYPE = 4;
        private static final int HEADER_SIZE_OFFSET = 2;
        private static final byte INT_TYPE = 0;
        private static final int LENGTH_OFFSET = 0;
        private static final byte LIST_TYPE = 3;
        private static final byte LONG_TYPE = 1;
        private static final int NANOSECONDS_OFFSET = 16;
        private static final int PROCESS_OFFSET = 4;
        private static final int SECONDS_OFFSET = 12;
        private static final byte STRING_TYPE = 2;
        private static final int THREAD_OFFSET = 8;
        private static final int UID_OFFSET = 24;
        private static final int V1_PAYLOAD_START = 20;
        private final ByteBuffer mBuffer;
        private Exception mLastWtf;

        @UnsupportedAppUsage
        Event(byte[] arrby) {
            this.mBuffer = ByteBuffer.wrap(arrby);
            this.mBuffer.order(ByteOrder.nativeOrder());
        }

        private Object decodeObject() {
            int n = this.mBuffer.get();
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n == 4) {
                                return Float.valueOf(this.mBuffer.getFloat());
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown entry type: ");
                            stringBuilder.append(n);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        int n2 = this.mBuffer.get();
                        n = n2;
                        if (n2 < 0) {
                            n = n2 + 256;
                        }
                        Object[] arrobject = new Object[n];
                        for (n2 = 0; n2 < n; ++n2) {
                            arrobject[n2] = this.decodeObject();
                        }
                        return arrobject;
                    }
                    try {
                        n = this.mBuffer.getInt();
                        int n3 = this.mBuffer.position();
                        this.mBuffer.position(n3 + n);
                        String string2 = new String(this.mBuffer.array(), n3, n, "UTF-8");
                        return string2;
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        Log.wtf(EventLog.TAG, "UTF-8 is not supported", unsupportedEncodingException);
                        this.mLastWtf = unsupportedEncodingException;
                        return null;
                    }
                }
                return this.mBuffer.getLong();
            }
            return this.mBuffer.getInt();
        }

        public static Event fromBytes(byte[] arrby) {
            return new Event(arrby);
        }

        public void clearError() {
            this.mLastWtf = null;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (Event)object;
                return Arrays.equals(this.mBuffer.array(), ((Event)object).mBuffer.array());
            }
            return false;
        }

        public byte[] getBytes() {
            byte[] arrby = this.mBuffer.array();
            return Arrays.copyOf(arrby, arrby.length);
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public Object getData() {
            int n;
            // MONITORENTER : this
            int n2 = n = this.mBuffer.getShort(2);
            if (n == 0) {
                n2 = 20;
            }
            this.mBuffer.limit(this.mBuffer.getShort(0) + n2);
            n = this.mBuffer.limit();
            if (n2 + 4 >= n) {
                // MONITOREXIT : this
                return null;
            }
            this.mBuffer.position(n2 + 4);
            Object object = this.decodeObject();
            // MONITOREXIT : this
            return object;
            catch (BufferUnderflowException bufferUnderflowException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Truncated entry payload: tag=");
                stringBuilder.append(this.getTag());
                Log.wtf(EventLog.TAG, stringBuilder.toString(), bufferUnderflowException);
                this.mLastWtf = bufferUnderflowException;
                // MONITOREXIT : this
                return null;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Illegal entry payload: tag=");
                stringBuilder.append(this.getTag());
                Log.wtf(EventLog.TAG, stringBuilder.toString(), illegalArgumentException);
                this.mLastWtf = illegalArgumentException;
                // MONITOREXIT : this
                return null;
            }
        }

        public Exception getLastError() {
            return this.mLastWtf;
        }

        public int getProcessId() {
            return this.mBuffer.getInt(4);
        }

        public int getTag() {
            int n;
            int n2 = n = this.mBuffer.getShort(2);
            if (n == 0) {
                n2 = 20;
            }
            return this.mBuffer.getInt(n2);
        }

        public int getThreadId() {
            return this.mBuffer.getInt(8);
        }

        public long getTimeNanos() {
            return (long)this.mBuffer.getInt(12) * 1000000000L + (long)this.mBuffer.getInt(16);
        }

        @SystemApi
        public int getUid() {
            try {
                int n = this.mBuffer.getInt(24);
                return n;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                return -1;
            }
        }

        public int hashCode() {
            return Arrays.hashCode(this.mBuffer.array());
        }
    }

}

