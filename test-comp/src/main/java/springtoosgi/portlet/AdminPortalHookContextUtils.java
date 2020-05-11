package springtoosgi.portlet;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * IntelliJ IDEA
 * User: sannamalai
 * Date: 12/01/14
 * Time: 5:47 PM
 */
public class AdminPortalHookContextUtils implements ApplicationContextAware {

   // private static final XLogger LOGGER = XLoggerFactory.getXLogger(AdminPortalHookContextUtils.class);

    private static ApplicationContext appContext;
    
    AdminPortalHookContextUtils() {
    	System.out.println("regestered");
    }
    
    @PostConstruct
    public void postConstructInit(){

        String myVar="Post init called";
       System.out.println(myVar);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      //  LOGGER.entry(applicationContext);
        appContext = applicationContext;
      //  LOGGER.exit();
    }

    public static ApplicationContext getApplicationContext() {
        return appContext;
    }

	public void contextInit() {
		// TODO Auto-generated method stub
		 String myVar="app init called";
	       System.out.println(myVar);
	}
	
	public Object getBean(String className) {
		System.out.println(appContext.getBean(className));
		return appContext.getBean(className);
	}
}
