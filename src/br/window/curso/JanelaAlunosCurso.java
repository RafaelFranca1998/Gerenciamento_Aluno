
package br.window.curso;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.dao.Datasource;
import br.dao.DAOAluno;
import br.model.Aluno;
import javax.swing.UIManager;
import java.awt.Color;
	
/**
 * @author Rafael
 *	Janela
 */
public class JanelaAlunosCurso {
	JTable table;
	private JFrame frame;
	String titulo;
	static int idCurso;

	/**
	 * Launch the application.
	 * @param id do curso.
	 */

	public static void run(int id) {
		try {
			
			JanelaAlunosCurso window = new JanelaAlunosCurso(id);
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public JanelaAlunosCurso(int id) {
		initialize(id);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize(int id) {
		idCurso = id;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		table = new JTable();
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds((d.width/2)-225, (d.height/2)-150, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 200);
		frame.getContentPane().add(scrollPane);

		JButton btnFechar = new JButton("Fechar");
		btnFechar.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnFechar.setBounds(167, 227, 89, 23);
		frame.getContentPane().add(btnFechar);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
			},
			new String[] {
				"ID", "Nome do Aluno"
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
		table.getColumnModel().getColumn(0).setPreferredWidth(28);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(400);
		scrollPane.setViewportView(table);

		updateTableAlunoCurso();
	}
	private void updateTableAlunoCurso() {
		Datasource ds = new Datasource();
		new DAOAluno(ds);
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setNumRows(0);
		for (Aluno cs2 : DAOAluno.listarAlunosNoCurso(idCurso)) {
			modelo.addRow(new Object[] {((Aluno) cs2).getId_aluno(), ((Aluno) cs2).getNome() });
		}
	}

}
