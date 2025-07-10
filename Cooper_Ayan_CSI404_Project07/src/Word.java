
public class Word {
	
	// An array of 32 of the Bit class
	public Bit words[];
	public boolean isNegative;
	
	// Word constructor
	public Word() {
		words = new Bit[32]; // Array of 32 bits
		for (int i = 0; i < words.length; i++) {
			words[i] = new Bit(); // Initialize all 32 bits
		}
	}
	
	// Get a new Bit that has the same value as bit i
	public Bit getBit(int i) {
		Bit newBit = new Bit();
		// Set the value of the new Bit to the value of the Bit at the specified index
		newBit.set(words[i].getValue());
		return newBit;
	}
	
	// set bit i's value
	public void setBit(int i, Bit value) {
		// Set the value of the Bit at the specified index to the value of the provided Bit
		words[i].set(value.getValue());
	}
	
	// and two words, returning a new Word
	public Word and(Word other) {
		Word andWord = new Word();
		// Iterate through each Bit in the Words and perform the AND operation
		for (int i = 0; i < words.length; i++) {
			// Check if the current Bit in both words are true
			if (words[i].getValue()) {
				// If both Bits are true, set the corresponding Bit in the result Word to true
				if (other.words[i].getValue()) {
					andWord.words[i].set();
				}
				// If the Bit in the other Word is false, set the corresponding Bit in the result Word to false
				else {
					andWord.words[i].clear();
				}
			}
			// If the Bit in the current Word is false, set the corresponding Bit in the result Word to false
			else {
				andWord.words[i].clear();
			}
		}
		return andWord;
	}
	
	// or two words, returning a new Word
	public Word or(Word other) {
		Word orWord = new Word();
		// Iterate through each Bit in the Words and perform the OR operation
		for (int i = 0; i < words.length; i++) {
			// Check if the current Bit in both words are false
			if (words[i].getValue() == false) {
				// If both Bits are false, set the corresponding Bit in the result Word to false
				if (other.words[i].getValue() == false) {
					orWord.words[i].clear();
				}
				// If the Bit in the other Word is true, set the corresponding Bit in the result Word to true
				else {
					orWord.words[i].set();
				}
			}
			// If the Bit in the current Word is true, set the corresponding Bit in the result Word to true
			else {
				orWord.words[i].set();
			}
		}
		return orWord;
	}
	
	// xor two words, returning a new Word
	public Word xor(Word other) {
		Word xorWord = new Word();
		// Iterate through each Bit in the Words and perform the XOR operation
		for (int i = 0; i < words.length; i++) {
			// Check if the current Bit in both words are true
			if (words[i].getValue()) {
				// If both Bits are true, set the corresponding Bit in the result Word to false
				if (other.words[i].getValue()) {
					xorWord.words[i].clear();
				}
				// If the Bit in the other Word is false, set the corresponding Bit in the result Word to true
				else {
					xorWord.words[i].set();
				}
			}
			// Check if the current Bit in both words are false
			else {
				// If both Bits are false, set the corresponding Bit in the result Word to false
				if (other.words[i].getValue() == false) {
					xorWord.words[i].clear();
				}
				// If the Bit in the other Word is true, set the corresponding Bit in the result Word to true
				else {
					xorWord.words[i].set();
				}
			}
		}
		return xorWord;
	}
	
	// negate this word, creating a new Word
	public Word not() {
		Word notWord = new Word();
		// Iterate through each Bit in the Words and perform the NOT operation
		for (int i = 0; i < words.length; i++) {
			// Set the corresponding Bit in the result Word to the logical NOT of the current Bit in the other word
			notWord.words[i].set(!this.words[i].getValue());
		}
		return notWord;
	}
	
	// right shift this word by amount bits, creating a new Word
	public Word rightShift(int amount) {
		Word shiftedWord = new Word();
		for (int i = 0; i < words.length; i++) {
			// Calculate the new index after shifting
			int newIndex = i + amount;
			
			// Check if the new index is within the bounds of the array
			if (newIndex < words.length) {
				shiftedWord.setBit(newIndex, words[i]);
			}
			// Set bit to false if out of bounds
			else {
				shiftedWord.words[i].clear();
			}
		}
		return shiftedWord;
	}
	
