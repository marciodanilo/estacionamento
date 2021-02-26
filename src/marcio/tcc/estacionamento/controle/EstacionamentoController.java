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
	 * a movimentação gerada.
	 * 
	 * @param placa - Placa do veículo
	 * @param marca - Marca do veículo
	 * @param modelo - Modelo do veículo
	 * @param cor - Cor do veículo
	 * @throws EstacionamentoException - Quando o estacionamento estiver lotado.
	 * @throws VeiculoException - Quando o padrão da placa for inválido
	 */
	
	public void processarEntrada(String placa, String marca, String modelo, String cor) throws EstacionamentoException, VeiculoException {
		// verificar se o estacionamento está lotado		
		if(!Vaga.temVagaLivre()) {
			throw new EstacionamentoException("Estacionamento Lotado!");
		}
		
		// verificar o padrão de string da placa
		if(!EstacionamentoUtil.validarPadraoPlaca(placa)) {
			throw new VeiculoException("Placa informada inválida!");
		}
		
		// criar uma instancia do veículo
		Veiculo veiculo = new Veiculo(placa, marca, modelo, cor);
		
		// criar uma movimentação do veiculo e com data de entrada corrente
		Movimentacao movimentacao = new Movimentacao(veiculo, LocalDateTime.now());
		
		
		// registrar na base de dados a informação
		DAOEstacionamento dao = new DAOEstacionamento();
		dao.criar(movimentacao);
		
		// atualizar o numero de vagas ocupadas
		Vaga.entrou();
		//fim
	}
	
	/**
	 * A partir de uma placa de veiculo informada, realiza todo o fluxo de saída de veiculo do estacionamento
	 * @param placa - Placa do veiculo que estiver saindo
	 * @return - Uma instancia de de movimentação com dados atualizados de valor
	 * @throws VeiculoException - Quando a placa estiver incorreta
	 * @throws EstacionamentoException - Quando o veiculo com a placa informada não é localizado
	 */
	public Movimentacao processarSaida(String placa) throws VeiculoException, EstacionamentoException {
		//Validar a placa
		if(!EstacionamentoUtil.validarPadraoPlaca(placa)) {
			throw new VeiculoException("Placa Inválida!");
		}
		
		//Buscar a movimentação aberta baseada na placa
		DAOEstacionamento dao = new DAOEstacionamento();
		Movimentacao movimentacao = dao.buscarMovimentacaoAberta(placa);
		
		if(movimentacao == null) {
			throw new EstacionamentoException("Veículo não encontrado!");
		}
		
		//Fazer o Calculo do valor a ser pago
		movimentacao.setDataHoraSaida(LocalDateTime.now());
		EstacionamentoUtil.calcularValorPago(movimentacao);
		
		//Atualizar os dados da movimentação
		dao.atualizar(movimentacao);
		
		//Atualizar o status da Vaga
		Vaga.saiu();
		
		return movimentacao;
	}
	
	/**
	 * Realiza o fluxo de emissão de relátorio de faturamento
	 * baseado em um mes e ano informados
	 * 
	 * @param data - Data (mês e ano) de emissão desejada
	 * @return Lista de movimentações que atendem ao filtro
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
