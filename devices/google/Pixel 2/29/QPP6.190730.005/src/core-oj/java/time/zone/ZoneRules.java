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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.time.zone.Ser;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneOffsetTransitionRule;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ZoneRules
implements Serializable {
    private static final ZoneOffsetTransitionRule[] EMPTY_LASTRULES;
    private static final LocalDateTime[] EMPTY_LDT_ARRAY;
    private static final long[] EMPTY_LONG_ARRAY;
    private static final int LAST_CACHED_YEAR = 2100;
    private static final long serialVersionUID = 3044319355680032515L;
    private final ZoneOffsetTransitionRule[] lastRules;
    private final transient ConcurrentMap<Integer, ZoneOffsetTransition[]> lastRulesCache = new ConcurrentHashMap<Integer, ZoneOffsetTransition[]>();
    private final long[] savingsInstantTransitions;
    private final LocalDateTime[] savingsLocalTransitions;
    private final ZoneOffset[] standardOffsets;
    private final long[] standardTransitions;
    private final ZoneOffset[] wallOffsets;

    static {
        EMPTY_LONG_ARRAY = new long[0];
        EMPTY_LASTRULES = new ZoneOffsetTransitionRule[0];
        EMPTY_LDT_ARRAY = new LocalDateTime[0];
    }

    private ZoneRules(ZoneOffset arrl) {
        ZoneOffset[] arrzoneOffset = this.standardOffsets = new ZoneOffset[1];
        arrzoneOffset[0] = arrl;
        arrl = EMPTY_LONG_ARRAY;
        this.standardTransitions = arrl;
        this.savingsInstantTransitions = arrl;
        this.savingsLocalTransitions = EMPTY_LDT_ARRAY;
        this.wallOffsets = arrzoneOffset;
        this.lastRules = EMPTY_LASTRULES;
    }

    /*
     * WARNING - void declaration
     */
    ZoneRules(ZoneOffset serializable, ZoneOffset comparable2, List<ZoneOffsetTransition> list, List<ZoneOffsetTransition> list2, List<ZoneOffsetTransitionRule> list3) {
        void var4_6;
        int n;
        ArrayList<Comparable<ZoneOffset>> arrayList;
        void var5_7;
        this.standardTransitions = new long[arrayList.size()];
        this.standardOffsets = new ZoneOffset[arrayList.size() + 1];
        this.standardOffsets[0] = serializable;
        for (n = 0; n < arrayList.size(); ++n) {
            this.standardTransitions[n] = ((ZoneOffsetTransition)arrayList.get(n)).toEpochSecond();
            this.standardOffsets[n + 1] = ((ZoneOffsetTransition)arrayList.get(n)).getOffsetAfter();
        }
        serializable = new ArrayList();
        arrayList = new ArrayList<Comparable<ZoneOffset>>();
        arrayList.add(comparable2);
        for (ZoneOffsetTransition zoneOffsetTransition : var4_6) {
            if (zoneOffsetTransition.isGap()) {
                serializable.add(zoneOffsetTransition.getDateTimeBefore());
                serializable.add(zoneOffsetTransition.getDateTimeAfter());
            } else {
                serializable.add(zoneOffsetTransition.getDateTimeAfter());
                serializable.add(zoneOffsetTransition.getDateTimeBefore());
            }
            arrayList.add(zoneOffsetTransition.getOffsetAfter());
        }
        this.savingsLocalTransitions = serializable.toArray(new LocalDateTime[serializable.size()]);
        this.wallOffsets = arrayList.toArray(new ZoneOffset[arrayList.size()]);
        this.savingsInstantTransitions = new long[var4_6.size()];
        for (n = 0; n < var4_6.size(); ++n) {
            this.savingsInstantTransitions[n] = ((ZoneOffsetTransition)var4_6.get(n)).toEpochSecond();
        }
        if (var5_7.size() <= 16) {
            this.lastRules = var5_7.toArray(new ZoneOffsetTransitionRule[var5_7.size()]);
            return;
        }
        throw new IllegalArgumentException("Too many transition rules");
    }

    private ZoneRules(long[] object, ZoneOffset[] object2, long[] arrl, ZoneOffset[] arrzoneOffset, ZoneOffsetTransitionRule[] object3) {
        this.standardTransitions = object;
        this.standardOffsets = object2;
        this.savingsInstantTransitions = arrl;
        this.wallOffsets = arrzoneOffset;
        this.lastRules = object3;
        if (arrl.length == 0) {
            this.savingsLocalTransitions = EMPTY_LDT_ARRAY;
        } else {
            object = new ArrayList();
            for (int i = 0; i < arrl.length; ++i) {
                object3 = arrzoneOffset[i];
                object2 = arrzoneOffset[i + 1];
                if (((ZoneOffsetTransition)(object2 = new ZoneOffsetTransition(arrl[i], (ZoneOffset)object3, (ZoneOffset)object2))).isGap()) {
                    object.add(((ZoneOffsetTransition)object2).getDateTimeBefore());
                    object.add(((ZoneOffsetTransition)object2).getDateTimeAfter());
                    continue;
                }
                object.add(((ZoneOffsetTransition)object2).getDateTimeAfter());
                object.add(((ZoneOffsetTransition)object2).getDateTimeBefore());
            }
            this.savingsLocalTransitions = object.toArray(new LocalDateTime[object.size()]);
        }
    }

    private Object findOffsetInfo(LocalDateTime localDateTime, ZoneOffsetTransition zoneOffsetTransition) {
        LocalDateTime localDateTime2 = zoneOffsetTransition.getDateTimeBefore();
        if (zoneOffsetTransition.isGap()) {
            if (localDateTime.isBefore(localDateTime2)) {
                return zoneOffsetTransition.getOffsetBefore();
            }
            if (localDateTime.isBefore(zoneOffsetTransition.getDateTimeAfter())) {
                return zoneOffsetTransition;
            }
            return zoneOffsetTransition.getOffsetAfter();
        }
        if (!localDateTime.isBefore(localDateTime2)) {
            return zoneOffsetTransition.getOffsetAfter();
        }
        if (localDateTime.isBefore(zoneOffsetTransition.getDateTimeAfter())) {
            return zoneOffsetTransition.getOffsetBefore();
        }
        return zoneOffsetTransition;
    }

    private ZoneOffsetTransition[] findTransitionArray(int n) {
        Integer n2 = n;
        Serializable[] arrserializable = (ZoneOffsetTransition[])this.lastRulesCache.get(n2);
        if (arrserializable != null) {
            return arrserializable;
        }
        arrserializable = this.lastRules;
        ZoneOffsetTransition[] arrzoneOffsetTransition = new ZoneOffsetTransition[arrserializable.length];
        for (int i = 0; i < arrserializable.length; ++i) {
            arrzoneOffsetTransition[i] = ((ZoneOffsetTransitionRule)arrserializable[i]).createTransition(n);
        }
        if (n < 2100) {
            this.lastRulesCache.putIfAbsent(n2, arrzoneOffsetTransition);
        }
        return arrzoneOffsetTransition;
    }

    private int findYear(long l, ZoneOffset zoneOffset) {
        return LocalDate.ofEpochDay(Math.floorDiv((long)zoneOffset.getTotalSeconds() + l, 86400L)).getYear();
    }

    private Object getOffsetInfo(LocalDateTime object) {
        Object object2;
        int n;
        int n2 = this.savingsInstantTransitions.length;
        if (n2 == 0) {
            return this.standardOffsets[0];
        }
        if (this.lastRules.length > 0 && object.isAfter((object2 = this.savingsLocalTransitions)[((LocalDateTime[])object2).length - 1])) {
            ZoneOffsetTransition[] arrzoneOffsetTransition = this.findTransitionArray(object.getYear());
            object2 = null;
            for (ZoneOffsetTransition zoneOffsetTransition : arrzoneOffsetTransition) {
                object2 = this.findOffsetInfo((LocalDateTime)object, zoneOffsetTransition);
                if (!(object2 instanceof ZoneOffsetTransition) && !object2.equals(zoneOffsetTransition.getOffsetBefore())) {
                    continue;
                }
                return object2;
            }
            return object2;
        }
        n2 = Arrays.binarySearch(this.savingsLocalTransitions, object);
        if (n2 == -1) {
            return this.wallOffsets[0];
        }
        if (n2 < 0) {
            n = -n2 - 2;
        } else {
            object = this.savingsLocalTransitions;
            n = n2;
            if (n2 < ((LocalDateTime[])object).length - 1) {
                n = n2;
                if (object[n2].equals(object[n2 + 1])) {
                    n = n2 + 1;
                }
            }
        }
        if ((n & 1) == 0) {
            object2 = this.savingsLocalTransitions;
            object = object2[n];
            LocalDateTime localDateTime = object2[n + 1];
            Object object3 = this.wallOffsets;
            object2 = object3[n / 2];
            if (((ZoneOffset)(object3 = object3[n / 2 + 1])).getTotalSeconds() > ((ZoneOffset)object2).getTotalSeconds()) {
                return new ZoneOffsetTransition((LocalDateTime)object, (ZoneOffset)object2, (ZoneOffset)object3);
            }
            return new ZoneOffsetTransition(localDateTime, (ZoneOffset)object2, (ZoneOffset)object3);
        }
        return this.wallOffsets[n / 2 + 1];
    }

    public static ZoneRules of(ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        return new ZoneRules(zoneOffset);
    }

    public static ZoneRules of(ZoneOffset zoneOffset, ZoneOffset zoneOffset2, List<ZoneOffsetTransition> list, List<ZoneOffsetTransition> list2, List<ZoneOffsetTransitionRule> list3) {
        Objects.requireNonNull(zoneOffset, "baseStandardOffset");
        Objects.requireNonNull(zoneOffset2, "baseWallOffset");
        Objects.requireNonNull(list, "standardOffsetTransitionList");
        Objects.requireNonNull(list2, "transitionList");
        Objects.requireNonNull(list3, "lastRules");
        return new ZoneRules(zoneOffset, zoneOffset2, list, list2, list3);
    }

    static ZoneRules readExternal(DataInput dataInput) throws IOException, ClassNotFoundException {
        int n;
        int n2 = dataInput.readInt();
        long[] arrl = n2 == 0 ? EMPTY_LONG_ARRAY : new long[n2];
        for (n = 0; n < n2; ++n) {
            arrl[n] = Ser.readEpochSec(dataInput);
        }
        ZoneOffset[] arrzoneOffset = new ZoneOffset[n2 + 1];
        for (n = 0; n < arrzoneOffset.length; ++n) {
            arrzoneOffset[n] = Ser.readOffset(dataInput);
        }
        n2 = dataInput.readInt();
        long[] arrl2 = n2 == 0 ? EMPTY_LONG_ARRAY : new long[n2];
        for (n = 0; n < n2; ++n) {
            arrl2[n] = Ser.readEpochSec(dataInput);
        }
        ZoneOffset[] arrzoneOffset2 = new ZoneOffset[n2 + 1];
        for (n = 0; n < arrzoneOffset2.length; ++n) {
            arrzoneOffset2[n] = Ser.readOffset(dataInput);
        }
        n2 = dataInput.readByte();
        ZoneOffsetTransitionRule[] arrzoneOffsetTransitionRule = n2 == 0 ? EMPTY_LASTRULES : new ZoneOffsetTransitionRule[n2];
        for (n = 0; n < n2; ++n) {
            arrzoneOffsetTransitionRule[n] = ZoneOffsetTransitionRule.readExternal(dataInput);
        }
        return new ZoneRules(arrl, arrzoneOffset, arrl2, arrzoneOffset2, arrzoneOffsetTransitionRule);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser(1, this);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof ZoneRules) {
            object = (ZoneRules)object;
            if (!(Arrays.equals(this.standardTransitions, ((ZoneRules)object).standardTransitions) && Arrays.equals(this.standardOffsets, ((ZoneRules)object).standardOffsets) && Arrays.equals(this.savingsInstantTransitions, ((ZoneRules)object).savingsInstantTransitions) && Arrays.equals(this.wallOffsets, ((ZoneRules)object).wallOffsets) && Arrays.equals(this.lastRules, ((ZoneRules)object).lastRules))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public Duration getDaylightSavings(Instant instant) {
        if (this.savingsInstantTransitions.length == 0) {
            return Duration.ZERO;
        }
        ZoneOffset zoneOffset = this.getStandardOffset(instant);
        return Duration.ofSeconds(this.getOffset(instant).getTotalSeconds() - zoneOffset.getTotalSeconds());
    }

    public ZoneOffset getOffset(Instant object) {
        int n;
        if (this.savingsInstantTransitions.length == 0) {
            return this.standardOffsets[0];
        }
        long l = ((Instant)object).getEpochSecond();
        if (this.lastRules.length > 0 && l > (object = this.savingsInstantTransitions)[((long[])object).length - 1]) {
            object = this.wallOffsets;
            ZoneOffsetTransition[] arrzoneOffsetTransition = this.findTransitionArray(this.findYear(l, (ZoneOffset)object[((long[])object).length - 1]));
            object = null;
            for (int i = 0; i < arrzoneOffsetTransition.length; ++i) {
                object = arrzoneOffsetTransition[i];
                if (l >= ((ZoneOffsetTransition)object).toEpochSecond()) continue;
                return ((ZoneOffsetTransition)object).getOffsetBefore();
            }
            return ((ZoneOffsetTransition)object).getOffsetAfter();
        }
        int n2 = n = Arrays.binarySearch(this.savingsInstantTransitions, l);
        if (n < 0) {
            n2 = -n - 2;
        }
        return this.wallOffsets[n2 + 1];
    }

    public ZoneOffset getOffset(LocalDateTime object) {
        if ((object = this.getOffsetInfo((LocalDateTime)object)) instanceof ZoneOffsetTransition) {
            return ((ZoneOffsetTransition)object).getOffsetBefore();
        }
        return (ZoneOffset)object;
    }

    public ZoneOffset getStandardOffset(Instant instant) {
        int n;
        if (this.savingsInstantTransitions.length == 0) {
            return this.standardOffsets[0];
        }
        long l = instant.getEpochSecond();
        int n2 = n = Arrays.binarySearch(this.standardTransitions, l);
        if (n < 0) {
            n2 = -n - 2;
        }
        return this.standardOffsets[n2 + 1];
    }

    public ZoneOffsetTransition getTransition(LocalDateTime object) {
        object = (object = this.getOffsetInfo((LocalDateTime)object)) instanceof ZoneOffsetTransition ? (ZoneOffsetTransition)object : null;
        return object;
    }

    public List<ZoneOffsetTransitionRule> getTransitionRules() {
        return Collections.unmodifiableList(Arrays.asList(this.lastRules));
    }

    public List<ZoneOffsetTransition> getTransitions() {
        Object[] arrobject;
        ArrayList<ZoneOffsetTransition> arrayList = new ArrayList<ZoneOffsetTransition>();
        for (int i = 0; i < (arrobject = this.savingsInstantTransitions).length; ++i) {
            long l = arrobject[i];
            arrobject = this.wallOffsets;
            arrayList.add(new ZoneOffsetTransition(l, (ZoneOffset)arrobject[i], (ZoneOffset)arrobject[i + 1]));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public List<ZoneOffset> getValidOffsets(LocalDateTime object) {
        if ((object = this.getOffsetInfo((LocalDateTime)object)) instanceof ZoneOffsetTransition) {
            return ((ZoneOffsetTransition)object).getValidOffsets();
        }
        return Collections.singletonList((ZoneOffset)object);
    }

    public int hashCode() {
        return Arrays.hashCode(this.standardTransitions) ^ Arrays.hashCode(this.standardOffsets) ^ Arrays.hashCode(this.savingsInstantTransitions) ^ Arrays.hashCode(this.wallOffsets) ^ Arrays.hashCode(this.lastRules);
    }

    public boolean isDaylightSavings(Instant instant) {
        return this.getStandardOffset(instant).equals(this.getOffset(instant)) ^ true;
    }

    public boolean isFixedOffset() {
        boolean bl = this.savingsInstantTransitions.length == 0;
        return bl;
    }

    public boolean isValidOffset(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return this.getValidOffsets(localDateTime).contains(zoneOffset);
    }

    public ZoneOffsetTransition nextTransition(Instant arrobject) {
        long[] arrl;
        if (this.savingsInstantTransitions.length == 0) {
            return null;
        }
        long l = arrobject.getEpochSecond();
        if (l >= (arrl = this.savingsInstantTransitions)[arrl.length - 1]) {
            if (this.lastRules.length == 0) {
                return null;
            }
            ZoneOffset[] arrzoneOffset = this.wallOffsets;
            int n = this.findYear(l, arrzoneOffset[arrzoneOffset.length - 1]);
            for (ZoneOffsetTransition zoneOffsetTransition : this.findTransitionArray(n)) {
                if (l >= zoneOffsetTransition.toEpochSecond()) continue;
                return zoneOffsetTransition;
            }
            if (n < 999999999) {
                return this.findTransitionArray(n + 1)[0];
            }
            return null;
        }
        int n = Arrays.binarySearch(arrl, l);
        n = n < 0 ? -n - 1 : ++n;
        l = this.savingsInstantTransitions[n];
        ZoneOffset[] arrzoneOffset = this.wallOffsets;
        return new ZoneOffsetTransition(l, arrzoneOffset[n], arrzoneOffset[n + 1]);
    }

    public ZoneOffsetTransition previousTransition(Instant object) {
        long l;
        int n;
        int n2;
        if (this.savingsInstantTransitions.length == 0) {
            return null;
        }
        long l2 = l = object.getEpochSecond();
        if (object.getNano() > 0) {
            l2 = l;
            if (l < Long.MAX_VALUE) {
                l2 = l + 1L;
            }
        }
        object = this.savingsInstantTransitions;
        l = object[((long[])object).length - 1];
        if (this.lastRules.length > 0 && l2 > l) {
            object = this.wallOffsets;
            object = object[((long[])object).length - 1];
            n = this.findYear(l2, (ZoneOffset)object);
            ZoneOffsetTransition[] arrzoneOffsetTransition = this.findTransitionArray(n);
            for (n2 = arrzoneOffsetTransition.length - 1; n2 >= 0; --n2) {
                if (l2 <= arrzoneOffsetTransition[n2].toEpochSecond()) continue;
                return arrzoneOffsetTransition[n2];
            }
            n2 = this.findYear(l, (ZoneOffset)object);
            if (--n > n2) {
                object = this.findTransitionArray(n);
                return object[((long[])object).length - 1];
            }
        }
        n2 = n = Arrays.binarySearch(this.savingsInstantTransitions, l2);
        if (n < 0) {
            n2 = -n - 1;
        }
        if (n2 <= 0) {
            return null;
        }
        l2 = this.savingsInstantTransitions[n2 - 1];
        object = this.wallOffsets;
        return new ZoneOffsetTransition(l2, (ZoneOffset)object[n2 - 1], (ZoneOffset)object[n2]);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ZoneRules[currentStandardOffset=");
        ZoneOffset[] arrzoneOffset = this.standardOffsets;
        stringBuilder.append(arrzoneOffset[arrzoneOffset.length - 1]);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        int n;
        dataOutput.writeInt(this.standardTransitions.length);
        Object[] arrobject = this.standardTransitions;
        int n2 = arrobject.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            Ser.writeEpochSec(arrobject[n], dataOutput);
        }
        arrobject = this.standardOffsets;
        n2 = arrobject.length;
        for (n = 0; n < n2; ++n) {
            Ser.writeOffset((ZoneOffset)arrobject[n], dataOutput);
        }
        dataOutput.writeInt(this.savingsInstantTransitions.length);
        arrobject = this.savingsInstantTransitions;
        n2 = arrobject.length;
        for (n = 0; n < n2; ++n) {
            Ser.writeEpochSec(arrobject[n], dataOutput);
        }
        arrobject = this.wallOffsets;
        n2 = arrobject.length;
        for (n = 0; n < n2; ++n) {
            Ser.writeOffset((ZoneOffset)arrobject[n], dataOutput);
        }
        dataOutput.writeByte(this.lastRules.length);
        arrobject = this.lastRules;
        n2 = arrobject.length;
        for (n = n3; n < n2; ++n) {
            arrobject[n].writeExternal(dataOutput);
        }
    }
}

