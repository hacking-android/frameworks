/*
 * Decompiled with CFR 0.145.
 */
package java.sql;

import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class SQLException
extends Exception
implements Iterable<Throwable> {
    private static final AtomicReferenceFieldUpdater<SQLException, SQLException> nextUpdater = AtomicReferenceFieldUpdater.newUpdater(SQLException.class, SQLException.class, "next");
    private static final long serialVersionUID = 2135244094396331484L;
    private String SQLState;
    private volatile SQLException next;
    private int vendorCode;

    public SQLException() {
        this.SQLState = null;
        this.vendorCode = 0;
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            this.printStackTrace(DriverManager.getLogWriter());
        }
    }

    public SQLException(String string) {
        super(string);
        this.SQLState = null;
        this.vendorCode = 0;
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            this.printStackTrace(DriverManager.getLogWriter());
        }
    }

    public SQLException(String charSequence, String string) {
        super((String)charSequence);
        this.SQLState = string;
        this.vendorCode = 0;
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            this.printStackTrace(DriverManager.getLogWriter());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("SQLException: SQLState(");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(")");
            DriverManager.println(((StringBuilder)charSequence).toString());
        }
    }

    public SQLException(String charSequence, String string, int n) {
        super((String)charSequence);
        this.SQLState = string;
        this.vendorCode = n;
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("SQLState(");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(") vendor code(");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(")");
            DriverManager.println(((StringBuilder)charSequence).toString());
            this.printStackTrace(DriverManager.getLogWriter());
        }
    }

    public SQLException(String charSequence, String string, int n, Throwable throwable) {
        super((String)charSequence, throwable);
        this.SQLState = string;
        this.vendorCode = n;
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("SQLState(");
            ((StringBuilder)charSequence).append(this.SQLState);
            ((StringBuilder)charSequence).append(") vendor code(");
            ((StringBuilder)charSequence).append(n);
            ((StringBuilder)charSequence).append(")");
            DriverManager.println(((StringBuilder)charSequence).toString());
            this.printStackTrace(DriverManager.getLogWriter());
        }
    }

    public SQLException(String charSequence, String string, Throwable throwable) {
        super((String)charSequence, throwable);
        this.SQLState = string;
        this.vendorCode = 0;
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            this.printStackTrace(DriverManager.getLogWriter());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("SQLState(");
            ((StringBuilder)charSequence).append(this.SQLState);
            ((StringBuilder)charSequence).append(")");
            DriverManager.println(((StringBuilder)charSequence).toString());
        }
    }

    public SQLException(String string, Throwable throwable) {
        super(string, throwable);
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            this.printStackTrace(DriverManager.getLogWriter());
        }
    }

    public SQLException(Throwable throwable) {
        super(throwable);
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            this.printStackTrace(DriverManager.getLogWriter());
        }
    }

    public int getErrorCode() {
        return this.vendorCode;
    }

    public SQLException getNextException() {
        return this.next;
    }

    public String getSQLState() {
        return this.SQLState;
    }

    @Override
    public Iterator<Throwable> iterator() {
        return new Iterator<Throwable>(){
            Throwable cause;
            SQLException firstException;
            SQLException nextException;
            {
                this.firstException = SQLException.this;
                this.nextException = this.firstException.getNextException();
                this.cause = this.firstException.getCause();
            }

            @Override
            public boolean hasNext() {
                return this.firstException != null || this.nextException != null || this.cause != null;
                {
                }
            }

            @Override
            public Throwable next() {
                block5 : {
                    Throwable throwable;
                    block3 : {
                        Throwable throwable2;
                        block4 : {
                            block2 : {
                                if (this.firstException == null) break block2;
                                throwable = this.firstException;
                                this.firstException = null;
                                break block3;
                            }
                            throwable2 = this.cause;
                            if (throwable2 == null) break block4;
                            throwable = this.cause;
                            this.cause = throwable2.getCause();
                            break block3;
                        }
                        throwable2 = this.nextException;
                        if (throwable2 == null) break block5;
                        throwable = this.nextException;
                        this.cause = throwable2.getCause();
                        this.nextException = this.nextException.getNextException();
                    }
                    return throwable;
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public void setNextException(SQLException sQLException) {
        SQLException sQLException2 = this;
        do {
            SQLException sQLException3;
            if ((sQLException3 = sQLException2.next) != null) {
                sQLException2 = sQLException3;
                continue;
            }
            if (nextUpdater.compareAndSet(sQLException2, null, sQLException)) {
                return;
            }
            sQLException2 = sQLException2.next;
        } while (true);
    }

}

