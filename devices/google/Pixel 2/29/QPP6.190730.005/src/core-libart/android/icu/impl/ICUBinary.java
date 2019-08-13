/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ClassLoaderUtil;
import android.icu.impl.ICUConfig;
import android.icu.impl.ICUData;
import android.icu.util.ICUUncheckedIOException;
import android.icu.util.VersionInfo;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class ICUBinary {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte CHAR_SET_ = 0;
    private static final byte CHAR_SIZE_ = 2;
    private static final String HEADER_AUTHENTICATION_FAILED_ = "ICU data file error: Header authentication failed, please check if you have a valid ICU data file";
    private static final byte MAGIC1 = -38;
    private static final byte MAGIC2 = 39;
    private static final String MAGIC_NUMBER_AUTHENTICATION_FAILED_ = "ICU data file error: Not an ICU data file";
    private static final List<DataFile> icuDataFiles = new ArrayList<DataFile>();

    static {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(ICUBinary.class.getName());
        charSequence.append(".dataPath");
        charSequence = ICUConfig.get(charSequence.toString());
        if (charSequence != null) {
            ICUBinary.addDataFilesFromPath((String)charSequence, icuDataFiles);
        }
    }

    public static void addBaseNamesInFileFolder(String string, String string2, Set<String> set) {
        Iterator<DataFile> iterator = icuDataFiles.iterator();
        while (iterator.hasNext()) {
            iterator.next().addBaseNamesInFolder(string, string2, set);
        }
    }

    private static void addDataFilesFromFolder(File arrfile, StringBuilder stringBuilder, List<DataFile> list) {
        if ((arrfile = arrfile.listFiles()) != null && arrfile.length != 0) {
            int n;
            int n2 = n = stringBuilder.length();
            if (n > 0) {
                stringBuilder.append('/');
                n2 = n + 1;
            }
            for (File file : arrfile) {
                Object object = file.getName();
                if (((String)object).endsWith(".txt")) continue;
                stringBuilder.append((String)object);
                if (file.isDirectory()) {
                    ICUBinary.addDataFilesFromFolder(file, stringBuilder, list);
                } else if (((String)object).endsWith(".dat")) {
                    object = ICUBinary.mapFile(file);
                    if (object != null && DatPackageReader.validate((ByteBuffer)object)) {
                        list.add(new PackageDataFile(stringBuilder.toString(), (ByteBuffer)object));
                    }
                } else {
                    list.add(new SingleDataFile(stringBuilder.toString(), file));
                }
                stringBuilder.setLength(n2);
            }
            return;
        }
    }

    private static void addDataFilesFromPath(String string, List<DataFile> object) {
        int n = 0;
        while (n < string.length()) {
            int n2 = string.indexOf(File.pathSeparatorChar, n);
            int n3 = n2 >= 0 ? n2 : string.length();
            String string2 = string.substring(n, n3).trim();
            object = string2;
            if (string2.endsWith(File.separator)) {
                object = string2.substring(0, string2.length() - 1);
            }
            if (((String)object).length() != 0) {
                ICUBinary.addDataFilesFromFolder(new File((String)object), new StringBuilder(), icuDataFiles);
            }
            if (n2 < 0) break;
            n = n2 + 1;
        }
    }

    static int compareKeys(CharSequence charSequence, ByteBuffer byteBuffer, int n) {
        int n2 = 0;
        int n3;
        while ((n3 = byteBuffer.get(n)) != 0) {
            if (n2 == charSequence.length()) {
                return -1;
            }
            n3 = charSequence.charAt(n2) - n3;
            if (n3 != 0) {
                return n3;
            }
            ++n2;
            ++n;
        }
        return n2 != charSequence.length();
    }

    static int compareKeys(CharSequence charSequence, byte[] arrby, int n) {
        int n2 = 0;
        int n3;
        while ((n3 = arrby[n]) != 0) {
            if (n2 == charSequence.length()) {
                return -1;
            }
            n3 = charSequence.charAt(n2) - n3;
            if (n3 != 0) {
                return n3;
            }
            ++n2;
            ++n;
        }
        return n2 != charSequence.length();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static ByteBuffer getByteBufferFromInputStreamAndCloseStream(InputStream var0) throws IOException {
        try {
            var1_1 = var0.available();
            if (var1_1 > 32) {
                var2_2 = new byte[var1_1];
            } else {
                var2_3 = new byte[128];
            }
            var1_1 = 0;
            do lbl-1000: // 3 sources:
            {
                if (var1_1 < ((void)var2_5).length) {
                    var3_9 = var0.read((byte[])var2_5, var1_1, ((void)var2_5).length - var1_1);
                    if (var3_9 >= 0) {
                        var1_1 += var3_9;
                        continue;
                    }
                } else {
                    var4_10 = var0.read();
                    if (var4_10 >= 0) break block11;
                }
                var2_6 = ByteBuffer.wrap((byte[])var2_5, 0, var1_1);
                break;
            } while (true);
        }
        catch (Throwable var2_8) {
            var0.close();
            throw var2_8;
        }
        {
            block12 : {
                block11 : {
                    var0.close();
                    return var2_6;
                }
                var5_11 = ((void)var2_5).length * 2;
                if (var5_11 < 128) {
                    var3_9 = 128;
                    break block12;
                }
                var3_9 = var5_11;
                if (var5_11 >= 16384) break block12;
                var3_9 = var5_11 * 2;
            }
            var2_7 = Arrays.copyOf((byte[])var2_5, var3_9);
            var2_7[var1_1] = (byte)var4_10;
            ++var1_1;
            ** while (true)
        }
    }

    public static byte[] getBytes(ByteBuffer byteBuffer, int n, int n2) {
        byte[] arrby = new byte[n];
        byteBuffer.get(arrby);
        if (n2 > 0) {
            ICUBinary.skipBytes(byteBuffer, n2);
        }
        return arrby;
    }

    public static char[] getChars(ByteBuffer byteBuffer, int n, int n2) {
        char[] arrc = new char[n];
        byteBuffer.asCharBuffer().get(arrc);
        ICUBinary.skipBytes(byteBuffer, n * 2 + n2);
        return arrc;
    }

    public static ByteBuffer getData(ClassLoader classLoader, String string, String string2) {
        return ICUBinary.getData(classLoader, string, string2, false);
    }

    private static ByteBuffer getData(ClassLoader object, String string, String string2, boolean bl) {
        block6 : {
            Object object2 = ICUBinary.getDataFromFile(string2);
            if (object2 != null) {
                return object2;
            }
            object2 = object;
            if (object == null) {
                object2 = ClassLoaderUtil.getClassLoader(ICUData.class);
            }
            object = string;
            if (string == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("android/icu/impl/data/icudt63b/");
                ((StringBuilder)object).append(string2);
                object = ((StringBuilder)object).toString();
            }
            try {
                object = ICUData.getStream((ClassLoader)object2, (String)object, bl);
                if (object != null) break block6;
                return null;
            }
            catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }
        object = ICUBinary.getByteBufferFromInputStreamAndCloseStream((InputStream)object);
        return object;
    }

    public static ByteBuffer getData(String string) {
        return ICUBinary.getData(null, null, string, false);
    }

    private static ByteBuffer getDataFromFile(String string) {
        Iterator<DataFile> iterator = icuDataFiles.iterator();
        while (iterator.hasNext()) {
            ByteBuffer byteBuffer = iterator.next().getData(string);
            if (byteBuffer == null) continue;
            return byteBuffer;
        }
        return null;
    }

    public static int[] getInts(ByteBuffer byteBuffer, int n, int n2) {
        int[] arrn = new int[n];
        byteBuffer.asIntBuffer().get(arrn);
        ICUBinary.skipBytes(byteBuffer, n * 4 + n2);
        return arrn;
    }

    public static long[] getLongs(ByteBuffer byteBuffer, int n, int n2) {
        long[] arrl = new long[n];
        byteBuffer.asLongBuffer().get(arrl);
        ICUBinary.skipBytes(byteBuffer, n * 8 + n2);
        return arrl;
    }

    public static ByteBuffer getRequiredData(String string) {
        return ICUBinary.getData(null, null, string, true);
    }

    public static short[] getShorts(ByteBuffer byteBuffer, int n, int n2) {
        short[] arrs = new short[n];
        byteBuffer.asShortBuffer().get(arrs);
        ICUBinary.skipBytes(byteBuffer, n * 2 + n2);
        return arrs;
    }

    public static String getString(ByteBuffer byteBuffer, int n, int n2) {
        String string = byteBuffer.asCharBuffer().subSequence(0, n).toString();
        ICUBinary.skipBytes(byteBuffer, n * 2 + n2);
        return string;
    }

    public static byte[] getVersionByteArrayFromCompactInt(int n) {
        return new byte[]{(byte)(n >> 24), (byte)(n >> 16), (byte)(n >> 8), (byte)n};
    }

    public static VersionInfo getVersionInfoFromCompactInt(int n) {
        return VersionInfo.getInstance(n >>> 24, n >> 16 & 255, n >> 8 & 255, n & 255);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static ByteBuffer mapFile(File object) {
        FileInputStream fileInputStream = new FileInputStream((File)object);
        object = fileInputStream.getChannel();
        object = ((FileChannel)object).map(FileChannel.MapMode.READ_ONLY, 0L, ((FileChannel)object).size());
        {
            catch (Throwable throwable) {
                fileInputStream.close();
                throw throwable;
            }
        }
        try {
            fileInputStream.close();
            return object;
        }
        catch (IOException iOException) {
            System.err.println(iOException);
            return null;
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.err.println(fileNotFoundException);
        }
        return null;
    }

    public static int readHeader(ByteBuffer byteBuffer, int n, Authenticate object) throws IOException {
        char c = byteBuffer.get(2);
        char c2 = byteBuffer.get(3);
        if (c == '\uffffffda' && c2 == '\'') {
            c = byteBuffer.get(8);
            byte by = byteBuffer.get(9);
            c2 = byteBuffer.get(10);
            if (c >= '\u0000' && '\u0001' >= c && by == 0 && c2 == '\u0002') {
                Object object2 = c != '\u0000' ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
                byteBuffer.order((ByteOrder)object2);
                c2 = byteBuffer.getChar(0);
                c = byteBuffer.getChar(4);
                if (c >= '\u0014' && c2 >= c + 4) {
                    object2 = new byte[]{byteBuffer.get(16), byteBuffer.get(17), byteBuffer.get(18), byteBuffer.get(19)};
                    if (byteBuffer.get(12) == (byte)(n >> 24) && byteBuffer.get(13) == (byte)(n >> 16) && byteBuffer.get(14) == (byte)(n >> 8) && byteBuffer.get(15) == (byte)n && (object == null || object.isDataVersionAcceptable((byte[])object2))) {
                        byteBuffer.position(c2);
                        return byteBuffer.get(20) << 24 | (byteBuffer.get(21) & 255) << 16 | (byteBuffer.get(22) & 255) << 8 | byteBuffer.get(23) & 255;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append(HEADER_AUTHENTICATION_FAILED_);
                    ((StringBuilder)object).append(String.format("; data format %02x%02x%02x%02x, format version %d.%d.%d.%d", byteBuffer.get(12), byteBuffer.get(13), byteBuffer.get(14), byteBuffer.get(15), object2[0] & 255, object2[1] & 255, object2[2] & 255, object2[3] & 255));
                    throw new IOException(((StringBuilder)object).toString());
                }
                throw new IOException("Internal Error: Header size error");
            }
            throw new IOException(HEADER_AUTHENTICATION_FAILED_);
        }
        throw new IOException(MAGIC_NUMBER_AUTHENTICATION_FAILED_);
    }

    public static VersionInfo readHeaderAndDataVersion(ByteBuffer byteBuffer, int n, Authenticate authenticate) throws IOException {
        return ICUBinary.getVersionInfoFromCompactInt(ICUBinary.readHeader(byteBuffer, n, authenticate));
    }

    public static void skipBytes(ByteBuffer byteBuffer, int n) {
        if (n > 0) {
            byteBuffer.position(byteBuffer.position() + n);
        }
    }

    public static ByteBuffer sliceWithOrder(ByteBuffer byteBuffer) {
        return byteBuffer.slice().order(byteBuffer.order());
    }

    public static int writeHeader(int n, int n2, int n3, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeChar(32);
        dataOutputStream.writeByte(-38);
        dataOutputStream.writeByte(39);
        dataOutputStream.writeChar(20);
        dataOutputStream.writeChar(0);
        dataOutputStream.writeByte(1);
        dataOutputStream.writeByte(0);
        dataOutputStream.writeByte(2);
        dataOutputStream.writeByte(0);
        dataOutputStream.writeInt(n);
        dataOutputStream.writeInt(n2);
        dataOutputStream.writeInt(n3);
        dataOutputStream.writeLong(0L);
        return 32;
    }

    public static interface Authenticate {
        public boolean isDataVersionAcceptable(byte[] var1);
    }

    private static final class DatPackageReader {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int DATA_FORMAT = 1131245124;
        private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();

        private DatPackageReader() {
        }

        static boolean addBaseName(ByteBuffer byteBuffer, int n, String string, String string2, StringBuilder stringBuilder, Set<String> set) {
            int n2;
            n = n2 = DatPackageReader.getNameOffset(byteBuffer, n) + ("icudt63b".length() + 1);
            if (string.length() != 0) {
                n = 0;
                while (n < string.length()) {
                    if (byteBuffer.get(n2) != string.charAt(n)) {
                        return false;
                    }
                    ++n;
                    ++n2;
                }
                if (byteBuffer.get(n2) != 47) {
                    return false;
                }
                n = n2 + 1;
            }
            stringBuilder.setLength(0);
            while ((n2 = (int)byteBuffer.get(n)) != 0) {
                char c = (char)n2;
                if (c == '/') {
                    return true;
                }
                stringBuilder.append(c);
                ++n;
            }
            n = stringBuilder.length() - string2.length();
            if (stringBuilder.lastIndexOf(string2, n) >= 0) {
                set.add(stringBuilder.substring(0, n));
            }
            return true;
        }

        static void addBaseNamesInFolder(ByteBuffer byteBuffer, String string, String string2, Set<String> set) {
            int n;
            int n2 = n = DatPackageReader.binarySearch(byteBuffer, string);
            if (n < 0) {
                n2 = n;
            }
            n = byteBuffer.getInt(byteBuffer.position());
            StringBuilder stringBuilder = new StringBuilder();
            while (n2 < n && DatPackageReader.addBaseName(byteBuffer, n2, string, string2, stringBuilder, set)) {
                ++n2;
            }
        }

        private static int binarySearch(ByteBuffer byteBuffer, CharSequence charSequence) {
            int n = byteBuffer.getInt(byteBuffer.position());
            int n2 = 0;
            while (n2 < n) {
                int n3 = n2 + n >>> 1;
                int n4 = ICUBinary.compareKeys(charSequence, byteBuffer, DatPackageReader.getNameOffset(byteBuffer, n3) + ("icudt63b".length() + 1));
                if (n4 < 0) {
                    n = n3;
                    continue;
                }
                if (n4 > 0) {
                    n2 = n3 + 1;
                    continue;
                }
                return n3;
            }
            return n2;
        }

        static ByteBuffer getData(ByteBuffer byteBuffer, CharSequence object) {
            int n = DatPackageReader.binarySearch(byteBuffer, (CharSequence)object);
            if (n >= 0) {
                object = byteBuffer.duplicate();
                ((Buffer)object).position(DatPackageReader.getDataOffset(byteBuffer, n));
                ((Buffer)object).limit(DatPackageReader.getDataOffset(byteBuffer, n + 1));
                return ICUBinary.sliceWithOrder((ByteBuffer)object);
            }
            return null;
        }

        private static int getDataOffset(ByteBuffer byteBuffer, int n) {
            int n2 = byteBuffer.position();
            if (n == byteBuffer.getInt(n2)) {
                return byteBuffer.capacity();
            }
            return byteBuffer.getInt(n2 + 4 + 4 + n * 8) + n2;
        }

        private static int getNameOffset(ByteBuffer byteBuffer, int n) {
            int n2 = byteBuffer.position();
            return byteBuffer.getInt(n2 + 4 + n * 8) + n2;
        }

        private static boolean startsWithPackageName(ByteBuffer byteBuffer, int n) {
            int n2;
            int n3 = "icudt63b".length() - 1;
            for (n2 = 0; n2 < n3; ++n2) {
                if (byteBuffer.get(n + n2) == "icudt63b".charAt(n2)) continue;
                return false;
            }
            n2 = byteBuffer.get(n3 + n);
            return (n2 == 98 || n2 == 108) && byteBuffer.get(n + (n3 + 1)) == 47;
        }

        static boolean validate(ByteBuffer byteBuffer) {
            int n;
            block4 : {
                try {
                    ICUBinary.readHeader(byteBuffer, 1131245124, IS_ACCEPTABLE);
                    n = byteBuffer.getInt(byteBuffer.position());
                    if (n > 0) break block4;
                    return false;
                }
                catch (IOException iOException) {
                    return false;
                }
            }
            if (byteBuffer.position() + 4 + n * 24 > byteBuffer.capacity()) {
                return false;
            }
            return DatPackageReader.startsWithPackageName(byteBuffer, DatPackageReader.getNameOffset(byteBuffer, 0)) && DatPackageReader.startsWithPackageName(byteBuffer, DatPackageReader.getNameOffset(byteBuffer, n - 1));
            {
            }
        }

        private static final class IsAcceptable
        implements Authenticate {
            private IsAcceptable() {
            }

            @Override
            public boolean isDataVersionAcceptable(byte[] arrby) {
                boolean bl = false;
                if (arrby[0] == 1) {
                    bl = true;
                }
                return bl;
            }
        }

    }

    private static abstract class DataFile {
        protected final String itemPath;

        DataFile(String string) {
            this.itemPath = string;
        }

        abstract void addBaseNamesInFolder(String var1, String var2, Set<String> var3);

        abstract ByteBuffer getData(String var1);

        public String toString() {
            return this.itemPath;
        }
    }

    private static final class PackageDataFile
    extends DataFile {
        private final ByteBuffer pkgBytes;

        PackageDataFile(String string, ByteBuffer byteBuffer) {
            super(string);
            this.pkgBytes = byteBuffer;
        }

        @Override
        void addBaseNamesInFolder(String string, String string2, Set<String> set) {
            DatPackageReader.addBaseNamesInFolder(this.pkgBytes, string, string2, set);
        }

        @Override
        ByteBuffer getData(String string) {
            return DatPackageReader.getData(this.pkgBytes, string);
        }
    }

    private static final class SingleDataFile
    extends DataFile {
        private final File path;

        SingleDataFile(String string, File file) {
            super(string);
            this.path = file;
        }

        @Override
        void addBaseNamesInFolder(String string, String string2, Set<String> set) {
            if (this.itemPath.length() > string.length() + string2.length() && this.itemPath.startsWith(string) && this.itemPath.endsWith(string2) && this.itemPath.charAt(string.length()) == '/' && this.itemPath.indexOf(47, string.length() + 1) < 0) {
                set.add(this.itemPath.substring(string.length() + 1, this.itemPath.length() - string2.length()));
            }
        }

        @Override
        ByteBuffer getData(String string) {
            if (string.equals(this.itemPath)) {
                return ICUBinary.mapFile(this.path);
            }
            return null;
        }

        @Override
        public String toString() {
            return this.path.toString();
        }
    }

}

