import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Parser {
	
	private TokenHandler tokenHandler; // Use's TokenHandler's methods to access tokens
	public LinkedList<Token> tokens; // Store tokens
	public ArrayList<String> memory; // Used to store parsed instruction words representing memory contents
	
	public Parser(LinkedList<Token> tokens) {
		tokenHandler = new TokenHandler(tokens);
		this.tokens = tokens;
		memory = new ArrayList<>();
	}
	
	// Accepts any number of separators and returns true if it finds at least one
	public boolean AcceptSeparators() {
		boolean foundSeparator = false;
		while (!tokenHandler.MatchAndRemove(Token.TokenType.SEPARATOR).isEmpty()) {
			foundSeparator = true;
		}
		return foundSeparator;
	}
	
	public String ParseWord() throws Exception {
		// Initialize variables for parsing
		int count  = 0;
		String func = ParseALU();
		String opcode = ParseStatement();
		int register1 = -1;
		int register2 = -1;
		int register3 = -1;
		int number = -1;
		Optional<Token> numToken = tokenHandler.MatchAndRemove(Token.TokenType.NUMBER);
		StringBuilder result = new StringBuilder();
		
		// Parse tokens to construct the instruction word
		while (!AcceptSeparators() && tokenHandler.MoreTokens()) {
			if (count < 3) {
				if (register1 < 0) {
					register1 = ParseRegister();
					if (register1 >= 0) {
						count++;
					}
				}
				else if (register2 < 0) {
					register2 = ParseRegister();
					if (register2 >= 0) {
						count++;
					}
				}
				else if (register3 < 0) {
					register3 = ParseRegister();
					if (register3 >= 0) {
						count++;
					}
				}
				else {
					throw new IllegalArgumentException("Too many registers");
				}
			}

			if (func == "") {
				func = ParseALU();
			}
			
			if (opcode == "") {
				opcode = ParseALU();
			}
			
			if (number < 0) {
				if (numToken.isPresent()) {
					number = Integer.parseInt(numToken.get().getValue());
				}
				else {
					numToken = tokenHandler.MatchAndRemove(Token.TokenType.NUMBER);
				}
			}
		}
		
		// Handle default values and prepare binary representations
		if (number < 0) {
			number = 0;
		}
		if(func == "") {
			func = "0000";
			if (func.isEmpty()) {
				throw new IllegalArgumentException("Missing ALU function");
			}
		}
		
		// Get the binary opcode for registers and reverse it to match string output from our implementation for bits
		String binaryString1 = String.format("%5s", Integer.toBinaryString(register1)).replace(' ', '0');
		String binaryString2 = String.format("%5s", Integer.toBinaryString(register2)).replace(' ', '0');
		String binaryString3 = String.format("%5s", Integer.toBinaryString(register3)).replace(' ', '0');
		String myReg1 = new StringBuilder(binaryString1).reverse().toString();
		String myReg2 = new StringBuilder(binaryString2).reverse().toString();
		String myReg3 = new StringBuilder(binaryString3).reverse().toString();
		
		// Append formmatted components to the result based on register count
		if (count == 0) {
			String numString = String.format("%27s", Integer.toBinaryString(number)).replace(' ', '0');
			String myNum = new StringBuilder(numString).reverse().toString();
			result.append(ParseFormat(count)).append(opcode).append(myNum);
		}
		
		if (count == 1) {
			String numString = String.format("%18s", Integer.toBinaryString(number)).replace(' ', '0');
			String myNum = new StringBuilder(numString).reverse().toString();
			result.append(ParseFormat(count)).append(opcode).append(myReg1).append(func).append(myNum);
		}
		
		if (count == 2) {
			String numString = String.format("%13s", Integer.toBinaryString(number)).replace(' ', '0');
			String myNum = new StringBuilder(numString).reverse().toString();
			result.append(ParseFormat(count)).append(opcode).append(myReg1).append(func).append(myReg2).append(myNum);
		}
		
		if (count == 3) {
			String numString = String.format("%8s", Integer.toBinaryString(number)).replace(' ', '0');
			String myNum = new StringBuilder(numString).reverse().toString();
			result.append(ParseFormat(count)).append(opcode).append(myReg1).append(func).append(myReg2).append(myReg3).append(myNum);
		}
		
		if (count < 0 || count > 3) {
			throw new IllegalArgumentException("Invalid number of registers");
		}
		
		return result.toString();
	}
	
	// Determines the instruction format based on the count of registers used
	public String ParseFormat(int count) throws Exception {
		String format = "";
		
		if (count == 0) {
			format = "00";
		}
		else if (count == 1) {
			format = "10";
		}
		else if (count == 2) {
			format = "01";	
		}
		else if (count == 3) {
			format = "11";	
		}
		else {
			throw new IllegalArgumentException("Invalid instruction format.");
		}
		
		return format;
	}
	
	// Parses the next token as a REGISTER type and returns the corresponding register value
	public int ParseRegister() throws Exception {
		int register = -1; // Initialize the register value with -1 as a default (error) value
		
		// Match and remove the next token as a REGISTER type
		Optional<Token> regToken = tokenHandler.MatchAndRemove(Token.TokenType.REGISTER);
		// Check if the register token is present
		if (regToken.isPresent()) {
			// Extract the register value from the token's value string and parse it as an integer
			register = Integer.parseInt(regToken.get().getValue());
		}
		// Return the parsed register value (or -1 if no valid REGISTER token was found)
		return register;
	}
	
	// Parses the statement token and returns the corresponding binary representation of the statement opcode
	public String ParseStatement() throws Exception {
		
		// Match and remove tokens for each possible statement type
		Optional<Token> mathToken = tokenHandler.MatchAndRemove(Token.TokenType.MATH);
		Optional<Token> branchToken = tokenHandler.MatchAndRemove(Token.TokenType.BRANCH);
		Optional<Token> callToken = tokenHandler.MatchAndRemove(Token.TokenType.CALL);
		Optional<Token> pushToken = tokenHandler.MatchAndRemove(Token.TokenType.PUSH);
		Optional<Token> loadToken = tokenHandler.MatchAndRemove(Token.TokenType.LOAD);
		Optional<Token> storeToken = tokenHandler.MatchAndRemove(Token.TokenType.STORE);
		Optional<Token> popToken = tokenHandler.MatchAndRemove(Token.TokenType.POP);
		Optional<Token> haltToken = tokenHandler.MatchAndRemove(Token.TokenType.HALT);
		Optional<Token> copyToken = tokenHandler.MatchAndRemove(Token.TokenType.COPY);
		Optional<Token> jumpToken = tokenHandler.MatchAndRemove(Token.TokenType.JUMP);
		Optional<Token> returnToken = tokenHandler.MatchAndRemove(Token.TokenType.RETURN);
		Optional<Token> peekToken = tokenHandler.MatchAndRemove(Token.TokenType.PEEK);
		
		String statement = ""; // Initialize the statament opcode string
		
		// Determine the statement opcode based on the matched token type
		if (mathToken.isPresent()) {
			statement = "000";	
		}
		else if (haltToken.isPresent()) {
			statement = "000";
		}
		else if (copyToken.isPresent()) {
			statement = "000";
		}
		else if (branchToken.isPresent()) {
			statement = "001";
		}
		else if (jumpToken.isPresent()) {
			statement = "001";
		}
		else if (callToken.isPresent()) {
			statement = "010";
		}
		else if (pushToken.isPresent()) {
			statement = "011";
		}
		else if (loadToken.isPresent()) {
			statement = "100";
		}
		else if (returnToken.isPresent()) {
			statement = "100";
		}
		else if (storeToken.isPresent()) {
			statement = "101";
		}
		else if (popToken.isPresent()) {
			statement = "110";
		}
		else if (peekToken.isPresent()) {
			statement = "110";
		}
		else {
			throw new IllegalArgumentException("Invalid statement. Expected a token representing a statement (math, call, branch, etc.).");
		}
		
		return statement; // Return the determined statement opcode
	}
	
	// Parses the ALU operation token and return the corresponding binary opcode 
	public String ParseALU() {
		
		// Match and remove tokens for each possible ALU operation
		Optional<Token> andToken = tokenHandler.MatchAndRemove(Token.TokenType.AND);
		Optional<Token> orToken = tokenHandler.MatchAndRemove(Token.TokenType.OR);
		Optional<Token> xorToken = tokenHandler.MatchAndRemove(Token.TokenType.XOR);
		Optional<Token> notToken = tokenHandler.MatchAndRemove(Token.TokenType.NOT);
		Optional<Token> addToken = tokenHandler.MatchAndRemove(Token.TokenType.ADD);
		Optional<Token> subToken = tokenHandler.MatchAndRemove(Token.TokenType.SUBTRACT);
		Optional<Token> multToken = tokenHandler.MatchAndRemove(Token.TokenType.MULTIPLY);
		Optional<Token> shiftToken = tokenHandler.MatchAndRemove(Token.TokenType.SHIFT);
		Optional<Token> eqToken = tokenHandler.MatchAndRemove(Token.TokenType.EQUAL);
		Optional<Token> uneqToken = tokenHandler.MatchAndRemove(Token.TokenType.UNEQUAL);
		Optional<Token> lessToken = tokenHandler.MatchAndRemove(Token.TokenType.LESS);
		Optional<Token> greateqToken = tokenHandler.MatchAndRemove(Token.TokenType.GREATEROREQUAL);
		Optional<Token> greatToken = tokenHandler.MatchAndRemove(Token.TokenType.GREATER);
		Optional<Token> lesseqToken = tokenHandler.MatchAndRemove(Token.TokenType.LESSOREQUAL);
		
		String alu = ""; // Initialize the ALU opcode string
		
		// Determine the ALU opcode based on the matched token type
		if (andToken.isPresent()) {
			alu = "1000";
		}
		else if (orToken.isPresent()) {
			alu = "1001";
		}
		else if (xorToken.isPresent()) {
			alu = "1010";
		}
		else if (notToken.isPresent()) {
			alu = "1011";
		}
		else if (addToken.isPresent()) {
			alu = "1110";
		}
		else if (subToken.isPresent()) {
			alu = "1111";
		}
		else if (multToken.isPresent()) {
			alu = "0111";
		}
		else if (shiftToken.isPresent()) {
			// Match shift direction tokens
			Optional<Token> leftToken = tokenHandler.MatchAndRemove(Token.TokenType.LEFT);
			Optional<Token> rightToken = tokenHandler.MatchAndRemove(Token.TokenType.RIGHT);
			
			if (leftToken.isPresent()) {
				alu = "1100"; // Shift left opcode
			}
			else if (rightToken.isPresent()) {
				alu = "1101"; // Shift right opcode
			}
		}
		else if (eqToken.isPresent()) {
			alu = "0000";
		}
		else if (uneqToken.isPresent()) {
			alu = "0001";
		}
		else if (lessToken.isPresent()) {
			alu = "0010";
		}
		else if (greateqToken.isPresent()) {
			alu = "0011";
		}
		else if (greatToken.isPresent()) {
			alu = "0100";
		}
		else if (lesseqToken.isPresent()) {
			alu = "0101";
		}
		
		return alu; // Return the determined ALU opcode
	}
	
	// Parses tokens to construct a memory array of instruction words
	public String[] Parse() throws Exception {
		// Process tokens until there are no more tokens to process
		while(tokenHandler.MoreTokens()) {
			// Process a single instruction from tokens
			String word = ParseWord();
			// Add the parsed word to memory
			memory.add(word);
		}
		// Conver memory list to a string array
		String[] array = new String[memory.size()];
		for (int i = 0; i < memory.size(); i++) {
			array[i] = memory.get(i);
		}
		// Return the array of instruction words
		return array;
	}

}
