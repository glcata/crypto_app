// * @author Catalin Glavan
package crypto.encrypt;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class test_readFilebytes {

    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Catalin Glavan\\Desktop\\mesaj.txt"));) {
            String sCurrentLine = null;

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(DatatypeConverter.printBase64Binary(sCurrentLine.getBytes()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
