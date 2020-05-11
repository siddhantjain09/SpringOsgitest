package test.comp.portlet;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.annotations.InitMethod;

import org.osgi.framework.ServiceRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import springtoosgi.portlet.AdminPortalHookContextUtils;
import springtoosgi.portlet.BeanToOSGiServiceFactory;
import springtoosgi.portlet.OSGiShim;
import test.service.in.spring.service.someServiceLocalService;

/**
 * @author siddhantjain
 */
@Controller
@RequestMapping("VIEW")
public class TestCompPortletViewController {

	@RenderMapping
	public String view(RenderRequest request, RenderResponse response) {
//	someServiceLocalService	someLocalService=(someServiceLocalService) adm.getBean(someServiceLocalService.class.getName());
//		List<String> s=new ArrayList<String>();
//		test.service.in.spring.model.someService s1=someLocalService.createsomeService(CounterLocalServiceUtil.increment());
//	s1.setField1("siddhant");
//		someLocalService.addsomeService(s1);
		return "view";
	}
	
	@Autowired
	AdminPortalHookContextUtils adm;
	

}
