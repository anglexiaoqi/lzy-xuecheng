package com.xuecheng.framework.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqun.chen on
 */
public class HttpUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * post请求
     *
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doPost(String url, JSONObject json) {
        logger.info("url:[{}];query:[{}]", url, json);
        // 创建 StopWatch
        StopWatch sw = new StopWatch();
        // 开始计时
        sw.start();

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");// 发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 结束计时
        sw.stop();
        long totalTimeMillis = sw.getTotalTimeMillis();
        if (totalTimeMillis >= 10000) {
            logger.warn("url:[{}];query:[{}];totalTimeMillis:[{}]", url, json, totalTimeMillis);
        } else {
            logger.info("url:[{}];query:[{}];totalTimeMillis:[{}]", url, json, totalTimeMillis);
        }
        return response;
    }

    public static String load(String url, String query) throws Exception {
        logger.info("url:[{}];query:[{}]", url, query);
        // 创建 StopWatch
        StopWatch sw = new StopWatch();
        // 开始计时
        sw.start();

/*        URL restURL = new URL(url);

        // 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类
        // 的子类HttpURLConnection

        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(30000);
        // 请求方式
        conn.setRequestMethod("POST");
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        // httpUrlConnection.setDoInput(true);
        conn.setDoOutput(true);
        // allowUserInteraction 如果为 true，则在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
        conn.setAllowUserInteraction(false);

        PrintStream ps = new PrintStream(conn.getOutputStream());
        ps.print(query);

        ps.close();

        String line, resultStr = "";

        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            logger.error("url:[{}];query:[{}];err:[{}]", url, query, e);
            return resultStr;
        }

        while (null != (line = bReader.readLine())) {
            resultStr += line;
        }

        bReader.close();*/

        String resultStr = "";
        URL restURL = new URL(url);
        URLConnection conn = restURL.openConnection(); // POST要求URL中不包含请求参数
        conn.setDoOutput(true); // 必须设置这两个请求属性为true，就表示默认使用POST发送
        conn.setDoInput(true);
        //conn.setRequestProperty("charsert", "utf-8");
        // 请求参数必须使用conn获取的OutputStream输出到请求体参数
        // 用PrintWriter进行包装
        //PrintWriter out = new PrintWriter(conn.getOutputStream());
        //out.println(param.getBytes("UTF-8"));
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        out.write(query);
        out.flush(); // 立即充刷至请求体）PrintWriter默认先写在内存缓存中
        try// 发送正常的请求（获取资源）
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                resultStr += line + "\n";
            }
        } catch (Exception e) {
            logger.error("url:[{}];query:[{}];err:[{}]", url, query, e);
        }


        // 结束计时
        sw.stop();
        long totalTimeMillis = sw.getTotalTimeMillis();
        if (totalTimeMillis >= 10000) {
            logger.warn("url:[{}];query:[{}];totalTimeMillis:[{}]", url, query, totalTimeMillis);
        } else {
            logger.info("url:[{}];query:[{}];totalTimeMillis:[{}]", url, query, totalTimeMillis);
        }

        return resultStr;

    }

    /**
     * 调用对方接口方法
     *
     * @param path 对方或第三方提供的路径
     * @param data 向对方或第三方发送的数据，大多数情况下给对方发送JSON数据让对方解析
     */
    public static String interfaceUtil(String path, String data) {
        logger.info("url:[{}];query:[{}]", path, data);
        // 创建 StopWatch
        StopWatch sw = new StopWatch();
        // 开始计时
        sw.start();

        String res = "";
        try {
            URL url = new URL(path);
            // 打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;
            // 请求方式
            conn.setRequestMethod("POST");
            // //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            // 最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            // post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数即数据
            out.print(data);
            // 缓冲数据
            out.flush();
            // 获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            // 构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                res = str;
            }
            // 关闭流
            is.close();
            // 断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            // 固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
            // logger.info("完整结束");
        } catch (Exception e) {
            logger.error("", e);
        }
        // 结束计时
        sw.stop();
        long totalTimeMillis = sw.getTotalTimeMillis();
        if (totalTimeMillis >= 10000) {
            logger.warn("url:[{}];query:[{}];totalTimeMillis:[{}]", path, data, totalTimeMillis);
        } else {
            logger.info("url:[{}];query:[{}];totalTimeMillis:[{}]", path, data, totalTimeMillis);
        }
        return res;
    }

    /**
     * Http请求工具类
     */
    static boolean proxySet = false;
    static String proxyHost = "127.0.0.1";
    static int proxyPort = 8087;

    /**
     * 编码
     *
     * @param source
     * @return
     */
    public static String urlEncode(String source, String encode) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "0";
        }
        return result;
    }

    public static String urlEncodeGBK(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "0";
        }
        return result;
    }

    /**
     * 发起http请求获取返回结果
     *
     * @param req_url 请求地址
     * @return
     */
    public static String httpRequest(String req_url) {
        logger.info("req_url:[{}]", req_url);
        // 创建 StopWatch
        StopWatch sw = new StopWatch();
        // 开始计时
        sw.start();

        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(req_url);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(false);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

        } catch (Exception e) {
            logger.error("", e);
        }

        // 结束计时
        sw.stop();
        long totalTimeMillis = sw.getTotalTimeMillis();
        if (totalTimeMillis >= 10000) {
            logger.warn("url:[{}];totalTimeMillis:[{}]", req_url, totalTimeMillis);
        } else {
            logger.info("url:[{}];totalTimeMillis:[{}]", req_url, totalTimeMillis);
        }
        return buffer.toString();
    }

    /**
     * 发送http请求取得返回的输入流
     *
     * @param requestUrl 请求地址
     * @return InputStream
     */
    public static InputStream httpRequestIO(String requestUrl) {
        logger.info("requestUrl:[{}]", requestUrl);
        // 创建 StopWatch
        StopWatch sw = new StopWatch();
        // 开始计时
        sw.start();

        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            // 获得返回的输入流
            inputStream = httpUrlConn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 结束计时
        sw.stop();
        long totalTimeMillis = sw.getTotalTimeMillis();
        if (totalTimeMillis >= 10000) {
            logger.warn("url:[{}];totalTimeMillis:[{}]", requestUrl, totalTimeMillis);
        } else {
            logger.info("url:[{}];totalTimeMillis:[{}]", requestUrl, totalTimeMillis);
        }
        return inputStream;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        logger.info("url:[{}];query:[{}]", url, param);
        // 创建 StopWatch
        StopWatch sw = new StopWatch();
        // 开始计时
        sw.start();

        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送GET请求出现异常！", e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        // 结束计时
        sw.stop();
        long totalTimeMillis = sw.getTotalTimeMillis();
        if (totalTimeMillis >= 10000) {
            logger.warn("url:[{}];query:[{}];totalTimeMillis:[{}]", url, param, totalTimeMillis);
        } else {
            logger.info("url:[{}];query:[{}];totalTimeMillis:[{}]", url, param, totalTimeMillis);
        }

        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url     发送请求的 URL
     * @param param   请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param isproxy 是否使用代理模式
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, boolean isproxy) {
        logger.info("url:[{}];query:[{}]", url, param);
        // 创建 StopWatch
        StopWatch sw = new StopWatch();
        // 开始计时
        sw.start();

        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            if (isproxy) {// 使用代理模式
                @SuppressWarnings("static-access")
                Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP, new InetSocketAddress(proxyHost, proxyPort));
                conn = (HttpURLConnection) realUrl.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) realUrl.openConnection();
            }
            // 打开和URL之间的连接

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // POST方法

            // 设置通用的请求属性

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            conn.connect();

            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常！", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // 结束计时
        sw.stop();
        long totalTimeMillis = sw.getTotalTimeMillis();
        if (totalTimeMillis >= 10000) {
            logger.warn("url:[{}];query:[{}];totalTimeMillis:[{}]", url, param, totalTimeMillis);
        } else {
            logger.info("url:[{}];query:[{}];totalTimeMillis:[{}]", url, param, totalTimeMillis);
        }
        return result;
    }

    /*
     * public static void main(String[] args) { //demo:代理访问 String url =
     * "http://api.adf.ly/api.php"; String para =
     * "key=youkeyid&youuid=uid&advert_type=int&domain=adf.ly&url=http://somewebsite.com";
     *
     * String sr=HttpRequestUtil.sendPost(url,para,true);
     * logger.info(sr); }
     */
    public static String post(String url, JSONObject json) {
        logger.info("url:[{}];query:[{}]", url, json);
        // 创建 StopWatch
        StopWatch sw = new StopWatch();
        // 开始计时
        sw.start();

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";

        try {

            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            logger.info(result);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                logger.info("请求服务器成功，做相应处理");
            } else {
                logger.info("请求服务端失败");
            }
        } catch (Exception e) {
            logger.error("请求异常！", e);
        }

        // 结束计时
        sw.stop();
        long totalTimeMillis = sw.getTotalTimeMillis();
        if (totalTimeMillis >= 10000) {
            logger.warn("url:[{}];query:[{}];totalTimeMillis:[{}]", url, json, totalTimeMillis);
        } else {
            logger.info("url:[{}];query:[{}];totalTimeMillis:[{}]", url, json, totalTimeMillis);
        }
        return result;
    }

    public static String getIpAddrLoginFilter(HttpServletRequest request) {
        try {
            logger.info("request============{}", request);
            return request.getRemoteAddr();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取请求ip
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        try {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();

                    // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
                    if (ip != null && ip.length() > 15) { // "***.***.***.***".length()
                        // = 15
                        if (ip.indexOf(",") > 0) {
                            ip = ip.substring(0, ip.indexOf(","));
                        }
                    }
                }
            }
            if (ip != null && ip.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ip.indexOf(",") > 0) {
                    ip = ip.substring(0, ip.indexOf(","));
                }
            }
            return ip;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void test() throws Exception {
        StopWatch sw = new StopWatch();
        sw.start();
        sw.stop();

        logger.info("1----" + sw.prettyPrint());
        logger.info("2----" + sw.getTotalTimeMillis());
        logger.info("3----" + sw.getLastTaskName());
        logger.info("4----" + sw.getLastTaskInfo());
        logger.info("5----" + sw.getTaskCount());
    }

    public static void main(String[] args) throws Exception {

        //   test();

        String url = "http://192.168.5.249:8019/api/recharge/balance";
        Map<String, Object> map = new HashMap<>();
        map.put("Code", "SDK");
        map.put("Balance", "500");
        map.put("Mark", "+");

        JSONObject jsonObject = JsonUtils.fromJson(JsonUtils.toJson(map), JSONObject.class);
        String result = post(url, jsonObject);
        logger.info(result);
    }
}