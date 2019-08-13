/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.util.Date;
import java.util.TimeZone;

public class CalendarAstronomer {
    public static final SolarLongitude AUTUMN_EQUINOX;
    public static final long DAY_MS = 86400000L;
    private static final double DEG_RAD = 0.017453292519943295;
    static final long EPOCH_2000_MS = 946598400000L;
    public static final MoonAge FIRST_QUARTER;
    public static final MoonAge FULL_MOON;
    public static final int HOUR_MS = 3600000;
    private static final double INVALID = Double.MIN_VALUE;
    static final double JD_EPOCH = 2447891.5;
    public static final long JULIAN_EPOCH_MS = -210866760000000L;
    public static final MoonAge LAST_QUARTER;
    public static final int MINUTE_MS = 60000;
    public static final MoonAge NEW_MOON;
    private static final double PI = 3.141592653589793;
    private static final double PI2 = 6.283185307179586;
    private static final double RAD_DEG = 57.29577951308232;
    private static final double RAD_HOUR = 3.819718634205488;
    public static final int SECOND_MS = 1000;
    public static final double SIDEREAL_DAY = 23.93446960027;
    public static final double SIDEREAL_MONTH = 27.32166;
    public static final double SIDEREAL_YEAR = 365.25636;
    public static final double SOLAR_DAY = 24.065709816;
    public static final SolarLongitude SUMMER_SOLSTICE;
    static final double SUN_E = 0.016713;
    static final double SUN_ETA_G = 4.87650757829735;
    static final double SUN_OMEGA_G = 4.935239984568769;
    public static final double SYNODIC_MONTH = 29.530588853;
    public static final double TROPICAL_YEAR = 365.242191;
    public static final SolarLongitude VERNAL_EQUINOX;
    public static final SolarLongitude WINTER_SOLSTICE;
    static final double moonA = 384401.0;
    static final double moonE = 0.0549;
    static final double moonI = 0.08980357792017056;
    static final double moonL0 = 5.556284436750021;
    static final double moonN0 = 5.559050068029439;
    static final double moonP0 = 0.6342598060246725;
    static final double moonPi = 0.016592845198710092;
    static final double moonT0 = 0.009042550854582622;
    private transient double eclipObliquity = Double.MIN_VALUE;
    private long fGmtOffset = 0L;
    private double fLatitude = 0.0;
    private double fLongitude = 0.0;
    private transient double julianCentury = Double.MIN_VALUE;
    private transient double julianDay = Double.MIN_VALUE;
    private transient double meanAnomalySun = Double.MIN_VALUE;
    private transient double moonEclipLong = Double.MIN_VALUE;
    private transient double moonLongitude = Double.MIN_VALUE;
    private transient Equatorial moonPosition = null;
    private transient double siderealT0 = Double.MIN_VALUE;
    private transient double siderealTime = Double.MIN_VALUE;
    private transient double sunLongitude = Double.MIN_VALUE;
    private long time;

    static {
        VERNAL_EQUINOX = new SolarLongitude(0.0);
        SUMMER_SOLSTICE = new SolarLongitude(1.5707963267948966);
        AUTUMN_EQUINOX = new SolarLongitude(3.141592653589793);
        WINTER_SOLSTICE = new SolarLongitude(4.71238898038469);
        NEW_MOON = new MoonAge(0.0);
        FIRST_QUARTER = new MoonAge(1.5707963267948966);
        FULL_MOON = new MoonAge(3.141592653589793);
        LAST_QUARTER = new MoonAge(4.71238898038469);
    }

    public CalendarAstronomer() {
        this(System.currentTimeMillis());
    }

    public CalendarAstronomer(double d, double d2) {
        this();
        this.fLongitude = CalendarAstronomer.normPI(d * 0.017453292519943295);
        this.fLatitude = CalendarAstronomer.normPI(0.017453292519943295 * d2);
        this.fGmtOffset = (long)(this.fLongitude * 24.0 * 3600000.0 / 6.283185307179586);
    }

