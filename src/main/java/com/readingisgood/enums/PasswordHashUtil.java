package com.readingisgood.enums;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.xml.bind.DatatypeConverter;

/**
 * A util class to encrypt and check password.
 *
 * @author tcmsaritas
 */
public enum PasswordHashUtil {

    INSTANCE;

    /**
     * Encrypt given passwort string with SHA-512 algorithm and salt.
     * 
     * @param passwordToHash
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String getSecurePassword(String passwordToHash, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] bytes = md.digest(passwordToHash.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        String generatedPassword = sb.toString();

        return generatedPassword;
    }
    /**

     * Return a random generated secret key to encrypt password.
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        // Get a random salt
        sr.nextBytes(salt);
        return DatatypeConverter.printBase64Binary(salt);

    }

    /**
     * Encrypt the given password string and compare with encrypted password.
     * 
     * @param passwordToHash
     * @param hashedPassword
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     */
    public boolean checkPassword(String passwordToHash, String hashedPassword, String salt) throws NoSuchAlgorithmException {
        return (hashedPassword != null && salt != null && getSecurePassword(passwordToHash, salt).equals(hashedPassword));
    }
}
