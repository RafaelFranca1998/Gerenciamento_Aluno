package br.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import br.dao.Datasource;
import br.dao.DAOUsuario;

import java.awt.Component;
import javax.swing.JMenuBar;

public class JanelaPrincipalLogin {
	static boolean administrator = false;
	static JFrame frmLogin;
	static JLabel lblLoginOuSenha = new JLabel("Login ou senha Incorretos");
	private static JTextField textFieldLogin;
	private static JPasswordField passwordFieldSenha;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new JanelaPrincipalLogin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public JanelaPrincipalLogin() {
		loginAsADM();
	}
	public static boolean isAdministrator() {
		return administrator;
	}

	public static void loginAsADM() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		frmLogin = new JFrame();
		frmLogin.setForeground(Color.BLACK);
		frmLogin.setBackground(Color.WHITE);
		frmLogin.setResizable(false);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setBackground(Color.WHITE);
		frmLogin.setTitle("Login");
		frmLogin.setBounds((d.width/2)-175, (d.height/2)-150, 350, 300);
		lblLoginOuSenha.setBounds(112, 165, 162, 14);
		
		lblLoginOuSenha.setVisible(false);
		frmLogin.getContentPane().setLayout(null);
		final JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(24, 137, 46, 14);
		frmLogin.getContentPane().add(lblSenha);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(24, 94, 46, 14);
		frmLogin.getContentPane().add(lblLogin);
		
		textFieldLogin = new JTextField();
		textFieldLogin.setBounds(80, 91, 205, 20);
		textFieldLogin.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
				Datasource ds = new Datasource();
				DAOUsuario daoUsuario =  new DAOUsuario(ds);
				if (daoUsuario.checkLogin(textFieldLogin.getText(), passwordFieldSenha. getText())) {
					JOptionPane.showMessageDialog(lblSenha, "Sucesso", "Login", JOptionPane.INFORMATION_MESSAGE);
					JanelaGerenciamentoAluno.run();
					frmLogin.dispose();
				}else {
					lblLoginOuSenha.setVisible(true);
				}
				}
			}
		});
		frmLogin.getContentPane().add(textFieldLogin);
		textFieldLogin.setColumns(10);
		
		passwordFieldSenha = new JPasswordField();
		passwordFieldSenha.setBounds(80, 134, 205, 20);
		passwordFieldSenha.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
						if(e.getKeyCode() == KeyEvent.VK_ENTER){
							Datasource ds = new Datasource();
							DAOUsuario daoUsuario =  new DAOUsuario(ds);
							if (daoUsuario.checkLogin(textFieldLogin.getText(), passwordFieldSenha. getText())) {
								JOptionPane.showMessageDialog(lblSenha, "Sucesso", "Login", JOptionPane.INFORMATION_MESSAGE);
								JanelaGerenciamentoAluno.run();
								frmLogin.dispose();
							}else {
								lblLoginOuSenha.setVisible(true);
							}
						}
			}
		});
		frmLogin.getContentPane().add(passwordFieldSenha);
		passwordFieldSenha.setColumns(10);
		
		JButton btnLogar = new JButton("Logar");
		btnLogar.setBounds(59, 205, 89, 23);
		btnLogar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				Datasource ds = new Datasource();
				DAOUsuario daoUsuario =  new DAOUsuario(ds);
				if (daoUsuario.checkLogin(textFieldLogin.getText(), passwordFieldSenha. getText())) {
					JOptionPane.showMessageDialog(lblSenha, "Sucesso", "Login", JOptionPane.INFORMATION_MESSAGE);
					JanelaGerenciamentoAluno.run();
					frmLogin.dispose();
				}else {
					lblLoginOuSenha.setVisible(true);
				}
			}
		});
		frmLogin.getContentPane().add(btnLogar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(189, 205, 89, 23);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLogin.dispose();
			}
		});
		frmLogin.getContentPane().add(btnCancelar);
		
		
		lblLoginOuSenha.setForeground(Color.RED);
		btnLogar.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnCancelar.setBackground(UIManager.getColor("Button.disabledShadow"));
		frmLogin.getContentPane().add(lblLoginOuSenha);
		frmLogin.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblSenha, lblLogin, textFieldLogin, passwordFieldSenha, btnLogar, btnCancelar, lblLoginOuSenha}));
		
		JMenuBar menuBar = new JMenuBar();
		frmLogin.setJMenuBar(menuBar);
		frmLogin.setVisible(true);
	}
	
	public static void admLogout() {
		administrator = false;
	}
}
