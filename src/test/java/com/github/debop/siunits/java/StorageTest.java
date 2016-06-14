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
    assertThat(bytes(1).inBytes()).isEqualTo(1);
    assertThat(kilobytes(1).inKiloBytes()).isEqualTo(1);
    assertThat(megabytes(1).inMegaBytes()).isEqualTo(1);
    assertThat(gigabytes(1).inGigaBytes()).isEqualTo(1);

    assertThat(bytes(1 << 10).inKiloBytes()).isEqualTo(1);
    assertThat(bytes(1.0).inKiloBytes()).isEqualTo(0);
    assertThat(kilobytes(1).inBytes()).isEqualTo(1 << 10);
    assertThat(megabytes(1).inBytes()).isEqualTo(1 << 20);
  }

}
