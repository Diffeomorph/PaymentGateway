package com.processor.PaymentGateway;

import com.processor.PaymentGateway.Controllers.AcquiringBankController;
import com.processor.PaymentGateway.Services.Payment;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentGatewayApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	Payment mockPayment;

	@Test
	void checkValidation(){
		mockPayment = new Payment("1234567891234567", "03/20", 1000, "USD", 456, false);
		assertEquals(mockPayment.performValidation(), true);
	}

	@Test
	void checkMasking(){
		mockPayment = new Payment("1234567891234567", "03/20", 1000, "USD", 456, false);
		mockPayment.maskCardNumber();
		assertEquals(mockPayment.getCardNumber().substring(0,12),"############");
	}

	@InjectMocks
	AcquiringBankController acquiringBankController;

	@Autowired
	public MockMvc mockMvc;

	@Test
	public void testSubmitPayment() throws Exception {
		mockPayment = new Payment("1234567891234567", "03/20", 1000, "USD", 456, false);
		mockMvc.perform(post("http://localhost:8080/payments",mockPayment)).andExpect(MockMvcResultMatchers.content().string("true"));
	}


}
