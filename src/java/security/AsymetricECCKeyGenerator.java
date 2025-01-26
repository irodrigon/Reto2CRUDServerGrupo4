/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;


import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;


public class AsymetricECCKeyGenerator {

    public static void main(String[] args) {
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC","BC");
            keyGen.initialize(256);
            KeyPair keyPair = keyGen.generateKeyPair();
            
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            
            FileOutputStream outputPublic = new FileOutputStream("Public.key");
            outputPublic.write(publicKey.getEncoded());
            outputPublic.close();
            
            FileOutputStream outputPrivate = new FileOutputStream("Private.key");
            outputPrivate.write(privateKey.getEncoded()); 
            outputPrivate.close();
            
            System.out.print("Llaves generadas");
        }catch(Exception error){
            System.out.print(error.getStackTrace());
        }
    }
}