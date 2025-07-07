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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import entities.AgendaExame;
import entities.Exame;
import entities.Medico;
import entities.Paciente;
import service.AgendaExameService;
import service.ExameService;
import service.MedicoService;
import service.PacienteService;

public class AgendarExameGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox cbPaciente;
	private JComboBox cbExame;
	private JComboBox cbMedico;
	private JComboBox cbStatus;
	private JTextField txtPreco;
	private JFormattedTextField txtData;
	private JLabel lblHorario;
	private JFormattedTextField txtHorario;
	
	private MaskFormatter mascaraData;
	private MaskFormatter mascaraHorario;
	
	private MedicoService medicoService;
	private PacienteService pacienteService;
	private ExameService exameService;
	private GestaoMedicaMenuGUI menuGui;
	private AgendaExameService agendaExameService;
	
	private JLabel lblMedico;
	private JButton btnSearch2;
	private JLabel lblExame;
	private JLabel lblData;
	private JButton btnAplicar;
	private JButton btnLimpar;

	public AgendarExameGUI(GestaoMedicaMenuGUI menuGui) {
		
		this.criarMascaras();
		
		this.menuGui = menuGui;
		this.pacienteService = new PacienteService();
		this.medicoService = new MedicoService();
		this.exameService = new ExameService();
		this.agendaExameService = new AgendaExameService();
		
		this.initComponents();
		this.buscarExame();
		this.buscarStatus();
	}
	
	public void cadastrar() {
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime localTime = LocalTime.parse(this.txtHorario.getText(), dtf);
			AgendaExame agendaExame = new AgendaExame();
			
			agendaExame.setPaciente((Paciente )this.cbPaciente.getSelectedItem());
			agendaExame.setMedico((Medico) this.cbMedico.getSelectedItem());
			agendaExame.setExame((Exame) this.cbExame.getSelectedItem());
			agendaExame.setData(new java.sql.Date(sdf.parse(this.txtData.getText()).getTime()));
			agendaExame.setHorario(Time.valueOf(localTime));
			agendaExame.setPreco(Integer.parseInt(this.txtPreco.getText()));
			agendaExame.setStatus((String) this.cbStatus.getSelectedItem());
			
			if(this.agendaExameService.horarioDisponivel(agendaExame) == true) {
				
				this.agendaExameService.cadastrar(agendaExame);
				
			} else {
				
				JOptionPane.showMessageDialog(null, "Exame já existente neste horário!", "ExameErro Horário Ocupado", JOptionPane.ERROR_MESSAGE);
			}
			
			fecharJanela();
		
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao agendar exame", "Erro Agendar Exame", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void fecharJanela() {
		
		this.dispose();
		this.menuGui.setVisible(true);
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
	
	public void buscarExame() {
		
		try {
			
			List<Exame> listaExames = this.exameService.buscarTodos();
			
			for(Exame exame : listaExames) {
				
				this.cbExame.addItem(exame);
			}
		
		} catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao buscar exames", "Erro Buscar Exames", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void buscarStatus() {
		
		this.cbStatus.addItem("Pendente");
		this.cbStatus.addItem("Realizado");
	}
	
	public void limparCampos() {
		
		this.txtData.setText("");
		this.txtPreco.setText("");
		this.cbExame.setSelectedIndex(0);
		this.cbMedico.setSelectedIndex(0);
		this.cbPaciente.setSelectedIndex(0);
		this.cbStatus.setSelectedIndex(0);
	}
	
	public void initComponents() {
		setTitle("Agendamento de Exame");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 488, 288);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPaciente = new JLabel("Paciente");
		lblPaciente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPaciente.setBounds(36, 32, 55, 14);
		contentPane.add(lblPaciente);
		
		cbPaciente = new JComboBox();
		cbPaciente.setBounds(99, 29, 264, 22);
		contentPane.add(cbPaciente);
		
		JButton btnSearch = new JButton("Buscar");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				buscarPaciente();
			}
		});
		btnSearch.setBounds(373, 29, 73, 23);
		contentPane.add(btnSearch);
		
		lblMedico = new JLabel("Médico");
		lblMedico.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMedico.setBounds(36, 76, 46, 14);
		contentPane.add(lblMedico);
		
		cbMedico = new JComboBox();
		cbMedico.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbMedico.setBounds(99, 73, 264, 22);
		contentPane.add(cbMedico);
		
		btnSearch2 = new JButton("Buscar");
		btnSearch2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				buscarMedico();
			}
		});
		btnSearch2.setBounds(373, 73, 73, 23);
		contentPane.add(btnSearch2);
		
		lblExame = new JLabel("Exame");
		lblExame.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblExame.setBounds(36, 120, 46, 14);
		contentPane.add(lblExame);
		
		cbExame = new JComboBox();
		cbExame.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbExame.setBounds(93, 116, 156, 22);
		contentPane.add(cbExame);
		
		lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblData.setBounds(36, 166, 46, 14);
		contentPane.add(lblData);
		
		txtData = new JFormattedTextField(mascaraData);
		txtData.setBounds(76, 164, 90, 20);
		contentPane.add(txtData);
		
		lblHorario = new JLabel("Horário");
		lblHorario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblHorario.setBounds(179, 166, 46, 14);
		contentPane.add(lblHorario);
		
		txtHorario = new JFormattedTextField(mascaraHorario);
		txtHorario.setBounds(235, 164, 63, 20);
		contentPane.add(txtHorario);
		
		JLabel lblPreco = new JLabel("Preço R$");
		lblPreco.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPreco.setBounds(308, 166, 55, 14);
		contentPane.add(lblPreco);
		
		txtPreco = new JTextField();
		txtPreco.setBounds(373, 164, 73, 20);
		contentPane.add(txtPreco);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStatus.setBounds(276, 121, 46, 14);
		contentPane.add(lblStatus);
		
		cbStatus = new JComboBox();
		cbStatus.setBounds(332, 117, 114, 22);
		contentPane.add(cbStatus);
		
		btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cadastrar();
			}
		});
		btnAplicar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAplicar.setBounds(242, 205, 96, 33);
		contentPane.add(btnAplicar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limparCampos();
			}
		});
		btnLimpar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLimpar.setBounds(136, 205, 96, 33);
		contentPane.add(btnLimpar);

	}
}
