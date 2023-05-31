package com.xiaozhang.springboot.controller;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.*;
import com.xiaozhang.springboot.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/statistic")
@Api(tags = "打卡接口")
@Slf4j
public class DataController {

    @Autowired
    SysCheckService sysCheckService;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserStudyService sysUserStudyService;

    @Autowired
    SysStudyDataService sysStudyDataService;

    @Autowired
    SysUserExamService sysUserExamService;

    @GetMapping("/allIndex")
    public Result getAllIndex(@RequestBody SysUser user)
    {
        Date endTime = new Date();
        Date startTime;
        switch (user.getTimeScale()){
            case "week":
                startTime = DateUtil.lastWeek();
                break;
            case "month":
                startTime = DateUtil.lastMonth();
                break;
            case "year":
                startTime = DateUtil.offset(endTime, DateField.DAY_OF_YEAR,-1);
                break;
            default:
                startTime = DateUtil.lastMonth();

        }
        List<SysCheck> allTime = sysCheckService.list(new QueryWrapper<SysCheck>().select("sum(over_time+add_time) as totalTime","user_id").between("create_time", startTime, endTime).apply("user_id in (SELECT id FROM sys_user u WHERE u.dept_id="+user.getDeptId()+")").groupBy("user_id").orderByDesc("totalTime"));
        return allTime.isEmpty()?Result.fail("数据为空"):Result.success(200,"1",allTime,"");
    }

    @GetMapping("/myIndex")
    public Result getMyIndex(@RequestBody SysUser user)
    {
        System.out.println("----------->"+user);
        Date endTime = new Date();
        Date startTime;
        switch (user.getTimeScale()){
            case "week":
                startTime = DateUtil.lastWeek();
                break;
            case "month":
                startTime = DateUtil.lastMonth();
                break;
            case "year":
                startTime = DateUtil.offset(endTime, DateField.DAY_OF_YEAR,-1);
                break;
            default:
                startTime = DateUtil.lastMonth();

        }
        List<SysCheck> allTime = sysCheckService.list(new QueryWrapper<SysCheck>().select("sum(over_time+add_time) as totalTime","user_id").between("create_time", startTime, endTime).apply("user_id in (SELECT id FROM sys_user u WHERE u.dept_id="+user.getDeptId()+")").groupBy("user_id").orderByDesc("totalTime"));
        SysCheck userCheck = sysCheckService.getOne(new QueryWrapper<SysCheck>().eq("user_id",user.getId()).select("sum(over_time+add_time) as totalTime","user_id").groupBy("user_id"));
        int index = allTime.indexOf(userCheck);
        return Result.success(200,"1",index+1,"");
    }

    @GetMapping("/DividedData")
    public Result getDividedData(@RequestBody SysUser user)
    {
        DateTime start,end;
        switch (user.getTimeScale()){
            case "week":
                start = DateUtil.beginOfWeek(DateUtil.lastWeek());
                end = DateUtil.endOfWeek(DateUtil.lastWeek());
                break;
            case "month":
                start = DateUtil.beginOfMonth(DateUtil.lastMonth());
                end = DateUtil.endOfMonth(DateUtil.lastMonth());
                break;
            case "year":
                start = DateUtil.beginOfYear(DateUtil.offset(new Date(), DateField.DAY_OF_YEAR,-1));
                end = DateUtil.endOfYear(DateUtil.offset(new Date(), DateField.DAY_OF_YEAR,-1));
                break;
            default:
                return Result.fail("无此种时间刻度");
        }
        List<ChartData> quarterData=new ArrayList<>();
        if("year".equals(user.getTimeScale()))
        {
            DateTime qurter = DateUtil.offset(start, DateField.DAY_OF_MONTH, 3);
            while(!qurter.isAfter(end))
            {
                SysCheck check = sysCheckService.getOne(new QueryWrapper<SysCheck>().select("ifnull(sum(add_time),0) as addTime", "ifnull(sum(over_time),0) as overTime").eq("user_id", user.getId()).between("create_time", start, qurter));
                quarterData.add(new ChartData("",check.getAddTime(),check.getOverTime(),0.0));
                start = qurter;
                qurter = DateUtil.offset(qurter,DateField.DAY_OF_MONTH,3);
            }

        }
        else if("month".equals(user.getTimeScale())){
            DateTime xun = DateUtil.offsetDay(start,10);
            SysCheck check = new SysCheck();
            int index=1;
            while(index <= 2)
            {
                check = sysCheckService.getOne(new QueryWrapper<SysCheck>().select("ifnull(sum(add_time),0) as addTime", "ifnull(sum(over_time),0) as overTime").eq("user_id", user.getId()).between("create_time", start, xun));
                quarterData.add(new ChartData("",check.getAddTime(), check.getOverTime(), 0.0));
                System.out.println();
                start = xun;
                System.out.println("------->"+start);
                xun = DateUtil.offsetDay(xun,10);
                System.out.println("------>"+xun);
                index++;
            }
            SysCheck checkLast = sysCheckService.getOne(new QueryWrapper<SysCheck>().select("ifnull(sum(add_time),0) as addTime", "ifnull(sum(over_time),0) as overTime").eq("user_id", user.getId()).between("create_time", start, end));
            quarterData.add(new ChartData("",checkLast.getAddTime(), checkLast.getOverTime(), 0.0));
        }
        else {
            List<SysCheck> check = sysCheckService.list(new QueryWrapper<SysCheck>().select("add_time", "over_time","create_time").eq("user_id", user.getId()).between("create_time", start, end).orderByAsc("create_time"));
            return Result.success(200,"1",check,"");
        }
        return Result.success(200,"获取成功",quarterData,"");
    }

