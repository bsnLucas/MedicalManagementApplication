package gui;

import java.awt.Font;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import entities.Especialidade;
import entities.Medico;
import service.EnderecoService;
import service.EspecialidadeService;
import service.MedicoService;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastrarMedicoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox cbUf;
	private JComboBox cbEspecialidade;
	
	private MaskFormatter mascaraDataNascimento;
	private MaskFormatter mascaraCep;
	private MaskFormatter mascaraTelefone;
	private MaskFormatter mascaraCrm;
	
	private GestaoMedicaMenuGUI menuGui;
	private EnderecoService enderecoService;
	private EspecialidadeService especialidadeService;
	private MedicoService medicoService;
	private JTextField txtNome;
	private JTextField txtRua;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JFormattedTextField txtCrm;
	private JFormattedTextField txtTelefone;
	private JFormattedTextField txtCep;
	private JSpinner spnNumero;
	
	public CadastrarMedicoGUI(GestaoMedicaMenuGUI menuGui) {
		
		this.criarMascaras();
		
		this.enderecoService = new EnderecoService();
		this.medicoService = new MedicoService();
		this.especialidadeService = new EspecialidadeService();
		
		this.initComponents();
		this.buscarEspecialidades();
		this.buscarUf();
		this.menuGui = menuGui;
	}
	
	public void cadastrar() {
		
		try {
			
			Medico medico = new Medico();
			Endereco endereco = new Endereco();
			
			medico.setCrm(Integer.parseInt(this.txtCrm.getText()));
			medico.setNome(this.txtNome.getText());
			medico.setTelefone(this.txtTelefone.getText());
			medico.setEspecialidade((Especialidade) this.cbEspecialidade.getSelectedItem());
			endereco.setLogradouro(this.txtRua.getText());
			endereco.setNumero((Integer) this.spnNumero.getValue());
			endereco.setBairro(this.txtBairro.getText());
			endereco.setCidade(this.txtCidade.getText());
			endereco.setUf((String) this.cbUf.getSelectedItem());
			endereco.setCep(this.txtCep.getText());
			
			this.enderecoService.cadastrar(endereco);
			
			medico.setEndereco(endereco);
			
			this.medicoService.cadastrar(medico);
			limparCampos();
			fecharJanela();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar médico na base de dados!", "Erro Cadastrar Medico", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void limparCampos() {
		
		this.txtNome.setText("");
		this.txtCrm.setText("");
		this.txtTelefone.setText("");
		this.cbEspecialidade.setSelectedIndex(0);
		this.txtRua.setText("");
		this.spnNumero.setValue(0);
		this.txtBairro.setText("");
		this.txtCidade.setText("");
		this.txtCep.setText("");
		this.cbUf.setSelectedIndex(0);
	}
	
	public void fecharJanela() {
		
		this.dispose();
		this.menuGui.setVisible(true);
	}
	
	public void criarMascaras() {
		
		try {
			
			this.mascaraCep = new MaskFormatter("#####-###");
			this.mascaraTelefone = new MaskFormatter("(##) #####-####");
			this.mascaraCrm = new MaskFormatter("######");
			
		} catch(ParseException e) {
			
			System.err.println(e.getMessage());
		}
	}
	
	public void buscarEspecialidades() {
		
		try {
			
			List<Especialidade> listaEspecialidade = this.especialidadeService.buscarTodos();
			
			for(Especialidade especialidade : listaEspecialidade) {
				
				this.cbEspecialidade.addItem(especialidade);
			}
			
		} catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao buscar especialidades na base de dados", "Erro Buscar Especialidades", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void buscarUf() {
		
		List<String> listaUf = this.enderecoService.buscarUf();
		
		for(String uf : listaUf) {
			
			this.cbUf.addItem(uf);
		}
		
	}

	public void initComponents() {
		setTitle("Cadastro Médico");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 538, 311);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNome.setBounds(31, 38, 42, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(75, 36, 431, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblCrm = new JLabel("CRM");
		lblCrm.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCrm.setBounds(31, 83, 46, 14);
		contentPane.add(lblCrm);
		
		txtCrm = new JFormattedTextField(mascaraCrm);
		txtCrm.setBounds(75, 81, 80, 20);
		contentPane.add(txtCrm);
		
		JLabel lblEspecialidade = new JLabel("Especialidade");
		lblEspecialidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEspecialidade.setBounds(181, 84, 80, 14);
		contentPane.add(lblEspecialidade);
		
		cbEspecialidade = new JComboBox();
		cbEspecialidade.setBounds(271, 80, 235, 22);
		contentPane.add(cbEspecialidade);
		
		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTelefone.setBounds(31, 128, 50, 14);
		contentPane.add(lblTelefone);
		
		txtTelefone = new JFormattedTextField(mascaraTelefone);
		txtTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtTelefone.setBounds(98, 126, 80, 20);
		contentPane.add(txtTelefone);
		
		JLabel lblRua = new JLabel("Rua");
		lblRua.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRua.setBounds(204, 128, 31, 14);
		contentPane.add(lblRua);
		
		txtRua = new JTextField();
		txtRua.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtRua.setBounds(243, 126, 263, 20);
		contentPane.add(txtRua);
		txtRua.setColumns(10);
		
		spnNumero = new JSpinner();
		spnNumero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		spnNumero.setBounds(58, 168, 46, 20);
		contentPane.add(spnNumero);
		
		JLabel lblNumero = new JLabel("N°");
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNumero.setBounds(31, 171, 46, 14);
		contentPane.add(lblNumero);
		
		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBairro.setBounds(127, 172, 46, 14);
		contentPane.add(lblBairro);
		
		txtBairro = new JTextField();
		txtBairro.setBounds(175, 169, 99, 20);
		contentPane.add(txtBairro);
		txtBairro.setColumns(10);
		
		JLabel lblCidadeUf = new JLabel("Cidade/UF");
		lblCidadeUf.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCidadeUf.setBounds(294, 172, 64, 14);
		contentPane.add(lblCidadeUf);
		
		txtCidade = new JTextField();
		txtCidade.setBounds(368, 169, 86, 20);
		contentPane.add(txtCidade);
		txtCidade.setColumns(10);
		
		cbUf = new JComboBox();
		cbUf.setBounds(464, 168, 42, 22);
		contentPane.add(cbUf);
		
		JLabel lblCep = new JLabel("CEP");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCep.setBounds(31, 216, 46, 14);
		contentPane.add(lblCep);
		
		txtCep = new JFormattedTextField(mascaraCep);
		txtCep.setBounds(66, 214, 80, 20);
		contentPane.add(txtCep);
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cadastrar();
			}
		});
		btnAplicar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAplicar.setBounds(384, 212, 114, 49);
		contentPane.add(btnAplicar);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limparCampos();
			}
		});
		btnLimpar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLimpar.setBounds(244, 213, 114, 49);
		contentPane.add(btnLimpar);

	}
}
