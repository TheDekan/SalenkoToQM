package com.salenko.rest;

import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salenko.service.DealService;


@Controller
@RequestMapping("/resources")
public class CheckController {
	
	@Autowired
	private DealService service;
	
	@RequestMapping(value = "/ch", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public @ResponseBody JSONObject getCheck() {
	    String[] checkParts = service.getCheck().split("\n");
		JSONObject jo = new JSONObject();		
		    jo.put("check", checkParts);
                    
		return jo;
	}
	
}
