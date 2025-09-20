package com.helger.peppol.uae.tdd.codelist;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * UAE TDD Document Scope.
 *
 * @author Philip Helger
 */
public enum EUAETDDDocumentScope implements IHasID <String>
{
  DOMESTIC ("D"),
  INTERNATIONAL_PEPPOL ("IP"),
  INTERNATIONAL_NON_PEPPOL ("INP");

  private final String m_sID;

  EUAETDDDocumentScope (@Nonnull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public static EUAETDDDocumentScope getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EUAETDDDocumentScope.class, sID);
  }
}
