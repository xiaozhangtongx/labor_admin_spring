package com.xiaozhang.springboot.controller;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaozhang.springboot.common.lang.Result;
import com.xiaozhang.springboot.domain.*;
import com.xiaozhang.springboot.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
        SysCheck userCheck = sysCheckService.getOne(new QueryWrapper<SysCheck>().eq("user_id",user.getId()).select("sum(over_time+add_time) as totalTime","user_id").between("create_time", startTime, endTime).groupBy("user_id"));
        int index = allTime.indexOf(userCheck);
        System.out.println("-------------->"+index);
        Map<String, Object> build = MapUtil.builder(new HashMap<String, Object>()).build();
        build.put("all",allTime);
        build.put("index",index+1);
        return Result.success(200,"1",allTime,"");
    }

    @PostMapping("/DividedData")
    public Result getDividedData(@RequestBody SysUser user)
    {
        DateTime start,end,startTime;
        Date endTime = new Date();
        Map<String, Object> build = MapUtil.builder(new HashMap<String, Object>()).build();
        List<ChartData> quarterData=new ArrayList<>();
        SysCheck check = new SysCheck();
        switch (user.getTimeScale()){
            case "week":
                start = DateUtil.beginOfWeek(DateUtil.lastWeek());
                DateTime offsetTime = DateUtil.offsetDay(start,1);
                SysCheck flag = new SysCheck();
                flag.setAddTime(0.0);
                flag.setOverTime(0.0);
                flag.setWorkTime(0.0);
                flag.setWorkDay(0);
                flag.setOverDay(0);
                flag.setLeaveDay(0);
                Map<String,SysCheck> checkDataByTime = MapUtil.builder(new HashMap<String,SysCheck>())
                        .put(start.toString(DatePattern.NORM_DATE_FORMAT),flag)
                        .put(offsetTime.toString(DatePattern.NORM_DATE_FORMAT),flag)
                        .put(DateUtil.offsetDay(offsetTime,1).toString(DatePattern.NORM_DATE_FORMAT),flag)
                        .put(DateUtil.offsetDay(offsetTime,2).toString(DatePattern.NORM_DATE_FORMAT),flag)
                        .put(DateUtil.offsetDay(offsetTime,3).toString(DatePattern.NORM_DATE_FORMAT),flag)
                        .put(DateUtil.offsetDay(offsetTime,4).toString(DatePattern.NORM_DATE_FORMAT),flag)
                        .put(DateUtil.offsetDay(offsetTime,5).toString(DatePattern.NORM_DATE_FORMAT),flag)
                        .build();
                end = DateUtil.endOfDay(DateUtil.endOfWeek(DateUtil.lastWeek()));
                startTime = DateUtil.lastWeek();
                List<SysCheck> checks = sysCheckService.list(new QueryWrapper<SysCheck>().select("add_time", "over_time","work_time","create_time","if(status=5,1,0) as leaveDay","if(status=0,1,0) as workDay","if(over_time=0,0,1) as overDay").eq("user_id", user.getId()).between("create_time", start, end).orderByAsc("create_time"));
                List<SysCheck> allTime = sysCheckService.list(new QueryWrapper<SysCheck>().select("sum(over_time+add_time+work_time) as totalTime","user_id").between("create_time", startTime, endTime).apply("user_id in (SELECT id FROM sys_user u WHERE u.dept_id="+user.getDeptId()+")").groupBy("user_id").orderByDesc("totalTime"));
                SysCheck userCheck = sysCheckService.getOne(new QueryWrapper<SysCheck>().eq("user_id",user.getId()).select("sum(over_time+add_time+work_time) as totalTime","user_id").between("create_time", startTime, endTime).groupBy("user_id"));
                int myIndex = allTime.indexOf(userCheck);
                for(SysCheck checkInfo:checks)
                {
                    DateTime time = new DateTime(checkInfo.getCreateTime());
                    String formatTime = time.toString(DatePattern.NORM_DATE_FORMAT);
                    if(checkDataByTime.containsKey(formatTime))
                    {
                        checkDataByTime.replace(formatTime,flag,checkInfo);
                    }
                }
                checkDataByTime = MapUtil.sort(checkDataByTime);
                List<SysCheck> valueList = checkDataByTime.values().stream().collect(Collectors.toList());
                build.put("datas",valueList);
                build.put("index",myIndex+1);
                break;
            case "month":
                start = DateUtil.beginOfMonth(DateUtil.lastMonth());
                end = DateUtil.endOfMonth(DateUtil.lastMonth());
                startTime = DateUtil.lastMonth();
                DateTime xun = DateUtil.offsetDay(start,9);
                int index=1;
                while(index <= 2)
                {
                    System.out.println("------->"+start);
                    System.out.println("------>"+xun);
                    check = sysCheckService.getOne(new QueryWrapper<SysCheck>().select("ifnull(sum(add_time),0) as addTime", "ifnull(sum(over_time),0) as overTime","ifnull(sum(work_time),0) as workTime","count(if(over_time=0,null,over_time)) as overDay","count(if(status=5,status,null)) as leaveDay","count(if(status=0,status,null)) as workDay").eq("user_id", user.getId()).between("create_time", start, xun));
                    quarterData.add(new ChartData("",check.getAddTime(), check.getOverTime(), check.getWorkTime(), check.getLeaveDay(),check.getOverDay(),check.getWorkDay()));
                    System.out.println();
                    if(index<2)
                    {
                        start = DateUtil.offsetDay(xun,1);
                        xun = DateUtil.offsetDay(xun,10);
                    }
                    index++;
                }
                //每月天数不同
                xun = DateUtil.offsetDay(xun,1);
                SysCheck checkLast = sysCheckService.getOne(new QueryWrapper<SysCheck>().select("ifnull(sum(add_time),0) as addTime", "ifnull(sum(over_time),0) as overTime","ifnull(sum(work_time),0) as workTime","count(if(over_time=0,null,over_time)) as overDay","count(if(status=5,status,null)) as leaveDay","count(if(status=0,status,null)) as workDay").eq("user_id", user.getId()).between("create_time", xun, end));
                quarterData.add(new ChartData("",checkLast.getAddTime(), checkLast.getOverTime(), check.getWorkTime(),checkLast.getLeaveDay(),checkLast.getOverDay(),checkLast.getWorkDay()));
                build.put("datas",quarterData);
                List<SysCheck> allTimeM = sysCheckService.list(new QueryWrapper<SysCheck>().select("sum(over_time+add_time+work_time) as totalTime","user_id").between("create_time", startTime, endTime).apply("user_id in (SELECT id FROM sys_user u WHERE u.dept_id="+user.getDeptId()+")").groupBy("user_id").orderByDesc("totalTime"));
                SysCheck userCheckM = sysCheckService.getOne(new QueryWrapper<SysCheck>().eq("user_id",user.getId()).select("sum(over_time+add_time+work_time) as totalTime","user_id").between("create_time", startTime, endTime).groupBy("user_id"));
                int indexM = allTimeM.indexOf(userCheckM);
                build.put("index",indexM+1);
                break;
            case "year":
                start = DateUtil.beginOfYear(DateUtil.offset(new Date(), DateField.YEAR,-1));
                startTime = DateUtil.beginOfYear(DateUtil.offset(new Date(), DateField.YEAR,-1));
                DateTime qurter = DateUtil.endOfMonth(DateUtil.endOfDay(DateUtil.offsetMonth(start,2)));
                System.out.println("------------>"+start+qurter);
                Integer Index=0;
                while(Index<=3)
                {
                    check = sysCheckService.getOne(new QueryWrapper<SysCheck>().select("ifnull(sum(add_time),0) as addTime", "ifnull(sum(over_time),0) as overTime","ifnull(sum(work_time),0) as workTime","count(if(over_time=0,null,over_time)) as overDay","count(if(status=5,status,null)) as leaveDay","count(if(status=0,status,null)) as workDay").eq("user_id", user.getId()).between("create_time", start, qurter));
                    quarterData.add(new ChartData("",check.getAddTime(), check.getOverTime(), check.getWorkTime(), check.getLeaveDay(),check.getOverDay(),check.getWorkDay()));
                    start = DateUtil.beginOfDay(DateUtil.beginOfMonth(DateUtil.offsetMonth(qurter,1)));
                    System.out.println("------->"+start);
                    qurter = DateUtil.endOfMonth(DateUtil.offsetMonth(qurter,3));
                    System.out.println("------->"+qurter);
                    Index++;
                }
                List<SysCheck> allTimeY = sysCheckService.list(new QueryWrapper<SysCheck>().select("sum(over_time+add_time) as totalTime","user_id").between("create_time", startTime, endTime).apply("user_id in (SELECT id FROM sys_user u WHERE u.dept_id="+user.getDeptId()+")").groupBy("user_id").orderByDesc("totalTime"));
                SysCheck userCheckY = sysCheckService.getOne(new QueryWrapper<SysCheck>().eq("user_id",user.getId()).select("sum(over_time+add_time) as totalTime","user_id").between("create_time", startTime, endTime).groupBy("user_id"));
                int indexY = allTimeY.indexOf(userCheckY);
                build.put("index",indexY+1);
                build.put("datas",quarterData);
                break;
            default:
                build.put("error","错误刻度！");
        }
        return Result.success(200,"获取成功",build,"");
    }

    @GetMapping("/studyData")
    public Result getStudyData(@RequestParam String userId)
    {
        //学习资料覆盖情况（文字）
        List<SysUserStudy> userCount= sysUserStudyService.list(new QueryWrapper<SysUserStudy>().select("DISTINCT study_id").eq("user_id", userId));
        int allCount = sysStudyDataService.count(new QueryWrapper<SysStudyData>().select("*"));
        Map<String, Object> build = MapUtil.builder(new HashMap<String, Object>())
                .put("userCount", userCount.size())
                .put("allCount", allCount).build();
        //学习资料学习时间分布(柱状图)
        DateTime start = DateUtil.beginOfWeek(DateUtil.lastWeek());
        DateTime end = DateUtil.endOfWeek(DateUtil.lastWeek());
        DateTime xun = DateUtil.offsetDay(start,1);
        List<SysStudyData> studyData = sysUserStudyService.getCount(userId,start,end);
        SysStudyData flag = new SysStudyData();
        flag.setTextNum(0);
        flag.setPDFNum(0);
        flag.setVideoNum(0);
        flag.setAllNum(0);
        Map<String,SysStudyData> studyDataByTime = MapUtil.builder(new HashMap<String,SysStudyData>())
                .put(start.toString(DatePattern.NORM_DATE_FORMAT),flag)
                .put(xun.toString(DatePattern.NORM_DATE_FORMAT),flag)
                .put(DateUtil.offsetDay(xun,1).toString(DatePattern.NORM_DATE_FORMAT),flag)
                .put(DateUtil.offsetDay(xun,2).toString(DatePattern.NORM_DATE_FORMAT),flag)
                .put(DateUtil.offsetDay(xun,3).toString(DatePattern.NORM_DATE_FORMAT),flag)
                .put(DateUtil.offsetDay(xun,4).toString(DatePattern.NORM_DATE_FORMAT),flag)
                .put(end.toString(DatePattern.NORM_DATE_FORMAT),flag)
                .build();
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
                studyData1.setAllNum(studyData1.getPDFNum()+studyData1.getTextNum()+studyData1.getVideoNum());
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
        List<SysStudyData> valueList = studyDataByTime.values().stream().collect(Collectors.toList());
        build.put("weekStudy",valueList);
        //最近学习的资料（Top3）文字
        List<SysStudyData> datas = new ArrayList<>();
        List<SysUserStudy> recent = sysUserStudyService.list(new QueryWrapper<SysUserStudy>().eq("user_id",userId).orderByDesc("update_time").last("limit 0,3"));
        for(SysUserStudy study:recent)
        {
            SysStudyData one = sysStudyDataService.getOne(new QueryWrapper<SysStudyData>().eq("id", study.getStudyId()));
            SysUser userInfoById = sysUserService.getById(one.getCreatorId());
            if (ObjectUtil.isNotNull(userInfoById)) {
                userInfoById.setPassword("");
                one.setCreator(userInfoById);
            }
            datas.add(one);
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


