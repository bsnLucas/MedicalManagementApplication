package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import entities.AgendaConsulta;
import entities.Medico;
import entities.Paciente;
import service.AgendaConsultaService;
import service.MedicoService;
import service.PacienteService;

public class AgendaConsultaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private MaskFormatter mascaraData;
	private MaskFormatter mascaraHorario;
	private MedicoService medicoService;
	private PacienteService pacienteService;
	private AgendaConsultaService agendaConsultaService;
	
	private JComboBox cbPaciente;
	private JComboBox cbMedico;
	private JComboBox cbStatus;
	private JLabel lblMedico;
	private JButton btnSearch2;
	private JLabel lblData;
	private JFormattedTextField txtData;
	
	private GestaoMedicaMenuGUI menuGui;
	private JLabel lblHorario;
	private JFormattedTextField txtHorario;
	private JButton btnLimpar;
	private JButton btnAplicar;

	public AgendaConsultaGUI(GestaoMedicaMenuGUI menuGui) {
		
		this.criarMascaras();
		
		this.agendaConsultaService = new AgendaConsultaService();
		this.pacienteService = new PacienteService();
		this.medicoService = new MedicoService();
		this.menuGui = menuGui;
		
		this.initComponents();
		this.buscarStatus();
	}
	
	public void cadastrar() {
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime localTime = LocalTime.parse(this.txtHorario.getText(), dtf);
			
			AgendaConsulta agendaConsulta = new AgendaConsulta();
			
			agendaConsulta.setPaciente((Paciente )this.cbPaciente.getSelectedItem());
			agendaConsulta.setMedico((Medico) this.cbMedico.getSelectedItem());
			agendaConsulta.setData(new java.sql.Date(sdf.parse(this.txtData.getText()).getTime()));
			agendaConsulta.setHorario(Time.valueOf(localTime));
			agendaConsulta.setStatus((String) this.cbStatus.getSelectedItem());
			
			if(this.agendaConsultaService.horarioDisponivel(agendaConsulta) == true) {
				
				this.agendaConsultaService.cadastrar(agendaConsulta);
				
			} else {
				
				JOptionPane.showMessageDialog(null, "Consulta já existente neste horário!", "Consulta Erro Horário Ocupado", JOptionPane.ERROR_MESSAGE);
			}
			
			fecharJanela();
		
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao agendar consulta", "Erro Agendar Consulta", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void limparCampos() {
		
		this.txtData.setText("");
		this.txtHorario.setText("");
		this.cbMedico.setSelectedIndex(0);
		this.cbPaciente.setSelectedIndex(0);
	}
	
	public void criarMascaras() {
		
		try {
			
			this.mascaraData = new MaskFormatter("##/##/####");
			this.mascaraHorario = new MaskFormatter("##:##");
			
		} catch(ParseException e) {
			
			System.err.println(e.getMessage());
		}
	}
	
	public void buscarPaciente() {
		
		try {
			
			String cpf = JOptionPane.showInputDialog(null, "Insira o CPF do paciente", "Buscar Paciente", JOptionPane.OK_CANCEL_OPTION);

			List<Paciente> listaPacientes = this.pacienteService.buscarTodos();
			
			for(Paciente paciente : listaPacientes) {
				
				if(paciente.getCpf().equals(cpf)) {
					
					this.cbPaciente.addItem(paciente);
					return;
				}
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao buscar paciente", "Erro Buscar Paciente", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void buscarMedico() {
		
		try {
			
			int crm = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o CRM do médico", "Buscar Médico", JOptionPane.OK_CANCEL_OPTION));
			
			List<Medico> listaMedicos = this.medicoService.buscarTodos();
			
			for(Medico medico : listaMedicos) {
				
				if(medico.getCrm() == crm) {
					
					this.cbMedico.addItem(medico);
					return;
				}
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao buscar medico", "Erro Buscar Medico", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void buscarStatus() {
		
		this.cbStatus.addItem("Pendente");
		this.cbStatus.addItem("Realizado");
	}
	
	
	public void fecharJanela() {
		
		this.dispose();
		this.menuGui.setVisible(true);
	}

	public void initComponents() {
		setTitle("Agendamento de Consulta");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 465, 305);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPaciente = new JLabel("Paciente");
		lblPaciente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPaciente.setBounds(36, 32, 55, 14);
		contentPane.add(lblPaciente);
		
		cbPaciente = new JComboBox();
		cbPaciente.setBounds(99, 29, 223, 22);
		contentPane.add(cbPaciente);
		
		JButton btnSearch = new JButton("Buscar");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				buscarPaciente();
			}
		});
		btnSearch.setBounds(332, 29, 90, 23);
		contentPane.add(btnSearch);
		
		lblMedico = new JLabel("Médico");
		lblMedico.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMedico.setBounds(36, 76, 46, 14);
		contentPane.add(lblMedico);
		
		cbMedico = new JComboBox();
		cbMedico.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbMedico.setBounds(99, 73, 223, 22);
		contentPane.add(cbMedico);
		
		btnSearch2 = new JButton("Buscar");
		btnSearch2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				buscarMedico();
			}
		});
		btnSearch2.setBounds(332, 73, 90, 23);
		contentPane.add(btnSearch2);
		
		lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblData.setBounds(36, 121, 46, 14);
		contentPane.add(lblData);
		
		txtData = new JFormattedTextField(mascaraData);
		txtData.setBounds(99, 119, 74, 20);
		contentPane.add(txtData);
		
		lblHorario = new JLabel("Horário");
		lblHorario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblHorario.setBounds(203, 122, 46, 14);
		contentPane.add(lblHorario);
		
		txtHorario = new JFormattedTextField(mascaraHorario);
		txtHorario.setBounds(269, 119, 63, 20);
		contentPane.add(txtHorario);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limparCampos();
			}
		});
		btnLimpar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLimpar.setBounds(118, 216, 99, 40);
		contentPane.add(btnLimpar);
		
		btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cadastrar();
			}
		});
		btnAplicar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAplicar.setBounds(233, 216, 99, 40);
		contentPane.add(btnAplicar);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStatus.setBounds(36, 169, 43, 13);
		contentPane.add(lblStatus);
		
		cbStatus = new JComboBox();
		cbStatus.setBounds(99, 166, 90, 21);
		contentPane.add(cbStatus);

	}
}
