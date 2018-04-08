package com.tzplatform.utils.common;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressUtil {
	private static InetAddress myIpAddress = null;
	private static InetAddress[] myServer = null;

	public static void main(String args[]) {
		InetAddressUtil address = new InetAddressUtil();
		System.out.println("Your host IP is: " + InetAddressUtil.getLocalhostIP().getHostAddress());
		/*String domain = "www.jb51.net";
		System.out.println("The server domain name is: " + domain);
		InetAddress[] array = address.getServerIP(domain);
		int count = 0;
		for (int i = 1; i < array.length; i++) {
			System.out.println("ip: " + i + " "
					+ address.getServerIP(domain)[i - 1]);
			count++;
		}
		System.out.println("IP address total: " + count);*/
	}

	/**
	 * 获得 localhost 的IP地址
	 * 
	 * @return
	 */
	public static InetAddress getLocalhostIP() {
		try {
			myIpAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return (myIpAddress);
	}

	/**
	 * 获得某域名的IP地址
	 * 
	 * @param domain
	 *            域名
	 * @return
	 */
	public static InetAddress[] getServerIP(String domain) {
		try {
			myServer = InetAddress.getAllByName(domain);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return (myServer);
	}

	public static String getRemoteHost(HttpServletRequest request) {
		String forwards = request.getHeader("x-forwarded-for");
		if (forwards == null || forwards.trim().length() == 0 || "unknown".equalsIgnoreCase(forwards)) {
			forwards = request.getHeader("Proxy-Client-IP");
		}
		if (forwards == null || forwards.trim().length() == 0 || "unknown".equalsIgnoreCase(forwards)) {
			forwards = request.getHeader("WL-Proxy-Client-IP");
		}
		if (forwards == null || forwards.trim().length() == 0 || "unknown".equalsIgnoreCase(forwards)) {
			forwards = request.getRemoteAddr();
		}
		if (forwards == null || forwards.trim().length() == 0 || "unknown".equalsIgnoreCase(forwards)) {
			forwards = request.getHeader("X-Real-IP");
		}
		if (forwards != null && forwards.trim().length() > 0) {
			String[] ips = forwards.split(",");
			return ips[0];
		}
		return forwards;
	}
}