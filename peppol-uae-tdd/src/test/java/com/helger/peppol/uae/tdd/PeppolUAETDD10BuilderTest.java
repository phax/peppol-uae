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

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.peppol.uae.tdd.codelist.EUAETDDDocumentScope;
import com.helger.peppol.uae.tdd.codelist.EUAETDDDocumentTypeCode;
import com.helger.peppol.uae.tdd.codelist.EUAETDDReporterRole;
import com.helger.peppol.uae.tdd.jaxb.PeppolUAETDD10Marshaller;
import com.helger.peppol.uae.tdd.v10.TaxDataType;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.peppolid.factory.PeppolIdentifierFactory;

/**
 * Test class for class {@link PeppolUAETDD10Builder}.
 *
 * @author Philip Helger
 */
public final class PeppolUAETDD10BuilderTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolUAETDD10BuilderTest.class);

  @Test
  public void testBasic ()
  {
    final IIdentifierFactory aIF = PeppolIdentifierFactory.INSTANCE;
    final TaxDataType aTDD = new PeppolUAETDD10Builder ().documentTypeCode (EUAETDDDocumentTypeCode.SUBMIT)
                                                         .documentScope (EUAETDDDocumentScope.DOMESTIC)
                                                         .reporterRole (EUAETDDReporterRole.SENDER)
                                                         .reportingParty (aIF.createParticipantIdentifierWithDefaultScheme ("0235:c1id"))
                                                         .receivingParty (aIF.createParticipantIdentifierWithDefaultScheme ("0235:c5id"))
                                                         .reportersRepresentative (aIF.createParticipantIdentifierWithDefaultScheme ("0242:987654"))
                                                         .reportedTransaction (rt -> rt.transportHeaderID (UUID.randomUUID ()
                                                                                                               .toString ())
                                                                                       .id ("invoice-1")
                                                                                       .sellerTaxID ("123456789")
                                                                                       .sellerTaxSchemeID ("VAT"))
                                                         .build ();
    assertNotNull (aTDD);

    // Serialize
    final String sXML = new PeppolUAETDD10Marshaller ().setFormattedOutput (true).getAsString (aTDD);
    assertNotNull (sXML);
    if (true)
      LOGGER.info (sXML);
  }
}
