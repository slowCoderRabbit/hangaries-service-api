package com.hangaries.service.customerDtlsService;

import com.hangaries.model.CustomerDtls;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerDtlsService {

    public CustomerDtls saveCustomerDtls(CustomerDtls customerDtls) throws Exception;

    public CustomerDtls updateCustomerDtls(CustomerDtls customerDtls) throws Exception;

    public List<CustomerDtls> getCustomerAddressDtlsByMobNum(String mobnum) throws Exception;
}
