package bussiness;

import Exceptions.*;
import model.Agency;
import model.User;
import persistence.PersistenceClass;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class AccountService {

    public static final int MINIMUM_PASS_LENGTH = 8;
    private static final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[a-zA-z]+\\.[a-z]+$");
    private static final Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9_.-]+$");

    private static final byte[] SALT = new byte[]{5,125,77,-100, -5,34,0,-89, 2,69,-4,20, 4,20,22,-120};

    public static User userLogIn(String usernameOrEmail, String password) throws DoesNotExistException, WrongPasswordException {
        List<User> user;

        if(emailValidator(usernameOrEmail))
            user = PersistenceClass.userPersistenceInstance.queryByField("email", "'" + usernameOrEmail + "'");
        else
            user = PersistenceClass.userPersistenceInstance.queryByField("username", "'" + usernameOrEmail + "'");

        if(user.isEmpty()) throw new DoesNotExistException("No user with username/email " + usernameOrEmail + " exist.");

        if(user.get(0).getPasswordHash().equals(encryptPassword(password)))
            return user.get(0);
        else
            throw new WrongPasswordException();
    }

    public static User userRegister(String firstname, String lastname, String email, String username, String password) throws AlreadyExistsException, InvalidRegisterInputException {

        List<User> userList = PersistenceClass.userPersistenceInstance.queryByField("username", "'" + username + "'");
        if(!userList.isEmpty())             throw new AlreadyExistsException("Username " + username + " already exists.");
        if(!usernameValidator(username))    throw new InvalidRegisterInputException(username + " is not a valid username.");
        if(!emailValidator(email))          throw new InvalidRegisterInputException(email + " is not a valid email.");
        if(emailExists(email))              throw new AlreadyExistsException("Email " + email + " already exists.");

        passwordValidator(password); //validates password, if it's not good it throws an exception

        PersistenceClass.userPersistenceInstance.persist(new User(firstname, lastname, email, encryptPassword(password), username,new ArrayList<>()));
        return PersistenceClass.userPersistenceInstance.queryByField("email", "'" + email + "'").get(0);
    }

    public static Agency agencyLogIn(String email, String password) throws DoesNotExistException, WrongPasswordException {
        List<Agency> agencyList = PersistenceClass.agencyPersistenceInstance.queryByField("email", "'" + email + "'");
        if(agencyList.isEmpty()) throw new DoesNotExistException("No account with email " + email + " exist.");

        if(agencyList.get(0).getPasswordHash().equals(encryptPassword(password)))
            return agencyList.get(0);
        else
            throw new WrongPasswordException();
    }

    static public Agency agencyRegister(String name, String email, String password, String description) throws InvalidRegisterInputException, AlreadyExistsException {

        if(!emailValidator(email))  throw new InvalidRegisterInputException(email + " is not a valid email.");
        if(emailExists(email))      throw new AlreadyExistsException("Email " + email + " is already being used.");

        passwordValidator(password); //validates password, if it's not good it throws an exception

        PersistenceClass.agencyPersistenceInstance.persist(new Agency(name, email, encryptPassword(password), description, new ArrayList<>()));

        return PersistenceClass.agencyPersistenceInstance.queryByField("email", "'" + email + "'").get(0);
    }

    /**
     *
     * @param email the email
     * @return
     */
    private static boolean emailExists(String email) {
        List<User> userList = PersistenceClass.userPersistenceInstance.queryByField("email", "'" + email + "'");
        if(!userList.isEmpty()) return true;

        List<Agency> agencyList = PersistenceClass.agencyPersistenceInstance.queryByField("email", "'" + email + "'");
        return !agencyList.isEmpty();
    }

    /**
     * This function checks if the string is an email.</br>
     *
     * It uses the following regex: ^[A-Za-z0-9+_.-]+@[a-zA-z]+\.[a-z]+$
     *
     * @param email the email to be checked
     * @return true if the string has a good for,. False if otherwise.
     */
    private static boolean emailValidator(String email){
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    /**
     * This function validates a username for the purposes of registering an account.</br>
     *
     * The username must contain only alphanumeric characters and/or "_", ".", and "-".</br>
     * No spaces are allowed.</br>
     *
     * it matches the following regex: ^[a-zA-Z0-1_.-]+$</br>
     *
     * @param username username to be validated.
     * @return true if the username has a good form, false if otherwise.
     */
    private static boolean usernameValidator(String username){
        Matcher matcher = usernamePattern.matcher(username);
        return matcher.find();
    }

    /**
     * This functions validates the password.</br>
     * The function doesn't return anything, it just throws InvalidRegisterInputException if
     * one of the following conditions aren't met.</br>
     *
     * - Password should be at least 8 characters long. </br>
     * - Should contain a number.</br>
     * - Should contain a special character.</br>
     *
     * @param password password to be validated
     * @throws InvalidRegisterInputException
     */
    private static void passwordValidator(String password) throws InvalidRegisterInputException {
        if(password.length() < MINIMUM_PASS_LENGTH)
            throw new InvalidRegisterInputException("Password should be at least " +
                    AccountService.MINIMUM_PASS_LENGTH +
                    " characters long. Current length: "+password.length()+".");

        if(!containsOneOf(password, "1234567890")) throw new InvalidRegisterInputException("Password should contain at least a number.");
        if(!containsOneOf(password,".?!@#$%^&*()_+-=<>?:[];'|"))
            throw new InvalidRegisterInputException("Password should contain at least one special character");
    }

    /**
     * This functions hashes the password through the SHA-512 hashing algorithm and returns it.</br>
     * @param input The password as a string.
     * @return The hashed password as a string (in hexadecimal).
     */
    private static String encryptPassword(String input)
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

    /**
     *
     * @param string the string in which we search the characters
     * @param listOfCharacters the characters to be searched, as a string
     * @return if at least one of the characters was found in the string
     */
    private static boolean containsOneOf(String string, String listOfCharacters){
        for(char c: listOfCharacters.toCharArray()){
            if(string.contains(c+""))
                return true;
        }
        return false;
    }

}
