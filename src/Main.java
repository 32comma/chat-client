import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Socket socket = null;
		Scanner sc = null;
		try {
			socket = new Socket("127.0.0.1", 9090);
			System.out.println("서버에 접속했습니다.");
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			sc = new Scanner(System.in);
			InputStream is = socket.getInputStream();
			Scanner sc2 = new Scanner(is);
			Thread th = new Thread() {

				@Override
				public void run() {
					try {
						Thread.sleep(3000);
						while (sc2.hasNext()) {
							String message = sc2.nextLine();
							System.out.println(message);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						sc2.close();
					}
				}

			};
			th.start();
			while (true) {
				String str = sc.nextLine();
				pw.println(str);
				pw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
