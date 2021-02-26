package marcio.tcc.estacionamento.apresentacao;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import marcio.tcc.estacionamento.controle.EstacionamentoController;
import marcio.tcc.estacionamento.negocio.Movimentacao;

@SuppressWarnings("serial")
public class TelaInicialRelatorio extends JFrame implements ActionListener{
	@SuppressWarnings("rawtypes")
	private JComboBox cboAno;
	@SuppressWarnings("rawtypes")
	private JComboBox cboMes;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TelaInicialRelatorio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(600, 140));
		setResizable(false);
		setTitle("Filtro do Relat\u00F3rio");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 15, 40));
		
		JLabel lblAno = new JLabel("Ano:");
		lblAno.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(lblAno);
		
		cboAno = new JComboBox();
		cboAno.setModel(new DefaultComboBoxModel(new String[] {"2021","2020", "2019", "2018", "2017", "2016"}));
		cboAno.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(cboAno);
		
		JLabel lblMes = new JLabel("M\u00EAs:");
		lblMes.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(lblMes);
		
		cboMes = new JComboBox();
		cboMes.setModel(new DefaultComboBoxModel(new String[] {"Janeiro", "Fevereiro", "Mar\u00E7o", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"}));
		cboMes.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(cboMes);
		
		JButton btnGerar = new JButton("Gerar");
		btnGerar.addActionListener(this);
		btnGerar.setActionCommand("gerar");
		btnGerar.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(btnGerar);
		
		
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		String cmd = evento.getActionCommand();
		
		//Recupera do combo o Ano e o Mês escolhido
		int ano = Integer.parseInt((String)cboAno.getSelectedItem());
		int mes = (Integer)cboMes.getSelectedIndex()+1;
				
		//Buscar as movimentações do mês e ano informados
		EstacionamentoController controle = new EstacionamentoController();
		LocalDateTime data = LocalDateTime.of(ano, mes,1,0,0);
		List<Movimentacao> movimentacoes = controle.emitirRelatorio(data);
		
		//Exibe a tela de conteudo e faturamento
		if(cmd.equals("gerar")) {
			JFrame relatorio = new TelaResultadoRelatorio(this, movimentacoes, data);
			relatorio.setVisible(true);
			
		}
		this.dispose();
	}

}
