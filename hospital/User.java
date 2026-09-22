public abstract class User {
    private String id;
    private String name;
    private String username;
    private String password;

  public User(String id, String name, String username, String password) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.password = password;
}

    public String getId()              { return id; }
    public void setId(String id)       { this.id = id; }
    public String getName()            { return name; }
    public void setName(String name)   { this.name = name; }
    public String getUsername()        { return username; }
    public void setUsername(String u)  { this.username = u; }
    public String getPassword()        { return password; }
    public void setPassword(String p)  { this.password = p; }

    public abstract void viewProfile();
}
