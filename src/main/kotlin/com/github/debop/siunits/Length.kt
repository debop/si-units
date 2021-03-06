@file:JvmName("lengths")

package com.github.debop.siunits

import java.io.Serializable

const val MILLIMETER_IN_METER = 1.0e-3
const val CENTIMETER_IN_METER = 1.0e-2
const val METER_IN_METER = 1.0
const val KILOMETER_IN_METER = 1.0e3
const val INCH_IN_METER = 39.37
const val FEET_IN_METER = 3.2809
const val YARD_IN_METER = 1.0936
const val MILE_IN_METER = 1609.344

fun Double.millimeter(): Length = Length.of(this, LengthUnit.MILLIMETER)
fun Double.centimeter(): Length = Length.of(this, LengthUnit.CENTIMETER)
fun Double.meter(): Length = Length.of(this)
fun Double.kilometer(): Length = Length.of(this, LengthUnit.KILOMETER)
fun Double.inch(): Length = Length.of(this, LengthUnit.INCH)
fun Double.feet(): Length = Length.of(this, LengthUnit.FEET)
fun Double.yard(): Length = Length.of(this, LengthUnit.YARD)
fun Double.mile(): Length = Length.of(this, LengthUnit.MILE)

/**
 * 길이(Length)의 단위
 */
enum class LengthUnit(val unitName: String, val factor: Double) {

  MILLIMETER("mm", MILLIMETER_IN_METER),
  CENTIMETER("cm", CENTIMETER_IN_METER),
  METER("m", METER_IN_METER),
  KILOMETER("km", KILOMETER_IN_METER),

  INCH("inch", INCH_IN_METER),
  FEET("ft", FEET_IN_METER),
  YARD("yd", YARD_IN_METER),
  MILE("ml", MILE_IN_METER);

  companion object {

    @JvmStatic
    fun parse(str: String): LengthUnit {
      val lower = str.toLowerCase()
      return LengthUnit.values().find { it.unitName == lower }
             ?: throw UnsupportedOperationException("Unknwon Length unit string. str=$str")
    }
  }
}

/**
 * 길이를 나타내는 클래스
 */
public data class Length(val meter: Double = 0.0) : Comparable<Length>, Serializable {

  public operator final fun plus(other: Length): Length = Length(meter + other.meter)
  public operator final fun minus(other: Length): Length = Length(meter - other.meter)
  public operator final fun times(scalar: Double): Length = Length(meter * scalar)
  public operator final fun times(other: Length): Area = Area(meter * other.meter)
  public operator final fun div(scalar: Double): Length = Length(meter / scalar)
  public operator final fun unaryMinus(): Length = Length(-meter)


  fun inMillimeter(): Double = meter / LengthUnit.MILLIMETER.factor
  fun inCentimeter(): Double = meter / LengthUnit.CENTIMETER.factor
  fun inMeter(): Double = meter
  fun inKilometer(): Double = meter / LengthUnit.KILOMETER.factor
  fun inInch(): Double = meter / LengthUnit.INCH.factor
  fun inFeet(): Double = meter / LengthUnit.FEET.factor
  fun inYard(): Double = meter / LengthUnit.YARD.factor
  fun inMile(): Double = meter / LengthUnit.MILE.factor

  override fun compareTo(other: Length): Int = meter.compareTo(other.meter)
  override fun toString(): String = "%.1f %s".format(meter, LengthUnit.METER.factor)

  companion object {
    final val ZERO = Length(0.0)
    final val MIN_VALUE = Length(Double.MIN_VALUE)
    final val MAX_VALUE = Length(Double.MAX_VALUE)
    final val POSITIVE_INF = Length(Double.POSITIVE_INFINITY)
    final val NEGATIVE_INF = Length(Double.NEGATIVE_INFINITY)
    final val NaN = Length(Double.NaN)

    @JvmStatic
    fun of(length: Double = 0.0, unit: LengthUnit = LengthUnit.METER): Length =
        Length(length * unit.factor)

    @JvmStatic
    fun parse(str: String): Length {
      if (str.isBlank()) {
        return ZERO
      }
      try {
        val (length, unit) = str.split(" ", limit = 2)
        return of(length.toDouble(), LengthUnit.parse(unit))
      } catch(e: Exception) {
        throw NumberFormatException("Invalid Length string. str=$str")
      }
    }
  }

}