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
package com.helger.peppol.uae.tdd.jaxb;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.helger.io.resource.ClassPathResource;
import com.helger.peppol.uae.tdd.testfiles.PeppolUAETestFiles;

/**
 * Test class for class {@link PeppolUAETDD10Marshaller}.
 *
 * @author Philip Helger
 */
public final class PeppolUAETDD10MarshallerTest
{
  @Test
  public void testBasic10 ()
  {
    final PeppolUAETDD10Marshaller m = new PeppolUAETDD10Marshaller ();
    for (final ClassPathResource aRes : PeppolUAETestFiles.getAllGoodTDD10Files ())
      assertNotNull (m.read (aRes));
    for (final ClassPathResource aRes : PeppolUAETestFiles.getAllSchematronBadTDD10Files ())
      assertNotNull (m.read (aRes));
  }
}
