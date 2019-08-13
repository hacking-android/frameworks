/*
 * Decompiled with CFR 0.145.
 */
package android.app.slice;

import android.app.slice.Slice;
import android.app.slice.SliceItem;
import android.app.slice._$$Lambda$SliceQuery$cG9kHpHpv4nbm7p3sCvlkQGlqQw;
import android.app.slice._$$Lambda$SliceQuery$fdDPNErwIni_vCQ6k_MlGGBunoE;
import android.app.slice._$$Lambda$SliceQuery$hLToAajdaMbaf8BUtZ8fpGK980E;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SliceQuery {
    private static final String TAG = "SliceQuery";

    public static boolean compareTypes(SliceItem sliceItem, String string2) {
        if (string2.length() == 3 && string2.equals("*/*")) {
            return true;
        }
        if (sliceItem.getSubType() == null && string2.indexOf(47) < 0) {
            return sliceItem.getFormat().equals(string2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sliceItem.getFormat());
        stringBuilder.append("/");
        stringBuilder.append(sliceItem.getSubType());
        return stringBuilder.toString().matches(string2.replaceAll("\\*", ".*"));
    }

    private static boolean contains(SliceItem sliceItem, SliceItem sliceItem2) {
        if (sliceItem != null && sliceItem2 != null) {
            return SliceQuery.stream(sliceItem).filter(new _$$Lambda$SliceQuery$fdDPNErwIni_vCQ6k_MlGGBunoE(sliceItem2)).findAny().isPresent();
        }
        return false;
    }

    public static SliceItem find(Slice slice, String string2) {
        return SliceQuery.find(slice, string2, (String[])null, null);
    }

    public static SliceItem find(Slice slice, String string2, String string3, String string4) {
        return SliceQuery.find(slice, string2, new String[]{string3}, new String[]{string4});
    }

    public static SliceItem find(Slice slice, String string2, String[] arrstring, String[] arrstring2) {
        List<String> list = slice.getHints();
        return SliceQuery.find(new SliceItem((Object)slice, "slice", null, list.toArray(new String[list.size()])), string2, arrstring, arrstring2);
    }

    public static SliceItem find(SliceItem sliceItem, String string2) {
        return SliceQuery.find(sliceItem, string2, (String[])null, null);
    }

    public static SliceItem find(SliceItem sliceItem, String string2, String string3, String string4) {
        return SliceQuery.find(sliceItem, string2, new String[]{string3}, new String[]{string4});
    }

    public static SliceItem find(SliceItem sliceItem, String string2, String[] arrstring, String[] arrstring2) {
        return SliceQuery.stream(sliceItem).filter(new _$$Lambda$SliceQuery$cG9kHpHpv4nbm7p3sCvlkQGlqQw(string2, arrstring, arrstring2)).findFirst().orElse(null);
    }

    public static List<SliceItem> findAll(SliceItem sliceItem, String string2) {
        return SliceQuery.findAll(sliceItem, string2, (String[])null, null);
    }

    public static List<SliceItem> findAll(SliceItem sliceItem, String string2, String string3, String string4) {
        return SliceQuery.findAll(sliceItem, string2, new String[]{string3}, new String[]{string4});
    }

    public static List<SliceItem> findAll(SliceItem sliceItem, String string2, String[] arrstring, String[] arrstring2) {
        return SliceQuery.stream(sliceItem).filter(new _$$Lambda$SliceQuery$hLToAajdaMbaf8BUtZ8fpGK980E(string2, arrstring, arrstring2)).collect(Collectors.toList());
    }

    public static SliceItem findNotContaining(SliceItem sliceItem, List<SliceItem> list) {
        SliceItem sliceItem2 = null;
        while (sliceItem2 == null && list.size() != 0) {
            SliceItem sliceItem3 = list.remove(0);
            if (SliceQuery.contains(sliceItem, sliceItem3)) continue;
            sliceItem2 = sliceItem3;
        }
        return sliceItem2;
    }

    public static SliceItem getPrimaryIcon(Slice object) {
        for (SliceItem sliceItem : ((Slice)object).getItems()) {
            if (Objects.equals(sliceItem.getFormat(), "image")) {
                return sliceItem;
            }
            if (SliceQuery.compareTypes(sliceItem, "slice") && sliceItem.hasHint("list") || sliceItem.hasHint("actions") || sliceItem.hasHint("list_item") || SliceQuery.compareTypes(sliceItem, "action") || (sliceItem = SliceQuery.find(sliceItem, "image")) == null) continue;
            return sliceItem;
        }
        return null;
    }

    static /* synthetic */ boolean lambda$contains$0(SliceItem sliceItem, SliceItem sliceItem2) {
        boolean bl = sliceItem2 == sliceItem;
        return bl;
    }

    static /* synthetic */ boolean lambda$find$2(String string2, String[] arrstring, String[] arrstring2, SliceItem sliceItem) {
        boolean bl = SliceQuery.compareTypes(sliceItem, string2) && sliceItem.hasHints(arrstring) && !sliceItem.hasAnyHints(arrstring2);
        return bl;
    }

    static /* synthetic */ boolean lambda$findAll$1(String string2, String[] arrstring, String[] arrstring2, SliceItem sliceItem) {
        boolean bl = SliceQuery.compareTypes(sliceItem, string2) && sliceItem.hasHints(arrstring) && !sliceItem.hasAnyHints(arrstring2);
        return bl;
    }

    public static Stream<SliceItem> stream(SliceItem sliceItem) {
        LinkedList<SliceItem> linkedList = new LinkedList<SliceItem>();
        linkedList.add(sliceItem);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<SliceItem>(){

            @Override
            public boolean hasNext() {
                boolean bl = Queue.this.size() != 0;
                return bl;
            }

            @Override
            public SliceItem next() {
                SliceItem sliceItem = (SliceItem)Queue.this.poll();
                if (SliceQuery.compareTypes(sliceItem, "slice") || SliceQuery.compareTypes(sliceItem, "action")) {
                    Queue.this.addAll(sliceItem.getSlice().getItems());
                }
                return sliceItem;
            }
        }, 0), false);
    }

}

