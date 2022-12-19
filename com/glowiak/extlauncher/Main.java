package com.glowiak.extlauncherfix;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.net.URL;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main extends JFrame
{
    protected static Image DIRT;
    protected static Image DIRT2;
    protected static Image STONE;
    
    protected static Main me;
    protected static LauncherPanel lp;
    
    private JButton B_PLAY;
    private JButton B_SETTINGS;
    protected static JButton B_LOGIN;
    
    protected static JTextField TF_USERNAME;
    protected static JPasswordField TF_PASSWORD;
    
    protected static JLabel L_USERNAME;
    protected static JLabel L_PASSWORD;
    protected static JLabel L_LOGGED_IN;
    
    protected static String username;
    protected static String password;
    protected static String playtesterId;
    protected static boolean loggedIn;
    
    public Main()
    {
        this.setTitle("Minecraft Alpha 1.0.16.05 Launcher");
        this.setSize(700, 400);
        system.out.println("ExtLauncherFix v1.0.0 Loaded!")
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);
        
        playtesterId = "";
        loggedIn = false;
        
        this.DIRT = new ImageIcon(new Buffer().getClass().getResource("/resources/dirt.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        this.DIRT2 = new ImageIcon(new Buffer().getClass().getResource("/resources/dirt2.png")).getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT);
        this.STONE = new ImageIcon(new Buffer().getClass().getResource("/resources/stone.png")).getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT);
        
        this.setIconImage(this.DIRT);
        
        this.B_PLAY = new JButton("Play");
        this.B_SETTINGS = new JButton("Settings");
        B_LOGIN = new JButton("Log in");
        
        this.B_PLAY.setBounds(700 - 235, 400 - 120, 220, 32);
        this.B_SETTINGS.setBounds(700 - 235, 400 - 64, 220, 32);
        B_LOGIN.setBounds(215, 400 - 68, 100, 20);

        L_USERNAME = new JLabel("Username:");
        L_PASSWORD = new JLabel("Password:");
        L_LOGGED_IN = new JLabel("");
        
        L_USERNAME.setBounds(10, 400 - 128, 100, 20);
        L_USERNAME.setForeground(Color.white);
        
        L_PASSWORD.setBounds(10, 400 - 88, 100, 20);
        L_PASSWORD.setForeground(Color.white);
        
        L_LOGGED_IN.setBounds(10, 400 - 68, 200, 20);
        L_LOGGED_IN.setForeground(Color.white);
        L_LOGGED_IN.setVisible(false);

        TF_USERNAME = new JTextField();
        TF_PASSWORD = new JPasswordField();
        
        TF_USERNAME.setBounds(10, 400 - 108, 200, 20);
        TF_PASSWORD.setBounds(10, 400 - 68, 200, 20);

        lp = new LauncherPanel();
        this.add(lp);
        
        B_LOGIN.addActionListener(new LogIn());
        B_PLAY.addActionListener(new GameRunner());
        
        lp.add(this.B_PLAY);
        lp.add(this.B_SETTINGS);
        lp.add(L_USERNAME);
        lp.add(TF_USERNAME);
        lp.add(L_PASSWORD);
        lp.add(TF_PASSWORD);
        lp.add(B_LOGIN);
        lp.add(L_LOGGED_IN);
        
        if (new File(MinecraftData.getMinecraftDir() + "/.user").exists())
        {
            try {
                FileReader fr = new FileReader(MinecraftData.getMinecraftDir() + "/.user");
                BufferedReader br = new BufferedReader(fr);
                username = br.readLine();
                TF_USERNAME.setText(username);
                br.close();
                fr.close();
            } catch (IOException ioe) { ioe.printStackTrace(); }
        }
        if (new File(MinecraftData.getMinecraftDir() + "/.password").exists())
        {
            try {
                FileReader fr = new FileReader(MinecraftData.getMinecraftDir() + "/.password");
                BufferedReader br = new BufferedReader(fr);
                username = br.readLine();
                TF_PASSWORD.setText(username);
                br.close();
                fr.close();
            } catch (IOException ioe) { ioe.printStackTrace(); }
        }
        
        while (true)
        {
            lp.repaint();
            this.repaint();
        }
    }
    
    public static void main(String[] args)
    {
        me = new Main();
    }
    

}
class LauncherPanel extends JPanel
{
    protected LauncherPanel()
    {
        super();
        
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.setBounds(0, 0, 700, 400);
        this.setLayout(null);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        int base_x = 0;
        int base_y = 0;
        
        for (int i = 0; i < 11; i++)
        {
            for (int j = 0; j < 11 + 1; j++)
            {
                g.drawImage(Main.DIRT2, base_x, base_y, null);
                base_x += 64;
            }
            base_y += 64;
            base_x = 0;
        }
        g.drawImage(Main.STONE, 700 - 128, 400 - 128, null);
        g.drawImage(Main.STONE, 700 - 64, 400 - 128, null);
        g.drawImage(Main.STONE, 700 - 128, 400 - 64, null);
        g.drawImage(Main.STONE, 700 - 64, 400 - 64, null);
        g.drawImage(Main.STONE, 700 - 245, 400 - 64, null);
        g.drawImage(Main.STONE, 700 - 185, 400 - 64, null);
        g.drawImage(Main.STONE, 700 - 245, 400 - 128, null);
        g.drawImage(Main.STONE, 700 - 185, 400 - 128, null);
        
        base_x = 5;
        for (int i = 0; i < 10; i++)
        {
            g.drawImage(Main.DIRT, base_x, 5, null);
            base_x += 64;
        }
        g.drawImage(Main.DIRT, 700 - 85, 5, null);
        
        g.setColor(Color.white);
        g.fillRect(0, 400 - 128, 700, 2);
        
        g.drawString("Build date: 22 November 2010", 20, 55);
        g.drawString("Please log in here with your Minecraft account to enable the site content (JavaScript must be enabled)", 5, 95);
        
        if (Main.loggedIn)
        {
            g.drawString("Login successful.", 275, 155);
            g.drawString("Your playtester ID (do not share):", 275, 175);
            g.drawString(Main.playtesterId, 275, 195);
			g.drawString("News!",275, 155);
		    g.drawString("A Finaly Working Custom Launcher!", 275 175);
        }
        
        g.setFont(g.getFont().deriveFont(g.getFont().getSize() * 1.4F));
        g.drawString("Minecraft Alpha Version 1.0.16.05_13", 20, 25);
    }
}
class Buffer
{
}
class LogIn implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Main.username = Main.TF_USERNAME.getText();
        Main.password = Main.TF_PASSWORD.getText();
        
        if (Main.username.equals(""))
        {
            JOptionPane.showMessageDialog(Main.me, "Username cannot be NULL!");
        } else if (Main.username.length() >= 15)
        {
            JOptionPane.showMessageDialog(Main.me, "Username cannot contain more than 15 characters!");
        } else
        {
            Main.L_USERNAME.setVisible(false);
            Main.L_PASSWORD.setVisible(false);
            Main.TF_USERNAME.setVisible(false);
            Main.TF_PASSWORD.setVisible(false);
            Main.B_LOGIN.setVisible(false);
        
            Main.L_LOGGED_IN.setText("Logged in as " + Main.me.username);
            Main.L_LOGGED_IN.setVisible(true);
            
            Main.loggedIn = true;
            
            Main.playtesterId = LilypadKey.generate(Main.username);
        }

    }
}
class MinecraftData
{
    protected static final String[] libraries = {
        "https://libraries.minecraft.net/net/minecraft/launchwrapper/1.5/launchwrapper-1.5.jar",
        "https://libraries.minecraft.net/net/sf/jopt-simple/jopt-simple/4.5/jopt-simple-4.5.jar",
        "https://libraries.minecraft.net/org/ow2/asm/asm-all/4.1/asm-all-4.1.jar",
        "https://libraries.minecraft.net/net/java/jinput/jinput/2.0.5/jinput-2.0.5.jar",
        "https://libraries.minecraft.net/net/java/jutils/jutils/1.0.0/jutils-1.0.0.jar",
        "https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl/2.9.0/lwjgl-2.9.0.jar",
        "https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl_util/2.9.0/lwjgl_util-2.9.0.jar"
        };
    protected static final String[] natives = {
        "https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl-platform/2.9.0/lwjgl-platform-2.9.0-natives-linux.jar",
        "https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl-platform/2.9.0/lwjgl-platform-2.9.0-natives-osx.jar",
        "https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl-platform/2.9.0/lwjgl-platform-2.9.0-natives-windows.jar",
        "https://libraries.minecraft.net/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-linux.jar",
        "https://libraries.minecraft.net/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-osx.jar",
        "https://libraries.minecraft.net/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-windows.jar"
        };
    protected static final String game_jar = "https://github.com/glowiak2/a1.0.16.05-mirror/raw/main/v1605_lilypad_qa.jar";
    
