import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;

public class Gui extends JFrame {
	
//	private JButton fileOpenButton = new JButton("Open file");
//	private JLabel fileFullPathLabel = new JLabel("File Full Path : ");
////	private JTextArea filePathTextArea = new JTextArea("", 20, 50, JTextArea.SCROLLBARS_VERTICAL_ONLY);
////	private JTextArea filePathTextArea = new JTextArea(20, 50);
//	private JTextPane filePathTextPane = new JTextPane();//20, 50);
////	private FileDialog fileOpenDialog;
	private JFileChooser fileChooser = new JFileChooser("c:\\");
	private JFileChooser base64Chooser = new JFileChooser("c:\\");
	
	private JButton createBase64txtButton = new JButton("Create BASE64 Txt file");
	private JButton createBinaryFileButton = new JButton("Create Binary file");

//	private JTextArea logArea;
	final private JTextPane logTextPane;
	final JTextField filePathTextField;
	
	private SimpleAttributeSet red = new SimpleAttributeSet();
    private SimpleAttributeSet blue = new SimpleAttributeSet();
    
	private UcloudFileManager ucloudFileManager;
	
	public Gui(final UcloudFileManager ucloudFileManager) throws Exception { 
		
		this.ucloudFileManager = ucloudFileManager;
		
		// MAIN PANEL
		JPanel mainPane = new JPanel(new BorderLayout());
		//		GridLayout mainLayout = new GridLayout(3, 0);
		//		mainPane.setLayout(mainLayout);

		JPanel blankPane = new JPanel();
		blankPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JTextField blankTextField = new JTextField(5);
		blankTextField.setVisible(false);
		JLabel blankLbl = new JLabel("1234");
		blankLbl.setEnabled(false);
		blankLbl.setVisible(false);
		blankPane.add(blankLbl); blankPane.add(blankTextField);

		//GRID TOP 
		JPanel topPanel = new JPanel(new FlowLayout());//new BorderLayout());
		topPanel.setBorder(new TitledBorder("File Open"));
//		JLabel fileOpenLabel = new JLabel("파일 열기 :");
//		topPanel.add(fileOpenLabel);
		
		JLabel filePathLabel = new JLabel("File Absolute Path : ");
		filePathTextField = new JTextField(25);
		filePathTextField.setEditable(false);
		
		JPanel botPane = new JPanel();
		botPane.setLayout(new FlowLayout());
		botPane.setBorder(new TitledBorder("File Info"));
		botPane.add(filePathLabel);
		botPane.add(filePathTextField);
		
		logTextPane = new JTextPane();
		logTextPane.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_DELETE) {
					logTextPane.setText("");
				}
				super.keyPressed(e);
			}
		});
		logTextPane.setEditable(false);
		
		JScrollPane logAreaScroll = new JScrollPane();
		logAreaScroll.setBounds(10, 10, 480, 700);
		logAreaScroll.setViewportView(logTextPane);
//		botPane.add(logAreaScroll);
		
		JPanel topMidPane = new JPanel();
		topMidPane.setLayout(new BorderLayout());
		topMidPane.add(BorderLayout.NORTH, topPanel);

		mainPane.add(BorderLayout.NORTH, topMidPane);
		mainPane.add(BorderLayout.CENTER, botPane);

		fileChooser.setFileFilter(new FileNameExtensionFilter("zip", "jar", "7z", "txt"));
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogTitle("Open file");
		
		//BUTTON
		createBase64txtButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(Gui.this) == JFileChooser.APPROVE_OPTION) {
				
					String filePath = fileChooser.getSelectedFile().getAbsolutePath();
					System.out.println("Selected File's Absolute Path : " + filePath);
					
					filePathTextField.setText(filePath);
					
					try {
						String base64Str = ucloudFileManager.binaryFileToBase64(filePath);
						
						ucloudFileManager.createBase64TextFile(filePath, base64Str);
						
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
//					createBinaryFileButton.setEnabled(true);
				}
			}
		});
		
		base64Chooser.setFileFilter(new FileNameExtensionFilter("txt", "txt"));
		base64Chooser.setMultiSelectionEnabled(false);
		base64Chooser.setDialogTitle("Open file");
		
		createBinaryFileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (base64Chooser.showOpenDialog(Gui.this) == JFileChooser.APPROVE_OPTION) {
					
					String filePath = base64Chooser.getSelectedFile().getParent();//base64Chooser.getSelectedFile().getPath();//getAbsolutePath();
					System.out.println("Selected Base64 File's Path : " + filePath);
					
//					filePath = base64Chooser.getSelectedFile().getParent();
//					System.out.println("### : " + base64Chooser.getSelectedFile().getParent());
					
					String selectedFileName = base64Chooser.getSelectedFile().getName();
					System.out.println("Selected File Name : " + selectedFileName);
					try {
						ucloudFileManager.base64TxtToBinaryFile(filePath, selectedFileName);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
//		createBinaryFileButton.setEnabled(false);

		topPanel.add(createBase64txtButton);
		topPanel.add(createBinaryFileButton);

		this.add(mainPane);
		this.setBounds(500, 200, 500, 200);//, 800);
		this.setTitle("File En/De-coder");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	public synchronized void writeLog(String msg, boolean warning) {
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Date currentTime = new Date(System.currentTimeMillis());
		String timeString = dateForm.format(currentTime);
		
		try {
			logTextPane.getStyledDocument().insertString(
					logTextPane.getStyledDocument().getEndPosition().getOffset()-1, 
					"["+timeString+"] "+msg+"\n", 
					warning?blue:null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logTextPane.setCaretPosition(logTextPane.getDocument().getLength());
	}

	public synchronized void writeLog(String msg, Throwable th) { 
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Date currentTime = new Date(System.currentTimeMillis());
		String timeString = dateForm.format(currentTime);
		
		try {
			logTextPane.getStyledDocument().insertString(
					logTextPane.getStyledDocument().getEndPosition().getOffset()-1, 
					"["+timeString+"] "+msg+" : "+th+"\n", 
					red);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logTextPane.setCaretPosition(logTextPane.getDocument().getLength());
	}
	
	static class WindowHandler extends WindowAdapter
    {
          public void windowClosing(WindowEvent e)
          {
                System.out.println("윈도우 창 닫기");
                System.exit(0);
          }
    }

}
	
