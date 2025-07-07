package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import entities.AgendaConsulta;
import entities.Medico;
import service.AgendaConsultaService;
import service.MedicoService;

public class RelatorioAgendaMedicoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblMedico;
	private JComboBox cbMedico;
	private JButton btnSearch;
	private JTable tblConsultas;
	
	private AgendaConsultaService agendaConsultaService;
	private MedicoService medicoService;
	private GestaoMedicaMenuGUI menuGui;
	private JButton btnGerarRelatorio;
	
	public RelatorioAgendaMedicoGUI(GestaoMedicaMenuGUI menuGui) {
		
		this.menuGui = menuGui;
		this.medicoService = new MedicoService();
		this.agendaConsultaService = new AgendaConsultaService();
		
		this.initComponents();
		this.buscarConsultas();
	}
	
	public void buscarConsultas() {
		
		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblConsultas.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);
			
			List<AgendaConsulta> listaAgendaConsulta = this.agendaConsultaService.buscarTodos();
			
			for(AgendaConsulta agendaConsulta : listaAgendaConsulta) {
				
				modelo.addRow(new Object[] {
						
					agendaConsulta.getId(),
					agendaConsulta.getData(),
					agendaConsulta.getHorario(),
					agendaConsulta.getPaciente().getNome(),
					agendaConsulta.getMedico().getNome(),
					agendaConsulta.getStatus()
				});
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao buscar consultas agendadas no sistema", "Erro Buscar Consultas Agendadas", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void buscarConsultasPorMedico(int crm) {
		
		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblConsultas.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);
			
			List<AgendaConsulta> listaAgendaConsulta = this.agendaConsultaService.buscarPorMedico(crm);
			
			for(AgendaConsulta agendaConsulta : listaAgendaConsulta) {
				
				modelo.addRow(new Object[] {
						
					agendaConsulta.getId(),
					agendaConsulta.getData(),
					agendaConsulta.getHorario(),
					agendaConsulta.getPaciente().getNome(),
					agendaConsulta.getMedico().getNome(),
					agendaConsulta.getStatus()
				});
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao buscar consultas agendadas no sistema", "Erro Buscar Consultas Agendadas", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void gerarRelatorio() {
		
		try {
			
			int crm = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o CRM do médico que deseja gerar o relatório", "Gerar Relatório Agenda Médico", JOptionPane.OK_CANCEL_OPTION));

			File file = new File("RelatorioAgendaMedico_" + crm + ".txt");
			BufferedWriter documento = new BufferedWriter(new FileWriter(file));

			documento.write("========================================\n");
			documento.write("Agenda do Médico de CRM: " + crm + "\n\n");

			List<AgendaConsulta> listaAgendaConsulta = this.agendaConsultaService.buscarPorMedico(crm);
			
			for (AgendaConsulta agendaConsulta : listaAgendaConsulta) {
				
				documento.write("ID da Consulta: " + agendaConsulta.getId() + "\n");
				documento.write("Nome do Paciente: " + agendaConsulta.getPaciente().getNome() + "\n");
				documento.write("Data: " + agendaConsulta.getData() + "\n");
				documento.write("Horário: " + agendaConsulta.getHorario() + "\n");
				documento.write("Status: " + agendaConsulta.getStatus() + "\n");
				documento.write("========================================\n");
			
			}
			
			documento.close();
			
			JOptionPane.showMessageDialog(null, "Relatório de Agenda de Médico foi criado com sucesso em:" + file.getAbsolutePath(), "Relatório Agenda Criado", JOptionPane.DEFAULT_OPTION);
			this.fecharJanela();
			
		} catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Não foi possível gerar o relatório da agenda do médico", "Erro Gerar Relatório", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void buscarMedico() {
		
		try {
			
			int crm = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o CRM do médico", "Buscar Médico", JOptionPane.OK_CANCEL_OPTION));
			
			List<Medico> listaMedicos = this.medicoService.buscarTodos();
			
			for(Medico medico : listaMedicos) {
				
				if(medico.getCrm() == crm) {
					
					this.cbMedico.addItem(medico);
					this.buscarConsultasPorMedico(crm);
					return;
				}
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao buscar medico", "Erro Buscar Medico", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void fecharJanela() {
		
		this.dispose();
		this.menuGui.setVisible(true);
	}
	
	public void initComponents() {
		setTitle("Gerar Relatório Agenda Médico");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 479, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblMedico = new JLabel("Médico");
		lblMedico.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMedico.setBounds(34, 30, 46, 14);
		contentPane.add(lblMedico);
		
		cbMedico = new JComboBox();
		cbMedico.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbMedico.setBounds(97, 27, 223, 22);
		contentPane.add(cbMedico);
		
		btnSearch = new JButton("Buscar");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				buscarMedico();
			}
		});
		btnSearch.setBounds(330, 27, 90, 23);
		contentPane.add(btnSearch);
		
		JPanel pnConsultas = new JPanel();
		pnConsultas.setBorder(new TitledBorder(null, "Consultas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnConsultas.setBounds(10, 70, 441, 292);
		contentPane.add(pnConsultas);
		
		tblConsultas = new JTable();
		tblConsultas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Data", "Hor\u00E1rio", "Paciente", "M\u00E9dico"
			}
		));
		tblConsultas.getColumnModel().getColumn(1).setPreferredWidth(71);
		tblConsultas.getColumnModel().getColumn(3).setPreferredWidth(94);
		tblConsultas.getColumnModel().getColumn(4).setPreferredWidth(110);
		pnConsultas.add(tblConsultas);
		
		btnGerarRelatorio = new JButton("Gerar Relatório");
		btnGerarRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				gerarRelatorio();
			}
		});
		btnGerarRelatorio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnGerarRelatorio.setBounds(316, 372, 119, 21);
		contentPane.add(btnGerarRelatorio);

	}

}
