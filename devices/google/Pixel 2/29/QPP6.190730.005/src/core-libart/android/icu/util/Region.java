/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.ICUResourceBundle;
import android.icu.util.UResourceBundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Region
implements Comparable<Region> {
    private static final String OUTLYING_OCEANIA_REGION_ID = "QO";
    private static final String UNKNOWN_REGION_ID = "ZZ";
    private static final String WORLD_ID = "001";
    private static ArrayList<Set<Region>> availableRegions;
    private static Map<Integer, Region> numericCodeMap;
    private static Map<String, Region> regionAliases;
    private static boolean regionDataIsLoaded;
    private static Map<String, Region> regionIDMap;
    private static ArrayList<Region> regions;
    private int code;
    private Set<Region> containedRegions = new TreeSet<Region>();
    private Region containingRegion = null;
    private String id;
    private List<Region> preferredValues = null;
    private RegionType type;

    static {
        regionDataIsLoaded = false;
        regionIDMap = null;
        numericCodeMap = null;
        regionAliases = null;
        regions = null;
        availableRegions = null;
    }

    private Region() {
    }

    public static Set<Region> getAvailable(RegionType regionType) {
        Region.loadRegionData();
        return Collections.unmodifiableSet(availableRegions.get(regionType.ordinal()));
    }

    public static Region getInstance(int n) {
        Object object;
        Region.loadRegionData();
        Object object2 = object = numericCodeMap.get(n);
        if (object == null) {
            object2 = "";
            if (n < 10) {
                object2 = "00";
            } else if (n < 100) {
                object2 = "0";
            }
            object = new StringBuilder();
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append(Integer.toString(n));
            object2 = ((StringBuilder)object).toString();
            object2 = regionAliases.get(object2);
        }
        if (object2 != null) {
            object = object2;
            if (((Region)object2).type == RegionType.DEPRECATED) {
                object = object2;
                if (((Region)object2).preferredValues.size() == 1) {
                    object = ((Region)object2).preferredValues.get(0);
                }
            }
            return object;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Unknown region code: ");
        ((StringBuilder)object2).append(n);
        throw new IllegalArgumentException(((StringBuilder)object2).toString());
    }

    public static Region getInstance(String object) {
        if (object != null) {
            Region.loadRegionData();
            Region region = regionIDMap.get(object);
            Object object2 = region;
            if (region == null) {
                object2 = regionAliases.get(object);
            }
            if (object2 != null) {
                object = object2;
                if (((Region)object2).type == RegionType.DEPRECATED) {
                    object = object2;
                    if (((Region)object2).preferredValues.size() == 1) {
                        object = ((Region)object2).preferredValues.get(0);
                    }
                }
                return object;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unknown region id: ");
            ((StringBuilder)object2).append((String)object);
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        throw new NullPointerException();
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void loadRegionData() {
        synchronized (Region.class) {
            int n;
            int n2;
            boolean bl = regionDataIsLoaded;
            if (bl) {
                return;
            }
            HashMap<String, Region> hashMap = new HashMap<String, Region>();
            regionAliases = hashMap;
            HashMap<String, Region> hashMap2 = new HashMap<String, Region>();
            regionIDMap = hashMap2;
            HashMap<Integer, Region> hashMap3 = new HashMap<Integer, Region>();
            numericCodeMap = hashMap3;
            ArrayList arrayList = new ArrayList(RegionType.values().length);
            availableRegions = arrayList;
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "metadata", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("alias").get("territory");
            UResourceBundle uResourceBundle2 = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            UResourceBundle uResourceBundle3 = uResourceBundle2.get("codeMappings");
            Object object = uResourceBundle2.get("idValidity").get("region");
            Object object22 = ((UResourceBundle)object).get("regular");
            Iterator iterator = ((UResourceBundle)object).get("macroregion");
            Object object3 = ((UResourceBundle)object).get("unknown");
            UResourceBundle uResourceBundle4 = uResourceBundle2.get("territoryContainment");
            Object object4 = uResourceBundle4.get(WORLD_ID);
            UResourceBundle uResourceBundle5 = uResourceBundle4.get("grouping");
            List<String> list = Arrays.asList(((UResourceBundle)object4).getStringArray());
            Enumeration<String> enumeration = uResourceBundle5.getKeys();
            ArrayList<String> arrayList2 = new ArrayList<String>();
            object4 = new ArrayList();
            Object object5 = Arrays.asList(((UResourceBundle)object22).getStringArray());
            object4.addAll(object5);
            object4.addAll(Arrays.asList(((UResourceBundle)((Object)iterator)).getStringArray()));
            object4.add(((UResourceBundle)object3).getString());
            object5 = object4.iterator();
            while (object5.hasNext()) {
                String string = (String)object5.next();
                int n3 = string.indexOf("~");
                if (n3 > 0) {
                    StringBuilder stringBuilder = new StringBuilder(string);
                    n2 = stringBuilder.charAt(n3 + 1);
                    stringBuilder.setLength(n3);
                    n = stringBuilder.charAt(n3 - 1);
                    while (n <= n2) {
                        arrayList2.add(stringBuilder.toString());
                        int n4 = n + '\u0001';
                        stringBuilder.setCharAt(n3 - 1, (char)n4);
                        n = n4;
                    }
                    continue;
                }
                arrayList2.add(string);
            }
            object3 = new ArrayList(arrayList2.size());
            regions = object3;
            for (Object object22 : arrayList2) {
                object3 = new Region();
                ((Region)object3).id = object22;
                ((Region)object3).type = RegionType.TERRITORY;
                regionIDMap.put((String)object22, (Region)object3);
                if (((String)object22).matches("[0-9]{3}")) {
                    ((Region)object3).code = Integer.valueOf((String)object22);
                    numericCodeMap.put(((Region)object3).code, (Region)object3);
                    ((Region)object3).type = RegionType.SUBCONTINENT;
                } else {
                    ((Region)object3).code = -1;
                }
                regions.add((Region)object3);
            }
            object3 = arrayList2;
            object22 = uResourceBundle;
            for (n2 = 0; n2 < ((UResourceBundle)object22).getSize(); ++n2) {
                UResourceBundle uResourceBundle6 = ((UResourceBundle)object22).get(n2);
                iterator = uResourceBundle6.getKey();
                object4 = uResourceBundle6.get("replacement").getString();
                if (regionIDMap.containsKey(object4) && !regionIDMap.containsKey(iterator)) {
                    regionAliases.put((String)((Object)iterator), regionIDMap.get(object4));
                    continue;
                }
                if (regionIDMap.containsKey(iterator)) {
                    Region region = regionIDMap.get(iterator);
                } else {
                    Region region = new Region();
                    region.id = iterator;
                    regionIDMap.put((String)((Object)iterator), region);
                    if (((String)((Object)iterator)).matches("[0-9]{3}")) {
                        region.code = Integer.valueOf(iterator);
                        numericCodeMap.put(region.code, region);
                    } else {
                        region.code = -1;
                    }
                    regions.add(region);
                }
                var1_12.type = RegionType.DEPRECATED;
                iterator = Arrays.asList(((String)object4).split(" "));
                object = new ArrayList();
                var1_12.preferredValues = object;
                iterator = iterator.iterator();
                while (iterator.hasNext()) {
                    void var1_12;
                    object = (String)iterator.next();
                    if (!regionIDMap.containsKey(object)) continue;
                    var1_12.preferredValues.add(regionIDMap.get(object));
                }
            }
            UResourceBundle uResourceBundle7 = uResourceBundle3;
            for (n2 = 0; n2 < uResourceBundle7.getSize(); ++n2) {
                object3 = uResourceBundle7.get(n2);
                if (((UResourceBundle)object3).getType() != 8) continue;
                object22 = ((UResourceBundle)object3).getStringArray();
                iterator = object22[0];
                object3 = Integer.valueOf(object22[1]);
                object22 = object22[2];
                if (!regionIDMap.containsKey(iterator)) continue;
                iterator = regionIDMap.get(iterator);
                ((Region)iterator).code = (Integer)object3;
                numericCodeMap.put(((Region)iterator).code, (Region)((Object)iterator));
                regionAliases.put((String)object22, (Region)((Object)iterator));
            }
            if (regionIDMap.containsKey(WORLD_ID)) {
                Region.regionIDMap.get((Object)"001").type = RegionType.WORLD;
            }
            if (regionIDMap.containsKey(UNKNOWN_REGION_ID)) {
                Region.regionIDMap.get((Object)"ZZ").type = RegionType.UNKNOWN;
            }
            for (String string : list) {
                if (!regionIDMap.containsKey(string)) continue;
                Region.regionIDMap.get((Object)string).type = RegionType.CONTINENT;
            }
            while (enumeration.hasMoreElements()) {
                String string = enumeration.nextElement();
                if (!regionIDMap.containsKey(string)) continue;
                Region.regionIDMap.get((Object)string).type = RegionType.GROUPING;
            }
            if (regionIDMap.containsKey(OUTLYING_OCEANIA_REGION_ID)) {
                Region.regionIDMap.get((Object)"QO").type = RegionType.SUBCONTINENT;
            }
            for (n2 = 0; n2 < uResourceBundle4.getSize(); ++n2) {
                UResourceBundle uResourceBundle8 = uResourceBundle4.get(n2);
                object3 = uResourceBundle8.getKey();
                if (((String)object3).equals("containedGroupings") || ((String)object3).equals("deprecated") || ((String)object3).equals("grouping")) continue;
                object3 = regionIDMap.get(object3);
                for (n = 0; n < uResourceBundle8.getSize(); ++n) {
                    object22 = uResourceBundle8.getString(n);
                    object22 = regionIDMap.get(object22);
                    if (object3 == null || object22 == null) continue;
                    ((Region)object3).containedRegions.add((Region)object22);
                    if (((Region)object3).getType() == RegionType.GROUPING) continue;
                    ((Region)object22).containingRegion = object3;
                }
            }
            for (n2 = 0; n2 < RegionType.values().length; ++n2) {
                ArrayList<Set<Region>> arrayList3 = availableRegions;
                object3 = new TreeSet();
                arrayList3.add((Set<Region>)object3);
            }
            object22 = regions.iterator();
            do {
                if (!object22.hasNext()) {
                    regionDataIsLoaded = true;
                    return;
                }
                Region region = (Region)object22.next();
                object3 = availableRegions.get(region.type.ordinal());
                object3.add(region);
                availableRegions.set(region.type.ordinal(), (Set<Region>)object3);
            } while (true);
        }
    }

    @Override
    public int compareTo(Region region) {
        return this.id.compareTo(region.id);
    }

    public boolean contains(Region region) {
        Region.loadRegionData();
        if (this.containedRegions.contains(region)) {
            return true;
        }
        Iterator<Region> iterator = this.containedRegions.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().contains(region)) continue;
            return true;
        }
        return false;
    }

    public Set<Region> getContainedRegions() {
        Region.loadRegionData();
        return Collections.unmodifiableSet(this.containedRegions);
    }

    public Set<Region> getContainedRegions(RegionType regionType) {
        Region.loadRegionData();
        TreeSet<Region> treeSet = new TreeSet<Region>();
        for (Region region : this.getContainedRegions()) {
            if (region.getType() == regionType) {
                treeSet.add(region);
                continue;
            }
            treeSet.addAll(region.getContainedRegions(regionType));
        }
        return Collections.unmodifiableSet(treeSet);
    }

    public Region getContainingRegion() {
        Region.loadRegionData();
        return this.containingRegion;
    }

    public Region getContainingRegion(RegionType regionType) {
        Region.loadRegionData();
        Region region = this.containingRegion;
        if (region == null) {
            return null;
        }
        if (region.type.equals((Object)regionType)) {
            return this.containingRegion;
        }
        return this.containingRegion.getContainingRegion(regionType);
    }

    public int getNumericCode() {
        return this.code;
    }

    public List<Region> getPreferredValues() {
        Region.loadRegionData();
        if (this.type == RegionType.DEPRECATED) {
            return Collections.unmodifiableList(this.preferredValues);
        }
        return null;
    }

    public RegionType getType() {
        return this.type;
    }

    public String toString() {
        return this.id;
    }

    public static enum RegionType {
        UNKNOWN,
        TERRITORY,
        WORLD,
        CONTINENT,
        SUBCONTINENT,
        GROUPING,
        DEPRECATED;
        
    }

}

