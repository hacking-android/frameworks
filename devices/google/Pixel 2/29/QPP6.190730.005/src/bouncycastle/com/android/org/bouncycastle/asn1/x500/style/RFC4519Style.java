/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x500.style;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.DERIA5String;
import com.android.org.bouncycastle.asn1.DERPrintableString;
import com.android.org.bouncycastle.asn1.x500.RDN;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x500.X500NameStyle;
import com.android.org.bouncycastle.asn1.x500.style.AbstractX500NameStyle;
import com.android.org.bouncycastle.asn1.x500.style.IETFUtils;
import java.util.Hashtable;

public class RFC4519Style
extends AbstractX500NameStyle {
    private static final Hashtable DefaultLookUp;
    private static final Hashtable DefaultSymbols;
    public static final X500NameStyle INSTANCE;
    public static final ASN1ObjectIdentifier businessCategory;
    public static final ASN1ObjectIdentifier c;
    public static final ASN1ObjectIdentifier cn;
    public static final ASN1ObjectIdentifier dc;
    public static final ASN1ObjectIdentifier description;
    public static final ASN1ObjectIdentifier destinationIndicator;
    public static final ASN1ObjectIdentifier distinguishedName;
    public static final ASN1ObjectIdentifier dnQualifier;
    public static final ASN1ObjectIdentifier enhancedSearchGuide;
    public static final ASN1ObjectIdentifier facsimileTelephoneNumber;
    public static final ASN1ObjectIdentifier generationQualifier;
    public static final ASN1ObjectIdentifier givenName;
    public static final ASN1ObjectIdentifier houseIdentifier;
    public static final ASN1ObjectIdentifier initials;
    public static final ASN1ObjectIdentifier internationalISDNNumber;
    public static final ASN1ObjectIdentifier l;
    public static final ASN1ObjectIdentifier member;
    public static final ASN1ObjectIdentifier name;
    public static final ASN1ObjectIdentifier o;
    public static final ASN1ObjectIdentifier ou;
    public static final ASN1ObjectIdentifier owner;
    public static final ASN1ObjectIdentifier physicalDeliveryOfficeName;
    public static final ASN1ObjectIdentifier postOfficeBox;
    public static final ASN1ObjectIdentifier postalAddress;
    public static final ASN1ObjectIdentifier postalCode;
    public static final ASN1ObjectIdentifier preferredDeliveryMethod;
    public static final ASN1ObjectIdentifier registeredAddress;
    public static final ASN1ObjectIdentifier roleOccupant;
    public static final ASN1ObjectIdentifier searchGuide;
    public static final ASN1ObjectIdentifier seeAlso;
    public static final ASN1ObjectIdentifier serialNumber;
    public static final ASN1ObjectIdentifier sn;
    public static final ASN1ObjectIdentifier st;
    public static final ASN1ObjectIdentifier street;
    public static final ASN1ObjectIdentifier telephoneNumber;
    public static final ASN1ObjectIdentifier teletexTerminalIdentifier;
    public static final ASN1ObjectIdentifier telexNumber;
    public static final ASN1ObjectIdentifier title;
    public static final ASN1ObjectIdentifier uid;
    public static final ASN1ObjectIdentifier uniqueMember;
    public static final ASN1ObjectIdentifier userPassword;
    public static final ASN1ObjectIdentifier x121Address;
    public static final ASN1ObjectIdentifier x500UniqueIdentifier;
    protected final Hashtable defaultLookUp = RFC4519Style.copyHashTable(DefaultLookUp);
    protected final Hashtable defaultSymbols = RFC4519Style.copyHashTable(DefaultSymbols);

    static {
        businessCategory = new ASN1ObjectIdentifier("2.5.4.15").intern();
        c = new ASN1ObjectIdentifier("2.5.4.6").intern();
        cn = new ASN1ObjectIdentifier("2.5.4.3").intern();
        dc = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25").intern();
        description = new ASN1ObjectIdentifier("2.5.4.13").intern();
        destinationIndicator = new ASN1ObjectIdentifier("2.5.4.27").intern();
        distinguishedName = new ASN1ObjectIdentifier("2.5.4.49").intern();
        dnQualifier = new ASN1ObjectIdentifier("2.5.4.46").intern();
        enhancedSearchGuide = new ASN1ObjectIdentifier("2.5.4.47").intern();
        facsimileTelephoneNumber = new ASN1ObjectIdentifier("2.5.4.23").intern();
        generationQualifier = new ASN1ObjectIdentifier("2.5.4.44").intern();
        givenName = new ASN1ObjectIdentifier("2.5.4.42").intern();
        houseIdentifier = new ASN1ObjectIdentifier("2.5.4.51").intern();
        initials = new ASN1ObjectIdentifier("2.5.4.43").intern();
        internationalISDNNumber = new ASN1ObjectIdentifier("2.5.4.25").intern();
        l = new ASN1ObjectIdentifier("2.5.4.7").intern();
        member = new ASN1ObjectIdentifier("2.5.4.31").intern();
        name = new ASN1ObjectIdentifier("2.5.4.41").intern();
        o = new ASN1ObjectIdentifier("2.5.4.10").intern();
        ou = new ASN1ObjectIdentifier("2.5.4.11").intern();
        owner = new ASN1ObjectIdentifier("2.5.4.32").intern();
        physicalDeliveryOfficeName = new ASN1ObjectIdentifier("2.5.4.19").intern();
        postalAddress = new ASN1ObjectIdentifier("2.5.4.16").intern();
        postalCode = new ASN1ObjectIdentifier("2.5.4.17").intern();
        postOfficeBox = new ASN1ObjectIdentifier("2.5.4.18").intern();
        preferredDeliveryMethod = new ASN1ObjectIdentifier("2.5.4.28").intern();
        registeredAddress = new ASN1ObjectIdentifier("2.5.4.26").intern();
        roleOccupant = new ASN1ObjectIdentifier("2.5.4.33").intern();
        searchGuide = new ASN1ObjectIdentifier("2.5.4.14").intern();
        seeAlso = new ASN1ObjectIdentifier("2.5.4.34").intern();
        serialNumber = new ASN1ObjectIdentifier("2.5.4.5").intern();
        sn = new ASN1ObjectIdentifier("2.5.4.4").intern();
        st = new ASN1ObjectIdentifier("2.5.4.8").intern();
        street = new ASN1ObjectIdentifier("2.5.4.9").intern();
        telephoneNumber = new ASN1ObjectIdentifier("2.5.4.20").intern();
        teletexTerminalIdentifier = new ASN1ObjectIdentifier("2.5.4.22").intern();
        telexNumber = new ASN1ObjectIdentifier("2.5.4.21").intern();
        title = new ASN1ObjectIdentifier("2.5.4.12").intern();
        uid = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1").intern();
        uniqueMember = new ASN1ObjectIdentifier("2.5.4.50").intern();
        userPassword = new ASN1ObjectIdentifier("2.5.4.35").intern();
        x121Address = new ASN1ObjectIdentifier("2.5.4.24").intern();
        x500UniqueIdentifier = new ASN1ObjectIdentifier("2.5.4.45").intern();
        DefaultSymbols = new Hashtable();
        DefaultLookUp = new Hashtable();
        DefaultSymbols.put(businessCategory, "businessCategory");
        DefaultSymbols.put(c, "c");
        DefaultSymbols.put(cn, "cn");
        DefaultSymbols.put(dc, "dc");
        DefaultSymbols.put(description, "description");
        DefaultSymbols.put(destinationIndicator, "destinationIndicator");
        DefaultSymbols.put(distinguishedName, "distinguishedName");
        DefaultSymbols.put(dnQualifier, "dnQualifier");
        DefaultSymbols.put(enhancedSearchGuide, "enhancedSearchGuide");
        DefaultSymbols.put(facsimileTelephoneNumber, "facsimileTelephoneNumber");
        DefaultSymbols.put(generationQualifier, "generationQualifier");
        DefaultSymbols.put(givenName, "givenName");
        DefaultSymbols.put(houseIdentifier, "houseIdentifier");
        DefaultSymbols.put(initials, "initials");
        DefaultSymbols.put(internationalISDNNumber, "internationalISDNNumber");
        DefaultSymbols.put(l, "l");
        DefaultSymbols.put(member, "member");
        DefaultSymbols.put(name, "name");
        DefaultSymbols.put(o, "o");
        DefaultSymbols.put(ou, "ou");
        DefaultSymbols.put(owner, "owner");
        DefaultSymbols.put(physicalDeliveryOfficeName, "physicalDeliveryOfficeName");
        DefaultSymbols.put(postalAddress, "postalAddress");
        DefaultSymbols.put(postalCode, "postalCode");
        DefaultSymbols.put(postOfficeBox, "postOfficeBox");
        DefaultSymbols.put(preferredDeliveryMethod, "preferredDeliveryMethod");
        DefaultSymbols.put(registeredAddress, "registeredAddress");
        DefaultSymbols.put(roleOccupant, "roleOccupant");
        DefaultSymbols.put(searchGuide, "searchGuide");
        DefaultSymbols.put(seeAlso, "seeAlso");
        DefaultSymbols.put(serialNumber, "serialNumber");
        DefaultSymbols.put(sn, "sn");
        DefaultSymbols.put(st, "st");
        DefaultSymbols.put(street, "street");
        DefaultSymbols.put(telephoneNumber, "telephoneNumber");
        DefaultSymbols.put(teletexTerminalIdentifier, "teletexTerminalIdentifier");
        DefaultSymbols.put(telexNumber, "telexNumber");
        DefaultSymbols.put(title, "title");
        DefaultSymbols.put(uid, "uid");
        DefaultSymbols.put(uniqueMember, "uniqueMember");
        DefaultSymbols.put(userPassword, "userPassword");
        DefaultSymbols.put(x121Address, "x121Address");
        DefaultSymbols.put(x500UniqueIdentifier, "x500UniqueIdentifier");
        DefaultLookUp.put("businesscategory", businessCategory);
        DefaultLookUp.put("c", c);
        DefaultLookUp.put("cn", cn);
        DefaultLookUp.put("dc", dc);
        DefaultLookUp.put("description", description);
        DefaultLookUp.put("destinationindicator", destinationIndicator);
        DefaultLookUp.put("distinguishedname", distinguishedName);
        DefaultLookUp.put("dnqualifier", dnQualifier);
        DefaultLookUp.put("enhancedsearchguide", enhancedSearchGuide);
        DefaultLookUp.put("facsimiletelephonenumber", facsimileTelephoneNumber);
        DefaultLookUp.put("generationqualifier", generationQualifier);
        DefaultLookUp.put("givenname", givenName);
        DefaultLookUp.put("houseidentifier", houseIdentifier);
        DefaultLookUp.put("initials", initials);
        DefaultLookUp.put("internationalisdnnumber", internationalISDNNumber);
        DefaultLookUp.put("l", l);
        DefaultLookUp.put("member", member);
        DefaultLookUp.put("name", name);
        DefaultLookUp.put("o", o);
        DefaultLookUp.put("ou", ou);
        DefaultLookUp.put("owner", owner);
        DefaultLookUp.put("physicaldeliveryofficename", physicalDeliveryOfficeName);
        DefaultLookUp.put("postaladdress", postalAddress);
        DefaultLookUp.put("postalcode", postalCode);
        DefaultLookUp.put("postofficebox", postOfficeBox);
        DefaultLookUp.put("preferreddeliverymethod", preferredDeliveryMethod);
        DefaultLookUp.put("registeredaddress", registeredAddress);
        DefaultLookUp.put("roleoccupant", roleOccupant);
        DefaultLookUp.put("searchguide", searchGuide);
        DefaultLookUp.put("seealso", seeAlso);
        DefaultLookUp.put("serialnumber", serialNumber);
        DefaultLookUp.put("sn", sn);
        DefaultLookUp.put("st", st);
        DefaultLookUp.put("street", street);
        DefaultLookUp.put("telephonenumber", telephoneNumber);
        DefaultLookUp.put("teletexterminalidentifier", teletexTerminalIdentifier);
        DefaultLookUp.put("telexnumber", telexNumber);
        DefaultLookUp.put("title", title);
        DefaultLookUp.put("uid", uid);
        DefaultLookUp.put("uniquemember", uniqueMember);
        DefaultLookUp.put("userpassword", userPassword);
        DefaultLookUp.put("x121address", x121Address);
        DefaultLookUp.put("x500uniqueidentifier", x500UniqueIdentifier);
        INSTANCE = new RFC4519Style();
    }

    protected RFC4519Style() {
    }

    @Override
    public ASN1ObjectIdentifier attrNameToOID(String string) {
        return IETFUtils.decodeAttrName(string, this.defaultLookUp);
    }

    @Override
    protected ASN1Encodable encodeStringValue(ASN1ObjectIdentifier aSN1ObjectIdentifier, String string) {
        if (aSN1ObjectIdentifier.equals(dc)) {
            return new DERIA5String(string);
        }
        if (!(aSN1ObjectIdentifier.equals(c) || aSN1ObjectIdentifier.equals(serialNumber) || aSN1ObjectIdentifier.equals(dnQualifier) || aSN1ObjectIdentifier.equals(telephoneNumber))) {
            return super.encodeStringValue(aSN1ObjectIdentifier, string);
        }
        return new DERPrintableString(string);
    }

    @Override
    public RDN[] fromString(String arrrDN) {
        RDN[] arrrDN2 = IETFUtils.rDNsFromString((String)arrrDN, this);
        arrrDN = new RDN[arrrDN2.length];
        for (int i = 0; i != arrrDN2.length; ++i) {
            arrrDN[arrrDN.length - i - 1] = arrrDN2[i];
        }
        return arrrDN;
    }

    @Override
    public String[] oidToAttrNames(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return IETFUtils.findAttrNamesForOID(aSN1ObjectIdentifier, this.defaultLookUp);
    }

    @Override
    public String oidToDisplayName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (String)DefaultSymbols.get(aSN1ObjectIdentifier);
    }

    @Override
    public String toString(X500Name arrrDN) {
        StringBuffer stringBuffer = new StringBuffer();
        boolean bl = true;
        arrrDN = arrrDN.getRDNs();
        for (int i = arrrDN.length - 1; i >= 0; --i) {
            if (bl) {
                bl = false;
            } else {
                stringBuffer.append(',');
            }
            IETFUtils.appendRDN(stringBuffer, arrrDN[i], this.defaultSymbols);
        }
        return stringBuffer.toString();
    }
}

