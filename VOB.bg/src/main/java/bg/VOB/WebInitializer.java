package bg.VOB;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.
AbstractAnnotationConfigDispatcherServletInitializer;
 
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
 
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { SpringWebConfig.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/", "*.html", "*.pdf" };
    }
    
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
    	registration.setMultipartConfig(getMultiPartConfigElement());
    }
    
    private MultipartConfigElement getMultiPartConfigElement() {
    	MultipartConfigElement multipartConfigElement = new MultipartConfigElement(LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
    return multipartConfigElement;
    }
    
    
    public static final String LOCATION = "/home/nikikocheva/Desktop/videos";
    private static final long MAX_FILE_SIZE = 52_428_800; //50 MB - max file size
    private static final long MAX_REQUEST_SIZE = 20_971_520; //20 MB - total request file size
    private static final int FILE_SIZE_THRESHOLD = 0;
}