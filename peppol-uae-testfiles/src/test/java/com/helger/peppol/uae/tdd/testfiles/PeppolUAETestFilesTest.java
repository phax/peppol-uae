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
package com.helger.peppol.uae.tdd.testfiles;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.io.resource.ClassPathResource;

/**
 * Test class for class {@link PeppolUAETestFiles}.
 *
 * @author Philip Helger
 */
public final class PeppolUAETestFilesTest
{
  @Test
  public void testExists ()
  {
    assertTrue (PeppolUAETestFiles.getAllGoodBillingCreditNoteFiles ().stream ().allMatch (ClassPathResource::exists));
    assertTrue (PeppolUAETestFiles.getAllGoodBillingInvoiceFiles ().stream ().allMatch (ClassPathResource::exists));
    assertTrue (PeppolUAETestFiles.getAllGoodTDD10Files ().stream ().allMatch (ClassPathResource::exists));
    assertTrue (PeppolUAETestFiles.getAllSchematronBadTDD10Files ().stream ().allMatch (ClassPathResource::exists));
    assertTrue (PeppolUAETestFiles.getAllPayloadBadTDD10Files ().stream ().allMatch (ClassPathResource::exists));
  }
}
