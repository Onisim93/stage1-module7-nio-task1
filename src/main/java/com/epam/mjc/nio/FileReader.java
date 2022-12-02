package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;


public class FileReader {

    public static void main(String[] args) {
        Path path = Path.of("/Users/onisim_albarov/IdeaProjects/stage1-module7-nio-task1/src/main/resources/Profile.txt");
        Profile p = getDataFromFile(path.toFile());
        System.out.println(p);
    }

    public static Profile getDataFromFile(File file) {
        Profile profile = new Profile();

        try (RandomAccessFile raf = new RandomAccessFile(file.getAbsolutePath(), "r");
            FileChannel channel = raf.getChannel()) {

            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            StringBuilder sb = new StringBuilder();

            while (channel.read(byteBuffer) > 0) {
                byteBuffer.flip();
                for (int i=0; i<byteBuffer.limit();i++) {
                    sb.append((char) byteBuffer.get());
                }
                byteBuffer.clear();
            }

            String[] pairs = sb.toString().split("\n");

            for (String pair : pairs) {
                String[] keyValue = pair.split(" ");
                switch (keyValue[0]) {
                    case "Name:": profile.setName(keyValue[1]); break;
                    case "Age:": profile.setAge(Integer.parseInt(keyValue[1])); break;
                    case "Email:": profile.setEmail(keyValue[1]); break;
                    case "Phone:": profile.setPhone(Long.parseLong(keyValue[1]));break;
                    default: break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return profile;
    }
}
