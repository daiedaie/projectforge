/////////////////////////////////////////////////////////////////////////////
//
// Project ProjectForge Community Edition
//         www.projectforge.org
//
// Copyright (C) 2001-2019 Micromata GmbH, Germany (www.micromata.com)
//
// ProjectForge is dual-licensed.
//
// This community edition is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License as published
// by the Free Software Foundation; version 3 of the License.
//
// This community edition is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
// Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, see http://www.gnu.org/licenses/.
//
/////////////////////////////////////////////////////////////////////////////

package org.projectforge.calendar;

import org.jfree.data.time.Month;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.projectforge.framework.calendar.WeekHolder;
import org.projectforge.framework.time.DatePrecision;
import org.projectforge.framework.time.PFDateTime;
import org.projectforge.test.TestSetup;

import java.time.ZoneId;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class WeekHolderTest {
  //private final static Logger log = Logger.getLogger(WeekHolderTest.class);

  @BeforeAll
  public static void setUp() {
    // Needed if this tests runs before the ConfigurationTest.
    TestSetup.init();
  }

  @Test
  public void testWeekHolder() {
    final PFDateTime dt = PFDateTime.now(ZoneId.of("UTC"), Locale.GERMAN);
    WeekHolder week = new WeekHolder(dt);
    assertEquals(7, week.getDays().length);
    assertEquals(2, week.getDays()[0].getDayOfWeek());
    assertEquals("monday", week.getDays()[0].getDayKey());
    PFDateTime dateTime = PFDateTime.now(ZoneId.of("UTC"), Locale.GERMAN).withPrecision(DatePrecision.DAY)
        .withYear(1970).withMonth(Month.NOVEMBER).withDayOfMonth(21).withHour(4).withMinute(50).withSecond(23);
    week = new WeekHolder(dateTime);
    assertEquals(7, week.getDays().length);
    assertEquals(2, week.getDays()[0].getDayOfWeek());
    assertEquals("monday", week.getDays()[0].getDayKey());
    assertEquals("sunday", week.getDays()[6].getDayKey());
    assertEquals(16, week.getDays()[0].getDayOfMonth());
    assertEquals("saturday", week.getDays()[5].getDayKey());
    assertEquals(21, week.getDays()[5].getDayOfMonth());
    dateTime = dateTime.withYear(2007).withMonth(Month.MARCH).withDayOfMonth(1);
    assertEquals(Month.MARCH, dateTime.getMonthValue());
    week = new WeekHolder(dateTime, dateTime.getMonthValue());
    assertEquals("monday", week.getDays()[0].getDayKey());
    assertEquals(26, week.getDays()[0].getDayOfMonth());
    assertTrue(week.getDays()[0].isMarker()); // February, 26
    assertTrue(week.getDays()[1].isMarker()); // February, 27
    assertTrue(week.getDays()[2].isMarker()); // February, 28
    assertEquals(1, week.getDays()[3].getDayOfMonth());
    assertFalse(week.getDays()[3].isMarker(), "Day is not of current month and should be marked."); // March, 1
  }
}
