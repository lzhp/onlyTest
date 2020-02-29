package cn.gov.customs.h2018.pta;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PtaApplication {

  public static void main(String[] args) {
    SpringApplication.run(PtaApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate()
      throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
           IOException, KeyManagementException, UnrecoverableKeyException {
    KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

    String keyStoreFile = "tomcat.jks";
    String keyStorePassword = "AAAhjz0228";

    //    String keyStoreFile = "ngkey.jks";
    //    String keyStorePassword = "123456";

    keyStore.load(
        ClassLoader.getSystemResourceAsStream(keyStoreFile), keyStorePassword.toCharArray());

    // 不验证证书
    SSLConnectionSocketFactory socketFactory =
        new SSLConnectionSocketFactory(
            new SSLContextBuilder()
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .loadKeyMaterial(keyStore, keyStorePassword.toCharArray())
                .build(),
            NoopHostnameVerifier.INSTANCE);

    //    // 验证证书
    //    SSLConnectionSocketFactory socketFactory =
    //        new SSLConnectionSocketFactory(
    //            new SSLContextBuilder()
    //                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
    //                .loadKeyMaterial(keyStore, keyStorePassword.toCharArray())
    //                .build());

    HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

    ClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory(httpClient);
    return new RestTemplate(requestFactory);
  }
}