    public CalendarAstronomer(long l) {
        this.time = l;
    }

    public CalendarAstronomer(Date date) {
        this(date.getTime());
    }

    private void clearCache() {
        this.julianDay = Double.MIN_VALUE;
        this.julianCentury = Double.MIN_VALUE;
        this.sunLongitude = Double.MIN_VALUE;
        this.meanAnomalySun = Double.MIN_VALUE;
        this.moonLongitude = Double.MIN_VALUE;
        this.moonEclipLong = Double.MIN_VALUE;
        this.eclipObliquity = Double.MIN_VALUE;
        this.siderealTime = Double.MIN_VALUE;
        this.siderealT0 = Double.MIN_VALUE;
        this.moonPosition = null;
    }

    private double eclipticObliquity() {
        if (this.eclipObliquity == Double.MIN_VALUE) {
            double d = (this.getJulianDay() - 2451545.0) / 36525.0;
            this.eclipObliquity = 23.439292 - 0.013004166666666666 * d - 1.6666666666666665E-7 * d * d + 5.027777777777778E-7 * d * d * d;
            this.eclipObliquity *= 0.017453292519943295;
        }
        return this.eclipObliquity;
    }

    private double getSiderealOffset() {
        if (this.siderealT0 == Double.MIN_VALUE) {
            double d = (Math.floor(this.getJulianDay() - 0.5) + 0.5 - 2451545.0) / 36525.0;
            this.siderealT0 = CalendarAstronomer.normalize(2400.051336 * d + 6.697374558 + 2.5862E-5 * d * d, 24.0);
        }
        return this.siderealT0;
    }

    private long lstToUT(double d) {
        d = CalendarAstronomer.normalize((d - this.getSiderealOffset()) * 0.9972695663, 24.0);
        long l = this.time;
        long l2 = this.fGmtOffset;
        l = (l + l2) / 86400000L;
        return (long)(3600000.0 * d) + (l * 86400000L - l2);
    }

    private static final double norm2PI(double d) {
        return CalendarAstronomer.normalize(d, 6.283185307179586);
    }

    private static final double normPI(double d) {
        return CalendarAstronomer.normalize(d + 3.141592653589793, 6.283185307179586) - 3.141592653589793;
    }

    private static final double normalize(double d, double d2) {
        return d - Math.floor(d / d2) * d2;
    }

    private static String radToDms(double d) {
        int n = (int)(d * 57.29577951308232);
        int n2 = (int)((d * 57.29577951308232 - (double)n) * 60.0);
        int n3 = (int)((57.29577951308232 * d - (double)n - (double)n2 / 60.0) * 3600.0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(n));
        stringBuilder.append("\u00b0");
        stringBuilder.append(n2);
        stringBuilder.append("'");
        stringBuilder.append(n3);
        stringBuilder.append("\"");
        return stringBuilder.toString();
    }

    private static String radToHms(double d) {
        int n = (int)(d * 3.819718634205488);
        int n2 = (int)((d * 3.819718634205488 - (double)n) * 60.0);
        int n3 = (int)((3.819718634205488 * d - (double)n - (double)n2 / 60.0) * 3600.0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(n));
        stringBuilder.append("h");
        stringBuilder.append(n2);
        stringBuilder.append("m");
        stringBuilder.append(n3);
        stringBuilder.append("s");
        return stringBuilder.toString();
    }

