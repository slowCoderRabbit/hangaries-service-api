package com.hangaries.service.wera;

import com.hangaries.model.*;
import com.hangaries.model.wera.dto.*;
import com.hangaries.model.wera.request.*;
import com.hangaries.model.wera.response.WeraMenuUploadResponse;
import com.hangaries.model.wera.response.WeraOrderResponse;
import com.hangaries.repository.CustomerDtlsRepository;
import com.hangaries.repository.CustomerRepository;
import com.hangaries.repository.wera.*;
import com.hangaries.service.config.impl.ConfigServiceImpl;
import com.hangaries.service.order.impl.OrderServiceImpl;
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

import java.util.*;

import static com.hangaries.constants.HangariesConstants.*;

@Service
public class WERAMenuServiceImpl {

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

        List<ConfigMaster> configMasterList = configService.getConfigDetailsByCriteria(ROO1, ALL, WERA_ACCEPT_ORDER_FLAG);
        boolean acceptWERAOrder = false;
        if (!configMasterList.isEmpty()) {
            acceptWERAOrder = configMasterList.get(0).getConfigCriteriaValue().equalsIgnoreCase(Y);
        }
        if (acceptWERAOrder) {

            try {
                saveOrderDetailsJSON(weraOrder);

//                OrderIdInput orderIdInput = getOrderIdInput();
//                logger.info("Getting new orderId for order request [{}] by passing values = [{}].", weraOrder.getOrder_id(), orderIdInput);
//                String newOrderId = orderService.getNewOrderId(orderIdInput);
//                logger.info("New orderId = [{}] generated for order request[{}].", newOrderId, weraOrder.getOrder_id());
//
//                WeraOrderMasterDTO weraOrderMasterDTO = getWeraOrderMasterFromWeraOrder(weraOrder, newOrderId);
//                logger.info("Saving order to DB for orderId = [{}]. ", newOrderId);
//                weraOrderMasterRepository.save(weraOrderMasterDTO);
//                logger.info("Order saved successfully for orderId = [{}]. ", newOrderId);
//
//                Customer customer = updateCustomerMaster(weraOrderMasterDTO);
//                CustomerDtls customerDtls = updateCustomerAddress(weraOrderMasterDTO);
//
//
//                if (!weraOrder.getOrder_items().isEmpty()) {
//                    List<WeraOrderDetailsDTO> weraOrderDetailsDTOList = getWeraOrderDetailsFromWeraOrder(weraOrder, newOrderId);
//                    logger.info("Saving order details to DB for orderId = [{}]. ", newOrderId);
//                    weraOrderDetailsRepository.saveAll(weraOrderDetailsDTOList);
//                    logger.info("Order details saved successfully for orderId = [{}]. ", newOrderId);
//
//                }

//                if (!weraOrder.getAddons().isEmpty()) {
//                    List<WeraOrderAddonDTO> weraOrderAddonDTOList = getWeraOrderAddonFromWeraOrder(weraOrder, newOrderId);
//                    logger.info("Saving addon details to DB for orderId = [{}]. ", newOrderId);
//                    weraOrderAddonRepository.saveAll(weraOrderAddonDTOList);
//                    logger.info("Addon details saved successfully for orderId = [{}]. ", newOrderId);
//                }
//
//                if (!weraOrder.getVariants().isEmpty()) {
//                    List<WeraOrderSizeDTO> weraOrderSizeDTOList = getWeraOrderSizeFromWeraOrder(weraOrder, newOrderId);
//                    logger.info("Saving size details to DB for orderId = [{}]. ", newOrderId);
//                    weraOrderSizeRepository.saveAll(weraOrderSizeDTOList);
//                    logger.info("Size details saved successfully for orderId = [{}]. ", newOrderId);
//                }
//
//                if (!weraOrder.getItem_discounts().isEmpty()) {
//                    List<WeraOrderItemDiscountDtlsDTO> weraOrderItemDiscountDtlsDTOList = getWeraOrderItemDiscountDtlsFromWeraOrder(weraOrder, newOrderId);
//                    logger.info("Saving discount details to DB for orderId = [{}]. ", newOrderId);
//                    weraOrderItemDiscountDtlsRepository.saveAll(weraOrderItemDiscountDtlsDTOList);
//                    logger.info("Discount details saved successfully for orderId = [{}]. ", newOrderId);
//                }

//                Order order = getOrderFromWeraOrderDetails(weraOrder, orderIdInput, newOrderId, customer);
//                orderService.saveOrderAndGetOrderView(order);

            } catch (Exception ex) {
                logger.error("Exception occurred while processing WERA order!!!!", ex);
            }


            return null;
        }
        return null;
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
            newCustomerDtls.setCreatedBy(WERA); /// TODO : WERA
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

