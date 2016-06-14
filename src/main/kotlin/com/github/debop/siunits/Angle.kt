@file:JvmName("angles")

package com.github.debop.siunits

import java.io.Serializable


const val DEGREE_FORMAT = "%.1f deg"
const val RADIAN_FORMAT = "%.1f rad"


fun Double.toRadian(): Double = this * Math.PI / 180.0
fun Double.toDegree(): Double = this * 180.0 / Math.PI

/**
 * 각도 단위 종류
 */
enum class AngleUnit(val unitName: String) {
  Degree("deg"),
  Radian("rad");

  companion object {

    @JvmStatic fun parse(str: String): AngleUnit {
      val lower = str.toLowerCase()
      return AngleUnit.values().first { it.unitName == lower }
             ?: throw NumberFormatException("Unknwon AngleUnit format. str=$str")
    }
  }
}

/**
 * 각도를 나타내는 클래스입니다
 */
data class Angle(val degree: Double) : Comparable<Angle>, Serializable {

  fun inDegree(): Double = this.degree
  fun inRadian(): Double = this.degree.toRadian()

  fun in360() = Angle(degree % 360.0)

  operator fun plus(angle: Angle): Angle = Angle(degree + angle.degree)
  operator fun plus(scalar: Double): Angle = Angle(degree + scalar)
  operator fun minus(angle: Angle): Angle = Angle(degree - angle.degree)
  operator fun minus(scalar: Double): Angle = Angle(degree - scalar)

  operator fun times(scalar: Double): Angle = Angle(degree * scalar)
  operator fun div(scalar: Double): Angle = Angle(degree / scalar)

  operator fun unaryMinus(): Angle = Angle(-degree)

  fun toHuman(unit: AngleUnit): String = when (unit) {
    AngleUnit.Degree -> "%.1f deg".format(inDegree())
    AngleUnit.Radian -> "%.1f red".format(inRadian())
  }

  override fun compareTo(other: Angle): Int = degree.compareTo(other.degree)
  override fun toString(): String = "%.1f deg".format(degree)

  companion object {

    val ZERO = Angle(0.0)
    val DEGREE_ZERO = ZERO
    val DEGREE_90 = Angle(90.0)
    val DEGREE_180 = Angle(180.0)
    val DEGREE_270 = Angle(270.0)
    val DEGREE_360 = Angle(360.0)

    val POSITIVE_INF = Angle(Double.POSITIVE_INFINITY)
    val NEGATIVE_INF = Angle(Double.NEGATIVE_INFINITY)
    val NaN = Angle(Double.NaN)


    @JvmStatic fun of(angle: Double, unit: AngleUnit = AngleUnit.Degree): Angle = when (unit) {
      AngleUnit.Degree -> degree(angle)
      AngleUnit.Radian -> radian(angle)
      else             -> throw IllegalArgumentException("Unknown Angle unit. unit=$unit")
    }

    @JvmStatic fun degree(angle: Double) = Angle(angle)
    @JvmStatic fun radian(angle: Double) = Angle(angle.toDegree())

    @JvmStatic fun parse(str: String): Angle {
      if (str.isBlank())
        return ZERO

      val (length, unit) = str.split(" ", limit = 2)

      try {
        return of(length.toDouble(), AngleUnit.parse(unit))
      } catch(e: Exception) {
        throw NumberFormatException("Invalid Angle string. str=$str")
      }
    }
  }
}