    protected static String getMinecraftDir()
    {
        if (System.getProperty("os.name").contains("indows"))
        {
            return System.getenv("APPDATA") + "/.minecraft";
        } else
        if (System.getProperty("os.name").contains("inux") || System.getProperty("os.name").toLowerCase().contains("bsd"))
        {
            return System.getenv("HOME") + "/.minecraft";
        } else
        {
            return null;
        }
    }
}
class Util
{
    protected static boolean downloadFile(String url, String dest)
    {
        try {
            File ie = new File(dest);
            if (!ie.exists())
            {
                URL u1 = new URL(url);
                Path u2 = Paths.get(dest);
                InputStream is = u1.openStream();
                Files.copy(is, u2, StandardCopyOption.REPLACE_EXISTING);
            }
            return true;
        } catch (IOException ioe) { ioe.printStackTrace(); return false; }
    }
    protected static String getFileFromUrl(String url)
    {
        return url.substring(url.lastIndexOf('/') + 1, url.length());
    }
    private static final int BUFFER_SIZE = 4096;
    protected static void unzip(String zfp, String dfp) throws IOException {
        File dest = new File(dfp);
        if (!dest.exists()) {
            dest.mkdir();
        }
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zfp));
        ZipEntry ze = zis.getNextEntry();

        while (ze != null) {
            String File_Path = dfp + File.separator + ze.getName();
            if (!ze.isDirectory()) {
                extractFile(zis, File_Path);
            } else {
                new File(File_Path).mkdirs();
            }
            zis.closeEntry();
            ze = zis.getNextEntry();
        }
        zis.close();
    }

    private static void extractFile(ZipInputStream zis, String path) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
        byte[] bytes = new byte[BUFFER_SIZE];
        int rb = 0;
        while ((rb = zis.read(bytes)) != -1) {
            bos.write(bytes, 0, rb);
        }
        bos.close();
    }

}
class GameRunner implements ActionListener
{
    private static JFrame DOWNLOADER;
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (Main.loggedIn)
        {
            new File(MinecraftData.getMinecraftDir()).mkdirs();
            try {
                FileWriter fw = new FileWriter(MinecraftData.getMinecraftDir() + "/.user");
                fw.write(Main.username);
                fw.close();
                
                fw = new FileWriter(MinecraftData.getMinecraftDir() + "/.password");
                fw.write(Main.password);
                fw.close();
            } catch (IOException ioe) { ioe.printStackTrace(); }
            
            DOWNLOADER = new JFrame("Progress");
            DOWNLOADER.setSize(650, 1);
            DOWNLOADER.setVisible(true);
            DOWNLOADER.setResizable(false);
            
            downloadJars();
            unpackNatives();
            
            DOWNLOADER.dispose();
            
            runGame();
        } else
        {
            JOptionPane.showMessageDialog(Main.me, "Log in first");
        }
    }
    
    protected static void downloadJars()
    {
        new File(MinecraftData.getMinecraftDir() + "/bin/libs").mkdirs();
        
        for (int i = 0; i < MinecraftData.libraries.length; i++)
        {
            DOWNLOADER.setTitle("Downloading " + MinecraftData.libraries[i] + "...");
            boolean result = Util.downloadFile(MinecraftData.libraries[i], MinecraftData.getMinecraftDir() + "/bin/libs/" + Util.getFileFromUrl(MinecraftData.libraries[i]));
            
            if (!result)
            {
                JOptionPane.showMessageDialog(Main.me, "Could not download file " + Util.getFileFromUrl(MinecraftData.libraries[i]) + ". Check your internet connection.");
                return;
            }
        }
        for (int i = 0; i < MinecraftData.natives.length; i++)
        {
            DOWNLOADER.setTitle("Downloading " + MinecraftData.libraries[i] + "...");
            boolean result = Util.downloadFile(MinecraftData.natives[i], MinecraftData.getMinecraftDir() + "/bin/libs/" + Util.getFileFromUrl(MinecraftData.natives[i]));
            
            if (!result)
            {
                JOptionPane.showMessageDialog(Main.me, "Could not download file " + Util.getFileFromUrl(MinecraftData.natives[i]) + ". Check your internet connection.");
                return;
            }
        }
        
        DOWNLOADER.setTitle("Downloading " + MinecraftData.game_jar + "...");
        boolean result = Util.downloadFile(MinecraftData.game_jar, MinecraftData.getMinecraftDir() + "/bin/minecraft.jar");
        if (!result)
        {
            JOptionPane.showMessageDialog(Main.me, "Could not download minecraft.jar. Please check your connection.");
        }
    }
    protected void unpackNatives()
    {
        new File(MinecraftData.getMinecraftDir() + "/bin/natives").mkdirs();
        
        String os = null;
        if (System.getProperty("os.name").contains("indows"))
        {
            os = "windows";
        } else
        {
            os = "linux";
        }
        
        String fname = "lwjgl-platform-2.9.0-natives-" + os + ".jar";
        DOWNLOADER.setTitle("Extracting " + MinecraftData.getMinecraftDir() + "/bin/libs/" + fname + "...");
        try {
            Util.unzip(MinecraftData.getMinecraftDir() + "/bin/libs/" + fname, MinecraftData.getMinecraftDir() + "/bin/natives");
        } catch (IOException ioe)
        {
            JOptionPane.showMessageDialog(Main.me, "Could not extract " + fname + "!");
            return;
        }
        fname = "jinput-platform-2.0.5-natives-" + os + ".jar";
        DOWNLOADER.setTitle("Extracting " + MinecraftData.getMinecraftDir() + "/bin/libs/" + fname + "...");
        try {
            Util.unzip(MinecraftData.getMinecraftDir() + "/bin/libs/" + fname, MinecraftData.getMinecraftDir() + "/bin/natives");
        } catch (IOException ioe)
        {
            JOptionPane.showMessageDialog(Main.me, "Could not extract " + fname + "!");
            return;
        }
    }
    private String build_classpath()
    {
        String w = "";
        for (int i = 0; i < MinecraftData.libraries.length; i++)
        {
            w = MinecraftData.getMinecraftDir() + "/bin/libs/" + Util.getFileFromUrl(MinecraftData.libraries[i]) + ":" + w;
        }
        return w;
    }
    protected void runGame()
    {
        String javaPath = null;
        String os = null;
        if (System.getProperty("os.name").contains("indows"))
        {
            os = "windows";
        } else
        {
            os = "linux";
        }
        
        String javaHome = System.getProperty("java.home");
        if (os.equals("windows"))
        {
            javaPath = javaHome + "/bin/javaw.exe";
        } else
        {
            javaPath = javaHome + "/bin/java";
        }
        String cp = build_classpath() + MinecraftData.getMinecraftDir() + "/bin/minecraft.jar";
        
        try {
            String m = (javaPath + " -Xmn512M -Xmx512M -XX:+UseConcMarkSweepGC -Dhttp.proxyHost=betacraft.uk -Dfile.encoding=UTF-8 -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Djava.library.path=" + MinecraftData.getMinecraftDir() + "/bin/natives -Dminecraft.launcher.version=1.0 -cp " + cp + " net.minecraft.launchwrapper.Launch " + Main.username + " as --gameDir " + MinecraftData.getMinecraftDir());
            System.out.println(m);
            Runtime.getRuntime().exec(m);
        } catch (IOException ioe)
        {
            JOptionPane.showMessageDialog(Main.me, "Could not launch game Possable Not Working JVM Argument??");
        }
    }
}
class LilypadKey // does not work yet
{
    private static final int[] EndBytes = { 39, 86, 26, 72, 13, 91, 23 };
    
