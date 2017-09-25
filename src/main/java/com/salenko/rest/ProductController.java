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

import com.salenko.model.Product;
import com.salenko.pagination.ProductPaginator;
import com.salenko.service.ProductService;

@Controller
@RequestMapping("/resources")
public class ProductController {

    @Autowired
    private ProductService service;

    private Long listCount() {
        return service.getCount();
    }

    @SuppressWarnings("unchecked")
    private List<Product> findRow(int startPosition, int maxResults, String sortFields, String sortDirections) {
        return service.sortedFind(startPosition, maxResults, sortFields, sortDirections);
    }

    private ProductPaginator findRow(ProductPaginator wrapper) {
        wrapper.setTotalResults(listCount());
        int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
        wrapper.setList(findRow(start, wrapper.getPageSize(), wrapper.getSortFields(), wrapper.getSortDirections()));
        return wrapper;
    }

    @RequestMapping(value = "/price", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public @ResponseBody ProductPaginator listMyProducts(@RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "sortFields", defaultValue = "id") String sortFields,
            @RequestParam(value = "sortDirections", defaultValue = "asc") String sortDirections) {
        ProductPaginator paginator = new ProductPaginator();
        paginator.setCurrentPage(page);
        paginator.setSortFields(sortFields);
        paginator.setSortDirections(sortDirections);
        paginator.setPageSize(10);
        return findRow(paginator);
    }

    @RequestMapping(value = "/price/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public @ResponseBody Product getById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/price", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public @ResponseBody Product save(@RequestBody Product row) {
        if (row.getId() == null) {
            Product rowToinsert = new Product();
            service.setProductRow(rowToinsert, row);
            row = service.insert(rowToinsert);
        } else {
            Product rowToUpdate = service.findById(row.getId());
            service.setProductRow(rowToUpdate, row);
            service.update(rowToUpdate);
        }
        return row;
    }

    @RequestMapping(value = "/price/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

}
