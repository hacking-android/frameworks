/*
 * Decompiled with CFR 0.145.
 */
package android.app.job;

import android.annotation.SystemApi;
import android.app.job.JobInfo;
import android.app.job.JobSnapshot;
import android.app.job.JobWorkItem;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public abstract class JobScheduler {
    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;

    public abstract void cancel(int var1);

    public abstract void cancelAll();

    public abstract int enqueue(JobInfo var1, JobWorkItem var2);

    public abstract List<JobSnapshot> getAllJobSnapshots();

    public abstract List<JobInfo> getAllPendingJobs();

    public abstract JobInfo getPendingJob(int var1);

    public abstract List<JobInfo> getStartedJobs();

    public abstract int schedule(JobInfo var1);

    @SystemApi
    public abstract int scheduleAsPackage(JobInfo var1, String var2, int var3, String var4);

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Result {
    }

}

