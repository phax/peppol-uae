/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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

import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.collection.commons.ICommonsList;
import com.helger.io.resource.ClassPathResource;
import com.helger.jaxb.GenericJAXBMarshaller;
import com.helger.peppol.uae.tdd.v10.ObjectFactory;
import com.helger.peppol.uae.tdd.v10.TaxDataType;
import com.helger.ubl21.UBL21Marshaller;

import jakarta.annotation.Nonnull;

/**
 * This is the reader and writer for Peppol UAE TDD 1.0 documents. This class may be derived to
 * override protected methods from {@link GenericJAXBMarshaller}.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PeppolUAETDD10Marshaller extends GenericJAXBMarshaller <TaxDataType>
{
  @Nonnull
  @ReturnsMutableCopy
  private static ICommonsList <ClassPathResource> _getAllXSDs (@Nonnull final ClassPathResource aXSD)
  {
    final ICommonsList <ClassPathResource> ret = UBL21Marshaller.getAllBaseXSDs ();
    ret.add (aXSD);
    return ret;
  }

  public PeppolUAETDD10Marshaller ()
  {
    super (TaxDataType.class, _getAllXSDs (CPeppolUAETDD.TDD_XSD_1_0), new ObjectFactory ()::createTaxData);
  }
}
