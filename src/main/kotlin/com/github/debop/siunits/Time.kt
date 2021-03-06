package com.github.debop.siunits

import java.io.Serializable

enum class TimeUnit(val unitName: String, val factor: Double) {

  NANO_SECOND("ns", 1.0e-9),
  MICRO_SECOND("us", 1.0e-6),
  MILLI_SECOND("ms", 1.0e-3),
  SECOND("sec", 1.0),
  MINUTE("min", 60.0),
  HOUR("hr", 60.0 * 60.0),
  DAY("day", 24 * 60 * 60.0);

  companion object {
    @JvmStatic
    fun parse(unitStr: String): TimeUnit {
      var lower = unitStr.toLowerCase()
      if (lower.endsWith("s")) {
        lower = lower.dropLast(1)
      }
      return TimeUnit.values().find { it.unitName == lower }
             ?: throw NumberFormatException("Unknown Time unit. unitStr=$unitStr")
    }
  }
}


data class Time(val second: Double = 0.0) : Comparable<Time>, Serializable {

  final operator fun plus(other: Time): Time = Time(second + other.second)
  final operator fun minus(other: Time): Time = Time(second - other.second)
  final operator fun times(scalar: Double): Time = Time(second * scalar)
  final operator fun div(scalar: Double): Time = Time(second / scalar)
  final operator fun unaryMinus(): Time = Time(-second)

  fun inNano(): Double = second / TimeUnit.NANO_SECOND.factor
  fun inMicro(): Double = second / TimeUnit.MICRO_SECOND.factor
  fun inMilli(): Double = second / TimeUnit.MILLI_SECOND.factor
  fun inSecond(): Double = second
  fun inMinute(): Double = second / TimeUnit.MINUTE.factor
  fun inHour(): Double = second / TimeUnit.HOUR.factor
  fun inDay(): Double = second / TimeUnit.DAY.factor

  fun toHuman(): String {
    TimeUnit.values().reversed().forEach {
      val time = second / it.factor
      if (time > 1.0) {
        return "%.1f %s".format(time, it.unitName)
      }
    }
    return toString()
  }

  override fun compareTo(other: Time): Int = second.compareTo(other.second)
  override fun toString() = "%.1f %s".format(second, TimeUnit.SECOND.unitName)

  companion object {
    final val ZERO = Time(0.0)
    final val MAX_VALUE = Time(Double.MAX_VALUE)
    final val MIN_VALUE = Time(Double.MIN_VALUE)
    final val POSITIVE_INF = Time(Double.POSITIVE_INFINITY)
    final val NEGATIVE_INF = Time(Double.NEGATIVE_INFINITY)
    final val NaN = Time(Double.NaN)

    @JvmStatic
    fun of(time: Double = 0.0, unit: TimeUnit = TimeUnit.SECOND): Time =
        Time(time * unit.factor)

    @JvmStatic
    fun parse(timeStr: String): Time {
      if (timeStr.isNullOrBlank())
        return ZERO

      try {
        val (time, unit) = timeStr.split(" ", limit = 2)
        return Time.of(time.toDouble(), TimeUnit.parse(unit))
      } catch(e: Exception) {
        throw NumberFormatException("Unknown Time string. timeStr=$timeStr")
      }
    }
  }
}