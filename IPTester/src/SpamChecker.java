import java.net.InetAddress;
import java.net.UnknownHostException;

public class SpamChecker {

	public static final String BLACKHOLE = "sbl.spamhaus.org";
	public static void main(String[] args) throws UnknownHostException {
		//String arg = "130.243.96.139";
		String arg = "192.168.1.87";
		if (isSpammer(arg)) {
			System.out.println(arg + " is a known spammer.");
		}
		else {
			System.out.println(arg + " appears legitimate.");
		}
	}

	private static boolean isSpammer(String arg) {
		try {
			InetAddress address = InetAddress.getByName(arg);
			byte[] quad = address.getAddress();
			String query = BLACKHOLE;
			for (byte octet : quad) {
				int unsignedByte = octet < 0 ? octet + 256 : octet;
				query = unsignedByte + "." + query;
			}
			InetAddress.getByName(query);
			return true;
		}
		catch (UnknownHostException e) {
			return false;
		}
	}
}
