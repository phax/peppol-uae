/*
 * Copyright (C) 2025-2026 Philip Helger
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
import java.util.function.Consumer;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.base.builder.IBuilder;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.log.ILogHandler;
import com.helger.base.string.StringHelper;
import com.helger.datetime.helper.PDTFactory;
import com.helger.datetime.xml.XMLOffsetTime;
import com.helger.diagnostics.error.list.ErrorList;
import com.helger.diagnostics.log.ErrorListLogHandler;
import com.helger.peppol.uae.tdd.codelist.EUAETDDDocumentScope;
import com.helger.peppol.uae.tdd.codelist.EUAETDDDocumentTypeCode;
import com.helger.peppol.uae.tdd.codelist.EUAETDDReporterRole;
import com.helger.peppol.uae.tdd.v100.ReportedTransactionType;
import com.helger.peppol.uae.tdd.v100.TaxDataDocumentReporterRoleType;
import com.helger.peppol.uae.tdd.v100.TaxDataDocumentScopeType;
import com.helger.peppol.uae.tdd.v100.TaxDataDocumentTypeCodeType;
import com.helger.peppol.uae.tdd.v100.TaxDataType;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.CustomizationIDType;
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
  public static final String DEFAULT_CUSTOMIZATION_ID = "urn:peppol:taxdata:ae-1";
  public static final String DEFAULT_PROFILE_ID = "urn:peppol:taxreporting";

  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolUAETDD10Builder.class);

  private ILogHandler m_aLogHandler = ILogHandler.createForLogger (LOGGER);
  private String m_sCustomizationID;
  private String m_sProfileID;
  private LocalDate m_aIssueDate;
  private OffsetTime m_aIssueTime;
  private EUAETDDDocumentTypeCode m_eDocumentTypeCode;
  private EUAETDDDocumentScope m_eDocumentScope;
  private EUAETDDReporterRole m_eReporterRole;
  private IParticipantIdentifier m_aReportingParty;
  private IParticipantIdentifier m_aReceivingParty;
  private IParticipantIdentifier m_aReportersRepresentative;
  private ReportedTransactionType m_aReportedTransaction;

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

  @NonNull
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

  @NonNull
  public PeppolUAETDD10Builder profileID (@Nullable final String s)
  {
    m_sProfileID = s;
    return this;
  }

  @Nullable
  public LocalDate issueDate ()
  {
    return m_aIssueDate;
  }

  @NonNull
  public PeppolUAETDD10Builder issueDateNow ()
  {
    return issueDate (PDTFactory.getCurrentLocalDate ());
  }

  @NonNull
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

  @NonNull
  public PeppolUAETDD10Builder issueTimeNow ()
  {
    return issueTime (PDTFactory.getCurrentOffsetTime ());
  }

  @NonNull
  public PeppolUAETDD10Builder issueTime (@Nullable final OffsetTime a)
  {
    // XSD can only handle milliseconds
    m_aIssueTime = PDTFactory.getWithMillisOnly (a);
    return this;
  }

  @NonNull
  public PeppolUAETDD10Builder issueDateTime (@Nullable final OffsetDateTime a)
  {
    if (a == null)
      return issueDate (null).issueTime (null);
    return issueDate (a.toLocalDate ()).issueTime (a.toOffsetTime ());
  }

  @NonNull
  public PeppolUAETDD10Builder issueDateTimeNow ()
  {
    return issueDateTime (PDTFactory.getCurrentOffsetDateTime ());
  }

  @Nullable
  public EUAETDDDocumentTypeCode documentTypeCode ()
  {
    return m_eDocumentTypeCode;
  }

  @NonNull
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

  @NonNull
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

  @NonNull
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
  @NonNull
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
  @NonNull
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
  @NonNull
  public PeppolUAETDD10Builder reportersRepresentative (@Nullable final IParticipantIdentifier a)
  {
    m_aReportersRepresentative = a;
    return this;
  }

  @Nullable
  public ReportedTransactionType reportedTransaction ()
  {
    return m_aReportedTransaction;
  }

  @NonNull
  public PeppolUAETDD10Builder reportedTransaction (@NonNull final Consumer <PeppolUAETDD10ReportedTransactionBuilder> aBuilderConsumer)
  {
    if (m_eDocumentTypeCode == null)
      throw new IllegalStateException ("The ReportedTransaction can only be built, after the DocumentTypeCode is set!");
    final PeppolUAETDD10ReportedTransactionBuilder aBuilder = new PeppolUAETDD10ReportedTransactionBuilder (m_eDocumentTypeCode);
    aBuilder.logHandler (m_aLogHandler);
    aBuilderConsumer.accept (aBuilder);
    return reportedTransaction (aBuilder.build ());
  }

  @NonNull
  public PeppolUAETDD10Builder reportedTransaction (@Nullable final ReportedTransactionType a)
  {
    m_aReportedTransaction = a;
    return this;
  }

  /**
   * @return The log handler that receives the messages of the required field checks - errors for
   *         missing or invalid fields. Never <code>null</code>.
   */
  @NonNull
  public ILogHandler logHandler ()
  {
    return m_aLogHandler;
  }

  /**
   * Set the log handler that receives the messages of the required field checks, when calling
   * {@link #build()}. It is not used by
   * {@link #isEveryRequiredFieldSet(ErrorList)}. Missing or invalid required fields are
   * reported as errors. The default handler logs to the logger of this class, use
   * {@link ILogHandler#createForConsumer(java.util.function.Consumer)} or
   * {@link ILogHandler#createNoOp()} to redirect or discard them. The handler is passed on to all
   * sub-builders that are created via a lambda <b>after</b> this call, so call this method before
   * adding any sub-elements. Sub-builders that are created externally and passed in are not
   * changed.
   *
   * @param aLogHandler
   *        The log handler to use. May not be <code>null</code>.
   * @return this for chaining
   */
  @NonNull
  public PeppolUAETDD10Builder logHandler (@NonNull final ILogHandler aLogHandler)
  {
    ValueEnforcer.notNull (aLogHandler, "LogHandler");
    m_aLogHandler = aLogHandler;
    return this;
  }

  private boolean _isEveryRequiredFieldSet (@NonNull final ILogHandler aLogHandler)
  {
    int nErrs = 0;
    final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    final String sErrorPrefix = "Error in Peppol UAE TDD 1.0 builder: ";

    if (StringHelper.isEmpty (m_sCustomizationID))
    {
      aLogHandler.error (sErrorPrefix + "CustomizationID is missing");
      nErrs++;
    }
    if (StringHelper.isEmpty (m_sProfileID))
    {
      aLogHandler.error (sErrorPrefix + "ProfileID is missing");
      nErrs++;
    }
    if (m_aIssueDate == null)
    {
      aLogHandler.error (sErrorPrefix + "IssueDate is missing");
      nErrs++;
    }
    if (m_aIssueTime == null)
    {
      aLogHandler.error (sErrorPrefix + "IssueTime is missing");
      nErrs++;
    }
    if (m_eDocumentTypeCode == null)
    {
      aLogHandler.error (sErrorPrefix + "DocumentTypeCode is missing");
      nErrs++;
    }
    if (m_eDocumentScope == null)
    {
      aLogHandler.error (sErrorPrefix + "DocumentScope is missing");
      nErrs++;
    }
    if (m_eReporterRole == null)
    {
      aLogHandler.error (sErrorPrefix + "ReporterRole is missing");
      nErrs++;
    }

    if (m_aReportingParty == null)
    {
      aLogHandler.error (sErrorPrefix + "ReportingParty is missing");
      nErrs++;
    }
    else
      if (!aIF.isParticipantIdentifierSchemeValid (m_aReportingParty.getScheme ()))
      {
        aLogHandler.error (sErrorPrefix +
                           "ReportingParty identifier scheme '" +
                           m_aReportingParty.getScheme () +
                           "' is invalid");
        nErrs++;
      }
      else
        if (!aIF.isParticipantIdentifierValueValid (m_aReportingParty.getScheme (), m_aReportingParty.getValue ()))
        {
          aLogHandler.error (sErrorPrefix +
                             "ReportingParty identifier value '" +
                             m_aReportingParty.getValue () +
                             "' is invalid for scheme '" +
                             m_aReportingParty.getScheme () +
                             "'");
          nErrs++;
        }

    if (m_aReceivingParty == null)
    {
      aLogHandler.error (sErrorPrefix + "ReceivingParty is missing");
      nErrs++;
    }
    else
      if (!aIF.isParticipantIdentifierSchemeValid (m_aReceivingParty.getScheme ()))
      {
        aLogHandler.error (sErrorPrefix +
                           "ReceivingParty identifier scheme '" +
                           m_aReceivingParty.getScheme () +
                           "' is invalid");
        nErrs++;
      }
      else
        if (!aIF.isParticipantIdentifierValueValid (m_aReceivingParty.getScheme (), m_aReceivingParty.getValue ()))
        {
          aLogHandler.error (sErrorPrefix +
                             "ReceivingParty identifier value '" +
                             m_aReceivingParty.getValue () +
                             "' is invalid for scheme '" +
                             m_aReceivingParty.getScheme () +
                             "'");
          nErrs++;
        }
        else
        {
          final String [] aParts = StringHelper.getExplodedArray (':', m_aReceivingParty.getValue (), 2);
          if (!"0242".equals (aParts[0]))
          {
            aLogHandler.error (sErrorPrefix +
                               "ReceivingParty identifier value '" +
                               m_aReceivingParty.getValue () +
                               "' must use the 0242 identifier scheme");
            nErrs++;
          }
        }

    if (m_aReportersRepresentative == null)
    {
      aLogHandler.error (sErrorPrefix + "ReportersRepresentative is missing");
      nErrs++;
    }
    else
      if (!aIF.isParticipantIdentifierSchemeValid (m_aReportersRepresentative.getScheme ()))
      {
        aLogHandler.error (sErrorPrefix +
                           "ReportersRepresentative identifier meta scheme '" +
                           m_aReportersRepresentative.getScheme () +
                           "' is invalid");
        nErrs++;
      }
      else
        if (!aIF.isParticipantIdentifierValueValid (m_aReportersRepresentative.getScheme (),
                                                    m_aReportersRepresentative.getValue ()))
        {
          aLogHandler.error (sErrorPrefix +
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
            aLogHandler.error (sErrorPrefix +
                               "ReportersRepresentative identifier value '" +
                               m_aReportersRepresentative.getValue () +
                               "' must use the 0242 identifier scheme");
            nErrs++;
          }
        }

    // UAE must have exactly one reported transaction
    if (m_aReportedTransaction == null)
    {
      aLogHandler.error (sErrorPrefix + "ReportedTransaction is missing");
      nErrs++;
    }

    return nErrs == 0;
  }

  public boolean isEveryRequiredFieldSet (@NonNull final ErrorList aErrorList)
  {
    ValueEnforcer.notNull (aErrorList, "ErrorList");
    return _isEveryRequiredFieldSet (new ErrorListLogHandler (aErrorList));
  }

  @Nullable
  public TaxDataType build ()
  {
    if (!_isEveryRequiredFieldSet (m_aLogHandler))
    {
      LOGGER.error ("At least one mandatory field is not set and therefore the TDD cannot be build.");
      return null;
    }

    final TaxDataType ret = new TaxDataType ();
    ret.setCustomizationID (new CustomizationIDType (m_sCustomizationID));
    ret.setProfileID (new ProfileIDType (m_sProfileID));
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
    ret.addReportedTransaction (m_aReportedTransaction);
    return ret;
  }
}
