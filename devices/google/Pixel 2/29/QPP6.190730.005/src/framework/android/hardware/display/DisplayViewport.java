/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.graphics.Rect;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class DisplayViewport {
    public static final int VIEWPORT_EXTERNAL = 2;
    public static final int VIEWPORT_INTERNAL = 1;
    public static final int VIEWPORT_VIRTUAL = 3;
    public int deviceHeight;
    public int deviceWidth;
    public int displayId;
    public final Rect logicalFrame = new Rect();
    public int orientation;
    public final Rect physicalFrame = new Rect();
    public Byte physicalPort;
    public int type;
    public String uniqueId;
    public boolean valid;

    public static String typeToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UNKNOWN (");
                    stringBuilder.append(n);
                    stringBuilder.append(")");
                    return stringBuilder.toString();
                }
                return "VIRTUAL";
            }
            return "EXTERNAL";
        }
        return "INTERNAL";
    }

    public void copyFrom(DisplayViewport displayViewport) {
        this.valid = displayViewport.valid;
        this.displayId = displayViewport.displayId;
        this.orientation = displayViewport.orientation;
        this.logicalFrame.set(displayViewport.logicalFrame);
        this.physicalFrame.set(displayViewport.physicalFrame);
        this.deviceWidth = displayViewport.deviceWidth;
        this.deviceHeight = displayViewport.deviceHeight;
        this.uniqueId = displayViewport.uniqueId;
        this.physicalPort = displayViewport.physicalPort;
        this.type = displayViewport.type;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof DisplayViewport)) {
            return false;
        }
        object = (DisplayViewport)object;
        if (!(this.valid == ((DisplayViewport)object).valid && this.displayId == ((DisplayViewport)object).displayId && this.orientation == ((DisplayViewport)object).orientation && this.logicalFrame.equals(((DisplayViewport)object).logicalFrame) && this.physicalFrame.equals(((DisplayViewport)object).physicalFrame) && this.deviceWidth == ((DisplayViewport)object).deviceWidth && this.deviceHeight == ((DisplayViewport)object).deviceHeight && TextUtils.equals(this.uniqueId, ((DisplayViewport)object).uniqueId) && this.physicalPort == ((DisplayViewport)object).physicalPort && this.type == ((DisplayViewport)object).type)) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        int n = 1 + (1 * 31 + this.valid);
        n += n * 31 + this.displayId;
        n += n * 31 + this.orientation;
        n += n * 31 + this.logicalFrame.hashCode();
        n += n * 31 + this.physicalFrame.hashCode();
        n += n * 31 + this.deviceWidth;
        n += n * 31 + this.deviceHeight;
        n += n * 31 + this.uniqueId.hashCode();
        n += n * 31 + this.physicalPort;
        return n + (n * 31 + this.type);
    }

    public DisplayViewport makeCopy() {
        DisplayViewport displayViewport = new DisplayViewport();
        displayViewport.copyFrom(this);
        return displayViewport;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DisplayViewport{type=");
        stringBuilder.append(DisplayViewport.typeToString(this.type));
        stringBuilder.append(", valid=");
        stringBuilder.append(this.valid);
        stringBuilder.append(", displayId=");
        stringBuilder.append(this.displayId);
        stringBuilder.append(", uniqueId='");
        stringBuilder.append(this.uniqueId);
        stringBuilder.append("', physicalPort=");
        stringBuilder.append(this.physicalPort);
        stringBuilder.append(", orientation=");
        stringBuilder.append(this.orientation);
        stringBuilder.append(", logicalFrame=");
        stringBuilder.append(this.logicalFrame);
        stringBuilder.append(", physicalFrame=");
        stringBuilder.append(this.physicalFrame);
        stringBuilder.append(", deviceWidth=");
        stringBuilder.append(this.deviceWidth);
        stringBuilder.append(", deviceHeight=");
        stringBuilder.append(this.deviceHeight);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ViewportType {
    }

}

