package com.salenko.pagination;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.salenko.model.Product;

@XmlRootElement
public class ProductPaginator extends Paginator {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private List<Product> list;

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }

    public List<Product> getProductList() {
        return list;
    }

    public void setProductList(List<Product> list) {
        this.list = list;
    }

}
