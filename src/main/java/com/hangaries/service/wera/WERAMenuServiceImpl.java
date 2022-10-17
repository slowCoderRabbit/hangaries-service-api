package com.hangaries.service.wera;

import com.hangaries.model.*;
import com.hangaries.model.vo.OrderVO;
import com.hangaries.model.wera.dto.*;
import com.hangaries.model.wera.request.*;
import com.hangaries.model.wera.response.WERAOrderAcceptResponse;
import com.hangaries.model.wera.response.WeraMenuUploadResponse;
import com.hangaries.model.wera.response.WeraOrderResponse;
import com.hangaries.repository.CustomerDtlsRepository;
import com.hangaries.repository.CustomerRepository;
import com.hangaries.repository.wera.*;
import com.hangaries.service.config.impl.ConfigServiceImpl;
import com.hangaries.service.order.impl.OrderServiceImpl;
import com.hangaries.service.store.impl.StoreServiceImpl;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static com.hangaries.constants.HangariesConstants.*;

@Service
public class WERAMenuServiceImpl {

    public static final String PIPE = "|";
    public static final String EQUAL = "=";
    private static final Logger logger = LoggerFactory.getLogger(WERAMenuServiceImpl.class);
    @Autowired
    WeraOrderJSONDumpRepository weraOrderJSONDumpRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerDtlsRepository customerDtlsRepository;

    @Autowired
    ConfigServiceImpl configService;
    @Value("${wera.menu.upload.url}")
    private String menuUploadURL;

    //    @Value("${wera.order.accept.url}")
//    private String orderAcceptURL;
    @Value("${wera.api.key}")
    private String werAPIKey;
    @Value("${wera.api.value}")
    private String werAPIValue;
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

    public List<WeraUploadMenu> uploadMenuToWeraFoods(String restaurantId, String storeId) {
        WeraUploadMenu request;
        List<WeraUploadMenu> requestList = null;
        List<WeraMenuUploadDTO> weraMenuUploadDTOList = getWeraMenuUploadViewDetails(restaurantId, storeId);
        if (!weraMenuUploadDTOList.isEmpty()) {
            Map<String, List<WeraMenuUploadDTO>> consolidateResponse = consolidateResponse(weraMenuUploadDTOList);
            logger.info("[{}] records after consolidateResponse for restaurantId = {} and storeId = {}", consolidateResponse.size(), restaurantId, storeId);
            requestList = generateWERAUploadMenuRequest(consolidateResponse);

        }
        callWERAUploadMenuAPI(requestList);
        return requestList;
    }

    String callWERAUploadMenuAPI(List<WeraUploadMenu> requestList) {
        logger.info("################## WERA MENU UPLOAD - INITIATING!!! ##################");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(werAPIKey, werAPIValue);
        int index = 0;
        for (WeraUploadMenu request : requestList) {
            index = ++index;
            logger.info("[{}] WERA MENU UPLOAD REQUEST = [{}]", index, request);
            HttpEntity<WeraUploadMenu> httpRequest = new HttpEntity<>(request, headers);
            try {
                ResponseEntity<WeraMenuUploadResponse> response = restTemplate.postForEntity(menuUploadURL, httpRequest, WeraMenuUploadResponse.class);
                logger.info("[{}] WERA MENU UPLOAD RESPONSE = [{}]", index, response.getBody());
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                return "Error while uploading menu items. Please check the logs!!!";
            }
        }

        logger.info("################## WERA MENU UPLOAD - FINISHED!!! ##################");

        return SUCCESS;
    }

