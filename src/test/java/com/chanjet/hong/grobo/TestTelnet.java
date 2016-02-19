package com.chanjet.hong.grobo;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;

import com.google.common.collect.Lists;


public class TestTelnet {

	@Test
	public void testScanJarFilet() throws IOException{
		 URL jarUrl =  Lists.class.getProtectionDomain().getCodeSource().getLocation();
		 JarFile jarfile = new JarFile(jarUrl.getPath());
		 Enumeration<JarEntry> entries = jarfile.entries();
		 while(entries.hasMoreElements()){
			 System.out.println(entries.nextElement().getName());
		 }
				 
	}
}
