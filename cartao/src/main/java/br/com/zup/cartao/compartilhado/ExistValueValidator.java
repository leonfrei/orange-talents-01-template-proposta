package br.com.zup.cartao.compartilhado;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class ExistValueValidator implements ConstraintValidator<ExistValue, Object> {

    private String fieldName;
    private Class domainClass;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(ExistValue existValue) {
        this.fieldName = existValue.fieldName();
        this.domainClass = existValue.domainClass();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(o)) {
            return true;
        }
        Query query = entityManager.createQuery("select 1 from " + domainClass.getName() + " where " + fieldName + " =:value");
        query.setParameter("value", o);
        List resultList = query.getResultList();
//        Assert.isTrue(resultList.size() <=1, "Foi encontrada mais de uma " + domainClass + " com o atributo " + fieldName);
        return !resultList.isEmpty();
    }
}
