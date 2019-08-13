/*
 * Decompiled with CFR 0.145.
 */
package java.time.zone;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.Ser;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ZoneOffsetTransition
implements Comparable<ZoneOffsetTransition>,
Serializable {
    private static final long serialVersionUID = -6946044323557704546L;
    private final ZoneOffset offsetAfter;
    private final ZoneOffset offsetBefore;
    private final LocalDateTime transition;

    ZoneOffsetTransition(long l, ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
        this.transition = LocalDateTime.ofEpochSecond(l, 0, zoneOffset);
        this.offsetBefore = zoneOffset;
        this.offsetAfter = zoneOffset2;
    }

    ZoneOffsetTransition(LocalDateTime localDateTime, ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
        this.transition = localDateTime;
        this.offsetBefore = zoneOffset;
        this.offsetAfter = zoneOffset2;
    }

    private int getDurationSeconds() {
        return this.getOffsetAfter().getTotalSeconds() - this.getOffsetBefore().getTotalSeconds();
    }

    public static ZoneOffsetTransition of(LocalDateTime localDateTime, ZoneOffset zoneOffset, ZoneOffset zoneOffset2) {
        Objects.requireNonNull(localDateTime, "transition");
        Objects.requireNonNull(zoneOffset, "offsetBefore");
        Objects.requireNonNull(zoneOffset2, "offsetAfter");
        if (!zoneOffset.equals(zoneOffset2)) {
            if (localDateTime.getNano() == 0) {
                return new ZoneOffsetTransition(localDateTime, zoneOffset, zoneOffset2);
            }
            throw new IllegalArgumentException("Nano-of-second must be zero");
        }
        throw new IllegalArgumentException("Offsets must not be equal");
    }

    static ZoneOffsetTransition readExternal(DataInput object) throws IOException {
        long l = Ser.readEpochSec((DataInput)object);
        ZoneOffset zoneOffset = Ser.readOffset((DataInput)object);
        if (!zoneOffset.equals(object = Ser.readOffset((DataInput)object))) {
            return new ZoneOffsetTransition(l, zoneOffset, (ZoneOffset)object);
        }
        throw new IllegalArgumentException("Offsets must not be equal");
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser(2, this);
    }

    @Override
    public int compareTo(ZoneOffsetTransition zoneOffsetTransition) {
        return this.getInstant().compareTo(zoneOffsetTransition.getInstant());
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof ZoneOffsetTransition) {
            object = (ZoneOffsetTransition)object;
            if (!(this.transition.equals(((ZoneOffsetTransition)object).transition) && this.offsetBefore.equals(((ZoneOffsetTransition)object).offsetBefore) && this.offsetAfter.equals(((ZoneOffsetTransition)object).offsetAfter))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public LocalDateTime getDateTimeAfter() {
        return this.transition.plusSeconds(this.getDurationSeconds());
    }

    public LocalDateTime getDateTimeBefore() {
        return this.transition;
    }

    public Duration getDuration() {
        return Duration.ofSeconds(this.getDurationSeconds());
    }

    public Instant getInstant() {
        return this.transition.toInstant(this.offsetBefore);
    }

    public ZoneOffset getOffsetAfter() {
        return this.offsetAfter;
    }

    public ZoneOffset getOffsetBefore() {
        return this.offsetBefore;
    }

    List<ZoneOffset> getValidOffsets() {
        if (this.isGap()) {
            return Collections.emptyList();
        }
        return Arrays.asList(this.getOffsetBefore(), this.getOffsetAfter());
    }

    public int hashCode() {
        return this.transition.hashCode() ^ this.offsetBefore.hashCode() ^ Integer.rotateLeft(this.offsetAfter.hashCode(), 16);
    }

    public boolean isGap() {
        boolean bl = this.getOffsetAfter().getTotalSeconds() > this.getOffsetBefore().getTotalSeconds();
        return bl;
    }

    public boolean isOverlap() {
        boolean bl = this.getOffsetAfter().getTotalSeconds() < this.getOffsetBefore().getTotalSeconds();
        return bl;
    }

    public boolean isValidOffset(ZoneOffset zoneOffset) {
        boolean bl = this.isGap();
        boolean bl2 = false;
        if (!bl && (this.getOffsetBefore().equals(zoneOffset) || this.getOffsetAfter().equals(zoneOffset))) {
            bl2 = true;
        }
        return bl2;
    }

    public long toEpochSecond() {
        return this.transition.toEpochSecond(this.offsetBefore);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Transition[");
        String string = this.isGap() ? "Gap" : "Overlap";
        stringBuilder.append(string);
        stringBuilder.append(" at ");
        stringBuilder.append(this.transition);
        stringBuilder.append(this.offsetBefore);
        stringBuilder.append(" to ");
        stringBuilder.append(this.offsetAfter);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        Ser.writeEpochSec(this.toEpochSecond(), dataOutput);
        Ser.writeOffset(this.offsetBefore, dataOutput);
        Ser.writeOffset(this.offsetAfter, dataOutput);
    }
}

