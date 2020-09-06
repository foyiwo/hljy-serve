package com.mall.mbg.Mapper;

import com.mall.mbg.Model.LOrder;
import com.mall.mbg.Model.LOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LOrderMapper {
    long countByExample(LOrderExample example);

    int deleteByExample(LOrderExample example);

    int deleteByPrimaryKey(Integer orderId);

    int insert(LOrder record);

    int insertSelective(LOrder record);

    List<LOrder> selectByExample(LOrderExample example);

    LOrder selectByPrimaryKey(Integer orderId);

    int updateByExampleSelective(@Param("record") LOrder record, @Param("example") LOrderExample example);

    int updateByExample(@Param("record") LOrder record, @Param("example") LOrderExample example);

    int updateByPrimaryKeySelective(LOrder record);

    int updateByPrimaryKey(LOrder record);
}