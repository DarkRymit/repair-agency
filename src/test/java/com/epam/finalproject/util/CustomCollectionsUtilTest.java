package com.epam.finalproject.util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomCollectionsUtilTest {

    @Test
    void toSingletonSet() {
        Set<String> set = Set.of("test", "test1");
        String result = set.stream().filter(e -> e.equals("test")).collect(CustomCollectionsUtil.toSingleton());
        assertEquals("test", result);
    }

    @Test
    void toSingletonSetInvalid() {
        Set<String> set = Set.of("test", "test1");
        assertThrows(IllegalStateException.class,() -> {set.stream().collect(CustomCollectionsUtil.toSingleton());});
    }
    @Test
    void toSingletonList() {
        List<String> list = List.of("test", "test");
        String result = list.stream().distinct().collect(CustomCollectionsUtil.toSingleton());
        assertEquals("test", result);
    }

    @Test
    void toSingletonListInvalid() {
        List<String> list = List.of("test", "test");
        assertThrows(IllegalStateException.class,() -> {list.stream().collect(CustomCollectionsUtil.toSingleton());});
    }
}