    public ResponseEntity<WERAOrderAcceptResponse> callWERAOrderAcceptAPI(WERAOrderAcceptRequest request) {
        logger.info("################## WERA ACCEPT ORDER - INITIATING!!! ##################");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(werAPIKey, werAPIValue);

        HttpEntity<WERAOrderAcceptRequest> httpRequest = new HttpEntity<>(request, headers);
        ResponseEntity<WERAOrderAcceptResponse> response = null;
//        try {
//            response = restTemplate.postForEntity(orderAcceptURL, httpRequest, WERAOrderAcceptResponse.class);
//            logger.info("WERA ACCEPT ORDER RESPONSE = [{}]", response.getBody());
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//            logger.error("Error while accepting wera order [{}]", request.getOrder_id(), ex.getMessage());
//        }

        return response;
    }


//    private List<WeraUploadMenu> generateWERAUploadMenuRequest(Map<String, List<WeraMenuUploadDTO>> consolidateResponse) {
//
//        List<WeraUploadMenu> weraUploadMenuList;
//        WeraUploadMenu weraUploadMenu;
//        WeraMenu weraMenu = null;
//        WeraMenuAddons weraMenuAddons;
//        List<WeraUploadMenu> menuList = new ArrayList<>();
//        for (List<WeraMenuUploadDTO> dtoList : consolidateResponse.values()) {
//            weraUploadMenu = new WeraUploadMenu();
//            weraUploadMenuList = new ArrayList<>();
//            ArrayList<WeraMenuAddons> weraMenuAddonsList = new ArrayList<>();
//            for (WeraMenuUploadDTO weraMenuUploadDTO : dtoList) {
//                weraUploadMenu.setMerchant_id(weraMenuUploadDTO.getMerchant_id());
//                weraUploadMenu.setMenu();
//
//            }
//
//            menuList.add(weraUploadMenu);
//
//        }
//        return menuList;
//    }

    private List<WeraUploadMenu> generateWERAUploadMenuRequest(Map<String, List<WeraMenuUploadDTO>> consolidateResponse) {

        List<WeraUploadMenu> weraUploadMenuList;
        WeraUploadMenu weraUploadMenu;
        WeraMenu weraMenu = null;
        WeraMenuAddons weraMenuAddons;
        List<WeraUploadMenu> menuList = new ArrayList<>();
        for (List<WeraMenuUploadDTO> dtoList : consolidateResponse.values()) {
            weraUploadMenu = new WeraUploadMenu();
            weraUploadMenuList = new ArrayList<>();
            ArrayList<WeraMenuAddons> weraMenuAddonsList = new ArrayList<>();
            for (int i = 0; i < dtoList.size(); i++) {
                if (i == 0) {
                    weraUploadMenu.setMerchant_id(dtoList.get(i).getMerchant_id());
                    weraMenu = populateWERAMenu(dtoList.get(i));
                    ArrayList<WeraMenu> weraMenuList = new ArrayList<>();
                    weraMenuList.add(weraMenu);
                    weraUploadMenu.setMenu(weraMenuList);
                }
                weraMenuAddons = populateWERAAddon(dtoList.get(i));
                if (null != weraMenuAddons) {
                    weraMenuAddonsList.add(weraMenuAddons);
                }

            }
            if (!weraMenuAddonsList.isEmpty()) {
                weraUploadMenu.setAddons(weraMenuAddonsList);
            }
            menuList.add(weraUploadMenu);

        }
        return menuList;
    }

    private WeraMenu populateWERAMenu(WeraMenuUploadDTO weraMenuUploadDTO) {
        WeraMenu weraMenu = new WeraMenu();
        weraMenu.setId(weraMenuUploadDTO.getId());
        weraMenu.setItem_name(weraMenuUploadDTO.getItem_name());
        weraMenu.setPrice(weraMenuUploadDTO.getZomato_product_price());
        weraMenu.setActive(BooleanUtils.toBoolean(weraMenuUploadDTO.getActive()));
        weraMenu.setCgst(weraMenuUploadDTO.getZomato_product_cgst());
        weraMenu.setSgst(weraMenuUploadDTO.getZomato_product_sgst());
        return weraMenu;
    }

    private WeraMenuAddons populateWERAAddon(WeraMenuUploadDTO weraMenuUploadDTO) {
        WeraMenuAddons weraMenuAddons = null;
        if (StringUtils.isNotBlank(weraMenuUploadDTO.getAddon_name())) {
            weraMenuAddons = new WeraMenuAddons();
            weraMenuAddons.setAddon_name(weraMenuUploadDTO.getAddon_name());
            weraMenuAddons.setAddon_id(weraMenuUploadDTO.getAddon_id());
            weraMenuAddons.setPrice(weraMenuUploadDTO.getZomato_subproduct_price());
        }

        return weraMenuAddons;
    }

    private int getWERAMerchantId(List<WeraMenuUploadDTO> weraMenuUploadDTOList) {
        return weraMenuUploadDTOList.get(0).getMerchant_id();
    }

