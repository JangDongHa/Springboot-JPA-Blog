package com.dong.newBlog.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AbsTest {
	@Test
	public void testHash() {
		String pass = new BCryptPasswordEncoder().encode("1234");
		String pass2 = new BCryptPasswordEncoder().encode("1234");
		System.out.println("Encode 1234 : " + pass);
		System.out.println("Encode another 1234 : " + pass2);
		if (pass.equals(pass2))
			System.out.println("true");
		else
			System.out.println("false");
	}
}
