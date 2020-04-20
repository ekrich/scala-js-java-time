package org.scalajs.testsuite.javalib.time

import java.time.DateTimeException
import java.time.temporal._

import org.junit.Test
import org.junit.Assert._
import org.scalajs.testsuite.utils.AssertThrows._
import munit.FunSuite

abstract class TemporalAccessorTest[TempAcc <: TemporalAccessor] extends FunSuite {
  val samples: Seq[TempAcc]

  def isSupported(field: ChronoField): Boolean

  test("isSupported_TemporalField") {
    for {
      accessor <- samples
      field <- ChronoField.values
    } {
      if (isSupported(field))
        assertTrue(accessor.isSupported(field))
      else
        assertFalse(accessor.isSupported(field))
    }
    for (accessor <- samples)
      assertFalse(accessor.isSupported(null))
  }

  def expectedRangeFor(accessor: TempAcc, field: TemporalField): ValueRange = field.range()

  test("range") {
    for {
      accessor <- samples
      field <- ChronoField.values
    } {
      if (accessor.isSupported(field)) {
        val expected = expectedRangeFor(accessor, field)
        assertEquals(expected, accessor.range(field))
      } else {
        expectThrows(classOf[UnsupportedTemporalTypeException], accessor.range(field))
      }
    }
  }

  test("get") {
    for {
      accessor <- samples
      field <- ChronoField.values
    } {
      if (accessor.isSupported(field) && field.range.isIntValue)
        assertEquals(accessor.getLong(field), accessor.get(field).toLong)
      else if (accessor.isSupported(field))
        expectThrows(classOf[DateTimeException], accessor.get(field))
      else
        expectThrows(classOf[UnsupportedTemporalTypeException], accessor.get(field))
    }
  }

  test("getLong_unsupported_field") {
    for {
      accessor <- samples
      field <- ChronoField.values() if !accessor.isSupported(field)
    } {
      expectThrows(classOf[UnsupportedTemporalTypeException],
          accessor.getLong(field))
    }
  }
}
