package marcio.tcc.estacionamento.controle;

import java.time.LocalDateTime;
import java.util.List;

import marcio.tcc.estacionamento.negocio.Movimentacao;
import marcio.tcc.estacionamento.negocio.Vaga;
import marcio.tcc.estacionamento.negocio.Veiculo;
import marcio.tcc.estacionamento.persistencia.DAOEstacionamento;
import marcio.tcc.estacionamento.utilitario.EstacionamentoUtil;

/**
 * Coordena todos os fluxos dos casos de uso do sistema
 * @author Marcio
 *
 */
public class EstacionamentoController {
	/**
	 * A partir dos dados do veiculo informados pelo operador
	 * o fluxo de entrada do veiculo no estacionamento registra
	 * a movimenta��o gerada.
	 * 
	 * @param placa - Placa do ve�culo
	 * @param marca - Marca do ve�culo
	 * @param modelo - Modelo do ve�culo
	 * @param cor - Cor do ve�culo
	 * @throws EstacionamentoException - Quando o estacionamento estiver lotado.
	 * @throws VeiculoException - Quando o padr�o da placa for inv�lido
	 */
	
	public void processarEntrada(String placa, String marca, String modelo, String cor) throws EstacionamentoException, VeiculoException {
		// verificar se o estacionamento est� lotado		
		if(!Vaga.temVagaLivre()) {
			throw new EstacionamentoException("Estacionamento Lotado!");
		}
		
		// verificar o padr�o de string da placa
		if(!EstacionamentoUtil.validarPadraoPlaca(placa)) {
			throw new VeiculoException("Placa informada inv�lida!");
		}
		
		// criar uma instancia do ve�culo
		Veiculo veiculo = new Veiculo(placa, marca, modelo, cor);
		
		// criar uma movimenta��o do veiculo e com data de entrada corrente
		Movimentacao movimentacao = new Movimentacao(veiculo, LocalDateTime.now());
		
		
		// registrar na base de dados a informa��o
		DAOEstacionamento dao = new DAOEstacionamento();
		dao.criar(movimentacao);
		
		// atualizar o numero de vagas ocupadas
		Vaga.entrou();
		//fim
	}
	
	/**
	 * A partir de uma placa de veiculo informada, realiza todo o fluxo de sa�da de veiculo do estacionamento
	 * @param placa - Placa do veiculo que estiver saindo
	 * @return - Uma instancia de de movimenta��o com dados atualizados de valor
	 * @throws VeiculoException - Quando a placa estiver incorreta
	 * @throws EstacionamentoException - Quando o veiculo com a placa informada n�o � localizado
	 */
	public Movimentacao processarSaida(String placa) throws VeiculoException, EstacionamentoException {
		//Validar a placa
		if(!EstacionamentoUtil.validarPadraoPlaca(placa)) {
			throw new VeiculoException("Placa Inv�lida!");
		}
		
		//Buscar a movimenta��o aberta baseada na placa
		DAOEstacionamento dao = new DAOEstacionamento();
		Movimentacao movimentacao = dao.buscarMovimentacaoAberta(placa);
		
		if(movimentacao == null) {
			throw new EstacionamentoException("Ve�culo n�o encontrado!");
		}
		
		//Fazer o Calculo do valor a ser pago
		movimentacao.setDataHoraSaida(LocalDateTime.now());
		EstacionamentoUtil.calcularValorPago(movimentacao);
		
		//Atualizar os dados da movimenta��o
		dao.atualizar(movimentacao);
		
		//Atualizar o status da Vaga
		Vaga.saiu();
		
		return movimentacao;
	}
	
	/**
	 * Realiza o fluxo de emiss�o de rel�torio de faturamento
	 * baseado em um mes e ano informados
	 * 
	 * @param data - Data (m�s e ano) de emiss�o desejada
	 * @return Lista de movimenta��es que atendem ao filtro
	 */
	public List<Movimentacao> emitirRelatorio(LocalDateTime data){
		DAOEstacionamento dao = new DAOEstacionamento();
		
		return dao.consultarMovimentacoes(data);
	}

	public int inicializarOcupadas() {
		DAOEstacionamento dao = new DAOEstacionamento();
		return dao.getOcupadas();
	}
}
