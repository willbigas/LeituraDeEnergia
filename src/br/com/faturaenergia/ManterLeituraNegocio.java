package br.com.faturaenergia;

import java.text.DecimalFormat;

public class ManterLeituraNegocio {
    
    public static void funcaoPrincipal(String[] linhas, String[] novoDadoConsumidor, String[] cadaImpressao) throws NumberFormatException {
        /**
         * Estrutura de Repeticao *
         */
        for (int i = 1; i < linhas.length; i++) {
            String linha = linhas[i];
            String[] dadosUmConsumidor = linha.split("[;]");
            novoDadoConsumidor[i] = dadosUmConsumidor[0];
            DecimalFormat df = new DecimalFormat("#,###0.00");

            /**
             * Variaveis de Uso comum *
             */
            Double calculoConsumo = 0.0;
            Double calculoPorcentagem = 0.0;
            Double consumoCalculadoVariavel = 0.0;
            Double valorICMS = 0.0;
            Double consumoFaturado = Double.valueOf(dadosUmConsumidor[4]) - Double.valueOf(dadosUmConsumidor[3]);

            consumoFaturado = condicionandoConsumoNegativo(consumoFaturado, dadosUmConsumidor);
            consumoFaturado = condicionandoConsumoMinimo(consumoFaturado);

            Double consumoFaturadoV = consumoFaturado;
            /**
             * Calculo baseado no tipo de Consumo *
             */
            // Conta Residencial
            if (dadosUmConsumidor[2].contains("R")) {
                calculoConsumo = consumoFaturadoV * 0.45;
                consumoCalculadoVariavel = calculoConsumo;
                calculoPorcentagem = (calculoConsumo * 12) / 100;
                calculoConsumo = calculoConsumo + calculoPorcentagem;
                valorICMS = 12.0;
                dadosUmConsumidor[2] = "Residencial";
            }
            // Conta Comercial
            if (dadosUmConsumidor[2].contains("C")) {
                calculoConsumo = consumoFaturadoV * 0.56;
                consumoCalculadoVariavel = calculoConsumo;
                calculoPorcentagem = (calculoConsumo * 14) / 100;
                calculoConsumo = calculoConsumo + calculoPorcentagem;
                valorICMS = 14.0;
                dadosUmConsumidor[2] = "Comercial";

            }
            // Conta Industrial
            if (dadosUmConsumidor[2].contains("I")) {
                calculoConsumo = consumoFaturadoV * 0.72;
                consumoCalculadoVariavel = calculoConsumo;
                calculoPorcentagem = (calculoConsumo * 15.50) / 100;
                calculoConsumo = calculoConsumo + calculoPorcentagem;
                valorICMS = 15.5;
                dadosUmConsumidor[2] = "Industrial";

            }
            imprimindoResultado(dadosUmConsumidor, consumoFaturadoV, df, consumoCalculadoVariavel, valorICMS, calculoConsumo, cadaImpressao, i);
        }
    }

    public static Double condicionandoConsumoMinimo(Double consumoFaturado) {
        if (consumoFaturado < 30.0) {
            consumoFaturado = 30.0;
        }
        return consumoFaturado;
    }

    public static Double condicionandoConsumoNegativo(Double consumoFaturado, String[] dadosUmConsumidor) throws NumberFormatException {
        /**
         * Condicao Consumo negativo ou menor que 30 *
         */
        if (consumoFaturado < 0.0) {
            consumoFaturado = Double.valueOf(dadosUmConsumidor[5]);
            // caso o consumo seja negativo - alterar valor para media de consumo //
        }
        return consumoFaturado;
    }

    public static void imprimindoResultado(String[] dadosUmConsumidor, Double consumoFaturadoV, DecimalFormat df, Double consumoCalculadoVariavel, Double valorICMS, Double calculoConsumo, String[] cadaImpressao, int i) {
        String impressao;
        // String de IMPRESSAO EM TXT
        impressao = "## Dados da unidade consumidora\r\n";
        impressao += "unidade=" + dadosUmConsumidor[0] + "\r\n";
        impressao += "cliente=" + dadosUmConsumidor[1] + "\r\n";
        impressao += "tipo=" + dadosUmConsumidor[2] + "\r\n" + "\r\n";
        impressao += "## Dados de consumo\r\n";
        impressao += "leitura_atual=" + dadosUmConsumidor[4] + "\r\n";
        impressao += "leitura_anterior=" + dadosUmConsumidor[3] + "\r\n";
        impressao += "consumo_faturado=" + consumoFaturadoV + "\r\n";
        impressao += "media=" + dadosUmConsumidor[5] + "\r\n" + "\r\n";
        impressao += "## Conta de energia eletrica\r\n";
        impressao += "valor_energia=" + df.format(consumoCalculadoVariavel) + "\r\n";
        impressao += "icms=" + df.format(valorICMS) + "\r\n";
        impressao += "valor_conta=" + df.format(calculoConsumo) + "\r\n";
        cadaImpressao[i] = impressao;
    }

    public static void escrevendoEmArquivo(String[] linhas, String[] novoDadoConsumidor, String[] cadaImpressao) throws Exception {
        /**
         * Escrevendo arquivo em txt *
         */
        for (int j = 1; j < linhas.length; j++) {
            UtilArquivo.escreverArquivoConcatenando("contas/" + novoDadoConsumidor[j] + ".txt", cadaImpressao[j]);
        }
    }
    
}
