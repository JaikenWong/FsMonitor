package com.jaiken.webhook.controller;

import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "测试Controller", description = "这是描述")
public class TestController {

    @PostMapping("/webhook")
    @Operation(summary = "飞书webhook接口")
    public JSONObject webhook(@RequestBody JSONObject jsonObject) {
        JSONObject result = new JSONObject();
        result.put("status", "success");
        result.put("received", jsonObject);
        System.out.println(jsonObject);
        return result;
    }


    @PostMapping("/webhook2")
    @Operation(summary = "飞书webhook接口2")
    public JSONObject testDevTool(@RequestBody JSONObject jsonObject) {
        JSONObject result = new JSONObject();
        result.put("status", "success");
        result.put("received", jsonObject);
        System.out.println(jsonObject);
        return result;
    }

}
