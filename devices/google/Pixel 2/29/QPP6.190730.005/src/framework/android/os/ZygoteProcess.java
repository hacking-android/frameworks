/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.content.pm.ApplicationInfo;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.ChildZygoteProcess;
import android.os.Parcel;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.ZygoteStartFailedEx;
import android.util.Log;
import android.util.Slog;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.Zygote;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ZygoteProcess {
    private static final String[] INVALID_USAP_FLAGS = new String[]{"--query-abi-list", "--get-pid", "--preload-default", "--preload-package", "--preload-app", "--start-child-zygote", "--set-api-blacklist-exemptions", "--hidden-api-log-sampling-rate", "--hidden-api-statslog-sampling-rate", "--invoke-with"};
    private static final String LOG_TAG = "ZygoteProcess";
    private static final String USAP_POOL_ENABLED_DEFAULT = "false";
    private static final int ZYGOTE_CONNECT_RETRY_DELAY_MS = 50;
    private static final int ZYGOTE_CONNECT_TIMEOUT_MS = 20000;
    static final int ZYGOTE_RETRY_MILLIS = 500;
    private List<String> mApiBlacklistExemptions = Collections.emptyList();
    private int mHiddenApiAccessLogSampleRate;
    private int mHiddenApiAccessStatslogSampleRate;
    private boolean mIsFirstPropCheck = true;
    private long mLastPropCheckTimestamp = 0L;
    private final Object mLock = new Object();
    private boolean mUsapPoolEnabled = false;
    private final LocalSocketAddress mUsapPoolSecondarySocketAddress;
    private final LocalSocketAddress mUsapPoolSocketAddress;
    private final LocalSocketAddress mZygoteSecondarySocketAddress;
    private final LocalSocketAddress mZygoteSocketAddress;
    private ZygoteState primaryZygoteState;
    private ZygoteState secondaryZygoteState;

    public ZygoteProcess() {
        this.mZygoteSocketAddress = new LocalSocketAddress("zygote", LocalSocketAddress.Namespace.RESERVED);
        this.mZygoteSecondarySocketAddress = new LocalSocketAddress("zygote_secondary", LocalSocketAddress.Namespace.RESERVED);
        this.mUsapPoolSocketAddress = new LocalSocketAddress("usap_pool_primary", LocalSocketAddress.Namespace.RESERVED);
        this.mUsapPoolSecondarySocketAddress = new LocalSocketAddress("usap_pool_secondary", LocalSocketAddress.Namespace.RESERVED);
    }

    public ZygoteProcess(LocalSocketAddress localSocketAddress, LocalSocketAddress localSocketAddress2) {
        this.mZygoteSocketAddress = localSocketAddress;
        this.mZygoteSecondarySocketAddress = localSocketAddress2;
        this.mUsapPoolSocketAddress = null;
        this.mUsapPoolSecondarySocketAddress = null;
    }

    @GuardedBy(value={"mLock"})
    private void attemptConnectionToPrimaryZygote() throws IOException {
        ZygoteState zygoteState = this.primaryZygoteState;
        if (zygoteState == null || zygoteState.isClosed()) {
            this.primaryZygoteState = ZygoteState.connect(this.mZygoteSocketAddress, this.mUsapPoolSocketAddress);
            this.maybeSetApiBlacklistExemptions(this.primaryZygoteState, false);
            this.maybeSetHiddenApiAccessLogSampleRate(this.primaryZygoteState);
            this.maybeSetHiddenApiAccessStatslogSampleRate(this.primaryZygoteState);
        }
    }

    @GuardedBy(value={"mLock"})
    private void attemptConnectionToSecondaryZygote() throws IOException {
        ZygoteState zygoteState = this.secondaryZygoteState;
        if (zygoteState == null || zygoteState.isClosed()) {
            this.secondaryZygoteState = ZygoteState.connect(this.mZygoteSecondarySocketAddress, this.mUsapPoolSecondarySocketAddress);
            this.maybeSetApiBlacklistExemptions(this.secondaryZygoteState, false);
            this.maybeSetHiddenApiAccessLogSampleRate(this.secondaryZygoteState);
            this.maybeSetHiddenApiAccessStatslogSampleRate(this.secondaryZygoteState);
        }
    }

    private Process.ProcessStartResult attemptUsapSendArgsAndGetResult(ZygoteState autoCloseable, String object) throws ZygoteStartFailedEx, IOException {
        block8 : {
            autoCloseable = ((ZygoteState)autoCloseable).getUsapSessionSocket();
            try {
                Closeable closeable = new OutputStreamWriter(((LocalSocket)autoCloseable).getOutputStream());
                BufferedWriter bufferedWriter = new BufferedWriter((Writer)closeable, 256);
                closeable = new DataInputStream(((LocalSocket)autoCloseable).getInputStream());
                bufferedWriter.write((String)object);
                bufferedWriter.flush();
                object = new Process.ProcessStartResult();
                ((Process.ProcessStartResult)object).pid = ((DataInputStream)closeable).readInt();
                ((Process.ProcessStartResult)object).usingWrapper = false;
                int n = ((Process.ProcessStartResult)object).pid;
                if (n < 0) break block8;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (autoCloseable != null) {
                        try {
                            ((LocalSocket)autoCloseable).close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    }
                    throw throwable2;
                }
            }
            ((LocalSocket)autoCloseable).close();
            return object;
        }
        object = new ZygoteStartFailedEx("USAP specialization failed");
        throw object;
    }

    private Process.ProcessStartResult attemptZygoteSendArgsAndGetResult(ZygoteState object, String object2) throws ZygoteStartFailedEx {
        block3 : {
            try {
                BufferedWriter bufferedWriter = ((ZygoteState)object).mZygoteOutputWriter;
                DataInputStream dataInputStream = ((ZygoteState)object).mZygoteInputStream;
                bufferedWriter.write((String)object2);
                bufferedWriter.flush();
                object2 = new Process.ProcessStartResult();
                ((Process.ProcessStartResult)object2).pid = dataInputStream.readInt();
                ((Process.ProcessStartResult)object2).usingWrapper = dataInputStream.readBoolean();
                if (((Process.ProcessStartResult)object2).pid < 0) break block3;
                return object2;
            }
            catch (IOException iOException) {
                ((ZygoteState)object).close();
                object = new StringBuilder();
                ((StringBuilder)object).append("IO Exception while communicating with Zygote - ");
                ((StringBuilder)object).append(iOException.toString());
                Log.e(LOG_TAG, ((StringBuilder)object).toString());
                throw new ZygoteStartFailedEx(iOException);
            }
        }
        object2 = new ZygoteStartFailedEx("fork() failed");
        throw object2;
    }

    private static boolean canAttemptUsap(ArrayList<String> object) {
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            CharSequence charSequence = (String)object.next();
            Object object2 = INVALID_USAP_FLAGS;
            int n = ((String[])object2).length;
            for (int i = 0; i < n; ++i) {
                if (!((String)charSequence).startsWith(object2[i])) continue;
                return false;
            }
            if (!((String)charSequence).startsWith("--nice-name=")) continue;
            object2 = ((String)charSequence).substring(12);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("wrap.");
            ((StringBuilder)charSequence).append((String)object2);
            object2 = SystemProperties.get(((StringBuilder)charSequence).toString());
            if (object2 == null || ((String)object2).length() == 0) continue;
            return false;
        }
        return true;
    }

    private boolean fetchUsapPoolEnabledProp() {
        boolean bl = this.mUsapPoolEnabled;
        if (!Zygote.getConfigurationProperty("usap_pool_enabled", USAP_POOL_ENABLED_DEFAULT).isEmpty()) {
            this.mUsapPoolEnabled = Zygote.getConfigurationPropertyBoolean("usap_pool_enabled", Boolean.parseBoolean(USAP_POOL_ENABLED_DEFAULT));
        }
        if (bl = bl != this.mUsapPoolEnabled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("usapPoolEnabled = ");
            stringBuilder.append(this.mUsapPoolEnabled);
            Log.i(LOG_TAG, stringBuilder.toString());
        }
        return bl;
    }

    private boolean fetchUsapPoolEnabledPropWithMinInterval() {
        long l = SystemClock.elapsedRealtime();
        if (SystemProperties.get("dalvik.vm.boot-image", "").endsWith("apex.art") && l <= 15000L) {
            return false;
        }
        if (!this.mIsFirstPropCheck && l - this.mLastPropCheckTimestamp < 60000L) {
            return false;
        }
        this.mIsFirstPropCheck = false;
        this.mLastPropCheckTimestamp = l;
        return this.fetchUsapPoolEnabledProp();
    }

    @GuardedBy(value={"mLock"})
    private static List<String> getAbiList(BufferedWriter arrby, DataInputStream dataInputStream) throws IOException {
        arrby.write("1");
        arrby.newLine();
        arrby.write("--query-abi-list");
        arrby.newLine();
        arrby.flush();
        arrby = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(arrby);
        return Arrays.asList(new String(arrby, StandardCharsets.US_ASCII).split(","));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void informZygotesOfUsapPoolStatus() {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("1\n--usap-pool-enabled=");
        ((StringBuilder)object).append(this.mUsapPoolEnabled);
        ((StringBuilder)object).append("\n");
        Object object2 = ((StringBuilder)object).toString();
        object = this.mLock;
        synchronized (object) {
            try {
                block12 : {
                    Object object3;
                    try {
                        this.attemptConnectionToPrimaryZygote();
                        this.primaryZygoteState.mZygoteOutputWriter.write((String)object2);
                        this.primaryZygoteState.mZygoteOutputWriter.flush();
                        object3 = this.mZygoteSecondarySocketAddress;
                        if (object3 == null) break block12;
                    }
                    catch (IOException iOException) {
                        boolean bl = !this.mUsapPoolEnabled;
                        this.mUsapPoolEnabled = bl;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Failed to inform zygotes of USAP pool status: ");
                        ((StringBuilder)object2).append(iOException.getMessage());
                        Log.w(LOG_TAG, ((StringBuilder)object2).toString());
                        return;
                    }
                    this.attemptConnectionToSecondaryZygote();
                    try {
                        this.secondaryZygoteState.mZygoteOutputWriter.write((String)object2);
                        this.secondaryZygoteState.mZygoteOutputWriter.flush();
                        this.secondaryZygoteState.mZygoteInputStream.readInt();
                    }
                    catch (IOException iOException) {
                        try {
                            object3 = new IllegalStateException("USAP pool state change cause an irrecoverable error", iOException);
                            throw object3;
                        }
                        catch (IOException iOException2) {
                            // empty catch block
                        }
                    }
                }
                try {
                    this.primaryZygoteState.mZygoteInputStream.readInt();
                    return;
                }
                catch (IOException iOException) {
                    object2 = new IllegalStateException("USAP pool state change cause an irrecoverable error", iOException);
                    throw object2;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @GuardedBy(value={"mLock"})
    private boolean maybeSetApiBlacklistExemptions(ZygoteState object, boolean bl) {
        if (object != null && !((ZygoteState)object).isClosed()) {
            block8 : {
                if (!bl && this.mApiBlacklistExemptions.isEmpty()) {
                    return true;
                }
                ((ZygoteState)object).mZygoteOutputWriter.write(Integer.toString(this.mApiBlacklistExemptions.size() + 1));
                ((ZygoteState)object).mZygoteOutputWriter.newLine();
                ((ZygoteState)object).mZygoteOutputWriter.write("--set-api-blacklist-exemptions");
                ((ZygoteState)object).mZygoteOutputWriter.newLine();
                int n = 0;
                do {
                    if (n >= this.mApiBlacklistExemptions.size()) break;
                    ((ZygoteState)object).mZygoteOutputWriter.write(this.mApiBlacklistExemptions.get(n));
                    ((ZygoteState)object).mZygoteOutputWriter.newLine();
                    ++n;
                    continue;
                    break;
                } while (true);
                try {
                    ((ZygoteState)object).mZygoteOutputWriter.flush();
                    n = ((ZygoteState)object).mZygoteInputStream.readInt();
                    if (n == 0) break block8;
                }
                catch (IOException iOException) {
                    Slog.e(LOG_TAG, "Failed to set API blacklist exemptions", iOException);
                    this.mApiBlacklistExemptions = Collections.emptyList();
                    return false;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to set API blacklist exemptions; status ");
                ((StringBuilder)object).append(n);
                Slog.e(LOG_TAG, ((StringBuilder)object).toString());
            }
            return true;
        }
        Slog.e(LOG_TAG, "Can't set API blacklist exemptions: no zygote connection");
        return false;
    }

    private void maybeSetHiddenApiAccessLogSampleRate(ZygoteState object) {
        if (object != null && !((ZygoteState)object).isClosed() && this.mHiddenApiAccessLogSampleRate != -1) {
            block4 : {
                ((ZygoteState)object).mZygoteOutputWriter.write(Integer.toString(1));
                ((ZygoteState)object).mZygoteOutputWriter.newLine();
                BufferedWriter bufferedWriter = ((ZygoteState)object).mZygoteOutputWriter;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("--hidden-api-log-sampling-rate=");
                stringBuilder.append(this.mHiddenApiAccessLogSampleRate);
                bufferedWriter.write(stringBuilder.toString());
                ((ZygoteState)object).mZygoteOutputWriter.newLine();
                ((ZygoteState)object).mZygoteOutputWriter.flush();
                int n = ((ZygoteState)object).mZygoteInputStream.readInt();
                if (n == 0) break block4;
                try {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Failed to set hidden API log sampling rate; status ");
                    ((StringBuilder)object).append(n);
                    Slog.e(LOG_TAG, ((StringBuilder)object).toString());
                }
                catch (IOException iOException) {
                    Slog.e(LOG_TAG, "Failed to set hidden API log sampling rate", iOException);
                }
            }
            return;
        }
    }

    private void maybeSetHiddenApiAccessStatslogSampleRate(ZygoteState object) {
        if (object != null && !((ZygoteState)object).isClosed() && this.mHiddenApiAccessStatslogSampleRate != -1) {
            block4 : {
                ((ZygoteState)object).mZygoteOutputWriter.write(Integer.toString(1));
                ((ZygoteState)object).mZygoteOutputWriter.newLine();
                BufferedWriter bufferedWriter = ((ZygoteState)object).mZygoteOutputWriter;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("--hidden-api-statslog-sampling-rate=");
                stringBuilder.append(this.mHiddenApiAccessStatslogSampleRate);
                bufferedWriter.write(stringBuilder.toString());
                ((ZygoteState)object).mZygoteOutputWriter.newLine();
                ((ZygoteState)object).mZygoteOutputWriter.flush();
                int n = ((ZygoteState)object).mZygoteInputStream.readInt();
                if (n == 0) break block4;
                try {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Failed to set hidden API statslog sampling rate; status ");
                    ((StringBuilder)object).append(n);
                    Slog.e(LOG_TAG, ((StringBuilder)object).toString());
                }
                catch (IOException iOException) {
                    Slog.e(LOG_TAG, "Failed to set hidden API statslog sampling rate", iOException);
                }
            }
            return;
        }
    }

    @GuardedBy(value={"mLock"})
    private ZygoteState openZygoteSocketIfNeeded(String object) throws ZygoteStartFailedEx {
        StringBuilder stringBuilder;
        try {
            this.attemptConnectionToPrimaryZygote();
            if (this.primaryZygoteState.matches((String)object)) {
                return this.primaryZygoteState;
            }
            if (this.mZygoteSecondarySocketAddress != null) {
                this.attemptConnectionToSecondaryZygote();
                if (this.secondaryZygoteState.matches((String)object)) {
                    object = this.secondaryZygoteState;
                    return object;
                }
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported zygote ABI: ");
        }
        catch (IOException iOException) {
            throw new ZygoteStartFailedEx("Error connecting to zygote", iOException);
        }
        stringBuilder.append((String)object);
        throw new ZygoteStartFailedEx(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Process.ProcessStartResult startViaZygote(String object, String object2, int n, int n2, int[] object3, int n3, int n4, int n5, String string2, String string3, String string4, String string5, String string6, boolean bl, String string7, boolean bl2, String[] arrstring) throws ZygoteStartFailedEx {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("--runtime-args");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--setuid=");
        stringBuilder.append(n);
        arrayList.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("--setgid=");
        stringBuilder.append(n2);
        arrayList.add(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("--runtime-flags=");
        stringBuilder.append(n3);
        arrayList.add(stringBuilder.toString());
        if (n4 == 1) {
            arrayList.add("--mount-external-default");
        } else if (n4 == 2) {
            arrayList.add("--mount-external-read");
        } else if (n4 == 3) {
            arrayList.add("--mount-external-write");
        } else if (n4 == 6) {
            arrayList.add("--mount-external-full");
        } else if (n4 == 5) {
            arrayList.add("--mount-external-installer");
        } else if (n4 == 4) {
            arrayList.add("--mount-external-legacy");
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("--target-sdk-version=");
        stringBuilder.append(n5);
        arrayList.add(stringBuilder.toString());
        if (object3 != null && ((int[])object3).length > 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("--setgroups=");
            n2 = ((Object)object3).length;
            for (n = 0; n < n2; ++n) {
                if (n != 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append((int)object3[n]);
            }
            arrayList.add(stringBuilder.toString());
        }
        if (object2 != null) {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("--nice-name=");
            ((StringBuilder)object3).append((String)object2);
            arrayList.add(((StringBuilder)object3).toString());
        }
        if (string2 != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("--seinfo=");
            ((StringBuilder)object2).append(string2);
            arrayList.add(((StringBuilder)object2).toString());
        }
        if (string4 != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("--instruction-set=");
            ((StringBuilder)object2).append(string4);
            arrayList.add(((StringBuilder)object2).toString());
        }
        if (string5 != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("--app-data-dir=");
            ((StringBuilder)object2).append(string5);
            arrayList.add(((StringBuilder)object2).toString());
        }
        if (string6 != null) {
            arrayList.add("--invoke-with");
            arrayList.add(string6);
        }
        if (bl) {
            arrayList.add("--start-child-zygote");
        }
        if (string7 != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("--package-name=");
            ((StringBuilder)object2).append(string7);
            arrayList.add(((StringBuilder)object2).toString());
        }
        arrayList.add((String)object);
        if (arrstring != null) {
            Collections.addAll(arrayList, arrstring);
        }
        object2 = this.mLock;
        synchronized (object2) {
            object = this.openZygoteSocketIfNeeded(string3);
            return this.zygoteSendArgsAndGetResult((ZygoteState)object, bl2, arrayList);
        }
    }

    public static void waitForConnectionToZygote(LocalSocketAddress localSocketAddress) {
        StringBuilder stringBuilder;
        for (int i = 400; i >= 0; --i) {
            try {
                ZygoteState.connect(localSocketAddress, null).close();
                return;
            }
            catch (IOException iOException) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Got error connecting to zygote, retrying. msg= ");
                stringBuilder.append(iOException.getMessage());
                Log.w(LOG_TAG, stringBuilder.toString());
                try {
                    Thread.sleep(50L);
                }
                catch (InterruptedException interruptedException) {
                }
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to connect to Zygote through socket ");
        stringBuilder.append(localSocketAddress.getName());
        Slog.wtf(LOG_TAG, stringBuilder.toString());
    }

    public static void waitForConnectionToZygote(String string2) {
        ZygoteProcess.waitForConnectionToZygote(new LocalSocketAddress(string2, LocalSocketAddress.Namespace.RESERVED));
    }

    @GuardedBy(value={"mLock"})
    private Process.ProcessStartResult zygoteSendArgsAndGetResult(ZygoteState zygoteState, boolean bl, ArrayList<String> object) throws ZygoteStartFailedEx {
        CharSequence charSequence;
        Object object2 = ((ArrayList)object).iterator();
        while (object2.hasNext()) {
            charSequence = object2.next();
            if (((String)charSequence).indexOf(10) < 0) {
                if (((String)charSequence).indexOf(13) < 0) continue;
                throw new ZygoteStartFailedEx("Embedded carriage returns not allowed");
            }
            throw new ZygoteStartFailedEx("Embedded newlines not allowed");
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(((ArrayList)object).size());
        ((StringBuilder)charSequence).append("\n");
        ((StringBuilder)charSequence).append(String.join((CharSequence)"\n", object));
        ((StringBuilder)charSequence).append("\n");
        charSequence = ((StringBuilder)charSequence).toString();
        if (bl && this.mUsapPoolEnabled && ZygoteProcess.canAttemptUsap(object)) {
            try {
                object = this.attemptUsapSendArgsAndGetResult(zygoteState, (String)charSequence);
                return object;
            }
            catch (IOException iOException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("IO Exception while communicating with USAP pool - ");
                ((StringBuilder)object2).append(iOException.getMessage());
                Log.e(LOG_TAG, ((StringBuilder)object2).toString());
            }
        }
        return this.attemptZygoteSendArgsAndGetResult(zygoteState, (String)charSequence);
    }

    public void close() {
        ZygoteState zygoteState = this.primaryZygoteState;
        if (zygoteState != null) {
            zygoteState.close();
        }
        if ((zygoteState = this.secondaryZygoteState) != null) {
            zygoteState.close();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void establishZygoteConnectionForAbi(String string2) {
        try {
            Object object = this.mLock;
            // MONITORENTER : object
        }
        catch (ZygoteStartFailedEx zygoteStartFailedEx) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to connect to zygote for abi: ");
            stringBuilder.append(string2);
            throw new RuntimeException(stringBuilder.toString(), zygoteStartFailedEx);
        }
        this.openZygoteSocketIfNeeded(string2);
        // MONITOREXIT : object
    }

    public LocalSocketAddress getPrimarySocketAddress() {
        return this.mZygoteSocketAddress;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public int getZygotePid(String arrby) {
        try {
            Object object = this.mLock;
            // MONITORENTER : object
        }
        catch (Exception exception) {
            throw new RuntimeException("Failure retrieving pid", exception);
        }
        Object object = this.openZygoteSocketIfNeeded((String)arrby);
        ((ZygoteState)object).mZygoteOutputWriter.write("1");
        ((ZygoteState)object).mZygoteOutputWriter.newLine();
        ((ZygoteState)object).mZygoteOutputWriter.write("--get-pid");
        ((ZygoteState)object).mZygoteOutputWriter.newLine();
        ((ZygoteState)object).mZygoteOutputWriter.flush();
        arrby = new byte[((ZygoteState)object).mZygoteInputStream.readInt()];
        ((ZygoteState)object).mZygoteInputStream.readFully(arrby);
        object = new String(arrby, StandardCharsets.US_ASCII);
        int n = Integer.parseInt((String)object);
        // MONITOREXIT : object
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean preloadApp(ApplicationInfo object, String object2) throws ZygoteStartFailedEx, IOException {
        Object object3 = this.mLock;
        synchronized (object3) {
            object2 = this.openZygoteSocketIfNeeded((String)object2);
            ((ZygoteState)object2).mZygoteOutputWriter.write("2");
            ((ZygoteState)object2).mZygoteOutputWriter.newLine();
            ((ZygoteState)object2).mZygoteOutputWriter.write("--preload-app");
            ((ZygoteState)object2).mZygoteOutputWriter.newLine();
            Parcel parcel = Parcel.obtain();
            boolean bl = false;
            ((ApplicationInfo)object).writeToParcel(parcel, 0);
            object = Base64.getEncoder().encodeToString(parcel.marshall());
            parcel.recycle();
            ((ZygoteState)object2).mZygoteOutputWriter.write((String)object);
            ((ZygoteState)object2).mZygoteOutputWriter.newLine();
            ((ZygoteState)object2).mZygoteOutputWriter.flush();
            if (((ZygoteState)object2).mZygoteInputStream.readInt() != 0) return bl;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean preloadDefault(String object) throws ZygoteStartFailedEx, IOException {
        Object object2 = this.mLock;
        synchronized (object2) {
            object = this.openZygoteSocketIfNeeded((String)object);
            ((ZygoteState)object).mZygoteOutputWriter.write("1");
            ((ZygoteState)object).mZygoteOutputWriter.newLine();
            ((ZygoteState)object).mZygoteOutputWriter.write("--preload-default");
            ((ZygoteState)object).mZygoteOutputWriter.newLine();
            ((ZygoteState)object).mZygoteOutputWriter.flush();
            if (((ZygoteState)object).mZygoteInputStream.readInt() != 0) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean preloadPackageForAbi(String string2, String string3, String string4, String string5, String object) throws ZygoteStartFailedEx, IOException {
        Object object2 = this.mLock;
        synchronized (object2) {
            object = this.openZygoteSocketIfNeeded((String)object);
            ((ZygoteState)object).mZygoteOutputWriter.write("5");
            ((ZygoteState)object).mZygoteOutputWriter.newLine();
            ((ZygoteState)object).mZygoteOutputWriter.write("--preload-package");
            ((ZygoteState)object).mZygoteOutputWriter.newLine();
            ((ZygoteState)object).mZygoteOutputWriter.write(string2);
            ((ZygoteState)object).mZygoteOutputWriter.newLine();
            ((ZygoteState)object).mZygoteOutputWriter.write(string3);
            ((ZygoteState)object).mZygoteOutputWriter.newLine();
            ((ZygoteState)object).mZygoteOutputWriter.write(string4);
            ((ZygoteState)object).mZygoteOutputWriter.newLine();
            ((ZygoteState)object).mZygoteOutputWriter.write(string5);
            ((ZygoteState)object).mZygoteOutputWriter.newLine();
            ((ZygoteState)object).mZygoteOutputWriter.flush();
            if (((ZygoteState)object).mZygoteInputStream.readInt() != 0) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean setApiBlacklistExemptions(List<String> list) {
        Object object = this.mLock;
        synchronized (object) {
            boolean bl;
            this.mApiBlacklistExemptions = list;
            boolean bl2 = bl = this.maybeSetApiBlacklistExemptions(this.primaryZygoteState, true);
            if (!bl) return bl2;
            return this.maybeSetApiBlacklistExemptions(this.secondaryZygoteState, true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setHiddenApiAccessLogSampleRate(int n) {
        Object object = this.mLock;
        synchronized (object) {
            this.mHiddenApiAccessLogSampleRate = n;
            this.maybeSetHiddenApiAccessLogSampleRate(this.primaryZygoteState);
            this.maybeSetHiddenApiAccessLogSampleRate(this.secondaryZygoteState);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setHiddenApiAccessStatslogSampleRate(int n) {
        Object object = this.mLock;
        synchronized (object) {
            this.mHiddenApiAccessStatslogSampleRate = n;
            this.maybeSetHiddenApiAccessStatslogSampleRate(this.primaryZygoteState);
            this.maybeSetHiddenApiAccessStatslogSampleRate(this.secondaryZygoteState);
            return;
        }
    }

    public final Process.ProcessStartResult start(String object, String string2, int n, int n2, int[] arrn, int n3, int n4, int n5, String string3, String string4, String string5, String string6, String string7, String string8, boolean bl, String[] arrstring) {
        if (this.fetchUsapPoolEnabledPropWithMinInterval()) {
            this.informZygotesOfUsapPoolStatus();
        }
        try {
            object = this.startViaZygote((String)object, string2, n, n2, arrn, n3, n4, n5, string3, string4, string5, string6, string7, false, string8, bl, arrstring);
            return object;
        }
        catch (ZygoteStartFailedEx zygoteStartFailedEx) {
            Log.e(LOG_TAG, "Starting VM process through Zygote failed");
            throw new RuntimeException("Starting VM process through Zygote failed", zygoteStartFailedEx);
        }
    }

    public ChildZygoteProcess startChildZygote(String object, String string2, int n, int n2, int[] arrn, int n3, String string3, String string4, String string5, String string6, int n4, int n5) {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append("/");
        ((StringBuilder)object2).append(UUID.randomUUID().toString());
        object2 = new LocalSocketAddress(((StringBuilder)object2).toString());
        CharSequence charSequence = new StringBuilder();
        charSequence.append("--zygote-socket=");
        charSequence.append(((LocalSocketAddress)object2).getName());
        charSequence = charSequence.toString();
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append("--abi-list=");
        charSequence2.append(string5);
        string5 = charSequence2.toString();
        charSequence2 = new StringBuilder();
        charSequence2.append("--uid-range-start=");
        charSequence2.append(n4);
        charSequence2 = charSequence2.toString();
        CharSequence charSequence3 = new StringBuilder();
        charSequence3.append("--uid-range-end=");
        charSequence3.append(n5);
        charSequence3 = charSequence3.toString();
        try {
            object = this.startViaZygote((String)object, string2, n, n2, arrn, n3, 0, 0, string3, string4, string6, null, null, true, null, false, new String[]{charSequence, string5, charSequence2, charSequence3});
            return new ChildZygoteProcess((LocalSocketAddress)object2, ((Process.ProcessStartResult)object).pid);
        }
        catch (ZygoteStartFailedEx zygoteStartFailedEx) {
            throw new RuntimeException("Starting child-zygote through Zygote failed", zygoteStartFailedEx);
        }
    }

    private static class ZygoteState
    implements AutoCloseable {
        private final List<String> mAbiList;
        private boolean mClosed;
        final LocalSocketAddress mUsapSocketAddress;
        final DataInputStream mZygoteInputStream;
        final BufferedWriter mZygoteOutputWriter;
        private final LocalSocket mZygoteSessionSocket;
        final LocalSocketAddress mZygoteSocketAddress;

        private ZygoteState(LocalSocketAddress localSocketAddress, LocalSocketAddress localSocketAddress2, LocalSocket localSocket, DataInputStream dataInputStream, BufferedWriter bufferedWriter, List<String> list) {
            this.mZygoteSocketAddress = localSocketAddress;
            this.mUsapSocketAddress = localSocketAddress2;
            this.mZygoteSessionSocket = localSocket;
            this.mZygoteInputStream = dataInputStream;
            this.mZygoteOutputWriter = bufferedWriter;
            this.mAbiList = list;
        }

        static ZygoteState connect(LocalSocketAddress localSocketAddress, LocalSocketAddress localSocketAddress2) throws IOException {
            LocalSocket localSocket = new LocalSocket();
            if (localSocketAddress != null) {
                try {
                    localSocket.connect(localSocketAddress);
                    DataInputStream dataInputStream = new DataInputStream(localSocket.getInputStream());
                    Writer writer = new OutputStreamWriter(localSocket.getOutputStream());
                    writer = new BufferedWriter(writer, 256);
                    return new ZygoteState(localSocketAddress, localSocketAddress2, localSocket, dataInputStream, (BufferedWriter)writer, ZygoteProcess.getAbiList((BufferedWriter)writer, dataInputStream));
                }
                catch (IOException iOException) {
                    try {
                        localSocket.close();
                    }
                    catch (IOException iOException2) {}
                    throw iOException;
                }
            }
            throw new IllegalArgumentException("zygoteSocketAddress can't be null");
        }

        @Override
        public void close() {
            try {
                this.mZygoteSessionSocket.close();
            }
            catch (IOException iOException) {
                Log.e(ZygoteProcess.LOG_TAG, "I/O exception on routine close", iOException);
            }
            this.mClosed = true;
        }

        LocalSocket getUsapSessionSocket() throws IOException {
            LocalSocket localSocket = new LocalSocket();
            localSocket.connect(this.mUsapSocketAddress);
            return localSocket;
        }

        boolean isClosed() {
            return this.mClosed;
        }

        boolean matches(String string2) {
            return this.mAbiList.contains(string2);
        }
    }

}

