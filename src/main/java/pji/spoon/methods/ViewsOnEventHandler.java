package pji.spoon.methods;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.view.View;
import spoon.reflect.code.CtBlock;
import spoon.reflect.declaration.CtMethod;

public abstract class ViewsOnEventHandler implements Method {
private String methodName ;
private String methodSignature ;

/**
 * Create an ViewsOnEventHandler this abstract class represents all the event handlers  in an android View Class
 * @param methodName the Simple name of the method
 * @param signature the Java signature of the method 
 */

	public ViewsOnEventHandler(String methodName,String signature)  {
		// TODO Auto-generated constructor stub
	this.methodName = methodName;
	this.methodSignature = signature;
	
	}
	/**
	 * 
	 */
	public boolean checkMethod(CtMethod<?> method){
		return method.getSimpleName().equals(methodName) && method.getSignature().equals(this.methodSignature);
	}
	public void processMethod(CtMethod<?> method){
		if (!(method == null)){
		CtBlock<?> block = method.getBody();
		if (!(block == null)){
		System.out.println(method.getDeclaringType().getQualifiedName());	
		System.out.println(method.getSignature());	
		System.out.println(block.toString());
		}
		}
	}
	public CtMethod<?> findMethod(Set<CtMethod<?>> setMethods){
		//	System.out.println(element.getQualifiedName()+"\n");
		//Set<CtMethod<?>> allmeths = element.getAllMethods(); 
		Iterator<CtMethod<?>> it = setMethods.iterator() ;
		CtMethod<?>methode = null ;
		boolean find = false ;
		while(it.hasNext() && !find){
			methode =it.next();
			//checks if the method in the parameter of processor is the method we are going to process
			if(checkStrategy(methode)){
			//	System.out.println(methode.getDeclaringType().getQualifiedName()+"-----view\n");	
			//DEBUG System.out.println(methode.getSimpleName()+"\n");
		
			//apply process instructions defined on the method
			find = true ;
		}

	}
		
		if(!find ){
			methode=null;
		} 
		return (methode) ;
		}

	private boolean checkStrategy(CtMethod<?> methode) {
		// TODO Auto-generated method stub
		return checkMethod(methode);
	}
	
	
}
