## Mybatis Generator(MBG)
* Mybatis代码生成器

# 功能特点
* 生产数据库实体类，使用Annotation注解方式建立实体类与数据库的映射关系，实体类包含表注释
* 生成Mapper.xml文件，仅包含BaseResultMap的字段映射
* 生产dao层的Mapper接口类
* 生产Service和实现类
* 生产controller类（可选RESTful风格的类）

# 使用方法
* 使用示例见mbg-tool/src/test/java/generator/CodeGenerator.java的main方法
* 1、使用前调整数据库连接信息，见CodeGenerator.java静态属性
* 2、指定文件生产的路径，见ProjectConstant.java，调整core包中的类路径（含controller.ftl中的路径），实际项目会放在更加标准的包路径中
* 3、设置本次要生成的代码对应的表（可以设置多个），见CodeGenerator.java的main方法：genCodeByCustomModelName("t_user", "User");
* 4、将生产的类文件，拷贝至实际项目中

# 其他高级配置
* 调整实体类ID主键的生成策励GeneratedKey
* 修改CodeGenerator.genCodeByCustomModelName(String tableName, String modelName),决定是否生成controller、service类
* set方法去除前后空格，例如this.userName = userName == null ? null : userName.trim();见javaModelGeneratorConfiguration.addProperty(PropertyRegistry.MODEL_GENERATOR_TRIM_STRINGS, "true");

# 待优化
* 实体类取消实现Serializable接口，统一在BaseEntity处理 ——不调整，需要代码自动生成private static final long serialVersionUID = 1L;
* 实体类使用@Data取代set、get方法，避免开发人员在实体类中修改set、get方法。