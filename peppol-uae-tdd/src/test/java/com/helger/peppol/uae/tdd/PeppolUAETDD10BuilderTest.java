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
