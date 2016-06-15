@file:JvmName("masses")

package com.github.debop.siunits

import java.io.Serializable

const val MILLIGRAM_IN_GRAM: Double = 1.0 / 1000.0
const val GRAM_IN_GRAM: Double = 1.0
const val KILOGRAM_IN_GRAM: Double = 1000.0
const val TON_IN_GRAM: Double = 1000.0 * 1000.0

fun Int.milligram(): Mass = Mass.of(this.toDouble(), MassUnit.MILLIGRAM)
fun Int.gram(): Mass = Mass.of(this.toDouble(), MassUnit.GRAM)
fun Int.kilogram(): Mass = Mass.of(this.toDouble(), MassUnit.KILOGRAM)
fun Int.ton(): Mass = Mass.of(this.toDouble(), MassUnit.TON)

fun Double.milligram(): Mass = Mass.of(this, MassUnit.MILLIGRAM)
fun Double.gram(): Mass = Mass.of(this, MassUnit.GRAM)
fun Double.kilogram(): Mass = Mass.of(this, MassUnit.KILOGRAM)
fun Double.ton(): Mass = Mass.of(this, MassUnit.TON)

/**
 * 질량/무게 (Mass/Weight) 단위를 표현합니다.
 */
enum class MassUnit(val unitName: String, val factor: Double) {

  MILLIGRAM("mg", MILLIGRAM_IN_GRAM),
  GRAM("g", GRAM_IN_GRAM),
  KILOGRAM("kg", KILOGRAM_IN_GRAM),
  TON("ton", TON_IN_GRAM);

  companion object {

    @JvmStatic
    fun parse(unitStr: String): MassUnit {
      var lower = unitStr.toLowerCase()
      if (lower.endsWith("s")) {
        lower = lower.dropLast(1)
      }
      return MassUnit.values().find { it.unitName == lower }
          ?: throw NumberFormatException("Unknown Mess unit. unit=$unitStr")
    }
  }
}

/**
 * 질량/무게 (Mass/Weight) 를 표현합니다.
 *
 * @author debop sunghyouk.bae@gmail.com
 */
data class Mass(val gram: Double = 0.0) : Comparable<Mass>, Serializable {

  fun inMilligram(): Double = gram / MILLIGRAM_IN_GRAM
  fun inGram(): Double = gram
  fun inKilogram(): Double = gram / KILOGRAM_IN_GRAM
  fun inTon(): Double = gram / TON_IN_GRAM

  fun inUnit(unit: MassUnit = MassUnit.GRAM): Double = when (unit) {
    MassUnit.MILLIGRAM -> inMilligram()
    MassUnit.GRAM      -> inGram()
    MassUnit.KILOGRAM  -> inKilogram()
    MassUnit.TON       -> inTon()
    else               -> throw UnsupportedOperationException("Unknown Mass unit. unit=$unit")
  }

  operator final fun plus(other: Mass): Mass = Mass(gram + other.gram)
  operator final fun minus(other: Mass): Mass = Mass(gram - other.gram)

  operator final fun times(scalar: Double): Mass = Mass(gram * scalar)
  operator final fun times(other: Mass): Mass = Mass(gram * other.gram)

  operator final fun div(scalar: Double): Mass = Mass(gram / scalar)
  operator final fun div(other: Mass): Mass = Mass(gram / other.gram)

  operator final fun unaryMinus(): Mass = Mass(-gram)

  fun toHuman(): String {
    var unit = MassUnit.GRAM
    var display = Math.abs(gram)

    if (display > TON_IN_GRAM) {
      display /= TON_IN_GRAM
      unit = MassUnit.TON
      return "%1.f %s".format(display * Math.signum(gram), unit.unitName)
    }

    if (display < GRAM_IN_GRAM) {
      unit = MassUnit.MILLIGRAM
      display /= MILLIGRAM_IN_GRAM
    } else if (display > KILOGRAM_IN_GRAM) {
      unit = MassUnit.KILOGRAM
      display /= KILOGRAM_IN_GRAM
    }
    return "%.1f %s".format(display * Math.signum(gram), unit.unitName)
  }

  override fun compareTo(other: Mass): Int = this.gram.compareTo(other.gram)

  companion object {

//    private val log = LoggerFactory.getLogger(MassUnit::class.java)

    val ZERO: Mass = Mass(0.0)
    val MAX_VALUE = Mass(Double.MAX_VALUE)
    val MIN_VALUE = Mass(Double.MIN_VALUE)
    val POSITIVE_INF: Mass = Mass(Double.POSITIVE_INFINITY)
    val NEGATIVE_INF: Mass = Mass(Double.NEGATIVE_INFINITY)
    val NaN: Mass = Mass(Double.NaN)

    /**
     * Static constructor
     */
    @JvmStatic
    fun of(value: Double, unit: MassUnit = MassUnit.GRAM): Mass = Mass(value * unit.factor)

    @JvmStatic
    fun parse(str: String): Mass {
      if (str.isBlank())
        return Mass.ZERO

      try {
        val (v, u) = str.trim().split(" ", limit = 2)
        return of(v.toDouble(), MassUnit.parse(u))

      } catch(e: Exception) {
        throw NumberFormatException("Invalid Mass string. str=$str")
      }
    }
  }
}
