@file:JvmName("lengths")

package com.github.debop.siunits

import java.io.Serializable


const val MILLIMETER_IN_METER = 1.0 / 1000.0
const val CENTIMETER_IN_METER = 1.0 / 100.0
const val METER_IN_METER = 1.0
const val KILOMETER_IN_METER = 1000.0

fun Double.millimeter(): Length = Length(this * MILLIMETER_IN_METER)
fun Double.centimeter(): Length = Length(this * CENTIMETER_IN_METER)
fun Double.meter(): Length = Length(this)
fun Double.kilometer(): Length = Length(this * KILOMETER_IN_METER)

enum class LengthUnit(val unitName: String, val factor: Double) {

  MILLIMETER("mm", MILLIMETER_IN_METER),
  CENTIMETER("cm", CENTIMETER_IN_METER),
  METER("m", METER_IN_METER),
  KILOMETER("km", KILOMETER_IN_METER);

  companion object {

    @JvmStatic fun parse(str: String): LengthUnit {
      val lower = str.toLowerCase()
      return LengthUnit.values().find { it.unitName == lower }
             ?: throw UnsupportedOperationException("Unknwon Length unit string. str=$str")
    }
  }
}


data class Length(val meter: Double) : Comparable<Length>, Serializable {

  fun inMillimeter(): Double = meter / MILLIMETER_IN_METER
  fun inCentimeter(): Double = meter / CENTIMETER_IN_METER
  fun inMeter(): Double = meter
  fun inKilometer(): Double = meter * KILOMETER_IN_METER

  override fun compareTo(other: Length): Int = meter.compareTo(other.meter)


  companion object {
    val ZERO = Length(0.0)
    val MIN_VALUE = Length(Double.MIN_VALUE)
    val MAX_VALUE = Length(Double.MAX_VALUE)
    val POSITIVE_INF = Length(Double.POSITIVE_INFINITY)
    val NEGATIVE_INF = Length(Double.NEGATIVE_INFINITY)
    val NaN = Length(Double.NaN)

    @JvmStatic fun of(length: Double, unit: LengthUnit = LengthUnit.METER): Length = when (unit) {
      LengthUnit.MILLIMETER -> length.millimeter()
      LengthUnit.CENTIMETER -> length.centimeter()
      LengthUnit.METER      -> length.meter()
      LengthUnit.KILOMETER  -> length.kilometer()
      else                  -> throw UnsupportedOperationException("Unknwon Length unit. unit=$unit")
    }

    @JvmStatic fun parse(str: String): Length {
      if (str.isBlank()) {
        return ZERO
      }

      val (length, unit) = str.split(" ", limit = 2)

      try {
        return of(length.toDouble(), LengthUnit.parse(unit))
      } catch(e: Exception) {
        throw NumberFormatException("Invalid Length string. str=$str")
      }
    }
  }

}