package com.base.test.java.calculate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.test.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class calculateRulesMatchingResultTest {
    public static Boolean cacluateSingle(JSONObject single,Map obj,List<Map> materialOrderExecuteItemRules){
        // 单个条件计算结果
        Boolean conditionResult = false;
        // 获取规则类型  rule规则 group分组
        String ruleType = single.getString("type");
        // 分组
        if("group".equals(ruleType)){
            // 获取分组数据
            JSONArray groupData = single.getJSONArray("data");
            // 递归计算分组结果
            conditionResult = calculateRulesMatchingResultTest(groupData,obj,materialOrderExecuteItemRules);
            // 规则
        } else if("rule".equals(ruleType)) {
            // 获取规则下标
            Integer ruleIndex = single.getInteger("index");
            // 获取匹配规则
            Map itemRule =  ruleIndex < materialOrderExecuteItemRules.size() ? materialOrderExecuteItemRules.get(ruleIndex) : null;
            if(Objects.nonNull(itemRule)){
                String targetVal = StrHelper.getObjectValue(itemRule.get("targetVal"));
                conditionResult = StrHelper.getObjectValue(obj.get("targetVal")).equals(targetVal);

                System.out.println(ruleIndex+"["+single.getString("name")+"]进行了一次计算"+conditionResult);
            }
        }
        return conditionResult;
    }

    /**
     * 计算规则匹配结果
     * @param conditionArr
     * @param materialOrderExecuteItemRules
     * @return
     */
    public static Boolean calculateRulesMatchingResultTest(JSONArray conditionArr,Map obj,List<Map> materialOrderExecuteItemRules){
        boolean result = false;
        if(CollectionUtils.isEmpty(conditionArr)){
            return false;
        }
        // 遍历多条件
        for (int i = 0; i < conditionArr.size(); i++) {
            // 获取单个条件
                JSONObject single = conditionArr.getJSONObject(i);
            // 获取与上一项的逻辑运算符
            String lastOperator = i == 0  ? "": conditionArr.getJSONObject(i-1).getString("operator");
            // 第一项
            if(StringUtils.isBlank(lastOperator)){
                // 直接计算结果
                result = cacluateSingle(single,obj,materialOrderExecuteItemRules);
            }

            // 逻辑运算符为并且
            if (lastOperator.equals("and")) {
                // 增加 短路与
                result = result && cacluateSingle(single,obj,materialOrderExecuteItemRules);
            } else if (lastOperator.equals("or")) {
                // 增加 短路或
                result = result || cacluateSingle(single,obj,materialOrderExecuteItemRules);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String multilayerConditionJson = "[\n" +
                "    {\n" +
                "        \"type\": \"rule\",\n" +
                "        \"name\": \"规则1\",\n" +
                "        \"index\": 0,\n" +
                "        \"operator\": \"and\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"type\": \"group\",\n" +
                "        \"data\": [\n" +
                "            {\n" +
                "                \"type\": \"rule\",\n" +
                "                \"name\": \"规则2\",\n" +
                "                \"index\": 1,\n" +
                "                \"operator\": \"and\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"type\": \"rule\",\n" +
                "                \"name\": \"规则3\",\n" +
                "                \"index\": 2,\n" +
                "                \"operator\": \"or\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"type\": \"group\",\n" +
                "                \"data\": [\n" +
                "                    {\n" +
                "                        \"type\": \"rule\",\n" +
                "                        \"name\": \"规则4\",\n" +
                "                        \"index\": 3,\n" +
                "                        \"operator\": \"or\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"rule\",\n" +
                "                        \"name\": \"规则5\",\n" +
                "                        \"index\": 4,\n" +
                "                        \"operator\": \"\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"operator\": \"and\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"type\": \"rule\",\n" +
                "                \"name\": \"规则6\",\n" +
                "                \"index\": 5,\n" +
                "                \"operator\": \"\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"operator\": \"and\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"type\": \"rule\",\n" +
                "        \"name\": \"规则7\",\n" +
                "        \"index\": 6,\n" +
                "        \"operator\": \"\"\n" +
                "    }\n" +
                "]";
        // 条件数组
        JSONArray conditionArr = new JSONArray();
        try{
            // 转换条件数组
            conditionArr  = JSONArray.parseArray(multilayerConditionJson);
        }catch (Exception e){
            log.info("参数[" + multilayerConditionJson + "]转换JSONArray失败！" + e.getMessage(), e);
        }
        JSONArray finalConditionArr = conditionArr;

        List<Map> rules = new ArrayList<>();
        Map rule1 = new HashMap();
        rule1.put("targetVal","1");
        rules.add(rule1);

        Map rule2 = new HashMap();
        rule2.put("targetVal","0");
        rules.add(rule2);

        Map rule3 = new HashMap();
        rule3.put("targetVal","1");
        rules.add(rule3);

        Map rule4 = new HashMap();
        rule4.put("targetVal","1");
        rules.add(rule4);

        Map rule5 = new HashMap();
        rule5.put("targetVal","1");
        rules.add(rule5);

        Map rule6 = new HashMap();
        rule6.put("targetVal","1");
        rules.add(rule6);

        Map rule7 = new HashMap();
        rule7.put("targetVal","1");
        rules.add(rule7);

        Map obj =new HashMap();
        obj.put("targetVal","1");
        Boolean reulst = calculateRulesMatchingResultTest(finalConditionArr,obj,rules);

        System.out.println(reulst);

    }
}
