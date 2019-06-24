package com.reven.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author reven
 */
public class BaseController {

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final boolean DEFAULT_NOCACHE = true;
    public static final String TEXT_TYPE = "text/plain";
    public static final String JSON_TYPE = "application/json";
    public static final String XML_TYPE = "text/xml";
    public static final String HTML_TYPE = "text/html";
    public static final String JS_TYPE = "text/javascript";
    public static final String EXCEL_TYPE = "application/vnd.ms-excel";
    public static int PAGE_SIZE = 20;


    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor dateEditor = new CustomDateEditor(format, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    public Object getAttribute(String attributeName) {
        return this.getRequest().getAttribute(attributeName);
    }

    public void setAttribute(String attributeName, Object object) {
        this.getRequest().setAttribute(attributeName, object);
    }

    public Object getSession(String attributeName) {
        return this.getRequest().getSession(true).getAttribute(attributeName);
    }

    public void setSession(String attributeName, Object object) {
        this.getRequest().getSession(true).setAttribute(attributeName, object);
    }

    public HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) ra).getRequest();
    }

    public HttpServletResponse getResponse() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) ra).getResponse();
    }

    public HttpSession getSession() {
        return this.getRequest().getSession(true);
    }

    public String getParameter(String paraName) {
        return this.getRequest().getParameter(paraName);
    }

    /**
     * 获取表单格式数据(或url拼接参数)
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Map getParameterMap() {
        return this.getRequest().getParameterMap();
    }

    public String getHeader(String headerName) {
        return this.getRequest().getHeader(headerName);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map getHeaderMap() {
        Enumeration headerNames = this.getRequest().getHeaderNames();
        Map headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            String headerValue = getRequest().getHeader(headerName);
            headerMap.put(headerName, headerValue);
        }
        return headerMap;
    }

    /**
     * 获取客户端ip地址，如果有nginx需要配置x-forwarded-for等参数转发
     * 
     * @param request
     * @return
     */
    public String getCliectIp() {
        HttpServletRequest request = getRequest();
        return WebUtil.getCliectIp(request);
    }

    /**
     * 获取服务器ip地址
     * 
     * @return
     */
    public String getServerIp() {
        return WebUtil.getServerIp();
    }

    /**
     * 获取json格式数据
     * 
     * @return
     */
    public Map<String, Object> getRequestMap() {
        try {
            InputStream inStream = this.getRequest().getInputStream();
            // 默认为json
            BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String buffer = "";
            while (null != (buffer = (in.readLine()))) {
                stringBuffer.append(buffer);
            }
            String reqDoc = stringBuffer.toString();
            if (reqDoc == null || reqDoc.equals("")) {
                return null;
            }
            return JsonUtil.toMap(reqDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 直接输出内容的简便函数.
     */
    protected void render(final String contentType, final String content, final HttpServletResponse response) {
        HttpServletResponse resp = initResponseHeader(contentType, response);
        try {
            resp.getWriter().write(content);
            resp.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接输出文本.
     */
    protected void outText(final String text, final HttpServletResponse response) {
        render(TEXT_TYPE, text, response);
    }

    /**
     * 直接输出HTML.
     */
    protected void outHtml(final String html, final HttpServletResponse response) {
        render(HTML_TYPE, html, response);
    }

    /**
     * 直接输出XML.
     */
    protected void outXml(final String xml, final HttpServletResponse response) {
        render(XML_TYPE, xml, response);
    }

    /**
     * 输出JSON,可以接收参数如： [{'name':'www'},{'name':'www'}],['a','b'],new
     * String[]{'a','b'},合并如下：jsonString = "{TOTALCOUNT:" + totalCount + ", ROOT:" +
     * jsonString + "}";
     *
     * @param jsonString json字符串.
     *
     */
    protected void outJson(final String json, final HttpServletResponse response) {
        render(JSON_TYPE, json, response);
    }

    /**
     * 设置让浏览器弹出下载对话框的Header.
     *
     * @param fileName 下载后的文件名.
     */
    protected void setFileDownloadHeader(HttpServletResponse response, String fileName) {
        try {
            // 中文文件名支持
            String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分析并设置contentType与headers.
     */
    protected HttpServletResponse initResponseHeader(final String contentType, final HttpServletResponse response) {
        // 分析headers参数
        String encoding = DEFAULT_ENCODING;
        boolean noCache = DEFAULT_NOCACHE;
        // 设置headers参数
        String fullContentType = contentType + ";charset=" + encoding;
        response.setContentType(fullContentType);
        if (noCache) {
            setNoCacheHeader(response);
        }

        return response;
    }

    /**
     * 设置客户端无缓存Header.
     */
    protected void setNoCacheHeader(HttpServletResponse response) {
        // Http 1.0 header
        response.setDateHeader("Expires", 0);
        response.addHeader("Pragma", "no-cache");
        // Http 1.1 header
        response.setHeader("Cache-Control", "no-cache");
    }

    /**   
     * 追加webbase需要webbase需要的参数至跳转的URL    
     */
    protected String appendParamToUrl(String currentUrl) {
        String ticket = getRequest().getParameter("ticket");
        if (StringUtils.isEmpty(ticket)) {
            ticket = (String) getSession().getAttribute("ticket");
        }

        String domain = getRequest().getParameter("domain");
        if (StringUtils.isEmpty(domain)) {
            domain = (String) getSession().getAttribute("domain");
        }

        if (currentUrl.indexOf('?') >= 0) {
            currentUrl = currentUrl + "&ticket=" + ticket;
        } else {
            currentUrl = currentUrl + "?ticket=" + ticket;
        }

        String roleType = getRoleType();
        currentUrl = currentUrl + "&roleType=" + roleType;
        currentUrl = currentUrl + "&domain=" + domain;
        return currentUrl;
    }

    protected String getRoleType() {
        String roleType = getRequest().getParameter("roleType");
        if (StringUtils.isEmpty(roleType)) {
            roleType = (String) getSession().getAttribute("roleType");
        }
        return roleType;
    }

}