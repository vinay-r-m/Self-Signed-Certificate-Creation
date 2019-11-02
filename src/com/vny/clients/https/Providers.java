package com.vny.clients.https;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.Set;

import javax.crypto.KeyAgreement;


public class Providers {

	public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore", "C:\\Program Files\\Java\\jdk1.8.0_152\\jre\\lib\\security\\cacerts");

		 Provider[] providers = Security.getProviders();
	     /*   for (int i = 0; i < providers.length; i++) {
	            System.out.println((i+1) + ": " + providers[i]);
	            System.out.println(providers[i].getInfo());
	            Set<Provider.Service> svcs = providers[i].getServices();
	            for (Provider.Service svc : svcs) System.out.print(svc);
	            System.out.println();
	        }*/
	        
	        KeyAgreement ka=null;
			try {
				ka = KeyAgreement.getInstance("SHA-1");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //System.out.println(ka);
	}

}
