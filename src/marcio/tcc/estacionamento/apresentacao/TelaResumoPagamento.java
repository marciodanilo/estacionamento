package marcio.tcc.estacionamento.apresentacao;

import javax.swing.JFrame;

import marcio.tcc.estacionamento.negocio.Movimentacao;
import marcio.tcc.estacionamento.utilitario.EstacionamentoUtil;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class TelaResumoPagamento extends JFrame implements ActionListener{
	private JFrame parent;
	
	public TelaResumoPagamento(Movimentacao movimentacao, JFrame parent) {
		this.parent = parent;
		setResizable(false);
		setSize(new Dimension(450, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Resumo de Pagamento");
		getContentPane().setLayout(null);
		
		JLabel lblPlaca = new JLabel("Placa:");
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPlaca.setBounds(108, 48, 46, 14);
		getContentPane().add(lblPlaca);
		
		JLabel lblDataEntrada = new JLabel("Entrada:");
		lblDataEntrada.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataEntrada.setBounds(108, 83, 66, 17);
		getContentPane().add(lblDataEntrada);
		
		JLabel lblDataSaida = new JLabel("Sa\u00EDda:");
		lblDataSaida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataSaida.setBounds(108, 124, 46, 17);
		getContentPane().add(lblDataSaida);
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValor.setBounds(108, 171, 46, 14);
		getContentPane().add(lblValor);
		
		String sPlaca = movimentacao.getVeiculo().getPlaca();
		JLabel lblValPlaca = new JLabel(sPlaca);
		lblValPlaca.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValPlaca.setBounds(266, 48, 146, 24);
		getContentPane().add(lblValPlaca);
		
		String sEntrada = EstacionamentoUtil.getDisplayData(movimentacao.getDataHoraEntrada());
		JLabel lblValDataEntrada = new JLabel(sEntrada);
		lblValDataEntrada.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValDataEntrada.setBounds(266, 83, 146, 17);
		getContentPane().add(lblValDataEntrada);
		
		String sSaida = EstacionamentoUtil.getDisplayData(movimentacao.getDataHoraSaida());
		JLabel lblValDataSaida = new JLabel(sSaida);
		lblValDataSaida.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValDataSaida.setBounds(266, 124, 146, 17);
		getContentPane().add(lblValDataSaida);
		
		String sValor = "R$ " + movimentacao.getValor();
		JLabel lblValValor = new JLabel(sValor);
		lblValValor.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblValValor.setBounds(266, 171, 146, 17);
		getContentPane().add(lblValValor);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(this);
		btnOk.setBounds(172, 217, 89, 23);
		getContentPane().add(btnOk);

		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		parent.setVisible(true);
		dispose();
	}
}
