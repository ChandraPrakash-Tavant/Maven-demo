package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void greetReturnsMessage() {
        assertEquals("Hello from Jenkins CI pipeline v3!", App.greet());
    }
}
