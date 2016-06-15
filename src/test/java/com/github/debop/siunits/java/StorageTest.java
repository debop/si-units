package com.github.debop.siunits.java;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.debop.siunits.storages.*;
import static org.assertj.core.api.Assertions.assertThat;

public class StorageTest extends AbstractUnitTest {


  private Logger log = LoggerFactory.getLogger(MassTest.class);

  @Test
  public void convertMassUnit() {
    assertThat(toBytes(1).inBytes()).isEqualTo(1);
    assertThat(toKBytes(1).inKBytes()).isEqualTo(1);
    assertThat(toMBytes(1).inMBytes()).isEqualTo(1);
    assertThat(toGBytes(1).inGBytes()).isEqualTo(1);

    assertThat(toBytes(1 << 10).inKBytes()).isEqualTo(1);
    assertThat(toBytes(1.0).inKBytes()).isEqualTo(0);
    assertThat(toKBytes(1).inBytes()).isEqualTo(1 << 10);
    assertThat(toMBytes(1).inBytes()).isEqualTo(1 << 20);
  }

}
