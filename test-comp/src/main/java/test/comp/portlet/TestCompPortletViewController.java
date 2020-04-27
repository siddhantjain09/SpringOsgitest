package test.comp.portlet;

import java.util.Arrays;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.ServiceRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import springtoosgi.portlet.AdminPortalHookContextUtils;
import springtoosgi.portlet.OSGiShim;

/**
 * @author siddhantjain
 */
@Controller
@RequestMapping("VIEW")
public class TestCompPortletViewController {

	@RenderMapping
	public String view(RenderRequest request, RenderResponse response) {
		foo3Service(adm, shim);
		return "view";
	}
	
	@Bean(name = "foo3Service")
	public ServiceRegistration<?> foo3Service(
		  AdminPortalHookContextUtils adm,
		 OSGiShim shim) {

		return shim.registerService(adm);
	}
	
	@Autowired
	AdminPortalHookContextUtils adm;
	
	@Autowired
	 OSGiShim shim;
	
}
