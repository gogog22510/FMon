package gogog22510.appmon.test;

public class foo {
	public static void main(String args[]) {
		foo f = new foo();
		f.doIt();
		f.notDoIt();
		System.out.println("initialize bar");
		bar b = new bar();
		b.doIt();
	}

	void doIt() {
		try {
			System.out.println("ran doIt() method");
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void notDoIt() {
		System.out.println("ran notDoIt() method");
	}
}