/*
 * Decompiled with CFR 0.145.
 */
package android.app.job;

import android.app.Service;
import android.app.job.IJobCallback;
import android.app.job.IJobService;
import android.app.job.JobParameters;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.lang.ref.WeakReference;

public abstract class JobServiceEngine {
    private static final int MSG_EXECUTE_JOB = 0;
    private static final int MSG_JOB_FINISHED = 2;
    private static final int MSG_STOP_JOB = 1;
    private static final String TAG = "JobServiceEngine";
    private final IJobService mBinder = new JobInterface(this);
    JobHandler mHandler;

    public JobServiceEngine(Service service) {
        this.mHandler = new JobHandler(service.getMainLooper());
    }

    public final IBinder getBinder() {
        return this.mBinder.asBinder();
    }

    public void jobFinished(JobParameters parcelable, boolean bl) {
        if (parcelable != null) {
            parcelable = Message.obtain(this.mHandler, 2, parcelable);
            ((Message)parcelable).arg2 = bl ? 1 : 0;
            ((Message)parcelable).sendToTarget();
            return;
        }
        throw new NullPointerException("params");
    }

    public abstract boolean onStartJob(JobParameters var1);

    public abstract boolean onStopJob(JobParameters var1);

    class JobHandler
    extends Handler {
        JobHandler(Looper looper) {
            super(looper);
        }

        private void ackStartMessage(JobParameters jobParameters, boolean bl) {
            IJobCallback iJobCallback = jobParameters.getCallback();
            int n = jobParameters.getJobId();
            if (iJobCallback != null) {
                try {
                    iJobCallback.acknowledgeStartMessage(n, bl);
                }
                catch (RemoteException remoteException) {
                    Log.e(JobServiceEngine.TAG, "System unreachable for starting job.");
                }
            } else if (Log.isLoggable(JobServiceEngine.TAG, 3)) {
                Log.d(JobServiceEngine.TAG, "Attempting to ack a job that has already been processed.");
            }
        }

        private void ackStopMessage(JobParameters jobParameters, boolean bl) {
            IJobCallback iJobCallback = jobParameters.getCallback();
            int n = jobParameters.getJobId();
            if (iJobCallback != null) {
                try {
                    iJobCallback.acknowledgeStopMessage(n, bl);
                }
                catch (RemoteException remoteException) {
                    Log.e(JobServiceEngine.TAG, "System unreachable for stopping job.");
                }
            } else if (Log.isLoggable(JobServiceEngine.TAG, 3)) {
                Log.d(JobServiceEngine.TAG, "Attempting to ack a job that has already been processed.");
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void handleMessage(Message object) {
            JobParameters jobParameters = (JobParameters)((Message)object).obj;
            int n = ((Message)object).what;
            if (n != 0) {
                boolean bl = true;
                if (n != 1) {
                    if (n != 2) {
                        Log.e(JobServiceEngine.TAG, "Unrecognised message received.");
                        return;
                    } else {
                        if (((Message)object).arg2 != 1) {
                            bl = false;
                        }
                        object = jobParameters.getCallback();
                        if (object != null) {
                            try {
                                object.jobFinished(jobParameters.getJobId(), bl);
                                return;
                            }
                            catch (RemoteException remoteException) {
                                Log.e(JobServiceEngine.TAG, "Error reporting job finish to system: binder has goneaway.");
                            }
                            return;
                        } else {
                            Log.e(JobServiceEngine.TAG, "finishJob() called for a nonexistent job id.");
                        }
                    }
                    return;
                }
                try {
                    this.ackStopMessage(jobParameters, JobServiceEngine.this.onStopJob(jobParameters));
                    return;
                }
                catch (Exception exception) {
                    Log.e(JobServiceEngine.TAG, "Application unable to handle onStopJob.", exception);
                    throw new RuntimeException(exception);
                }
            }
            try {
                this.ackStartMessage(jobParameters, JobServiceEngine.this.onStartJob(jobParameters));
                return;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error while executing job: ");
                stringBuilder.append(jobParameters.getJobId());
                Log.e(JobServiceEngine.TAG, stringBuilder.toString());
                throw new RuntimeException(exception);
            }
        }
    }

    static final class JobInterface
    extends IJobService.Stub {
        final WeakReference<JobServiceEngine> mService;

        JobInterface(JobServiceEngine jobServiceEngine) {
            this.mService = new WeakReference<JobServiceEngine>(jobServiceEngine);
        }

        @Override
        public void startJob(JobParameters jobParameters) throws RemoteException {
            JobServiceEngine jobServiceEngine = (JobServiceEngine)this.mService.get();
            if (jobServiceEngine != null) {
                Message.obtain(jobServiceEngine.mHandler, 0, jobParameters).sendToTarget();
            }
        }

        @Override
        public void stopJob(JobParameters jobParameters) throws RemoteException {
            JobServiceEngine jobServiceEngine = (JobServiceEngine)this.mService.get();
            if (jobServiceEngine != null) {
                Message.obtain(jobServiceEngine.mHandler, 1, jobParameters).sendToTarget();
            }
        }
    }

}

