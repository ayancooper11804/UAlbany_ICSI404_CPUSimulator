
public class Processor {

	public Word PC, SP; // Word for PC and SP, will be initialized to 0 and 1024 respectively
	public Bit halted; // Used to indicate halted
	public Word currentInstruction; // Word used to hold the instruction
	// An array of Words, which will be initialized to 32
	public Word[] registers;
	
	// Variables for masking
	public Word rs1Mask; // Mask for extracting the rs1 field from the instruction
	public Word rs2Mask; // Mask for extracting the rs2 field from the instruction
	public Word funcMask; // Mask for extracting the function field from the instruction
	public Word rdMask; // Mask for extracting the rd field from the instruction
	public Word opMask; // Mask for extracting the opcode field from the instruction
	public Word immMask; // Mask for extracting the immediate field from the instruction
	
	// Variables for decoded instruction fields
	public Word imm; // Immediate value extracted from the instruction
	public Word rs1; // Value of source register 1 extracted from the instruction
	public Word rs2; // Value of source register 2 extracted from the instruction
	public Word func; // Function code extracted from the instruction
	public Word rd; // Destination register extracted from the instruction
	public Word op; // Opcode extracted from the instruction
	
	public ALU alu; // ALU used for executing operatioms
	
	public InstructionCache cache; // InstructionCache variable
	public static int currentClockCycle; // Tracks the current clock cycle
	
	// Processor constructor
	public Processor() {
		// Initialize PC and SP to 0 and 1024 respectively
		PC = new Word();
		SP = new Word();
		PC.set(0);
		SP.set(1024);
		// Initialize halted and ensure it is false
		halted = new Bit();
		halted.clear();
		
		registers = new Word[32]; // Array of 32
		for (int i = 0; i < registers.length; i++) {
			registers[i] = new Word(); // Initialize all 32 Words
		}
		
		// Initialize masks
		rs1Mask = new Word();
		rs2Mask = new Word();
		funcMask = new Word();
		rdMask = new Word();
		opMask = new Word();
		immMask = new Word();
		
		// Initialize decoded instruction fields
		imm = new Word();
		rs1 = new Word();
		rs2 = new Word();
		func = new Word();
		rd = new Word();
		op = new Word();
		
		// Initialize ALU
		alu = new ALU();
		
		// Initialize InstructionCache
		cache = new InstructionCache();
		
		// Initialize the current clock cycle
		currentClockCycle = 0;
	}
	
	// Accessor for PC
	public Word getPC() {
		return PC;
	}
	
	// Accessor for SP
	public Word getSP() {
		return SP;
	}
	
	// Accessor for currentInstruction
	public Word getCurrentInstruction() {
		return currentInstruction;
	}
	
	// Call various instructions for Processor. This method will be fully implemented later
	public void run() throws Exception {
		// Perform instructions while halted is true
		while (!halted.getValue()) {
			fetch();
			decode();
			Word result = execute();
			store(result);
		}
	}
	
