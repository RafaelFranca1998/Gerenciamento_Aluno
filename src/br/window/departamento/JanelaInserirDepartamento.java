package br.window.departamento;

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import br.dao.DAOAluno;
import br.dao.DAODepartamento;
import br.dao.Datasource;
import br.model.Departamento;
import javax.swing.UIManager;
import java.awt.Color;

public class JanelaInserirDepartamento extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7889475273883412172L;
	private JFrame frmInserirDepartamento;
	private TextField textFieldNomedoDepartamento;
	int i;

	Datasource ds;
	DAODepartamento daoDepartamento;
	DAOAluno daoAluno;

	/**
	 * Launch the application.
	 */

	public static void run() {
		try {
			JanelaInserirDepartamento window = new JanelaInserirDepartamento();
			window.frmInserirDepartamento.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public JanelaInserirDepartamento() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInserirDepartamento = new JFrame("Inserir Aluno");
		frmInserirDepartamento.getContentPane().setBackground(Color.WHITE);
		frmInserirDepartamento.setTitle("Inserir Departamento");
		frmInserirDepartamento.setResizable(false);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		frmInserirDepartamento.setBounds((d.width / 2) - 250, (d.height / 2) - 155, 500, 310);
		frmInserirDepartamento.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmInserirDepartamento.getContentPane().setLayout(null);

		JButton btnFechar = new JButton("Fechar");
		btnFechar.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmInserirDepartamento.dispose();
			}
		});
		btnFechar.setBounds(336, 240, 90, 25);
		frmInserirDepartamento.getContentPane().add(btnFechar);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnAdicionar.setBounds(157, 240, 89, 23);
		frmInserirDepartamento.getContentPane().add(btnAdicionar);

		textFieldNomedoDepartamento = new TextField();
		textFieldNomedoDepartamento.setBounds(133, 44, 355, 20);
		frmInserirDepartamento.getContentPane().add(textFieldNomedoDepartamento);
		textFieldNomedoDepartamento.setColumns(10);

		JLabel lblNomedoDPT = new JLabel("Nome do Departamento");
		lblNomedoDPT.setBounds(10, 44, 206, 14);
		frmInserirDepartamento.getContentPane().add(lblNomedoDPT);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o");
		lblDescrio.setBounds(10, 121, 99, 14);
		frmInserirDepartamento.getContentPane().add(lblDescrio);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(133, 70, 355, 132);
		frmInserirDepartamento.getContentPane().add(textArea);
		
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ds =new Datasource();
				new DAODepartamento(ds);
				Departamento BDdepartamento = new Departamento();
				try {
					BDdepartamento.setNome(textFieldNomedoDepartamento.getText());
					BDdepartamento.setDescricao(textArea.getText());
					DAODepartamento.create(BDdepartamento);
					JOptionPane.showMessageDialog(frmInserirDepartamento, "Adicionado!", "Menssagem", 1);
					textFieldNomedoDepartamento.setText("");
					textArea.setText("");
					JanelaGerenciaDepartamento.initThread();
				} catch (Exception a) {
					JOptionPane.showMessageDialog(frmInserirDepartamento, "Preencha todos os campos", "Erro!", 2);
					System.err.println(a.getMessage());
				}
			}
		});

	}
}
