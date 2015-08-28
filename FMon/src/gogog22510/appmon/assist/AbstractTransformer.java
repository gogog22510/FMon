package gogog22510.appmon.assist;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;

public abstract class AbstractTransformer implements ClassFileTransformer {
	private Map<String, List<String>> redefinedMap;
	
	public AbstractTransformer() {
		redefinedMap = new HashMap<String, List<String>>();
	}
	
	protected void addNewMethod(String clazzName, String methodName) {
		if(clazzName == null || methodName == null) return;
		if(redefinedMap.containsKey(clazzName)) {
			List<String> methods = redefinedMap.get(clazzName);
			if(methods == null) {
				methods = new ArrayList<String>();
				redefinedMap.put(clazzName, methods);
			}
			if(!methods.contains(methodName))
				methods.add(methodName);
		} else {
			List<String> methods = new ArrayList<String>();
			methods.add(methodName);
			redefinedMap.put(clazzName, methods);
		}
	}

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		return transformClass(classBeingRedefined, classfileBuffer);
	}
	
	protected byte[] transformClass(Class<?> classToTransform, byte[] b) {
		ClassPool pool = ClassPool.getDefault();
		CtClass cl = null;
		try {
			cl = pool.makeClass(new java.io.ByteArrayInputStream(b));
			// if contains classes
			if(redefinedMap.containsKey(cl.getName())) {
				CtBehavior[] methods = cl.getDeclaredBehaviors();
				for (int i = 0; i < methods.length; i++) {
					if (methods[i].isEmpty() == false) {
						changeMethod(methods[i]);
					}
				}
				b = cl.toBytecode();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cl != null) {
				cl.detach();
			}
		}
		return b;
	}
	
	protected void changeMethod(CtBehavior method) throws NotFoundException, CannotCompileException {
		List<String> methods = redefinedMap.get(method.getClass().getName());
		if(methods.contains(method.getName())) {
			changeMethodImpl(method);
		}
	}
	
	protected abstract void initializeMapping(Map<String, List<String>> redefinedMap);
	
	protected abstract void changeMethodImpl(CtBehavior method) throws NotFoundException, CannotCompileException;
}
