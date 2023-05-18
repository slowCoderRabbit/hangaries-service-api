package com.hangaries.service.wera;

import com.hangaries.model.*;
import com.hangaries.model.vo.OrderVO;
import com.hangaries.model.wera.dto.*;
import com.hangaries.model.wera.request.*;
import com.hangaries.model.wera.response.WeraOrderResponse;
import com.hangaries.repository.CustomerDtlsRepository;
import com.hangaries.repository.CustomerRepository;
import com.hangaries.repository.wera.*;
import com.hangaries.service.config.impl.ConfigServiceImpl;
import com.hangaries.service.order.impl.OrderServiceImpl;
import com.hangaries.service.store.impl.StoreServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.hangaries.constants.HangariesConstants.*;

@Service
public class WERAOrderServiceImpl {

    public static final String PIPE = "|";
    public static final String EQUAL = "=";
    private static final Logger logger = LoggerFactory.getLogger(WERAOrderServiceImpl.class);
    @Autowired
    WeraOrderJSONDumpRepository weraOrderJSONDumpRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerDtlsRepository customerDtlsRepository;

    @Autowired
    ConfigServiceImpl configService;
    @Autowired
    WERACallbackServiceImpl weraCallbackService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private WeraOrderAddonRepository weraOrderAddonRepository;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private WeraOrderItemDiscountDtlsRepository weraOrderItemDiscountDtlsRepository;
    @Autowired
    private WeraOrderSizeRepository weraOrderSizeRepository;
    @Autowired
    private WeraOrderDetailsRepository weraOrderDetailsRepository;
    @Autowired
    private WeraOrderMasterRepository weraOrderMasterRepository;

//    public ResponseEntity<WERAOrderAcceptResponse> callWERAOrderAcceptAPI(WERAOrderAcceptRequest request) {
//        logger.info("################## WERA ACCEPT ORDER - INITIATING!!! ##################");
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set(werAPIKey, werAPIValue);
//
//        HttpEntity<WERAOrderAcceptRequest> httpRequest = new HttpEntity<>(request, headers);
//        ResponseEntity<WERAOrderAcceptResponse> response = null;
////        try {
////            response = restTemplate.postForEntity(orderAcceptURL, httpRequest, WERAOrderAcceptResponse.class);
////            logger.info("WERA ACCEPT ORDER RESPONSE = [{}]", response.getBody());
////        } catch (Exception ex) {
////            logger.error(ex.getMessage());
////            logger.error("Error while accepting wera order [{}]", request.getOrder_id(), ex.getMessage());
////        }
//
//        return response;
//    }
//
//    public ResponseEntity<WERAOrderAcceptResponse> callWERAOrderAcceptAPI(String orderId) {
//        WERAOrderAcceptRequest request = null;
//        return callWERAOrderAcceptAPI(request);
//    }

