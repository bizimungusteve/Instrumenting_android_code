package pji.spoon.methods;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.view.View;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;

public abstract class ListenersEventHandlerMethod implements Method{

private String eventName ;	
private SetOnEventListener setterMethod ;

public boolean checkMethod(CtMethod<?> method){

	return method.equals(eventName);
}

public void processMethod(CtMethod<?> method){
	
}

public CtMethod<?> findMethod(CtType<View> element){
	CtMethod<?> handlerMethod =null;
	CtMethod<?> setterMethod =this.setterMethod.findMethod(element);
	
	List<CtParameter<?>> settParameters = setterMethod.getParameters();
	
	//check if the list size is equal to 1 i android or the setter have one argument the eventListener
	CtParameter<?> refeventListener =null;
	if (settParameters.size() == 1){
		refeventListener = settParameters.get(0);
		//TODO Change this to getTypeDeclaration the recent version which never returns null 
		Set<CtMethod<?>> allmeths= refeventListener.getType().getDeclaration().getAllMethods();
		Iterator<CtMethod<?>> it = allmeths.iterator() ;
		
		boolean find = false ;
		while(it.hasNext() && !find){
			handlerMethod =it.next();
			//checks if the method in the parameter of processor is the method we are going to process
			if(checkMethod(handlerMethod)){
				
			System.out.println(handlerMethod.getSimpleName()+"\n");
			//apply process instructions defined on the method
			find = true ;
		}

	}
		if (!find){
			handlerMethod =null;
		}
		
		
	}
	return handlerMethod ;
	
}

public void eventNameSetter(String eventName){
	this.eventName = eventName ;
}
public void setterMethodsetter(SetOnEventListener setterMethode){
	this.setterMethod= setterMethode ;
}

}
