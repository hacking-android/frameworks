/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.location.gnssmetrics;

import android.location.GnssStatus;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.connectivity.GpsBatteryStats;
import android.util.Base64;
import android.util.Log;
import android.util.StatsLog;
import android.util.TimeUtils;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.internal.app.IBatteryStats;
import com.android.internal.location.nano.GnssLogsProto;
import java.util.Arrays;

public class GnssMetrics {
    private static final int DEFAULT_TIME_BETWEEN_FIXES_MILLISECS = 1000;
    public static final int GPS_SIGNAL_QUALITY_GOOD = 1;
    public static final int GPS_SIGNAL_QUALITY_POOR = 0;
    public static final int GPS_SIGNAL_QUALITY_UNKNOWN = -1;
    public static final int NUM_GPS_SIGNAL_QUALITY_LEVELS = 2;
    private static final String TAG = GnssMetrics.class.getSimpleName();
    private Statistics locationFailureStatistics;
    private String logStartInElapsedRealTime;
    private boolean[] mConstellationTypes;
    private GnssPowerMetrics mGnssPowerMetrics;
    private Statistics positionAccuracyMeterStatistics;
    private Statistics timeToFirstFixSecStatistics;
    private Statistics topFourAverageCn0Statistics;

    public GnssMetrics(IBatteryStats iBatteryStats) {
        this.mGnssPowerMetrics = new GnssPowerMetrics(iBatteryStats);
        this.locationFailureStatistics = new Statistics();
        this.timeToFirstFixSecStatistics = new Statistics();
        this.positionAccuracyMeterStatistics = new Statistics();
        this.topFourAverageCn0Statistics = new Statistics();
        this.reset();
    }

    private void reset() {
        StringBuilder stringBuilder = new StringBuilder();
        TimeUtils.formatDuration(SystemClock.elapsedRealtimeNanos() / 1000000L, stringBuilder);
        this.logStartInElapsedRealTime = stringBuilder.toString();
        this.locationFailureStatistics.reset();
        this.timeToFirstFixSecStatistics.reset();
        this.positionAccuracyMeterStatistics.reset();
        this.topFourAverageCn0Statistics.reset();
        this.resetConstellationTypes();
    }

    public String dumpGnssMetricsAsProtoString() {
        Object object = new GnssLogsProto.GnssLog();
        if (this.locationFailureStatistics.getCount() > 0) {
            ((GnssLogsProto.GnssLog)object).numLocationReportProcessed = this.locationFailureStatistics.getCount();
            ((GnssLogsProto.GnssLog)object).percentageLocationFailure = (int)(this.locationFailureStatistics.getMean() * 100.0);
        }
        if (this.timeToFirstFixSecStatistics.getCount() > 0) {
            ((GnssLogsProto.GnssLog)object).numTimeToFirstFixProcessed = this.timeToFirstFixSecStatistics.getCount();
            ((GnssLogsProto.GnssLog)object).meanTimeToFirstFixSecs = (int)this.timeToFirstFixSecStatistics.getMean();
            ((GnssLogsProto.GnssLog)object).standardDeviationTimeToFirstFixSecs = (int)this.timeToFirstFixSecStatistics.getStandardDeviation();
        }
        if (this.positionAccuracyMeterStatistics.getCount() > 0) {
            ((GnssLogsProto.GnssLog)object).numPositionAccuracyProcessed = this.positionAccuracyMeterStatistics.getCount();
            ((GnssLogsProto.GnssLog)object).meanPositionAccuracyMeters = (int)this.positionAccuracyMeterStatistics.getMean();
            ((GnssLogsProto.GnssLog)object).standardDeviationPositionAccuracyMeters = (int)this.positionAccuracyMeterStatistics.getStandardDeviation();
        }
        if (this.topFourAverageCn0Statistics.getCount() > 0) {
            ((GnssLogsProto.GnssLog)object).numTopFourAverageCn0Processed = this.topFourAverageCn0Statistics.getCount();
            ((GnssLogsProto.GnssLog)object).meanTopFourAverageCn0DbHz = this.topFourAverageCn0Statistics.getMean();
            ((GnssLogsProto.GnssLog)object).standardDeviationTopFourAverageCn0DbHz = this.topFourAverageCn0Statistics.getStandardDeviation();
        }
        ((GnssLogsProto.GnssLog)object).powerMetrics = this.mGnssPowerMetrics.buildProto();
        ((GnssLogsProto.GnssLog)object).hardwareRevision = SystemProperties.get("ro.boot.revision", "");
        object = Base64.encodeToString(GnssLogsProto.GnssLog.toByteArray((MessageNano)object), 0);
        this.reset();
        return object;
    }

