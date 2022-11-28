package com.sook.backend.common.exception;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sook.backend.AbstractSoOkTest;
import com.sook.backend.common.dto.ExceptionDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GlobalExceptionHandlerTest extends AbstractSoOkTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DisplayName("GlobalExceptionHandler 테스트: UserNotFoundException 발생시, Advice 가 정상적으로 동작하는지 확인")
    public void testGlobalExceptionHandlerWithUserNotFoundException() throws Exception {
        //given
        String url = "/api/user/";
        String email = "non-exist@email.com";

        //when
        ResponseEntity<ExceptionDto> response = restTemplate.getForEntity(url + email, ExceptionDto.class);
        ExceptionDto exceptionDto = Optional.ofNullable(response.getBody()).orElseThrow();

        //then
        assertEquals(response.getStatusCodeValue(), 404);
        assertEquals(exceptionDto.status(), 404);
        assertEquals(exceptionDto.error(), HttpStatus.NOT_FOUND.toString());
    }
}
