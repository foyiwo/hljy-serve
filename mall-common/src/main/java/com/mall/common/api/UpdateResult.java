package com.mall.common.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用的更新的结果
 *
 * @author Carl Don
 * @Date 2020/5/25  10:23
 * @Version 1.0
 */
@Data
public class UpdateResult {

    @ApiModelProperty(value = "接收数量")
    private Integer receiveCount;

    @ApiModelProperty(value = "插入数量")
    private Integer insertCount;

    @ApiModelProperty(value = "更新数量")
    private Integer updateCount;

    @ApiModelProperty(value = "失败数量")
    private Integer failCount;

    @ApiModelProperty(value = "无更新数量,即相等数量")
    private Integer equalCount;
}
