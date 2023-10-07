package com.hangaries.service.customerService;


import com.hangaries.model.Customer;

public interface CustomerService {
    Customer registerCustomer(String restaurantId, String mobnum) throws Exception;

    Customer updateCustomerInfo(Customer customer) throws Exception;

    Customer getCustomerDtlsByMobNum(String restaurantId, String mobnum) throws Exception;
}
