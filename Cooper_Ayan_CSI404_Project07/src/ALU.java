
public class ALU {

	// Word objects used to store the result of performing an operation between two words
	public Word op1;
	public Word op2;
	public Word result;
	
	// ALU constructor, initializing Word objects
	public ALU() {
		op1 = new Word();
		op2 = new Word();
		result = new Word();
	}
	
	public ALU(Word op1, Word op2) {
		this.op1 = op1;
		this.op2 = op2;
		result = new Word();
	}
	
	// Look at the array of bits (4 bits) and determine the operation: 
	// and, or, xor, not, left shift, right shift, add, subtract, multiply
	public void doOperation(Bit[] operation) {
		for (int i = 0; i < op1.words.length; i++) {
			// Perform and operation if bits are 1000
			if (operation[0].getValue() && !operation[1].getValue() && !operation[2].getValue() && !operation[3].getValue()) {
				// Get the bits from op1 and op2
				Bit bitop1 = op1.getBit(i);
				Bit bitop2 = op2.getBit(i);
				// Set result to the outcome of anding the bits of op1 and op2
				result.words[i] = bitop1.and(bitop2);
			}
			// Perform or operation if bits are 1001
			if (operation[0].getValue() && !operation[1].getValue() && !operation[2].getValue() && operation[3].getValue()) {
				// Get the bits from op1 and op2
				Bit bitop1 = op1.getBit(i);
				Bit bitop2 = op2.getBit(i);
				// Set result to the outcome of "oring" the bits of op1 and op2
				result.words[i] = bitop1.or(bitop2);
			}
			// Perform xor operation if bits are 1010
			if (operation[0].getValue() && !operation[1].getValue() && operation[2].getValue() && !operation[3].getValue()) {
				// Get the bits from op1 and op2
				Bit bitop1 = op1.getBit(i);
				Bit bitop2 = op2.getBit(i);
				// Set result to the outcome of "xoring" the bits of op1 and op2
				result.words[i] = bitop1.xor(bitop2);
			}
			// Perform not operation if bits are 1011 (we will "not" op1, ignoring op2)
			if (operation[0].getValue() && !operation[1].getValue() && operation[2].getValue() && operation[3].getValue()) {
				// Get the bits from op1
				Bit bitop1 = op1.getBit(i);
				// Set the result to the outcome of "notting" the bits of op1
				result.words[i] = bitop1.not();
			}
			// Perform the left shift operation if bits are 1100 (op1 is the value to shift, op2 is the amount to shift;
			// ignore all but the lower 5 bits)
			if (operation[0].getValue() && operation[1].getValue() && !operation[2].getValue() && !operation[3].getValue()) {
				int shiftAmount = op2.getSigned();
				shiftAmount = shiftAmount % 32; // Only consider the lowest 5 bits
				result = op1.leftShift(shiftAmount);
			}
			// Perform the right shift operation if bits are 1101 (op1 is the value to shift, op2 is the amount to shift;
			// ignore all but the lower 5 bits)
			if (operation[0].getValue() && operation[1].getValue() && !operation[2].getValue() && operation[3].getValue()) {
				int shiftAmount = op2.getSigned();
				shiftAmount = shiftAmount % 32; // Only consider the lowest 5 bits
				result = op1.rightShift(shiftAmount);
			}
			// Perform subtraction if the bits are 1111, calling the subtraction method
			if (operation[0].getValue() && operation[1].getValue() && operation[2].getValue() && operation[3].getValue()) {
				result.copy(subtract(op1, op2));
			}
			// Perform addition if the bits are 1110, calling the addition method
			if (operation[0].getValue() && operation[1].getValue() && operation[2].getValue() && !operation[3].getValue()) {
				result.copy(add(op1, op2));
			}
			// Perform multiplication if the bits are 0111, calling the multiplication  method
			if (!operation[0].getValue() && operation[1].getValue() && operation[2].getValue() && operation[3].getValue()) {
				result.copy(multiply(op1, op2));
			}
		}
	}
	
