/**
 * Date: 2018-03-23 15:24:10.
 *
 * @author: lizhipeng.
 */
package org.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;
// import cn.customs.h2018.sample.utils.CustomExceptionPojo;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Date: 2018-03-23 15:24:10.
 *
 * @author: lizhipeng.
 * @description:
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class ApplicationConfig {
  @Autowired private TypeResolver typeResolver;

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getApiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("org.test"))
        .paths(PathSelectors.any())
        .build()
        .pathMapping("/")
        .genericModelSubstitutes(ResponseEntity.class)
        .additionalModels(typeResolver.resolve(CustomExceptionPojo.class))
        .useDefaultResponseMessages(false)
        .globalResponseMessage(RequestMethod.GET, getErrorResponse())
        .enableUrlTemplating(true);
  }

  private List<ResponseMessage> getErrorResponse() {
    return Lists.newArrayList(
        new ResponseMessageBuilder()
            .code(500)
            .message("500 message")
            .responseModel(new ModelRef("CustomExceptionPojo"))
            .build(),
        new ResponseMessageBuilder().code(403).message("Forbidden!!!!!").build());
  }

  private ApiInfo getApiInfo() {

    Contact contact = new Contact("lizhipeng", "https://github.com/sdcuike", "lizhipeng@gmail.com");
    return new ApiInfoBuilder()
        .title("H2018")
        .description("API信息")
        .termsOfServiceUrl("https://github.com/sdcuike/spring-boot-practice")
        .contact(contact)
        .license("版权所有，全国海关信息中心")
        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
        .version("1.0.0")
        .build();
  }
}
