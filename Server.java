
package chatting.application;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ChatWindow extends JFrame {
    private JList<ChatListItem> chatList;

    public ChatWindow() {
        initialize();
    }

    private void initialize() {
        setTitle("Chat Window");
        setSize(470, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Color backgroundColor = new Color(0, 0, 0);

        getContentPane().setBackground(backgroundColor);

        JLabel appLabel = new JLabel("RFB", SwingConstants.CENTER);
        appLabel.setForeground(Color.LIGHT_GRAY);
        appLabel.setBackground(Color.BLACK);
        appLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        appLabel.setOpaque(true);
        appLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel chatsLabel = new JLabel("Chats", SwingConstants.LEFT);
        chatsLabel.setForeground(Color.WHITE);
        chatsLabel.setBackground(Color.BLACK);
        chatsLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        chatsLabel.setOpaque(true);
        chatsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        
        JTextField searchBar = new JTextField();
        searchBar.setFont(new Font("Helvetica", Font.PLAIN, 14));
        searchBar.setPreferredSize(new Dimension(200, 45));
        searchBar.setBorder(BorderFactory.createMatteBorder(10, 20, 10, 20,Color.BLACK));
        searchBar.setBackground(new Color(0, 150, 230));
        searchBar.setText("Search");
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (searchBar.getText().isEmpty()) {
                   searchBar.setText("Search");
                }
            }

            public void keyTyped(KeyEvent e) {
                if (searchBar.getText().equals("Search")) {
                    searchBar.setText("");
                }
            }
        });
        
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filterList();
            }

            public void removeUpdate(DocumentEvent e) {
                filterList();
            }

            public void changedUpdate(DocumentEvent e) {
                filterList();
            }
            
            private void filterModel(ListModel<ChatListItem> model, String filter) {
                DefaultListModel<ChatListItem> filteredModel = new DefaultListModel<>();
                for (int i = 0; i < model.getSize(); i++) {
                    ChatListItem item = model.getElementAt(i);
                    if (item.getName().toLowerCase().contains(filter.toLowerCase())) {
                        filteredModel.addElement(item);
                    }
                }
                chatList.setModel(filteredModel);
            }

            private void filterList() {
                String filter = searchBar.getText();
                if (filter.isEmpty()||filter.equals("Search")) {
                    chatList.setModel(new DefaultListModel<>());
                    DefaultListModel<ChatListItem> listModel = (DefaultListModel<ChatListItem>) chatList.getModel();
                    listModel.addElement(new ChatListItem("Zoha", new ImageIcon(ClassLoader.getSystemResource("icons/zoha.jpg"))));
                    listModel.addElement(new ChatListItem("Ayesha", new ImageIcon(ClassLoader.getSystemResource("icons/ayesha.png"))));
                    listModel.addElement(new ChatListItem("Royal AI", new ImageIcon(ClassLoader.getSystemResource("icons/chatbot1.png"))));
                } 
                else {
                    filterModel((DefaultListModel<ChatListItem>) chatList.getModel(), filter);
                }
            }
        });
       
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.add(appLabel, BorderLayout.NORTH);
        topPanel.add(chatsLabel, BorderLayout.CENTER);
        
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(backgroundColor);
        searchPanel.add(searchBar, BorderLayout.CENTER);
        
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        DefaultListModel<ChatListItem> listModel = new DefaultListModel<>();
        listModel.addElement(new ChatListItem("Fiza", new ImageIcon(ClassLoader.getSystemResource("icons/zoha.jpg"))));
        listModel.addElement(new ChatListItem("Raja", new ImageIcon(ClassLoader.getSystemResource("icons/ayesha.png")))); 
        listModel.addElement(new ChatListItem("Royal AI", new ImageIcon(ClassLoader.getSystemResource("icons/chatbot1.png"))));
    
        chatList = new JList<>(listModel);
        chatList.setCellRenderer(new ChatListRenderer());
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(chatList);
        scrollPane.setBackground(Color.BLACK);
        chatList.setBackground(Color.BLACK);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor); 
 
        mainPanel.add(topPanel,BorderLayout.NORTH);       
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        chatList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = chatList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        ChatListItem selectedChatItem = chatList.getSelectedValue();
                        String selectedChat = selectedChatItem.getName();
                        System.out.println("Selected chat: " + selectedChat);
                        if (selectedChat.equals("Zoha")) {
                            new Server();
                        } else if (selectedChat.equals("Ayesha")) {
                            new Client();
                        }
                        else if (selectedChat.equals("Royal AI")) {
                            new ChatBot();
                        }
                    }
                }
            }
        });

        setContentPane(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ChatWindow();
    }
}

class ChatListItem {
    private String name;
    private ImageIcon profilePic;

    public ChatListItem(String name, ImageIcon profilePic) {
        this.name = name;
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public ImageIcon getProfilePic() {
        return profilePic;
    }
}

class ChatListRenderer extends JPanel implements ListCellRenderer<ChatListItem> {
    private JLabel nameLabel;
    private JLabel iconLabel;

    public ChatListRenderer() {
        setLayout(new BorderLayout());
        nameLabel = new JLabel();
        iconLabel = new JLabel();
        add(nameLabel, BorderLayout.CENTER);
        add(iconLabel, BorderLayout.WEST);
    }

    public Component getListCellRendererComponent(JList<? extends ChatListItem> list, ChatListItem value, int index, boolean isSelected, boolean cellHasFocus) {
        nameLabel.setText(value.getName());
        nameLabel.setOpaque(true);
        nameLabel.setBackground(Color.BLACK);
        nameLabel.setForeground(Color.white);
        nameLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        ImageIcon profilePic = value.getProfilePic();
        if (profilePic != null) {
            Image scaledImage = profilePic.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            iconLabel.setIcon(scaledIcon);
        } else {
            iconLabel.setIcon(null);
        }
          iconLabel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,Color.BLACK));
          
        return this;
    }
}