    List<WeraMenuUploadDTO> getWeraMenuUploadViewDetails(String restaurantId, String storeId) {

        List<WeraMenuUploadDTO> weraMenuUploadDTOList = jdbcTemplate.query("SELECT * FROM vWeraMenuUpload where restaurant_id = ? and store_id = ? order by id", new Object[]{restaurantId, storeId}, BeanPropertyRowMapper.newInstance(WeraMenuUploadDTO.class));
        logger.info("[{}] records found for restaurantId = {} and storeId = {}", weraMenuUploadDTOList.size(), restaurantId, storeId);
        return weraMenuUploadDTOList;

    }

    Map<String, List<WeraMenuUploadDTO>> consolidateResponse(List<WeraMenuUploadDTO> results) {
        Map<String, List<WeraMenuUploadDTO>> orderMap = new LinkedHashMap<>();
        for (WeraMenuUploadDTO result : results) {
            List<WeraMenuUploadDTO> exitingList = orderMap.get(result.getId());
            if (exitingList == null) {
                List<WeraMenuUploadDTO> newList = new ArrayList<>();
                newList.add(result);
                orderMap.put(result.getId(), newList);
            } else {
                exitingList.add(result);
                orderMap.put(result.getId(), exitingList);
            }
        }
        return orderMap;
    }


    public WeraOrderResponse placeOrder(WeraOrder weraOrder) {

        WeraOrderResponse weraOrderResponse = new WeraOrderResponse();
        List<ConfigMaster> configMasterList = configService.getConfigDetailsByCriteria(RESTAURANT_ID, ALL, WERA_ACCEPT_ORDER_FLAG);
        boolean acceptWERAOrder = false;
        if (!configMasterList.isEmpty()) {
            acceptWERAOrder = configMasterList.get(0).getConfigCriteriaValue().equalsIgnoreCase(Y);
        }
        if (acceptWERAOrder) {

            try {
                WeraOrderJSONDumpDTO savedWeraOrderJSONDumpDTO = saveOrderDetailsJSON(weraOrder);
                String weraMerchantId = weraOrder.getRestaurant_id() + "";
                String weraOrderId = weraOrder.getOrder_id() + "";
                Store storeDetails = StoreServiceImpl.getWeraMerchantToStoreMapping().get(weraMerchantId);
                logger.info("Wera MerchantId = [{}] returned Store Details = [{}]", weraMerchantId, storeDetails);

//                OrderIdInput orderIdInput = getOrderIdInput(RESTAURANT_ID,storeDetails.getStoreId(),ORDER_SOURCE);
//                logger.info("Getting new orderId for order request [{}] by passing values = [{}].", weraOrder.getOrder_id(), orderIdInput);
//                String newOrderId = orderService.getNewOrderId(orderIdInput);
//                logger.info("New orderId = [{}] generated for order request[{}].", newOrderId, weraOrder.getOrder_id());

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

                Order order = getOrderFromWeraOrderDetails(weraOrder, getOrderIdInput(RESTAURANT_ID, storeDetails.getStoreId(), ORDER_SOURCE), customer, customerDtls);
                List<OrderVO> result = orderService.saveOrderAndGetOrderView(order);
                logger.info("WERA order saved successfully!!! [{}] ", result);

                String newOrderId = result.stream().findFirst().get().getOrderId();
                weraOrderResponse.setOrder_id(newOrderId);
                weraOrderResponse.setMessage("OK");
                weraOrderResponse.setStatus(200);

                try {
                    updateOrderIdInWERATables(newOrderId, result, savedWeraOrderMasterDTO, savedWeraOrderDetailsDTOList);
                } catch (Exception ex) {
                    logger.error("Error in updateOrderIdInWERATables", ex);
                }

            } catch (Exception ex) {
                logger.error("Exception occurred while processing WERA order!!!!", ex);
                weraOrderResponse.setOrder_id("");
                weraOrderResponse.setMessage("Internal Server Error");
                weraOrderResponse.setStatus(500);
            }

            return weraOrderResponse;
        }
        weraOrderResponse.setOrder_id("");
        weraOrderResponse.setMessage("Service Unavailable");
        weraOrderResponse.setStatus(503);
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
            newCustomerDtls.setAddress1(weraOrderMasterDTO.getAddress());
            newCustomerDtls.setLandmark(weraOrderMasterDTO.getDelivery_area());
            newCustomerDtls.setCity(NA);
            newCustomerDtls.setState(NA);
            newCustomerDtls.setZipCode(000000);
            newCustomerDtls.setActive(STATUS_Y);
            newCustomerDtls.setCreatedBy(WERA);
            newCustomerDtls.setUpdatedBy(WERA);
            return customerDtlsRepository.save(newCustomerDtls);

        } else {
            existingCustomerDtls.setAddress1(weraOrderMasterDTO.getAddress());
            existingCustomerDtls.setLandmark(weraOrderMasterDTO.getDelivery_area());
            existingCustomerDtls.setActive(STATUS_Y);
            existingCustomerDtls.setUpdatedBy(WERA);
            existingCustomerDtls.setUpdatedDate(new Date());
            return customerDtlsRepository.save(existingCustomerDtls);

        }
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
        order.setCustomerId(customer.getId());
        order.setCustomerAddressId(customerDtls.getId());
        order.setOrderReceivedDateTime(getDateFromEpoch(weraOrder.getOrder_date_time()));
        order.setOrderDeliveryType(weraOrder.getOrder_type());
        order.setOrderStatus(SUBMITTED);
        order.setPaymentStatus(PAID);
        order.setPaymentMode(weraOrder.getPayment_mode());
        order.setTotalPrice(weraOrder.getNet_amount());
        order.setOverallPriceWithTax(weraOrder.getGross_amount());
        order.setCgstCalculatedValue(weraOrder.getCgst());
        order.setSgstCalculatedValue(weraOrder.getSgst());
        order.setDeliveryCharges(weraOrder.getDelivery_charge());
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

//    private LocalDate getDateTimeFromEpoch(String orderDateTime) {
//        LocalDate localDate =  Instant.ofEpochMilli(Long.parseLong(orderDateTime)).atZone(ZoneId.of("Asia/Kolkata")).toLocalDate();
//        logger.info("For Epoch = [{}] localDate = [{}]",orderDateTime,localDate);
//
//    }

