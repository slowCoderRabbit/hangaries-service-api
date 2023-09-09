package com.hangaries.service.css;

import com.hangaries.model.CSSMaster;
import com.hangaries.model.CSSRequest;
import com.hangaries.repository.CSSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.hangaries.constants.HangariesConstants.ACTIVE;

@Service
public class CSSServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(CSSServiceImpl.class);

    @Autowired
    CSSRepository cssRepository;

    public CSSMaster saveCss(CSSMaster request) {
        return cssRepository.save(request);
    }

    public Optional<CSSMaster> saveCSSStatus(long id, String status) {
        cssRepository.saveCSSStatus(id, status);
        return cssRepository.findById(id);
    }

    public List<CSSMaster> getActiveCSS(CSSRequest request) {
        return cssRepository.findByRestaurantIdAndStoreIdAndCategoryAndSubCategoryAndStatus(
                request.getRestaurantId(), request.getStoreId(), request.getCategory(), request.getSubCategory(), ACTIVE);
    }
}
