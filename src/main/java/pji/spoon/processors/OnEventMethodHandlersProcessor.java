package pji.spoon.processors;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import pji.spoon.methods.Method;
public class OnEventMethodHandlersProcessor extends AbstractProcessor<CtType<?>> {

	private List<Method> methList ;
/**
 * Create an OnEventMethodHandler Processor
 * @param methodList the methods list to be accessed by this processor 
 * the processor takes one by one methods in this list and check if it can find the Java android method it represent and modifiy it with the embed code 
 */
	public OnEventMethodHandlersProcessor(List<Method> methodList) {
		// TODO Auto-generated constructor stub
 this.methList=methodList;
	
	}

	@Override
	public void process(CtType<?> element) {
		// TODO Auto-generated method stub
		//System.out.println("searching for methods in-> "+element.getQualifiedName());
		Set<CtMethod<?>> allmeths = element.getAllMethods() ;
		if(allmeths != null){
	//Iterator<CtMethod<?>> itCtMeth = allmeths.iterator() ; 
	
		if(! this.methList.equals(null)){
			Iterator <Method> itMeth =this.methList.iterator();
		
		
		while (itMeth.hasNext()){
			Method meth =itMeth.next();
		CtMethod<?> method =	meth.findMethod(allmeths);
		meth.processMethod(method) ;
		
		}
	}
}}}
