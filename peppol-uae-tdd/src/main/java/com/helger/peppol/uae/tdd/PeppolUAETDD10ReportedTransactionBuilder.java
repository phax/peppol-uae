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
import com.helger.peppol.uae.tdd.v10.ReferencedDocumentTypeCodeType;
import com.helger.peppol.uae.tdd.v10.ReportedDocumentType;
import com.helger.peppol.uae.tdd.v10.ReportedTransactionType;
import com.helger.peppol.uae.tdd.v10.TransportHeaderIDType;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyTaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.CustomizationIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.DocumentCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.IssueDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.IssueTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.ProfileIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.TaxCurrencyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.UUIDType;

/**
 * Builder for Peppol UAE TDD 1.0 sub element called "ReportedTransaction".
 *
 * @author Philip Helger
 */
public class PeppolUAETDD10ReportedTransactionBuilder implements IBuilder <ReportedTransactionType>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolUAETDD10ReportedTransactionBuilder.class);

  private String m_sTransportHeaderID;
  private String m_sCustomizationID;
  private String m_sProfileID;
  private String m_sID;
  private String m_sUUID;
  private LocalDate m_aIssueDate;
  private OffsetTime m_aIssueTime;
  private String m_sDocumentTypeCode;
  private String m_sDocumentCurrencyCode;
  private String m_sTaxCurrencyCode;
  private String m_sSellerTaxID;
  private String m_sSellerTaxSchemeID;
  private String m_sBuyerID;
  private String m_sBuyerIDSchemeID;
  private String m_sBuyerTaxID;

  public PeppolUAETDD10ReportedTransactionBuilder ()
  {}

  @Nullable
  public String transportHeaderID ()
  {
    return m_sTransportHeaderID;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder transportHeaderID (@Nullable final String s)
  {
    m_sTransportHeaderID = s;
    return this;
  }

  @Nullable
  public String customizationID ()
  {
    return m_sCustomizationID;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder customizationID (@Nullable final String s)
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
  public PeppolUAETDD10ReportedTransactionBuilder profileID (@Nullable final String s)
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
  public PeppolUAETDD10ReportedTransactionBuilder id (@Nullable final String s)
  {
    m_sID = s;
    return this;
  }

  @Nullable
  public String uuid ()
  {
    return m_sUUID;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder uuid (@Nullable final String s)
  {
    m_sUUID = s;
    return this;
  }

  @Nullable
  public LocalDate issueDate ()
  {
    return m_aIssueDate;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder issueDate (@Nullable final LocalDate a)
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
  public PeppolUAETDD10ReportedTransactionBuilder issueTime (@Nullable final OffsetTime a)
  {
    // XSD can only handle milliseconds
    m_aIssueTime = PDTFactory.getWithMillisOnly (a);
    return this;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder issueDateTime (@Nullable final OffsetDateTime a)
  {
    if (a == null)
      return issueDate (null).issueTime (null);
    return issueDate (a.toLocalDate ()).issueTime (a.toOffsetTime ());
  }

  @Nullable
  public String documentTypeCode ()
  {
    return m_sDocumentTypeCode;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder documentTypeCode (@Nullable final String s)
  {
    m_sDocumentTypeCode = s;
    return this;
  }

  @Nullable
  public String documentCurrencyCode ()
  {
    return m_sDocumentCurrencyCode;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder documentCurrencyCode (@Nullable final String s)
  {
    m_sDocumentCurrencyCode = s;
    return this;
  }

  @Nullable
  public String taxCurrencyCode ()
  {
    return m_sTaxCurrencyCode;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder taxCurrencyCode (@Nullable final String s)
  {
    m_sTaxCurrencyCode = s;
    return this;
  }

  @Nullable
  public String sellerTaxID ()
  {
    return m_sSellerTaxID;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder sellerTaxID (@Nullable final String s)
  {
    m_sSellerTaxID = s;
    return this;
  }

  @Nullable
  public String sellerTaxSchemeID ()
  {
    return m_sSellerTaxSchemeID;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder sellerTaxSchemeID (@Nullable final String s)
  {
    m_sSellerTaxSchemeID = s;
    return this;
  }

  @Nullable
  public String buyerID ()
  {
    return m_sBuyerID;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder buyerID (@Nullable final String s)
  {
    m_sBuyerID = s;
    return this;
  }

  @Nullable
  public String buyerIDSchemeID ()
  {
    return m_sBuyerIDSchemeID;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder buyerIDSchemeID (@Nullable final String s)
  {
    m_sBuyerIDSchemeID = s;
    return this;
  }

  @Nullable
  public String buyerTaxID ()
  {
    return m_sBuyerTaxID;
  }

  @Nonnull
  public PeppolUAETDD10ReportedTransactionBuilder buyerTaxID (@Nullable final String s)
  {
    m_sBuyerTaxID = s;
    return this;
  }

  public boolean isEveryRequiredFieldSet (final boolean bDoLogOnError)
  {
    int nErrs = 0;
    final ConditionalLogger aCondLog = new ConditionalLogger (LOGGER, bDoLogOnError);
    final String sErrorPrefix = "Error in Peppol UAE TDD 1.0 ReportedTransaction builder: ";

    // All optional except:
    if (StringHelper.isEmpty (m_sID))
    {
      aCondLog.error (sErrorPrefix + "ID is missing");
      nErrs++;
    }
    if (StringHelper.isEmpty (m_sSellerTaxID))
    {
      aCondLog.error (sErrorPrefix + "SellerTaxID is missing");
      nErrs++;
    }
    if (StringHelper.isEmpty (m_sSellerTaxSchemeID))
    {
      aCondLog.error (sErrorPrefix + "SellerTaxSchemeID is missing");
      nErrs++;
    }

    // TODO
    return nErrs == 0;
  }

  @Nullable
  public ReportedTransactionType build ()
  {
    if (!isEveryRequiredFieldSet (true))
    {
      LOGGER.error ("At least one mandatory field is not set and therefore the TDD ReportedTransaction cannot be build.");
      return null;
    }

    final ReportedTransactionType ret = new ReportedTransactionType ();
    if (StringHelper.isNotEmpty (m_sTransportHeaderID))
    {
      final TransportHeaderIDType a = new TransportHeaderIDType ();
      a.setValue (m_sTransportHeaderID);
      ret.setTransportHeaderID (a);
    }

    if (StringHelper.isNotEmpty (m_sTransportHeaderID))
    {
      final ReportedDocumentType a = new ReportedDocumentType ();
      if (StringHelper.isNotEmpty (m_sCustomizationID))
        a.setCustomizationID (new CustomizationIDType (m_sCustomizationID));
      if (StringHelper.isNotEmpty (m_sProfileID))
        a.setProfileID (new ProfileIDType (m_sProfileID));
      a.setID (new IDType (m_sID));
      if (StringHelper.isNotEmpty (m_sUUID))
        a.setUUID (new UUIDType (m_sUUID));
      if (m_aIssueDate != null)
        a.setIssueDate (new IssueDateType (m_aIssueDate));
      if (m_aIssueTime != null)
        a.setIssueTime (new IssueTimeType (XMLOffsetTime.of (m_aIssueTime)));
      if (StringHelper.isNotEmpty (m_sDocumentTypeCode))
      {
        final ReferencedDocumentTypeCodeType a2 = new ReferencedDocumentTypeCodeType ();
        a2.setValue (m_sDocumentTypeCode);
        a.setDocumentTypeCode (a2);
      }
      if (StringHelper.isNotEmpty (m_sDocumentCurrencyCode))
        a.setDocumentCurrencyCode (new DocumentCurrencyCodeType (m_sDocumentCurrencyCode));
      if (StringHelper.isNotEmpty (m_sTaxCurrencyCode))
        a.setTaxCurrencyCode (new TaxCurrencyCodeType (m_sTaxCurrencyCode));
      {
        final SupplierPartyType a2 = new SupplierPartyType ();
        {
          final PartyType aParty = new PartyType ();
          {
            final PartyTaxSchemeType aPTS = new PartyTaxSchemeType ();
            {
              aPTS.setCompanyID (m_sSellerTaxID);
              final TaxSchemeType aTS = new TaxSchemeType ();
              aTS.setID (m_sSellerTaxSchemeID);
              aPTS.setTaxScheme (aTS);
            }
            aParty.addPartyTaxScheme (aPTS);
          }
          a2.setParty (aParty);
        }
        a.setAccountingSupplierParty (a2);
      }
      {
        final CustomerPartyType a2 = new CustomerPartyType ();
        {
          final PartyType aParty = new PartyType ();
          a2.setParty (aParty);
        }
        a.setAccountingCustomerParty (a2);
      }
      ret.setReportedDocument (a);
    }

    // TODO
    return ret;
  }
}
