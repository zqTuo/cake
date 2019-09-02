package io.renren.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author:Created By Clarence
 * @Date: 2019/8/27 10:08
 */
@ApiModel(value = "返回类")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    @ApiModelProperty(value = "请求状态码")
    private int code;
    @ApiModelProperty(value = "描述")
    private String msg;
    @ApiModelProperty(value = "响应数据")
    private T data;

    public Result(){}

    private Result(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Result(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result ok(){
        this.code = 0;
        this.msg = "success";
        return this;
    }
    public Result ok(T data){
        return new Result<>(0,"success",data);
    }

    public Result error(String msg){
        return new Result(500,msg);
    }

    public Result error(int code, String msg) {
        return new Result(code,msg);
    }
}
