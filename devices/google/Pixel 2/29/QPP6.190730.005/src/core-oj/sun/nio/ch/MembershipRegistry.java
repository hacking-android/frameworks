/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.channels.MembershipKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import sun.nio.ch.MembershipKeyImpl;

class MembershipRegistry {
    private Map<InetAddress, List<MembershipKeyImpl>> groups = null;

    MembershipRegistry() {
    }

    void add(MembershipKeyImpl membershipKeyImpl) {
        InetAddress inetAddress = membershipKeyImpl.group();
        Object object = this.groups;
        if (object == null) {
            this.groups = new HashMap<InetAddress, List<MembershipKeyImpl>>();
            object = null;
        } else {
            object = object.get(inetAddress);
        }
        LinkedList<MembershipKeyImpl> linkedList = object;
        if (object == null) {
            linkedList = new LinkedList<MembershipKeyImpl>();
            this.groups.put(inetAddress, linkedList);
        }
        linkedList.add(membershipKeyImpl);
    }

    MembershipKey checkMembership(InetAddress iterator, NetworkInterface networkInterface, InetAddress inetAddress) {
        Object object = this.groups;
        if (object != null && (iterator = object.get(iterator)) != null) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                object = (MembershipKeyImpl)iterator.next();
                if (!((MembershipKeyImpl)object).networkInterface().equals(networkInterface)) continue;
                if (inetAddress == null) {
                    if (((MembershipKeyImpl)object).sourceAddress() == null) {
                        return object;
                    }
                    throw new IllegalStateException("Already a member to receive all packets");
                }
                if (((MembershipKeyImpl)object).sourceAddress() != null) {
                    if (!inetAddress.equals(((MembershipKeyImpl)object).sourceAddress())) continue;
                    return object;
                }
                throw new IllegalStateException("Already have source-specific membership");
            }
        }
        return null;
    }

    void invalidateAll() {
        Map<InetAddress, List<MembershipKeyImpl>> map = this.groups;
        if (map != null) {
            for (InetAddress inetAddress : map.keySet()) {
                Iterator<MembershipKeyImpl> object = this.groups.get(inetAddress).iterator();
                while (object.hasNext()) {
                    object.next().invalidate();
                }
            }
        }
    }

    void remove(MembershipKeyImpl membershipKeyImpl) {
        InetAddress inetAddress = membershipKeyImpl.group();
        List<MembershipKeyImpl> list = this.groups.get(inetAddress);
        if (list != null) {
            Iterator<MembershipKeyImpl> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (iterator.next() != membershipKeyImpl) continue;
                iterator.remove();
                break;
            }
            if (list.isEmpty()) {
                this.groups.remove(inetAddress);
            }
        }
    }
}

