package com.openfaas.cli;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.CopyUtils;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws IOException
    {
        try {
            Charset charset = StandardCharsets.UTF_8;
            String requestBody = Utils.convert(System.in, charset);
            int count=0;
            String[] array=requestBody.split(",");
            for (int i=0;i<array.length;i++){
                int ele=Integer.valueOf(array[i]);
                count=count+ele;
            }
            String result=String.valueOf(count);
            InputStream inputStream = new ByteArrayInputStream(result.getBytes(charset));
            CopyUtils utils = new CopyUtils();
            utils.copy(inputStream, System.out);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