    private Order getOrderFromWeraOrderDetails(WeraOrder weraOrder, OrderIdInput orderIdInput, String newOrderId, Customer customer) {

        Order order = new Order();
        order.setOrderId(newOrderId);
        order.setRestaurantId(orderIdInput.getRestaurantId());
        order.setStoreId(orderIdInput.getStoreId());
        order.setOrderSource(orderIdInput.getOrderSource());
        order.setCustomerId(customer.getId());
        //TODO - Once the date format is confirmed will convert to date.
        order.setOrderReceivedDateTime(new Date());
        order.setOrderDeliveryType(weraOrder.getOrder_type());
        order.setOrderStatus(SUBMITTED);
        order.setPaymentStatus(PAID);
        order.setPaymentMode(weraOrder.getOrder_from()); /// //TODO Inter mapping with order from
        //TODO - Tax. This will be nullable
        // order.setTaxRuleId(1);
        order.setTotalPrice(weraOrder.getNet_amount());
        order.setOverallPriceWithTax(weraOrder.getGross_amount());
        order.setCgstCalculatedValue(weraOrder.getCgst());
        order.setSgstCalculatedValue(weraOrder.getSgst());
        order.setDeliveryCharges(weraOrder.getDelivery_charge());
        //TODO convert list to single value
//        order.setCouponCode(weraOrder.);
//        order.setDiscountPercentage(weraOrder.);
        List<OrderDetail> orderDetailList = getOrderDetailsFromWERAOrder(weraOrder, newOrderId);
        order.setOrderDetails(orderDetailList);
        return order;

    }

    private List<OrderDetail> getOrderDetailsFromWERAOrder(WeraOrder weraOrder, String newOrderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
//        for (WeraOrderAddon addon : weraOrder.getAddons()) {
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrderId(newOrderId);
//            orderDetail.setProductId("P033");
//            orderDetail.setSubProductId("S013");
//            orderDetail.setPrice(addon.getPrice());
//            orderDetail.setQuantity(1);
//            orderDetails.add(orderDetail);
//
//        }
        return orderDetails;
    }

