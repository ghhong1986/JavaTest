package com.abc.hong.security;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RSACryptoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVeritfy() throws IOException {
		String  sign = "UUTki462CEOnlmNxMYDVbikhW0W0Xx+0J9w8ngvy3vCG4vKod8P66/sZe5wgsuIZDHWqx2SoyYSlpMpoHBkU0+ZeGRwudlcFWBP2eKf/rYUGhUvFFKRDyFzao+hsydg8QwEdIWCgGw0Ajt9JQ1AU+ltpV4T4bbbBsP3U0sp+3Oo=";
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9kLp9DWCjLkvgmLpxbeFT6v0Y"+
							"sa1LP3G8CoSwRusa/CPvl6jNlQ/3GkMUmxvLgwJHcXQ0F/tD4y4RpF0LRe2bdy55"+
							"E4Xmk7PNakJtsxQgjHD7oLFF6WLaA6LCetbd3lMJZ0locwJKjeJa34jx+dNYKhjR"+
							"3EvW4dcvP8kx99K27wIDAQAB";
		File file = new File("/Users/honggh/log/search.txt");
		Long flength = file.length();
		
		FileInputStream stream = null;
		String fcontent ="";
		try {
			stream = new FileInputStream(file);
			byte[] buffer = new byte[flength.intValue()];
			stream.read(buffer);
			fcontent = new String(buffer, "UTF-8");
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
		assertTrue(RSACrypto.verify(fcontent, sign, publicKey));
	}

}