    public WeraOrderResponse placeOrder(WeraOrder weraOrder) {

        String serviceMessage = "Service Unavailable";
        int status = 503;

        WeraOrderResponse weraOrderResponse = new WeraOrderResponse();
        List<ConfigMaster> configWERAAcceptOrder = configService.getConfigDetailsByCriteria(RESTAURANT_ID, ALL, WERA_ACCEPT_ORDER_FLAG);
        boolean acceptWERAOrder = false;
        if (!configWERAAcceptOrder.isEmpty()) {
            acceptWERAOrder = configWERAAcceptOrder.get(0).getConfigCriteriaValue().equalsIgnoreCase(Y);
        }
        if (acceptWERAOrder) {
            Map<String, String> orderSourceMap = ConfigServiceImpl.getOrderSourceMap();
            if (!orderSourceMap.isEmpty()) {
                String orderSource = orderSourceMap.get(weraOrder.getOrder_from());
                if (StringUtils.isNotBlank(orderSource)) {

                    try {
                        WeraOrderJSONDumpDTO savedWeraOrderJSONDumpDTO = saveOrderDetailsJSON(weraOrder);
                        String weraMerchantId = weraOrder.getRestaurant_id() + "";
                        String weraOrderId = weraOrder.getOrder_id() + "";
                        Store storeDetails = StoreServiceImpl.getWeraMerchantToStoreMapping().get(weraMerchantId);
                        logger.info("Wera MerchantId = [{}] returned Store Details = [{}]", weraMerchantId, storeDetails);

                        WeraOrderMasterDTO weraOrderMasterDTO = getWeraOrderMasterFromWeraOrder(weraOrder);
                        weraOrderMasterDTO.setStore_id(storeDetails.getStoreId());
                        logger.info("Saving order to DB for orderId = [{}] and storeId = [{}]. ", weraOrderId, weraOrderMasterDTO.getStore_id());
                        WeraOrderMasterDTO savedWeraOrderMasterDTO = weraOrderMasterRepository.save(weraOrderMasterDTO);
                        logger.info("Order saved successfully for orderId = [{}]. ", weraOrderMasterDTO.getWera_order_id());

                        Customer customer = updateCustomerMaster(weraOrderMasterDTO);
                        CustomerDtls customerDtls = updateCustomerAddress(weraOrderMasterDTO);

                        List<WeraOrderDetailsDTO> savedWeraOrderDetailsDTOList = null;
                        if (!weraOrder.getOrder_items().isEmpty()) {
                            List<WeraOrderDetailsDTO> weraOrderDetailsDTOList = getWeraOrderDetailsFromWeraOrder(weraOrder);
                            logger.info("Saving order details to DB for orderId = [{}]. ", weraOrderId);
                            savedWeraOrderDetailsDTOList = weraOrderDetailsRepository.saveAll(weraOrderDetailsDTOList);
                            logger.info("Order details saved successfully for orderId = [{}]. ", weraOrderId);

                        }

                        Order order = getOrderFromWeraOrderDetails(weraOrder, getOrderIdInput(RESTAURANT_ID, storeDetails.getStoreId(), orderSource), customer, customerDtls);
                        List<OrderVO> result = orderService.saveOrderAndGetOrderView(order);
                        logger.info("WERA order saved successfully!!! [{}] ", result);

                        String newOrderId = result.stream().findFirst().get().getOrderId();
                        weraOrderResponse.setOrder_id(newOrderId);
                        weraOrderResponse.setMessage("OK");
                        weraOrderResponse.setStatus(200);

                        try {

                            List<WeraOrderSizeDTO> sizeList = getWeraOrderSizeFromWeraOrder(weraOrder, newOrderId);
                            if (null != sizeList && !sizeList.isEmpty()) {
                                weraOrderSizeRepository.saveAll(sizeList);
                            }

                            WERAOrderAcceptRequest request = new WERAOrderAcceptRequest();
                            request.setOrder_id(weraOrderId);
                            request.setMerchant_id(weraMerchantId);
                            request.setPreparation_time(30);
                            weraCallbackService.callWERAOrderAcceptAPI(request);
                        } catch (Exception ex) {
                            logger.error("Error Accepting WERA OrderId [{}]", weraOrderId, ex);
                        }

                    } catch (Exception ex) {
                        logger.error("Exception occurred while processing WERA order!!!!", ex);
                        weraOrderResponse.setOrder_id("");
                        weraOrderResponse.setMessage("Internal Server Error");
                        weraOrderResponse.setStatus(500);
                    }

                    return weraOrderResponse;
                } else {
                    logger.warn("Order source not found in config table!!! Rejecting Order = [{}]", weraOrder);
                    serviceMessage = "Order Source Not Configured";
                    status = 400;

                }
            } else {
                logger.warn("ZERO order source configured in config table!!! Rejecting Order = [{}]", weraOrder);
                serviceMessage = "Order Source Not Configured";
                status = 400;
            }
        }
        weraOrderResponse.setOrder_id("");
        weraOrderResponse.setMessage(serviceMessage);
        weraOrderResponse.setStatus(status);
        return weraOrderResponse;
    }

