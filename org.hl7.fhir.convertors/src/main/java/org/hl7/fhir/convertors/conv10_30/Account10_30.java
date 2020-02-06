package org.hl7.fhir.convertors.conv10_30;

import org.hl7.fhir.convertors.VersionConvertor_10_30;
import org.hl7.fhir.exceptions.FHIRException;

public class Account10_30 {

    public static org.hl7.fhir.dstu2.model.Account convertAccount(org.hl7.fhir.dstu3.model.Account src) throws FHIRException {
        if (src == null || src.isEmpty())
            return null;
        org.hl7.fhir.dstu2.model.Account tgt = new org.hl7.fhir.dstu2.model.Account();
        VersionConvertor_10_30.copyDomainResource(src, tgt);
        if (src.hasIdentifier()) {
            for (org.hl7.fhir.dstu3.model.Identifier t : src.getIdentifier()) tgt.addIdentifier(VersionConvertor_10_30.convertIdentifier(t));
        }
        if (src.hasNameElement())
            tgt.setNameElement((org.hl7.fhir.dstu2.model.StringType) VersionConvertor_10_30.convertType(src.getNameElement()));
        if (src.hasType()) {
            tgt.setType(VersionConvertor_10_30.convertCodeableConcept(src.getType()));
        }
        if (src.hasStatus()) {
            tgt.setStatus(convertAccountStatus(src.getStatus()));
        }
        if (src.hasActive()) {
            tgt.setActivePeriod(VersionConvertor_10_30.convertPeriod(src.getActive()));
        }
        if (src.hasBalance()) {
            tgt.setBalance(VersionConvertor_10_30.convertMoney(src.getBalance()));
        }
        if (src.hasSubject()) {
            tgt.setSubject(VersionConvertor_10_30.convertReference(src.getSubject()));
        }
        if (src.hasOwner()) {
            tgt.setOwner(VersionConvertor_10_30.convertReference(src.getOwner()));
        }
        if (src.hasDescriptionElement())
            tgt.setDescriptionElement((org.hl7.fhir.dstu2.model.StringType) VersionConvertor_10_30.convertType(src.getDescriptionElement()));
        return tgt;
    }

    public static org.hl7.fhir.dstu3.model.Account convertAccount(org.hl7.fhir.dstu2.model.Account src) throws FHIRException {
        if (src == null || src.isEmpty())
            return null;
        org.hl7.fhir.dstu3.model.Account tgt = new org.hl7.fhir.dstu3.model.Account();
        VersionConvertor_10_30.copyDomainResource(src, tgt);
        if (src.hasIdentifier()) {
            for (org.hl7.fhir.dstu2.model.Identifier t : src.getIdentifier()) tgt.addIdentifier(VersionConvertor_10_30.convertIdentifier(t));
        }
        if (src.hasNameElement())
            tgt.setNameElement((org.hl7.fhir.dstu3.model.StringType) VersionConvertor_10_30.convertType(src.getNameElement()));
        if (src.hasType())
            tgt.setType(VersionConvertor_10_30.convertCodeableConcept(src.getType()));
        if (src.hasStatus()) {
            tgt.setStatus(convertAccountStatus(src.getStatus()));
        }
        if (src.hasActivePeriod()) {
            tgt.setActive(VersionConvertor_10_30.convertPeriod(src.getActivePeriod()));
        }
        if (src.hasBalance()) {
            tgt.setBalance(VersionConvertor_10_30.convertMoney(src.getBalance()));
        }
        if (src.hasSubject()) {
            tgt.setSubject(VersionConvertor_10_30.convertReference(src.getSubject()));
        }
        if (src.hasOwner()) {
            tgt.setOwner(VersionConvertor_10_30.convertReference(src.getOwner()));
        }
        if (src.hasDescriptionElement())
            tgt.setDescriptionElement((org.hl7.fhir.dstu3.model.StringType) VersionConvertor_10_30.convertType(src.getDescriptionElement()));
        return tgt;
    }

    public static org.hl7.fhir.dstu3.model.Account.AccountStatus convertAccountStatus(org.hl7.fhir.dstu2.model.Account.AccountStatus src) throws FHIRException {
        if (src == null)
            return null;
        switch(src) {
            case ACTIVE:
                return org.hl7.fhir.dstu3.model.Account.AccountStatus.ACTIVE;
            case INACTIVE:
                return org.hl7.fhir.dstu3.model.Account.AccountStatus.INACTIVE;
            default:
                return org.hl7.fhir.dstu3.model.Account.AccountStatus.NULL;
        }
    }

    public static org.hl7.fhir.dstu2.model.Account.AccountStatus convertAccountStatus(org.hl7.fhir.dstu3.model.Account.AccountStatus src) throws FHIRException {
        if (src == null)
            return null;
        switch(src) {
            case ACTIVE:
                return org.hl7.fhir.dstu2.model.Account.AccountStatus.ACTIVE;
            case INACTIVE:
                return org.hl7.fhir.dstu2.model.Account.AccountStatus.INACTIVE;
            default:
                return org.hl7.fhir.dstu2.model.Account.AccountStatus.NULL;
        }
    }
}
