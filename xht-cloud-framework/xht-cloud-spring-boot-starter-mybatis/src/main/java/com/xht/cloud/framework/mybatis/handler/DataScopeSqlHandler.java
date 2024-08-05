package com.xht.cloud.framework.mybatis.handler;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xht.cloud.framework.mybatis.core.DataScopeFieldBuilder;
import com.xht.cloud.framework.mybatis.dataobject.AbstractDO;
import com.xht.cloud.framework.mybatis.handler.dto.DataScopeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.Objects;

/**
 * 描述 ：
 *
 * @author 小糊涂
 **/
@Slf4j
@SuppressWarnings("all")
public abstract class DataScopeSqlHandler implements InitializingBean, DataScopeSql {

    protected final DataScopeSqlFactory dataScopeFactory;

    public DataScopeSqlHandler(DataScopeSqlFactory dataScopeFactory) {
        this.dataScopeFactory = dataScopeFactory;
    }

    /**
     * 权限前置校验 并且返回校验后的信息
     *
     * @return {@link DataScopeDTO}
     */
    public abstract DataScopeDTO verify();

    /**
     * @return 如果是 mybatisPlus 那么不需要返回 如果不是 mybatisPlus 则返回sql
     */
    public final <T extends AbstractDO> String execute(DataScopeFieldBuilder<T> builder, Wrapper<T> wrapper) {
        DataScopeDTO dataScopeDTO = verify();
        if (!dataScopeDTO.isVerify()) {
            return "";
        }
        if (Objects.isNull(wrapper)) {
            return generate(dataScopeDTO, builder);
        }
        if (wrapper instanceof LambdaQueryWrapper<T>) {
            generate(dataScopeDTO, builder, (LambdaQueryWrapper<T>) wrapper);
        }
        if (wrapper instanceof QueryWrapper<T>) {
            generate(dataScopeDTO, builder, (QueryWrapper<T>) wrapper);
        }
        return null;
    }


}
