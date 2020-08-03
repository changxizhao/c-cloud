package com.chang.ccloud.common.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import com.chang.ccloud.common.Result;
import com.chang.ccloud.common.constants.HttpConstants;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author changxizhao
 * @Date 2020/7/25 10:18
 * @Description
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String doPost(String url, Map<String, Object> paramsMap) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        //配置连接超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)
                .build();
        HttpPost httpPost = new HttpPost(url);
        //设置超时时间
        httpPost.setConfig(requestConfig);
        try {
            if(MapUtils.isNotEmpty(paramsMap)) {
                //装配post请求参数
                List<NameValuePair> list = new ArrayList<>();
                for (String key : paramsMap.keySet()) {
                    list.add(new BasicNameValuePair(key, String.valueOf(paramsMap.get(key))));
                }
                //将参数进行编码为合适的格式,如将键值对编码为param1=value1&param2=value2
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, HttpConstants.UTF8.getValue());
                httpPost.setEntity(urlEncodedFormEntity);
            }
            //执行 post请求
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
            logger.info("do post response code {}", closeableHttpResponse.getStatusLine().getStatusCode());
            if (closeableHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = closeableHttpResponse.getEntity();
                return EntityUtils.toString(httpEntity);
            } else {
                return "FAIL";
            }
        } catch (Exception e) {
            logger.error("do post error: {}", e.toString());
            return "ERROR";
        } finally {
            try {
                if (closeableHttpClient != null) {
                    closeableHttpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * @param url         接口地址
     * @param json        json参数
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static Result doPost(String url, String json) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringBuilder header = new StringBuilder();
        header.append(HttpConstants.JSON_HEADER.getValue());
        header.append(HttpConstants.HEADER_SEPARATOR.getValue());
        header.append(HttpConstants.SET_CHARSET.getValue());
        httpPost.setHeader("Content-Type", header.toString());
        try {
            StringEntity entity = new StringEntity(json);
            entity.setContentType(HttpConstants.JSON_CONTENT_TYPE.getValue());
            httpPost.setEntity(entity);
            HttpResponse response = client.execute(httpPost);
            HttpEntity resultObj = response.getEntity();
            String result = EntityUtils.toString(resultObj, HttpConstants.UTF8.getValue());
            return Result.success(result);
        }catch (Exception e) {
            logger.info("do post error: {}", e.toString());
            return Result.error("ERROR");
        }
    }

    public static String doGet(String url, Map<String, Object> paramMap){
        String result = "";
        HttpGet httpGet = new HttpGet(url);
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            if(MapUtils.isNotEmpty(paramMap)) {
                List<NameValuePair> params = new ArrayList<>();
                Set<Map.Entry<String, Object>> set = paramMap.entrySet();
                for (Map.Entry<String, Object> entry : set) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                String param = URLEncodedUtils.format(params, HttpConstants.UTF8.getValue());
                httpGet.setURI(URI.create(url + "?" + param));
            }
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                InputStream in = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, HttpConstants.UTF8.getValue()));
                StringBuilder strber= new StringBuilder();
                String line;
                while((line = br.readLine())!=null){
                    strber.append(line+'\n');
                }
                br.close();
                in.close();
                result = strber.toString();
            }

            if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK){
                return "服务异常";
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally{
            httpGet.abort();
        }
        return result;
    }

    /**
     * 上传文件
     */
    public static String doPostUpload(String url, Map<String, String> map, String filename, byte[] body_data,
                                      String charset) throws IOException {
        logger.info("CHttpClientUtil -> upload url = " + url + ",filename = " + filename);
        // 设置三个常用字符串常量：换行、前缀、分界线（NEWLINE、PREFIX、BOUNDARY）；
        final String NEWLINE = "\r\n";
        final String PREFIX = "--";
        final String BOUNDARY = "#";
        HttpURLConnection httpConn = null;
        BufferedInputStream bis = null;
        DataOutputStream dos = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 实例化URL对象。调用URL有参构造方法，参数是一个url地址；
        URL urlObj = new URL(url);
        // 调用URL对象的openConnection()方法，创建HttpURLConnection对象；
        httpConn = (HttpURLConnection) urlObj.openConnection();
        // 调用HttpURLConnection对象setDoOutput(true)、setDoInput(true)、setRequestMethod("POST")；
        httpConn.setDoInput(true);
        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        // 设置Http请求头信息；（Accept、Connection、Accept-Encoding、Cache-Control、Content-Type、User-Agent）
        httpConn.setUseCaches(false);
        httpConn.setRequestProperty("Connection", "Keep-Alive");
        httpConn.setRequestProperty("Accept", "*/*");
        httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        httpConn.setRequestProperty("Cache-Control", "no-cache");
        httpConn.setRequestProperty("Content-Type", "multipart/form-data;charset=utf-8;boundary=" + BOUNDARY);
        httpConn.setRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)");
        // 调用HttpURLConnection对象的connect()方法，建立与服务器的真实连接；
        httpConn.connect();

        // 调用HttpURLConnection对象的getOutputStream()方法构建输出流对象；
        dos = new DataOutputStream(httpConn.getOutputStream());
        // 获取表单中上传控件之外的控件数据，写入到输出流对象（根据HttpWatch提示的流信息拼凑字符串）；
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = map.get(key);
                dos.writeBytes(PREFIX + BOUNDARY + NEWLINE);
                dos.writeBytes("Content-Disposition: form-data; " + "name=\"" + key + "\"" + NEWLINE);
                dos.writeBytes(NEWLINE);
                dos.writeBytes(URLEncoder.encode(value.toString(), charset));
                // 或者写成：dos.write(value.toString().getBytes(charset));
                dos.writeBytes(NEWLINE);
            }
        }

        // 获取表单中上传控件的数据，写入到输出流对象（根据HttpWatch提示的流信息拼凑字符串）；
        if (body_data != null && body_data.length > 0) {
            dos.writeBytes(PREFIX + BOUNDARY + NEWLINE);
            // String fileName =
            // filePath.substring(filePath.lastIndexOf(File.separatorChar));
            String cd = "Content-Disposition: form-data; " + "name=\"" + "uploadFile" + "\"" + "; filename=\""
                    + filename + "\"" + NEWLINE;
            dos.write(cd.getBytes(Charset.forName("UTF-8"))); // 设置文件名称编码
            // dos.write(new String("Content-Disposition: form-data; " + "name=\"" +
            // "uploadFile" + "\"" + "; filename=\""
            // + filename + "\"" + NEWLINE).getBytes());
            dos.writeBytes(NEWLINE);
            dos.write(body_data);
            dos.writeBytes(NEWLINE);
        }
        dos.writeBytes(PREFIX + BOUNDARY + PREFIX + NEWLINE);
        dos.flush();

        // 调用HttpURLConnection对象的getInputStream()方法构建输入流对象；
        byte[] buffer = new byte[8 * 1024];
        int c = 0;
        // 调用HttpURLConnection对象的getResponseCode()获取客户端与服务器端的连接状态码。如果是200，则执行以下操作，否则返回null；
        if (httpConn.getResponseCode() == 200) {
            bis = new BufferedInputStream(httpConn.getInputStream());
            while ((c = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, c);
                baos.flush();
            }
        }
        dos.close();
        bis.close();
        baos.close();
        // 将输入流转成字节数组，返回给客户端。
        return new String(baos.toByteArray(), charset);
    }
}

