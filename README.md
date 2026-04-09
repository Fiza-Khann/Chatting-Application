# Chatting-Application
A Chatting Application demonstrates effective use of core OOP concepts.

The Chatting Application demonstrates effective use of core OOP concepts such as encapsulation, inheritance, 
polymorphism, and abstraction. Each class encapsulates specific functionalities, promoting modularity and 
maintainability. Inheritance from Swing components enables reuse of existing functionality, while polymorphism 
and event-driven programming provide flexibility and responsiveness. 
 
1. Encapsulation: 
                                 Encapsulation is the bundling of data with the methods that operate on that data. In your 
project, encapsulation is evident in the following ways: 
 
Class Definitions: Each class in your project (e.g., Client, ChatWindow, ProfilePage) encapsulates its data (fields) 
and methods. For example, the ProfilePage class encapsulates the user profile information and methods to display 
it. 
 
Access Modifiers: The use of private fields and public methods ensures that the internal state of the objects is 
protected from unauthorized access and modification. For instance, in the ProfilePage class, details such as name, 
profilePic, and isOnline are passed via the constructor and encapsulated within the class. 
 
2. Inheritance: 
Inheritance is the mechanism by which one class can inherit the properties and methods of another class. In your 
project, inheritance is used in the following ways: 
 
Swing Components: Your classes extend Swing components to create custom GUI elements. For example, 
ProfilePage extends JFrame to inherit its properties and methods, enabling it to function as a window. 
 
3. Polymorphism: 
Polymorphism allows objects to be treated as instances of their parent class rather than their actual class. This 
concept is demonstrated in your project as follows: 
 
 
ActionListeners and MouseListeners: By implementing interfaces such as ActionListener and MouseAdapter, your 
classes can handle different types of events. The Client class, for example, implements ActionListener to handle 
button click events polymorphically. 
 
ListCellRenderer: The ChatListRenderer class implements ListCellRenderer to customize the rendering of list items, 
allowing for polymorphic behavior in how each list item is displayed. 
 
4. Abstraction: 
Abstraction involves hiding complex implementation details and showing only the necessary features. In your 
project, abstraction is utilized as follows: 
 
GUI Components: The use of Swing components abstracts the complex details of rendering graphical elements. For 
example, using JLabel, JButton, and JScrollPane abstracts the drawing and event handling details. 
 
Networking: The handling of socket connections abstracts the low-level details of network communication. The 
Client class uses Socket, DataInputStream, and DataOutputStream to abstract the complexities of data transmission 
over a network.

<img width="298" height="427" alt="image" src="https://github.com/user-attachments/assets/2b438c87-824b-4a24-bbbf-8d2b1ff004cb" />
<img width="311" height="470" alt="image" src="https://github.com/user-attachments/assets/c3ab184a-d5ba-4d94-af9f-7d751a75df8b" />
<img width="321" height="486" alt="image" src="https://github.com/user-attachments/assets/7776645f-640b-4e3f-98b8-f41698cd4313" />
<img width="262" height="234" alt="image" src="https://github.com/user-attachments/assets/745d4b11-9345-4c80-b1d4-b33991658cd0" />
