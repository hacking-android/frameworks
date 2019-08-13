/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics.fonts;

import android.graphics.fonts.Font;
import android.graphics.fonts.FontStyle;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.CriticalNative;
import java.util.ArrayList;
import java.util.HashSet;
import libcore.util.NativeAllocationRegistry;

public final class FontFamily {
    private static final String TAG = "FontFamily";
    private final ArrayList<Font> mFonts;
    private final long mNativePtr;

    private FontFamily(ArrayList<Font> arrayList, long l) {
        this.mFonts = arrayList;
        this.mNativePtr = l;
    }

    public Font getFont(int n) {
        return this.mFonts.get(n);
    }

    public long getNativePtr() {
        return this.mNativePtr;
    }

    public int getSize() {
        return this.mFonts.size();
    }

    public static final class Builder {
        private static final NativeAllocationRegistry sFamilyRegistory = NativeAllocationRegistry.createMalloced((ClassLoader)FontFamily.class.getClassLoader(), (long)Builder.nGetReleaseNativeFamily());
        private final ArrayList<Font> mFonts = new ArrayList();
        private final HashSet<Integer> mStyleHashSet = new HashSet();

        public Builder(Font font) {
            Preconditions.checkNotNull(font, "font can not be null");
            this.mStyleHashSet.add(Builder.makeStyleIdentifier(font));
            this.mFonts.add(font);
        }

        private static int makeStyleIdentifier(Font font) {
            return font.getStyle().getWeight() | font.getStyle().getSlant() << 16;
        }

        @CriticalNative
        private static native void nAddFont(long var0, long var2);

        private static native long nBuild(long var0, String var2, int var3, boolean var4);

        @CriticalNative
        private static native long nGetReleaseNativeFamily();

        private static native long nInitBuilder();

        public Builder addFont(Font font) {
            Preconditions.checkNotNull(font, "font can not be null");
            if (this.mStyleHashSet.add(Builder.makeStyleIdentifier(font))) {
                this.mFonts.add(font);
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(font);
            stringBuilder.append(" has already been added");
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public FontFamily build() {
            return this.build("", 0, true);
        }

        public FontFamily build(String object, int n, boolean bl) {
            long l = Builder.nInitBuilder();
            for (int i = 0; i < this.mFonts.size(); ++i) {
                Builder.nAddFont(l, this.mFonts.get(i).getNativePtr());
            }
            l = Builder.nBuild(l, (String)object, n, bl);
            object = new FontFamily(this.mFonts, l);
            sFamilyRegistory.registerNativeAllocation(object, l);
            return object;
        }
    }

}

