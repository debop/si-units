@file:JvmName("pressures")

package com.github.debop.siunits

import java.io.Serializable


const val ATM_IN_PASCAL = 101325.0
const val HETOPASCAL_IN_PASCAL = 100.0
const val KILOPASCAL_IN_PASCAL = 1000.0
const val MEGAPASCAL_IN_PASCAL = 1000.0 * 1000.0

const val BAR_IN_PASCAL = 1.0e5
const val DECIBAR_IN_PASCAL = 1.0e4
const val MILLIBAR_IN_PASCAL = 1.0e2

const val PSI_IN_PASCAL = 6895.757
const val TORR_IN_PASCAL = 1.0 / 122.322
const val MMHG_IN_PASCAL = 1.0 / 122.322

fun Double.toAtm(): Pressure = Pressure(this * ATM_IN_PASCAL)
fun Double.toPascal(): Pressure = Pressure(this)
fun Double.toHectoPascal(): Pressure = Pressure(this * HETOPASCAL_IN_PASCAL)


data class Pressure(val pascal: Double = 0.0) : Comparable<Pressure>, Serializable {

  fun inAtm(): Double = pascal / ATM_IN_PASCAL
  fun inPascal(): Double = pascal
  fun inHectoPascal(): Double = pascal / HETOPASCAL_IN_PASCAL
  fun inKiloPascal(): Double = pascal / KILOPASCAL_IN_PASCAL
  fun inMegaPascal(): Double = pascal / MEGAPASCAL_IN_PASCAL

  fun inBar(): Double = pascal / BAR_IN_PASCAL
  fun inDeciBar(): Double = pascal / DECIBAR_IN_PASCAL
  fun inMilliBar(): Double = pascal / MILLIMETER_IN_METER

  fun inPsi(): Double = pascal / PSI_IN_PASCAL
  fun inTorr(): Double = pascal / TORR_IN_PASCAL
  fun inMmHg(): Double = pascal / MMHG_IN_PASCAL

  operator fun plus(other: Pressure): Pressure = Pressure(pascal + other.pascal)
  operator fun minus(other: Pressure): Pressure = Pressure(pascal - other.pascal)
  operator fun times(scalar: Double): Pressure = Pressure(pascal * scalar)
  operator fun div(scalar: Double): Pressure = Pressure(pascal / scalar)

  operator fun unaryMinus(): Pressure = Pressure(-pascal)

  fun toHuman(): String = TODO()

  override fun compareTo(other: Pressure): Int = pascal.compareTo(other.pascal)
  override fun toString(): String = "%.1f pascal".format(pascal)

  companion object {

    val ZERO = Pressure(0.0)
    val MIN_VALUE = Pressure(Double.MIN_VALUE)
    val MAX_VALUE = Pressure(Double.MAX_VALUE)
    val POSITIVE_INF = Pressure(Double.POSITIVE_INFINITY)
    val NEGATIVE_INF = Pressure(Double.NEGATIVE_INFINITY)


    @JvmStatic fun of(value: Double, unit: PressureUnit): Pressure {
      TODO()
    }

    @JvmStatic fun parse(str: String): Pressure {
      TODO()
    }
  }

}