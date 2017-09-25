package com.salenko.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salenko.dao.ProductDao;
import com.salenko.model.Product;

@Service("testService")
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao dao;

    @Override
    public void update(Product row) {
        dao.update(row);
    }

    @Override
    public Product insert(Product row) {
        return dao.insert(row);
    }

    @Override
    public List<Product> findAll() {
        return dao.findAll();
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public Product findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public Long getCount() {
        return dao.getCount();
    }

    @Override
    public List<Product> sortedFind(int startPosition, int maxResults, String sortFields, String sortDirections) {
        return dao.sortedFind(startPosition, maxResults, sortFields, sortDirections);
    }
    
    @Override
    public void setProductRow(Product product, Product row){
        product.setName(row.getName());
        product.setPrice(row.getPrice());
        product.setCalculationType(row.getCalculationType());
        product.setActionValid(row.getActionValid());
        product.setActionCount(row.getActionCount());
        product.setActionPrice(row.getActionPrice());
        product.setGift(row.getGift());
        product.setGiftName(row.getGiftName());
        product.setGiftCount(row.getGiftCount());        
    }
}
