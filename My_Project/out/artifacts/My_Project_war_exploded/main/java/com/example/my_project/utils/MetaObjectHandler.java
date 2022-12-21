package com.example.my_project.utils;


import com.example.my_project.entity.Order;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

@Component
public class MetaObjectHandler implements com.baomidou.mybatisplus.core.handlers.MetaObjectHandler {
//    public static void main (String args[]){
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd 'at' hh:mm:ss");
//        Date date = new Date();
//        System.out.println(dateFormat.format(date));
//    }
//    @Override
//    public void insertFill(MetaObject metaObject) {
////        DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd 'at' hh:mm:ss");
//        this.fillStrategy(metaObject, "createDate", LocalDateTime.now());
//        this.fillStrategy(metaObject, "updateDate", LocalDateTime.now());
//    }

//    @Override
//    public void updateFill(MetaObject metaObject) {
//        this.fillStrategy(metaObject, "createDate", LocalDateTime.now());
//    }

//    @Override
//    public void insertFill(MetaObject metaObject) {
//        this.setFieldValByName("createDate", LocalDateTime.now(), metaObject);
//        this.setFieldValByName("updateDate", LocalDateTime.now(), metaObject);
//    }
//
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        this.setFieldValByName("updateDate", LocalDateTime.now(), metaObject);
//    }

    @Override
    public void insertFill(MetaObject metaObject){
        //fieldName为相对应entity内定义的名字
        this.strictUpdateFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createDate", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject){
        this.strictUpdateFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
    }
}
