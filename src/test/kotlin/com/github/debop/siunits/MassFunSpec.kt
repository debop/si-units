package com.github.debop.siunits

import com.github.debop.siunits.java.AbstractUnitTest
import com.github.debop.siunits.java.MassTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.slf4j.LoggerFactory

class MassFunSpec {

  private val log = LoggerFactory.getLogger(MassTest::class.java)

  @Test
  fun convertMassUnit() {
    val millis = 1.0.milligram()
    val mg = millis.inMilligram()
    assertThat(mg).isEqualTo(1.0)

    assertThat(1.0.milligram().inMilligram()).isEqualTo(1.0)
    assertThat(1.0.gram().inGram()).isEqualTo(1.0)
    assertThat(1.kilogram().inKilogram()).isEqualTo(1.0)
    assertThat(1.ton().inTon()).isEqualTo(1.0)

    assertThat(1000.0.milligram().inGram()).isEqualTo(1.0)
    assertThat(1.0.milligram().inGram()).isEqualTo(1 / 1000.0)
    assertThat(1.gram().inMilligram()).isEqualTo(1000.0)
    assertThat(1.kilogram().inGram()).isEqualTo(1000.0)
  }

  @Test
  fun testToHuman() {
    assertThat(900.milligram().toHuman()).isEqualTo("900.0 mg")
    assertThat(10.5.kilogram().toHuman()).isEqualTo("10.5 kg")
    assertThat(10.56.kilogram().toHuman()).isEqualTo("10.6 kg")

    assertThat(10050.gram().toHuman()).isEqualTo("10.1 kg")
    //    assertThat(gram(Integer.MAX_VALUE).toHuman()).isEqualTo("2147.5 ton");
  }

  @Test
  fun parsing() {

    assertThat(Mass.parse("142.0 mg").inGram()).isEqualTo(142.milligram().inGram(), AbstractUnitTest.offset)
    assertThat(Mass.parse("0.1 g").inGram()).isEqualTo(0.1.gram().inGram(), AbstractUnitTest.offset)
    assertThat(Mass.parse("10000.1 g").inGram()).isEqualTo(10000.1.gram().inGram(), AbstractUnitTest.offset)
    assertThat(Mass.parse("78.4 kg").inGram()).isEqualTo(78.4.kilogram().inGram(), AbstractUnitTest.offset)
  }

//  @Test(expected = NumberFormatException.class)
//  public void parse100bottles() {
//    Length.valueOf("100.bottles");
//  }
//
//  @Test(expected = NumberFormatException.class)
//  public void parse100gram() {
//    Length.valueOf("100 gram");
//  }
//
//  @Test(expected = NumberFormatException.class)
//  public void parseMalformat() {
//    Length.valueOf("100.0.0.0.gram");
//  }

  @Test
  fun negative() {
    assertThat((-132).gram().inGram()).isEqualTo(-132.0, AbstractUnitTest.offset)
    assertThat((-2).kilogram().toHuman()).isEqualTo("-2.0 kg")
  }

  @Test
  fun sameHashCode() {
    val i = 4.kilogram()
    val j = 4.kilogram()
    val k = 4.0.kilogram()

    assertThat(i.hashCode()).isEqualTo(j.hashCode())
    assertThat(j.hashCode()).isEqualTo(k.hashCode())
  }

  @Test
  fun compare() {
    assertThat(4.1.kilogram()).isGreaterThan(3.9.kilogram())
    assertThat((-1.2).gram()).isLessThan((-0.2).gram())
    assertThat((-1.2).gram()).isGreaterThan((-2.5).gram())
  }

  @Test
  fun arithmetics() {
    assertThat(1.kilogram().plus(2.kilogram())).isEqualTo(3000.gram())
    assertThat(1.kilogram().minus(2.kilogram())).isEqualTo((-1).kilogram())
    assertThat(4.kilogram().times(2.0)).isEqualTo(8.kilogram())
    assertThat(4.kilogram().div(2.0)).isEqualTo(2.kilogram())
  }

}