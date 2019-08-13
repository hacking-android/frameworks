/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.MonthDisplayHelper;

public class DayOfMonthCursor
extends MonthDisplayHelper {
    private int mColumn;
    private int mRow;

    public DayOfMonthCursor(int n, int n2, int n3, int n4) {
        super(n, n2, n4);
        this.mRow = this.getRowOf(n3);
        this.mColumn = this.getColumnOf(n3);
    }

    public boolean down() {
        if (this.isWithinCurrentMonth(this.mRow + 1, this.mColumn)) {
            ++this.mRow;
            return false;
        }
        this.nextMonth();
        this.mRow = 0;
        while (!this.isWithinCurrentMonth(this.mRow, this.mColumn)) {
            ++this.mRow;
        }
        return true;
    }

    public int getSelectedColumn() {
        return this.mColumn;
    }

    public int getSelectedDayOfMonth() {
        return this.getDayAt(this.mRow, this.mColumn);
    }

    public int getSelectedMonthOffset() {
        if (this.isWithinCurrentMonth(this.mRow, this.mColumn)) {
            return 0;
        }
        if (this.mRow == 0) {
            return -1;
        }
        return 1;
    }

    public int getSelectedRow() {
        return this.mRow;
    }

    public boolean isSelected(int n, int n2) {
        boolean bl = this.mRow == n && this.mColumn == n2;
        return bl;
    }

    public boolean left() {
        int n = this.mColumn;
        if (n == 0) {
            --this.mRow;
            this.mColumn = 6;
        } else {
            this.mColumn = n - 1;
        }
        if (this.isWithinCurrentMonth(this.mRow, this.mColumn)) {
            return false;
        }
        this.previousMonth();
        n = this.getNumberOfDaysInMonth();
        this.mRow = this.getRowOf(n);
        this.mColumn = this.getColumnOf(n);
        return true;
    }

    public boolean right() {
        int n = this.mColumn;
        if (n == 6) {
            ++this.mRow;
            this.mColumn = 0;
        } else {
            this.mColumn = n + 1;
        }
        if (this.isWithinCurrentMonth(this.mRow, this.mColumn)) {
            return false;
        }
        this.nextMonth();
        this.mRow = 0;
        this.mColumn = 0;
        while (!this.isWithinCurrentMonth(this.mRow, this.mColumn)) {
            ++this.mColumn;
        }
        return true;
    }

    public void setSelectedDayOfMonth(int n) {
        this.mRow = this.getRowOf(n);
        this.mColumn = this.getColumnOf(n);
    }

    public void setSelectedRowColumn(int n, int n2) {
        this.mRow = n;
        this.mColumn = n2;
    }

    public boolean up() {
        if (this.isWithinCurrentMonth(this.mRow - 1, this.mColumn)) {
            --this.mRow;
            return false;
        }
        this.previousMonth();
        this.mRow = 5;
        while (!this.isWithinCurrentMonth(this.mRow, this.mColumn)) {
            --this.mRow;
        }
        return true;
    }
}

