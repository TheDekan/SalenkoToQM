package com.salenko.rest;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salenko.model.Deal;
import com.salenko.pagination.DealPaginator;
import com.salenko.service.DealService;

@Controller
@RequestMapping("/resources")
public class DealController {

    @Autowired
    private DealService service;

    private Long listCount() {
        return service.getCount();
    }

    @SuppressWarnings("unchecked")
    private List<Deal> findRow(int startPosition, int maxResults) {
        return service.sortedFind(startPosition, maxResults);
    }

    private DealPaginator findRow(DealPaginator wrapper) {
        wrapper.setTotalResults(listCount());
        int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
        wrapper.setList(findRow(start, wrapper.getPageSize()));
        return wrapper;
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public @ResponseBody DealPaginator listMyDeals(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        DealPaginator paginator = new DealPaginator();
        paginator.setCurrentPage(page);
        paginator.setPageSize(10);
        return findRow(paginator);
    }

    @RequestMapping(value = "/checkout/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public @ResponseBody Deal getById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public @ResponseBody Deal save(@RequestBody Deal row) {
        if (row.getId() == null) {
            Deal rowToinsert = new Deal();
            rowToinsert.setProduct(row.getProduct());
            rowToinsert.setProductCount(row.getProductCount());
            row = service.insert(rowToinsert);
        } else {
            Deal rowToUpdate = service.findById(row.getId());
            rowToUpdate.setProduct(row.getProduct());
            rowToUpdate.setProductCount(row.getProductCount());
        }
        return row;
    }

    @RequestMapping(value = "/checkout/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

}
