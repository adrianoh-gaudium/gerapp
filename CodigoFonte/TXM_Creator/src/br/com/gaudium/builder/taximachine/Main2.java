package br.com.gaudium.builder.taximachine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Taskbar;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

//import com.apple.eawt.Application;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

// Não usar, é só pra testes.
public class Main2 {

	private JFrame frmTXMCreator;
	private DataObj data = new DataObj();

	private JTextField txtDadosApps;
	private JTextField txtProjsAndamento;
	private JTextField txtAndroidCustom;
	private JTextField txtArqBase;
	
	private JTextField txtImagens;
	private JTextField txtDados;
	private JTextField txtMarketing;
	private JTextField txtIOSCustom;
	private JTextField txtProjNumbers;
	private JTextField txtScriptPath;
	private JTextField txtServidorCliente;
	private JTextField txtURLCliente;
	private JTextField txtNomeCliente;
	private JTextField txtEmailLoginCliente;
	private JTextField txtSenhaCliente;
	private JTextField txtURLIcones;
	private JTextField txtPinTaxistaLivre;
	private JTextField txtPinTaxistaOcupado;
	private JTextField txtPinTaxistaConfirmado;
	private JTextField txtPinMotoTaxistaLivre;
	private JTextField txtPinMotoTaxistaOcupado;
	private JTextField txtPinMotoTaxistaConfirmado;
	private JTextField txtPinMotoristaLivre;
	private JTextField txtPinMotoristaOcupado;
	private JTextField txtPinMotoristaConfirmado;
	private JTextField txtPinLocalizacao;
	private JTextField txtPinPassageiro;
	private JTextField txtSecretDir;
	
	private JCheckBox chkPackage, chkMarketing, chkMeta, chkAndroid, chkIOS;
	private JTextField txtContaFastLane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Taskbar.getTaskbar().setIconImage(Toolkit.getDefaultToolkit().getImage(Main2.class.getResource("/resources/machine-branco.png")));
		} catch(Exception e) {}
