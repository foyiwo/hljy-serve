package com.mall.web.dto;

import com.mall.mbg.Model.LMember;
import com.mall.mbg.Model.LMemberExample;
import com.mall.mbg.Model.LMemberWechat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LMemberDto extends LMember {

    @ApiModelProperty(value = "微信")
    private LMemberWechat wechat;


}
