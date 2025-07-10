
public class Bit {
	
	// internal storage to represent the bit
	private Boolean storage;
	
	// Bit constructor to initialize storage
	public Bit() {
		storage = false;
	}

	// sets the value of the bit
	public void set(Boolean value) {
		storage = value;
	}
	
	// changes the value from true to false or vice versa
	public void toggle() {
		if (storage) {
			storage = false;
		}
		else {
			storage = true;
		}
	}
	
	// sets the bit to true
	public void set() {
		storage = true;
	}
	
	// sets the bit to false
	public void clear() {
		storage = false;
	}
	
	// returns the current value
	public Boolean getValue() {
		return storage;
	}
	
	// performs and on two bits and returns a new bit set to the result
	public Bit and(Bit other) {
		Bit andBit = new Bit();
		
		// Check if the current bit is true
		if (storage) {
			// If the other bit is true, set the result bit to true
			if (other.getValue()) {
				andBit.set();
			}
			else {
				// If the other bit is false, set the result bit to false
				andBit.clear();
			}
		}
		else {
			// If the current bit is false, set the result bit to false
			andBit.clear();
		}
		return andBit;
	}
	
	// performs or on two bits and returns a new bit set to the result
	public Bit or(Bit other) {
		Bit orBit = new Bit();
		
		// Check if the current bit is false
		if (storage == false) {
			// If the other bit is false, set the result bit to false
			if (other.getValue() == false) {
				orBit.clear();
			}
			else {
				// If the other bit is true, set the result bit to true
				orBit.set();
			}
		}
		else {
			// If the current bit is true, set the result bit to true
			orBit.set();
		}
		return orBit;
	}
	
	// performs xor on two bits and returns a new bit set to the result
	public Bit xor(Bit other) {
		Bit xorBit = new Bit();
		
		// Check if the current bit is true
		if (storage) {
			// If the other bit is true, set the result bit to false
			if (other.getValue()) {
				xorBit.clear();
			}
			else {
				// If the other bit is false, set the result bit to true
				xorBit.set();
			}
		}
		
		// Check if the current bit is false
		else {
			// If the other bit is false, set the result bit to false
			if (other.getValue() == false) {
				xorBit.clear();
			}
			else {
				// If the other bit is true, set the result bit to true
				xorBit.set();
			}
		}
		return xorBit;
	}
	
	// performs not on the existing bit, returning the result as a new bit
	public Bit not() {
		Bit notBit = new Bit();
		if (storage ) {
			notBit.clear();
		}
		else {
			notBit.set();
		}
		return notBit;
	}
	
	// returns “t” or “f”
	public String toString() {
		if (storage) {
			return "t";
		}
		else {
			return "f";
		}
	}
	
}
