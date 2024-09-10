import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileUtility {

    public static void fileW(){
    try {
        FileWriter writer = new FileWriter("poem.txt");
        writer.write("Roses are red \nViolets are blue \nTwinkle twinkle little star!");
        writer.append("\n(Added at the end of the file!");
        writer.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static void fileR(){
        try {
            FileReader reader = new FileReader("poem.txt");
            int data = reader.read();
            while(data != -1) {
                System.out.print((char)data);
                data = reader.read();
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static void writeIntoFile(String fileName, String text, boolean append) throws IOException {
        //FileWriter fw = new FileWriter(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, append));) {
            bw.write(text);
        } //  bw.close();
    }

    public static void writeIntoFile(String fileName, String text) throws IOException {
        writeIntoFile(fileName, text, false);
    }

    public static void appendIntoFile(String fileName, String text) throws IOException {
        writeIntoFile(fileName, text, true);
    }

    public static void writeBytes(String fileName, byte[] data) throws Exception{
        //File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(fileName); //<-- file

        fos.write(data);
        fos.flush();
        fos.close();
        System.out.println("Done");
    }

    public static String read(String fileName) throws Exception {
        try (InputStream in = new FileInputStream(fileName);
             InputStreamReader r = new InputStreamReader(in);
             BufferedReader reader = new BufferedReader(r); ){
            String line = null;
            String result = "";

            while((line = reader.readLine()) != null) {
                result += line+"\n";
            }
            return result;
        }
    }

    public static byte[] readBytes(String fileName) throws Exception {
        File file = new File(fileName);

        try(FileInputStream fileInputStream = new FileInputStream(file);) {

            byte[] bytesArray = new byte[ (int) file.length()];
            fileInputStream.read(bytesArray);
            return bytesArray;
        } //catch (Exception ex) {
//			ex.printStackTrace();
//			return null;
//		} finally {
//			System.out.println("Xeta olsa da olmasa da bura calisacaq");
//		}
    }


    public static Object readFileDeserialize(String name) throws Exception {

        Object obj = null;
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(name))) {
            obj = in.readObject();
        } finally {
            return obj;
        }
    }

    public static void writeObjectToFile(Serializable object, String name) throws Exception {
        try(
                FileOutputStream fout = new FileOutputStream(name);
                ObjectOutputStream oos = new ObjectOutputStream(fout);
        ){
            oos.writeObject(object);
        }
    }

    public static void writeBytes(byte[] data, String fileName) throws Exception {
        Path filePath = Paths.get(fileName);
        Files.write(filePath, data);
    }

    public static byte[] readBytesNio(String fileName) throws Exception {
        Path filePath = Paths.get(fileName);
        byte[] byteArray = Files.readAllBytes(filePath);
        return byteArray;
    }

    private static void download(String fromFile, String toFile) throws Exception {
        URL website = new URL(fromFile);
        URL url = new URL(fromFile);
        URLConnection con = url.openConnection();
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);
        InputStream in = con.getInputStream();
        ReadableByteChannel rbc = Channels.newChannel(in);
        FileOutputStream fos = new FileOutputStream(toFile);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }


}