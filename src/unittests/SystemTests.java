package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import system.allcommonclasses.modalities.*;
import system.allcommonclasses.utilities.Functions;
import system.makefeaturevector.fingerprintmethods.*;

public class SystemTests {

	{}// TODO tons of unit tests
	//test binning
	
	@org.junit.Test
	public void testBinsToBits() {
		Boolean result = true;
		ArrayList<Integer> bins = new ArrayList<Integer>(Arrays.asList(2,3,4,5,6,7,8,9,10,13,15,16,17)); 
		ArrayList<Integer> bits = new ArrayList<Integer>(Arrays.asList(1,2,2,3,3,3,3,4, 4, 4, 4, 4, 5)); 
		ArrayList<Integer> computedBits = new ArrayList<Integer>(); 
		int n=bins.size();
		for(int i=0; i<n; i++){
			result = result && Functions.binsToBits(bins.get(i)) == bits.get(i);
			computedBits.add(Functions.binsToBits(bins.get(i)));
		}
		assertTrue("\nexpected: " + bits + "\ncomputed: " + computedBits, result);
	}
	
	@org.junit.Test
	public void translateFingerprint() {
		FingerprintMethod method = new PathsMethod();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(500, 500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint();
		expected.minutiae.add(new Minutia(11, 11, 230));
		expected.minutiae.add(new Minutia(64, 38, 25));
		expected.minutiae.add(new Minutia(968, 332, 109));
		expected.minutiae.add(new Minutia(505, 508, 359));
		expected.minutiae.add(new Minutia(5, 8, 0));
		
		Fingerprint computed = test.translate(5, 8);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void translateFingerprintNoTranslation() {
		FingerprintMethod method = new PathsMethod();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(500, 500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint();
		expected.minutiae.add(new Minutia(6, 3, 230));
		expected.minutiae.add(new Minutia(59, 30, 25));
		expected.minutiae.add(new Minutia(963, 324, 109));
		expected.minutiae.add(new Minutia(500, 500, 359));
		expected.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint computed = test.translate(0, 0);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void rotateFingerprintDefaultCenter() {
		//FingerprintMethod method = PathsMethod.getInstance();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(-500, -500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint();
		expected.minutiae.add(new Minutia(4, 6, 260));
		expected.minutiae.add(new Minutia(36, 55, 55));
		expected.minutiae.add(new Minutia(672, 762, 139));
		expected.minutiae.add(new Minutia(-183, -683, 29));
		expected.minutiae.add(new Minutia(0, 0, 30));
		
		Fingerprint computed = test.rotate(30.0);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void rotateFingerprint() {
		FingerprintMethod method = new PathsMethod();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(-500, -500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint();
		expected.minutiae.add(new Minutia(151, 57, 330));
		expected.minutiae.add(new Minutia(116, 105, 125));
		expected.minutiae.add(new Minutia(-331, 944, 209));
		expected.minutiae.add(new Minutia(735, -354, 99));
		expected.minutiae.add(new Minutia(155, 52, 100));
		
		Fingerprint computed = test.rotate(100.0, 56, 91);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void rotateFingerprintNegativeDegrees() {
		FingerprintMethod method = new PathsMethod();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(-500, -500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint();
		expected.minutiae.add(new Minutia(-153, 45, 130));
		expected.minutiae.add(new Minutia(-136, -12, 285));
		expected.minutiae.add(new Minutia(-3, -953, 9));
		expected.minutiae.add(new Minutia(-561, 631, 259));
		expected.minutiae.add(new Minutia(-155, 52, 260));
		
		Fingerprint computed = test.rotate(-100.0, -56, 91);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	
	@org.junit.Test
	public void rotateFingerprintNoRotation() {
		FingerprintMethod method = new PathsMethod();
		
		Fingerprint test = new Fingerprint();
		test.minutiae.add(new Minutia(6, 3, 230));
		test.minutiae.add(new Minutia(59, 30, 25));
		test.minutiae.add(new Minutia(963, 324, 109));
		test.minutiae.add(new Minutia(500, 500, 359));
		test.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint expected = new Fingerprint();
		expected.minutiae.add(new Minutia(6, 3, 230));
		expected.minutiae.add(new Minutia(59, 30, 25));
		expected.minutiae.add(new Minutia(963, 324, 109));
		expected.minutiae.add(new Minutia(500, 500, 359));
		expected.minutiae.add(new Minutia(0, 0, 0));
		
		Fingerprint computed = test.rotate(0.0, 50, 34);
		
		assertTrue("expected: " + expected.toString() + "\ncomputed: " + computed.toString(), 
				computed.equals(expected));
	}
	


}