    // left shift this word by amount bits, creating a new Word
	public Word leftShift(int amount) {
		Word shiftedWord = new Word();
		for (int i = 0; i < words.length; i++) {
			// Calculate the new index after shifting
			int newIndex = i - amount;
			
			// Check if the new index is within the bounds of the array
			if (newIndex >= 0) {
				shiftedWord.words[newIndex].set(words[i].getValue());
			}
			// Set bit to false if out of bounds
			else {
				shiftedWord.words[i].clear();
			}
		}
		return shiftedWord;
	}
	
	// (positive only) returns the value of this word as a long
	public long getUnsigned() {
		long result = 0;
		// Iterate through each Bit in the Word
		for (int i = 0; i < words.length; i++) {
			// Check if the current Bit is true
			if (words[i].getValue()) {
				// Add 2^i to the result if the Bit is true
				result += Math.pow(2, i);
			}
		}
		return result;	
	}
	
	// (negative only) returns the value of this word as an int
	public int getSigned() {
		int result = 0;
		// Flip each Bit if the Word is negative
		if (isNegative) {
			for(int i = 0;i < words.length;i++) {
				if(words[i].getValue()) {
					words[i].clear();
					break;
				}
				else {
					words[i].set();
				}
			}
			for (int i = 0; i < words.length; i++) {
				words[i].toggle();
			}
		}
		
		int magnitude = 0;
		// Iterate through the rest of the Bits in the Word after the most significant Bit
		for (int i = 1; i < words.length; i++) {
			if (words[i - 1].getValue()) {
				magnitude += Math.pow(2, i - 1);
			}
		}
		
		// Assign the correct sign depending on if the Word is positive or negative
		if (isNegative) {
			result = -magnitude;
			set(result);
		}
		else {
			result = magnitude;
		}
		
		return result;
	}
	
	// copies the values of the bits from another Word into this one
	public void copy(Word other) {
		// Iterate through each Bit in the Word
		for (int i = 0; i < words.length; i++) {
			// Set the current Bit in this Word to the value of the corresponding Bit in the other Word
			this.words[i].set(other.getBit(i).getValue());
		}
	}
	
	// set the value of the bits of this Word (used for tests)
	public void set(int value) {
		// Check if the value is negative
		if (value < 0) {
			isNegative = true;
		}
		// Calculate the magnitude of the value (absolute value)
		int magnitude = Math.abs(value);
		
		// Set the Bits in the Word based on the binary representation of the magnitude
		for (int i = 0; i < words.length; i++) {
			// Set the current Bit to the remainder of the magnitude divided by 2
			words[i].set((magnitude % 2) != 0);
			// Divide the magnitude by 2 to move to the next Bit
			magnitude /= 2;
		}
		
		if(isNegative) {
			for(int i = 0;i < words.length;i++) {
				words[i].toggle();
			}
			for(int i = 0;i < words.length;i++) {
				if(words[i].getValue()) {
					words[i].clear();
				}
				else {
					words[i].set();
					break;
				}
			}
		}
	}
	
	// Increments a Word
	public void increment() {
		// Initialize the carry Bit to 1
		Bit carry = new Bit();
		carry.set();
		
		// Iterate through each Bit and perform binary addition using AND operation & XOR operation
		for (int i = 0; i < words.length; i++) {
			// Calculate the sum and update carry for the next iteration
			Bit sum = words[i].xor(carry);
			carry = words[i].and(carry);
			// Update the current Bit with the sum
			words[i].set(sum.getValue());
		}
	}
	
	// Decrements a Word
	public void decrement() {
		// Initialize the borrow Bit to 0
		Bit borrow = new Bit();
		borrow.clear();
		
		// Iterate through each Bit and perform binary subtraction using AND operation & XOR operation
		for (int i = 0; i < words.length; i++) {
			if(words[i].getValue()) {
				words[i].clear();
				break;
			}
			else {
				words[i].set();
			}
//			// Calculate the difference and update borrow for the next iteration
//			Bit diff = words[i].xor(borrow);
//			borrow = words[i].and(borrow).or(words[i].and(diff));
//			// Update the current Bit with the difference
//			words[i].set(diff.getValue());
		}
	}
	
	// returns a comma separated string t’s and f’s
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < words.length; i++) {
			result.append(words[i].getValue() ? "t" : "f");
			
			if (i  < words.length - 1) {
				result.append(", ");
			}
		}
		return result.toString();
	}
	
}
