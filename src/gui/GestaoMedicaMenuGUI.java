package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GestaoMedicaMenuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public GestaoMedicaMenuGUI() {
		
		this.initComponents();
	}
	
	public void abrirJanelaCadastroPaciente() {
		
		CadastrarPacienteGUI cadastroPacienteGui = new CadastrarPacienteGUI(this);
		
		cadastroPacienteGui.setVisible(true);
		cadastroPacienteGui.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void abrirJanelaCadastroMedico() {
		
		CadastrarMedicoGUI cadastroMedicoGui = new CadastrarMedicoGUI(this);
		
		cadastroMedicoGui.setVisible(true);
		cadastroMedicoGui.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void abrirJanelaCadastroEspecialidade() {
		
		CadastrarEspecialidadeGUI cadastroEspecialidadeGui = new CadastrarEspecialidadeGUI(this);
		
		cadastroEspecialidadeGui.setVisible(true);
		cadastroEspecialidadeGui.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void abrirJanelaCadastroExame() {
		
		CadastroExameGUI cadastroExameGui = new CadastroExameGUI(this);
		
		cadastroExameGui.setVisible(true);
		cadastroExameGui.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void abrirJanelaAgendarExame() {
		
		AgendarExameGUI agendaExameGui = new AgendarExameGUI(this);
		
		agendaExameGui.setVisible(true);
		agendaExameGui.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void abrirJanelaAgendarConsulta() {
		
		AgendaConsultaGUI agendaConsultaGui = new AgendaConsultaGUI(this);
		
		agendaConsultaGui.setVisible(true);
		agendaConsultaGui.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void abrirJanelaMostrarPacientes() {
		
		MostrarPacientesGUI mostrarPacientesGui = new MostrarPacientesGUI(this);
		
		mostrarPacientesGui.setVisible(true);
		mostrarPacientesGui.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void abrirJanelaMostrarMedicos() {
		
		MostrarMedicoGUI mostrarMedicoGui = new MostrarMedicoGUI(this);
		
		mostrarMedicoGui.setVisible(true);
		mostrarMedicoGui.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void abrirJanelaMostrarExames() {
		
		MostrarExameGUI mostrarExameGui = new MostrarExameGUI(this);
		
		mostrarExameGui.setVisible(true);
		mostrarExameGui.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void abrirJanelaMostrarConsultas() {
		
		MostrarConsultaGUI mostrarConsultaGui = new MostrarConsultaGUI(this);
		
		mostrarConsultaGui.setVisible(true);
		mostrarConsultaGui.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void abrirJanelaRelatorioAgendaMedico() {
		
		RelatorioAgendaMedicoGUI relatorioAgendaMedico = new RelatorioAgendaMedicoGUI(this);
		
		relatorioAgendaMedico.setVisible(true);
		relatorioAgendaMedico.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void abrirJanelaRelatorioAgendaExame() {
		
		RelatorioAgendaExameGUI relatorioAgendaExame = new RelatorioAgendaExameGUI(this);
		
		relatorioAgendaExame.setVisible(true);
		relatorioAgendaExame.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	public void abrirJanelaRelatorioHistoricoPaciente() {
		
		RelatorioHistoricoPacienteGUI relatorioHistoricoPaciente = new RelatorioHistoricoPacienteGUI(this);
		
		relatorioHistoricoPaciente.setVisible(true);
		relatorioHistoricoPaciente.setLocationRelativeTo(null);
		
		this.setVisible(false);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestaoMedicaMenuGUI frame = new GestaoMedicaMenuGUI();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void initComponents() {
		
		setTitle("Gerenciamento Médico");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 319, 378);
		
		JMenuBar mnBar = new JMenuBar();
		setJMenuBar(mnBar);
		
		JMenu mnMenu = new JMenu("Menu");
		mnBar.add(mnMenu);
		
		JMenu mnPaciente = new JMenu("Paciente");
		mnMenu.add(mnPaciente);
		
		JMenuItem mnCadastrarPaciente = new JMenuItem("Cadastrar Paciente");
		mnCadastrarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaCadastroPaciente();
			}
		});
		mnPaciente.add(mnCadastrarPaciente);
		
		JMenu mnMedico = new JMenu("Médico");
		mnMenu.add(mnMedico);
		
		JMenuItem mnCadastrarEspecialidades = new JMenuItem("Cadastrar Especialidades");
		mnCadastrarEspecialidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaCadastroEspecialidade();
			}
		});
		mnMedico.add(mnCadastrarEspecialidades);
		
		JMenuItem mnCadastrarMedico = new JMenuItem("Cadastrar Médico");
		mnCadastrarMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaCadastroMedico();
			}
		});
		mnMedico.add(mnCadastrarMedico);
		
		JMenu mnExame = new JMenu("Exame");
		mnMenu.add(mnExame);
		
		JMenuItem mnCadastrarExame = new JMenuItem("Cadastrar Exame");
		mnCadastrarExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaCadastroExame();
			}
		});
		mnExame.add(mnCadastrarExame);
		
		JMenuItem mnAgendarExame = new JMenuItem("Agendar Exame");
		mnAgendarExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaAgendarExame();
			}
		});		
		mnExame.add(mnAgendarExame);
		
		JMenu mnConsulta = new JMenu("Consulta");
		mnMenu.add(mnConsulta);
		
		JMenuItem mnAgendarConsulta = new JMenuItem("Agendar Consulta");
		mnAgendarConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaAgendarConsulta();
			}
		});	
		mnConsulta.add(mnAgendarConsulta);
		
		JMenu mnRelatorio = new JMenu("Relatórios");
		mnBar.add(mnRelatorio);
		
		JMenuItem mnAgendaMédico = new JMenuItem("Agenda Médico");
		mnAgendaMédico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaRelatorioAgendaMedico();
			}
		});
		mnRelatorio.add(mnAgendaMédico);
		
		JMenuItem mnAgendaExame = new JMenuItem("Agenda Exame");
		mnAgendaExame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaRelatorioAgendaExame();
			}
		});
		mnRelatorio.add(mnAgendaExame);
		
		JMenuItem mnHistoricoMedico = new JMenuItem("Histórico Paciente");
		mnHistoricoMedico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaRelatorioHistoricoPaciente();
			}
		});
		mnRelatorio.add(mnHistoricoMedico);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnExames = new JButton("Exames");
		btnExames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaMostrarExames();
			}
		});
		btnExames.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnExames.setBounds(36, 34, 226, 61);
		contentPane.add(btnExames);
		
		JButton btnConsultas = new JButton("Consultas");
		btnConsultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaMostrarConsultas();
			}
		});
		btnConsultas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnConsultas.setBounds(36, 137, 226, 61);
		contentPane.add(btnConsultas);
		
		JButton btnPacientes = new JButton("Pacientes");
		btnPacientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaMostrarPacientes();
			}
		});	
		btnPacientes.setBounds(36, 243, 110, 40);
		contentPane.add(btnPacientes);
		
		JButton btnMedicos = new JButton("Médicos");
		btnMedicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				abrirJanelaMostrarMedicos();
			}
		});
		btnMedicos.setBounds(158, 243, 104, 40);
		contentPane.add(btnMedicos);

	}
}
