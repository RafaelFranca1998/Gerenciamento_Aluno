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
import br.dao.DAOCursos;
import br.model.Disciplinas;

public class JanelaDisciplinasObrigatorias {

	JTable table = new JTable();
	private JFrame frmDisciplinasObrigatrias;
	

	/**
	 * Launch the application.
	 */

	public static void run(int id) {
		try {
			JanelaDisciplinasObrigatorias window = new JanelaDisciplinasObrigatorias(id);
			window.frmDisciplinasObrigatrias.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public JanelaDisciplinasObrigatorias(int id) {
		initialize(id);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int id) {
		int idCurso =  id;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		frmDisciplinasObrigatrias = new JFrame();
		frmDisciplinasObrigatrias.setTitle("Disciplinas Obrigat\u00F3rias");
		frmDisciplinasObrigatrias.setBounds((d.width/2)-225, (d.height/2)-150, 450, 300);
		frmDisciplinasObrigatrias.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmDisciplinasObrigatrias.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 200);
		frmDisciplinasObrigatrias.getContentPane().add(scrollPane);

		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setBounds(0, 0, 439, 87);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
			},
			new String[] {
				"Disciplina Obrigat\u00F3ria"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(199);

		updateTableAlunoCurso(idCurso);
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmDisciplinasObrigatrias.dispose();
			}
		});
		btnFechar.setBounds(180, 227, 89, 23);
		frmDisciplinasObrigatrias.getContentPane().add(btnFechar);
	}

	private void updateTableAlunoCurso(int idCurso) {
		Datasource ds = new Datasource();
		new DAOCursos(ds);
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setNumRows(0);
		for (Object cs2 : DAOCursos.listarDisciplinasObrigatorias(idCurso)) {
			modelo.addRow(new Object[] {((Disciplinas) cs2).getNome() });
		}
	}
}
