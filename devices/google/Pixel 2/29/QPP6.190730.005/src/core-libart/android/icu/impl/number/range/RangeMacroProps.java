/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.range;

import android.icu.number.NumberRangeFormatter;
import android.icu.number.UnlocalizedNumberFormatter;
import android.icu.util.ULocale;
import java.util.Objects;

public class RangeMacroProps {
    public NumberRangeFormatter.RangeCollapse collapse;
    public UnlocalizedNumberFormatter formatter1;
    public UnlocalizedNumberFormatter formatter2;
    public NumberRangeFormatter.RangeIdentityFallback identityFallback;
    public ULocale loc;
    public int sameFormatters = -1;

    public boolean equals(Object object) {
        boolean bl;
        block3 : {
            bl = false;
            if (object == null) {
                return false;
            }
            if (this == object) {
                return true;
            }
            if (!(object instanceof RangeMacroProps)) {
                return false;
            }
            object = (RangeMacroProps)object;
            if (!Objects.equals(this.formatter1, ((RangeMacroProps)object).formatter1) || !Objects.equals(this.formatter2, ((RangeMacroProps)object).formatter2) || !Objects.equals((Object)this.collapse, (Object)((RangeMacroProps)object).collapse) || !Objects.equals((Object)this.identityFallback, (Object)((RangeMacroProps)object).identityFallback) || !Objects.equals(this.loc, ((RangeMacroProps)object).loc)) break block3;
            bl = true;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.formatter1, this.formatter2, this.collapse, this.identityFallback, this.loc});
    }
}

