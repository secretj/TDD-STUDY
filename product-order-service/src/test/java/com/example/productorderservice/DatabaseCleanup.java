package com.example.productorderservice;

import com.google.common.base.CaseFormat;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.Entity;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DatabaseCleanup implements InitializingBean {
    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    /**
     * entity를 모두 가져와서 entity 어노테이션과 table 어노테이션이 있는지 확인을 하고
     * 테이블 이름들을 모두 리스트에 담는다.
     * 테이블 어노테이션이 없는 애들은 camelCase로 된 이름을 snake로 바꿔주는 라이브러리
     */
    @Override
    public void afterPropertiesSet() {
        final Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        tableNames = entities.stream()
                .filter(e -> isEntity(e) && hasTableAnnotation(e))
                .map(e -> {
                    String tableName = e.getJavaType().getAnnotation(Table.class).name();
                    return tableName.isBlank() ? CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()) : tableName;
                })
                .collect(Collectors.toList());

        final List<String> entityNames = entities.stream()
                .filter(e -> isEntity(e) && !hasTableAnnotation(e))
                .map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
                .toList();

        tableNames.addAll(entityNames);
    }

    private boolean isEntity(final EntityType<?> e) {
        return null != e.getJavaType().getAnnotation(Entity.class);
    }

    private boolean hasTableAnnotation(final EntityType<?> e) {
        return null != e.getJavaType().getAnnotation(Table.class);
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate(); // 참조 무결성 무시

        for (final String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate(); // truncate
            entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1").executeUpdate();
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

}