    private WeraOrderMasterDTO getWeraOrderMasterFromWeraOrder(WeraOrder weraOrder, String newOrderId) {
        logger.info("Extracting order master details from WERA order for orderId = [{}].", newOrderId);
        WeraOrderMasterDTO weraOrderMasterDTO = new WeraOrderMasterDTO();
        weraOrderMasterDTO.setOrder_id(newOrderId);
        weraOrderMasterDTO.setWera_order_id(weraOrder.getOrder_id() + "");
        weraOrderMasterDTO.setExternal_order_id(weraOrder.getExternal_order_id());
        weraOrderMasterDTO.setWera_outlet_id(weraOrder.getRestaurant_id());
        weraOrderMasterDTO.setRestaurant_id(weraOrder.getRestaurant_id() + "");
        weraOrderMasterDTO.setStore_id(weraOrder.getRestaurant_id() + "");
        weraOrderMasterDTO.setOrder_from(weraOrder.getOrder_from());
        weraOrderMasterDTO.setOrder_date_time(weraOrder.getOrder_date_time());
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

    private List<WeraOrderDetailsDTO> getWeraOrderDetailsFromWeraOrder(WeraOrder weraOrder, String newOrderId) {

        logger.info("Extracting order item details from WERA order for orderId = [{}].", newOrderId);
        List<WeraOrderDetailsDTO> weraOrderDetailsDTOList = new ArrayList<>();
        for (WeraOrderItem item : weraOrder.getOrder_items()) {
            WeraOrderDetailsDTO weraOrderDetailsDTO = new WeraOrderDetailsDTO();
            weraOrderDetailsDTO.setOrder_id(newOrderId);
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

        }
        logger.info("Extracted [{}] order item details from WERA order for orderId = [{}].", weraOrderDetailsDTOList.size(), newOrderId);
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

    private List<WeraOrderItemDiscountDtlsDTO> getWeraOrderItemDiscountDtlsFromWeraOrder(WeraOrder weraOrder, String newOrderId) {
        logger.info("Extracting discount details from WERA order for orderId = [{}].", newOrderId);
        List<WeraOrderItemDiscountDtlsDTO> weraOrderItemDiscountDtlsDTOList = new ArrayList<>();
//        for (WeraOrderDiscount discount : weraOrder.getItem_discounts()) {
//            WeraOrderItemDiscountDtlsDTO weraOrderItemDiscountDtlsDTO = new WeraOrderItemDiscountDtlsDTO();
//            weraOrderItemDiscountDtlsDTO.setOrder_id(newOrderId);
//            weraOrderItemDiscountDtlsDTO.setWera_order_id(weraOrder.getOrder_id() + "");
//            weraOrderItemDiscountDtlsDTO.setItem_discount_type(discount.getType());
//            weraOrderItemDiscountDtlsDTO.setItem_discount_amount(discount.getAmount());
//            weraOrderItemDiscountDtlsDTOList.add(weraOrderItemDiscountDtlsDTO);
//        }
        logger.info("Extracted [{}] discount details from WERA order for orderId = [{}].", weraOrderItemDiscountDtlsDTOList.size(), newOrderId);
        return weraOrderItemDiscountDtlsDTOList;
    }

    private OrderIdInput getOrderIdInput() {
        OrderIdInput orderIdInput = new OrderIdInput();
        orderIdInput.setRestaurantId(ROO1);
        orderIdInput.setStoreId("S001"); /// TODO ---> Lookup 1800 -> Storid
        orderIdInput.setOrderSource(ORDER_SOURCE);
        return orderIdInput;
    }

    private List<WeraOrderAddonDTO> getWeraOrderAddonFromWeraOrder(WeraOrder weraOrder, String newOrderId) {
        logger.info("Extracting addon details from WERA order for orderId = [{}].", newOrderId);
        List<WeraOrderAddonDTO> weraOrderAddonDTOList = new ArrayList<>();
//        for (WeraOrderAddon addon : weraOrder.getAddons()) {
//            WeraOrderAddonDTO addonDTO = new WeraOrderAddonDTO();
//            addonDTO.setOrder_id(newOrderId);
//            addonDTO.setWera_order_id(weraOrder.getOrder_id() + "");
//            addonDTO.setAddon_id(addon.getAddon_id());
//            addonDTO.setName(addon.getName());
//            addonDTO.setPrice(addon.getPrice());
//            addonDTO.setCgst(addon.getCgst());
//            addonDTO.setSgst(addon.getSgst());
//            addonDTO.setCgst_percent(addon.getCgst_percent());
//            addonDTO.setSgst_percent(addon.getSgst_percent());
//            weraOrderAddonDTOList.add(addonDTO);
//        }
        logger.info("Extracted [{}] addon details from WERA order for orderId = [{}].", weraOrderAddonDTOList.size(), newOrderId);
        return weraOrderAddonDTOList;
    }

    private void saveOrderDetailsJSON(WeraOrder order) {

        WeraOrderJSONDumpDTO weraOrderJSONDumpDTO = new WeraOrderJSONDumpDTO();
        weraOrderJSONDumpDTO.setOrder_id(order.getOrder_id());
        weraOrderJSONDumpDTO.setExternal_order_id(order.getExternal_order_id());
        weraOrderJSONDumpDTO.setRestaurant_id(order.getRestaurant_id());
        weraOrderJSONDumpDTO.setOrder_from(order.getOrder_from());
        weraOrderJSONDumpDTO.setOrder_date_time(order.getOrder_date_time());
        weraOrderJSONDumpDTO.setWera_order_json(order.toString());
        weraOrderJSONDumpRepository.save(weraOrderJSONDumpDTO);
        logger.info("WERA order details saved successfully to logging table before starting order processing !!!");

    }
}
