package com.wongnai.interview.wamup;

import java.util.InputMismatchException;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DigitalRootTest {
	private DigitalRoot digitalRoot;

	@Before
	public void setUp() {
		digitalRoot = new DigitalRoot();
	}

	@Test
	public void testDigitalRoot() {
		//You are able to add more unit test to make it coverage.
		Assert.assertThat(digitalRoot.check(9), Matchers.equalTo(9L));
		Assert.assertThat(digitalRoot.check(934623324L), Matchers.equalTo(9L));
		Assert.assertThat(digitalRoot.check(1235889343324L), Matchers.equalTo(1L));
		Assert.assertThat(digitalRoot.check(493193L), Matchers.equalTo(2L));
	}


	@Test(expected = InputMismatchException.class)
	public void testInvalidInput() {
		digitalRoot.check(-87625L);
	}
}
