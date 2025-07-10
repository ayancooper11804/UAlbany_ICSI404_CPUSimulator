import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

public class BitsAndWordsTest {

	@Test
	public void testSetBoolean() {
		Bit bit = new Bit();
		bit.set(true);
		assertTrue(bit.getValue());
		bit.set(false);
		assertFalse(bit.getValue());
	}
	
	@Test
	public void testToggle() {
		Bit bit = new Bit();
		assertFalse(bit.getValue());
		bit.toggle();
		assertTrue(bit.getValue());
		bit.toggle();
		assertFalse(bit.getValue());
	}
	
	@Test
	public void testSetAndClear() {
		Bit bit = new Bit();
		assertFalse(bit.getValue());
		
		bit.set();
		assertTrue(bit.getValue());
		
		bit.clear();
		assertFalse(bit.getValue());
	}
	
	@Test
	public void testGetValue() {
		Bit bit = new Bit();
		assertFalse(bit.getValue());
		bit.set(true);
		assertTrue(bit.getValue());
		bit.toggle();
		assertFalse(bit.getValue());
	}
	
	@Test
	public void testBitAnd() {
		Bit bit1 = new Bit();
		Bit bit2 = new Bit();
		assertFalse(bit1.getValue());
		assertFalse(bit2.getValue());
		
		Bit result1 = bit1.and(bit2);
		assertFalse(result1.getValue());
		
		bit1.set(true);
		Bit result2 = bit1.and(bit2);
		assertFalse(result2.getValue());
		
		bit2.set(true);
		Bit result3 = bit1.and(bit2);
		assertTrue(result3.getValue());
	}
	
	@Test
	public void testBitOr() {
		Bit bit1 = new Bit();
		Bit bit2 = new Bit();
		assertFalse(bit1.getValue());
		assertFalse(bit2.getValue());
		
		Bit result1 = bit1.or(bit2);
		assertFalse(result1.getValue());
		
		bit1.set(true);
		Bit result2 = bit1.or(bit2);
		assertTrue(result2.getValue());
		
		bit2.set(true);
		Bit result3 = bit1.or(bit2);
		assertTrue(result3.getValue());
	}
	
	@Test
	public void testBitXor() {
		Bit bit1 = new Bit();
		Bit bit2 = new Bit();
		assertFalse(bit1.getValue());
		assertFalse(bit2.getValue());
		
		Bit result1 = bit1.xor(bit2);
		assertFalse(result1.getValue());
		
		bit1.set(true);
		Bit result2 = bit1.xor(bit2);
		assertTrue(result2.getValue());
		
		bit2.set(true);
		Bit result3 = bit1.xor(bit2);
		assertFalse(result3.getValue());
	}
	
	@Test
	public void testBitNot() {
		Bit bit = new Bit();
		assertFalse(bit.getValue());
		
		Bit result1 = bit.not();
		assertTrue(result1.getValue());
		
		bit.toggle();
		Bit result2 = bit.not();
		assertFalse(result2.getValue());
	}
	
	@Test
	public void testBitToString() {
		Bit bit = new Bit();
		assertEquals("f", bit.toString());
		
		bit.toggle();
		assertEquals("t", bit.toString());
	}
	
	@Test
	public void testGetBit() {
		Word word = new Word();
		for (int i = 0; i < word.words.length; i++) {
			assertFalse(word.getBit(i).getValue());
		}
		
		word.words[3].set(true);
		assertTrue(word.getBit(3).getValue());
	}
	
	@Test
	public void testSetBit() {
		Word word = new Word();
		for (int i = 0; i < word.words.length; i++) {
			assertFalse(word.getBit(i).getValue());
		}
		
		Bit trueBit = new Bit();
		trueBit.set();
		word.setBit(3, trueBit);
		assertTrue(word.getBit(3).getValue());
		
		Bit falseBit = new Bit();
		falseBit.clear();
		word.setBit(3, falseBit);
		assertFalse(word.getBit(3).getValue());
	}
	
	@Test
	public void testWordAnd() {
		Word word1 = new Word();
		Word word2 = new Word();
		for (int i = 0; i < word1.words.length; i++) {
			assertFalse(word1.getBit(i).getValue());
		}
		for (int i = 0; i < word2.words.length; i++) {
			assertFalse(word2.getBit(i).getValue());
		}
		
		Bit trueBit1 = new Bit();
		trueBit1.set();
		word1.setBit(3, trueBit1);
		Bit trueBit2 = new Bit();
		trueBit2.set();
		word2.setBit(3, trueBit2);
		Word result1 = word1.and(word2);
		assertTrue(result1.getBit(3).getValue());
		
		Bit falseBit = new Bit();
		falseBit.clear();
		word2.setBit(3, falseBit);
		Word result2 = word1.and(word2);
		assertFalse(result2.getBit(3).getValue());
	}
	
	@Test
	public void testWordOr() {
		Word word1 = new Word();
		Word word2 = new Word();
		for (int i = 0; i < word1.words.length; i++) {
			assertFalse(word1.getBit(i).getValue());
		}
		for (int i = 0; i < word2.words.length; i++) {
			assertFalse(word2.getBit(i).getValue());
		}
		
		Bit trueBit = new Bit();
		trueBit.set();
		word2.setBit(3, trueBit);
		Word result1 = word1.or(word2);
		assertTrue(result1.getBit(3).getValue());
		
		trueBit.toggle();
		word2.setBit(3, trueBit);
		Word result2 = word1.or(word2);
		assertFalse(result2.getBit(3).getValue());
	}
	
