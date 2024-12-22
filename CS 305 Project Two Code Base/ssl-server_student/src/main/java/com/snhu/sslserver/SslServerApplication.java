package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class SslServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SslServerApplication.class, args);
    }

}

@RestController
class ServerController {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private String getHash(String input) {
        try {
            // Use SHA-256 algorithm to generate hash
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // Get the hash of the input string
            byte[] digest = messageDigest.digest(input.getBytes());

            // Return the hex string representation of the hash
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            return "Error: Unsupported hashing algorithm";  // Avoid stack traces in production
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    @RequestMapping("/hash")
    public String myHash() {
        String data = "Jeremy Brown's Really Cool Check Sum!";	// Hard coded data
        String hash = getHash(data);	// Run data through hash
        return "<p>data: " + data + "</p><p>Name of Cipher Used: SHA-256</p><p>Value: " + hash + "</p>"; 
    }
}
