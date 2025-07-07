package gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import entities.AgendaExame;
import service.AgendaExameService;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MostrarExameGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private AgendaExameService agendaExameService;
	private GestaoMedicaMenuGUI menuGui;
	private JTable tblExamesAgendados;

	public MostrarExameGUI(GestaoMedicaMenuGUI menuGui) {

		this.menuGui = menuGui;
		this.agendaExameService = new AgendaExameService();

		this.initComponents();
		this.buscarExames();
	}
	
	public void alterar() {
		
		try {
			
			int idExame = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o ID do exame agendado que deseja alterar o status", "Alterar Status Exame Agendado", JOptionPane.OK_CANCEL_OPTION));
		
			this.agendaExameService.atualizar(idExame);
			buscarExames();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao alterar status de exame agendado", "Erro Alterar Status Exame", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void excluir() {
		
		try {
			
			int idExame = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o ID do exame agendado que deseja excluir", "Excluir Exame Agendado", JOptionPane.OK_CANCEL_OPTION));
			
			this.agendaExameService.excluir(idExame);
			fecharJanela();
			buscarExames();
			
			
		} catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao excluir exame agendado", "Erro Excluir Exame", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void buscarExames() {

		try {

			DefaultTableModel modelo = (DefaultTableModel) tblExamesAgendados.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);

			List<AgendaExame> listaAgendaExame = this.agendaExameService.buscarExameAgendado();

			System.out.println();

			for (AgendaExame agendaExame : listaAgendaExame) {

				modelo.addRow(new Object[] {

					agendaExame.getCodigoAgendamento(), agendaExame.getData(), agendaExame.getHorario(),
					agendaExame.getExame().getNome(), agendaExame.getPaciente().getNome(),
					agendaExame.getMedico().getNome(), agendaExame.getPreco(), agendaExame.getStatus() 
					
				});
			}

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível encontrar os exames agendados",
					"Erro Buscar Exames Agendados", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void fecharJanela() {

		this.dispose();
		this.menuGui.setVisible(true);
	}

	public void initComponents() {
		setTitle("Exames Agendados");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 823, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel pnExamesAgendados = new JPanel();
		pnExamesAgendados.setBorder(
				new TitledBorder(null, "Exames Agendados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnExamesAgendados.setBounds(10, 10, 789, 298);
		contentPane.add(pnExamesAgendados);

		tblExamesAgendados = new JTable();
		tblExamesAgendados
				.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "C\u00F3digo do Agendamento", "Data",
						"Hor\u00E1rio", "Exame", "Paciente", "M\u00E9dico", "Pre\u00E7o", "Status" }));
		tblExamesAgendados.getColumnModel().getColumn(0).setPreferredWidth(128);
		tblExamesAgendados.getColumnModel().getColumn(3).setPreferredWidth(106);
		tblExamesAgendados.getColumnModel().getColumn(4).setPreferredWidth(115);
		tblExamesAgendados.getColumnModel().getColumn(5).setPreferredWidth(125);
		pnExamesAgendados.add(tblExamesAgendados);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				alterar();
			}
		});
		btnAlterar.setBounds(701, 324, 85, 21);
		contentPane.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				excluir();
			}
		});
		btnExcluir.setBounds(597, 324, 85, 21);
		contentPane.add(btnExcluir);

	}
}
