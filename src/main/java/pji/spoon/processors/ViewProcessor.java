package pji.spoon.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCatch;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtStatement;
import spoon.support.reflect.code.CtCodeSnippetStatementImpl;
import spoon.reflect.factory.MethodFactory;
import spoon.reflect.factory.ClassFactory;
import spoon.reflect.factory.TypeFactory;
import spoon.Launcher;
import spoon.reflect.factory.*;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Level;

import android.view.View;
//import spoon.support.reflect.declaration.CtTypeParameterImpl;


/**
 * 
 * @author bizimungu
 *Detect all the classes in a project and displays all the methods  
 *This processor is used for debug 
 */
public class ViewProcessor extends AbstractProcessor<CtType<?>> {

	@Override
	public void process(CtType<?> element) {
		// TODO Auto-generated method stub
		//get A list of all onclick methodes
		//CTMethode listOnClickListenerMethodes= element.getMethodsByName("setOnClickListener)
	//	System.out.println("----------------------------i have detected a View\n");
	//	System.out.println("ViewName :"+element.getQualifiedName()+"\n");
		
		//get all the methodes named SetOnclickListener of this View
	/*	CtPackage pkg= element.getPackage();
		if(pkg.equals(null)){
			System.out.println("null");
		}else {
		System.out.println(pkg.getSimpleName());
		}*/
		//Get all Methods
	//	System.out.println("***Methods\n");
		System.out.println("+type"+element.getQualifiedName()+"_______________________________");
		if(element.isSubtypeOf(getFactory().Type().createReference("android.view.View"))){
		System.out.println("//is sub type of view...");
		}
		Set<CtMethod<?>> allmeth = element.getAllMethods();
		//System.out.println("....Methods...");
		
		// From A set of metho
		
List<String> listOfViewMeth = new ArrayList<String>();


Iterator<CtMethod<?>> it = allmeth.iterator() ;

while(it.hasNext()){
	CtMethod<?>method =it.next();
	
//if(method.getSimpleName().equals("SetOnClickListener"))
	//System.out.println("NAME=> "+method.getSimpleName()+"\n");
	System.out.println("       * "+method.getSignature()+"\n");
}
		
	}
	
	}

