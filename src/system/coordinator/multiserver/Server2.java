package system.coordinator.multiserver;

//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.math.BigInteger;
//import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Key;
import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.PrivateKey;
import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.Security;
//import java.security.spec.ECFieldFp;
//import java.security.spec.ECParameterSpec;
//import java.security.spec.ECPoint;
//import java.security.spec.EllipticCurve;
import java.util.ArrayList;
import java.util.HashMap;

//import javax.crypto.Cipher;
//import javax.crypto.NoSuchPaddingException;

import java.util.HashSet;

import settings.coordinatorsettings.multiservercoordinatorsettings.ServerOneSettings;
import settings.coordinatorsettings.multiservercoordinatorsettings.ServerTwoSettings;
//import settings.hashersettings.AllHasherSettings;
import system.allcommonclasses.commonstructures.RawScores;
import system.allcommonclasses.commonstructures.Template;
import system.allcommonclasses.commonstructures.Users;
import system.hasher.Hasher;
//import system.hasher.HasherFactory.HasherEnumerator;
import system.quantizer.Quantizer;

public class Server2 extends Server {

	
	private BigInteger publicKey;
	private InterServerObjectWrapper receivedObject;
//	private InterServerObjectWrapper objectToSend;
//	private Socket S1;
	private HashMap<Long, BigInteger> keyMap;
	
	public Server2(Hasher hasher, Users enrollees) {
		
		super(hasher, enrollees);

	}


	// FIX SERVER2 and DO THE HASHMAP FOR FUZZY VAULT 
	// testing 
	
	

//	public BigInteger encrypt(Key key, BigInteger minutia, Cipher cipher){
//		
//		BigInteger encryptedMinutia = null;
//		try{
//			cipher.init(Cipher.ENCRYPT_MODE, key);
//			
//			encryptedMinutia = new BigInteger (cipher.doFinal(minutia.toByteArray()));
////		    System.out.println("Encrypted!");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		
//		return encryptedMinutia;
//	}
//	
//	public KeyPair generateKeyPair() throws Exception{
//
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECIES", "BC");
//		System.out.println("Generated key pair");
//		EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger(
//				"fffffffffffffffffffffffffffffffeffffffffffffffff", 16)), new BigInteger(
//						"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
//								"fffffffffffffffffffffffffffffffefffffffffffffffc", 16));
//
//		ECParameterSpec ecSpec = new ECParameterSpec(curve, new ECPoint(new BigInteger(
//				"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger(
//						"fffffffffffffffffffffffffffffffefffffffffffffffc", 16)), new BigInteger(
//								"fffffffffffffffffffffffffffffffefffffffffffffffc", 16), 1);
//
//		keyGen.initialize(ecSpec, new SecureRandom());
//
//		//KeyAgreement aKeyAgree = KeyAgreement.getInstance("ECDH", "BC");
//		KeyPair keyPair = keyGen.generateKeyPair();
//
//		return keyPair;
//
//	}
//	
	/*
	public void initialize() throws Exception{
		
		_privateKey = generateKeyPair().getPrivate();

		int port = ServerTwoSettings.getInstance().portNumber().getValue().intValue();
		Socket S1 = new Socket(ServerOneSettings.getInstance().ip().getValue(), ServerOneSettings.getInstance().portNumber().getValue().intValue());
		ServerSocket serv_socket = null;
		Socket client = null;
		serv_socket = new ServerSocket(port);
		while(true){
			//port = serv_socket.getLocalPort();
			client = serv_socket.accept();

			ObjectInputStream objInput = new ObjectInputStream(client.getInputStream());
			_receivedObject = (InterServerObjectWrapper)objInput.readObject();
			
			if (_receivedObject.isEnrolling()){
					enroll();
			}else{
					test();
			}
			ObjectOutputStream objOutput = new ObjectOutputStream(S1.getOutputStream());
			objOutput.writeObject(_objectToSend);
		}

		
	}
	*/
	
	
//	
//	public InterServerObjectWrapper testEnroll(InterServerObjectWrapper receivedObject, PrivateKey privateKey) throws Exception{
//		Cipher cipher = Cipher.getInstance("ECIES", "BC");
//		Template receivedEncryptedFP = (Template)receivedObject.getContents(); 
//		for (BigInteger minutiaPoint : receivedEncryptedFP.getHashes() ){
//			minutiaPoint = encrypt(privateKey, minutiaPoint, cipher);
//		}
//		InterServerObjectWrapper objectToSend = new InterServerObjectWrapper();
//		objectToSend.setContents(receivedEncryptedFP);
//		objectToSend.setOrigin("server 2");
//		return objectToSend;
//	}
//	
//	public InterServerObjectWrapper testTest(InterServerObjectWrapper receivedObject, PrivateKey privateKey) throws Exception{
//		Cipher cipher = Cipher.getInstance("ECIES","BH");
//		ArrayList<Template> fingerprintList = (ArrayList<Template>)receivedObject.getContents();
//		for (Template fingerprint : fingerprintList){
//			for (BigInteger minutiaPoint : fingerprint.getHashes()){
//				minutiaPoint = encrypt(publicKey, minutiaPoint,cipher);
//			}
//		}
//		InterServerObjectWrapper objectToSend = new InterServerObjectWrapper();
//		objectToSend.setContents(fingerprintList);
//		objectToSend.setOrigin("server 2");
//		return objectToSend;
//	}
	
