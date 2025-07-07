package gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import entities.Medico;
import service.EnderecoService;
import service.MedicoService;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MostrarMedicoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private GestaoMedicaMenuGUI menuGui;
	private MedicoService medicoService;
	private EnderecoService enderecoService;
	
	private JTable tblMedicos;
	private JButton btnAlterar;
	private JButton btnExcluir;
	
	public MostrarMedicoGUI(GestaoMedicaMenuGUI menuGui) {
		
		this.menuGui = menuGui;
		this.medicoService = new MedicoService();
		this.enderecoService = new EnderecoService();
		
		this.initComponents();
		this.buscarMedicos();
	}
	
	public void excluir() {
		
		try {
			
			int crm = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o CPF do medico que deseja excluir do cadastro", "Excluir Cadastro Medico", JOptionPane.OK_CANCEL_OPTION));
			
			int idEndereco = this.medicoService.buscarIdEndereco(crm);
			this.medicoService.excluir(crm);
			this.enderecoService.excluir(idEndereco);
			fecharJanela();
			buscarMedicos();
		
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível excluir o cadastro do medico", "Erro Excluir Cadastro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void abrirJanelaAlterarMedico() {
		
		AlterarMedicoGUI alterarMedicoGui = new AlterarMedicoGUI(this);
		
		alterarMedicoGui.setVisible(true);
		alterarMedicoGui.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void fecharJanela() {
		
		this.dispose();
		this.menuGui.setVisible(true);
	}
	
	public void buscarMedicos() {
		
		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblMedicos.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);
			
			List<Medico> listaMedicos = this.medicoService.buscarComEndereco();
			
			for(Medico medico : listaMedicos) {
				
				modelo.addRow(new Object[] {
						
					medico.getNome(),
					medico.getCrm(),
					medico.getTelefone(),
					medico.getEspecialidade().getNome(),
					medico.getEndereco().getLogradouro(),
					medico.getEndereco().getNumero(),
					medico.getEndereco().getBairro(),
					medico.getEndereco().getCidade(),
					medico.getEndereco().getUf(),
					medico.getEndereco().getCep()
				});
			}
			
			
		} catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar os médicos no banco", "Erro Buscar Médicos", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void initComponents() {
		setTitle("Médicos Cadastrados");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 906, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JPanel pnMedicos = new JPanel();
		pnMedicos.setBorder(new TitledBorder(null, "Medicos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnMedicos.setBounds(10, 11, 870, 251);
		contentPane.add(pnMedicos);
		
		tblMedicos = new JTable();
		tblMedicos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nome", "CRM", "Telefone", "Especialidade", "Rua", "N\u00FAmero", "Bairro", "Cidade", "UF", "CEP"
			}
		));
		tblMedicos.getColumnModel().getColumn(0).setPreferredWidth(138);
		tblMedicos.getColumnModel().getColumn(1).setPreferredWidth(82);
		tblMedicos.getColumnModel().getColumn(2).setPreferredWidth(110);
		tblMedicos.getColumnModel().getColumn(3).setPreferredWidth(91);
		tblMedicos.getColumnModel().getColumn(4).setPreferredWidth(120);
		tblMedicos.getColumnModel().getColumn(5).setPreferredWidth(55);
		tblMedicos.getColumnModel().getColumn(7).setPreferredWidth(84);
		tblMedicos.getColumnModel().getColumn(8).setPreferredWidth(29);
		pnMedicos.add(tblMedicos);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaAlterarMedico();
			}
		});
		btnAlterar.setBounds(779, 280, 89, 23);
		contentPane.add(btnAlterar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				excluir();
			}
		});
		btnExcluir.setBounds(669, 280, 89, 23);
		contentPane.add(btnExcluir);
	}
}