    @GetMapping("/studyData")
    public Result getStudyData(@RequestBody SysUserStudy sysUserStudy)
    {

        //学习资料覆盖情况（文字）
        int userCount = sysUserStudyService.count(new QueryWrapper<SysUserStudy>().eq("user_id", sysUserStudy.getUserId()));
        int allCount = sysStudyDataService.count(new QueryWrapper<SysStudyData>().select("*"));
        Map<String, Object> build = MapUtil.builder(new HashMap<String, Object>())
                .put("userCount", userCount)
                .put("allCount", allCount).build();
        //学习资料学习时间分布(柱状图)
        DateTime start = DateUtil.beginOfWeek(DateUtil.lastWeek());
        DateTime end = DateUtil.endOfWeek(DateUtil.lastWeek());
        DateTime xun = DateUtil.offsetDay(start,1);
        List<SysStudyData> studyData = sysUserStudyService.getCount(sysUserStudy,start,end);
        Map<String,SysStudyData> studyDataByTime = MapUtil.builder(new HashMap<String,SysStudyData>()).build();
        for(SysStudyData studyData1:studyData)
        {
            DateTime time = new DateTime(studyData1.getTime());
            String formatTime = time.toString(DatePattern.NORM_DATE_FORMAT);
            if(studyDataByTime.containsKey(formatTime))
            {
                SysStudyData sameData = studyDataByTime.get(formatTime);
                studyData1.setVideoNum(sameData.getVideoNum()+studyData1.getVideoNum());
                studyData1.setPDFNum(sameData.getPDFNum()+studyData1.getPDFNum());
                studyData1.setTextNum(sameData.getTextNum()+studyData1.getTextNum());
                studyDataByTime.remove(formatTime);
            }
            studyDataByTime.put(formatTime,studyData1);
        }
//        quarterData.add(studyData);
//        while(index <= 7)
//        {
//            List<String> ids = new ArrayList<>();
////            List<SysUserStudy> study_id = sysUserStudyService.list(new QueryWrapper<SysUserStudy>().select("study_id").between("ifnull(update_time,create_time)", start, xun));
////            for(SysUserStudy study:study_id)
////            {
////                ids.add(study.getStudyId());
////            }
////            SysStudyData studyData = sysStudyDataService.getOne(new QueryWrapper<SysStudyData>().in("id",ids).select("count(case when type=0 then 1 end) as textNum,count(case when type=1 then 1 end) as videoNum,count(case when type=2 then 1 end) as PDFNum"));
//           SysStudyData studyData = sysUserStudyService.getCount(sysUserStudy,start,xun);
//            studyData.setAllNum(studyData.getPDFNum()+studyData.getTextNum()+studyData.getVideoNum());
//            quarterData.add(studyData);
//            start = xun;
//            xun = DateUtil.offsetDay(xun,1);
//            index++;
//        }
//        SysCheck checkLast = sysCheckService.getOne(new QueryWrapper<SysCheck>().select("ifnull(sum(add_time),0) as addTime", "ifnull(sum(over_time),0) as overTime").eq("user_id", user.getId()).between("create_time", start, end));
       studyDataByTime = MapUtil.sort(studyDataByTime);
       build.put("weekStudy",studyDataByTime);
        //最近学习的资料（Top3）文字
        List<SysStudyData> datas = new ArrayList<>();
        List<SysUserStudy> recent = sysUserStudyService.list(new QueryWrapper<SysUserStudy>().eq("user_id",sysUserStudy.getUserId()).orderByDesc("update_time").last("limit 0,3"));
        for(SysUserStudy study:recent)
        {
            datas.add(sysStudyDataService.getOne(new QueryWrapper<SysStudyData>().eq("id",study.getStudyId())));
        }
        build.put("recent",datas);
        //考试通过率
        QueryWrapper<SysUserExam> wrapper = new QueryWrapper<>();
        wrapper.select("*");
        int joinCount = sysUserExamService.count(wrapper);
        wrapper.eq("res",0);
        int failCount = sysUserExamService.count(wrapper);
        build.put("examJoin",joinCount);
        build.put("examFail",failCount);
        //学习资料种类（饼图）
        SysStudyData studyDataConuts = sysStudyDataService.getOne(new QueryWrapper<SysStudyData>().select("count(case when type=0 then 1 end) as textNum","count(case when type=1 then 1 end) as videoNum","count(case when type=2 then 1 end) as PDFNum"));
        build.put("typeCount",studyDataConuts);
        return Result.success(200,"成功",build,"");
    }
}


