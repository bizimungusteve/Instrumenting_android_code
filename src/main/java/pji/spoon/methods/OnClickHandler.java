package pji.spoon.methods;

import java.util.Iterator;
import java.util.Set;

import android.renderscript.Type;
import android.view.View;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.TypeFactory;
import spoon.reflect.reference.CtTypeReference;

public class OnClickHandler extends ItfOnEventHandler {
private TypeFactory tf;
	public OnClickHandler(){
		super("onClick" ,"void onClick(android.view.View)","android.view.View$OnClickListener") ;
	//	this.tf = new TypeFactory();
	}
	
	
	public boolean checkMethod(CtMethod<?> method){
		boolean rep =super.checkMethod(method);
//		getDeclaringType().isSubtypeOf(method.getFactory().Type().createArrayReference("android.view.View.OnClickListener"));
		return  rep  && method.getSignature().equals("void onClick(android.view.View)");
	}
}