    public String dumpGnssMetricsAsText() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GNSS_KPI_START");
        stringBuilder.append('\n');
        stringBuilder.append("  KPI logging start time: ");
        stringBuilder.append(this.logStartInElapsedRealTime);
        stringBuilder.append("\n");
        stringBuilder.append("  KPI logging end time: ");
        TimeUtils.formatDuration(SystemClock.elapsedRealtimeNanos() / 1000000L, stringBuilder);
        stringBuilder.append("\n");
        stringBuilder.append("  Number of location reports: ");
        stringBuilder.append(this.locationFailureStatistics.getCount());
        stringBuilder.append("\n");
        if (this.locationFailureStatistics.getCount() > 0) {
            stringBuilder.append("  Percentage location failure: ");
            stringBuilder.append(this.locationFailureStatistics.getMean() * 100.0);
            stringBuilder.append("\n");
        }
        stringBuilder.append("  Number of TTFF reports: ");
        stringBuilder.append(this.timeToFirstFixSecStatistics.getCount());
        stringBuilder.append("\n");
        if (this.timeToFirstFixSecStatistics.getCount() > 0) {
            stringBuilder.append("  TTFF mean (sec): ");
            stringBuilder.append(this.timeToFirstFixSecStatistics.getMean());
            stringBuilder.append("\n");
            stringBuilder.append("  TTFF standard deviation (sec): ");
            stringBuilder.append(this.timeToFirstFixSecStatistics.getStandardDeviation());
            stringBuilder.append("\n");
        }
        stringBuilder.append("  Number of position accuracy reports: ");
        stringBuilder.append(this.positionAccuracyMeterStatistics.getCount());
        stringBuilder.append("\n");
        if (this.positionAccuracyMeterStatistics.getCount() > 0) {
            stringBuilder.append("  Position accuracy mean (m): ");
            stringBuilder.append(this.positionAccuracyMeterStatistics.getMean());
            stringBuilder.append("\n");
            stringBuilder.append("  Position accuracy standard deviation (m): ");
            stringBuilder.append(this.positionAccuracyMeterStatistics.getStandardDeviation());
            stringBuilder.append("\n");
        }
        stringBuilder.append("  Number of CN0 reports: ");
        stringBuilder.append(this.topFourAverageCn0Statistics.getCount());
        stringBuilder.append("\n");
        if (this.topFourAverageCn0Statistics.getCount() > 0) {
            stringBuilder.append("  Top 4 Avg CN0 mean (dB-Hz): ");
            stringBuilder.append(this.topFourAverageCn0Statistics.getMean());
            stringBuilder.append("\n");
            stringBuilder.append("  Top 4 Avg CN0 standard deviation (dB-Hz): ");
            stringBuilder.append(this.topFourAverageCn0Statistics.getStandardDeviation());
            stringBuilder.append("\n");
        }
        stringBuilder.append("  Used-in-fix constellation types: ");
        for (int i = 0; i < ((boolean[])(object = this.mConstellationTypes)).length; ++i) {
            if (!object[i]) continue;
            stringBuilder.append(GnssStatus.constellationTypeToString(i));
            stringBuilder.append(" ");
        }
        stringBuilder.append("\n");
        stringBuilder.append("GNSS_KPI_END");
        stringBuilder.append("\n");
        object = this.mGnssPowerMetrics.getGpsBatteryStats();
        if (object != null) {
            stringBuilder.append("Power Metrics");
            stringBuilder.append("\n");
            long[] arrl = new StringBuilder();
            arrl.append("  Time on battery (min): ");
            arrl.append((double)((GpsBatteryStats)object).getLoggingDurationMs() / 60000.0);
            stringBuilder.append(arrl.toString());
            stringBuilder.append("\n");
            arrl = ((GpsBatteryStats)object).getTimeInGpsSignalQualityLevel();
            if (arrl != null && arrl.length == 2) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("  Amount of time (while on battery) Top 4 Avg CN0 > ");
                stringBuilder2.append(Double.toString(20.0));
                stringBuilder2.append(" dB-Hz (min): ");
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder.append((double)arrl[1] / 60000.0);
                stringBuilder.append("\n");
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("  Amount of time (while on battery) Top 4 Avg CN0 <= ");
                stringBuilder2.append(Double.toString(20.0));
                stringBuilder2.append(" dB-Hz (min): ");
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder.append((double)arrl[0] / 60000.0);
                stringBuilder.append("\n");
            }
            stringBuilder.append("  Energy consumed while on battery (mAh): ");
            stringBuilder.append((double)((GpsBatteryStats)object).getEnergyConsumedMaMs() / 3600000.0);
            stringBuilder.append("\n");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Hardware Version: ");
        ((StringBuilder)object).append(SystemProperties.get("ro.boot.revision", ""));
        stringBuilder.append(((StringBuilder)object).toString());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public void logCn0(float[] arrf, int n) {
        if (n != 0 && arrf != null && arrf.length != 0 && arrf.length >= n) {
            arrf = Arrays.copyOf(arrf, n);
            Arrays.sort(arrf);
            this.mGnssPowerMetrics.reportSignalQuality(arrf, n);
            if (n < 4) {
                return;
            }
            if ((double)arrf[n - 4] > 0.0) {
                double d = 0.0;
                for (int i = n - 4; i < n; ++i) {
                    d += (double)arrf[i];
                }
                this.topFourAverageCn0Statistics.addItem(d /= 4.0);
            }
            return;
        }
        if (n == 0) {
            this.mGnssPowerMetrics.reportSignalQuality(null, 0);
        }
    }

