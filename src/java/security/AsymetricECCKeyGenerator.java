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
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class AsymetricECCKeyGenerator {

    public static void main(String[] args) {
        try{
            Security.addProvider(new BouncyCastleProvider());
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC","BC");
            
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("P-256");
            keyGen.initialize(ecSpec);
            
            KeyPair keyPair = keyGen.generateKeyPair();
            
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            
            try (FileOutputStream outputPublic = new FileOutputStream("Public.key")) {
                outputPublic.write(publicKey.getEncoded());
            }
            
            try (FileOutputStream outputPrivate = new FileOutputStream("Private.key")) {
                outputPrivate.write(privateKey.getEncoded());
            }
            
            System.out.print("Llaves generadas");
        }catch(Exception error){
            error.getStackTrace();
        }
    }
}