package CCB.NETBANK.SAFE;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

@SuppressWarnings("all")
public class TripleDesUtils {
	//������Կ key size must be 16 or 24 bytes
	private static byte[] key = "EBSEBSEBSEBSEBSEBSEBSEBS".getBytes();
	//��ʼ������ IV must be 8 bytes long
	private static byte[] ivs = "31313131".getBytes();
	
	public static final String ISONAME="ISO8859_1";

	public static final String GBKNAME="GBK";
	
	static {
		if (Security.getProvider("BC") == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}
	/**
	 * ������Կ
	 * @param newkey  size must be 16 or 24 bytes
	 */
	
	public static void setKey(byte[] newkey){
		try{
			key = newkey;
		}catch(Exception e){
			System.out.println("set key error");
		}
	}
	/**
	 * ��������
	 * @param newivs must be 8 bytes
	 */
	
	public static void setIvs(byte[] newivs){
		try{
			ivs = newivs;
		}catch(Exception e){
			System.out.println("set ivs error");
		}
	}
	/**
	 * DES������
	 * @param srcStr ��������
	 * @return
	 */
	public static String TripleDesDecrypt(String srcStr) 
	{

		SecretKeySpec desKey = new SecretKeySpec(key, "DESede");
		
		IvParameterSpec iv = new IvParameterSpec(ivs);

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DESede/CBC/PKCS7Padding","BC");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			cipher.init(Cipher.DECRYPT_MODE, desKey, iv);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		byte[] cipherText = null;
		try {
			cipherText = cipher.doFinal(Base64.decode(srcStr));
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			return new String(cipherText,GBKNAME);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
	}
	
	/**
	 * DES����
	 * @param srcStr ����ԭ��
	 * @return
	 */
	public static String TripleDesEncrypt(String srcStr) 
	{

		SecretKeySpec desKey = new SecretKeySpec(key, "DESede");
		
		IvParameterSpec iv = new IvParameterSpec(ivs);

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DESede/CBC/PKCS7Padding","BC");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			cipher.init(Cipher.ENCRYPT_MODE, desKey, iv);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		byte[] cipherText = null;
		try {
			cipherText = cipher.doFinal(srcStr.getBytes(GBKNAME));
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new String(Base64.encode(cipherText));
	
	}
	
	
	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();
		String srcStr = "TXCODE=AF1001|DATE=201305070938|sdfsdf";
//		for(int i=0;i<100000;i++){
			String a = TripleDesUtils.TripleDesEncrypt(srcStr);
			System.out.println(a);
			
			String b = TripleDesUtils.TripleDesDecrypt(a);
			System.out.println(b);
			if(srcStr.equals(b)){
				System.out.println(" ---- "+true);
			}else{
				System.err.println(false);
			}
//		}
//		long end = System.currentTimeMillis();
//		System.out.println("costTime:"+(end-start)/1000.00);
	}

}
