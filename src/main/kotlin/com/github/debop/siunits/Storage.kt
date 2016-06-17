@file:JvmName("storages")

package com.github.debop.siunits

import java.io.Serializable

fun Int.toBytes(): Storage = Storage(this.toLong())
fun Int.toKBytes(): Storage = Storage.of(this.toDouble(), StorageUnit.KBYTE)
fun Int.toMBytes(): Storage = Storage.of(this.toDouble(), StorageUnit.MBYTE)
fun Int.toGBytes(): Storage = Storage.of(this.toDouble(), StorageUnit.GBYTE)
fun Int.toTBytes(): Storage = Storage.of(this.toDouble(), StorageUnit.TBYTE)
fun Int.toPBytes(): Storage = Storage.of(this.toDouble(), StorageUnit.PBYTE)
fun Int.toXBytes(): Storage = Storage.of(this.toDouble(), StorageUnit.XBYTE)


fun Long.toBytes(): Storage = Storage(this)
fun Long.toKBytes(): Storage = Storage.of(this.toDouble(), StorageUnit.KBYTE)
fun Long.toMBytes(): Storage = Storage.of(this.toDouble(), StorageUnit.MBYTE)
fun Long.toGBytes(): Storage = Storage.of(this.toDouble(), StorageUnit.GBYTE)
fun Long.toTBytes(): Storage = Storage.of(this.toDouble(), StorageUnit.TBYTE)
fun Long.toPBytes(): Storage = Storage.of(this.toDouble(), StorageUnit.PBYTE)
fun Long.toXBytes(): Storage = Storage.of(this.toDouble(), StorageUnit.XBYTE)

fun Double.toBytes(): Storage = Storage(this.toLong())
fun Double.toKBytes(): Storage = Storage.of(this, StorageUnit.KBYTE)
fun Double.toMBytes(): Storage = Storage.of(this, StorageUnit.MBYTE)
fun Double.toGBytes(): Storage = Storage.of(this, StorageUnit.GBYTE)
fun Double.toTBytes(): Storage = Storage.of(this, StorageUnit.TBYTE)
fun Double.toPBytes(): Storage = Storage.of(this, StorageUnit.PBYTE)
fun Double.toXBytes(): Storage = Storage.of(this, StorageUnit.XBYTE)

/**
 * 저장 단위 (Bytes) 종류
 */
enum class StorageUnit(val unitName: String, val factor: Long) {

  BYTE("B", 1L),
  KBYTE("KB", 1L shl 10),
  MBYTE("MB", 1L shl 20),
  GBYTE("GB", 1L shl 30),
  TBYTE("TB", 1L shl 40),
  PBYTE("PB", 1L shl 50),
  XBYTE("XB", 1L shl 60);

  companion object {

    @JvmStatic
    fun parse(unitStr: String): StorageUnit {
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
data class Storage(val bytes: Long = 0L) : Comparable<Storage>, Serializable {

  final operator fun plus(that: Storage): Storage = Storage(bytes + that.bytes)
  final operator fun plus(scalar: Long): Storage = Storage(bytes + scalar)
  final operator fun minus(that: Storage): Storage = Storage(bytes - that.bytes)
  final operator fun minus(scalar: Long): Storage = Storage(bytes - scalar)

  final operator fun times(that: Storage): Storage = Storage(bytes * that.bytes)
  final operator fun times(scalar: Long): Storage = Storage(bytes * scalar)
  final operator fun times(scalar: Int): Storage = Storage(bytes * scalar)
  final operator fun times(scalar: Float): Storage = Storage((bytes * scalar).toLong())
  final operator fun times(scalar: Double): Storage = Storage((bytes * scalar).toLong())

  final operator fun div(that: Storage): Storage = Storage(bytes / that.bytes)
  final operator fun div(scalar: Long): Storage = Storage(bytes / scalar)
  final operator fun div(scalar: Int): Storage = Storage(bytes / scalar)
  final operator fun div(scalar: Float): Storage = Storage((bytes / scalar).toLong())
  final operator fun div(scalar: Double): Storage = Storage((bytes / scalar).toLong())

  final operator fun unaryMinus(): Storage = Storage(-bytes)

  fun inBytes(): Long = bytes
  fun inKBytes(): Long = bytes / StorageUnit.KBYTE.factor
  fun inMBytes(): Long = bytes / StorageUnit.MBYTE.factor
  fun inGBytes(): Long = bytes / StorageUnit.GBYTE.factor
  fun inTBytes(): Long = bytes / StorageUnit.TBYTE.factor
  fun inPBytes(): Long = bytes / StorageUnit.PBYTE.factor
  fun inXBytes(): Long = bytes / StorageUnit.XBYTE.factor

  override fun compareTo(other: Storage): Int = bytes.compareTo(other.bytes)
  override fun toString(): String = "$bytes ${StorageUnit.BYTE.unitName}"

  fun toHuman(): String {
    var display = Math.abs(bytes)
    var order = 0

    while (display >= 1024.0) {
      order++
      display /= StorageUnit.KBYTE.factor
    }

    return if (order == 0)
      "%f %s".format(bytes, StorageUnit.BYTE.unitName)
    else
      "%.1f %s".format(Math.signum(bytes.toDouble()) * display, StorageUnit.values()[order].unitName)
  }

  companion object {

    final val ZERO = Storage(0L)
    final val MAX_VALUE = Storage(Long.MAX_VALUE)
    final val MIN_VALUE = Storage(Long.MIN_VALUE)

    @JvmStatic
    final fun of(value: Double = 0.0, unit: StorageUnit = StorageUnit.BYTE): Storage =
        Storage((value * unit.factor).toLong())

    @JvmStatic
    fun parse(storageStr: String): Storage {
      if (storageStr.isBlank())
        return Storage.ZERO

      try {
        val (bytes, unit) = storageStr.split(" ", limit = 2)
        return of(bytes.toDouble(), StorageUnit.parse(unit))

      } catch(e: Exception) {
        throw NumberFormatException("Invalid Storage string. storageStr=$storageStr")
      }
    }
  }
}
