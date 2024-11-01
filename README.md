
声明：本项目基于悟空crm（https://gitee.com/wukongcrm/crm_pro）  进行二次开发和修改，如有侵权请联系删除
适合于学习，商用请自行考虑法律风险
## 目录结构

``` lua
wk_crm
├── admin         -- 系统管理模块和用户管理模块
├── authorization -- 鉴权模块，目前仅用于登录鉴权，后期可能有更改
├── bi            -- 商业智能模块
├── core          -- 通用的代码和工具类
├── crm           -- 客户管理模块
├── examine       -- 审批模块
├── gateway       -- 网关模块
├── job           -- 定时任务模块
├── oa            -- 办公自动化
└── work          -- 项目管理模块
└── hrm           -- 人力资源管理模块

```
### 核心功能模块

CRM/OA功能：<br/>
客户管理：客户数据录入，支持数据查重，数据转移<br/>
线索管理：支持线索转化客户<br/>
客户池管理：可配置多个客户池，根据成交等多种条件设置回收规则<br/>
商机管理：支持设置多个商机，商机阶段自定义配置<br/>
合同管理：合同审批流配置<br/>
回款管理：回款审核、审批流配置<br/>
办公审批：支持配置审批流（请假、报销、借款、出差等自定义配置）<br/>
日程/任务：支持任务分配、任务<br/><br/>

人力资源功能：<br/>

招聘管理：支持候选人添加、筛选、面试管理<br/>
人员管理：多维度组织架构配置<br/>
薪酬管理：可以灵活配置薪酬类型<br/>
社保管理：支持全国不同地区社保配置<br/>
绩效考核：灵活配置绩效考核流程，支持OKR和KPI绩效考核模板<br/><br/>

员工端：<br/>
员工自助查询个人信息、薪酬信息、社保信息、绩效考核信息<br/><br/>

系统配置：<br/>
系统自定义字段配置<br/>
审批流配置<br/>
员工组织架构配置<br/>
角色权限配置（精确到字段权限）<br/>
日志配置<br/>
公海规则配置<br/>
业务参数配置<br/>
初始化数据配置<br/>
其他配置<br/>

| 名称                  | 版本                        | 说明 |
|---------------------|---------------------------|----|
| spring-cloud-alibaba| 2.2.1.RELEASE(Hoxton.SR3) |  核心框架  |
| swagger             | 2.9.2                     |  接口文档  |
| mybatis-plus        | 3.3.0                     |  ORM框架  |
| sentinel            | 2.2.1.RELEASE             |  断路器以及限流  |
| nacos               | 1.2.1.RELEASE             |  注册中心以及分布式配置管理  |
| seata               | 1.2.0                     |  分布式事务 |
| elasticsearch       | 2.2.5.RELEASE(6.8.6)      |  搜索引擎中间件  |
| jetcache            | 2.6.0                     |  分布式缓存框架  |
| xxl-job             | 2.1.2                     |  分布式定时任务框架  |
| gateway             | 2.2.2.RELEASE             |  微服务网关        |
| feign               | 2.2.2.RELEASE             |  服务调用        |

### 一、前置环境
- Jdk1.8
- Maven3.5.0+   
- Mysql5.7.20 （<a href="https://gitee.com/wukongcrm/crm_pro/wikis/mysql配置说明" target="_blank">数据库安装注意事项</a>）
- Redis(版本不限)
- Elasticsearch 6.8.6 （<a href="https://gitee.com/wukongcrm/crm_pro/wikis/elasticsearch配置说明" target="_blank">环境配置注意事项</a>）
- Seata（1.2.0）（<a href="https://gitee.com/wukongcrm/crm_pro/wikis/seata" target="_blank">配置说明</a>）
- Sentinel（1.7.2）（项目中sentinel使用8079端口）
- Nacos（1.2.1)
#### 2.模块依赖关系 <br/>

```
- 除网关外，其余项目均依赖于admin模块，用于获取当前登录人的信息
- oa模块的任务依赖于work模块，其余一些关联业务功能依赖于crm模块，examine模块
- 商业智能依赖crm,oa模块

```
禁用 only_full_group_by
你可以通过禁用 MySQL 的 only_full_group_by 模式来避免此限制。你可以通过以下方式禁用：


在 MySQL 会话中运行以下 SQL 语句禁用当前会话的 only_full_group_by：
SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));

