package com.processor.PaymentGateway;

import com.processor.PaymentGateway.Controllers.AcquiringBankController;
import com.processor.PaymentGateway.Services.Payment;
import com.processor.PaymentGateway.Services.Payments;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PaymentGatewayApplicationTests {

	@Test
	void contextLoads() {
	}

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

	@Test
	public void testSubmitPayment() throws Exception {
		mockPayment = new Payment("1234567891234567", "03/20", 1000, "USD", 456, false);
		Payments payments = new Payments();
		boolean result = payments.submitPayment(mockPayment);
		assertEquals(result, true);
	}


}
