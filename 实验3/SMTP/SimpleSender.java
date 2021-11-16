import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class SimpleSender {
	public static void main(String[] args) throws Exception {
		Date dDate = new Date();
		DateFormat dFormat = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL,
				Locale.US);
		String command = null;

		/** ���ʼ�����������TCP����. */
		// TODO: 1.��""���������ǵ�smtp����������ȷ�˿ڣ��廪�ʼ�������mails.tsinghua.edu.cn���˿���25
		// e.g. Socket socket = new Socket("mails.163.com",25);
		Socket socket = new Socket("mails.tsinghua.edu.cn", 25);

		/** ����BufferedReaderÿ�ζ���һ����Ϣ. */
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		/** ����ϵͳ�Ļ�ӭ��Ϣ. */
		String response = br.readLine();
		System.out.println(response);
		// TODO: 2.��code��Ϊ���ʵĴ���
		int code = 220;	//��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		}

		/** ȡ��socket�����������. */
		OutputStream os = socket.getOutputStream();

		/** ���� HELO ���ȡ�÷�������Ӧ. 
		 *	�������������, �����µ�"\r\n"ǰд����������� 
		 *	e.g.	command = "Helo x\r\n";
		 *	����\r\nΪ�س���,ÿ�����񶼱��������ǽ�β. */
		// TODO: 3.��������
		command = "HELO mails.tsinghua.edu.cn\r\n";		
		System.out.print(command);
		os.write(command.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
		// TODO: 4.��code��Ϊ���ʵĴ���
		code = 250; //��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		}

		/** ���� MAIL FROM ����. */
		// TODO: 5.��x@x.x��Ϊ���ʵĵ�ַ
		command = "MAIL FROM:<Steam@steam.com>\r\n";
		System.out.print(command);
		os.write(command.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
		// TODO: 6.��code��Ϊ���ʵĴ���
		code = 250; //��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		}

		/** ���� RCPT TO ����. */
		// TODO: 7.��x@x.x��Ϊ���ʵĵ�ַ
		command = "RCPT TO:<qsy19@mails.tsinghua.edu.cn>\r\n";
		System.out.print(command);
		os.write(command.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
		// TODO: 8.��code��Ϊ���ʵĴ���
		 code = 250; //��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		}

		/** ���� DATA ����. */
		// TODO: 9.��������
		command = "DATA\r\n";
		System.out.print(command);
		os.write(command.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
		// TODO: 10.��code��Ϊ���ʵĴ���
		 code = 354; //��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		}

		/** �Զ�д�뵱ǰ������ */
		String date = "DATE: " + dFormat.format(dDate) + "\r\n";
		System.out.print(date);
		os.write(date.getBytes("US-ASCII"));
		String str = "";
		// TODO: 11.��"x@x.x"��Ϊ�ʼ�����ʾ�ķ����˵�ַ
		str = "From:" + "Steam@steam.com" + "\r\n";
		System.out.print(str);
		os.write(str.getBytes("US-ASCII"));
		// TODO: 12.��"x@x.x"��Ϊ�ʼ�����ʾ���ռ��˵�ַ
		str = "To:" + "qsy19@mails.tsinghua.edu.cn" + "\r\n";
		System.out.print(str);
		os.write(str.getBytes("US-ASCII"));

		/** �����ʼ�����. */
		// TODO: 13.��"x"������Subject����.
		str = "SUBJECT:" + "Yor Purchase Has Been Made" + "\r\n\r\n";
		System.out.print(str);
		os.write(str.getBytes("US-ASCII"));
		// TODO: 14.��"x"�������ʼ���������.
		str = "Thanks for your purchasing on Steam, please contact us further." + "\r\n";
		System.out.print(str);
		os.write(str.getBytes("US-ASCII"));
		str = "Hope you will enjoy your Age Of Empire 4." + "\r\n";
		System.out.print(str);
		os.write(str.getBytes("US-ASCII"));

		/** ��.��Ϊ�ʼ����ݵĽ����� */
		str = ".\r\n";
		System.out.print(str);
		os.write(str.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
		// TODO: 15.��code��Ϊ���ʵĴ���
		 code = 250; //��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		}

		/** ���� QUIT ����. */
		//TODO:	16.��������
		command = "QUIT\r\n";
		System.out.print(command);
		os.write(command.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
	}
}
