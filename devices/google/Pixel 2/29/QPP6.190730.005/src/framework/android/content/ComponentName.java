/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;

public final class ComponentName
implements Parcelable,
Cloneable,
Comparable<ComponentName> {
    public static final Parcelable.Creator<ComponentName> CREATOR = new Parcelable.Creator<ComponentName>(){

        @Override
        public ComponentName createFromParcel(Parcel parcel) {
            return new ComponentName(parcel);
        }

        public ComponentName[] newArray(int n) {
            return new ComponentName[n];
        }
    };
    private final String mClass;
    private final String mPackage;

    public ComponentName(Context context, Class<?> class_) {
        this.mPackage = context.getPackageName();
        this.mClass = class_.getName();
    }

    public ComponentName(Context context, String string2) {
        if (string2 != null) {
            this.mPackage = context.getPackageName();
            this.mClass = string2;
            return;
        }
        throw new NullPointerException("class name is null");
    }

    public ComponentName(Parcel parcel) {
        this.mPackage = parcel.readString();
        if (this.mPackage != null) {
            this.mClass = parcel.readString();
            if (this.mClass != null) {
                return;
            }
            throw new NullPointerException("class name is null");
        }
        throw new NullPointerException("package name is null");
    }

    private ComponentName(String string2, Parcel parcel) {
        this.mPackage = string2;
        this.mClass = parcel.readString();
    }

    public ComponentName(String string2, String string3) {
        if (string2 != null) {
            if (string3 != null) {
                this.mPackage = string2;
                this.mClass = string3;
                return;
            }
            throw new NullPointerException("class name is null");
        }
        throw new NullPointerException("package name is null");
    }

    private static void appendShortClassName(StringBuilder stringBuilder, String string2, String string3) {
        if (string3.startsWith(string2)) {
            int n = string2.length();
            int n2 = string3.length();
            if (n2 > n && string3.charAt(n) == '.') {
                stringBuilder.append(string3, n, n2);
                return;
            }
        }
        stringBuilder.append(string3);
    }

    @UnsupportedAppUsage
    public static void appendShortString(StringBuilder stringBuilder, String string2, String string3) {
        stringBuilder.append(string2);
        stringBuilder.append('/');
        ComponentName.appendShortClassName(stringBuilder, string2, string3);
    }

    public static ComponentName createRelative(Context context, String string2) {
        return ComponentName.createRelative(context.getPackageName(), string2);
    }

    public static ComponentName createRelative(String string2, String string3) {
        if (!TextUtils.isEmpty(string3)) {
            if (string3.charAt(0) == '.') {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(string3);
                string3 = stringBuilder.toString();
            }
            return new ComponentName(string2, string3);
        }
        throw new IllegalArgumentException("class name cannot be empty");
    }

    public static String flattenToShortString(ComponentName object) {
        object = object == null ? null : ((ComponentName)object).flattenToShortString();
        return object;
    }

    private static void printShortClassName(PrintWriter printWriter, String string2, String string3) {
        if (string3.startsWith(string2)) {
            int n = string2.length();
            int n2 = string3.length();
            if (n2 > n && string3.charAt(n) == '.') {
                printWriter.write(string3, n, n2 - n);
                return;
            }
        }
        printWriter.print(string3);
    }

    @UnsupportedAppUsage
    public static void printShortString(PrintWriter printWriter, String string2, String string3) {
        printWriter.print(string2);
        printWriter.print('/');
        ComponentName.printShortClassName(printWriter, string2, string3);
    }

    public static ComponentName readFromParcel(Parcel object) {
        String string2 = ((Parcel)object).readString();
        object = string2 != null ? new ComponentName(string2, (Parcel)object) : null;
        return object;
    }

    public static ComponentName unflattenFromString(String charSequence) {
        int n = ((String)charSequence).indexOf(47);
        if (n >= 0 && n + 1 < ((String)charSequence).length()) {
            String string2 = ((String)charSequence).substring(0, n);
            String string3 = ((String)charSequence).substring(n + 1);
            charSequence = string3;
            if (string3.length() > 0) {
                charSequence = string3;
                if (string3.charAt(0) == '.') {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append(string3);
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            }
            return new ComponentName(string2, (String)charSequence);
        }
        return null;
    }

    public static void writeToParcel(ComponentName componentName, Parcel parcel) {
        if (componentName != null) {
            componentName.writeToParcel(parcel, 0);
        } else {
            parcel.writeString(null);
        }
    }

    public void appendShortString(StringBuilder stringBuilder) {
        ComponentName.appendShortString(stringBuilder, this.mPackage, this.mClass);
    }

    public ComponentName clone() {
        return new ComponentName(this.mPackage, this.mClass);
    }

    @Override
    public int compareTo(ComponentName componentName) {
        int n = this.mPackage.compareTo(componentName.mPackage);
        if (n != 0) {
            return n;
        }
        return this.mClass.compareTo(componentName.mClass);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null) {
            try {
                boolean bl2;
                object = (ComponentName)object;
                if (this.mPackage.equals(((ComponentName)object).mPackage) && (bl2 = this.mClass.equals(((ComponentName)object).mClass))) {
                    bl = true;
                }
                return bl;
            }
            catch (ClassCastException classCastException) {
            }
        }
        return false;
    }

    public String flattenToShortString() {
        StringBuilder stringBuilder = new StringBuilder(this.mPackage.length() + this.mClass.length());
        ComponentName.appendShortString(stringBuilder, this.mPackage, this.mClass);
        return stringBuilder.toString();
    }

    public String flattenToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mPackage);
        stringBuilder.append("/");
        stringBuilder.append(this.mClass);
        return stringBuilder.toString();
    }

    public String getClassName() {
        return this.mClass;
    }

    public String getPackageName() {
        return this.mPackage;
    }

    public String getShortClassName() {
        if (this.mClass.startsWith(this.mPackage)) {
            int n = this.mPackage.length();
            int n2 = this.mClass.length();
            if (n2 > n && this.mClass.charAt(n) == '.') {
                return this.mClass.substring(n, n2);
            }
        }
        return this.mClass;
    }

    public int hashCode() {
        return this.mPackage.hashCode() + this.mClass.hashCode();
    }

    public String toShortString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(this.mPackage);
        stringBuilder.append("/");
        stringBuilder.append(this.mClass);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ComponentInfo{");
        stringBuilder.append(this.mPackage);
        stringBuilder.append("/");
        stringBuilder.append(this.mClass);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPackage);
        parcel.writeString(this.mClass);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1138166333441L, this.mPackage);
        protoOutputStream.write(1138166333442L, this.mClass);
        protoOutputStream.end(l);
    }

    @FunctionalInterface
    public static interface WithComponentName {
        public ComponentName getComponentName();
    }

}

