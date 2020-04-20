package org.scalajs.testsuite.javalib.time

import java.time._
import java.time.chrono.IsoChronology
import java.time.temporal.ChronoUnit

import org.scalajs.testsuite.utils.AssertThrows._

class PeriodTest extends TemporalAmountTest {

  import Period._
  import ChronoUnit._

  final val pmin = of(Int.MinValue, Int.MinValue, Int.MinValue)
  final val pmin1 = of(-Int.MaxValue, -Int.MaxValue, -Int.MaxValue)
  final val pmax = of(Int.MaxValue, Int.MaxValue, Int.MaxValue)
  final val oneYear = of(1, 0, 0)
  final val oneMonth = of(0, 1, 0)
  final val oneDay = of(0, 0, 1)

  val samples1 = Seq(ZERO, oneYear, oneYear.negated,
      oneMonth, oneMonth.negated, oneDay, oneDay.negated)

  val samples: Seq[Period] = samples1 ++ Seq(pmin, pmin1, pmax)

  val units = Seq(YEARS, MONTHS, DAYS)

  test("test_get") {
    for (p <- samples) {
      assertEquals(p.getYears.toLong, p.get(YEARS))
      assertEquals(p.getMonths.toLong, p.get(MONTHS))
      assertEquals(p.getDays.toLong, p.get(DAYS))
    }
  }

  test("test_getChronology") {
    for (p <- samples) {
      assertEquals(IsoChronology.INSTANCE, p.getChronology)
    }
  }

  test("test_isZero") {
    for (p <- samples) {
      if (p == ZERO) assert(p.isZero)
      else assert(!p.isZero)
    }
  }

  test("test_isNegative") {
    for (p <- Seq(ZERO, oneYear, oneMonth, oneDay, pmax))
      assert(!p.isNegative)
    for (p <- Seq(oneYear.negated, oneMonth.negated, oneDay.negated, pmin, pmin1))
      assert(p.isNegative)
    for (p <- Seq(of(-1, 1, 1), of(1, -1, 1), of(1, 1, -1)))
      assert(p.isNegative)
  }

  test("test_getYears") {
    assertEquals(1, oneYear.getYears)
    assertEquals(0, oneMonth.getYears)
    assertEquals(0, oneDay.getYears)
    assertEquals(Int.MinValue, pmin.getYears)
    assertEquals(Int.MinValue + 1, pmin1.getYears)
    assertEquals(Int.MaxValue, pmax.getYears)
  }

  test("test_getMonths") {
    assertEquals(0, oneYear.getMonths)
    assertEquals(1, oneMonth.getMonths)
    assertEquals(0, oneDay.getMonths)
    assertEquals(Int.MinValue, pmin.getMonths)
    assertEquals(Int.MinValue + 1, pmin1.getMonths)
    assertEquals(Int.MaxValue, pmax.getMonths)
  }

  test("test_getDays") {
    assertEquals(0, oneYear.getDays)
    assertEquals(0, oneMonth.getDays)
    assertEquals(1, oneDay.getDays)
    assertEquals(Int.MinValue, pmin.getDays)
    assertEquals(Int.MinValue + 1, pmin1.getDays)
    assertEquals(Int.MaxValue, pmax.getDays)
  }

  test("test_withYears") {
    for {
      p <- samples
      n <- Seq(Int.MinValue, 0, Int.MaxValue)
    } {
      val p1 = p.withYears(n)
      assertEquals(n, p1.getYears)
      assertEquals(p.getMonths, p1.getMonths)
      assertEquals(p.getDays, p1.getDays)
    }
  }

  test("test_withMonths") {
    for {
      p <- samples
      n <- Seq(Int.MinValue, 0, Int.MaxValue)
    } {
      val p1 = p.withMonths(n)
      assertEquals(p.getYears, p1.getYears)
      assertEquals(n, p1.getMonths)
      assertEquals(p.getDays, p1.getDays)
    }
  }

  test("test_withDays") {
    for {
      p <- samples
      n <- Seq(Int.MinValue, 0, Int.MaxValue)
    } {
      val p1 = p.withDays(n)
      assertEquals(p.getYears, p1.getYears)
      assertEquals(p.getMonths, p1.getMonths)
      assertEquals(n, p1.getDays)
    }
  }

