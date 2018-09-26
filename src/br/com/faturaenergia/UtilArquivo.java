package br.com.faturaenergia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class UtilArquivo {

    public static String lerArquivo(String nmArquivo) throws Exception {
        File arquivo = new File(nmArquivo);
        if (!arquivo.exists()) {
            return "";
        }
        FileReader leitor = new FileReader(nmArquivo);
        BufferedReader buffer = new BufferedReader(leitor);
        String linha = buffer.readLine();
        String conteudo = "";
        while (linha != null) {
            conteudo += linha + "\r\n";
            linha = buffer.readLine();
        }
        buffer.close();
        return conteudo;
    }
    
    public static String[] lerArquivoArray(String nmArquivo) throws Exception {
        String conteudo = lerArquivo(nmArquivo);
        String[] linhas = conteudo.split("\r\n");
        return linhas;
    }

    /**
     * Escreve em arquivo, sobreescrevendo o conteudo ja existente
     * @param nmArquivo
     * @param conteudo
     * @throws Exception 
     */
    public static void escreverArquivo(String nmArquivo, String conteudo) throws Exception {
        FileWriter escritor = new FileWriter(nmArquivo);
        BufferedWriter buffer = new BufferedWriter(escritor);
        buffer.append(conteudo);
        buffer.close();
    }

    /**
     * Escreve em arquivo, concatenando o conteudo atual
     * @param nmArquivo
     * @param conteudo
     * @throws Exception 
     */
    public static void escreverArquivoConcatenando(String nmArquivo, String conteudo) throws Exception {
        FileWriter escritor = new FileWriter(nmArquivo, true);
        BufferedWriter buffer = new BufferedWriter(escritor);
        buffer.append(conteudo + "\r\n");
        buffer.close();
    }

}
