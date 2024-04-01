package com.numberone.backend;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = CoreTestConfiguration.class)
@Import({CoreTestConfiguration.class})
public abstract class CoreTestBase {
}
