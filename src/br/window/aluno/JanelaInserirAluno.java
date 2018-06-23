package br.window.aluno;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import br.dao.Datasource;
import br.dao.DAOAluno;
import br.dao.DAOCursos;
import br.model.Aluno;
import br.model.Curso;
import br.window.JanelaGerenciamentoAluno;

public class JanelaInserirAluno extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7889475273883412172L;
	private JFrame frame;
	private TextField textFieldNome = new TextField();
	private MaskFormatter mascaraTelefone;// Atributo formatador para telefone
	MaskFormatter mascaraCpf;
	MaskFormatter mascaraRG;
	Choice choiceDia = new Choice();
	Choice choiceMes = new Choice();
	Choice choiceAno = new Choice();
	int id, idaluno;
	Datasource ds;
	DAOAluno daoAluno;
	private Aluno BDAlunos = new Aluno();
	private JTable table;

	/**
	 * Launch the application.
	 */

	public static void run() {
		try {
			JanelaInserirAluno window = new JanelaInserirAluno();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public JanelaInserirAluno() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Inserir Aluno");
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		frame.setBounds((d.width / 2) - 300, (d.height / 2) - 250, 600, 453);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		// Choices-----------------------------------------------------------------------------------------------
		choiceDia.setBounds(160, 114, 60, 20);
		choiceDia.setEnabled(false);
		choiceMes.setEnabled(false);
		choiceAno.setEnabled(false);
		choiceMes.setBounds(224, 114, 60, 20);
		choiceAno.setBounds(290, 114, 60, 20);
		frame.getContentPane().add(choiceDia);
		frame.getContentPane().add(choiceMes);
		frame.getContentPane().add(choiceAno);

		JFormattedTextField textfieldFormattedCPF = new JFormattedTextField();
		textfieldFormattedCPF.setLocation(160, 88);
		textfieldFormattedCPF.setSize(206, 20);
		try {
			mascaraRG = new MaskFormatter("##.###.###-##");
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraTelefone = new MaskFormatter("(##)####-####");
			textfieldFormattedCPF.setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###.###.###-##")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		frame.getContentPane().add(textfieldFormattedCPF);
		mascaraTelefone.setValidCharacters("0123456789");
		mascaraCpf.setValidCharacters("0123456789");

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(365, 377, 89, 23);
		frame.getContentPane().add(btnLimpar);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(160, 377, 89, 23);
		frame.getContentPane().add(btnAdicionar);

		textFieldNome = new TextField();
		textFieldNome.setBounds(160, 37, 355, 20);
		frame.getContentPane().add(textFieldNome);

		JLabel lblNome = new JLabel("Nome Completo:");
		lblNome.setBounds(55, 37, 206, 14);
		frame.getContentPane().add(lblNome);

		JLabel lblRg = new JLabel("RG:");
		lblRg.setBounds(55, 63, 46, 14);
		frame.getContentPane().add(lblRg);

		JFormattedTextField textfieldFormattedRG = new JFormattedTextField(mascaraRG);
		textfieldFormattedRG.setBounds(160, 63, 250, 20);
		frame.getContentPane().add(textfieldFormattedRG);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(55, 88, 46, 14);
		frame.getContentPane().add(lblCpf);

		JLabel lblDataDeNascimento = new JLabel("Data de nascimento:");
		lblDataDeNascimento.setBounds(55, 114, 115, 14);
		frame.getContentPane().add(lblDataDeNascimento);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(201, 195, 225, 150);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setBounds(0, 0, 223, 1);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setModel(new DefaultTableModel(new Object[][] { { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, { null, null }, { null, null }, },
				new String[] { "ID", "Curso" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		updateTableCursos();
		scrollPane.setViewportView(table);

		textFieldNome.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent e) {
				if (textFieldNome.getText().length() != 0) {
					choiceDia.setEnabled(true);
					choiceMes.setEnabled(true);
					choiceAno.setEnabled(true);
				} else {
					choiceDia.setEnabled(false);
					choiceMes.setEnabled(false);
					choiceAno.setEnabled(false);
				}
			}
		});

		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ds = new Datasource();
				daoAluno = new DAOAluno(ds);
				Random R = new Random();
				idaluno = R.nextInt((200 - 100) + 1) + 100;

				try {
					BDAlunos.setId_aluno(idaluno);
					System.out.println(textfieldFormattedCPF.getText());
					BDAlunos.setNome(textFieldNome.getText());
					BDAlunos.setRg(textfieldFormattedRG.getText());
					BDAlunos.setCpf(textfieldFormattedCPF.getText());
					String idade = choiceAno.getSelectedItem().toString() + choiceMes.getSelectedItem().toString()
							+ choiceDia.getSelectedItem().toString();
					BDAlunos.setDataNascimento(idade);
					Object coluna = table.getValueAt(table.getSelectedRow(), 0);
					id = Integer.parseInt(coluna.toString());
					BDAlunos.setidCurso(id);
					daoAluno.criar(BDAlunos);
					JOptionPane.showMessageDialog(frame, "Adicionado!", "Menssagem", 1);
					textFieldNome.setText("");
					JanelaGerenciamentoAluno.atualizarTabelaAluno();
					updateChoiceIdade();
					close();
				} catch (Exception a) {
					JOptionPane.showMessageDialog(frame, "Preencha todos os campos", "Erro!", 2);
					System.err.println(a.getMessage());
				}
			}
		});

		updateChoiceIdade();
	}

	private void close() {
		frame.dispose();
	}

	private void updateChoiceIdade() {
		choiceDia.removeAll();
		choiceMes.removeAll();
		choiceAno.removeAll();
		for (int i = 0; i < 30; i++) {
			if (i < 9) {
				choiceDia.add(String.valueOf("0" + (i + 1)));
			} else {
				choiceDia.add(String.valueOf(i + 1));
			}
		}
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				choiceMes.add(String.valueOf("0" + (i + 1)));
			} else {
				choiceMes.add(String.valueOf(i + 1));
			}
		}
		Calendar cal = Calendar.getInstance();
		int anoatual = cal.get(Calendar.YEAR);
		for (int i = anoatual; i > 1900; i--) {
			choiceAno.add(String.valueOf(i));
		}
	}

	public void updateTableCursos() {
		ds = new Datasource();
		new DAOCursos(ds);
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setNumRows(0);
		for (Curso cs2 : DAOCursos.listarCursos()) {
			modelo.addRow(new Object[] { cs2.getIdCurso(), cs2.getNome() });
		}
	}
}
