package com.visa.transaction.exception;
import com.visa.transaction.mapper.AccountMapper;
import com.visa.transaction.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExceptionTestController.class)
@Import(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn404ForNotFound() throws Exception {
        mockMvc.perform(get("/test-notfound"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Resource not found"));
    }

    @Test
    void shouldReturn404ForAccountNotFound() throws Exception {
        mockMvc.perform(get("/test-account"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("ACCOUNT_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Account missing"));
    }

    @Test
    void shouldReturn404ForOperationTypeNotFound() throws Exception {
        mockMvc.perform(get("/test-operation"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("OPERATION_TYPE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Operation missing"));
    }

    @Test
    void shouldReturn400ForIllegalArgument() throws Exception {
        mockMvc.perform(get("/test-illegal"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Invalid input"));
    }

    @Test
    void shouldReturn500ForGenericException() throws Exception {
        mockMvc.perform(get("/test-generic"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value("Something went wrong"));
    }
}

@RestController
class ExceptionTestController {

    @GetMapping("/test-illegal")
    public void illegal() {
        throw new IllegalArgumentException("bad input");
    }

    @GetMapping("/test-notfound")
    public void notFound() {
        throw new NotFoundException("Resource not found");
    }

    @GetMapping("/test-account")
    public void account() {
        throw new AccountNotFoundException("Account missing");
    }

    @GetMapping("/test-operation")
    public void operation() {
        throw new OperationTypeNotFoundException("Operation missing");
    }

    @GetMapping("/test-generic")
    public void generic() {
        throw new RuntimeException("boom");
    }
}