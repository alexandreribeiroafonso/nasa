package com.example.nasa.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(value = "/my-api.com")
public class NasaController {
	
	//Mapeamento para acesso à API.
	@GetMapping(path = "/nasa/temperature", produces = "application/json")
	
	//Retorna a Média de Valores de Temperatura, caso um SOL não seja especificado.
	public String buscarMedia() {
		String uri = "https://api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0";
		JSONObject json;
        int num=0;
        double soma=0.0;
    	boolean err=false;
        try {
             try {
                 URL url = new URL(uri);
                 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                 connection.setDoOutput(true);
                 connection.setRequestProperty("Content-Type", "application/json");
                 connection.setConnectTimeout(5000);
                 connection.setReadTimeout(5000);
                 OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                 out.close();

                 int http_status = connection.getResponseCode();
                 if (http_status / 100 != 2) {
                     System.out.println("Ocorreu um erro. Conexão.");
                     err=true;
                 }

                 try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                     String line;
 					
                     StringBuilder jsonString = new StringBuilder();
                     while( ((line = reader.readLine()) != null)){
                         jsonString.append(line);
                     }
                     
                     JSONObject jo = new JSONObject(jsonString.toString());
                     JSONObject sol;
                     JSONObject  at;
                     Double  av;                      
                                          
                     JSONArray arr = jo.getJSONArray("sol_keys");

	                  num=0;
	                  soma=0.0;
	                  for(int i=0;i<arr.length();i++){
		       	  		  sol = jo.getJSONObject(arr.getString(i));
		       	  		  at = sol.getJSONObject("AT");
		       	  		  av = at.getDouble("av");
		       	  		  soma=soma+av;
		       	  		  num++;
							
		       	  		  System.out.println(arr.getString(i)); 
		       	  		  System.out.println(av); 
		                  System.out.println(soma/num);
	                  }

                 }catch (Exception e) {
                	 System.out.println("Ocorreu um erro." + e);
                	 err=true;
                 }
             }catch (Exception e) {
            	 System.out.println("Ocorreu um erro. " + e);
            	 err=true;
             } 
    	}catch (Exception e) {
    		System.out.println("Ocorreu um erro. " + e);
    		err=true;
    	}
    	 
    	if ((num>0) && (err==false))
    		return("averageTemperature:" + Double.toString(soma/num));
    	else
    		return(null);
	}


	//Retorna a temperatura para um SOL específico.
	@GetMapping(path = "/nasa/temperature/{id}", produces = "application/json") 
	public String buscarPorId(
			@PathVariable(name = "id", required = true) Long id) throws
		  			Exception { 
		
		String uri = "https://api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0";
   	 	JSONObject json;

   	 	JSONObject sol;
   	 	JSONObject  at;
   	 	Double  av=0.0;  
   	 	boolean idEncontrado=false;
   	 	boolean err=false;
   	 
	   	try {
	   		try {
	            URL url = new URL(uri);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoOutput(true);
	            connection.setRequestProperty("Content-Type", "application/json");
	            connection.setConnectTimeout(5000);
	            connection.setReadTimeout(5000);
	            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
	            out.close();
	
                int http_status = connection.getResponseCode();
                if (http_status / 100 != 2) {
                	System.out.println("Ocorreu um erro." + "Conexão.");
                	err=true;
                }

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
					
                    StringBuilder jsonString = new StringBuilder();
                    while( ((line = reader.readLine()) != null)){
                        jsonString.append(line);
                    }
                    
                    JSONObject jo = new JSONObject(jsonString.toString());
                    
                    JSONArray arr = jo.getJSONArray("sol_keys");

                    idEncontrado=false;
                    for(int i=0;i<arr.length();i++){
                    	if (id == Long.parseLong(arr.getString(i))){
      	  		  			sol = jo.getJSONObject(arr.getString(i));
      	  		  			at = sol.getJSONObject("AT");
      	  		  			av = at.getDouble("av");
      	  		  			idEncontrado=true;
      	  		  			break;
                 	 }
                 }
                 
                }catch (Exception e) {
                	System.out.println("Ocorreu um erro. " + e);
                	err=true;
                }
	   		}catch (Exception e) {
	   			System.out.println("Ocorreu um erro. " + e);
	   			err=true;
	   		}
   	 
	   	}catch (Exception e) {
	   		System.out.println("Ocorreu um erro. " + e);
	   		err=true;
	   	}
   	 
	   	if ((idEncontrado) && (err==false))
	   		return(Double.toString(av));
	   	else
	   		return(null);	  		
	}
}
