/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.os.Bundle;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public final class InputConnectionInspector {
    private static final Map<Class, Integer> sMissingMethodsMap = Collections.synchronizedMap(new WeakHashMap());

    public static int getMissingMethodFlags(InputConnection inputConnection) {
        if (inputConnection == null) {
            return 0;
        }
        if (inputConnection instanceof BaseInputConnection) {
            return 0;
        }
        if (inputConnection instanceof InputConnectionWrapper) {
            return ((InputConnectionWrapper)inputConnection).getMissingMethodFlags();
        }
        return InputConnectionInspector.getMissingMethodFlagsInternal(inputConnection.getClass());
    }

    public static String getMissingMethodFlagsAsString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = true;
        if ((n & 1) != 0) {
            stringBuilder.append("getSelectedText(int)");
            bl = false;
        }
        boolean bl2 = bl;
        if ((n & 2) != 0) {
            if (!bl) {
                stringBuilder.append(",");
            }
            stringBuilder.append("setComposingRegion(int, int)");
            bl2 = false;
        }
        bl = bl2;
        if ((n & 4) != 0) {
            if (!bl2) {
                stringBuilder.append(",");
            }
            stringBuilder.append("commitCorrection(CorrectionInfo)");
            bl = false;
        }
        bl2 = bl;
        if ((n & 8) != 0) {
            if (!bl) {
                stringBuilder.append(",");
            }
            stringBuilder.append("requestCursorUpdate(int)");
            bl2 = false;
        }
        bl = bl2;
        if ((n & 16) != 0) {
            if (!bl2) {
                stringBuilder.append(",");
            }
            stringBuilder.append("deleteSurroundingTextInCodePoints(int, int)");
            bl = false;
        }
        if ((n & 32) != 0) {
            if (!bl) {
                stringBuilder.append(",");
            }
            stringBuilder.append("getHandler()");
        }
        if ((n & 64) != 0) {
            if (!bl) {
                stringBuilder.append(",");
            }
            stringBuilder.append("closeConnection()");
        }
        if ((n & 128) != 0) {
            if (!bl) {
                stringBuilder.append(",");
            }
            stringBuilder.append("commitContent(InputContentInfo, Bundle)");
        }
        return stringBuilder.toString();
    }

    public static int getMissingMethodFlagsInternal(Class class_) {
        Integer n = sMissingMethodsMap.get(class_);
        if (n != null) {
            return n;
        }
        int n2 = 0;
        if (!InputConnectionInspector.hasGetSelectedText(class_)) {
            n2 = false | true;
        }
        int n3 = n2;
        if (!InputConnectionInspector.hasSetComposingRegion(class_)) {
            n3 = n2 | 2;
        }
        n2 = n3;
        if (!InputConnectionInspector.hasCommitCorrection(class_)) {
            n2 = n3 | 4;
        }
        n3 = n2;
        if (!InputConnectionInspector.hasRequestCursorUpdate(class_)) {
            n3 = n2 | 8;
        }
        n2 = n3;
        if (!InputConnectionInspector.hasDeleteSurroundingTextInCodePoints(class_)) {
            n2 = n3 | 16;
        }
        int n4 = n2;
        if (!InputConnectionInspector.hasGetHandler(class_)) {
            n4 = n2 | 32;
        }
        n3 = n4;
        if (!InputConnectionInspector.hasCloseConnection(class_)) {
            n3 = n4 | 64;
        }
        n2 = n3;
        if (!InputConnectionInspector.hasCommitContent(class_)) {
            n2 = n3 | 128;
        }
        sMissingMethodsMap.put(class_, n2);
        return n2;
    }

    private static boolean hasCloseConnection(Class class_) {
        try {
            boolean bl = Modifier.isAbstract(class_.getMethod("closeConnection", new Class[0]).getModifiers());
            return bl ^ true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return false;
        }
    }

    private static boolean hasCommitContent(Class class_) {
        try {
            boolean bl = Modifier.isAbstract(class_.getMethod("commitContent", InputContentInfo.class, Integer.TYPE, Bundle.class).getModifiers());
            return bl ^ true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return false;
        }
    }

    private static boolean hasCommitCorrection(Class class_) {
        try {
            boolean bl = Modifier.isAbstract(class_.getMethod("commitCorrection", CorrectionInfo.class).getModifiers());
            return bl ^ true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return false;
        }
    }

    private static boolean hasDeleteSurroundingTextInCodePoints(Class class_) {
        try {
            boolean bl = Modifier.isAbstract(class_.getMethod("deleteSurroundingTextInCodePoints", Integer.TYPE, Integer.TYPE).getModifiers());
            return bl ^ true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return false;
        }
    }

    private static boolean hasGetHandler(Class class_) {
        try {
            boolean bl = Modifier.isAbstract(class_.getMethod("getHandler", new Class[0]).getModifiers());
            return bl ^ true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return false;
        }
    }

    private static boolean hasGetSelectedText(Class class_) {
        try {
            boolean bl = Modifier.isAbstract(class_.getMethod("getSelectedText", Integer.TYPE).getModifiers());
            return bl ^ true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return false;
        }
    }

    private static boolean hasRequestCursorUpdate(Class class_) {
        try {
            boolean bl = Modifier.isAbstract(class_.getMethod("requestCursorUpdates", Integer.TYPE).getModifiers());
            return bl ^ true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return false;
        }
    }

    private static boolean hasSetComposingRegion(Class class_) {
        try {
            boolean bl = Modifier.isAbstract(class_.getMethod("setComposingRegion", Integer.TYPE, Integer.TYPE).getModifiers());
            return bl ^ true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return false;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MissingMethodFlags {
        public static final int CLOSE_CONNECTION = 64;
        public static final int COMMIT_CONTENT = 128;
        public static final int COMMIT_CORRECTION = 4;
        public static final int DELETE_SURROUNDING_TEXT_IN_CODE_POINTS = 16;
        public static final int GET_HANDLER = 32;
        public static final int GET_SELECTED_TEXT = 1;
        public static final int REQUEST_CURSOR_UPDATES = 8;
        public static final int SET_COMPOSING_REGION = 2;
    }

}

