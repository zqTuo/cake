/**
 *
 *
 * http://www.xkygame.com
 *
 * 版权所有，侵权必究！
 */

package io.renren.exception;

import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public R handleRRException(RRException e){
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());

		return r;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public R httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
		logger.error(e.getMessage());
		return R.error(e.getMessage());
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public R httpMediaTypeNotSupportedException(HttpRequestMethodNotSupportedException e){
		logger.error(e.getMessage());
		return R.error(e.getMessage() + "，请使用application/json提交您的请求");
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public R httpMessageNotReadableException(HttpRequestMethodNotSupportedException e){
		logger.error(e.getMessage());
		return R.error(e.getMessage() + "，缺少参数");
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public R missingServletRequestParameterException(HttpRequestMethodNotSupportedException e){
		logger.error(e.getMessage());
		return R.error(e.getMessage() + "，参数异常");
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error();
	}
}
