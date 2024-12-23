package xcmg.global.cockpit.backend.filer;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import xcmg.global.cockpit.backend.util.HttpUtil;
import xcmg.global.cockpit.backend.util.OauthUtil;
import xcmg.global.cockpit.backend.util.StringUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter
public class OauthFilter implements Filter {

    public void destroy() {

    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String code = req.getParameter("code");//code一次性消费，消费过后失效，3分钟内无消费的则失效
        Object user = session.getAttribute("user");
        Object ssoSessionIdObj = session.getAttribute("ssoSessionId");

        String ssoSessionId = null;
        if (null == ssoSessionIdObj) {
            String sessionId = req.getParameter("sessionId");
            if (!StringUtil.isNullEmpTrim(sessionId)) {
                ssoSessionId = sessionId;
                session.setAttribute("ssoSessionId", sessionId);
            }
        } else {
            ssoSessionId = (String) ssoSessionIdObj;
        }

        isLogin(req);//基于cookie,移动端不需要检测cookie
        if (!StringUtil.isNullEmpTrim(ssoSessionId)) {
            try {
                //基于接口
                //isLogin(ssoSessionId, req);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (null != user) {//判断自身系统是否已登录
            //放行
        } else if (!StringUtil.isNullEmpTrim(code)) {
            //sso进来的,自动完成系统登录
            Map<String, String> resultMap = OauthUtil.getAccessToken(code);
            String resultCode = resultMap.get("code");
            if ("success".equals(resultCode)) {
                String accessToken = resultMap.get("msg");
                String loginName = OauthUtil.getUserinfo(accessToken);
                if (!StringUtil.isNullEmpTrim(loginName)) {
                    //根据登录名判断用户是否存在自身系统中，并获取相关信息保存到session中
//                    User sysUser = new User();
//                    sysUser.setName(loginName);
//                    session.setAttribute("user", sysUser);
                    //放行
                }
            } else {
                String authorize_url = "http://iam.demo.com:8882/sso/oauth/authorize?response_type=code&client_id=APP002&redirect_uri=http%3A%2F%2Fiam.demo.com%3A8080%2FoauthDemo%2Findex.do";
                resp.sendRedirect(authorize_url);
                return;
            }
        } else {
            //redirect_uri为sso登录成功后，会跳的地址，应用方自己指定
            String authorize_url = "http://iam.demo.com:8882/sso/oauth/authorize?response_type=code&client_id=APP002&redirect_uri=http%3A%2F%2Fiam.demo.com%3A8080%2FoauthDemo%2Findex.do";
            resp.sendRedirect(authorize_url);
            return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {

    }

    /**
     * 基于cookie判断单点是否切换账号或退出
     *
     * @param request
     * @return
     */
    public boolean isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        boolean flag = false;
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("iamToken")) {
                    String[] value = cookie.getValue().split("%23%23");
                    String version = value[1];
                    String oldVersion = (String) session.getAttribute("version");
                    if (null != oldVersion && !oldVersion.equals(version)) {
                        //销毁session
                        session.removeAttribute("user");
                    }
                    flag = true;
                    session.setAttribute("version", version);
                }
            }
        }

        if (!flag) {
            session.removeAttribute("user");
        }
        return flag;
    }

    /**
     * 基于接口模式，判断单点是否切换账号或退出
     *
     * @return
     * @throws Exception
     */
    public boolean isLogin(String ssoSessionId, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        boolean flag = false;
        String cookieUrl = "http://iam.demo.com:8882/portal/getCookie.action";
        Map<String, String> param = new HashMap<String, String>();
        param.put("name", "iamToken_" + ssoSessionId);
        String resultStr = HttpUtil.post(cookieUrl, null, param);
        JSONObject json = JSONObject.parseObject(resultStr);
        String cookie = json.getString("iamToken_" + ssoSessionId);
        if (null != cookie && !"null".equals(cookie)) {
            String[] value = cookie.split("%23%23");
            String version = value[1];
            String oldVersion = (String) session.getAttribute("version");
            if (null != oldVersion && !oldVersion.equals(version)) {
                //销毁session
                session.removeAttribute("user");
            }
            flag = true;
            session.setAttribute("version", version);
        }
        if (!flag) {
            session.removeAttribute("user");
        }
        return flag;
    }
}

