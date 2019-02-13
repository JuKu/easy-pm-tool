package com.jukusoft.pm.tool.app.config;

import com.jukusoft.pm.tool.def.config.Config;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class MVCConfig implements WebMvcConfigurer {

    protected static final String CONF_SECTION = "StaticResCache";

    /*@Autowired
    CsrfTokenInterceptor csrfTokenInterceptor;*/

    //@Autowired
    //private MessageSource messageSource;

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
    public CsrfTokenInterceptor csrfTokenInterceptor() {
        return new CsrfTokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(csrfTokenInterceptor);
    }*/

    /*@Bean
    public Mustache.Compiler mustacheCompiler(
            Mustache.TemplateLoader templateLoader,
            Environment environment, HttpServletRequest request) {

        MustacheEnvironmentCollector collector
                = new MustacheEnvironmentCollector();
        collector.setEnvironment(environment);

        Mustache.Lambda i18n = new Mustache.Lambda() {
            public void execute (Template.Fragment frag, Writer out) throws IOException {
                String key = frag.execute();
                String text = messageSource().getMessage(key, new Object[0], localeResolver().)// look up key in i18n system
                        out.write(text);
            }
        };

        return Mustache.compiler()
                .defaultValue("Some Default Value")
                .withLoader(templateLoader)
                .withCollector(collector);
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);

        //i18n
        registry.addInterceptor(i18nMessageInterceptor());
    }

    @Bean
    public LocaleResolver localeResolver() {
        //see also: https://www.baeldung.com/spring-security-login-error-handling-localization

        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();

        //By default, the locale resolver will obtain the locale code from the HTTP header. To force a default locale, we need to set it on the localeResolver():
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);

        return cookieLocaleResolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0);
        return messageSource;
    }

    /**
    * mustache.java
    */
    /*@Bean
    public org.springframework.web.servlet.view.mustache.java.LocalizationMessageInterceptor i18nMessageInterceptor1() {
        LocalizationMessageInterceptor lmi = new LocalizationMessageInterceptor();
        lmi.setLocaleResolver(localeResolver());
        lmi.setMessageSource(messageSource());
        lmi.setMessageKey("i18n");
        return lmi;
    }*/

    @Bean
    public org.springframework.web.servlet.view.mustache.jmustache.LocalizationMessageInterceptor i18nMessageInterceptor () {
        org.springframework.web.servlet.view.mustache.jmustache.LocalizationMessageInterceptor lmi = new org.springframework.web.servlet.view.mustache.jmustache.LocalizationMessageInterceptor();
        lmi.setLocaleResolver(localeResolver());
        lmi.setMessageSource(messageSource());
        lmi.setMessageKey("i18n");
        return lmi;
    }

}
