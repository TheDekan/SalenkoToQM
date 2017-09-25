package com.salenko.service;

import java.util.List;

import com.salenko.model.Deal;

public interface DealService {

    Deal insert(Deal row);

    List<Deal> findAll();

    void delete(Long id);

    Deal findById(Long id);

    List<Deal> sortedFind(int startPosition, int maxResults);

    Long getCount();

    String getCheck();

}
