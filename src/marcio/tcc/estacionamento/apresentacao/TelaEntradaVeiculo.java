package marcio.tcc.estacionamento.apresentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import marcio.tcc.estacionamento.controle.EstacionamentoController;
import marcio.tcc.estacionamento.controle.EstacionamentoException;
import marcio.tcc.estacionamento.controle.VeiculoException;

@SuppressWarnings("serial")
public class TelaEntradaVeiculo extends JFrame implements ActionListener{

	private JFrame parent;
	private JTextField txtMarca;
	private JTextField txtModelo;
	private JTextField txtCor;
	private JFormattedTextField txfPlaca;
	private JButton btnOk;
	private JButton btnCancel;
	
	public TelaEntradaVeiculo(JFrame parent) {
		setResizable(false);
		setSize(400,300);
		setTitle("Entrada de Ve\u00EDculo");
		
		this.parent = parent;
		getContentPane().setLayout(null);
		
		JLabel lblPlaca = new JLabel("Placa:");
		lblPlaca.setBounds(123, 14, 46, 14);
		getContentPane().add(lblPlaca);
		
		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBounds(123, 75, 46, 14);
		getContentPane().add(lblMarca);
		
		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBounds(123, 126, 46, 14);
		getContentPane().add(lblModelo);
		
		JLabel lblCor = new JLabel("Cor:");
		lblCor.setBounds(123, 182, 46, 14);
		getContentPane().add(lblCor);
		
		txtMarca = new JTextField();
		txtMarca.setBounds(168, 72, 86, 20);
		getContentPane().add(txtMarca);
		txtMarca.setColumns(10);
		
		txtModelo = new JTextField();
		txtModelo.setBounds(168, 123, 86, 20);
		getContentPane().add(txtModelo);
		txtModelo.setColumns(10);
		
		txtCor = new JTextField();
		txtCor.setBounds(168, 179, 86, 20);
		getContentPane().add(txtCor);
		txtCor.setColumns(10);
		
		btnOk = new JButton("Ok");
		btnOk.setBounds(88, 228, 89, 23);
		btnOk.addActionListener(this);
		btnOk.setActionCommand("ok");
		getContentPane().add(btnOk);
		
		btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(200, 228, 89, 23);
		btnCancel.addActionListener(this);
		btnCancel.setActionCommand("cancel");
		getContentPane().add(btnCancel);
		
		try {
			txfPlaca = new JFormattedTextField(new MaskFormatter("UUU-####"));
		} catch (ParseException e) {
			assert false : "Padrão de placa Inválido";
		}
		txfPlaca.setColumns(10);
		txfPlaca.setBounds(168, 11,86, 20);
		getContentPane().add(txfPlaca);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		if(evento.getActionCommand().equals("ok")) {
			EstacionamentoController controle = new EstacionamentoController();
			try {
				controle.processarEntrada(txfPlaca.getText(), txtMarca.getText(), txtModelo.getText(), txtCor.getText());
				
				//Mostra mensagem de sucesso
				JOptionPane.showMessageDialog(null, "Veículo registrado com sucesso","Entrada de veículo",JOptionPane.INFORMATION_MESSAGE);
			} catch (EstacionamentoException | VeiculoException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(),"Falha na entrada",JOptionPane.ERROR_MESSAGE);
			}
			
			this.parent.setVisible(true);
			this.dispose();
		}
		this.parent.setVisible(true);
		this.dispose();
	}
}
