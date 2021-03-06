@file:JvmName("volumnes")

package com.github.debop.siunits

import java.io.Serializable

fun Double.toCC(): Volumn = Volumn.of(this, VolumnUnit.CC)
fun Double.toMilliLiter(): Volumn = Volumn.of(this, VolumnUnit.MILLILETER)
fun Double.toDeciLiter(): Volumn = Volumn.of(this, VolumnUnit.DECILITER)
fun Double.toLiter(): Volumn = Volumn.of(this, VolumnUnit.LITER)
fun Double.toCentiMeter3(): Volumn = Volumn.of(this, VolumnUnit.CENTIMETER_3)
fun Double.toMeter3(): Volumn = Volumn.of(this, VolumnUnit.METER_3)
fun Double.toInch3(): Volumn = Volumn.of(this, VolumnUnit.INCH_3)
fun Double.toFeet3(): Volumn = Volumn.of(this, VolumnUnit.FEET_3)
fun Double.toYard3(): Volumn = Volumn.of(this, VolumnUnit.YARD_3)
fun Double.toGallon(): Volumn = Volumn.of(this, VolumnUnit.GALLON)
fun Double.toBarrel(): Volumn = Volumn.of(this, VolumnUnit.BARREL)
fun Double.toOnce(): Volumn = Volumn.of(this, VolumnUnit.ONCE)

enum class VolumnUnit(val unitName: String, val factor: Double) {

  CC("cc", 1.0e-9),
  MILLILETER("ml", 1.0e-3),
  DECILITER("dl", 1.0e-2),
  LITER("l", 1.0),

  CENTIMETER_3("cm^3", 1.0e-3),
  METER_3("m^3", 1.0e3),

  INCH_3("in^3", 1.0e3 / (INCH_IN_METER * INCH_IN_METER * INCH_IN_METER)),
  FEET_3("ft^3", 1.0e3 / (FEET_IN_METER * FEET_IN_METER * FEET_IN_METER)),
  YARD_3("yd^3", 1.0e3 / (YARD_IN_METER * YARD_IN_METER * YARD_IN_METER)),

  GALLON("gl", 1.0 / 0.264172),
  BARREL("barrel", 1.0 / 0.006293),
  ONCE("oz", 1.0 / 33.814022);

  companion object {

    @JvmStatic
    fun parse(unitStr: String): VolumnUnit {
      var lower = unitStr.toLowerCase()
      if (lower.endsWith("s"))
        lower = lower.dropLast(1)

      return VolumnUnit.values().find { it.unitName == lower }
             ?: throw NumberFormatException("Unknown Volumn unit. unitStr=$unitStr")
    }
  }
}

data class Volumn(val liter: Double = 0.0) : Comparable<Volumn>, Serializable {

  operator fun plus(other: Volumn): Volumn = Volumn(liter + other.liter)
  operator fun minus(other: Volumn): Volumn = Volumn(liter - other.liter)
  operator fun times(scalar: Double): Volumn = Volumn(liter * scalar)
  operator fun div(scalar: Double): Volumn = Volumn(liter / scalar)
  operator fun div(area: Area): Length = Length(inMeter3() / area.m2)
  operator fun div(length: Length): Area = Area(inMeter3() / length.meter)
  operator fun unaryMinus(): Volumn = Volumn(-liter)

  fun inCC(): Double = liter / VolumnUnit.CC.factor
  fun inMilliLiter() = liter / VolumnUnit.MILLILETER.factor
  fun inDeciLiter() = liter / VolumnUnit.DECILITER.factor
  fun inCentiMeter3() = liter / VolumnUnit.CENTIMETER_3.factor
  fun inMeter3() = liter / VolumnUnit.METER_3.factor
  fun inInch3() = liter / VolumnUnit.INCH_3.factor
  fun inFeet3() = liter / VolumnUnit.FEET_3.factor
  fun inYard3() = liter / VolumnUnit.YARD_3.factor
  fun inGallon() = liter / VolumnUnit.GALLON.factor
  fun inBarrel() = liter / VolumnUnit.BARREL.factor
  fun inOnce() = liter / VolumnUnit.ONCE.factor

  override fun compareTo(other: Volumn): Int = liter.compareTo(other.liter)

  override fun toString(): String = "%.1f %s".format(liter, VolumnUnit.LITER.unitName)

  companion object {
    final val ZERO = Volumn(0.0)
    final val MAX_VALUE = Volumn(Double.MAX_VALUE)
    final val MIN_VALUE = Volumn(Double.MIN_VALUE)
    final val POSITIVE_INF = Volumn(Double.POSITIVE_INFINITY)
    final val NEGATIVE_INF = Volumn(Double.NEGATIVE_INFINITY)
    final val NaN = Volumn(Double.NaN)

    @JvmStatic
    fun of(volumn: Double, unit: VolumnUnit = VolumnUnit.LITER): Volumn =
        Volumn(volumn * unit.factor)

    @JvmStatic
    fun parse(volStr: String): Volumn {
      if (volStr.isNullOrBlank())
        return ZERO

      try {
        val (vol, unit) = volStr.split(" ", limit = 2)
        return of(vol.toDouble(), VolumnUnit.parse(unit))
      } catch(e: Exception) {
        throw NumberFormatException("Unknown Volumn string. volStr=$volStr")
      }
    }
  }

}