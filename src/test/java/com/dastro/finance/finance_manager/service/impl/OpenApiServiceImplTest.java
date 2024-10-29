package com.dastro.finance.finance_manager.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dastro.finance.finance_manager.service.OpenApiService;

public class OpenApiServiceImplTest {

    @InjectMocks
    private OpenApiService openApiService = new OpenApiServiceImpl();

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEncodingStringOneParam() {
        String testString = "테스트문자열";

        String ret = openApiService.encodingString(testString);

        assertEquals(ret, "%ED%85%8C%EC%8A%A4%ED%8A%B8%EB%AC%B8%EC%9E%90%EC%97%B4");
    }

    @Test
    void testEncodingStringTwoParams() {
        String testString = "테스트문자열";
        String encodeType = "UTF-8";

        String ret = openApiService.encodingString(testString, encodeType);

        assertEquals(ret, "%ED%85%8C%EC%8A%A4%ED%8A%B8%EB%AC%B8%EC%9E%90%EC%97%B4");
    }
}
