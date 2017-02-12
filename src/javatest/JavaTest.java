/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.elliptica.java.pstream.FileInputSpliterator;

class Signature{
	final private char[] signatureChars;
	public Signature(String sig){
		signatureChars = sig.toCharArray();
	}
	
	private String strArgs[] = new String[3];
	
	String s(final String pattern){
		String parts[] = pattern.split(":");
		for (int i=0; i<strArgs.length; i++){
			if (i<parts.length){
				strArgs[i] = parts[i].isEmpty()?null:parts[i];
			} else {
				strArgs[i] = null;
			}
		}
		int[] args = new int[strArgs.length];
		args[2] = (strArgs[2]==null || "1".equals(strArgs[2])) ? 1 : -1;
		boolean r = args[2]<0;
		int len = signatureChars.length;
		args[0] = (strArgs[0]!=null) ? Integer.valueOf(strArgs[0]) : (r?len-1:0);
		args[1] = (strArgs[1]!=null) ? Integer.valueOf(strArgs[1]) : (r?-1:len);
		StringBuilder sb = new StringBuilder(Math.abs(args[0]-args[1])+1);
		if (!r){
			for (int i=args[0]; i<args[1]; i++){
				sb.append(signatureChars[i]);
			}
		}else {
			for (int i=args[0]; i>args[1]; i--){
				sb.append(signatureChars[i]);
			}
		}
		return sb.toString();
	}
	String s(int index){
		return Character.toString(signatureChars[index]);
	}
	public String decrypt(){
		switch (signatureChars.length){
//			case 86:
//				return s("2:63") + s(82) + s("64:82") + s(63);
			case 93:
				return s("86:29:-1") + s(88) + s("28:5:-1");
			case 92:
				return s(25) + s("3:25") + s(0) + s("26:42") + s(79) + s("43:79") + s(91) + s("80:83");
			case 91:
				return s("84:27:-1") + s(86) + s("26:5:-1");
			case 90:
				return s(25) + s("3:25") + s(2) + s("26:40") + s(77) + s("41:77") + s(89) + s("78:81");
			case 89:
				return s("84:78:-1") + s(87) + s("77:60:-1") + s(0) + s("59:3:-1");
			case 88:
				return s("7:28") + s(87) + s("29:45") + s(55) + s("46:55") + s(2) + s("56:87") + s(28);
			case 87:
				return s("6:27") + s(4) + s("28:39") + s(27) + s("40:59") + s(2) + s("60:");
			case 86:
				return s("80:72:-1") + s(16) + s("71:39:-1") + s(72) + s("38:16:-1") + s(82) + s("15::-1");
			case 85:
				return s("3:11") + s(0) + s("12:55") + s(84) + s("56:84");
			case 84:
				return s("78:70:-1") + s(14) + s("69:37:-1") + s(70) + s("36:14:-1") + s(80) + s("13::-1");
			case 83:
				return s("80:63:-1") + s(0) + s("62:0:-1") + s(63);
			case 82:
				return s("80:37:-1") + s(7) + s("36:7:-1") + s(0) + s("6:0:-1") + s(37);
			case 81:
				return s(56) + s("79:56:-1") + s(41) + s("55:41:-1") + s(80) + s("40:34:-1") + s(0) + s("33:29:-1") + s(34) + s("28:9:-1") + s(29) + s("8:0:-1") + s(9);
			case 80:
				return s("1:19") + s(0) + s("20:68") + s(19) + s("69:80");
			case 79:
				return s(54) + s("77:54:-1") + s(39) + s("53:39:-1") + s(78) + s("38:34:-1") + s(0) + s("33:29:-1") + s(34) + s("28:9:-1") + s(29) + s("8:0:-1") + s(9);
			default:
				return null;
		}
	}
}

public class JavaTest {
	
	private static Map<String, String> getAttrMap(final String attrString){
		
		Map<String, String> attrsMap = new TreeMap<String, String>();
		
		Pattern p = Pattern.compile(".*?( |^)([a-z]{2,}=).*?.*");
		Matcher m = p.matcher(attrString);
		
		List<Integer> positions = new ArrayList<Integer>();
		List<String> names = new ArrayList<String>();
		int group = 2;
		while (m.matches()){
			int attStart = m.start(group);
			positions.add(attStart);
			int attStop = m.end(group);
			String g = m.group(group);
			names.add(g);
			m = m.region(attStop, m.end());
		}
		positions.add(attrString.length());

		for (int i=0; i<positions.size()-1; i++){
			final String name = names.get(i);
			final int startVal = positions.get(i) + name.length();
			final int stopVal = positions.get(i+1);
			String value = attrString.substring(startVal, stopVal).trim();
			try {
				value = URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException ex) {
				Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
			}
			attrsMap.put(name.substring(0, name.length()-1), value);
		}
		return attrsMap;
	}
	
	private static String[] parseLink(final String link){
		String[] result = new String[2];
		int attrStop = link.indexOf(">");
		int tagStop = link.indexOf("</a>");
		result[0] = link.substring(3, attrStop).trim();	//attrString
		result[1] = link.substring(attrStop+1, tagStop);	//textStr
		return result;
	}
	
	private static String findAttr(final String source, final String attr){
		int tagStart = source.indexOf("<" + attr + " ");
		int tagStop = source.indexOf("</" + attr + ">", tagStart );
		if (tagStart<0 || tagStop<0){
			return null;
		}
		String tag = source.substring(tagStart, tagStop + attr.length() + 3);
		return tag;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		
		long sum = 0;
		try {
			sum = stream("/home/mahairod/Develop/NetBeansProjects/NB81/nb-javac81/src/jdk.compiler/share/classes/com/sun/tools/javac/jvm/ClassReader.java")
					.mapToLong(s -> s.getBytes().length)
					.sum();
		} catch (IOException ex) {
			Logger.getLogger(JavaTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		System.out.println("Result sum:" + sum);
		
		if (sum>0){
			return;
		}
		
		String source = "0123456789abcdefhijklm";
		
		InputStream is = null;
		InputStream ers = null;
		String pythonValue = null;
		String pythonError = null;
		try {
			String[] command = new String[]{
				"/usr/bin/python",
				"-c",
				"x='" +source+"'; print x[10:3:-1]"
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
		System.out.println("python: " + pythonValue + ", error: " + pythonError);
		
		Signature ds = new Signature(source);
		System.out.println("Outcome: " + ds.s("13::-1"));
	}

	static Stream<String> stream(String fName) throws IOException{
		return StreamSupport.stream(new FileInputSpliterator(new File(fName)), true);
/*
		Files.lines(null).parallel().flatMap(new Function<String, Stream<? extends Integer>>() {
			@Override
			public Stream<? extends Integer> apply(String t) {
				return null;
			}
		});
*/
	}
}
