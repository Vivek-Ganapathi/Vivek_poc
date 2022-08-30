package com.poc.application.poc1.namebinder;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.poc.application.poc1.dao.ItemDao;
import com.poc.application.poc1.entity.Item;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;


@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueItemId.UniqueItemIdValidator.class})
@Documented
public @interface UniqueItemId {
	
	String message() default "ItemId Already Exists";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    
    public class UniqueItemIdValidator implements ConstraintValidator<UniqueItemId, Item>  {

    	private ItemDao itemDao = new ItemDao();
    	

		@Override
		public boolean isValid(Item value, ConstraintValidatorContext context) {
			Item item = itemDao.findByItemId(value.getItemId());
    		if(item != null)
    		    return false;
    		
    	  return true;
		}

    }
}
