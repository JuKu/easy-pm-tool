package com.jukusoft.pm.tool.app.config;

import com.jukusoft.pm.tool.def.config.Config;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class MVCConfig implements WebMvcConfigurer {

    protected static final String CONF_SECTION = "StaticResCache";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/res/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(Config.getInt(CONF_SECTION, "cache_period"))
                .setCacheControl(CacheControl.maxAge(Config.getInt(CONF_SECTION, "cache_control_max_age"), TimeUnit.SECONDS))
                .resourceChain(true)

                //supports br and gzip, see also https://www.baeldung.com/spring-mvc-static-resources
                .addResolver(new EncodedResourceResolver());

        registry
                .addResourceHandler("/files/public/**")
                .addResourceLocations("file:./www/public/");
    }

    /*@Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        //bean.setViewClass(JstlView.class);
        bean.setPrefix("/views/");
        bean.setSuffix(".html");
        return bean;
    }

    @Bean
    public ViewResolver resourceBundleViewResolver() {
        ResourceBundleViewResolver bean = new ResourceBundleViewResolver();
        bean.setBasename("views");
        return bean;
    }*/

    /*@Bean
    public Mustache.Compiler mustacheCompiler(
            Mustache.TemplateLoader templateLoader,
            Environment environment) {

        MustacheEnvironmentCollector collector
                = new MustacheEnvironmentCollector();
        collector.setEnvironment(environment);

        return Mustache.compiler()
                .defaultValue("Some Default Value")
                .withLoader(templateLoader)
                .withCollector(collector);
    }*/

}