  test("test_plus") {
    for {
      p1 <- samples1 :+ pmin1
      p2 <- if (p1 != pmin1) samples1 :+ pmin1 else samples1
    } {
      val p = p1.plus(p2)
      assertEquals(p1.getYears + p2.getYears, p.getYears)
      assertEquals(p1.getMonths + p2.getMonths, p.getMonths)
      assertEquals(p1.getDays + p2.getDays, p.getDays)
    }

    for (p <- Seq(oneYear, oneMonth, oneDay))
      expectThrows(classOf[ArithmeticException], pmax.plus(p))
    for (p <- Seq(oneYear.negated, oneMonth.negated, oneDay.negated))
      expectThrows(classOf[ArithmeticException], pmin.plus(p))
    for (p <- samples)
      expectThrows(classOf[DateTimeException], p.plus(Duration.ZERO))
  }

  test("test_plusYears") {
    for {
      p <- samples1
      n <- Seq(Int.MinValue + 1, -1, 0, 1, Int.MaxValue - 1)
    } {
      val p1 = p.plusYears(n)
      assertEquals(p.getYears + n, p1.getYears)
      assertEquals(p.getMonths, p1.getMonths)
      assertEquals(p.getDays, p1.getDays)
    }

    expectThrows(classOf[ArithmeticException], oneYear.plusYears(Int.MaxValue))
    expectThrows(classOf[ArithmeticException],
        oneYear.negated.plusYears(Int.MinValue))
    expectThrows(classOf[ArithmeticException], pmax.plusYears(1))
    expectThrows(classOf[ArithmeticException], pmin.plusYears(-1))
  }

  test("test_plusMonths") {
    for {
      p <- samples1
      n <- Seq(Int.MinValue + 1, -1, 0, 1, Int.MaxValue - 1)
    } {
      val p1 = p.plusMonths(n)
      assertEquals(p.getYears, p1.getYears)
      assertEquals(p.getMonths + n, p1.getMonths)
      assertEquals(p.getDays, p1.getDays)
    }

    expectThrows(classOf[ArithmeticException], oneMonth.plusMonths(Int.MaxValue))
    expectThrows(classOf[ArithmeticException],
        oneMonth.negated.plusMonths(Int.MinValue))
    expectThrows(classOf[ArithmeticException], pmax.plusMonths(1))
    expectThrows(classOf[ArithmeticException], pmin.plusMonths(-1))
  }

  test("test_plusDays") {
    for {
      p <- samples1
      n <- Seq(Int.MinValue + 1, -1, 0, 1, Int.MaxValue - 1)
    } {
      val p1 = p.plusDays(n)
      assertEquals(p.getYears, p1.getYears)
      assertEquals(p.getMonths, p1.getMonths)
      assertEquals(p.getDays + n, p1.getDays)
    }

    expectThrows(classOf[ArithmeticException], oneDay.plusDays(Int.MaxValue))
    expectThrows(classOf[ArithmeticException],
        oneDay.negated.plusDays(Int.MinValue))
    expectThrows(classOf[ArithmeticException], pmax.plusDays(1))
    expectThrows(classOf[ArithmeticException], pmin.plusDays(-1))
  }

  test("test_minus") {
    for {
      p1 <- samples1 :+ pmin1
      p2 <- if (p1.isNegative) samples1 :+ pmin1 else samples1
    } {
      val p = p1.minus(p2)
      assertEquals(p1.getYears - p2.getYears, p.getYears)
      assertEquals(p1.getMonths - p2.getMonths, p.getMonths)
      assertEquals(p1.getDays - p2.getDays, p.getDays)
    }

    for (p <- Seq(oneYear, oneMonth, oneDay))
      expectThrows(classOf[ArithmeticException], pmin.minus(p))
    for (p <- Seq(oneYear.negated, oneMonth.negated, oneDay.negated))
      expectThrows(classOf[ArithmeticException], pmax.minus(p))
    for (p <- samples)
      expectThrows(classOf[DateTimeException], p.minus(Duration.ZERO))
  }

  test("test_minusYears") {
    for {
      p <- samples1
      n <- Seq(Int.MinValue + 2, -1, 0, 1, Int.MaxValue)
    } {
      val p1 = p.minusYears(n)
      assertEquals(p.getYears - n, p1.getYears)
      assertEquals(p.getMonths, p1.getMonths)
      assertEquals(p.getDays, p1.getDays)
    }

    expectThrows(classOf[ArithmeticException],
        oneYear.minusYears(Int.MinValue + 1))
    expectThrows(classOf[ArithmeticException], pmin.minusYears(1))
    expectThrows(classOf[ArithmeticException], pmax.minusYears(-1))
  }

