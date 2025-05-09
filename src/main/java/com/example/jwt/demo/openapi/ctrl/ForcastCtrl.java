package com.example.jwt.demo.openapi.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.demo.openapi.domain.ForcastRequestDTO;
import com.example.jwt.demo.openapi.domain.ForcastResponseDTO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/forcast")
public class ForcastCtrl {
    
    @Value("${openApi.serviceKey}")
    private String serviceKey;
    
    @Value("${openApi.callBackUrl}")
    private String callBackUrl;
    
    @Value("${openApi.dataType}")
    private String dataType;

    @GetMapping("/getData")
    public ResponseEntity<List<ForcastResponseDTO>> getData(@RequestBody ForcastRequestDTO params) {
        System.out.println("debug >> forcastCtrl getData");
        System.out.println("debug > key : " + serviceKey);
        System.out.println("debug > url : " + callBackUrl);
        System.out.println("debug > type : " + dataType);
        System.out.println("debug > params : " + params);

        //callbackUrl에 요펑 파라미터를 보내는 것
        String requestURL = callBackUrl+
                            "?serviceKey="+serviceKey+
                            "&beach_num="+params.getBeach_num()+
                            "&base_date="+params.getBase_date()+
                            "&base_time="+params.getBase_time();
        System.out.println("debug >> : "+requestURL);
        ////////////////////////////////////////////////////////////
        HttpURLConnection http   = null ; 
        InputStream       stream = null ; 
        String            result = null ;

        List<ForcastResponseDTO>  list  = null ;
        try {
            URL url = new URL(requestURL);
            http = (HttpURLConnection)url.openConnection();
            System.out.println("http connection = " + http ); 
            int code = http.getResponseCode() ; 
            System.out.println("http response code  = " + code );  
            if( code == 200 ) {
                stream = http.getInputStream() ; 
                result = readString(stream) ;
                System.out.println("result = " + result); 
		
                // �쒕퉬�� 援ы쁽 
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        /// ////////////////////////////////////////////////////////
        return null;
    }
            //////
        public String readString(InputStream stream) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String input = null  ;
            StringBuilder result = new StringBuilder();
            while( (input = br.readLine() ) != null ) {
                result.append(input+"\n\r");
            }
            br.close();   
            return result.toString() ; 
        }
}
