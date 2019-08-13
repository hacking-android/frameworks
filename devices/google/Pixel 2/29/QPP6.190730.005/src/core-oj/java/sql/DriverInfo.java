/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.sql.Driver;

class DriverInfo {
    final Driver driver;

    DriverInfo(Driver driver) {
        this.driver = driver;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof DriverInfo && this.driver == ((DriverInfo)object).driver;
        return bl;
    }

    public int hashCode() {
        return this.driver.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("driver[className=");
        stringBuilder.append(this.driver);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

