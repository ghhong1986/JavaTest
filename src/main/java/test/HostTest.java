package test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.junit.Test;

public class HostTest {

	@Test
	public void testHost(){
		System.out.println(normalizeHostName(""));
	}
	  // normalize host removing any url scheme.
	  // input can be null, host, or url_prefix://host
	 public   String normalizeHostName(String host)  {

	    if (host == null || host.length() == 0) {
	      String hostaddress;
	      try {
	        hostaddress = InetAddress.getLocalHost().getHostAddress();
	      } catch (UnknownHostException e) {
	        hostaddress = "127.0.0.1";   // cannot resolve system hostname, fall through
	      }
	      // Re-get the IP again for "127.0.0.1", the other case we trust the hosts
	      // file is right.
	      if ("127.0.0.1".equals(hostaddress)) {
	        Enumeration<NetworkInterface> netInterfaces = null;
	        try {
	          netInterfaces = NetworkInterface.getNetworkInterfaces();
	          while (netInterfaces.hasMoreElements()) {
	            NetworkInterface ni = netInterfaces.nextElement();
	            Enumeration<InetAddress> ips = ni.getInetAddresses();
	            while (ips.hasMoreElements()) {
	              InetAddress ip = ips.nextElement();
	              if (ip.isSiteLocalAddress()) {
	                hostaddress = ip.getHostAddress();
	              }
	            }
	          }
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	      }
	      host = hostaddress;
	    } 
	    return host;
	  }
}
