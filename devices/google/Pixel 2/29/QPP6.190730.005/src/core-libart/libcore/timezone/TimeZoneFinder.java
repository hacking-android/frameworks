/*
 * Decompiled with CFR 0.145.
 */
package libcore.timezone;

import android.icu.util.TimeZone;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import libcore.timezone.CountryTimeZones;
import libcore.timezone.CountryZonesFinder;
import libcore.timezone.TimeZoneDataFiles;
import libcore.timezone._$$Lambda$TimeZoneFinder$ReaderSupplier$Q2dnwJWibh29nQ77BtDmdnZnbdI;
import libcore.timezone._$$Lambda$TimeZoneFinder$ReaderSupplier$gDAfECMWS_ohJ_Rfk1HN7JyDSJA;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public final class TimeZoneFinder {
    private static final String COUNTRY_CODE_ATTRIBUTE = "code";
    private static final String COUNTRY_ELEMENT = "country";
    private static final String COUNTRY_ZONES_ELEMENT = "countryzones";
    private static final String DEFAULT_TIME_ZONE_ID_ATTRIBUTE = "default";
    private static final String EVER_USES_UTC_ATTRIBUTE = "everutc";
    private static final String FALSE_ATTRIBUTE_VALUE = "n";
    private static final String IANA_VERSION_ATTRIBUTE = "ianaversion";
    private static final String TIMEZONES_ELEMENT = "timezones";
    private static final String TRUE_ATTRIBUTE_VALUE = "y";
    private static final String TZLOOKUP_FILE_NAME = "tzlookup.xml";
    private static final String ZONE_ID_ELEMENT = "id";
    private static final String ZONE_NOT_USED_AFTER_ATTRIBUTE = "notafter";
    private static final String ZONE_SHOW_IN_PICKER_ATTRIBUTE = "picker";
    private static TimeZoneFinder instance;
    private CountryTimeZones lastCountryTimeZones;
    private final ReaderSupplier xmlSource;

    private TimeZoneFinder(ReaderSupplier readerSupplier) {
        this.xmlSource = readerSupplier;
    }

    private static void checkOnEndTag(XmlPullParser xmlPullParser, String charSequence) throws XmlPullParserException {
        if (xmlPullParser.getEventType() == 3 && xmlPullParser.getName().equals(charSequence)) {
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unexpected tag encountered: ");
        ((StringBuilder)charSequence).append(xmlPullParser.getPositionDescription());
        throw new XmlPullParserException(((StringBuilder)charSequence).toString());
    }

    private static String consumeText(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        int n = xmlPullParser.next();
        if (n == 4) {
            CharSequence charSequence = xmlPullParser.getText();
            n = xmlPullParser.next();
            if (n == 3) {
                return charSequence;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unexpected nested tag or end of document when expecting text: type=");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(" at ");
            ((StringBuilder)charSequence).append(xmlPullParser.getPositionDescription());
            throw new XmlPullParserException(((StringBuilder)charSequence).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Text not found. Found type=");
        stringBuilder.append(n);
        stringBuilder.append(" at ");
        stringBuilder.append(xmlPullParser.getPositionDescription());
        throw new XmlPullParserException(stringBuilder.toString());
    }

    private static void consumeUntilEndTag(XmlPullParser xmlPullParser, String charSequence) throws IOException, XmlPullParserException {
        int n;
        if (xmlPullParser.getEventType() == 3 && ((String)charSequence).equals(xmlPullParser.getName())) {
            return;
        }
        int n2 = n = xmlPullParser.getDepth();
        if (xmlPullParser.getEventType() == 2) {
            n2 = n - 1;
        }
        while (xmlPullParser.getEventType() != 1) {
            int n3 = xmlPullParser.next();
            n = xmlPullParser.getDepth();
            if (n >= n2) {
                if (n != n2 || n3 != 3) continue;
                if (((String)charSequence).equals(xmlPullParser.getName())) {
                    return;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unexpected eng tag: ");
                ((StringBuilder)charSequence).append(xmlPullParser.getPositionDescription());
                throw new XmlPullParserException(((StringBuilder)charSequence).toString());
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unexpected depth while looking for end tag: ");
            ((StringBuilder)charSequence).append(xmlPullParser.getPositionDescription());
            throw new XmlPullParserException(((StringBuilder)charSequence).toString());
        }
        throw new XmlPullParserException("Unexpected end of document");
    }

    public static TimeZoneFinder createInstance(String string) throws IOException {
        return new TimeZoneFinder(ReaderSupplier.forFile(string, StandardCharsets.UTF_8));
    }

    public static TimeZoneFinder createInstanceForTests(String string) {
        return new TimeZoneFinder(ReaderSupplier.forString(string));
    }

    public static TimeZoneFinder createInstanceWithFallback(String ... arrstring) {
        IOException iOException = null;
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            Object object = arrstring[i];
            try {
                object = TimeZoneFinder.createInstance((String)object);
                return object;
            }
            catch (IOException iOException2) {
                if (iOException != null) {
                    iOException2.addSuppressed(iOException);
                }
                iOException = iOException2;
                continue;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No valid file found in set: ");
        stringBuilder.append(Arrays.toString(arrstring));
        stringBuilder.append(" Printing exceptions and falling back to empty map.");
        System.logE((String)stringBuilder.toString(), (Throwable)iOException);
        return TimeZoneFinder.createInstanceForTests("<timezones><countryzones /></timezones>");
    }

    private static List<String> extractTimeZoneIds(List<CountryTimeZones.TimeZoneMapping> object) {
        ArrayList<String> arrayList = new ArrayList<String>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(((CountryTimeZones.TimeZoneMapping)object.next()).timeZoneId);
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static boolean findOptionalStartTag(XmlPullParser xmlPullParser, String string) throws IOException, XmlPullParserException {
        return TimeZoneFinder.findStartTag(xmlPullParser, string, false);
    }

    private static void findRequiredStartTag(XmlPullParser xmlPullParser, String string) throws IOException, XmlPullParserException {
        TimeZoneFinder.findStartTag(xmlPullParser, string, true);
    }

    private static boolean findStartTag(XmlPullParser object, String string, boolean bl) throws IOException, XmlPullParserException {
        int n;
        while ((n = object.next()) != 1) {
            if (n != 2) {
                if (n != 3) continue;
                if (!bl) {
                    return false;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("No child element found with name ");
                ((StringBuilder)object).append(string);
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
            String string2 = object.getName();
            if (string.equals(string2)) {
                return true;
            }
            object.next();
            TimeZoneFinder.consumeUntilEndTag((XmlPullParser)object, string2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unexpected end of document while looking for ");
        ((StringBuilder)object).append(string);
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static TimeZoneFinder getInstance() {
        synchronized (TimeZoneFinder.class) {
            if (instance == null) {
                instance = TimeZoneFinder.createInstanceWithFallback(TimeZoneDataFiles.getTimeZoneFilePaths(TZLOOKUP_FILE_NAME));
            }
            return instance;
        }
    }

    static String normalizeCountryIso(String string) {
        return string.toLowerCase(Locale.US);
    }

    private static Boolean parseBooleanAttribute(XmlPullParser xmlPullParser, String string, Boolean serializable) throws XmlPullParserException {
        String string2 = xmlPullParser.getAttributeValue(null, string);
        if (string2 == null) {
            return serializable;
        }
        boolean bl = TRUE_ATTRIBUTE_VALUE.equals(string2);
        if (!bl && !FALSE_ATTRIBUTE_VALUE.equals(string2)) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Attribute \"");
            ((StringBuilder)serializable).append(string);
            ((StringBuilder)serializable).append("\" is not \"y\" or \"n\": ");
            ((StringBuilder)serializable).append(xmlPullParser.getPositionDescription());
            throw new XmlPullParserException(((StringBuilder)serializable).toString());
        }
        return bl;
    }

    private static Long parseLongAttribute(XmlPullParser xmlPullParser, String string, Long l) throws XmlPullParserException {
        long l2;
        String string2 = xmlPullParser.getAttributeValue(null, string);
        if (string2 == null) {
            return l;
        }
        try {
            l2 = Long.parseLong(string2);
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attribute \"");
            stringBuilder.append(string);
            stringBuilder.append("\" is not a long value: ");
            stringBuilder.append(xmlPullParser.getPositionDescription());
            throw new XmlPullParserException(stringBuilder.toString());
        }
        return l2;
    }

    private static List<CountryTimeZones.TimeZoneMapping> parseTimeZoneMappings(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        ArrayList<CountryTimeZones.TimeZoneMapping> arrayList = new ArrayList<CountryTimeZones.TimeZoneMapping>();
        while (TimeZoneFinder.findOptionalStartTag(xmlPullParser, ZONE_ID_ELEMENT)) {
            boolean bl = TimeZoneFinder.parseBooleanAttribute(xmlPullParser, ZONE_SHOW_IN_PICKER_ATTRIBUTE, true);
            Serializable serializable = TimeZoneFinder.parseLongAttribute(xmlPullParser, ZONE_NOT_USED_AFTER_ATTRIBUTE, null);
            String string = TimeZoneFinder.consumeText(xmlPullParser);
            TimeZoneFinder.checkOnEndTag(xmlPullParser, ZONE_ID_ELEMENT);
            if (string != null && string.length() != 0) {
                arrayList.add(new CountryTimeZones.TimeZoneMapping(string, bl, (Long)serializable));
                continue;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Missing text for id): ");
            ((StringBuilder)serializable).append(xmlPullParser.getPositionDescription());
            throw new XmlPullParserException(((StringBuilder)serializable).toString());
        }
        return Collections.unmodifiableList(arrayList);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static boolean processCountryZones(XmlPullParser var0, TimeZonesProcessor var1_1) throws IOException, XmlPullParserException {
        block6 : {
            block7 : {
                while (TimeZoneFinder.findOptionalStartTag(var0, "country") != false) {
                    if (var1_1 == null) {
                        TimeZoneFinder.consumeUntilEndTag(var0, "country");
                    } else {
                        var2_2 = var0.getAttributeValue(null, "code");
                        if (var2_2 == null || var2_2.isEmpty()) break block6;
                        var3_3 = var0.getAttributeValue(null, "default");
                        if (var3_3 != null && !var3_3.isEmpty()) {
                            var4_4 = TimeZoneFinder.parseBooleanAttribute(var0, "everutc", null);
                            if (var4_4 == null) {
                                var1_1 = new StringBuilder();
                                var1_1.append("Unable to find UTC hint attribute (everutc): ");
                                var1_1.append(var0.getPositionDescription());
                                throw new XmlPullParserException(var1_1.toString());
                            }
                            var5_5 = var0.getPositionDescription();
                            var6_6 = TimeZoneFinder.parseTimeZoneMappings(var0);
                            if (!var1_1.processCountryZones(var2_2, var3_3, var4_4, var6_6, var5_5)) {
                                return false;
                            } else {
                                ** GOTO lbl22
                            }
                        }
                        break block7;
                    }
lbl22: // 3 sources:
                    TimeZoneFinder.checkOnEndTag(var0, "country");
                }
                return true;
            }
            var1_1 = new StringBuilder();
            var1_1.append("Unable to find default time zone ID: ");
            var1_1.append(var0.getPositionDescription());
            throw new XmlPullParserException(var1_1.toString());
        }
        var1_1 = new StringBuilder();
        var1_1.append("Unable to find country code: ");
        var1_1.append(var0.getPositionDescription());
        throw new XmlPullParserException(var1_1.toString());
    }

    private void processXml(TimeZonesProcessor timeZonesProcessor) throws XmlPullParserException, IOException {
        block13 : {
            Reader reader;
            Object object;
            block11 : {
                block12 : {
                    boolean bl;
                    block9 : {
                        block10 : {
                            reader = this.xmlSource.get();
                            try {
                                object = XmlPullParserFactory.newInstance();
                                ((XmlPullParserFactory)object).setNamespaceAware(false);
                                object = ((XmlPullParserFactory)object).newPullParser();
                                object.setInput(reader);
                                TimeZoneFinder.findRequiredStartTag((XmlPullParser)object, TIMEZONES_ELEMENT);
                                bl = timeZonesProcessor.processHeader(object.getAttributeValue(null, IANA_VERSION_ATTRIBUTE));
                                if (bl) break block9;
                                if (reader == null) break block10;
                            }
                            catch (Throwable throwable) {
                                try {
                                    throw throwable;
                                }
                                catch (Throwable throwable2) {
                                    if (reader != null) {
                                        try {
                                            reader.close();
                                        }
                                        catch (Throwable throwable3) {
                                            throwable.addSuppressed(throwable3);
                                        }
                                    }
                                    throw throwable2;
                                }
                            }
                            reader.close();
                        }
                        return;
                    }
                    TimeZoneFinder.findRequiredStartTag((XmlPullParser)object, COUNTRY_ZONES_ELEMENT);
                    bl = TimeZoneFinder.processCountryZones((XmlPullParser)object, timeZonesProcessor);
                    if (bl) break block11;
                    if (reader == null) break block12;
                    reader.close();
                }
                return;
            }
            TimeZoneFinder.checkOnEndTag((XmlPullParser)object, COUNTRY_ZONES_ELEMENT);
            object.next();
            TimeZoneFinder.consumeUntilEndTag((XmlPullParser)object, TIMEZONES_ELEMENT);
            TimeZoneFinder.checkOnEndTag((XmlPullParser)object, TIMEZONES_ELEMENT);
            if (reader == null) break block13;
            reader.close();
        }
    }

    public CountryZonesFinder getCountryZonesFinder() {
        Object object = new CountryZonesLookupExtractor();
        try {
            this.processXml((TimeZonesProcessor)object);
            object = ((CountryZonesLookupExtractor)object).getCountryZonesLookup();
            return object;
        }
        catch (IOException | XmlPullParserException exception) {
            System.logW((String)"Error reading country zones ", (Throwable)exception);
            return null;
        }
    }

    public String getIanaVersion() {
        Object object = new IanaVersionExtractor();
        try {
            this.processXml((TimeZonesProcessor)object);
            object = ((IanaVersionExtractor)object).getIanaVersion();
            return object;
        }
        catch (IOException | XmlPullParserException exception) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public CountryTimeZones lookupCountryTimeZones(String object) {
        // MONITORENTER : this
        if (this.lastCountryTimeZones != null && this.lastCountryTimeZones.isForCountryCode((String)object)) {
            object = this.lastCountryTimeZones;
            // MONITOREXIT : this
            return object;
        }
        // MONITOREXIT : this
        object = new SelectiveCountryTimeZonesExtractor((String)object);
        try {
            this.processXml((TimeZonesProcessor)object);
            object = ((SelectiveCountryTimeZonesExtractor)object).getValidatedCountryTimeZones();
            if (object == null) {
                return null;
            }
            // MONITORENTER : this
            this.lastCountryTimeZones = object;
        }
        catch (IOException | XmlPullParserException exception) {
            System.logW((String)"Error reading country zones ", (Throwable)exception);
            return null;
        }
        // MONITOREXIT : this
        return object;
    }

    public String lookupDefaultTimeZoneIdByCountry(String object) {
        object = (object = this.lookupCountryTimeZones((String)object)) == null ? null : ((CountryTimeZones)object).getDefaultTimeZoneId();
        return object;
    }

    public TimeZone lookupTimeZoneByCountryAndOffset(String object, int n, boolean bl, long l, TimeZone object2) {
        CountryTimeZones countryTimeZones = this.lookupCountryTimeZones((String)object);
        object = null;
        if (countryTimeZones == null) {
            return null;
        }
        if ((object2 = countryTimeZones.lookupByOffsetWithBias(n, bl, l, (TimeZone)object2)) != null) {
            object = ((CountryTimeZones.OffsetResult)object2).mTimeZone;
        }
        return object;
    }

    public List<String> lookupTimeZoneIdsByCountry(String list) {
        list = (list = this.lookupCountryTimeZones((String)((Object)list))) == null ? null : TimeZoneFinder.extractTimeZoneIds(((CountryTimeZones)((Object)list)).getTimeZoneMappings());
        return list;
    }

    public List<TimeZone> lookupTimeZonesByCountry(String list) {
        list = (list = this.lookupCountryTimeZones((String)((Object)list))) == null ? null : ((CountryTimeZones)((Object)list)).getIcuTimeZones();
        return list;
    }

    public void validate() throws IOException {
        try {
            TimeZonesValidator timeZonesValidator = new TimeZonesValidator();
            this.processXml(timeZonesValidator);
            return;
        }
        catch (XmlPullParserException xmlPullParserException) {
            throw new IOException("Parsing error", xmlPullParserException);
        }
    }

    private static class CountryZonesLookupExtractor
    implements TimeZonesProcessor {
        private List<CountryTimeZones> countryTimeZonesList = new ArrayList<CountryTimeZones>(250);

        private CountryZonesLookupExtractor() {
        }

        CountryZonesFinder getCountryZonesLookup() {
            return new CountryZonesFinder(this.countryTimeZonesList);
        }

        @Override
        public boolean processCountryZones(String object, String string, boolean bl, List<CountryTimeZones.TimeZoneMapping> list, String string2) throws XmlPullParserException {
            object = CountryTimeZones.createValidated((String)object, string, bl, list, string2);
            this.countryTimeZonesList.add((CountryTimeZones)object);
            return true;
        }
    }

    private static class IanaVersionExtractor
    implements TimeZonesProcessor {
        private String ianaVersion;

        private IanaVersionExtractor() {
        }

        public String getIanaVersion() {
            return this.ianaVersion;
        }

        @Override
        public boolean processHeader(String string) throws XmlPullParserException {
            this.ianaVersion = string;
            return false;
        }
    }

    private static interface ReaderSupplier {
        public static ReaderSupplier forFile(String string, Charset object) throws IOException {
            Path path = Paths.get(string, new String[0]);
            if (Files.exists(path, new LinkOption[0])) {
                if (!Files.isRegularFile(path, new LinkOption[0]) && Files.isReadable(path)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(" must be a regular readable file.");
                    throw new IOException(((StringBuilder)object).toString());
                }
                return new _$$Lambda$TimeZoneFinder$ReaderSupplier$gDAfECMWS_ohJ_Rfk1HN7JyDSJA(path, (Charset)object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" does not exist");
            throw new FileNotFoundException(((StringBuilder)object).toString());
        }

        public static ReaderSupplier forString(String string) {
            return new _$$Lambda$TimeZoneFinder$ReaderSupplier$Q2dnwJWibh29nQ77BtDmdnZnbdI(string);
        }

        public static /* synthetic */ Reader lambda$forFile$0(Path path, Charset charset) throws IOException {
            return Files.newBufferedReader(path, charset);
        }

        public static /* synthetic */ Reader lambda$forString$1(String string) throws IOException {
            return new StringReader(string);
        }

        public Reader get() throws IOException;
    }

    private static class SelectiveCountryTimeZonesExtractor
    implements TimeZonesProcessor {
        private final String countryCodeToMatch;
        private CountryTimeZones validatedCountryTimeZones;

        private SelectiveCountryTimeZonesExtractor(String string) {
            this.countryCodeToMatch = TimeZoneFinder.normalizeCountryIso(string);
        }

        CountryTimeZones getValidatedCountryTimeZones() {
            return this.validatedCountryTimeZones;
        }

        @Override
        public boolean processCountryZones(String string, String string2, boolean bl, List<CountryTimeZones.TimeZoneMapping> list, String string3) {
            if (!this.countryCodeToMatch.equals(string = TimeZoneFinder.normalizeCountryIso(string))) {
                return true;
            }
            this.validatedCountryTimeZones = CountryTimeZones.createValidated(string, string2, bl, list, string3);
            return false;
        }
    }

    private static interface TimeZonesProcessor {
        public static final boolean CONTINUE = true;
        public static final boolean HALT = false;

        default public boolean processCountryZones(String string, String string2, boolean bl, List<CountryTimeZones.TimeZoneMapping> list, String string3) throws XmlPullParserException {
            return true;
        }

        default public boolean processHeader(String string) throws XmlPullParserException {
            return true;
        }
    }

    private static class TimeZonesValidator
    implements TimeZonesProcessor {
        private final Set<String> knownCountryCodes = new HashSet<String>();

        private TimeZonesValidator() {
        }

        @Override
        public boolean processCountryZones(String string, String charSequence, boolean bl, List<CountryTimeZones.TimeZoneMapping> list, String string2) throws XmlPullParserException {
            if (TimeZoneFinder.normalizeCountryIso(string).equals(string)) {
                if (!this.knownCountryCodes.contains(string)) {
                    if (!list.isEmpty()) {
                        if (CountryTimeZones.TimeZoneMapping.containsTimeZoneId(list, (String)charSequence)) {
                            this.knownCountryCodes.add(string);
                            return true;
                        }
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("defaultTimeZoneId for country code: ");
                        ((StringBuilder)charSequence).append(string);
                        ((StringBuilder)charSequence).append(" is not one of the zones ");
                        ((StringBuilder)charSequence).append(list);
                        ((StringBuilder)charSequence).append(" at ");
                        ((StringBuilder)charSequence).append(string2);
                        throw new XmlPullParserException(((StringBuilder)charSequence).toString());
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("No time zone IDs for country code: ");
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append(" at ");
                    ((StringBuilder)charSequence).append(string2);
                    throw new XmlPullParserException(((StringBuilder)charSequence).toString());
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Second entry for country code: ");
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(" at ");
                ((StringBuilder)charSequence).append(string2);
                throw new XmlPullParserException(((StringBuilder)charSequence).toString());
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Country code: ");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" is not normalized at ");
            ((StringBuilder)charSequence).append(string2);
            throw new XmlPullParserException(((StringBuilder)charSequence).toString());
        }
    }

}

