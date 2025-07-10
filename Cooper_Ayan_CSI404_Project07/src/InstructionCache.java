import java.util.HashMap;

public class InstructionCache {
	
	// The main cache for instruction words
	public HashMap<Integer, Word> cache;
	
	// The level 2 caches for instruction words
	public HashMap<Integer, Word> L2cache1;
	public HashMap<Integer, Word> L2cache2;
	public HashMap<Integer, Word> L2cache3;
	public HashMap<Integer, Word> L2cache4;
	
	public InstructionCache() {
		
		// Initialize cache and level 2 caches
		cache = new HashMap<Integer, Word>();
		L2cache1 = new HashMap<Integer, Word>();
		L2cache2 = new HashMap<Integer, Word>();
		L2cache3 = new HashMap<Integer, Word>();
		L2cache4 = new HashMap<Integer, Word>();
	}
	
	
	// Reads an instruction word from the cache. If the word is not found in the cache, it is fetched from main memory
	// and stored in the cache.
	@SuppressWarnings("unlikely-arg-type")
	public Word read(Word address) throws Exception {
		
		// Set the memory address to be read
		Word index = new Word();
		index.copy(address);
		
		// Check if the instruction word is present in the cache
		if (cache.containsKey((int) index.getUnsigned())) {
			if (cache.get((int) index.getUnsigned()) == null) {
				cache.replace((int) index.getUnsigned(), MainMemory.read(index));
				return MainMemory.read(index);
			}
			// Increment the clock cycle count and return the instruction word from the cache
			Processor.currentClockCycle += 10;
			return cache.get((int) index.getUnsigned());
		}
		else {
			// If the instruction word is not present in the cache, fetch it from the main memory
			cache.clear(); // Clear the cache
			
			// Fetch 8 consecutive instruction words from main memory starting at the given address
			for (int i = 0; i < 8; i++) {
				cache.put((int) index.getUnsigned(), MainMemory.read(index));
				index.increment(); // Move to the next memory address
			}
			// Increment the clock cycle count for fetching from main memory and return the instruction word from the cache
			Processor.currentClockCycle += 350;
			return cache.get((int) address.getUnsigned());
			//return readL2(address);
		}
	}
	
	// Reads an instruction word from the level 2 cache. If the word is not found in any of the cache levels, it is fetched from main memory
	// and stored in the appropriate cache level.
	public Word readL2(Word address) throws Exception {
		// Set the memory address to be read
		Word index = new Word();
		index.copy(address);
		
		// Check if the instruction word is present in any of the L2 caches
		if (L2cache1.containsKey((int) index.getUnsigned())) {
			if (L2cache1.get((int) index.getUnsigned()) == null) {
				L2cache1.replace((int) index.getUnsigned(), MainMemory.read(index));
				return MainMemory.read(index);
			}
			// Increment the clock cycle count and return the instruction word from the first L2 cache
			Processor.currentClockCycle += 20;
			return L2cache1.get((int) index.getUnsigned());
		}
		// Do a similar process for the other L2 caches
		else if (L2cache2.containsKey((int) index.getUnsigned())) {
			if (L2cache2.get((int) index.getUnsigned()) == null) {
				L2cache2.replace((int) index.getUnsigned(), MainMemory.read(index));
				return MainMemory.read(index);
			}
			Processor.currentClockCycle += 20;
			return L2cache2.get((int) index.getUnsigned());
		}
		else if (L2cache3.containsKey((int) index.getUnsigned())) {
			if (L2cache3.get((int) index.getUnsigned()) == null) {
				L2cache3.replace((int) index.getUnsigned(), MainMemory.read(index));
				return MainMemory.read(index);
			}
			Processor.currentClockCycle += 20;
			return L2cache3.get((int) index.getUnsigned());
		}
		else if (L2cache4.containsKey((int) index.getUnsigned())) {
			if (L2cache4.get((int) index.getUnsigned()) == null) {
				L2cache4.replace((int) index.getUnsigned(), MainMemory.read(index));
				return MainMemory.read(index);
			}
			Processor.currentClockCycle += 20;
			return L2cache4.get((int) index.getUnsigned());
		}
		else {
			// If the instruction word is not present in any of the L2 caches, fetch it from main memory
			
			// Clear all L2 caches
			L2cache1.clear();
			L2cache2.clear();
			L2cache3.clear();
			L2cache4.clear();
			
			// Fetch 32 consecutive instruction words and distribute them among the L2 caches
			for (int i = 0; i < 4; i++) {
				
				if (i == 0) {
					for (int j = 0; j < 8; j++) {
						L2cache1.put((int) index.getUnsigned(), MainMemory.read(index));
						index.increment();
						Processor.currentClockCycle += 20;
					}
				}
				else if (i == 1) {
					for (int j = 0; j < 8; j++) {
						L2cache2.put((int) index.getUnsigned(), MainMemory.read(index));
						index.increment();
						Processor.currentClockCycle += 20;
					}
				}
				else if (i == 2) {
					for (int j = 0; j < 8; j++) {
						L2cache3.put((int) index.getUnsigned(), MainMemory.read(index));
						index.increment();
						Processor.currentClockCycle += 20;
					}
				}
				else if (i == 3) {
					for (int j = 0; j < 8; j++) {
						L2cache4.put((int) index.getUnsigned(), MainMemory.read(index));
						index.increment();
						Processor.currentClockCycle += 20;
					}
				}
			}
			// Increment the clock cycle count for fetching from main memory
			Processor.currentClockCycle += 350;
			// Return the instruction word from the first L2 cache
			return cache.get((int) address.getUnsigned());
		}
		
	}
	
