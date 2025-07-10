import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

public class MainMemoryTest {

	@Test
	public void testIncrement() {
		Word positiveWord = new Word();
		positiveWord.set(5);
		positiveWord.increment();
		assertEquals(6, positiveWord.getUnsigned());
		
		Word zeroWord = new Word();
		zeroWord.set(0);
		zeroWord.increment();
		assertEquals(1, zeroWord.getUnsigned());
		
		Word negativeWord = new Word();
		negativeWord.set(-3);
		negativeWord.increment();
		assertEquals(-2, negativeWord.getSigned());
	}
	
	@Test
	public void testProcessSetUp() {
		Processor process = new Processor();
		assertEquals(0, process.PC.getSigned());
		assertEquals(1024, process.SP.getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testFetch() throws Exception {
		Processor process = new Processor();
		MainMemory memory = new MainMemory();
		
		String[] data = {
				"01101010101010101010010101010101",
				"10011011011010101010100101010101"
		};
		memory.load(data);
		process.fetch();
		assertEquals(memory.memory[0].getSigned(), process.currentInstruction.getSigned());
		assertEquals(1, process.PC.getSigned());
		
		process.fetch();
		assertEquals(memory.memory[1].getSigned(), process.currentInstruction.getSigned());
		assertEquals(2, process.PC.getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testLoadRead() throws Exception {
		MainMemory memory = new MainMemory();
		
		String[] data = {
				"01101010101010101010010101010101",
				"01101010101010101010010101010101"
		};
		memory.load(data);
		
		Word address = new Word();
		address.set(0);
		assertEquals("f, t, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, t, f, f, t, f, t, f, t, f, t, f, t, f, t", 
				memory.read(address).toString());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testLoadReadWrite() throws Exception {
		MainMemory memory = new MainMemory();
		
		String[] data = {
				"01101010101010101010010101010101",
				"01101010101010101010010101010101"
		};
		memory.load(data);
		
		Word address = new Word();
		Word value = new Word();
		
		address.set(0);
		value.set(42);
		memory.write(address, value);
		assertEquals(42, memory.read(address).getUnsigned());
		
		address.set(10);
		value.set(99);
		memory.write(address, value);
		assertEquals(99, memory.read(address).getUnsigned());
	}
}
