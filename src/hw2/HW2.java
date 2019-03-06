package hw2;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Scanner;
// BEGIN SOLUTION
// please import only standard libraries and make sure that your code compiles and runs
//without unhandled exceptions
// END SOLUTION
public class HW2 {

  static void P1() throws Exception {
    byte[] cipherBMP = Files.readAllBytes(Paths.get("cipher1.bmp"));
// BEGIN SOLUTION
    byte[] iv = new byte[]{ 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 0, 0, 0};
    byte[] key = new byte[] { 7, 7, 7, 7, 7, 7, 7, 7,
                              7, 7, 7, 7, 7, 7, 7, 7 };
    SecretKeySpec secKey = new SecretKeySpec(key,"AES");
    IvParameterSpec ivSpec = new IvParameterSpec(iv);

    Cipher cip = Cipher.getInstance("AES/CFB/Nopadding");
    cip.init(Cipher.DECRYPT_MODE,secKey,ivSpec);
    cipherBMP = cip.doFinal(cipherBMP);

    byte[] plainBMP = cipherBMP;
// END SOLUTION
    Files.write(Paths.get("plain1.bmp"), plainBMP);

  }

  static void P2() throws Exception {
// BEGIN SOLUTION
    Scanner scanner = new Scanner(new File("messages.txt"));
    while (scanner.hasNextLine()) {
      String message = scanner.nextLine();
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(message.getBytes());
      byte[] a = messageDigest.digest();
      if(a[0] == 70 && a[1] == 124 && a[2] == 72 ){
        System.out.println(message);
      }
    }
    scanner.close();
// END SOLUTION
  }

  static void P3() throws Exception {
    byte[] cipherBMP = Files.readAllBytes(Paths.get("cipher3.bmp"));
// BEGIN SOLUTION
    byte[] a = Files.readAllBytes(Paths.get("plain1.bmp"));
    for(int i = 0 ; i < 100 ;i++){
      cipherBMP[i] = a[i];
    }
    byte[] modifiedBMP = cipherBMP;
// END SOLUTION
    Files.write(Paths.get("cipher3_modified.bmp"), modifiedBMP);
  }

  static void P4() throws Exception {
    byte[] cipherPNG = Files.readAllBytes(Paths.get("cipher4.png"));
// BEGIN SOLUTION
    byte[] key = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0,
                              0, 0, 0, 0, 0, 0, 0, 0 };
    byte[] iv = new byte[]{ 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 0, 0, 0};
    byte[] plainPNG = new byte[]{};
    byte x, m ,z;
    boolean flag=true;
    for( x = 0 ; x < 100 && flag ; x++){
      key[0] = x;
      for( m = 1 ; m <=12 && flag; m++){
        key[1] = m;
        for( z = 1 ; z <= 31 && flag ; z++){
          key[2] = z;
          SecretKeySpec secKey = new SecretKeySpec(key,"AES");
          IvParameterSpec ivSpec = new IvParameterSpec(iv);

          Cipher ci = Cipher.getInstance("AES/CBC/Nopadding");
          ci.init(Cipher.DECRYPT_MODE,secKey,ivSpec);
          plainPNG=ci.doFinal(cipherPNG);

          if(plainPNG[1] == 80 && plainPNG[2] == 78 && plainPNG[3] == 71){
            flag = false;
          }
        }
      }
    }
// END SOLUTION
    Files.write(Paths.get("plain4.png"), plainPNG);
  }

  public static void main(String[]args){
//    String filename = "cipher1.bmp";
//    Path pathToFile = Paths.get(filename);
//    System.out.println(pathToFile.getAbsolutePath());
    try {
      P1();
      P2();
      P3();
      P4();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}