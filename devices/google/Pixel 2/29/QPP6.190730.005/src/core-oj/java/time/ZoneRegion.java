/*
 * Decompiled with CFR 0.145.
 */
package java.time;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Ser;
import java.time.ZoneId;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesException;
import java.time.zone.ZoneRulesProvider;
import java.util.Objects;

final class ZoneRegion
extends ZoneId
implements Serializable {
    private static final long serialVersionUID = 8386373296231747096L;
    private final String id;
    private final transient ZoneRules rules;

    ZoneRegion(String string, ZoneRules zoneRules) {
        this.id = string;
        this.rules = zoneRules;
    }

    private static void checkName(String string) {
        int n = string.length();
        if (n >= 2) {
            for (int i = 0; i < n; ++i) {
                char c = string.charAt(i);
                if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '/' && i != 0 || c >= '0' && c <= '9' && i != 0 || c == '~' && i != 0 || c == '.' && i != 0 || c == '_' && i != 0 || c == '+' && i != 0 || c == '-' && i != 0) {
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid ID for region-based ZoneId, invalid format: ");
                stringBuilder.append(string);
                throw new DateTimeException(stringBuilder.toString());
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid ID for region-based ZoneId, invalid format: ");
        stringBuilder.append(string);
        throw new DateTimeException(stringBuilder.toString());
    }

    static ZoneRegion ofId(String string, boolean bl) {
        ZoneRulesException zoneRulesException2;
        block2 : {
            Objects.requireNonNull(string, "zoneId");
            ZoneRegion.checkName(string);
            ZoneRules zoneRules = null;
            try {
                ZoneRules zoneRules2;
                zoneRules = zoneRules2 = ZoneRulesProvider.getRules(string, true);
            }
            catch (ZoneRulesException zoneRulesException2) {
                if (bl) break block2;
            }
            return new ZoneRegion(string, zoneRules);
        }
        throw zoneRulesException2;
    }

    static ZoneId readExternal(DataInput dataInput) throws IOException {
        return ZoneId.of(dataInput.readUTF(), false);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser(7, this);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public ZoneRules getRules() {
        ZoneRules zoneRules = this.rules;
        if (zoneRules == null) {
            zoneRules = ZoneRulesProvider.getRules(this.id, false);
        }
        return zoneRules;
    }

    @Override
    void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(7);
        this.writeExternal(dataOutput);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.id);
    }
}

