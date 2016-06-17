package com.github.debop.siunits

import io.kotlintest.specs.FunSpec

class StorageFunSpec : FunSpec() {
  init {

    test("convert Storage Unit") {

      100.toBytes().inBytes() shouldEqual 100.0
      100.toKBytes().inBytes() shouldEqual 100.0 * 1024
      100.toMBytes().inBytes() shouldEqual 100.0 * 1024 * 1024

      100.toGBytes().inKBytes() shouldEqual 100.0 * 1024 * 1024
      100.toTBytes().inMBytes() shouldEqual 100.0 * 1024 * 1024
      100.toPBytes().inGBytes() shouldEqual 100.0 * 1024 * 1024
      100.toXBytes().inTBytes() shouldEqual 100.0 * 1024 * 1024
      100.toZBytes().inPBytes() shouldEqual 100.0 * 1024 * 1024
      100.toYBytes().inXBytes() shouldEqual 100.0 * 1024 * 1024
    }
  }
}