	public void doBoperation(Bit[] operation) {
		for (int i = 0; i < op1.words.length; i++) {
			// 0000 (equals)
			if (!operation[0].getValue() && !operation[1].getValue() && !operation[2].getValue() && !operation[3].getValue()) {
				result.copy(subtract(op1, op2));
				// If the result of op1 - op2 is 0, then op1 is equal to op2
				boolean isEqual = true;
				for (int j = 0; j < result.words.length; j++) {
					if (result.getUnsigned() != 0) {
						isEqual = false;
						break;
					}
				}
				result.words[i].set(isEqual);
			}
			// 0001 (not equal)
			if (!operation[0].getValue() && !operation[1].getValue() && !operation[2].getValue() && operation[3].getValue()) {
				result.copy(subtract(op1, op2));
				// If the result of op1 - op2 is not 0, then op1 isn't equal to op2
				boolean notEqual = false;
				for (int j = 0; j < result.words.length; j++) {
					if (result.getUnsigned() != 0) {
						notEqual = true;
						break;
					}
				}
				result.words[i].set(notEqual);
			}
			// 0010 (less than)
			if (!operation[0].getValue() && !operation[1].getValue() && operation[2].getValue() && !operation[3].getValue()) {
				// If the result of op1 - op2 is negative, then op1 is less than op2
				result.copy(subtract(op1, op2));
				if (result.getSigned() < 0) {
					result.words[i].set();
				}
			}
			// 0011 (greater than or equal to)
			if (!operation[0].getValue() && !operation[1].getValue() && operation[2].getValue() && operation[3].getValue()) {
				result.copy(subtract(op1, op2));
				// If the result of op1 - op2 is less than 0, then op1 isn't greater than or equal to op2. Vice-versa otherwise
				boolean ge = true;
				if (result.getSigned() < 0) {
					ge = false;
				}
				result.words[i].set(ge);
			}
			// 0100 (greater than)
			if (!operation[0].getValue() && operation[1].getValue() && !operation[2].getValue() && !operation[3].getValue()) {
				result.copy(subtract(op1, op2));
				// If the result of op1 - op2 is positive, then op1 is greater than op2
				if (!result.words[0].getValue()) {
					result.words[i].set();
				}
			}
			// 0101 (less than or equal to)
			if (!operation[0].getValue() && operation[1].getValue() && !operation[2].getValue() && operation[3].getValue()) {
				result.copy(subtract(op1, op2));
				// If the result of op1 - op2 is greater than 0, then op1 isn't less than or equal to op2. Vice-versa otherwise
				boolean le = true;
				for (int j = 0; j < result.words.length; j++) {
					if (!result.words[0].getValue()) {
						le = false;
						break;
					}
				}
				result.words[i].set(le);
			}
		}
	}
	
	
	
	// Performs addition using the add2 method
	public Word add(Word a, Word b) {
		Word sum = add2(op1, op2);
		return sum;
	}
	
	// Performs subtraction of Word a - Word b using two's compliment
	public Word subtract(Word a, Word b) {
		// Calculate the 2's compliment of Word b
		Word op2TwosCompliment = twosCompliment(b);
		// Add the modified 2's compliment to Word a
		Word subtractResult = add2(a, op2TwosCompliment);
		result.copy(subtractResult);
		// Return the result of the subtraction
		return subtractResult;
	}
	
	// Calculates the 2's compliment of a Word
	private Word twosCompliment(Word word) {
		Word compliment = word.not(); // Flip all the bits
		// Create a word representing one
		Word one = new Word();
		one.set(1);
		return add2(compliment, one); // Add 1 to the compliment
	}
	
	// Multiply two words using the binary multiplication algorithm
	public Word multiply(Word a, Word b) {
		// Array to store intermediate results of multiplication
		Word[] multiplyResults = new Word[32];
		for (int i = 0; i < 32; i++) {
			multiplyResults[i] = new Word();
		}
		for (int j = 0; j < 32; j++) {
			for (int i = 0; i < 32; i++) {
				multiplyResults[j].setBit(i, a.getBit(i).and(b.getBit(j)));
			}
		}
		int j = 0;
		for (int i = 0; i < 32; i++) {
			multiplyResults[i] = multiplyResults[i].rightShift(i);
			j++;
		}
		
		// Round 1: Use call add4 8 times to reduce the number of adds down to 8
		Word round1_1 = add4(multiplyResults[0], multiplyResults[1], multiplyResults[2], multiplyResults[3]);
		Word round1_2 = add4(multiplyResults[4], multiplyResults[5], multiplyResults[6], multiplyResults[7]);
		Word round1_3 = add4(multiplyResults[8], multiplyResults[9], multiplyResults[10], multiplyResults[11]);
		Word round1_4 = add4(multiplyResults[12], multiplyResults[13], multiplyResults[14], multiplyResults[15]);
		Word round1_5 = add4(multiplyResults[16], multiplyResults[17], multiplyResults[18], multiplyResults[19]);
		Word round1_6 = add4(multiplyResults[20], multiplyResults[21], multiplyResults[22], multiplyResults[23]);
		Word round1_7 = add4(multiplyResults[24], multiplyResults[25], multiplyResults[26], multiplyResults[27]);
		Word round1_8 = add4(multiplyResults[28], multiplyResults[29], multiplyResults[30], multiplyResults[31]);
		
		// Round 2: Call add4 two more times, reducing the number of adds to 2
		Word round2_1 = add4(round1_1, round1_2, round1_3, round1_4);
		Word round2_2 = add4(round1_5, round1_6, round1_7, round1_8);
		
		// Round 3/Final Round: Call add2 once to give use the final result
		Word round3 = add2(round2_1, round2_2);
		
		// Set the result
		return round3;
	}
	
