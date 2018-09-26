package br.com.faturaenergia;

/**
 *
 * @author William
 */
public class Principal {

    public static void main(String[] args) throws Exception {

        /**
         * LENDO ARQUIVO *
         */
        String[] linhas = UtilArquivo.lerArquivoArray("dados_leitura.txt");  // Diretorio de Arquivo
        String impressao = "";
        String cadaImpressao[] = new String[100];
        String novoDadoConsumidor[] = new String[100];
        ManterLeituraNegocio.funcaoPrincipal(linhas, novoDadoConsumidor, cadaImpressao);
        ManterLeituraNegocio.escrevendoEmArquivo(linhas, novoDadoConsumidor, cadaImpressao);

    }

    
}
