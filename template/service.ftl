package 패키지경로

@Service
@Transactional
public class ${upper_class_name}Service {

    @Autowired
    public ${upper_class_name}Mapper mapper;

    <#list columns as column>${column.name} </#list>
}