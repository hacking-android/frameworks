/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.util.ListResourceBundle;

public class Resources
extends ListResourceBundle {
    private static final Object[][] contents;

    static {
        Object[] arrobject = new Object[]{"invalid.null.input.s.", "invalid null input(s)"};
        Object[] arrobject2 = new Object[]{"actions.can.only.be.read.", "actions can only be 'read'"};
        Object[] arrobject3 = new Object[]{"permission.name.name.syntax.invalid.", "permission name [{0}] syntax invalid: "};
        Object[] arrobject4 = new Object[]{"Credential.Class.not.followed.by.a.Principal.Class.and.Name", "Credential Class not followed by a Principal Class and Name"};
        Object[] arrobject5 = new Object[]{"Principal.Class.not.followed.by.a.Principal.Name", "Principal Class not followed by a Principal Name"};
        Object[] arrobject6 = new Object[]{"Principal.Name.missing.end.quote", "Principal Name missing end quote"};
        Object[] arrobject7 = new Object[]{"CredOwner.Principal.Class.class.Principal.Name.name", "CredOwner:\n\tPrincipal Class = {0}\n\tPrincipal Name = {1}"};
        Object[] arrobject8 = new Object[]{"provided.null.name", "provided null name"};
        Object[] arrobject9 = new Object[]{"provided.null.OID.map", "provided null OID map"};
        Object[] arrobject10 = new Object[]{"NEWLINE", "\n"};
        Object[] arrobject11 = new Object[]{"invalid.null.action.provided", "invalid null action provided"};
        Object[] arrobject12 = new Object[]{"invalid.null.Class.provided", "invalid null Class provided"};
        Object[] arrobject13 = new Object[]{"Subject.", "Subject:\n"};
        Object[] arrobject14 = new Object[]{".Public.Credential.", "\tPublic Credential: "};
        Object[] arrobject15 = new Object[]{".Private.Credential.", "\tPrivate Credential: "};
        Object[] arrobject16 = new Object[]{".Private.Credential.inaccessible.", "\tPrivate Credential inaccessible\n"};
        Object[] arrobject17 = new Object[]{"Subject.is.read.only", "Subject is read-only"};
        Object[] arrobject18 = new Object[]{"Invalid.null.input.name", "Invalid null input: name"};
        Object[] arrobject19 = new Object[]{"invalid.null.Subject.provided", "invalid null Subject provided"};
        Object[] arrobject20 = new Object[]{"null.subject.logout.called.before.login", "null subject - logout called before login"};
        Object[] arrobject21 = new Object[]{"unable.to.instantiate.LoginModule.module.because.it.does.not.provide.a.no.argument.constructor", "unable to instantiate LoginModule, {0}, because it does not provide a no-argument constructor"};
        Object[] arrobject22 = new Object[]{"unable.to.instantiate.LoginModule", "unable to instantiate LoginModule"};
        Object[] arrobject23 = new Object[]{"unable.to.access.LoginModule.", "unable to access LoginModule: "};
        Object[] arrobject24 = new Object[]{"java.security.policy.error.adding.Permission.perm.message", "java.security.policy: error adding Permission, {0}:\n\t{1}"};
        Object[] arrobject25 = new Object[]{"java.security.policy.error.adding.Entry.message", "java.security.policy: error adding Entry:\n\t{0}"};
        Object[] arrobject26 = new Object[]{"unable.to.perform.substitution.on.alias.suffix", "unable to perform substitution on alias, {0}"};
        Object[] arrobject27 = new Object[]{"substitution.value.prefix.unsupported", "substitution value, {0}, unsupported"};
        Object[] arrobject28 = new Object[]{"LPARAM", "("};
        Object[] arrobject29 = new Object[]{"type.can.t.be.null", "type can't be null"};
        Object[] arrobject30 = new Object[]{"keystorePasswordURL.can.not.be.specified.without.also.specifying.keystore", "keystorePasswordURL can not be specified without also specifying keystore"};
        Object[] arrobject31 = new Object[]{"expected.keystore.type", "expected keystore type"};
        Object[] arrobject32 = new Object[]{"expected.keystore.provider", "expected keystore provider"};
        Object[] arrobject33 = new Object[]{"multiple.Codebase.expressions", "multiple Codebase expressions"};
        Object[] arrobject34 = new Object[]{"multiple.SignedBy.expressions", "multiple SignedBy expressions"};
        Object[] arrobject35 = new Object[]{"duplicate.keystore.name", "duplicate keystore name: {0}"};
        Object[] arrobject36 = new Object[]{"SignedBy.has.empty.alias", "SignedBy has empty alias"};
        Object[] arrobject37 = new Object[]{"can.not.specify.Principal.with.a.wildcard.class.without.a.wildcard.name", "can not specify Principal with a wildcard class without a wildcard name"};
        Object[] arrobject38 = new Object[]{"expected.permission.entry", "expected permission entry"};
        Object[] arrobject39 = new Object[]{"number.", "number "};
        Object[] arrobject40 = new Object[]{"expected.expect.read.end.of.file.", "expected [{0}], read [end of file]"};
        Object[] arrobject41 = new Object[]{"expected.read.end.of.file.", "expected [;], read [end of file]"};
        Object[] arrobject42 = new Object[]{"line.number.msg", "line {0}: {1}"};
        Object[] arrobject43 = new Object[]{"null.principalClass.or.principalName", "null principalClass or principalName"};
        Object[] arrobject44 = new Object[]{"PKCS11.Token.providerName.Password.", "PKCS11 Token [{0}] Password: "};
        Object[] arrobject45 = new Object[]{"unable.to.instantiate.Subject.based.policy", "unable to instantiate Subject-based policy"};
        contents = new Object[][]{arrobject, arrobject2, arrobject3, arrobject4, arrobject5, {"Principal.Name.must.be.surrounded.by.quotes", "Principal Name must be surrounded by quotes"}, arrobject6, {"PrivateCredentialPermission.Principal.Class.can.not.be.a.wildcard.value.if.Principal.Name.is.not.a.wildcard.value", "PrivateCredentialPermission Principal Class can not be a wildcard (*) value if Principal Name is not a wildcard (*) value"}, arrobject7, arrobject8, {"provided.null.keyword.map", "provided null keyword map"}, arrobject9, arrobject10, {"invalid.null.AccessControlContext.provided", "invalid null AccessControlContext provided"}, arrobject11, arrobject12, arrobject13, {".Principal.", "\tPrincipal: "}, arrobject14, {".Private.Credentials.inaccessible.", "\tPrivate Credentials inaccessible\n"}, arrobject15, arrobject16, arrobject17, {"attempting.to.add.an.object.which.is.not.an.instance.of.java.security.Principal.to.a.Subject.s.Principal.Set", "attempting to add an object which is not an instance of java.security.Principal to a Subject's Principal Set"}, {"attempting.to.add.an.object.which.is.not.an.instance.of.class", "attempting to add an object which is not an instance of {0}"}, {"LoginModuleControlFlag.", "LoginModuleControlFlag: "}, arrobject18, {"No.LoginModules.configured.for.name", "No LoginModules configured for {0}"}, arrobject19, {"invalid.null.CallbackHandler.provided", "invalid null CallbackHandler provided"}, arrobject20, arrobject21, arrobject22, {"unable.to.instantiate.LoginModule.", "unable to instantiate LoginModule: "}, {"unable.to.find.LoginModule.class.", "unable to find LoginModule class: "}, arrobject23, {"Login.Failure.all.modules.ignored", "Login Failure: all modules ignored"}, {"java.security.policy.error.parsing.policy.message", "java.security.policy: error parsing {0}:\n\t{1}"}, arrobject24, arrobject25, {"alias.name.not.provided.pe.name.", "alias name not provided ({0})"}, arrobject26, arrobject27, arrobject28, {"RPARAM", ")"}, arrobject29, arrobject30, arrobject31, arrobject32, arrobject33, arrobject34, {"duplicate.keystore.domain.name", "duplicate keystore domain name: {0}"}, arrobject35, arrobject36, arrobject37, {"expected.codeBase.or.SignedBy.or.Principal", "expected codeBase or SignedBy or Principal"}, arrobject38, arrobject39, arrobject40, arrobject41, arrobject42, {"line.number.expected.expect.found.actual.", "line {0}: expected [{1}], found [{2}]"}, arrobject43, arrobject44, arrobject45};
    }

    @Override
    public Object[][] getContents() {
        return contents;
    }
}

