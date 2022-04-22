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
	Payments testPayments;
	Payment testPayment;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	public void setUp() throws Exception {
		testPayment = new Payment("1234567891234567", "03/20", 1000, "USD", 456);
	}

	// check card validation works
	@Test
	void testValidation(){
		assertEquals(testPayment.performValidation(), true);
	}

	// check card number masking works
	@Test
	void testMasking(){
		testPayment.maskCardNumber();
		assertEquals(testPayment.getCardNumber().substring(0,12),"############");
	}

	// test whether you can submitPayment() to the acquiring bank
	@Test
	public void testSubmitPayment() throws Exception {
		boolean result = testPayments.submitPayment(testPayment);
		assertEquals(result, true);
	}

}