	@Test
	public void testWordXor() {
		Word word1 = new Word();
		Word word2 = new Word();
		for (int i = 0; i < word1.words.length; i++) {
			assertFalse(word1.getBit(i).getValue());
		}
		for (int i = 0; i < word2.words.length; i++) {
			assertFalse(word2.getBit(i).getValue());
		}
		
		Bit trueBit = new Bit();
		trueBit.set();
		word2.setBit(3, trueBit);
		Word result1 = word1.xor(word2);
		assertTrue(result1.getBit(3).getValue());
		
		word1.setBit(3, trueBit);
		Word result2 = word1.xor(word2);
		assertFalse(result2.getBit(3).getValue());
		
		Bit falseBit = new Bit();
		falseBit.clear();
		word1.setBit(3, falseBit);
		word2.setBit(3, falseBit);
		Word result3 = word1.xor(word2);
		assertFalse(result3.getBit(3).getValue());
		
		word1.setBit(3, trueBit);
		Word result4 = word1.xor(word2);
		assertTrue(result4.getBit(3).getValue());
	}
	
	@Test
	public void testWordNot() {
//		Word word = new Word();
//		for (int i = 0; i < word.words.length; i++) {
//			assertFalse(word.getBit(i).getValue());
//		}
//		
//		Bit trueBit = new Bit();
//		trueBit.set();
//		word.setBit(3, trueBit);
//		Word result1 = word.not();
//		assertFalse(result1.getBit(3).getValue());
//		
//		trueBit.toggle();
//		word.setBit(3, trueBit);
//		Word result2 = word.not();
//		assertTrue(result2.getBit(3).getValue());
		
		Word notWord = new Word();
		notWord.set(5);
		System.out.println(notWord.toString());
		Word result = notWord.not();
		System.out.println(result.toString());
	}
	
	@Test
	public void testRightShift() {
		Word word = new Word();
		for (int i = 0; i < word.words.length; i++) {
			assertFalse(word.getBit(i).getValue());
		}

		Bit trueBit1 = new Bit();
		trueBit1.set();
		Bit trueBit2 = new Bit();
		trueBit2.set();
		word.setBit(3, trueBit1);
		word.setBit(5, trueBit2);
		
		Word shiftedWord = word.rightShift(2);
		
		assertFalse(shiftedWord.getBit(2).getValue());
		assertFalse(shiftedWord.getBit(3).getValue());
		assertFalse(shiftedWord.getBit(4).getValue());
		assertTrue(shiftedWord.getBit(5).getValue());
		assertFalse(shiftedWord.getBit(6).getValue());
		assertTrue(shiftedWord.getBit(7).getValue());
	}
	
	@Test
	public void testLeftShift() {
		Word word = new Word();
		for (int i = 0; i < word.words.length; i++) {
			assertFalse(word.getBit(i).getValue());
		}

		Bit trueBit1 = new Bit();
		trueBit1.set();
		Bit trueBit2 = new Bit();
		trueBit2.set();
		word.setBit(3, trueBit1);
		word.setBit(5, trueBit2);
		
		Word shiftedWord = word.leftShift(2);
		assertFalse(shiftedWord.getBit(0).getValue());
		assertTrue(shiftedWord.getBit(1).getValue());
		assertFalse(shiftedWord.getBit(2).getValue());
		assertTrue(shiftedWord.getBit(3).getValue());
		assertFalse(shiftedWord.getBit(4).getValue());
		assertFalse(shiftedWord.getBit(5).getValue());
	}
	
	@Test
	public void testGetUnsigned() {
		Word word = new Word();
		for (int i = 0; i < word.words.length; i++) {
			assertFalse(word.getBit(i).getValue());
		}

		Bit trueBit1 = new Bit();
		trueBit1.set();
		Bit trueBit2 = new Bit();
		trueBit2.set();
		word.setBit(3, trueBit1);
		word.setBit(5, trueBit2);
		
		assertEquals(40, word.getUnsigned());
	}
	
	@Test
	public void testGetSigned() {
		Word positiveWord = new Word();
		for (int i = 0; i < positiveWord.words.length; i++) {
			assertFalse(positiveWord.getBit(i).getValue());
		}
		Bit trueBit1 = new Bit();
		trueBit1.set();
		Bit trueBit2 = new Bit();
		trueBit2.set();
		positiveWord.setBit(3, trueBit1);
		positiveWord.setBit(5, trueBit2);
		assertEquals(40, positiveWord.getSigned());

		Word negativeWord = new Word();
		negativeWord.set(-5);
		assertEquals(-5, negativeWord.getSigned());
	}
	
	@Test
	public void testCopy() {
		Word original = new Word();
		Word copy = new Word();
		
		original.getBit(2).set();
		original.getBit(5).set();
		original.getBit(10).set();
		copy.copy(original);
		
		for (int i = 0; i < original.words.length; i++) {
			assertEquals(original.getBit(i).getValue(), copy.getBit(i).getValue());
		}
	}
	
	@Test
	public void testWordSet() {
		Word word = new Word();
		word.set(25);
		assertEquals(25, word.getSigned());
		
		word.set(-15);
		assertEquals(-15, word.getSigned());
		
		word.set(0);
		assertEquals(0, word.getSigned());
	}
	
	@Test
	public void testWordToString() {
		Word word = new Word();
		word.set(25);
		assertEquals("t, f, f, t, t, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f, f",
				word.toString());
		
		word.set(-15);
		assertEquals("t, f, f, f, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t",
				word.toString());
	}
	
}
