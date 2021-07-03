package caeser; /**
 * ClassName:Caesar
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Author:HP
 * @date:2021/6/30 16:24
 */
import java.io.IOException;
import java.util.Scanner;

/**
 * ClassName:Caesar
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Author:HP
 * @date:2021/6/30 10:22
 */
public class Caesar {
    static int ASCII;
    static char[] messageArray=null;
    static int i = 0;
    static int key=0;
    public static void main(String[] args) throws IOException {
        int choice=-1;
        int flag=0;
        while(flag==0) {
            menu();
            Scanner in2=new Scanner(System.in);
            choice=in2.nextInt();
            switch (choice) {
                case 1:
                    go(choice);
                    break;
                case 2:
                    go(choice);
                    break;
                case 3:
                    flag++;
                    break;
                default:
                    System.out.println("出错！");
                    break;
            }
        }
        System.out.println("谢谢您的使用！");
    }
    public static void menu() {
        System.out.println();
        System.out.println();
        System.out.println("*************CaesarCipher*************");
        System.out.println("    "+"1.明文加密"+"      "+"2.密文解密"+"        "+"3.退出");
        System.out.println("**************************************");
        System.out.print("请选择菜单功能：");
    }
    public static void go(int choice) {
        boolean flag=false;
        do {
            flag=false;
            if(choice==1)
                System.out.print("请输入明文：");
            else
                System.out.print("请输入密文：");
            Scanner in1=new Scanner(System.in);
            String message=in1.nextLine();
            System.out.print("请输入密钥：");
            Scanner in2=new Scanner(System.in);
            message=message.replaceAll("\\s*", "");
            try
            {
                key=in2.nextInt();
            }catch (Exception e) {
                flag=true;
                System.out.println("密钥必须为数字！请重新输入...");
            }
            if(flag!=true) {
                messageArray=message.toCharArray();
                for(i=0; i < messageArray.length; i++) {
                    ASCII=Integer.valueOf(messageArray[i]);
                    if(ASCII<65||(ASCII>90&&ASCII<97)||ASCII>122) {
                        flag=true;
                        System.out.println("请输入英文！请再次尝试...");
                        break;
                    }
                    else
                        switch (choice) {
                            case 1:
                                encode();
                                break;
                            case 2:
                                decode();
                                break;
                            default:
                                System.out.println("出错！");
                                break;
                        }
                }
            }
        }while(flag);
        System.out.print("密文为：");
        for(int i = 0; i < messageArray.length; i++)
            System.out.print(messageArray[i]);
    }
    public static void encode() {
        if(ASCII>=97&&ASCII<=122)//小写字母
            messageArray[i]=(char) ((ASCII+key-97)%26+97);
        else //大写字母
            messageArray[i]=(char) ((ASCII+key-65)%26+65);
    }
    public static void decode() {
        if(ASCII>=97&&ASCII<=122)//小写字母
            messageArray[i]=(char) ((ASCII-97+26-key)%26+97);
        else //大写字母
            messageArray[i]=(char) ((ASCII-65+26-key)%26+65);
    }
}

