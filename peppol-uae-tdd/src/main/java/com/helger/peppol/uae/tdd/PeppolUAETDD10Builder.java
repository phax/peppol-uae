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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.base.builder.IBuilder;
import com.helger.base.log.ConditionalLogger;
import com.helger.peppol.uae.tdd.v10.TaxDataType;

import jakarta.annotation.Nullable;

/**
 * Builder for Peppol UAE TDD 1.0 document.
 *
 * @author Philip Helger
 */
public class PeppolUAETDD10Builder implements IBuilder <TaxDataType>
{
  private static final Logger LOGGER = LoggerFactory.getLogger (PeppolUAETDD10Builder.class);

  public PeppolUAETDD10Builder ()
  {}

  public boolean isEveryRequiredFieldSet (final boolean bDoLogOnError)
  {
    final ConditionalLogger aCondLog = new ConditionalLogger (LOGGER, bDoLogOnError);

    // TODO
    return true;
  }

  @Nullable
  public TaxDataType build ()
  {
    if (!isEveryRequiredFieldSet (true))
    {
      LOGGER.error ("At least one mandatory field is not set and therefore the AS4 message cannot be send.");
      return null;
    }

    final TaxDataType ret = new TaxDataType ();

    // TODO
    return ret;
  }
}
