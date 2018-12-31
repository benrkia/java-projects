package com.benrkia.market.managers;

import com.benrkia.market.address.Address;
import com.benrkia.market.address.City;
import com.benrkia.market.address.Country;
import com.benrkia.market.configuration.Answer;
import com.benrkia.market.dao.AddressDAO;

public class AddressManager {

    private AddressDAO addressDAO = AddressDAO.getInstance();

    public Answer addAddress(String street, City city, Country country, String postalCode){

        int pCode;

        if(country == null)
            return Answer.getInstance(false, "country must be selected");
        if(city == null)
            return Answer.getInstance(false, "city must be selected");
        if(street.length() < 3)
            return Answer.getInstance(false, "street name must be at least 3 chars");

        try {
            pCode = Integer.parseInt(postalCode);
        }catch (Exception e){
            return Answer.getInstance(false, "postal code must be a number");
        }

        if(postalCode.length() < 4)
            return Answer.getInstance(false, "postal code must be at least 4 digits");

        Address address = new Address(-1, street, city, country, pCode);
        addressDAO.add(address);

        return Answer.getInstance(true, "address has been added successfully", address);
    }

    public Answer deleteAddress(Address address){

        addressDAO.delete(address);

        return Answer.getInstance(true, "address has been deleted successfully");

    }

}
