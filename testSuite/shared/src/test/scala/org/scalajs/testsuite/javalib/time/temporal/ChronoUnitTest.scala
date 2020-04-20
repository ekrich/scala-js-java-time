package org.scalajs.testsuite.javalib.time.temporal

import java.time.temporal.ChronoUnit


class ChronoUnitTest extends munit.FunSuite {
  import ChronoUnit._

  test("test_isDurationEstimated") {
    for (u <- ChronoUnit.values)
      assert(u.isDurationEstimated != u.isTimeBased)
  }

  test("test_isDateBased") {
    assert(!NANOS.isDateBased)
    assert(!MICROS.isDateBased)
    assert(!MILLIS.isDateBased)
    assert(!SECONDS.isDateBased)
    assert(!MINUTES.isDateBased)
    assert(!HOURS.isDateBased)
    assert(!HALF_DAYS.isDateBased)
    assert(DAYS.isDateBased)
    assert(WEEKS.isDateBased)
    assert(MONTHS.isDateBased)
    assert(YEARS.isDateBased)
    assert(DECADES.isDateBased)
    assert(CENTURIES.isDateBased)
    assert(MILLENNIA.isDateBased)
    assert(ERAS.isDateBased)
    assert(!FOREVER.isDateBased)
  }

  test("test_isTimeBased") {
    assert(NANOS.isTimeBased)
    assert(MICROS.isTimeBased)
    assert(MILLIS.isTimeBased)
    assert(SECONDS.isTimeBased)
    assert(MINUTES.isTimeBased)
    assert(HOURS.isTimeBased)
    assert(HALF_DAYS.isTimeBased)
    assert(!DAYS.isTimeBased)
    assert(!WEEKS.isTimeBased)
    assert(!MONTHS.isTimeBased)
    assert(!YEARS.isTimeBased)
    assert(!DECADES.isTimeBased)
    assert(!CENTURIES.isTimeBased)
    assert(!MILLENNIA.isTimeBased)
    assert(!ERAS.isTimeBased)
    assert(!FOREVER.isTimeBased)
  }

  test("test_values") {
    val units = Array[AnyRef](NANOS, MICROS, MILLIS, SECONDS, MINUTES, HOURS,
        HALF_DAYS, DAYS, WEEKS, MONTHS, YEARS, DECADES, CENTURIES, MILLENNIA,
        ERAS, FOREVER)
    assertEquals(units, values.asInstanceOf[Array[AnyRef]])
  }

  test("test_valueOf") {
    assertEquals(NANOS, valueOf("NANOS"))
    assertEquals(MICROS, valueOf("MICROS"))
    assertEquals(MILLIS, valueOf("MILLIS"))
    assertEquals(SECONDS, valueOf("SECONDS"))
    assertEquals(MINUTES, valueOf("MINUTES"))
    assertEquals(HOURS, valueOf("HOURS"))
    assertEquals(HALF_DAYS, valueOf("HALF_DAYS"))
    assertEquals(DAYS, valueOf("DAYS"))
    assertEquals(WEEKS, valueOf("WEEKS"))
    assertEquals(MONTHS, valueOf("MONTHS"))
    assertEquals(YEARS, valueOf("YEARS"))
    assertEquals(DECADES, valueOf("DECADES"))
    assertEquals(CENTURIES, valueOf("CENTURIES"))
    assertEquals(MILLENNIA, valueOf("MILLENNIA"))
    assertEquals(ERAS, valueOf("ERAS"))
    assertEquals(FOREVER, valueOf("FOREVER"))
  }
}