    private long riseOrSet(CoordFunc coordFunc, boolean bl, double d, double d2, long l) {
        long l2;
        block2 : {
            Equatorial equatorial;
            long l3;
            double d3;
            double d4 = Math.tan(this.fLatitude);
            int n = 0;
            do {
                equatorial = coordFunc.eval();
                d3 = Math.acos(-d4 * Math.tan(equatorial.declination));
                if (bl) {
                    d3 = 6.283185307179586 - d3;
                }
                l3 = this.lstToUT((d3 + equatorial.ascension) * 24.0 / 6.283185307179586);
                l2 = this.time;
                this.setTime(l3);
            } while (++n < 5 && Math.abs(l3 - l2) > l);
            d3 = Math.cos(equatorial.declination);
            d4 = Math.acos(Math.sin(this.fLatitude) / d3);
            l = (long)(240.0 * Math.asin(Math.sin(d / 2.0 + d2) / Math.sin(d4)) * 57.29577951308232 / d3 * 1000.0);
            l2 = this.time;
            if (!bl) break block2;
            l = -l;
        }
        return l2 + l;
    }

    private long timeOfAngle(AngleFunc angleFunc, double d, double d2, long l, boolean bl) {
        double d3;
        double d4 = angleFunc.eval();
        double d5 = CalendarAstronomer.norm2PI(d - d4);
        double d6 = bl ? 0.0 : -6.283185307179586;
        d6 = d3 = (d6 + d5) * (d2 * 8.64E7) / 6.283185307179586;
        long l2 = this.time;
        this.setTime(this.time + (long)d3);
        do {
            double d7 = angleFunc.eval();
            d3 = Math.abs(d3 / CalendarAstronomer.normPI(d7 - d4));
            d3 = CalendarAstronomer.normPI(d - d7) * d3;
            if (Math.abs(d3) > Math.abs(d6)) {
                long l3 = (long)(8.64E7 * d2 / 8.0);
                if (!bl) {
                    l3 = -l3;
                }
                this.setTime(l3 + l2);
                return this.timeOfAngle(angleFunc, d, d2, l, bl);
            }
            d6 = d3;
            this.setTime(this.time + (long)d3);
            if (!(Math.abs(d3) > (double)l)) {
                return this.time;
            }
            d4 = d7;
        } while (true);
    }

    private double trueAnomaly(double d, double d2) {
        double d3;
        double d4 = d;
        do {
            d3 = d4 - Math.sin(d4) * d2 - d;
            d4 -= d3 / (1.0 - Math.cos(d4) * d2);
        } while (Math.abs(d3) > 1.0E-5);
        return Math.atan(Math.tan(d4 / 2.0) * Math.sqrt((d2 + 1.0) / (1.0 - d2))) * 2.0;
    }

    public final Equatorial eclipticToEquatorial(double d) {
        return this.eclipticToEquatorial(d, 0.0);
    }

    public final Equatorial eclipticToEquatorial(double d, double d2) {
        double d3 = this.eclipticObliquity();
        double d4 = Math.sin(d3);
        d3 = Math.cos(d3);
        double d5 = Math.sin(d);
        d = Math.cos(d);
        double d6 = Math.sin(d2);
        double d7 = Math.cos(d2);
        return new Equatorial(Math.atan2(d5 * d3 - Math.tan(d2) * d4, d), Math.asin(d6 * d3 + d7 * d4 * d5));
    }

    public final Equatorial eclipticToEquatorial(Ecliptic ecliptic) {
        return this.eclipticToEquatorial(ecliptic.longitude, ecliptic.latitude);
    }

    public Horizon eclipticToHorizon(double d) {
        Equatorial equatorial = this.eclipticToEquatorial(d);
        double d2 = this.getLocalSidereal() * 3.141592653589793 / 12.0 - equatorial.ascension;
        d = Math.sin(d2);
        double d3 = Math.cos(d2);
        double d4 = Math.sin(equatorial.declination);
        double d5 = Math.cos(equatorial.declination);
        double d6 = Math.sin(this.fLatitude);
        d2 = Math.cos(this.fLatitude);
        d3 = Math.asin(d4 * d6 + d5 * d2 * d3);
        return new Horizon(Math.atan2(-d5 * d2 * d, d4 - Math.sin(d3) * d6), d3);
    }

