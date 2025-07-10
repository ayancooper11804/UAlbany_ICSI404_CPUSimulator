import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

public class ALUTest {

	@Test
	public void testAdd2() {
		ALU alu = new ALU();
		
		Word operand1 = new Word();
		operand1.set(3);
		Word operand2 = new Word();
		operand2.set(5);
		Word result = alu.add2(operand1, operand2);
		assertEquals(8, result.getUnsigned());
		
		Word operand3 = new Word();
		operand3.set(7);
		Word operand4 = new Word();
		operand4.set(-2);
		assertEquals(-2, operand4.getSigned());
		
		Word result2 = alu.add2(operand3, operand4);
		System.out.println(operand3.toString());
		System.out.println(operand4.toString());
		System.out.println(result2.toString());
		assertEquals(5, result2.getSigned());
	}
	
	@Test
	public void testAdd4() {
		ALU alu = new ALU();
		Word operand1 = new Word();
		Word operand2 = new Word();
		Word operand3 = new Word();
		Word operand4 = new Word();
		
		operand1.set(5);
		operand2.set(3);
		operand3.set(2);
		operand4.set(4);
		System.out.println("\n\n\n"+operand1+"\n"+operand2+"\n"+operand3+"\n"+operand4);
		Word result = alu.add4(operand1, operand2, operand3, operand4);
		assertEquals(14, result.getUnsigned());
		
		operand1.set(8);
		operand2.set(4);
		operand3.set(-3);
		assertEquals(-3, operand3.getSigned());
		operand4.set(-2);
		assertEquals(-2, operand4.getSigned());
		Word result2 = alu.add4(operand1, operand2, operand3, operand4);
		assertEquals(7, result2.getSigned());
	}
	
	@Test
	public void testSubtract() {
		ALU alu = new ALU();
		
		Word operand1 = new Word();
		Word operand2 = new Word();
		operand1.set(23);
		operand2.set(14);
		Word result = alu.subtract(operand1, operand2);
		assertEquals(9, result.getUnsigned());
	}
	
	@Test
	public void testMultiply() {
		ALU alu = new ALU();
		
		Word operand1 = new Word();
		Word operand2 = new Word();
		operand1.set(2);
		operand2.set(3);
		Word result = alu.multiply(operand1, operand2);
		assertEquals(6, result.getUnsigned());
	}
	
	@Test
	public void testAndOperation() {
		ALU alu = new ALU();
		
		Bit andbit0 = new Bit();
		Bit andbit1 = new Bit();
		Bit andbit2 = new Bit();
		Bit andbit3 = new Bit();
		andbit0.set();
		Bit[] andOperation = {andbit0, andbit1, andbit2, andbit3};
		
		alu.op1.set(10);
		alu.op2.set(5);
		alu.doOperation(andOperation);
		assertEquals(0, alu.result.getSigned());
		
		alu.op1.set(-5);
		assertEquals(-5, alu.op1.getSigned());
		alu.op2.set(-3);
		assertEquals(-3, alu.op2.getSigned());
		alu.doOperation(andOperation);
		//assertEquals(-7, alu.result.getSigned());
		
		alu.op1.set(-10);
		assertEquals(-10, alu.op1.getSigned());
		alu.op2.set(0);
		alu.doOperation(andOperation);
		assertEquals(0, alu.result.getSigned());
		
		alu.op1.set(7);
		alu.op2.set(0);
		alu.doOperation(andOperation);
		assertEquals(0, alu.result.getSigned());
	}
	
	@Test
	public void testOrOperation() {
		ALU alu = new ALU();
		
		Bit bit0 = new Bit();
		Bit bit1 = new Bit();
		Bit bit2 = new Bit();
		Bit bit3 = new Bit();
		bit0.set();
		bit3.set();
		Bit[] orOperation = {bit0, bit1, bit2, bit3};
		
		alu.op1.set(10);
		alu.op2.set(5);
		alu.doOperation(orOperation);
		assertEquals(15, alu.result.getSigned());
	}
	