  test("test_minusMonths") {
    for {
      p <- samples1
      n <- Seq(Int.MinValue + 2, -1, 0, 1, Int.MaxValue)
    } {
      val p1 = p.minusMonths(n)
      assertEquals(p.getYears, p1.getYears)
      assertEquals(p.getMonths - n, p1.getMonths)
      assertEquals(p.getDays, p1.getDays)
    }

    expectThrows(classOf[ArithmeticException],
        oneMonth.minusMonths(Int.MinValue + 1))
    expectThrows(classOf[ArithmeticException], pmin.minusMonths(1))
    expectThrows(classOf[ArithmeticException], pmax.minusMonths(-1))
  }

  test("test_minusDays") {
    for {
      p <- samples1
      n <- Seq(Int.MinValue + 2, -1, 0, 1, Int.MaxValue)
    } {
      val p1 = p.minusDays(n)
      assertEquals(p.getYears, p1.getYears)
      assertEquals(p.getMonths, p1.getMonths)
      assertEquals(p.getDays - n, p1.getDays)
    }

    expectThrows(classOf[ArithmeticException],
        oneDay.minusDays(Int.MinValue + 1))
    expectThrows(classOf[ArithmeticException], pmin.minusDays(1))
    expectThrows(classOf[ArithmeticException], pmax.minusDays(-1))
  }

  test("test_multipliedBy") {
    for {
      p <- samples1
      min = if (p.isNegative) Int.MinValue + 1 else Int.MinValue
      n <- Seq(min, -2, 2, Int.MaxValue)
    } {
      val p1 = p.multipliedBy(n)
      assertEquals(p.getYears * n, p1.getYears)
      assertEquals(p.getMonths * n, p1.getMonths)
      assertEquals(p.getDays * n, p1.getDays)
    }
    for (p <- samples) {
      assertEquals(p, p.multipliedBy(1))
      assertEquals(ZERO, p.multipliedBy(0))
    }
    for (p <- samples if p != pmin)
      assertEquals(p.negated, p.multipliedBy(-1))

    expectThrows(classOf[ArithmeticException], pmin.multipliedBy(2))
    expectThrows(classOf[ArithmeticException], pmin.multipliedBy(-1))
    for (p <- Seq(pmin1, pmax)) {
      expectThrows(classOf[ArithmeticException], p.multipliedBy(2))
      expectThrows(classOf[ArithmeticException], p.multipliedBy(-2))
    }
    for (p <- samples1 if p != ZERO) {
      val p2 = p.multipliedBy(2)
      expectThrows(classOf[ArithmeticException], p2.multipliedBy(Int.MaxValue))
      expectThrows(classOf[ArithmeticException], p2.multipliedBy(Int.MinValue))
    }
    for (p <- Seq(oneYear.negated, oneMonth.negated, oneDay.negated))
      expectThrows(classOf[ArithmeticException], p.multipliedBy(Int.MinValue))
  }

  test("test_negated") {
    for (p <- samples if p != pmin) {
      val p1 = p.negated
      assertEquals(-p.getYears, p1.getYears)
      assertEquals(-p.getMonths, p1.getMonths)
      assertEquals(-p.getDays, p1.getDays)
    }

    expectThrows(classOf[ArithmeticException], pmin.negated)
  }

  test("test_normalized") {
    val ps = samples1 ++ Seq(of(1, -1, 0), of(-1, 1, 0)) ++
        Seq(of(1, -25, 1), of(-1, 25, -1), of(1, 13, 1), of(-1, -13, -1))
    for (p <- ps) {
      val p1 = p.normalized
      val years = p1.getYears
      val months = p1.getMonths
      assert(Math.abs(months) < 12)
      assert(!(years > 0 && months < 0))
      assert(!(years < 0 && months > 0))
      assertEquals(p.getYears * 12 + p.getMonths, years * 12 + months)
    }

    for (p <- Seq(pmin, pmin1, pmax))
      expectThrows(classOf[ArithmeticException], p.normalized)
  }

  test("test_toTotalMonths") {
    for (p <- samples)
      assertEquals(p.getYears.toLong * 12 + p.getMonths, p.toTotalMonths)
  }

