/*
 * Copyright (C) 2025 Philip Helger
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.peppol.uae.tdd;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.base.builder.IBuilder;
import com.helger.base.log.ConditionalLogger;
import com.helger.base.string.StringHelper;
import com.helger.datetime.helper.PDTFactory;
import com.helger.datetime.xml.XMLOffsetTime;
import com.helger.peppol.uae.tdd.codelist.EUAETDDDocumentScope;
import com.helger.peppol.uae.tdd.codelist.EUAETDDDocumentTypeCode;
import com.helger.peppol.uae.tdd.codelist.EUAETDDReporterRole;
import com.helger.peppol.uae.tdd.v10.TaxDataDocumentReporterRoleType;
import com.helger.peppol.uae.tdd.v10.TaxDataDocumentScopeType;
import com.helger.peppol.uae.tdd.v10.TaxDataDocumentTypeCodeType;
import com.helger.peppol.uae.tdd.v10.TaxDataType;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.CustomizationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.IssueDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.IssueTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.ProfileIDType;

/**
 * Builder for Peppol UAE TDD 1.0 document.
 *
 * @author Philip Helger
 */
public class PeppolUAETDD10Builder implements IBuilder <TaxDataType>
{
  public static final String DEFAULT_CUSTOMIZATION_ID = "urn:peppol:pint:taxdata-1@ae-1";
  public static final String DEFAULT_PROFILE_ID = "urn:peppol:bis:taxreporting";

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolUAETDD10Builder.class);

  private String m_sCustomizationID;
  private String m_sProfileID;
  private String m_sID;
  private LocalDate m_aIssueDate;
  private OffsetTime m_aIssueTime;
  private EUAETDDDocumentTypeCode m_eDocumentTypeCode;
  private EUAETDDDocumentScope m_eDocumentScope;
  private EUAETDDReporterRole m_eReporterRole;
  private IParticipantIdentifier m_aReportingParty;
  private IParticipantIdentifier m_aReceivingParty;
  private IParticipantIdentifier m_aReportersRepresentative;

  public PeppolUAETDD10Builder ()
  {
    customizationID (DEFAULT_CUSTOMIZATION_ID);
    profileID (DEFAULT_PROFILE_ID);
    issueDateTimeNow ();
  }

  @Nullable
  public String customizationID ()
  {
    return m_sCustomizationID;
  }

  @Nonnull
  public PeppolUAETDD10Builder customizationID (@Nullable final String s)
  {
    m_sCustomizationID = s;
    return this;
  }

  @Nullable
  public String profileID ()
  {
    return m_sProfileID;
  }

  @Nonnull
  public PeppolUAETDD10Builder profileID (@Nullable final String s)
  {
    m_sProfileID = s;
    return this;
  }

  @Nullable
  public String id ()
  {
    return m_sID;
  }

  @Nonnull
  public PeppolUAETDD10Builder id (@Nullable final String s)
  {
    m_sID = s;
    return this;
  }

  @Nullable
  public LocalDate issueDate ()
  {
    return m_aIssueDate;
  }

  @Nonnull
  public PeppolUAETDD10Builder issueDateNow ()
  {
    return issueDate (PDTFactory.getCurrentLocalDate ());
  }

  @Nonnull
  public PeppolUAETDD10Builder issueDate (@Nullable final LocalDate a)
  {
    m_aIssueDate = a;
    return this;
  }

  @Nullable
  public OffsetTime issueTime ()
  {
    return m_aIssueTime;
  }

  @Nonnull
  public PeppolUAETDD10Builder issueTimeNow ()
  {
    return issueTime (PDTFactory.getCurrentOffsetTime ());
  }

  @Nonnull
  public PeppolUAETDD10Builder issueTime (@Nullable final OffsetTime a)
  {
    // XSD can only handle milliseconds
    m_aIssueTime = PDTFactory.getWithMillisOnly (a);
    return this;
  }

  @Nonnull
  public PeppolUAETDD10Builder issueDateTime (@Nullable final OffsetDateTime a)
  {
    if (a == null)
      return issueDate (null).issueTime (null);
    return issueDate (a.toLocalDate ()).issueTime (a.toOffsetTime ());
  }

  @Nonnull
  public PeppolUAETDD10Builder issueDateTimeNow ()
  {
    return issueDateTime (PDTFactory.getCurrentOffsetDateTime ());
  }

  @Nullable
  public EUAETDDDocumentTypeCode documentTypeCode ()
  {
    return m_eDocumentTypeCode;
  }

  @Nonnull
  public PeppolUAETDD10Builder documentTypeCode (@Nullable final EUAETDDDocumentTypeCode e)
  {
    m_eDocumentTypeCode = e;
    return this;
  }

  @Nullable
  public EUAETDDDocumentScope documentScope ()
  {
    return m_eDocumentScope;
  }

  @Nonnull
  public PeppolUAETDD10Builder documentScope (@Nullable final EUAETDDDocumentScope e)
  {
    m_eDocumentScope = e;
    return this;
  }

  @Nullable
  public EUAETDDReporterRole reporterRole ()
  {
    return m_eReporterRole;
  }

  @Nonnull
  public PeppolUAETDD10Builder reporterRole (@Nullable final EUAETDDReporterRole e)
  {
    m_eReporterRole = e;
    return this;
  }

  @Nullable
  public IParticipantIdentifier reportingParty ()
  {
    return m_aReportingParty;
  }

  /**
   * @param a
   *        Peppol Participant ID of C1/C4 of the business document.
   * @return this for chaining
   */
  @Nonnull
  public PeppolUAETDD10Builder reportingParty (@Nullable final IParticipantIdentifier a)
  {
    m_aReportingParty = a;
    return this;
  }

  @Nullable
  public IParticipantIdentifier receivingParty ()
  {
    return m_aReceivingParty;
  }

  /**
   * @param a
   *        Peppol Participant ID of C5 of the TDD.
   * @return this for chaining
   */
  @Nonnull
  public PeppolUAETDD10Builder receivingParty (@Nullable final IParticipantIdentifier a)
  {
    m_aReceivingParty = a;
    return this;
  }

  @Nullable
  public IParticipantIdentifier reportersRepresentative ()
  {
    return m_aReportersRepresentative;
  }

  /**
   * @param a
   *        Peppol Participant ID of C2/C3 of the business document. Must use the SPIS scheme.
   * @return this for chaining
   */
  @Nonnull
  public PeppolUAETDD10Builder reportersRepresentative (@Nullable final IParticipantIdentifier a)
  {
    m_aReportersRepresentative = a;
    return this;
  }

  public boolean isEveryRequiredFieldSet (final boolean bDoLogOnError)
  {
    int nErrs = 0;
    final ConditionalLogger aCondLog = new ConditionalLogger (LOGGER, bDoLogOnError);
    final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    final String sErrorPrefix = "Error in Peppol UAE TDD 1.0 builder: ";

    if (StringHelper.isEmpty (m_sCustomizationID))
    {
      aCondLog.error (sErrorPrefix + "CustomizationID is missing");
      nErrs++;
    }
    if (StringHelper.isEmpty (m_sProfileID))
    {
      aCondLog.error (sErrorPrefix + "ProfileID is missing");
      nErrs++;
    }
    // ID is optional
    if (m_aIssueDate == null)
    {
      aCondLog.error (sErrorPrefix + "IssueDate is missing");
      nErrs++;
    }
    if (m_aIssueTime == null)
    {
      aCondLog.error (sErrorPrefix + "IssueTime is missing");
      nErrs++;
    }
    if (m_eDocumentTypeCode == null)
    {
      aCondLog.error (sErrorPrefix + "DocumentTypeCode is missing");
      nErrs++;
    }
    if (m_eDocumentScope == null)
    {
      aCondLog.error (sErrorPrefix + "DocumentScope is missing");
      nErrs++;
    }
    if (m_eReporterRole == null)
    {
      aCondLog.error (sErrorPrefix + "ReporterRole is missing");
      nErrs++;
    }
    if (m_aReportingParty == null)
    {
      aCondLog.error (sErrorPrefix + "ReportingParty is missing");
      nErrs++;
    }
    else
      if (!aIF.isParticipantIdentifierSchemeValid (m_aReportingParty.getScheme ()))
      {
        aCondLog.error (sErrorPrefix +
                        "ReportingParty identifier scheme '" +
                        m_aReportingParty.getScheme () +
                        "' is invalid");
        nErrs++;
      }
      else
        if (!aIF.isParticipantIdentifierValueValid (m_aReportingParty.getScheme (), m_aReportingParty.getValue ()))
        {
          aCondLog.error (sErrorPrefix +
                          "ReportingParty identifier value '" +
                          m_aReportingParty.getValue () +
                          "' is invalid for scheme '" +
                          m_aReportingParty.getScheme () +
                          "'");
          nErrs++;
        }

    if (m_aReceivingParty == null)
    {
      aCondLog.error (sErrorPrefix + "ReceivingParty is missing");
      nErrs++;
    }
    else
      if (!aIF.isParticipantIdentifierSchemeValid (m_aReceivingParty.getScheme ()))
      {
        aCondLog.error (sErrorPrefix +
                        "ReceivingParty identifier scheme '" +
                        m_aReceivingParty.getScheme () +
                        "' is invalid");
        nErrs++;
      }
      else
        if (!aIF.isParticipantIdentifierValueValid (m_aReceivingParty.getScheme (), m_aReceivingParty.getValue ()))
        {
          aCondLog.error (sErrorPrefix +
                          "ReceivingParty identifier value '" +
                          m_aReceivingParty.getValue () +
                          "' is invalid for scheme '" +
                          m_aReceivingParty.getScheme () +
                          "'");
          nErrs++;
        }

    if (m_aReportersRepresentative == null)
    {
      aCondLog.error (sErrorPrefix + "ReportersRepresentative is missing");
      nErrs++;
    }
    else
      if (!aIF.isParticipantIdentifierSchemeValid (m_aReportersRepresentative.getScheme ()))
      {
        aCondLog.error (sErrorPrefix +
                        "ReportersRepresentative identifier meta scheme '" +
                        m_aReportersRepresentative.getScheme () +
                        "' is invalid");
        nErrs++;
      }
      else
        if (!aIF.isParticipantIdentifierValueValid (m_aReportersRepresentative.getScheme (),
                                                    m_aReportersRepresentative.getValue ()))
        {
          aCondLog.error (sErrorPrefix +
                          "ReportersRepresentative identifier value '" +
                          m_aReportersRepresentative.getValue () +
                          "' is invalid for meta scheme '" +
                          m_aReportersRepresentative.getScheme () +
                          "'");
          nErrs++;
        }
        else
        {
          final String [] aParts = StringHelper.getExplodedArray (':', m_aReportersRepresentative.getValue (), 2);
          if (!"0242".equals (aParts[0]))
          {
            aCondLog.error (sErrorPrefix +
                            "ReportersRepresentative identifier value '" +
                            m_aReportersRepresentative.getValue () +
                            "' must use the 0242 identifier scheme");
            nErrs++;
          }
        }

    // TODO
    return nErrs == 0;
  }

  @Nullable
  public TaxDataType build ()
  {
    if (!isEveryRequiredFieldSet (true))
    {
      LOGGER.error ("At least one mandatory field is not set and therefore the TDD cannot be build.");
      return null;
    }

    final TaxDataType ret = new TaxDataType ();
    ret.setCustomizationID (new CustomizationIDType (m_sCustomizationID));
    ret.setProfileID (new ProfileIDType (m_sProfileID));
    if (StringHelper.isNotEmpty (m_sID))
      ret.setID (new IDType (m_sID));
    ret.setIssueDate (new IssueDateType (m_aIssueDate));
    ret.setIssueTime (new IssueTimeType (XMLOffsetTime.of (m_aIssueTime)));
    {
      final TaxDataDocumentTypeCodeType a = new TaxDataDocumentTypeCodeType ();
      a.setValue (m_eDocumentTypeCode.getID ());
      ret.setDocumentTypeCode (a);
    }
    {
      final TaxDataDocumentScopeType a = new TaxDataDocumentScopeType ();
      a.setValue (m_eDocumentScope.getID ());
      ret.setDocumentScope (a);
    }
    {
      final TaxDataDocumentReporterRoleType a = new TaxDataDocumentReporterRoleType ();
      a.setValue (m_eReporterRole.getID ());
      ret.setReporterRole (a);
    }
    {
      final String [] aParts = StringHelper.getExplodedArray (':', m_aReportingParty.getValue (), 2);
      final PartyType aParty = new PartyType ();
      aParty.setEndpointID (aParts[1]).setSchemeID (aParts[0]);
      ret.setReportingParty (aParty);
    }
    {
      final String [] aParts = StringHelper.getExplodedArray (':', m_aReceivingParty.getValue (), 2);
      final PartyType aParty = new PartyType ();
      aParty.setEndpointID (aParts[1]).setSchemeID (aParts[0]);
      ret.setReceivingParty (aParty);
    }
    {
      final String [] aParts = StringHelper.getExplodedArray (':', m_aReportersRepresentative.getValue (), 2);
      final PartyType aParty = new PartyType ();
      final PartyIdentificationType aPID = new PartyIdentificationType ();
      aPID.setID (aParts[1]).setSchemeID (aParts[0]);
      aParty.addPartyIdentification (aPID);
      ret.setReportersRepresentative (aParty);
    }

    // TODO
    return ret;
  }
}
