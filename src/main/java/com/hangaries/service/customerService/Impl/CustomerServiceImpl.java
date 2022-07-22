package com.hangaries.service.customerService.Impl;

import com.hangaries.model.Customer;
import com.hangaries.model.Menu;
import com.hangaries.repository.CustomerRepository;
import com.hangaries.service.customerService.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

//    public Customer registerCustomer() throws Exception {
//        Customer menuList = null;
//        try {
//            menuList = menuRepository.getAllMenuItems();
//        } catch (Exception ex) {
//            logger.error("Error while getting menuitems::" + ex.getMessage());
//            throw new Exception(ex);
//        }
//        return menuList;
//    }
    public Customer registerCustomer(String mobnum) throws Exception {
        Customer customerRes=null;
        try {
            Customer customer=new Customer();
            int count = customerRepository.getCustomerRegisterStatus(mobnum);
            if(count==0){
                customer.setMobileNumber(mobnum);
                customer.setFirstName("");

                customerRes=customerRepository.save(customer);
            }
            if(count==1){
                customerRes=customerRepository.getCustomerById(mobnum);
            }
        } catch (Exception ex) {
            logger.error("Error while saving registration details::"+ex.getMessage());
            throw new Exception(ex);
        }

        return customerRes;
    }

    public Customer getCustomerDtlsByMobNum(String mobnum) throws Exception {
        Customer customerList = null;
        try {
            customerList = customerRepository.getCustomerDtlsByMobNum(mobnum);
        } catch (Exception ex) {
            logger.error("Error while retrieving customer details:: ");
            throw new Exception(ex);
        }
        return customerList;

    }


    public Customer updateCustomerInfo(Customer customer)throws  Exception{
        Customer retCustomer=null;
        try{
            long customerId=customerRepository.getCustomerIdByMobNo(customer.getMobileNumber());
            Customer customerUpdate=customerRepository.getOne(customerId);
            customerUpdate.setEmailId(customer.getEmailId());
            customerUpdate.setFirstName(customer.getFirstName());
            customerUpdate.setLastName(customer.getLastName());
            customerUpdate.setCreatedBy(customer.getCreatedBy());
            customerUpdate.setAlternativeContactId(customer.getAlternativeContactId());
            customerUpdate.setRestaurantId(customer.getRestaurantId());
            customerUpdate.setCustomerActiveStatus(customer.getCustomerActiveStatus());
            customerUpdate.setMiddleName(customer.getMiddleName());
            customerUpdate.setRestaurantId(customer.getRestaurantId());
            retCustomer=customerRepository.save(customerUpdate);
        }catch (Exception ex){
            throw new Exception(ex);
        }
        return retCustomer;
    }

}
