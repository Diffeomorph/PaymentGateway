package com.processor.PaymentGateway;

import com.processor.PaymentGateway.Services.Payment;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
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



}