    public Date getDate() {
        return new Date(this.time);
    }

    public double getGreenwichSidereal() {
        if (this.siderealTime == Double.MIN_VALUE) {
            double d = CalendarAstronomer.normalize((double)this.time / 3600000.0, 24.0);
            this.siderealTime = CalendarAstronomer.normalize(this.getSiderealOffset() + 1.002737909 * d, 24.0);
        }
        return this.siderealTime;
    }

    public double getJulianCentury() {
        if (this.julianCentury == Double.MIN_VALUE) {
            this.julianCentury = (this.getJulianDay() - 2415020.0) / 36525.0;
        }
        return this.julianCentury;
    }

    public double getJulianDay() {
        if (this.julianDay == Double.MIN_VALUE) {
            this.julianDay = (double)(this.time + 210866760000000L) / 8.64E7;
        }
        return this.julianDay;
    }

    public double getLocalSidereal() {
        return CalendarAstronomer.normalize(this.getGreenwichSidereal() + (double)this.fGmtOffset / 3600000.0, 24.0);
    }

    public double getMoonAge() {
        this.getMoonPosition();
        return CalendarAstronomer.norm2PI(this.moonEclipLong - this.sunLongitude);
    }

    public double getMoonPhase() {
        return (1.0 - Math.cos(this.getMoonAge())) * 0.5;
    }

    public Equatorial getMoonPosition() {
        if (this.moonPosition == null) {
            double d = this.getSunLongitude();
            double d2 = this.getJulianDay() - 2447891.5;
            double d3 = CalendarAstronomer.norm2PI(0.22997150421858628 * d2 + 5.556284436750021);
            double d4 = CalendarAstronomer.norm2PI(d3 - 0.001944368345221015 * d2 - 0.6342598060246725);
            double d5 = Math.sin((d3 - d) * 2.0 - d4) * 0.022233749341155764;
            double d6 = Math.sin(this.meanAnomalySun) * 0.003242821750205464;
            this.moonLongitude = d3 + d5 + Math.sin(d4 += d5 - d6 - Math.sin(this.meanAnomalySun) * 0.00645771823237902) * 0.10975677534091541 - d6 + Math.sin(d4 * 2.0) * 0.0037350045992678655;
            d5 = Math.sin((this.moonLongitude - d) * 2.0);
            this.moonLongitude += d5 * 0.011489502465878671;
            d = CalendarAstronomer.norm2PI(5.559050068029439 - 9.242199067718253E-4 * d2) - Math.sin(this.meanAnomalySun) * 0.0027925268031909274;
            d5 = Math.sin(this.moonLongitude - d);
            d2 = Math.cos(this.moonLongitude - d);
            this.moonEclipLong = Math.atan2(d5 * Math.cos(0.08980357792017056), d2) + d;
            d2 = Math.asin(Math.sin(0.08980357792017056) * d5);
            this.moonPosition = this.eclipticToEquatorial(this.moonEclipLong, d2);
        }
        return this.moonPosition;
    }

    public long getMoonRiseSet(boolean bl) {
        return this.riseOrSet(new CoordFunc(){

            @Override
            public Equatorial eval() {
                return CalendarAstronomer.this.getMoonPosition();
            }
        }, bl, 0.009302604913129777, 0.009890199094634533, 60000L);
    }

    public long getMoonTime(double d, boolean bl) {
        return this.timeOfAngle(new AngleFunc(){

            @Override
            public double eval() {
                return CalendarAstronomer.this.getMoonAge();
            }
        }, d, 29.530588853, 60000L, bl);
    }

    public long getMoonTime(MoonAge moonAge, boolean bl) {
        return this.getMoonTime(moonAge.value, bl);
    }

    public double getSunLongitude() {
        if (this.sunLongitude == Double.MIN_VALUE) {
            double[] arrd = this.getSunLongitude(this.getJulianDay());
            this.sunLongitude = arrd[0];
            this.meanAnomalySun = arrd[1];
        }
        return this.sunLongitude;
    }

