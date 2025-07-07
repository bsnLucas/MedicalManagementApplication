package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import entities.Endereco;
import entities.Paciente;
import service.EnderecoService;
import service.PacienteService;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AlterarPacienteGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private MostrarPacientesGUI mostrarPacientesGui;
	private PacienteService pacienteService;
	private EnderecoService enderecoService;
	private JTextField txtCpf;
	private JTextField txtRua;
	private JTextField txtNumero;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JFormattedTextField txtCep;
	private JFormattedTextField txtTelefone;
	private JComboBox cbUf;
	private JComboBox cbPagamento;
	
	private MaskFormatter mascaraCep;
	private MaskFormatter mascaraTelefone;
	
	public AlterarPacienteGUI(MostrarPacientesGUI mostrarPacientesGui) {
		
		this.criarMascaras();
		
		this.mostrarPacientesGui = mostrarPacientesGui;
		this.pacienteService = new PacienteService();
		this.enderecoService = new EnderecoService();
		
		this.initComponents();
		this.buscarUf();
		this.buscarPagamento();;
	}
		
	public void atualizar() {
		
		try {
			

			Paciente paciente = new Paciente();
			Endereco endereco = new Endereco();

			paciente.setCpf(this.txtCpf.getText());
			paciente.setTelefone(this.txtTelefone.getText());
			paciente.setFormaPagamento((String) this.cbPagamento.getSelectedItem());
			endereco.setLogradouro(this.txtRua.getText());
			endereco.setNumero(Integer.parseInt(this.txtNumero.getText()));
			endereco.setBairro(this.txtBairro.getText());
			endereco.setCidade(this.txtCidade.getText());
			endereco.setUf((String) this.cbUf.getSelectedItem());
			endereco.setCep(this.txtCep.getText());
			
			int idEndereco = this.pacienteService.buscarIdEndereco(this.txtCpf.getText());
			
			endereco.setId(idEndereco);
			
			this.enderecoService.atualizar(endereco);
			
			paciente.setEndereco(endereco);
	
			this.pacienteService.atualizar(paciente);
			fecharJanela();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível atualizar o cadastro do paciente", "Erro Atualizar Paciente", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void criarMascaras() {
		
		try {

			this.mascaraCep = new MaskFormatter("#####-###");
			this.mascaraTelefone = new MaskFormatter("(##) #####-####");
			
		} catch(ParseException e) {
			
			System.err.println(e.getMessage());
		}
	}
	

	public void buscarUf() {
		
		List<String> listaUf = this.enderecoService.buscarUf();
		
		for(String uf : listaUf) {
			
			this.cbUf.addItem(uf);
		}
		
	}
	
	public void buscarPagamento() {
		
		this.cbPagamento.addItem("Crédito");
		this.cbPagamento.addItem("Débito");
		this.cbPagamento.addItem("PIX");
	}
	
	public void limparCampos() {
		
		this.txtBairro.setText("");
		this.txtCep.setText("");
		this.txtCidade.setText("");
		this.txtCpf.setText("");
		this.txtNumero.setText("");
		this.txtRua.setText("");
		this.txtTelefone.setText("");
		this.cbUf.setSelectedIndex(0);
		this.cbPagamento.setSelectedIndex(0);
	}
	
	public void fecharJanela() {

		this.dispose();
		this.mostrarPacientesGui.setVisible(true);
		this.mostrarPacientesGui.buscarPacientes();
	}
	
	public void initComponents() {
		setTitle("Alterar Cadastro Paciente");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 318);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCpf = new JLabel("Informe o CPF do paciente que deseja alterar o cadastro");
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCpf.setBounds(36, 43, 328, 14);
		contentPane.add(lblCpf);
		
		txtCpf = new JTextField();
		txtCpf.setBounds(382, 41, 117, 20);
		contentPane.add(txtCpf);
		txtCpf.setColumns(10);
		
		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTelefone.setBounds(36, 90, 60, 14);
		contentPane.add(lblTelefone);
		
		txtTelefone = new JFormattedTextField(mascaraTelefone);
		txtTelefone.setBounds(101, 88, 92, 20);
		contentPane.add(txtTelefone);
		
		JLabel lblRua = new JLabel("Rua");
		lblRua.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRua.setBounds(225, 91, 22, 14);
		contentPane.add(lblRua);
		
		txtRua = new JTextField();
		txtRua.setBounds(266, 88, 233, 20);
		contentPane.add(txtRua);
		txtRua.setColumns(10);
		
		JLabel lblNumero = new JLabel("N°");
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNumero.setBounds(36, 136, 46, 14);
		contentPane.add(lblNumero);
		
		txtNumero = new JTextField();
		txtNumero.setBounds(64, 134, 38, 20);
		contentPane.add(txtNumero);
		txtNumero.setColumns(10);
		
		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBairro.setBounds(122, 136, 38, 14);
		contentPane.add(lblBairro);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(170, 134, 92, 20);
		contentPane.add(txtBairro);
		txtBairro.setColumns(10);
		
		JLabel lblCidade = new JLabel("Cidade/UF");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCidade.setBounds(275, 137, 72, 13);
		contentPane.add(lblCidade);
		
		txtCidade = new JTextField();
		txtCidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCidade.setColumns(10);
		txtCidade.setBounds(346, 133, 96, 19);
		contentPane.add(txtCidade);
		
		cbUf = new JComboBox();
		cbUf.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbUf.setBounds(446, 133, 53, 20);
		contentPane.add(cbUf);
		
		JLabel lblCep = new JLabel("CEP");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCep.setBounds(36, 187, 45, 13);
		contentPane.add(lblCep);
		
		txtCep = new JFormattedTextField(mascaraCep);
		txtCep.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCep.setBounds(75, 183, 96, 19);
		contentPane.add(txtCep);
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				atualizar();
			}
		});
		btnAplicar.setBounds(272, 236, 102, 32);
		contentPane.add(btnAplicar);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limparCampos();
			}
		});
		btnLimpar.setBounds(134, 236, 102, 32);
		contentPane.add(btnLimpar);
		
		JLabel lblPagamento = new JLabel("Pagamento");
		lblPagamento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPagamento.setBounds(204, 187, 79, 13);
		contentPane.add(lblPagamento);
		
		cbPagamento = new JComboBox();
		cbPagamento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbPagamento.setBounds(287, 183, 87, 21);
		contentPane.add(cbPagamento);

	}
}
