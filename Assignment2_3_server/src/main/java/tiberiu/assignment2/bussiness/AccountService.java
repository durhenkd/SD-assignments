package tiberiu.assignment2.bussiness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tiberiu.assignment2.exceptions.AlreadyExistsException;
import tiberiu.assignment2.exceptions.InvalidRegisterInputException;
import tiberiu.assignment2.model.Admin;
import tiberiu.assignment2.model.Restaurant;
import tiberiu.assignment2.model.User;
import tiberiu.assignment2.persistence.AdminRepository;
import tiberiu.assignment2.persistence.RestaurantRepository;
import tiberiu.assignment2.persistence.UserRepository;
import tiberiu.assignment2.security.SHAPasswordEncoder;
import tiberiu.assignment2.web.dto.AdminRegistrationDTO;
import tiberiu.assignment2.web.dto.UserRegistrationDTO;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class AccountService implements UserDetailsService {

    public static final String USER_AUTHORITY = "USER";
    public static final String ADMIN_AUTHORITY = "ADMIN";

    public static final int MINIMUM_PASS_LENGTH = 8;
    private static final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[a-zA-z]+\\.[a-z]+$");
    private static final Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9_.-]+$");

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final RestaurantRepository restaurantRepository;
    private final SHAPasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(UserRepository userRepository, AdminRepository adminRepository, RestaurantRepository restaurantRepository, SHAPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This function checks if the string is an email.</br>
     * <p>
     * It uses the following regex: ^[A-Za-z0-9+_.-]+@[a-zA-z]+\.[a-z]+$
     *
     * @param email the email to be checked
     * @return true if the string has a good for,. False if otherwise.
     */
    public static boolean emailValidator(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.find();
    }

    /**
     * This function validates a username for the purposes of registering an account.</br>
     * <p>
     * The username must contain only alphanumeric characters and/or "_", ".", and "-".</br>
     * No spaces are allowed.</br>
     * <p>
     * it matches the following regex: ^[a-zA-Z0-1_.-]+$</br>
     *
     * @param username username to be validated.
     * @return true if the username has a good form, false if otherwise.
     */
    public static boolean usernameValidator(String username) {
        Matcher matcher = usernamePattern.matcher(username);
        return matcher.find();
    }

    /**
     * This functions validates the password.</br>
     * The function doesn't return anything, it just throws InvalidRegisterInputException if
     * one of the following conditions aren't met.</br>
     * <p>
     * - Password should be at least 8 characters long. </br>
     * - Should contain a number.</br>
     * - Should contain a special character.</br>
     *
     * @param password password to be validated
     * @throws InvalidRegisterInputException
     */
    public static void passwordValidator(String password) throws InvalidRegisterInputException {
        if (password.length() < MINIMUM_PASS_LENGTH)
            throw new InvalidRegisterInputException("Password should be at least " +
                    AccountService.MINIMUM_PASS_LENGTH +
                    " characters long. Current length: " + password.length() + ".");

        if (!containsOneOf(password, "1234567890"))
            throw new InvalidRegisterInputException("Password should contain at least a number.");
        if (!containsOneOf(password, ".?!@#$%^&*()_+-=<>?:[];'|"))
            throw new InvalidRegisterInputException("Password should contain at least one special character");
    }

    /**
     * @param string           the string in which we search the characters
     * @param listOfCharacters the characters to be searched, as a string
     * @return if at least one of the characters was found in the string
     */
    private static boolean containsOneOf(String string, String listOfCharacters) {
        for (char c : listOfCharacters.toCharArray()) {
            if (string.contains(c + ""))
                return true;
        }
        return false;
    }

    /***
     * Validates the input, and persists the user. This function is meant to persist new users.
     * @param user - the user object as sent by the client
     * @return the persisted user, containing the id
     * @throws AlreadyExistsException   when the email, password or username already exists
     * @throws InvalidRegisterInputException when the email or username are in an invalid format
     */
    public User save(UserRegistrationDTO user)
            throws AlreadyExistsException, InvalidRegisterInputException {
        if (usernameExists(user.getUsername()))
            throw new AlreadyExistsException("Username " + user.getUsername() + " already exists.");
        if (!AccountService.usernameValidator(user.getUsername()))
            throw new InvalidRegisterInputException(user.getUsername() + " is not a valid username.");
        if (!AccountService.emailValidator(user.getEmail()))
            throw new InvalidRegisterInputException(user.getEmail() + " is not a valid email.");
        if (emailExists(user.getEmail()))
            throw new AlreadyExistsException("Email " + user.getEmail() + " already exists.");

        AccountService.passwordValidator(user.getPassword()); //validates password, if it's not good it throws an exception

        return userRepository.save(
                new User(user.getUsername(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        passwordEncoder.encode(user.getPassword()),
                        new ArrayList<>())
        );
    }

    /***
     * Validates the input, and persists the admin. This function is meant to persist new admins.
     * @param admin - object containing the admin and restaurant information to persist, as sent by the client
     * @return the persisted admin
     * @throws InvalidRegisterInputException when the email or password are in an invalid format
     * @throws AlreadyExistsException when the email or username already exists
     */
    public Admin save(AdminRegistrationDTO admin) throws InvalidRegisterInputException, AlreadyExistsException {
        log.info("Adding Admin: " + admin);

        if (!emailValidator(admin.getEmail()))
            throw new InvalidRegisterInputException(admin.getEmail() + " is not a valid email.");
        if (emailExists(admin.getEmail()))
            throw new AlreadyExistsException("Email " + admin.getEmail() + " is already being used.");

        passwordValidator(admin.getPassword()); //validates password, if it's not good it throws an exception


        Restaurant restaurant =
                restaurantRepository.save(
                        new Restaurant(admin.getName(), admin.getLocation(), admin.getDeliveryLocations(), admin.getPhotoUrl(), new ArrayList<>()));

        return adminRepository.save(new Admin(admin.getEmail(), passwordEncoder.encode(admin.getPassword()), restaurant));
    }

    /**
     * Checks if a username already exists within a database
     *
     * @param username -
     * @return true if it's present, false otherwise
     */
    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Checks if an email already exists within a database
     *
     * @param email the mail
     * @return true if it's present, false otherwise
     */
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent() || adminRepository.findByEmail(email).isPresent();
    }

    /***
     *
     * @param username
     * @return
     */
    public Optional<?> login(String username) {
        Optional<User> user;

        log.info("Attempting to log in username: " + username);

        if (emailValidator(username))
            user = userRepository.findByEmail(username);
        else
            user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            log.info("Found user with credentials: " + user.get().getUsername() + " " + user.get().getPassHash());
            return user;
        }

        Optional<Admin> admin = adminRepository.findByEmail(username);
        if (admin.isPresent()) {
            log.info("Found user with credentials: " + admin.get().getEmail() + " " + admin.get().getPassHash());
            return admin;
        }

        return admin; //doesn't really matter, they're both empty
    }

    /***
     * this is a java spring function, avoid
     * @param username the username
     * @return an UserDetails object containing the username, password, and authorities of the user
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<?> optional = login(username);

        if (optional.isPresent()) {
            if (optional.get() instanceof User user)
                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassHash(), getUserAuthorities());

            if (optional.get() instanceof Admin user)
                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassHash(), getAdminAuthorities());
        }

        throw new UsernameNotFoundException("Invalid username or email");
    }

    /***
     * @return the authorities for a regular user
     */
    private Collection<? extends GrantedAuthority> getUserAuthorities() {
        /**
         * Basically authorities are a string
         * GrantedAuthority is just a container for such string
         * SimpleGrantedAuthority is just an implementation of GrantedAuthority
         */
        return new HashSet<>(Set.of(new SimpleGrantedAuthority(USER_AUTHORITY)));
    }

    /***
     *
     * @return the authorities for an admin
     */
    private Collection<? extends GrantedAuthority> getAdminAuthorities() {
        /**
         * Basically authorities are a string
         * GrantedAuthority is just a container for such string
         * SimpleGrantedAuthority is just an implementation of GrantedAuthority
         */
        return new HashSet<>(Set.of(new SimpleGrantedAuthority(ADMIN_AUTHORITY)));
    }

}
