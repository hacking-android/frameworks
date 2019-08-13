/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

import com.android.okhttp.Callback;
import com.android.okhttp.Connection;
import com.android.okhttp.Dispatcher;
import com.android.okhttp.HttpUrl;
import com.android.okhttp.Interceptor;
import com.android.okhttp.OkHttpClient;
import com.android.okhttp.Request;
import com.android.okhttp.Response;
import com.android.okhttp.internal.Internal;
import com.android.okhttp.internal.NamedRunnable;
import com.android.okhttp.internal.http.HttpEngine;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Call {
    volatile boolean canceled;
    private final OkHttpClient client;
    HttpEngine engine;
    private boolean executed;
    Request originalRequest;

    protected Call(OkHttpClient okHttpClient, Request request) {
        this.client = okHttpClient.copyWithDefaults();
        this.originalRequest = request;
    }

    static /* synthetic */ Response access$100(Call call, boolean bl) throws IOException {
        return call.getResponseWithInterceptorChain(bl);
    }

    static /* synthetic */ String access$200(Call call) {
        return call.toLoggableString();
    }

    private Response getResponseWithInterceptorChain(boolean bl) throws IOException {
        return new ApplicationInterceptorChain(0, this.originalRequest, bl).proceed(this.originalRequest);
    }

    private String toLoggableString() {
        String string = this.canceled ? "canceled call" : "call";
        HttpUrl httpUrl = this.originalRequest.httpUrl().resolve("/...");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" to ");
        stringBuilder.append(httpUrl);
        return stringBuilder.toString();
    }

    public void cancel() {
        this.canceled = true;
        HttpEngine httpEngine = this.engine;
        if (httpEngine != null) {
            httpEngine.cancel();
        }
    }

    public void enqueue(Callback callback) {
        this.enqueue(callback, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void enqueue(Callback object, boolean bl) {
        synchronized (this) {
            if (!this.executed) {
                this.executed = true;
                // MONITOREXIT [2, 3] lbl4 : MonitorExitStatement: MONITOREXIT : this
                this.client.getDispatcher().enqueue(new AsyncCall((Callback)object, bl));
                return;
            }
            object = new IllegalStateException("Already Executed");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                IllegalStateException illegalStateException = new IllegalStateException("Already Executed");
                throw illegalStateException;
            }
            this.executed = true;
        }
        try {
            this.client.getDispatcher().executed(this);
            Object object = this.getResponseWithInterceptorChain(false);
            if (object != null) {
                this.client.getDispatcher().finished(this);
                return object;
            }
            object = new IOException("Canceled");
            throw object;
        }
        catch (Throwable throwable) {
            this.client.getDispatcher().finished(this);
            throw throwable;
        }
    }

    /*
     * Exception decompiling
     */
    Response getResponse(Request var1_1, boolean var2_4) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public boolean isExecuted() {
        synchronized (this) {
            boolean bl = this.executed;
            return bl;
        }
    }

    Object tag() {
        return this.originalRequest.tag();
    }

    class ApplicationInterceptorChain
    implements Interceptor.Chain {
        private final boolean forWebSocket;
        private final int index;
        private final Request request;

        ApplicationInterceptorChain(int n, Request request, boolean bl) {
            this.index = n;
            this.request = request;
            this.forWebSocket = bl;
        }

        @Override
        public Connection connection() {
            return null;
        }

        @Override
        public Response proceed(Request object) throws IOException {
            if (this.index < Call.this.client.interceptors().size()) {
                Object object2 = new ApplicationInterceptorChain(this.index + 1, (Request)object, this.forWebSocket);
                object = Call.this.client.interceptors().get(this.index);
                object2 = object.intercept((Interceptor.Chain)object2);
                if (object2 != null) {
                    return object2;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("application interceptor ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(" returned null");
                throw new NullPointerException(((StringBuilder)object2).toString());
            }
            return Call.this.getResponse((Request)object, this.forWebSocket);
        }

        @Override
        public Request request() {
            return this.request;
        }
    }

    final class AsyncCall
    extends NamedRunnable {
        private final boolean forWebSocket;
        private final Callback responseCallback;

        private AsyncCall(Callback callback, boolean bl) {
            super("OkHttp %s", Call.this.originalRequest.urlString());
            this.responseCallback = callback;
            this.forWebSocket = bl;
        }

        void cancel() {
            Call.this.cancel();
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        protected void execute() {
            var2_2 = var1_1 = false;
            var3_3 = Call.access$100(Call.this, this.forWebSocket);
            var2_2 = var1_1;
            if (Call.this.canceled) {
                var2_2 = var1_1 = true;
                var4_7 = this.responseCallback;
                var2_2 = var1_1;
                var5_9 = Call.this.originalRequest;
                var2_2 = var1_1;
                var2_2 = var1_1;
                var3_3 = new IOException("Canceled");
                var2_2 = var1_1;
                var4_7.onFailure(var5_9, (IOException)var3_3);
            } else {
                var2_2 = true;
                this.responseCallback.onResponse((Response)var3_3);
            }
            Call.access$300(Call.this).getDispatcher().finished(this);
            return;
            {
                catch (IOException var5_10) {}
                if (!var2_2) ** GOTO lbl30
                {
                    var6_11 = Internal.logger;
                    var3_5 = Level.INFO;
                    var4_8 = new StringBuilder();
                    var4_8.append("Callback failure for ");
                    var4_8.append(Call.access$200(Call.this));
                    var6_11.log(var3_5, var4_8.toString(), var5_10);
lbl30: // 1 sources:
                    var3_6 = Call.this.engine == null ? Call.this.originalRequest : Call.this.engine.getRequest();
                    this.responseCallback.onFailure(var3_6, var5_10);
                }
            }
            ** finally { 
lbl33: // 1 sources:
            Call.access$300(Call.this).getDispatcher().finished(this);
            throw var3_4;
        }

        Call get() {
            return Call.this;
        }

        String host() {
            return Call.this.originalRequest.httpUrl().host();
        }

        Request request() {
            return Call.this.originalRequest;
        }

        Object tag() {
            return Call.this.originalRequest.tag();
        }
    }

}

