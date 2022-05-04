package com.hangaries.service.customerDtlsService.Impl;


import com.hangaries.model.CustomerDtls;
import com.hangaries.repository.CustomerDtlsRepository;
import com.hangaries.service.customerDtlsService.CustomerDtlsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.hangaries.config.HangariesConstants.STATUS_Y;

@Service
public class CustomerDtlsServiceImpl implements CustomerDtlsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerDtlsServiceImpl.class);

    @Autowired
    private CustomerDtlsRepository customerDtlsRepository;


    public CustomerDtls saveCustomerDtls(CustomerDtls customerDtls) throws Exception {
        CustomerDtls retCustomerDtls = null;
        try {
            logger.debug("save customer dtls::");
            int count = customerDtlsRepository.getCustomerStatus(customerDtls.getMobileNumber(), customerDtls.getCustomerAddressType(), STATUS_Y);
            if (count == 0) {
                retCustomerDtls = customerDtlsRepository.save(customerDtls);
            } else {
                retCustomerDtls = updateCustomerDtls(customerDtls);
            }

        } catch (Exception ex) {
            logger.error("Error while save customer details::");
            throw new Exception(ex);
        }
        return retCustomerDtls;
    }

    public CustomerDtls updateCustomerDtls(CustomerDtls customerDtls) throws Exception {
        CustomerDtls retCustomerDtls = null;
        CustomerDtls customerDtlsUpdate = null;
        try {
            logger.debug("update customer details::");
            customerDtlsUpdate = customerDtlsRepository.getCustometDtlsById(customerDtls.getMobileNumber(), customerDtls.getCustomerAddressType(), STATUS_Y);
            customerDtlsUpdate.setAddress1(customerDtls.getAddress1());
            customerDtlsUpdate.setAddress2(customerDtls.getAddress2());
            customerDtlsUpdate.setLandmark(customerDtls.getLandmark());
            customerDtlsUpdate.setCity(customerDtls.getCity());
            customerDtlsUpdate.setUpdatedDate(new Date());
            customerDtlsUpdate.setZipCode(customerDtls.getZipCode());
            customerDtlsUpdate.setState(customerDtls.getState());
            retCustomerDtls = customerDtlsRepository.save(customerDtlsUpdate);
        } catch (Exception ex) {
            logger.error("Error while save customer details::");
            throw new Exception(ex);
        }
        return retCustomerDtls;
    }

    public List<CustomerDtls> getCustomerAddressDtlsByMobNum(String mobnum) throws Exception {
        List<CustomerDtls> customerDtlsList = null;
        try {
            customerDtlsList = customerDtlsRepository.getCustomerAddressDtlsByMobNum(mobnum, STATUS_Y);
        } catch (Exception ex) {
            logger.error("Error while retrieving address:: ");
            throw new Exception(ex);
        }
        return customerDtlsList;
    }

    public int updateAddressStatusByType(String mobnum, String type, String status) throws Exception {
        return customerDtlsRepository.updateAddressStatusByType(mobnum, type, status);
    }

}
