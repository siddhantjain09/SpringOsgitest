package springtoosgi.portlet;

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      //  LOGGER.entry(applicationContext);
        appContext = applicationContext;
      //  LOGGER.exit();
    }

    public static ApplicationContext getApplicationContext() {
        return appContext;
    }
}
