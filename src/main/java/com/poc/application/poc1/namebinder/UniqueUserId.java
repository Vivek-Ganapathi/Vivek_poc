package com.poc.application.poc1.namebinder;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.poc.application.poc1.dao.UserDao;
import com.poc.application.poc1.entity.User;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;


@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueUserId.UniqueUserIdValidator.class})
@Documented
public @interface UniqueUserId {
	
	String message() default "User Already Exists";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    
    public class UniqueUserIdValidator implements ConstraintValidator<UniqueUserId, User>  {

    	private UserDao userDao = new UserDao();
    	
    	@Override
    	public boolean isValid(User value, ConstraintValidatorContext context) {
    		User user = userDao.findByUserId(value.getUserId());
    		if(user != null)
    		    return false;
    		
    	  return true;
    	}

    }
}
