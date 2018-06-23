package br.window.aluno;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

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

import br.dao.DAOAluno;
import br.dao.DAOCursos;
import br.dao.Datasource;
import br.model.Aluno;
import br.model.Curso;
import br.window.JanelaGerenciamentoAluno;

public class JanelaEditarAluno extends JFrame {
	private static final long serialVersionUID = 7889475273883412172L;
	private JFrame frame;
	private TextField textFieldNome = new TextField();
	private MaskFormatter mascaraTelefone;// Atributo formatador para telefone
	private MaskFormatter mascaraCpf;
	private MaskFormatter mascaraRG;
	private JFormattedTextField textfieldFormattedRG;
	private JFormattedTextField textfieldFormattedCPF;
	private Choice choiceDia = new Choice();
	private Choice choiceMes = new Choice();
	private Choice choiceAno = new Choice();
	static int id, idaluno;
	Datasource ds;
	DAOAluno daoAluno;
	private Aluno BDAlunos;
	private JTable table;

	/**
	 * Launch the application.
	 */

	public static void run(int id) {
		try {
			idaluno = id;
			JanelaEditarAluno window = new JanelaEditarAluno();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public JanelaEditarAluno() {
		BDAlunos = new Aluno();
		initialize();
		preencherCampos();
		atualizarTabelaCurso();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Editar Aluno");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		frame.setBounds((d.width / 2) - 300, (d.height / 2) - 250, 600, 454);
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

		textfieldFormattedCPF = new JFormattedTextField();
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

		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnFechar.setBounds(380, 376, 89, 23);
		frame.getContentPane().add(btnFechar);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(195, 376, 89, 23);
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

		textfieldFormattedRG = new JFormattedTextField(mascaraRG);
		textfieldFormattedRG.setBounds(160, 63, 250, 20);
		frame.getContentPane().add(textfieldFormattedRG);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(55, 88, 46, 14);
		frame.getContentPane().add(lblCpf);

		JLabel lblDataDeNascimento = new JLabel("Data de nascimento:");
		lblDataDeNascimento.setBounds(55, 114, 115, 14);
		frame.getContentPane().add(lblDataDeNascimento);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(218, 204, 225, 150);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object coluna = table.getValueAt(table.getSelectedRow(), 0);
				id = Integer.parseInt(coluna.toString());
			}
		});
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setBounds(0, 0, 223, 1);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "ID", "Curso" }) {
			private static final long serialVersionUID = 1L;
			boolean[] canEdit = new boolean[] { false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
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
				
				try {
					BDAlunos.setId_aluno(idaluno);
					BDAlunos.setNome(textFieldNome.getText());
					BDAlunos.setRg(textfieldFormattedRG.getText());
					BDAlunos.setCpf(textfieldFormattedCPF.getText());
					String idade = choiceAno.getSelectedItem().toString() + choiceMes.getSelectedItem().toString()
							+ choiceDia.getSelectedItem().toString();
					BDAlunos.setDataNascimento(idade);
					Object coluna = table.getValueAt(table.getSelectedRow(), 0);
					BDAlunos.setidCurso(Integer.parseInt(coluna.toString()));
					daoAluno.atualizar(BDAlunos);
					JOptionPane.showMessageDialog(frame, "Atualizado!", "Menssagem", 1);
					textFieldNome.setText("");
					JanelaGerenciamentoAluno.atualizarTabelaAluno();
					frame.dispose();
				} catch (Exception a) {
					JOptionPane.showMessageDialog(frame, "Preencha todos os campos", "Erro!", 2);
					System.err.println(a.getMessage());
				}

			}
		});
		updateChoiceIdade();
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

	public void atualizarTabelaCurso() {
		ds = new Datasource();
		new DAOCursos(ds);
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setNumRows(0);
		for (Curso cs2 : DAOCursos.listarCursos()) {
			modelo.addRow(new Object[] { cs2.getIdCurso(), cs2.getNome() });
		}
	}

	private void preencherCampos() {
		Datasource ds2 = new Datasource();
		DAOAluno daoAluno2 = new DAOAluno(ds2);
		@SuppressWarnings("rawtypes")
		List alunoList = daoAluno2.selecionarId(idaluno);
		Aluno aluno = (Aluno) alunoList.get(0);
		textFieldNome.setText(aluno.getNome());
		textfieldFormattedRG.setText(aluno.getRg());
		textfieldFormattedCPF.setText(aluno.getCpf());

	}
}
