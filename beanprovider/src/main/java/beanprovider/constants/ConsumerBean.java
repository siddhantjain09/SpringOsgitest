package beanprovider.constants;

public class ConsumerBean  implements Testsrvc{
	
	public TestBean tb;
	public int n;
	public ConsumerBean(TestBean tbx, int num) {
		System.out.println("from bean provider"+tbx.x);
		tb=tbx;
		n=num;
	}
	@Override
	public void printVal() {
		// TODO Auto-generated method stub
		System.out.println("local var::"+n);
		System.out.println("TestBean var::"+tb.x);
	}
}
