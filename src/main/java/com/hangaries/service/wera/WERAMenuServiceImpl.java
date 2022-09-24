package com.hangaries.service.wera;

import com.hangaries.model.wera.dto.WeraMenuUploadDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WERAMenuServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(WERAMenuServiceImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String uploadMenuToWeraFoods(String restaurantId, String storeId) {
        List<WeraMenuUploadDTO> rssOrderSources = getWeraMenuUploadViewDetails(restaurantId, storeId);
        return "";
    }

    List<WeraMenuUploadDTO> getWeraMenuUploadViewDetails(String restaurantId, String storeId) {

        List<WeraMenuUploadDTO> weraMenuUploadDTOList = jdbcTemplate.query("SELECT * FROM vWeraMenuUpload where restaurant_id = ? and store_id = ?", new Object[]{restaurantId, storeId}, BeanPropertyRowMapper.newInstance(WeraMenuUploadDTO.class));
        logger.info("[{}] records found for restaurantId = {} and storeId = {}", weraMenuUploadDTOList.size(), restaurantId, storeId);
        return weraMenuUploadDTOList;

    }
}
