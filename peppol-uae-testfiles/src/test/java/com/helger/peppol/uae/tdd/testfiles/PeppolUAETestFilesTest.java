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
  }
}
