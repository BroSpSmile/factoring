package com.smile.start;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

public class GenerateSqlTest {

    @Test
    public void testGenerateSql() {
        String tableName = "contract_sign_list";
        String str = "  `serial_no` varchar(64) COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '业务流水',\n" +
                "  `contract_serial_no` varchar(64) COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '合同流水',\n" +
                "  `sign_list_name` varchar(256) COLLATE utf8_general_mysql500_ci NOT NULL COMMENT '签署清单名称',";

        List<String> fields = Lists.newArrayList(Splitter.on("\n").split(str));
        List<String> fieldList = Lists.newArrayList();
        List<String> camelFieldList = Lists.newArrayList();
        fields.forEach(e -> {
            String substring = e.substring(e.indexOf("`") + 1, e.lastIndexOf("`"));
            fieldList.add(substring);
            camelFieldList.add(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, substring));
        });
        StringBuilder insertSql = new StringBuilder("insert into ");
        insertSql.append(tableName).append(" (");
        insertSql.append(Joiner.on(",").join(fieldList));
        insertSql.append(") values (#{");
        insertSql.append(Joiner.on("},#{").join(camelFieldList));
        insertSql.append("})");
        System.out.println(insertSql);

        StringBuilder updateSql = new StringBuilder("update ");
        updateSql.append(tableName).append(" set ");
        List<String> tempList = Lists.newArrayList();
        for(int i = 0; i < fieldList.size(); i++) {
            tempList.add(fieldList.get(i) + "=#{" + camelFieldList.get(i) + "}");
        }
        updateSql.append(Joiner.on(",").join(tempList));
        updateSql.append(" where id=#{id}");
        System.out.println(updateSql);
    }
}
