package com.liujiaming.crm.entity.BO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.liujiaming.core.entity.PageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.elasticsearch.script.Script;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujiaming
 * crm通用搜索对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = " Crm高级筛选BO", description = "高级筛选表")
@ToString
public class CrmSearchBO extends PageEntity implements Serializable {

    @ApiModelProperty(value = "搜索条件")
    private String search;

    @ApiModelProperty(value = "公海ID")
    private Integer poolId;

    @ApiModelProperty(value = "场景ID")
    private Integer sceneId;

    @ApiModelProperty(value = "type 类型和这个 CrmTypeEnum枚举类对应")
    private Integer label;

    @ApiModelProperty(value = "排序字段")
    private String sortField;

    @ApiModelProperty(value = "排序字段 1 倒序 2 正序")
    private Integer order;

    @ApiModelProperty(value = "高级筛选列表")
    private List<Search> searchList = new ArrayList<>();

    @Data
    @ApiModel(value = "高级筛选子查询")
    @Accessors(chain = true)
    public static class Search implements Serializable {

        @ApiModelProperty(value = "条件名字例如电话（telephone）地址（address）")
        private String name;

        @ApiModelProperty(value = "values的数据格式例如电话的文本(text)数据日期的(datetime)数据格式")
        private String formType;

        @ApiModelProperty(value = "判断条件 等于 不等于 包含 不包含等 对应FieldSearchEnum这个枚举类")
        @JsonProperty("type")
        private FieldSearchEnum searchEnum;

        @ApiModelProperty(value = "es查询需要")
        private transient Script script;

        @ApiModelProperty(value = "值列表例如电话号码（['12363456783',]）")
        private List<String> values = new ArrayList<>();

        public Search(String name, String formType, FieldSearchEnum searchEnum, List<String> values) {
            this.name = name;
            this.formType = formType;
            this.searchEnum = searchEnum;
            this.values = values;
        }

        public Search(String name, String formType, FieldSearchEnum searchEnum) {
            this.name = name;
            this.formType = formType;
            this.searchEnum = searchEnum;
        }

        public Search() {
        }
    }

    @ApiModel(value = "字段搜索枚举")
    public enum FieldSearchEnum {
        /**
         * 为空
         */
        IS_NULL(5),
        /**
         * 不为空
         */
        IS_NOT_NULL(6),
        /**
         * 包含
         */
        CONTAINS(3),
        /**
         * 等于
         */
        IS(1),
        /**
         * 不等于
         */
        IS_NOT(2),
        /**
         * 不包含
         */
        NOT_CONTAINS(4),
        /**
         * 大于
         */
        GT(7),
        /**
         * 大于等于
         */
        EGT(8),
        /**
         * 小于
         */
        LT(9),
        /**
         * 小于等于
         */
        ELT(10),
        /**
         * 通过id
         */
        ID(11),
        /**
         * 前缀匹配
         */
        PREFIX(12),
        /**
         * 后缀匹配
         */
        SUFFIX(13),
        /**
         * 数字区间
         */
        RANGE(14),
        /**
         * 不存在
         */
        NULL(0),


        /**
         * 脚本
         */
        SCRIPT(-1);

        FieldSearchEnum(Integer type) {
            this.type = type;
        }

        @JsonCreator
        public static FieldSearchEnum create(String type) {
            for (FieldSearchEnum searchEnum : values()) {
                if (searchEnum.getType().toString().equals(type)) {
                    return searchEnum;
                }
            }
            return NULL;
        }

        private Integer type;

        public Integer getType() {
            return type;
        }

        @Override
        public String toString() {
            return type.toString() + ":" + name();
        }

        public String valueOf(Integer type) {
            return type.toString() + ":" + name();
        }
    }

}
