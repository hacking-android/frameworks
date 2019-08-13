/*
 * Decompiled with CFR 0.145.
 */
package android.app.job;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobServiceEngine;
import android.content.Intent;
import android.os.IBinder;

public abstract class JobService
extends Service {
    public static final String PERMISSION_BIND = "android.permission.BIND_JOB_SERVICE";
    private static final String TAG = "JobService";
    private JobServiceEngine mEngine;

    public final void jobFinished(JobParameters jobParameters, boolean bl) {
        this.mEngine.jobFinished(jobParameters, bl);
    }

    @Override
    public final IBinder onBind(Intent intent) {
        if (this.mEngine == null) {
            this.mEngine = new JobServiceEngine(this){

                @Override
                public boolean onStartJob(JobParameters jobParameters) {
                    return JobService.this.onStartJob(jobParameters);
                }

                @Override
                public boolean onStopJob(JobParameters jobParameters) {
                    return JobService.this.onStopJob(jobParameters);
                }
            };
        }
        return this.mEngine.getBinder();
    }

    public abstract boolean onStartJob(JobParameters var1);

    public abstract boolean onStopJob(JobParameters var1);

}

