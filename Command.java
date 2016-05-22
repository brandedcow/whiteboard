public class Command {

	public String command;
    public DShape shape;

    public Command() {
        command = null;
        shape = null;
    }

    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }

    public DShape getShape() { 
        return shape;
    }
    public void setShape(DShape shape) {
        this.shape = shape;
    }

}