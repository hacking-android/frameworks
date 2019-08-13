/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

public final class DialogState
extends Enum<DialogState> {
    private static final /* synthetic */ DialogState[] $VALUES;
    public static final /* enum */ DialogState CONFIRMED;
    public static final /* enum */ DialogState EARLY;
    public static final /* enum */ DialogState TERMINATED;
    public static final int _CONFIRMED;
    public static final int _EARLY;
    public static final int _TERMINATED;

    static {
        EARLY = new DialogState();
        CONFIRMED = new DialogState();
        TERMINATED = new DialogState();
        DialogState dialogState = EARLY;
        $VALUES = new DialogState[]{dialogState, CONFIRMED, TERMINATED};
        _EARLY = dialogState.ordinal();
        _CONFIRMED = CONFIRMED.ordinal();
        _TERMINATED = TERMINATED.ordinal();
    }

    public static DialogState getObject(int n) {
        try {
            DialogState dialogState = DialogState.values()[n];
            return dialogState;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid dialog state: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public static DialogState valueOf(String string) {
        return Enum.valueOf(DialogState.class, string);
    }

    public static DialogState[] values() {
        return (DialogState[])$VALUES.clone();
    }

    public int getValue() {
        return this.ordinal();
    }
}

