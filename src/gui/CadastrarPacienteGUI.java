package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import entities.Endereco;
import entities.Paciente;
import service.EnderecoService;
import service.PacienteService;

public class CadastrarPacienteGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private File imagemSelecionada;
	
	private GestaoMedicaMenuGUI menuGui;
	private JTextField txtNome;
	private JTextField txtRua;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JFormattedTextField txtCpf;
	private JFormattedTextField txtDataNascimento;
	private JSpinner spnNumero;
	private JComboBox cbUf;
	private JComboBox cbSexo;
	private JFormattedTextField txtCep;
	private JFormattedTextField txtTelefone;
	private JComboBox cbPagamento;
	
	private MaskFormatter mascaraCpf;
	private MaskFormatter mascaraDataNascimento;
	private MaskFormatter mascaraCep;
	private MaskFormatter mascaraTelefone;
	
	private EnderecoService enderecoService;
	private PacienteService pacienteService;

	public CadastrarPacienteGUI(GestaoMedicaMenuGUI menuGui) {
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosed(WindowEvent e) {
				
				fecharJanela();
			}
		});
		
		this.criarMascaras();
		
		this.enderecoService = new EnderecoService();
		this.pacienteService = new PacienteService();
		
		this.initComponents();
		this.buscarUf();
		this.buscarSexo();
		this.buscarPagamento();
		this.menuGui = menuGui;
	}

	public void cadastrar() {
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Paciente paciente = new Paciente();
			Endereco endereco = new Endereco();
			
			if (imagemSelecionada == null) {
				
			    JOptionPane.showMessageDialog(null, "Por favor, selecione uma imagem antes de cadastrar.");
			    return;
			}
			
			paciente.setCpf((this.txtCpf.getText().replaceAll("\\D+", "")));
			paciente.setNome(this.txtNome.getText());
			paciente.setDataNascimento(new java.sql.Date(sdf.parse(this.txtDataNascimento.getText()).getTime()));
			paciente.setSexo((String) this.cbSexo.getSelectedItem());
			paciente.setTelefone(this.txtTelefone.getText());
			paciente.setFormaPagamento((String) this.cbPagamento.getSelectedItem());
			endereco.setLogradouro(this.txtRua.getText());
			endereco.setNumero((Integer) this.spnNumero.getValue());
			endereco.setBairro(this.txtBairro.getText());
			endereco.setCidade(this.txtCidade.getText());
			endereco.setUf((String) this.cbUf.getSelectedItem());
			endereco.setCep(this.txtCep.getText());
			
			this.enderecoService.cadastrar(endereco);
			
			paciente.setEndereco(endereco);
			
			System.out.println("Imagem selecionada: " + imagemSelecionada);
			
			
			File foto = imagemSelecionada;
			
			this.pacienteService.cadastrar(paciente, foto);
			fecharJanela(); 
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o paciente", "Erro Cadastro Paciente", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void enviarFoto() {
		
		  JFileChooser fileChooser = new JFileChooser();
		  
          int opcao = fileChooser.showOpenDialog(null);
          
          if (opcao == JFileChooser.APPROVE_OPTION) {
           
        	  imagemSelecionada = fileChooser.getSelectedFile();
        	  System.out.println("Imagem selecionada: " + imagemSelecionada);
          }
	}
	
	public void criarMascaras() {
		
		try {
			
			this.mascaraCpf = new MaskFormatter("###.###.###-##");
			this.mascaraDataNascimento = new MaskFormatter("##/##/####");
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
	
	public void buscarSexo() {
		
		this.cbSexo.addItem("M");
		this.cbSexo.addItem("F");
	}
	
	public void buscarPagamento() {
		
		this.cbPagamento.addItem("Crédito");
		this.cbPagamento.addItem("Débito");
		this.cbPagamento.addItem("PIX");
	}
	
	public void fecharJanela() {
		
		this.dispose();
		this.menuGui.setVisible(true);
	}
	
	public void initComponents() {
		setTitle("Cadastro Paciente");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 569, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCpf.setBounds(27, 74, 45, 13);
		contentPane.add(lblCpf);
		
		txtCpf = new JFormattedTextField(mascaraCpf);
		txtCpf.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCpf.setBounds(64, 72, 107, 19);
		contentPane.add(txtCpf);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNome.setBounds(27, 29, 45, 13);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtNome.setBounds(75, 27, 451, 19);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblDataNascimento = new JLabel("Data de Nascimento");
		lblDataNascimento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDataNascimento.setBounds(210, 75, 119, 13);
		contentPane.add(lblDataNascimento);
		
		txtDataNascimento = new JFormattedTextField(mascaraDataNascimento);
		txtDataNascimento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtDataNascimento.setBounds(339, 72, 87, 19);
		contentPane.add(txtDataNascimento);
		
		JLabel lblFoto = new JLabel("Foto");
		lblFoto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFoto.setBounds(27, 125, 45, 13);
		contentPane.add(lblFoto);
		
		JButton btnEnviarFoto = new JButton("Upload Photo");
		btnEnviarFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				enviarFoto();
			}
		});
		btnEnviarFoto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEnviarFoto.setBounds(64, 118, 109, 29);
		contentPane.add(btnEnviarFoto);
		
		JLabel lblRua = new JLabel("Rua");
		lblRua.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRua.setBounds(210, 126, 45, 13);
		contentPane.add(lblRua);
		
		txtRua = new JTextField();
		txtRua.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtRua.setBounds(243, 123, 283, 19);
		contentPane.add(txtRua);
		txtRua.setColumns(10);
		
		JLabel lblNumero = new JLabel("N°");
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNumero.setBounds(27, 178, 24, 13);
		contentPane.add(lblNumero);
		
		spnNumero = new JSpinner();
		spnNumero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		spnNumero.setBounds(53, 176, 55, 20);
		contentPane.add(spnNumero);
		
		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBairro.setBounds(137, 179, 45, 13);
		contentPane.add(lblBairro);
		
		txtBairro = new JTextField();
		txtBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtBairro.setBounds(186, 176, 96, 19);
		contentPane.add(txtBairro);
		txtBairro.setColumns(10);
		
		JLabel lblCidade = new JLabel("Cidade/UF");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCidade.setBounds(307, 179, 58, 13);
		contentPane.add(lblCidade);
		
		txtCidade = new JTextField();
		txtCidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCidade.setColumns(10);
		txtCidade.setBounds(375, 176, 96, 19);
		contentPane.add(txtCidade);
		
		cbUf = new JComboBox();
		cbUf.setBounds(481, 175, 45, 21);
		contentPane.add(cbUf);
		
		JLabel lblCep = new JLabel("CEP");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCep.setBounds(27, 230, 45, 13);
		contentPane.add(lblCep);
		
		txtCep = new JFormattedTextField(mascaraCep);
		txtCep.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCep.setBounds(64, 228, 96, 19);
		contentPane.add(txtCep);
		
		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTelefone.setBounds(186, 231, 58, 13);
		contentPane.add(lblTelefone);
		
		txtTelefone = new JFormattedTextField(mascaraTelefone);
		txtTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtTelefone.setBounds(248, 228, 96, 19);
		contentPane.add(txtTelefone);
		
		JLabel lblPagamento = new JLabel("Pagamento");
		lblPagamento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPagamento.setBounds(364, 230, 79, 13);
		contentPane.add(lblPagamento);
		
		cbPagamento = new JComboBox();
		cbPagamento.setBounds(439, 227, 87, 21);
		contentPane.add(cbPagamento);
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cadastrar();
			}
		});
		btnAplicar.setBounds(296, 268, 126, 45);
		contentPane.add(btnAplicar);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLimpar.setBounds(156, 268, 126, 45);
		contentPane.add(btnLimpar);
		
		JLabel lblSexo = new JLabel("Sexo");
		lblSexo.setBounds(447, 74, 30, 14);
		contentPane.add(lblSexo);
		
		cbSexo = new JComboBox();
		cbSexo.setBounds(481, 70, 45, 22);
		contentPane.add(cbSexo);

	}
}
