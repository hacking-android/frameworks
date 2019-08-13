/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration.impl;

import android.icu.impl.duration.impl.RecordReader;
import android.icu.impl.duration.impl.RecordWriter;
import java.util.ArrayList;

public class DataRecord {
    boolean allowZero;
    String countSep;
    byte decimalHandling;
    char decimalSep;
    String digitPrefix;
    String fifteenMinutes;
    String fiveMinutes;
    byte fractionHandling;
    byte[] genders;
    String[] halfNames;
    byte[] halfPlacements;
    byte[] halfSupport;
    String[] halves;
    String[] measures;
    String[] mediumNames;
    String[] numberNames;
    byte numberSystem;
    boolean omitDualCount;
    boolean omitSingularCount;
    String[] optSuffixes;
    byte pl;
    String[][] pluralNames;
    boolean requiresDigitSeparator;
    boolean[] requiresSkipMarker;
    String[] rqdSuffixes;
    ScopeData[] scopeData;
    String[] shortNames;
    String shortUnitSep;
    String[] singularNames;
    String skippedUnitMarker;
    String[] unitSep;
    boolean[] unitSepRequiresDP;
    byte useMilliseconds;
    boolean weeksAloneOnly;
    char zero;
    byte zeroHandling;

    public static DataRecord read(String string, RecordReader object) {
        if (object.open("DataRecord")) {
            DataRecord dataRecord = new DataRecord();
            dataRecord.pl = object.namedIndex("pl", EPluralization.names);
            dataRecord.pluralNames = object.stringTable("pluralName");
            dataRecord.genders = object.namedIndexArray("gender", EGender.names);
            dataRecord.singularNames = object.stringArray("singularName");
            dataRecord.halfNames = object.stringArray("halfName");
            dataRecord.numberNames = object.stringArray("numberName");
            dataRecord.mediumNames = object.stringArray("mediumName");
            dataRecord.shortNames = object.stringArray("shortName");
            dataRecord.measures = object.stringArray("measure");
            dataRecord.rqdSuffixes = object.stringArray("rqdSuffix");
            dataRecord.optSuffixes = object.stringArray("optSuffix");
            dataRecord.halves = object.stringArray("halves");
            dataRecord.halfPlacements = object.namedIndexArray("halfPlacement", EHalfPlacement.names);
            dataRecord.halfSupport = object.namedIndexArray("halfSupport", EHalfSupport.names);
            dataRecord.fifteenMinutes = object.string("fifteenMinutes");
            dataRecord.fiveMinutes = object.string("fiveMinutes");
            dataRecord.requiresDigitSeparator = object.bool("requiresDigitSeparator");
            dataRecord.digitPrefix = object.string("digitPrefix");
            dataRecord.countSep = object.string("countSep");
            dataRecord.shortUnitSep = object.string("shortUnitSep");
            dataRecord.unitSep = object.stringArray("unitSep");
            dataRecord.unitSepRequiresDP = object.boolArray("unitSepRequiresDP");
            dataRecord.requiresSkipMarker = object.boolArray("requiresSkipMarker");
            dataRecord.numberSystem = object.namedIndex("numberSystem", ENumberSystem.names);
            dataRecord.zero = object.character("zero");
            dataRecord.decimalSep = object.character("decimalSep");
            dataRecord.omitSingularCount = object.bool("omitSingularCount");
            dataRecord.omitDualCount = object.bool("omitDualCount");
            dataRecord.zeroHandling = object.namedIndex("zeroHandling", EZeroHandling.names);
            dataRecord.decimalHandling = object.namedIndex("decimalHandling", EDecimalHandling.names);
            dataRecord.fractionHandling = object.namedIndex("fractionHandling", EFractionHandling.names);
            dataRecord.skippedUnitMarker = object.string("skippedUnitMarker");
            dataRecord.allowZero = object.bool("allowZero");
            dataRecord.weeksAloneOnly = object.bool("weeksAloneOnly");
            dataRecord.useMilliseconds = object.namedIndex("useMilliseconds", EMilliSupport.names);
            if (object.open("ScopeDataList")) {
                ScopeData scopeData;
                ArrayList<ScopeData> arrayList = new ArrayList<ScopeData>();
                while ((scopeData = ScopeData.read((RecordReader)object)) != null) {
                    arrayList.add(scopeData);
                }
                if (object.close()) {
                    dataRecord.scopeData = arrayList.toArray(new ScopeData[arrayList.size()]);
                }
            }
            if (object.close()) {
                return dataRecord;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("null data read while reading ");
            ((StringBuilder)object).append(string);
            throw new InternalError(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("did not find DataRecord while reading ");
        ((StringBuilder)object).append(string);
        throw new InternalError(((StringBuilder)object).toString());
    }

    public void write(RecordWriter recordWriter) {
        recordWriter.open("DataRecord");
        recordWriter.namedIndex("pl", EPluralization.names, this.pl);
        recordWriter.stringTable("pluralName", this.pluralNames);
        recordWriter.namedIndexArray("gender", EGender.names, this.genders);
        recordWriter.stringArray("singularName", this.singularNames);
        recordWriter.stringArray("halfName", this.halfNames);
        recordWriter.stringArray("numberName", this.numberNames);
        recordWriter.stringArray("mediumName", this.mediumNames);
        recordWriter.stringArray("shortName", this.shortNames);
        recordWriter.stringArray("measure", this.measures);
        recordWriter.stringArray("rqdSuffix", this.rqdSuffixes);
        recordWriter.stringArray("optSuffix", this.optSuffixes);
        recordWriter.stringArray("halves", this.halves);
        recordWriter.namedIndexArray("halfPlacement", EHalfPlacement.names, this.halfPlacements);
        recordWriter.namedIndexArray("halfSupport", EHalfSupport.names, this.halfSupport);
        recordWriter.string("fifteenMinutes", this.fifteenMinutes);
        recordWriter.string("fiveMinutes", this.fiveMinutes);
        recordWriter.bool("requiresDigitSeparator", this.requiresDigitSeparator);
        recordWriter.string("digitPrefix", this.digitPrefix);
        recordWriter.string("countSep", this.countSep);
        recordWriter.string("shortUnitSep", this.shortUnitSep);
        recordWriter.stringArray("unitSep", this.unitSep);
        recordWriter.boolArray("unitSepRequiresDP", this.unitSepRequiresDP);
        recordWriter.boolArray("requiresSkipMarker", this.requiresSkipMarker);
        recordWriter.namedIndex("numberSystem", ENumberSystem.names, this.numberSystem);
        recordWriter.character("zero", this.zero);
        recordWriter.character("decimalSep", this.decimalSep);
        recordWriter.bool("omitSingularCount", this.omitSingularCount);
        recordWriter.bool("omitDualCount", this.omitDualCount);
        recordWriter.namedIndex("zeroHandling", EZeroHandling.names, this.zeroHandling);
        recordWriter.namedIndex("decimalHandling", EDecimalHandling.names, this.decimalHandling);
        recordWriter.namedIndex("fractionHandling", EFractionHandling.names, this.fractionHandling);
        recordWriter.string("skippedUnitMarker", this.skippedUnitMarker);
        recordWriter.bool("allowZero", this.allowZero);
        recordWriter.bool("weeksAloneOnly", this.weeksAloneOnly);
        recordWriter.namedIndex("useMilliseconds", EMilliSupport.names, this.useMilliseconds);
        if (this.scopeData != null) {
            ScopeData[] arrscopeData;
            recordWriter.open("ScopeDataList");
            for (int i = 0; i < (arrscopeData = this.scopeData).length; ++i) {
                arrscopeData[i].write(recordWriter);
            }
            recordWriter.close();
        }
        recordWriter.close();
    }

    public static interface ECountVariant {
        public static final byte DECIMAL1 = 3;
        public static final byte DECIMAL2 = 4;
        public static final byte DECIMAL3 = 5;
        public static final byte HALF_FRACTION = 2;
        public static final byte INTEGER = 0;
        public static final byte INTEGER_CUSTOM = 1;
        public static final String[] names = new String[]{"INTEGER", "INTEGER_CUSTOM", "HALF_FRACTION", "DECIMAL1", "DECIMAL2", "DECIMAL3"};
    }

    public static interface EDecimalHandling {
        public static final byte DPAUCAL = 3;
        public static final byte DPLURAL = 0;
        public static final byte DSINGULAR = 1;
        public static final byte DSINGULAR_SUBONE = 2;
        public static final String[] names = new String[]{"DPLURAL", "DSINGULAR", "DSINGULAR_SUBONE", "DPAUCAL"};
    }

    public static interface EFractionHandling {
        public static final byte FPAUCAL = 3;
        public static final byte FPLURAL = 0;
        public static final byte FSINGULAR_PLURAL = 1;
        public static final byte FSINGULAR_PLURAL_ANDAHALF = 2;
        public static final String[] names = new String[]{"FPLURAL", "FSINGULAR_PLURAL", "FSINGULAR_PLURAL_ANDAHALF", "FPAUCAL"};
    }

    public static interface EGender {
        public static final byte F = 1;
        public static final byte M = 0;
        public static final byte N = 2;
        public static final String[] names = new String[]{"M", "F", "N"};
    }

    public static interface EHalfPlacement {
        public static final byte AFTER_FIRST = 1;
        public static final byte LAST = 2;
        public static final byte PREFIX = 0;
        public static final String[] names = new String[]{"PREFIX", "AFTER_FIRST", "LAST"};
    }

    public static interface EHalfSupport {
        public static final byte NO = 1;
        public static final byte ONE_PLUS = 2;
        public static final byte YES = 0;
        public static final String[] names = new String[]{"YES", "NO", "ONE_PLUS"};
    }

    public static interface EMilliSupport {
        public static final byte NO = 1;
        public static final byte WITH_SECONDS = 2;
        public static final byte YES = 0;
        public static final String[] names = new String[]{"YES", "NO", "WITH_SECONDS"};
    }

    public static interface ENumberSystem {
        public static final byte CHINESE_SIMPLIFIED = 2;
        public static final byte CHINESE_TRADITIONAL = 1;
        public static final byte DEFAULT = 0;
        public static final byte KOREAN = 3;
        public static final String[] names = new String[]{"DEFAULT", "CHINESE_TRADITIONAL", "CHINESE_SIMPLIFIED", "KOREAN"};
    }

    public static interface EPluralization {
        public static final byte ARABIC = 5;
        public static final byte DUAL = 2;
        public static final byte HEBREW = 4;
        public static final byte NONE = 0;
        public static final byte PAUCAL = 3;
        public static final byte PLURAL = 1;
        public static final String[] names = new String[]{"NONE", "PLURAL", "DUAL", "PAUCAL", "HEBREW", "ARABIC"};
    }

    public static interface ESeparatorVariant {
        public static final byte FULL = 2;
        public static final byte NONE = 0;
        public static final byte SHORT = 1;
        public static final String[] names = new String[]{"NONE", "SHORT", "FULL"};
    }

    public static interface ETimeDirection {
        public static final byte FUTURE = 2;
        public static final byte NODIRECTION = 0;
        public static final byte PAST = 1;
        public static final String[] names = new String[]{"NODIRECTION", "PAST", "FUTURE"};
    }

    public static interface ETimeLimit {
        public static final byte LT = 1;
        public static final byte MT = 2;
        public static final byte NOLIMIT = 0;
        public static final String[] names = new String[]{"NOLIMIT", "LT", "MT"};
    }

    public static interface EUnitVariant {
        public static final byte MEDIUM = 1;
        public static final byte PLURALIZED = 0;
        public static final byte SHORT = 2;
        public static final String[] names = new String[]{"PLURALIZED", "MEDIUM", "SHORT"};
    }

    public static interface EZeroHandling {
        public static final byte ZPLURAL = 0;
        public static final byte ZSINGULAR = 1;
        public static final String[] names = new String[]{"ZPLURAL", "ZSINGULAR"};
    }

    public static class ScopeData {
        String prefix;
        boolean requiresDigitPrefix;
        String suffix;

        public static ScopeData read(RecordReader recordReader) {
            if (recordReader.open("ScopeData")) {
                ScopeData scopeData = new ScopeData();
                scopeData.prefix = recordReader.string("prefix");
                scopeData.requiresDigitPrefix = recordReader.bool("requiresDigitPrefix");
                scopeData.suffix = recordReader.string("suffix");
                if (recordReader.close()) {
                    return scopeData;
                }
            }
            return null;
        }

        public void write(RecordWriter recordWriter) {
            recordWriter.open("ScopeData");
            recordWriter.string("prefix", this.prefix);
            recordWriter.bool("requiresDigitPrefix", this.requiresDigitPrefix);
            recordWriter.string("suffix", this.suffix);
            recordWriter.close();
        }
    }

}

