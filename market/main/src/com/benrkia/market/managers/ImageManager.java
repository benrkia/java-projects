package com.benrkia.market.managers;

import com.benrkia.market.configuration.Answer;
import com.benrkia.market.dao.ImageDAO;
import com.benrkia.market.products.Image;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ImageManager {

    private ImageDAO imageDAO = ImageDAO.getInstance();

    public Answer addImage(String path, String title, String extension){

        if(title.length() < 5)
            return Answer.getInstance(false, "title must be at least 5 chars");

        /*
        *
        * [!] Image upload instructions...
        *
        * */

        Image image = new Image(-1, generateName(title), title, extension);
        imageDAO.add(image);

        return Answer.getInstance(true, "image has been added successfully", image);
    }

    private String generateName(String title){
        String name=title+String.valueOf(System.currentTimeMillis());
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedName = md.digest(name.getBytes(StandardCharsets.UTF_8));

            name = new String(hashedName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return name;
    }

}