    double[] getSunLongitude(double d) {
        d = CalendarAstronomer.norm2PI(4.87650757829735 + CalendarAstronomer.norm2PI(0.017202791632524146 * (d - 2447891.5)) - 4.935239984568769);
        return new double[]{CalendarAstronomer.norm2PI(this.trueAnomaly(d, 0.016713) + 4.935239984568769), d};
    }

    public Equatorial getSunPosition() {
        return this.eclipticToEquatorial(this.getSunLongitude(), 0.0);
    }

    public long getSunRiseSet(boolean bl) {
        long l = this.time;
        long l2 = this.time;
        long l3 = this.fGmtOffset;
        long l4 = (l2 + l3) / 86400000L;
        l2 = bl ? -6L : 6L;
        this.setTime(l2 * 3600000L + (l4 * 86400000L - l3 + 43200000L));
        l2 = this.riseOrSet(new CoordFunc(){

            @Override
            public Equatorial eval() {
                return CalendarAstronomer.this.getSunPosition();
            }
        }, bl, 0.009302604913129777, 0.009890199094634533, 5000L);
        this.setTime(l);
        return l2;
    }

    public long getSunTime(double d, boolean bl) {
        return this.timeOfAngle(new AngleFunc(){

            @Override
            public double eval() {
                return CalendarAstronomer.this.getSunLongitude();
            }
        }, d, 365.242191, 60000L, bl);
    }

    public long getSunTime(SolarLongitude solarLongitude, boolean bl) {
        return this.getSunTime(solarLongitude.value, bl);
    }

    public long getTime() {
        return this.time;
    }

    public String local(long l) {
        return new Date(l - (long)TimeZone.getDefault().getRawOffset()).toString();
    }

    public void setDate(Date date) {
        this.setTime(date.getTime());
    }

    public void setJulianDay(double d) {
        this.time = (long)(8.64E7 * d) - 210866760000000L;
        this.clearCache();
        this.julianDay = d;
    }

    public void setTime(long l) {
        this.time = l;
        this.clearCache();
    }

    private static interface AngleFunc {
        public double eval();
    }

    private static interface CoordFunc {
        public Equatorial eval();
    }

    public static final class Ecliptic {
        public final double latitude;
        public final double longitude;

        public Ecliptic(double d, double d2) {
            this.latitude = d;
            this.longitude = d2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Double.toString(this.longitude * 57.29577951308232));
            stringBuilder.append(",");
            stringBuilder.append(this.latitude * 57.29577951308232);
            return stringBuilder.toString();
        }
    }

    public static final class Equatorial {
        public final double ascension;
        public final double declination;

        public Equatorial(double d, double d2) {
            this.ascension = d;
            this.declination = d2;
        }

        public String toHmsString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(CalendarAstronomer.radToHms(this.ascension));
            stringBuilder.append(",");
            stringBuilder.append(CalendarAstronomer.radToDms(this.declination));
            return stringBuilder.toString();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Double.toString(this.ascension * 57.29577951308232));
            stringBuilder.append(",");
            stringBuilder.append(this.declination * 57.29577951308232);
            return stringBuilder.toString();
        }
    }

    public static final class Horizon {
        public final double altitude;
        public final double azimuth;

        public Horizon(double d, double d2) {
            this.altitude = d;
            this.azimuth = d2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Double.toString(this.altitude * 57.29577951308232));
            stringBuilder.append(",");
            stringBuilder.append(this.azimuth * 57.29577951308232);
            return stringBuilder.toString();
        }
    }

    private static class MoonAge {
        double value;

        MoonAge(double d) {
            this.value = d;
        }
    }

    private static class SolarLongitude {
        double value;

        SolarLongitude(double d) {
            this.value = d;
        }
    }

}

