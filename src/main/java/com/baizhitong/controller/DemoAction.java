package com.baizhitong.controller;

import com.baizhitong.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangsj on 2017/11/28.
 */
@RestController
@RequestMapping("/demo")
public class DemoAction {

    @Autowired
    private ITestService testService;

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public String getInfo(){
        return testService.getInfo();
    }

    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public Integer saveInfo(){
        return testService.saveInfo();
    }
}
