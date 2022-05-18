package tiberiu.assignment2.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
@Slf4j
public class SHAPasswordEncoder implements PasswordEncoder {

    private static final byte[] SALT = new byte[]{5,125,77,-100, -5,34,0,-89, 2,69,-4,20, 4,20,22,-120};



    @Override
    public String encode(CharSequence rawPassword) {
        return encryptPassword(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        boolean toReturn = (encryptPassword(rawPassword.toString()).equals(encodedPassword));
        log.info("Raw Password     : " + rawPassword);
        log.info("Raw Password (ae): " + encode(rawPassword));
        log.info("Encoded Password : " + encodedPassword);
        log.info("Password matches: " + toReturn);
        return toReturn;
    }

    /**
            * This functions hashes the password through the SHA-512 hashing algorithm and returns it.</br>
            * @param input The password as a string.
            * @return The hashed password as a string (in hexadecimal).
            */
    public static String encryptPassword(String input)
    {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            md.update(SALT);
            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;

        } // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
