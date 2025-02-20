package xcmg.global.cockpit.backend.Util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class OauthUtil {
    /**
     * 
     * <p>Description: 获取accessToken</p>
     * @param code sso登录成功后，应用到IAM进行sso认证时，IAM返回此参数
     * @return accessToken
     */
    public static Map<String,String> getAccessToken(String code){
    	Map<String,String> resultMap = new HashMap<String,String>();
        String accessToken = null;
        try {
            Map<String,String> param  = new HashMap<String,String>();
            param.put("code", code);//code一次性消费，消费过后失效，3分钟内无消费的则失效
            param.put("grant_type", "authorization_code");
            param.put("client_id", "APP002");//value是修改成应用自身的client_id,key值不变
            param.put("client_secret", "a8730657-66db-4cbc-830f-edbf0605");//value是修改成应用自身的client_secret,key值不变
            String resultStr = HttpUtil.post("http://iam.demo.com:8882/sso/oauth/accessToken", null, param);
            if(resultStr.indexOf("access_token")>0) {
            	JSONObject json = JSONObject.parseObject(resultStr);
                accessToken = json.getString("access_token");
                resultMap.put("code","success" );
            	resultMap.put("msg",accessToken);
            }else {
            	resultMap.put("code","fail" );
            	resultMap.put("msg",resultStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }
    /**
     * 
     * <p>Description: 获取当前sso登录人的登录名</p>
     * @param accessToken
     * @return uid
     */
    public static String getUserinfo(String accessToken){
        String uid = null;
        try {
            Map<String,String> param  = new HashMap<String,String>();
            param.put("access_token", accessToken);
            String resultStr = HttpUtil.post("http://iam.demo.com:8882/sso/oauth/userInfo", null, param);
            JSONObject json = JSONObject.parseObject(resultStr);
            uid = json.get("uid").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uid;
    }
}
