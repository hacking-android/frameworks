/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.clientauthutils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import javax.sip.header.AuthorizationHeader;

class CredentialsCache {
    private ConcurrentHashMap<String, List<AuthorizationHeader>> authorizationHeaders = new ConcurrentHashMap();
    private Timer timer;

    CredentialsCache(Timer timer) {
        this.timer = timer;
    }

    void cacheAuthorizationHeader(String object, AuthorizationHeader authorizationHeader, int n) {
        String string = authorizationHeader.getUsername();
        if (object != null) {
            List<Object> list;
            List<AuthorizationHeader> list2 = this.authorizationHeaders.get(object);
            if (list2 == null) {
                list = new LinkedList();
                this.authorizationHeaders.put((String)object, list);
            } else {
                String string2 = authorizationHeader.getRealm();
                ListIterator<AuthorizationHeader> listIterator = list2.listIterator();
                do {
                    list = list2;
                    if (!listIterator.hasNext()) break;
                    if (!string2.equals(listIterator.next().getRealm())) continue;
                    listIterator.remove();
                } while (true);
            }
            list.add(authorizationHeader);
            object = new TimeoutTask((String)object, string);
            if (n != -1) {
                this.timer.schedule((TimerTask)object, n * 1000);
            }
            return;
        }
        throw new NullPointerException("Call ID is null!");
    }

    Collection<AuthorizationHeader> getCachedAuthorizationHeaders(String string) {
        if (string != null) {
            return this.authorizationHeaders.get(string);
        }
        throw new NullPointerException("Null arg!");
    }

    public void removeAuthenticationHeader(String string) {
        this.authorizationHeaders.remove(string);
    }

    class TimeoutTask
    extends TimerTask {
        String callId;
        String userName;

        public TimeoutTask(String string, String string2) {
            this.callId = string2;
            this.userName = string;
        }

        @Override
        public void run() {
            CredentialsCache.this.authorizationHeaders.remove(this.callId);
        }
    }

}

