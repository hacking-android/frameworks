/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.EventLogTags;
import android.net.Network;
import android.net.TrafficStats;
import android.os.SystemClock;
import android.util.Log;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class SntpClient {
    private static final boolean DBG = true;
    private static final int NTP_LEAP_NOSYNC = 3;
    private static final int NTP_MODE_BROADCAST = 5;
    private static final int NTP_MODE_CLIENT = 3;
    private static final int NTP_MODE_SERVER = 4;
    private static final int NTP_PACKET_SIZE = 48;
    private static final int NTP_PORT = 123;
    private static final int NTP_STRATUM_DEATH = 0;
    private static final int NTP_STRATUM_MAX = 15;
    private static final int NTP_VERSION = 3;
    private static final long OFFSET_1900_TO_1970 = 2208988800L;
    private static final int ORIGINATE_TIME_OFFSET = 24;
    private static final int RECEIVE_TIME_OFFSET = 32;
    private static final int REFERENCE_TIME_OFFSET = 16;
    private static final String TAG = "SntpClient";
    private static final int TRANSMIT_TIME_OFFSET = 40;
    private long mNtpTime;
    private long mNtpTimeReference;
    private long mRoundTripTime;

    private static void checkValidServerReply(byte by, byte by2, int n, long l) throws InvalidServerReplyException {
        if (by != 3) {
            if (by2 != 4 && by2 != 5) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("untrusted mode: ");
                stringBuilder.append(by2);
                throw new InvalidServerReplyException(stringBuilder.toString());
            }
            if (n != 0 && n <= 15) {
                if (l != 0L) {
                    return;
                }
                throw new InvalidServerReplyException("zero transmitTime");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("untrusted stratum: ");
            stringBuilder.append(n);
            throw new InvalidServerReplyException(stringBuilder.toString());
        }
        throw new InvalidServerReplyException("unsynchronized server");
    }

    private long read32(byte[] arrby, int n) {
        int n2;
        int n3;
        int n4;
        block2 : {
            int n5 = arrby[n];
            n4 = arrby[n + 1];
            n2 = arrby[n + 2];
            n3 = arrby[n + 3];
            n = (n5 & 128) == 128 ? (n5 & 127) + 128 : n5;
            if ((n4 & 128) == 128) {
                n4 = (n4 & 127) + 128;
            }
            if ((n2 & 128) == 128) {
                n2 = (n2 & 127) + 128;
            }
            if ((n3 & 128) != 128) break block2;
            n3 = 128 + (n3 & 127);
        }
        return ((long)n << 24) + ((long)n4 << 16) + ((long)n2 << 8) + (long)n3;
    }

    private long readTimeStamp(byte[] arrby, int n) {
        long l = this.read32(arrby, n);
        long l2 = this.read32(arrby, n + 4);
        if (l == 0L && l2 == 0L) {
            return 0L;
        }
        return (l - 2208988800L) * 1000L + 1000L * l2 / 0x100000000L;
    }

    private void writeTimeStamp(byte[] arrby, int n, long l) {
        if (l == 0L) {
            Arrays.fill(arrby, n, n + 8, (byte)0);
            return;
        }
        long l2 = l / 1000L;
        long l3 = l2 + 2208988800L;
        int n2 = n + 1;
        arrby[n] = (byte)(l3 >> 24);
        int n3 = n2 + 1;
        arrby[n2] = (byte)(l3 >> 16);
        n = n3 + 1;
        arrby[n3] = (byte)(l3 >> 8);
        n3 = n + 1;
        arrby[n] = (byte)(l3 >> 0);
        l = 0x100000000L * (l - l2 * 1000L) / 1000L;
        n2 = n3 + 1;
        arrby[n3] = (byte)(l >> 24);
        n = n2 + 1;
        arrby[n2] = (byte)(l >> 16);
        n2 = n + 1;
        arrby[n] = (byte)(l >> 8);
        arrby[n2] = (byte)(Math.random() * 255.0);
    }

    @UnsupportedAppUsage
    public long getNtpTime() {
        return this.mNtpTime;
    }

    @UnsupportedAppUsage
    public long getNtpTimeReference() {
        return this.mNtpTimeReference;
    }

    @UnsupportedAppUsage
    public long getRoundTripTime() {
        return this.mRoundTripTime;
    }

    @Deprecated
    @UnsupportedAppUsage
    public boolean requestTime(String string2, int n) {
        Log.w(TAG, "Shame on you for calling the hidden API requestTime()!");
        return false;
    }

    public boolean requestTime(String charSequence, int n, Network network) {
        network = network.getPrivateDnsBypassingCopy();
        try {
            InetAddress inetAddress = network.getByName((String)charSequence);
            return this.requestTime(inetAddress, 123, n, network);
        }
        catch (Exception exception) {
            EventLogTags.writeNtpFailure((String)charSequence, exception.toString());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("request time failed: ");
            ((StringBuilder)charSequence).append(exception);
            Log.d(TAG, ((StringBuilder)charSequence).toString());
            return false;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean requestTime(InetAddress serializable, int n, int n2, Network object) {
        Throwable throwable2222;
        int n3;
        DatagramSocket datagramSocket;
        block8 : {
            DatagramSocket datagramSocket2 = null;
            DatagramSocket datagramSocket3 = null;
            n3 = TrafficStats.getAndSetThreadStatsTag(-191);
            datagramSocket = datagramSocket3;
            DatagramSocket datagramSocket4 = datagramSocket2;
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket2;
            Object object2 = new DatagramSocket();
            datagramSocket = datagramSocket3 = object2;
            datagramSocket4 = datagramSocket3;
            ((Network)object).bindSocket(datagramSocket3);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            datagramSocket3.setSoTimeout(n2);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            object = new byte[48];
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            object2 = new DatagramPacket((byte[])object, ((Object)object).length, (InetAddress)serializable, (int)n);
            object[0] = (byte)27;
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            long l = System.currentTimeMillis();
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            long l2 = SystemClock.elapsedRealtime();
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            this.writeTimeStamp((byte[])object, 40, l);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            datagramSocket3.send((DatagramPacket)object2);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            object2 = new DatagramPacket((byte[])object, ((Object)object).length);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            datagramSocket3.receive((DatagramPacket)object2);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            long l3 = SystemClock.elapsedRealtime();
            l += l3 - l2;
            byte by = (byte)(object[0] >> 6 & 3);
            byte by2 = (byte)(object[0] & 7);
            n = object[1];
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            long l4 = this.readTimeStamp((byte[])object, 24);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            long l5 = this.readTimeStamp((byte[])object, 32);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            long l6 = this.readTimeStamp((byte[])object, 40);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            SntpClient.checkValidServerReply(by, by2, n & 255, l6);
            l2 = l3 - l2 - (l6 - l5);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            l4 = (l5 - l4 + (l6 - l)) / 2L;
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            EventLogTags.writeNtpSuccess(((InetAddress)serializable).toString(), l2, l4);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            object = new StringBuilder();
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            ((StringBuilder)object).append("round trip: ");
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            ((StringBuilder)object).append(l2);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            ((StringBuilder)object).append("ms, clock offset: ");
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            ((StringBuilder)object).append(l4);
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            ((StringBuilder)object).append("ms");
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            Log.d(TAG, ((StringBuilder)object).toString());
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            this.mNtpTime = l + l4;
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            this.mNtpTimeReference = l3;
            datagramSocket = datagramSocket3;
            datagramSocket4 = datagramSocket3;
            this.mRoundTripTime = l2;
            datagramSocket3.close();
            TrafficStats.setThreadStatsTag(n3);
            return true;
            {
                block9 : {
                    catch (Throwable throwable2222) {
                        break block8;
                    }
                    catch (Exception exception) {}
                    datagramSocket = datagramSocket4;
                    {
                        EventLogTags.writeNtpFailure(((InetAddress)serializable).toString(), exception.toString());
                        datagramSocket = datagramSocket4;
                        datagramSocket = datagramSocket4;
                        serializable = new StringBuilder();
                        datagramSocket = datagramSocket4;
                        ((StringBuilder)serializable).append("request time failed: ");
                        datagramSocket = datagramSocket4;
                        ((StringBuilder)serializable).append(exception);
                        datagramSocket = datagramSocket4;
                        Log.d(TAG, ((StringBuilder)serializable).toString());
                        if (datagramSocket4 == null) break block9;
                        datagramSocket4.close();
                    }
                }
                TrafficStats.setThreadStatsTag(n3);
                return false;
            }
        }
        if (datagramSocket != null) {
            datagramSocket.close();
        }
        TrafficStats.setThreadStatsTag(n3);
        throw throwable2222;
    }

    private static class InvalidServerReplyException
    extends Exception {
        public InvalidServerReplyException(String string2) {
            super(string2);
        }
    }

}

