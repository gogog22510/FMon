package gogog22510.appmon.test;

import java.lang.instrument.Instrumentation;

public class SimpleMain {
	public static void premain(String agentArguments, Instrumentation instrumentation) {
		instrumentation.addTransformer(new SimpleTransformer());
	}
}
