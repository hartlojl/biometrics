package system.coordinator.tests;

import system.allcommonclasses.modalities.Biometric;

/**
 * 
 * Identifies a pair of biometrics to be tested and whether it is a genuine or impostor test.
 *
 */
public class Test {

	public Biometric enroll;
	public Biometric test;
	public Boolean genuine;
	
}