	// Adds two bits together and deals with carry; left public for testing
	public Word add2(Word a, Word b) {
		Word sum = new Word(); // Word that will be the sum of the bits from Word a and Word b
		Bit carry = new Bit(); // Handles the carry from addition
		
		for (int i = 0; i < sum.words.length; i++) {
			Bit bitA = a.getBit(i);
			Bit bitB = b.getBit(i);
			
			// Calculate sum bit
			//sum.getBit(i).set(bitA.xor(bitB).xor(carry).getValue());
			sum.setBit(i, bitA.xor(bitB).xor(carry));
			//sum.setBit(i, a.xor(b).getBit(i).xor(carry));
			
			// Calculate carry out bit
			//carry = bitA.and(bitB).or(bitA.xor(bitB)).and(carry);
			carry = bitA.and(bitB).or(bitA.and(carry)).or(bitB.and(carry));
			//carry = a.and(b).getBit(i).or(a.xor(b).getBit(i).and(carry));
			
		}
		result.copy(sum);
		return sum;
	}
	
	// Adds four bits together and deals with carry; left public for testing
	public Word add4(Word a, Word b, Word c, Word d) {
		Word sum = new Word(); // Word that will be the sum of the bits
		Bit carry1 = new Bit(); // Handles the carry from addition
		Bit carry2 = new Bit(); // Handles the second level carry
		Bit saveCarry1 = new Bit();
		Bit saveCarry2 = new Bit(); // Temporary storage for carry2
		Bit add = new Bit();
		
		Bit bitA = new Bit();
		Bit bitB = new Bit();
		Bit bitC = new Bit();
		Bit bitD = new Bit();
		Bit temp = carry2;
		carry2 = saveCarry2;
		saveCarry2 = temp;
		
		Bit temp2 = carry1;
		
		int count;
		for (int i = 0; i < sum.words.length; i++) {
			bitA = a.getBit(i);
			bitB = b.getBit(i);
		    bitC = c.getBit(i);
			bitD = d.getBit(i);
			temp = carry2;
			carry2 = saveCarry2;
			saveCarry2 = temp;
			
			temp2 = carry1;
			//System.out.println(bitA+"\n\n"+bitB+"\n\n"+bitC+"\n\n"+bitD+"\n\n"+carry1+"\n\n"+carry2);
			add = bitA.xor(bitB).xor(bitC).xor(bitD).xor(carry1).xor(carry2);
			// Calculate sum bit
			sum.setBit(i, bitA.xor(bitB).xor(bitC).xor(bitD).xor(carry1).xor(carry2));
			
			// Calculate carry1 out bit, accounting for cases where there are 2, 3, or 6 true bits
			carry1 = (bitA.and(bitB).and(bitC.and(bitD).and(temp2.and(carry2)))).or(
					(bitA.and(bitB).and(bitC.or(bitD).not()).and(temp2.or(carry2).not()))).or(
					(bitA.or(bitB).not()).and(bitC.and(bitD)).and(temp2.or(carry2).not()))
			.or(
					(bitA.or(bitB).not()).and(bitC.or(bitD).not()).and(temp2.and(carry2)).or(
					(bitA.xor(bitB).and(bitC.xor(bitD)).and(temp2.or(carry2).not()))).or(
					(bitA.or(bitB).not().and(bitC.xor(bitD).and(temp2.and(carry2))))).or(
					(bitA.xor(bitB).and(bitC.or(bitD).not().and(temp2.xor(carry2))))).or(
					(bitA.and(bitB).and(bitC.xor(bitD).and(temp2.or(carry2).not())))).or(
					(bitA.xor(bitB).and(bitC.and(bitD).and(temp2.or(carry2).not())))).or(
					(bitA.or(bitB).not().and(bitC.and(bitD).and(temp2.xor(carry2))))).or(
					(bitA.or(bitB).not().and(bitC.xor(bitD).and(temp2.and(carry2))))).or(
					(bitA.xor(bitB).and(bitC.or(bitD).not().and(temp2.and(carry2))))).or(
					(bitA.and(bitB).and(bitC.or(bitD).not().and(temp2.xor(carry2))))).or(
					(bitA.xor(bitB).and(bitC.xor(bitD).and(temp2.xor(carry2))))));
			
			count = 0;
			if(bitA.getValue()) {
				//System.out.println("a");
				count++;
			}
			if(bitB.getValue()) {
				//System.out.println("b");
				count++;
			}
			if(bitC.getValue()) {
				//System.out.println("c");
				count++;
			}
			if(bitD.getValue()) {
				//System.out.println("d");
				count++;
			}
			if(temp2.getValue()) {
				//System.out.println("c1");
				count++;
			}
			if(carry2.getValue()) {
				//System.out.println("c2");
				count++;
			}
			if(count == 4 || count == 5 || count == 6) {
				//System.out.println(count);
				carry2.set();
			}
			else carry2.clear();
		}
		// Set the result
		return sum;
	}
	
}
