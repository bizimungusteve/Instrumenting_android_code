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
import spoon.reflect.factory.*;

public abstract class OnEventHandler implements Method {

	private String simpleName ;
	private String signature;
	
	//TODO usefull to check ---private String interfaceName ;
	
	public OnEventHandler(String simpleName,String signature){
		this.simpleName = simpleName ;
		this.signature = signature ;
		
		
		//TODO initialise an Inntreface Name this.interfaceName = interfaceName; 
	}
	public boolean checkMethod(CtMethod<?> method){
	return (method.getSimpleName().equals(this.simpleName)) && method.getSignature().equals(signature);
	}
/** Access or modify a spoon's CtMethod   
 * @param method The spoon's CtMethod to process  
 */
	public void processMethod(CtMethod<?> method){
		if (!(method == null)){
			
		CtBlock<?> block = method.getBody();
		if (!(block == null)){
			Factory factory = method.getFactory();
			block.insertBegin(factory.Code().createCodeSnippetStatement("/*"
					+ "\n************************\n"
					+ " \n*you can add code here*\n"
					+ " \n***********************/"));
		System.out.println(method.getDeclaringType().getQualifiedName());	
		System.out.println(method.getSignature());	
		System.out.println(block.toString());
		}
		}
	}
	
	
	public void addCodeInMethodBlock(String code){
		
		
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
		return checkMethod(method)  ;
	}
}
