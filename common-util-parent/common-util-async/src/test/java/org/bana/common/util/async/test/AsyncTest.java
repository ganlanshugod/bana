package org.bana.common.util.async.test;
/**
 * @author ffychina
 * @since 2020-04-13
 * https://blog.csdn.net/ffychina
 * */
 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AsyncTest{
	private List<AsyncFunction> asyncFnList=new ArrayList<AsyncTest.AsyncFunction>();

	public AsyncTest(){
	}

	public AsyncTest(AsyncFunction runable){
		add(runable);
	}

	public AsyncTest add(AsyncFunction runable){
		asyncFnList.add(runable);
		return this;
	}

	public Object await(){
		CountDownLatch latch=new CountDownLatch(asyncFnList.size());
		for(AsyncFunction asyncFn:asyncFnList){
			asyncFn._latch[0]=latch;
			Thread thread=new Thread(asyncFn);
			thread.start();
		}
		try{
			latch.await();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		List result=this.asyncFnList.get(0)._result;
		if(asyncFnList.size()==0){
			return result.get(0);
		}else{
			return result.toArray();
		}
	}

	@FunctionalInterface
	public static interface AsyncFunction extends Runnable{
		Object call();

		List _result=new ArrayList();
		CountDownLatch[] _latch=new CountDownLatch[1];

		@Override
		default void run(){
			synchronized(_result){
				_result.add(call());
			}
			_latch[0].countDown();
		}

		default Object result(){
			return _result.toArray(new Object[0]);
		}
	}
	
	
	public static void main(String[] args) {
		final long begin = System.currentTimeMillis();
		Object result=new AsyncTest(()->{
			int courseId=Double.valueOf(Math.random()*100000).intValue();
			String url="http://127.0.0.1:8080/hello/"+courseId;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String res = "first";
			System.out.println(res + ":" + (System.currentTimeMillis() - begin));
			return res;
		}).add(()->{ //增加多一个异步线程
			String url="http://127.0.0.1:8080/hello/world";
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String res= "second";
			System.out.println(res + ":" + (System.currentTimeMillis() - begin));
			return res;
		}).await();
		System.out.println(result);
		System.out.println("finish test,spend ms: "+(System.currentTimeMillis()-begin));
	}

}