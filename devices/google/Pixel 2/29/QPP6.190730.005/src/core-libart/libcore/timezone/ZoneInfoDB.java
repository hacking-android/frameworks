/*
 * Decompiled with CFR 0.145.
 */
package libcore.timezone;

import android.system.ErrnoException;
import dalvik.annotation.optimization.ReachabilitySensitive;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import libcore.io.BufferIterator;
import libcore.io.MemoryMappedFile;
import libcore.timezone.TimeZoneDataFiles;
import libcore.util.BasicLruCache;
import libcore.util.ZoneInfo;

public final class ZoneInfoDB {
    private static final TzData DATA = TzData.loadTzDataWithFallback(TimeZoneDataFiles.getTimeZoneFilePaths("tzdata"));
    public static final String TZDATA_FILE = "tzdata";

    private ZoneInfoDB() {
    }

    public static TzData getInstance() {
        return DATA;
    }

    public static class TzData
    implements AutoCloseable {
        private static final int CACHE_SIZE = 1;
        public static final int SIZEOF_INDEX_ENTRY = 52;
        private static final int SIZEOF_TZINT = 4;
        private static final int SIZEOF_TZNAME = 40;
        private int[] byteOffsets;
        private final BasicLruCache<String, ZoneInfo> cache = new BasicLruCache<String, ZoneInfo>(1){

            @Override
            protected ZoneInfo create(String string) {
                try {
                    ZoneInfo zoneInfo = this.makeTimeZoneUncached(string);
                    return zoneInfo;
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unable to load timezone for ID=");
                    stringBuilder.append(string);
                    throw new IllegalStateException(stringBuilder.toString(), iOException);
                }
            }
        };
        private boolean closed;
        private String[] ids;
        @ReachabilitySensitive
        private MemoryMappedFile mappedFile;
        private int[] rawUtcOffsetsCache;
        private String version;
        private String zoneTab;

        private TzData() {
        }

        private void checkNotClosed() throws IllegalStateException {
            if (!this.closed) {
                return;
            }
            throw new IllegalStateException("TzData is closed");
        }

        private static TzData createFallback() {
            TzData tzData = new TzData();
            tzData.populateFallback();
            return tzData;
        }

        private int[] getRawUtcOffsets() {
            synchronized (this) {
                block7 : {
                    if (this.rawUtcOffsetsCache == null) break block7;
                    int[] arrn = this.rawUtcOffsetsCache;
                    return arrn;
                }
                this.rawUtcOffsetsCache = new int[this.ids.length];
                int n = 0;
                do {
                    if (n >= this.ids.length) break;
                    this.rawUtcOffsetsCache[n] = this.cache.get(this.ids[n]).getRawOffset();
                    ++n;
                } while (true);
                int[] arrn = this.rawUtcOffsetsCache;
                return arrn;
            }
        }

        private boolean loadData(String string) {
            try {
                this.mappedFile = MemoryMappedFile.mmapRO(string);
            }
            catch (ErrnoException errnoException) {
                return false;
            }
            try {
                this.readHeader();
                return true;
            }
            catch (Exception exception) {
                this.close();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("tzdata file \"");
                stringBuilder.append(string);
                stringBuilder.append("\" was present but invalid!");
                System.logE((String)stringBuilder.toString(), (Throwable)exception);
                return false;
            }
        }

        public static TzData loadTzData(String string) {
            TzData tzData = new TzData();
            if (tzData.loadData(string)) {
                return tzData;
            }
            return null;
        }

        public static TzData loadTzDataWithFallback(String ... arrstring) {
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                TzData tzData = new TzData();
                String string = arrstring[i];
                if (!tzData.loadData(string)) continue;
                return tzData;
            }
            System.logE((String)"Couldn't find any tzdata file!");
            return TzData.createFallback();
        }

