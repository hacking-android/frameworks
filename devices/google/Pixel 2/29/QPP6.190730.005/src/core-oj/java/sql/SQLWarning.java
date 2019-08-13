/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLWarning
extends SQLException {
    private static final long serialVersionUID = 3917336774604784856L;

    public SQLWarning() {
        DriverManager.println("SQLWarning: ");
    }

    public SQLWarning(String string) {
        super(string);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SQLWarning: reason(");
        stringBuilder.append(string);
        stringBuilder.append(")");
        DriverManager.println(stringBuilder.toString());
    }

    public SQLWarning(String string, String string2) {
        super(string, string2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SQLWarning: reason(");
        stringBuilder.append(string);
        stringBuilder.append(") SQLState(");
        stringBuilder.append(string2);
        stringBuilder.append(")");
        DriverManager.println(stringBuilder.toString());
    }

    public SQLWarning(String string, String string2, int n) {
        super(string, string2, n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SQLWarning: reason(");
        stringBuilder.append(string);
        stringBuilder.append(") SQLState(");
        stringBuilder.append(string2);
        stringBuilder.append(") vendor code(");
        stringBuilder.append(n);
        stringBuilder.append(")");
        DriverManager.println(stringBuilder.toString());
    }

    public SQLWarning(String string, String string2, int n, Throwable serializable) {
        super(string, string2, n, (Throwable)serializable);
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("SQLWarning: reason(");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append(") SQLState(");
        ((StringBuilder)serializable).append(string2);
        ((StringBuilder)serializable).append(") vendor code(");
        ((StringBuilder)serializable).append(n);
        ((StringBuilder)serializable).append(")");
        DriverManager.println(((StringBuilder)serializable).toString());
    }

    public SQLWarning(String string, String string2, Throwable serializable) {
        super(string, string2, (Throwable)serializable);
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("SQLWarning: reason(");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append(") SQLState(");
        ((StringBuilder)serializable).append(string2);
        ((StringBuilder)serializable).append(")");
        DriverManager.println(((StringBuilder)serializable).toString());
    }

    public SQLWarning(String string, Throwable serializable) {
        super(string, (Throwable)serializable);
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("SQLWarning : reason(");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append(")");
        DriverManager.println(((StringBuilder)serializable).toString());
    }

    public SQLWarning(Throwable throwable) {
        super(throwable);
        DriverManager.println("SQLWarning");
    }

    public SQLWarning getNextWarning() {
        try {
            SQLWarning sQLWarning = (SQLWarning)this.getNextException();
            return sQLWarning;
        }
        catch (ClassCastException classCastException) {
            throw new Error("SQLWarning chain holds value that is not a SQLWarning");
        }
    }

    public void setNextWarning(SQLWarning sQLWarning) {
        this.setNextException(sQLWarning);
    }
}

