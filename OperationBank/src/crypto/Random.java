package crypto;

import gnu.crypto.prng.Fortuna;
import gnu.crypto.prng.LimitReachedException;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

import com.sun.management.OperatingSystemMXBean;

import database.DatabaseManager;

public class Random {
	
	private OperatingSystemMXBean mxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	private Fortuna PRNG = new Fortuna();
	private static Random instance = null;

	
	private Random(){
		byte[] nanoTime = new String(Long.toString(System.nanoTime())).getBytes();
		String randomInfo = Integer.toString(Runtime.getRuntime().availableProcessors()); //get number of cpu;
		randomInfo       += Long.toString(Runtime.getRuntime().freeMemory()); // get free memory.
		randomInfo		 += Long.toString(mxBean.getTotalPhysicalMemorySize());

		Map<String,byte[]> attr = new HashMap<String, byte[]>();
		attr.put("gnu.crypto.prng.fortuna.seed", new String(randomInfo).getBytes());
		PRNG.setup(attr);
		PRNG.init(attr);
		try {
			PRNG.fillBlock();
			PRNG.addRandomBytes(nanoTime, 0, nanoTime.length);
		} catch (LimitReachedException e) {
			e.printStackTrace();
		}
	}
	
	public static Random getInstance(){
		if(instance == null)
			instance = new Random();
		return instance;
	}
	
	public Fortuna getFortuna(){
		return PRNG;
	}

}
