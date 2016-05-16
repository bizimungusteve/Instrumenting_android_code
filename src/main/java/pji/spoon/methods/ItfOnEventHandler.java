package pji.spoon.methods;

import java.util.Iterator;
import java.util.Set;

import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;

public abstract class ItfOnEventHandler extends OnEventHandler {
private String interfaceQualifiedName ;

	public ItfOnEventHandler(String simpleName, String signature,String itfQualifiedName) {
		super(simpleName,signature);
		this.interfaceQualifiedName = itfQualifiedName ;
		// TODO Auto-generated constructor stub
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
	public boolean checkStrategy(CtMethod<?> methode) {
		// TODO Auto-generated method stub
		return super.checkMethod(methode) && checkInterfaceOf(methode);
	}
}