    protected static String generate(String name) // java clone of https://github.com/exalpha-dev/exalpha-dev.github.io/blob/main/avkeys.js
    {
        name = name.toUpperCase();
        String enc = "";
        int writtenBytes = 0;
        
        for (int i = 0; i < name.length(); i++)
        {
            enc += String.format("%d", (70 - (26 - (name.codePointAt(i) - "A".codePointAt(0)))));
            writtenBytes++;
        }
        enc += String.format("%d", EndBytes[(int)Math.floor((int)(Math.random() * EndBytes.length))]);
        writtenBytes++;
        
        String fullNameStr = enc;
        
        while (writtenBytes != 15)
        {
            fullNameStr += String.format("%d", 10 + (int)Math.floor(Math.random() * 89));
            writtenBytes++;
        }
        
        int checksum = 0;
        for (int i = 0; i < fullNameStr.length(); i++)
        {
            checksum += fullNameStr.codePointAt(i) - "0".codePointAt(0);
        }
        
        int checksumName = 0;
        for (int i = 0; i < enc.length(); i++)
        {
            checksumName += enc.codePointAt(i) - "0".codePointAt(0);
        }
        checksumName %= 100;
        
        int csp1 = checksum + (int)Math.floor(Math.random() * (999 - checksum));
        int csp2 = csp1 - checksum;
        
        String ret = "";
        
        ret += ("000" + csp1).substring(("000" + csp1).length() - 3);
        String ret1 = "";
        for (int i = ret.length() - 1; i > 0; i--)
        {
            ret1 += ret.charAt(i);
        }
        ret = ret1;
        
        ret += fullNameStr;
        ret += ("000" + csp1).substring(("000" + csp2).length() - 3);
        ret += ("00" + checksumName).substring(("00" + checksumName).length() - 2);
        
        ret = ret.substring(0, 6) + "-" + ret.substring(6);
        ret = ret.substring(0, 15) + "-" + ret.substring(15);
        ret = ret.substring(0, 23) + "-" + ret.substring(23);
        ret = ret.substring(0, 31) + "-" + ret.substring(31);
        ret = ret.substring(0, 36) + "-" + ret.substring(36);
        
        return ret;
    }
}
