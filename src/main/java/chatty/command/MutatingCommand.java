package chatty.command;

/** A command that mutates the task list. */
public abstract class MutatingCommand implements Command {
    @Override
    public boolean isMutating() {
        return true;
    }
}
