package com.hangaries.service.customerDtlsService;

import com.hangaries.model.CustomerDtls;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerDtlsService {

    CustomerDtls saveCustomerDtls(CustomerDtls customerDtls) throws Exception;

    CustomerDtls updateCustomerDtls(CustomerDtls customerDtls) throws Exception;

    List<CustomerDtls> getCustomerAddressDtlsByMobNum(String restaurantId, String mobnum) throws Exception;
}