	// Helper method to calculate indices for rs1, rs2, and rd
	public int getIndex(Word word) {
		// Checks for the first 5 bits
		boolean[] bits = {
				word.getBit(0).getValue(),
				word.getBit(1).getValue(),
				word.getBit(2).getValue(),
				word.getBit(3).getValue(),
				word.getBit(4).getValue(),
		};
		
		// Variable for counting index
		int index = 0;
		
		// Add appropriate index based on number represented by 5 bits
		if (!bits[0] && !bits[1] && !bits[2] && !bits[3] && !bits[4]) {
			index = 0;
		}
		if (bits[0] && !bits[1] && !bits[2] && !bits[3] && !bits[4]) {
			index = 1;
		}
		if (!bits[0] && bits[1] && !bits[2] && !bits[3] && !bits[4]) {
			index = 2;
		}
		if (bits[0] && bits[1] && !bits[2] && !bits[3] && !bits[4]) {
			index = 3;
		}
		if (!bits[0] && !bits[1] && bits[2] && !bits[3] && !bits[4]) {
			index = 4;
		}
		if (bits[0] && !bits[1] && bits[2] && !bits[3] && !bits[4]) {
			index = 5;
		}
		if (!bits[0] && bits[1] && bits[2] && !bits[3] && !bits[4]) {
			index = 6;
		}
		if (bits[0] && bits[1] && bits[2] && !bits[3] && !bits[4]) {
			index = 7;
		}
		if (!bits[0] && !bits[1] && !bits[2] && bits[3] && !bits[4]) {
			index = 8;
		}
		if (bits[0] && !bits[1] && !bits[2] && bits[3] && !bits[4]) {
			index = 9;
		}
		if (!bits[0] && bits[1] && !bits[2] && bits[3] && !bits[4]) {
			index = 10;
		}
		if (bits[0] && bits[1] && !bits[2] && bits[3] && !bits[4]) {
			index = 11;
		}
		if (!bits[0] && !bits[1] && bits[2] && bits[3] && !bits[4]) {
			index = 12;
		}
		if (bits[0] && !bits[1] && bits[2] && bits[3] && !bits[4]) {
			index = 13;
		}
		if (!bits[0] && bits[1] && bits[2] && bits[3] && !bits[4]) {
			index = 14;
		}
		if (bits[0] && bits[1] && bits[2] && bits[3] && !bits[4]) {
			index = 15;
		}
		if (!bits[0] && !bits[1] && !bits[2] && !bits[3] && bits[4]) {
			index = 16;
		}
		if (bits[0] && !bits[1] && !bits[2] && !bits[3] && bits[4]) {
			index = 17;
		}
		if (!bits[0] && bits[1] && !bits[2] && !bits[3] && bits[4]) {
			index = 18;
		}
		if (bits[0] && bits[1] && !bits[2] && !bits[3] && bits[4]) {
			index = 19;
		}
		if (!bits[0] && !bits[1] && bits[2] && !bits[3] && bits[4]) {
			index = 20;
		}
		if (bits[0] && !bits[1] && bits[2] && !bits[3] && bits[4]) {
			index = 21;
		}
		if (!bits[0] && bits[1] && bits[2] && !bits[3] && bits[4]) {
			index = 22;
		}
		if (bits[0] && bits[1] && bits[2] && !bits[3] && bits[4]) {
			index = 23;
		}
		if (!bits[0] && !bits[1] && !bits[2] && bits[3] && bits[4]) {
			index = 24;
		}
		if (bits[0] && !bits[1] && !bits[2] && bits[3] && bits[4]) {
			index = 25;
		}
		if (!bits[0] && bits[1] && !bits[2] && bits[3] && bits[4]) {
			index = 26;
		}
		if (bits[0] && bits[1] && !bits[2] && bits[3] && bits[4]) {
			index = 27;
		}
		if (!bits[0] && !bits[1] && bits[2] && bits[3] && bits[4]) {
			index = 28;
		}
		if (bits[0] && !bits[1] && bits[2] && bits[3] && bits[4]) {
			index = 29;
		}
		if (!bits[0] && bits[1] && bits[2] && bits[3] && bits[4]) {
			index = 30;
		}
		if (bits[0] && bits[1] && bits[2] && bits[3] && bits[4]) {
			index = 31;
		}
		
		// return the appropriate index
		return index;
	}
	
	// Get the next instruction (based on the PC) and increment the PC
	public void fetch() throws Exception {
		// Set currentInstruction to reading from the PC
		//currentInstruction = MainMemory.read(PC);
		
		// Read the instruction from the cache
		currentInstruction = cache.read(PC);
		
		//currentClockCycle += 300;
		
		// Call Increment on PC
		PC.increment();
	}
	
