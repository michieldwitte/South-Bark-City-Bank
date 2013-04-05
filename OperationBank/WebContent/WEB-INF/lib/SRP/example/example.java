import com.jordanzimmerman.SRPClientSession;
import com.jordanzimmerman.SRPClientSessionRunner;
import com.jordanzimmerman.SRPFactory;
import com.jordanzimmerman.SRPInputStream;
import com.jordanzimmerman.SRPOutputStream;
import com.jordanzimmerman.SRPRunner;
import com.jordanzimmerman.SRPServerSession;
import com.jordanzimmerman.SRPServerSessionRunner;
import com.jordanzimmerman.SRPVerifier;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

/*

	Example of how to use the SRP library.

	To use example:

	First: run and enter "password" for mode. You will then be asked for a password. The values for "v" and "s" will be
	displayed. Save these values (and the password) for subsequent runs.

	Testing: run two instances of the example. For the first instance, enter "server" for the mode and "client" for the
	second instance. For the server, you will be asked for the generated v and s values. For the client, you will be asked
	for the password. The client sends lines of text that you enter to the server. The server prints the lines and then
	repeats them back to the client.

	-Jordan Zimmerman
	jordan@jordanzimmerman.com

 */
public class example
{
	public static void main(String[] args) throws Exception
	{
		SRPFactory 			factory = SRPFactory.getInstance();
		BufferedReader		in = new BufferedReader(new InputStreamReader(System.in));

		String 				mode = get_line("mode", in);
		if ( mode.equals("password") )
		{
			mode_password(factory, in);
		}
		else if ( mode.equals("server") )
		{
			mode_io_server(factory, in);
		}
		else if ( mode.equals("client") )
		{
			mode_io_client(factory, in);
		}
		else if ( mode.equals("manual server") )
		{
			mode_manual_server(factory, in);
		}
		else if ( mode.equals("manual client") )
		{
			mode_manual_client(factory, in);
		}
		else if ( mode.equals("runner server") )
		{
			mode_runner_server(factory, in);
		}
		else if ( mode.equals("runner client") )
		{
			mode_runner_client(factory, in);
		}
		else
		{
			print_help();
		}
	}

