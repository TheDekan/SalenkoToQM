package com.salenko.pagination;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.salenko.model.Deal;

@XmlRootElement
public class DealPaginator extends Paginator {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<Deal> list;

    public List<Deal> getList() {
        return list;
    }

    public void setList(List<Deal> list) {
        this.list = list;
    }

}
