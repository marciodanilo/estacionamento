package marcio.tcc.estacionamento.utilitario;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import marcio.tcc.estacionamento.negocio.Movimentacao;
import marcio.tcc.estacionamento.negocio.Tarifario;

/**
 * Representa uma classe de apoio �s demais do sistema
 * @author Marcio
 *
 */
public class EstacionamentoUtil {
	/**
	 * Valida a placa com o padr�o nacional LLL-NNNN
	 * L = Letra
	 * N = Numero
	 * 
	 * @param placa Placa do Veiculo 
	 * @return TRUE se atender o padr�o e FALSE sen�o
	 */
	public static boolean validarPadraoPlaca(String placa) {
		
		String padrao = "[A-Z][A-Z][A-Z]-\\d\\d\\d\\d";
		Pattern p = Pattern.compile(padrao);
		Matcher m = p.matcher(placa);
		return m.matches();
	}
	
	/**
	 * O calculo do valor da estada do ve�culo baseado no tarif�rio e na hora de entrada e sa�da do ve�culo
	 * 
	 * Altera a pr�pria inst�ncia do parametro
	 * 
	 * @param movimentacao Instancia da movimenta��o 
	 */
	public void calcularPagamento(Movimentacao movimentacao) {
		//TODO implementar
	}

	/**
	 * Recupera uma propriedade do arquivo de configura��o da aplica��o
	 * configuration.txt
	 * @param string
	 * @return
	 */
	public static String ini(String propriedade) {		
		String valor = null;
		Properties prop = new Properties();
		
		try {
			
			prop.load(EstacionamentoUtil.class.getResourceAsStream("/Recursos/configuration.txt"));
			valor = prop.getProperty(propriedade);
		} catch (IOException e) {
			assert false : "configura��o n�o carregada";
			e.printStackTrace();
		}
		return valor;
	}

	public static String getDataAsString(LocalDateTime dataHoraEntrada) {
		return dataHoraEntrada.toString();
	}

	public static void calcularValorPago(Movimentacao movimentacao) {
		LocalDateTime inicio = movimentacao.getDataHoraEntrada();
		LocalDateTime fim = movimentacao.getDataHoraSaida();
		double valor = 0;		
		
		//Calcular a diferen�a entre duas data/hora
		long diffHoras = inicio.until(fim, ChronoUnit.HOURS);
		
		if(diffHoras > 0) {
			valor += Tarifario.VALOR_HORA;
			fim = fim.minus(1, ChronoUnit.HOURS);
		}
		
		long diffMinutos = inicio.until(fim, ChronoUnit.MINUTES);
		
		valor += (diffMinutos / Tarifario.INCREMENTO_MINUTOS) * Tarifario.VALOR_INCREMENTAL;
		movimentacao.setValor(valor);
	}

	public static LocalDateTime getDate(String rdataEntrada) {
		return LocalDateTime.parse(rdataEntrada, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
	}

	public static String getDisplayData(LocalDateTime data) {
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}

	public static String gerarTextoFaturamento(LocalDateTime data, List<Movimentacao> movimentacoes) {
		double totalFaturado = 0;
		String resultado = "";
		
		for(Movimentacao movimentacao : movimentacoes) {
			totalFaturado += movimentacao.getValor();
		}
		
		String sAno = "" + data.getYear();
		String sMes = data.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
		
		resultado = "Faturamento do m�s: " + sMes;
		resultado +=" de " + sAno + " foi de R$ " + totalFaturado;
		
		return resultado;
	}
}
