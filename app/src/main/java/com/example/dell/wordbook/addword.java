package com.example.dell.wordbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.example.dell.wordbook.dummy.DummyContent;

public class addword extends AppCompatActivity {

    Button yesbutton;
    Button iselectbutton;
    EditText wordtext;
    EditText meaningtext;
    EditText sampletext;
    String wordmeaning="";
    String wordsample="";
    int f=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addword);
        setaa setaa=new setaa();
        setaa.start();


    }
    class settext extends Thread
    {
        public void run()
        {

        }
    }
    class setaa extends Thread
    {
        public void run()
        {
            yesbutton=(Button)findViewById(R.id.yesbutton);
            wordtext=(EditText) findViewById(R.id.wordtext);
            meaningtext=(EditText)findViewById(R.id.meaningtext);
            sampletext=(EditText)findViewById(R.id.sampletext);
            iselectbutton=(Button)findViewById(R.id.iselectbutton);
            yesbutton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(sqlite.insert(wordtext.getText().toString(),meaningtext.getText().toString(),sampletext.getText().toString())) {
                                DummyContent.gx();
                                //MainActivity.gxfra();
                                //finish();
                                Intent intent = new Intent(addword.this, MainActivity.class);
                                startActivity(intent);
                                //MainActivity mm=new MainActivity();
                            }
                        }
                    }
            );
            iselectbutton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            wllj wlljj=new wllj(wordtext.getText().toString());
                            wlljj.start();
                            try
                            {
                                Thread.currentThread().sleep(500);//毫秒
                            }
                            catch(Exception e){}
                            meaningtext.setText(wordmeaning);
                            sampletext.setText(wordsample);

                        }
                    }
            );
        }
    }
    class wllj extends Thread
    {
        String word;
        wllj(String word)
        {
            this.word=word;
        }
        public void run()
        {
            Map<String, String> params = new HashMap<String, String>();
            String appKey ="4d9128f7cf104b21";
            String query = word;
            String salt = String.valueOf(System.currentTimeMillis());
            String from = "EN";
            String to ="zh-CHS" ;
            String sign = md5(appKey + query + salt+ "0AsD7OrpfGh06elsBWki55xF5NV3bqkR");
            params.put("q", query);
            params.put("from", from);
            params.put("to", to);
            params.put("sign", sign);
            params.put("salt", salt);
            params.put("appKey", appKey);
            try {
                SSLContext sslcontext = SSLContext.getInstance("TLS");
                sslcontext.init(null, new TrustManager[] { myX509TrustManager }, null);
                String sendUrl = getUrlWithQueryString("http://openapi.youdao.com/api", params);
                System.out.println(sendUrl);
                URL uri =new URL("http://dict-co.iciba.com/api/dictionary.php?w="+word+"&key=27ED27166918C664C90438B501855561"); // 创建URL对象
                HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
                if (conn instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) conn).setSSLSocketFactory(sslcontext.getSocketFactory());
                }

                conn.setConnectTimeout(10000); // 设置相应超时
                conn.setRequestMethod("GET");
                int statusCode = conn.getResponseCode();
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    System.out.println("Http错误码：" + statusCode);
                }

                // 读取服务器的数据
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }

                String text = builder.toString();

                Log.i("123",text);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputStream iStream=new ByteArrayInputStream(text.getBytes());
                Document document = db.parse(iStream);  ;

                NodeList acceptation=document.getElementsByTagName("acceptation");
                NodeList orig=document.getElementsByTagName("orig");
                wordmeaning=acceptation.item(0).getFirstChild().getNodeValue();
                wordsample=orig.item(0).getFirstChild().getNodeValue();
                Log.i("123",wordmeaning);
                Log.i("123",wordsample);
                f=1;
                settext settext=new settext();
                settext.start();
                //JSONObject json= new JSONObject(text);
                //String translation=json.getString("translation");
                //JSONArray jArray=new JSONArray(translation);
                //Log.i("123",jArray.getString(0));
                //isampletext.setText(""+wordmeaning+"");
                br.close(); // 关闭数据流
                is.close(); // 关闭数据流
                conn.disconnect(); // 断开连接


            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (java.io.IOException e)
            {

            }
            catch(java.security.KeyManagementException e)
            {

            }
            /*catch(org.json.JSONException e)
            {

            }*/
            catch (javax.xml.parsers.ParserConfigurationException e)
            {

            }
            catch(org.xml.sax.SAXException e)
            {

            }

        }
    }
    public static String md5(String string) {
        if(string == null){
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try{
            byte[] btInput = string.getBytes("utf-8");
            /** 获得MD5摘要算法的 MessageDigest 对象 */
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            /** 使用指定的字节更新摘要 */
            mdInst.update(btInput);
            /** 获得密文 */
            byte[] md = mdInst.digest();
            /** 把密文转换成十六进制的字符串形式 */
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            return null;
        }
    }
    public static String getUrlWithQueryString(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(encode(value));

            i++;
        }

        return builder.toString();
    }
    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }
    private static TrustManager myX509TrustManager = new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    };
}
