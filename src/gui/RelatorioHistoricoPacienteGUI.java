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
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import entities.AgendaConsulta;
import entities.AgendaExame;
import entities.Paciente;
import service.AgendaConsultaService;
import service.AgendaExameService;
import service.PacienteService;

public class RelatorioHistoricoPacienteGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox cbPaciente;
	
	private GestaoMedicaMenuGUI menuGui;
	private PacienteService pacienteService;
	private AgendaConsultaService agendaConsultaService;
	private AgendaExameService agendaExameService;
	private JTable tblConsultas;
	private JTable tblExames;
	
	public RelatorioHistoricoPacienteGUI(GestaoMedicaMenuGUI menuGui) {
		
		this.menuGui = menuGui;
		this.pacienteService = new PacienteService();
		this.agendaConsultaService = new AgendaConsultaService();
		this.agendaExameService = new AgendaExameService();
		
		this.initComponents();
		this.buscarPaciente();
	}
	
	public void gerarArquivo() {
		
		try {
			
			Paciente paciente = ((Paciente) this.cbPaciente.getSelectedItem());
			
			String cpf = paciente.getCpf();
			
			File file = new File("Histórico_Paciente_CPF" + cpf + ".txt");
			BufferedWriter documento = new BufferedWriter(new FileWriter(file));
			
			documento.write("========================================\n");
			documento.write("Histórico do Paciente: " + paciente.getNome() + "\n");
			documento.write("========================================\n");
			documento.write("Consultas Realizadas\n\n");

			List<AgendaConsulta> listaAgendaConsulta = this.agendaConsultaService.buscarPorPaciente(cpf);
			
			for (AgendaConsulta agendaConsulta : listaAgendaConsulta) {
				
				documento.write("ID da Consulta: " + agendaConsulta.getId() + "\n");
				documento.write("Nome do Paciente: " + agendaConsulta.getPaciente().getNome() + "\n");
				documento.write("Data: " + agendaConsulta.getData() + "\n");
				documento.write("Horário: " + agendaConsulta.getHorario() + "\n");
				documento.write("Status: " + agendaConsulta.getStatus() + "\n");
				documento.write("========================================\n");
			
			}
			documento.write("==============================================================\n");
			documento.write("========================================\n");
			documento.write("Exames Realizados\n\n");
			
			List<AgendaExame> listaAgendaExames = this.agendaExameService.buscarExameAgendadoPorCpf(cpf);
			
			for (AgendaExame agendaExame : listaAgendaExames) {
				
				documento.write("ID do Exame: " + agendaExame.getCodigoAgendamento() + "\n");
				documento.write("Nome do Paciente: " + agendaExame.getPaciente().getNome() + "\n");
				documento.write("Nome do Médico: " + agendaExame.getMedico().getNome() +"\n");
				documento.write("Data: " + agendaExame.getData() + "\n");
				documento.write("Horário: " + agendaExame.getHorario() + "\n");
				documento.write("Preço do Exame: R$" + String.format("%.2f", agendaExame.getPreco()) + "\n");
				documento.write("Status: " + agendaExame.getStatus() + "\n");
				documento.write("========================================\n");
			
			}
			
			documento.close();
			
			JOptionPane.showMessageDialog(null, "Histórico do Paciente foi criado com sucesso em:" + file.getAbsolutePath(), "Relatório Exame Criado", JOptionPane.DEFAULT_OPTION);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível gerar o histórico do paciente", "Erro Gerar Histórico", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void buscarConsultasPorPaciente(String cpf) {
		
		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblConsultas.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);
			
			List<AgendaConsulta> listaAgendaConsulta = this.agendaConsultaService.buscarPorPaciente(cpf);
			
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
	
	
	public void buscarExamesAgendados(String cpf) {

		try {

			DefaultTableModel modelo = (DefaultTableModel) tblExames.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);

			List<AgendaExame> listaAgendaExame = this.agendaExameService.buscarExameAgendadoPorCpf(cpf);

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
	
	public void buscarPaciente() {
		
		try {
			
			String cpf = JOptionPane.showInputDialog(null, "Insira o CPF do paciente", "Buscar Paciente", JOptionPane.OK_CANCEL_OPTION);

			List<Paciente> listaPacientes = this.pacienteService.buscarTodos();
			
			for(Paciente paciente : listaPacientes) {
				
				if(paciente.getCpf().equals(cpf)) {
					
					this.cbPaciente.addItem(paciente);
					this.buscarConsultasPorPaciente(cpf);
					this.buscarExamesAgendados(cpf);
					return;
				}
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao buscar paciente", "Erro Buscar Paciente", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void fecharJanela() {
		
		this.dispose();
		this.menuGui.setVisible(true);
	}
	
	public void initComponents() {
		setTitle("Relatório Histórico Paciente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 992, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPaciente = new JLabel("Paciente");
		lblPaciente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPaciente.setBounds(34, 22, 48, 16);
		contentPane.add(lblPaciente);
		
		cbPaciente = new JComboBox();
		cbPaciente.setBounds(92, 21, 195, 21);
		contentPane.add(cbPaciente);
		
		JButton btnSearch = new JButton("Buscar");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				buscarPaciente();
			}
		});
		btnSearch.setBounds(297, 21, 86, 21);
		contentPane.add(btnSearch);
		
		JPanel pnConsultas = new JPanel();
		pnConsultas.setBorder(new TitledBorder(null, "Consultas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnConsultas.setBounds(30, 70, 432, 295);
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
		
		JPanel pnExames = new JPanel();
		pnExames.setBorder(new TitledBorder(null, "Exames", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnExames.setBounds(510, 70, 438, 295);
		contentPane.add(pnExames);
		
		tblExames = new JTable();
		tblExames.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Data", "Hor\u00E1rio", "Paciente", "M\u00E9dico"
			}
		));
		tblExames.getColumnModel().getColumn(1).setPreferredWidth(71);
		tblExames.getColumnModel().getColumn(3).setPreferredWidth(94);
		tblExames.getColumnModel().getColumn(4).setPreferredWidth(110);
		pnExames.add(tblExames);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(483, 70, 2, 295);
		contentPane.add(separator);
		
		JButton btnGerarArquivo = new JButton("Gerar Arquivo");
		btnGerarArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				gerarArquivo();
			}
		});
		btnGerarArquivo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnGerarArquivo.setBounds(815, 381, 122, 21);
		contentPane.add(btnGerarArquivo);

	}
}
