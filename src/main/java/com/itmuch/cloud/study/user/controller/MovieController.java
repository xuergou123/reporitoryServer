package com.itmuch.cloud.study.user.controller;

import com.itmuch.cloud.study.user.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.itmuch.cloud.study.user.entity.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class MovieController {
  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @GetMapping("/user/{id}")
  public User findById(@PathVariable Long id) {
    return this.restTemplate.getForObject("http://localhost:8000/" + id, User.class);
  }

  @GetMapping("/login")
  public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
    Result r = new Result();
    String sql = "select * from sys_user where username=? and password = ?";
    List<Map<String,Object>> data = jdbcTemplate.queryForList(sql,username,password);
    if(data.size()>0){
      r.setCode(200);
      r.setMsg("登陆成功！");
      r.setData(data);
    }else{
      r.setCode(400);
      r.setMsg("用户名或密码错误！");
      r.setData(null);
    }
    return r;
  }

  @GetMapping("/getGoodsList")
  public Result getGoodsList(@RequestParam("userid") String userid,
                             @RequestParam("filter") String filter,
                             @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int size) {
    Result r = new Result();
    Map<String,Object> result = new HashMap<>();
    String sql0 = "select count(*) from goods where userid=?";
    int count = jdbcTemplate.queryForObject(sql0,new Object[]{userid},Integer.class);

    String sql = "select * from goods where userid=? ";
    if(!StringUtils.isEmpty(filter)){
        sql+=" and goodsname like '%" +filter+ "%'";
    }
     sql +=" order by CREATETIME desc limit ?,?";
    List<Map<String,Object>> data = jdbcTemplate.queryForList(sql,userid,(page-1)*size,size);


    if(data.size()>0){
      r.setCode(200);
      r.setMsg("查询成功！");
      result.put("data",data);
      result.put("total",count);
      r.setData(result);
    }else{
      r.setCode(500);
      r.setMsg("出错！");
      r.setData(null);
    }
    return r;
  }

  @GetMapping("/inorout")
  public Result inorout(@RequestParam("inoroutCount") String inoroutCount, @RequestParam("uuid") String uuid,@RequestParam("inorout") String inorout) {
    Result r = new Result();
    Map<String,Object> result = new HashMap<>();
    String sql =  "";
    if(inorout.equals("入库")){
      sql = "update goods set goodscount = (goodscount+?) where uuid = ?";
    }else if(inorout.equals("出库")){
      sql = "update goods set goodscount = (goodscount-?) where uuid = ?";
    }else{
      r.setCode(500);
      r.setMsg("参数错误！");
      r.setData(null);
    }

    int res = jdbcTemplate.update(sql,inoroutCount,uuid);
    if(res>0){

    }



    if(res>0){
      r.setCode(200);
      r.setMsg("操作成功！");
      r.setData(null);
    }else{
      r.setCode(500);
      r.setMsg("出错！");
      r.setData(null);
    }
    return r;
  }

    @GetMapping("/addGoodsList")
    public Result addGoodsList(@RequestParam("uuid") String uuid,
                               @RequestParam("goodsname") String goodsname,
                               @RequestParam("goodscount") String goodscount,
                               @RequestParam("goodsprice") String goodsprice,
                               @RequestParam("memo") String memo) {
        Result r = new Result();
        Map<String,Object> result = new HashMap<>();
        String sql = "insert into  goods(uuid,goodsname,goodscount,goodsprice,memo) values(?,?,?,?,?)";
        int count = jdbcTemplate.update(sql,uuid,goodsname,goodscount,goodsprice,memo);
        if(count>0){
            r.setCode(200);
            r.setMsg("添加成功！");
            r.setData(null);
        }else{
            r.setCode(500);
            r.setMsg("添加失败！");
            r.setData(null);
        }
        return r;
    }

    @GetMapping("/editGoodsList")
    public Result editGoodsList(@RequestParam("uuid") String uuid,
                               @RequestParam("goodsname") String goodsname,
                               @RequestParam("goodscount") String goodscount,
                               @RequestParam("goodsprice") String goodsprice,
                               @RequestParam("memo") String memo) {
        Result r = new Result();
        Map<String,Object> result = new HashMap<>();
        String sql = "update  goods set goodsname=?,goodscount=?,goodsprice=?,memo=? where uuid = ?";
        int count = jdbcTemplate.update(sql,goodsname,goodscount,goodsprice,memo,uuid);
        if(count>0){
            r.setCode(200);
            r.setMsg("添加成功！");
            r.setData(null);
        }else{
            r.setCode(500);
            r.setMsg("添加失败！");
            r.setData(null);
        }
        return r;
    }


}