    private List<OrderDetail> getOrderDetailsFromWERAOrder(WeraOrder weraOrder) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (WeraOrderItem item : weraOrder.getOrder_items()) {
            OrderDetail detail = getDefaultOrderDetail(item);
            detail.setCreatedBy(weraOrder.getOrder_from());
            orderDetails.add(detail);
            if (null != item.getAddons()) {
                for (WeraOrderAddon addon : item.getAddons()) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setProductId(item.getItem_id());
                    orderDetail.setSubProductId(addon.getAddon_id());
                    orderDetail.setPrice(addon.getPrice());
                    orderDetail.setQuantity(1);
                    orderDetail.setFoodPackagedFlag(STATUS_N);
                    orderDetail.setCreatedBy(weraOrder.getOrder_from());
                }
            }
        }
        return orderDetails;
    }

    private OrderDetail getDefaultOrderDetail(WeraOrderItem weraOrderItem) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId(weraOrderItem.getItem_id());
        orderDetail.setSubProductId(NAA);
        orderDetail.setPrice(weraOrderItem.getItem_unit_price());
        orderDetail.setQuantity(1);
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
//        for (WeraOrderVariant size : weraOrder.getVariants()) {
//            WeraOrderSizeDTO weraOrderSizeDTO = new WeraOrderSizeDTO();
//            weraOrderSizeDTO.setOrder_id(newOrderId);
//            weraOrderSizeDTO.setWera_order_id(weraOrder.getOrder_id() + "");
//            weraOrderSizeDTO.setSize_id(size.getSize_id());
//            weraOrderSizeDTO.setSize_name(size.getSize_name());
//            weraOrderSizeDTO.setSize_price(size.getPrice());
//            weraOrderSizeDTO.setCgst(size.getCgst());
//            weraOrderSizeDTO.setSgst(size.getSgst());
//            weraOrderSizeDTO.setCgst_percent(size.getCgst_percent());
//            weraOrderSizeDTO.setSgst_percent(size.getSgst_percent());
//            weraOrderSizeDTOList.add(weraOrderSizeDTO);
//        }
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
