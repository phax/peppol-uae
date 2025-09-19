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
