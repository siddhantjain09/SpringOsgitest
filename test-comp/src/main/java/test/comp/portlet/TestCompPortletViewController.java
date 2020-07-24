package test.comp.portlet;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import beanprovider.constants.TestBean;
import test.service.in.spring.service.someServiceLocalService;

/**
 * @author siddhantjain
 */
@Controller
@RequestMapping("VIEW")
public class TestCompPortletViewController {

	@RenderMapping
	public String view(RenderRequest request, RenderResponse response) {
	someServiceLocalService	someLocalService=adm;
		List<String> s=new ArrayList<String>();
		int s1=someLocalService.getsomeServicesCount();
		System.out.println(tb);
		System.out.println(cb.tb.x);
	
		//System.out.println(s1);
		return "view";
	}
	
	@Autowired
	someServiceLocalService adm;
	
	
	@Autowired
	beanprovider.constants.ConsumerBean cb;
	
	@Autowired
	TestBean tb;
	

}
