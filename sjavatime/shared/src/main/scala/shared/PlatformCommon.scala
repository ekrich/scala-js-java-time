package shared

trait PlatformCommon {
  /**
    * The local date tuple with month values 1 to 12
    *
    * @return year, month, day
    */
  def localDate(): (Int, Int, Int)

  /**
    * The local date tuple with month values 0 to 11
    *
    * @return year, month, day
    */
  def chronoLocalDate(): (Int, Int, Int)

  /**
    * The local time tuple
    *
    * @return hour, minute, second, nano
    */
  def localTime(): (Int, Int, Int, Int)

  /**
    * The min of the day or the last day of the month
    *
    * @param day of month
    * @param lastDayOfMonth or max day in this month
    * @return dayOfMonth
    */
  def minDay(day: Int, lastDayOfMonth: Int): Int
}
