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
package com.helger.peppol.uae.tdd.testfiles;

import com.helger.annotation.Nonempty;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.io.resource.ClassPathResource;

import jakarta.annotation.Nonnull;

/**
 * Sanity methods to get all Peppol UAE test files
 *
 * @author Philip Helger
 */
public final class PeppolUAETestFiles
{
  private PeppolUAETestFiles ()
  {}

  @Nonnull
  private static ClassLoader _getCL ()
  {
    return PeppolUAETestFiles.class.getClassLoader ();
  }

  @Nonnull
  @ReturnsMutableCopy
  private static ICommonsList <ClassPathResource> _getAll (@Nonnull final String sPrefix,
                                                           @Nonnull final String... aFilenames)
  {
    final ICommonsList <ClassPathResource> ret = new CommonsArrayList <> (aFilenames.length);
    for (final String s : aFilenames)
      ret.add (new ClassPathResource ("external/" + sPrefix + s, _getCL ()));
    return ret;
  }

  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllGoodBillingCreditNoteFiles ()
  {
    return _getAll ("billing/cn/", "Disclosed agent billing tax credit note.xml", "Standard tax credit Note.xml");
  }

  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllGoodBillingInvoiceFiles ()
  {
    return _getAll ("billing/inv/",
                    "Commercial invoice.xml",
                    "Continuous supplies.xml",
                    "Deemed supply - predefined endpoint.xml",
                    "Disclosed agent billing.xml",
                    "Exports.xml",
                    "Exports - predefined endpoint.xml",
                    "Margin scheme.xml",
                    "Standard invoice - Extensive.xml",
                    "Standard invoice Mandatory fields.xml",
                    "Standard tax invoice.xml",
                    "Standard tax invoice - predefined endpoint.xml",
                    "Summary tax invoice.xml",
                    "Supply involving free trade zone.xml",
                    "Supply through e-commerce.xml",
                    "Supply under Reverse charge mechanism.xml",
                    "Zero rated supplies.xml");
  }

  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllGoodTDD10Files ()
  {
    return _getAll ("tdd/10/good/", "simple.xml", "tax-currency.xml");
  }

  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public static ICommonsList <ClassPathResource> getAllSchematronBadTDD10Files ()
  {
    return _getAll ("tdd/10/bad-sch/", "bad-ibr-tdd-09.xml");
  }
}
