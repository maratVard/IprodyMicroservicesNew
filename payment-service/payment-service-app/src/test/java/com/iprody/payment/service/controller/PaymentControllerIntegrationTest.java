package com.iprody.payment.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.payment.service.TestJwtFactory;
import com.iprody.payment.service.AbstractPostgresIntegrationTest;
import com.iprody.payment.service.dto.PaymentDto;
import com.iprody.payment.service.persistence.entity.Payment;
import com.iprody.payment.service.persistence.entity.PaymentStatus;
import com.iprody.payment.service.persistence.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerIntegrationTest extends AbstractPostgresIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnOnlyLiquibasePayments() throws Exception {
        mockMvc.perform(get("/payments")
                        .with(TestJwtFactory.jwtWithRole("test-user","user"))
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[?(@.guid=='00000000-0000-0000-0000-000000000001')]").exists())
                .andExpect(jsonPath("$.content[?(@.guid=='00000000-0000-0000-0000-000000000002')]").exists())
                .andExpect(jsonPath("$.content[?(@.guid=='00000000-0000-0000-0000-000000000003')]").exists());
    }
    @Test
    void shouldCreatePaymentAndVerifyInDatabase() throws Exception {
        PaymentDto dto = new PaymentDto();
        dto.setAmount(new BigDecimal("123.45"));
        dto.setCurrency("EUR");
        dto.setStatus(PaymentStatus.PENDING);
        dto.setCreatedAt(Instant.now());
        dto.setUpdatedAt(Instant.now());
        String json = objectMapper.writeValueAsString(dto);
        String response = mockMvc.perform(post("/payments")
                        .with(TestJwtFactory.jwtWithRole("test-user", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.guid").exists())
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.amount").value(123.45))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PaymentDto created = objectMapper.readValue(response,
                PaymentDto.class);
        Optional<Payment> saved =
                paymentRepository.findById(created.getGuid());
        assertThat(saved).isPresent();
        assertThat(saved.get().getCurrency()).isEqualTo("EUR");
        assertThat(saved.get().getAmount()).isEqualByComparingTo("123.45");
    }
    @Test
    void shouldReturnPaymentById() throws Exception {
        UUID existingId =
                UUID.fromString("00000000-0000-0000-0000-000000000002");
        mockMvc.perform(get("/payments/" + existingId)
                        .with(TestJwtFactory.jwtWithRole("test-user","user"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guid").value(existingId.toString()))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.amount").value(50.00));

    }
    @Test
    void shouldReturn404ForNonexistentPayment() throws Exception {
        UUID nonexistentId = UUID.randomUUID();
        mockMvc.perform(get("/payments/" + nonexistentId)
                        .with(TestJwtFactory.jwtWithRole("test-user","user"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error")
                        .value("Платёж не найден: " + nonexistentId))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.operation")
                        .value("GET /payments/" + nonexistentId))
                .andExpect(jsonPath("$.entityId")
                        .value(nonexistentId.toString()));
    }
}
