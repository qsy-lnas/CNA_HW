import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Base64;

public class AuthSender {
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
		
		
		
		/**
		 *����Auth Login������֤ 
		 *��Ҫ����BASE64������û���������
		 */
		// TODO: 5.��ȫ����
		command = "Auth Login\r\n";		
		System.out.print(command);
		os.write(command.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
		// ��������Ӧ334ʱ���������û���������
		// TODO: 6.��ȫ����
		code = 334; //��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		}
		//�����û���������
		//TODO: 7.��x@x.x�޸�Ϊ���ʵĵ�ַ
		String username = "qsy19@mails.tsinghua.edu.cn";
		Base64.Encoder encoder = Base64.getEncoder();
		//BASE64����
		String username_encoded = encoder.encodeToString(username.getBytes()) + "\r\n";
		os.write(username_encoded.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
		// ��������Ӧ334ʱ������������
		// TODO: 7.��ȫ����
		code = 334; //��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		} 
		String password = "MYPASSWORD";//the password has been editted 
		String password_encoded = encoder.encodeToString(password.getBytes()) + "\r\n";
		os.write(password_encoded.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
		//��Ӧ��ͷΪ235��ʾ��֤�ɹ�
		// TODO: 8.��ȫ����
		code = 235; //��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		} 
		 
		
		

		/** ���� MAIL FROM ����. */
		// TODO: 9.��x@x.x��Ϊ���ʵĵ�ַ
		command = "MAIL FROM:<qsy19@mails.tsinghua.edu.cn>\r\n";
		System.out.print(command);
		os.write(command.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
		// TODO: 10.��code��Ϊ���ʵĴ���
		code = 250; //��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		}

		/** ���� RCPT TO ����. */
		// TODO: 11.��x@x.x��Ϊ���ʵĵ�ַ
		command = "RCPT TO:<lnasqsy@qq.com>\r\n";
		System.out.print(command);
		os.write(command.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
		// TODO: 12.��code��Ϊ���ʵĴ���
		 code = 250; //��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		}

		/** ���� DATA ����. */
		// TODO: 13.��������
		command = "DATA\r\n";
		System.out.print(command);
		os.write(command.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
		// TODO: 14.��code��Ϊ���ʵĴ���
		 code = 354; //��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		}

		/** �Զ�д�뵱ǰ������ */
		String date = "DATE: " + dFormat.format(dDate) + "\r\n";
		System.out.print(date);
		os.write(date.getBytes("US-ASCII"));
		String str = "";
		// TODO: 15.��"x@x.x"��Ϊ�ʼ�����ʾ�ķ����˵�ַ
		str = "From:" + "qsy19@mails.tsinghua.edu.cn" + "\r\n";
		System.out.print(str);
		os.write(str.getBytes("US-ASCII"));
		// TODO: 16.��"x@x.x"��Ϊ�ʼ�����ʾ���ռ��˵�ַ
		str = "To:" + "lnasqsy@qq.com" + "\r\n";
		System.out.print(str);
		os.write(str.getBytes("US-ASCII"));

		/** �����ʼ�����. */
		// TODO: 17.��"x"������Subject����.
		str = "SUBJECT:" + "Test" + "\r\n\r\n";
		System.out.print(str);
		os.write(str.getBytes("US-ASCII"));
		// TODO: 18.��"x"�������ʼ���������.
		str = "test" + "\r\n";
		System.out.print(str);
		os.write(str.getBytes("US-ASCII"));

		/** ��.��Ϊ�ʼ����ݵĽ����� */
		str = ".\r\n";
		System.out.print(str);
		os.write(str.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
		// TODO: 19.��code��Ϊ���ʵĴ���
		 code = 250; //��-1��Ϊ���ʵĴ���
		if (!response.startsWith(Integer.toString(code))) {
			throw new Exception(code + " reply not received from server.");
		}

		/** ���� QUIT ����. */
		//TODO:	20.��������
		command = "QUIT\r\n";
		System.out.print(command);
		os.write(command.getBytes("US-ASCII"));
		response = br.readLine();
		System.out.println(response);
	}
}
