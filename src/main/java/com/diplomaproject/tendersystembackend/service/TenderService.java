package com.diplomaproject.tendersystembackend.service;

import com.diplomaproject.tendersystembackend.model.Tender;

import java.util.List;

public interface TenderService {
    Tender saveOrUpdateTender(String tenderName, String description, Long categoryID);

    Tender findByTenderId(Long id);
    List<Tender> getAllTenders();
    void deleteTenderById(Long id);
}