    private void updateOrderIdInWERATables(String newOrderId, List<OrderVO> result, WeraOrderMasterDTO savedWeraOrderMasterDTO, List<WeraOrderDetailsDTO> savedWeraOrderDetailsDTOList) {

        logger.info("Updating WERA_ORDER_MASTER with order id = [{}]", newOrderId);
        savedWeraOrderMasterDTO.setOrder_id(newOrderId);
        weraOrderMasterRepository.save(savedWeraOrderMasterDTO);


        if (null != savedWeraOrderDetailsDTOList) {
            logger.info("Updating WERA_ORDER_DETAILS with order id = [{}]", newOrderId);
            for (WeraOrderDetailsDTO weraOrderDetails : savedWeraOrderDetailsDTOList) {
                weraOrderDetails.setOrder_id(newOrderId);
                if (!weraOrderDetails.getWeraOrderAddons().isEmpty()) {
                    for (WeraOrderAddonDTO weraOrderAddon : weraOrderDetails.getWeraOrderAddons()) {
                        weraOrderAddon.setOrder_id(newOrderId);
                    }
                }

                if (!weraOrderDetails.getWeraOrderItemDiscounts().isEmpty()) {
                    for (WeraOrderItemDiscountDtlsDTO WeraOrderItemDiscount : weraOrderDetails.getWeraOrderItemDiscounts()) {
                        WeraOrderItemDiscount.setOrder_id(newOrderId);
                    }
                }
            }
            weraOrderDetailsRepository.saveAll(savedWeraOrderDetailsDTOList);
            logger.info("Updated WERA_ORDER_DETAILS with order id = [{}] successfully!!! ", newOrderId);
        }

    }

    private CustomerDtls updateCustomerAddress(WeraOrderMasterDTO weraOrderMasterDTO) throws Exception {
        CustomerDtls existingCustomerDtls = customerDtlsRepository.getCustometDtlsById(weraOrderMasterDTO.getPhone_number(), ONLINE, STATUS_Y);
        if (null == existingCustomerDtls) {
            CustomerDtls newCustomerDtls = new CustomerDtls();
            newCustomerDtls.setCustomerAddressType(ONLINE);
            newCustomerDtls.setMobileNumber(weraOrderMasterDTO.getPhone_number());
            newCustomerDtls = populateCustomerAddressCommonFields(weraOrderMasterDTO, newCustomerDtls);
            newCustomerDtls.setCity(NA);
            newCustomerDtls.setState(NA);
            newCustomerDtls.setZipCode(000000);
            newCustomerDtls.setCreatedBy(WERA);
            return customerDtlsRepository.save(newCustomerDtls);

        } else {
            existingCustomerDtls = populateCustomerAddressCommonFields(weraOrderMasterDTO, existingCustomerDtls);
            return customerDtlsRepository.save(existingCustomerDtls);

        }
    }

    private CustomerDtls populateCustomerAddressCommonFields(WeraOrderMasterDTO weraOrderMasterDTO, CustomerDtls customerDtls) {
        customerDtls.setAddress1(weraOrderMasterDTO.getAddress());
        customerDtls.setAddress2(weraOrderMasterDTO.getDelivery_area());
        customerDtls.setActive(STATUS_Y);
        customerDtls.setUpdatedBy(WERA);
        customerDtls.setUpdatedDate(new Date());
        return customerDtls;
    }

    private Customer updateCustomerMaster(WeraOrderMasterDTO weraOrderMasterDTO) {
        List<Customer> customers = customerRepository.findByMobileNumber(weraOrderMasterDTO.getPhone_number());
        if (customers.isEmpty()) {
            Customer newCustomer = new Customer();
            newCustomer.setFirstName(weraOrderMasterDTO.getCustomer_name());
            newCustomer.setEmailId(weraOrderMasterDTO.getEmail());
            newCustomer.setMobileNumber(weraOrderMasterDTO.getPhone_number());
            newCustomer.setCreatedBy(WERA);
            newCustomer.setUpdatedBy(WERA);
            return customerRepository.save(newCustomer);

        } else {
            for (Customer customer : customers) {
                customer.setFirstName(weraOrderMasterDTO.getCustomer_name());
                customer.setEmailId(weraOrderMasterDTO.getEmail());
                customer.setLastName(BLANK);
                customer.setLastName(BLANK);
                customer.setMiddleName(BLANK);
                customer.setUpdatedBy(WERA);
                customer.setUpdatedDate(new Date());
                customerRepository.save(customer);
            }
        }
        return customers.get(0);
    }

