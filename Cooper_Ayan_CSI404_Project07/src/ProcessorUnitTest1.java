import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;


public class ProcessorUnitTest1 {
	
	@SuppressWarnings("static-access")
	@Test
	public void testPhipps() throws Exception {
		String[] str = {
				"10000100000000101000000000000000",
				"11000010001110100001000000000000",
				"01000010001110010000000000000000",
				"11000110001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(25, process.registers[3].getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testMultiply() throws Exception {
		String[] str = {
				"10000100000000001000000000000000",
				"11000010000111100001000000000000",
				"01000010000111010000000000000000",
				"11000110000111100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(1024, process.registers[3].getSigned());
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testSubtract() throws Exception {
		String[] str = {
				"10000100000000000100000000000000",
				"10000010000000011000000000000000",
				"11000110001111010001000000000000",
				"00000000000000000000000000000000"		
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(2, process.registers[3].getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testAnd() throws Exception {
		String[] str = {
				"10000100000000110000000000000000",
				"10000010000000010000000000000000",
				"11000110001000100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(2, process.registers[3].getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testOr() throws Exception {
		String[] str = {
				"10000100000000101000000000000000",
				"10000010000000011000000000000000",
				"11000110001001100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(7, process.registers[3].getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testXor() throws Exception {
		String[] str = {
				"10000100000000100000000000000000",
				"10000010000000100100000000000000",
				"11000110001010100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(8, process.registers[3].getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testLeftShift() throws Exception {
		String[] str = {
				"10000100000000110000000000000000",
				"10000010000000100000000000000000",
				"11000110001100010001000000000000",
				"00000000000000000000000000000000"		
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(1, process.registers[3].getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testRightShift() throws Exception {
		String[] str = {
				"10000100000000101000000000000000",
				"10000010000000100000000000000000",
				"11000110001101010001000000000000",
				"00000000000000000000000000000000"		
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(10, process.registers[3].getSigned());
	}
	
	@Test
	public void testRegister0() throws Exception {
		Word nonZero = new Word();
		nonZero.set(10);
		Processor process = new Processor();
		process.store(nonZero);
		Word valueInRegister0 = process.registers[0];
		assertEquals(0, valueInRegister0.getUnsigned());
	}
	
	@Test
	public void getNumber() {
		Word number = new Word();
		number.set(18);
		System.out.println(number.toString());
	}

}
