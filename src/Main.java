import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 9090);
			System.out.println("서버에 접속했습니다.");
			Thread listenThread = new Thread() {

				@Override
				public void run() {
					try {
						Scanner listenScanner = new Scanner(socket.getInputStream());
						while (listenScanner.hasNext()) {
							Thread.sleep(100);
							String str = listenScanner.nextLine();
							System.out.println("<<"+str);
							if (str.equals("exit")) {
								break;
							}
						}
						listenScanner.close();
						socket.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			};
			listenThread.start();

			Thread sendThread = new Thread() {

				@Override
				public void run() {
					try {
						Scanner inputScanner = new Scanner(System.in);
						PrintWriter pw = new PrintWriter(socket.getOutputStream());
						while (true) {
							Thread.sleep(100);
							String str = inputScanner.nextLine();
							System.out.println(">>"+str);
							pw.println(str);
							pw.flush();
							if (str.equals("exit")) {
								inputScanner.close();
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			};
			sendThread.start();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}

	}
}
