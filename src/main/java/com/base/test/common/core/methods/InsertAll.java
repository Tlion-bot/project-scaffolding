package com.base.test.common.core.methods;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 单sql批量插入
 *
 * @author Lion Li
 */
public class InsertAll extends AbstractMethod {

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		final String sql = "<script>insert into %s %s values %s</script>";
		final String fieldSql = prepareFieldSql(tableInfo);
		final String valueSql = prepareValuesSqlForMysqlBatch(tableInfo);
		final String sqlResult = String.format(sql, tableInfo.getTableName(), fieldSql, valueSql);
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
		return this.addInsertMappedStatement(mapperClass, modelClass, "insertAll", sqlSource, new NoKeyGenerator(), null, null);
	}

	private String prepareFieldSql(TableInfo tableInfo) {
		StringBuilder fieldSql = new StringBuilder();
		if (StrUtil.isNotBlank(tableInfo.getKeyColumn())) {
			fieldSql.append(tableInfo.getKeyColumn()).append(",");
		}
		tableInfo.getFieldList().forEach(x -> fieldSql.append(x.getColumn()).append(","));
		fieldSql.delete(fieldSql.length() - 1, fieldSql.length());
		fieldSql.insert(0, "(");
		fieldSql.append(")");
		return fieldSql.toString();
	}

	private String prepareValuesSqlForMysqlBatch(TableInfo tableInfo) {
		final StringBuilder valueSql = new StringBuilder();
		valueSql.append("<foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" separator=\"),(\" close=\")\">");
		if (StrUtil.isNotBlank(tableInfo.getKeyColumn())) {
			valueSql.append("#{item.").append(tableInfo.getKeyProperty()).append("},");
		}
		tableInfo.getFieldList().forEach(x -> valueSql.append("#{item.").append(x.getProperty()).append("},"));
		valueSql.delete(valueSql.length() - 1, valueSql.length());
		valueSql.append("</foreach>");
		return valueSql.toString();
	}
}

