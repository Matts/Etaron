/**
 * Etaron
 *
 *
 * @Author Dalthow Game Studios 
 * @Class Login.java
 *
 **/

package nl.dalthow.etaron.window;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import nl.dalthow.etaron.base.Main;
import nl.dalthow.etaron.framework.Encrypter;
import nl.dalthow.etaron.handler.SoundHandler;
import nl.dalthow.etaron.model.Score;
import nl.dalthow.etaron.model.User;
import nl.dalthow.etaron.service.AccessClient;
import nl.dalthow.etaron.service.ConnectionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Login 
{
    // Declaration

    private JPanel credentialContent;

    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userText;
    private JPasswordField passwordText;

    private JButton loginButton;
    private JButton registerButton;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static JFrame frame;

    public static String encryptedPassword;

    @Autowired
    private SoundHandler soundHandler;

    @Autowired
    private AccessClient accessClient;

    
    // Creates the window where everything gets projected on
    
    @Autowired
    private Login(@Value("${login.width}") int width, @Value("${login.height}") int height, @Value("${login.title}") String title) 
    {
        frame = new JFrame(title);

        credentialContent = new JPanel();

        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        userText = new JTextField(20);
        passwordText = new JPasswordField(20);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        frame.pack();

        frame.add(credentialContent);
        
        frame.getRootPane().setDefaultButton(loginButton);
        frame.setSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setResizable(false);
        frame.requestFocus();
        frame.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent event) 
            {
                logger.debug("Login window closed.");
            }
        });

        Image icon = Toolkit.getDefaultToolkit().createImage(ClassLoader.getSystemResource("global/icon.png"));
        
        frame.setIconImage(icon);

        placeComponents(credentialContent);
    }


    // All the components and their functions are separated in a panel
   
    private void placeComponents(JPanel panel) 
    {
        panel.setLayout(null);

        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        passwordText.setBounds(100, 40, 160, 25);
        panel.add(passwordText);

        loginButton.setBounds(100, 80, 65, 25);
        loginButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event) 
            {
                encryptedPassword = Encrypter.encryptString(passwordText.getText());
               
                try 
                {
                    User user = accessClient.login(userText.getText(), encryptedPassword);
                    
                    if(user == null) 
                    {
                        JOptionPane.showMessageDialog(frame, "Incorrect username or password.", "Authorization error", JOptionPane.WARNING_MESSAGE);
                    }
                    
                    else 
                    {
                        JOptionPane.showMessageDialog(frame, "Your login was successful.", "Information", JOptionPane.INFORMATION_MESSAGE);
                        logger.debug("Access granted for {}.", user.getUserName());

                        Main.user = user;
                        
                        for(Score score : user.getScores()) 
                        {
                            Main.allScores[score.getLevel()] = String.valueOf(score.getValue());
                        }
                        
                        Main.musicVolume = user.getSettings().getMusicVolume();
                        Main.soundVolume = user.getSettings().getSoundVolume();
                        
                        soundHandler.changeVolume(Main.musicVolume, soundHandler.musicClip);
                        soundHandler.changeVolume(Main.soundVolume, soundHandler.soundClip);
                        
                        Settings.soundVolumeSlider.setValue(user.getSettings().getSoundVolume());
                        Settings.musicVolumeSlider.setValue(user.getSettings().getMusicVolume());

                        frame.setVisible(false);
                    }
                } 
                
                catch(HeadlessException error) 
                {
                    logger.error(error.getMessage(), error);
                } 
                
                catch(ConnectionException error)
                {
                    JOptionPane.showMessageDialog(frame, error.getMessage(), "Connection error.", JOptionPane.ERROR_MESSAGE);
                    logger.error(error.getMessage(), error);
                }
            }
        });

        panel.add(loginButton);

        registerButton.setBounds(175, 80, 85, 25);
        registerButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent event) 
            {
                try 
                {
                    Desktop.getDesktop().browse(new URL("http://www.dalthow.com/registration.php").toURI());
                } 
                
                catch (Exception error) 
                {
                    logger.error(error.getMessage(), error);
                }
            }
        });

        panel.add(registerButton);
    }
    
    public void show() 
    {
        frame.setVisible(true);
        frame.requestFocus();
    }
}