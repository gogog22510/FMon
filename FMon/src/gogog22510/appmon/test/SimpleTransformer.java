package gogog22510.appmon.test;

import gogog22510.appmon.assist.AbstractTransformer;

import java.util.List;
import java.util.Map;

import javassist.*;


public class SimpleTransformer extends AbstractTransformer {

	@Override
	protected void initializeMapping(Map<String, List<String>> redefinedMap) {
		addNewMethod("gogog22510.appmon.test.foo", "doIt");
	}

	@Override
	protected void changeMethodImpl(CtBehavior method)	throws NotFoundException, CannotCompileException {
		if (method.getName().equals("doIt")) {
			method.insertBefore("System.out.println(\"started method at \" + new java.util.Date());");
			method.insertAfter("System.out.println(\"ended method at \" + new java.util.Date());");
		}
	}

}