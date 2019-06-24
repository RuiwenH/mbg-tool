package ${SERVICE_PACKAGE}.impl;

import ${MAPPER_PACKAGE}.${modelNameUpperCamel}Mapper;
import ${MODEL_PACKAGE}.${modelNameUpperCamel};
import ${SERVICE_PACKAGE}.I${modelNameUpperCamel}Service;
import ${basePackage}.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}> implements I${modelNameUpperCamel}Service {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}
