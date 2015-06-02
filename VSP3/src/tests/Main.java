package tests;


import accessor_one.SomeException110;
import accessor_one.SomeException112;
import accessor_two.SomeException304;

public class Main {
	
	
	public static void main(String[] args){
		
		if (args.length < 3){
			args = new String[]{"localhost", "25645", "false"};
		}
		
		ServerA serverA = new ServerA(args[0], Integer.parseInt(args[1]), Boolean.parseBoolean(args[2]));
		serverA.start();
		
		ServerB serverB = new ServerB(args[0], Integer.parseInt(args[1]), Boolean.parseBoolean(args[2]));
		serverB.start();
		
		Client client = new Client(args[0], Integer.parseInt(args[1]), Boolean.parseBoolean(args[2]));
		System.out.println("Starting Test...");
		
		
		System.out.println("Test: classOneAccessorOneMethodOne");
		
		try {
			String ergebnis = client.classOneAccessorOneMethodOne();
			if (!ergebnis.equals("Hello21")){
				System.out.println("Error in classOneAccessorOneMethodOne");
			}
			else {
				System.out.println("Successful");
			}
		} catch (SomeException112 e) {
			System.out.println("Error in classOneAccessorOneMethodOne");
			e.printStackTrace();
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
		System.out.println("Test: classOneAccessorOneMethodOneError");
		boolean errorThrown = false;
		try {
			client.classOneAccessorOneMethodOneError();
		} catch (SomeException112 e) {
			errorThrown = true;
		}
		if (errorThrown){
			System.out.println("Successful");
		}
		else {
			System.out.println("Error in classOneAccessorOneMethodOneError");
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
		
		
		System.out.println("Test: classOneAccessorTwoMethodOne");
		
		try {
			double ergebnisd = client.classOneAccessorTwoMethodOne();
			if (!(ergebnisd == 1)){
				System.out.println("Error in classOneAccessorTwoMethodOne");
			}
			else {
				System.out.println("Successful");
			}
		} catch (accessor_two.SomeException112 e) {
			System.out.println("Error in classOneAccessorTwoMethodOne");
			e.printStackTrace();
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
		System.out.println("Test: classOneAccessorTwoMethodOneError");
		errorThrown = false;
		try {
			client.classOneAccessorTwoMethodOneError();
		} catch (accessor_two.SomeException112 e) {
			errorThrown = true;
		}
		if (errorThrown){
			System.out.println("Successful");
		}
		else {
			System.out.println("Error in classOneAccessorTwoMethodOneError");
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
		
		System.out.println("Test: classOneAccessorTwoMethodTwo");
		
		try {
			double ergebnisd = client.classOneAccessorTwoMethodTwo();
			if (!(ergebnisd == 1)){
				System.out.println("Error in classOneAccessorTwoMethodTwo");
			}
			else {
				System.out.println("Successful");
			}
		} catch (accessor_two.SomeException112 | SomeException304 e) {
			System.out.println("Error in classOneAccessorTwoMethodTwo");
			e.printStackTrace();
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
		System.out.println("Test: classOneAccessorTwoMethodTwoError112");
		errorThrown = false;
		try {
			client.classOneAccessorTwoMethodTwoError112();
		} catch (accessor_two.SomeException112 e) {
			errorThrown = true;
		} catch (SomeException304 e) {
			e.printStackTrace();
		}
		if (errorThrown){
			System.out.println("Successful");
		}
		else {
			System.out.println("Error in classOneAccessorTwoMethodTwoError112");
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
		System.out.println("Test: classOneAccessorTwoMethodTwoError304");
		errorThrown = false;
		try {
			client.classOneAccessorTwoMethodTwoError304();
		} catch (accessor_two.SomeException112 e) {
			e.printStackTrace();
		} catch (SomeException304 e) {
			errorThrown = true;
		}
		if (errorThrown){
			System.out.println("Successful");
		}
		else {
			System.out.println("Error in classOneAccessorTwoMethodTwoError304");
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
		
		System.out.println("Test: classTwoAccessorOneMethodOne");
		
		try {
			double ergebnisd = client.classTwoAccessorOneMethodOne();
			if (!(ergebnisd == 1)){
				System.out.println("Error in classTwoAccessorOneMethodOne");
			}
			else {
				System.out.println("Successful");
			}
		} catch (SomeException110 e) {
			System.out.println("Error in classTwoAccessorOneMethodOne");
			e.printStackTrace();
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
		System.out.println("Test: classTwoAccessorOneMethodOneError");
		errorThrown = false;
		try {
			client.classTwoAccessorOneMethodOneError();
		} catch (SomeException110 e) {
			errorThrown = true;
		}
		if (errorThrown){
			System.out.println("Successful");
		}
		else {
			System.out.println("Error in classTwoAccessorOneMethodOneError");
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
		
		System.out.println("Test: classTwoAccessorOneMethodTwo");
		
		try {
			double ergebnisd = client.classTwoAccessorOneMethodTwo();
			if (!(ergebnisd == 2)){
				System.out.println("Error in classTwoAccessorOneMethodTwo");
			}
			else {
				System.out.println("Successful");
			}
		} catch (SomeException112 | SomeException110 e) {
			System.out.println("Error in classTwoAccessorOneMethodTwo");
			e.printStackTrace();
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
		System.out.println("Test: classTwoAccessorOneMethodTwoError");
		errorThrown = false;
		try {
			client.classTwoAccessorOneMethodTwoError();
		} catch (SomeException110 e) {
			e.printStackTrace();
		} catch (SomeException112 e) {
			errorThrown = true;			
		}
		if (errorThrown){
			System.out.println("Successful");
		}
		else {
			System.out.println("Error in classTwoAccessorOneMethodTwoError");
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
		
		System.out.println("Test: Async");
		
		try {
			if (client.testAsync()){
				System.out.println("Successful");
			}
			else {
				System.out.println("Error in Async");
			}
		} catch (SomeException112 e) {
			System.out.println("Error in Async");
		}
		System.out.println("-----------------------------------------------------------------------------------");
		try {
			if (client.testNull()==null){
				System.out.println("Successful");
			}
			else {
				System.out.println("Error in testNull");
			}
		} catch (SomeException112 e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("-----------------------------------------------------------------------------------");
		
		
		serverA.shutdown();
		serverB.shutdown();
		client.shutdown();
	}
}