    public void logConstellationType(int n) {
        Object object = this.mConstellationTypes;
        if (n >= ((boolean[])object).length) {
            String string2 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("Constellation type ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" is not valid.");
            Log.e(string2, ((StringBuilder)object).toString());
            return;
        }
        object[n] = true;
    }

    public void logMissedReports(int n, int n2) {
        if ((n2 = n2 / Math.max(1000, n) - 1) > 0) {
            for (n = 0; n < n2; ++n) {
                this.locationFailureStatistics.addItem(1.0);
            }
        }
    }

    public void logPositionAccuracyMeters(float f) {
        this.positionAccuracyMeterStatistics.addItem(f);
    }

    public void logReceivedLocationStatus(boolean bl) {
        if (!bl) {
            this.locationFailureStatistics.addItem(1.0);
            return;
        }
        this.locationFailureStatistics.addItem(0.0);
    }

    public void logTimeToFirstFixMilliSecs(int n) {
        this.timeToFirstFixSecStatistics.addItem(n / 1000);
    }

    public void resetConstellationTypes() {
        this.mConstellationTypes = new boolean[8];
    }

    private class GnssPowerMetrics {
        public static final double POOR_TOP_FOUR_AVG_CN0_THRESHOLD_DB_HZ = 20.0;
        private static final double REPORTING_THRESHOLD_DB_HZ = 1.0;
        private final IBatteryStats mBatteryStats;
        private double mLastAverageCn0;
        private int mLastSignalLevel;

        public GnssPowerMetrics(IBatteryStats iBatteryStats) {
            this.mBatteryStats = iBatteryStats;
            this.mLastAverageCn0 = -100.0;
            this.mLastSignalLevel = -1;
        }

        private int getSignalLevel(double d) {
            return d > 20.0;
        }

        public GnssLogsProto.PowerMetrics buildProto() {
            GnssLogsProto.PowerMetrics powerMetrics = new GnssLogsProto.PowerMetrics();
            long[] arrl = GnssMetrics.this.mGnssPowerMetrics.getGpsBatteryStats();
            if (arrl != null) {
                powerMetrics.loggingDurationMs = arrl.getLoggingDurationMs();
                powerMetrics.energyConsumedMah = (double)arrl.getEnergyConsumedMaMs() / 3600000.0;
                arrl = arrl.getTimeInGpsSignalQualityLevel();
                powerMetrics.timeInSignalQualityLevelMs = new long[arrl.length];
                for (int i = 0; i < arrl.length; ++i) {
                    powerMetrics.timeInSignalQualityLevelMs[i] = arrl[i];
                }
            }
            return powerMetrics;
        }

        public GpsBatteryStats getGpsBatteryStats() {
            try {
                GpsBatteryStats gpsBatteryStats = this.mBatteryStats.getGpsBatteryStats();
                return gpsBatteryStats;
            }
            catch (Exception exception) {
                Log.w(TAG, "Exception", exception);
                return null;
            }
        }

        public void reportSignalQuality(float[] arrf, int n) {
            double d;
            double d2 = d = 0.0;
            if (n > 0) {
                for (int i = Math.max((int)0, (int)(n - 4)); i < n; ++i) {
                    d += (double)arrf[i];
                }
                d2 = d / (double)Math.min(n, 4);
            }
            if (Math.abs(d2 - this.mLastAverageCn0) < 1.0) {
                return;
            }
            n = this.getSignalLevel(d2);
            if (n != this.mLastSignalLevel) {
                StatsLog.write(69, n);
                this.mLastSignalLevel = n;
            }
            try {
                this.mBatteryStats.noteGpsSignalQuality(n);
                this.mLastAverageCn0 = d2;
            }
            catch (Exception exception) {
                Log.w(TAG, "Exception", exception);
            }
        }
    }

    private class Statistics {
        private int count;
        private double sum;
        private double sumSquare;

        private Statistics() {
        }

        public void addItem(double d) {
            ++this.count;
            this.sum += d;
            this.sumSquare += d * d;
        }

        public int getCount() {
            return this.count;
        }

        public double getMean() {
            return this.sum / (double)this.count;
        }

        public double getStandardDeviation() {
            double d = this.sum;
            int n = this.count;
            d /= (double)n;
            double d2 = this.sumSquare / (double)n;
            if (d2 > (d *= d)) {
                return Math.sqrt(d2 - d);
            }
            return 0.0;
        }

        public void reset() {
            this.count = 0;
            this.sum = 0.0;
            this.sumSquare = 0.0;
        }
    }

}

