
public class Token {

	public enum TokenType {
		
		MATH, ADD, SUBTRACT, MULTIPLY, AND, OR, NOT, XOR, COPY, HALT, BRANCH, JUMP, CALL, PUSH, LOAD, RETURN, STORE, PEEK,
		POP, EQUAL, UNEQUAL, GREATER, LESS, GREATEROREQUAL, LESSOREQUAL, SHIFT, LEFT, RIGHT, REGISTER,
		
		NUMBER, SEPARATOR
	}
	
	private TokenType type;
	private String value; // Two words don't have the same value
	private int lineNumber;
	private int charPosition;
	
	// Some tokens don't have a value because it doesn't matter (new line), so we make 
	// two constructors
	public Token(TokenType type, int lineNumber, int charPosition) {
		this.type = type;
		this.value = null;
		this.lineNumber = lineNumber;
		this.charPosition = charPosition;
	}
	
	public Token(TokenType type, String value, int lineNumber, int charPosition) {
		this.type = type;
		this.value = value;
		this.lineNumber = lineNumber;
		this.charPosition = charPosition;
	}
	
	// Accesor method for Token types
	public TokenType getType() {
		return type;
	}
	
	// Accesor method for Token values
	public String getValue() {
		return value;
	}
	
	// Output token type and the token itself: WORD(Hello)
	@Override
	public String toString() {
		String tokenStr = type.toString();
		if (value != null) {
			return tokenStr += "(" + value + ")";
		}
		return tokenStr;
	}
	
}
