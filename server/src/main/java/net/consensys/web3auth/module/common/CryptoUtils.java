package net.consensys.web3auth.module.common;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CryptoUtils {
    
    private static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";

    private CryptoUtils() {}
    
    public static Map<Integer, String> ecrecover(String signature, String message) {
        log.debug("checkSig(signature={}, message={})", signature, message);
    
         // Message
         String pref = PERSONAL_MESSAGE_PREFIX + message.length();

         byte[] msgHash = Hash.sha3((pref+message).getBytes());
         log.trace("msgHash={}", Numeric.toHexString(msgHash));

         // Signature
         byte[] array = Numeric.hexStringToByteArray(signature);
         byte v = array[64];
         if (v < 27) { v += 27; }
            
         SignatureData sd = new SignatureData(v, (byte[]) Arrays.copyOfRange(array, 0, 32), (byte[])  Arrays.copyOfRange(array, 32, 64));
         
         Map<Integer, String> addresses = new HashMap<>();
         
         // Iterate for each possible key to recover
         for(int i=0; i<4; i++) {
             BigInteger publicKey = Sign.recoverFromSignature((byte)i, new ECDSASignature(new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())), msgHash);
             log.trace("publicKey ({}) = {}", i, publicKey);
                
             if(publicKey != null) {
                 addresses.put(i, "0x" + Keys.getAddress(publicKey));
             }
         }
         
         log.debug("checkSig(signature={}, message={}) => addresses recovered {}", signature, message, addresses);
 
         return addresses;
    }
    
}