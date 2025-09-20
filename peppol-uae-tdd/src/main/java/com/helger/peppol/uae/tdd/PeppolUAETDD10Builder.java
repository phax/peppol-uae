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
import com.helger.peppol.uae.tdd.v10.TaxDataDocumentScopeType;
import com.helger.peppol.uae.tdd.v10.TaxDataType;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
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
  private EUAETDDDocumentScope m_eDocumentScope;

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

  public boolean isEveryRequiredFieldSet (final boolean bDoLogOnError)
  {
    int nErrs = 0;
    final ConditionalLogger aCondLog = new ConditionalLogger (LOGGER, bDoLogOnError);

    if (StringHelper.isEmpty (m_sCustomizationID))
    {
      aCondLog.error ("CustomizationID is missing");
      nErrs++;
    }
    if (StringHelper.isEmpty (m_sProfileID))
    {
      aCondLog.error ("ProfileID is missing");
      nErrs++;
    }
    // ID is optional
    if (m_aIssueDate == null)
    {
      aCondLog.error ("IssueDate is missing");
      nErrs++;
    }
    if (m_aIssueTime == null)
    {
      aCondLog.error ("IssueTime is missing");
      nErrs++;
    }
    if (m_eDocumentScope == null)
    {
      aCondLog.error ("DocumentScope is missing");
      nErrs++;
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
      final TaxDataDocumentScopeType a = new TaxDataDocumentScopeType ();
      a.setValue (m_eDocumentScope.getID ());
      ret.setDocumentScope (a);
    }

    // TODO
    return ret;
  }
}
