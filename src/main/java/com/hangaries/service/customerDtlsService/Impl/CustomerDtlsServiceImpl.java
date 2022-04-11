package com.hangaries.service.customerDtlsService.Impl;


import com.hangaries.controller.CustomerDtlsController;
import com.hangaries.model.Customer;
import com.hangaries.model.CustomerDtls;
import com.hangaries.repository.CustomerDtlsRepository;
import com.hangaries.service.customerDtlsService.CustomerDtlsService;
import com.hangaries.service.customerService.CustomerService;
import com.hangaries.service.menuDishpointService.MenuDishpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Service
public class CustomerDtlsServiceImpl implements CustomerDtlsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDtlsServiceImpl.class);
    @Autowired
    private CustomerDtlsRepository customerDtlsRepository;


    public CustomerDtls saveCustomerDtls(CustomerDtls customerDtls) throws Exception {
        CustomerDtls retCustomerDtls=null;
        try {
            logger.debug("save customer dtls::");
            int count = customerDtlsRepository.getCustomerStatus(customerDtls.getMobileNumber(), customerDtls.getCustomerAddressType());
            if (count == 0) {
                retCustomerDtls=customerDtlsRepository.save(customerDtls);
            } else {
                retCustomerDtls=updateCustomerDtls(customerDtls);
            }

        } catch (Exception ex) {
            logger.error("Error while save customer details::");
            throw new Exception(ex);
        }
        return retCustomerDtls;
    }

    public CustomerDtls updateCustomerDtls(CustomerDtls customerDtls) throws Exception {
        CustomerDtls retCustomerDtls=null;
        CustomerDtls customerDtlsUpdate = null;
        try {
            logger.debug("update customer details::");
            customerDtlsUpdate = customerDtlsRepository.getCustometDtlsById(customerDtls.getMobileNumber(), customerDtls.getCustomerAddressType());
            customerDtlsUpdate.setAddress1(customerDtls.getAddress1());
            customerDtlsUpdate.setAddress2(customerDtls.getAddress2());
            customerDtlsUpdate.setLandmark(customerDtls.getLandmark());
            customerDtlsUpdate.setCity(customerDtls.getCity());
            customerDtlsUpdate.setUpdatedDate(new Date());
            customerDtlsUpdate.setZipCode(customerDtls.getZipCode());
            customerDtlsUpdate.setState(customerDtls.getState());
            retCustomerDtls=customerDtlsRepository.save(customerDtlsUpdate);
        } catch (Exception ex) {
            logger.error("Error while save customer details::");
            throw new Exception(ex);
        }
        return retCustomerDtls;
    }

    public List<CustomerDtls> getCustomerAddressDtlsByMobNum(String mobnum) throws Exception {
        List<CustomerDtls> customerDtlsList = null;
        try {
            customerDtlsList = customerDtlsRepository.getCustomerAddressDtlsByMobNum(mobnum,"Y");
        } catch (Exception ex) {
            logger.error("Error while retrieving address:: ");
            throw new Exception(ex);
        }
        return customerDtlsList;
    }

    public void deleteAddressByType(String mobnum,String type)throws Exception{
        try{
            customerDtlsRepository.deleteAddressByType(mobnum,type);
        }catch (Exception ex){
            throw new Exception(ex);
        }
    }
}
