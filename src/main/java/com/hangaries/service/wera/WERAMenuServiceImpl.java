package com.hangaries.service.wera;

import com.hangaries.model.wera.dto.WeraMenuUploadDTO;
import com.hangaries.model.wera.request.WeraMenu;
import com.hangaries.model.wera.request.WeraMenuAddons;
import com.hangaries.model.wera.request.WeraUploadMenu;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WERAMenuServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(WERAMenuServiceImpl.class);

    @Value("${wera.menu.upload.url}")
    private String menuUploadURL;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<WeraUploadMenu> uploadMenuToWeraFoods(String restaurantId, String storeId) {
        WeraUploadMenu request;
        List<WeraUploadMenu> requestList = null;
        List<WeraMenuUploadDTO> weraMenuUploadDTOList = getWeraMenuUploadViewDetails(restaurantId, storeId);
        if (!weraMenuUploadDTOList.isEmpty()) {
            Map<String, List<WeraMenuUploadDTO>> consolidateResponse = consolidateResponse(weraMenuUploadDTOList);
            logger.info("[{}] records after consolidateResponse for restaurantId = {} and storeId = {}", consolidateResponse.size(), restaurantId, storeId);
           requestList = generateWERAUploadMenuRequest(consolidateResponse);

        }
        return requestList;
    }

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


}
