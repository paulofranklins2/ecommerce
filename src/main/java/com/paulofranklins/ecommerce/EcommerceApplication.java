package com.paulofranklins.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {

/*      Generate random 256 bits secret key to use for jwt token
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        String secretKey = Base64.getEncoder().encodeToString(key);
        System.out.println(secretKey);
*/
        SpringApplication.run(EcommerceApplication.class, args);
    }

}
