package com.hangaries.service.customerService;


import com.hangaries.model.Customer;

public interface CustomerService {
    public Customer  registerCustomer(String mobnum) throws Exception;
    public Customer updateCustomerInfo(Customer customer)throws Exception;
}
