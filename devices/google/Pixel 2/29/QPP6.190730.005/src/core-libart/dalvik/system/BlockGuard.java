/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.Objects;

public final class BlockGuard {
    @UnsupportedAppUsage
    public static final Policy LAX_POLICY = new Policy(){

        @Override
        public int getPolicyMask() {
            return 0;
        }

        @Override
        public void onExplicitGc() {
        }

        @Override
        public void onNetwork() {
        }

        @Override
        public void onReadFromDisk() {
        }

        @Override
        public void onUnbufferedIO() {
        }

        @Override
        public void onWriteToDisk() {
        }

        public String toString() {
            return "LAX_POLICY";
        }
    };
    public static final VmPolicy LAX_VM_POLICY = new VmPolicy(){

        @Override
        public void onPathAccess(String string) {
        }

        public String toString() {
            return "LAX_VM_POLICY";
        }
    };
    @UnsupportedAppUsage
    private static ThreadLocal<Policy> threadPolicy = new ThreadLocal<Policy>(){

        @Override
        protected Policy initialValue() {
            return LAX_POLICY;
        }
    };
    private static volatile VmPolicy vmPolicy = LAX_VM_POLICY;

    private BlockGuard() {
    }

    @UnsupportedAppUsage
    public static Policy getThreadPolicy() {
        return threadPolicy.get();
    }

    public static VmPolicy getVmPolicy() {
        return vmPolicy;
    }

    @UnsupportedAppUsage
    public static void setThreadPolicy(Policy policy) {
        threadPolicy.set(Objects.requireNonNull(policy));
    }

    public static void setVmPolicy(VmPolicy vmPolicy) {
        BlockGuard.vmPolicy = Objects.requireNonNull(vmPolicy);
    }

    @Deprecated
    public static class BlockGuardPolicyException
    extends RuntimeException {
        @UnsupportedAppUsage
        private final String mMessage;
        @UnsupportedAppUsage
        private final int mPolicyState;
        @UnsupportedAppUsage
        private final int mPolicyViolated;

        public BlockGuardPolicyException(int n, int n2) {
            this(n, n2, null);
        }

        @UnsupportedAppUsage
        public BlockGuardPolicyException(int n, int n2, String string) {
            this.mPolicyState = n;
            this.mPolicyViolated = n2;
            this.mMessage = string;
            this.fillInStackTrace();
        }

        @Override
        public String getMessage() {
            CharSequence charSequence;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("policy=");
            stringBuilder.append(this.mPolicyState);
            stringBuilder.append(" violation=");
            stringBuilder.append(this.mPolicyViolated);
            if (this.mMessage == null) {
                charSequence = "";
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" msg=");
                ((StringBuilder)charSequence).append(this.mMessage);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            stringBuilder.append((String)charSequence);
            return stringBuilder.toString();
        }

        public int getPolicy() {
            return this.mPolicyState;
        }

        public int getPolicyViolation() {
            return this.mPolicyViolated;
        }
    }

    public static interface Policy {
        public int getPolicyMask();

        public void onExplicitGc();

        @UnsupportedAppUsage
        public void onNetwork();

        @UnsupportedAppUsage
        public void onReadFromDisk();

        public void onUnbufferedIO();

        public void onWriteToDisk();
    }

    public static interface VmPolicy {
        public void onPathAccess(String var1);
    }

}

