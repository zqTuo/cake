/**
 *
 *
 * http://www.xkygame.com
 *
 * 版权所有，侵权必究！
 */

package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.TokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Token
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface TokenDao extends BaseMapper<TokenEntity> {
	
}
