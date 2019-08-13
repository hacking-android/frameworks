/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import android.database.sqlite.SQLiteCustomFunction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SQLiteDatabaseConfiguration {
    private static final Pattern EMAIL_IN_DB_PATTERN = Pattern.compile("[\\w\\.\\-]+@[\\w\\.\\-]+");
    public static final String MEMORY_DB_PATH = ":memory:";
    public final ArrayList<SQLiteCustomFunction> customFunctions = new ArrayList();
    public boolean foreignKeyConstraintsEnabled;
    public long idleConnectionTimeoutMs = Long.MAX_VALUE;
    public String journalMode;
    public final String label;
    public Locale locale;
    public int lookasideSlotCount = -1;
    public int lookasideSlotSize = -1;
    @UnsupportedAppUsage
    public int maxSqlCacheSize;
    public int openFlags;
    public final String path;
    public String syncMode;

    public SQLiteDatabaseConfiguration(SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        if (sQLiteDatabaseConfiguration != null) {
            this.path = sQLiteDatabaseConfiguration.path;
            this.label = sQLiteDatabaseConfiguration.label;
            this.updateParametersFrom(sQLiteDatabaseConfiguration);
            return;
        }
        throw new IllegalArgumentException("other must not be null.");
    }

    public SQLiteDatabaseConfiguration(String string2, int n) {
        if (string2 != null) {
            this.path = string2;
            this.label = SQLiteDatabaseConfiguration.stripPathForLogs(string2);
            this.openFlags = n;
            this.maxSqlCacheSize = 25;
            this.locale = Locale.getDefault();
            return;
        }
        throw new IllegalArgumentException("path must not be null.");
    }

    private static String stripPathForLogs(String string2) {
        if (string2.indexOf(64) == -1) {
            return string2;
        }
        return EMAIL_IN_DB_PATTERN.matcher(string2).replaceAll("XX@YY");
    }

    public boolean isInMemoryDb() {
        return this.path.equalsIgnoreCase(MEMORY_DB_PATH);
    }

    boolean isLegacyCompatibilityWalEnabled() {
        boolean bl = this.journalMode == null && this.syncMode == null && (this.openFlags & Integer.MIN_VALUE) != 0;
        return bl;
    }

    boolean isLookasideConfigSet() {
        boolean bl = this.lookasideSlotCount >= 0 && this.lookasideSlotSize >= 0;
        return bl;
    }

    public void updateParametersFrom(SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        if (sQLiteDatabaseConfiguration != null) {
            if (this.path.equals(sQLiteDatabaseConfiguration.path)) {
                this.openFlags = sQLiteDatabaseConfiguration.openFlags;
                this.maxSqlCacheSize = sQLiteDatabaseConfiguration.maxSqlCacheSize;
                this.locale = sQLiteDatabaseConfiguration.locale;
                this.foreignKeyConstraintsEnabled = sQLiteDatabaseConfiguration.foreignKeyConstraintsEnabled;
                this.customFunctions.clear();
                this.customFunctions.addAll(sQLiteDatabaseConfiguration.customFunctions);
                this.lookasideSlotSize = sQLiteDatabaseConfiguration.lookasideSlotSize;
                this.lookasideSlotCount = sQLiteDatabaseConfiguration.lookasideSlotCount;
                this.idleConnectionTimeoutMs = sQLiteDatabaseConfiguration.idleConnectionTimeoutMs;
                this.journalMode = sQLiteDatabaseConfiguration.journalMode;
                this.syncMode = sQLiteDatabaseConfiguration.syncMode;
                return;
            }
            throw new IllegalArgumentException("other configuration must refer to the same database.");
        }
        throw new IllegalArgumentException("other must not be null.");
    }
}