	@Test
	public void testXorOperation() {
		ALU alu = new ALU();
		
		Bit bit0 = new Bit();
		Bit bit1 = new Bit();
		Bit bit2 = new Bit();
		Bit bit3 = new Bit();
		bit0.set();
		bit2.set();
		Bit[] xorOperation = {bit0, bit1, bit2, bit3};
		
		alu.op1.set(10);
		alu.op2.set(5);
		alu.doOperation(xorOperation);
		assertEquals(15, alu.result.getSigned());

		alu.op1.set(-5);
		assertEquals(-5, alu.op1.getSigned());
		alu.op2.set(-3);
		assertEquals(-3, alu.op2.getSigned());
		alu.doOperation(xorOperation);
		assertEquals(6, alu.result.getSigned());
	}
	
	@Test
	public void testNotOperation() {
		ALU alu = new ALU();
		
		Bit bit0 = new Bit();
		Bit bit1 = new Bit();
		Bit bit2 = new Bit();
		Bit bit3 = new Bit();
		bit0.set();
		bit2.set();
		bit3.set();
		Bit[] notOperation = {bit0, bit1, bit2, bit3};
		
		alu.op1.set(-10);
		assertEquals(-10, alu.op1.getSigned());
		System.out.println(alu.op1.toString());
		alu.doOperation(notOperation);
		assertEquals(9, alu.result.getSigned());
	}
	
	@Test
	public void testLeftShiftOperation() {
		ALU alu = new ALU();
		
		Bit bit0 = new Bit();
		Bit bit1 = new Bit();
		Bit bit2 = new Bit();
		Bit bit3 = new Bit();
		bit0.set();
		bit1.set();
		Bit[] leftShifttOperation = {bit0, bit1, bit2, bit3};
		
		alu.op1.set(10);
		alu.op2.set(2);
		alu.doOperation(leftShifttOperation);
		assertEquals(2, alu.result.getSigned());
		
		alu.op1.set(-10);
		assertEquals(-10, alu.op1.getSigned());
		alu.op2.set(3);
		alu.doOperation(leftShifttOperation);
		//assertEquals(-1, alu.result.getSigned());
		
		alu.op1.set(0);
		alu.op2.set(4);
		alu.doOperation(leftShifttOperation);
		assertEquals(0, alu.result.getSigned());
	}
	
	@Test
	public void testRighShiftOperation() {
		ALU alu = new ALU();
		
		Bit bit0 = new Bit();
		Bit bit1 = new Bit();
		Bit bit2 = new Bit();
		Bit bit3 = new Bit();
		bit0.set();
		bit1.set();
		bit3.set();
		Bit[] rightShifttOperation = {bit0, bit1, bit2, bit3};
		
		alu.op1.set(10);
		alu.op2.set(2);
		alu.doOperation(rightShifttOperation);
		assertEquals(40, alu.result.getSigned());
		
		alu.op1.set(-10);
		assertEquals(-10, alu.op1.getSigned());
		alu.op2.set(3);
		alu.doOperation(rightShifttOperation);
		//assertEquals(-80, alu.result.getSigned());
		
		alu.op1.set(0);
		alu.op2.set(4);
		alu.doOperation(rightShifttOperation);
		assertEquals(0, alu.result.getSigned());
	}
	
	@Test
	public void testRealSubtract() {
		ALU alu = new ALU();
		
		Bit bit0 = new Bit();
		Bit bit1 = new Bit();
		Bit bit2 = new Bit();
		Bit bit3 = new Bit();
		bit0.set();
		bit1.set();
		bit2.set();
		bit3.set();
		
		Bit[] sub = {bit0, bit1, bit2, bit3};
		
		alu.op1.set(10);
		alu.op2.set(2);
		alu.doOperation(sub);
		assertEquals(8, alu.result.getSigned());
	}
}
