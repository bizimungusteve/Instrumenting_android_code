package pji.spoon.methods;

import java.util.Set;

import android.view.View;
import android.view.View.OnClickListener;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;

/**
 * This Interface represents all the android method the main goal is to check whether a java given method is or not the method represented here
 * @author bizimungu
 *
 */
public interface Method {
	/**
	 * This method verify if a given CtMethod is the desired method  
	 * @param method the element (CtMethod to verify 
	 * @return true if this CtMethod is the method represented here false otherwise
	 */

	boolean checkMethod(CtMethod<?> method);
/**
 * Process a Given CtMethod the process can be a modification of the given element or instrumentation using spoon library
 * @param method the CtMethod to instrument
 */
	void processMethod(CtMethod<?> method);
	
	/**
	 * This method return the CtMethod using the startegy define in checkMthod 
	 * @param setMethod
	 * @return
	 */
	CtMethod<?> findMethod(Set<CtMethod<?>> setMethod);
	

}
