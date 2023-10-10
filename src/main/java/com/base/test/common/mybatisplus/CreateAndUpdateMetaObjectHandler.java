package com.base.test.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * MP注入处理器
 *
 * @author Lion Li
 * @date 2021/4/25
 */
public class CreateAndUpdateMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //根据属性名字设置要填充的值
        if (metaObject.hasGetter("createTime")) {
            if (metaObject.getValue("createTime") == null) {
                this.setFieldValByName("createTime", new Date(), metaObject);
            }
        }
        if (metaObject.hasGetter("createBy")) {
            if (metaObject.getValue("createBy") == null) {
            }
        }
        doUpdateFill(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        doUpdateFill(metaObject);
    }

    private void doUpdateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("updateBy")) {
        	//始终放入新的更新者和更新时间
        }
        if (metaObject.hasGetter("updateTime")) {
			this.setFieldValByName("updateTime", new Date(), metaObject);
        }
    }

}
