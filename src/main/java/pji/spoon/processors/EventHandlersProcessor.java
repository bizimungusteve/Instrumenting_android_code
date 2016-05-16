package pji.spoon.processors;
import java.util.EventListener;

import android.view.View;
import android.view.View.OnClickListener;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;

public class EventHandlersProcessor extends AbstractProcessor<CtMethod<?>> {

	@Override
	public void process(CtMethod<?> element) {
		// TODO Auto-generated method stub
		
		CtTypeReference onclickListener = getFactory().Code().createCtTypeReference(OnClickListener.class); 
		if(element.getDeclaringType().isInterface()&& element.getDeclaringType().getReference().equals(onclickListener)){
			System.out.println(element.getBody().getLabel());
		}
		if(element.getSimpleName().equals("OnClickListner")){
			System.out.println(element.getBody().getLabel());
		}
	}

	
	
}