	// Decode the current instruction to extract relevant fields based on the instruction format
	public void decode() {
		// Initialize variables
		
		// Used to check instruction format
		Bit bit1 = new Bit();
		Bit bit2 = new Bit();
		Bit trueBit = new Bit();
		bit1.set(currentInstruction.getBit(0).getValue());
		bit2.set(currentInstruction.getBit(1).getValue());
		trueBit.set(); // Set bits to true (for masking)
		
		// Set up masks for decoding
		for (int i = 0; i < 32; i++) {
			if (i < 5) {
				opMask.setBit(i, trueBit); // opcode mask
			}
			if (i < 10 && i >= 5) {
				rdMask.setBit(i, trueBit); // rd mask
			}
			if (i < 14 && i >= 10) {
				funcMask.setBit(i, trueBit); // func mask
			}
			if (i < 19 && i >= 14) {
				rs2Mask.setBit(i, trueBit); // rs2 mask
			}
			if (i < 24 && i >= 19) {
				rs1Mask.setBit(i, trueBit); // rs1 mask
			}
		}
		
		// Decode instruction based on the format
		
		// 3R (11)
		if (bit1.getValue() && bit2.getValue()) {
			
			// Set immediate mask
			for (int i = 24; i < 32; i++) {
				immMask.setBit(i, trueBit);
			}
			
			// Extract instruction fields and left shift
			imm = currentInstruction.and(immMask).leftShift(24);
			rs1 = currentInstruction.and(rs1Mask).leftShift(19);
			rs2 = currentInstruction.and(rs2Mask).leftShift(14);
			func = currentInstruction.and(funcMask).leftShift(10);
			rd = currentInstruction.and(rdMask).leftShift(5);
			op = currentInstruction.and(opMask).leftShift(0);	
		}
		
		// 2R (01)
		// Use rs2 as rs
		else if (!bit1.getValue() && bit2.getValue()) {
			
			// Set immediate mask
			for (int i = 19; i < 32; i++) {
				immMask.setBit(i, trueBit);
			}
			
			// Extract instruction fields and left shift
			imm = currentInstruction.and(immMask).leftShift(19);
			rs2 = currentInstruction.and(rs2Mask).leftShift(14);
			func = currentInstruction.and(funcMask).leftShift(10);
			rd = currentInstruction.and(rdMask).leftShift(5);
			op = currentInstruction.and(opMask).leftShift(0);
		}
		
		// Dest Only/1R (10)
		else if (bit1.getValue() && !bit2.getValue()) {
			
			// Set immediate mask
			for (int i = 14; i < 32; i++) {
				immMask.setBit(i, trueBit);
			}
			
			// Extract instruction fields and left shift
			imm = currentInstruction.and(immMask).leftShift(14);
			func = currentInstruction.and(funcMask).leftShift(10);
			rd = currentInstruction.and(rdMask).leftShift(5);
			op = currentInstruction.and(opMask).leftShift(0);
		}
		
		// No Register/0R (00)
		else {
			
			// Set immediate mask
			for (int i = 5; i < 32; i++) {
				immMask.setBit(i, trueBit);
			}
			
			// Extract instruction fields and left shift
			imm = currentInstruction.and(immMask).leftShift(5);
			op = currentInstruction.and(opMask).leftShift(0);
		}
	}
	
