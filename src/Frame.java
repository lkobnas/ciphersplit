import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
public class Frame extends JFrame implements ActionListener{

    JButton button1, button2, Efile, Dfile1, Dfile2, confirm1, confirm2;
    JPanel panel;
    JFrame encode, decode;
    String address1=null, address2=null, address=null;
    Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocation(400,200);
        this.setVisible(true);
        this.setSize(400, 300);
        //this.setLocation(EXIT_ON_CLOSE, ABORT);
        button1 = new JButton("File Encoder");
        button1.addActionListener(this);
        button2 = new JButton("File Decoder");
        button2.addActionListener(this);
        panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLocation(250,200);
        panel.setVisible(true);
        panel.add(button1);
        panel.add(button2);
        this.add(panel);

        encode = new JFrame("Encode");
        encode.pack();
        encode.setLocation(450,300);
        encode.setLayout(null);
        encode.setVisible(false);
        encode.setSize(400, 500);
        Efile = new JButton("Select a txt file to encode");
        Efile.addActionListener(this);
        Efile.setBounds(100,125,200,50);
        confirm1 = new JButton("Confirm");
        confirm1.addActionListener(this);
        confirm1.setBounds(125,350,150,50);
        encode.add(Efile);
        encode.add(confirm1);
        //Efile.setBackground(Color.RED);

        decode = new JFrame("Decode");
        decode.pack();
        decode.setLocation(650,300);
        decode.setLayout(null);
        decode.setVisible(false);
        decode.setSize(400,500);
        Dfile1 = new JButton("Select first file to decode");
        Dfile1.addActionListener(this);
        Dfile1.setBounds(100,125,200,50);
        Dfile2 = new JButton("Select second file to decode");
        Dfile2.addActionListener(this);
        Dfile2.setBounds(100,200,200,50);
        confirm2 = new JButton("Confirm");
        confirm2.addActionListener(this);
        confirm2.setBounds(125,350,150,50);
        decode.add(Dfile1);
        decode.add(Dfile2);
        decode.add(confirm2);

        encode.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    encode.setVisible(false);
            }
        });

        decode.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    decode.setVisible(false);
            }
        });

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e){
        // TODO Auto-generated method stub
        if(e.getSource()==button1) {
            encode.setVisible(true);
        }
        if(e.getSource()==button2) {
            decode.setVisible(true);
        }
        if(e.getSource()==Efile) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("."));
            int res1 = fileChooser.showOpenDialog(null);// null = parent component
            if(res1 == JFileChooser.APPROVE_OPTION) {
                System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
                address = fileChooser.getSelectedFile().getAbsolutePath();
                Efile.setBackground(Color.green);
            }
        }
        if(e.getSource()==Dfile1) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("."));
            int res1 = fileChooser.showOpenDialog(null);// null = parent component
            if(res1 == JFileChooser.APPROVE_OPTION) {
                System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
                address1 = fileChooser.getSelectedFile().getAbsolutePath();
                Dfile1.setBackground(Color.green);
            }
        }
        if(e.getSource()==Dfile2) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("."));
            int res1 = fileChooser.showOpenDialog(null);// null = parent component
            if(res1 == JFileChooser.APPROVE_OPTION) {
                System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
                address2 = fileChooser.getSelectedFile().getAbsolutePath();
                Dfile2.setBackground(Color.green);
            }
        }
        if(e.getSource()==confirm1) {
            if(address!=null) {
                try {
                    new Encode(address);
                    encode.setVisible(false);
                    this.setVisible(false);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    this.setVisible(false);
                    encode.setVisible(false);
                    e1.printStackTrace();
                }
            }else {
                JOptionPane.showMessageDialog(null,"Please select a file","Error",JOptionPane.WARNING_MESSAGE);
                Efile.setBackground(Color.red);
            }
        }
        if(e.getSource()==confirm2) {
            if(address1!=null && address2!=null) {
                try {
                    new Decode(address1,address2);
                    decode.setVisible(false);
                    this.setVisible(false);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    this.setVisible(false);
                    decode.setVisible(false);
                    e1.printStackTrace();
                }
            }else {
                JOptionPane.showMessageDialog(null,"Please select two file","Error",JOptionPane.WARNING_MESSAGE);
                if(address1==null && address2 == null){
                    Dfile1.setBackground(Color.red);
                    Dfile2.setBackground(Color.red);
                }else if(address1 ==null){
                    Dfile1.setBackground(Color.red);
                }else {
                    Dfile2.setBackground(Color.red);
                }
            }
        }




    }

}
