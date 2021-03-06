@file:JvmName("angles")

package com.github.debop.siunits

import java.io.Serializable


const val DEGREE_FORMAT = "%.1f deg"
const val RADIAN_FORMAT = "%.4f rad"

fun radToDeg(rad: Double): Double = rad * 180.0 / Math.PI
fun degToRad(degree: Double): Double = degree * Math.PI / 180.0

fun Double.toRadian(): Angle = Angle(radToDeg(this))
fun Double.toDegree(): Angle = Angle(this)

/**
 * 각도 단위 종류
 */
enum class AngleUnit(val unitName: String) {

  Degree("deg"),
  Radian("rad");

  companion object {

    @JvmStatic
    fun parse(str: String): AngleUnit {
      var lower = str.toLowerCase()
      if (lower.endsWith("s"))
        lower = lower.dropLast(1)

      return AngleUnit.values().find { it.unitName == lower }
             ?: throw NumberFormatException("Unknwon AngleUnit format. str=$str")
    }
  }
}

/**
 * 각도를 나타내는 클래스입니다
 */
data class Angle(val degree: Double = 0.0) : Comparable<Angle>, Serializable {

  fun inDegree(): Double = this.degree
  fun inRadian(): Double = degToRad(this.degree)

  fun in360() = Angle(degree % 360.0)

  operator fun plus(angle: Angle): Angle = Angle(degree + angle.degree)
  operator fun plus(scalar: Double): Angle = Angle(degree + scalar)
  operator fun minus(angle: Angle): Angle = Angle(degree - angle.degree)
  operator fun minus(scalar: Double): Angle = Angle(degree - scalar)

  operator fun times(scalar: Double): Angle = Angle(degree * scalar)
  operator fun div(scalar: Double): Angle = Angle(degree / scalar)

  operator fun unaryMinus(): Angle = Angle(-degree)

  fun toHuman(unit: AngleUnit = AngleUnit.Degree): String = when (unit) {
    AngleUnit.Degree -> DEGREE_FORMAT.format(inDegree())
    AngleUnit.Radian -> RADIAN_FORMAT.format(inRadian())
  }

  override fun compareTo(other: Angle): Int = degree.compareTo(other.degree)
  override fun toString(): String = "%.4f %s".format(degree, AngleUnit.Degree.unitName)

  companion object {

    final val ZERO = Angle(0.0)
    final val DEGREE_ZERO = ZERO
    final val DEGREE_90 = Angle(90.0)
    final val DEGREE_180 = Angle(180.0)
    final val DEGREE_270 = Angle(270.0)
    final val DEGREE_360 = Angle(360.0)

    final val MAX_VALUE = Angle(Double.MAX_VALUE)
    final val MIN_VALUE = Angle(Double.MIN_VALUE)
    final val POSITIVE_INF = Angle(Double.POSITIVE_INFINITY)
    final val NEGATIVE_INF = Angle(Double.NEGATIVE_INFINITY)
    final val NaN = Angle(Double.NaN)


    @JvmStatic
    fun of(angle: Double = 0.0, unit: AngleUnit = AngleUnit.Degree): Angle = when (unit) {
      AngleUnit.Degree -> degree(angle)
      AngleUnit.Radian -> radian(angle)
      else             -> throw IllegalArgumentException("Unknown Angle unit. unit=$unit")
    }

    @JvmStatic
    fun degree(angle: Double) = Angle(angle)

    @JvmStatic
    fun radian(angle: Double) = Angle(radToDeg(angle))

    @JvmStatic
    fun parse(str: String): Angle {
      if (str.isBlank())
        return ZERO

      try {
        val (angle, unit) = str.split(" ", limit = 2)
        return of(angle.toDouble(), AngleUnit.parse(unit))
      } catch(e: Exception) {
        throw NumberFormatException("Invalid Angle string. str=$str")
      }
    }
  }
}