	private static void mode_io_client(final SRPFactory factory, BufferedReader sysin) throws IOException
	{
		String 				address = get_line("Address", sysin);
		int 				port = Integer.parseInt(get_line("Port", sysin));

		// in production use, the password will come from a DB or other storage
		String 				password = get_line("Password", sysin);

		Socket socket = new Socket(address, port);
		try
		{
			SRPInputStream				in = new SRPInputStream(new BufferedInputStream(socket.getInputStream()));
			SRPOutputStream				out = new SRPOutputStream(new BufferedOutputStream(socket.getOutputStream()));

			// important - both streams must be authenticated
			SRPClientSessionRunner 		runner = new SRPClientSessionRunner(factory.newClientSession(password.getBytes()));
			in.authenticate(runner, out);
			out.authenticate(runner, in);

			// at this point both streams are set for encrypted communication

			for(;;)
			{
				String 		str = get_line("String (blank to quit)", sysin);
				if ( str.trim().length() == 0 )
				{
					break;
				}

				for ( int i = 0; i < str.length(); ++i )
				{
					out.write(str.charAt(i) & 0xff);
				}
				out.write('\n');
				out.flush();

				for(;;)
				{
					int		b = in.read();
					if ( b < 0 )
					{
						break;
					}
					System.out.print((char)(b & 0xff));
					if ( b == '\n' )
					{
						break;
					}
				}
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				socket.close();
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}

	private static void mode_io_server(final SRPFactory factory, BufferedReader in) throws IOException
	{
		int 				port = Integer.parseInt(get_line("Port", in));

		// in production use, the v/s values will come from a DB or other storage
		String 				v = get_line("v", in);
		String 				s = get_line("s", in);
		final SRPVerifier 	verifier = new SRPVerifier(new BigInteger(v, 16), new BigInteger(s, 16));

		ServerSocket 		server = new ServerSocket(port);
		for(;;)
		{
			final Socket 		socket = server.accept();
			Thread				thread = new Thread()
			{
				public void run()
				{
					try
					{
						SRPInputStream				in = new SRPInputStream(new BufferedInputStream(socket.getInputStream()));
						SRPOutputStream				out = new SRPOutputStream(new BufferedOutputStream(socket.getOutputStream()));

						SRPServerSessionRunner 		runner = new SRPServerSessionRunner(factory.newServerSession(verifier));

						// important - both streams must be authenticated
						in.authenticate(runner, out);
						out.authenticate(runner, in);

						// at this point both streams are set for encrypted communication

						for(;;)
						{
							int		b = in.read();
							if ( b < 0 )
							{
								break;
							}
							System.out.print((char)(b & 0xff));
							out.write(b);
							if ( b == '\n' )
							{
								out.flush();
							}
						}
					}
					catch ( Exception e )
					{
						e.printStackTrace();
					}
					finally
					{
						try
						{
							socket.close();
						}
						catch ( IOException e )
						{
							e.printStackTrace();
						}
					}
				}
			};
			thread.start();
		}
	}

	private static void mode_password(SRPFactory factory, BufferedReader in) throws IOException
	{
		String 		password = get_line("Password", in);
		SRPVerifier verifier = factory.makeVerifier(password.getBytes());
		
		System.out.println("v: " + verifier.verifier_v.toString(16));
		System.out.println("s: " + verifier.salt_s.toString(16));
	}

	private static void mode_manual_client(SRPFactory factory, BufferedReader in) throws IOException
	{
		String 				P = get_line("Password", in);
		String 				s = get_line("s", in);
		SRPClientSession session = factory.newClientSession(P.getBytes());
		session.setSalt_s(new BigInteger(s, 16));
		System.out.println("A: " + session.getPublicKey_A().toString(16));
		String 				B = get_line("B", in);
		session.setServerPublicKey_B(new BigInteger(B, 16));
		System.out.println("M1: " + session.getEvidenceValue_M1().toString(16));
		String 				M2 = get_line("M2", in);
		session.validateServerEvidenceValue_M2(new BigInteger(M2, 16));
		System.out.println("OK");
	}

	private static void mode_manual_server(SRPFactory factory, BufferedReader in) throws IOException
	{
		String 				v = get_line("v", in);
		String 				s = get_line("s", in);
		SRPVerifier verifier = new SRPVerifier(new BigInteger(v, 16), new BigInteger(s, 16));
		SRPServerSession session = factory.newServerSession(verifier);
		String 				A = get_line("A", in);
		session.setClientPublicKey_A(new BigInteger(A, 16));
		System.out.println("B: " + session.getPublicKey_B().toString(16));
		session.computeCommonValue_S();
		String 				M1 = get_line("M1", in);
		session.validateClientEvidenceValue_M1(new BigInteger(M1, 16));
		System.out.println("M2: " + session.getEvidenceValue_M2().toString(16));
		System.out.println("OK");
	}

	private static void mode_runner_client(SRPFactory factory, BufferedReader in) throws IOException
	{
		String 			P = get_line("Password", in);
		SRPRunner runner = new SRPClientSessionRunner(factory.newClientSession(P.getBytes()));
		runit(in, runner);
	}

	private static void mode_runner_server(SRPFactory factory, BufferedReader in) throws IOException
	{
		String 						v = get_line("v", in);
		String 						s = get_line("s", in);
		SRPVerifier verifier = new SRPVerifier(new BigInteger(v, 16), new BigInteger(s, 16));
		SRPRunner runner = new SRPServerSessionRunner(factory.newServerSession(verifier));
		runit(in, runner);
	}

	private static void runit(BufferedReader in, SRPRunner runner) throws IOException
	{
		while ( runner.next() )
		{
			if ( runner.hasOutput() )
			{
				System.out.println(runner.getOutput().toString(16));
			}
			if ( runner.needsInput() )
			{
				String 				x = get_line("i", in);
				runner.setInput(new BigInteger(x, 16));
			}
		}
		System.out.println(runner.success() ? "Succeeded" : "Failed");
	}

	private static String get_line(String message, BufferedReader in) throws IOException
	{
		System.out.print(message + ": ");
		return in.readLine();
	}

	private static void print_help()
	{
		System.out.println
		(
			"Mode must be one of the following values:\n\n" +
			"password: outputs a verifier (v and s) for the given password.\n\n" +
			"server: runs an example server (that repeats all lines sent to it). You will be asked for the server port and the v and s values.\n\n" +
			"client: runs an example client (that sends lines to the server). You will be asked for the server port and address and the password.\n\n" +
			"runner server: runs a server that directly uses the runner APIs. This server is not TCP/IP. You must copy/paste values to/from the client.\n\n" +
			"runner client: runs a client that directly uses the runner APIs. This client is not TCP/IP. You must copy/paste values to/from the client.\n\n" +
			"manual server: runs a server that uses the low level SRP APIs. This server is not TCP/IP. You must copy/paste values to/from the client.\n\n" +
			"manual client: runs a client that uses the low level SRP APIs. This client is not TCP/IP. You must copy/paste values to/from the client.\n\n"
		);
	}
}
