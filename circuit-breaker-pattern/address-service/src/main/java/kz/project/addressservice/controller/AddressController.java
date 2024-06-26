package kz.project.addressservice.controller;

import kz.project.addressservice.model.Address;
import kz.project.addressservice.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/{postalCode}")
    public Address getAddressByPostalCode(@PathVariable("postalCode") String postalCode) {
        return addressService.getAddressByPostalCode(postalCode);
    }

    @GetMapping()
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

}
