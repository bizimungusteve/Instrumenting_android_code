package pji.spoon.methods;

import java.util.Iterator;
import java.util.Set;

import android.view.View;
import android.view.View.OnClickListener;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;

public abstract class OnEventHandler implements Method {

	private String simpleName ;
	private String interfaceQualifiedName ;
	//TODO usefull to check ---private String interfaceName ;
	
	public OnEventHandler(String simpleName,String interfaceQualifiedName){
		this.simpleName = simpleName ;
		this.interfaceQualifiedName =interfaceQualifiedName ;
		
		
		//TODO initialise an Inntreface Name this.interfaceName = interfaceName; 
	}
	public boolean checkMethod(CtMethod<?> method){
	return (method.getSimpleName().equals(this.simpleName));
	}
/** Access or modify a spoon's CtMethod   
 * @param method The spoon's CtMethod to process  
 */
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
	
	
public boolean checkInterfaceOf(CtMethod<?> method){
	System.out.println("Debug Checking interfaces of "+method.getSimpleName());
	boolean find = false;	
	if (method != null){
		Set <CtTypeReference<?>> supIntfc = method.getDeclaringType().getSuperInterfaces();
		
		Iterator<CtTypeReference<?>> it =supIntfc.iterator();
		//boolean find = false;
		while(it.hasNext() && !find){
			CtTypeReference<?> typr = it.next() ;
		//	CtType<?> typ = typr.getDeclaration() ;
			System.out.println(typr.getSimpleName());
			if ((typr != null)){
				
			if(typr.getQualifiedName().equals(this.interfaceQualifiedName)){
				find = true ;
			//TODO 	System.out.println("true");
			}}
			
		}
		
		//boolean rep1 = method.getDeclaringType().isSubtypeOf(method.getFactory().Type().createArrayReference("android.view.View.OnClickListener"));
		}
		return  find ;
	
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
	
	public boolean checkStrategy(CtMethod<?>method){
		return checkMethod(method) &&checkInterfaceOf(method) ;
	}
}
