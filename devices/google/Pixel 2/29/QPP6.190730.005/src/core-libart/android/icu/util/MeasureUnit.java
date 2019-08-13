/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.CollectionSet;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.Pair;
import android.icu.impl.UResource;
import android.icu.text.UnicodeSet;
import android.icu.util.Currency;
import android.icu.util.NoUnit;
import android.icu.util.TimeUnit;
import android.icu.util.UResourceBundle;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MeasureUnit
implements Serializable {
    public static final MeasureUnit ACRE;
    public static final MeasureUnit ACRE_FOOT;
    public static final MeasureUnit AMPERE;
    public static final MeasureUnit ARC_MINUTE;
    public static final MeasureUnit ARC_SECOND;
    static final UnicodeSet ASCII;
    static final UnicodeSet ASCII_HYPHEN_DIGITS;
    public static final MeasureUnit ASTRONOMICAL_UNIT;
    public static final MeasureUnit ATMOSPHERE;
    public static final MeasureUnit BIT;
    public static final MeasureUnit BUSHEL;
    public static final MeasureUnit BYTE;
    public static final MeasureUnit CALORIE;
    public static final MeasureUnit CARAT;
    public static final MeasureUnit CELSIUS;
    public static final MeasureUnit CENTILITER;
    public static final MeasureUnit CENTIMETER;
    public static final MeasureUnit CENTURY;
    public static final MeasureUnit CUBIC_CENTIMETER;
    public static final MeasureUnit CUBIC_FOOT;
    public static final MeasureUnit CUBIC_INCH;
    public static final MeasureUnit CUBIC_KILOMETER;
    public static final MeasureUnit CUBIC_METER;
    public static final MeasureUnit CUBIC_MILE;
    public static final MeasureUnit CUBIC_YARD;
    public static final MeasureUnit CUP;
    public static final MeasureUnit CUP_METRIC;
    static Factory CURRENCY_FACTORY;
    public static final TimeUnit DAY;
    public static final MeasureUnit DECILITER;
    public static final MeasureUnit DECIMETER;
    public static final MeasureUnit DEGREE;
    public static final MeasureUnit FAHRENHEIT;
    public static final MeasureUnit FATHOM;
    public static final MeasureUnit FLUID_OUNCE;
    public static final MeasureUnit FOODCALORIE;
    public static final MeasureUnit FOOT;
    public static final MeasureUnit FURLONG;
    public static final MeasureUnit GALLON;
    public static final MeasureUnit GALLON_IMPERIAL;
    public static final MeasureUnit GENERIC_TEMPERATURE;
    public static final MeasureUnit GIGABIT;
    public static final MeasureUnit GIGABYTE;
    public static final MeasureUnit GIGAHERTZ;
    public static final MeasureUnit GIGAWATT;
    public static final MeasureUnit GRAM;
    public static final MeasureUnit G_FORCE;
    public static final MeasureUnit HECTARE;
    public static final MeasureUnit HECTOLITER;
    public static final MeasureUnit HECTOPASCAL;
    public static final MeasureUnit HERTZ;
    public static final MeasureUnit HORSEPOWER;
    public static final TimeUnit HOUR;
    public static final MeasureUnit INCH;
    public static final MeasureUnit INCH_HG;
    public static final MeasureUnit JOULE;
    public static final MeasureUnit KARAT;
    public static final MeasureUnit KELVIN;
    public static final MeasureUnit KILOBIT;
    public static final MeasureUnit KILOBYTE;
    public static final MeasureUnit KILOCALORIE;
    public static final MeasureUnit KILOGRAM;
    public static final MeasureUnit KILOHERTZ;
    public static final MeasureUnit KILOJOULE;
    public static final MeasureUnit KILOMETER;
    public static final MeasureUnit KILOMETER_PER_HOUR;
    public static final MeasureUnit KILOWATT;
    public static final MeasureUnit KILOWATT_HOUR;
    public static final MeasureUnit KNOT;
    public static final MeasureUnit LIGHT_YEAR;
    public static final MeasureUnit LITER;
    public static final MeasureUnit LITER_PER_100KILOMETERS;
    public static final MeasureUnit LITER_PER_KILOMETER;
    public static final MeasureUnit LUX;
    public static final MeasureUnit MEGABIT;
    public static final MeasureUnit MEGABYTE;
    public static final MeasureUnit MEGAHERTZ;
    public static final MeasureUnit MEGALITER;
    public static final MeasureUnit MEGAWATT;
    public static final MeasureUnit METER;
    public static final MeasureUnit METER_PER_SECOND;
    public static final MeasureUnit METER_PER_SECOND_SQUARED;
    public static final MeasureUnit METRIC_TON;
    public static final MeasureUnit MICROGRAM;
    public static final MeasureUnit MICROMETER;
    public static final MeasureUnit MICROSECOND;
    public static final MeasureUnit MILE;
    public static final MeasureUnit MILE_PER_GALLON;
    public static final MeasureUnit MILE_PER_GALLON_IMPERIAL;
    public static final MeasureUnit MILE_PER_HOUR;
    public static final MeasureUnit MILE_SCANDINAVIAN;
    public static final MeasureUnit MILLIAMPERE;
    public static final MeasureUnit MILLIBAR;
    public static final MeasureUnit MILLIGRAM;
    public static final MeasureUnit MILLIGRAM_PER_DECILITER;
    public static final MeasureUnit MILLILITER;
    public static final MeasureUnit MILLIMETER;
    public static final MeasureUnit MILLIMETER_OF_MERCURY;
    public static final MeasureUnit MILLIMOLE_PER_LITER;
    public static final MeasureUnit MILLISECOND;
    public static final MeasureUnit MILLIWATT;
    public static final TimeUnit MINUTE;
    public static final TimeUnit MONTH;
    public static final MeasureUnit NANOMETER;
    public static final MeasureUnit NANOSECOND;
    public static final MeasureUnit NAUTICAL_MILE;
    static Factory NOUNIT_FACTORY;
    public static final MeasureUnit OHM;
    public static final MeasureUnit OUNCE;
    public static final MeasureUnit OUNCE_TROY;
    public static final MeasureUnit PARSEC;
    public static final MeasureUnit PART_PER_MILLION;
    public static final MeasureUnit PERCENT;
    public static final MeasureUnit PERMILLE;
    public static final MeasureUnit PETABYTE;
    public static final MeasureUnit PICOMETER;
    public static final MeasureUnit PINT;
    public static final MeasureUnit PINT_METRIC;
    public static final MeasureUnit POINT;
    public static final MeasureUnit POUND;
    public static final MeasureUnit POUND_PER_SQUARE_INCH;
    public static final MeasureUnit QUART;
    public static final MeasureUnit RADIAN;
    public static final MeasureUnit REVOLUTION_ANGLE;
    public static final TimeUnit SECOND;
    public static final MeasureUnit SQUARE_CENTIMETER;
    public static final MeasureUnit SQUARE_FOOT;
    public static final MeasureUnit SQUARE_INCH;
    public static final MeasureUnit SQUARE_KILOMETER;
    public static final MeasureUnit SQUARE_METER;
    public static final MeasureUnit SQUARE_MILE;
    public static final MeasureUnit SQUARE_YARD;
    public static final MeasureUnit STONE;
    public static final MeasureUnit TABLESPOON;
    public static final MeasureUnit TEASPOON;
    public static final MeasureUnit TERABIT;
    public static final MeasureUnit TERABYTE;
    static Factory TIMEUNIT_FACTORY;
    public static final MeasureUnit TON;
    private static Factory UNIT_FACTORY;
    public static final MeasureUnit VOLT;
    public static final MeasureUnit WATT;
    public static final TimeUnit WEEK;
    public static final MeasureUnit YARD;
    public static final TimeUnit YEAR;
    private static final Map<String, Map<String, MeasureUnit>> cache;
    private static boolean cacheIsPopulated = false;
    private static final long serialVersionUID = -1839973855554750484L;
    private static HashMap<Pair<MeasureUnit, MeasureUnit>, MeasureUnit> unitPerUnitToSingleUnit;
    @Deprecated
    protected final String subType;
    @Deprecated
    protected final String type;

    static {
        cache = new HashMap<String, Map<String, MeasureUnit>>();
        cacheIsPopulated = false;
        ASCII = new UnicodeSet(97, 122).freeze();
        ASCII_HYPHEN_DIGITS = new UnicodeSet(45, 45, 48, 57, 97, 122).freeze();
        UNIT_FACTORY = new Factory(){

            @Override
            public MeasureUnit create(String string, String string2) {
                return new MeasureUnit(string, string2);
            }
        };
        CURRENCY_FACTORY = new Factory(){

            @Override
            public MeasureUnit create(String string, String string2) {
                return new Currency(string2);
            }
        };
        TIMEUNIT_FACTORY = new Factory(){

            @Override
            public MeasureUnit create(String string, String string2) {
                return new TimeUnit(string, string2);
            }
        };
        NOUNIT_FACTORY = new Factory(){

            @Override
            public MeasureUnit create(String string, String string2) {
                return new NoUnit(string2);
            }
        };
        G_FORCE = MeasureUnit.internalGetInstance("acceleration", "g-force");
        METER_PER_SECOND_SQUARED = MeasureUnit.internalGetInstance("acceleration", "meter-per-second-squared");
        ARC_MINUTE = MeasureUnit.internalGetInstance("angle", "arc-minute");
        ARC_SECOND = MeasureUnit.internalGetInstance("angle", "arc-second");
        DEGREE = MeasureUnit.internalGetInstance("angle", "degree");
        RADIAN = MeasureUnit.internalGetInstance("angle", "radian");
        REVOLUTION_ANGLE = MeasureUnit.internalGetInstance("angle", "revolution");
        ACRE = MeasureUnit.internalGetInstance("area", "acre");
        HECTARE = MeasureUnit.internalGetInstance("area", "hectare");
        SQUARE_CENTIMETER = MeasureUnit.internalGetInstance("area", "square-centimeter");
        SQUARE_FOOT = MeasureUnit.internalGetInstance("area", "square-foot");
        SQUARE_INCH = MeasureUnit.internalGetInstance("area", "square-inch");
        SQUARE_KILOMETER = MeasureUnit.internalGetInstance("area", "square-kilometer");
        SQUARE_METER = MeasureUnit.internalGetInstance("area", "square-meter");
        SQUARE_MILE = MeasureUnit.internalGetInstance("area", "square-mile");
        SQUARE_YARD = MeasureUnit.internalGetInstance("area", "square-yard");
        KARAT = MeasureUnit.internalGetInstance("concentr", "karat");
        MILLIGRAM_PER_DECILITER = MeasureUnit.internalGetInstance("concentr", "milligram-per-deciliter");
        MILLIMOLE_PER_LITER = MeasureUnit.internalGetInstance("concentr", "millimole-per-liter");
        PART_PER_MILLION = MeasureUnit.internalGetInstance("concentr", "part-per-million");
        PERCENT = MeasureUnit.internalGetInstance("concentr", "percent");
        PERMILLE = MeasureUnit.internalGetInstance("concentr", "permille");
        LITER_PER_100KILOMETERS = MeasureUnit.internalGetInstance("consumption", "liter-per-100kilometers");
        LITER_PER_KILOMETER = MeasureUnit.internalGetInstance("consumption", "liter-per-kilometer");
        MILE_PER_GALLON = MeasureUnit.internalGetInstance("consumption", "mile-per-gallon");
        MILE_PER_GALLON_IMPERIAL = MeasureUnit.internalGetInstance("consumption", "mile-per-gallon-imperial");
        BIT = MeasureUnit.internalGetInstance("digital", "bit");
        BYTE = MeasureUnit.internalGetInstance("digital", "byte");
        GIGABIT = MeasureUnit.internalGetInstance("digital", "gigabit");
        GIGABYTE = MeasureUnit.internalGetInstance("digital", "gigabyte");
        KILOBIT = MeasureUnit.internalGetInstance("digital", "kilobit");
        KILOBYTE = MeasureUnit.internalGetInstance("digital", "kilobyte");
        MEGABIT = MeasureUnit.internalGetInstance("digital", "megabit");
        MEGABYTE = MeasureUnit.internalGetInstance("digital", "megabyte");
        PETABYTE = MeasureUnit.internalGetInstance("digital", "petabyte");
        TERABIT = MeasureUnit.internalGetInstance("digital", "terabit");
        TERABYTE = MeasureUnit.internalGetInstance("digital", "terabyte");
        CENTURY = MeasureUnit.internalGetInstance("duration", "century");
        DAY = (TimeUnit)MeasureUnit.internalGetInstance("duration", "day");
        HOUR = (TimeUnit)MeasureUnit.internalGetInstance("duration", "hour");
        MICROSECOND = MeasureUnit.internalGetInstance("duration", "microsecond");
        MILLISECOND = MeasureUnit.internalGetInstance("duration", "millisecond");
        MINUTE = (TimeUnit)MeasureUnit.internalGetInstance("duration", "minute");
        MONTH = (TimeUnit)MeasureUnit.internalGetInstance("duration", "month");
        NANOSECOND = MeasureUnit.internalGetInstance("duration", "nanosecond");
        SECOND = (TimeUnit)MeasureUnit.internalGetInstance("duration", "second");
        WEEK = (TimeUnit)MeasureUnit.internalGetInstance("duration", "week");
        YEAR = (TimeUnit)MeasureUnit.internalGetInstance("duration", "year");
        AMPERE = MeasureUnit.internalGetInstance("electric", "ampere");
        MILLIAMPERE = MeasureUnit.internalGetInstance("electric", "milliampere");
        OHM = MeasureUnit.internalGetInstance("electric", "ohm");
        VOLT = MeasureUnit.internalGetInstance("electric", "volt");
        CALORIE = MeasureUnit.internalGetInstance("energy", "calorie");
        FOODCALORIE = MeasureUnit.internalGetInstance("energy", "foodcalorie");
        JOULE = MeasureUnit.internalGetInstance("energy", "joule");
        KILOCALORIE = MeasureUnit.internalGetInstance("energy", "kilocalorie");
        KILOJOULE = MeasureUnit.internalGetInstance("energy", "kilojoule");
        KILOWATT_HOUR = MeasureUnit.internalGetInstance("energy", "kilowatt-hour");
        GIGAHERTZ = MeasureUnit.internalGetInstance("frequency", "gigahertz");
        HERTZ = MeasureUnit.internalGetInstance("frequency", "hertz");
        KILOHERTZ = MeasureUnit.internalGetInstance("frequency", "kilohertz");
        MEGAHERTZ = MeasureUnit.internalGetInstance("frequency", "megahertz");
        ASTRONOMICAL_UNIT = MeasureUnit.internalGetInstance("length", "astronomical-unit");
        CENTIMETER = MeasureUnit.internalGetInstance("length", "centimeter");
        DECIMETER = MeasureUnit.internalGetInstance("length", "decimeter");
        FATHOM = MeasureUnit.internalGetInstance("length", "fathom");
        FOOT = MeasureUnit.internalGetInstance("length", "foot");
        FURLONG = MeasureUnit.internalGetInstance("length", "furlong");
        INCH = MeasureUnit.internalGetInstance("length", "inch");
        KILOMETER = MeasureUnit.internalGetInstance("length", "kilometer");
        LIGHT_YEAR = MeasureUnit.internalGetInstance("length", "light-year");
        METER = MeasureUnit.internalGetInstance("length", "meter");
        MICROMETER = MeasureUnit.internalGetInstance("length", "micrometer");
        MILE = MeasureUnit.internalGetInstance("length", "mile");
        MILE_SCANDINAVIAN = MeasureUnit.internalGetInstance("length", "mile-scandinavian");
        MILLIMETER = MeasureUnit.internalGetInstance("length", "millimeter");
        NANOMETER = MeasureUnit.internalGetInstance("length", "nanometer");
        NAUTICAL_MILE = MeasureUnit.internalGetInstance("length", "nautical-mile");
        PARSEC = MeasureUnit.internalGetInstance("length", "parsec");
        PICOMETER = MeasureUnit.internalGetInstance("length", "picometer");
        POINT = MeasureUnit.internalGetInstance("length", "point");
        YARD = MeasureUnit.internalGetInstance("length", "yard");
        LUX = MeasureUnit.internalGetInstance("light", "lux");
        CARAT = MeasureUnit.internalGetInstance("mass", "carat");
        GRAM = MeasureUnit.internalGetInstance("mass", "gram");
        KILOGRAM = MeasureUnit.internalGetInstance("mass", "kilogram");
        METRIC_TON = MeasureUnit.internalGetInstance("mass", "metric-ton");
        MICROGRAM = MeasureUnit.internalGetInstance("mass", "microgram");
        MILLIGRAM = MeasureUnit.internalGetInstance("mass", "milligram");
        OUNCE = MeasureUnit.internalGetInstance("mass", "ounce");
        OUNCE_TROY = MeasureUnit.internalGetInstance("mass", "ounce-troy");
        POUND = MeasureUnit.internalGetInstance("mass", "pound");
        STONE = MeasureUnit.internalGetInstance("mass", "stone");
        TON = MeasureUnit.internalGetInstance("mass", "ton");
        GIGAWATT = MeasureUnit.internalGetInstance("power", "gigawatt");
        HORSEPOWER = MeasureUnit.internalGetInstance("power", "horsepower");
        KILOWATT = MeasureUnit.internalGetInstance("power", "kilowatt");
        MEGAWATT = MeasureUnit.internalGetInstance("power", "megawatt");
        MILLIWATT = MeasureUnit.internalGetInstance("power", "milliwatt");
        WATT = MeasureUnit.internalGetInstance("power", "watt");
        ATMOSPHERE = MeasureUnit.internalGetInstance("pressure", "atmosphere");
        HECTOPASCAL = MeasureUnit.internalGetInstance("pressure", "hectopascal");
        INCH_HG = MeasureUnit.internalGetInstance("pressure", "inch-hg");
        MILLIBAR = MeasureUnit.internalGetInstance("pressure", "millibar");
        MILLIMETER_OF_MERCURY = MeasureUnit.internalGetInstance("pressure", "millimeter-of-mercury");
        POUND_PER_SQUARE_INCH = MeasureUnit.internalGetInstance("pressure", "pound-per-square-inch");
        KILOMETER_PER_HOUR = MeasureUnit.internalGetInstance("speed", "kilometer-per-hour");
        KNOT = MeasureUnit.internalGetInstance("speed", "knot");
        METER_PER_SECOND = MeasureUnit.internalGetInstance("speed", "meter-per-second");
        MILE_PER_HOUR = MeasureUnit.internalGetInstance("speed", "mile-per-hour");
        CELSIUS = MeasureUnit.internalGetInstance("temperature", "celsius");
        FAHRENHEIT = MeasureUnit.internalGetInstance("temperature", "fahrenheit");
        GENERIC_TEMPERATURE = MeasureUnit.internalGetInstance("temperature", "generic");
        KELVIN = MeasureUnit.internalGetInstance("temperature", "kelvin");
        ACRE_FOOT = MeasureUnit.internalGetInstance("volume", "acre-foot");
        BUSHEL = MeasureUnit.internalGetInstance("volume", "bushel");
        CENTILITER = MeasureUnit.internalGetInstance("volume", "centiliter");
        CUBIC_CENTIMETER = MeasureUnit.internalGetInstance("volume", "cubic-centimeter");
        CUBIC_FOOT = MeasureUnit.internalGetInstance("volume", "cubic-foot");
        CUBIC_INCH = MeasureUnit.internalGetInstance("volume", "cubic-inch");
        CUBIC_KILOMETER = MeasureUnit.internalGetInstance("volume", "cubic-kilometer");
        CUBIC_METER = MeasureUnit.internalGetInstance("volume", "cubic-meter");
        CUBIC_MILE = MeasureUnit.internalGetInstance("volume", "cubic-mile");
        CUBIC_YARD = MeasureUnit.internalGetInstance("volume", "cubic-yard");
        CUP = MeasureUnit.internalGetInstance("volume", "cup");
        CUP_METRIC = MeasureUnit.internalGetInstance("volume", "cup-metric");
        DECILITER = MeasureUnit.internalGetInstance("volume", "deciliter");
        FLUID_OUNCE = MeasureUnit.internalGetInstance("volume", "fluid-ounce");
        GALLON = MeasureUnit.internalGetInstance("volume", "gallon");
        GALLON_IMPERIAL = MeasureUnit.internalGetInstance("volume", "gallon-imperial");
        HECTOLITER = MeasureUnit.internalGetInstance("volume", "hectoliter");
        LITER = MeasureUnit.internalGetInstance("volume", "liter");
        MEGALITER = MeasureUnit.internalGetInstance("volume", "megaliter");
        MILLILITER = MeasureUnit.internalGetInstance("volume", "milliliter");
        PINT = MeasureUnit.internalGetInstance("volume", "pint");
        PINT_METRIC = MeasureUnit.internalGetInstance("volume", "pint-metric");
        QUART = MeasureUnit.internalGetInstance("volume", "quart");
        TABLESPOON = MeasureUnit.internalGetInstance("volume", "tablespoon");
        TEASPOON = MeasureUnit.internalGetInstance("volume", "teaspoon");
        unitPerUnitToSingleUnit = new HashMap();
        unitPerUnitToSingleUnit.put(Pair.of(LITER, KILOMETER), LITER_PER_KILOMETER);
        unitPerUnitToSingleUnit.put(Pair.of(POUND, SQUARE_INCH), POUND_PER_SQUARE_INCH);
        unitPerUnitToSingleUnit.put(Pair.of(MILE, HOUR), MILE_PER_HOUR);
        unitPerUnitToSingleUnit.put(Pair.of(MILLIGRAM, DECILITER), MILLIGRAM_PER_DECILITER);
        unitPerUnitToSingleUnit.put(Pair.of(MILE, GALLON_IMPERIAL), MILE_PER_GALLON_IMPERIAL);
        unitPerUnitToSingleUnit.put(Pair.of(KILOMETER, HOUR), KILOMETER_PER_HOUR);
        unitPerUnitToSingleUnit.put(Pair.of(MILE, GALLON), MILE_PER_GALLON);
        unitPerUnitToSingleUnit.put(Pair.of(METER, SECOND), METER_PER_SECOND);
    }

    @Deprecated
    protected MeasureUnit(String string, String string2) {
        this.type = string;
        this.subType = string2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    protected static MeasureUnit addUnit(String object, String string, Factory factory) {
        synchronized (MeasureUnit.class) {
            Object object2;
            void var1_1;
            Object object3;
            Map<String, MeasureUnit> map = cache.get(object);
            if (map == null) {
                object2 = cache;
                object3 = new HashMap();
                map = object3;
                object2.put((String)object, object3);
            } else {
                object = map.entrySet().iterator().next().getValue().type;
            }
            object3 = object2 = map.get(var1_1);
            if (object2 == null) {
                void var2_2;
                object3 = object = var2_2.create((String)object, (String)var1_1);
                map.put((String)var1_1, (MeasureUnit)object);
            }
            return object3;
        }
    }

    public static Set<MeasureUnit> getAvailable() {
        synchronized (MeasureUnit.class) {
            HashSet<MeasureUnit> hashSet = new HashSet<MeasureUnit>();
            Object object = new HashSet(MeasureUnit.getAvailableTypes());
            Iterator<String> iterator = ((HashSet)object).iterator();
            while (iterator.hasNext()) {
                object = MeasureUnit.getAvailable(iterator.next()).iterator();
                while (object.hasNext()) {
                    hashSet.add((MeasureUnit)object.next());
                }
            }
            hashSet = Collections.unmodifiableSet(hashSet);
            return hashSet;
        }
    }

    public static Set<MeasureUnit> getAvailable(String set) {
        synchronized (MeasureUnit.class) {
            MeasureUnit.populateCache();
            set = cache.get(set);
            if (set == null) {
                set = Collections.emptySet();
            } else {
                CollectionSet collectionSet = new CollectionSet(set.values());
                set = Collections.unmodifiableSet(collectionSet);
            }
            return set;
        }
    }

    public static Set<String> getAvailableTypes() {
        synchronized (MeasureUnit.class) {
            MeasureUnit.populateCache();
            Set<String> set = Collections.unmodifiableSet(cache.keySet());
            return set;
        }
    }

    @Deprecated
    public static MeasureUnit internalGetInstance(String string, String string2) {
        if (string != null && string2 != null) {
            if (!("currency".equals(string) || ASCII.containsAll(string) && ASCII_HYPHEN_DIGITS.containsAll(string2))) {
                throw new IllegalArgumentException("The type or subType are invalid.");
            }
            Factory factory = "currency".equals(string) ? CURRENCY_FACTORY : ("duration".equals(string) ? TIMEUNIT_FACTORY : ("none".equals(string) ? NOUNIT_FACTORY : UNIT_FACTORY));
            return MeasureUnit.addUnit(string, string2, factory);
        }
        throw new NullPointerException("Type and subType must be non-null");
    }

    private static void populateCache() {
        if (cacheIsPopulated) {
            return;
        }
        cacheIsPopulated = true;
        ((ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/unit", "en")).getAllItemsWithFallback("units", new MeasureUnitSink());
        ((ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "currencyNumericCodes", ICUResourceBundle.ICU_DATA_CLASS_LOADER)).getAllItemsWithFallback("codeMap", new CurrencyNumericCodeSink());
    }

    @Deprecated
    public static MeasureUnit resolveUnitPerUnit(MeasureUnit measureUnit, MeasureUnit measureUnit2) {
        return unitPerUnitToSingleUnit.get(Pair.of(measureUnit, measureUnit2));
    }

    private Object writeReplace() throws ObjectStreamException {
        return new MeasureUnitProxy(this.type, this.subType);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof MeasureUnit)) {
            return false;
        }
        object = (MeasureUnit)object;
        if (!this.type.equals(((MeasureUnit)object).type) || !this.subType.equals(((MeasureUnit)object).subType)) {
            bl = false;
        }
        return bl;
    }

    public String getSubtype() {
        return this.subType;
    }

    public String getType() {
        return this.type;
    }

    public int hashCode() {
        return this.type.hashCode() * 31 + this.subType.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.type);
        stringBuilder.append("-");
        stringBuilder.append(this.subType);
        return stringBuilder.toString();
    }

    private static final class CurrencyNumericCodeSink
    extends UResource.Sink {
        private CurrencyNumericCodeSink() {
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                MeasureUnit.internalGetInstance("currency", key.toString());
                ++n;
            }
        }
    }

    @Deprecated
    protected static interface Factory {
        @Deprecated
        public MeasureUnit create(String var1, String var2);
    }

    static final class MeasureUnitProxy
    implements Externalizable {
        private static final long serialVersionUID = -3910681415330989598L;
        private String subType;
        private String type;

        public MeasureUnitProxy() {
        }

        public MeasureUnitProxy(String string, String string2) {
            this.type = string;
            this.subType = string2;
        }

        private Object readResolve() throws ObjectStreamException {
            return MeasureUnit.internalGetInstance(this.type, this.subType);
        }

        @Override
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            objectInput.readByte();
            this.type = objectInput.readUTF();
            this.subType = objectInput.readUTF();
            short s = objectInput.readShort();
            if (s > 0) {
                objectInput.read(new byte[s], 0, s);
            }
        }

        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(0);
            objectOutput.writeUTF(this.type);
            objectOutput.writeUTF(this.subType);
            objectOutput.writeShort(0);
        }
    }

    private static final class MeasureUnitSink
    extends UResource.Sink {
        private MeasureUnitSink() {
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (!key.contentEquals("compound") && !key.contentEquals("coordinate")) {
                    String string = key.toString();
                    UResource.Table table2 = value.getTable();
                    int n2 = 0;
                    while (table2.getKeyAndValue(n2, key, value)) {
                        MeasureUnit.internalGetInstance(string, key.toString());
                        ++n2;
                    }
                }
                ++n;
            }
        }
    }

}

