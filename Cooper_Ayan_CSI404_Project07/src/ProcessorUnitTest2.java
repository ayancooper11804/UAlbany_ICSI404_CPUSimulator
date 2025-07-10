import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

public class ProcessorUnitTest2 {

	@SuppressWarnings("static-access")
	@Test
	public void testPhipps2() throws Exception {
		String[] str = {
				"10000100000000011000000000000000",
				"10000010000000101000000000000000",
				"01001 01000 0011 100001000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(5, process.registers[2].getSigned());
		assertEquals(5, process.PC.getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testBranch0R() throws Exception {
		String[] str = {
				"10000100000000001000000000000000",
				"10000010000000000100000000000000",
				"00001001000000000000000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(8, process.registers[2].getSigned());
		assertEquals(5, process.PC.getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testBranch1R() throws Exception {
		String[] str = {
				"10000100000000110000000000000000",
				"10000010000000011000000000000000",
				"10001000000000100000000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(6, process.registers[2].getSigned());
		assertEquals(5, process.PC.getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testBranch3R() throws Exception {
		String[] str = {
				"10000100000000010000000000000000",
				"10000010000000100100000000000000",
				"11010010000010100000100010000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(9, process.registers[2].getSigned());
		assertEquals(5, process.PC.getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testCall0R() throws Exception {
		String[] str = {
				"10000100000000001000000000000000",
				"10000010000000000100000000000000",
				"00010001000000000000000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(8, process.registers[2].getSigned());
		assertEquals(5, process.PC.getSigned());
		assertEquals(3, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testCall1R() throws Exception {
		String[] str = {
				"10000100000000110000000000000000",
				"10000010000000011000000000000000",
				"10010000000000100000000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(6, process.registers[2].getSigned());
		assertEquals(5, process.PC.getSigned());
		assertEquals(3, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testCall2R() throws Exception {
		String[] str = {
				"10000100000000011000000000000000",
				"10000010000000101000000000000000",
				"01010010000011100001000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(5, process.registers[2].getSigned());
		assertEquals(5, process.PC.getSigned());
		assertEquals(3, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testCall3R() throws Exception {
		String[] str = {
				"10000100000000010000000000000000",
				"10000010000000100100000000000000",
				"11010010000010100000100010000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(9, process.registers[2].getSigned());
		assertEquals(5, process.PC.getSigned());
		assertEquals(3, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testPush1R() throws Exception {
		String[] str = {
				"10000100000000101000000000000000",
				"10011100001110100000000000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(6, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testPush2R() throws Exception {
		String[] str = {
				"10000100000000101000000000000000",
				"10000010000000011000000000000000",
				"01011010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(11, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testPush3R() throws Exception {
		String[] str = {
				"10000100000000101000000000000000",
				"10000010000000010100000000000000",
				"11011110001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(15, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testLoad0R() throws Exception {
		String[] str = {
				"10000100000000001000000000000000",
				"10000010000000000100000000000000",
				"00100001000000000000000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		process.SP.decrement();
		Word five = new Word();
		five.set(5);
		MainMemory.write(process.SP, five);
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(8, process.registers[2].getSigned());
		assertEquals(6, process.PC.getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testLoad1R() throws Exception {
		String[] str = {
				"10000100000000110000000000000000",
				"10000010000000011000000000000000",
				"10100001000000100000000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		process.SP.decrement();
		Word five = new Word();
		five.set(5);
		MainMemory.write(process.SP, five);
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(9, process.registers[2].getSigned());
		assertEquals(5, process.PC.getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testLoad2R() throws Exception {
		String[] str = {
				"10000100000000011000000000000000",
				"10000010000000101000000000000000",
				"01010010000011100001000000000000",
				"01100010000011100001000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		process.SP.decrement();
		Word five = new Word();
		five.set(5);
		MainMemory.write(process.SP, five);
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(11, process.registers[2].getSigned());
		assertEquals(6, process.PC.getSigned());
		assertEquals(3, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testLoad3R() throws Exception {
		String[] str = {
				"10000100000000010000000000000000",
				"10000010000000100100000000000000",
				"11010010000010100000100010000000",
				"11100010000010100000100010000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		process.SP.decrement();
		Word five = new Word();
		five.set(5);
		MainMemory.write(process.SP, five);
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(11, process.registers[2].getSigned());
		assertEquals(6, process.PC.getSigned());
		assertEquals(3, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testStore1R() throws Exception {
		String[] str = {
				"10000100000000001000000000000000",
				"10000010000000000100000000000000",
				"00010001000000000000000000000000",
				"10101100000000000000000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(12, process.registers[2].getSigned());
		assertEquals(6, process.PC.getSigned());
		assertEquals(3, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testStore2R() throws Exception {
		String[] str = {
				"10000100000000001000000000000000",
				"10000010000000000100000000000000",
				"00010001000000000000000000000000",
				"01101010000000000000000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(12, process.registers[2].getSigned());
		assertEquals(6, process.PC.getSigned());
		assertEquals(3, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testStore3R() throws Exception {
		String[] str = {
				"10000100000000001000000000000000",
				"10000010000000000100000000000000",
				"00010001000000000000000000000000",
				"11101100000000000000000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(12, process.registers[2].getSigned());
		assertEquals(6, process.PC.getSigned());
		assertEquals(3, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testPop1R() throws Exception {
		String[] str = {
				"10000100000000001000000000000000",
				"10000010000000000100000000000000",
				"00010001000000000000000000000000",
				"10110100000000000000000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(12, process.registers[2].getSigned());
		assertEquals(6, process.PC.getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testPop2R() throws Exception {
		String[] str = {
				"10000100000000001000000000000000",
				"10000010000000000100000000000000",
				"00010001000000000000000000000000",
				"01110100000000000000000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(12, process.registers[2].getSigned());
		assertEquals(6, process.PC.getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testPop3R() throws Exception {
		String[] str = {
				"10000100000000001000000000000000",
				"10000010000000000100000000000000",
				"00010001000000000000000000000000",
				"11110100000000000000000000000000",
				"11000010001110100000100000000000",
				"00000000000000000000000000000000"
		};
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(str);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(12, process.registers[2].getSigned());
		assertEquals(6, process.PC.getSigned());
	}
	
}
