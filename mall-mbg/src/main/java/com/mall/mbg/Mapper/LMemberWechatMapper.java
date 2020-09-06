package com.mall.mbg.Mapper;

import com.mall.mbg.Model.LMemberWechat;
import com.mall.mbg.Model.LMemberWechatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LMemberWechatMapper {
    long countByExample(LMemberWechatExample example);

    int deleteByExample(LMemberWechatExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LMemberWechat record);

    int insertSelective(LMemberWechat record);

    List<LMemberWechat> selectByExample(LMemberWechatExample example);

    LMemberWechat selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LMemberWechat record, @Param("example") LMemberWechatExample example);

    int updateByExample(@Param("record") LMemberWechat record, @Param("example") LMemberWechatExample example);

    int updateByPrimaryKeySelective(LMemberWechat record);

    int updateByPrimaryKey(LMemberWechat record);
}