package com.einswatch.einswatchuser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shao Fei on 15/6/2015.
 */


// TODO: Download library for URIBuilder to complete Class

public class URLConnectionClient {

    public static void main(String[] args) {

        ArrayList<String> request = getRequest();

        URL url = processRequest(request);

        String jsonResponseString = executePOST(url);

        displayResults(jsonResponseString);

    }

    private static URL processRequest(ArrayList<String> request) {

        String address = request.get(request.size()-1);

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        for(int i = 0; i < request.size() - 1; i++) {
            String[] nameValuePair = request.get(i).split(" ");
            params.add(new BasicNameValuePair(nameValuePair[0], nameValuePair[1]));
        }

        return encodeParams(address, params);
    }

    private static ArrayList<String> getRequest() {
        Scanner sc = new Scanner(System.in);

        String url = sc.nextLine();

        ArrayList<String> paramsString = new ArrayList<String>();

        while(sc.hasNextLine()) {
            String param = sc.nextLine();
            if(!param.equalsIgnoreCase("OK")) {
                paramsString.add(param);
            } else {
                break;
            }
        }
        sc.close();

        paramsString.add(url);
        return paramsString;
    }

    private static void displayResults(String jsonResponseString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponseString);
            int empty = jsonObject.getInt("empty");

            System.out.println("Empty: " + empty);

            JSONArray jsonArray = jsonObject.getJSONArray("entries");

            for(int i = 0; i < jsonArray.length(); i++) {
                System.out.println(jsonArray.getJSONObject(i).get("firstName"));
                System.out.println(jsonArray.getJSONObject(i).get("lastName"));
                System.out.println(jsonArray.getJSONObject(i).get("email"));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println(jsonResponseString);
            e.printStackTrace();

        }
    }

    public static String executeGET(URL urlObject) {

        try {
            //URL urlObject = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();

            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String executePOST(URL urlObject) {

        try {
            //URL urlObject = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();

            conn.setRequestMethod("POST");

			/*
			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(params);
			wr.flush();
			wr.close();
			*/

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static URL encodeParams(String url, List<NameValuePair> params) {
        try {
            URIBuilder uriBuilder;
            uriBuilder = new URIBuilder(url);
            uriBuilder.addParameters(params);
            URI encodedUrl = uriBuilder.build();
            return encodedUrl.toURL();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
