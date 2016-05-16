package pji.spoon.processors;


import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;

import java.util.Iterator;
import java.util.Set;

import android.view.View;
import android.view.View.OnClickListener;
import pji.spoon.methods.Method;
/**
 * The generic class of all method's processor that can be spooned , every class thats implements this class 
 * @author bizimungu
 *
 */
public class MethodsInViewProcessor extends AbstractProcessor<CtType<View>>{

	/*
	 * The desired Metho innformations
	 */
	// The processor retreive all the information on the desired in this object  
	private Method method; 
	
	public MethodsInViewProcessor(Method meth){
	this.method= meth ;
}
	
	
	@Override
	public void process(CtType<View> element) {
		// TODO Auto-generated method stub
	

		Set<CtMethod<?>> allmeths = element.getAllMethods();   	
		CtMethod<?> method = this.method.findMethod(allmeths);
		this.method.processMethod(method);
		
		
	}
}