//		Application.getApplication().setDockIconImage(
//				Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/resources/machine-branco.png")));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main2 window = new Main2();
					window.frmTXMCreator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int frameWidth = 800, frameHeight = 740;

		frmTXMCreator = new JFrame();
		frmTXMCreator.setIconImage(
				Toolkit.getDefaultToolkit().getImage(Main2.class.getResource("/resources/machine-branco.png")));
		frmTXMCreator.setTitle("Machine Generator");
		frmTXMCreator.setBounds((dim.width-frameWidth)/2, (dim.height-frameHeight)/2, frameWidth, frameHeight);
		frmTXMCreator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTXMCreator.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 800, 720);
		frmTXMCreator.getContentPane().add(tabbedPane);
		
		JPanel tabProjeto = new JPanel();
		tabbedPane.addTab("Projetos", null, tabProjeto, null);
		tabProjeto.setLayout(null);

		JPanel tabConfig = new JPanel();
		tabbedPane.addTab("Configuração", null, tabConfig, null);
		tabConfig.setLayout(null);
		
		JLabel lblServidorParaCadastrar = new JLabel("Cadastro de cliente de teste");
		lblServidorParaCadastrar.setOpaque(true);
		lblServidorParaCadastrar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblServidorParaCadastrar.setBounds(18, 322, 163, 14);
		tabConfig.add(lblServidorParaCadastrar);
		
		JLabel lblSubdirectoryName = new JLabel("Subdiretórios dentro da pasta do projeto");
		lblSubdirectoryName.setOpaque(true);
		lblSubdirectoryName.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSubdirectoryName.setBounds(18, 245, 238, 14);
		tabConfig.add(lblSubdirectoryName);
		
		JLabel lblFolders = new JLabel("Pastas");
		lblFolders.setOpaque(true);
		lblFolders.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblFolders.setBounds(18, 0, 41, 14);
		tabConfig.add(lblFolders);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(6, 6, 768, 227);
		tabConfig.add(panel);
		
		JLabel lblDadosApps = new JLabel("Dados Apps");
		lblDadosApps.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDadosApps.setOpaque(true);
		lblDadosApps.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblDadosApps.setBounds(18, 18, 98, 14);
		panel.add(lblDadosApps);
		
		JLabel lblProjetos = new JLabel("Projs Andamento");
		lblProjetos.setOpaque(true);
		lblProjetos.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblProjetos.setBounds(18, 44, 109, 14);
		panel.add(lblProjetos);
		
		JButton btnDadosApps = new JButton("");
		btnDadosApps.setBounds(727, 15, 35, 23);
		btnDadosApps.setIcon(new ImageIcon(Main2.class.getResource("/resources/folder.png")));
		btnDadosApps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = pathChooser();
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtDadosApps.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}				

		});
		panel.add(btnDadosApps);
		
		txtDadosApps = new JTextField();
		txtDadosApps.setText("/Users/integrador/DadosApp/");
		txtDadosApps.setColumns(10);
		txtDadosApps.setBounds(129, 16, 600, 20);
		panel.add(txtDadosApps);
		
		JLabel lblAndroid = new JLabel("Android Custom");
		lblAndroid.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAndroid.setOpaque(true);
		lblAndroid.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblAndroid.setBounds(18, 70, 98, 14);
		panel.add(lblAndroid);
		
		JLabel lblBasePath = new JLabel("Arquivos Base");
		lblBasePath.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBasePath.setOpaque(true);
		lblBasePath.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblBasePath.setBounds(18, 123, 98, 14);
		panel.add(lblBasePath);
		
		JButton btnProjsAndamento = new JButton("");
		btnProjsAndamento.setBounds(727, 40, 35, 23);
		btnProjsAndamento.setIcon(new ImageIcon(Main2.class.getResource("/resources/folder.png")));
		btnProjsAndamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = pathChooser();
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtProjsAndamento.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}				

		});
		panel.add(btnProjsAndamento);
		
		txtProjsAndamento = new JTextField();
		txtProjsAndamento.setText("/Users/integrador/ProjetosAndamento/");
		txtProjsAndamento.setColumns(10);
		txtProjsAndamento.setBounds(129, 42, 600, 20);
		panel.add(txtProjsAndamento);
		
		txtAndroidCustom = new JTextField();
		txtAndroidCustom.setText("/Users/integrador/AndroidCustom/");
		txtAndroidCustom.setColumns(10);
		txtAndroidCustom.setBounds(129, 68, 600, 20);
		panel.add(txtAndroidCustom);
		
		JButton btnAndroidCustom = new JButton("");
		btnAndroidCustom.setBounds(727, 66, 35, 23);
		btnAndroidCustom.setIcon(new ImageIcon(Main2.class.getResource("/resources/folder.png")));
		btnAndroidCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = pathChooser();
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtAndroidCustom.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}				

		});
		panel.add(btnAndroidCustom);
		
		txtArqBase = new JTextField();
		txtArqBase.setText("/Users/colaborador1/Dropbox (Gaudium)/Operacao/Projetos andamento/Proj076-TaxiMachine/Implantação/Novas bandeiras/modules/arquivos-base");
		txtArqBase.setColumns(10);
		txtArqBase.setBounds(129, 120, 600, 20);
		panel.add(txtArqBase);
		
		JButton btnIOSCustom = new JButton("");
		btnIOSCustom.setBounds(727, 92, 35, 23);
		btnIOSCustom.setIcon(new ImageIcon(Main2.class.getResource("/resources/folder.png")));
		btnIOSCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = pathChooser();
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtIOSCustom.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}				

		});
		panel.add(btnIOSCustom);
		
		JButton btnArqsBase = new JButton("");
		btnArqsBase.setIcon(new ImageIcon(Main2.class.getResource("/resources/folder.png")));
		btnArqsBase.setBounds(727, 118, 35, 23);
		btnArqsBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = pathChooser();
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtArqBase.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}				

		});
		panel.add(btnArqsBase);
		
		JLabel lblIosCustom = new JLabel("iOS Custom");
		lblIosCustom.setOpaque(true);
		lblIosCustom.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIosCustom.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblIosCustom.setBounds(18, 97, 98, 14);
		panel.add(lblIosCustom);
		
		txtIOSCustom = new JTextField();
		txtIOSCustom.setColumns(10);
		txtIOSCustom.setBounds(129, 94, 600, 20);
		panel.add(txtIOSCustom);
		
		JLabel lblScripts = new JLabel("Scripts");
		lblScripts.setOpaque(true);
		lblScripts.setHorizontalAlignment(SwingConstants.RIGHT);
		lblScripts.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblScripts.setBounds(18, 149, 98, 14);
		panel.add(lblScripts);
		
		txtScriptPath = new JTextField();
		txtScriptPath.setColumns(10);
		txtScriptPath.setBounds(129, 146, 600, 20);
		panel.add(txtScriptPath);
		
		JButton btnScripts = new JButton("");
		btnScripts.setIcon(new ImageIcon(Main2.class.getResource("/resources/folder.png")));
		btnScripts.setBounds(727, 143, 35, 23);
		btnScripts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = pathChooser();
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtScriptPath.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}				

		});
		panel.add(btnScripts);
		
		JLabel lblSecretDir = new JLabel("Secret JSON Path");
		lblSecretDir.setOpaque(true);
		lblSecretDir.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSecretDir.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSecretDir.setBounds(18, 175, 98, 14);
		panel.add(lblSecretDir);
		
		txtSecretDir = new JTextField();
		txtSecretDir.setText((String) null);
		txtSecretDir.setColumns(10);
		txtSecretDir.setBounds(129, 171, 600, 20);
		panel.add(txtSecretDir);
		
		JButton btnSecret = new JButton("");
		btnSecret.setIcon(new ImageIcon(Main2.class.getResource("/resources/folder.png")));
		btnSecret.setBounds(727, 168, 35, 23);
		btnSecret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = pathChooser();
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtSecretDir.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}				

		});
		panel.add(btnSecret);
		
		JPanel panel_11 = new JPanel();
		panel_11.setLayout(null);
		panel_11.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_11.setBounds(6, 253, 768, 57);
		tabConfig.add(panel_11);
		
		txtImagens = new JTextField();
		txtImagens.setText("Design");
		txtImagens.setColumns(10);
		txtImagens.setBounds(80, 16, 134, 28);
		panel_11.add(txtImagens);
		
		JLabel lblImagens = new JLabel("Imagens");
		lblImagens.setOpaque(true);
		lblImagens.setHorizontalAlignment(SwingConstants.LEFT);
		lblImagens.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblImagens.setBounds(22, 22, 50, 14);
		panel_11.add(lblImagens);
		
		JLabel lblDados = new JLabel("Dados");
		lblDados.setOpaque(true);
		lblDados.setHorizontalAlignment(SwingConstants.LEFT);
		lblDados.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblDados.setBounds(260, 22, 50, 14);
		panel_11.add(lblDados);
		
		txtDados = new JTextField();
		txtDados.setText("Miscelâneas");
		txtDados.setColumns(10);
		txtDados.setBounds(310, 16, 134, 28);
		panel_11.add(txtDados);
		
		JLabel lblMarketing = new JLabel("Marketing");
		lblMarketing.setOpaque(true);
		lblMarketing.setHorizontalAlignment(SwingConstants.LEFT);
		lblMarketing.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblMarketing.setBounds(504, 22, 70, 14);
		panel_11.add(lblMarketing);
		
		txtMarketing = new JTextField();
		txtMarketing.setText("Marketing & Vendas");
		txtMarketing.setColumns(10);
		txtMarketing.setBounds(580, 16, 140, 28);
		panel_11.add(txtMarketing);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(6, 330, 768, 91);
		tabConfig.add(panel_4);
		
		txtServidorCliente = new JTextField();
		txtServidorCliente.setColumns(10);
		txtServidorCliente.setBounds(80, 16, 352, 28);
		panel_4.add(txtServidorCliente);
		
		JLabel lblServidor = new JLabel("Servidor");
		lblServidor.setOpaque(true);
		lblServidor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblServidor.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblServidor.setBounds(22, 22, 50, 14);
		panel_4.add(lblServidor);
		
		JLabel lblApi = new JLabel("URL ");
		lblApi.setOpaque(true);
		lblApi.setHorizontalAlignment(SwingConstants.LEFT);
		lblApi.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblApi.setBounds(444, 23, 26, 14);
		panel_4.add(lblApi);
		
		txtURLCliente = new JTextField();
		txtURLCliente.setColumns(10);
		txtURLCliente.setBounds(476, 16, 190, 28);
		panel_4.add(txtURLCliente);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setOpaque(true);
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNome.setBounds(22, 48, 50, 14);
		panel_4.add(lblNome);
		
		JLabel lblEmaillogin = new JLabel("Email/Login");
		lblEmaillogin.setOpaque(true);
		lblEmaillogin.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmaillogin.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblEmaillogin.setBounds(264, 47, 71, 14);
		panel_4.add(lblEmaillogin);
		
		txtNomeCliente = new JTextField();
		txtNomeCliente.setText((String) null);
		txtNomeCliente.setColumns(10);
		txtNomeCliente.setBounds(80, 41, 167, 28);
		panel_4.add(txtNomeCliente);
		
		txtEmailLoginCliente = new JTextField();
		txtEmailLoginCliente.setText((String) null);
		txtEmailLoginCliente.setColumns(10);
		txtEmailLoginCliente.setBounds(337, 41, 167, 28);
		panel_4.add(txtEmailLoginCliente);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setOpaque(true);
		lblSenha.setHorizontalAlignment(SwingConstants.LEFT);
		lblSenha.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSenha.setBounds(516, 47, 42, 14);
		panel_4.add(lblSenha);
		
		txtSenhaCliente = new JTextField();
		txtSenhaCliente.setText((String) null);
		txtSenhaCliente.setColumns(10);
		txtSenhaCliente.setBounds(556, 41, 110, 28);
		panel_4.add(txtSenhaCliente);
		
		JLabel lblPinosDoMapa = new JLabel("Pinos do mapa");
		lblPinosDoMapa.setOpaque(true);
		lblPinosDoMapa.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPinosDoMapa.setBounds(18, 430, 86, 14);
		tabConfig.add(lblPinosDoMapa);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_5.setBounds(6, 438, 768, 144);
		tabConfig.add(panel_5);
		
		txtURLIcones = new JTextField();
		txtURLIcones.setText((String) null);
		txtURLIcones.setColumns(10);
		txtURLIcones.setBounds(185, 15, 533, 28);
		panel_5.add(txtURLIcones);
		
		JLabel lblServidorDeImagens = new JLabel("URL contendo as imagens");
		lblServidorDeImagens.setOpaque(true);
		lblServidorDeImagens.setHorizontalAlignment(SwingConstants.RIGHT);
		lblServidorDeImagens.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblServidorDeImagens.setBounds(22, 22, 151, 14);
		panel_5.add(lblServidorDeImagens);
		
		JLabel lblTaxista = new JLabel("Taxista");
		lblTaxista.setOpaque(true);
		lblTaxista.setHorizontalAlignment(SwingConstants.LEFT);
		lblTaxista.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblTaxista.setBounds(149, 48, 50, 14);
		panel_5.add(lblTaxista);
		
		txtPinTaxistaLivre = new JTextField();
		txtPinTaxistaLivre.setText((String) null);
		txtPinTaxistaLivre.setColumns(10);
		txtPinTaxistaLivre.setBounds(98, 64, 160, 28);
		panel_5.add(txtPinTaxistaLivre);
		
		JLabel lblLivre = new JLabel("Livre");
		lblLivre.setOpaque(true);
		lblLivre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLivre.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblLivre.setBounds(36, 71, 50, 14);
		panel_5.add(lblLivre);
		
		JLabel lblOcupado = new JLabel("Ocupado");
		lblOcupado.setOpaque(true);
		lblOcupado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOcupado.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblOcupado.setBounds(28, 93, 58, 14);
		panel_5.add(lblOcupado);
		
		txtPinTaxistaOcupado = new JTextField();
		txtPinTaxistaOcupado.setText((String) null);
		txtPinTaxistaOcupado.setColumns(10);
		txtPinTaxistaOcupado.setBounds(98, 87, 160, 28);
		panel_5.add(txtPinTaxistaOcupado);
		
		JLabel lblConfirmado = new JLabel("Confirmado");
		lblConfirmado.setOpaque(true);
		lblConfirmado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmado.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblConfirmado.setBounds(6, 116, 80, 14);
		panel_5.add(lblConfirmado);
		
		txtPinTaxistaConfirmado = new JTextField();
		txtPinTaxistaConfirmado.setText((String) null);
		txtPinTaxistaConfirmado.setColumns(10);
		txtPinTaxistaConfirmado.setBounds(98, 110, 160, 28);
		panel_5.add(txtPinTaxistaConfirmado);
		
		JLabel lblMototaxista = new JLabel("Mototaxista");
		lblMototaxista.setOpaque(true);
		lblMototaxista.setHorizontalAlignment(SwingConstants.LEFT);
		lblMototaxista.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblMototaxista.setBounds(306, 48, 67, 14);
		panel_5.add(lblMototaxista);
		
		txtPinMotoTaxistaLivre = new JTextField();
		txtPinMotoTaxistaLivre.setText((String) null);
		txtPinMotoTaxistaLivre.setColumns(10);
		txtPinMotoTaxistaLivre.setBounds(262, 64, 160, 28);
		panel_5.add(txtPinMotoTaxistaLivre);
		
		txtPinMotoTaxistaOcupado = new JTextField();
		txtPinMotoTaxistaOcupado.setText((String) null);
		txtPinMotoTaxistaOcupado.setColumns(10);
		txtPinMotoTaxistaOcupado.setBounds(262, 87, 160, 28);
		panel_5.add(txtPinMotoTaxistaOcupado);
		
		txtPinMotoTaxistaConfirmado = new JTextField();
		txtPinMotoTaxistaConfirmado.setText((String) null);
		txtPinMotoTaxistaConfirmado.setColumns(10);
		txtPinMotoTaxistaConfirmado.setBounds(262, 110, 160, 28);
		panel_5.add(txtPinMotoTaxistaConfirmado);
		
		JLabel lblMotorista = new JLabel("Motorista");
		lblMotorista.setOpaque(true);
		lblMotorista.setHorizontalAlignment(SwingConstants.LEFT);
		lblMotorista.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblMotorista.setBounds(476, 48, 67, 14);
		panel_5.add(lblMotorista);
		
		txtPinMotoristaLivre = new JTextField();
		txtPinMotoristaLivre.setText((String) null);
		txtPinMotoristaLivre.setColumns(10);
		txtPinMotoristaLivre.setBounds(425, 64, 160, 28);
		panel_5.add(txtPinMotoristaLivre);
		
		txtPinMotoristaOcupado = new JTextField();
		txtPinMotoristaOcupado.setText((String) null);
		txtPinMotoristaOcupado.setColumns(10);
		txtPinMotoristaOcupado.setBounds(425, 87, 160, 28);
		panel_5.add(txtPinMotoristaOcupado);
		
		txtPinMotoristaConfirmado = new JTextField();
		txtPinMotoristaConfirmado.setText((String) null);
		txtPinMotoristaConfirmado.setColumns(10);
		txtPinMotoristaConfirmado.setBounds(425, 110, 160, 28);
		panel_5.add(txtPinMotoristaConfirmado);
		
		JLabel lblLocalizao = new JLabel("Localização");
		lblLocalizao.setOpaque(true);
		lblLocalizao.setHorizontalAlignment(SwingConstants.LEFT);
		lblLocalizao.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblLocalizao.setBounds(623, 48, 67, 14);
		panel_5.add(lblLocalizao);
		
		txtPinLocalizacao = new JTextField();
		txtPinLocalizacao.setText((String) null);
		txtPinLocalizacao.setColumns(10);
		txtPinLocalizacao.setBounds(597, 64, 120, 28);
		panel_5.add(txtPinLocalizacao);
		
		JLabel lblPassageiro = new JLabel("Passageiro");
		lblPassageiro.setOpaque(true);
		lblPassageiro.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassageiro.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPassageiro.setBounds(623, 93, 67, 14);
		panel_5.add(lblPassageiro);
		
		txtPinPassageiro = new JTextField();
		txtPinPassageiro.setText((String) null);
		txtPinPassageiro.setColumns(10);
		txtPinPassageiro.setBounds(598, 109, 120, 28);
		panel_5.add(txtPinPassageiro);
		
		JLabel lblContas = new JLabel("Contas e logins");
		lblContas.setBackground(SystemColor.window);
		lblContas.setOpaque(true);
		lblContas.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblContas.setBounds(13, 591, 91, 14);
		tabConfig.add(lblContas);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setHgap(10);
		flowLayout.setVgap(15);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_3.setBounds(6, 599, 768, 69);
		tabConfig.add(panel_3);
		
		JLabel label_3 = new JLabel("Conta iTunes");
		label_3.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_3.add(label_3);
		label_3.setOpaque(true);
		label_3.setFont(new Font("SansSerif", Font.PLAIN, 12));
		
		txtContaFastLane = new JTextField();
		panel_3.add(txtContaFastLane);
		txtContaFastLane.setToolTipText("Digite a conta a ser usada no iTunes");
		txtContaFastLane.setColumns(30);
		
		JLabel label = new JLabel("Número dos projetos (separados por vírgula)");
		label.setOpaque(true);
		label.setFont(new Font("SansSerif", Font.PLAIN, 12));
		label.setBounds(30, 6, 280, 14);
		tabProjeto.add(label);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(18, 14, 453, 57);
		tabProjeto.add(panel_1);
		
		txtProjNumbers = new JTextField();
		txtProjNumbers.setText((String) null);
		txtProjNumbers.setColumns(10);
		txtProjNumbers.setBounds(16, 16, 419, 28);
		panel_1.add(txtProjNumbers);
		
		JLabel lblPlataformaAlvo = new JLabel("Plataforma alvo");
		lblPlataformaAlvo.setOpaque(true);
		lblPlataformaAlvo.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPlataformaAlvo.setBounds(648, 6, 95, 14);
		tabProjeto.add(lblPlataformaAlvo);
		
		JLabel label_2 = new JLabel("Ações");
		label_2.setOpaque(true);
		label_2.setFont(new Font("SansSerif", Font.PLAIN, 12));
		label_2.setBounds(495, 6, 41, 14);
		tabProjeto.add(label_2);
		
		JLabel label_1 = new JLabel("Log do processamento");
		label_1.setOpaque(true);
		label_1.setFont(new Font("SansSerif", Font.PLAIN, 12));
		label_1.setBounds(30, 94, 141, 14);
		tabProjeto.add(label_1);
		
		final JTextArea txtLogProc = new JTextArea();
		txtLogProc.setWrapStyleWord(true);
		txtLogProc.setTabSize(4);
		txtLogProc.setRows(4);
		txtLogProc.setLineWrap(true);
		txtLogProc.setEditable(false);
		txtLogProc.setBounds(18, 351, 738, 230);
		
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 120, 742, 481);
		tabProjeto.add(scrollPane);
		scrollPane.setViewportView(txtLogProc);
	
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(483, 13, 141, 95);
		tabProjeto.add(panel_2);
		
		chkPackage = new JCheckBox("package");
		chkPackage.setSelected(true);
		panel_2.add(chkPackage);
		
		chkMarketing = new JCheckBox("marketing");
		chkMarketing.setSelected(true);
		panel_2.add(chkMarketing);
		
		chkMeta = new JCheckBox("meta");
		chkMeta.setSelected(true);
		panel_2.add(chkMeta);
		
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("/resources/folder.png"));
		} catch (IOException ex) {}

		final JButton btnPreparar = new JButton("Preparar");
		btnPreparar.setBounds(342, 619, 95, 29);
		tabProjeto.add(btnPreparar);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_6.setBounds(636, 13, 124, 95);
		tabProjeto.add(panel_6);
		panel_6.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		chkAndroid = new JCheckBox("Android");
		chkAndroid.setSelected(true);
		panel_6.add(chkAndroid);
		
		chkIOS = new JCheckBox("iOS");
		chkIOS.setSelected(true);
		panel_6.add(chkIOS);
		
		JLabel label_4 = new JLabel("v1.3");
		label_4.setForeground(Color.LIGHT_GRAY);
		label_4.setHorizontalAlignment(SwingConstants.TRAILING);
		label_4.setBounds(712, 652, 61, 16);
		tabProjeto.add(label_4);
		btnPreparar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (data.isMontando()) {  //cancelamento pelo usuário
					data.setMontando(false);
					btnPreparar.setEnabled(false);
					return;
				}

				txtLogProc.setText(null);  //limpar a caixa de log.
				data.setMontando(true);
				
				data.setMarketing(chkMarketing.isSelected());
				data.setPackge(chkPackage.isSelected());
				data.setMeta(chkMeta.isSelected());
