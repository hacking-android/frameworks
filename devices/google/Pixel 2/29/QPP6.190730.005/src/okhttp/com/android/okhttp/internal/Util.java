/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp.internal;

import com.android.okhttp.HttpUrl;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.ByteString;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class Util {
    @UnsupportedAppUsage
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    @UnsupportedAppUsage
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private Util() {
    }

    public static void checkOffsetAndCount(long l, long l2, long l3) {
        if ((l2 | l3) >= 0L && l2 <= l && l - l2 >= l3) {
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @UnsupportedAppUsage
    public static void closeAll(Closeable closeable, Closeable object) throws IOException {
        block8 : {
            Object var2_3 = null;
            try {
                closeable.close();
                closeable = var2_3;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            try {
                object.close();
                object = closeable;
            }
            catch (Throwable throwable) {
                object = closeable;
                if (closeable != null) break block8;
                object = throwable;
            }
        }
        if (object == null) {
            return;
        }
        if (!(object instanceof IOException)) {
            if (!(object instanceof RuntimeException)) {
                if (object instanceof Error) {
                    throw (Error)object;
                }
                throw new AssertionError(object);
            }
            throw (RuntimeException)object;
        }
        throw (IOException)object;
    }

    @UnsupportedAppUsage
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (Exception exception) {
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
        }
    }

    public static void closeQuietly(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            }
            catch (Exception exception) {
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
        }
    }

    public static void closeQuietly(Socket socket) {
        block5 : {
            if (socket != null) {
                try {
                    socket.close();
                }
                catch (Exception exception) {
                }
                catch (RuntimeException runtimeException) {
                    throw runtimeException;
                }
                catch (AssertionError assertionError) {
                    if (Util.isAndroidGetsocknameError(assertionError)) break block5;
                    throw assertionError;
                }
            }
        }
    }

    public static String[] concat(String[] arrstring, String string) {
        String[] arrstring2 = new String[arrstring.length + 1];
        System.arraycopy(arrstring, 0, arrstring2, 0, arrstring.length);
        arrstring2[arrstring2.length - 1] = string;
        return arrstring2;
    }

    public static boolean contains(String[] arrstring, String string) {
        return Arrays.asList(arrstring).contains(string);
    }

    public static boolean discard(Source source, int n, TimeUnit timeUnit) {
        try {
            boolean bl = Util.skipAll(source, n, timeUnit);
            return bl;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public static boolean equal(Object object, Object object2) {
        boolean bl = object == object2 || object != null && object.equals(object2);
        return bl;
    }

    public static String hostHeader(HttpUrl object, boolean bl) {
        CharSequence charSequence;
        if (((HttpUrl)object).host().contains(":")) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[");
            ((StringBuilder)charSequence).append(((HttpUrl)object).host());
            ((StringBuilder)charSequence).append("]");
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = ((HttpUrl)object).host();
        }
        if (!bl && ((HttpUrl)object).port() == HttpUrl.defaultPort(((HttpUrl)object).scheme())) {
            object = charSequence;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(":");
            stringBuilder.append(((HttpUrl)object).port());
            object = stringBuilder.toString();
        }
        return object;
    }

    public static <T> List<T> immutableList(List<T> list) {
        return Collections.unmodifiableList(new ArrayList<T>(list));
    }

    public static <T> List<T> immutableList(T ... arrT) {
        return Collections.unmodifiableList(Arrays.asList((Object[])arrT.clone()));
    }

    public static <K, V> Map<K, V> immutableMap(Map<K, V> map) {
        return Collections.unmodifiableMap(new LinkedHashMap<K, V>(map));
    }

    private static <T> List<T> intersect(T[] arrT, T[] arrT2) {
        ArrayList<T> arrayList = new ArrayList<T>();
        block0 : for (T t : arrT) {
            for (T t2 : arrT2) {
                if (!t.equals(t2)) continue;
                arrayList.add(t2);
                continue block0;
            }
        }
        return arrayList;
    }

    public static <T> T[] intersect(Class<T> class_, T[] object, T[] arrT) {
        object = Util.intersect(object, arrT);
        return object.toArray((Object[])Array.newInstance(class_, object.size()));
    }

    public static boolean isAndroidGetsocknameError(AssertionError assertionError) {
        boolean bl = ((Throwable)((Object)assertionError)).getCause() != null && ((Throwable)((Object)assertionError)).getMessage() != null && ((Throwable)((Object)assertionError)).getMessage().contains("getsockname failed");
        return bl;
    }

    public static String md5Hex(String string) {
        try {
            string = ByteString.of(MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"))).hex();
            return string;
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException exception) {
            throw new AssertionError(exception);
        }
    }

    public static ByteString sha1(ByteString byteString) {
        try {
            byteString = ByteString.of(MessageDigest.getInstance("SHA-1").digest(byteString.toByteArray()));
            return byteString;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException);
        }
    }

    public static String shaBase64(String string) {
        try {
            string = ByteString.of(MessageDigest.getInstance("SHA-1").digest(string.getBytes("UTF-8"))).base64();
            return string;
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException exception) {
            throw new AssertionError(exception);
        }
    }

    public static boolean skipAll(Source source, int n, TimeUnit object) throws IOException {
        long l = System.nanoTime();
        long l2 = source.timeout().hasDeadline() ? source.timeout().deadlineNanoTime() - l : Long.MAX_VALUE;
        source.timeout().deadlineNanoTime(Math.min(l2, ((TimeUnit)((Object)object)).toNanos(n)) + l);
        try {
            object = new Buffer();
            while (source.read((Buffer)object, 2048L) != -1L) {
                ((Buffer)object).clear();
            }
            return true;
        }
        catch (InterruptedIOException interruptedIOException) {
            return false;
        }
        finally {
            if (l2 == Long.MAX_VALUE) {
                source.timeout().clearDeadline();
            } else {
                source.timeout().deadlineNanoTime(l + l2);
            }
        }
    }

    public static ThreadFactory threadFactory(final String string, final boolean bl) {
        return new ThreadFactory(){

            @Override
            public Thread newThread(Runnable runnable) {
                runnable = new Thread(runnable, string);
                ((Thread)runnable).setDaemon(bl);
                return runnable;
            }
        };
    }

    public static String toHumanReadableAscii(String string) {
        int n;
        int n2 = string.length();
        for (int i = 0; i < n2; i += Character.charCount((int)n)) {
            n = string.codePointAt(i);
            if (n > 31 && n < 127) {
                continue;
            }
            Buffer buffer = new Buffer();
            buffer.writeUtf8(string, 0, i);
            while (i < n2) {
                int n3 = string.codePointAt(i);
                n = n3 > 31 && n3 < 127 ? n3 : 63;
                buffer.writeUtf8CodePoint(n);
                i += Character.charCount(n3);
            }
            return buffer.readUtf8();
        }
        return string;
    }

}

