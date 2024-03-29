/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers;

import com.android.i18n.phonenumbers.AlternateFormatsCountryCodeSet;
import com.android.i18n.phonenumbers.MetadataLoader;
import com.android.i18n.phonenumbers.Phonemetadata;
import com.android.i18n.phonenumbers.ShortNumbersRegionCodeSet;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

final class MetadataManager {
    private static final String ALTERNATE_FORMATS_FILE_PREFIX = "/com/android/i18n/phonenumbers/data/PhoneNumberAlternateFormatsProto";
    static final MetadataLoader DEFAULT_METADATA_LOADER = new MetadataLoader(){

        @Override
        public InputStream loadMetadata(String string) {
            return MetadataManager.class.getResourceAsStream(string);
        }
    };
    static final String MULTI_FILE_PHONE_NUMBER_METADATA_FILE_PREFIX = "/com/android/i18n/phonenumbers/data/PhoneNumberMetadataProto";
    private static final String SHORT_NUMBER_METADATA_FILE_PREFIX = "/com/android/i18n/phonenumbers/data/ShortNumberMetadataProto";
    static final String SINGLE_FILE_PHONE_NUMBER_METADATA_FILE_NAME = "/com/android/i18n/phonenumbers/data/SingleFilePhoneNumberMetadataProto";
    private static final Set<Integer> alternateFormatsCountryCodes;
    private static final ConcurrentHashMap<Integer, Phonemetadata.PhoneMetadata> alternateFormatsMap;
    private static final Logger logger;
    private static final ConcurrentHashMap<String, Phonemetadata.PhoneMetadata> shortNumberMetadataMap;
    private static final Set<String> shortNumberMetadataRegionCodes;

    static {
        logger = Logger.getLogger(MetadataManager.class.getName());
        alternateFormatsMap = new ConcurrentHashMap();
        shortNumberMetadataMap = new ConcurrentHashMap();
        alternateFormatsCountryCodes = AlternateFormatsCountryCodeSet.getCountryCodeSet();
        shortNumberMetadataRegionCodes = ShortNumbersRegionCodeSet.getRegionCodeSet();
    }

    private MetadataManager() {
    }

    static Phonemetadata.PhoneMetadata getAlternateFormatsForCountry(int n) {
        if (!alternateFormatsCountryCodes.contains(n)) {
            return null;
        }
        return MetadataManager.getMetadataFromMultiFilePrefix(n, alternateFormatsMap, ALTERNATE_FORMATS_FILE_PREFIX, DEFAULT_METADATA_LOADER);
    }

    static <T> Phonemetadata.PhoneMetadata getMetadataFromMultiFilePrefix(T object, ConcurrentHashMap<T, Phonemetadata.PhoneMetadata> concurrentHashMap, String object2, MetadataLoader object3) {
        Object object4 = concurrentHashMap.get(object);
        if (object4 != null) {
            return object4;
        }
        object4 = new StringBuilder();
        ((StringBuilder)object4).append((String)object2);
        ((StringBuilder)object4).append("_");
        ((StringBuilder)object4).append(object);
        object2 = ((StringBuilder)object4).toString();
        object3 = MetadataManager.getMetadataFromSingleFileName((String)object2, (MetadataLoader)object3);
        if (object3.size() > 1) {
            object4 = logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("more than one metadata in file ");
            stringBuilder.append((String)object2);
            ((Logger)object4).log(level, stringBuilder.toString());
        }
        if ((object = concurrentHashMap.putIfAbsent(object, (Phonemetadata.PhoneMetadata)(object2 = (Phonemetadata.PhoneMetadata)object3.get(0)))) == null) {
            object = object2;
        }
        return object;
    }

    private static List<Phonemetadata.PhoneMetadata> getMetadataFromSingleFileName(String string, MetadataLoader object) {
        if ((object = object.loadMetadata(string)) != null) {
            if ((object = MetadataManager.loadMetadataAndCloseInput((InputStream)object).getMetadataList()).size() != 0) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("empty metadata: ");
            ((StringBuilder)object).append(string);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("missing metadata: ");
        ((StringBuilder)object).append(string);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    static Phonemetadata.PhoneMetadata getShortNumberMetadataForRegion(String string) {
        if (!shortNumberMetadataRegionCodes.contains(string)) {
            return null;
        }
        return MetadataManager.getMetadataFromMultiFilePrefix(string, shortNumberMetadataMap, SHORT_NUMBER_METADATA_FILE_PREFIX, DEFAULT_METADATA_LOADER);
    }

    static SingleFileMetadataMaps getSingleFileMetadataMaps(AtomicReference<SingleFileMetadataMaps> atomicReference, String string, MetadataLoader metadataLoader) {
        SingleFileMetadataMaps singleFileMetadataMaps = atomicReference.get();
        if (singleFileMetadataMaps != null) {
            return singleFileMetadataMaps;
        }
        atomicReference.compareAndSet(null, SingleFileMetadataMaps.load(string, metadataLoader));
        return atomicReference.get();
    }

    static Set<String> getSupportedShortNumberRegions() {
        return Collections.unmodifiableSet(shortNumberMetadataRegionCodes);
    }

    /*
     * Exception decompiling
     */
    private static Phonemetadata.PhoneMetadataCollection loadMetadataAndCloseInput(InputStream var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 7[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    static class SingleFileMetadataMaps {
        private final Map<Integer, Phonemetadata.PhoneMetadata> countryCallingCodeToMetadata;
        private final Map<String, Phonemetadata.PhoneMetadata> regionCodeToMetadata;

        private SingleFileMetadataMaps(Map<String, Phonemetadata.PhoneMetadata> map, Map<Integer, Phonemetadata.PhoneMetadata> map2) {
            this.regionCodeToMetadata = Collections.unmodifiableMap(map);
            this.countryCallingCodeToMetadata = Collections.unmodifiableMap(map2);
        }

        static SingleFileMetadataMaps load(String object, MetadataLoader object2) {
            Object object3 = MetadataManager.getMetadataFromSingleFileName((String)object, (MetadataLoader)object2);
            object = new HashMap();
            object2 = new HashMap();
            object3 = object3.iterator();
            while (object3.hasNext()) {
                Phonemetadata.PhoneMetadata phoneMetadata = (Phonemetadata.PhoneMetadata)object3.next();
                String string = phoneMetadata.getId();
                if ("001".equals(string)) {
                    object2.put(phoneMetadata.getCountryCode(), phoneMetadata);
                    continue;
                }
                object.put(string, phoneMetadata);
            }
            return new SingleFileMetadataMaps((Map<String, Phonemetadata.PhoneMetadata>)object, (Map<Integer, Phonemetadata.PhoneMetadata>)object2);
        }

        Phonemetadata.PhoneMetadata get(int n) {
            return this.countryCallingCodeToMetadata.get(n);
        }

        Phonemetadata.PhoneMetadata get(String string) {
            return this.regionCodeToMetadata.get(string);
        }
    }

}

