package org.test.query;

import java.util.UUID;
import org.junit.Test;
import com.fasterxml.uuid.Generators;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UUIDTests {

  @Test
  public void test() {
    UUID uuid = Generators.timeBasedGenerator().generate();

    log.info("{}", uuid);

    for (int i = 0; i < 100; i++) {
      uuid = Generators.timeBasedGenerator().generate();

      log.info("{}", uuid);
    }
  }
}
