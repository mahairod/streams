/*
 *  Лицензия Астафьева
 * Данный программный код является собственностью Астафьева Антона Александровича
 * и может быть использован только с его личного разрешения
 */
package javatest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mahairod
 */
public class SignatureTest {
	
	public SignatureTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}
	
	private String[] patterns = new String[]{
				"2:63", "64:82",
				"86:29:-1", "28:5:-1",
				"3:25", "26:42", "43:79", "80:83",
				"84:27:-1", "26:5:-1",
				"3:25", "26:40", "41:77", "78:81",
				"84:78:-1", "77:60:-1", "59:3:-1",
				"7:28", "29:45", "46:55", "56:87",
				"6:27", "28:39", "40:59", "60:",
				"80:72:-1", "71:39:-1", "38:16:-1", "15::-1",
				"3:11", "12:55", "56:84",
				"78:70:-1", "69:37:-1", "36:14:-1", ":14",
				"80:63:-1", "62:0:-1",
				"80:37:-1", "36:7:-1", "6:0:-1",
				"79:56:-1", "55:41:-1", "40:34:-1", "33:29:-1", "28:9:-1", "8:0:-1",
				"1:19", "20:68", "69:80",
				"77:54:-1", "53:39:-1", "38:34:-1", "33:29:-1", "28:9:-1", "8:0:-1"
	};
	
	private String pythonCall(String pattern, String value){
		InputStream is = null;
		InputStream ers = null;
		String pythonValue = null;
		String pythonError = null;
		try {
			String[] command = new String[]{
				"/usr/bin/python",
				"-c",
				"x='" +value+"'; print x["+pattern+"]"
			};
			Process process = Runtime.getRuntime().exec(command);
			ers = process.getErrorStream();
			is = process.getInputStream();
			int exitCode = process.waitFor();
			System.out.println("python exits: " + exitCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			pythonValue = br.readLine();
			
			BufferedReader br2 = new BufferedReader(new InputStreamReader(ers));
			pythonError = br2.readLine();
			
			return pythonValue;
			
		} catch (IOException ex) {
			Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
		} finally{
			if (is!=null){
				try {
					is.close();
				} catch (IOException ex) {
					Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return null;
	}

	@Test
	public void testS_String() {
		String singleVal = "0123456789abcdefghijklmnopqrstuvwxyz";
		String value = singleVal+singleVal+singleVal;
		Signature signature = new Signature(value);
		for (String pattern: patterns){
			System.out.println("Testing pattern " + pattern);
			String expResult = pythonCall(pattern, value);
			String result = signature.s(pattern);
			assertEquals(expResult, result);
		}
	}

	@Test
	public void testS_int() {
		System.out.println("s");
		int index = 0;
		Signature instance = null;
		String expResult = "";
		String result = instance.s(index);
		assertEquals(expResult, result);
		fail("The test case is a prototype.");
	}

	@Test
	public void testDecrypt() {
		System.out.println("decrypt");
		Signature instance = null;
		String expResult = "";
		String result = instance.decrypt();
		assertEquals(expResult, result);
		fail("The test case is a prototype.");
	}
}