//				data.setImagens(chkImagem.isSelected());
//				data.setPassageiro(chkPassageiro.isSelected());
//				data.setTaxista(chkTaxista.isSelected());
//				data.setReload_meta(chkReloadMeta.isSelected());
//				data.setCerts(chkCerts.isSelected());
				
				txtProjNumbers.setText(Util.retirarBarraFinal(txtProjNumbers.getText()));
				data.setProjNumbers(txtProjNumbers.getText());

				txtDadosApps.setText(Util.retirarBarraFinal(txtDadosApps.getText()));
				data.setDadosAppsPath(new File(txtDadosApps.getText()));
				
				txtProjsAndamento.setText(Util.retirarBarraFinal(txtProjsAndamento.getText()));
				data.setProjsAndamentoPath(new File(txtProjsAndamento.getText()));

				txtAndroidCustom.setText(Util.retirarBarraFinal(txtAndroidCustom.getText()));
				data.setAndroidCustomPath(new File(txtAndroidCustom.getText()));
				
				txtArqBase.setText(Util.retirarBarraFinal(txtArqBase.getText()));
				data.setBasePath(new File(txtArqBase.getText()));
				
				txtIOSCustom.setText(Util.retirarBarraFinal(txtIOSCustom.getText()));
				data.setIOSCustomPath(new File(txtIOSCustom.getText()));
				
				txtScriptPath.setText(Util.retirarBarraFinal(txtScriptPath.getText()));
				data.setScriptsPath(new File(txtScriptPath.getText()));
				
				data.setImagensSubDir(txtImagens.getText().trim());
				data.setDadosSubDir(txtDados.getText().trim());
				data.setMarketingSubDir(txtMarketing.getText().trim());
				
				data.setContaFastLane(txtContaFastLane.getText().trim());
				
				txtServidorCliente.setText(Util.retirarBarraFinal(txtServidorCliente.getText()));
				data.setServidorCliente(txtServidorCliente.getText());	
				
				txtURLCliente.setText(Util.colocarBarraInicial(txtURLCliente.getText()));
				data.setApiCliente(txtURLCliente.getText());				
				
				data.setNomeCliente(txtNomeCliente.getText().trim());
				data.setEmailLoginCliente(txtEmailLoginCliente.getText().trim());
				data.setSenhaCliente(txtSenhaCliente.getText());
				
				txtURLIcones.setText(Util.colocarBarraFinal(txtURLIcones.getText()));
				data.setUrlPinsMapa(txtURLIcones.getText());
				
				txtPinLocalizacao.setText(Util.retirarBarraInicial(txtPinLocalizacao.getText()));
				data.setPinLocalizacao(txtPinLocalizacao.getText());
				
				txtPinPassageiro.setText(Util.retirarBarraInicial(txtPinPassageiro.getText()));
				data.setPinPassageiro(txtPinPassageiro.getText());
				
				txtPinTaxistaLivre.setText(Util.retirarBarraInicial(txtPinTaxistaLivre.getText()));
				data.setPinTaxistaLivre(txtPinTaxistaLivre.getText());
				
				txtPinTaxistaOcupado.setText(Util.retirarBarraInicial(txtPinTaxistaOcupado.getText()));
				data.setPinTaxistaOcupado(txtPinTaxistaOcupado.getText());
				
				txtPinTaxistaConfirmado.setText(Util.retirarBarraInicial(txtPinTaxistaConfirmado.getText()));
				data.setPinTaxistaConfirmado(txtPinTaxistaConfirmado.getText());
				
				txtPinMotoTaxistaLivre.setText(Util.retirarBarraInicial(txtPinMotoTaxistaLivre.getText()));
				data.setPinMototaxistaLivre(txtPinMotoTaxistaLivre.getText());
				
				txtPinMotoTaxistaOcupado.setText(Util.retirarBarraInicial(txtPinMotoTaxistaOcupado.getText()));
				data.setPinMototaxistaOcupado(txtPinMotoTaxistaOcupado.getText());
				
				txtPinMotoTaxistaConfirmado.setText(Util.retirarBarraInicial(txtPinMotoTaxistaConfirmado.getText()));
				data.setPinMototaxistaConfirmado(txtPinMotoTaxistaOcupado.getText());
				
				txtPinMotoristaLivre.setText(Util.retirarBarraInicial(txtPinMotoristaLivre.getText()));
				data.setPinMotoristaLivre(txtPinMotoristaLivre.getText());
				
				txtPinMotoristaOcupado.setText(Util.retirarBarraInicial(txtPinMotoristaOcupado.getText()));
				data.setPinMotoristaOcupado(txtPinMotoristaOcupado.getText());
				
				txtPinMotoristaConfirmado.setText(Util.retirarBarraInicial(txtPinMotoristaConfirmado.getText()));
				data.setPinMotoristaConfirmado(txtPinMotoristaConfirmado.getText());
				
				data.setSecretPath(txtSecretDir.getText().trim());
				data.setAndroid(chkAndroid.isSelected());
				data.setIOS(chkIOS.isSelected());
				
				gravarDadosTela();
				btnPreparar.setText("Cancelar");

				new Thread(new Runnable() {
					@Override
					public void run() {
						txtLogProc.setText("");
						String resultado = new TaxiMachinePreparator(data, new ICallback() {
							@Override
							public void callback(String text) {
								//inclusão de texto no log
								final String timestamp = "[" + millisecondsToHour(System.currentTimeMillis() - 3 * 60 * 60 * 1000) + "] ";  //-3GMT
								txtLogProc.append(timestamp + text+"\n");
								//scroll automático, para acompanhar o log
								JScrollBar vertical = scrollPane.getVerticalScrollBar();
								vertical.setValue( vertical.getMaximum() );
								
								Util.writeLog(data.getLogFilename(), timestamp + text);
							}
							@Override
							public void callbackFinal(String text) {
                                callback(text);
							}
						}).go();

						if (!data.isMontando()) {
							txtLogProc.append("\nCANCELADO PELO USUÁRIO\n");
							Util.writeLog(data.getLogFilename(), "\nCANCELADO PELO USUÁRIO\n");
						} else { 
							if (!Util.ehVazio(resultado)) {
                               //fatal error
								txtLogProc.append("\n"+resultado);
							}
							txtLogProc.append("\nProcessamento encerrado.\n\n");
						}
						Util.closeLog(data.getLogFilename());
						txtLogProc.append("\n---------------------------");
						
						//scroll automático, para acompanhar o log
						JScrollBar vertical = scrollPane.getVerticalScrollBar();
						vertical.setValue( vertical.getMaximum() );
						btnPreparar.setText("Preparar");
						data.setMontando(false);
						btnPreparar.setEnabled(true);
					}
				}).start();
				
			}
		});

		buscarDadosTela();
	}
	
    private String millisecondsToHour(long mills) {
        long seconds = mills / 1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h, m, s);
    }
	
	private void gravarDadosTela() {
		Properties p = new Properties();
		OutputStream stream = null;
		try {
			stream = new FileOutputStream("tela.properties");
			
			p.put(Util.PROJS_NUMBERS, txtProjNumbers.getText().trim());
			
			p.put(Util.CHK_MARKETING, (data.isMarketing()?"true":"false"));
//			p.put(Util.CHK_IMAGENS, (data.isImagens()?"true":"false"));
			p.put(Util.CHK_PACKAGE, (data.isPackge()?"true":"false"));
			p.put(Util.CHK_META,(data.isMeta()?"true":"false"));
//			p.put(Util.CHK_PASSAGEIRO, (data.isPassageiro()?"true":"false"));
//			p.put(Util.CHK_TAXISTA, (data.isTaxista()?"true":"false"));
//			p.put(Util.CHK_RELOAD_META, (data.isReload_meta()?"true":"false"));
//			p.put(Util.CHK_CERTS, (data.isCerts()?"true":"false"));
			
			p.put(Util.DADOS_APPS, txtDadosApps.getText().trim());
			p.put(Util.PROJS_ANDAMENTO, txtProjsAndamento.getText().trim());
			p.put(Util.ANDROID_CUSTOM, txtAndroidCustom.getText().trim());
			p.put(Util.IOS_CUSTOM, txtIOSCustom.getText().trim());
			p.put(Util.ARQ_BASE, txtArqBase.getText().trim());
			p.put(Util.SCRIPTS_PATH, txtScriptPath.getText().trim());

			p.put(Util.IMAGENS_SUBDIR, txtImagens.getText().trim());
			p.put(Util.DADOS_SUBDIR, txtDados.getText().trim());
			p.put(Util.MKT_SUBDIR, txtMarketing.getText().trim());
			
			p.put(Util.CONTA_FASTLANE, txtContaFastLane.getText().trim());

			p.put(Util.API_CLIENTE, txtURLCliente.getText().trim());
			p.put(Util.SERVIDOR_CLIENTE, txtServidorCliente.getText().trim());
			p.put(Util.SENHA_CLIENTE,  data.getSenhaCliente());
			p.put(Util.EMAIL_LOGIN_CLIENTE,  data.getEmailLoginCliente());
			p.put(Util.NOME_CLIENTE,  data.getNomeCliente());
			
			p.put(Util.URL_PIN_MAPA, data.getUrlPinsMapa());
			p.put(Util.PIN_LOCALIZACAO, data.getPinLocalizacao());
			p.put(Util.PIN_PASSAGEIRO, data.getPinPassageiro());
			p.put(Util.PIN_TAXISTA_LIVRE, data.getPinTaxistaLivre());
			p.put(Util.PIN_TAXISTA_OCUPADO, data.getPinTaxistaOcupado());
			p.put(Util.PIN_TAXISTA_CONFIRMADO, data.getPinTaxistaConfirmado());
			p.put(Util.PIN_MOTOTAXISTA_LIVRE, data.getPinMototaxistaLivre());
			p.put(Util.PIN_MOTOTAXISTA_OCUPADO, data.getPinMototaxistaOcupado());
			p.put(Util.PIN_MOTOTAXISTA_CONFIRMADO, data.getPinMototaxistaConfirmado());
			p.put(Util.PIN_MOTORISTA_LIVRE, data.getPinMotoristaLivre());
			p.put(Util.PIN_MOTORISTA_OCUPADO, data.getPinMotoristaOcupado());
			p.put(Util.PIN_MOTORISTA_CONFIRMADO, data.getPinMotoristaConfirmado());

			p.put(Util.SECRET_DIR, data.getSecretPath());
			p.put(Util.CHK_ANDROID, (data.isAndroid()?"true":"false"));
			p.put(Util.CHK_IOS, (data.isIOS()?"true":"false"));

			p.store(stream, null);
			stream.close();
		} catch (IOException e) {  //arquivo não existe
			if (stream!=null) {
				try {stream.close();} catch (Exception e2) {}
			}
		}		
	}

	private void buscarDadosTela() {
		Properties p = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("tela.properties");
			p.load(inputStream);
			inputStream.close();
			
			txtDadosApps.setText(p.getProperty(Util.DADOS_APPS));
			txtProjsAndamento.setText(p.getProperty(Util.PROJS_ANDAMENTO));
			txtAndroidCustom.setText(p.getProperty(Util.ANDROID_CUSTOM));
			txtIOSCustom.setText(p.getProperty(Util.IOS_CUSTOM));
			txtArqBase.setText(p.getProperty(Util.ARQ_BASE));
			txtScriptPath.setText(p.getProperty(Util.SCRIPTS_PATH));
			txtImagens.setText(p.getProperty(Util.IMAGENS_SUBDIR));
			txtDados.setText(p.getProperty(Util.DADOS_SUBDIR));
			txtMarketing.setText(p.getProperty(Util.MKT_SUBDIR));
			txtSecretDir.setText(p.getProperty(Util.SECRET_DIR));
			
			txtContaFastLane.setText(p.getProperty(Util.CONTA_FASTLANE));
			
			txtServidorCliente.setText(p.getProperty(Util.SERVIDOR_CLIENTE));
			if (Util.ehVazio(txtServidorCliente.getText())) {
				txtServidorCliente.setText("http://cloud.taximachine.com.br");
			}

			txtURLCliente.setText(p.getProperty(Util.API_CLIENTE));
			if (Util.ehVazio(txtURLCliente.getText())) {
				txtURLCliente.setText("api/passageiro");
			}

			txtNomeCliente.setText(p.getProperty(Util.NOME_CLIENTE));
			if (Util.ehVazio(txtNomeCliente.getText())) {
				txtNomeCliente.setText("Cliente Teste");
			}

			txtEmailLoginCliente.setText(p.getProperty(Util.EMAIL_LOGIN_CLIENTE));
			if (Util.ehVazio(txtEmailLoginCliente.getText())) {
				txtEmailLoginCliente.setText("rsagoes@gaudium.com.br");
			}
            
			txtSenhaCliente.setText(p.getProperty(Util.SENHA_CLIENTE));
			if (Util.ehVazio(txtSenhaCliente.getText())) {
				txtSenhaCliente.setText("123456");
			}
			
			txtURLIcones.setText(p.getProperty(Util.URL_PIN_MAPA));
			if (Util.ehVazio(txtURLIcones.getText())) {
				txtURLIcones.setText("http://www.taximachine.com.br/wp-content/uploads/2016/03");
			}
			
			txtPinLocalizacao.setText(p.getProperty(Util.PIN_LOCALIZACAO));
			if (Util.ehVazio(txtPinLocalizacao.getText())) {
				txtPinLocalizacao.setText("pin-lugar.png");
			}

			txtPinPassageiro.setText(p.getProperty(Util.PIN_PASSAGEIRO));
			if (Util.ehVazio(txtPinPassageiro.getText())) {
				txtPinPassageiro.setText("pin-pass.png");
			}

			txtPinTaxistaLivre.setText(p.getProperty(Util.PIN_TAXISTA_LIVRE));
			if (Util.ehVazio(txtPinTaxistaLivre.getText())) {
				txtPinTaxistaLivre.setText("pin-taxi-livre.png");
			}

			txtPinTaxistaOcupado.setText(p.getProperty(Util.PIN_TAXISTA_OCUPADO));
			if (Util.ehVazio(txtPinTaxistaOcupado.getText())) {
				txtPinTaxistaOcupado.setText("pin-taxi-ocupado.png");
			}

			txtPinTaxistaConfirmado.setText(p.getProperty(Util.PIN_TAXISTA_CONFIRMADO));
			if (Util.ehVazio(txtPinTaxistaConfirmado.getText())) {
				txtPinTaxistaConfirmado.setText("pin-taxi-confirmado.png");
			}
			
			txtPinMotoTaxistaLivre.setText(p.getProperty(Util.PIN_MOTOTAXISTA_LIVRE));
			if (Util.ehVazio(txtPinMotoTaxistaLivre.getText())) {
				txtPinMotoTaxistaLivre.setText("pin-moto-livre.png");
			}

			txtPinMotoTaxistaOcupado.setText(p.getProperty(Util.PIN_MOTOTAXISTA_OCUPADO));
			if (Util.ehVazio(txtPinMotoTaxistaOcupado.getText())) {
				txtPinMotoTaxistaOcupado.setText("pin-moto-ocupado.png");
			}

			txtPinMotoTaxistaConfirmado.setText(p.getProperty(Util.PIN_MOTOTAXISTA_CONFIRMADO));
			if (Util.ehVazio(txtPinMotoTaxistaConfirmado.getText())) {
				txtPinMotoTaxistaConfirmado.setText("pin-moto-confirmado.png");
			}

			txtPinMotoristaLivre.setText(p.getProperty(Util.PIN_MOTORISTA_LIVRE));
			if (Util.ehVazio(txtPinMotoristaLivre.getText())) {
				txtPinMotoristaLivre.setText("pin-carro-livre.png");
			}

			txtPinMotoristaOcupado.setText(p.getProperty(Util.PIN_MOTORISTA_OCUPADO));
			if (Util.ehVazio(txtPinMotoristaOcupado.getText())) {
				txtPinMotoristaOcupado.setText("pin-carro-ocupado.png");
			}

			txtPinMotoristaConfirmado.setText(p.getProperty(Util.PIN_MOTORISTA_CONFIRMADO));
			if (Util.ehVazio(txtPinMotoristaConfirmado.getText())) {
				txtPinMotoristaConfirmado.setText("pin-carro-confirmado.png");
			}

			txtProjNumbers.setText(p.getProperty(Util.PROJS_NUMBERS));
			
			chkMarketing.setSelected(new Boolean(p.getProperty(Util.CHK_MARKETING, "false")));
			chkPackage.setSelected(new Boolean(p.getProperty(Util.CHK_PACKAGE, "false")));
			chkMeta.setSelected(new Boolean(p.getProperty(Util.CHK_META, "false")));
			chkAndroid.setSelected(new Boolean(p.getProperty(Util.CHK_ANDROID, "false")));
			chkIOS.setSelected(new Boolean(p.getProperty(Util.CHK_IOS, "false")));
			
		} catch (IOException e) {  //arquivo não existe
			if (inputStream!=null) {
				try {inputStream.close();} catch (Exception e2) {}
			}
		}		
	}

	private JFileChooser pathChooser() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Escolha o diretório");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		return chooser;
	}
}