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

import org.junit.Test;

import com.helger.peppol.uae.tdd.codelist.EUAETDDDocumentScope;
import com.helger.peppol.uae.tdd.v10.TaxDataType;

/**
 * Test class for class {@link PeppolUAETDD10Builder}.
 *
 * @author Philip Helger
 */
public final class PeppolUAETDD10BuilderTest
{
  @Test
  public void testBasic ()
  {
    TaxDataType aTDD = new PeppolUAETDD10Builder ().documentScope (EUAETDDDocumentScope.DOMESTIC).build ();
    assertNotNull (aTDD);
  }
}
