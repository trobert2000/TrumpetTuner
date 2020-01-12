package de.imbium.trumpettuner;

import javax.swing.JFrame;

public class TestThread extends Thread {
	
	private TrumpetTuner parent;
	
	public TestThread(TrumpetTuner Parent) {
		parent = Parent;
	}
	
	public void run() {
		int cnt = 0;
		System.out.print("run");
		try  {
			sleep(2000);
			while(cnt < 20) {
				System.out.print(".");
				sleep(500);
				
				parent.setData(cnt);
				
				cnt++;
			}
		}catch(InterruptedException ex) {
			ex.printStackTrace();
		}
	}

}
