package com.mall.mbg.Mapper;

import com.mall.mbg.Model.LLogs;
import com.mall.mbg.Model.LLogsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LLogsMapper {
    long countByExample(LLogsExample example);

    int deleteByExample(LLogsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LLogs record);

    int insertSelective(LLogs record);

    List<LLogs> selectByExample(LLogsExample example);

    LLogs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LLogs record, @Param("example") LLogsExample example);

    int updateByExample(@Param("record") LLogs record, @Param("example") LLogsExample example);

    int updateByPrimaryKeySelective(LLogs record);

    int updateByPrimaryKey(LLogs record);
}