import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
	
	@Test
	public void testMath() throws Exception {
		var lex = new Lexer("math R1 add R2 R3");
		lex.Lex();
		var parse = new Parser(lex.tokens);
		var program = parse.Parse();
		Assert.assertEquals(program[0], "11000100001110010001100000000000");
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testRun1() throws Exception {
		var lex = new Lexer("math 5 R1 \n math R2 add R1 R1 \n math R2 add R2 \n math R3 add R1 R2 \n halt");
		lex.Lex();
		var parse = new Parser(lex.tokens);
		var program = parse.Parse();
		Assert.assertEquals(program[0], "10000100000000101000000000000000");
		Assert.assertEquals(program[1], "11000010001110100001000000000000");
		Assert.assertEquals(program[2], "01000010001110010000000000000000");
		Assert.assertEquals(program[3], "11000110001110100000100000000000");
		Assert.assertEquals(program[4], "00000000000000000000000000000000");
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(program);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(25, process.registers[3].getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testRun2() throws Exception {
		var lex = new Lexer("math 6 R1 \n math 5 R2 \n branch R2 greaterorequal 1 R1 \n math R2 add R1 R2 \n halt");
		lex.Lex();
		var parse = new Parser(lex.tokens);
		var program = parse.Parse();
		Assert.assertEquals(program[0], "10000100000000011000000000000000");
		Assert.assertEquals(program[1], "10000010000000101000000000000000");
		Assert.assertEquals(program[2], "01001010000011100001000000000000");
		Assert.assertEquals(program[3], "11000010001110100000100000000000");
		Assert.assertEquals(program[4], "00000000000000000000000000000000");
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(program);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(5, process.registers[2].getSigned());
		assertEquals(5, process.PC.getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testRun3() throws Exception {
		var lex = new Lexer("math 3 R1 \n math 6 R2 \n call 1 R0 \n math R2 add R1 R2 \n halt");
		lex.Lex();
		var parse = new Parser(lex.tokens);
		var program = parse.Parse();
		Assert.assertEquals(program[0], "10000100000000110000000000000000");
		Assert.assertEquals(program[1], "10000010000000011000000000000000");
		Assert.assertEquals(program[2], "10010000000000100000000000000000");
		Assert.assertEquals(program[3], "11000010001110100000100000000000");
		Assert.assertEquals(program[4], "00000000000000000000000000000000");
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(program);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(6, process.registers[2].getSigned());
		assertEquals(5, process.PC.getSigned());
		assertEquals(3, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testRun4() throws Exception {
		var lex = new Lexer("math 5 R1 \n push R1 1 add \n halt");
		lex.Lex();
		var parse = new Parser(lex.tokens);
		var program = parse.Parse();
		Assert.assertEquals(program[1], "10011100001110100000000000000000");
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(program);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(6, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testRun5() throws Exception {
		var lex = new Lexer("math 3 R1 \n math 6 R2 \n load 1 R4 \n math R2 add R1 R2 \n halt");
		lex.Lex();
		var parse = new Parser(lex.tokens);
		var program = parse.Parse();
		Assert.assertEquals(program[2], "10100001000000100000000000000000");
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(program);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(9, process.registers[2].getSigned());
		assertEquals(5, process.PC.getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testRun6() throws Exception {
		var lex = new Lexer("math 4 R1 \n math 8 R2 \n call 4 R4 \n store 0 R1 \n math R2 add R1 R2 \n halt"); // \n store 0 R1 \n math R2 add R1 R2 \\n halt");
		lex.Lex();
		var parse = new Parser(lex.tokens);
		var program = parse.Parse();
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(program);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(8, process.registers[2].getSigned());
		assertEquals(8, process.PC.getSigned());
		assertEquals(3, memory.read(process.SP).getSigned());
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testRun7() throws Exception {
		var lex = new Lexer("math 4 R1 \n math 8 R2 \n call 4 R4 \n pop 0 R1 \n math R2 add R1 R2 \n halt");
		lex.Lex();
		var parse = new Parser(lex.tokens);
		var program = parse.Parse();
		
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(program);
		assertEquals(false, process.halted.getValue());
		process.run();
		assertEquals(true, process.halted.getValue());
		assertEquals(8, process.registers[2].getSigned());
		assertEquals(8, process.PC.getSigned());
	}
	
//	@Test
//	public void testRun8() throws Exception {
//		var lex = new Lexer("load 200 R1 \n load 220 R2 \n load 240 R3 \n load 260 R4 \n load 280 R5 \n load 300 R6 \n load 320 R7 \n load 340 R8 \n load 360 R9 \n load 380 R10 \n load 400 R11 \n load 420 R12 \n load 440 R13 \n load 460 R14 \n load 480 R15 \n load 500 R16 \n load 520 R17 \n load 540 R18 \n load 560 R19 \n load 580 R20 \n math R21 add R1 R2 \n math R21 add R21 R3 \n math R21 add R21 R4 \n math R21 add R21 R5 \n math R21 add R21 R6 \n math R21 add R21 R7 \n math R21 add R21 R8 \n math R21 add R21 R9 \n math R21 add R21 R10 \n math R21 add R21 R11 \n math R21 add R21 R12 \n math R21 add R21 R13 \n math R21 add R21 R14 \n math R21 add R21 R15 \n math R21 add R21 R16 \n math R21 add R21 R17 \n math R21 add R21 R18 \n math R21 add R21 R19 \n math R21 add R21 R20 \n halt");
//		lex.Lex();
//		var parse = new Parser(lex.tokens);
//		var program = parse.Parse();
//		//Assert.assertEquals(program[0], "10100001000000100000000000000000");
//		
//		MainMemory memory = new MainMemory();
//		Processor process = new Processor();
//		MainMemory.load(program);
//		assertEquals(false, process.halted.getValue());
//		process.run();
//		assertEquals(true, process.halted.getValue());
//		assertEquals(210, process.registers[21].getSigned());
//	}

}