	public void initialize() throws Exception{
		SimpleKeyPair keyPair = encryptionScheme.generateKeyPair();
		publicKey = keyPair.getPublic();
		keyMap = new HashMap<Long, BigInteger>(); //userID to encryption key
		int port = ServerTwoSettings.getInstance().portNumber().getValue().intValue();
		
		ServerSocket serverSocket = new ServerSocket(port);
		Socket client = null;
		
		int state = 1; 
		while (true) {
			System.out.println("State:"+state);

			switch (state){
			case 1:
//				System.out.println("Server 2 listening....");
//
//				client = fromS1.accept();
//				System.out.println("Server 2 has accepted the client");
//				ObjectInputStream objIn = new ObjectInputStream (client.getInputStream());
//				System.out.println("S2 inStream made");
//				_receivedObject = (InterServerObjectWrapper) objIn.readObject();
//				System.out.println("S2 object read");
//				System.out.println(_receivedObject.getOrigin());
//				System.out.println(_receivedObject.getContents());
//
//				System.out.println(_receivedObject.isEnrolling());
//				System.out.println(_receivedObject.getUserID());

				receivedObject = receive(serverSocket);
				
				if (receivedObject.isEnrolling()){
					state = 2;
				}else {
					state = 3;
				}
				break;
			case 2:
//				S1 = new Socket(InetAddress.getByName(ServerOneSettings.getInstance().ip().getValue()),
//						ServerOneSettings.getInstance().portNumber().getValue().intValue());
				InterServerObjectWrapper encryptedBiometric = enroll(receivedObject, publicKey);
				send(ServerOneSettings.getInstance().ip().getValue(),
						ServerOneSettings.getInstance().portNumber().getValue().intValue(),
						encryptedBiometric);
				state = 1;
				break;
			case 3:
				InterServerObjectWrapper encryptedBiometrics = test(receivedObject, publicKey);
				send(ServerOneSettings.getInstance().ip().getValue(),
						ServerOneSettings.getInstance().portNumber().getValue().intValue(),
						encryptedBiometrics);
				state = 1;
				break;
			}
				
		}
		
		// THIS NEEDS TO BE FIXED. I WILL DO THIS TOMORROW ie. Friday Morning 
		// In short, this will be accidentally trying to send out data to the same 
		// file descriptor that it just received from ie. the client which is not what we
		// want
//		ObjectOutputStream objOutput = new ObjectOutputStream(client.getOutputStream());
//		objOutput.writeObject(_objectToSend);
		
		
		

	}
	
	public InterServerObjectWrapper enroll(InterServerObjectWrapper receivedObject, BigInteger publicKey) throws Exception{
//		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//		System.out.println("adding bouncy castle");
//		Cipher cipher = Cipher.getInstance("ECIES", "BC");
//		System.out.println("got the cipher!");
		//store this users key
		this.keyMap.put(receivedObject.getUserID(), publicKey);
		Template receivedEncryptedFP = (Template) receivedObject.getContents(); 
//		System.out.println(receivedEncryptedFP);
		
		//encrypt genuines and mark with a 0 at the end
//		for(BigInteger feature : receivedEncryptedFP.getHashes() ){
//			feature = encryptionScheme.encrypt(publicKey, feature);
//			feature = feature.shiftLeft(1); //mark the genuines for chaff injection
//		}
		
		HashSet<BigInteger> outGoingFV = new HashSet<BigInteger>();
		outGoingFV.addAll(multiEncrypt(publicKey, receivedEncryptedFP.getHashes(), 0));
		
		//add in chaff points (need to be encrypted at S2)
		HashSet<BigInteger> chaff = new HashSet<BigInteger>();
		//chaff points are marked with a 1 as the least significant bit
		long numberOfChaffPoints = ServerTwoSettings.getInstance().chaffPoints().getValue();
		for(int i=0; i<numberOfChaffPoints; i++){
			chaff.add(Quantizer.getQuantizer().getRandomBigInt());
//			BigInteger chaff = Quantizer.getQuantizer().getRandomBigInt();
//			chaff = encryptionScheme.encrypt(publicKey, chaff);
//			chaff = chaff.shiftLeft(1).add(BigInteger.ONE); //mark as chaff for chaff injection
//			receivedEncryptedFP.getHashes().add(chaff);
		}
		//encrypt the chaff
		outGoingFV.addAll(multiEncrypt(publicKey, receivedEncryptedFP.getHashes(), 1));
		receivedEncryptedFP.setHashes(outGoingFV);
		//send out the fv
		InterServerObjectWrapper objectToSend = new InterServerObjectWrapper();
		objectToSend.setContents(receivedEncryptedFP);
		objectToSend.setOrigin("server 2");
		objectToSend.setEnrolling(true);
		objectToSend.setUserID(receivedObject.getUserID());
		return objectToSend;
	}
	
	public InterServerObjectWrapper test(InterServerObjectWrapper receivedObject, BigInteger publicKey){
		
		ArrayList<Template> templates = (ArrayList<Template>) receivedObject.getContents();
		for (Template template : templates){
			HashSet<BigInteger> hashes = new HashSet<BigInteger>();
			hashes.addAll(multiEncrypt(publicKey, template.getHashes()));
			template.setHashes(hashes);
			
//			for (BigInteger feature : template.getHashes()){
//				feature = encryptionScheme.encrypt(publicKey, feature);
//			}
		}
		
		
		InterServerObjectWrapper objectToSend = new InterServerObjectWrapper();
		objectToSend.setContents(templates);
		objectToSend.setOrigin("server 2");
		objectToSend.setEnrolling(false);
		objectToSend.setTesting(true);
		objectToSend.setUserID(receivedObject.getUserID());
		return objectToSend;

		
	}






	@Override
	public RawScores run() {

		try {
			initialize();
		} catch (Exception e) {
			System.out.println("Server 2 failed to initialize");
			e.printStackTrace();
		}

		return null;
	}

}
