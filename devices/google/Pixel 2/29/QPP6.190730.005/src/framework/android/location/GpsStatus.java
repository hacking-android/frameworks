/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.UnsupportedAppUsage;
import android.location.GnssStatus;
import android.location.GpsSatellite;
import android.util.SparseArray;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Deprecated
public final class GpsStatus {
    private static final int BEIDOU_SVID_OFFSET = 200;
    private static final int GLONASS_SVID_OFFSET = 64;
    public static final int GPS_EVENT_FIRST_FIX = 3;
    public static final int GPS_EVENT_SATELLITE_STATUS = 4;
    public static final int GPS_EVENT_STARTED = 1;
    public static final int GPS_EVENT_STOPPED = 2;
    private static final int NUM_SATELLITES = 255;
    private static final int SBAS_SVID_OFFSET = -87;
    private Iterable<GpsSatellite> mSatelliteList = new Iterable<GpsSatellite>(){

        @Override
        public Iterator<GpsSatellite> iterator() {
            return new SatelliteIterator();
        }
    };
    private final SparseArray<GpsSatellite> mSatellites = new SparseArray();
    private int mTimeToFirstFix;

    GpsStatus() {
    }

    private void clearSatellites() {
        int n = this.mSatellites.size();
        for (int i = 0; i < n; ++i) {
            this.mSatellites.valueAt((int)i).mValid = false;
        }
    }

    private void setStatus(int n, int[] arrn, float[] arrf, float[] arrf2, float[] arrf3) {
        this.clearSatellites();
        for (int i = 0; i < n; ++i) {
            GpsSatellite gpsSatellite;
            int n2;
            int n3 = arrn[i] >> 4 & 15;
            int n4 = arrn[i] >> 8;
            boolean bl = true;
            if (n3 == 3) {
                n2 = n4 + 64;
            } else if (n3 == 5) {
                n2 = n4 + 200;
            } else if (n3 == 2) {
                n2 = n4 - 87;
            } else {
                n2 = n4;
                if (n3 != 1) {
                    n2 = n4;
                    if (n3 != 4) continue;
                }
            }
            if (n2 <= 0 || n2 > 255) continue;
            GpsSatellite gpsSatellite2 = gpsSatellite = this.mSatellites.get(n2);
            if (gpsSatellite == null) {
                gpsSatellite2 = new GpsSatellite(n2);
                this.mSatellites.put(n2, gpsSatellite2);
            }
            gpsSatellite2.mValid = true;
            gpsSatellite2.mSnr = arrf[i];
            gpsSatellite2.mElevation = arrf2[i];
            gpsSatellite2.mAzimuth = arrf3[i];
            boolean bl2 = (arrn[i] & 1) != 0;
            gpsSatellite2.mHasEphemeris = bl2;
            bl2 = (2 & arrn[i]) != 0;
            gpsSatellite2.mHasAlmanac = bl2;
            bl2 = (4 & arrn[i]) != 0 ? bl : false;
            gpsSatellite2.mUsedInFix = bl2;
        }
    }

    public int getMaxSatellites() {
        return 255;
    }

    public Iterable<GpsSatellite> getSatellites() {
        return this.mSatelliteList;
    }

    public int getTimeToFirstFix() {
        return this.mTimeToFirstFix;
    }

    void setStatus(GnssStatus gnssStatus, int n) {
        this.mTimeToFirstFix = n;
        this.setStatus(gnssStatus.mSvCount, gnssStatus.mSvidWithFlags, gnssStatus.mCn0DbHz, gnssStatus.mElevations, gnssStatus.mAzimuths);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    void setTimeToFirstFix(int n) {
        this.mTimeToFirstFix = n;
    }

    @Deprecated
    public static interface Listener {
        public void onGpsStatusChanged(int var1);
    }

    @Deprecated
    public static interface NmeaListener {
        public void onNmeaReceived(long var1, String var3);
    }

    private final class SatelliteIterator
    implements Iterator<GpsSatellite> {
        private int mIndex = 0;
        private final int mSatellitesCount;

        SatelliteIterator() {
            this.mSatellitesCount = GpsStatus.this.mSatellites.size();
        }

        @Override
        public boolean hasNext() {
            while (this.mIndex < this.mSatellitesCount) {
                if (((GpsSatellite)GpsStatus.access$000((GpsStatus)GpsStatus.this).valueAt((int)this.mIndex)).mValid) {
                    return true;
                }
                ++this.mIndex;
            }
            return false;
        }

        @Override
        public GpsSatellite next() {
            while (this.mIndex < this.mSatellitesCount) {
                GpsSatellite gpsSatellite = (GpsSatellite)GpsStatus.this.mSatellites.valueAt(this.mIndex);
                ++this.mIndex;
                if (!gpsSatellite.mValid) continue;
                return gpsSatellite;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}

