package br.window.curso;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import br.dao.DAOCursos;
import br.dao.Datasource;
import br.model.Curso;

public class JanelaGerenciaCurso {

	private JFrame frameCurso = new JFrame();
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnExibirAlunosDo = new JButton("Exibir Alunos do Curso");
	private JButton btnDisciplinasObrigatrias= new JButton("Disciplinas Obrigat\u00F3rias");;
	private JButton btnDisciplinasOptativas = new JButton("Disciplinas Optativas");
	private final JButton btnAdicionarDisciplinas = new JButton("Adicionar Disciplinas");
	JButton btnAdicionarCurso = new JButton("Adicionar Curso");
	static Atualiza A;

	/**
	 * Launch the application.
	 */
	public static void run() {
		try {
			JanelaGerenciaCurso window =  new JanelaGerenciaCurso();
			window.frameCurso.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public JanelaGerenciaCurso() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		table = new JTable();
		scrollPane = new JScrollPane();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		frameCurso.getContentPane().setBackground(Color.WHITE);
		frameCurso.setTitle("Gerenciar Cursos");
		frameCurso.setBounds((d.width/2)-300, (d.height/2)-200, 600, 461);
		frameCurso.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameCurso.setResizable(false);
		frameCurso.getContentPane().setLayout(null);
		frameCurso.getContentPane().add(scrollPane);

		scrollPane.setBounds(0, 0, 594, 289);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(0, 0, 439, 87);

		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"ID", "Curso"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(554);
		scrollPane.setViewportView(table);
		
		btnExibirAlunosDo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object coluna = table.getValueAt(table.getSelectedRow(), 0);
				int idCurso = Integer.parseInt(coluna.toString());
				JanelaAlunosCurso.run(idCurso);
			}
		});
		btnExibirAlunosDo.setBounds(399, 379, 175, 23);
		frameCurso.getContentPane().add(btnExibirAlunosDo);
		btnDisciplinasObrigatrias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object coluna = table.getValueAt(table.getSelectedRow(), 0);
				int idCurso = Integer.parseInt(coluna.toString());
				JanelaDisciplinasObrigatorias.run(idCurso);
			}
		});
 
		btnDisciplinasObrigatrias.setBounds(201, 379, 188, 23);
		frameCurso.getContentPane().add(btnDisciplinasObrigatrias);

		
		btnDisciplinasOptativas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object coluna = table.getValueAt(table.getSelectedRow(), 0);
				int idCurso = Integer.parseInt(coluna.toString());
				JanelaDisciplinasOptativas.run(idCurso);
			}
		});
		btnDisciplinasOptativas.setBounds(22, 379, 169, 23);
		btnDisciplinasOptativas.setBackground(UIManager.getColor("Button.disabledShadow"));
		frameCurso.getContentPane().add(btnDisciplinasOptativas);
		
		btnDisciplinasObrigatrias.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnAdicionarDisciplinas.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnExibirAlunosDo.setBackground(UIManager.getColor("Button.disabledShadow"));
		
		
		btnAdicionarCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JanelaInserirCurso.run();
			}
		});
		btnAdicionarCurso.setBounds(22, 334, 169, 23);
		frameCurso.getContentPane().add(btnAdicionarCurso);
		btnAdicionarDisciplinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JanelaInserirDisciplina.run();
			}
		});
		btnAdicionarDisciplinas.setBounds(204, 334, 185, 23);
		
		frameCurso.getContentPane().add(btnAdicionarDisciplinas);

		btnAdicionarCurso.setBackground(UIManager.getColor("Button.disabledShadow"));
		
		JButton btnRemoverCurso = new JButton("Remover Curso");
		btnRemoverCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Datasource ds = new Datasource();
				new DAOCursos(ds);
				Curso curso =  new Curso();
				Object coluna = table.getValueAt(table.getSelectedRow(), 0);
				if (JOptionPane.showConfirmDialog(table,
						"Esta Ação não poderá ser desfeita! \n Deseja remover o Curso?", "Atenção!",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					int id = Integer.parseInt(coluna.toString());

					System.out.println("[Log] ID= " + id + JOptionPane.YES_OPTION);
					curso.setIdCurso(id);
					DAOCursos.deleteCurso(curso);
					initThreadAtualiza();
				}
			}
		});
		btnRemoverCurso.setBounds(399, 334, 175, 23);
		frameCurso.getContentPane().add(btnRemoverCurso);
		btnRemoverCurso.setBackground(UIManager.getColor("Button.disabledShadow"));

		A =  new Atualiza();
		initThreadAtualiza();
	}

	public static void initThreadAtualiza() {
		Thread threadA = new Thread(A);
		threadA.start();
	}
	
	public class Atualiza implements Runnable {
	    public void run () {
	    		Datasource ds = new Datasource();
	    		new DAOCursos(ds);
	    		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
	    		modelo.setNumRows(0);
	    		for (Curso BD2 : DAOCursos.listarCursos()) {
	    			modelo.addRow(new Object[] { BD2.getIdCurso(), BD2.getNome(), BD2.getIdDepartamento()});
	    		}
	    }
	}
}
