package xyz.blushyes.config;

import java.time.LocalDateTime;
import java.util.Set;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import cn.dev33.satoken.stp.StpUtil;

@Configuration
public class DataConfig implements MetaObjectHandler {
    private static final String CREATED_TIME = "createdTime";
    private static final String UPDATED_TIME = "updatedTime";
    private static final String CREATED_BY = "createdBy";
    private static final String UPDATED_BY = "updatedBy";

    private static final Set<String> TIME_FIELDS = Set.of(CREATED_TIME, UPDATED_TIME);

    private static final Set<String> OPERATOR_FIELDS = Set.of(CREATED_BY, UPDATED_BY);


    /**
     * 插入时自动填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        for (String setterName : metaObject.getSetterNames()) {
            if (TIME_FIELDS.contains(setterName)) {
                metaObject.setValue(setterName, LocalDateTime.now());
            }
            if (OPERATOR_FIELDS.contains(setterName)) {
                metaObject.setValue(setterName, StpUtil.getLoginIdAsLong());
            }
        }
    }

    /**
     * 更新操作，自动填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        for (String setterName : metaObject.getSetterNames()) {
            if (UPDATED_TIME.equals(setterName)) {
                metaObject.setValue(setterName, LocalDateTime.now());
            }
            if (UPDATED_BY.equals(setterName)) {
                metaObject.setValue(setterName, StpUtil.getLoginIdAsLong());
            }
        }
    }
}
