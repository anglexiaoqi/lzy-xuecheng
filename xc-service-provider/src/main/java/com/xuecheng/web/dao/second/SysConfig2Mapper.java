package com.xuecheng.web.dao.second;

import com.xuecheng.domain.SysConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysConfig2Mapper {
    int deleteByPrimaryKey(String variable);

    int insert(SysConfig record);

    int insertSelective(SysConfig record);

    SysConfig selectByPrimaryKey(String variable);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);

    List<SysConfig> selectList(String variable);
}