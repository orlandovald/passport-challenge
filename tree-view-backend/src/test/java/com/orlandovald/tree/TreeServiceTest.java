package com.orlandovald.tree;

import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@MicronautTest
class TreeServiceTest {

    @Inject
    TreeRepository repo;
    TreeService service;

    public TreeServiceTest() {
        service = new TreeService(repo);
    }

    @ParameterizedTest
    @CsvSource({"-1,0,1,10", "0,0,1,10", "1,1,1,10", "15,15,0,10", "8,8,45,423", "13,13,462542,1246573521"})
    void generateRandomNumbers(int count, int expected, int min, int max) {
assertThat(service.generateRandomNumbers(count,min,max))
        .hasSize(expected)
        .allMatch(num -> num >= min && num <= max);
    }

    @MockBean(TreeRepository.class)
    TreeRepository mathService() {
        return mock(TreeRepository.class);
    }
}