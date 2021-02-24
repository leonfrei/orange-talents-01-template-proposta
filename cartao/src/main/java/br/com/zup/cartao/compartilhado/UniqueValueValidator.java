package br.com.zup.cartao.compartilhado;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String fieldName;
    private Class domainClass;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueValue uniqueValue) {
        this.fieldName = uniqueValue.fieldName();
        this.domainClass = uniqueValue.domainClass();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Query query = entityManager.createQuery("select 1 from " + domainClass.getName() + " where " + fieldName + " =:value");
        query.setParameter("value", o);
        List resultList = query.getResultList();
        Assert.isTrue(resultList.size() <=1, "Foi encontrada mais de uma " + domainClass + " com o atributo " + fieldName);
        return resultList.isEmpty();
    }
}
