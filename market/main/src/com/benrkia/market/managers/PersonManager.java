package com.benrkia.market.managers;

import com.benrkia.market.address.Address;
import com.benrkia.market.configuration.Answer;
import com.benrkia.market.dao.PersonDAO;
import com.benrkia.market.orders.Cart;
import com.benrkia.market.users.Client;
import com.benrkia.market.users.Commercial;
import com.benrkia.market.users.Person;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.regex.Pattern;

public class PersonManager {

    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private PersonDAO personDAO = PersonDAO.getInstance();
    private boolean isLoggedIn = false;

    public Person get(long id){
        return personDAO.get(id);
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public Answer signUp(String firstName, String lastName, Date birthdate, String email, String password, String confirmPassword, Address address){

        if(firstName.length() < 3)
            return Answer.getInstance(false, "first name must be at least 3 chars");
        if(lastName.length() < 3)
            return Answer.getInstance(false, "last name must be at least 3 chars");
        if(birthdate.after(new Date()))
            return Answer.getInstance(false, "enter a valid birthday");
        if(!VALID_EMAIL_ADDRESS_REGEX.matcher(email).find())
            return Answer.getInstance(false, "enter a valid email");
        if(password.length() < 6)
            return Answer.getInstance(false, "password must be at least 6 chars");
        if(!confirmPassword.equals(password))
            return Answer.getInstance(false, "the password confirmation must match password");

        Person person = new Client(-1, firstName, lastName, birthdate, email, hashPassword(password), address, null);
        personDAO.add(person);

        return Answer.getInstance(true, "user has been added successfully");

    }

    public Answer signIn(String email, String password){

        if(isLoggedIn()) {
            return Answer.getInstance(false, "forbidden operation");
        }

        if(!VALID_EMAIL_ADDRESS_REGEX.matcher(email).find())
            return Answer.getInstance(false, "enter a valid email");
        if(password.length() < 6)
            return Answer.getInstance(false, "password must be at least 6 chars");

        Person person = personDAO.get(email, hashPassword(password));

        if(person == null)
            return Answer.getInstance(false, "email or password is incorrect");

        setLoggedIn(true);
        return Answer.getInstance(true, "you have been logged in successfully", person);
    }

    public Answer logOut(){
        if(!isLoggedIn()) {
            return Answer.getInstance(false, "forbidden operation");
        }
        setLoggedIn(false);
        return Answer.getInstance(true, "good bye");
    }

    private String hashPassword(String password){
        String hashedPassword="";
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedBPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

            hashedPassword = new String(hashedBPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashedPassword;
    }

    public Answer updateFirstName(Person person, String firstName){

        if(firstName.length() < 3)
            return Answer.getInstance(false, "first name must be at least 3 chars");

        person.setFirstName(firstName);
        personDAO.update(person);

        return Answer.getInstance(true, "first name has been updated successfully", person);

    }

    public Answer updateLastName(Person person, String lastName){

        if(lastName.length() < 3)
            return Answer.getInstance(false, "last name must be at least 3 chars");

        person.setLastName(lastName);
        personDAO.update(person);

        return Answer.getInstance(true, "last name has been updated successfully", person);
    }

    public Answer updateEmail(Person person, String email){

        if(!VALID_EMAIL_ADDRESS_REGEX.matcher(email).find())
            return Answer.getInstance(false, "enter a valid email");

        person.setEmail(email);
        personDAO.update(person);

        return Answer.getInstance(true, "email has been updated successfully", person);
    }

    public Answer updatePassword(Person person, String password, String confirmPassword){

        if(password.length() < 6)
            return Answer.getInstance(false, "password must be at least 6 chars");
        if(!confirmPassword.equals(password))
            return Answer.getInstance(false, "the password confirmation must match password");

        person.setPassword(hashPassword(password));
        personDAO.update(person);

        return Answer.getInstance(true, "password has been updated successfully", person);
    }

    public Answer updateAddress(Person person, Address address){

        person.setAddress(address);
        personDAO.update(person);

        return Answer.getInstance(true, "address has been updated successfully", person);
    }

    public Answer updateCart(Person person, Cart cart){

        ((Client)person).setCart(cart);
        personDAO.update(person);

        return Answer.getInstance(true, "address has been updated successfully", person);
    }

    public Commercial getAvailableCommercial(){
        Commercial commercial = personDAO.getAvailableCommercial();
        if(commercial == null)
            commercial = personDAO.getRandomCommercial();

        return commercial;
    }

}
