/*
 * Decompiled with CFR 0.145.
 */
package javax.security.auth.callback;

import java.io.Serializable;
import javax.security.auth.callback.Callback;

public class PasswordCallback
implements Callback,
Serializable {
    private static final long serialVersionUID = 2267422647454909926L;
    private boolean echoOn;
    private char[] inputPassword;
    private String prompt;

    public PasswordCallback(String string, boolean bl) {
        if (string != null && string.length() != 0) {
            this.prompt = string;
            this.echoOn = bl;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void clearPassword() {
        if (this.inputPassword != null) {
            char[] arrc;
            for (int i = 0; i < (arrc = this.inputPassword).length; ++i) {
                arrc[i] = (char)32;
            }
        }
    }

    public char[] getPassword() {
        Object object = this.inputPassword;
        object = object == null ? null : (char[])object.clone();
        return object;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public boolean isEchoOn() {
        return this.echoOn;
    }

    public void setPassword(char[] object) {
        object = object == null ? null : (char[])object.clone();
        this.inputPassword = object;
    }
}

