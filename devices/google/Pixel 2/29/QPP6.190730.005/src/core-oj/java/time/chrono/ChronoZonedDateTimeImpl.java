/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoLocalDateTimeImpl;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.Ser;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;
import java.util.List;
import java.util.Objects;

final class ChronoZonedDateTimeImpl<D extends ChronoLocalDate>
implements ChronoZonedDateTime<D>,
Serializable {
    private static final long serialVersionUID = -5261813987200935591L;
    private final transient ChronoLocalDateTimeImpl<D> dateTime;
    private final transient ZoneOffset offset;
    private final transient ZoneId zone;

    private ChronoZonedDateTimeImpl(ChronoLocalDateTimeImpl<D> chronoLocalDateTimeImpl, ZoneOffset zoneOffset, ZoneId zoneId) {
        this.dateTime = Objects.requireNonNull(chronoLocalDateTimeImpl, "dateTime");
        this.offset = Objects.requireNonNull(zoneOffset, "offset");
        this.zone = Objects.requireNonNull(zoneId, "zone");
    }

    private ChronoZonedDateTimeImpl<D> create(Instant instant, ZoneId zoneId) {
        return ChronoZonedDateTimeImpl.ofInstant(this.getChronology(), instant, zoneId);
    }

    static <R extends ChronoLocalDate> ChronoZonedDateTimeImpl<R> ensureValid(Chronology chronology, Temporal temporal) {
        if (chronology.equals((temporal = (ChronoZonedDateTimeImpl)temporal).getChronology())) {
            return temporal;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Chronology mismatch, required: ");
        stringBuilder.append(chronology.getId());
        stringBuilder.append(", actual: ");
        stringBuilder.append(temporal.getChronology().getId());
        throw new ClassCastException(stringBuilder.toString());
    }

    static <R extends ChronoLocalDate> ChronoZonedDateTime<R> ofBest(ChronoLocalDateTimeImpl<R> chronoLocalDateTimeImpl, ZoneId zoneId, ZoneOffset comparable) {
        LocalDateTime localDateTime;
        Objects.requireNonNull(chronoLocalDateTimeImpl, "localDateTime");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId instanceof ZoneOffset) {
            return new ChronoZonedDateTimeImpl<R>(chronoLocalDateTimeImpl, (ZoneOffset)zoneId, zoneId);
        }
        ZoneRules zoneRules = zoneId.getRules();
        List<ZoneOffset> list = zoneRules.getValidOffsets(localDateTime = LocalDateTime.from(chronoLocalDateTimeImpl));
        if (list.size() == 1) {
            comparable = list.get(0);
        } else if (list.size() == 0) {
            comparable = zoneRules.getTransition(localDateTime);
            chronoLocalDateTimeImpl = chronoLocalDateTimeImpl.plusSeconds(((ZoneOffsetTransition)comparable).getDuration().getSeconds());
            comparable = ((ZoneOffsetTransition)comparable).getOffsetAfter();
        } else if (comparable == null || !list.contains(comparable)) {
            comparable = list.get(0);
        }
        Objects.requireNonNull(comparable, "offset");
        return new ChronoZonedDateTimeImpl<R>(chronoLocalDateTimeImpl, (ZoneOffset)comparable, zoneId);
    }

    static ChronoZonedDateTimeImpl<?> ofInstant(Chronology chronology, Instant instant, ZoneId zoneId) {
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(instant);
        Objects.requireNonNull(zoneOffset, "offset");
        return new ChronoZonedDateTimeImpl<D>((ChronoLocalDateTimeImpl)chronology.localDateTime(LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), zoneOffset)), zoneOffset, zoneId);
    }

    static ChronoZonedDateTime<?> readExternal(ObjectInput object) throws IOException, ClassNotFoundException {
        ChronoLocalDateTime chronoLocalDateTime = (ChronoLocalDateTime)object.readObject();
        ZoneOffset zoneOffset = (ZoneOffset)object.readObject();
        object = (ZoneId)object.readObject();
        return chronoLocalDateTime.atZone(zoneOffset).withZoneSameLocal((ZoneId)object);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser(3, this);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof ChronoZonedDateTime) {
            if (this.compareTo((ChronoZonedDateTime)object) != 0) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public ZoneOffset getOffset() {
        return this.offset;
    }

    @Override
    public ZoneId getZone() {
        return this.zone;
    }

    @Override
    public int hashCode() {
        return this.toLocalDateTime().hashCode() ^ this.getOffset().hashCode() ^ Integer.rotateLeft(this.getZone().hashCode(), 3);
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        boolean bl = temporalField instanceof ChronoField || temporalField != null && temporalField.isSupportedBy(this);
        return bl;
    }

    @Override
    public ChronoZonedDateTime<D> plus(long l, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            return this.with(this.dateTime.plus(l, temporalUnit));
        }
        return ChronoZonedDateTimeImpl.ensureValid(this.getChronology(), temporalUnit.addTo(this, l));
    }

    @Override
    public ChronoLocalDateTime<D> toLocalDateTime() {
        return this.dateTime;
    }

    @Override
    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this.toLocalDateTime().toString());
        ((StringBuilder)charSequence).append(this.getOffset().toString());
        String string = ((StringBuilder)charSequence).toString();
        charSequence = string;
        if (this.getOffset() != this.getZone()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append('[');
            ((StringBuilder)charSequence).append(this.getZone().toString());
            ((StringBuilder)charSequence).append(']');
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    @Override
    public long until(Temporal chronoZonedDateTime, TemporalUnit temporalUnit) {
        Objects.requireNonNull(chronoZonedDateTime, "endExclusive");
        chronoZonedDateTime = this.getChronology().zonedDateTime(chronoZonedDateTime);
        if (temporalUnit instanceof ChronoUnit) {
            chronoZonedDateTime = chronoZonedDateTime.withZoneSameInstant(this.offset);
            return this.dateTime.until(chronoZonedDateTime.toLocalDateTime(), temporalUnit);
        }
        Objects.requireNonNull(temporalUnit, "unit");
        return temporalUnit.between(this, chronoZonedDateTime);
    }

    @Override
    public ChronoZonedDateTime<D> with(TemporalField object, long l) {
        if (object instanceof ChronoField) {
            ChronoField chronoField = (ChronoField)object;
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    return ChronoZonedDateTimeImpl.ofBest(this.dateTime.with((TemporalField)object, l), this.zone, this.offset);
                }
                object = ZoneOffset.ofTotalSeconds(chronoField.checkValidIntValue(l));
                return this.create(this.dateTime.toInstant((ZoneOffset)object), this.zone);
            }
            return this.plus(l - this.toEpochSecond(), ChronoUnit.SECONDS);
        }
        return ChronoZonedDateTimeImpl.ensureValid(this.getChronology(), object.adjustInto(this, l));
    }

    @Override
    public ChronoZonedDateTime<D> withEarlierOffsetAtOverlap() {
        Comparable<ZoneOffsetTransition> comparable = this.getZone().getRules().getTransition(LocalDateTime.from(this));
        if (comparable != null && ((ZoneOffsetTransition)comparable).isOverlap() && !((ZoneOffset)(comparable = ((ZoneOffsetTransition)comparable).getOffsetBefore())).equals(this.offset)) {
            return new ChronoZonedDateTimeImpl<D>(this.dateTime, (ZoneOffset)comparable, this.zone);
        }
        return this;
    }

    @Override
    public ChronoZonedDateTime<D> withLaterOffsetAtOverlap() {
        Comparable<ZoneOffsetTransition> comparable = this.getZone().getRules().getTransition(LocalDateTime.from(this));
        if (comparable != null && !((ZoneOffset)(comparable = ((ZoneOffsetTransition)comparable).getOffsetAfter())).equals(this.getOffset())) {
            return new ChronoZonedDateTimeImpl<D>(this.dateTime, (ZoneOffset)comparable, this.zone);
        }
        return this;
    }

    @Override
    public ChronoZonedDateTime<D> withZoneSameInstant(ZoneId serializable) {
        Objects.requireNonNull(serializable, "zone");
        serializable = this.zone.equals(serializable) ? this : this.create(this.dateTime.toInstant(this.offset), (ZoneId)serializable);
        return serializable;
    }

    @Override
    public ChronoZonedDateTime<D> withZoneSameLocal(ZoneId zoneId) {
        return ChronoZonedDateTimeImpl.ofBest(this.dateTime, zoneId, this.offset);
    }

    void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(this.dateTime);
        objectOutput.writeObject(this.offset);
        objectOutput.writeObject(this.zone);
    }

}

