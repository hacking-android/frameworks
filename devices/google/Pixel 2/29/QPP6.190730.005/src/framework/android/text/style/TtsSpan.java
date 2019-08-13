/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.os.Parcel;
import android.os.PersistableBundle;
import android.text.ParcelableSpan;
import java.text.NumberFormat;
import java.util.Locale;

public class TtsSpan
implements ParcelableSpan {
    public static final String ANIMACY_ANIMATE = "android.animate";
    public static final String ANIMACY_INANIMATE = "android.inanimate";
    public static final String ARG_ANIMACY = "android.arg.animacy";
    public static final String ARG_CASE = "android.arg.case";
    public static final String ARG_COUNTRY_CODE = "android.arg.country_code";
    public static final String ARG_CURRENCY = "android.arg.money";
    public static final String ARG_DAY = "android.arg.day";
    public static final String ARG_DENOMINATOR = "android.arg.denominator";
    public static final String ARG_DIGITS = "android.arg.digits";
    public static final String ARG_DOMAIN = "android.arg.domain";
    public static final String ARG_EXTENSION = "android.arg.extension";
    public static final String ARG_FRACTIONAL_PART = "android.arg.fractional_part";
    public static final String ARG_FRAGMENT_ID = "android.arg.fragment_id";
    public static final String ARG_GENDER = "android.arg.gender";
    public static final String ARG_HOURS = "android.arg.hours";
    public static final String ARG_INTEGER_PART = "android.arg.integer_part";
    public static final String ARG_MINUTES = "android.arg.minutes";
    public static final String ARG_MONTH = "android.arg.month";
    public static final String ARG_MULTIPLICITY = "android.arg.multiplicity";
    public static final String ARG_NUMBER = "android.arg.number";
    public static final String ARG_NUMBER_PARTS = "android.arg.number_parts";
    public static final String ARG_NUMERATOR = "android.arg.numerator";
    public static final String ARG_PASSWORD = "android.arg.password";
    public static final String ARG_PATH = "android.arg.path";
    public static final String ARG_PORT = "android.arg.port";
    public static final String ARG_PROTOCOL = "android.arg.protocol";
    public static final String ARG_QUANTITY = "android.arg.quantity";
    public static final String ARG_QUERY_STRING = "android.arg.query_string";
    public static final String ARG_TEXT = "android.arg.text";
    public static final String ARG_UNIT = "android.arg.unit";
    public static final String ARG_USERNAME = "android.arg.username";
    public static final String ARG_VERBATIM = "android.arg.verbatim";
    public static final String ARG_WEEKDAY = "android.arg.weekday";
    public static final String ARG_YEAR = "android.arg.year";
    public static final String CASE_ABLATIVE = "android.ablative";
    public static final String CASE_ACCUSATIVE = "android.accusative";
    public static final String CASE_DATIVE = "android.dative";
    public static final String CASE_GENITIVE = "android.genitive";
    public static final String CASE_INSTRUMENTAL = "android.instrumental";
    public static final String CASE_LOCATIVE = "android.locative";
    public static final String CASE_NOMINATIVE = "android.nominative";
    public static final String CASE_VOCATIVE = "android.vocative";
    public static final String GENDER_FEMALE = "android.female";
    public static final String GENDER_MALE = "android.male";
    public static final String GENDER_NEUTRAL = "android.neutral";
    public static final int MONTH_APRIL = 3;
    public static final int MONTH_AUGUST = 7;
    public static final int MONTH_DECEMBER = 11;
    public static final int MONTH_FEBRUARY = 1;
    public static final int MONTH_JANUARY = 0;
    public static final int MONTH_JULY = 6;
    public static final int MONTH_JUNE = 5;
    public static final int MONTH_MARCH = 2;
    public static final int MONTH_MAY = 4;
    public static final int MONTH_NOVEMBER = 10;
    public static final int MONTH_OCTOBER = 9;
    public static final int MONTH_SEPTEMBER = 8;
    public static final String MULTIPLICITY_DUAL = "android.dual";
    public static final String MULTIPLICITY_PLURAL = "android.plural";
    public static final String MULTIPLICITY_SINGLE = "android.single";
    public static final String TYPE_CARDINAL = "android.type.cardinal";
    public static final String TYPE_DATE = "android.type.date";
    public static final String TYPE_DECIMAL = "android.type.decimal";
    public static final String TYPE_DIGITS = "android.type.digits";
    public static final String TYPE_ELECTRONIC = "android.type.electronic";
    public static final String TYPE_FRACTION = "android.type.fraction";
    public static final String TYPE_MEASURE = "android.type.measure";
    public static final String TYPE_MONEY = "android.type.money";
    public static final String TYPE_ORDINAL = "android.type.ordinal";
    public static final String TYPE_TELEPHONE = "android.type.telephone";
    public static final String TYPE_TEXT = "android.type.text";
    public static final String TYPE_TIME = "android.type.time";
    public static final String TYPE_VERBATIM = "android.type.verbatim";
    public static final int WEEKDAY_FRIDAY = 6;
    public static final int WEEKDAY_MONDAY = 2;
    public static final int WEEKDAY_SATURDAY = 7;
    public static final int WEEKDAY_SUNDAY = 1;
    public static final int WEEKDAY_THURSDAY = 5;
    public static final int WEEKDAY_TUESDAY = 3;
    public static final int WEEKDAY_WEDNESDAY = 4;
    private final PersistableBundle mArgs;
    private final String mType;

    public TtsSpan(Parcel parcel) {
        this.mType = parcel.readString();
        this.mArgs = parcel.readPersistableBundle();
    }

    public TtsSpan(String string2, PersistableBundle persistableBundle) {
        this.mType = string2;
        this.mArgs = persistableBundle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PersistableBundle getArgs() {
        return this.mArgs;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 24;
    }

    public String getType() {
        return this.mType;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeString(this.mType);
        parcel.writePersistableBundle(this.mArgs);
    }

    public static class Builder<C extends Builder<?>> {
        private PersistableBundle mArgs = new PersistableBundle();
        private final String mType;

        public Builder(String string2) {
            this.mType = string2;
        }

        public TtsSpan build() {
            return new TtsSpan(this.mType, this.mArgs);
        }

        public C setIntArgument(String string2, int n) {
            this.mArgs.putInt(string2, n);
            return (C)this;
        }

        public C setLongArgument(String string2, long l) {
            this.mArgs.putLong(string2, l);
            return (C)this;
        }

        public C setStringArgument(String string2, String string3) {
            this.mArgs.putString(string2, string3);
            return (C)this;
        }
    }

    public static class CardinalBuilder
    extends SemioticClassBuilder<CardinalBuilder> {
        public CardinalBuilder() {
            super(TtsSpan.TYPE_CARDINAL);
        }

        public CardinalBuilder(long l) {
            this();
            this.setNumber(l);
        }

        public CardinalBuilder(String string2) {
            this();
            this.setNumber(string2);
        }

        public CardinalBuilder setNumber(long l) {
            return this.setNumber(String.valueOf(l));
        }

        public CardinalBuilder setNumber(String string2) {
            return (CardinalBuilder)this.setStringArgument(TtsSpan.ARG_NUMBER, string2);
        }
    }

    public static class DateBuilder
    extends SemioticClassBuilder<DateBuilder> {
        public DateBuilder() {
            super(TtsSpan.TYPE_DATE);
        }

        public DateBuilder(Integer n, Integer n2, Integer n3, Integer n4) {
            this();
            if (n != null) {
                this.setWeekday(n);
            }
            if (n2 != null) {
                this.setDay(n2);
            }
            if (n3 != null) {
                this.setMonth(n3);
            }
            if (n4 != null) {
                this.setYear(n4);
            }
        }

        public DateBuilder setDay(int n) {
            return (DateBuilder)this.setIntArgument(TtsSpan.ARG_DAY, n);
        }

        public DateBuilder setMonth(int n) {
            return (DateBuilder)this.setIntArgument(TtsSpan.ARG_MONTH, n);
        }

        public DateBuilder setWeekday(int n) {
            return (DateBuilder)this.setIntArgument(TtsSpan.ARG_WEEKDAY, n);
        }

        public DateBuilder setYear(int n) {
            return (DateBuilder)this.setIntArgument(TtsSpan.ARG_YEAR, n);
        }
    }

    public static class DecimalBuilder
    extends SemioticClassBuilder<DecimalBuilder> {
        public DecimalBuilder() {
            super(TtsSpan.TYPE_DECIMAL);
        }

        public DecimalBuilder(double d, int n, int n2) {
            this();
            this.setArgumentsFromDouble(d, n, n2);
        }

        public DecimalBuilder(String string2, String string3) {
            this();
            this.setIntegerPart(string2);
            this.setFractionalPart(string3);
        }

        public DecimalBuilder setArgumentsFromDouble(double d, int n, int n2) {
            Object object = NumberFormat.getInstance(Locale.US);
            ((NumberFormat)object).setMinimumFractionDigits(n2);
            ((NumberFormat)object).setMaximumFractionDigits(n2);
            ((NumberFormat)object).setGroupingUsed(false);
            object = ((NumberFormat)object).format(d);
            n = ((String)object).indexOf(46);
            if (n >= 0) {
                this.setIntegerPart(((String)object).substring(0, n));
                this.setFractionalPart(((String)object).substring(n + 1));
            } else {
                this.setIntegerPart((String)object);
            }
            return this;
        }

        public DecimalBuilder setFractionalPart(String string2) {
            return (DecimalBuilder)this.setStringArgument(TtsSpan.ARG_FRACTIONAL_PART, string2);
        }

        public DecimalBuilder setIntegerPart(long l) {
            return this.setIntegerPart(String.valueOf(l));
        }

        public DecimalBuilder setIntegerPart(String string2) {
            return (DecimalBuilder)this.setStringArgument(TtsSpan.ARG_INTEGER_PART, string2);
        }
    }

    public static class DigitsBuilder
    extends SemioticClassBuilder<DigitsBuilder> {
        public DigitsBuilder() {
            super(TtsSpan.TYPE_DIGITS);
        }

        public DigitsBuilder(String string2) {
            this();
            this.setDigits(string2);
        }

        public DigitsBuilder setDigits(String string2) {
            return (DigitsBuilder)this.setStringArgument(TtsSpan.ARG_DIGITS, string2);
        }
    }

    public static class ElectronicBuilder
    extends SemioticClassBuilder<ElectronicBuilder> {
        public ElectronicBuilder() {
            super(TtsSpan.TYPE_ELECTRONIC);
        }

        public ElectronicBuilder setDomain(String string2) {
            return (ElectronicBuilder)this.setStringArgument(TtsSpan.ARG_DOMAIN, string2);
        }

        public ElectronicBuilder setEmailArguments(String string2, String string3) {
            return this.setDomain(string3).setUsername(string2);
        }

        public ElectronicBuilder setFragmentId(String string2) {
            return (ElectronicBuilder)this.setStringArgument(TtsSpan.ARG_FRAGMENT_ID, string2);
        }

        public ElectronicBuilder setPassword(String string2) {
            return (ElectronicBuilder)this.setStringArgument(TtsSpan.ARG_PASSWORD, string2);
        }

        public ElectronicBuilder setPath(String string2) {
            return (ElectronicBuilder)this.setStringArgument(TtsSpan.ARG_PATH, string2);
        }

        public ElectronicBuilder setPort(int n) {
            return (ElectronicBuilder)this.setIntArgument(TtsSpan.ARG_PORT, n);
        }

        public ElectronicBuilder setProtocol(String string2) {
            return (ElectronicBuilder)this.setStringArgument(TtsSpan.ARG_PROTOCOL, string2);
        }

        public ElectronicBuilder setQueryString(String string2) {
            return (ElectronicBuilder)this.setStringArgument(TtsSpan.ARG_QUERY_STRING, string2);
        }

        public ElectronicBuilder setUsername(String string2) {
            return (ElectronicBuilder)this.setStringArgument(TtsSpan.ARG_USERNAME, string2);
        }
    }

    public static class FractionBuilder
    extends SemioticClassBuilder<FractionBuilder> {
        public FractionBuilder() {
            super(TtsSpan.TYPE_FRACTION);
        }

        public FractionBuilder(long l, long l2, long l3) {
            this();
            this.setIntegerPart(l);
            this.setNumerator(l2);
            this.setDenominator(l3);
        }

        public FractionBuilder setDenominator(long l) {
            return this.setDenominator(String.valueOf(l));
        }

        public FractionBuilder setDenominator(String string2) {
            return (FractionBuilder)this.setStringArgument(TtsSpan.ARG_DENOMINATOR, string2);
        }

        public FractionBuilder setIntegerPart(long l) {
            return this.setIntegerPart(String.valueOf(l));
        }

        public FractionBuilder setIntegerPart(String string2) {
            return (FractionBuilder)this.setStringArgument(TtsSpan.ARG_INTEGER_PART, string2);
        }

        public FractionBuilder setNumerator(long l) {
            return this.setNumerator(String.valueOf(l));
        }

        public FractionBuilder setNumerator(String string2) {
            return (FractionBuilder)this.setStringArgument(TtsSpan.ARG_NUMERATOR, string2);
        }
    }

    public static class MeasureBuilder
    extends SemioticClassBuilder<MeasureBuilder> {
        public MeasureBuilder() {
            super(TtsSpan.TYPE_MEASURE);
        }

        public MeasureBuilder setDenominator(long l) {
            return this.setDenominator(String.valueOf(l));
        }

        public MeasureBuilder setDenominator(String string2) {
            return (MeasureBuilder)this.setStringArgument(TtsSpan.ARG_DENOMINATOR, string2);
        }

        public MeasureBuilder setFractionalPart(String string2) {
            return (MeasureBuilder)this.setStringArgument(TtsSpan.ARG_FRACTIONAL_PART, string2);
        }

        public MeasureBuilder setIntegerPart(long l) {
            return this.setIntegerPart(String.valueOf(l));
        }

        public MeasureBuilder setIntegerPart(String string2) {
            return (MeasureBuilder)this.setStringArgument(TtsSpan.ARG_INTEGER_PART, string2);
        }

        public MeasureBuilder setNumber(long l) {
            return this.setNumber(String.valueOf(l));
        }

        public MeasureBuilder setNumber(String string2) {
            return (MeasureBuilder)this.setStringArgument(TtsSpan.ARG_NUMBER, string2);
        }

        public MeasureBuilder setNumerator(long l) {
            return this.setNumerator(String.valueOf(l));
        }

        public MeasureBuilder setNumerator(String string2) {
            return (MeasureBuilder)this.setStringArgument(TtsSpan.ARG_NUMERATOR, string2);
        }

        public MeasureBuilder setUnit(String string2) {
            return (MeasureBuilder)this.setStringArgument(TtsSpan.ARG_UNIT, string2);
        }
    }

    public static class MoneyBuilder
    extends SemioticClassBuilder<MoneyBuilder> {
        public MoneyBuilder() {
            super(TtsSpan.TYPE_MONEY);
        }

        public MoneyBuilder setCurrency(String string2) {
            return (MoneyBuilder)this.setStringArgument(TtsSpan.ARG_CURRENCY, string2);
        }

        public MoneyBuilder setFractionalPart(String string2) {
            return (MoneyBuilder)this.setStringArgument(TtsSpan.ARG_FRACTIONAL_PART, string2);
        }

        public MoneyBuilder setIntegerPart(long l) {
            return this.setIntegerPart(String.valueOf(l));
        }

        public MoneyBuilder setIntegerPart(String string2) {
            return (MoneyBuilder)this.setStringArgument(TtsSpan.ARG_INTEGER_PART, string2);
        }

        public MoneyBuilder setQuantity(String string2) {
            return (MoneyBuilder)this.setStringArgument(TtsSpan.ARG_QUANTITY, string2);
        }
    }

    public static class OrdinalBuilder
    extends SemioticClassBuilder<OrdinalBuilder> {
        public OrdinalBuilder() {
            super(TtsSpan.TYPE_ORDINAL);
        }

        public OrdinalBuilder(long l) {
            this();
            this.setNumber(l);
        }

        public OrdinalBuilder(String string2) {
            this();
            this.setNumber(string2);
        }

        public OrdinalBuilder setNumber(long l) {
            return this.setNumber(String.valueOf(l));
        }

        public OrdinalBuilder setNumber(String string2) {
            return (OrdinalBuilder)this.setStringArgument(TtsSpan.ARG_NUMBER, string2);
        }
    }

    public static class SemioticClassBuilder<C extends SemioticClassBuilder<?>>
    extends Builder<C> {
        public SemioticClassBuilder(String string2) {
            super(string2);
        }

        public C setAnimacy(String string2) {
            return (C)((SemioticClassBuilder)this.setStringArgument(TtsSpan.ARG_ANIMACY, string2));
        }

        public C setCase(String string2) {
            return (C)((SemioticClassBuilder)this.setStringArgument(TtsSpan.ARG_CASE, string2));
        }

        public C setGender(String string2) {
            return (C)((SemioticClassBuilder)this.setStringArgument(TtsSpan.ARG_GENDER, string2));
        }

        public C setMultiplicity(String string2) {
            return (C)((SemioticClassBuilder)this.setStringArgument(TtsSpan.ARG_MULTIPLICITY, string2));
        }
    }

    public static class TelephoneBuilder
    extends SemioticClassBuilder<TelephoneBuilder> {
        public TelephoneBuilder() {
            super(TtsSpan.TYPE_TELEPHONE);
        }

        public TelephoneBuilder(String string2) {
            this();
            this.setNumberParts(string2);
        }

        public TelephoneBuilder setCountryCode(String string2) {
            return (TelephoneBuilder)this.setStringArgument(TtsSpan.ARG_COUNTRY_CODE, string2);
        }

        public TelephoneBuilder setExtension(String string2) {
            return (TelephoneBuilder)this.setStringArgument(TtsSpan.ARG_EXTENSION, string2);
        }

        public TelephoneBuilder setNumberParts(String string2) {
            return (TelephoneBuilder)this.setStringArgument(TtsSpan.ARG_NUMBER_PARTS, string2);
        }
    }

    public static class TextBuilder
    extends SemioticClassBuilder<TextBuilder> {
        public TextBuilder() {
            super(TtsSpan.TYPE_TEXT);
        }

        public TextBuilder(String string2) {
            this();
            this.setText(string2);
        }

        public TextBuilder setText(String string2) {
            return (TextBuilder)this.setStringArgument(TtsSpan.ARG_TEXT, string2);
        }
    }

    public static class TimeBuilder
    extends SemioticClassBuilder<TimeBuilder> {
        public TimeBuilder() {
            super(TtsSpan.TYPE_TIME);
        }

        public TimeBuilder(int n, int n2) {
            this();
            this.setHours(n);
            this.setMinutes(n2);
        }

        public TimeBuilder setHours(int n) {
            return (TimeBuilder)this.setIntArgument(TtsSpan.ARG_HOURS, n);
        }

        public TimeBuilder setMinutes(int n) {
            return (TimeBuilder)this.setIntArgument(TtsSpan.ARG_MINUTES, n);
        }
    }

    public static class VerbatimBuilder
    extends SemioticClassBuilder<VerbatimBuilder> {
        public VerbatimBuilder() {
            super(TtsSpan.TYPE_VERBATIM);
        }

        public VerbatimBuilder(String string2) {
            this();
            this.setVerbatim(string2);
        }

        public VerbatimBuilder setVerbatim(String string2) {
            return (VerbatimBuilder)this.setStringArgument(TtsSpan.ARG_VERBATIM, string2);
        }
    }

}