	// Executes the instruction currently held in the processor, performing the corresponding operation based on the opcode and instruction format
	public Word execute() throws Exception {
		// Initialize variables
		
		// Used for getting indices 
		int index = 0;
		int index2 = 0;
		
		// Extract function Bits for ALU operation
		Bit[] funcArray = new Bit[4];
		for (int i = 0; i < 4; i++) {
			funcArray[i] = func.getBit(i);
		}
		
		// Every instruction needs to be read into memory, and therefore, every instruction will require more than 300 cycles.
		//currentClockCycle += 300;
		
		// Checks for math op (000)
		if (!op.getBit(2).getValue() && !op.getBit(3).getValue() && !op.getBit(4).getValue()) {
			
			// 0R (No register)
			if (!op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				halted.set(); // Set halted flag to true
				System.out.println(currentClockCycle);
			}
			
			// 1R (Dest only)
			if (op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				registers[rd.getSigned()].copy(imm); // Copy immediate to register
				return imm; // Return immediate value
			}
			
			// 2R
			if (!op.getBit(0).getValue() && op.getBit(1).getValue()) {
				
				// Extract register indices for rd and rs2
				index = getIndex(rd);
				index2 = getIndex(rs2);
				
				alu = new ALU(registers[index], registers[index2]); // Initialize ALU with registers
				alu.doOperation(funcArray); // Perform ALU operation
				
				// Add 10 cycles for multiplication, and add 2 cycles for other ALU math functions
				if (funcArray[0].getValue() == false && funcArray[1].getValue() == true && funcArray[2].getValue() == true && funcArray[3].getValue() == true) {
					currentClockCycle += 10;
				}
				else {
					currentClockCycle += 2;
				}
				
				return alu.result; // Return result
			}
			
			// 3R
			if (op.getBit(0).getValue() && op.getBit(1).getValue()) {
				
				// Extract register indices for rs1 and rs2
				index = getIndex(rs1);
				index2 = getIndex(rs2);
				
				alu = new ALU(registers[index], registers[index2]); // Initialize ALU with registers
				alu.doOperation(funcArray); // Perform ALU operation
				
				// Add 10 cycles for multiplication, and add 2 cycles for other ALU math functions
				if (funcArray[0].getValue() == false && funcArray[1].getValue() == true && funcArray[2].getValue() == true && funcArray[3].getValue() == true) {
					currentClockCycle += 10;
				}
				else {
					currentClockCycle += 2;
				}
				
				return alu.result; // Return result
			}
			
		}
		
		// Checks for branch op (001)
		if (!op.getBit(2).getValue() && !op.getBit(3).getValue() && op.getBit(4).getValue()) {
			
			// 0R (No register)
			if (!op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				// Return immediate
				return imm;
			}
			
			// 1R (Dest only)
			if (op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				// Add the values of PC and immediate and return the result
				alu.add2(PC, imm);
				return alu.result;
			}
			
			// 2R
			if (!op.getBit(0).getValue() && op.getBit(1).getValue()) {
				
				// Extract register indices for rs2 and rd
				index = getIndex(rs2);
				index2 = getIndex(rd);

				alu = new ALU(registers[index], registers[index2]); // Initialize ALU with registers
				alu.doBoperation(funcArray); // Perform ALU Boperation
				
				// Check if the result is true
				if (alu.result.getUnsigned() != 0) {
					// If true, return the result of PC + immediate
					alu.add2(PC, imm);
					return alu.result;
				}
				else {
					// If false, return PC
					return PC;
				}
			}
			
			// 3R
			if (op.getBit(0).getValue() && op.getBit(1).getValue()) {
				
				// Extract register indices for rs1 and rs2
				index = getIndex(rs1);
				index2 = getIndex(rs2);
				
				alu = new ALU(registers[index], registers[index2]); // Initialize ALU with registers
				alu.doBoperation(funcArray); // Perform ALU Boperation
				
				// Check if the result is true
				if (alu.result.getUnsigned() != 0) {
					// If true, return the result of PC + immediate
					alu.add2(PC, imm);
					return alu.result;
				}
				else {
					// If false, return PC
					return PC;
				}
			}	
		}
		
		// Checks for call op (010)
		if (!op.getBit(2).getValue() && op.getBit(3).getValue() && !op.getBit(4).getValue()) {
			
			// 0R (No register)
			if (!op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				// Decrement SP and push the PC
				SP.decrement();
				MainMemory.memory[(int) SP.getUnsigned()].copy(PC);
				// return immediate
				return imm;
			}
			
			// 1R (Dest only)
			if (op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				// Decrement SP and push the PC
				SP.decrement();
				MainMemory.memory[(int) SP.getUnsigned()].copy(PC);
				// Return the result of adding PC and immediate
				alu.add2(PC, imm);
				return alu.result;
			}
			
			// 2R
			if (!op.getBit(0).getValue() && op.getBit(1).getValue()) {
				// Extract register indices for rs2 and rd
				index = getIndex(rs2);
				index2 = getIndex(rd);
				
				alu = new ALU(registers[index], registers[index2]); // Initialize ALU with registers
				alu.doBoperation(funcArray); // Perform ALU Boperation
				
				// Check if the result is true
				if (alu.result.getUnsigned() != 0) {  // If true
					// Decrement SP and push the PC
					SP.decrement();
					MainMemory.memory[(int) SP.getUnsigned()].copy(PC);
					// Return the result of adding PC and immediate
					alu.add2(PC, imm);
					return alu.result;
				}
				else {
					// If false, return PC
					return PC;
				}
			}
			
			// 3R
			if (op.getBit(0).getValue() && op.getBit(1).getValue()) {
				// Extract register indices for rs1 and rs2
				index = getIndex(rd);
				index2 = getIndex(rs2);
				
				alu = new ALU(registers[index], registers[index2]); // Initialize ALU with registers
				alu.doBoperation(funcArray); // Perform ALU Boperation
				
				// Check if the result is true
				if (alu.result.getUnsigned() != 0) { // If true
					// Decrement SP and push the PC
					SP.decrement();
					MainMemory.memory[(int) SP.getUnsigned()].copy(PC);
					// Return the result of adding PC and immediate
					alu.add2(PC, imm);
					return alu.result;
				}
				else {
					// If false, return PC
					return PC;
				}
			}
		}
		
		// Checks for push op (011) (0R is unused)
		if (!op.getBit(2).getValue() && op.getBit(3).getValue() && op.getBit(4).getValue()) {
			
			// 1R (Dest only)
			if (op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				// Extract register indices for rd
				index = getIndex(rd);
				alu = new ALU(registers[index], imm); // Initialize ALU with registers
				alu.doOperation(funcArray); // Perform ALU operation
				
				// Add 300 cycles for accessing memory
				currentClockCycle += 300;
				// Add 10 cycles for multiplication, and add 2 cycles for other ALU math functions
				if (funcArray[0].getValue() == false && funcArray[1].getValue() == true && funcArray[2].getValue() == true && funcArray[3].getValue() == true) {
					currentClockCycle += 10;
				}
				else {
					currentClockCycle += 2;
				}
				
				return alu.result; // Return result
			}
			
			// 2R
			if (!op.getBit(0).getValue() && op.getBit(1).getValue()) {
				
				// Extract register indices for rd and rs2
				index = getIndex(rd);
				index2 = getIndex(rs2);
				
				alu = new ALU(registers[index], registers[index2]); // Initialize ALU with registers
				alu.doOperation(funcArray); // Perform ALU operation
				
				// Add 300 cycles for accessing memory
				currentClockCycle += 300;
				// Add 10 cycles for multiplication, and add 2 cycles for other ALU math functions
				if (funcArray[0].getValue() == false && funcArray[1].getValue() == true && funcArray[2].getValue() == true && funcArray[3].getValue() == true) {
					currentClockCycle += 10;
				}
				else {
					currentClockCycle += 2;
				}
				
				return alu.result; // Return result
			}
			
			// 3R
			if (op.getBit(0).getValue() && op.getBit(1).getValue()) {
				
				// Extract register indices for rs1 and rs2
				index = getIndex(rs1);
				index2 = getIndex(rs2);
				
				alu = new ALU(registers[index], registers[index2]); // Initialize ALU with registers
				alu.doOperation(funcArray); // Perform ALU operation
				
				// Add 300 cycles for accessing memory
				currentClockCycle += 300;
				// Add 10 cycles for multiplication, and add 2 cycles for other ALU math functions
				if (funcArray[0].getValue() == false && funcArray[1].getValue() == true && funcArray[2].getValue() == true && funcArray[3].getValue() == true) {
					currentClockCycle += 10;
				}
				else {
					currentClockCycle += 2;
				}
				
				return alu.result; // Return result
			}
			
		}
		
		// Checks for load op (100)
		if (op.getBit(2).getValue() && !op.getBit(3).getValue() && !op.getBit(4).getValue()) {
			
			// 0R (No register) 
			if (!op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				// Read and increment SP
				Word word = MainMemory.read(SP);
				SP.increment();
				// Return that result
				return word;
			}
			
			// 1R (Dest only)
			if (op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				
				// Extract register indices for rd
				index = getIndex(rd);
				
				// Add the values of rd and immediate and read that into memory
				Word memIndex = alu.add2(registers[index], imm);
				// Add 300 cycles for accessing memory
				currentClockCycle += 300;
				return MainMemory.read(memIndex);
				//return cache.read(memIndex);
			}
			
			// 2R
			if (!op.getBit(0).getValue() && op.getBit(1).getValue()) {
				
				// Extract register indices for rs2
				index = getIndex(rs2);
				
				// Add the values of rs2 and immediate and read that into memory
				Word memIndex = alu.add2(registers[index], imm);
				// Add 300 cycles for accessing memory
				currentClockCycle += 300;
				return MainMemory.read(memIndex);
			}
			
			// 3R
			if (op.getBit(0).getValue() && op.getBit(1).getValue()) {
				
				// Extract register indices for rs1 and rs2
				index = getIndex(rs1);
				index2 = getIndex(rs2);
				
				// Add the values of rs1 and rs2 and read that into memory
				Word memIndex = alu.add2(registers[index], registers[index2]);
				// Add 300 cycles for accessing memory
				currentClockCycle += 300;
				return MainMemory.read(memIndex);
			}
			
		}
		
		// Checks for store op (101)
		if (op.getBit(2).getValue() && !op.getBit(3).getValue() && op.getBit(4).getValue()) {
			
			// 1R (Dest only)
			if (op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				// Add 300 cycles for accessing memory
				currentClockCycle += 300;
				// Return immediate
				return imm;
			}
			
			// 2R
			if (!op.getBit(0).getValue() && op.getBit(1).getValue()) {
				// Extract register indices for rs2
				index = getIndex(rs2);
				// Add 300 cycles for accessing memory
				currentClockCycle += 300;
				// Return rs2
				return registers[index];
			}
			
			// 3R
			if (op.getBit(0).getValue() && op.getBit(1).getValue()) {
				// Extract register indices for rs2
				index = getIndex(rs2);
				// Return rs2
				return registers[index];
			}
			
		}
		
		// Checks for pop op (110) (We will not handle interrupt)
		if (op.getBit(2).getValue() && op.getBit(3).getValue() && !op.getBit(4).getValue()) {
			
			// 1R (Dest only)
			if (op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				// Read an increment SP
				Word word = MainMemory.read(SP);
				SP.increment();
				// Add 300 cycles for accessing memory
				currentClockCycle += 300;
				// Return that value
				return word;
			}
			
			// 2R
			if (!op.getBit(0).getValue() && op.getBit(1).getValue()) {
				// Extract register indices for rs2
				index = getIndex(rs2);
				// Subtract the value of rs2 + immediate from SP
				Word memIndex = alu.subtract(SP, alu.add2(registers[index], imm));
				// Add 300 cycles for accessing memory
				currentClockCycle += 300;
				// Read that value into memory
				return MainMemory.read(memIndex);
			}
			
			// 3R
			if (op.getBit(0).getValue() && op.getBit(1).getValue()) {
				// Extract register indices for rs1 and rs2
				index = getIndex(rs1);
				index2 = getIndex(rs2);
				// Subtract the value of rs1 + rs2 from SP
				Word memIndex = alu.subtract(SP, alu.add2(registers[index], registers[index2]));
				// Add 300 cycles for accessing memory
				currentClockCycle += 300;
				// Read that value into memory
				return MainMemory.read(memIndex);
			}
			
		}
		
		return null; // Return null if no operation performed
	}

