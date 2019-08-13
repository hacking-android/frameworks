/*
 * Decompiled with CFR 0.145.
 */
package java.time.zone;

import java.time.zone.IcuZoneRulesProvider;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ZoneRulesProvider {
    private static final CopyOnWriteArrayList<ZoneRulesProvider> PROVIDERS = new CopyOnWriteArrayList();
    private static final ConcurrentMap<String, ZoneRulesProvider> ZONES = new ConcurrentHashMap<String, ZoneRulesProvider>(512, 0.75f, 2);

    static {
        ZoneRulesProvider.registerProvider(new IcuZoneRulesProvider());
    }

    protected ZoneRulesProvider() {
    }

    public static Set<String> getAvailableZoneIds() {
        return new HashSet<String>(ZONES.keySet());
    }

    private static ZoneRulesProvider getProvider(String string) {
        Object object = (ZoneRulesProvider)ZONES.get(string);
        if (object == null) {
            if (ZONES.isEmpty()) {
                throw new ZoneRulesException("No time-zone data files registered");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown time-zone ID: ");
            ((StringBuilder)object).append(string);
            throw new ZoneRulesException(((StringBuilder)object).toString());
        }
        return object;
    }

    public static ZoneRules getRules(String string, boolean bl) {
        Objects.requireNonNull(string, "zoneId");
        return ZoneRulesProvider.getProvider(string).provideRules(string, bl);
    }

    public static NavigableMap<String, ZoneRules> getVersions(String string) {
        Objects.requireNonNull(string, "zoneId");
        return ZoneRulesProvider.getProvider(string).provideVersions(string);
    }

    public static boolean refresh() {
        boolean bl = false;
        Iterator<ZoneRulesProvider> iterator = PROVIDERS.iterator();
        while (iterator.hasNext()) {
            bl |= iterator.next().provideRefresh();
        }
        return bl;
    }

    public static void registerProvider(ZoneRulesProvider zoneRulesProvider) {
        Objects.requireNonNull(zoneRulesProvider, "provider");
        ZoneRulesProvider.registerProvider0(zoneRulesProvider);
        PROVIDERS.add(zoneRulesProvider);
    }

    private static void registerProvider0(ZoneRulesProvider zoneRulesProvider) {
        Object object = zoneRulesProvider.provideZoneIds().iterator();
        while (object.hasNext()) {
            String string = object.next();
            Objects.requireNonNull(string, "zoneId");
            if (ZONES.putIfAbsent(string, zoneRulesProvider) == null) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to register zone as one already registered with that ID: ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(", currently loading from provider: ");
            ((StringBuilder)object).append(zoneRulesProvider);
            throw new ZoneRulesException(((StringBuilder)object).toString());
        }
    }

    protected boolean provideRefresh() {
        return false;
    }

    protected abstract ZoneRules provideRules(String var1, boolean var2);

    protected abstract NavigableMap<String, ZoneRules> provideVersions(String var1);

    protected abstract Set<String> provideZoneIds();
}

