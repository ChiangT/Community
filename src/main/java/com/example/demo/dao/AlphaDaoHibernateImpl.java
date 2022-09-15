package com.example.demo.dao;
import org.springframework.stereotype.Repository;

@Repository("alphaHibernate")   //重命名bean名称，默认为首字母小写的类名
public class AlphaDaoHibernateImpl implements AlphaDao{

    @Override
    public String select() {
        return "Hibernate";
    }
}
