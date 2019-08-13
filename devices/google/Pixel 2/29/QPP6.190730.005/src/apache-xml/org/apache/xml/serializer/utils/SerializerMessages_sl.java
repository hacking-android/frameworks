/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.utils;

import java.util.ListResourceBundle;

public class SerializerMessages_sl
extends ListResourceBundle {
    @Override
    public Object[][] getContents() {
        Object[] arrobject = new Object[]{"BAD_MSGFORMAT", "Format sporo\u010dila ''{0}'' v razredu sporo\u010dila ''{1}'' je spodletel."};
        Object[] arrobject2 = new Object[]{"ER_SERIALIZER_NOT_CONTENTHANDLER", "Razred serializerja ''{0}'' ne izvede org.xml.sax.ContentHandler."};
        Object[] arrobject3 = new Object[]{"ER_RESOURCE_COULD_NOT_FIND", "Vira [ {0} ] ni mogo\u010de najti.\n {1}"};
        Object[] arrobject4 = new Object[]{"ER_RESOURCE_COULD_NOT_LOAD", "Sredstva [ {0} ] ni bilo mogo\u010de nalo\u017eiti: {1} \n {2} \t {3}"};
        Object[] arrobject5 = new Object[]{"ER_BUFFER_SIZE_LESSTHAN_ZERO", "Velikost medpomnilnika <=0"};
        Object[] arrobject6 = new Object[]{"ER_INVALID_UTF16_SURROGATE", "Zaznan neveljaven nadomestek UTF-16: {0} ?"};
        Object[] arrobject7 = new Object[]{"ER_STRAY_ATTRIBUTE", "Atribut ''{0}'' je zunaj elementa."};
        Object[] arrobject8 = new Object[]{"ER_COULD_NOT_LOAD_RESOURCE", "Ni bilo mogo\u010de nalo\u017eiti ''{0}'' (preverite CLASSPATH), trenutno se uporabljajo samo privzete vrednosti"};
        Object[] arrobject9 = new Object[]{"ER_ILLEGAL_CHARACTER", "Poskus izpisa znaka integralne vrednosti {0}, ki v navedenem izhodnem kodiranju {1} ni zastopan."};
        Object[] arrobject10 = new Object[]{"ER_PORT_WHEN_HOST_NULL", "Ko je gostitelj NULL, nastavitev vrat ni mogo\u010da"};
        Object[] arrobject11 = new Object[]{"ER_HOST_ADDRESS_NOT_WELLFORMED", "Naslov gostitelja ni pravilno oblikovan"};
        Object[] arrobject12 = new Object[]{"ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Pot vsebuje neveljavno zaporedje za izhod"};
        Object[] arrobject13 = new Object[]{"ER_PATH_INVALID_CHAR", "Pot vsebuje neveljaven znak: {0}"};
        Object[] arrobject14 = new Object[]{"ER_FRAG_INVALID_CHAR", "Fragment vsebuje neveljaven znak"};
        Object[] arrobject15 = new Object[]{"ER_FRAG_WHEN_PATH_NULL", "Ko je pot NULL, nastavitev fragmenta ni mogo\u010da"};
        Object[] arrobject16 = new Object[]{"ER_FRAG_FOR_GENERIC_URI", "Fragment je lahko nastavljen samo za splo\u0161ni URI"};
        Object[] arrobject17 = new Object[]{"ER_NO_SCHEME_IN_URI", "Ne najdem sheme v URI"};
        Object[] arrobject18 = new Object[]{"ER_CANNOT_INIT_URI_EMPTY_PARMS", "Ni mogo\u010de inicializirati URI-ja s praznimi parametri"};
        Object[] arrobject19 = new Object[]{"ER_NO_QUERY_STRING_IN_PATH", "Poizvedbeni niz ne more biti naveden v nizu poti in poizvedbenem nizu"};
        Object[] arrobject20 = new Object[]{"ER_NO_USERINFO_IF_NO_HOST", "Informacije o uporabniku ne morejo biti navedene, \u010de ni naveden gostitelj"};
        Object[] arrobject21 = new Object[]{"ER_XML_VERSION_NOT_SUPPORTED", "Opozorilo: Zahtevana razli\u010dica izhodnega dokumenta je ''{0}''.  Ta razli\u010dica XML ni podprta.  Razli\u010dica izhodnega dokumenta bo ''1.0''."};
        Object[] arrobject22 = new Object[]{"ER_SCHEME_REQUIRED", "Zahtevana je shema!"};
        Object[] arrobject23 = new Object[]{"ER_FACTORY_PROPERTY_MISSING", "Predmet Properties (lastnosti), ki je prene\u0161en v SerializerFactory, nima lastnosti ''{0}''."};
        Object[] arrobject24 = new Object[]{"FEATURE_NOT_SUPPORTED", "Parameter ''{0}'' je prepoznan, vendar pa zahtevane vrednosti ni mogo\u010de nastaviti."};
        Object[] arrobject25 = new Object[]{"DOMSTRING_SIZE_ERR", "Nastali niz je predolg za DOMString: ''{0}''."};
        Object[] arrobject26 = new Object[]{"TYPE_MISMATCH_ERR", "Tip vrednosti za to ime parametra je nezdru\u017eljiv s pri\u010dakovanim tipom vrednosti."};
        Object[] arrobject27 = new Object[]{"no-output-specified", "Izhodno mesto za vpisovanje podatkov je bilo ni\u010d."};
        Object[] arrobject28 = new Object[]{"unsupported-encoding", "Odkrito je nepodprto kodiranje."};
        Object[] arrobject29 = new Object[]{"ER_UNABLE_TO_SERIALIZE_NODE", "Vozli\u0161\u010da ni mogo\u010de serializirati."};
        Object[] arrobject30 = new Object[]{"cdata-sections-splitted", "Odsek CDATA vsebuje enega ali ve\u010d ozna\u010devalnikov prekinitve ']]>'."};
        Object[] arrobject31 = new Object[]{"wf-invalid-character", "Vozli\u0161\u010de ''{0}'' vsebuje neveljavne znake XML."};
        Object[] arrobject32 = new Object[]{"ER_WF_INVALID_CHARACTER_IN_PI", "V podatkih navodila za obdelavo je bil najden neveljaven znak XML (Unicode: 0x{0})."};
        Object[] arrobject33 = new Object[]{"ER_WF_LT_IN_ATTVAL", "Vrednost atributa \"{1}\", ki je povezan s tipom elementa \"{0}\", ne sme vsebovati znaka ''<''."};
        Object[] arrobject34 = new Object[]{"ER_WF_REF_TO_UNPARSED_ENT", "Neraz\u010dlenjeni sklic entitete \"&{0};\" ni dovoljen."};
        Object[] arrobject35 = new Object[]{"ER_WF_REF_TO_EXTERNAL_ENT", "Zunanji sklic entitete \"&{0};\" ni dovoljen v vrednosti atributa."};
        Object[] arrobject36 = new Object[]{"ER_NULL_LOCAL_ELEMENT_NAME", "Lokalno ime elementa \"{0}\" je ni\u010d."};
        Object[] arrobject37 = new Object[]{"ER_NULL_LOCAL_ATTR_NAME", "Lokalno ime atributa \"{0}\" je ni\u010d."};
        Object[] arrobject38 = new Object[]{"unbound-prefix-in-entity-reference", "Besedilo za zamenjavo za vozli\u0161\u010de entitete \"{0}\" vsebuje vozli\u0161\u010de elementa \"{1}\" z nevezano predpono \"{2}\"."};
        Object[] arrobject39 = new Object[]{"unbound-prefix-in-entity-reference", "Besedilo za zamenjavo za vozli\u0161\u010de entitete \"{0}\" vsebuje vozli\u0161\u010de atributa \"{1}\" z nevezano predpono \"{2}\"."};
        return new Object[][]{{"BAD_MSGKEY", "Klju\u010d sporo\u010dila ''{0}'' ni v rezredu sporo\u010dila ''{1}''"}, arrobject, arrobject2, arrobject3, arrobject4, arrobject5, arrobject6, {"ER_OIERROR", "Napaka V/I"}, {"ER_ILLEGAL_ATTRIBUTE_POSITION", "Atributa {0} ne morem dodati za podrejenimi vozli\u0161\u010di ali pred izdelavo elementa.  Atribut bo prezrt."}, {"ER_NAMESPACE_PREFIX", "Imenski prostor za predpono ''{0}'' ni bil naveden."}, arrobject7, {"ER_STRAY_NAMESPACE", "Deklaracija imenskega prostora ''{0}''=''{1}'' je zunaj elementa."}, arrobject8, arrobject9, {"ER_COULD_NOT_LOAD_METHOD_PROPERTY", "Datoteke z lastnostmi ''{0}'' ni bilo mogo\u010de nalo\u017eiti za izhodno metodo ''{1}'' (preverite CLASSPATH)"}, {"ER_INVALID_PORT", "Neveljavna \u0161tevilka vrat"}, arrobject10, arrobject11, {"ER_SCHEME_NOT_CONFORMANT", "Shema ni skladna."}, {"ER_SCHEME_FROM_NULL_STRING", "Ni mogo\u010de nastaviti sheme iz niza NULL"}, arrobject12, arrobject13, arrobject14, arrobject15, arrobject16, arrobject17, arrobject18, {"ER_NO_FRAGMENT_STRING_IN_PATH", "Fragment ne more biti hkrati naveden v poti in v fragmentu"}, arrobject19, {"ER_NO_PORT_IF_NO_HOST", "Vrata ne morejo biti navedena, \u010de ni naveden gostitelj"}, arrobject20, arrobject21, arrobject22, arrobject23, {"ER_ENCODING_NOT_SUPPORTED", "Opozorilo:  Izvajalno okolje Java ne podpira kodiranja ''{0}''."}, {"FEATURE_NOT_FOUND", "Parameter ''{0}'' ni prepoznan."}, arrobject24, arrobject25, arrobject26, arrobject27, arrobject28, arrobject29, arrobject30, {"ER_WARNING_WF_NOT_CHECKED", "Primerka preverjevalnika Well-Formedness ni bilo mogo\u010de ustvariti.  Parameter well-formed je bil nastavljen na True, ampak ni mogo\u010de preveriti well-formedness."}, arrobject31, {"ER_WF_INVALID_CHARACTER_IN_COMMENT", "V komentarju je bil najden neveljaven XML znak (Unicode: 0x{0})."}, arrobject32, {"ER_WF_INVALID_CHARACTER_IN_CDATA", "V vsebini odseka CDATASection je bil najden neveljaven znak XML (Unicode: 0x{0})."}, {"ER_WF_INVALID_CHARACTER_IN_TEXT", "V podatkovni vsebini znaka vozli\u0161\u010da je bil najden neveljaven znak XML (Unicode: 0x{0})."}, {"wf-invalid-character-in-node-name", "V vozli\u0161\u010du {0} z imenom ''{1}'' je bil najden neveljaven znak XML."}, {"ER_WF_DASH_IN_COMMENT", "Niz \"--\" ni dovoljen v komentarjih."}, arrobject33, arrobject34, arrobject35, {"ER_NS_PREFIX_CANNOT_BE_BOUND", "Predpona \"{0}\" ne more biti povezana z imenskim prostorom \"{1}\"."}, arrobject36, arrobject37, arrobject38, arrobject39};
    }
}

