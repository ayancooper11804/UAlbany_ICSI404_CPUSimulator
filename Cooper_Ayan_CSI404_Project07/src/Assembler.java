import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Assembler {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		
//		// Sum 20 integers forwards (array)
		Lexer lexer = new Lexer("copy 200 R2 \n copy 219 R3 \n copy 1 R4 \n "
				+ "branch R2 less 1 R3 \n halt \n "
				+ "load R5 R2 \n "
				+ "math add R1 R5 \n "
				+ "math add R2 R4 \n "
				+ "jump 3 \n"
				+ "halt \n");
		lexer.Lex();
//		
//		// Sum 20 integers backwards (array)
//		Lexer lexer2 = new Lexer("load 200 R1 \n load 220 R2 \n load 240 R3 \n load 260 R4 \n load 280 R5 \n load 300 R6 \n load 320 R7 \n load 340 R8 \n load 360 R9 \n load 380 R10 \n load 400 R11 \n load 420 R12 "
//				+ "\n load 440 R13 \n load 460 R14 \n load 480 R15 \n load 500 R16 \n load 520 R17 \n load 540 R18 \n load 560 R19 \n load 580 R20 \n "
//				+ "math R21 add R20 R19 \n math R21 add R21 R18 \n math R21 add R21 R17 \n math R21 add R21 R16 \n math R21 add R21 R15 \n math R21 add R21 R14 \n math R21 add R21 R13 \n math R21 add R21 R12 \n math R21 add R21 R11 \n math R21 add R21 R10 \n math R21 add R21 R9 \n math R21 add R21 R8 \n math R21 add R21 R7 \n math R21 add R21 R6 \n math R21 add R21 R5 \n math R21 add R21 R4 \n math R21 add R21 R3 \n math R21 add R21 R2 \n math R21 add R21 R1 \n halt \n");
//		lexer2.Lex();
//		
//		// Sum 20 integers in a LinkedList
//		Lexer lexer3 = new Lexer("load 200 R1 \n load 220 R2 \n load 240 R3 \n load 260 R4 \n load 280 R5 \n load 300 R6 \n load 320 R7 \n load 340 R8 \n load 360 R9 \n load 380 R10 \n load 400 R11 \n load 420 R12 \n load 440 R13 \n load 460 R14 \n load 480 R15 \n load 500 R16 \n load 520 R17 \n load 540 R18 \n load 560 R19 \n load 580 R20 \n load R1 \n load R2 \n load R3 \n load R4 \n load R5 \n load R6 \n load R7 \n load R8 \n load R9 \n load R10 \n load R11 \n load R12 \n load R13 \n load R14 \n load R15 \n load R16 \n load R17 \n load R18 \n load R19 \n load R20 \n math R21 add R1 R2 \n math R21 add R21 R3 \n math R21 add R21 R4 \n math R21 add R21 R5 \n math R21 add R21 R6 \n math R21 add R21 R7 \n math R21 add R21 R8 \n math R21 add R21 R9 \n math R21 add R21 R10 \n math R21 add R21 R11 \n math R21 add R21 R12 \n math R21 add R21 R13 \n math R21 add R21 R14 \n math R21 add R21 R15 \n math R21 add R21 R16 \n math R21 add R21 R17 \n math R21 add R21 R18 \n math R21 add R21 R19 \n math R21 add R21 R20 \n halt \n");
//		lexer3.Lex();
//		
		// Parse the tokenized content
		Parser parser = new Parser(lexer.tokens);
		String[] binaryOutput = parser.Parse();
		
//		for(int i = 0;i < binaryOutput.length;i++)
//			System.out.println(binaryOutput[i]);
		
		// Load memory with binaryOutput
		MainMemory memory = new MainMemory();
		Processor process = new Processor();
		memory.load(binaryOutput);
		
		// Creating address and numbers to write into memory
		Word address1 = new Word(); address1.set(200);
		Word address2 = new Word(); address2.set(220);
		Word address3 = new Word(); address3.set(240);
		Word address4 = new Word(); address4.set(260);
		Word address5 = new Word(); address5.set(280);
		Word address6 = new Word(); address6.set(300);
		Word address7 = new Word(); address7.set(320);
		Word address8 = new Word(); address8.set(340);
		Word address9 = new Word(); address9.set(360);
		Word address10 = new Word(); address10.set(380);
		Word address11 = new Word(); address11.set(400);
		Word address12 = new Word(); address12.set(420);
		Word address13 = new Word(); address13.set(440);
		Word address14 = new Word(); address14.set(460);
		Word address15 = new Word(); address15.set(480);
		Word address16 = new Word(); address16.set(500);
		Word address17 = new Word(); address17.set(520);
		Word address18 = new Word(); address18.set(540);
		Word address19 = new Word(); address19.set(560);
		Word address20 = new Word(); address20.set(580);
		
		Word num1 = new Word(); num1.set(1);
		Word num2 = new Word(); num2.set(2);
		Word num3 = new Word(); num3.set(3);
		Word num4 = new Word(); num4.set(4);
		Word num5 = new Word(); num5.set(5);
		Word num6 = new Word(); num6.set(6);
		Word num7 = new Word(); num7.set(7);
		Word num8 = new Word(); num8.set(8);
		Word num9 = new Word(); num9.set(9);
		Word num10 = new Word(); num10.set(10);
		Word num11 = new Word(); num11.set(11);
		Word num12 = new Word(); num12.set(12);
		Word num13 = new Word(); num13.set(13);
		Word num14 = new Word(); num14.set(14);
		Word num15 = new Word(); num15.set(15);
		Word num16 = new Word(); num16.set(16);
		Word num17 = new Word(); num17.set(17);
		Word num18 = new Word(); num18.set(18);
		Word num19 = new Word(); num19.set(19);
		Word num20 = new Word(); num20.set(20);
		
		MainMemory.write(address1, num1);
		MainMemory.write(address2, num2);
		MainMemory.write(address3, num3);
		MainMemory.write(address4, num4);
		MainMemory.write(address5, num5);
		MainMemory.write(address6, num6);
		MainMemory.write(address7, num7);
		MainMemory.write(address8, num8);
		MainMemory.write(address9, num9);
		MainMemory.write(address10, num10);
		MainMemory.write(address11, num11);
		MainMemory.write(address12, num12);
		MainMemory.write(address13, num13);
		MainMemory.write(address14, num14);
		MainMemory.write(address15, num15);
		MainMemory.write(address16, num16);
		MainMemory.write(address17, num17);
		MainMemory.write(address18, num18);
		MainMemory.write(address19, num19);
		MainMemory.write(address20, num20);
		
		// Process the loaded memory
		process.run();
//		System.out.println(process.registers[21].getSigned());	
		
//		Lexer lexer = new Lexer("copy 200 R2 \n copy 219 R3 \n copy 1 R4 \n "
//				+ "branch R2 less 1 R3 \n halt \n "
//				+ "load R5 R2 \n "
//				+ "math add R1 R5 \n "
//				+ "math add R2 R4 \n "
//				+ "jump 3 \n");
//		lexer.Lex();
//		Parser parser = new Parser(lexer.tokens);
//		String[] output = parser.Parse();
//		MainMemory memory = new MainMemory();
//		Processor process = new Processor();
//		memory.load(output);
//		
//		int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
//		int realResult = 0;
//		
//		for (int i = 200; i < 220; i++) {
//			Word address = new Word();
//			Word value = new Word();
//			address.set(i);
//			value.set(numbers[i-200]);
//			MainMemory.write(address, value);
//			realResult += numbers[i-200];
//		}
//		
//		process.run();
		
		
	}

}
