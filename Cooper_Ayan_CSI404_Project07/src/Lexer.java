import java.util.LinkedList;
import java.util.HashMap;

public class Lexer {
	
	private StringHandler stringHandler; // Uses StringHandler's methods to access documents
	private int lineNumber; // Tracks the current line number
	private int linePosition; // Tracks the current character position in the current line
	public LinkedList<Token> tokens; // Store tokens
	
	// HashMap to store assembler keywords
	private HashMap<String, Token.TokenType> assembler = new HashMap<String, Token.TokenType>();
	
	public Lexer(String input) {
		stringHandler = new StringHandler(input);
		lineNumber = 0;
		linePosition = 0;
		tokens = new LinkedList<Token>();
		assemblerKeywords();
	}
	
	// Helper method to initialize assembler keywords
	private void assemblerKeywords() {
		assembler.put("math", Token.TokenType.MATH);
		assembler.put("add", Token.TokenType.ADD);
		assembler.put("subtract", Token.TokenType.SUBTRACT);
		assembler.put("multiply", Token.TokenType.MULTIPLY);
		assembler.put("and", Token.TokenType.AND);
		assembler.put("or", Token.TokenType.OR);
		assembler.put("not", Token.TokenType.NOT);
		assembler.put("xor", Token.TokenType.XOR);
		assembler.put("copy", Token.TokenType.COPY);
		assembler.put("halt", Token.TokenType.HALT);
        assembler.put("branch", Token.TokenType.BRANCH);
        assembler.put("jump", Token.TokenType.JUMP);
        assembler.put("call", Token.TokenType.CALL);
        assembler.put("push", Token.TokenType.PUSH);
        assembler.put("load", Token.TokenType.LOAD);
        assembler.put("return", Token.TokenType.RETURN);
        assembler.put("store", Token.TokenType.STORE);
        assembler.put("peek", Token.TokenType.PEEK);
        assembler.put("pop", Token.TokenType.POP);
        assembler.put("equal", Token.TokenType.EQUAL);
        assembler.put("unequal", Token.TokenType.UNEQUAL);
        assembler.put("greater", Token.TokenType.GREATER);
        assembler.put("less", Token.TokenType.LESS);
        assembler.put("greaterorequal", Token.TokenType.GREATEROREQUAL);
        assembler.put("lessorequal", Token.TokenType.LESSOREQUAL);
        assembler.put("shift", Token.TokenType.SHIFT);
        assembler.put("left", Token.TokenType.LEFT);
        assembler.put("right", Token.TokenType.RIGHT);
	}
	
	// Process the next character sequence as either a NUMBER or REGISTER token
	public Token ProcessNumberOrRegister() {
		StringBuilder builder = new StringBuilder(); // StringBuilder to accumulate digits
		char currentChar = stringHandler.Peek(0); // Peek at the next character in the input stream
		
		if (Character.isDigit(currentChar)) {
			// Prcoess a number
			while (Character.isDigit(currentChar)) {
				builder.append(stringHandler.GetChar()); // Append the current digit to the builder
				currentChar = stringHandler.Peek(0); // Peek at the next character
			}
			// Create a NUMBER token with the acuumulated digit sequence
			return new Token(Token.TokenType.NUMBER, builder.toString(), lineNumber, linePosition);
		}
		
		else if (currentChar == 'R') {
			// Process a register
			stringHandler.GetChar(); // Consume the R character
			currentChar = stringHandler.Peek(0); // Peek at the next character
			
			while (Character.isDigit(currentChar)) {
				builder.append(stringHandler.GetChar()); // Append the current digit to the builder
				currentChar = stringHandler.Peek(0); // Peek at the next character
			}
			
			if (builder.length() > 0) {
				int registerNumber = Integer.parseInt(builder.toString());
				// Check if the extracted register number is within the valid range (0-31)
				if (registerNumber >= 0 && registerNumber <= 31) {
					// Create a REGISTER token with the valid register number
					return new Token(Token.TokenType.REGISTER, builder.toString(), lineNumber, linePosition);
				}
				else {
					// Throw an exception for an invalud register number
					throw new IllegalArgumentException("Invalid register number: " + builder.toString());
				}
			}
		}
		// Return null if neither a NUMBER nor REGISTER token was matched
		return null;
	}
	
	// Process the next character sequence as an assembler keyword
	public Token ProcessKeywords() {
		StringBuilder builder = new StringBuilder(); // StringBuilder to accumulate characters
		
		// Continue appending characters to the builder while they are letters
		while (Character.isLetter(stringHandler.Peek(0))) {
			builder.append(stringHandler.GetChar()); // Append the current character to the builder
			linePosition++; // Increment the current character position
		}
		
		String value = builder.toString(); // Get the accumulated string
		
		// Check if the accumulated string represents a keyword
		if (assembler.containsKey(value)) {
			// Create a token for the recognized keyword with the corresponding token type
			return new Token(assembler.get(value), null, lineNumber, linePosition);
		}
		else {
			// Return null if the accumulated string is not a keyword
			return null;
		}
	}
	
	public void Lex() {
		while (!stringHandler.IsDone()) {
			char currentChar = stringHandler.Peek(0);
			if (Character.isWhitespace(currentChar)) {
				linePosition++;
				if (currentChar == '\n') {
					tokens.add(new Token(Token.TokenType.SEPARATOR, lineNumber, linePosition)); // Add separator for new line
					lineNumber++;
					linePosition = 0; // Reset the character position for the new line
				}
				stringHandler.GetChar(); // Move past whitespace
			}
			
			else if (Character.isDigit(currentChar) || currentChar == 'R') {
				Token token = ProcessNumberOrRegister();
				if (token != null) {
					tokens.add(token);
				}
			}
			else if (Character.isLetter(currentChar)) {
				Token wordToken = ProcessKeywords(); // ProcessWord returns a token (WORD or keyword)
				tokens.add(wordToken);
			}
		}
	}
	
	// Returns a string representation of the tokens produced by the lexer
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			builder.append(t.toString());
			
			if (i < tokens.size() - 1) {
				builder.append(" ");
			}
		}
		return builder.toString();
	}

}