    private Order getOrderFromWeraOrderDetails(WeraOrder weraOrder, OrderIdInput orderIdInput, Customer customer, CustomerDtls customerDtls) {

        Order order = new Order();
        order.setRestaurantId(orderIdInput.getRestaurantId());
        order.setStoreId(orderIdInput.getStoreId());
        order.setOrderSource(orderIdInput.getOrderSource());
        order.setAggregatorOrderId(weraOrder.getOrder_id() + "");
        order.setExternalOrderId(weraOrder.getExternal_order_id());
        order.setOrderChannel(WERA);
        order.setCustomerId(customer.getId());
        order.setCustomerAddressId(customerDtls.getId());
        order.setOrderReceivedDateTime(getDateFromEpoch(weraOrder.getOrder_date_time()));
        order.setOrderDeliveryType(weraOrder.getOrder_type());
        order.setOrderStatus(SUBMITTED);
        order.setPaymentStatus(PAID);
        order.setPaymentMode(weraOrder.getOrder_from());
        order.setTotalPrice(weraOrder.getNet_amount());
        order.setOverallPriceWithTax(weraOrder.getGross_amount());
//        order.setCgstCalculatedValue(weraOrder.getCgst());
//        order.setSgstCalculatedValue(weraOrder.getSgst());
        order.setDeliveryCharges(weraOrder.getDelivery_charge());
        order.setPackagingCharges(weraOrder.getOrder_packaging());
        order.setDiscountAmount(weraOrder.getDiscount());
        order.setCreatedBy(weraOrder.getOrder_from());
        String formattedCouponCode = getFormattedCouponCode(weraOrder);
        order.setCouponCode(formattedCouponCode.substring(0, Math.min(formattedCouponCode.length(), 99)));
        List<OrderDetail> orderDetailList = getOrderDetailsFromWERAOrder(weraOrder);
        if (!orderDetailList.isEmpty()) {
            order.setOrderDetails(orderDetailList);
        }
        return order;

    }

    private String getFormattedCouponCode(WeraOrder weraOrder) {

        StringBuilder stringBuilder = new StringBuilder();
        for (WeraOrderItem item : weraOrder.getOrder_items()) {
            if (null != item.getItem_discounts()) {
                for (WeraOrderDiscount discount : item.getItem_discounts()) {
                    stringBuilder.append(discount.getType());
                    stringBuilder.append(EQUAL);
                    stringBuilder.append(discount.getAmount());
                    stringBuilder.append(PIPE);
                }

            }

        }
        return stringBuilder.toString();
    }

