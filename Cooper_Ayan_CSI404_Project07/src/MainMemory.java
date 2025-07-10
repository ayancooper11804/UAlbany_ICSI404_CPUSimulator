
public class MainMemory {
	
	// An array of Words, which will be initialized to 1024
	public static Word[] memory;
	
	// MainMemory constructor
	public MainMemory() {
		memory = new Word[1024]; // Array of 1024
		for (int i = 0; i < memory.length; i++) {
			memory[i] = new Word(); // Initialize all 1024 Words
		}
	}

	// Returns a new Word with the same value as the indexed memory
	public static Word read(Word address) throws Exception {
		// Ensure that memory is constructed
		if (memory == null || address == null) {
			throw new Exception("Memory and/or address is null");
		}
		
		// Check if the address is within the valid range
		int index = (int) address.getUnsigned();
		if (index < 0 || index >= memory.length) {
			throw new Exception("Invalid memory address " + index);
		}
		
		// Create and return a new Word with the same value as the indexed memory
		Word result = new Word();
		result.copy(memory[index]);
		return result;
	}
	
	// Alters the indexed memory to have the same value as the Word passed in
	public static void write(Word address, Word value) throws Exception {
		// Ensure that memory is constructed
		if (memory == null) {
			throw new Exception("Memory is null");
		}
		
		if (address == null) {
			throw new Exception("Address is null");
		}
		
		// Check if the address is within the valid range
		int index = (int) address.getUnsigned();
		if (index < 0 || index >= memory.length) {
			throw new Exception("Invalid memory address " + index);
		}
		
		// Update the value at the specified memory address
		memory[index].copy(value);
	}
	
	// Loops over arrays, converts 0's to 1's, populates memory
	public static void load(String[] data) throws Exception {
		// Ensure that memory is constructed
		if (memory == null || data == null) {
			throw new Exception("Memory and/or data is null");
		}
		
		// Iterate over the array of Strings
		for (int i = 0; i < data.length && i < memory.length; i++) {
			// Ensure that each String has a length of 32
			if (data[i].length() != 32) {
				throw new Exception("Invalid String length at index " + i);
			}
			
			// Iterate over the characters in the String
			for (int j = 0; j < data[i].length(); j++) {
				Bit value = new Bit();
				value.set(data[i].charAt(j) == '1');
				memory[i].setBit(j, value);
			}
		}
	}
	
}