	// Writes a word to the instruction cache, following a similar structure to the readL2 method.
	public void write(Word address, Word value) throws Exception {
		
		Word index = address;
		
		// Update the word in the L2 caches
		if (L2cache1.containsKey((int) index.getUnsigned())) {
			Processor.currentClockCycle += 20;
			L2cache1.replace((int) index.getUnsigned(), value);
		}
		else if (L2cache2.containsKey((int) index.getUnsigned())) {
			Processor.currentClockCycle += 20;
			L2cache2.replace((int) index.getUnsigned(), value);
		}
		else if (L2cache3.containsKey((int) index.getUnsigned())) {
			Processor.currentClockCycle += 20;
			L2cache3.replace((int) index.getUnsigned(), value);
		}
		else if (L2cache4.containsKey((int) index.getUnsigned())) {
			Processor.currentClockCycle += 20;
			L2cache4.replace((int) index.getUnsigned(), value);
		}
		else {
			
			L2cache1.clear();
			L2cache2.clear();
			L2cache3.clear();
			L2cache4.clear();
			
			for (int i = 0; i < 4; i++) {
				
				if (i == 0) {
					for (int j = 0; j < 8; j++) {
						L2cache1.put((int) index.getUnsigned(), MainMemory.read(index));
						index.increment();
						Processor.currentClockCycle += 20;
					}
				}
				else if (i == 1) {
					for (int j = 0; j < 8; j++) {
						L2cache2.put((int) index.getUnsigned(), MainMemory.read(index));
						index.increment();
						Processor.currentClockCycle += 20;
					}
				}
				else if (i == 2) {
					for (int j = 0; j < 8; j++) {
						L2cache3.put((int) index.getUnsigned(), MainMemory.read(index));
						index.increment();
						Processor.currentClockCycle += 20;
					}
				}
				else if (i == 3) {
					for (int j = 0; j < 8; j++) {
						L2cache4.put((int) index.getUnsigned(), MainMemory.read(index));
						index.increment();
						Processor.currentClockCycle += 20;
					}
				}
			}
			
			Processor.currentClockCycle += 350;
			// Recursively call the write method to update the word in the appropriate L2 cache level
			write(address, value);
			// Write the word to main memory
			MainMemory.write(address, value);
		}
	}

}