    private Date getDateFromEpoch(String orderDateTime) {
        LocalDate localDate = Instant.ofEpochMilli(Long.parseLong(orderDateTime)).atZone(ZoneId.of("Asia/Kolkata")).toLocalDate();
        logger.info("For Epoch = [{}] localDate = [{}]", orderDateTime, localDate);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.of("Asia/Kolkata")).toInstant());
        logger.info("For Epoch = [{}] date = [{}]", orderDateTime, date);
        return date;
    }

    private List<OrderDetail> getOrderDetailsFromWERAOrder(WeraOrder weraOrder) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (WeraOrderItem item : weraOrder.getOrder_items()) {
            if (null != item.getAddons()) {
                for (WeraOrderAddon addon : item.getAddons()) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setProductId(item.getItem_id());
                    orderDetail.setSubProductId(addon.getAddon_id());
                    orderDetail.setPrice(addon.getPrice());
                    orderDetail.setQuantity(item.getItem_quantity());
                    orderDetail.setFoodPackagedFlag(STATUS_N);
                    orderDetail.setCreatedBy(weraOrder.getOrder_from());
                    orderDetails.add(orderDetail);
                }
            }
            OrderDetail detail = getDefaultOrderDetail(item);
            detail.setCreatedBy(weraOrder.getOrder_from());
            orderDetails.add(detail);
        }
        return orderDetails;
    }

    private OrderDetail getDefaultOrderDetail(WeraOrderItem weraOrderItem) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId(weraOrderItem.getItem_id());
        orderDetail.setSubProductId(NAA);
        orderDetail.setPrice(weraOrderItem.getItem_unit_price());
        orderDetail.setQuantity(weraOrderItem.getItem_quantity());
        orderDetail.setFoodPackagedFlag(STATUS_N);
        return orderDetail;
    }

    private WeraOrderMasterDTO getWeraOrderMasterFromWeraOrder(WeraOrder weraOrder) {
        logger.info("Extracting order master details from WERA order for wera orderId = [{}].", weraOrder.getOrder_id());
        WeraOrderMasterDTO weraOrderMasterDTO = new WeraOrderMasterDTO();
        weraOrderMasterDTO.setWera_order_id(weraOrder.getOrder_id() + "");
        weraOrderMasterDTO.setExternal_order_id(weraOrder.getExternal_order_id());
        weraOrderMasterDTO.setWera_outlet_id(weraOrder.getRestaurant_id());
        weraOrderMasterDTO.setRestaurant_id(RESTAURANT_ID);
        weraOrderMasterDTO.setStore_id(weraOrder.getRestaurant_id() + "");
        weraOrderMasterDTO.setOrder_from(weraOrder.getOrder_from());
        weraOrderMasterDTO.setOrder_date(getDateFromEpoch(weraOrder.getOrder_date_time()));
//        weraOrderMasterDTO.setOrder_date_time(weraOrder.getOrder_date_time());
        weraOrderMasterDTO.setEnable_delivery(weraOrder.getEnable_delivery());
        weraOrderMasterDTO.setNet_amount(weraOrder.getNet_amount());
        weraOrderMasterDTO.setGross_amount(weraOrder.getGross_amount());
        weraOrderMasterDTO.setPayment_mode(weraOrder.getPayment_mode());
        weraOrderMasterDTO.setOrder_type(weraOrder.getOrder_type());
        weraOrderMasterDTO.setOrder_instructions(weraOrder.getOrder_instructions());
        weraOrderMasterDTO.setCgst(weraOrder.getCgst());
        weraOrderMasterDTO.setSgst(weraOrder.getSgst());
        weraOrderMasterDTO.setOrder_packaging(weraOrder.getOrder_packaging());
        weraOrderMasterDTO.setOrder_packaging_cgst(weraOrder.getOrder_packaging_cgst());
        weraOrderMasterDTO.setOrder_packaging_sgst(weraOrder.getOrder_packaging_sgst());
        weraOrderMasterDTO.setOrder_packaging_cgst_percent(weraOrder.getOrder_packaging_cgst_percent());
        weraOrderMasterDTO.setOrder_packaging_sgst_percent(weraOrder.getOrder_packaging_sgst_percent());
        weraOrderMasterDTO.setDiscount(weraOrder.getDiscount());
        weraOrderMasterDTO.setDelivery_charges(weraOrder.getDelivery_charge());
        if (null != weraOrder.getCustomer_details()) {
            weraOrderMasterDTO.setCustomer_name(weraOrder.getCustomer_details().getName());
            weraOrderMasterDTO.setPhone_number(weraOrder.getCustomer_details().getPhone_number());
            weraOrderMasterDTO.setEmail(weraOrder.getCustomer_details().getEmail());
            weraOrderMasterDTO.setAddress(weraOrder.getCustomer_details().getAddress());
            weraOrderMasterDTO.setDelivery_area(weraOrder.getCustomer_details().getDelivery_area());
            weraOrderMasterDTO.setAddress_instructions(weraOrder.getCustomer_details().getAddress_instructions());
        }
        return weraOrderMasterDTO;
    }

    private List<WeraOrderDetailsDTO> getWeraOrderDetailsFromWeraOrder(WeraOrder weraOrder) {

        logger.info("Extracting order item details from WERA order for wera orderId = [{}].", weraOrder.getOrder_id());
        List<WeraOrderDetailsDTO> weraOrderDetailsDTOList = new ArrayList<>();
        for (WeraOrderItem item : weraOrder.getOrder_items()) {
            WeraOrderDetailsDTO weraOrderDetailsDTO = new WeraOrderDetailsDTO();
            weraOrderDetailsDTO.setWera_order_id(weraOrder.getOrder_id() + "");
            weraOrderDetailsDTO.setWera_item_id(item.getWera_item_id());
            weraOrderDetailsDTO.setItem_id(item.getItem_id());
            weraOrderDetailsDTO.setItem_name(item.getItem_name());
            weraOrderDetailsDTO.setItem_unit_price(item.getItem_unit_price());
            weraOrderDetailsDTO.setSubtotal(item.getSubtotal());
            weraOrderDetailsDTO.setDiscount(item.getDiscount());
            weraOrderDetailsDTO.setItem_quantity(item.getItem_quantity());
            weraOrderDetailsDTO.setCgst(item.getCgst());
            weraOrderDetailsDTO.setSgst(item.getSgst());
            weraOrderDetailsDTO.setCgst_percent(item.getCgst_percent());
            weraOrderDetailsDTO.setSgst_percent(item.getSgst_percent());
            weraOrderDetailsDTO.setPackaging(item.getPackaging());
            weraOrderDetailsDTO.setPackaging_cgst(item.getPackaging_cgst());
            weraOrderDetailsDTO.setPackaging_sgst(item.getPackaging_sgst());
            weraOrderDetailsDTO.setPackaging_cgst_percent(item.getPackaging_cgst_percent());
            weraOrderDetailsDTO.setPackaging_sgst_percent(item.getPackaging_sgst_percent());
            weraOrderDetailsDTOList.add(weraOrderDetailsDTO);
            if (null != item.getAddons()) {
                weraOrderDetailsDTO.setWeraOrderAddons(getWeraOrderAddonFromWeraOrder(item));
            }
            if (null != item.getItem_discounts()) {
                weraOrderDetailsDTO.setWeraOrderItemDiscounts(getWeraOrderItemDiscountDtlsFromWeraOrder(item));
            }

        }
        logger.info("Extracted [{}] order item details from WERA order for wera orderId = [{}].", weraOrderDetailsDTOList.size(), weraOrder.getOrder_id());
        return weraOrderDetailsDTOList;

    }

    private List<WeraOrderSizeDTO> getWeraOrderSizeFromWeraOrder(WeraOrder weraOrder, String newOrderId) {
        logger.info("Extracting size details from WERA order for orderId = [{}].", newOrderId);
        List<WeraOrderSizeDTO> weraOrderSizeDTOList = new ArrayList<>();
        for (WeraOrderItem item : weraOrder.getOrder_items()) {
            if (null != item.getVariants()) {
                for (WeraOrderVariant variant : item.getVariants()) {
                    WeraOrderSizeDTO weraOrderSizeDTO = new WeraOrderSizeDTO();
                    weraOrderSizeDTO.setOrder_id(newOrderId);
                    weraOrderSizeDTO.setWera_order_id(weraOrder.getOrder_id() + "");
                    weraOrderSizeDTO.setSize_id(variant.getSize_id());
                    weraOrderSizeDTO.setSize_name(variant.getSize_name());
                    weraOrderSizeDTO.setSize_price(variant.getPrice());
                    weraOrderSizeDTO.setCgst(variant.getCgst());
                    weraOrderSizeDTO.setSgst(variant.getSgst());
                    weraOrderSizeDTO.setCgst_percent(variant.getCgst_percent());
                    weraOrderSizeDTO.setSgst_percent(variant.getSgst_percent());
                    weraOrderSizeDTOList.add(weraOrderSizeDTO);
                }
            }
        }
        logger.info("Extracted [{}] size details from WERA order for orderId = [{}].", weraOrderSizeDTOList.size(), newOrderId);
        return weraOrderSizeDTOList;

    }

    private List<WeraOrderItemDiscountDtlsDTO> getWeraOrderItemDiscountDtlsFromWeraOrder(WeraOrderItem weraOrderItem) {
        String weraItemId = weraOrderItem.getWera_item_id() + "";
        logger.info("Extracting discount details from WERA order for orderId = [{}].", weraItemId);
        List<WeraOrderItemDiscountDtlsDTO> weraOrderItemDiscountDtlsDTOList = new ArrayList<>();
        for (WeraOrderDiscount discount : weraOrderItem.getItem_discounts()) {
            WeraOrderItemDiscountDtlsDTO weraOrderItemDiscountDtlsDTO = new WeraOrderItemDiscountDtlsDTO();
            weraOrderItemDiscountDtlsDTO.setWera_order_id(weraItemId);
            weraOrderItemDiscountDtlsDTO.setItem_discount_type(discount.getType());
            weraOrderItemDiscountDtlsDTO.setItem_discount_amount(discount.getAmount());
            weraOrderItemDiscountDtlsDTOList.add(weraOrderItemDiscountDtlsDTO);
        }
        logger.info("Extracted [{}] discount details from WERA order for orderId = [{}].", weraOrderItemDiscountDtlsDTOList.size(), weraItemId);
        return weraOrderItemDiscountDtlsDTOList;
    }

    private OrderIdInput getOrderIdInput(String restaurantId, String storeId, String orderSource) {
        OrderIdInput orderIdInput = new OrderIdInput();
        orderIdInput.setRestaurantId(restaurantId);
        orderIdInput.setStoreId(storeId);
        orderIdInput.setOrderSource(orderSource);
        return orderIdInput;
    }

    private List<WeraOrderAddonDTO> getWeraOrderAddonFromWeraOrder(WeraOrderItem weraOrderItem) {
        String weraItemId = weraOrderItem.getWera_item_id() + "";
        logger.info("Extracting addon details from WERA order for wera orderId = [{}].", weraItemId);
        List<WeraOrderAddonDTO> weraOrderAddonDTOList = new ArrayList<>();
        for (WeraOrderAddon addon : weraOrderItem.getAddons()) {
            WeraOrderAddonDTO addonDTO = new WeraOrderAddonDTO();
            addonDTO.setWera_order_id(weraItemId);
            addonDTO.setAddon_id(addon.getAddon_id());
            addonDTO.setName(addon.getName());
            addonDTO.setPrice(addon.getPrice());
            addonDTO.setCgst(addon.getCgst());
            addonDTO.setSgst(addon.getSgst());
            addonDTO.setCgst_percent(addon.getCgst_percent());
            addonDTO.setSgst_percent(addon.getSgst_percent());
            weraOrderAddonDTOList.add(addonDTO);
        }
        logger.info("Extracted [{}] addon details from WERA order for wera orderId = [{}].", weraOrderAddonDTOList.size(), weraItemId);
        return weraOrderAddonDTOList;
    }

    private WeraOrderJSONDumpDTO saveOrderDetailsJSON(WeraOrder order) {

        logger.info("Saving OrderDetailsJSON.....!!!");
        WeraOrderJSONDumpDTO weraOrderJSONDumpDTO = new WeraOrderJSONDumpDTO();
        weraOrderJSONDumpDTO.setOrder_id(order.getOrder_id());
        weraOrderJSONDumpDTO.setExternal_order_id(order.getExternal_order_id());
        weraOrderJSONDumpDTO.setRestaurant_id(order.getRestaurant_id());
        weraOrderJSONDumpDTO.setOrder_from(order.getOrder_from());
        weraOrderJSONDumpDTO.setOrder_date_time(order.getOrder_date_time());
        weraOrderJSONDumpDTO.setWera_order_json(order.toString());
        return weraOrderJSONDumpRepository.save(weraOrderJSONDumpDTO);

    }
}
