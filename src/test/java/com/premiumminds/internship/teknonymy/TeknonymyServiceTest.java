package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

	/**
	 * The corresponding implementations to test.
	 *
	 * If you want, you can make others :)
	 *
	 */
	public TeknonymyServiceTest() {
	};

	@Test
	public void PersonNoChildrenTest() {
		Person person = new Person("John", 'M', null, LocalDateTime.of(1046, 1, 1, 0, 0));
		String result = new TeknonymyService().getTeknonymy(person);
		String expected = "";
		assertEquals(result, expected);
	}

	@Test
	public void PersonOneChildTest() {
		Person person = new Person(
				"John",
				'M',
				new Person[] { new Person("Holy", 'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) },
				LocalDateTime.of(1046, 1, 1, 0, 0));
		String result = new TeknonymyService().getTeknonymy(person);
		String expected = "father of Holy";
		assertEquals(result, expected);
	}

	@Test
	public void BigFamilyTest() {
		Person ggc1 = new Person("Sarah", 'F', null, LocalDateTime.of(2020, 4, 2, 0, 0));
		Person gc2 = new Person("Jacob", 'M', new Person[] { ggc1 }, LocalDateTime.of(2022, 8, 15, 0, 0));
		Person ggc4 = new Person("Ethan", 'M', null, LocalDateTime.of(2023, 6, 25, 0, 0));

		Person gc3 = new Person("Olivia", 'F', new Person[] { ggc4 }, LocalDateTime.of(2021, 10, 10, 0, 0));

		Person child1 = new Person("Alice", 'F', new Person[] { gc2 }, LocalDateTime.of(1995, 3, 10, 0, 0));
		Person child2 = new Person("Bob", 'M', null, LocalDateTime.of(1998, 7, 25, 0, 0));
		Person child3 = new Person("Emily", 'F', null, LocalDateTime.of(2003, 12, 5, 0, 0));
		Person child4 = new Person("David", 'M', null, LocalDateTime.of(2006, 9, 14, 0, 0));
		Person child5 = new Person("Sophia", 'F', null, LocalDateTime.of(2010, 2, 28, 0, 0));
		Person child6 = new Person("James", 'M', new Person[] { gc3 }, LocalDateTime.of(2015, 11, 20, 0, 0));

		Person father = new Person("John", 'M', new Person[] { child1, child2, child3, child4, child5, child6 },
				LocalDateTime.of(1970, 5, 15, 0, 0));
		Person grandmother = new Person("Mary", 'F', new Person[] { father }, LocalDateTime.of(1975, 8, 20, 0, 0));

		String result = new TeknonymyService().getTeknonymy(grandmother);
		String expected = "great-great-grandmother of Sarah";
		assertEquals(result, expected);
	}

	@Test
	public void motherTest() {
		Person son = new Person("John", 'M', null,
				LocalDateTime.of(1970, 5, 15, 0, 0));
		Person mother = new Person("Mary", 'F', new Person[] { son }, LocalDateTime.of(1975, 8, 20, 0, 0));
		String result = new TeknonymyService().getTeknonymy(mother);
		String expected = "mother of John";
		assertEquals(result, expected);
	}

	@Test
	public void fatherTest() {
		Person daughter = new Person("Mary", 'F', null,
				LocalDateTime.of(1970, 5, 15, 0, 0));
		Person father = new Person("John", 'M', new Person[] { daughter }, LocalDateTime.of(1975, 8, 20, 0, 0));
		String result = new TeknonymyService().getTeknonymy(father);
		String expected = "father of Mary";
		assertEquals(result, expected);
	}

	@Test
	public void GrandfatherTest() {
		Person grandDaughter = new Person("James", 'M', null, LocalDateTime.of(2015, 11, 20, 0, 0));

		Person daughter = new Person("Mary", 'F', new Person[] { grandDaughter },
				LocalDateTime.of(1970, 5, 15, 0, 0));
		Person father = new Person("John", 'M', new Person[] { daughter }, LocalDateTime.of(1975, 8, 20, 0, 0));
		String result = new TeknonymyService().getTeknonymy(father);
		String expected = "grandfather of James";
		assertEquals(result, expected);
	}

	@Test
	public void GrandMotherTest() {
		Person grandDaughter = new Person("James", 'M', null, LocalDateTime.of(2015, 11, 20, 0, 0));

		Person daughter = new Person("Mary", 'F', new Person[] { grandDaughter },
				LocalDateTime.of(1970, 5, 15, 0, 0));
		Person mother = new Person("Rica", 'F', new Person[] { daughter }, LocalDateTime.of(1975, 8, 20, 0, 0));
		String result = new TeknonymyService().getTeknonymy(mother);
		String expected = "grandmother of James";
		assertEquals(result, expected);
	}

}
