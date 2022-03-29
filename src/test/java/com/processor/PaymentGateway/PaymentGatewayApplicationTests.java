package com.processor.PaymentGateway;

import com.processor.PaymentGateway.Services.Payment;
import com.processor.PaymentGateway.Services.Payments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PaymentGatewayApplicationTests {

	@Autowired
	Payments payments;
	Payment mockPayment;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	public void setUp() throws Exception {
		mockPayment = new Payment("1234567891234567", "03/20", 1000, "USD", 456, false);
	}

	@Test
	void testValidation(){
		assertEquals(mockPayment.performValidation(), true);
	}

	@Test
	void testMasking(){
		mockPayment.maskCardNumber();
		assertEquals(mockPayment.getCardNumber().substring(0,12),"############");
	}

	@Test
	public void testSubmitPayment() throws Exception {
		boolean result = payments.submitPayment(mockPayment);
		assertEquals(result, true);
	}

}
