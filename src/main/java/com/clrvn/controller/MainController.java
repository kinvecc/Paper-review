package com.clrvn.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@Controller
public class MainController {

    /**
     * 实例es客户端连接对象
     */
    @Autowired
    private RestHighLevelClient highLevelClient;

    @RequestMapping("/")
    @ResponseBody
    public String test() {
        return "欢迎来到测试";
    }

    @RequestMapping("/main")
    @ResponseBody
    public Object main() {


        //获取返回的数据
        try {
            //构建请求
            Request request = new Request("POST", "/es_paper/_search");
            JSONObject jsonRequestObject = new JSONObject();

            //构建source
            jsonRequestObject.put("_source", "*");
            String reqJson = jsonRequestObject.toJSONString();
            request.setJsonEntity(reqJson);

            Response response = highLevelClient.getLowLevelClient().performRequest(request);

            //将其转换位json对象
            String responseStr = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(responseStr);

            //获取数据源

            return jsonObject.getJSONObject("hits").getJSONArray("hits");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


}
