package com.github.debop.siunits.java;

import com.github.debop.siunits.Mass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.debop.siunits.MassKt.*;
import static org.assertj.core.api.Assertions.assertThat;


public class MassTest extends AbstractUnitTest {

  private Logger log = LoggerFactory.getLogger(MassTest.class);

  @Test
  public void convertMassUnit() {
    Mass millis = milligram(1.0);
    Double mg = millis.inMilligram();
    assertThat(mg).isEqualTo(1.0);

    assertThat(milligram(1.0).inMilligram()).isEqualTo(1);
    assertThat(gram(1.0).inGram()).isEqualTo(1);
    assertThat(kilogram(1).inKilogram()).isEqualTo(1);
    assertThat(ton(1).inTon()).isEqualTo(1);

    assertThat(milligram(1000.0).inGram()).isEqualTo(1.0);
    assertThat(milligram(1.0).inGram()).isEqualTo(1 / 1000.0);
    assertThat(gram(1).inMilligram()).isEqualTo(1000.0);
    assertThat(kilogram(1).inGram()).isEqualTo(1000.0);
  }

  @Test
  public void testToHuman() {
    assertThat(milligram(900).toHuman()).isEqualTo("900.0 mg");
    assertThat(kilogram(10.5).toHuman()).isEqualTo("10.5 kg");
    assertThat(kilogram(10.56).toHuman()).isEqualTo("10.6 kg");

    assertThat(gram(10050).toHuman()).isEqualTo("10.1 kg");
//    assertThat(gram(Integer.MAX_VALUE).toHuman()).isEqualTo("2147.5 ton");
  }

  @Test
  public void parsing() {

    assertThat(Mass.parse("142.0 mg").inGram()).isEqualTo(milligram(142).inGram(), offset);
    assertThat(Mass.parse("0.1 g").inGram()).isEqualTo(gram(0.1).inGram(), offset);
    assertThat(Mass.parse("10000.1 g").inGram()).isEqualTo(gram(10000.1).inGram(), offset);
    assertThat(Mass.parse("78.4 kg").inGram()).isEqualTo(kilogram(78.4).inGram(), offset);
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
  public void negative() {
    assertThat(gram(-132).inGram()).isEqualTo(-132, offset);
    assertThat(kilogram(-2).toHuman()).isEqualTo("-2.0 kg");
  }

  @Test
  public void sameHashCode() {
    Mass i = kilogram(4);
    Mass j = kilogram(4);
    Mass k = kilogram(4.0);

    assertThat(i.hashCode()).isEqualTo(j.hashCode());
    assertThat(j.hashCode()).isEqualTo(k.hashCode());
  }

  @Test
  public void compare() {
    assertThat(kilogram(4.1)).isGreaterThan(kilogram(3.9));
    assertThat(gram(-1.2)).isLessThan(gram(-0.2));
    assertThat(gram(-1.2)).isGreaterThan(gram(-2.5));
  }

  @Test
  public void arithmetics() {
    assertThat(kilogram(1).plus(kilogram(2))).isEqualTo(gram(3000));
    assertThat(kilogram(1).minus(kilogram(2))).isEqualTo(kilogram(-1));
    assertThat(kilogram(4).times(2)).isEqualTo(kilogram(8));
    assertThat(kilogram(4).div(2)).isEqualTo(kilogram(2));
  }

}
