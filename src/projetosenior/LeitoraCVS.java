/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetosenior;

/**
 *
 * @author gusta
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LeitoraCVS {

  public static void main(String[] args) {

    LeitoraCVS obj = new LeitoraCVS();
    obj.run();

  }

  public void run() {

    String arquivoCSV = "arquivo.csv";
    BufferedReader br = null;
    String linha = "";
    String csvDivisor = ",";
    try {

        br = new BufferedReader(new FileReader(arquivoCSV));
        while ((linha = br.readLine()) != null) {

            String[] cidade = linha.split(csvDivisor);
            
            
            
            
            System.out.println("Cidade [code= " + cidade[cidade.length-2] 
                                 + " , name=" + cidade[cidade.length-1] + "]");

        }

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
  }

}