	// Store the result of the executed instruction into the appropriate register based on the operation performed and the instruction format
	public void store(Word result) throws Exception {
		
		// Check if the result is null
		if (result == null) {
			return;
		}
		
		// Used to update RD
		int index = 0;
		int index2 = 0;
		
		// Checks for math op (000)
		if (!op.getBit(2).getValue() && !op.getBit(3).getValue() && !op.getBit(4).getValue()) {
			// Update RD accordingly
			index = getIndex(rd);
			// Copy the result to the appropriate register
			registers[index].copy(result);
		}
		
		// Checks for branch op (001)
		if (!op.getBit(2).getValue() && !op.getBit(3).getValue() && op.getBit(4).getValue()) {
			// Update PC accordingly
			PC.copy(result);
		}
		
		// Checks for call op (010)
		if (!op.getBit(2).getValue() && op.getBit(3).getValue() && !op.getBit(4).getValue()) {
			PC.copy(result);
		}
		
		// Checks for push op (011)
		if (!op.getBit(2).getValue() && op.getBit(3).getValue() && op.getBit(4).getValue()) {
			// Update mem[--SP]
			SP.decrement();
			MainMemory.write(SP, result);
		}
		
		// Checks for load op (100)
		if (op.getBit(2).getValue() && !op.getBit(3).getValue() && !op.getBit(4).getValue()) {
			if (!op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				PC.copy(result);
			}
			else {
				// Update RD accordingly
				index = getIndex(rd);
				// Copy the result to the appropriate register
				registers[index].copy(result);
		
			}
		}
		
		// Checks for store op (101)
		if (op.getBit(2).getValue() && !op.getBit(3).getValue() && op.getBit(4).getValue()) {
			
			// 1R (Dest only)
			if (op.getBit(0).getValue() && !op.getBit(1).getValue()) {
				index = getIndex(rd);
				MainMemory.write(registers[index], result);
			}
			
			// 2R
			if (!op.getBit(0).getValue() && op.getBit(1).getValue()) {
				index = getIndex(rd);
				alu.add2(registers[index], imm);
				MainMemory.write(alu.result, result);
			}
			
			// 3R
			if (op.getBit(0).getValue() && op.getBit(1).getValue()) {
				index = getIndex(rd);
				index2 = getIndex(rs1);
				alu.add2(registers[index], registers[index2]);
				MainMemory.write(alu.result, result);
			}
			
		}
		
		// Checks for pop op (110)
		if (op.getBit(2).getValue() && op.getBit(3).getValue() && !op.getBit(4).getValue()) {
			// Update RD accordingly
			index = getIndex(rd);
			// Copy the result to the appropriate register
			registers[index].copy(result);
		}
		
		// Set R0 to to 0
		registers[0].set(0);
		
	}
	
}
