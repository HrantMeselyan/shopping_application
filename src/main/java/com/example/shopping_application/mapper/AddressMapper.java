package com.example.shopping_application.mapper;

import com.example.shopping_application.dto.addressDto.AddressDto;
import com.example.shopping_application.entity.Address;

/**
 * Created by Ashot Simonyan on 09.06.23.
 */

public class AddressMapper {

    public static AddressDto addressToAddressDto(Address address) {
        if (address == null) {
            return null;
        }
        AddressDto addressDto = new AddressDto();
        addressDto.setCountry(address.getCountry());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setUnitNumber(address.getUnitNumber());
        return addressDto;
    }

    public static Address addressDtoToAddress(AddressDto addressDto) {
        if (addressDto == null) {
            return null;
        }
        Address address = new Address();
        address.setCountry(addressDto.getCountry());
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setUnitNumber(addressDto.getUnitNumber());
        return address;
    }
}