  test("test_addTo") {
    val ds = Seq(LocalDate.MIN, LocalDate.MAX, LocalDate.of(2011, 2, 28),
        LocalDate.of(2012, 2, 29))
    val ts = Seq(LocalTime.MIN, LocalTime.NOON, LocalTime.MAX)
    val p1 = Period.of(1, 3, 5)
    val p2 = p1.negated

    for (t <- ds ++ ts)
      assertEquals(ZERO.addTo(t), t)

    assertEquals(Period.of(1999999998, 11, 30).addTo(LocalDate.MIN),
      LocalDate.MAX)
    assertEquals(Period.of(-1999999998, -11, -30).addTo(LocalDate.MAX),
      LocalDate.MIN)
    assertEquals(Period.of(1, 0, 1).addTo(LocalDate.of(2011, 2, 28)),
      LocalDate.of(2012, 2, 29))
    assertEquals(Period.of(-1, 0, -1).addTo(LocalDate.of(2012, 2, 29)),
      LocalDate.of(2011, 2, 27))
    assertEquals(oneYear.addTo(LocalDate.of(2012, 2, 29)),
      LocalDate.of(2013, 2, 28))
    assertEquals(Period.of(-1, 0, 1).addTo(LocalDate.of(2013, 2, 28)),
      LocalDate.of(2012, 2, 29))

    for (p <- Seq(oneYear, oneMonth, oneYear, pmin, pmax))
      expectThrows(classOf[DateTimeException], p.addTo(LocalDate.MAX))
    for (p <- Seq(oneYear.negated, oneMonth.negated, oneYear.negated, pmin, pmax))
      expectThrows(classOf[DateTimeException], p.addTo(LocalDate.MIN))
    for {
      p <- samples if p != ZERO
      t <- ts
    } {
      expectThrows(classOf[DateTimeException], p.addTo(t))
    }
  }

  test("test_subtractFrom") {
    val ds = Seq(LocalDate.MIN, LocalDate.MAX, LocalDate.of(2012, 2, 29))
    val ts = Seq(LocalTime.MIN, LocalTime.NOON, LocalTime.MAX)

    for (t <- ds ++ ts)
      assertEquals(ZERO.subtractFrom(t), t)

    assertEquals(Period.of(-1999999998, -11, -30).subtractFrom(LocalDate.MIN),
      LocalDate.MAX)
    assertEquals(Period.of(1999999998, 11, 30).subtractFrom(LocalDate.MAX),
      LocalDate.MIN)
    assertEquals(Period.of(-1, 0, -1).subtractFrom(LocalDate.of(2011, 2, 28)),
      LocalDate.of(2012, 2, 29))
    assertEquals(Period.of(1, 0, 1).subtractFrom(LocalDate.of(2012, 2, 29)),
      LocalDate.of(2011, 2, 27))
    assertEquals(oneYear.negated.subtractFrom(LocalDate.of(2012, 2, 29)),
      LocalDate.of(2013, 2, 28))
    assertEquals(Period.of(1, 0, -1).subtractFrom(LocalDate.of(2013, 2, 28)),
      LocalDate.of(2012, 2, 29))

    for (p <- Seq(oneYear.negated, oneMonth.negated, oneYear.negated, pmin, pmax))
      expectThrows(classOf[DateTimeException], p.subtractFrom(LocalDate.MAX))
    for (p <- Seq(oneYear, oneMonth, oneYear, pmin, pmax))
      expectThrows(classOf[DateTimeException], p.subtractFrom(LocalDate.MIN))
    for {
      p <- samples if p != ZERO
      t <- ts
    } {
      expectThrows(classOf[DateTimeException], p.subtractFrom(t))
    }
  }

  test("test_toString") {
    assertEquals("P0D", ZERO.toString)
    assertEquals("P-2147483648Y-2147483648M-2147483648D", pmin.toString)
    assertEquals("P2147483647Y2147483647M2147483647D", pmax.toString)
    assertEquals("P1Y", oneYear.toString)
    assertEquals("P-1Y", oneYear.negated.toString)
    assertEquals("P1M", oneMonth.toString)
    assertEquals("P-1M", oneMonth.negated.toString)
    assertEquals("P1D", oneDay.toString)
    assertEquals("P-1D", oneDay.negated.toString)
    assertEquals("P2Y-3M", of(2, -3, 0).toString)
    assertEquals("P-5Y7D", of(-5, 0, 7).toString)
    assertEquals("P11M-13D", of(0, 11, -13).toString)
  }
}
