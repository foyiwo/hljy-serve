package com.mall.common.annotationn;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 时间格式化
 * <b>类名称：</b>DateFormat<br/>
 * <b>类描述：</b><br/>
 * <b>创建时间：</b>2018年3月15日上午9:56:03<br/>
 * <b>备注：</b>tzm<br/>
 * @version 1.0.0<br/>
 *
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {
    
    public String value();
}