        private void populateFallback() {
            this.version = "missing";
            this.zoneTab = "# Emergency fallback data.\n";
            this.ids = new String[]{"GMT"};
            int[] arrn = new int[1];
            this.rawUtcOffsetsCache = arrn;
            this.byteOffsets = arrn;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void readHeader() throws IOException {
            var1_1 = this.mappedFile.bigEndianIterator();
            try {
                var2_2 = new byte[12];
                var1_1.readByteArray(var2_2, 0, var2_2.length);
                var3_5 = new String(var2_2, 0, 6, StandardCharsets.US_ASCII);
            }
            catch (IndexOutOfBoundsException var2_4) {
                throw new IOException("Invalid read from data file", var2_4);
            }
            if (!var3_5.equals("tzdata") || var2_2[11] != 0) ** GOTO lbl42
            var3_5 = new String(var2_2, 6, 5, StandardCharsets.US_ASCII);
            this.version = var3_5;
            var4_6 = this.mappedFile.size();
            var5_7 = var1_1.readInt();
            TzData.validateOffset(var5_7, var4_6);
            var6_8 = var1_1.readInt();
            TzData.validateOffset(var6_8, var4_6);
            var7_9 = var1_1.readInt();
            TzData.validateOffset(var7_9, var4_6);
            if (var5_7 < var6_8 && var6_8 < var7_9) {
                this.readIndex((BufferIterator)var1_1, var5_7, var6_8);
                this.readZoneTab((BufferIterator)var1_1, var7_9, var4_6 - var7_9);
                return;
            }
            var1_1 = new StringBuilder();
            var1_1.append("Invalid offset: index_offset=");
            var1_1.append(var5_7);
            var1_1.append(", data_offset=");
            var1_1.append(var6_8);
            var1_1.append(", zonetab_offset=");
            var1_1.append(var7_9);
            var1_1.append(", fileSize=");
            var1_1.append(var4_6);
            var2_3 = new IOException(var1_1.toString());
            throw var2_3;
lbl42: // 1 sources:
            var3_5 = new StringBuilder();
            var3_5.append("bad tzdata magic: ");
            var3_5.append(Arrays.toString(var2_2));
            var1_1 = new IOException(var3_5.toString());
            throw var1_1;
        }

        private void readIndex(BufferIterator object, int n, int n2) throws IOException {
            ((BufferIterator)object).seek(n);
            byte[] arrby = new byte[40];
            n = n2 - n;
            if (n % 52 == 0) {
                int n3 = n / 52;
                this.byteOffsets = new int[n3];
                this.ids = new String[n3];
                for (n = 0; n < n3; ++n) {
                    ((BufferIterator)object).readByteArray(arrby, 0, arrby.length);
                    this.byteOffsets[n] = ((BufferIterator)object).readInt();
                    Object[] arrobject = this.byteOffsets;
                    arrobject[n] = arrobject[n] + n2;
                    if (((BufferIterator)object).readInt() >= 44) {
                        int n4;
                        ((BufferIterator)object).skip(4);
                        for (n4 = 0; arrby[n4] != 0 && n4 < arrby.length; ++n4) {
                        }
                        if (n4 != 0) {
                            this.ids[n] = new String(arrby, 0, n4, StandardCharsets.US_ASCII);
                            if (n <= 0 || (arrobject = this.ids)[n].compareTo((String)arrobject[n - 1]) > 0) continue;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Index not sorted or contains multiple entries with the same ID, index=");
                            ((StringBuilder)object).append(n);
                            ((StringBuilder)object).append(", ids[i]=");
                            ((StringBuilder)object).append(this.ids[n]);
                            ((StringBuilder)object).append(", ids[i - 1]=");
                            ((StringBuilder)object).append(this.ids[n - 1]);
                            throw new IOException(((StringBuilder)object).toString());
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid ID at index=");
                        ((StringBuilder)object).append(n);
                        throw new IOException(((StringBuilder)object).toString());
                    }
                    throw new IOException("length in index file < sizeof(tzhead)");
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Index size is not divisible by 52, indexSize=");
            ((StringBuilder)object).append(n);
            throw new IOException(((StringBuilder)object).toString());
        }

        private void readZoneTab(BufferIterator bufferIterator, int n, int n2) {
            byte[] arrby = new byte[n2];
            bufferIterator.seek(n);
            bufferIterator.readByteArray(arrby, 0, arrby.length);
            this.zoneTab = new String(arrby, 0, arrby.length, StandardCharsets.US_ASCII);
        }

        private static void validateOffset(int n, int n2) throws IOException {
            if (n >= 0 && n < n2) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid offset=");
            stringBuilder.append(n);
            stringBuilder.append(", size=");
            stringBuilder.append(n2);
            throw new IOException(stringBuilder.toString());
        }

        @Override
        public void close() {
            if (!this.closed) {
                this.closed = true;
                this.ids = null;
                this.byteOffsets = null;
                this.rawUtcOffsetsCache = null;
                this.cache.evictAll();
                MemoryMappedFile memoryMappedFile = this.mappedFile;
                if (memoryMappedFile != null) {
                    try {
                        memoryMappedFile.close();
                    }
                    catch (ErrnoException errnoException) {
                        // empty catch block
                    }
                    this.mappedFile = null;
                }
            }
        }

        protected void finalize() throws Throwable {
            try {
                this.close();
                return;
            }
            finally {
                super.finalize();
            }
        }

        public String[] getAvailableIDs() {
            this.checkNotClosed();
            return (String[])this.ids.clone();
        }

        public String[] getAvailableIDs(int n) {
            this.checkNotClosed();
            ArrayList<String> arrayList = new ArrayList<String>();
            int[] arrn = this.getRawUtcOffsets();
            for (int i = 0; i < arrn.length; ++i) {
                if (arrn[i] != n) continue;
                arrayList.add(this.ids[i]);
            }
            return arrayList.toArray(new String[arrayList.size()]);
        }

        public BufferIterator getBufferIterator(String object) {
            this.checkNotClosed();
            int n = Arrays.binarySearch(this.ids, object);
            if (n < 0) {
                return null;
            }
            n = this.byteOffsets[n];
            object = this.mappedFile.bigEndianIterator();
            ((BufferIterator)object).skip(n);
            return object;
        }

        public String getVersion() {
            this.checkNotClosed();
            return this.version;
        }

        public String getZoneTab() {
            this.checkNotClosed();
            return this.zoneTab;
        }

        public boolean hasTimeZone(String string) throws IOException {
            this.checkNotClosed();
            boolean bl = this.cache.get(string) != null;
            return bl;
        }

        public ZoneInfo makeTimeZone(String object) throws IOException {
            this.checkNotClosed();
            object = this.cache.get((String)object);
            object = object == null ? null : (ZoneInfo)((ZoneInfo)object).clone();
            return object;
        }

        ZoneInfo makeTimeZoneUncached(String string) throws IOException {
            BufferIterator bufferIterator = this.getBufferIterator(string);
            if (bufferIterator == null) {
                return null;
            }
            return ZoneInfo.readTimeZone(string, bufferIterator, System.currentTimeMillis());
        }

        public void validate() throws IOException {
            this.checkNotClosed();
            Object object = this.getAvailableIDs();
            int n = ((String[])object).length;
            for (int i = 0; i < n; ++i) {
                String string = object[i];
                if (this.makeTimeZoneUncached(string) != null) {
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to find data for ID=");
                ((StringBuilder)object).append(string);
                throw new IOException(((StringBuilder)object).toString());
            }
        }

    }

}

