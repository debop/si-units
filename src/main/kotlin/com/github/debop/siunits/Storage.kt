@file:JvmName("storages")

package com.github.debop.siunits

import java.io.Serializable


fun Long.bytes(): Storage = Storage(this)
fun Long.kilobytes(): Storage = Storage.of(this.toDouble(), StorageUnit.KILO_BYTE)
fun Long.megabytes(): Storage = Storage.of(this.toDouble(), StorageUnit.MEGA_BYTE)
fun Long.gigabytes(): Storage = Storage.of(this.toDouble(), StorageUnit.GIGA_BYTE)
fun Long.terabytes(): Storage = Storage.of(this.toDouble(), StorageUnit.TERA_BYTE)
fun Long.petabytes(): Storage = Storage.of(this.toDouble(), StorageUnit.PETA_BYTE)
fun Long.exabytes(): Storage = Storage.of(this.toDouble(), StorageUnit.EXA_BYTE)

fun Double.bytes(): Storage = Storage(this.toLong())
fun Double.kilobytes(): Storage = Storage.of(this, StorageUnit.KILO_BYTE)
fun Double.megabytes(): Storage = Storage.of(this, StorageUnit.MEGA_BYTE)
fun Double.gigabytes(): Storage = Storage.of(this, StorageUnit.GIGA_BYTE)
fun Double.terabytes(): Storage = Storage.of(this, StorageUnit.TERA_BYTE)
fun Double.petabytes(): Storage = Storage.of(this, StorageUnit.PETA_BYTE)
fun Double.exabytes(): Storage = Storage.of(this, StorageUnit.EXA_BYTE)

const val BYTE_FACTOR: Long = 1L
const val KILO_FACTOR: Long = 1L shl 10
const val MEGA_FACTOR: Long = 1L shl 20
const val GIGA_FACTOR: Long = 1L shl 30
const val TERA_FACTOR: Long = 1L shl 40
const val PETA_FACTOR: Long = 1L shl 50
const val EXA_FACTOR: Long = 1L shl 60

/**
 * 저장 단위 (Bytes) 종류
 */
enum class StorageUnit(val unitName: String, val factor: Long) {

  BYTE("B", 1),
  KILO_BYTE("KB", KILO_FACTOR),
  MEGA_BYTE("MB", MEGA_FACTOR),
  GIGA_BYTE("GB", GIGA_FACTOR),
  TERA_BYTE("TB", TERA_FACTOR),
  PETA_BYTE("PB", PETA_FACTOR),
  EXA_BYTE("XB", EXA_FACTOR);

  companion object {

    @JvmStatic fun parse(unitStr: String): StorageUnit {
      var upper = unitStr.toUpperCase()
      if (upper.endsWith("s")) {
        upper = upper.dropLast(1)
      }
      return StorageUnit.values().find { it.unitName == upper }
             ?: throw NumberFormatException("Unknown Storage unit. unit=$unitStr")
    }
  }
}

/**
 * 저장 단위 (Bytes) 를 나타내는 클래스
 */
data class Storage(val bytes: Long = 0) : Comparable<Storage>, Serializable {

  operator fun plus(that: Storage): Storage = Storage(bytes + that.bytes)
  operator fun plus(scala: Long): Storage = Storage(bytes + scala)
  operator fun minus(that: Storage): Storage = Storage(bytes - that.bytes)
  operator fun minus(scala: Long): Storage = Storage(bytes - scala)

  operator fun times(that: Storage): Storage = Storage(bytes * that.bytes)
  operator fun times(scala: Long): Storage = Storage(bytes * scala)
  operator fun times(scala: Int): Storage = Storage(bytes * scala)
  operator fun times(scala: Float): Storage = Storage((bytes * scala).toLong())
  operator fun times(scala: Double): Storage = Storage((bytes * scala).toLong())

  operator fun div(that: Storage): Storage = Storage(bytes / that.bytes)
  operator fun div(scala: Long): Storage = Storage(bytes / scala)
  operator fun div(scala: Int): Storage = Storage(bytes / scala)
  operator fun div(scala: Float): Storage = Storage((bytes / scala).toLong())
  operator fun div(scala: Double): Storage = Storage((bytes / scala).toLong())

  operator fun unaryMinus(): Storage = Storage(-bytes)

  fun inBytes(): Long = bytes
  fun inKiloBytes(): Long = bytes / KILO_FACTOR
  fun inMegaBytes(): Long = bytes / MEGA_FACTOR
  fun inGigaBytes(): Long = bytes / GIGA_FACTOR
  fun inTeraBytes(): Long = bytes / TERA_FACTOR
  fun inPetaBytes(): Long = bytes / PETA_FACTOR
  fun inExaBytes(): Long = bytes / EXA_FACTOR

  override fun compareTo(other: Storage): Int = bytes.compareTo(other.bytes)
  override fun toString(): String = "$bytes.${StorageUnit.BYTE.unitName}"

  fun toHuman(): String {
    var display = Math.abs(bytes)
    var order = 0

    while (display > 1126.0) {
      order++
      display /= StorageUnit.KILO_BYTE.factor
    }

    return "%.1f %s".format(Math.signum(bytes.toDouble()) * display, StorageUnit.values()[order])
  }

  companion object {

    val ZERO = Storage(0L)
    val MAX_VALUE = Storage(Long.MAX_VALUE)
    val MIN_VALUE = Storage(Long.MIN_VALUE)

    @JvmStatic
    fun of(value: Double, unit: StorageUnit = StorageUnit.BYTE): Storage =
        Storage((value * unit.factor).toLong())

    @JvmStatic
    fun parse(str: String): Storage {
      if (str.isBlank())
        return Storage.ZERO

      try {
        var (v, u) = str.split(".", limit = 2)
        if (v.isNullOrBlank()) v = ""
        if (u.startsWith(".")) u = u.drop(1)

        return of(v.toDouble(), StorageUnit.parse(u))

      } catch(e: Exception) {
        throw NumberFormatException("Invalid Storage string. str=$str")
      }
    }
  }
}
