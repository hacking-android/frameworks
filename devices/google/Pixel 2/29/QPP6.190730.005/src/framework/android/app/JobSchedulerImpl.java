/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.job.IJobScheduler;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobSnapshot;
import android.app.job.JobWorkItem;
import android.content.pm.ParceledListSlice;
import android.os.RemoteException;
import java.util.List;

public class JobSchedulerImpl
extends JobScheduler {
    IJobScheduler mBinder;

    JobSchedulerImpl(IJobScheduler iJobScheduler) {
        this.mBinder = iJobScheduler;
    }

    @Override
    public void cancel(int n) {
        try {
            this.mBinder.cancel(n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void cancelAll() {
        try {
            this.mBinder.cancelAll();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public int enqueue(JobInfo jobInfo, JobWorkItem jobWorkItem) {
        try {
            int n = this.mBinder.enqueue(jobInfo, jobWorkItem);
            return n;
        }
        catch (RemoteException remoteException) {
            return 0;
        }
    }

    @Override
    public List<JobSnapshot> getAllJobSnapshots() {
        try {
            List list = this.mBinder.getAllJobSnapshots().getList();
            return list;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @Override
    public List<JobInfo> getAllPendingJobs() {
        try {
            List list = this.mBinder.getAllPendingJobs().getList();
            return list;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @Override
    public JobInfo getPendingJob(int n) {
        try {
            JobInfo jobInfo = this.mBinder.getPendingJob(n);
            return jobInfo;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @Override
    public List<JobInfo> getStartedJobs() {
        try {
            List<JobInfo> list = this.mBinder.getStartedJobs();
            return list;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    @Override
    public int schedule(JobInfo jobInfo) {
        try {
            int n = this.mBinder.schedule(jobInfo);
            return n;
        }
        catch (RemoteException remoteException) {
            return 0;
        }
    }

    @Override
    public int scheduleAsPackage(JobInfo jobInfo, String string2, int n, String string3) {
        try {
            n = this.mBinder.scheduleAsPackage(jobInfo, string2, n, string3);
            return n;
        }
        catch (RemoteException remoteException) {
            return 0;
        }
    }
}

