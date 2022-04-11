package com.hangaries.controller;


import com.hangaries.constants.Status;
import com.hangaries.model.Customer;
import com.hangaries.repository.CustomerRepository;
import com.hangaries.service.customerService.Impl.CustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerServiceImpl customerService;

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @GetMapping("/customer/register")
    public ResponseEntity<Customer> registerCustomer(@RequestParam("mobno") String mobno) {
        Customer customerRet=null;
        try {
            logger.info("Register mobile number: " + mobno);
            customerRet=customerService.registerCustomer(mobno);
            return new ResponseEntity<Customer>(customerRet, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while registering customer::" + ex.getMessage());
            return new ResponseEntity<Customer>(customerRet, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/customer/update")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer newCustomer) {
        Customer retCustomer=null;
        try {
            retCustomer=customerService.updateCustomerInfo(newCustomer);
            return new ResponseEntity<Customer>(retCustomer, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while updating customer infor::" + ex.getMessage());
            return new ResponseEntity<Customer>(retCustomer, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/loginId")
    ResponseEntity getCustomerByLoginId() {

       // ResponseEntity responseEntity = new ResponseEntity<List<Customer>>(customerRepository.findByLoginId("hungries"), HttpStatus.OK);
        return null;

    }

    @GetMapping("/customer/getDtlsbyId")
    List<Customer> getCustomersById() {
        Long id = 1L;
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    @DeleteMapping("/customer/loginId")
    ResponseEntity deleteCustomerByLoginId(@RequestParam String loginId) {
       // ResponseEntity responseEntity = new ResponseEntity<List<Customer>>(customerRepository.deleteByLoginId(loginId), HttpStatus.OK);
        return null;
    }

}
