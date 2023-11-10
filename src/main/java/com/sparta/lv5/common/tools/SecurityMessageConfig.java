package com.sparta.lv5.common.tools;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

//@Configuration
@SuppressWarnings("unused")
public class SecurityMessageConfig {
    //    @Bean
    public MessageSource messageSource() {

        Locale.setDefault(Locale.KOREA); // 위치 한국으로 설정
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setDefaultEncoding("UTF-8"); // 인코딩 설정
        messageSource.setBasenames("classpath:/customMessage"); // 커스텀한 properties 파일, security properties 파일 순서대로 설정
        return messageSource;
    }

}
