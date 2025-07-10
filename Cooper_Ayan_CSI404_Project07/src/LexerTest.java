import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

public class LexerTest {

	@Test
	public void testRegisterAndNumber() {
		String input =  "R1 R2 R3 1 2 3";
		var lex = new Lexer(input);
		lex.Lex();
		Assert.assertEquals("REGISTER(1) REGISTER(2) REGISTER(3) NUMBER(1) NUMBER(2) NUMBER(3)", lex.toString());	
	}
	
	@Test
	public void testKeywords() {
		String input = "math add subtract multiply and or not xor copy halt branch jump call push load return store peek pop equal unequal greater less greaterorequal lessorequal shift left right";
		var lex = new Lexer(input);
		lex.Lex();
		Assert.assertEquals("MATH ADD SUBTRACT MULTIPLY AND OR NOT XOR COPY HALT BRANCH JUMP CALL PUSH LOAD RETURN STORE PEEK POP EQUAL UNEQUAL GREATER LESS GREATEROREQUAL LESSOREQUAL SHIFT LEFT RIGHT", lex.toString());	
	}
	
}
