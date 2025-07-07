package gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import entities.Paciente;
import service.EnderecoService;
import service.PacienteService;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MostrarPacientesGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private GestaoMedicaMenuGUI menuGui;
	private PacienteService pacienteService;
	private EnderecoService enderecoService;
	private JTable tblPacientes;
	private JButton btnAlterar;
	private JButton btnExcluir;

	public MostrarPacientesGUI(GestaoMedicaMenuGUI menuGui) {

		this.menuGui = menuGui;
		this.pacienteService = new PacienteService();
		this.enderecoService = new EnderecoService();
		
		this.initComponents();
		this.buscarPacientes();
	}
	
	public void abrirJanelaAlterar() {
		
		AlterarPacienteGUI alterarPacienteGui = new AlterarPacienteGUI(this);
		
		alterarPacienteGui.setVisible(true);
		alterarPacienteGui.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void excluir() {
		
		try {
			
			String cpf = JOptionPane.showInputDialog(null, "Insira o CPF do paciente que deseja excluir do cadastro", "Excluir Cadastro Paciente", JOptionPane.OK_CANCEL_OPTION);
			
			int idEndereco = this.pacienteService.buscarIdEndereco(cpf);
			this.pacienteService.excluir(cpf);
			this.enderecoService.excluir(idEndereco);
			fecharJanela();
			buscarPacientes();
		
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível excluir o cadastro do paciente", "Erro Excluir Cadastro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void buscarPacientes() {
		
		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblPacientes.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);
			
			List<Paciente> listaPacientes = this.pacienteService.buscarComEndereco();
			
			for(Paciente paciente : listaPacientes) {
				
				modelo.addRow(new Object[] {
						
					paciente.getNome(),
					paciente.getCpf(),
					paciente.getFoto(),
					paciente.getDataNascimento(),
					paciente.getSexo(),
					paciente.getTelefone(),
					paciente.getFormaPagamento(),
					paciente.getEndereco().getLogradouro(),
					paciente.getEndereco().getNumero(),
					paciente.getEndereco().getBairro(),
					paciente.getEndereco().getCidade(),
					paciente.getEndereco().getUf(),
					paciente.getEndereco().getCep()
				});
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível buscar os pacientes", "Erro Buscar Pacientes", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void fecharJanela() {

		this.dispose();
		this.menuGui.setVisible(true);
	}

	public void initComponents() {
		setTitle("Pacientes Cadastrados");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1151, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnPacientes = new JPanel();
		pnPacientes.setBorder(new TitledBorder(null, "Pacientes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnPacientes.setBounds(10, 11, 1115, 251);
		contentPane.add(pnPacientes);
		
		tblPacientes = new JTable();
		tblPacientes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "CPF", "Foto", "Data de Nascimento", "Sexo", "Telefone", "Forma de Pagamento", "Rua", "N\u00FAmero", "Bairro", "Cidade", "UF", "CEP"
			}
		));
		tblPacientes.getColumnModel().getColumn(0).setPreferredWidth(138);
		tblPacientes.getColumnModel().getColumn(1).setPreferredWidth(82);
		tblPacientes.getColumnModel().getColumn(2).setPreferredWidth(79);
		tblPacientes.getColumnModel().getColumn(3).setPreferredWidth(107);
		tblPacientes.getColumnModel().getColumn(4).setPreferredWidth(35);
		tblPacientes.getColumnModel().getColumn(5).setPreferredWidth(110);
		tblPacientes.getColumnModel().getColumn(6).setPreferredWidth(115);
		tblPacientes.getColumnModel().getColumn(7).setPreferredWidth(120);
		tblPacientes.getColumnModel().getColumn(8).setPreferredWidth(55);
		tblPacientes.getColumnModel().getColumn(10).setPreferredWidth(84);
		tblPacientes.getColumnModel().getColumn(11).setPreferredWidth(29);
		pnPacientes.add(tblPacientes);
		
		btnAlterar = new JButton("Alterar Cadastro");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaAlterar();
			}
		});
		btnAlterar.setBounds(993, 273, 121, 23);
		contentPane.add(btnAlterar);
		
		btnExcluir = new JButton("Excluir Cadastro");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				excluir();
			}
		});
		btnExcluir.setBounds(851, 273, 121, 23);
		contentPane.add(btnExcluir);

	}
}
