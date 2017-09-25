package com.salenko.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.salenko.model.Product;

@Repository("ProductDao")
public class ProductDaoImpl extends AbstractDao implements ProductDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> findAll() {
        Criteria criteria = getSession().createCriteria(Product.class);
        return (List<Product>) criteria.list();
    }

    @Override
    public void delete(Long id) {
        Query query = getSession().createQuery("delete from Product where id = :id");
        query.setLong("id", id);
        query.executeUpdate();
    }

    @Override
    public Product findById(Long id) {
        return (Product) findByField(Product.class, "id", id);
    }

    @Override
    public Long getCount() {
        Query query = getSession().createQuery("select count(*) from Product");
        return (Long) query.uniqueResult();
    }

    @Override
    public List<Product> sortedFind(int startPosition, int maxResults, String sortFields, String sortDirections) {
        maxResults += startPosition;
        Query query = getSession().createQuery("FROM Product ORDER BY " + sortFields + " " + sortDirections);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResults);
        return query.list();
    }

    @Override
    public void update(Product row) {
        super.update(row);
    }

    @Override
    public Product insert(Product row) {
        return (Product) super.insert(row);
    }

}
